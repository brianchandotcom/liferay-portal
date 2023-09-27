/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.jaxrs.application;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.validator.AccountEntryEmailAddressValidator;
import com.liferay.account.validator.AccountEntryEmailAddressValidatorFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/com-liferay-account-admin-web",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=com.liferay.account.admin.web.internal.jaxrs.application.AccountAdminApplication",
		"auth.verifier.auth.verifier.PortalSessionAuthVerifier.urls.includes=/*",
		"auth.verifier.guest.allowed=false", "liferay.oauth2=false"
	},
	service = Application.class
)
public class AccountAdminApplication extends Application {

	public Set<Object> getSingletons() {
		return Collections.singleton(this);
	}

	@Path("/validate-email-address")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response validate(@Context HttpServletRequest httpServletRequest) {
		String emailAddress = ParamUtil.getString(
			httpServletRequest, "emailAddress");

		if (Validator.isNull(emailAddress)) {
			return Response.ok(
			).build();
		}

		long accountEntryId = ParamUtil.getLong(
			httpServletRequest, "accountEntryId");

		AccountEntry accountEntry = _accountEntryLocalService.fetchAccountEntry(
			accountEntryId);

		String[] accountDomains = new String[0];

		if ((accountEntry != null) && accountEntry.isRestrictMembership()) {
			accountDomains = accountEntry.getDomainsArray();
		}

		AccountEntryEmailAddressValidator accountEntryEmailAddressValidator =
			_accountEntryEmailAddressValidatorFactory.create(
				_portal.getCompanyId(httpServletRequest), accountDomains);

		String errorMessage = null;

		if (!accountEntryEmailAddressValidator.isValidEmailAddressFormat(
				emailAddress)) {

			errorMessage = "x-is-not-a-valid-email-address";
		}
		else if (!accountEntryEmailAddressValidator.isValidDomain(
					emailAddress)) {

			errorMessage = "x-has-an-invalid-email-domain";
		}

		if (Validator.isNotNull(errorMessage)) {
			errorMessage = _language.format(
				httpServletRequest, errorMessage, emailAddress, false);
		}

		return Response.ok(
			JSONUtil.put(
				"errorMessage", errorMessage
			).toString(),
			MediaType.APPLICATION_JSON_TYPE
		).build();
	}

	@Reference
	private AccountEntryEmailAddressValidatorFactory
		_accountEntryEmailAddressValidatorFactory;

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}