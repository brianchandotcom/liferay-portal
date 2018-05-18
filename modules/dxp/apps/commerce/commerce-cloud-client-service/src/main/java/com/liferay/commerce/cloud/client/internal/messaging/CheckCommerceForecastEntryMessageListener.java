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

package com.liferay.commerce.cloud.client.internal.messaging;

import com.liferay.commerce.cloud.client.configuration.CommerceCloudClientConfiguration;
import com.liferay.commerce.cloud.client.constants.CommerceCloudClientConstants;
import com.liferay.commerce.cloud.client.util.CommerceCloudClient;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	configurationPid = CommerceCloudClientConstants.CONFIGURATION_PID,
	immediate = true, service = CheckCommerceForecastEntryMessageListener.class
)
public class CheckCommerceForecastEntryMessageListener
	extends BaseMessageListener {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_commerceCloudClientConfiguration = ConfigurableUtil.createConfigurable(
			CommerceCloudClientConfiguration.class, properties);

		int interval =
			_commerceCloudClientConfiguration.forecastingEntriesCheckInterval();

		if (!_commerceCloudClientConfiguration.forecastingEnabled() ||
			_commerceCloudClientConfiguration.pushSynchronizationEnabled() ||
			(interval <= 0)) {

			return;
		}

		Class<?> clazz = getClass();

		String className = clazz.getName();

		Trigger trigger = _triggerFactory.createTrigger(
			className, className, null, null, interval, TimeUnit.MINUTE);

		SchedulerEntry schedulerEntry = new SchedulerEntryImpl(
			className, trigger);

		_schedulerEngineHelper.register(
			this, schedulerEntry, DestinationNames.SCHEDULER_DISPATCH);
	}

	@Deactivate
	protected void deactivate() {
		if (_commerceCloudClientConfiguration.forecastingEnabled()) {
			_schedulerEngineHelper.unregister(this);
		}
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		_commerceCloudClient.checkCommerceForecastEntries();
	}

	@Reference
	private CommerceCloudClient _commerceCloudClient;

	private volatile CommerceCloudClientConfiguration
		_commerceCloudClientConfiguration;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

}