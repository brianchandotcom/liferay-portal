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

package com.liferay.portal.security.sso.token.internal.events;

import com.liferay.portal.kernel.configuration.module.ModuleConfigurationFactory;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.token.events.LogoutProcessor;
import com.liferay.portal.security.sso.token.events.LogoutProcessorType;
import com.liferay.portal.security.sso.token.internal.configuration.TokenConfiguration;
import com.liferay.portal.security.sso.token.internal.constants.TokenConstants;
import com.liferay.portal.util.PortalUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.security.sso.token.internal.configuration.TokenConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {"key=logout.events.post"}, service = LifecycleAction.class
)
public class TokenLogoutAction extends Action {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response) {
		try {
			long companyId = PortalUtil.getCompanyId(request);

			TokenConfiguration tokenCompanyServiceSettings =
				_moduleConfigurationFactory.getModuleConfiguration(
					TokenConfiguration.class,
					new CompanyServiceSettingsLocator(
						companyId, TokenConstants.SERVICE_NAME));

			if (!tokenCompanyServiceSettings.enabled()) {
				return;
			}

			String[] authenticationCookies =
				tokenCompanyServiceSettings.authenticationCookies();

			if (ArrayUtil.isNotEmpty(authenticationCookies)) {
				LogoutProcessor cookieLogoutProcessor = _logoutProcessors.get(
					LogoutProcessorType.COOKIE);

				if (cookieLogoutProcessor != null) {
					cookieLogoutProcessor.logout(
						request, response, authenticationCookies);
				}
			}

			String logoutRedirectURL =
				tokenCompanyServiceSettings.logoutRedirectURL();

			if (Validator.isNotNull(logoutRedirectURL)) {
				LogoutProcessor redirectLogoutProcessor = _logoutProcessors.get(
					LogoutProcessorType.REDIRECT);

				if (redirectLogoutProcessor != null) {
					redirectLogoutProcessor.logout(
						request, response, logoutRedirectURL);
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.AT_LEAST_ONE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setLogoutProcessor(LogoutProcessor logoutProcessor) {
		_logoutProcessors.put(
			logoutProcessor.getLogoutProcessorType(), logoutProcessor);
	}

	@Reference(unbind = "-")
	protected void setModuleConfigurationFactory(
		ModuleConfigurationFactory moduleConfigurationFactory) {

		_moduleConfigurationFactory = moduleConfigurationFactory;
	}

	protected void unsetLogoutProcessor(LogoutProcessor logoutProcessor) {
		_logoutProcessors.remove(logoutProcessor.getLogoutProcessorType());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TokenLogoutAction.class);

	private final Map<LogoutProcessorType, LogoutProcessor> _logoutProcessors =
		new ConcurrentHashMap<>();
	private volatile ModuleConfigurationFactory _moduleConfigurationFactory;

}