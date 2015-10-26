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
 * The extended model interface for the ResourceTypePermission service. Represents a row in the &quot;ResourceTypePermission&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ResourceTypePermissionModel
 * @see com.liferay.portal.model.impl.ResourceTypePermissionImpl
 * @see com.liferay.portal.model.impl.ResourceTypePermissionModelImpl
 * @generated
 */
@ProviderType
public interface ResourceTypePermission extends ResourceTypePermissionModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.model.impl.ResourceTypePermissionImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ResourceTypePermission, Long> RESOURCE_TYPE_PERMISSION_ID_ACCESSOR =
		new Accessor<ResourceTypePermission, Long>() {
			@Override
			public Long get(ResourceTypePermission resourceTypePermission) {
				return resourceTypePermission.getResourceTypePermissionId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ResourceTypePermission> getTypeClass() {
				return ResourceTypePermission.class;
			}
		};

	public boolean hasAction(
		com.liferay.portal.model.ResourceAction resourceAction);

	public boolean isCompanyScope();

	public boolean isGroupScope();
}