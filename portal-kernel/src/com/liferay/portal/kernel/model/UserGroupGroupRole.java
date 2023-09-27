/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the UserGroupGroupRole service. Represents a row in the &quot;UserGroupGroupRole&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupGroupRoleModel
 * @generated
 */
@ImplementationClassName("com.liferay.portal.model.impl.UserGroupGroupRoleImpl")
@ProviderType
public interface UserGroupGroupRole
	extends PersistedModel, UserGroupGroupRoleModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.UserGroupGroupRoleImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<UserGroupGroupRole, Long>
		USER_GROUP_GROUP_ROLE_ID_ACCESSOR =
			new Accessor<UserGroupGroupRole, Long>() {

				@Override
				public Long get(UserGroupGroupRole userGroupGroupRole) {
					return userGroupGroupRole.getUserGroupGroupRoleId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<UserGroupGroupRole> getTypeClass() {
					return UserGroupGroupRole.class;
				}

			};

	@Override
	public boolean equals(Object object);

	public Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException;

	public Role getRole()
		throws com.liferay.portal.kernel.exception.PortalException;

	public UserGroup getUserGroup()
		throws com.liferay.portal.kernel.exception.PortalException;

	public int hashCode();

}