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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
@Deprecated
public class OrgGroupRoleImpl extends OrgGroupRoleBaseImpl {

	@Override
	public boolean containsGroup(List<Group> groups) {
		if (groups == null) {
			return false;
		}

		for (Group group : groups) {
			if (group.getGroupId() == getGroupId()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean containsOrganization(List<Organization> organizations) {
		if (organizations == null) {
			return false;
		}

		for (Organization organization : organizations) {
			if (organization.getOrganizationId() == getOrganizationId()) {
				return true;
			}
		}

		return false;
	}

}