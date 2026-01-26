/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.asset.categories.admin.web.constants.AssetCategoriesAdminPortletKeys;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.tags.constants.AssetTagsAdminPortletKeys;
import com.liferay.exportimport.constants.ExportImportPortletKeys;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.taglib.security.PermissionsURLTag;

import jakarta.portlet.PortletRequest;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * @author Noor Najjar
 */
public class ViewVocabulariesDisplayContext {

	public ViewVocabulariesDisplayContext(
		GroupService groupService, HttpServletRequest httpServletRequest,
		ThemeDisplay themeDisplay) {

		_groupService = groupService;
		_httpServletRequest = httpServletRequest;
		_themeDisplay = themeDisplay;
	}

	public String getAPIURL() {
		return "/o/headless-admin-taxonomy/v1.0/sites/" +
			_themeDisplay.getScopeGroupId() + "/taxonomy-vocabularies";
	}

	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					PortalUtil.getLayoutFullURL(
						LayoutLocalServiceUtil.getLayoutByFriendlyURL(
							_themeDisplay.getScopeGroupId(), false,
							"/categorization/new-vocabulary"),
						_themeDisplay));
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "new-vocabulary"));
			}
		).build();
	}

	public Map<String, Object> getEmptyState() {
		return HashMapBuilder.<String, Object>put(
			"description",
			LanguageUtil.get(
				_httpServletRequest,
				"vocabularies-are-needed-to-create-categories")
		).put(
			"image", "/states/cms_empty_state_categorization.svg"
		).put(
			"title",
			LanguageUtil.get(_httpServletRequest, "no-vocabularies-yet")
		).build();
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems()
		throws PortalException {

		String fullLayoutURL = PortalUtil.getLayoutFullURL(
			LayoutLocalServiceUtil.getLayoutByFriendlyURL(
				_themeDisplay.getScopeGroupId(), false,
				"/categorization/edit-vocabulary"),
			_themeDisplay);

		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				fullLayoutURL + "?vocabularyId={id}", "pencil", "edit",
				LanguageUtil.get(_httpServletRequest, "edit"), "get", "update",
				null),
			new FDSActionDropdownItem(
				HttpComponentsUtil.addParameter(
					PortalUtil.getLayoutFullURL(
						LayoutLocalServiceUtil.getLayoutByFriendlyURL(
							_themeDisplay.getScopeGroupId(), false,
							"/categorization/view-categories"),
						_themeDisplay),
					"vocabularyId", "{id}"),
				null, "view-categories",
				LanguageUtil.get(_httpServletRequest, "view-categories"), "get",
				null, null),
			new FDSActionDropdownItem(
				_getEditPermissionsURL(), "password-policies", "permissions",
				LanguageUtil.get(_httpServletRequest, "permissions"), "get",
				null, "modal-permissions"),
			new FDSActionDropdownItem(
				null, "trash", "delete",
				LanguageUtil.get(_httpServletRequest, "delete"), null, "delete",
				null));
	}

	public Map<String, Object> getReactData() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"actionItems",
			JSONUtil.put(
				JSONUtil.put(
					"href",
					_getControlPanelPortletURL(
						AssetCategoriesAdminPortletKeys.
							ASSET_CATEGORIES_ADMIN)
				).put(
					"label",
					LanguageUtil.get(
						_httpServletRequest,
						"export-import-vocabularies")
				)
			).put(JSONUtil.put(
				"href",
				_getControlPanelPortletURL(
					AssetTagsAdminPortletKeys.ASSET_TAGS_ADMIN)
			).put(
				"label",
				LanguageUtil.get(
					_httpServletRequest, "export-import-tags")
			))
		).put(
			"activeTab", "vocabularies"
		).put(
			"tagsURL",
			PortalUtil.getLayoutFullURL(
				LayoutLocalServiceUtil.getLayoutByFriendlyURL(
					_themeDisplay.getScopeGroupId(), false,
					"/categorization/view-tags"),
				_themeDisplay)
		).put(
			"vocabulariesURL",
			PortalUtil.getLayoutFullURL(
				LayoutLocalServiceUtil.getLayoutByFriendlyURL(
					_themeDisplay.getScopeGroupId(), false,
					"/categorization/view-vocabularies"),
				_themeDisplay)
		).build();
	}

	private String _getControlPanelPortletURL(String portletId)
		throws Exception {

		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_httpServletRequest,
				_groupService.getGroup(_themeDisplay.getScopeGroupId()),
				ExportImportPortletKeys.EXPORT_IMPORT, 0, 0,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/export_import/export_import"
		).setRedirect(
			PortalUtil.getCurrentURL(_httpServletRequest)
		).setPortletResource(
			portletId
		).setParameter(
			"returnToFullPageURL", PortalUtil.getCurrentURL(_httpServletRequest)
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	private String _getEditPermissionsURL() {
		String url = StringPool.BLANK;

		try {
			url = PermissionsURLTag.doTag(
				_themeDisplay.getURLCurrent(), AssetVocabulary.class.getName(),
				"{name}", GroupConstants.DEFAULT_LIVE_GROUP_ID, "{id}",
				LiferayWindowState.POP_UP.toString(), null,
				_httpServletRequest);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return url;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewVocabulariesDisplayContext.class);

	private final GroupService _groupService;
	private final HttpServletRequest _httpServletRequest;
	private final ThemeDisplay _themeDisplay;

}