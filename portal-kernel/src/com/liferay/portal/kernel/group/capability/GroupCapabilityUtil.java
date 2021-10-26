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

package com.liferay.portal.kernel.group.capability;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.GroupCapabilityContributor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Alejandro Tardín
 */
public class GroupCapabilityUtil {

	public static boolean isSupportsPages(Group group) {
		for (GroupCapability groupCapability : _getGroupCapabilities(group)) {
			if (!groupCapability.isSupportsPages()) {
				return false;
			}
		}

		return true;
	}

	public static boolean isSupportsPortlet(Group group, Portlet portlet) {
		for (GroupCapability groupCapability : _getGroupCapabilities(group)) {
			if (!groupCapability.isSupportPortlet(portlet)) {
				return false;
			}
		}

		return true;
	}

	private static List<GroupCapability> _getGroupCapabilities(Group group) {
		List<GroupCapability> groupCapabilities = new ArrayList<>();

		if (group == null) {
			return groupCapabilities;
		}

		for (GroupCapabilityContributor groupCapabilityContributor :
				_groupCapabilityContributors) {

			Optional<GroupCapability> capabilityOptional =
				groupCapabilityContributor.getGroupCapabilityOptional(group);

			capabilityOptional.ifPresent(groupCapabilities::add);
		}

		return groupCapabilities;
	}

	private static final ServiceTrackerList<GroupCapabilityContributor>
		_groupCapabilityContributors = ServiceTrackerListFactory.open(
			SystemBundleUtil.getBundleContext(),
			GroupCapabilityContributor.class);

}