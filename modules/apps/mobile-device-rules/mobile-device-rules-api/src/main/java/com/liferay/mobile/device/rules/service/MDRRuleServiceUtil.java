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

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for MDRRule. This utility wraps
 * {@link com.liferay.mobile.device.rules.service.impl.MDRRuleServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Edward C. Han
 * @see MDRRuleService
 * @see com.liferay.mobile.device.rules.service.base.MDRRuleServiceBaseImpl
 * @see com.liferay.mobile.device.rules.service.impl.MDRRuleServiceImpl
 * @generated
 */
@ProviderType
public class MDRRuleServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.mobile.device.rules.service.impl.MDRRuleServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.mobile.device.rules.model.MDRRule addRule(
		long ruleGroupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String typeSettings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addRule(ruleGroupId, nameMap, descriptionMap, type,
			typeSettings, serviceContext);
	}

	public static com.liferay.mobile.device.rules.model.MDRRule addRule(
		long ruleGroupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addRule(ruleGroupId, nameMap, descriptionMap, type,
			typeSettings, serviceContext);
	}

	public static void deleteRule(long ruleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteRule(ruleId);
	}

	public static com.liferay.mobile.device.rules.model.MDRRule fetchRule(
		long ruleId) throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchRule(ruleId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.mobile.device.rules.model.MDRRule getRule(
		long ruleId) throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRule(ruleId);
	}

	public static com.liferay.mobile.device.rules.model.MDRRule updateRule(
		long ruleId, java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String typeSettings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateRule(ruleId, nameMap, descriptionMap, type,
			typeSettings, serviceContext);
	}

	public static com.liferay.mobile.device.rules.model.MDRRule updateRule(
		long ruleId, java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateRule(ruleId, nameMap, descriptionMap, type,
			typeSettingsProperties, serviceContext);
	}

	public static MDRRuleService getService() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(MDRRuleService service) {
	}

	private static ServiceTracker<MDRRuleService, MDRRuleService> _serviceTracker =
		ServiceTrackerFactory.open(MDRRuleService.class);
}