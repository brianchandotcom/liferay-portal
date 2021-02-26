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

import com.liferay.mobile.device.rules.model.MDRAction;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Map;

/**
 * Provides the remote service utility for MDRAction. This utility wraps
 * <code>com.liferay.mobile.device.rules.service.impl.MDRActionServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Edward C. Han
 * @see MDRActionService
 * @generated
 */
public class MDRActionServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.mobile.device.rules.service.impl.MDRActionServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static MDRAction addAction(
			long ruleGroupInstanceId, Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap, String type,
			String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addAction(
			ruleGroupInstanceId, nameMap, descriptionMap, type, typeSettings,
			serviceContext);
	}

	public static MDRAction addAction(
			long ruleGroupInstanceId, Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap, String type,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addAction(
			ruleGroupInstanceId, nameMap, descriptionMap, type,
			typeSettingsUnicodeProperties, serviceContext);
	}

	public static void deleteAction(long actionId) throws PortalException {
		getService().deleteAction(actionId);
	}

	public static MDRAction fetchAction(long actionId) throws PortalException {
		return getService().fetchAction(actionId);
	}

	public static MDRAction getAction(long actionId) throws PortalException {
		return getService().getAction(actionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static MDRAction updateAction(
			long actionId, Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap, String type,
			String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateAction(
			actionId, nameMap, descriptionMap, type, typeSettings,
			serviceContext);
	}

	public static MDRAction updateAction(
			long actionId, Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap, String type,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateAction(
			actionId, nameMap, descriptionMap, type,
			typeSettingsUnicodeProperties, serviceContext);
	}

	public static MDRActionService getService() {
		return _service;
	}

	private static volatile MDRActionService _service;

}