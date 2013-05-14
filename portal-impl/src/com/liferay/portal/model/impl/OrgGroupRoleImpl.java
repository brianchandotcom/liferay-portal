/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class OrgGroupRoleImpl extends OrgGroupRoleBaseImpl {

	public OrgGroupRoleImpl() {
	}

	@Override
	public boolean containsGroup(List<Group> groups) {
		if (groups == null) {
			return false;
		}
		else {
			for (Group group : groups) {
				if (group.getGroupId() == getGroupId()) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public boolean containsOrganization(List<Organization> organizations) {
		if (organizations == null) {
			return false;
		}
		else {
			for (Organization organization : organizations) {
				if (organization.getOrganizationId() == getOrganizationId()) {
					return true;
				}
			}
		}

		return false;
	}

}