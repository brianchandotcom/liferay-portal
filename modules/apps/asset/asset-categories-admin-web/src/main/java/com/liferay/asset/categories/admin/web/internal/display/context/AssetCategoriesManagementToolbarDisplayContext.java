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

package com.liferay.asset.categories.admin.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class AssetCategoriesManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public AssetCategoriesManagementToolbarDisplayContext(
			HttpServletRequest httpServletRequest,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			AssetCategoriesDisplayContext assetCategoriesDisplayContext)
		throws PortalException {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			assetCategoriesDisplayContext.getCategoriesSearchContainer());

		_assetCategoriesDisplayContext = assetCategoriesDisplayContext;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						this::_isSetDisplayPageTemplateEnabled,
						dropdownItem -> {
							dropdownItem.putData(
								"action", "setCategoryDisplayPageTemplate");
							dropdownItem.putData(
								"setCategoryDisplayPageTemplateURL",
								PortletURLBuilder.createRenderURL(
									liferayPortletResponse
								).setMVCPath(
									"/set_category_display_page_template.jsp"
								).setRedirect(
									currentURLObj
								).setParameter(
									"parentCategoryId",
									_assetCategoriesDisplayContext.
										getCategoryId()
								).setParameter(
									"vocabularyId",
									_assetCategoriesDisplayContext.
										getVocabularyId()
								).buildString());
							dropdownItem.setIcon("page");
							dropdownItem.setLabel(
								LanguageUtil.get(
									httpServletRequest,
									"assign-display-page-template"));
							dropdownItem.setQuickAction(true);
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						dropdownItem -> {
							dropdownItem.putData(
								"action", "deleteSelectedCategories");
							dropdownItem.setIcon("trash");
							dropdownItem.setLabel(
								LanguageUtil.get(httpServletRequest, "delete"));
							dropdownItem.setQuickAction(true);
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).build();
	}

	public String getAvailableActions(AssetCategory category)
		throws PortalException {

		List<String> availableActions = new ArrayList<>();

		if (_assetCategoriesDisplayContext.hasPermission(
				category, ActionKeys.UPDATE)) {

			availableActions.add("setCategoryDisplayPageTemplate");
		}

		if (_assetCategoriesDisplayContext.hasPermission(
				category, ActionKeys.DELETE)) {

			availableActions.add("deleteSelectedCategories");
		}

		return StringUtil.merge(availableActions, StringPool.COMMA);
	}

	@Override
	public String getClearResultsURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setKeywords(
			StringPool.BLANK
		).setNavigation(
			"all"
		).setParameter(
			"categoryId", "0"
		).buildString();
	}

	@Override
	public String getComponentId() {
		return "assetCategoriesManagementToolbar";
	}

	@Override
	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createRenderURL(
						liferayPortletResponse
					).setMVCPath(
						"/edit_category.jsp"
					).setParameter(
						"parentCategoryId",
						() -> {
							if (_assetCategoriesDisplayContext.getCategoryId() >
									0) {

								return _assetCategoriesDisplayContext.
									getCategoryId();
							}

							return null;
						}
					).setParameter(
						"vocabularyId",
						_assetCategoriesDisplayContext.getVocabularyId()
					).buildPortletURL());

				String label = "add-category";

				if (_assetCategoriesDisplayContext.getCategoryId() > 0) {
					label = "add-subcategory";
				}

				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, label));
			}
		).build();
	}

	@Override
	public String getDefaultEventHandler() {
		return "assetCategoriesManagementToolbarDefaultEventHandler";
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		if (!_isNavigationCategory()) {
			return null;
		}

		AssetCategory category = _assetCategoriesDisplayContext.getCategory();

		if (category == null) {
			return null;
		}

		return LabelItemListBuilder.add(
			labelItem -> {
				labelItem.putData(
					"removeLabelURL",
					PortletURLBuilder.create(
						PortletURLUtil.clone(
							currentURLObj, liferayPortletResponse)
					).setNavigation(
						(String)null
					).setParameter(
						"categoryId", "0"
					).buildString());

				labelItem.setCloseable(true);

				ThemeDisplay themeDisplay =
					(ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				labelItem.setLabel(category.getTitle(themeDisplay.getLocale()));
			}
		).build();
	}

	@Override
	public List<DropdownItem> getFilterNavigationDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(_isNavigationAll());
				dropdownItem.setHref(getPortletURL(), "navigation", "all");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "all"));
			}
		).add(
			_assetCategoriesDisplayContext::isFlattenedNavigationAllowed,
			dropdownItem -> {
				dropdownItem.putData("action", "selectCategory");
				dropdownItem.putData(
					"categoriesSelectorURL", _getCategoriesSelectorURL());
				dropdownItem.putData(
					"viewCategoriesURL", _getViewCategoriesURL());
				dropdownItem.setActive(_isNavigationCategory());
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "category"));
			}
		).build();
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	@Override
	public String getSearchContainerId() {
		return "assetCategories";
	}

	@Override
	public Boolean isShowCreationMenu() {
		return _assetCategoriesDisplayContext.isShowCategoriesAddButton();
	}

	@Override
	protected String getDisplayStyle() {
		return _assetCategoriesDisplayContext.getDisplayStyle();
	}

	@Override
	protected String[] getDisplayViews() {
		if (_assetCategoriesDisplayContext.isFlattenedNavigationAllowed()) {
			return new String[0];
		}

		return new String[] {"list", "descriptive"};
	}

	@Override
	protected String[] getOrderByKeys() {
		if (_assetCategoriesDisplayContext.isFlattenedNavigationAllowed()) {
			return new String[] {"path"};
		}

		return new String[] {"create-date"};
	}

	private String _getCategoriesSelectorURL() throws Exception {
		return PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				httpServletRequest, AssetCategory.class.getName(),
				PortletProvider.Action.BROWSE)
		).setParameter(
			"eventName",
			liferayPortletResponse.getNamespace() + "selectCategory"
		).setParameter(
			"singleSelect", true
		).setParameter(
			"vocabularyIds", _assetCategoriesDisplayContext.getVocabularyId()
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	private String _getViewCategoriesURL() throws PortalException {
		return PortletURLBuilder.createRenderURL(
			liferayPortletResponse
		).setMVCPath(
			"/view.jsp"
		).setNavigation(
			"category"
		).setParameter(
			"vocabularyId", _assetCategoriesDisplayContext.getVocabularyId()
		).buildString();
	}

	private boolean _isNavigationAll() {
		if (!_assetCategoriesDisplayContext.isFlattenedNavigationAllowed() ||
			Objects.equals(getNavigation(), "all")) {

			return true;
		}

		return false;
	}

	private boolean _isNavigationCategory() {
		if (_assetCategoriesDisplayContext.isFlattenedNavigationAllowed() &&
			Objects.equals(getNavigation(), "category")) {

			return true;
		}

		return false;
	}

	private boolean _isSetDisplayPageTemplateEnabled() {
		if (_setDisplayPageTemplateEnabled != null) {
			return _setDisplayPageTemplateEnabled;
		}

		boolean setDisplayPageTemplateEnabled = true;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getScopeGroup();

		if (group.isCompany() || group.isDepot()) {
			setDisplayPageTemplateEnabled = false;
		}

		_setDisplayPageTemplateEnabled = setDisplayPageTemplateEnabled;

		return _setDisplayPageTemplateEnabled;
	}

	private final AssetCategoriesDisplayContext _assetCategoriesDisplayContext;
	private Boolean _setDisplayPageTemplateEnabled;

}