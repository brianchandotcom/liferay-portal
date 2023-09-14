/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.internal.search.index.creation.instance.lifecycle;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.workflow.metrics.internal.search.index.creation.helper.WorkflowMetricsIndexCreator;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class WorkflowMetricsIndexPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		_workflowMetricsIndexCreator.createIndex(company);

		String jobName = StringBundler.concat(
			DestinationNames.WORKFLOW_METRICS_REINDEX, StringPool.SLASH,
			company.getCompanyId());

		SchedulerResponse schedulerResponse =
			_schedulerEngineHelper.getScheduledJob(
				jobName, jobName, StorageType.MEMORY);

		if (schedulerResponse != null) {
			return;
		}

		Message message = new Message();

		message.put("companyId", company.getCompanyId());

		_schedulerEngineHelper.schedule(
			_triggerFactory.createTrigger(
				jobName, jobName, new Date(), null, 5, TimeUnit.SECOND),
			StorageType.MEMORY, null, DestinationNames.WORKFLOW_METRICS_REINDEX,
			message);
	}

	@Override
	public void portalInstanceUnregistered(Company company) throws Exception {
		_workflowMetricsIndexCreator.removeIndex(company);
	}

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

	@Reference
	private WorkflowMetricsIndexCreator _workflowMetricsIndexCreator;

}