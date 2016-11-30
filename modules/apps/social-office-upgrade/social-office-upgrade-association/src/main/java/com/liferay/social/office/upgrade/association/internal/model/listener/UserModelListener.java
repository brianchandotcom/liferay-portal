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

package com.liferay.social.office.upgrade.association.internal.model.listener;

import com.liferay.portal.kernel.cache.thread.local.Lifecycle;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.persistence.impl.TableMapper;
import com.liferay.social.office.upgrade.association.internal.constants.RoleConstants;
import com.liferay.social.office.upgrade.association.internal.constants.SocialOfficeConstants;
import com.liferay.social.office.upgrade.association.internal.util.LayoutSetPrototypeUtil;
import com.liferay.social.office.upgrade.association.internal.util.SocialOfficeUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jonathan Lee
 * @author Eudaldo Alonso
 * @author Sergio González
 */
@Component(immediate = true, service = ModelListener.class)
public class UserModelListener extends BaseModelListener<User> {

	@Override
	public void onAfterAddAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		try {
			User user = userLocalService.getUser((Long)classPK);

			if (associationClassName.equals(Group.class.getName()) ||
				associationClassName.equals(Organization.class.getName()) ||
				associationClassName.equals(UserGroup.class.getName())) {

				Role role = roleLocalService.fetchRole(
					user.getCompanyId(), RoleConstants.SOCIAL_OFFICE_USER);

				if (role == null) {
					return;
				}

				Group group = null;

				if (associationClassName.equals(Group.class.getName())) {
					group = groupLocalService.getGroup(
						(Long)associationClassPK);
				}
				else if (associationClassName.equals(
							Organization.class.getName())) {

					group = groupLocalService.getOrganizationGroup(
						user.getCompanyId(), (Long)associationClassPK);
				}
				else if (associationClassName.equals(
							UserGroup.class.getName())) {

					group = groupLocalService.getUserGroupGroup(
						user.getCompanyId(), (Long)associationClassPK);
				}

				if (groupLocalService.hasRoleGroup(
						role.getRoleId(), group.getGroupId())) {

					enableSocialOffice(user.getGroup());
				}
			}
			else if (associationClassName.equals(Role.class.getName())) {
				Role role = roleLocalService.getRole((Long)associationClassPK);

				String name = role.getName();

				if (name.equals(RoleConstants.SOCIAL_OFFICE_USER)) {
					enableSocialOffice(user.getGroup());
				}
			}
		}
		catch (NoSuchGroupException nsge) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsge, nsge);
			}
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Override
	public void onAfterRemoveAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		try {
			User user = userLocalService.getUser((Long)classPK);

			FinderCacheUtil.clearCache(
				_MAPPING_TABLE_USERS_ROLES_NAME_LEFT_TO_RIGHT);
			FinderCacheUtil.clearCache(
				_MAPPING_TABLE_USERS_ROLES_NAME_RIGHT_TO_LEFT);

			ThreadLocalCacheManager.clearAll(Lifecycle.REQUEST);

			if (userLocalService.hasRoleUser(
					user.getCompanyId(), RoleConstants.SOCIAL_OFFICE_USER,
					user.getUserId(), true)) {

				return;
			}

			if (associationClassName.equals(Group.class.getName()) ||
				associationClassName.equals(Organization.class.getName()) ||
				associationClassName.equals(UserGroup.class.getName())) {

				Role role = roleLocalService.getRole(
					user.getCompanyId(), RoleConstants.SOCIAL_OFFICE_USER);

				Group group = null;

				if (associationClassName.equals(Group.class.getName())) {
					group = groupLocalService.getGroup(
						(Long)associationClassPK);
				}
				else if (associationClassName.equals(
							Organization.class.getName())) {

					group = groupLocalService.getOrganizationGroup(
						user.getCompanyId(), (Long)associationClassPK);
				}
				else if (associationClassName.equals(
							UserGroup.class.getName())) {

					group = groupLocalService.getUserGroupGroup(
						user.getCompanyId(), (Long)associationClassPK);
				}

				if (groupLocalService.hasRoleGroup(
						role.getRoleId(), group.getGroupId())) {

					disableSocialOffice(user.getGroup());
				}
			}
			else if (associationClassName.equals(Role.class.getName())) {
				Role role = roleLocalService.getRole((Long)associationClassPK);

				String name = role.getName();

				if (name.equals(RoleConstants.SOCIAL_OFFICE_USER)) {
					disableSocialOffice(user.getGroup());
				}
			}
		}
		catch (NoSuchGroupException nsge) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsge, nsge);
			}
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	protected void disableSocialOffice(Group group) throws Exception {
		if (!socialOfficeUtil.isSocialOfficeGroup(group.getGroupId())) {
			return;
		}

		layoutSetPrototypeUtil.removeLayoutSetPrototype(
			group, false,
			SocialOfficeConstants.LAYOUT_SET_PROTOTYPE_KEY_USER_PUBLIC);
		layoutSetPrototypeUtil.removeLayoutSetPrototype(
			group, true,
			SocialOfficeConstants.LAYOUT_SET_PROTOTYPE_KEY_USER_PRIVATE);

		socialOfficeUtil.disableSocialOffice(group);
	}

	protected void enableSocialOffice(Group group) throws Exception {
		if (socialOfficeUtil.isSocialOfficeGroup(group.getGroupId())) {
			return;
		}

		layoutSetPrototypeUtil.updateLayoutSetPrototype(
			group, false,
			SocialOfficeConstants.LAYOUT_SET_PROTOTYPE_KEY_USER_PUBLIC);
		layoutSetPrototypeUtil.updateLayoutSetPrototype(
			group, true,
			SocialOfficeConstants.LAYOUT_SET_PROTOTYPE_KEY_USER_PRIVATE);

		socialOfficeUtil.enableSocialOffice(group);
	}

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected LayoutSetPrototypeUtil layoutSetPrototypeUtil;

	@Reference
	protected RoleLocalService roleLocalService;

	@Reference
	protected SocialOfficeUtil socialOfficeUtil;

	@Reference
	protected UserLocalService userLocalService;

	/**
	 * {@link
	 * com.liferay.portal.kernel.service.persistence.impl.TableMapperImpl}
	 */
	private static final String _MAPPING_TABLE_USERS_ROLES_NAME_LEFT_TO_RIGHT =
		TableMapper.class.getName() + "-Users_Roles-LeftToRight";

	/**
	 * {@link
	 * com.liferay.portal.kernel.service.persistence.impl.TableMapperImpl}
	 */
	private static final String _MAPPING_TABLE_USERS_ROLES_NAME_RIGHT_TO_LEFT =
		TableMapper.class.getName() + "-Users_Roles-RightToLeft";

	private static final Log _log = LogFactoryUtil.getLog(
		UserModelListener.class);

}