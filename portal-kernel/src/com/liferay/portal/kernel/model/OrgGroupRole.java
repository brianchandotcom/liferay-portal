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
 * The extended model interface for the OrgGroupRole service. Represents a row in the &quot;OrgGroupRole&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see OrgGroupRoleModel
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
@ImplementationClassName("com.liferay.portal.model.impl.OrgGroupRoleImpl")
@ProviderType
public interface OrgGroupRole extends OrgGroupRoleModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.OrgGroupRoleImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<OrgGroupRole, Long> ORGANIZATION_ID_ACCESSOR =
		new Accessor<OrgGroupRole, Long>() {

			@Override
			public Long get(OrgGroupRole orgGroupRole) {
				return orgGroupRole.getOrganizationId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<OrgGroupRole> getTypeClass() {
				return OrgGroupRole.class;
			}

		};
	public static final Accessor<OrgGroupRole, Long> GROUP_ID_ACCESSOR =
		new Accessor<OrgGroupRole, Long>() {

			@Override
			public Long get(OrgGroupRole orgGroupRole) {
				return orgGroupRole.getGroupId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<OrgGroupRole> getTypeClass() {
				return OrgGroupRole.class;
			}

		};
	public static final Accessor<OrgGroupRole, Long> ROLE_ID_ACCESSOR =
		new Accessor<OrgGroupRole, Long>() {

			@Override
			public Long get(OrgGroupRole orgGroupRole) {
				return orgGroupRole.getRoleId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<OrgGroupRole> getTypeClass() {
				return OrgGroupRole.class;
			}

		};

	public boolean containsGroup(java.util.List<Group> groups);

	public boolean containsOrganization(
		java.util.List<Organization> organizations);

}