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

package com.liferay.osb.faro.web.internal.messaging;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.osb.faro.engine.client.model.DataSource;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Kong
 */
@Component(service = CleanDLFileEntryMessageListener.class)
public class CleanDLFileEntryMessageListener extends BaseMessageListener {

	@Activate
	protected void activate() {
		try {
			Class<?> clazz = getClass();

			_trigger = _triggerFactory.createTrigger(
				clazz.getName(), clazz.getName(), new Date(), null,
				"0 0 0 * * ?");

			_schedulerEngineHelper.schedule(
				_trigger, StorageType.PERSISTED, null,
				DestinationNames.SCHEDULER_DISPATCH, null);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	@Deactivate
	protected void deactivate() {
		try {
			if (_trigger == null) {
				return;
			}

			_schedulerEngineHelper.unschedule(
				_trigger.getJobName(), _trigger.getGroupName(),
				StorageType.PERSISTED);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		Date date = new Date(System.currentTimeMillis() - Time.DAY);

		DynamicQuery dynamicQuery = _dlFileVersionLocalService.dynamicQuery();

		Property statusProperty = PropertyFactoryUtil.forName("status");

		dynamicQuery.add(statusProperty.eq(WorkflowConstants.STATUS_DRAFT));

		Property versionProperty = PropertyFactoryUtil.forName("version");

		dynamicQuery.add(
			versionProperty.eq(DLFileEntryConstants.VERSION_DEFAULT));

		List<DLFileVersion> dlFileVersions =
			_dlFileVersionLocalService.dynamicQuery(dynamicQuery);

		for (DLFileVersion dlFileVersion : dlFileVersions) {
			if (DateUtil.compareTo(date, dlFileVersion.getCreateDate()) <= 0) {
				continue;
			}

			DLFileEntry dlFileEntry = _dlFileEntryLocalService.getDLFileEntry(
				dlFileVersion.getFileEntryId());

			String className = dlFileEntry.getClassName();

			if (className.equals(DataSource.class.getName())) {
				_dlFileEntryLocalService.deleteFileEntry(
					dlFileVersion.getFileEntryId());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CleanDLFileEntryMessageListener.class);

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFileVersionLocalService _dlFileVersionLocalService;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	private Trigger _trigger;

	@Reference
	private TriggerFactory _triggerFactory;

}