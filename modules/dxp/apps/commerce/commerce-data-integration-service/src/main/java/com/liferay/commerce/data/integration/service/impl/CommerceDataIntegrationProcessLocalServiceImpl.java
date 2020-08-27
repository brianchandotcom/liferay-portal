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

import com.liferay.commerce.data.integration.exception.CommerceDataIntegrationProcessEndDateException;
import com.liferay.commerce.data.integration.exception.CommerceDataIntegrationProcessNameException;
import com.liferay.commerce.data.integration.exception.CommerceDataIntegrationProcessStartDateException;
import com.liferay.commerce.data.integration.exception.DuplicateCommerceDataIntegrationProcessException;
import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcess;
import com.liferay.commerce.data.integration.service.base.CommerceDataIntegrationProcessLocalServiceBaseImpl;
import com.liferay.commerce.data.integration.trigger.CommerceDataIntegrationProcessTriggerHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Date;
import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceDataIntegrationProcessLocalServiceImpl
	extends CommerceDataIntegrationProcessLocalServiceBaseImpl {

	@Override
	public CommerceDataIntegrationProcess addCommerceDataIntegrationProcess(
			long userId, String name, String type,
			UnicodeProperties typeSettingsUnicodeProperties, boolean system)
		throws PortalException {

		// Commerce data integration process

		User user = userLocalService.getUser(userId);

		validate(user.getCompanyId(), 0, name);

		Company company = companyLocalService.getCompany(user.getCompanyId());

		CommerceDataIntegrationProcess commerceDataIntegrationProcess =
			commerceDataIntegrationProcessPersistence.create(
				counterLocalService.increment());

		commerceDataIntegrationProcess.setCompanyId(company.getCompanyId());
		commerceDataIntegrationProcess.setUserId(user.getUserId());
		commerceDataIntegrationProcess.setUserName(user.getFullName());
		commerceDataIntegrationProcess.setName(name);
		commerceDataIntegrationProcess.setType(type);
		commerceDataIntegrationProcess.setTypeSettingsProperties(
			typeSettingsUnicodeProperties);
		commerceDataIntegrationProcess.setSystem(system);

		commerceDataIntegrationProcess =
			commerceDataIntegrationProcessPersistence.update(
				commerceDataIntegrationProcess);

		// Resources

		resourceLocalService.addResources(
			company.getCompanyId(), 0, user.getUserId(),
			CommerceDataIntegrationProcess.class.getName(),
			commerceDataIntegrationProcess.
				getCommerceDataIntegrationProcessId(),
			false, true, true);

		return commerceDataIntegrationProcess;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceDataIntegrationProcess deleteCommerceDataIntegrationProcess(
			CommerceDataIntegrationProcess commerceDataIntegrationProcess)
		throws PortalException {

		if (commerceDataIntegrationProcess.isSystem()) {
			return commerceDataIntegrationProcess;
		}

		// Commerce data integration process logs

		commerceDataIntegrationProcessLogLocalService.
			deleteCommerceDataIntegrationProcessLogs(
				commerceDataIntegrationProcess.
					getCommerceDataIntegrationProcessId());

		// Commerce data integration process

		commerceDataIntegrationProcessPersistence.remove(
			commerceDataIntegrationProcess);

		// Resources

		resourceLocalService.deleteResource(
			commerceDataIntegrationProcess, ResourceConstants.SCOPE_INDIVIDUAL);

		//Trigger

		_commerceDataIntegrationProcessTriggerHelper.deleteScheduledTask(
			commerceDataIntegrationProcess.
				getCommerceDataIntegrationProcessId());

		return commerceDataIntegrationProcess;
	}

	@Override
	public CommerceDataIntegrationProcess deleteCommerceDataIntegrationProcess(
			long commerceDataIntegrationProcessId)
		throws PortalException {

		CommerceDataIntegrationProcess commerceDataIntegrationProcess =
			commerceDataIntegrationProcessPersistence.findByPrimaryKey(
				commerceDataIntegrationProcessId);

		return commerceDataIntegrationProcessLocalService.
			deleteCommerceDataIntegrationProcess(
				commerceDataIntegrationProcess);
	}

	@Override
	public CommerceDataIntegrationProcess fetchCommerceDataIntegrationProcess(
		long companyId, String name) {

		return commerceDataIntegrationProcessPersistence.fetchByC_N(
			companyId, name);
	}

	@Override
	public CommerceDataIntegrationProcess getCommerceDataIntegrationProcess(
			long commerceDataIntegrationProcessId)
		throws PortalException {

		return commerceDataIntegrationProcessPersistence.findByPrimaryKey(
			commerceDataIntegrationProcessId);
	}

	@Override
	public List<CommerceDataIntegrationProcess>
		getCommerceDataIntegrationProcesses(
			long companyId, int start, int end) {

		return commerceDataIntegrationProcessPersistence.findByCompanyId(
			companyId, start, end);
	}

	@Override
	public int getCommerceDataIntegrationProcessesCount(long companyId) {
		return commerceDataIntegrationProcessPersistence.countByCompanyId(
			companyId);
	}

	@Override
	public CommerceDataIntegrationProcess updateCommerceDataIntegrationProcess(
			long commerceDataIntegrationProcessId, String name,
			UnicodeProperties typeSettingsUnicodeProperties)
		throws PortalException {

		CommerceDataIntegrationProcess commerceDataIntegrationProcess =
			commerceDataIntegrationProcessPersistence.findByPrimaryKey(
				commerceDataIntegrationProcessId);

		validate(
			commerceDataIntegrationProcess.getCompanyId(),
			commerceDataIntegrationProcessId, name);

		commerceDataIntegrationProcess.setName(name);
		commerceDataIntegrationProcess.setTypeSettingsProperties(
			typeSettingsUnicodeProperties);

		return commerceDataIntegrationProcessPersistence.update(
			commerceDataIntegrationProcess);
	}

	@Override
	public CommerceDataIntegrationProcess
			updateCommerceDataIntegrationProcessTrigger(
				long commerceDataIntegrationProcessId, boolean active,
				String cronExpression, int startDateMonth, int startDateDay,
				int startDateYear, int startDateHour, int startDateMinute,
				int endDateMonth, int endDateDay, int endDateYear,
				int endDateHour, int endDateMinute, boolean neverEnd)
		throws PortalException {

		// Commerce data integration process

		CommerceDataIntegrationProcess commerceDataIntegrationProcess =
			commerceDataIntegrationProcessPersistence.fetchByPrimaryKey(
				commerceDataIntegrationProcessId);

		Date endDate = null;

		Date startDate = PortalUtil.getDate(
			startDateMonth, startDateDay, startDateYear, startDateHour,
			startDateMinute,
			CommerceDataIntegrationProcessStartDateException.class);

		if (!neverEnd) {
			endDate = PortalUtil.getDate(
				endDateMonth, endDateDay, endDateYear, endDateHour,
				endDateMinute,
				CommerceDataIntegrationProcessEndDateException.class);
		}

		commerceDataIntegrationProcess.setActive(active);
		commerceDataIntegrationProcess.setCronExpression(cronExpression);
		commerceDataIntegrationProcess.setStartDate(startDate);
		commerceDataIntegrationProcess.setEndDate(endDate);

		commerceDataIntegrationProcess =
			commerceDataIntegrationProcessPersistence.update(
				commerceDataIntegrationProcess);

		//Trigger

		if (active) {
			_commerceDataIntegrationProcessTriggerHelper.addScheduledTask(
				commerceDataIntegrationProcessId, cronExpression, startDate,
				endDate);
		}
		else {
			_commerceDataIntegrationProcessTriggerHelper.deleteScheduledTask(
				commerceDataIntegrationProcessId);
		}

		return commerceDataIntegrationProcess;
	}

	protected void validate(
			long companyId, long commerceDataIntegrationProcessId, String name)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new CommerceDataIntegrationProcessNameException(
				"Commerce data integration process name cannot be null for" +
					"company " + companyId);
		}

		CommerceDataIntegrationProcess commerceDataIntegrationProcess =
			commerceDataIntegrationProcessPersistence.fetchByC_N(
				companyId, name);

		if (commerceDataIntegrationProcess == null) {
			return;
		}

		long existingCommerceDataIntegrationProcessId =
			commerceDataIntegrationProcess.
				getCommerceDataIntegrationProcessId();

		if ((commerceDataIntegrationProcessId > 0) &&
			(existingCommerceDataIntegrationProcessId ==
				commerceDataIntegrationProcessId)) {

			return;
		}

		throw new DuplicateCommerceDataIntegrationProcessException(
			"A commerce data integration process with the name " + name +
				" already exists");
	}

	@ServiceReference(type = CommerceDataIntegrationProcessTriggerHelper.class)
	private CommerceDataIntegrationProcessTriggerHelper
		_commerceDataIntegrationProcessTriggerHelper;

}