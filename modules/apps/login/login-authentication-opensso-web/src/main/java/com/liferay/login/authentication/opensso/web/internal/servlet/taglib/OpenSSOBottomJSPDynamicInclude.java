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

package com.liferay.login.authentication.opensso.web.internal.servlet.taglib;

import com.liferay.portal.kernel.exception.ContactNameException;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.sso.OpenSSO;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.servlet.taglib.BaseJSPDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.opensso.configuration.OpenSSOConfiguration;
import com.liferay.portal.security.sso.opensso.constants.OpenSSOConstants;
import com.liferay.portal.security.sso.opensso.constants.OpenSSOWebKeys;
import com.liferay.portal.security.sso.opensso.exception.StrangersNotAllowedException;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stian Sigvartsen
 */
@Component(immediate = true, service = DynamicInclude.class)
public class OpenSSOBottomJSPDynamicInclude extends BaseJSPDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		try {
			OpenSSOConfiguration openSSOConfiguration =
				_configurationProvider.getConfiguration(
					OpenSSOConfiguration.class,
					new CompanyServiceSettingsLocator(
						_portal.getCompanyId(httpServletRequest),
						OpenSSOConstants.SERVICE_NAME));

			if (!openSSOConfiguration.enabled()) {
				return;
			}
		}
		catch (Exception e) {
			_log.error(e, e);

			return;
		}

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		String error = (String)originalHttpServletRequest.getAttribute(
			OpenSSOWebKeys.OPEN_SSO_ERROR);

		if (Validator.isBlank(error)) {
			return;
		}

		originalHttpServletRequest.removeAttribute(
			OpenSSOWebKeys.OPEN_SSO_ERROR);

		if (ArrayUtil.contains(_ERRORS, error)) {
			SessionMessages.add(httpServletRequest, error);
		}

		super.include(httpServletRequest, httpServletResponse, key);
	}

	@Override
	public void register(
		DynamicInclude.DynamicIncludeRegistry dynamicIncludeRegistry) {

		dynamicIncludeRegistry.register("/html/common/themes/bottom.jsp#post");
	}

	@Override
	protected String getJspPath() {
		return "/dynamic_include/com.liferay.portal/opensso_error.jsp";
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.login.authentication.opensso.web)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	private static final String[] _ERRORS = {
		ContactNameException.class.getSimpleName(),
		PrincipalException.MustBeAuthenticated.class.getSimpleName(),
		StrangersNotAllowedException.class.getSimpleName(),
		UserEmailAddressException.MustNotUseCompanyMx.class.getSimpleName()
	};

	private static final Log _log = LogFactoryUtil.getLog(
		OpenSSOBottomJSPDynamicInclude.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private OpenSSO _openSSO;

	@Reference
	private Portal _portal;

}