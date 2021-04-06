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

package com.liferay.saml.web.internal.portlet.action;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.ContactNameException;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.exception.UserEmailAddressException.MustNotUseCompanyMx;
import com.liferay.portal.kernel.exception.UserScreenNameException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.constants.SamlPortletKeys;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.exception.AuthnAgeException;
import com.liferay.saml.runtime.exception.EntityInteractionException;
import com.liferay.saml.runtime.exception.SubjectException;
import com.liferay.saml.runtime.servlet.profile.WebSsoProfile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	immediate = true,
	property = {
		"auth.token.ignore.mvc.action=true",
		"javax.portlet.name=" + SamlPortletKeys.SAML,
		"mvc.command.name=/saml/assertion_consumer_service"
	},
	service = MVCActionCommand.class
)
public class AssertionConsumerServiceMVCActionCommand
	extends BaseSamlMVCActionCommand {

	@Override
	public boolean isEnabled() {
		if (super.isEnabled()) {
			return samlProviderConfigurationHelper.isRoleSp();
		}

		return false;
	}

	@Override
	@Reference(unbind = "-")
	public void setPortal(Portal portal) {
		super.setPortal(portal);
	}

	@Override
	@Reference(unbind = "-")
	public void setSamlProviderConfigurationHelper(
		SamlProviderConfigurationHelper samlProviderConfigurationHelper) {

		super.setSamlProviderConfigurationHelper(
			samlProviderConfigurationHelper);
	}

	@Override
	protected void doProcessAction(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		try {
			_webSsoProfile.processResponse(
				httpServletRequest, httpServletResponse);
		}
		catch (EntityInteractionException entityInteractionException) {
			HttpSession httpSession = httpServletRequest.getSession();

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

			String redirect = ParamUtil.getString(
				httpServletRequest, "RelayState");

			redirect = portal.escapeRedirect(redirect);

			if (Validator.isNull(redirect)) {
				redirect = portal.getHomeURL(httpServletRequest);
			}

			httpServletResponse.sendRedirect(redirect);
		}
	}

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

	@Reference
	private WebSsoProfile _webSsoProfile;

}