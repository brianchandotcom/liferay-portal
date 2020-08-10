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

package com.liferay.commerce.data.integration.service.impl;

import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcess;
import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcessLog;
import com.liferay.commerce.data.integration.service.base.CommerceDataIntegrationProcessLogServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;

import java.util.Date;
import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceDataIntegrationProcessLogServiceImpl
	extends CommerceDataIntegrationProcessLogServiceBaseImpl {

	@Override
	public CommerceDataIntegrationProcessLog
			addCommerceDataIntegrationProcessLog(
				long userId, long commerceDataIntegrationProcessId,
				String error, String output, int status, Date startDate,
				Date endDate)
		throws PortalException {

		_commerceDataIntegrationProcessModelResourcePermission.check(
			getPermissionChecker(), commerceDataIntegrationProcessId,
			ActionKeys.UPDATE);

		return commerceDataIntegrationProcessLogLocalService.
			addCommerceDataIntegrationProcessLog(
				userId, commerceDataIntegrationProcessId, error, output, status,
				startDate, endDate);
	}

	@Override
	public void deleteCommerceDataIntegrationProcessLog(
			long cDataIntegrationProcessLogId)
		throws PortalException {

		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog =
			commerceDataIntegrationProcessLogLocalService.
				getCommerceDataIntegrationProcessLog(
					cDataIntegrationProcessLogId);

		_commerceDataIntegrationProcessModelResourcePermission.check(
			getPermissionChecker(),
			commerceDataIntegrationProcessLog.getCDataIntegrationProcessId(),
			ActionKeys.UPDATE);

		commerceDataIntegrationProcessLogLocalService.
			deleteCommerceDataIntegrationProcessLog(
				commerceDataIntegrationProcessLog);
	}

	@Override
	public CommerceDataIntegrationProcessLog
			getCommerceDataIntegrationProcessLog(
				long cDataIntegrationProcessLogId)
		throws PortalException {

		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog =
			commerceDataIntegrationProcessLogLocalService.
				getCommerceDataIntegrationProcessLog(
					cDataIntegrationProcessLogId);

		_commerceDataIntegrationProcessModelResourcePermission.check(
			getPermissionChecker(),
			commerceDataIntegrationProcessLog.getCDataIntegrationProcessId(),
			ActionKeys.VIEW);

		return commerceDataIntegrationProcessLog;
	}

	@Override
	public List<CommerceDataIntegrationProcessLog>
			getCommerceDataIntegrationProcessLogs(
				long commerceDataIntegrationProcessId, int start, int end)
		throws PortalException {

		_commerceDataIntegrationProcessModelResourcePermission.check(
			getPermissionChecker(), commerceDataIntegrationProcessId,
			ActionKeys.VIEW);

		return commerceDataIntegrationProcessLogLocalService.
			getCommerceDataIntegrationProcessLogs(
				commerceDataIntegrationProcessId, start, end);
	}

	@Override
	public int getCommerceDataIntegrationProcessLogsCount(
			long commerceDataIntegrationProcessId)
		throws PortalException {

		_commerceDataIntegrationProcessModelResourcePermission.check(
			getPermissionChecker(), commerceDataIntegrationProcessId,
			ActionKeys.VIEW);

		return commerceDataIntegrationProcessLogLocalService.
			getCommerceDataIntegrationProcessLogsCount(
				commerceDataIntegrationProcessId);
	}

	@Override
	public CommerceDataIntegrationProcessLog
			updateCommerceDataIntegrationProcessLog(
				long cDataIntegrationProcessLogId, String error, String output,
				int status, Date endDate)
		throws PortalException {

		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog =
			commerceDataIntegrationProcessLogLocalService.
				getCommerceDataIntegrationProcessLog(
					cDataIntegrationProcessLogId);

		_commerceDataIntegrationProcessModelResourcePermission.check(
			getPermissionChecker(),
			commerceDataIntegrationProcessLog.getCDataIntegrationProcessId(),
			ActionKeys.UPDATE);

		return commerceDataIntegrationProcessLogLocalService.
			updateCommerceDataIntegrationProcessLog(
				cDataIntegrationProcessLogId, error, output, status, endDate);
	}

	private static volatile ModelResourcePermission
		<CommerceDataIntegrationProcess>
			_commerceDataIntegrationProcessModelResourcePermission =
				ModelResourcePermissionFactory.getInstance(
					CommerceDataIntegrationProcessServiceImpl.class,
					"_commerceDataIntegrationProcessModelResourcePermission",
					CommerceDataIntegrationProcess.class);

}