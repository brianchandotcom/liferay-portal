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

package com.liferay.object.service.impl;

import com.liferay.object.model.ObjectEntry;
import com.liferay.object.security.permission.resource.ObjectEntryModelResourcePermissionTracker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Mateus Santana
 */
@Component(
	immediate = true, service = ObjectEntryModelResourcePermissionTracker.class
)
public class ObjectEntryModelResourcePermissionTrackerImpl
	implements ObjectEntryModelResourcePermissionTracker {

	public ModelResourcePermission<ObjectEntry>
		getObjectEntryModelResourcePermission(String className) {

		return _objectEntryModelResourcePermissions.get(className);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(&(com.liferay.object=true)(model.class.name=*))"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<ObjectEntry> modelResourcePermission,
		Map<String, Object> properties) {

		String className = (String)properties.get("model.class.name");

		_objectEntryModelResourcePermissions.put(
			className, modelResourcePermission);
	}

	protected void unsetModelResourcePermission(
		ModelResourcePermission<ObjectEntry> modelResourcePermission,
		Map<String, Object> properties) {

		String className = (String)properties.get("model.class.name");

		_objectEntryModelResourcePermissions.remove(className);
	}

	private final Map<String, ModelResourcePermission<ObjectEntry>>
		_objectEntryModelResourcePermissions = new ConcurrentHashMap<>();

}