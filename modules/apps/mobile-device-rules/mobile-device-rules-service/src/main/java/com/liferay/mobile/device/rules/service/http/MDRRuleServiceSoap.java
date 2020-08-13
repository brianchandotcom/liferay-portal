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

package com.liferay.mobile.device.rules.service.http;

import com.liferay.mobile.device.rules.service.MDRRuleServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>MDRRuleServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.mobile.device.rules.model.MDRRuleSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.mobile.device.rules.model.MDRRule</code>, that is translated to a
 * <code>com.liferay.mobile.device.rules.model.MDRRuleSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Edward C. Han
 * @see MDRRuleServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class MDRRuleServiceSoap {

	public static com.liferay.mobile.device.rules.model.MDRRuleSoap addRule(
			long ruleGroupId, String[] nameMapLanguageIds,
			String[] nameMapValues, String[] descriptionMapLanguageIds,
			String[] descriptionMapValues, String type, String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.mobile.device.rules.model.MDRRule returnValue =
				MDRRuleServiceUtil.addRule(
					ruleGroupId, nameMap, descriptionMap, type, typeSettings,
					serviceContext);

			return com.liferay.mobile.device.rules.model.MDRRuleSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteRule(long ruleId) throws RemoteException {
		try {
			MDRRuleServiceUtil.deleteRule(ruleId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.mobile.device.rules.model.MDRRuleSoap fetchRule(
			long ruleId)
		throws RemoteException {

		try {
			com.liferay.mobile.device.rules.model.MDRRule returnValue =
				MDRRuleServiceUtil.fetchRule(ruleId);

			return com.liferay.mobile.device.rules.model.MDRRuleSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.mobile.device.rules.model.MDRRuleSoap getRule(
			long ruleId)
		throws RemoteException {

		try {
			com.liferay.mobile.device.rules.model.MDRRule returnValue =
				MDRRuleServiceUtil.getRule(ruleId);

			return com.liferay.mobile.device.rules.model.MDRRuleSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.mobile.device.rules.model.MDRRuleSoap updateRule(
			long ruleId, String[] nameMapLanguageIds, String[] nameMapValues,
			String[] descriptionMapLanguageIds, String[] descriptionMapValues,
			String type, String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.mobile.device.rules.model.MDRRule returnValue =
				MDRRuleServiceUtil.updateRule(
					ruleId, nameMap, descriptionMap, type, typeSettings,
					serviceContext);

			return com.liferay.mobile.device.rules.model.MDRRuleSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(MDRRuleServiceSoap.class);

}