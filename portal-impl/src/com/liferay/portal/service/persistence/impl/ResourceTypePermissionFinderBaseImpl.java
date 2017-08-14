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

package com.liferay.portal.service.persistence.impl;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.model.ResourceTypePermission;
import com.liferay.portal.kernel.service.persistence.ResourceTypePermissionPersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

/**
 * @author Brian Wing Shun Chan
 * @deprecated
 * @generated
 */
@Deprecated
public class ResourceTypePermissionFinderBaseImpl extends BasePersistenceImpl<ResourceTypePermission> {
	public ResourceTypePermissionFinderBaseImpl() {
		setModelClass(ResourceTypePermission.class);
	}

	/**
	 * Returns the resource type permission persistence.
	 *
	 * @return the resource type permission persistence
	 */
	public ResourceTypePermissionPersistence getResourceTypePermissionPersistence() {
		return resourceTypePermissionPersistence;
	}

	/**
	 * Sets the resource type permission persistence.
	 *
	 * @param resourceTypePermissionPersistence the resource type permission persistence
	 */
	public void setResourceTypePermissionPersistence(
		ResourceTypePermissionPersistence resourceTypePermissionPersistence) {
		this.resourceTypePermissionPersistence = resourceTypePermissionPersistence;
	}

	@BeanReference(type = ResourceTypePermissionPersistence.class)
	protected ResourceTypePermissionPersistence resourceTypePermissionPersistence;
}