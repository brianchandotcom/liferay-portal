/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.punchout.portal.security.auto.login;

import com.liferay.commerce.punchout.portal.security.auto.login.internal.constants.PunchOutAutoLoginConstants;
import com.liferay.commerce.punchout.portal.security.auto.login.module.configuration.PunchOutAccessTokenAutoLoginConfiguration;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.security.auto.login.BaseAutoLogin;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jaclyn Ong
 */
@Component(
	configurationPid = "com.liferay.commerce.punchout.portal.security.auto.login.module.configuration.PunchOutAccessTokenAutoLoginConfiguration",
	immediate = true, service = AutoLogin.class
)
public class PunchOutAccessTokenAutoLogin extends BaseAutoLogin {

	@Override
	protected String[] doLogin(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		long companyId = _portal.getCompanyId(request);

		if (!isEnabled(companyId)) {
			return null;
		}

		return _autoLogin.login(request, response);
	}

	protected boolean isEnabled(long companyId) {
		PunchOutAccessTokenAutoLoginConfiguration
			punchOutAccessTokenAutoLoginConfiguration =
				_getPunchOutAccessTokenAutoLoginConfiguration(companyId);

		if (punchOutAccessTokenAutoLoginConfiguration == null) {
			return false;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Punch out enabled for channel: " +
					punchOutAccessTokenAutoLoginConfiguration.enabled());
		}

		return punchOutAccessTokenAutoLoginConfiguration.enabled();
	}

	@Reference(unbind = "-")
	protected void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		_portal = portal;
	}

	private PunchOutAccessTokenAutoLoginConfiguration
		_getPunchOutAccessTokenAutoLoginConfiguration(long companyId) {

		try {
			return _configurationProvider.getConfiguration(
				PunchOutAccessTokenAutoLoginConfiguration.class,
				new CompanyServiceSettingsLocator(
					companyId, PunchOutAutoLoginConstants.SERVICE_NAME));
		}
		catch (ConfigurationException ce) {
			_log.error(
				"Unable to get punch out access token auto login configuration",
				ce);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PunchOutAccessTokenAutoLogin.class);

	@Reference(
		target = "(&(private.auto.login=true)(type=punchout.access.token))"
	)
	private AutoLogin _autoLogin;

	private ConfigurationProvider _configurationProvider;
	private Portal _portal;

}