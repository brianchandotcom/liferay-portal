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

	public static void getAddress(HttpServletRequest httpServletRequest)
		throws Exception {

		long addressId = ParamUtil.getLong(httpServletRequest, "addressId");

		Address address = null;

		if (addressId > 0) {
			address = AddressServiceUtil.getAddress(addressId);
		}

		httpServletRequest.setAttribute(WebKeys.ADDRESS, address);
	}

	public static void getAddress(PortletRequest portletRequest)
		throws Exception {

		getAddress(PortalUtil.getHttpServletRequest(portletRequest));
	}

	public static void getEmailAddress(HttpServletRequest httpServletRequest)
		throws Exception {

		long emailAddressId = ParamUtil.getLong(
			httpServletRequest, "emailAddressId");

		EmailAddress emailAddress = null;

		if (emailAddressId > 0) {
			emailAddress = EmailAddressServiceUtil.getEmailAddress(
				emailAddressId);
		}

		httpServletRequest.setAttribute(WebKeys.EMAIL_ADDRESS, emailAddress);
	}

	public static void getEmailAddress(PortletRequest portletRequest)
		throws Exception {

		getEmailAddress(PortalUtil.getHttpServletRequest(portletRequest));
	}

	public static Organization getOrganization(
			HttpServletRequest httpServletRequest)
		throws Exception {

		long organizationId = ParamUtil.getLong(
			httpServletRequest, "organizationId");

		Organization organization = null;

		if (organizationId > 0) {
			organization = OrganizationServiceUtil.getOrganization(
				organizationId);
		}

		httpServletRequest.setAttribute(WebKeys.ORGANIZATION, organization);

		return organization;
	}

	public static Organization getOrganization(PortletRequest portletRequest)
		throws Exception {

		return getOrganization(
			PortalUtil.getHttpServletRequest(portletRequest));
	}

	public static void getOrgLabor(HttpServletRequest httpServletRequest)
		throws Exception {

		long orgLaborId = ParamUtil.getLong(httpServletRequest, "orgLaborId");

		OrgLabor orgLabor = null;

		if (orgLaborId > 0) {
			orgLabor = OrgLaborServiceUtil.getOrgLabor(orgLaborId);
		}

		httpServletRequest.setAttribute(WebKeys.ORG_LABOR, orgLabor);
	}

	public static void getOrgLabor(PortletRequest portletRequest)
		throws Exception {

		getOrgLabor(PortalUtil.getHttpServletRequest(portletRequest));
	}

	public static void getPhone(HttpServletRequest httpServletRequest)
		throws Exception {

		long phoneId = ParamUtil.getLong(httpServletRequest, "phoneId");

		Phone phone = null;

		if (phoneId > 0) {
			phone = PhoneServiceUtil.getPhone(phoneId);
		}

		httpServletRequest.setAttribute(WebKeys.PHONE, phone);
	}

	public static void getPhone(PortletRequest portletRequest)
		throws Exception {

		getPhone(PortalUtil.getHttpServletRequest(portletRequest));
	}

	public static void getWebsite(HttpServletRequest httpServletRequest)
		throws Exception {

		long websiteId = ParamUtil.getLong(httpServletRequest, "websiteId");

		Website website = null;

		if (websiteId > 0) {
			website = WebsiteServiceUtil.getWebsite(websiteId);
		}

		httpServletRequest.setAttribute(WebKeys.WEBSITE, website);
	}

	public static void getWebsite(PortletRequest portletRequest)
		throws Exception {

		getWebsite(PortalUtil.getHttpServletRequest(portletRequest));
	}

}