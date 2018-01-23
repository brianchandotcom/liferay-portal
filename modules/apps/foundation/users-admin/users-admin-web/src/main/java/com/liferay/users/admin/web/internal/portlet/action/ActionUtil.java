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

package com.liferay.users.admin.web.internal.portlet.action;

import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.OrgLabor;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.service.AddressServiceUtil;
import com.liferay.portal.kernel.service.EmailAddressServiceUtil;
import com.liferay.portal.kernel.service.OrgLaborServiceUtil;
import com.liferay.portal.kernel.service.OrganizationServiceUtil;
import com.liferay.portal.kernel.service.PhoneServiceUtil;
import com.liferay.portal.kernel.service.WebsiteServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class ActionUtil {

	public static void getAddress(HttpServletRequest request) throws Exception {
		long addressId = ParamUtil.getLong(request, "addressId");

		Address address = null;

		if (addressId > 0) {
			address = AddressServiceUtil.getAddress(addressId);
		}

		request.setAttribute(WebKeys.ADDRESS, address);
	}

	public static void getAddress(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getAddress(request);
	}

	public static void getEmailAddress(HttpServletRequest request)
		throws Exception {

		long emailAddressId = ParamUtil.getLong(request, "emailAddressId");

		EmailAddress emailAddress = null;

		if (emailAddressId > 0) {
			emailAddress = EmailAddressServiceUtil.getEmailAddress(
				emailAddressId);
		}

		request.setAttribute(WebKeys.EMAIL_ADDRESS, emailAddress);
	}

	public static void getEmailAddress(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getEmailAddress(request);
	}

	public static Organization getOrganization(HttpServletRequest request)
		throws Exception {

		long organizationId = ParamUtil.getLong(request, "organizationId");

		Organization organization = null;

		if (organizationId > 0) {
			organization = OrganizationServiceUtil.getOrganization(
				organizationId);
		}

		request.setAttribute(WebKeys.ORGANIZATION, organization);

		return organization;
	}

	public static Organization getOrganization(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getOrganization(request);
	}

	public static void getOrgLabor(HttpServletRequest request)
		throws Exception {

		long orgLaborId = ParamUtil.getLong(request, "orgLaborId");

		OrgLabor orgLabor = null;

		if (orgLaborId > 0) {
			orgLabor = OrgLaborServiceUtil.getOrgLabor(orgLaborId);
		}

		request.setAttribute(WebKeys.ORG_LABOR, orgLabor);
	}

	public static void getOrgLabor(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getOrgLabor(request);
	}

	public static void getPhone(HttpServletRequest request) throws Exception {
		long phoneId = ParamUtil.getLong(request, "phoneId");

		Phone phone = null;

		if (phoneId > 0) {
			phone = PhoneServiceUtil.getPhone(phoneId);
		}

		request.setAttribute(WebKeys.PHONE, phone);
	}

	public static void getPhone(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getPhone(request);
	}

	public static void getWebsite(HttpServletRequest request) throws Exception {
		long websiteId = ParamUtil.getLong(request, "websiteId");

		Website website = null;

		if (websiteId > 0) {
			website = WebsiteServiceUtil.getWebsite(websiteId);
		}

		request.setAttribute(WebKeys.WEBSITE, website);
	}

	public static void getWebsite(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getWebsite(request);
	}

}