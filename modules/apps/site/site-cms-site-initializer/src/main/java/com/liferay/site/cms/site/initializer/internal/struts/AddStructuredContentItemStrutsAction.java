/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.struts;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.fragment.listener.FragmentEntryLinkListener;
import com.liferay.fragment.listener.FragmentEntryLinkListenerRegistry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.layout.constants.LayoutTypeSettingsConstants;
import com.liferay.layout.manager.FormManager;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.FormStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.portlet.FriendlyURLResolverRegistryUtil;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = "path=/cms/add_structured_content_item",
	service = StrutsAction.class
)
public class AddStructuredContentItemStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		long objectDefinitionId = ParamUtil.getLong(
			httpServletRequest, "objectDefinitionId");

		ObjectDefinition objectDefinition =
			_objectDefinitionService.getObjectDefinition(objectDefinitionId);

		if (!Objects.equals(
				objectDefinition.getScope(),
				ObjectDefinitionConstants.SCOPE_DEPOT)) {

			return null;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group group = _groupLocalService.getGroup(
			themeDisplay.getCompanyId(), GroupConstants.CMS);

		long classNameId = _portal.getClassNameId(
			objectDefinition.getClassName());

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				fetchDefaultLayoutPageTemplateEntry(
					group.getGroupId(), classNameId, 0);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			httpServletRequest);

		if (layoutPageTemplateEntry == null) {
			layoutPageTemplateEntry = _addDefaultLayoutPageTemplateEntry(
				classNameId, group.getGroupId(), objectDefinition.getName(),
				ParamUtil.getLong(httpServletRequest, "plid"), serviceContext);
		}

		String groupFriendlyURL = _portal.getGroupFriendlyURL(
			group.getPublicLayoutSet(), themeDisplay, false, false);

		Layout layout = _layoutLocalService.fetchLayout(
			layoutPageTemplateEntry.getPlid());

		long groupId = _getGroupId(httpServletRequest, serviceContext);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		ObjectEntry objectEntry = _objectEntryService.addObjectEntry(
			groupId, objectDefinitionId,
			_getObjectEntryFolderId(
				themeDisplay.getCompanyId(), groupId, objectDefinition),
			LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale()),
			Collections.emptyMap(), serviceContext);

		httpServletResponse.sendRedirect(
			_portal.escapeRedirect(
				_portal.addPreservedParameters(
					themeDisplay,
					StringBundler.concat(
						groupFriendlyURL, _getURLSeparator(),
						layout.getFriendlyURL(themeDisplay.getLocale()),
						StringPool.SLASH, classNameId, StringPool.SLASH,
						objectEntry.getObjectEntryId()))));

		return null;
	}

	private LayoutPageTemplateEntry _addDefaultLayoutPageTemplateEntry(
			long classNameId, long groupId, String name, long plid,
			ServiceContext serviceContext)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				null, serviceContext.getUserId(), groupId, 0, null, classNameId,
				0, name, LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE, 0,
				true, 0, 0, 0, WorkflowConstants.STATUS_APPROVED,
				serviceContext);

		Layout layout = _layoutLocalService.getLayout(
			layoutPageTemplateEntry.getPlid());

		Layout draftLayout = layout.fetchDraftLayout();

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					draftLayout.getGroupId(), draftLayout.getPlid());

		if (layoutPageTemplateStructure == null) {
			return layoutPageTemplateEntry;
		}

		long segmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				draftLayout.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(segmentsExperienceId));

		FormStyledLayoutStructureItem formStyledLayoutStructureItem =
			(FormStyledLayoutStructureItem)
				layoutStructure.addFormStyledLayoutStructureItem(
					layoutStructure.getMainItemId(), 0);

		formStyledLayoutStructureItem.setClassNameId(
			layoutPageTemplateEntry.getClassNameId());

		List<FragmentEntryLink> addedFragmentEntryLinks = new ArrayList<>();

		_formManager.addFragmentEntryLinksLayoutStructureItems(
			addedFragmentEntryLinks, _jsonFactory.createJSONObject(),
			formStyledLayoutStructureItem, true, draftLayout, layoutStructure,
			LocaleUtil.getMostRelevantLocale(), segmentsExperienceId,
			serviceContext, null);

		Layout backLayout = _layoutLocalService.fetchLayout(plid);

		if (backLayout != null) {
			layoutStructure.updateItemConfig(
				JSONUtil.put(
					"successMessage",
					JSONUtil.put(
						"layout",
						JSONUtil.put(
							"groupId", backLayout.getGroupId()
						).put(
							"layoutId", backLayout.getLayoutId()
						).put(
							"layoutUuid", backLayout.getUuid()
						).put(
							"private", backLayout.isPrivateLayout()
						).put(
							"title", backLayout.getTitle()
						)
					).put(
						"showNotification", true
					).put(
						"type", "page"
					)),
				formStyledLayoutStructureItem.getItemId());
		}

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				draftLayout.getGroupId(), draftLayout.getPlid(),
				segmentsExperienceId, layoutStructure.toString());

		for (FragmentEntryLink addedFragmentEntryLink :
				addedFragmentEntryLinks) {

			for (FragmentEntryLinkListener fragmentEntryLinkListener :
					_fragmentEntryLinkListenerRegistry.
						getFragmentEntryLinkListeners()) {

				fragmentEntryLinkListener.onAddFragmentEntryLink(
					addedFragmentEntryLink);
			}
		}

		_layoutLocalService.copyLayoutContent(draftLayout, layout);

		draftLayout = _layoutLocalService.getLayout(draftLayout.getPlid());

		draftLayout.setStatus(WorkflowConstants.STATUS_APPROVED);

		draftLayout = _layoutLocalService.updateLayout(draftLayout);

		UnicodeProperties typeSettingsUnicodeProperties =
			draftLayout.getTypeSettingsProperties();

		typeSettingsUnicodeProperties.put(
			LayoutTypeSettingsConstants.KEY_PUBLISHED, Boolean.TRUE.toString());

		_layoutLocalService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			typeSettingsUnicodeProperties.toString());

		return layoutPageTemplateEntry;
	}

	private long _getGroupId(
			HttpServletRequest httpServletRequest,
			ServiceContext serviceContext)
		throws Exception {

		long assetLibraryId = ParamUtil.getLong(
			httpServletRequest, "assetLibraryId");

		DepotEntry depotEntry = _depotEntryLocalService.fetchDepotEntry(
			assetLibraryId);

		if (depotEntry != null) {
			return depotEntry.getGroupId();
		}

		List<DepotEntry> depotEntries = _depotEntryLocalService.getDepotEntries(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		if (!depotEntries.isEmpty()) {
			depotEntry = depotEntries.get(0);
		}
		else {
			depotEntry = _depotEntryLocalService.addDepotEntry(
				HashMapBuilder.put(
					LocaleUtil.getDefault(), "Default"
				).build(),
				new HashMap<>(), serviceContext);
		}

		return depotEntry.getGroupId();
	}

	private long _getObjectEntryFolderId(
			long companyId, long groupId, ObjectDefinition objectDefinition)
		throws Exception {

		String externalReferenceCode =
			ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS;

		if (Objects.equals(
				objectDefinition.getObjectFolderExternalReferenceCode(),
				"L_CMS_FILE_TYPES")) {

			externalReferenceCode =
				ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_FILES;
		}

		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.
				getObjectEntryFolderByExternalReferenceCode(
					externalReferenceCode, groupId, companyId);

		return objectEntryFolder.getObjectEntryFolderId();
	}

	private String _getURLSeparator() {
		FriendlyURLResolver friendlyURLResolver =
			FriendlyURLResolverRegistryUtil.
				getFriendlyURLResolverByDefaultURLSeparator(
					FriendlyURLResolverConstants.URL_SEPARATOR_CUSTOM_ASSET);

		if (friendlyURLResolver != null) {
			String urlSeparator = friendlyURLResolver.getURLSeparator();

			return urlSeparator.substring(0, urlSeparator.length() - 1);
		}

		return FriendlyURLResolverConstants.URL_SEPARATOR_X_CUSTOM_ASSET;
	}

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private FormManager _formManager;

	@Reference
	private FragmentEntryLinkListenerRegistry
		_fragmentEntryLinkListenerRegistry;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private ObjectDefinitionService _objectDefinitionService;

	@Reference
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Reference
	private ObjectEntryService _objectEntryService;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}