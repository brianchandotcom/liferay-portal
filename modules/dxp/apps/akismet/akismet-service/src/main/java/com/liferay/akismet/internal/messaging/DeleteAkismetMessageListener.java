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
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.util.Time;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jamie Sammons
 */
@Component(
	configurationPid = "com.liferay.akismet.internal.configuration.AkismetServiceConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	service = SchedulerJobConfiguration.class
)
public class DeleteAkismetMessageListener implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutor() {
		return () -> {
			int reportableTime =
				_akismetServiceConfiguration.akismetReportableTime();

			_akismetEntryLocalService.deleteAkismetEntry(
				new Date(
					System.currentTimeMillis() - (reportableTime * Time.DAY)));
		};
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration("0 0 0 * * ?");
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_akismetServiceConfiguration = ConfigurableUtil.createConfigurable(
			AkismetServiceConfiguration.class, properties);
	}

	@Reference
	private AkismetEntryLocalService _akismetEntryLocalService;

	private AkismetServiceConfiguration _akismetServiceConfiguration;

}