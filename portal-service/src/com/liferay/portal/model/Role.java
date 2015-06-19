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
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the Role service. Represents a row in the &quot;Role_&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see RoleModel
 * @see com.liferay.portal.model.impl.RoleImpl
 * @see com.liferay.portal.model.impl.RoleModelImpl
 * @generated
 */
@ProviderType
public interface Role extends RoleModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.model.impl.RoleImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<Role, Long> ROLE_ID_ACCESSOR = new Accessor<Role, Long>() {
			@Override
			public Long get(Role role) {
				return role.getRoleId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<Role> getTypeClass() {
				return Role.class;
			}
		};

	public static final Accessor<Role, String> NAME_ACCESSOR = new Accessor<Role, String>() {
			@Override
			public String get(Role role) {
				return role.getName();
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<Role> getTypeClass() {
				return Role.class;
			}
		};

	public static final Accessor<Role, String> TITLE_ACCESSOR = new Accessor<Role, String>() {
			@Override
			public String get(Role role) {
				return role.getTitle(LocaleThreadLocal.getThemeDisplayLocale());
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<Role> getTypeClass() {
				return Role.class;
			}
		};

	public java.lang.String getDescriptiveName()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String getTypeLabel();

	public boolean isSystem();

	public boolean isTeam();
}