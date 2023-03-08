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
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;
import java.util.List;
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
public class DeleteMBMessagesListener implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutor() {
		return () -> {
			Class<?> clazz = _mbMessageLocalService.getClass();

			DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
				MBMessage.class, clazz.getClassLoader());

			Property statusProperty = PropertyFactoryUtil.forName("status");

			dynamicQuery.add(
				statusProperty.eq(WorkflowConstants.STATUS_DENIED));

			Property statusDateProperty = PropertyFactoryUtil.forName(
				"statusDate");

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
						_log.debug(noSuchMessageException);
					}
				}
			}
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

	private static final Log _log = LogFactoryUtil.getLog(
		DeleteMBMessagesListener.class);

	private AkismetServiceConfiguration _akismetServiceConfiguration;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

}