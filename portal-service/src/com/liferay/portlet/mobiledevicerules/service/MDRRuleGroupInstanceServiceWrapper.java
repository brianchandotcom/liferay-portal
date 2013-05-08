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

package com.liferay.portlet.mobiledevicerules.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MDRRuleGroupInstanceService}.
 *
 * @author    Edward C. Han
 * @see       MDRRuleGroupInstanceService
 * @generated
 */
public class MDRRuleGroupInstanceServiceWrapper
	implements MDRRuleGroupInstanceService,
		ServiceWrapper<MDRRuleGroupInstanceService> {
	public MDRRuleGroupInstanceServiceWrapper(
		MDRRuleGroupInstanceService mdrRuleGroupInstanceService) {
		_mdrRuleGroupInstanceService = mdrRuleGroupInstanceService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _mdrRuleGroupInstanceService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_mdrRuleGroupInstanceService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance addRuleGroupInstance(
		long groupId, java.lang.String className, long classPK,
		long ruleGroupId, int priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupInstanceService.addRuleGroupInstance(groupId,
			className, classPK, ruleGroupId, priority, serviceContext);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance addRuleGroupInstance(
		long groupId, java.lang.String className, long classPK,
		long ruleGroupId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupInstanceService.addRuleGroupInstance(groupId,
			className, classPK, ruleGroupId, serviceContext);
	}

	public void deleteRuleGroupInstance(long ruleGroupInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mdrRuleGroupInstanceService.deleteRuleGroupInstance(ruleGroupInstanceId);
	}

	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> getRuleGroupInstances(
		java.lang.String className, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupInstanceService.getRuleGroupInstances(className,
			classPK, start, end, orderByComparator);
	}

	public int getRuleGroupInstancesCount(java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupInstanceService.getRuleGroupInstancesCount(className,
			classPK);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance updateRuleGroupInstance(
		long ruleGroupInstanceId, int priority)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupInstanceService.updateRuleGroupInstance(ruleGroupInstanceId,
			priority);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public MDRRuleGroupInstanceService getWrappedMDRRuleGroupInstanceService() {
		return _mdrRuleGroupInstanceService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedMDRRuleGroupInstanceService(
		MDRRuleGroupInstanceService mdrRuleGroupInstanceService) {
		_mdrRuleGroupInstanceService = mdrRuleGroupInstanceService;
	}

	public MDRRuleGroupInstanceService getWrappedService() {
		return _mdrRuleGroupInstanceService;
	}

	public void setWrappedService(
		MDRRuleGroupInstanceService mdrRuleGroupInstanceService) {
		_mdrRuleGroupInstanceService = mdrRuleGroupInstanceService;
	}

	private MDRRuleGroupInstanceService _mdrRuleGroupInstanceService;
}