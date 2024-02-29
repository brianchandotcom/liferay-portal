/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.helper;

import com.liferay.change.tracking.internal.dispatch.executor.CTConflictCheckerDispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskClusterMode;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.model.DispatchTriggerTable;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = CTConflictCheckerDispatchTriggerHelper.class)
public class CTConflictCheckerDispatchTriggerHelper {

	public void addDispatchTrigger(Company company) throws PortalException {
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
			null, CTConflictCheckerDispatchTaskExecutor.KEY, false);

		TimeZone timeZone = company.getTimeZone();

		Calendar calendar = CalendarFactoryUtil.getCalendar(timeZone);

		_dispatchTriggerLocalService.updateDispatchTrigger(
			dispatchTrigger.getDispatchTriggerId(), true, "0 0 0 * * ?",
			DispatchTaskClusterMode.valueOf(
				dispatchTrigger.getDispatchTaskClusterMode()),
			0, 0, 0, 0, 0, true, false, calendar.get(Calendar.MONTH),
			calendar.get(Calendar.DATE), calendar.get(Calendar.YEAR),
			calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
			timeZone.getID());
	}

	public void deleteDispatchTrigger(long companyId) throws PortalException {
		List<DispatchTrigger> dispatchTriggers =
			_dispatchTriggerLocalService.dslQuery(
				DSLQueryFactoryUtil.select(
					DispatchTriggerTable.INSTANCE
				).from(
					DispatchTriggerTable.INSTANCE
				).where(
					DispatchTriggerTable.INSTANCE.companyId.eq(
						companyId
					).and(
						DispatchTriggerTable.INSTANCE.dispatchTaskExecutorType.
							eq(CTConflictCheckerDispatchTaskExecutor.KEY)
					)
				));

		for (DispatchTrigger dispatchTrigger : dispatchTriggers) {
			_dispatchTriggerLocalService.deleteDispatchTrigger(dispatchTrigger);
		}
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