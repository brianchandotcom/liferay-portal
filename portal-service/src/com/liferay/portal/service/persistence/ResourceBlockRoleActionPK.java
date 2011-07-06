/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 */
public class ResourceBlockRoleActionPK implements Comparable<ResourceBlockRoleActionPK>,
	Serializable {
	public long actionId;
	public long resourceBlockId;
	public long roleId;

	public ResourceBlockRoleActionPK() {
	}

	public ResourceBlockRoleActionPK(long actionId, long resourceBlockId,
		long roleId) {
		this.actionId = actionId;
		this.resourceBlockId = resourceBlockId;
		this.roleId = roleId;
	}

	public long getActionId() {
		return actionId;
	}

	public void setActionId(long actionId) {
		this.actionId = actionId;
	}

	public long getResourceBlockId() {
		return resourceBlockId;
	}

	public void setResourceBlockId(long resourceBlockId) {
		this.resourceBlockId = resourceBlockId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public int compareTo(ResourceBlockRoleActionPK pk) {
		if (pk == null) {
			return -1;
		}

		int value = 0;

		if (actionId < pk.actionId) {
			value = -1;
		}
		else if (actionId > pk.actionId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (resourceBlockId < pk.resourceBlockId) {
			value = -1;
		}
		else if (resourceBlockId > pk.resourceBlockId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (roleId < pk.roleId) {
			value = -1;
		}
		else if (roleId > pk.roleId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		ResourceBlockRoleActionPK pk = null;

		try {
			pk = (ResourceBlockRoleActionPK)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		if ((actionId == pk.actionId) &&
				(resourceBlockId == pk.resourceBlockId) &&
				(roleId == pk.roleId)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (String.valueOf(actionId) + String.valueOf(resourceBlockId) +
		String.valueOf(roleId)).hashCode();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append(StringPool.OPEN_CURLY_BRACE);

		sb.append("actionId");
		sb.append(StringPool.EQUAL);
		sb.append(actionId);

		sb.append(StringPool.COMMA);
		sb.append(StringPool.SPACE);
		sb.append("resourceBlockId");
		sb.append(StringPool.EQUAL);
		sb.append(resourceBlockId);

		sb.append(StringPool.COMMA);
		sb.append(StringPool.SPACE);
		sb.append("roleId");
		sb.append(StringPool.EQUAL);
		sb.append(roleId);

		sb.append(StringPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}
}