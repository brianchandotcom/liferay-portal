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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MDRRuleGroupInstanceService}.
 *
 * @author Edward C. Han
 * @see MDRRuleGroupInstanceService
 * @generated
 */
@ProviderType
public class MDRRuleGroupInstanceServiceWrapper
	implements MDRRuleGroupInstanceService,
		ServiceWrapper<MDRRuleGroupInstanceService> {
	public MDRRuleGroupInstanceServiceWrapper(
		MDRRuleGroupInstanceService mdrRuleGroupInstanceService) {
		_mdrRuleGroupInstanceService = mdrRuleGroupInstanceService;
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroupInstance addRuleGroupInstance(
		long groupId, java.lang.String className, long classPK,
		long ruleGroupId, int priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleGroupInstanceService.addRuleGroupInstance(groupId,
			className, classPK, ruleGroupId, priority, serviceContext);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroupInstance addRuleGroupInstance(
		long groupId, java.lang.String className, long classPK,
		long ruleGroupId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleGroupInstanceService.addRuleGroupInstance(groupId,
			className, classPK, ruleGroupId, serviceContext);
	}

	@Override
	public void deleteRuleGroupInstance(long ruleGroupInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_mdrRuleGroupInstanceService.deleteRuleGroupInstance(ruleGroupInstanceId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _mdrRuleGroupInstanceService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroupInstance> getRuleGroupInstances(
		java.lang.String className, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.mobile.device.rules.model.MDRRuleGroupInstance> orderByComparator) {
		return _mdrRuleGroupInstanceService.getRuleGroupInstances(className,
			classPK, start, end, orderByComparator);
	}

	@Override
	public int getRuleGroupInstancesCount(java.lang.String className,
		long classPK) {
		return _mdrRuleGroupInstanceService.getRuleGroupInstancesCount(className,
			classPK);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroupInstance updateRuleGroupInstance(
		long ruleGroupInstanceId, int priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleGroupInstanceService.updateRuleGroupInstance(ruleGroupInstanceId,
			priority);
	}

	@Override
	public MDRRuleGroupInstanceService getWrappedService() {
		return _mdrRuleGroupInstanceService;
	}

	@Override
	public void setWrappedService(
		MDRRuleGroupInstanceService mdrRuleGroupInstanceService) {
		_mdrRuleGroupInstanceService = mdrRuleGroupInstanceService;
	}

	private MDRRuleGroupInstanceService _mdrRuleGroupInstanceService;
}