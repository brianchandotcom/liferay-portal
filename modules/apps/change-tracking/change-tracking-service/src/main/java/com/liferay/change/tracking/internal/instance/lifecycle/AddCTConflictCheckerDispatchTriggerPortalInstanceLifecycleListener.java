/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.instance.lifecycle;

import com.liferay.change.tracking.internal.dispatch.executor.CTConflictCheckerDispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskClusterMode;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.UserLocalService;

import java.time.LocalDateTime;

import java.util.TimeZone;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class AddCTConflictCheckerDispatchTriggerPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		DispatchTrigger dispatchTrigger =
			_dispatchTriggerLocalService.fetchDispatchTrigger(
				company.getCompanyId(),
				CTConflictCheckerDispatchTaskExecutor.KEY);

		if (dispatchTrigger != null) {
			return;
		}

		dispatchTrigger = _dispatchTriggerLocalService.addDispatchTrigger(
			null, _userLocalService.getGuestUserId(company.getCompanyId()),
			_dispatchTaskExecutor, CTConflictCheckerDispatchTaskExecutor.KEY,
			null, CTConflictCheckerDispatchTaskExecutor.KEY, true);

		TimeZone timeZone = company.getTimeZone();

		LocalDateTime localDateTime = LocalDateTime.now(timeZone.toZoneId());

		_dispatchTriggerLocalService.updateDispatchTrigger(
			dispatchTrigger.getDispatchTriggerId(), true, "0 0 0 * * ?",
			DispatchTaskClusterMode.valueOf(
				dispatchTrigger.getDispatchTaskClusterMode()),
			0, 0, 0, 0, 0, true, false, localDateTime.getMonthValue() - 1,
			localDateTime.getDayOfMonth(), localDateTime.getYear(),
			localDateTime.getHour(), localDateTime.getMinute(),
			timeZone.getID());
	}

	@Reference(
		target = "(dispatch.task.executor.type=" + CTConflictCheckerDispatchTaskExecutor.KEY + ")"
	)
	private DispatchTaskExecutor _dispatchTaskExecutor;

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

	@Reference
	private UserLocalService _userLocalService;

}