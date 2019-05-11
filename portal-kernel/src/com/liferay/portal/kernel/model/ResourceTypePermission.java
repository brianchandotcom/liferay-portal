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
 * The extended model interface for the ResourceTypePermission service. Represents a row in the &quot;ResourceTypePermission&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ResourceTypePermissionModel
 * @deprecated As of Judson (7.1.x), with no direct replacement
 * @generated
 */
@Deprecated
@ImplementationClassName(
	"com.liferay.portal.model.impl.ResourceTypePermissionImpl"
)
@ProviderType
public interface ResourceTypePermission
	extends PersistedModel, ResourceTypePermissionModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.ResourceTypePermissionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ResourceTypePermission, Long>
		RESOURCE_TYPE_PERMISSION_ID_ACCESSOR =
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

	public boolean hasAction(ResourceAction resourceAction);

	public boolean isCompanyScope();

	public boolean isGroupScope();

}