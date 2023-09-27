/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.dao.search;

import com.liferay.account.admin.web.internal.display.AccountUserDisplay;
import com.liferay.account.configuration.AccountEntryEmailDomainsConfiguration;
import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.retriever.AccountUserRetriever;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Pei-Jung Lan
 */
public class AssignableAccountUserDisplaySearchContainerFactory {

	public static SearchContainer<AccountUserDisplay> create(
			long accountEntryId, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			RowChecker rowChecker)
		throws PortalException {

		SearchContainer<AccountUserDisplay> searchContainer =
			new SearchContainer<>(
				liferayPortletRequest,
				PortletURLUtil.getCurrent(
					liferayPortletRequest, liferayPortletResponse),
				null, "no-users-were-found");

		searchContainer.setId("accountUsers");
		searchContainer.setOrderByCol(
			SearchOrderByUtil.getOrderByCol(
				liferayPortletRequest, AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
				"assignable-account-user-order-by-col", "last-name"));
		searchContainer.setOrderByType(
			SearchOrderByUtil.getOrderByType(
				liferayPortletRequest, AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
				"assignable-account-user-order-by-type", "asc"));

		String navigation = ParamUtil.getString(
			liferayPortletRequest, "navigation");

		if (Validator.isNull(navigation)) {
			navigation = _getDefaultNavigation(liferayPortletRequest);
		}

		String keywords = ParamUtil.getString(
			liferayPortletRequest, "keywords", null);

		long[] accountEntryIds = null;

		long accountRoleId = ParamUtil.getLong(
			liferayPortletRequest, "accountRoleId");

		if ((accountEntryId > 0) && (accountRoleId > 0)) {
			accountEntryIds = new long[] {accountEntryId};
		}
		else if (navigation.equals("account-users")) {
			accountEntryIds = new long[] {
				AccountConstants.ACCOUNT_ENTRY_ID_ANY
			};
		}
		else if (navigation.equals("no-assigned-account")) {
			accountEntryIds = new long[0];
		}

		AccountUserRetriever accountUserRetriever =
			_accountUserRetrieverSnapshot.get();

		BaseModelSearchResult<User> baseModelSearchResult =
			accountUserRetriever.searchAccountUsers(
				accountEntryIds, keywords,
				LinkedHashMapBuilder.<String, Serializable>put(
					"emailAddressDomains",
					_getEmailAddressDomains(accountEntryId, navigation)
				).build(),
				WorkflowConstants.STATUS_APPROVED, searchContainer.getStart(),
				searchContainer.getDelta(), searchContainer.getOrderByCol(),
				_isReverseOrder(searchContainer.getOrderByType()));

		searchContainer.setResultsAndTotal(
			() -> TransformUtil.transform(
				baseModelSearchResult.getBaseModels(), AccountUserDisplay::of),
			baseModelSearchResult.getLength());

		searchContainer.setRowChecker(rowChecker);

		return searchContainer;
	}

	private static String _getDefaultNavigation(
		LiferayPortletRequest liferayPortletRequest) {

		try {
			AccountEntryEmailDomainsConfiguration
				accountEntryEmailDomainsConfiguration =
					ConfigurationProviderUtil.getCompanyConfiguration(
						AccountEntryEmailDomainsConfiguration.class,
						PortalUtil.getCompanyId(liferayPortletRequest));

			if (accountEntryEmailDomainsConfiguration.
					enableEmailDomainValidation()) {

				return "valid-domain-users";
			}
		}
		catch (ConfigurationException configurationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(configurationException);
			}
		}

		return "all-users";
	}

	private static String[] _getEmailAddressDomains(
		long accountEntryId, String navigation) {

		if (Objects.equals(navigation, "valid-domain-users")) {
			AccountEntryLocalService accountEntryLocalService =
				_accountEntryLocalServiceSnapshot.get();

			AccountEntry accountEntry =
				accountEntryLocalService.fetchAccountEntry(accountEntryId);

			return StringUtil.split(accountEntry.getDomains());
		}

		return null;
	}

	private static boolean _isReverseOrder(String orderByType) {
		if (Objects.equals(orderByType, "desc")) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssignableAccountUserDisplaySearchContainerFactory.class);

	private static final Snapshot<AccountEntryLocalService>
		_accountEntryLocalServiceSnapshot = new Snapshot<>(
			AssignableAccountUserDisplaySearchContainerFactory.class,
			AccountEntryLocalService.class);
	private static final Snapshot<AccountUserRetriever>
		_accountUserRetrieverSnapshot = new Snapshot<>(
			AssignableAccountUserDisplaySearchContainerFactory.class,
			AccountUserRetriever.class);

}