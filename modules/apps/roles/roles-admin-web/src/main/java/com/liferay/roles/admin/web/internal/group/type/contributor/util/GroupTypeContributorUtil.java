/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.roles.admin.web.internal.group.type.contributor.util;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.roles.admin.group.type.contributor.GroupTypeContributor;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Alejandro Tardín
 */
public class GroupTypeContributorUtil {

	public static long[] getClassNameIds() {
		List<Long> classNameIds = new ArrayList<>();

		for (GroupTypeContributor groupTypeContributor : _serviceTrackerList) {
			if (!groupTypeContributor.isEnabled()) {
				continue;
			}

			classNameIds.add(
				PortalUtil.getClassNameId(groupTypeContributor.getClassName()));
		}

		return ListUtil.toLongArray(classNameIds, Long::valueOf);
	}

	private static final ServiceTrackerList<GroupTypeContributor>
		_serviceTrackerList;

	static {
		Bundle bundle = FrameworkUtil.getBundle(GroupTypeContributorUtil.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, GroupTypeContributor.class);
	}

}