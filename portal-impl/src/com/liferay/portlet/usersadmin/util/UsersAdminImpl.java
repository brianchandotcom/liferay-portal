/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.usersadmin.util;

import com.liferay.portal.NoSuchOrganizationException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.OrgLabor;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.model.Website;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.AddressLocalServiceUtil;
import com.liferay.portal.service.AddressServiceUtil;
import com.liferay.portal.service.EmailAddressLocalServiceUtil;
import com.liferay.portal.service.EmailAddressServiceUtil;
import com.liferay.portal.service.OrgLaborLocalServiceUtil;
import com.liferay.portal.service.OrgLaborServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.PhoneLocalServiceUtil;
import com.liferay.portal.service.PhoneServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.WebsiteLocalServiceUtil;
import com.liferay.portal.service.WebsiteServiceUtil;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.service.permission.RolePermissionUtil;
import com.liferay.portal.service.permission.UserGroupPermissionUtil;
import com.liferay.portal.service.permission.UserGroupRolePermissionUtil;
import com.liferay.portal.service.persistence.UserGroupRolePK;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.comparator.GroupNameComparator;
import com.liferay.portal.util.comparator.GroupTypeComparator;
import com.liferay.portal.util.comparator.OrganizationNameComparator;
import com.liferay.portal.util.comparator.OrganizationTypeComparator;
import com.liferay.portal.util.comparator.RoleDescriptionComparator;
import com.liferay.portal.util.comparator.RoleNameComparator;
import com.liferay.portal.util.comparator.RoleTypeComparator;
import com.liferay.portal.util.comparator.UserEmailAddressComparator;
import com.liferay.portal.util.comparator.UserFirstNameComparator;
import com.liferay.portal.util.comparator.UserGroupDescriptionComparator;
import com.liferay.portal.util.comparator.UserGroupNameComparator;
import com.liferay.portal.util.comparator.UserJobTitleComparator;
import com.liferay.portal.util.comparator.UserLastNameComparator;
import com.liferay.portal.util.comparator.UserScreenNameComparator;
import com.liferay.util.UniqueList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Julio Camarero
 */
public class UsersAdminImpl implements UsersAdmin {

	public void addPortletBreadcrumbEntries(
			Organization organization, HttpServletRequest request,
			RenderResponse renderResponse)
		throws Exception {

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("struts_action", "/users_admin/view");

		List<Organization> ancestorOrganizations = organization.getAncestors();

		Collections.reverse(ancestorOrganizations);

		for (Organization ancestorOrganization : ancestorOrganizations) {
			portletURL.setParameter(
				"organizationId",
				String.valueOf(ancestorOrganization.getOrganizationId()));

			PortalUtil.addPortletBreadcrumbEntry(
				request, ancestorOrganization.getName(), portletURL.toString());
		}

		portletURL.setParameter(
			"organizationId", String.valueOf(organization.getOrganizationId()));

		PortalUtil.addPortletBreadcrumbEntry(
			request, organization.getName(), portletURL.toString());
	}

	public long[] addRequiredRoles(long userId, long[] roleIds)
		throws PortalException, SystemException {

		User user = UserLocalServiceUtil.getUser(userId);

		return addRequiredRoles(user, roleIds);
	}

	public long[] addRequiredRoles(User user, long[] roleIds)
		throws PortalException, SystemException {

		if (user.isDefaultUser()) {
			return removeRequiredRoles(user, roleIds);
		}

		Role role = RoleLocalServiceUtil.getRole(
			user.getCompanyId(), RoleConstants.USER);

		if (!ArrayUtil.contains(roleIds, role.getRoleId())) {
			roleIds = ArrayUtil.append(roleIds, role.getRoleId());
		}

		return roleIds;
	}

	public List<Role> filterGroupRoles(
			PermissionChecker permissionChecker, long groupId, List<Role> roles)
		throws PortalException, SystemException {

		List<Role> filteredGroupRoles = ListUtil.copy(roles);

		Iterator<Role> itr = filteredGroupRoles.iterator();

		while (itr.hasNext()) {
			Role groupRole = itr.next();

			String name = groupRole.getName();

			if (name.equals(RoleConstants.ORGANIZATION_USER) ||
				name.equals(RoleConstants.SITE_MEMBER)) {

				itr.remove();
			}
		}

		if (permissionChecker.isCompanyAdmin() ||
			permissionChecker.isGroupOwner(groupId)) {

			return filteredGroupRoles;
		}

		itr = filteredGroupRoles.iterator();

		while (itr.hasNext()) {
			Role groupRole = itr.next();

			String groupRoleName = groupRole.getName();

			if (groupRoleName.equals(
					RoleConstants.ORGANIZATION_ADMINISTRATOR) ||
				groupRoleName.equals(RoleConstants.ORGANIZATION_OWNER) ||
				groupRoleName.equals(RoleConstants.SITE_ADMINISTRATOR) ||
				groupRoleName.equals(RoleConstants.SITE_OWNER) ||
				!GroupPermissionUtil.contains(
					permissionChecker, groupId, ActionKeys.ASSIGN_USER_ROLES)) {

				itr.remove();
			}
		}

		return filteredGroupRoles;
	}

	public List<Group> filterGroups(
			PermissionChecker permissionChecker, List<Group> groups)
		throws PortalException, SystemException {

		if (permissionChecker.isCompanyAdmin()) {
			return groups;
		}

		List<Group> filteredGroups = ListUtil.copy(groups);

		Iterator<Group> itr = filteredGroups.iterator();

		while (itr.hasNext()) {
			Group group = itr.next();

			if (!GroupPermissionUtil.contains(
					permissionChecker, group.getGroupId(),
					ActionKeys.ASSIGN_MEMBERS)) {

				itr.remove();
			}
		}

		return filteredGroups;
	}

	public List<Organization> filterOrganizations(
			PermissionChecker permissionChecker,
			List<Organization> organizations)
		throws PortalException, SystemException {

		if (permissionChecker.isCompanyAdmin()) {
			return organizations;
		}

		List<Organization> filteredOrganizations = ListUtil.copy(organizations);

		Iterator<Organization> itr = filteredOrganizations.iterator();

		while (itr.hasNext()) {
			Organization organization = itr.next();

			if (!OrganizationPermissionUtil.contains(
					permissionChecker, organization.getOrganizationId(),
					ActionKeys.ASSIGN_MEMBERS)) {

				itr.remove();
			}
		}

		return filteredOrganizations;
	}

	public List<Role> filterRoles(
		PermissionChecker permissionChecker, List<Role> roles) {

		List<Role> filteredRoles = ListUtil.copy(roles);

		Iterator<Role> itr = filteredRoles.iterator();

		while (itr.hasNext()) {
			Role role = itr.next();

			String name = role.getName();

			if (name.equals(RoleConstants.GUEST) ||
				name.equals(RoleConstants.ORGANIZATION_USER) ||
				name.equals(RoleConstants.OWNER) ||
				name.equals(RoleConstants.SITE_MEMBER) ||
				name.equals(RoleConstants.USER)) {

				itr.remove();
			}
		}

		if (permissionChecker.isCompanyAdmin()) {
			return filteredRoles;
		}

		itr = filteredRoles.iterator();

		while (itr.hasNext()) {
			Role role = itr.next();

			if (!RolePermissionUtil.contains(
					permissionChecker, role.getRoleId(),
					ActionKeys.ASSIGN_MEMBERS)) {

				itr.remove();
			}
		}

		return filteredRoles;
	}

	public List<UserGroupRole> filterUserGroupRoles(
			PermissionChecker permissionChecker,
			List<UserGroupRole> userGroupRoles)
		throws PortalException, SystemException {

		List<UserGroupRole> filteredUserGroupRoles =
			ListUtil.copy(userGroupRoles);

		Iterator<UserGroupRole> itr = filteredUserGroupRoles.iterator();

		while (itr.hasNext()) {
			UserGroupRole userGroupRole = itr.next();

			Role role = userGroupRole.getRole();

			String name = role.getName();

			if (name.equals(RoleConstants.ORGANIZATION_USER) ||
				name.equals(RoleConstants.SITE_MEMBER)) {

				itr.remove();
			}
		}

		if (permissionChecker.isCompanyAdmin()) {
			return filteredUserGroupRoles;
		}

		itr = filteredUserGroupRoles.iterator();

		while (itr.hasNext()) {
			UserGroupRole userGroupRole = itr.next();

			if (!UserGroupRolePermissionUtil.contains(
					permissionChecker, userGroupRole.getGroupId(),
					userGroupRole.getRoleId())) {

				itr.remove();
			}
		}

		return filteredUserGroupRoles;
	}

	public List<UserGroup> filterUserGroups(
		PermissionChecker permissionChecker, List<UserGroup> userGroups) {

		if (permissionChecker.isCompanyAdmin()) {
			return userGroups;
		}

		List<UserGroup> filteredUserGroups = ListUtil.copy(userGroups);

		Iterator<UserGroup> itr = filteredUserGroups.iterator();

		while (itr.hasNext()) {
			UserGroup userGroup = itr.next();

			if (!UserGroupPermissionUtil.contains(
					permissionChecker, userGroup.getUserGroupId(),
					ActionKeys.ASSIGN_MEMBERS)) {

				itr.remove();
			}
		}

		return filteredUserGroups;
	}

	public List<Address> getAddresses(ActionRequest actionRequest) {
		List<Address> addresses = new ArrayList<Address>();

		int[] addressesIndexes = StringUtil.split(
			ParamUtil.getString(actionRequest, "addressesIndexes"), 0);

		int addressPrimary = ParamUtil.getInteger(
			actionRequest, "addressPrimary");

		for (int addressesIndex : addressesIndexes) {
			long addressId = ParamUtil.getLong(
				actionRequest, "addressId" + addressesIndex);

			String street1 = ParamUtil.getString(
				actionRequest, "addressStreet1_" + addressesIndex);
			String street2 = ParamUtil.getString(
				actionRequest, "addressStreet2_" + addressesIndex);
			String street3 = ParamUtil.getString(
				actionRequest, "addressStreet3_" + addressesIndex);
			String city = ParamUtil.getString(
				actionRequest, "addressCity" + addressesIndex);
			String zip = ParamUtil.getString(
				actionRequest, "addressZip" + addressesIndex);

			if (Validator.isNull(street1) && Validator.isNull(street2) &&
				Validator.isNull(street3) && Validator.isNull(city) &&
				Validator.isNull(zip)) {

				continue;
			}

			long regionId = ParamUtil.getLong(
				actionRequest, "addressRegionId" + addressesIndex);
			long countryId = ParamUtil.getLong(
				actionRequest, "addressCountryId" + addressesIndex);
			int typeId = ParamUtil.getInteger(
				actionRequest, "addressTypeId" + addressesIndex);
			boolean mailing = ParamUtil.getBoolean(
				actionRequest, "addressMailing" + addressesIndex);

			boolean primary = false;

			if (addressesIndex == addressPrimary) {
				primary = true;
			}

			Address address = AddressLocalServiceUtil.createAddress(addressId);

			address.setStreet1(street1);
			address.setStreet2(street2);
			address.setStreet3(street3);
			address.setCity(city);
			address.setZip(zip);
			address.setRegionId(regionId);
			address.setCountryId(countryId);
			address.setTypeId(typeId);
			address.setMailing(mailing);
			address.setPrimary(primary);

			addresses.add(address);
		}

		return addresses;
	}

	public List<EmailAddress> getEmailAddresses(ActionRequest actionRequest) {
		List<EmailAddress> emailAddresses = new ArrayList<EmailAddress>();

		int[] emailAddressesIndexes = StringUtil.split(
			ParamUtil.getString(actionRequest, "emailAddressesIndexes"), 0);

		int emailAddressPrimary = ParamUtil.getInteger(
			actionRequest, "emailAddressPrimary");

		for (int emailAddressesIndex : emailAddressesIndexes) {
			long emailAddressId = ParamUtil.getLong(
				actionRequest, "emailAddressId" + emailAddressesIndex);

			String address = ParamUtil.getString(
				actionRequest, "emailAddressAddress" + emailAddressesIndex);

			if (Validator.isNull(address)) {
				continue;
			}

			int typeId = ParamUtil.getInteger(
				actionRequest, "emailAddressTypeId" + emailAddressesIndex);

			boolean primary = false;

			if (emailAddressesIndex == emailAddressPrimary) {
				primary = true;
			}

			EmailAddress emailAddress =
				EmailAddressLocalServiceUtil.createEmailAddress(emailAddressId);

			emailAddress.setAddress(address);
			emailAddress.setTypeId(typeId);
			emailAddress.setPrimary(primary);

			emailAddresses.add(emailAddress);
		}

		return emailAddresses;
	}

	public OrderByComparator getGroupOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = new GroupNameComparator(orderByAsc);
		}
		else if (orderByCol.equals("type")) {
			orderByComparator = new GroupTypeComparator(orderByAsc);
		}
		else {
			orderByComparator = new GroupNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public Long[] getOrganizationIds(List<Organization> organizations) {
		if ((organizations == null) || organizations.isEmpty()) {
			return new Long[0];
		}

		Long[] organizationIds = new Long[organizations.size()];

		for (int i = 0; i < organizations.size(); i++) {
			Organization organization = organizations.get(i);

			organizationIds[i] = new Long(organization.getOrganizationId());
		}

		return organizationIds;
	}

	public OrderByComparator getOrganizationOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = new OrganizationNameComparator(orderByAsc);
		}
		else if (orderByCol.equals("type")) {
			orderByComparator = new OrganizationTypeComparator(orderByAsc);
		}
		else {
			orderByComparator = new OrganizationNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public Tuple getOrganizations(Hits hits)
		throws PortalException, SystemException {

		List<Organization> organizations = new ArrayList<Organization>();
		boolean corruptIndex = false;

		List<Document> documents = hits.toList();

		for (Document document : documents) {
			long organizationId = GetterUtil.getLong(
				document.get(Field.ORGANIZATION_ID));

			try {
				Organization organization =
					OrganizationLocalServiceUtil.getOrganization(
						organizationId);

				organizations.add(organization);
			}
			catch (NoSuchOrganizationException nsoe) {
				corruptIndex = true;

				Indexer indexer = IndexerRegistryUtil.getIndexer(
					Organization.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
		}

		return new Tuple(organizations, corruptIndex);
	}

	public List<OrgLabor> getOrgLabors(ActionRequest actionRequest) {
		List<OrgLabor> orgLabors = new ArrayList<OrgLabor>();

		int[] orgLaborsIndexes = StringUtil.split(
			ParamUtil.getString(actionRequest, "orgLaborsIndexes"), 0);

		for (int orgLaborsIndex : orgLaborsIndexes) {
			long orgLaborId = ParamUtil.getLong(
				actionRequest, "orgLaborId" + orgLaborsIndex);

			int typeId = ParamUtil.getInteger(
				actionRequest, "orgLaborTypeId" + orgLaborsIndex, -1);

			if (typeId == -1) {
				continue;
			}

			int sunOpen = ParamUtil.getInteger(
				actionRequest, "sunOpen" + orgLaborsIndex, -1);
			int sunClose = ParamUtil.getInteger(
				actionRequest, "sunClose" + orgLaborsIndex, -1);
			int monOpen = ParamUtil.getInteger(
				actionRequest, "monOpen" + orgLaborsIndex, -1);
			int monClose = ParamUtil.getInteger(
				actionRequest, "monClose" + orgLaborsIndex, -1);
			int tueOpen = ParamUtil.getInteger(
				actionRequest, "tueOpen" + orgLaborsIndex, -1);
			int tueClose = ParamUtil.getInteger(
				actionRequest, "tueClose" + orgLaborsIndex, -1);
			int wedOpen = ParamUtil.getInteger(
				actionRequest, "wedOpen" + orgLaborsIndex, -1);
			int wedClose = ParamUtil.getInteger(
				actionRequest, "wedClose" + orgLaborsIndex, -1);
			int thuOpen = ParamUtil.getInteger(
				actionRequest, "thuOpen" + orgLaborsIndex, -1);
			int thuClose = ParamUtil.getInteger(
				actionRequest, "thuClose" + orgLaborsIndex, -1);
			int friOpen = ParamUtil.getInteger(
				actionRequest, "friOpen" + orgLaborsIndex, -1);
			int friClose = ParamUtil.getInteger(
				actionRequest, "friClose" + orgLaborsIndex, -1);
			int satOpen = ParamUtil.getInteger(
				actionRequest, "satOpen" + orgLaborsIndex, -1);
			int satClose = ParamUtil.getInteger(
				actionRequest, "satClose" + orgLaborsIndex, -1);

			OrgLabor orgLabor = OrgLaborLocalServiceUtil.createOrgLabor(
				orgLaborId);

			orgLabor.setTypeId(typeId);
			orgLabor.setSunOpen(sunOpen);
			orgLabor.setSunClose(sunClose);
			orgLabor.setMonOpen(monOpen);
			orgLabor.setMonClose(monClose);
			orgLabor.setTueOpen(tueOpen);
			orgLabor.setTueClose(tueClose);
			orgLabor.setWedOpen(wedOpen);
			orgLabor.setWedClose(wedClose);
			orgLabor.setThuOpen(thuOpen);
			orgLabor.setThuClose(thuClose);
			orgLabor.setFriOpen(friOpen);
			orgLabor.setFriClose(friClose);
			orgLabor.setSatOpen(satOpen);
			orgLabor.setSatClose(satClose);

			orgLabors.add(orgLabor);
		}

		return orgLabors;
	}

	public List<Phone> getPhones(ActionRequest actionRequest) {
		List<Phone> phones = new ArrayList<Phone>();

		int[] phonesIndexes = StringUtil.split(
			ParamUtil.getString(actionRequest, "phonesIndexes"), 0);

		int phonePrimary = ParamUtil.getInteger(actionRequest, "phonePrimary");

		for (int phonesIndex : phonesIndexes) {
			long phoneId = ParamUtil.getLong(
				actionRequest, "phoneId" + phonesIndex);

			String number = ParamUtil.getString(
				actionRequest, "phoneNumber" + phonesIndex);
			String extension = ParamUtil.getString(
				actionRequest, "phoneExtension" + phonesIndex);

			if (Validator.isNull(number) && Validator.isNull(extension)) {
				continue;
			}

			int typeId = ParamUtil.getInteger(
				actionRequest, "phoneTypeId" + phonesIndex);

			boolean primary = false;

			if (phonesIndex == phonePrimary) {
				primary = true;
			}

			Phone phone = PhoneLocalServiceUtil.createPhone(phoneId);

			phone.setNumber(number);
			phone.setExtension(extension);
			phone.setTypeId(typeId);
			phone.setPrimary(primary);

			phones.add(phone);
		}

		return phones;
	}

	public OrderByComparator getRoleOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = new RoleNameComparator(orderByAsc);
		}
		else if (orderByCol.equals("description")) {
			orderByComparator = new RoleDescriptionComparator(orderByAsc);
		}
		else if (orderByCol.equals("type")) {
			orderByComparator = new RoleTypeComparator(orderByAsc);
		}
		else {
			orderByComparator = new RoleNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public OrderByComparator getUserGroupOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = new UserGroupNameComparator(orderByAsc);
		}
		else if (orderByCol.equals("description")) {
			orderByComparator = new UserGroupDescriptionComparator(orderByAsc);
		}
		else {
			orderByComparator = new UserGroupNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public List<UserGroupRole> getUserGroupRoles(PortletRequest portletRequest)
		throws PortalException, SystemException {

		List<UserGroupRole> userGroupRoles = new UniqueList<UserGroupRole>();

		long[] groupRolesRoleIds= StringUtil.split(ParamUtil.getString(
			portletRequest, "groupRolesRoleIds"), 0L);
		long[] groupRolesGroupIds= StringUtil.split(ParamUtil.getString(
			portletRequest, "groupRolesGroupIds"), 0L);

		if (groupRolesGroupIds.length != groupRolesRoleIds.length) {
			return userGroupRoles;
		}

		User user = PortalUtil.getSelectedUser(portletRequest);

		long userId = 0;

		if (user != null) {
			userId = user.getUserId();
		}

		for (int i = 0; i < groupRolesGroupIds.length; i++) {
			if ((groupRolesGroupIds[i] == 0) ||
				(groupRolesRoleIds[i] == 0)) {

				continue;
			}

			UserGroupRolePK userGroupRolePK = new UserGroupRolePK(
				userId, groupRolesGroupIds[i], groupRolesRoleIds[i]);

			UserGroupRole userGroupRole =
				UserGroupRoleLocalServiceUtil.createUserGroupRole(
					userGroupRolePK);

			userGroupRoles.add(userGroupRole);
		}

		return userGroupRoles;
	}

	public OrderByComparator getUserOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator orderByComparator = null;

		if (orderByCol.equals("email-address")) {
			orderByComparator = new UserEmailAddressComparator(orderByAsc);
		}
		else if (orderByCol.equals("first-name")) {
			orderByComparator = new UserFirstNameComparator(orderByAsc);
		}
		else if (orderByCol.equals("job-title")) {
			orderByComparator = new UserJobTitleComparator(orderByAsc);
		}
		else if (orderByCol.equals("last-name")) {
			orderByComparator = new UserLastNameComparator(orderByAsc);
		}
		else if (orderByCol.equals("screen-name")) {
			orderByComparator = new UserScreenNameComparator(orderByAsc);
		}
		else {
			orderByComparator = new UserLastNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public Tuple getUsers(Hits hits) throws PortalException, SystemException {
		List<User> users = new ArrayList<User>();
		boolean corruptIndex = false;

		List<Document> documents = hits.toList();

		for (Document document : documents) {
			long userId = GetterUtil.getLong(document.get(Field.USER_ID));

			try {
				User user = UserLocalServiceUtil.getUser(userId);

				users.add(user);
			}
			catch (NoSuchUserException nsue) {
				corruptIndex = true;

				Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
		}

		return new Tuple(users, corruptIndex);
	}

	public List<Website> getWebsites(ActionRequest actionRequest) {
		List<Website> websites = new ArrayList<Website>();

		int[] websitesIndexes = StringUtil.split(
			ParamUtil.getString(actionRequest, "websitesIndexes"), 0);

		int websitePrimary = ParamUtil.getInteger(
			actionRequest, "websitePrimary");

		for (int websitesIndex : websitesIndexes) {
			long websiteId = ParamUtil.getLong(
				actionRequest, "websiteId" + websitesIndex);

			String url = ParamUtil.getString(
				actionRequest, "websiteUrl" + websitesIndex);

			if (Validator.isNull(url)) {
				continue;
			}

			int typeId = ParamUtil.getInteger(
				actionRequest, "websiteTypeId" + websitesIndex);

			boolean primary = false;

			if (websitesIndex == websitePrimary) {
				primary = true;
			}

			Website website = WebsiteLocalServiceUtil.createWebsite(websiteId);

			website.setUrl(url);
			website.setTypeId(typeId);
			website.setPrimary(primary);

			websites.add(website);
		}

		return websites;
	}

	public boolean hasUpdateEmailAddress(
			PermissionChecker permissionChecker, User user)
		throws PortalException, SystemException {

		String[] fieldEditiableUserEmailAddress =
			PropsValues.
				FIELD_EDITABLE_COM_LIFERAY_PORTAL_MODEL_USER_EMAILADDRESS;

		if (ArrayUtil.contains(
				fieldEditiableUserEmailAddress, "administrator") &&
			permissionChecker.isCompanyAdmin()) {

			return true;
		}

		if (ArrayUtil.contains(
				fieldEditiableUserEmailAddress, "user-with-mx") &&
			user.hasCompanyMx()) {

			return true;
		}

		if (ArrayUtil.contains(
				fieldEditiableUserEmailAddress, "user-without-mx") &&
			!user.hasCompanyMx()) {

			return true;
		}

		return false;
	}

	public boolean hasUpdateScreenName(
			PermissionChecker permissionChecker, User user)
		throws PortalException, SystemException {

		String[] fieldEditiableUserScreenName =
			PropsValues.
				FIELD_EDITABLE_COM_LIFERAY_PORTAL_MODEL_USER_SCREENNAME;

		if (ArrayUtil.contains(
				fieldEditiableUserScreenName, "administrator") &&
			permissionChecker.isCompanyAdmin()) {

			return true;
		}

		if (ArrayUtil.contains(
				fieldEditiableUserScreenName, "user-with-mx") &&
			user.hasCompanyMx()) {

			return true;
		}

		if (ArrayUtil.contains(
				fieldEditiableUserScreenName, "user-without-mx") &&
			!user.hasCompanyMx()) {

			return true;
		}

		return false;
	}

	public long[] removeRequiredRoles(long userId, long[] roleIds)
		throws PortalException, SystemException {

		User user = UserLocalServiceUtil.getUser(userId);

		return removeRequiredRoles(user, roleIds);
	}

	public long[] removeRequiredRoles(User user, long[] roleIds)
		throws PortalException, SystemException {

		Role role = RoleLocalServiceUtil.getRole(
			user.getCompanyId(), RoleConstants.USER);

		roleIds = ArrayUtil.remove(roleIds, role.getRoleId());

		return roleIds;
	}

	public void updateAddresses(
			String className, long classPK, List<Address> addresses)
		throws PortalException, SystemException {

		Set<Long> addressIds = new HashSet<Long>();

		for (Address address : addresses) {
			long addressId = address.getAddressId();

			String street1 = address.getStreet1();
			String street2 = address.getStreet2();
			String street3 = address.getStreet3();
			String city = address.getCity();
			String zip = address.getZip();
			long regionId = address.getRegionId();
			long countryId = address.getCountryId();
			int typeId = address.getTypeId();
			boolean mailing = address.isMailing();
			boolean primary = address.isPrimary();

			if (addressId <= 0) {
				address = AddressServiceUtil.addAddress(
					className, classPK, street1, street2, street3, city, zip,
					regionId, countryId, typeId, mailing, primary);

				addressId = address.getAddressId();
			}
			else {
				AddressServiceUtil.updateAddress(
					addressId, street1, street2, street3, city, zip, regionId,
					countryId, typeId, mailing, primary);
			}

			addressIds.add(addressId);
		}

		addresses = AddressServiceUtil.getAddresses(className, classPK);

		for (Address address : addresses) {
			if (!addressIds.contains(address.getAddressId())) {
				AddressServiceUtil.deleteAddress(address.getAddressId());
			}
		}
	}

	public void updateEmailAddresses(
			String className, long classPK, List<EmailAddress> emailAddresses)
		throws PortalException, SystemException {

		Set<Long> emailAddressIds = new HashSet<Long>();

		for (EmailAddress emailAddress : emailAddresses) {
			long emailAddressId = emailAddress.getEmailAddressId();

			String address = emailAddress.getAddress();
			int typeId = emailAddress.getTypeId();
			boolean primary = emailAddress.isPrimary();

			if (emailAddressId <= 0) {
				emailAddress = EmailAddressServiceUtil.addEmailAddress(
					className, classPK, address, typeId, primary);

				emailAddressId = emailAddress.getEmailAddressId();
			}
			else {
				EmailAddressServiceUtil.updateEmailAddress(
					emailAddressId, address, typeId, primary);
			}

			emailAddressIds.add(emailAddressId);
		}

		emailAddresses = EmailAddressServiceUtil.getEmailAddresses(
			className, classPK);

		for (EmailAddress emailAddress : emailAddresses) {
			if (!emailAddressIds.contains(emailAddress.getEmailAddressId())) {
				EmailAddressServiceUtil.deleteEmailAddress(
					emailAddress.getEmailAddressId());
			}
		}
	}

	public void updateOrgLabors(long classPK, List<OrgLabor> orgLabors)
		throws PortalException, SystemException {

		Set<Long> orgLaborsIds = new HashSet<Long>();

		for (OrgLabor orgLabor : orgLabors) {
			long orgLaborId = orgLabor.getOrgLaborId();

			int typeId = orgLabor.getTypeId();
			int sunOpen = orgLabor.getSunOpen();
			int sunClose = orgLabor.getSunClose();
			int monOpen = orgLabor.getMonOpen();
			int monClose = orgLabor.getMonClose();
			int tueOpen = orgLabor.getTueOpen();
			int tueClose = orgLabor.getTueClose();
			int wedOpen = orgLabor.getWedOpen();
			int wedClose = orgLabor.getWedClose();
			int thuOpen = orgLabor.getThuOpen();
			int thuClose = orgLabor.getThuClose();
			int friOpen = orgLabor.getFriOpen();
			int friClose = orgLabor.getFriClose();
			int satOpen = orgLabor.getSatOpen();
			int satClose = orgLabor.getSatClose();

			if (orgLaborId <= 0) {
				orgLabor = OrgLaborServiceUtil.addOrgLabor(
					classPK, typeId, sunOpen, sunClose, monOpen, monClose,
					tueOpen, tueClose, wedOpen, wedClose, thuOpen, thuClose,
					friOpen, friClose, satOpen, satClose);

				orgLaborId = orgLabor.getOrgLaborId();
			}
			else {
				OrgLaborServiceUtil.updateOrgLabor(
					orgLaborId, typeId, sunOpen, sunClose, monOpen, monClose,
					tueOpen, tueClose, wedOpen, wedClose, thuOpen, thuClose,
					friOpen, friClose, satOpen, satClose);
			}

			orgLaborsIds.add(orgLaborId);
		}

		orgLabors = OrgLaborServiceUtil.getOrgLabors(classPK);

		for (OrgLabor orgLabor : orgLabors) {
			if (!orgLaborsIds.contains(orgLabor.getOrgLaborId())) {
				OrgLaborServiceUtil.deleteOrgLabor(orgLabor.getOrgLaborId());
			}
		}
	}

	public void updatePhones(String className, long classPK, List<Phone> phones)
		throws PortalException, SystemException {

		Set<Long> phoneIds = new HashSet<Long>();

		for (Phone phone : phones) {
			long phoneId = phone.getPhoneId();

			String number = phone.getNumber();
			String extension = phone.getExtension();
			int typeId = phone.getTypeId();
			boolean primary = phone.isPrimary();

			if (phoneId <= 0) {
				phone = PhoneServiceUtil.addPhone(
					className, classPK, number, extension, typeId, primary);

				phoneId = phone.getPhoneId();
			}
			else {
				PhoneServiceUtil.updatePhone(
					phoneId, number, extension, typeId, primary);
			}

			phoneIds.add(phoneId);
		}

		phones = PhoneServiceUtil.getPhones(className, classPK);

		for (Phone phone : phones) {
			if (!phoneIds.contains(phone.getPhoneId())) {
				PhoneServiceUtil.deletePhone(phone.getPhoneId());
			}
		}
	}

	public void updateWebsites(
			String className, long classPK, List<Website> websites)
		throws PortalException, SystemException {

		Set<Long> websiteIds = new HashSet<Long>();

		for (Website website : websites) {
			long websiteId = website.getWebsiteId();

			String url = website.getUrl();
			int typeId = website.getTypeId();
			boolean primary = website.isPrimary();

			if (websiteId <= 0) {
				website = WebsiteServiceUtil.addWebsite(
					className, classPK, url, typeId, primary);

				websiteId = website.getWebsiteId();
			}
			else {
				WebsiteServiceUtil.updateWebsite(
					websiteId, url, typeId, primary);
			}

			websiteIds.add(websiteId);
		}

		websites = WebsiteServiceUtil.getWebsites(className, classPK);

		for (Website website : websites) {
			if (!websiteIds.contains(website.getWebsiteId())) {
				WebsiteServiceUtil.deleteWebsite(website.getWebsiteId());
			}
		}
	}

}