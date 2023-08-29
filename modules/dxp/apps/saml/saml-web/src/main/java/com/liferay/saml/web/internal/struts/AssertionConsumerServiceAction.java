/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.web.internal.struts;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.ContactNameException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.exception.UserEmailAddressException.MustNotUseCompanyMx;
import com.liferay.portal.kernel.exception.UserScreenNameException;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.helper.RelayStateHelper;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.exception.AuthnAgeException;
import com.liferay.saml.runtime.exception.EntityInteractionException;
import com.liferay.saml.runtime.exception.SubjectException;
import com.liferay.saml.runtime.servlet.profile.WebSsoProfile;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(property = "path=/portal/saml/acs", service = StrutsAction.class)
public class AssertionConsumerServiceAction extends BaseSamlStrutsAction {

	@Override
	public boolean isEnabled() {
		if (_samlProviderConfigurationHelper.isRoleSp()) {
			return _samlProviderConfigurationHelper.isEnabled();
		}

		return false;
	}

	@Override
	protected String doExecute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		try {
			_webSsoProfile.processResponse(
				httpServletRequest, httpServletResponse);
		}
		catch (EntityInteractionException entityInteractionException) {
			HttpServletRequest originalHttpServletRequest =
				_portal.getOriginalServletRequest(httpServletRequest);

			HttpSession httpSession = originalHttpServletRequest.getSession();

			httpSession.setAttribute(
				com.liferay.saml.web.internal.constants.SamlWebKeys.
					SAML_SSO_ERROR_ENTITY_ID,
				entityInteractionException.getEntityId());
			httpSession.setAttribute(
				SamlWebKeys.SAML_SUBJECT_NAME_ID,
				entityInteractionException.getNameIdValue());

			Throwable causeThrowable = entityInteractionException.getCause();

			String error = StringPool.BLANK;

			if (causeThrowable instanceof AuthnAgeException) {
				error = AuthnAgeException.class.getSimpleName();
			}
			else if (causeThrowable instanceof ContactNameException) {
				error = ContactNameException.class.getSimpleName();
			}
			else if (causeThrowable instanceof SubjectException) {
				error = SubjectException.class.getSimpleName();
			}
			else if (causeThrowable instanceof UserEmailAddressException) {
				if (causeThrowable instanceof MustNotUseCompanyMx) {
					error = MustNotUseCompanyMx.class.getSimpleName();
				}
				else {
					error = UserEmailAddressException.class.getSimpleName();
				}
			}
			else if (causeThrowable instanceof UserScreenNameException) {
				error = UserScreenNameException.class.getSimpleName();
			}
			else {
				Class<?> clazz = causeThrowable.getClass();

				error = clazz.getSimpleName();
			}

			httpSession.setAttribute(SamlWebKeys.SAML_SSO_ERROR, error);

			String redirect = _relayStateHelper.getRedirectFromRelayStateToken(
				ParamUtil.getString(httpServletRequest, "RelayState"));

			redirect = _portal.escapeRedirect(redirect);

			if (Validator.isNull(redirect)) {
				redirect = _portal.getHomeURL(httpServletRequest);
			}

			try {
				httpServletResponse.sendRedirect(redirect);

				return null;
			}
			catch (IOException ioException) {
				throw new SystemException(ioException);
			}
		}

		return null;
	}

	@Reference
	private Portal _portal;

	@Reference
	private RelayStateHelper _relayStateHelper;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	@Reference
	private WebSsoProfile _webSsoProfile;

}