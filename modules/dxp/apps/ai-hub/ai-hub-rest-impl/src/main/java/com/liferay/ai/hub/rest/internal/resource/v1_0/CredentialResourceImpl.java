/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.internal.resource.v1_0;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.ai.hub.rest.dto.v1_0.Credential;
import com.liferay.ai.hub.rest.resource.v1_0.CredentialResource;
import com.liferay.ai.hub.util.AccountEntryUtil;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.RoleLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Feliphe Marinho
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/credential.properties",
	scope = ServiceScope.PROTOTYPE, service = CredentialResource.class
)
public class CredentialResourceImpl extends BaseCredentialResourceImpl {

	@Override
	public Credential getCredential() throws Exception {
		AccountEntry accountEntry = AccountEntryUtil.getUserAccountEntry(
			contextUser.getUserId());

		Role role = _roleLocalService.getRole(
			contextCompany.getCompanyId(), "AI Hub Agent Manager");

		AccountRole accountRole =
			_accountRoleLocalService.getAccountRoleByRoleId(role.getRoleId());

		if (!_accountRoleLocalService.hasUserAccountRole(
				accountEntry.getAccountEntryId(),
				accountRole.getAccountRoleId(), contextUser.getUserId())) {

			throw new UnsupportedOperationException();
		}

		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.
				fetchOAuth2ApplicationByExternalReferenceCode(
					accountEntry.getAccountEntryId() +
						"-ai-hub-oauth2-application",
					contextCompany.getCompanyId());

		contextHttpServletResponse.setHeader("Cache-Control", "no-store");

		return new Credential() {
			{
				setClientId(oAuth2Application::getClientId);
				setClientSecret(oAuth2Application::getClientSecret);
			}
		};
	}

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}