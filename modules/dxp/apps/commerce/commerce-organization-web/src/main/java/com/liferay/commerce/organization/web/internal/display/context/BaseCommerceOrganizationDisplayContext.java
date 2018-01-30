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

package com.liferay.commerce.organization.web.internal.display.context;

import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Marco Leo
 */
public abstract class BaseCommerceOrganizationDisplayContext {

	public BaseCommerceOrganizationDisplayContext(
		HttpServletRequest httpServletRequest,
		OrganizationService organizationService) {

		this.httpServletRequest = httpServletRequest;
		this.organizationService = organizationService;

		themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		cpRequestHelper = new CPRequestHelper(httpServletRequest);

		liferayPortletRequest = cpRequestHelper.getLiferayPortletRequest();
		liferayPortletResponse = cpRequestHelper.getLiferayPortletResponse();

		_defaultOrderByCol = "create-date";
		_defaultOrderByType = "desc";
	}

	public Organization getCurrentAccount() throws PortalException {
		HttpServletRequest originalHttpServletRequest =
			PortalUtil.getOriginalServletRequest(httpServletRequest);

		HttpSession httpSession = originalHttpServletRequest.getSession();

		long organizationId = GetterUtil.getLong(
			httpSession.getAttribute("LIFERAY_SHARED_currentOrganizationId"));

		return OrganizationLocalServiceUtil.fetchOrganization(organizationId);
	}

	public Organization getCurrentOrganization() throws PortalException {
		long organizationId = ParamUtil.getLong(
			httpServletRequest, "organizationId");

		Organization organization =
			OrganizationLocalServiceUtil.fetchOrganization(organizationId);

		if (organization == null) {
			organization = getCurrentAccount();
		}

		return organization;
	}

	public String getDisplayStyle() {
		if (_displayStyle == null) {
			_displayStyle = ParamUtil.getString(
				httpServletRequest, "displayStyle");
		}

		return _displayStyle;
	}

	public String getOrderByCol() {
		return ParamUtil.getString(
			httpServletRequest, SearchContainer.DEFAULT_ORDER_BY_COL_PARAM,
			_defaultOrderByCol);
	}

	public String getOrderByType() {
		return ParamUtil.getString(
			httpServletRequest, SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM,
			_defaultOrderByType);
	}

	public String getPath(Organization organization) throws PortalException {
		Organization topOrganization = getCurrentAccount();

		List<Organization> organizations = new ArrayList<>();

		while (organization != null) {
			if (organization.getOrganizationId() ==
					topOrganization.getOrganizationId()) {

				break;
			}

			organization = OrganizationLocalServiceUtil.fetchOrganization(
				organization.getParentOrganizationId());

			organizations.add(organization);
		}

		int size = organizations.size();

		StringBundler sb = new StringBundler(((size - 1) * 4) + 1);

		organization = organizations.get(size - 1);

		sb.append(organization.getName());

		for (int i = size - 2; i >= 0; i--) {
			organization = organizations.get(i);

			sb.append(StringPool.SPACE);
			sb.append(StringPool.GREATER_THAN);
			sb.append(StringPool.SPACE);
			sb.append(organization.getName());
		}

		return sb.toString();
	}

	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		String redirect = ParamUtil.getString(httpServletRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		String delta = ParamUtil.getString(httpServletRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			httpServletRequest, "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		String displayStyle = ParamUtil.getString(
			httpServletRequest, "displayStyle");

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", getDisplayStyle());
		}

		String keywords = ParamUtil.getString(httpServletRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		return portletURL;
	}

	public Organization getSiteOrganization() throws PortalException {
		Group group = themeDisplay.getScopeGroup();

		return OrganizationLocalServiceUtil.fetchOrganization(
			group.getOrganizationId());
	}

	public List<Organization> getUserOrganizations() throws PortalException {
		User user = themeDisplay.getUser();

		return user.getOrganizations(true);
	}

	public void setBreadCrumbs(Organization curentOrganization)
		throws PortalException {

		PortletURL portletURL = getPortletURL();

		Map<String, Object> data = new HashMap<>();

		data.put("direction-right", StringPool.TRUE);

		Organization topOrganization = getCurrentAccount();

		List<Organization> organizations = new ArrayList<>();

		Organization organization = curentOrganization;

		while (organization != null) {
			if (organization.getOrganizationId() ==
					topOrganization.getOrganizationId()) {

				break;
			}

			organization = OrganizationLocalServiceUtil.fetchOrganization(
				organization.getParentOrganizationId());

			portletURL.setParameter(
				"organizationId",
				String.valueOf(organization.getOrganizationId()));

			PortalUtil.addPortletBreadcrumbEntry(
				httpServletRequest, organization.getName(),
				portletURL.toString(), data);
		}

		PortalUtil.addPortletBreadcrumbEntry(
			httpServletRequest, curentOrganization.getName(),
			portletURL.toString(), data);
	}

	public void setDefaultOrderByCol(String defaultOrderByCol) {
		_defaultOrderByCol = defaultOrderByCol;
	}

	public void setDefaultOrderByType(String defaultOrderByType) {
		_defaultOrderByType = defaultOrderByType;
	}

	public boolean siteHasOrganization() {
		Group group = themeDisplay.getScopeGroup();

		if (group.getOrganizationId() > 0) {
			return true;
		}

		return false;
	}

	protected final CPRequestHelper cpRequestHelper;
	protected final HttpServletRequest httpServletRequest;
	protected final LiferayPortletRequest liferayPortletRequest;
	protected final LiferayPortletResponse liferayPortletResponse;
	protected final OrganizationService organizationService;
	protected final ThemeDisplay themeDisplay;

	private String _defaultOrderByCol;
	private String _defaultOrderByType;
	private String _displayStyle;

}