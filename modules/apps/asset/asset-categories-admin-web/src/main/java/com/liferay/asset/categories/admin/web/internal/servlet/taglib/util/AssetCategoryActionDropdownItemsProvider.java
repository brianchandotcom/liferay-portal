/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.asset.categories.admin.web.internal.servlet.taglib.util;

import com.liferay.asset.categories.admin.web.constants.AssetCategoriesAdminPortletKeys;
import com.liferay.asset.categories.admin.web.internal.constants.AssetCategoriesAdminWebKeys;
import com.liferay.asset.categories.admin.web.internal.display.context.AssetCategoriesDisplayContext;
import com.liferay.asset.categories.configuration.AssetCategoriesCompanyConfiguration;
import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.exportimport.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;
import com.liferay.taglib.security.PermissionsURLTag;

import java.util.List;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Víctor Galán
 */
public class AssetCategoryActionDropdownItemsProvider {

	public AssetCategoryActionDropdownItemsProvider(
			AssetCategoriesDisplayContext assetCategoriesDisplayContext,
			HttpServletRequest httpServletRequest,
			RenderResponse renderResponse)
		throws PortalException {

		_httpServletRequest = httpServletRequest;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_assetCategoriesLimitExceeded = _isAssetCategoriesLimitExceeded(
			assetCategoriesDisplayContext);
		_assetDisplayPageFriendlyURLProvider =
			(AssetDisplayPageFriendlyURLProvider)
				_httpServletRequest.getAttribute(
					AssetCategoriesAdminWebKeys.
						ASSET_DISPLAY_PAGE_FRIENDLY_URL_PROVIDER);
	}

	public List<DropdownItem> getActionDropdownItems(AssetCategory category) {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() -> _hasPermission(category, ActionKeys.UPDATE),
						dropdownItem -> {
							dropdownItem.setHref(
								PortletURLBuilder.createRenderURL(
									_renderResponse
								).setMVCPath(
									"/edit_category.jsp"
								).setParameter(
									"categoryId", category.getCategoryId()
								).setParameter(
									"vocabularyId", category.getVocabularyId()
								).buildString());

							dropdownItem.setIcon("pencil");
							dropdownItem.setLabel(
								LanguageUtil.get(_httpServletRequest, "edit"));
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() ->
							!_assetCategoriesLimitExceeded &&
							_hasPermission(category, ActionKeys.ADD_CATEGORY),
						dropdownItem -> {
							dropdownItem.setHref(
								PortletURLBuilder.createRenderURL(
									_renderResponse
								).setMVCPath(
									"/edit_category.jsp"
								).setParameter(
									"parentCategoryId", category.getCategoryId()
								).setParameter(
									"vocabularyId", category.getVocabularyId()
								).buildString());
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "add-subcategory"));
						}
					).add(
						() -> _getDisplayPageURL(category) != null,
						dropdownItem -> {
							dropdownItem.setHref(_getDisplayPageURL(category));
							dropdownItem.setIcon("view");
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "view-display-page"));
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() -> _hasPermission(category, ActionKeys.UPDATE),
						dropdownItem -> {
							dropdownItem.putData("action", "moveCategory");
							dropdownItem.putData(
								"categoryId",
								String.valueOf(category.getCategoryId()));
							dropdownItem.putData(
								"categoryTitle",
								String.valueOf(
									category.getTitle(
										_themeDisplay.getLocale())));
							dropdownItem.putData(
								"moveCategoryURL",
								_getSelectCategoryURL(
									category.getVocabularyId()));
							dropdownItem.setIcon("move-folder");
							dropdownItem.setLabel(
								LanguageUtil.get(_httpServletRequest, "move"));
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() -> _hasPermission(category, ActionKeys.PERMISSIONS),
						dropdownItem -> {
							dropdownItem.putData(
								"action", "permissionsCategory");
							dropdownItem.putData(
								"permissionsCategoryURL",
								PermissionsURLTag.doTag(
									StringPool.BLANK,
									AssetCategory.class.getName(),
									category.getTitle(
										_themeDisplay.getLocale()),
									null,
									String.valueOf(category.getCategoryId()),
									LiferayWindowState.POP_UP.toString(), null,
									_httpServletRequest));
							dropdownItem.setIcon("password-policies");
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "permissions"));
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() -> _hasPermission(category, ActionKeys.DELETE),
						dropdownItem -> {
							dropdownItem.putData("action", "deleteCategory");

							dropdownItem.putData(
								"deleteCategoryURL",
								PortletURLBuilder.createActionURL(
									_renderResponse
								).setActionName(
									"deleteCategory"
								).setRedirect(
									_themeDisplay.getURLCurrent()
								).setParameter(
									"categoryId", category.getCategoryId()
								).buildString());

							dropdownItem.setIcon("trash");
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "delete"));
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).build();
	}

	private String _getDisplayPageURL(AssetCategory category)
		throws PortalException {

		return _assetDisplayPageFriendlyURLProvider.getFriendlyURL(
			AssetCategory.class.getName(), category.getCategoryId(),
			_themeDisplay);
	}

	private String _getSelectCategoryURL(long vocabularyId) throws Exception {
		return PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				_httpServletRequest, AssetCategory.class.getName(),
				PortletProvider.Action.BROWSE)
		).setParameter(
			"allowedSelectVocabularies", true
		).setParameter(
			"eventName", _renderResponse.getNamespace() + "selectCategory"
		).setParameter(
			"moveCategory", true
		).setParameter(
			"singleSelect", true
		).setParameter(
			"vocabularyIds",
			() -> {
				AssetVocabulary vocabulary =
					AssetVocabularyServiceUtil.getVocabulary(vocabularyId);

				List<AssetVocabulary> vocabularies =
					AssetVocabularyServiceUtil.getGroupVocabularies(
						_themeDisplay.getScopeGroupId(),
						vocabulary.getVisibilityType());

				return ListUtil.toString(
					vocabularies, AssetVocabulary.VOCABULARY_ID_ACCESSOR);
			}
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	private boolean _hasPermission(AssetCategory category, String actionId)
		throws PortalException {

		if (category.getGroupId() != _themeDisplay.getScopeGroupId()) {
			return false;
		}

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			_themeDisplay.getPermissionChecker(),
			_themeDisplay.getScopeGroupId(), AssetCategory.class.getName(),
			category.getCategoryId(),
			AssetCategoriesAdminPortletKeys.ASSET_CATEGORIES_ADMIN, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		return AssetCategoryPermission.contains(
			_themeDisplay.getPermissionChecker(), category, actionId);
	}

	private boolean _isAssetCategoriesLimitExceeded(
			AssetCategoriesDisplayContext assetCategoriesDisplayContext)
		throws PortalException {

		long vocabularyId = assetCategoriesDisplayContext.getVocabularyId();

		if (vocabularyId <= 0) {
			return false;
		}

		try {
			AssetVocabulary vocabulary =
				AssetVocabularyLocalServiceUtil.getVocabulary(vocabularyId);

			int vocabularyCategoriesCount =
				AssetCategoryLocalServiceUtil.getVocabularyCategoriesCount(
					vocabulary.getVocabularyId());

			AssetCategoriesCompanyConfiguration
				assetCategoriesCompanyConfiguration =
					ConfigurationProviderUtil.getCompanyConfiguration(
						AssetCategoriesCompanyConfiguration.class,
						_themeDisplay.getCompanyId());

			if (vocabularyCategoriesCount >=
					assetCategoriesCompanyConfiguration.
						maximumNumberOfCategoriesPerVocabulary()) {

				return true;
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetCategoryActionDropdownItemsProvider.class);

	private final boolean _assetCategoriesLimitExceeded;
	private final AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;
	private final HttpServletRequest _httpServletRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}