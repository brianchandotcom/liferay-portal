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

package com.liferay.commerce.machine.learning.forecast.alert.service.impl;

import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.machine.learning.forecast.alert.constants.CommerceMLForecastAlertActionKeys;
import com.liferay.commerce.machine.learning.forecast.alert.constants.CommerceMLForecastAlertConstants;
import com.liferay.commerce.machine.learning.forecast.alert.model.CommerceMLForecastAlertEntry;
import com.liferay.commerce.machine.learning.forecast.alert.service.base.CommerceMLForecastAlertEntryServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Riccardo Ferrari
 */
public class CommerceMLForecastAlertEntryServiceImpl
	extends CommerceMLForecastAlertEntryServiceBaseImpl {

	@Override
	public List<CommerceMLForecastAlertEntry>
			getAboveThresholdCommerceMLForecastAlertEntries(
				long companyId, long userId, int status, double relativeChange,
				int start, int end)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), GroupConstants.DEFAULT_LIVE_GROUP_ID,
			CommerceMLForecastAlertActionKeys.VIEW_ALERTS);

		long[] commerceAccountIds = _getUserCommerceAccountIds(userId);

		return commerceMLForecastAlertEntryLocalService.
			getAboveThresholdCommerceMLForecastAlertEntries(
				companyId, commerceAccountIds, relativeChange, status, start,
				end);
	}

	@Override
	public int getAboveThresholdCommerceMLForecastAlertEntriesCount(
			long companyId, long userId, int status, double relativeChange)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), GroupConstants.DEFAULT_LIVE_GROUP_ID,
			CommerceMLForecastAlertActionKeys.VIEW_ALERTS);

		long[] commerceAccountIds = _getUserCommerceAccountIds(userId);

		return commerceMLForecastAlertEntryLocalService.
			getAboveThresholdCommerceMLForecastAlertEntriesCount(
				companyId, commerceAccountIds, relativeChange, status);
	}

	@Override
	public List<CommerceMLForecastAlertEntry>
			getBelowThresholdCommerceMLForecastAlertEntries(
				long companyId, long userId, int status, double relativeChange,
				int start, int end)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), GroupConstants.DEFAULT_LIVE_GROUP_ID,
			CommerceMLForecastAlertActionKeys.VIEW_ALERTS);

		long[] commerceAccountIds = _getUserCommerceAccountIds(userId);

		return commerceMLForecastAlertEntryLocalService.
			getBelowThresholdCommerceMLForecastAlertEntries(
				companyId, commerceAccountIds, relativeChange, status, start,
				end);
	}

	@Override
	public int getBelowThresholdCommerceMLForecastAlertEntriesCount(
			long companyId, long userId, int status, double relativeChange)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), GroupConstants.DEFAULT_LIVE_GROUP_ID,
			CommerceMLForecastAlertActionKeys.VIEW_ALERTS);

		long[] commerceAccountIds = _getUserCommerceAccountIds(userId);

		return commerceMLForecastAlertEntryLocalService.
			getBelowThresholdCommerceMLForecastAlertEntriesCount(
				companyId, commerceAccountIds, relativeChange, status);
	}

	@Override
	public List<CommerceMLForecastAlertEntry> getCommerceMLForecastAlertEntries(
			long companyId, long userId, int status, int start, int end)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), GroupConstants.DEFAULT_LIVE_GROUP_ID,
			CommerceMLForecastAlertActionKeys.VIEW_ALERTS);

		long[] commerceAccountIds = _getUserCommerceAccountIds(userId);

		return commerceMLForecastAlertEntryLocalService.
			getCommerceMLForecastAlertEntries(
				companyId, commerceAccountIds, status, start, end);
	}

	@Override
	public int getCommerceMLForecastAlertEntriesCount(
			long companyId, long userId, int status)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), GroupConstants.DEFAULT_LIVE_GROUP_ID,
			CommerceMLForecastAlertActionKeys.VIEW_ALERTS);

		long[] commerceAccountIds = _getUserCommerceAccountIds(userId);

		return commerceMLForecastAlertEntryLocalService.
			getCommerceMLForecastAlertEntriesCount(
				companyId, commerceAccountIds, status);
	}

	@Override
	public CommerceMLForecastAlertEntry updateStatus(
			long commerceMLForecastAlertEntryId, int status)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), GroupConstants.DEFAULT_LIVE_GROUP_ID,
			CommerceMLForecastAlertActionKeys.MANAGE_ALERT_STATUS);

		return commerceMLForecastAlertEntryLocalService.updateStatus(
			getUserId(), commerceMLForecastAlertEntryId, status);
	}

	private long[] _getUserCommerceAccountIds(long userId)
		throws PortalException {

		List<CommerceAccount> commerceAccounts =
			_commerceAccountLocalService.getUserCommerceAccounts(
				userId, null, CommerceAccountConstants.SITE_TYPE_B2X, null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		return ListUtil.toLongArray(
			commerceAccounts, CommerceAccount::getCommerceAccountId);
	}

	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				CommerceMLForecastAlertEntryServiceImpl.class,
				"_portletResourcePermission",
				CommerceMLForecastAlertConstants.RESOURCE_NAME);

	@ServiceReference(type = CommerceAccountLocalService.class)
	private CommerceAccountLocalService _commerceAccountLocalService;

}