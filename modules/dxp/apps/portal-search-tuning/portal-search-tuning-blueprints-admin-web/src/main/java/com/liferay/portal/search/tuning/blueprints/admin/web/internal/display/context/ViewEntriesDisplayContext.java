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

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminMVCCommandNames;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminTabNames;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminWebKeys;
import com.liferay.portal.search.tuning.blueprints.constants.BlueprintsPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Petteri Karttunen
 */
public abstract class ViewEntriesDisplayContext<R> {

	public ViewEntriesDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		this.liferayPortletRequest = liferayPortletRequest;
		this.liferayPortletResponse = liferayPortletResponse;

		httpServletRequest = liferayPortletRequest.getHttpServletRequest();
		portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			liferayPortletRequest);
		tab = getTabName();
		themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getDisplayStyle() {
		String displayStyle = ParamUtil.getString(
			liferayPortletRequest, "displayStyle");

		String preferenceName = "entries-display-style-" + tab;

		if (Validator.isNull(displayStyle)) {
			return portalPreferences.getValue(
				BlueprintsPortletKeys.BLUEPRINTS_ADMIN, preferenceName,
				"descriptive");
		}

		portalPreferences.setValue(
			BlueprintsPortletKeys.BLUEPRINTS_ADMIN, preferenceName,
			displayStyle);

		httpServletRequest.setAttribute(
			WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);

		return displayStyle;
	}

	protected PortletURL getIteratorURL() {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setProperty(
			"mvcRenderCommandName", getMVCRenderCommandName());

		portletURL.setParameter(BlueprintsAdminWebKeys.TAB, tab);

		return portletURL;
	}

	protected String getMVCRenderCommandName() {
		if (tab.equals(BlueprintsAdminTabNames.ELEMENTS)) {
			return BlueprintsAdminMVCCommandNames.VIEW_ELEMENTS;
		}

		return BlueprintsAdminMVCCommandNames.VIEW_BLUEPRINTS;
	}

	protected String getOrderByCol() {
		return ParamUtil.getString(
			liferayPortletRequest, "orderByCol", Field.MODIFIED_DATE);
	}

	protected String getOrderByType() {
		return ParamUtil.getString(
			liferayPortletRequest, "orderByType", "desc");
	}

	protected SearchContainer<R> getSearchContainer()
		throws PortalException, PortletException {

		SearchContainer<R> searchContainer = new SearchContainer<>(
			liferayPortletRequest, getIteratorURL(), null,
			"no-entries-were-found");

		searchContainer.setOrderByCol(getOrderByCol());

		searchContainer.setOrderByType(getOrderByType());

		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(liferayPortletResponse));

		return searchContainer;
	}

	protected String getTabName() {
		return ParamUtil.getString(
			liferayPortletRequest, BlueprintsAdminWebKeys.TAB,
			BlueprintsAdminTabNames.BLUEPRINTS);
	}

	protected final HttpServletRequest httpServletRequest;
	protected final LiferayPortletRequest liferayPortletRequest;
	protected final LiferayPortletResponse liferayPortletResponse;
	protected final PortalPreferences portalPreferences;
	protected final String tab;
	protected final ThemeDisplay themeDisplay;

}