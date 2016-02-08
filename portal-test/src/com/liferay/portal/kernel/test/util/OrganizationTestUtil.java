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

package com.liferay.portal.kernel.test.util;

import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.OrgLabor;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.service.AddressLocalServiceUtil;
import com.liferay.portal.kernel.service.EmailAddressLocalServiceUtil;
import com.liferay.portal.kernel.service.ListTypeServiceUtil;
import com.liferay.portal.kernel.service.OrgLaborLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.PasswordPolicyRelLocalServiceUtil;
import com.liferay.portal.kernel.service.PhoneLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WebsiteLocalServiceUtil;
import com.liferay.portlet.passwordpoliciesadmin.util.test.PasswordPolicyTestUtil;

import java.util.List;

/**
 * @author Alberto Chaparro
 */
public class OrganizationTestUtil {

	public static Address addAddress(Organization organization)
		throws Exception {

		return AddressLocalServiceUtil.addAddress(
			organization.getUserId(), organization.getModelClassName(),
			organization.getOrganizationId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.nextLong(), RandomTestUtil.randomLong(),
			_getListTypeId(ListTypeConstants.ORGANIZATION_ADDRESS), false,
			false, new ServiceContext());
	}

	public static EmailAddress addEmailAddress(Organization organization)
		throws Exception {

		return EmailAddressLocalServiceUtil.addEmailAddress(
			organization.getUserId(), organization.getModelClassName(),
			organization.getOrganizationId(), "test@liferay.com",
			_getListTypeId(ListTypeConstants.ORGANIZATION_EMAIL_ADDRESS), false,
			new ServiceContext());
	}

	public static Organization addOrganization() throws Exception {
		return addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			RandomTestUtil.randomString(), false);
	}

	public static Organization addOrganization(boolean site) throws Exception {
		return addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			RandomTestUtil.randomString(), site);
	}

	public static Organization addOrganization(
			long parentOrganizationId, String name, boolean site)
		throws Exception {

		User user = UserTestUtil.addUser();

		return OrganizationLocalServiceUtil.addOrganization(
			user.getUserId(), parentOrganizationId, name, site);
	}

	public static OrgLabor addOrgLabor(Organization organization)
		throws Exception {

		return OrgLaborLocalServiceUtil.addOrgLabor(
			organization.getOrganizationId(),
			_getListTypeId(ListTypeConstants.ORGANIZATION_SERVICE),
			RandomTestUtil.nextInt(), RandomTestUtil.nextInt(),
			RandomTestUtil.nextInt(), RandomTestUtil.nextInt(),
			RandomTestUtil.nextInt(), RandomTestUtil.nextInt(),
			RandomTestUtil.nextInt(), RandomTestUtil.nextInt(),
			RandomTestUtil.nextInt(), RandomTestUtil.nextInt(),
			RandomTestUtil.nextInt(), RandomTestUtil.nextInt(),
			RandomTestUtil.nextInt(), RandomTestUtil.nextInt());
	}

	public static PasswordPolicy addPasswordPolicyRel(
			Organization organization, ServiceContext serviceContext)
		throws Exception {

		PasswordPolicy passwordPolicy =
			PasswordPolicyTestUtil.addPasswordPolicy(serviceContext);

		PasswordPolicyRelLocalServiceUtil.addPasswordPolicyRel(
			passwordPolicy.getPasswordPolicyId(),
			organization.getModelClassName(), organization.getOrganizationId());

		return passwordPolicy;
	}

	public static Phone addPhone(Organization organization) throws Exception {
		return PhoneLocalServiceUtil.addPhone(
			organization.getUserId(), organization.getModelClassName(),
			organization.getOrganizationId(), "0000000000", "000",
			_getListTypeId(ListTypeConstants.ORGANIZATION_PHONE), false,
			new ServiceContext());
	}

	public static Organization addSite(Organization organization)
		throws Exception {

		return OrganizationLocalServiceUtil.updateOrganization(
			organization.getCompanyId(), organization.getOrganizationId(),
			organization.getParentOrganizationId(), organization.getName(),
			organization.getType(), organization.getRegionId(),
			organization.getCountryId(), organization.getStatusId(),
			organization.getComments(), false, null, true, null);
	}

	public static Website addWebsite(Organization organization)
		throws Exception {

		return WebsiteLocalServiceUtil.addWebsite(
			organization.getUserId(), organization.getModelClassName(),
			organization.getOrganizationId(), "http://www.test.com",
			_getListTypeId(ListTypeConstants.ORGANIZATION_WEBSITE), false,
			new ServiceContext());
	}

	private static long _getListTypeId(String type) throws Exception {
		List<ListType> listTypes = ListTypeServiceUtil.getListTypes(type);

		ListType listType = listTypes.get(0);

		return listType.getListTypeId();
	}

}