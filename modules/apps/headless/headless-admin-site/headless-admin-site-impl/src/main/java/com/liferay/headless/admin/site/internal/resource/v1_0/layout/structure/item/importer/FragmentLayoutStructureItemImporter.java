/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.DefaultFragmentEntryProcessorContext;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.headless.admin.site.dto.v1_0.FragmentInstancePageElementDefinition;
import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.dto.v1_0.WidgetInstance;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.FragmentEditableElementUtil;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.FragmentEntryReference;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.FragmentEntryReferenceUtil;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.FragmentViewportUtil;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.context.LayoutStructureItemImporterContext;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.util.FragmentConfigurationFieldValuesUtil;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.LayoutStructureUtil;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.PortletUtil;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Eudaldo Alonso
 */
public class FragmentLayoutStructureItemImporter
	implements LayoutStructureItemImporter {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
			LayoutStructure layoutStructure,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext,
			PageElement pageElement)
		throws Exception {

		FragmentInstancePageElementDefinition
			fragmentInstancePageElementDefinition =
				(FragmentInstancePageElementDefinition)
					pageElement.getPageElementDefinition();

		if (fragmentInstancePageElementDefinition == null) {
			return null;
		}

		List<String> fragmentEntryLinkPortletIds = null;

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.
				fetchFragmentEntryLinkByExternalReferenceCode(
					fragmentInstancePageElementDefinition.
						getFragmentInstanceExternalReferenceCode(),
					layoutStructureItemImporterContext.getGroupId());

		if (fragmentEntryLink == null) {
			fragmentEntryLink = _addFragmentEntryLink(
				fragmentInstancePageElementDefinition,
				layoutStructureItemImporterContext);
		}
		else {
			PortletRegistry portletRegistry =
				_portletRegistryServiceTracker.getService();

			if (portletRegistry != null) {
				fragmentEntryLinkPortletIds =
					portletRegistry.getFragmentEntryLinkPortletIds(
						fragmentEntryLink);
			}

			fragmentEntryLink = _updateFragmentEntryLink(
				fragmentEntryLink, fragmentInstancePageElementDefinition,
				layoutStructureItemImporterContext);
		}

		if (fragmentEntryLink == null) {
			return null;
		}

		Layout layout = layoutStructureItemImporterContext.getLayout();

		if (ArrayUtil.isNotEmpty(
				fragmentInstancePageElementDefinition.getWidgetInstances())) {

			for (WidgetInstance widgetInstance :
					fragmentInstancePageElementDefinition.
						getWidgetInstances()) {

				if (Validator.isNull(widgetInstance.getWidgetName())) {
					continue;
				}

				String portletId = PortletIdCodec.encode(
					widgetInstance.getWidgetName(),
					widgetInstance.getWidgetInstanceId());

				PortletUtil.importPortletPermissions(
					layout, portletId, widgetInstance.getWidgetName(),
					widgetInstance.getWidgetPermissions());
				PortletUtil.importPortletPreferences(
					layout, portletId, widgetInstance.getWidgetConfig());
			}
		}

		if (ListUtil.isNotEmpty(fragmentEntryLinkPortletIds)) {
			for (String fragmentEntryLinkPortletId :
					SetUtil.asymmetricDifference(
						fragmentEntryLinkPortletIds,
						_getPortletIds(
							fragmentInstancePageElementDefinition.
								getWidgetInstances()))) {

				PortletPreferencesLocalServiceUtil.deletePortletPreferences(
					PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
					fragmentEntryLinkPortletId);
				ResourcePermissionLocalServiceUtil.deleteResourcePermissions(
					layout.getCompanyId(), fragmentEntryLinkPortletId,
					ResourceConstants.SCOPE_INDIVIDUAL,
					PortletPermissionUtil.getPrimaryKey(
						layout.getPlid(), fragmentEntryLinkPortletId));
			}
		}

		FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem =
			(FragmentStyledLayoutStructureItem)
				layoutStructure.addFragmentStyledLayoutStructureItem(
					fragmentEntryLink.getFragmentEntryLinkId(),
					pageElement.getExternalReferenceCode(),
					LayoutStructureUtil.getParentExternalReferenceCode(
						pageElement, layoutStructure),
					pageElement.getPosition());

		fragmentStyledLayoutStructureItem.setCssClasses(
			SetUtil.fromArray(
				fragmentInstancePageElementDefinition.getCssClasses()));
		fragmentStyledLayoutStructureItem.setIndexed(
			GetterUtil.getBoolean(
				fragmentInstancePageElementDefinition.getIndexed(), true));
		fragmentStyledLayoutStructureItem.setName(
			fragmentInstancePageElementDefinition.getName());

		JSONObject fragmentViewportsJSONObject =
			FragmentViewportUtil.toFragmentViewportsJSONObject(
				fragmentInstancePageElementDefinition.getFragmentViewports());

		if (fragmentViewportsJSONObject != null) {
			fragmentStyledLayoutStructureItem.updateItemConfig(
				fragmentViewportsJSONObject);
		}

		return fragmentStyledLayoutStructureItem;
	}

	private FragmentEntryLink _addFragmentEntryLink(
			FragmentInstancePageElementDefinition
				fragmentInstancePageElementDefinition,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext)
		throws Exception {

		Layout layout = layoutStructureItemImporterContext.getLayout();

		FragmentEntryReference fragmentEntryReference =
			FragmentEntryReferenceUtil.getFragmentEntryReference(
				layoutStructureItemImporterContext.getCompanyId(),
				fragmentInstancePageElementDefinition.getFragmentReference(),
				layoutStructureItemImporterContext.getGroupId());

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date createDate = serviceContext.getCreateDate();
		String uuid = serviceContext.getUuid();

		try {
			serviceContext.setCreateDate(
				fragmentInstancePageElementDefinition.getDatePropagated());
			serviceContext.setUuid(
				fragmentInstancePageElementDefinition.getUuid());

			FragmentEntryLink fragmentEntryLink =
				FragmentEntryLinkLocalServiceUtil.addFragmentEntryLink(
					fragmentInstancePageElementDefinition.
						getFragmentInstanceExternalReferenceCode(),
					layoutStructureItemImporterContext.getUserId(),
					layout.getGroupId(),
					_getOriginalFragmentEntryLinkERC(
						fragmentInstancePageElementDefinition,
						layoutStructureItemImporterContext),
					fragmentEntryReference.getFragmentEntryERC(),
					fragmentEntryReference.getFragmentEntryScopeERC(),
					layoutStructureItemImporterContext.
						getSegmentsExperienceId(),
					layout.getPlid(),
					GetterUtil.getString(
						fragmentInstancePageElementDefinition.getCss()),
					GetterUtil.getString(
						fragmentInstancePageElementDefinition.getHtml()),
					GetterUtil.getString(
						fragmentInstancePageElementDefinition.getJs()),
					GetterUtil.getString(
						fragmentInstancePageElementDefinition.
							getConfiguration()),
					_getEditableValues(
						fragmentInstancePageElementDefinition,
						layoutStructureItemImporterContext),
					fragmentInstancePageElementDefinition.getNamespace(), 0,
					fragmentEntryReference.getRendererKey(),
					_getType(fragmentInstancePageElementDefinition),
					serviceContext);

			FragmentEntryProcessorRegistry fragmentEntryProcessorRegistry =
				layoutStructureItemImporterContext.
					getFragmentEntryProcessorRegistry();

			return FragmentEntryLinkLocalServiceUtil.updateFragmentEntryLink(
				fragmentEntryLink.getUserId(),
				fragmentEntryLink.getFragmentEntryLinkId(),
				fragmentEntryProcessorRegistry.mergeDefaultEditableValues(
					fragmentEntryLink.getConfigurationJSONObject(),
					fragmentEntryLink.getEditableValuesJSONObject(),
					_getProcessedHTML(
						fragmentEntryLink, fragmentEntryProcessorRegistry,
						serviceContext)),
				false);
		}
		finally {
			serviceContext.setCreateDate(createDate);
			serviceContext.setUuid(uuid);
		}
	}

	private String _getEditableValues(
			FragmentInstancePageElementDefinition
				fragmentInstancePageElementDefinition,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext)
		throws Exception {

		return JSONUtil.put(
			FragmentEntryProcessorConstants.
				KEY_BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR,
			FragmentEditableElementUtil.
				getBackgroundImageFragmentEntryProcessorJSONObject(
					fragmentInstancePageElementDefinition.
						getFragmentEditableElements(),
					layoutStructureItemImporterContext)
		).put(
			FragmentEntryProcessorConstants.
				KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
			FragmentEditableElementUtil.
				getEditableFragmentEntryProcessorJSONObject(
					fragmentInstancePageElementDefinition.
						getFragmentEditableElements(),
					layoutStructureItemImporterContext)
		).put(
			FragmentEntryProcessorConstants.
				KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
			FragmentConfigurationFieldValuesUtil.
				getFreeMarkerFragmentEntryProcessorJSONObject(
					fragmentInstancePageElementDefinition.getConfiguration(),
					fragmentInstancePageElementDefinition.
						getFragmentConfigurationFieldValues(),
					layoutStructureItemImporterContext)
		).toString();
	}

	private String _getOriginalFragmentEntryLinkERC(
		FragmentInstancePageElementDefinition
			fragmentInstancePageElementDefinition,
		LayoutStructureItemImporterContext layoutStructureItemImporterContext) {

		if (Validator.isNull(
				fragmentInstancePageElementDefinition.
					getDraftFragmentInstanceExternalReferenceCode())) {

			return null;
		}

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.
				fetchFragmentEntryLinkByExternalReferenceCode(
					fragmentInstancePageElementDefinition.
						getDraftFragmentInstanceExternalReferenceCode(),
					layoutStructureItemImporterContext.getGroupId());

		if (fragmentEntryLink == null) {
			return null;
		}

		return fragmentEntryLink.getExternalReferenceCode();
	}

	private List<String> _getPortletIds(WidgetInstance[] widgetInstances) {
		List<String> portletIds = new ArrayList<>();

		if (ArrayUtil.isEmpty(widgetInstances)) {
			return portletIds;
		}

		for (WidgetInstance widgetInstance : widgetInstances) {
			portletIds.add(
				PortletIdCodec.encode(
					widgetInstance.getWidgetName(),
					widgetInstance.getWidgetInstanceId()));
		}

		return portletIds;
	}

	private String _getProcessedHTML(
			FragmentEntryLink fragmentEntryLink,
			FragmentEntryProcessorRegistry fragmentEntryProcessorRegistry,
			ServiceContext serviceContext)
		throws PortalException {

		if (serviceContext == null) {
			return fragmentEntryLink.getHtml();
		}

		HttpServletRequest httpServletRequest = serviceContext.getRequest();
		HttpServletResponse httpServletResponse = serviceContext.getResponse();
		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		if ((httpServletRequest == null) && (themeDisplay != null)) {
			httpServletRequest = themeDisplay.getRequest();
		}

		if ((httpServletResponse == null) && (themeDisplay != null)) {
			httpServletResponse = themeDisplay.getResponse();
		}

		if ((httpServletRequest == null) || (httpServletResponse == null)) {
			return fragmentEntryLink.getHtml();
		}

		fragmentEntryLink.setEditableValues(null);

		FragmentEntryProcessorContext fragmentEntryProcessorContext =
			new DefaultFragmentEntryProcessorContext(
				httpServletRequest, httpServletResponse,
				FragmentEntryLinkConstants.EDIT,
				LocaleUtil.getMostRelevantLocale());

		return fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
			fragmentEntryLink, fragmentEntryProcessorContext);
	}

	private int _getType(
		FragmentInstancePageElementDefinition
			fragmentInstancePageElementDefinition) {

		int type = FragmentConstants.TYPE_COMPONENT;

		if (Objects.equals(
				FragmentInstancePageElementDefinition.FragmentType.FORM,
				fragmentInstancePageElementDefinition.getFragmentType())) {

			type = FragmentConstants.TYPE_INPUT;
		}

		return type;
	}

	private FragmentEntryLink _updateFragmentEntryLink(
			FragmentEntryLink fragmentEntryLink,
			FragmentInstancePageElementDefinition
				fragmentInstancePageElementDefinition,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext)
		throws Exception {

		Layout layout = layoutStructureItemImporterContext.getLayout();

		if ((fragmentEntryLink.getPlid() != layout.getPlid()) ||
			(fragmentEntryLink.getSegmentsExperienceId() !=
				layoutStructureItemImporterContext.getSegmentsExperienceId())) {

			throw new UnsupportedOperationException();
		}

		FragmentEntryReference fragmentEntryReference =
			FragmentEntryReferenceUtil.getFragmentEntryReference(
				layoutStructureItemImporterContext.getCompanyId(),
				fragmentInstancePageElementDefinition.getFragmentReference(),
				layoutStructureItemImporterContext.getGroupId());

		fragmentEntryLink.setOriginalFragmentEntryLinkERC(
			_getOriginalFragmentEntryLinkERC(
				fragmentInstancePageElementDefinition,
				layoutStructureItemImporterContext));
		fragmentEntryLink.setFragmentEntryERC(
			fragmentEntryReference.getFragmentEntryERC());
		fragmentEntryLink.setFragmentEntryScopeERC(
			fragmentEntryReference.getFragmentEntryScopeERC());
		fragmentEntryLink.setCss(
			GetterUtil.getString(
				fragmentInstancePageElementDefinition.getCss()));
		fragmentEntryLink.setHtml(
			GetterUtil.getString(
				fragmentInstancePageElementDefinition.getHtml()));
		fragmentEntryLink.setJs(
			GetterUtil.getString(
				fragmentInstancePageElementDefinition.getJs()));
		fragmentEntryLink.setConfiguration(
			GetterUtil.getString(
				fragmentInstancePageElementDefinition.getConfiguration()));
		fragmentEntryLink.setEditableValues(
			_getEditableValues(
				fragmentInstancePageElementDefinition,
				layoutStructureItemImporterContext));
		fragmentEntryLink.setNamespace(
			fragmentInstancePageElementDefinition.getNamespace());
		fragmentEntryLink.setRendererKey(
			fragmentEntryReference.getRendererKey());
		fragmentEntryLink.setType(
			_getType(fragmentInstancePageElementDefinition));
		fragmentEntryLink.setLastPropagationDate(
			fragmentInstancePageElementDefinition.getDatePropagated());

		fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.updateFragmentEntryLink(
				fragmentEntryLink);

		FragmentEntryProcessorRegistry fragmentEntryProcessorRegistry =
			layoutStructureItemImporterContext.
				getFragmentEntryProcessorRegistry();

		return FragmentEntryLinkLocalServiceUtil.updateFragmentEntryLink(
			fragmentEntryLink.getUserId(),
			fragmentEntryLink.getFragmentEntryLinkId(),
			fragmentEntryProcessorRegistry.mergeDefaultEditableValues(
				fragmentEntryLink.getConfigurationJSONObject(),
				fragmentEntryLink.getEditableValuesJSONObject(),
				_getProcessedHTML(
					fragmentEntryLink, fragmentEntryProcessorRegistry,
					ServiceContextThreadLocal.getServiceContext())),
			false);
	}

	private static final ServiceTracker<PortletRegistry, PortletRegistry>
		_portletRegistryServiceTracker = ServiceTrackerFactory.open(
			FrameworkUtil.getBundle(FragmentLayoutStructureItemImporter.class),
			PortletRegistry.class);

}