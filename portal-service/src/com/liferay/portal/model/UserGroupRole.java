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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the UserGroupRole service. Represents a row in the &quot;UserGroupRole&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupRoleModel
 * @see com.liferay.portal.model.impl.UserGroupRoleImpl
 * @see com.liferay.portal.model.impl.UserGroupRoleModelImpl
 * @generated
 */
@ProviderType
public interface UserGroupRole extends UserGroupRoleModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.model.impl.UserGroupRoleImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<UserGroupRole, Long> USER_ID_ACCESSOR = new Accessor<UserGroupRole, Long>() {
			@Override
			public Long get(UserGroupRole userGroupRole) {
				return userGroupRole.getUserId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<UserGroupRole> getTypeClass() {
				return UserGroupRole.class;
			}
		};

	public static final Accessor<UserGroupRole, Long> GROUP_ID_ACCESSOR = new Accessor<UserGroupRole, Long>() {
			@Override
			public Long get(UserGroupRole userGroupRole) {
				return userGroupRole.getGroupId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<UserGroupRole> getTypeClass() {
				return UserGroupRole.class;
			}
		};

	public static final Accessor<UserGroupRole, Long> ROLE_ID_ACCESSOR = new Accessor<UserGroupRole, Long>() {
			@Override
			public Long get(UserGroupRole userGroupRole) {
				return userGroupRole.getRoleId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<UserGroupRole> getTypeClass() {
				return UserGroupRole.class;
			}
		};

	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.model.Role getRole()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.model.User getUser()
		throws com.liferay.portal.kernel.exception.PortalException;
}