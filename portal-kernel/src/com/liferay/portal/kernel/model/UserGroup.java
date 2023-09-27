/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the UserGroup service. Represents a row in the &quot;UserGroup&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupModel
 * @generated
 */
@ImplementationClassName("com.liferay.portal.model.impl.UserGroupImpl")
@ProviderType
public interface UserGroup extends PersistedModel, UserGroupModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.UserGroupImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<UserGroup, Long> USER_GROUP_ID_ACCESSOR =
		new Accessor<UserGroup, Long>() {

			@Override
			public Long get(UserGroup userGroup) {
				return userGroup.getUserGroupId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<UserGroup> getTypeClass() {
				return UserGroup.class;
			}

		};
	public static final Accessor<UserGroup, String> NAME_ACCESSOR =
		new Accessor<UserGroup, String>() {

			@Override
			public String get(UserGroup userGroup) {
				return userGroup.getName();
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<UserGroup> getTypeClass() {
				return UserGroup.class;
			}

		};

	public Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getGroupId()
		throws com.liferay.portal.kernel.exception.PortalException;

	public int getPrivateLayoutsPageCount()
		throws com.liferay.portal.kernel.exception.PortalException;

	public int getPublicLayoutsPageCount()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean hasPrivateLayouts()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean hasPublicLayouts()
		throws com.liferay.portal.kernel.exception.PortalException;

}