/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.internal.resource.v1_0;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.ai.hub.rest.dto.v1_0.ProvisioningRequest;
import com.liferay.ai.hub.rest.resource.v1_0.ProvisioningRequestResource;
import com.liferay.headless.common.spi.service.context.ServiceContextBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Calendar;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Feliphe Marinho
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/provisioning-request.properties",
	scope = ServiceScope.PROTOTYPE, service = ProvisioningRequestResource.class
)
public class ProvisioningRequestResourceImpl
	extends BaseProvisioningRequestResourceImpl {

	@Override
	public void postProvisioning(ProvisioningRequest provisioningRequest)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-62272")) {

			throw new UnsupportedOperationException();
		}

		AccountEntry aiHubAccountEntry =
			_accountEntryService.getAccountEntryByExternalReferenceCode(
				"L_AI_HUB", contextCompany.getCompanyId());

		ServiceContext serviceContext = ServiceContextBuilder.create(
			contextCompany.getGroupId(), contextHttpServletRequest, null
		).build();

		serviceContext.setCompanyId(contextCompany.getCompanyId());
		serviceContext.setUserId(contextUser.getUserId());

		AccountEntry customerAccountEntry = _getOrAddAccountEntry(
			provisioningRequest.getCustomerName(), serviceContext);
		User serviceAccountUser = _getOrAddUser(
			provisioningRequest.getCustomerName() + "-service-account",
			serviceContext);

		_accountEntryUserRelLocalService.updateAccountEntryUserRels(
			new long[] {
				aiHubAccountEntry.getAccountEntryId(),
				customerAccountEntry.getAccountEntryId()
			},
			new long[0], serviceAccountUser.getUserId());
	}

	private AccountEntry _getOrAddAccountEntry(
			String name, ServiceContext serviceContext)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				name, contextCompany.getCompanyId());

		if (accountEntry != null) {
			return accountEntry;
		}

		return _accountEntryService.addAccountEntry(
			name, contextUser.getUserId(),
			AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT, name, null, null,
			null, null, null, AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	private User _getOrAddUser(String screenName, ServiceContext serviceContext)
		throws Exception {

		User user = _userLocalService.fetchUserByScreenName(
			contextCompany.getCompanyId(), screenName);

		if (user != null) {
			return user;
		}

		user = _userLocalService.addUser(
			UserConstants.USER_ID_DEFAULT, contextCompany.getCompanyId(), true,
			null, null, false, screenName,
			screenName + StringPool.AT + contextCompany.getMx(),
			contextAcceptLanguage.getPreferredLocale(), screenName,
			StringPool.BLANK, screenName, 0, 0, true, Calendar.JANUARY, 1, 1970,
			StringPool.BLANK, UserConstants.TYPE_SERVICE_ACCOUNT, null, null,
			null, null, false, serviceContext);

		user.setPasswordReset(false);
		user.setEmailAddressVerified(true);

		return _userLocalService.updateUser(user);
	}

	@Reference
	private AccountEntryService _accountEntryService;

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	private UserLocalService _userLocalService;

}