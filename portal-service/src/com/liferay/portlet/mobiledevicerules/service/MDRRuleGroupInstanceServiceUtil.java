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

package com.liferay.portlet.mobiledevicerules.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the m d r rule group instance remote service. This utility wraps {@link com.liferay.portlet.mobiledevicerules.service.impl.MDRRuleGroupInstanceServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Edward C. Han
 * @see MDRRuleGroupInstanceService
 * @see com.liferay.portlet.mobiledevicerules.service.base.MDRRuleGroupInstanceServiceBaseImpl
 * @see com.liferay.portlet.mobiledevicerules.service.impl.MDRRuleGroupInstanceServiceImpl
 * @generated
 */
public class MDRRuleGroupInstanceServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.mobiledevicerules.service.impl.MDRRuleGroupInstanceServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance addRuleGroupInstance(
		long groupId, java.lang.String className, long classPK,
		long ruleGroupId, int priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addRuleGroupInstance(groupId, className, classPK,
			ruleGroupId, priority, serviceContext);
	}

	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance addRuleGroupInstance(
		long groupId, java.lang.String className, long classPK,
		long ruleGroupId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addRuleGroupInstance(groupId, className, classPK,
			ruleGroupId, serviceContext);
	}

	public static void deleteRuleGroupInstance(long ruleGroupInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRuleGroupInstance(ruleGroupInstanceId);
	}

	public static void deleteRuleGroupInstance(
		com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance ruleGroupInstance)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRuleGroupInstance(ruleGroupInstance);
	}

	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> getRuleGroupInstances(
		java.lang.String className, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getRuleGroupInstances(className, classPK, start, end,
			orderByComparator);
	}

	public static int getRuleGroupInstancesCount(java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRuleGroupInstancesCount(className, classPK);
	}

	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance updateRuleGroupInstance(
		long ruleGroupInstanceId, int priority)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateRuleGroupInstance(ruleGroupInstanceId, priority);
	}

	public static MDRRuleGroupInstanceService getService() {
		if (_service == null) {
			_service = (MDRRuleGroupInstanceService)PortalBeanLocatorUtil.locate(MDRRuleGroupInstanceService.class.getName());

			ReferenceRegistry.registerReference(MDRRuleGroupInstanceServiceUtil.class,
				"_service");
			MethodCache.remove(MDRRuleGroupInstanceService.class);
		}

		return _service;
	}

	public void setService(MDRRuleGroupInstanceService service) {
		MethodCache.remove(MDRRuleGroupInstanceService.class);

		_service = service;

		ReferenceRegistry.registerReference(MDRRuleGroupInstanceServiceUtil.class,
			"_service");
		MethodCache.remove(MDRRuleGroupInstanceService.class);
	}

	private static MDRRuleGroupInstanceService _service;
}