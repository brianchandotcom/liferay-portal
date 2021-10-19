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

package com.liferay.search.experiences.web.internal.display.context;

import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
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
import com.liferay.search.experiences.constants.SXPPortletKeys;

import java.util.Objects;

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
		tabs1 = ParamUtil.getString(
			liferayPortletRequest, "tabs1", "sxpBlueprints");
		themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getDisplayStyle() {
		String displayStyle = ParamUtil.getString(
			liferayPortletRequest, "displayStyle");

		String preferenceName = "entries-display-style-" + tabs1;

		if (Validator.isNull(displayStyle)) {
			return portalPreferences.getValue(
				SXPPortletKeys.SXP_BLUEPRINT_ADMIN, preferenceName,
				"descriptive");
		}

		portalPreferences.setValue(
			SXPPortletKeys.SXP_BLUEPRINT_ADMIN, preferenceName, displayStyle);

		httpServletRequest.setAttribute(
			WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);

		return displayStyle;
	}

	protected PortletURL getIteratorURL() {
		return PortletURLBuilder.createRenderURL(
			liferayPortletResponse
		).setMVCRenderCommandName(
			getMVCRenderCommandName()
		).setTabs1(
			tabs1
		).build();
	}

	protected String getMVCRenderCommandName() {
		if (tabs1.equals("sxpElements")) {
			return "/sxp_blueprint_admin/view_sxp_elements";
		}

		return "/sxp_blueprint_admin/view_sxp_blueprints";
	}

	protected String getOrderByCol() {
		return ParamUtil.getString(
			liferayPortletRequest, "orderByCol", Field.MODIFIED_DATE);
	}

	protected String getOrderByType() {
		String orderByType = ParamUtil.getString(
			liferayPortletRequest, "orderByType");

		if (Validator.isNotNull(orderByType)) {
			return orderByType;
		}

		if (Objects.equals(getOrderByCol(), Field.TITLE)) {
			return "asc";
		}

		return "desc";
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

	protected final HttpServletRequest httpServletRequest;
	protected final LiferayPortletRequest liferayPortletRequest;
	protected final LiferayPortletResponse liferayPortletResponse;
	protected final PortalPreferences portalPreferences;
	protected final String tabs1;
	protected final ThemeDisplay themeDisplay;

}