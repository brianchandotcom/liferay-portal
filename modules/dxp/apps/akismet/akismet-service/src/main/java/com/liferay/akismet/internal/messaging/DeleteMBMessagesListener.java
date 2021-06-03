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

package com.liferay.akismet.internal.messaging;

import com.liferay.akismet.internal.configuration.AkismetServiceConfiguration;
import com.liferay.message.boards.exception.NoSuchMessageException;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jamie Sammons
 */
@Component(
	configurationPid = "com.liferay.akismet.internal.configuration.AkismetServiceConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	property = "cron.expression=0 0 0 * * ?",
	service = DeleteMBMessagesListener.class
)
public class DeleteMBMessagesListener extends BaseMessageListener {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		modified(properties);

		String cronExpression = GetterUtil.getString(
			properties.get("cron.expression"), _DEFAULT_CRON_EXPRESSION);

		Class<?> clazz = getClass();

		Trigger trigger = _triggerFactory.createTrigger(
			clazz.getName(), clazz.getName(), new Date(), null, cronExpression);

		_schedulerEntryImpl = new SchedulerEntryImpl(clazz.getName(), trigger);

		if (_initialized) {
			deactivate();
		}

		_schedulerEngineHelper.register(
			this, _schedulerEntryImpl, DestinationNames.SCHEDULER_DISPATCH);

		_initialized = true;
	}

	@Deactivate
	protected void deactivate() {
		if (_initialized) {
			try {
				if (_log.isDebugEnabled()) {
					_log.debug("Unscheduling trigger");
				}

				_schedulerEngineHelper.unschedule(
					_schedulerEntryImpl, StorageType.MEMORY_CLUSTERED);
			}
			catch (SchedulerException schedulerException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to unschedule trigger", schedulerException);
				}
			}

			_schedulerEngineHelper.unregister(this);
		}

		_initialized = false;
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		Class<?> clazz = _mbMessageLocalService.getClass();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			MBMessage.class, clazz.getClassLoader());

		Property statusProperty = PropertyFactoryUtil.forName("status");

		dynamicQuery.add(statusProperty.eq(WorkflowConstants.STATUS_DENIED));

		Property statusDateProperty = PropertyFactoryUtil.forName("statusDate");

		long retainSpamTime =
			_akismetServiceConfiguration.akismetRetainSpamTime() * Time.DAY;

		dynamicQuery.add(
			statusDateProperty.lt(
				new Date(System.currentTimeMillis() - retainSpamTime)));

		List<MBMessage> mbMessages = _mbMessageLocalService.dynamicQuery(
			dynamicQuery);

		for (MBMessage mbMessage : mbMessages) {
			try {
				_mbMessageLocalService.deleteMBMessage(
					mbMessage.getMessageId());
			}
			catch (NoSuchMessageException noSuchMessageException) {
				if (_log.isDebugEnabled()) {
					_log.debug(noSuchMessageException, noSuchMessageException);
				}
			}
		}
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		_akismetServiceConfiguration = ConfigurableUtil.createConfigurable(
			AkismetServiceConfiguration.class, properties);
	}

	private static final String _DEFAULT_CRON_EXPRESSION = "0 0 0 * * ?";

	private static final Log _log = LogFactoryUtil.getLog(
		DeleteMBMessagesListener.class);

	private volatile AkismetServiceConfiguration _akismetServiceConfiguration;
	private volatile boolean _initialized;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	private volatile SchedulerEntryImpl _schedulerEntryImpl;

	@Reference
	private TriggerFactory _triggerFactory;

}