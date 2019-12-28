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

package com.liferay.headless.delivery.client.permission;

import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Permissions {

	public String[] getActionIds() {
		return _actionIds;
	}

	public String getRoleName() {
		return _roleName;
	}

	public void setActionIds(String[] actionIds) {
		this._actionIds = actionIds;
	}

	public void setRoleName(String roleName) {
		this._roleName = roleName;
	}

	private String[] _actionIds;
	private String _roleName;

	private static class PermissionsJSONParser<T>
		extends BaseJSONParser<Permissions> {

		@Override
		protected Permissions createDTO() {
			return new Permissions();
		}

		@Override
		protected Permissions[] createDTOArray(int size) {
			return new Permissions[size];
		}

		@Override
		protected void setField(
			Permissions permissions, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actionIds")) {
				if (jsonParserFieldValue != null) {
					permissions.setActionIds((String[])jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "roleName")) {
				if (jsonParserFieldValue != null) {
					permissions.setRoleName((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}