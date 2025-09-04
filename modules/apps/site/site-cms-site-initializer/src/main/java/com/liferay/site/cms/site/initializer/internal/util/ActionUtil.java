/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.util;

import com.liferay.depot.model.DepotEntry;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.listener.FragmentEntryLinkListener;
import com.liferay.fragment.listener.FragmentEntryLinkListenerRegistry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererRegistry;
import com.liferay.fragment.service.FragmentEntryLinkService;
import com.liferay.info.field.InfoField;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.search.InfoSearchClassMapperRegistry;
import com.liferay.layout.constants.LayoutTypeSettingsConstants;
import com.liferay.layout.manager.FormManager;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FormStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.RowStyledLayoutStructureItem;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.portlet.FriendlyURLResolverRegistryUtil;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;
import com.liferay.site.cms.site.initializer.internal.constants.CMSLayoutConstants;
import com.liferay.site.cms.site.initializer.internal.fragment.renderer.SpaceListComponentSectionFragmentRenderer;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Eudaldo Alonso
 */
public class ActionUtil {

	public static void generateEditContentLayoutStructure(
			FormManager formManager,
			FragmentEntryLinkListenerRegistry fragmentEntryLinkListenerRegistry,
			FragmentEntryLinkService fragmentEntryLinkService,
			FragmentRendererRegistry fragmentRendererRegistry, Layout layout,
			LayoutPageTemplateEntry layoutPageTemplateEntry,
			ServiceContext serviceContext)
		throws Exception {

		long segmentsExperienceId =
			SegmentsExperienceLocalServiceUtil.fetchDefaultSegmentsExperienceId(
				layout.getPlid());

		LayoutStructure layoutStructure = new LayoutStructure();

		layoutStructure.addRootLayoutStructureItem();

		ContainerStyledLayoutStructureItem
			parentContainerStyledLayoutStructureItem =
				(ContainerStyledLayoutStructureItem)
					layoutStructure.addContainerStyledLayoutStructureItem(
						layoutStructure.getMainItemId(), 0);

		parentContainerStyledLayoutStructureItem.updateItemConfig(
			JSONUtil.put(
				"styles",
				JSONUtil.put(
					"paddingBottom", "40px"
				).put(
					"paddingLeft", "12px"
				).put(
					"paddingRight", "12px"
				).put(
					"paddingTop", "40px"
				)));

		ContainerStyledLayoutStructureItem
			childContainerStyledLayoutStructureItem =
				(ContainerStyledLayoutStructureItem)
					layoutStructure.addContainerStyledLayoutStructureItem(
						parentContainerStyledLayoutStructureItem.getItemId(),
						0);

		childContainerStyledLayoutStructureItem.setWidthType("fixed");

		FormStyledLayoutStructureItem formStyledLayoutStructureItem =
			(FormStyledLayoutStructureItem)
				layoutStructure.addFormStyledLayoutStructureItem(
					childContainerStyledLayoutStructureItem.getItemId(), 0);

		formStyledLayoutStructureItem.setClassNameId(
			layoutPageTemplateEntry.getClassNameId());

		formStyledLayoutStructureItem.updateItemConfig(
			JSONUtil.put(
				"cssClasses", JSONUtil.put("lfr-main-form-container")));

		List<FragmentEntryLink> addedFragmentEntryLinks = new ArrayList<>();

		FragmentEntryLink spaceListFragmentEntryLink = _addFragmentEntryLink(
			StringPool.BLANK, fragmentEntryLinkService,
			fragmentRendererRegistry,
			SpaceListComponentSectionFragmentRenderer.class.getName(), layout,
			segmentsExperienceId, serviceContext);

		if (spaceListFragmentEntryLink != null) {
			layoutStructure.addFragmentStyledLayoutStructureItem(
				spaceListFragmentEntryLink.getFragmentEntryLinkId(),
				childContainerStyledLayoutStructureItem.getItemId(), 0);

			addedFragmentEntryLinks.add(spaceListFragmentEntryLink);
		}

		FragmentEntryLink localizationSelectFragmentEntryLink =
			_addFragmentEntryLink(
				JSONUtil.toString(
					JSONUtil.put(
						FragmentEntryProcessorConstants.
							KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
						JSONUtil.put("size", "small"))),
				fragmentEntryLinkService, fragmentRendererRegistry,
				"localization-select", layout, segmentsExperienceId,
				serviceContext);

		if (localizationSelectFragmentEntryLink != null) {
			LayoutStructureItem layoutStructureItem =
				layoutStructure.addFragmentStyledLayoutStructureItem(
					localizationSelectFragmentEntryLink.
						getFragmentEntryLinkId(),
					childContainerStyledLayoutStructureItem.getItemId(), 0);

			layoutStructureItem.updateItemConfig(
				JSONUtil.put("styles", JSONUtil.put("marginBottom", "5")));

			addedFragmentEntryLinks.add(localizationSelectFragmentEntryLink);
		}

		formManager.addFragmentEntryLinksLayoutStructureItems(
			addedFragmentEntryLinks, JSONFactoryUtil.createJSONObject(),
			formStyledLayoutStructureItem, false, layout, layoutStructure,
			LocaleUtil.getMostRelevantLocale(), false, segmentsExperienceId,
			serviceContext, null);

		LayoutPageTemplateStructureLocalServiceUtil.
			updateLayoutPageTemplateStructureData(
				serviceContext.getUserId(), layout.getGroupId(),
				layout.getPlid(), segmentsExperienceId,
				layoutStructure.toString());

		for (FragmentEntryLink addedFragmentEntryLink :
				addedFragmentEntryLinks) {

			for (FragmentEntryLinkListener fragmentEntryLinkListener :
					fragmentEntryLinkListenerRegistry.
						getFragmentEntryLinkListeners()) {

				fragmentEntryLinkListener.onAddFragmentEntryLink(
					addedFragmentEntryLink);
			}
		}
	}

	public static void generateTranslateContentLayoutStructure(
			FormManager formManager,
			FragmentEntryLinkListenerRegistry fragmentEntryLinkListenerRegistry,
			FragmentEntryLinkService fragmentEntryLinkService,
			FragmentRendererRegistry fragmentRendererRegistry,
			InfoItemServiceRegistry infoItemServiceRegistry,
			InfoSearchClassMapperRegistry infoSearchClassMapperRegistry,
			Layout layout, LayoutPageTemplateEntry layoutPageTemplateEntry,
			ServiceContext serviceContext)
		throws Exception {

		long segmentsExperienceId =
			SegmentsExperienceLocalServiceUtil.fetchDefaultSegmentsExperienceId(
				layout.getPlid());

		LayoutStructure layoutStructure = new LayoutStructure();

		layoutStructure.addRootLayoutStructureItem();

		ContainerStyledLayoutStructureItem
			parentContainerStyledLayoutStructureItem =
				(ContainerStyledLayoutStructureItem)
					layoutStructure.addContainerStyledLayoutStructureItem(
						layoutStructure.getMainItemId(), 0);

		parentContainerStyledLayoutStructureItem.updateItemConfig(
			JSONUtil.put(
				"styles",
				JSONUtil.put(
					"paddingBottom", "40px"
				).put(
					"paddingLeft", "12px"
				).put(
					"paddingRight", "12px"
				).put(
					"paddingTop", "40px"
				)));

		RowStyledLayoutStructureItem rowStyledLayoutStructureItem =
			(RowStyledLayoutStructureItem)
				layoutStructure.addRowStyledLayoutStructureItem(
					parentContainerStyledLayoutStructureItem.getItemId(), 0, 2);

		ColumnLayoutStructureItem firstColumnLayoutStructureItem =
			(ColumnLayoutStructureItem)
				layoutStructure.addColumnLayoutStructureItem(
					rowStyledLayoutStructureItem.getItemId(), 0);

		firstColumnLayoutStructureItem.setSize(6);

		FormStyledLayoutStructureItem formStyledLayoutStructureItem =
			(FormStyledLayoutStructureItem)
				layoutStructure.addFormStyledLayoutStructureItem(
					firstColumnLayoutStructureItem.getItemId(), 0);

		formStyledLayoutStructureItem.setClassNameId(
			layoutPageTemplateEntry.getClassNameId());

		Set<String> localizableInfoFieldIds = _getLocalizableInfoFieldIds(
			layoutPageTemplateEntry.getClassNameId(), layout.getGroupId(),
			infoItemServiceRegistry, infoSearchClassMapperRegistry);

		List<FragmentEntryLink> addedFragmentEntryLinks = new ArrayList<>();

		formManager.addFragmentEntryLinksLayoutStructureItems(
			addedFragmentEntryLinks, JSONFactoryUtil.createJSONObject(),
			formStyledLayoutStructureItem, false, layout, layoutStructure,
			LocaleUtil.getMostRelevantLocale(), true, segmentsExperienceId,
			serviceContext, localizableInfoFieldIds.toArray(new String[0]));

		FragmentEntryLink localizationSelectFragmentEntryLink =
			_addFragmentEntryLink(
				JSONUtil.toString(
					JSONUtil.put(
						FragmentEntryProcessorConstants.
							KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
						JSONUtil.put("size", "small"))),
				fragmentEntryLinkService, fragmentRendererRegistry,
				"localization-select", layout, segmentsExperienceId,
				serviceContext);

		if (localizationSelectFragmentEntryLink != null) {
			LayoutStructureItem layoutStructureItem =
				layoutStructure.addFragmentStyledLayoutStructureItem(
					localizationSelectFragmentEntryLink.
						getFragmentEntryLinkId(),
					formStyledLayoutStructureItem.getItemId(), 0);

			layoutStructureItem.updateItemConfig(
				JSONUtil.put("styles", JSONUtil.put("marginBottom", "5")));

			addedFragmentEntryLinks.add(localizationSelectFragmentEntryLink);
		}

		ColumnLayoutStructureItem secondColumnLayoutStructureItem =
			(ColumnLayoutStructureItem)
				layoutStructure.addColumnLayoutStructureItem(
					rowStyledLayoutStructureItem.getItemId(), 1);

		secondColumnLayoutStructureItem.setSize(6);

		formStyledLayoutStructureItem =
			(FormStyledLayoutStructureItem)
				layoutStructure.addFormStyledLayoutStructureItem(
					secondColumnLayoutStructureItem.getItemId(), 0);

		formStyledLayoutStructureItem.setClassNameId(
			layoutPageTemplateEntry.getClassNameId());
		formStyledLayoutStructureItem.updateItemConfig(
			JSONUtil.put(
				"cssClasses", JSONUtil.put("lfr-main-form-container")));

		formManager.addFragmentEntryLinksLayoutStructureItems(
			addedFragmentEntryLinks, JSONFactoryUtil.createJSONObject(),
			formStyledLayoutStructureItem, false, layout, layoutStructure,
			LocaleUtil.getMostRelevantLocale(), false, segmentsExperienceId,
			serviceContext, localizableInfoFieldIds.toArray(new String[0]));

		localizationSelectFragmentEntryLink = _addFragmentEntryLink(
			JSONUtil.toString(
				JSONUtil.put(
					FragmentEntryProcessorConstants.
						KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
					JSONUtil.put(
						"allowLocalizationManagement", "true"
					).put(
						"size", "small"
					))),
			fragmentEntryLinkService, fragmentRendererRegistry,
			"localization-select", layout, segmentsExperienceId,
			serviceContext);

		if (localizationSelectFragmentEntryLink != null) {
			LayoutStructureItem layoutStructureItem =
				layoutStructure.addFragmentStyledLayoutStructureItem(
					localizationSelectFragmentEntryLink.
						getFragmentEntryLinkId(),
					formStyledLayoutStructureItem.getItemId(), 0);

			layoutStructureItem.updateItemConfig(
				JSONUtil.put("styles", JSONUtil.put("marginBottom", "5")));

			addedFragmentEntryLinks.add(localizationSelectFragmentEntryLink);
		}

		LayoutPageTemplateStructureLocalServiceUtil.
			updateLayoutPageTemplateStructureData(
				serviceContext.getUserId(), layout.getGroupId(),
				layout.getPlid(), segmentsExperienceId,
				layoutStructure.toString());

		for (FragmentEntryLink addedFragmentEntryLink :
				addedFragmentEntryLinks) {

			for (FragmentEntryLinkListener fragmentEntryLinkListener :
					fragmentEntryLinkListenerRegistry.
						getFragmentEntryLinkListeners()) {

				fragmentEntryLinkListener.onAddFragmentEntryLink(
					addedFragmentEntryLink);
			}
		}
	}

	public static String getBaseAddSpaceMembersURL(ThemeDisplay themeDisplay) {
		return StringBundler.concat(
			themeDisplay.getPathFriendlyURLPublic(),
			GroupConstants.CMS_FRIENDLY_URL, "/add-space-members");
	}

	public static String getBaseSpaceSettingsURL(ThemeDisplay themeDisplay) {
		return StringBundler.concat(
			themeDisplay.getPathFriendlyURLPublic(),
			GroupConstants.CMS_FRIENDLY_URL, "/e/space-settings/",
			PortalUtil.getClassNameId(DepotEntry.class), StringPool.SLASH);
	}

	public static String getBaseSpaceURL(ThemeDisplay themeDisplay) {
		return StringBundler.concat(
			themeDisplay.getPathFriendlyURLPublic(),
			GroupConstants.CMS_FRIENDLY_URL, "/e/space/",
			PortalUtil.getClassNameId(DepotEntry.class), StringPool.SLASH);
	}

	public static String getBaseViewFolderRecycleBinURL(
		ThemeDisplay themeDisplay) {

		return StringBundler.concat(
			themeDisplay.getPathFriendlyURLPublic(),
			GroupConstants.CMS_FRIENDLY_URL, "/e/recycle-bin/",
			PortalUtil.getClassNameId(ObjectEntryFolder.class),
			StringPool.SLASH);
	}

	public static String getBaseViewFolderURL(ThemeDisplay themeDisplay) {
		return StringBundler.concat(
			themeDisplay.getPathFriendlyURLPublic(),
			GroupConstants.CMS_FRIENDLY_URL, "/e/view-folder/",
			PortalUtil.getClassNameId(ObjectEntryFolder.class),
			StringPool.SLASH);
	}

	public static String getDisplayPageEditURL(
		FormManager formManager,
		FragmentEntryLinkListenerRegistry fragmentEntryLinkListenerRegistry,
		FragmentEntryLinkService fragmentEntryLinkService,
		FragmentRendererRegistry fragmentRendererRegistry,
		HttpServletRequest httpServletRequest,
		ObjectDefinition objectDefinition) {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			Layout layout = _getEditContentLayout(
				PortalUtil.getClassNameId(objectDefinition.getClassName()),
				formManager, fragmentEntryLinkListenerRegistry,
				fragmentEntryLinkService, fragmentRendererRegistry,
				GroupLocalServiceUtil.getGroup(
					themeDisplay.getCompanyId(), GroupConstants.CMS),
				objectDefinition,
				ServiceContextFactory.getInstance(httpServletRequest));

			String editURL = HttpComponentsUtil.addParameters(
				PortalUtil.getLayoutFullURL(
					layout.fetchDraftLayout(), themeDisplay),
				"p_l_mode", Constants.EDIT);

			String backURL = ParamUtil.getString(httpServletRequest, "backURL");

			if (Validator.isNotNull(backURL)) {
				editURL = HttpComponentsUtil.addParameters(
					editURL, "backURL", backURL, "p_l_back_url", backURL);
			}

			return editURL;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return StringPool.BLANK;
	}

	public static String getEditURL(
		FormManager formManager,
		FragmentEntryLinkListenerRegistry fragmentEntryLinkListenerRegistry,
		FragmentEntryLinkService fragmentEntryLinkService,
		FragmentRendererRegistry fragmentRendererRegistry,
		HttpServletRequest httpServletRequest, String id,
		ObjectDefinition objectDefinition) {

		try {
			long classNameId = PortalUtil.getClassNameId(
				objectDefinition.getClassName());

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			Group group = GroupLocalServiceUtil.getGroup(
				themeDisplay.getCompanyId(), GroupConstants.CMS);

			Layout layout = _getEditContentLayout(
				classNameId, formManager, fragmentEntryLinkListenerRegistry,
				fragmentEntryLinkService, fragmentRendererRegistry, group,
				objectDefinition,
				ServiceContextFactory.getInstance(httpServletRequest));

			String editURL = PortalUtil.addPreservedParameters(
				themeDisplay,
				StringBundler.concat(
					PortalUtil.getGroupFriendlyURL(
						group.getPublicLayoutSet(), themeDisplay, false, false),
					_getURLSeparator(),
					layout.getFriendlyURL(themeDisplay.getLocale()),
					StringPool.SLASH, classNameId, StringPool.SLASH, id));

			String backURL = ParamUtil.getString(
				httpServletRequest, "redirect");

			if (Validator.isNotNull(backURL)) {
				editURL = HttpComponentsUtil.addParameter(
					editURL, "redirect", backURL);
			}

			return editURL;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return StringPool.BLANK;
	}

	public static String getRecycleBinURL(ThemeDisplay themeDisplay) {
		return StringBundler.concat(
			themeDisplay.getPathFriendlyURLPublic(),
			GroupConstants.CMS_FRIENDLY_URL, "/recycle-bin");
	}

	public static String getSpaceSettingsURL(
		long classPK, String redirectURL, ThemeDisplay themeDisplay) {

		String url = getBaseSpaceSettingsURL(themeDisplay) + classPK;

		if (Validator.isNotNull(redirectURL)) {
			return url + "?redirect=" + redirectURL;
		}

		return url;
	}

	public static String getSpaceURL(long classPK, ThemeDisplay themeDisplay) {
		return getBaseSpaceURL(themeDisplay) + classPK;
	}

	public static String getTranslateURL(
		FormManager formManager,
		FragmentEntryLinkListenerRegistry fragmentEntryLinkListenerRegistry,
		FragmentEntryLinkService fragmentEntryLinkService,
		FragmentRendererRegistry fragmentRendererRegistry,
		HttpServletRequest httpServletRequest, String id,
		InfoItemServiceRegistry infoItemServiceRegistry,
		InfoSearchClassMapperRegistry infoSearchClassMapperRegistry,
		ObjectDefinition objectDefinition) {

		try {
			long classNameId = PortalUtil.getClassNameId(
				objectDefinition.getClassName());

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			Group group = GroupLocalServiceUtil.getGroup(
				themeDisplay.getCompanyId(), GroupConstants.CMS);

			Layout layout = _getTranslateContentLayout(
				classNameId, formManager, fragmentEntryLinkListenerRegistry,
				fragmentEntryLinkService, fragmentRendererRegistry, group,
				infoItemServiceRegistry, infoSearchClassMapperRegistry,
				objectDefinition,
				ServiceContextFactory.getInstance(httpServletRequest));

			String translateURL = PortalUtil.addPreservedParameters(
				themeDisplay,
				StringBundler.concat(
					PortalUtil.getGroupFriendlyURL(
						group.getPublicLayoutSet(), themeDisplay, false, false),
					_getURLSeparator(),
					layout.getFriendlyURL(themeDisplay.getLocale()),
					StringPool.SLASH, classNameId, StringPool.SLASH, id));

			String backURL = ParamUtil.getString(
				httpServletRequest, "redirect");

			if (Validator.isNotNull(backURL)) {
				translateURL = HttpComponentsUtil.addParameter(
					translateURL, "redirect", backURL);
			}

			return translateURL;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return StringPool.BLANK;
	}

	public static String getViewFolderRecycleBinURL(
		long objectEntryFolderId, ThemeDisplay themeDisplay) {

		return getBaseViewFolderRecycleBinURL(themeDisplay) +
			objectEntryFolderId;
	}

	public static String getViewFolderURL(
		long objectEntryFolderId, ThemeDisplay themeDisplay) {

		return getBaseViewFolderURL(themeDisplay) + objectEntryFolderId;
	}

	private static LayoutPageTemplateEntry
			_addEditContentDefaultLayoutPageTemplateEntry(
				long classNameId, FormManager formManager,
				FragmentEntryLinkListenerRegistry
					fragmentEntryLinkListenerRegistry,
				FragmentEntryLinkService fragmentEntryLinkService,
				FragmentRendererRegistry fragmentRendererRegistry, long groupId,
				String name, ServiceContext serviceContext)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.addLayoutPageTemplateEntry(
				null, serviceContext.getUserId(), groupId, 0, null, classNameId,
				0, name, LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE, 0,
				true, 0, 0, 0, WorkflowConstants.STATUS_APPROVED,
				serviceContext);

		Layout layout = LayoutLocalServiceUtil.getLayout(
			layoutPageTemplateEntry.getPlid());

		Layout draftLayout = layout.fetchDraftLayout();

		generateEditContentLayoutStructure(
			formManager, fragmentEntryLinkListenerRegistry,
			fragmentEntryLinkService, fragmentRendererRegistry, draftLayout,
			layoutPageTemplateEntry, serviceContext);

		LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntry(groupId, "content-editor-master");

		if (masterLayoutPageTemplateEntry != null) {
			draftLayout.setMasterLayoutPlid(
				masterLayoutPageTemplateEntry.getPlid());
		}

		LayoutLocalServiceUtil.copyLayoutContent(draftLayout, layout);

		draftLayout = LayoutLocalServiceUtil.getLayout(draftLayout.getPlid());

		if (masterLayoutPageTemplateEntry != null) {
			draftLayout.setMasterLayoutPlid(
				masterLayoutPageTemplateEntry.getPlid());
		}

		draftLayout.setStatus(WorkflowConstants.STATUS_APPROVED);

		draftLayout = LayoutLocalServiceUtil.updateLayout(draftLayout);

		UnicodeProperties typeSettingsUnicodeProperties =
			draftLayout.getTypeSettingsProperties();

		typeSettingsUnicodeProperties.put(
			LayoutTypeSettingsConstants.KEY_AUTOGENERATED,
			Boolean.TRUE.toString());
		typeSettingsUnicodeProperties.put(
			LayoutTypeSettingsConstants.KEY_PUBLISHED, Boolean.TRUE.toString());

		LayoutLocalServiceUtil.updateLayout(
			draftLayout.getGroupId(), draftLayout.isPrivateLayout(),
			draftLayout.getLayoutId(),
			typeSettingsUnicodeProperties.toString());

		return layoutPageTemplateEntry;
	}

	private static FragmentEntryLink _addFragmentEntryLink(
			String editableValues,
			FragmentEntryLinkService fragmentEntryLinkService,
			FragmentRendererRegistry fragmentRendererRegistry,
			String fragmentEntryKey, Layout layout, long segmentsExperienceId,
			ServiceContext serviceContext)
		throws Exception {

		FragmentRenderer fragmentRenderer =
			fragmentRendererRegistry.getFragmentRenderer(fragmentEntryKey);

		if (fragmentRenderer == null) {
			return null;
		}

		DefaultFragmentRendererContext defaultFragmentRendererContext =
			new DefaultFragmentRendererContext(null);

		return fragmentEntryLinkService.addFragmentEntryLink(
			null, layout.getGroupId(), 0, 0, segmentsExperienceId,
			layout.getPlid(), StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK,
			JSONFactoryUtil.toString(
				fragmentRenderer.getConfigurationJSONObject(
					defaultFragmentRendererContext)),
			editableValues, StringPool.BLANK, 0, fragmentEntryKey,
			fragmentRenderer.getType(), serviceContext);
	}

	private static LayoutPageTemplateEntry
			_addTranslateContentDefaultLayoutPageTemplateEntry(
				long classNameId, FormManager formManager,
				FragmentEntryLinkListenerRegistry
					fragmentEntryLinkListenerRegistry,
				FragmentEntryLinkService fragmentEntryLinkService,
				FragmentRendererRegistry fragmentRendererRegistry, long groupId,
				InfoItemServiceRegistry infoItemServiceRegistry,
				InfoSearchClassMapperRegistry infoSearchClassMapperRegistry,
				String name, ServiceContext serviceContext)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.addLayoutPageTemplateEntry(
				null, serviceContext.getUserId(), groupId, 0,
				CMSLayoutConstants.
					TRANSLATION_LAYOUT_PAGE_TEMPLATE_ENTRY_KEY_PREFIX +
						classNameId,
				classNameId, 0, name,
				LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE, 0, true, 0,
				0, 0, WorkflowConstants.STATUS_APPROVED, serviceContext);

		Layout layout = LayoutLocalServiceUtil.getLayout(
			layoutPageTemplateEntry.getPlid());

		Layout draftLayout = layout.fetchDraftLayout();

		generateTranslateContentLayoutStructure(
			formManager, fragmentEntryLinkListenerRegistry,
			fragmentEntryLinkService, fragmentRendererRegistry,
			infoItemServiceRegistry, infoSearchClassMapperRegistry, draftLayout,
			layoutPageTemplateEntry, serviceContext);

		LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntry(groupId, "cms-translation-master");

		if (masterLayoutPageTemplateEntry != null) {
			draftLayout.setMasterLayoutPlid(
				masterLayoutPageTemplateEntry.getPlid());
		}

		LayoutLocalServiceUtil.copyLayoutContent(draftLayout, layout);

		draftLayout = LayoutLocalServiceUtil.getLayout(draftLayout.getPlid());

		if (masterLayoutPageTemplateEntry != null) {
			draftLayout.setMasterLayoutPlid(
				masterLayoutPageTemplateEntry.getPlid());
		}

		draftLayout.setStatus(WorkflowConstants.STATUS_APPROVED);

		LayoutLocalServiceUtil.updateLayout(draftLayout);

		return layoutPageTemplateEntry;
	}

	private static Layout _getEditContentLayout(
			long classNameId, FormManager formManager,
			FragmentEntryLinkListenerRegistry fragmentEntryLinkListenerRegistry,
			FragmentEntryLinkService fragmentEntryLinkService,
			FragmentRendererRegistry fragmentRendererRegistry, Group group,
			ObjectDefinition objectDefinition, ServiceContext serviceContext)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchDefaultLayoutPageTemplateEntry(
					group.getGroupId(), classNameId, 0);

		if (layoutPageTemplateEntry == null) {
			layoutPageTemplateEntry =
				_addEditContentDefaultLayoutPageTemplateEntry(
					classNameId, formManager, fragmentEntryLinkListenerRegistry,
					fragmentEntryLinkService, fragmentRendererRegistry,
					group.getGroupId(), objectDefinition.getName(),
					serviceContext);
		}

		return LayoutLocalServiceUtil.fetchLayout(
			layoutPageTemplateEntry.getPlid());
	}

	private static Set<String> _getLocalizableInfoFieldIds(
			long classNameId, long groupId,
			InfoItemServiceRegistry infoItemServiceRegistry,
			InfoSearchClassMapperRegistry infoSearchClassMapperRegistry)
		throws Exception {

		ClassName className = ClassNameLocalServiceUtil.fetchClassName(
			classNameId);

		String itemClassName = infoSearchClassMapperRegistry.getClassName(
			className.getClassName());

		InfoItemFormProvider<?> infoItemFormProvider =
			infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFormProvider.class, itemClassName);

		if (infoItemFormProvider == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get info item form provider for class " +
						itemClassName);
			}

			return Collections.emptySet();
		}

		Set<String> localizableInfoFieldIds = new HashSet<>();

		InfoForm infoForm = infoItemFormProvider.getInfoForm(
			StringPool.BLANK, groupId);

		for (InfoField<?> infoField : infoForm.getAllInfoFields()) {
			if (infoField.isLocalizable()) {
				localizableInfoFieldIds.add(infoField.getUniqueId());
			}
		}

		return localizableInfoFieldIds;
	}

	private static Layout _getTranslateContentLayout(
			long classNameId, FormManager formManager,
			FragmentEntryLinkListenerRegistry fragmentEntryLinkListenerRegistry,
			FragmentEntryLinkService fragmentEntryLinkService,
			FragmentRendererRegistry fragmentRendererRegistry, Group group,
			InfoItemServiceRegistry infoItemServiceRegistry,
			InfoSearchClassMapperRegistry infoSearchClassMapperRegistry,
			ObjectDefinition objectDefinition, ServiceContext serviceContext)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntry(
					group.getGroupId(),
					CMSLayoutConstants.
						TRANSLATION_LAYOUT_PAGE_TEMPLATE_ENTRY_KEY_PREFIX +
							classNameId);

		if (layoutPageTemplateEntry == null) {
			layoutPageTemplateEntry =
				_addTranslateContentDefaultLayoutPageTemplateEntry(
					classNameId, formManager, fragmentEntryLinkListenerRegistry,
					fragmentEntryLinkService, fragmentRendererRegistry,
					group.getGroupId(), infoItemServiceRegistry,
					infoSearchClassMapperRegistry,
					CMSLayoutConstants.
						TRANSLATION_LAYOUT_PAGE_TEMPLATE_ENTRY_KEY_PREFIX +
							objectDefinition.getName(),
					serviceContext);
		}

		return LayoutLocalServiceUtil.fetchLayout(
			layoutPageTemplateEntry.getPlid());
	}

	private static String _getURLSeparator() {
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

	private static final Log _log = LogFactoryUtil.getLog(ActionUtil.class);

}