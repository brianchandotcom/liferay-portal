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

package com.liferay.portlet.mobiledevicerules.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Edward C. Han
 */
public class MDRRuleGroupFinderUtil {
	public static int countByG_N(long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByG_N(groupId, name);
	}

	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup> findByG_N(
		long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByG_N(groupId, name);
	}

	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup> findByG_N(
		long groupId, java.lang.String name, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByG_N(groupId, name, start, end);
	}

	public static MDRRuleGroupFinder getFinder() {
		if (_finder == null) {
			_finder = (MDRRuleGroupFinder)PortalBeanLocatorUtil.locate(MDRRuleGroupFinder.class.getName());

			ReferenceRegistry.registerReference(MDRRuleGroupFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(MDRRuleGroupFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(MDRRuleGroupFinderUtil.class,
			"_finder");
	}

	private static MDRRuleGroupFinder _finder;
}