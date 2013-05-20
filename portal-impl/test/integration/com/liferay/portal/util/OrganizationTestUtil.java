/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.model.Address;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.ListType;
import com.liferay.portal.model.ListTypeConstants;
import com.liferay.portal.model.OrgLabor;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.User;
import com.liferay.portal.model.Website;
import com.liferay.portal.service.AddressLocalServiceUtil;
import com.liferay.portal.service.EmailAddressLocalServiceUtil;
import com.liferay.portal.service.ListTypeServiceUtil;
import com.liferay.portal.service.OrgLaborLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.PasswordPolicyRelLocalServiceUtil;
import com.liferay.portal.service.PhoneLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.WebsiteLocalServiceUtil;
import com.liferay.portlet.passwordpoliciesadmin.util.PasswordPolicyTestUtil;

import java.util.List;

/**
 * @author Alberto Chaparro
 */
public class OrganizationTestUtil {

	public static Address addAddress(Organization organization)
		throws Exception {

		List<ListType> listTypeIds = ListTypeServiceUtil.getListTypes(
			ListTypeConstants.ORGANIZATION_ADDRESS);

		return AddressLocalServiceUtil.addAddress(
			organization.getUserId(), organization.getModelClassName(),
			organization.getPrimaryKey(), ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString(),
			ServiceTestUtil.nextLong(), ServiceTestUtil.randomLong(),
			listTypeIds.get(0).getListTypeId(), false, false, null);
	}

	public static EmailAddress addEmailAddress(Organization organization)
		throws Exception {

		List<ListType> listTypeIds = ListTypeServiceUtil.getListTypes(
			ListTypeConstants.ORGANIZATION_EMAIL_ADDRESS);

		return EmailAddressLocalServiceUtil.addEmailAddress(
			organization.getUserId(), organization.getModelClassName(),
			organization.getPrimaryKey(), "test@liferay.com",
			listTypeIds.get(0).getListTypeId(), false, null);
	}

	public static Organization addOrganization() throws Exception {
		return addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			ServiceTestUtil.randomString(), false);
	}

	public static Organization addOrganization(boolean site) throws Exception {
		return addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			ServiceTestUtil.randomString(), site);
	}

	public static Organization addOrganization(
			long parentOrganizationId, String name, boolean site)
		throws Exception {

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), false, null);

		return OrganizationLocalServiceUtil.addOrganization(
			user.getUserId(), parentOrganizationId, name, site);
	}

	public static OrgLabor addOrgLabor(Organization organization)
		throws Exception {

		List<ListType> listTypeIds = ListTypeServiceUtil.getListTypes(
			ListTypeConstants.ORGANIZATION_SERVICE);

		return OrgLaborLocalServiceUtil.addOrgLabor(
			organization.getOrganizationId(),
			listTypeIds.get(0).getListTypeId(), ServiceTestUtil.nextInt(),
			ServiceTestUtil.nextInt(), ServiceTestUtil.nextInt(),
			ServiceTestUtil.nextInt(), ServiceTestUtil.nextInt(),
			ServiceTestUtil.nextInt(), ServiceTestUtil.nextInt(),
			ServiceTestUtil.nextInt(), ServiceTestUtil.nextInt(),
			ServiceTestUtil.nextInt(), ServiceTestUtil.nextInt(),
			ServiceTestUtil.nextInt(), ServiceTestUtil.nextInt(),
			ServiceTestUtil.nextInt());
	}

	public static PasswordPolicy addPasswordPolicyRel(
			Organization organization, ServiceContext serviceContext)
		throws Exception {

		PasswordPolicy passwordPolicy =
			PasswordPolicyTestUtil.addPasswordPolicy(serviceContext);

		PasswordPolicyRelLocalServiceUtil.addPasswordPolicyRel(
			passwordPolicy.getPasswordPolicyId(),
			organization.getModelClassName(), organization.getPrimaryKey());

		return passwordPolicy;
	}

	public static Phone addPhone(Organization organization) throws Exception {

		List<ListType> listTypeIds = ListTypeServiceUtil.getListTypes(
			ListTypeConstants.ORGANIZATION_PHONE);

		return PhoneLocalServiceUtil.addPhone(
			organization.getUserId(), organization.getModelClassName(),
			organization.getPrimaryKey(), "0000000000", "000",
			listTypeIds.get(0).getListTypeId(), false, null);
	}

	public static Website addWebsite(Organization organization)
		throws Exception {

		List<ListType> listTypeIds = ListTypeServiceUtil.getListTypes(
			ListTypeConstants.ORGANIZATION_WEBSITE);

		return WebsiteLocalServiceUtil.addWebsite(
			organization.getUserId(), organization.getModelClassName(),
			organization.getPrimaryKey(), "http://www.test.com",
			listTypeIds.get(0).getListTypeId(), false, null);
	}

}