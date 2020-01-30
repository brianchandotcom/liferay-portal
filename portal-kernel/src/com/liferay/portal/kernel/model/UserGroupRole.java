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

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the UserGroupRole service. Represents a row in the &quot;UserGroupRole&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupRoleModel
 * @generated
 */
@ImplementationClassName("com.liferay.portal.model.impl.UserGroupRoleImpl")
@ProviderType
public interface UserGroupRole extends PersistedModel, UserGroupRoleModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.UserGroupRoleImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<UserGroupRole, Long>
		USER_GROUP_ROLE_ID_ACCESSOR = new Accessor<UserGroupRole, Long>() {

			@Override
			public Long get(UserGroupRole userGroupRole) {
				return userGroupRole.getUserGroupRoleId();
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
	public static final Accessor<UserGroupRole, Long> USER_ID_ACCESSOR =
		new Accessor<UserGroupRole, Long>() {

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
	public static final Accessor<UserGroupRole, Long> GROUP_ID_ACCESSOR =
		new Accessor<UserGroupRole, Long>() {

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
	public static final Accessor<UserGroupRole, Long> ROLE_ID_ACCESSOR =
		new Accessor<UserGroupRole, Long>() {

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

	@Override
	public boolean equals(Object obj);

	public Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException;

	public Role getRole()
		throws com.liferay.portal.kernel.exception.PortalException;

	public User getUser()
		throws com.liferay.portal.kernel.exception.PortalException;

	public int hashCode();

}