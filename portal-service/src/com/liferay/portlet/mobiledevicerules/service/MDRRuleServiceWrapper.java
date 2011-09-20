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

/**
 * <p>
 * This class is a wrapper for {@link MDRRuleService}.
 * </p>
 *
 * @author    Edward C. Han
 * @see       MDRRuleService
 * @generated
 */
public class MDRRuleServiceWrapper implements MDRRuleService {
	public MDRRuleServiceWrapper(MDRRuleService mdrRuleService) {
		_mdrRuleService = mdrRuleService;
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRule addRule(
		long groupId, long ruleGroupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String typeSettings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleService.addRule(groupId, ruleGroupId, nameMap,
			descriptionMap, type, typeSettings, serviceContext);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRule addRule(
		long groupId, long ruleGroupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleService.addRule(groupId, ruleGroupId, nameMap,
			descriptionMap, type, typeSettings, serviceContext);
	}

	public void deleteRule(
		com.liferay.portlet.mobiledevicerules.model.MDRRule rule)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mdrRuleService.deleteRule(rule);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRule fetchRule(
		long ruleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleService.fetchRule(ruleId);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRule getRule(
		long ruleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleService.getRule(ruleId);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRule updateRule(
		long ruleId, java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleService.updateRule(ruleId, nameMap, descriptionMap,
			type, typeSettingsProperties, serviceContext);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRule updateRule(
		long ruleId, java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String typeSettings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleService.updateRule(ruleId, nameMap, descriptionMap,
			type, typeSettings, serviceContext);
	}

	public MDRRuleService getWrappedMDRRuleService() {
		return _mdrRuleService;
	}

	public void setWrappedMDRRuleService(MDRRuleService mdrRuleService) {
		_mdrRuleService = mdrRuleService;
	}

	private MDRRuleService _mdrRuleService;
}