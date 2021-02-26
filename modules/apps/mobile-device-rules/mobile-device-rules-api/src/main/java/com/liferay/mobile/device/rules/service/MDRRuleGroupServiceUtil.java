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

package com.liferay.mobile.device.rules.service;

import com.liferay.mobile.device.rules.model.MDRRuleGroup;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for MDRRuleGroup. This utility wraps
 * <code>com.liferay.mobile.device.rules.service.impl.MDRRuleGroupServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Edward C. Han
 * @see MDRRuleGroupService
 * @generated
 */
public class MDRRuleGroupServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.mobile.device.rules.service.impl.MDRRuleGroupServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static MDRRuleGroup addRuleGroup(
			long groupId, Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addRuleGroup(
			groupId, nameMap, descriptionMap, serviceContext);
	}

	public static MDRRuleGroup copyRuleGroup(
			long ruleGroupId, long groupId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().copyRuleGroup(ruleGroupId, groupId, serviceContext);
	}

	public static void deleteRuleGroup(long ruleGroupId)
		throws PortalException {

		getService().deleteRuleGroup(ruleGroupId);
	}

	public static MDRRuleGroup fetchRuleGroup(long ruleGroupId)
		throws PortalException {

		return getService().fetchRuleGroup(ruleGroupId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static MDRRuleGroup getRuleGroup(long ruleGroupId)
		throws PortalException {

		return getService().getRuleGroup(ruleGroupId);
	}

	public static List<MDRRuleGroup> getRuleGroups(
		long[] groupIds, int start, int end) {

		return getService().getRuleGroups(groupIds, start, end);
	}

	public static int getRuleGroupsCount(long[] groupIds) {
		return getService().getRuleGroupsCount(groupIds);
	}

	public static MDRRuleGroup updateRuleGroup(
			long ruleGroupId, Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateRuleGroup(
			ruleGroupId, nameMap, descriptionMap, serviceContext);
	}

	public static MDRRuleGroupService getService() {
		return _service;
	}

	private static volatile MDRRuleGroupService _service;

}