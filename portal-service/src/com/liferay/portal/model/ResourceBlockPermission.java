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
 * The extended model interface for the ResourceBlockPermission service. Represents a row in the &quot;ResourceBlockPermission&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ResourceBlockPermissionModel
 * @see com.liferay.portal.model.impl.ResourceBlockPermissionImpl
 * @see com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl
 * @generated
 */
@ProviderType
public interface ResourceBlockPermission extends ResourceBlockPermissionModel,
	PermissionedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.model.impl.ResourceBlockPermissionImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ResourceBlockPermission, Long> RESOURCE_BLOCK_PERMISSION_ID_ACCESSOR =
		new Accessor<ResourceBlockPermission, Long>() {
			@Override
			public Long get(ResourceBlockPermission resourceBlockPermission) {
				return resourceBlockPermission.getResourceBlockPermissionId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ResourceBlockPermission> getTypeClass() {
				return ResourceBlockPermission.class;
			}
		};
}