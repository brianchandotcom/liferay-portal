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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

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
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			HttpServletRequest httpServletRequest,
			AssetCategoriesDisplayContext assetCategoriesDisplayContext)
		throws PortalException {

		super(
			liferayPortletRequest, liferayPortletResponse, httpServletRequest,
			assetCategoriesDisplayContext.getCategoriesSearchContainer());

		_assetCategoriesDisplayContext = assetCategoriesDisplayContext;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData(
							"action", "deleteSelectedCategories");
						dropdownItem.setIcon("times-circle");
						dropdownItem.setLabel(
							LanguageUtil.get(request, "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	public String getAvailableActions(AssetCategory category)
		throws PortalException {

		if (_assetCategoriesDisplayContext.hasPermission(
				category, ActionKeys.UPDATE)) {

			return "deleteSelectedCategories";
		}

		return StringPool.BLANK;
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("navigation", "all");
		clearResultsURL.setParameter("categoryId", "0");
		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	@Override
	public String getComponentId() {
		return "assetCategoriesManagementToolbar";
	}

	@Override
	public CreationMenu getCreationMenu() {
		return new CreationMenu() {
			{
				addPrimaryDropdownItem(
					dropdownItem -> {
						PortletURL addCategoryURL =
							liferayPortletResponse.createRenderURL();

						addCategoryURL.setParameter(
							"mvcPath", "/edit_category.jsp");

						if (_assetCategoriesDisplayContext.getCategoryId() >
								0) {

							addCategoryURL.setParameter(
								"parentCategoryId",
								String.valueOf(
									_assetCategoriesDisplayContext.
										getCategoryId()));
						}

						addCategoryURL.setParameter(
							"vocabularyId",
							String.valueOf(
								_assetCategoriesDisplayContext.
									getVocabularyId()));

						dropdownItem.setHref(addCategoryURL);

						String label = "add-category";

						if (_assetCategoriesDisplayContext.getCategoryId() >
								0) {

							label = "add-subcategory";
						}

						dropdownItem.setLabel(LanguageUtil.get(request, label));
					});
			}
		};
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

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return new LabelItemList() {
			{
				add(
					labelItem -> {
						PortletURL removeLabelURL = PortletURLUtil.clone(
							currentURLObj, liferayPortletResponse);

						removeLabelURL.setParameter("navigation", (String)null);
						removeLabelURL.setParameter("categoryId", "0");

						labelItem.putData(
							"removeLabelURL", removeLabelURL.toString());

						labelItem.setCloseable(true);

						labelItem.setLabel(
							category.getTitle(themeDisplay.getLocale()));
					});
			}
		};
	}

	@Override
	public List<DropdownItem> getFilterNavigationDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(_isNavigationAll());
						dropdownItem.setHref(
							getPortletURL(), "navigation", "all");
						dropdownItem.setLabel(LanguageUtil.get(request, "all"));
					});

				if (_assetCategoriesDisplayContext.
						isFlattenedNavigationAllowed()) {

					add(
						dropdownItem -> {
							dropdownItem.setActive(_isNavigationCategory());
							dropdownItem.putData("action", "selectCategory");
							dropdownItem.putData(
								"categoriesSelectorURL",
								_getCategoriesSelectorURL());
							dropdownItem.putData(
								"viewCategoriesURL", _getViewCategoriesURL());
							dropdownItem.setLabel(
								LanguageUtil.get(request, "category"));
						});
				}
			}
		};
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
		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			request, AssetCategory.class.getName(),
			PortletProvider.Action.BROWSE);

		portletURL.setParameter(
			"vocabularyIds",
			String.valueOf(_assetCategoriesDisplayContext.getVocabularyId()));
		portletURL.setParameter(
			"eventName",
			liferayPortletResponse.getNamespace() + "selectCategory");
		portletURL.setParameter("singleSelect", Boolean.TRUE.toString());
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	private String _getViewCategoriesURL() {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view_categories.jsp");
		portletURL.setParameter("navigation", "category");
		portletURL.setParameter(
			"vocabularyId",
			String.valueOf(_assetCategoriesDisplayContext.getVocabularyId()));

		return portletURL.toString();
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

	private final AssetCategoriesDisplayContext _assetCategoriesDisplayContext;

}