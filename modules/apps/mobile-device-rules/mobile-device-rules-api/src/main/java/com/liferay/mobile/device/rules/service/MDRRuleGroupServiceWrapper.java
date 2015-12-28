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
 * Provides a wrapper for {@link MDRRuleGroupService}.
 *
 * @author Edward C. Han
 * @see MDRRuleGroupService
 * @generated
 */
@ProviderType
public class MDRRuleGroupServiceWrapper implements MDRRuleGroupService,
	ServiceWrapper<MDRRuleGroupService> {
	public MDRRuleGroupServiceWrapper(MDRRuleGroupService mdrRuleGroupService) {
		_mdrRuleGroupService = mdrRuleGroupService;
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup addRuleGroup(
		long groupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleGroupService.addRuleGroup(groupId, nameMap,
			descriptionMap, serviceContext);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup copyRuleGroup(
		long ruleGroupId, long groupId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleGroupService.copyRuleGroup(ruleGroupId, groupId,
			serviceContext);
	}

	@Override
	public void deleteRuleGroup(long ruleGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_mdrRuleGroupService.deleteRuleGroup(ruleGroupId);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup fetchRuleGroup(
		long ruleGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleGroupService.fetchRuleGroup(ruleGroupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _mdrRuleGroupService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup getRuleGroup(
		long ruleGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleGroupService.getRuleGroup(ruleGroupId);
	}

	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup> getRuleGroups(
		long[] groupIds, int start, int end) {
		return _mdrRuleGroupService.getRuleGroups(groupIds, start, end);
	}

	@Override
	public int getRuleGroupsCount(long[] groupIds) {
		return _mdrRuleGroupService.getRuleGroupsCount(groupIds);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup updateRuleGroup(
		long ruleGroupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleGroupService.updateRuleGroup(ruleGroupId, nameMap,
			descriptionMap, serviceContext);
	}

	@Override
	public MDRRuleGroupService getWrappedService() {
		return _mdrRuleGroupService;
	}

	@Override
	public void setWrappedService(MDRRuleGroupService mdrRuleGroupService) {
		_mdrRuleGroupService = mdrRuleGroupService;
	}

	private MDRRuleGroupService _mdrRuleGroupService;
}