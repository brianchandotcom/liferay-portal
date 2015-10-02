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

package com.liferay.service.access.policy;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationFactory;
import com.liferay.portal.kernel.security.access.control.AccessControlPolicy;
import com.liferay.portal.kernel.security.access.control.AccessControlUtil;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.security.access.control.BaseAccessControlPolicy;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierResult;
import com.liferay.portal.kernel.security.service.access.policy.ServiceAccessPolicyThreadLocal;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.AccessControlContext;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.service.access.policy.configuration.SAPConfiguration;
import com.liferay.service.access.policy.constants.SAPConstants;
import com.liferay.service.access.policy.model.SAPEntry;
import com.liferay.service.access.policy.service.SAPEntryLocalService;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(service = AccessControlPolicy.class)
public class SAPAccessControlPolicy extends BaseAccessControlPolicy {

	@Override
	public void onServiceRemoteAccess(
			Method method, Object[] arguments,
			AccessControlled accessControlled)
		throws SecurityException {

		if (alreadyChecked()) {
			return;
		}

		long companyId = CompanyThreadLocal.getCompanyId();

		List<String> serviceAccessPolicyNames = getActiveSAPNames();

		List<String> systemSAPNames = getSystemSAPNames(companyId);
		serviceAccessPolicyNames.addAll(systemSAPNames);

		Set<String> allowedServiceSignatures = loadAllowedServiceSignatures(
			companyId, serviceAccessPolicyNames);

		Class<?> clazz = method.getDeclaringClass();

		String className = clazz.getName();
		String methodName = method.getName();

		checkAccess(allowedServiceSignatures, className, methodName);
	}

	protected boolean alreadyChecked() {
		AccessControlContext accessControlContext =
			AccessControlUtil.getAccessControlContext();

		if (accessControlContext != null) {
			Map<String, Object> settings = accessControlContext.getSettings();

			int serviceDepth = (Integer)settings.get(
				AccessControlContext.Settings.SERVICE_DEPTH.toString());

			if (serviceDepth > 1) {
				return true;
			}
		}

		return false;
	}

	protected void checkAccess(
		Set<String> allowedServiceSignatures, String className,
		String methodName) {

		if (allowedServiceSignatures.contains(StringPool.STAR)) {
			return;
		}

		if (allowedServiceSignatures.contains(className)) {
			return;
		}

		String classNameAndMethodName = className.concat(
			StringPool.POUND).concat(methodName);

		if (allowedServiceSignatures.contains(classNameAndMethodName)) {
			return;
		}

		for (String allowedService : allowedServiceSignatures) {
			if (matches(className, methodName, allowedService)) {
				return;
			}
		}

		throw new SecurityException(
			"Access denied to " + classNameAndMethodName);
	}

	protected List<String> getActiveSAPNames() {
		List<String> serviceAccessPolicyNames =
			ServiceAccessPolicyThreadLocal.getActiveServiceAccessPolicyNames();

		if (serviceAccessPolicyNames == null) {
			serviceAccessPolicyNames = new ArrayList<>();

			ServiceAccessPolicyThreadLocal.setActiveServiceAccessPolicyNames(
				serviceAccessPolicyNames);
		}

		return serviceAccessPolicyNames;
	}

	protected List<String> getSystemSAPNames(long companyId) {
		List<String> systemSAPNames = new ArrayList<>(2);

		SAPConfiguration sapConfiguration = null;

		try {
			sapConfiguration = _configurationFactory.getConfiguration(
				SAPConfiguration.class,
				new CompanyServiceSettingsLocator(
					companyId, SAPConstants.SERVICE_NAME));
		}
		catch (ConfigurationException ce) {
			throw new SecurityException(
				"Unable to get service access policy configuration", ce);
		}

		if (!sapConfiguration.useSystemSAPEntries()) {
			return systemSAPNames;
		}

		systemSAPNames.add(sapConfiguration.systemDefaultSAPEntryName());

		boolean passwordBasedAuthentication = false;

		AccessControlContext accessControlContext =
			AccessControlUtil.getAccessControlContext();

		if (accessControlContext != null) {
			AuthVerifierResult authVerifierResult =
				accessControlContext.getAuthVerifierResult();

			if (authVerifierResult != null) {
				passwordBasedAuthentication =
					authVerifierResult.isPasswordBasedAuthentication();
			}
		}

		if (passwordBasedAuthentication) {
			systemSAPNames.add(
				sapConfiguration.systemUserPasswordSAPEntryName());
		}

		return systemSAPNames;
	}

	protected Set<String> loadAllowedServiceSignatures(
		long companyId, List<String> serviceAccessPolicyNames) {

		Set<String> allowedServiceSignatures = new HashSet<>();

		for (String name : serviceAccessPolicyNames) {
			try {
				SAPEntry sapEntry = _sapEntryLocalService.getSAPEntry(
					companyId, name);

				if (!sapEntry.isEnabled()) {
					continue;
				}

				allowedServiceSignatures.addAll(
					sapEntry.getAllowedServiceSignaturesList());
			}
			catch (PortalException pe) {
				throw new SecurityException(pe);
			}
		}

		return allowedServiceSignatures;
	}

	protected boolean matches(
		String className, String methodName, String allowedServiceSignature) {

		String allowedClassName = null;
		String allowedMethodName = null;

		int index = allowedServiceSignature.indexOf(CharPool.POUND);

		if (index > -1) {
			allowedClassName = allowedServiceSignature.substring(0, index);
			allowedMethodName = allowedServiceSignature.substring(index + 1);
		}
		else {
			allowedClassName = allowedServiceSignature;
		}

		boolean wildcardMatchClass = false;

		if (Validator.isNotNull(allowedClassName) &&
			allowedClassName.endsWith(StringPool.STAR)) {

			allowedClassName = allowedClassName.substring(
				0, allowedClassName.length() - 1);
			wildcardMatchClass = true;
		}

		boolean wildcardMatchMethod = false;

		if (Validator.isNotNull(allowedMethodName) &&
			allowedMethodName.endsWith(StringPool.STAR)) {

			allowedMethodName = allowedMethodName.substring(
				0, allowedMethodName.length() - 1);
			wildcardMatchMethod = true;
		}

		if (Validator.isNotNull(allowedClassName) &&
			Validator.isNotNull(allowedMethodName)) {

			if (wildcardMatchClass && !className.startsWith(allowedClassName)) {
				return false;
			}
			else if (!wildcardMatchClass &&
					 !className.equals(allowedClassName)) {

				return false;
			}

			if (wildcardMatchMethod &&
				!methodName.startsWith(allowedMethodName)) {

				return false;
			}
			else if (!wildcardMatchMethod &&
					 !methodName.equals(allowedMethodName)) {

				return false;
			}

			return true;
		}
		else if (Validator.isNotNull(allowedClassName)) {
			if (wildcardMatchClass && !className.startsWith(allowedClassName)) {
				return false;
			}
			else if (!wildcardMatchClass &&
					 !className.equals(allowedClassName)) {

				return false;
			}

			return true;
		}
		else if (Validator.isNotNull(allowedMethodName)) {
			if (wildcardMatchMethod &&
				!methodName.startsWith(allowedMethodName)) {

				return false;
			}
			else if (!wildcardMatchMethod &&
					 !methodName.equals(allowedMethodName)) {

				return false;
			}

			return true;
		}
		else if (wildcardMatchClass && Validator.isNull(allowedClassName)) {
			return true;
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setConfigurationFactory(
		ConfigurationFactory configurationFactory) {

		_configurationFactory = configurationFactory;
	}

	@Reference(unbind = "-")
	protected void setSAPEntryLocalService(
		SAPEntryLocalService sapEntryLocalService) {

		_sapEntryLocalService = sapEntryLocalService;
	}

	private volatile ConfigurationFactory _configurationFactory;
	private SAPEntryLocalService _sapEntryLocalService;

}