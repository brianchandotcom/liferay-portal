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

package com.liferay.saml.web.internal.servlet.taglib;

import com.liferay.portal.kernel.exception.ContactNameException;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.exception.UserScreenNameException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.servlet.taglib.BaseJSPDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.exception.SubjectException;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stian Sigvartsen
 */
@Component(immediate = true, service = DynamicInclude.class)
public class SAMLBottomJSPDynamicInclude extends BaseJSPDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		SamlProviderConfiguration samlProviderConfiguration =
			_samlProviderConfigurationHelper.getSamlProviderConfiguration();

		if (!samlProviderConfiguration.enabled()) {
			return;
		}

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		HttpSession session = originalHttpServletRequest.getSession();

		String error = (String)session.getAttribute(SamlWebKeys.SAML_SSO_ERROR);

		if (Validator.isBlank(error)) {
			return;
		}

		if (ArrayUtil.contains(_ERRORS, error)) {
			SessionMessages.add(httpServletRequest, error);
		}

		String samlSubjectNameId = (String)session.getAttribute(
			SamlWebKeys.SAML_SUBJECT_SCREEN_NAME);

		if (Validator.isBlank(samlSubjectNameId)) {
			return;
		}

		httpServletRequest.setAttribute(
			SamlWebKeys.SAML_SUBJECT_SCREEN_NAME, samlSubjectNameId);

		session.removeAttribute(SamlWebKeys.SAML_SUBJECT_SCREEN_NAME);

		super.include(httpServletRequest, httpServletResponse, key);
	}

	@Override
	public void register(
		DynamicInclude.DynamicIncludeRegistry dynamicIncludeRegistry) {

		dynamicIncludeRegistry.register("/html/common/themes/bottom.jsp#post");
	}

	@Override
	protected String getJspPath() {
		return "/dynamic_include/com.liferay.portal/saml_error.jsp";
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.saml.web)", unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	private static final String[] _ERRORS = {
		ContactNameException.class.getSimpleName(),
		PrincipalException.MustBeAuthenticated.class.getSimpleName(),
		SubjectException.class.getSimpleName(),
		UserEmailAddressException.class.getSimpleName(),
		UserScreenNameException.class.getSimpleName()
	};

	private static final Log _log = LogFactoryUtil.getLog(
		SAMLBottomJSPDynamicInclude.class);

	@Reference
	private Portal _portal;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

}