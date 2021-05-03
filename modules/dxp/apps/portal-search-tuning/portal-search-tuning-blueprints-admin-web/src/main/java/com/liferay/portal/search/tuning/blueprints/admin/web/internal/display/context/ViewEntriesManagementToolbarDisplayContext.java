/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.blueprints.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminMVCCommandNames;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminTabNames;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminWebKeys;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.util.BlueprintsAdminRequestUtil;

import java.util.List;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Petteri Karttunen
 */
public abstract class ViewEntriesManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public ViewEntriesManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<?> searchContainer, String displayStyle) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);

		this.displayStyle = displayStyle;

		tab = getTabName();

		themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteEntries");

				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));

				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);
		clearResultsURL.setParameter(
			"mvcRenderCommandName", getMVCRenderCommandName());
		clearResultsURL.setParameter(BlueprintsAdminWebKeys.TAB, tab);

		clearResultsURL.setProperty("orderByCol", getOrderByCol());
		clearResultsURL.setProperty("orderByType", getOrderByType());

		return clearResultsURL.toString();
	}

	@Override
	public String getDefaultEventHandler() {
		return "VIEW_ENTRIES_MANAGEMENT_TOOLBAR_DEFAULT_EVENT_HANDLER";
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		searchActionURL.setParameter(
			"mvcRenderCommandName", getMVCRenderCommandName());

		searchActionURL.setParameter(BlueprintsAdminWebKeys.TAB, tab);

		searchActionURL.setProperty("orderByCol", getOrderByCol());
		searchActionURL.setProperty("orderByType", getOrderByType());

		return searchActionURL.toString();
	}

	@Override
	public List<ViewTypeItem> getViewTypeItems() {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setProperty(
			"mvcRenderCommandName", getMVCRenderCommandName());

		portletURL.setParameter(BlueprintsAdminWebKeys.TAB, tab);

		if (searchContainer.getDelta() > 0) {
			portletURL.setProperty(
				"delta", String.valueOf(searchContainer.getDelta()));
		}

		portletURL.setProperty("orderByCol", searchContainer.getOrderByCol());
		portletURL.setProperty("orderByType", searchContainer.getOrderByType());

		if (searchContainer.getCur() > 0) {
			portletURL.setProperty(
				"cur", String.valueOf(searchContainer.getCur()));
		}

		return new ViewTypeItemList(portletURL, displayStyle) {
			{
				addListViewTypeItem();

				addTableViewTypeItem();
			}
		};
	}

	protected String createActionURL(String actionName, String cmd) {
		PortletURL portletURL = liferayPortletResponse.createActionURL();

		portletURL.setParameter(ActionRequest.ACTION_NAME, actionName);

		if (!Validator.isBlank(cmd)) {
			portletURL.setParameter(Constants.CMD, Constants.ADD);
		}

		portletURL.setParameter("redirect", currentURLObj.toString());
		portletURL.setParameter(BlueprintsAdminWebKeys.TAB, tab);

		return portletURL.toString();
	}

	protected PortletURL getCurrentSortingURL() {
		PortletURL sortingURL = getPortletURL();

		sortingURL.setProperty(
			"mvcRenderCommandName", getMVCRenderCommandName());

		sortingURL.setParameter(BlueprintsAdminWebKeys.TAB, tab);

		sortingURL.setProperty(SearchContainer.DEFAULT_CUR_PARAM, "0");

		String keywords = BlueprintsAdminRequestUtil.getKeywords(
			liferayPortletRequest);

		if (!Validator.isBlank(keywords)) {
			sortingURL.setProperty("keywords", keywords);
		}

		return sortingURL;
	}

	protected String getMVCRenderCommandName() {
		if (tab.equals(BlueprintsAdminTabNames.ELEMENTS)) {
			return BlueprintsAdminMVCCommandNames.VIEW_ELEMENTS;
		}

		return BlueprintsAdminMVCCommandNames.VIEW_BLUEPRINTS;
	}

	@Override
	protected List<DropdownItem> getOrderByDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(
					Objects.equals(getOrderByCol(), Field.TITLE));
				dropdownItem.setHref(
					getCurrentSortingURL(), "orderByCol", Field.TITLE);
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "title"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setActive(
					Objects.equals(getOrderByCol(), Field.CREATE_DATE));
				dropdownItem.setHref(
					getCurrentSortingURL(), "orderByCol", Field.CREATE_DATE);
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "created"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setActive(
					Objects.equals(getOrderByCol(), Field.MODIFIED_DATE));
				dropdownItem.setHref(
					getCurrentSortingURL(), "orderByCol", Field.MODIFIED_DATE);
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "modified"));
			}
		).build();
	}

	protected String getTabName() {
		return ParamUtil.getString(
			liferayPortletRequest, BlueprintsAdminWebKeys.TAB,
			BlueprintsAdminTabNames.BLUEPRINTS);
	}

	protected final String displayStyle;
	protected final String tab;
	protected final ThemeDisplay themeDisplay;

}