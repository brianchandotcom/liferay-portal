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

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class PermissionDisplay
	implements Comparable<PermissionDisplay>, Serializable {

	public PermissionDisplay(
		Permission permission, Resource resource, String portletName,
		String portletLabel, String modelName, String modelLabel,
		String actionId, String actionLabel) {

		_permission = permission;
		_resource = resource;
		_portletName = portletName;
		_portletLabel = portletLabel;
		_modelName = modelName;
		_modelLabel = modelLabel;
		_actionId = actionId;
		_actionLabel = actionLabel;
	}

	@Override
	public int compareTo(PermissionDisplay permissionDisplay) {
		int value = getPortletLabel().compareTo(
			permissionDisplay.getPortletLabel());

		if (value == 0) {
			value = getModelLabel().compareTo(
				permissionDisplay.getModelLabel());

			if (value == 0) {
				value = getActionLabel().compareTo(
					permissionDisplay.getActionLabel());
			}
		}

		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PermissionDisplay)) {
			return false;
		}

		PermissionDisplay permissionDisplay = (PermissionDisplay)obj;

		if (_portletName.equals(permissionDisplay.getPortletName()) &&
			_modelName.equals(permissionDisplay.getModelName()) &&
			_actionId.equals(permissionDisplay.getActionId())) {

			return true;
		}

		return false;
	}

	public String getActionId() {
		return _actionId;
	}

	public String getActionLabel() {
		return _actionLabel;
	}

	public String getModelLabel() {
		return _modelLabel;
	}

	public String getModelName() {
		return _modelName;
	}

	public Permission getPermission() {
		return _permission;
	}

	public String getPortletLabel() {
		return _portletLabel;
	}

	public String getPortletName() {
		return _portletName;
	}

	public Resource getResource() {
		return _resource;
	}

	@Override
	public int hashCode() {
		return _portletName.concat(_modelName).concat(_actionId).hashCode();
	}

	private final String _actionId;
	private final String _actionLabel;
	private final String _modelLabel;
	private final String _modelName;
	private final Permission _permission;
	private final String _portletLabel;
	private final String _portletName;
	private final Resource _resource;

}