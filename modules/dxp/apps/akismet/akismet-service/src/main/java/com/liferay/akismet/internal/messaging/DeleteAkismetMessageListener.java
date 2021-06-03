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
import com.liferay.akismet.service.AkismetEntryLocalService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
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

import java.util.Date;
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
	service = DeleteAkismetMessageListener.class
)
public class DeleteAkismetMessageListener extends BaseMessageListener {

	@Activate
	protected void activate(Map<String, Object> properties) {
		modified(properties);

		String cronExpression = GetterUtil.getString(
			properties.get("cron.expression"), _DEFAULT_CRON_EXPRESSION);

		String className = getClass().getName();

		Trigger trigger = _triggerFactory.createTrigger(
			className, className, new Date(), null, cronExpression);

		_schedulerEntryImpl = new SchedulerEntryImpl(
			getClass().getName(), trigger);

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
	protected void doReceive(Message message) {
		int reportableTime =
			_akismetServiceConfiguration.akismetReportableTime();

		_akismetEntryLocalService.deleteAkismetEntry(
			new Date(System.currentTimeMillis() - (reportableTime * Time.DAY)));
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		_akismetServiceConfiguration = ConfigurableUtil.createConfigurable(
			AkismetServiceConfiguration.class, properties);
	}

	private static final String _DEFAULT_CRON_EXPRESSION = "0 0 0 * * ?";

	private static final Log _log = LogFactoryUtil.getLog(
		DeleteAkismetMessageListener.class);

	@Reference
	private AkismetEntryLocalService _akismetEntryLocalService;

	private volatile AkismetServiceConfiguration _akismetServiceConfiguration;
	private volatile boolean _initialized;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	private SchedulerEntryImpl _schedulerEntryImpl;

	@Reference
	private TriggerFactory _triggerFactory;

}