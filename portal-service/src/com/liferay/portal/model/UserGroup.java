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
 * The extended model interface for the UserGroup service. Represents a row in the &quot;UserGroup&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupModel
 * @see com.liferay.portal.model.impl.UserGroupImpl
 * @see com.liferay.portal.model.impl.UserGroupModelImpl
 * @generated
 */
@ProviderType
public interface UserGroup extends UserGroupModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.model.impl.UserGroupImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<UserGroup, String> NAME_ACCESSOR = new Accessor<UserGroup, String>() {
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

	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getGroupId()
		throws com.liferay.portal.kernel.exception.PortalException;

	public int getLayoutsPageCount()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean hasLayouts()
		throws com.liferay.portal.kernel.exception.PortalException;
}