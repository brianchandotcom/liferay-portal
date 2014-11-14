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

package com.liferay.portlet.usersadmin.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.Country;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.OrgLabor;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.Region;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.UserGroupGroupRole;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.model.Website;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.CountryServiceUtil;
import com.liferay.portal.service.RegionServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;

import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Julio Camarero
 */
@ProviderType
public interface UsersAdmin {

	public static final String CUSTOM_QUESTION = "write-my-own-question";

	public static final Accessor<Organization, String>
		ORGANIZATION_COUNTRY_NAME_ACCESSOR =
			new Accessor<Organization, String>() {

			@Override
			public String get(Organization organization) {
				Address address = organization.getAddress();

				Country country = address.getCountry();

				String countryName = country.getName(
					LocaleThreadLocal.getThemeDisplayLocale());

				if (Validator.isNull(countryName)) {
					country = CountryServiceUtil.fetchCountry(
						organization.getCountryId());

					if (country != null) {
						countryName = country.getName(
							LocaleThreadLocal.getThemeDisplayLocale());
					}
				}

				return countryName;
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<Organization> getTypeClass() {
				return Organization.class;
			}

		};

	public static final Accessor<Organization, String>
		ORGANIZATION_REGION_NAME_ACCESSOR =
			new Accessor<Organization, String>() {

			@Override
			public String get(Organization organization) {
				Address address = organization.getAddress();

				Region region = address.getRegion();

				String regionName = region.getName();

				if (Validator.isNull(regionName)) {
					region = RegionServiceUtil.fetchRegion(
						organization.getRegionId());

					if (region != null) {
						regionName = LanguageUtil.get(
							LocaleThreadLocal.getThemeDisplayLocale(),
							region.getName());
					}
				}

				return regionName;
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<Organization> getTypeClass() {
				return Organization.class;
			}

		};

	public static final Accessor<UserGroupGroupRole, String>
		USER_GROUP_GROUP_ROLE_TITLE_ACCESSOR =
			new Accessor<UserGroupGroupRole, String>() {

				@Override
				public String get(UserGroupGroupRole userGroupGroupRole) {
					Role role = RoleLocalServiceUtil.fetchRole(
						userGroupGroupRole.getRoleId());

					if (role == null) {
						return StringPool.BLANK;
					}

					return role.getTitle(
						LocaleThreadLocal.getThemeDisplayLocale());
				}

				@Override
				public Class<String> getAttributeClass() {
					return String.class;
				}

				@Override
				public Class<UserGroupGroupRole> getTypeClass() {
					return UserGroupGroupRole.class;
				}

			};

	public static final Accessor<UserGroupRole, String>
		USER_GROUP_ROLE_TITLE_ACCESSOR =
			new Accessor<UserGroupRole, String>() {

				@Override
				public String get(UserGroupRole userGroupRole) {
					Role role = RoleLocalServiceUtil.fetchRole(
						userGroupRole.getRoleId());

					if (role == null) {
						return StringPool.BLANK;
					}

					return role.getTitle(
						LocaleThreadLocal.getThemeDisplayLocale());
				}

				@Override
				public Class<String> getAttributeClass() {
					return String.class;
				}

				@Override
				public Class<UserGroupRole> getTypeClass() {
					return UserGroupRole.class;
				}

			};

	public void addPortletBreadcrumbEntries(
			Organization organization, HttpServletRequest request,
			RenderResponse renderResponse)
		throws Exception;

	public long[] addRequiredRoles(long userId, long[] roleIds)
		throws PortalException;

	public long[] addRequiredRoles(User user, long[] roleIds)
		throws PortalException;

	public List<Role> filterGroupRoles(
			PermissionChecker permissionChecker, long groupId, List<Role> roles)
		throws PortalException;

	public List<Group> filterGroups(
			PermissionChecker permissionChecker, List<Group> groups)
		throws PortalException;

	public List<Organization> filterOrganizations(
			PermissionChecker permissionChecker,
			List<Organization> organizations)
		throws PortalException;

	public List<Role> filterRoles(
		PermissionChecker permissionChecker, List<Role> roles);

	public long[] filterUnsetGroupUserIds(
			PermissionChecker permissionChecker, long groupId, long[] userIds)
		throws PortalException;

	public long[] filterUnsetOrganizationUserIds(
			PermissionChecker permissionChecker, long organizationId,
			long[] userIds)
		throws PortalException;

	public List<UserGroupRole> filterUserGroupRoles(
			PermissionChecker permissionChecker,
			List<UserGroupRole> userGroupRoles)
		throws PortalException;

	public List<UserGroup> filterUserGroups(
		PermissionChecker permissionChecker, List<UserGroup> userGroups);

	public List<Address> getAddresses(ActionRequest actionRequest);

	public List<Address> getAddresses(
		ActionRequest actionRequest, List<Address> defaultAddresses);

	public List<EmailAddress> getEmailAddresses(ActionRequest actionRequest);

	public List<EmailAddress> getEmailAddresses(
		ActionRequest actionRequest, List<EmailAddress> defaultEmailAddresses);

	public OrderByComparator<Group> getGroupOrderByComparator(
		String orderByCol, String orderByType);

	public Long[] getOrganizationIds(List<Organization> organizations);

	public OrderByComparator<Organization> getOrganizationOrderByComparator(
		String orderByCol, String orderByType);

	public List<Organization> getOrganizations(Hits hits)
		throws PortalException;

	public List<OrgLabor> getOrgLabors(ActionRequest actionRequest);

	public List<Phone> getPhones(ActionRequest actionRequest);

	public List<Phone> getPhones(
		ActionRequest actionRequest, List<Phone> defaultPhones);

	public OrderByComparator<Role> getRoleOrderByComparator(
		String orderByCol, String orderByType);

	public <T> String getUserColumnText(
		Locale locale, List<? extends T> list, Accessor<T, String> accessor,
		int count);

	public OrderByComparator<UserGroup> getUserGroupOrderByComparator(
		String orderByCol, String orderByType);

	public List<UserGroupRole> getUserGroupRoles(PortletRequest portletRequest)
		throws PortalException;

	public List<UserGroup> getUserGroups(Hits hits) throws PortalException;

	public OrderByComparator<User> getUserOrderByComparator(
		String orderByCol, String orderByType);

	public List<User> getUsers(Hits hits) throws PortalException;

	public List<Website> getWebsites(ActionRequest actionRequest);

	public List<Website> getWebsites(
		ActionRequest actionRequest, List<Website> defaultWebsites);

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #hasUpdateFieldPermission(PermissionChecker, User, User,
	 *             String)}
	 */
	@Deprecated
	public boolean hasUpdateEmailAddress(
			PermissionChecker permissionChecker, User user)
		throws PortalException;

	public boolean hasUpdateFieldPermission(
			PermissionChecker permissionChecker, User updatingUser,
			User updatedUser, String field)
		throws PortalException;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #hasUpdateFieldPermission(PermissionChecker, User, User,
	 *             String)}
	 */
	@Deprecated
	public boolean hasUpdateFieldPermission(User user, String field)
		throws PortalException;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #hasUpdateFieldPermission(PermissionChecker, User, User,
	 *             String)}
	 */
	@Deprecated
	public boolean hasUpdateScreenName(
			PermissionChecker permissionChecker, User user)
		throws PortalException;

	public long[] removeRequiredRoles(long userId, long[] roleIds)
		throws PortalException;

	public long[] removeRequiredRoles(User user, long[] roleIds)
		throws PortalException;

	public void updateAddresses(
			String className, long classPK, List<Address> addresses)
		throws PortalException;

	public void updateEmailAddresses(
			String className, long classPK, List<EmailAddress> emailAddresses)
		throws PortalException;

	public void updateOrgLabors(long classPK, List<OrgLabor> orgLabors)
		throws PortalException;

	public void updatePhones(String className, long classPK, List<Phone> phones)
		throws PortalException;

	public void updateWebsites(
			String className, long classPK, List<Website> websites)
		throws PortalException;

}