/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR
 * LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.security.PermissionsURLTag;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cheryl Tang
 */
public class ViewCategoriesDisplayContext {

	public ViewCategoriesDisplayContext(
		AssetVocabularyLocalService assetVocabularyLocalService,
		HttpServletRequest httpServletRequest, JSONFactory jsonFactory,
		LayoutLocalService layoutLocalService, Language language,
		Portal portal) {

		_assetVocabularyLocalService = assetVocabularyLocalService;
		_httpServletRequest = httpServletRequest;
		_jsonFactory = jsonFactory;
		_layoutLocalService = layoutLocalService;
		_language = language;
		_portal = portal;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, Object> getBreadcrumbReactData() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"breadcrumbItems",
			JSONUtil.putAll(
				JSONUtil.put(
					"active", false
				).put(
					"href",
					_portal.getLayoutFullURL(
						_layoutLocalService.getLayoutByFriendlyURL(
							_themeDisplay.getScopeGroupId(), false,
							"/categorization/view_vocabularies"),
						_themeDisplay)
				).put(
					"label",
					_language.get(_httpServletRequest, "categorization")
				),
				JSONUtil.put(
					"active", true
				).put(
					"label",
					() -> {
						AssetVocabulary assetVocabulary =
							_assetVocabularyLocalService.getVocabulary(
								getVocabularyId());

						return assetVocabulary.getName();
					}
				))
		).build();
	}

	public String getCategoriesByVocabularyIdAPIURL() {
		return StringBundler.concat(
			"/o/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/",
			getVocabularyId(), "/taxonomy-categories");
	}

	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			item -> {
				item.setHref(
					HttpComponentsUtil.addParameter(
						_portal.getLayoutFullURL(
							_layoutLocalService.getLayoutByFriendlyURL(
								_themeDisplay.getScopeGroupId(), false,
								"/categorization/new_category"),
							_themeDisplay),
						"vocabularyId", getVocabularyId()));

				item.setLabel(
					_language.get(_httpServletRequest, "new-category"));
			}
		).build();
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems()
		throws PortalException {

		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				HttpComponentsUtil.addParameters(
					_portal.getLayoutFullURL(
						_layoutLocalService.getLayoutByFriendlyURL(
							_themeDisplay.getScopeGroupId(), false,
							"/categorization/edit_category"),
						_themeDisplay),
					"categoryId", "{id}", "vocabularyId",
					"{taxonomyVocabularyId}"),
				"pencil", "edit", _language.get(_httpServletRequest, "edit"),
				"get", "update", null),
			new FDSActionDropdownItem(
				"TODO: Add Subcategory URL", null, "add-subcategory",
				_language.get(_httpServletRequest, "add-subcategory"), "get",
				"update", null),
			new FDSActionDropdownItem(
				"TODO: Move URL", null, "move",
				_language.get(_httpServletRequest, "move"), null, "update",
				null),
			new FDSActionDropdownItem(
				_getEditPermissionsURL(), "password-policies", "permissions",
				_language.get(_httpServletRequest, "permissions"), "get", null,
				"modal-permissions"),
			new FDSActionDropdownItem(
				null, "times-circle", "delete",
				_language.get(_httpServletRequest, "delete"), null, "delete",
				null));
	}

	public long getVocabularyId() {
		if (_vocabularyId != null) {
			return _vocabularyId;
		}

		_vocabularyId = ParamUtil.getLong(_httpServletRequest, "vocabularyId");

		return _vocabularyId;
	}

	private String _getEditPermissionsURL() {
		String url = StringPool.BLANK;

		try {
			url = PermissionsURLTag.doTag(
				_themeDisplay.getURLCurrent(), AssetCategory.class.getName(),
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
		ViewCategoriesDisplayContext.class);

	private final AssetVocabularyLocalService _assetVocabularyLocalService;
	private final HttpServletRequest _httpServletRequest;
	private final JSONFactory _jsonFactory;
	private final Language _language;
	private final LayoutLocalService _layoutLocalService;
	private final Portal _portal;
	private final ThemeDisplay _themeDisplay;
	private Long _vocabularyId;

}