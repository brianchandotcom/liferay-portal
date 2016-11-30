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

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
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

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jonathan Lee
 * @author Sergio González
 */
@Component(immediate = true, service = ModelListener.class)
public class RoleModelListener extends BaseModelListener<Role> {

	@Override
	public void onAfterAddAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		try {
			Role role = roleLocalService.getRole((Long)classPK);

			String name = role.getName();

			if (!name.equals(RoleConstants.SOCIAL_OFFICE_USER)) {
				return;
			}

			if (!associationClassName.equals(Group.class.getName())) {
				return;
			}

			Group group = groupLocalService.getGroup((Long)associationClassPK);

			List<User> users = null;

			String className = group.getClassName();

			if (className.equals(UserGroup.class.getName())) {
				users = userLocalService.getUserGroupUsers(group.getClassPK());
			}
			else if (className.equals(Organization.class.getName())) {
				users = userLocalService.getOrganizationUsers(
					group.getClassPK());
			}
			else if (className.equals(Group.class.getName())) {
				users = userLocalService.getGroupUsers(group.getClassPK());
			}

			if (users == null) {
				return;
			}

			for (User user : users) {
				Group userGroup = user.getGroup();

				if (socialOfficeUtil.isSocialOfficeGroup(
						userGroup.getGroupId())) {

					continue;
				}

				layoutSetPrototypeUtil.updateLayoutSetPrototype(
					userGroup, false,
					SocialOfficeConstants.LAYOUT_SET_PROTOTYPE_KEY_USER_PUBLIC);
				layoutSetPrototypeUtil.updateLayoutSetPrototype(
					userGroup, true,
					SocialOfficeConstants.
						LAYOUT_SET_PROTOTYPE_KEY_USER_PRIVATE);

				socialOfficeUtil.enableSocialOffice(userGroup);
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
			Role role = roleLocalService.getRole((Long)classPK);

			String name = role.getName();

			if (!name.equals(RoleConstants.SOCIAL_OFFICE_USER)) {
				return;
			}

			if (!associationClassName.equals(Group.class.getName())) {
				return;
			}

			Group group = groupLocalService.getGroup((Long)associationClassPK);

			List<User> users = null;

			String className = group.getClassName();

			if (className.equals(UserGroup.class.getName())) {
				users = userLocalService.getUserGroupUsers(group.getClassPK());
			}
			else if (className.equals(Organization.class.getName())) {
				users = userLocalService.getOrganizationUsers(
					group.getClassPK());
			}
			else if (className.equals(Group.class.getName())) {
				users = userLocalService.getGroupUsers(group.getClassPK());
			}

			if (users == null) {
				return;
			}

			for (User user : users) {
				Group userGroup = user.getGroup();

				if (socialOfficeUtil.isSocialOfficeGroup(
						userGroup.getGroupId())) {

					continue;
				}

				FinderCacheUtil.clearCache(
					_MAPPING_TABLE_USERS_ROLES_NAME_LEFT_TO_RIGHT);
				FinderCacheUtil.clearCache(
					_MAPPING_TABLE_USERS_ROLES_NAME_RIGHT_TO_LEFT);

				if (userLocalService.hasRoleUser(
						user.getCompanyId(), RoleConstants.SOCIAL_OFFICE_USER,
						user.getUserId(), true)) {

					continue;
				}

				layoutSetPrototypeUtil.removeLayoutSetPrototype(
					userGroup, false,
					SocialOfficeConstants.LAYOUT_SET_PROTOTYPE_KEY_USER_PUBLIC);
				layoutSetPrototypeUtil.removeLayoutSetPrototype(
					userGroup, true,
					SocialOfficeConstants.
						LAYOUT_SET_PROTOTYPE_KEY_USER_PRIVATE);

				socialOfficeUtil.disableSocialOffice(userGroup);
			}
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
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

}