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

import com.liferay.commerce.organization.service.CommerceOrganizationService;
import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.commerce.organization.web.internal.display.context.util.CommerceOrganizationRequestHelper;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public abstract class BaseCommerceOrganizationDisplayContext {

	public BaseCommerceOrganizationDisplayContext(
		CommerceOrganizationHelper commerceOrganizationHelper,
		CommerceOrganizationService commerceOrganizationService,
		HttpServletRequest httpServletRequest, Portal portal) {

		_commerceOrganizationHelper = commerceOrganizationHelper;

		this.commerceOrganizationService = commerceOrganizationService;
		this.portal = portal;

		commerceOrganizationRequestHelper =
			new CommerceOrganizationRequestHelper(httpServletRequest);

		_defaultOrderByCol = "create-date";
		_defaultOrderByType = "desc";
	}

	public Organization getCurrentOrganization() throws PortalException {
		long organizationId = ParamUtil.getLong(
			commerceOrganizationRequestHelper.getRequest(), "organizationId");

		if (organizationId > 0) {
			return commerceOrganizationService.getOrganization(organizationId);
		}

		return _getCurrentAccountOrganization();
	}

	public String getDisplayStyle() {
		if (_displayStyle == null) {
			_displayStyle = ParamUtil.getString(
				commerceOrganizationRequestHelper.getRequest(), "displayStyle");
		}

		return _displayStyle;
	}

	public String getOrderByCol() {
		return ParamUtil.getString(
			commerceOrganizationRequestHelper.getRequest(),
			SearchContainer.DEFAULT_ORDER_BY_COL_PARAM, _defaultOrderByCol);
	}

	public String getOrderByType() {
		return ParamUtil.getString(
			commerceOrganizationRequestHelper.getRequest(),
			SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM, _defaultOrderByType);
	}

	public String getPath(Organization organization) throws PortalException {
		Organization topOrganization = _getCurrentAccountOrganization();

		List<Organization> organizations = new ArrayList<>();

		while (organization != null) {
			if (organization.getOrganizationId() ==
					topOrganization.getOrganizationId()) {

				break;
			}

			organization = commerceOrganizationService.getOrganization(
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
		LiferayPortletResponse liferayPortletResponse =
			commerceOrganizationRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		String redirect = ParamUtil.getString(
			commerceOrganizationRequestHelper.getRequest(), "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		String delta = ParamUtil.getString(
			commerceOrganizationRequestHelper.getRequest(), "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			commerceOrganizationRequestHelper.getRequest(), "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		String displayStyle = ParamUtil.getString(
			commerceOrganizationRequestHelper.getRequest(), "displayStyle");

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", getDisplayStyle());
		}

		String keywords = ParamUtil.getString(
			commerceOrganizationRequestHelper.getRequest(), "keywords");

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		return portletURL;
	}

	public void setBreadcrumbs(Organization currentOrganization)
		throws PortalException {

		PortletURL portletURL = getPortletURL();

		Map<String, Object> data = new HashMap<>();

		data.put("direction-right", StringPool.TRUE);

		Organization topOrganization = _getCurrentAccountOrganization();

		Organization organization = currentOrganization;

		while (organization != null) {
			if (organization.getOrganizationId() ==
					topOrganization.getOrganizationId()) {

				break;
			}

			organization = commerceOrganizationService.getOrganization(
				organization.getParentOrganizationId());

			portletURL.setParameter(
				"organizationId",
				String.valueOf(organization.getOrganizationId()));

			portal.addPortletBreadcrumbEntry(
				commerceOrganizationRequestHelper.getRequest(),
				organization.getName(), portletURL.toString(), data);
		}

		portal.addPortletBreadcrumbEntry(
			commerceOrganizationRequestHelper.getRequest(),
			currentOrganization.getName(), portletURL.toString(), data);
	}

	protected void setDefaultOrderByCol(String defaultOrderByCol) {
		_defaultOrderByCol = defaultOrderByCol;
	}

	protected void setDefaultOrderByType(String defaultOrderByType) {
		_defaultOrderByType = defaultOrderByType;
	}

	protected final CommerceOrganizationRequestHelper
		commerceOrganizationRequestHelper;
	protected final CommerceOrganizationService commerceOrganizationService;
	protected final Portal portal;

	private Organization _getCurrentAccountOrganization()
		throws PortalException {

		return _commerceOrganizationHelper.getCurrentOrganization(
			commerceOrganizationRequestHelper.getRequest());
	}

	private final CommerceOrganizationHelper _commerceOrganizationHelper;
	private String _defaultOrderByCol;
	private String _defaultOrderByType;
	private String _displayStyle;

}