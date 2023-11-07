/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.membershippolicy;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.security.membershippolicy.SiteMembershipPolicy;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
public class SiteMembershipPolicyUtil {

	public static void checkMembership(
			long[] userIds, long[] addGroupIds, long[] removeGroupIds)
		throws PortalException {

		getSiteMembershipPolicy().checkMembership(
			userIds, addGroupIds, removeGroupIds);
	}

	public static void checkRoles(
			List<UserGroupRole> addUserGroupRoles,
			List<UserGroupRole> removeUserGroupRoles)
		throws PortalException {

		getSiteMembershipPolicy().checkRoles(
			addUserGroupRoles, removeUserGroupRoles);
	}

	public static SiteMembershipPolicy getSiteMembershipPolicy() {
		ServiceTracker<SiteMembershipPolicy, SiteMembershipPolicy>
			serviceTracker = _serviceTrackerDCLSingleton.getSingleton(
				SiteMembershipPolicyUtil::_createServiceTracker);

		return serviceTracker.getService();
	}

	public static boolean isMembershipAllowed(long userId, long groupId)
		throws PortalException {

		return getSiteMembershipPolicy().isMembershipAllowed(userId, groupId);
	}

	public static boolean isMembershipProtected(
			PermissionChecker permissionChecker, long userId, long groupId)
		throws PortalException {

		return getSiteMembershipPolicy().isMembershipProtected(
			permissionChecker, userId, groupId);
	}

	public static boolean isMembershipRequired(long userId, long groupId)
		throws PortalException {

		return getSiteMembershipPolicy().isMembershipRequired(userId, groupId);
	}

	public static boolean isRoleAllowed(long userId, long groupId, long roleId)
		throws PortalException {

		return getSiteMembershipPolicy().isRoleAllowed(userId, groupId, roleId);
	}

	public static boolean isRoleProtected(
			PermissionChecker permissionChecker, long userId, long groupId,
			long roleId)
		throws PortalException {

		return getSiteMembershipPolicy().isRoleProtected(
			permissionChecker, userId, groupId, roleId);
	}

	public static boolean isRoleRequired(long userId, long groupId, long roleId)
		throws PortalException {

		return getSiteMembershipPolicy().isRoleRequired(
			userId, groupId, roleId);
	}

	public static void propagateMembership(
			long[] userIds, long[] addGroupIds, long[] removeGroupIds)
		throws PortalException {

		getSiteMembershipPolicy().propagateMembership(
			userIds, addGroupIds, removeGroupIds);
	}

	public static void propagateRoles(
			List<UserGroupRole> addUserGroupRoles,
			List<UserGroupRole> removeUserGroupRoles)
		throws PortalException {

		getSiteMembershipPolicy().propagateRoles(
			addUserGroupRoles, removeUserGroupRoles);
	}

	public static void verifyPolicy() throws PortalException {
		getSiteMembershipPolicy().verifyPolicy();
	}

	public static void verifyPolicy(Group group) throws PortalException {
		getSiteMembershipPolicy().verifyPolicy(group);
	}

	public static void verifyPolicy(
			Group group, Group oldGroup, List<AssetCategory> oldAssetCategories,
			List<AssetTag> oldAssetTags,
			Map<String, Serializable> oldExpandoAttributes,
			UnicodeProperties oldTypeSettingsUnicodeProperties)
		throws PortalException {

		getSiteMembershipPolicy().verifyPolicy(
			group, oldGroup, oldAssetCategories, oldAssetTags,
			oldExpandoAttributes, oldTypeSettingsUnicodeProperties);
	}

	public static void verifyPolicy(Role role) throws PortalException {
		getSiteMembershipPolicy().verifyPolicy(role);
	}

	public static void verifyPolicy(
			Role role, Role oldRole,
			Map<String, Serializable> oldExpandoAttributes)
		throws PortalException {

		getSiteMembershipPolicy().verifyPolicy(
			role, oldRole, oldExpandoAttributes);
	}

	public void destroy() {
		_serviceTrackerDCLSingleton.destroy(ServiceTracker::close);
	}

	private static ServiceTracker<SiteMembershipPolicy, SiteMembershipPolicy>
		_createServiceTracker() {

		ServiceTracker<SiteMembershipPolicy, SiteMembershipPolicy>
			serviceTracker = new ServiceTracker<>(
				_bundleContext, SiteMembershipPolicy.class,
				new SiteMembershipPolicyUtil.
					SiteMembershipPolicyTrackerCustomizer());

		serviceTracker.open();

		return serviceTracker;
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private static final DCLSingleton
		<ServiceTracker<SiteMembershipPolicy, SiteMembershipPolicy>>
			_serviceTrackerDCLSingleton = new DCLSingleton<>();

	private static class SiteMembershipPolicyTrackerCustomizer
		implements ServiceTrackerCustomizer
			<SiteMembershipPolicy, SiteMembershipPolicy> {

		@Override
		public SiteMembershipPolicy addingService(
			ServiceReference<SiteMembershipPolicy> serviceReference) {

			return _bundleContext.getService(serviceReference);
		}

		@Override
		public void modifiedService(
			ServiceReference<SiteMembershipPolicy> serviceReference,
			SiteMembershipPolicy siteMembershipPolicy) {
		}

		@Override
		public void removedService(
			ServiceReference<SiteMembershipPolicy> serviceReference,
			SiteMembershipPolicy siteMembershipPolicy) {

			_bundleContext.ungetService(serviceReference);
		}

	}

}