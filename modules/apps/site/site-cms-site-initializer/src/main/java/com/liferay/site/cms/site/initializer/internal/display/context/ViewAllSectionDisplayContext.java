/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.service.ObjectDefinitionSettingLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.editor.configuration.EditorConfiguration;
import com.liferay.portal.kernel.editor.configuration.EditorConfigurationFactoryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.site.cms.site.initializer.internal.util.ActionUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Jürgen Kappler
 */
public class ViewAllSectionDisplayContext extends BaseSectionDisplayContext {

	public ViewAllSectionDisplayContext(
		DepotEntryLocalService depotEntryLocalService,
		DLConfiguration dlConfiguration, GroupLocalService groupLocalService,
		HttpServletRequest httpServletRequest, Language language,
		ObjectDefinitionService objectDefinitionService,
		ObjectDefinitionSettingLocalService objectDefinitionSettingLocalService,
		ModelResourcePermission<ObjectEntryFolder>
			objectEntryFolderModelResourcePermission,
		Portal portal) {

		super(
			depotEntryLocalService, dlConfiguration, groupLocalService,
			httpServletRequest, language, objectDefinitionService,
			objectDefinitionSettingLocalService,
			objectEntryFolderModelResourcePermission, portal);

		_httpServletRequest = httpServletRequest;
	}

	@Override
	public Map<String, Object> getAdditionalProps() {
		Map<String, Object> additionProps = super.getAdditionalProps();

		return HashMapBuilder.<String, Object>putAll(
			additionProps
		).put(
			"commentsProps",
			HashMapBuilder.<String, Object>put(
				"addCommentURL",
				StringBundler.concat(
					themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
					GroupConstants.CMS_FRIENDLY_URL,
					"/add_content_item_comment")
			).put(
				"deleteCommentURL",
				StringBundler.concat(
					themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
					GroupConstants.CMS_FRIENDLY_URL,
					"/delete_content_item_comment")
			).put(
				"editCommentURL",
				StringBundler.concat(
					themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
					GroupConstants.CMS_FRIENDLY_URL,
					"/edit_content_item_comment")
			).put(
				"editorConfig",
				() -> {
					EditorConfiguration contentItemCommentEditorConfiguration =
						EditorConfigurationFactoryUtil.getEditorConfiguration(
							StringPool.BLANK, "contentItemCommentEditor",
							StringPool.BLANK, Collections.emptyMap(),
							themeDisplay,
							RequestBackedPortletURLFactoryUtil.create(
								httpServletRequest));

					Map<String, Object> data =
						contentItemCommentEditorConfiguration.getData();

					return data.get("editorConfig");
				}
			).put(
				"getCommentsURL",
				StringBundler.concat(
					themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
					GroupConstants.CMS_FRIENDLY_URL, "/get_asset_comments")
			).build()
		).build();
	}

	@Override
	public String getAPIURL() {
		if (_httpServletRequest.getParameter("q") != null) {
			return HttpComponentsUtil.addParameters(
				super.getAPIURL(), "search",
				_httpServletRequest.getParameter("q"));
		}

		return super.getAPIURL();
	}

	@Override
	public List<DropdownItem> getBulkActionDropdownItems() {
		List<DropdownItem> fdsBulkActionDropdownItems =
			super.getBulkActionDropdownItems();

		fdsBulkActionDropdownItems.add(
			new FDSActionDropdownItem(
				null, "pencil", "edit-categories",
				LanguageUtil.get(httpServletRequest, "edit-categories"), "post",
				"edit-categories", null));
		fdsBulkActionDropdownItems.add(
			new FDSActionDropdownItem(
				null, "pencil", "edit-tags",
				LanguageUtil.get(httpServletRequest, "edit-tags"), "post",
				"edit-tags", null));
		fdsBulkActionDropdownItems.add(
			new FDSActionDropdownItem(
				StringPool.BLANK, "password-policies", "permissions",
				LanguageUtil.get(_httpServletRequest, "permissions"), null,
				null, null));

		return fdsBulkActionDropdownItems;
	}

	@Override
	public List<DropdownItem> getCreationMenuDropdownItems() {
		return ActionUtil.getAllSectionCreationMenuDropdownItems(
			httpServletRequest);
	}

	@Override
	public Map<String, Object> getEmptyState() {
		return HashMapBuilder.<String, Object>put(
			"description",
			LanguageUtil.get(
				httpServletRequest,
				"click-new-or-drag-and-drop-your-files-here")
		).put(
			"image", "/states/cms_empty_state.svg"
		).put(
			"title", LanguageUtil.get(httpServletRequest, "no-assets-yet")
		).build();
	}

	@Override
	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		List<FDSActionDropdownItem> fdsActionDropdownItems =
			super.getFDSActionDropdownItems();

		fdsActionDropdownItems.add(
			1,
			new FDSActionDropdownItem(
				"{embedded.file.link.href}", "download", "download",
				LanguageUtil.get(httpServletRequest, "download"), "get", null,
				"link"));
		fdsActionDropdownItems.add(
			2,
			new FDSActionDropdownItem(
				StringPool.BLANK, "info-circle-open", "show-details",
				LanguageUtil.get(httpServletRequest, "show-details"), null,
				null, "infoPanel"));

		return fdsActionDropdownItems;
	}

	@Override
	protected String getCMSSectionFilterString() {
		return appendStatus(
			"cmsKind eq 'object' and (cmsSection eq 'contents' or cmsSection " +
				"eq 'files')");
	}

	private final HttpServletRequest _httpServletRequest;

}