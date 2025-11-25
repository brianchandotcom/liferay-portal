/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.converter;

import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.util.configuration.FragmentConfigurationField;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.headless.admin.site.dto.v1_0.DefaultFragmentReference;
import com.liferay.headless.admin.site.dto.v1_0.FragmentConfigurationFieldValue;
import com.liferay.headless.admin.site.dto.v1_0.FragmentInstancePageElementDefinition;
import com.liferay.headless.admin.site.dto.v1_0.FragmentItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.PageElementDefinition;
import com.liferay.headless.admin.site.dto.v1_0.WidgetInstance;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.FragmentEditableElementUtil;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.FragmentViewportUtil;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.ItemScopeUtil;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.WidgetInstanceUtil;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "dto.class.name=com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem",
	service = DTOConverter.class
)
public class FragmentInstancePageElementDefinitionDTOConverter
	implements DTOConverter
		<FragmentStyledLayoutStructureItem,
		 FragmentInstancePageElementDefinition> {

	@Override
	public String getContentType() {
		return FragmentInstancePageElementDefinition.class.getSimpleName();
	}

	@Override
	public FragmentInstancePageElementDefinition toDTO(
			DTOConverterContext dtoConverterContext,
			FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem)
		throws Exception {

		Long companyId = (Long)dtoConverterContext.getAttribute("companyId");
		Long scopeGroupId = (Long)dtoConverterContext.getAttribute(
			"scopeGroupId");

		if ((companyId == null) || (scopeGroupId == null)) {
			throw new UnsupportedOperationException();
		}

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentStyledLayoutStructureItem.getFragmentEntryLinkId());

		if (fragmentEntryLink == null) {
			throw new UnsupportedOperationException();
		}

		return new FragmentInstancePageElementDefinition() {
			{
				setConfiguration(fragmentEntryLink::getConfiguration);
				setCss(fragmentEntryLink::getCss);
				setCssClasses(
					() -> {
						if (SetUtil.isEmpty(
								fragmentStyledLayoutStructureItem.
									getCssClasses())) {

							return null;
						}

						return ArrayUtil.toStringArray(
							fragmentStyledLayoutStructureItem.getCssClasses());
					});
				setCustomCSS(fragmentStyledLayoutStructureItem::getCustomCSS);
				setDatePropagated(fragmentEntryLink::getLastPropagationDate);
				setDraftFragmentInstanceExternalReferenceCode(
					() -> _getDraftFragmentInstanceExternalReferenceCode(
						fragmentEntryLink));
				setFragmentConfigurationFieldValues(
					() -> _getFragmentConfigurationFieldValues(
						fragmentEntryLink));
				setFragmentEditableElements(
					() ->
						FragmentEditableElementUtil.getFragmentEditableElements(
							companyId, fragmentEntryLink,
							_infoItemServiceRegistry, scopeGroupId));
				setFragmentInstanceExternalReferenceCode(
					fragmentEntryLink::getExternalReferenceCode);
				setFragmentReference(
					() -> {
						if (Validator.isNull(
								fragmentEntryLink.getFragmentEntryERC()) &&
							Validator.isNull(
								fragmentEntryLink.getRendererKey())) {

							return null;
						}

						if (Validator.isNotNull(
								fragmentEntryLink.getFragmentEntryERC())) {

							return new FragmentItemExternalReference() {
								{
									setExternalReferenceCode(
										fragmentEntryLink::getFragmentEntryERC);
									setFragmentReferenceType(
										() ->
											FragmentReferenceType.
												FRAGMENT_ITEM_EXTERNAL_REFERENCE);
									setScope(
										() -> ItemScopeUtil.getItemScope(
											fragmentEntryLink.getCompanyId(),
											fragmentEntryLink.
												getFragmentEntryScopeERC(),
											fragmentEntryLink.getGroupId()));
								}
							};
						}

						return new DefaultFragmentReference() {
							{
								setDefaultFragmentKey(
									fragmentEntryLink::getRendererKey);
								setFragmentReferenceType(
									() ->
										FragmentReferenceType.
											DEFAULT_FRAGMENT_REFERENCE);
							}
						};
					});
				setFragmentType(
					() -> {
						if (fragmentEntryLink.isTypeComponent()) {
							return FragmentType.BASIC;
						}

						return FragmentType.FORM;
					});
				setFragmentViewports(
					() -> FragmentViewportUtil.toFragmentViewports(
						fragmentStyledLayoutStructureItem.
							getItemConfigJSONObject()));
				setHtml(fragmentEntryLink::getHtml);
				setIndexed(fragmentStyledLayoutStructureItem::isIndexed);
				setJs(fragmentEntryLink::getJs);
				setName(fragmentStyledLayoutStructureItem::getName);
				setNamespace(fragmentEntryLink::getNamespace);
				setType(() -> PageElementDefinition.Type.FRAGMENT);
				setUuid(fragmentEntryLink::getUuid);
				setWidgetInstances(
					() -> _getWidgetInstances(fragmentEntryLink));
			}
		};
	}

	private String _getDraftFragmentInstanceExternalReferenceCode(
		FragmentEntryLink fragmentEntryLink) {

		String originalFragmentEntryLinkERC =
			fragmentEntryLink.getOriginalFragmentEntryLinkERC();

		if (Validator.isNull(originalFragmentEntryLinkERC)) {
			return null;
		}

		FragmentEntryLink originalFragmentEntryLink =
			_fragmentEntryLinkLocalService.
				fetchFragmentEntryLinkByExternalReferenceCode(
					originalFragmentEntryLinkERC,
					fragmentEntryLink.getGroupId());

		if (originalFragmentEntryLink == null) {
			return null;
		}

		return originalFragmentEntryLink.getExternalReferenceCode();
	}

	private DTOConverterContext _getDTOConverterContext(
		long companyId, long scopeGroupId) {

		DTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(null, null, null, null, null);

		dtoConverterContext.setAttribute("companyId", companyId);
		dtoConverterContext.setAttribute("scopeGroupId", scopeGroupId);

		return dtoConverterContext;
	}

	private Map<String, FragmentConfigurationFieldValue>
			_getFragmentConfigurationFieldValues(
				FragmentEntryLink fragmentEntryLink)
		throws Exception {

		JSONObject editableValuesJSONObject =
			fragmentEntryLink.getEditableValuesJSONObject();

		JSONObject freeMarkerJSONObject =
			editableValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);

		if (freeMarkerJSONObject == null) {
			return Collections.emptyMap();
		}

		JSONObject configurationJSONObject =
			fragmentEntryLink.getConfigurationJSONObject();

		if (configurationJSONObject == null) {
			return Collections.emptyMap();
		}

		DTOConverterContext dtoConverterContext = _getDTOConverterContext(
			fragmentEntryLink.getCompanyId(), fragmentEntryLink.getGroupId());

		Map<String, FragmentConfigurationFieldValue> map = new HashMap<>();

		for (FragmentConfigurationField fragmentConfigurationField :
				_fragmentEntryConfigurationParser.
					getFragmentConfigurationFields(
						fragmentEntryLink.getConfigurationJSONObject())) {

			if (!freeMarkerJSONObject.has(
					fragmentConfigurationField.getName())) {

				continue;
			}

			dtoConverterContext.setAttribute(
				"fragmentFragmentConfigurationFieldValue",
				freeMarkerJSONObject.get(fragmentConfigurationField.getName()));

			map.put(
				fragmentConfigurationField.getName(),
				_configurationFieldValueDTOConverter.toDTO(
					dtoConverterContext, fragmentConfigurationField));
		}

		return map;
	}

	private WidgetInstance[] _getWidgetInstances(
		FragmentEntryLink fragmentEntryLink) {

		List<String> fragmentEntryLinkPortletIds =
			_portletRegistry.getFragmentEntryLinkPortletIds(fragmentEntryLink);

		if (ListUtil.isEmpty(fragmentEntryLinkPortletIds)) {
			return null;
		}

		List<WidgetInstance> widgetInstances = new ArrayList<>();

		for (String fragmentEntryLinkPortletId : fragmentEntryLinkPortletIds) {
			widgetInstances.add(
				WidgetInstanceUtil.getWidgetInstance(
					PortletIdCodec.decodeInstanceId(fragmentEntryLinkPortletId),
					fragmentEntryLink.getPlid(), fragmentEntryLinkPortletId));
		}

		return widgetInstances.toArray(new WidgetInstance[0]);
	}

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.FragmentConfigurationFieldValueDTOConverter)"
	)
	private DTOConverter
		<FragmentConfigurationField, FragmentConfigurationFieldValue>
			_configurationFieldValueDTOConverter;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private PortletRegistry _portletRegistry;

}