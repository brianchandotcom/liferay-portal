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

import com.liferay.osb.faro.model.FaroPreferences;
import com.liferay.osb.faro.service.FaroPreferencesLocalService;
import com.liferay.osb.faro.web.internal.model.preferences.EmailReportPreferences;
import com.liferay.osb.faro.web.internal.model.preferences.WorkspacePreferences;
import com.liferay.osb.faro.web.internal.util.EmailReportHelper;
import com.liferay.osb.faro.web.internal.util.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.TriggerFactory;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
public abstract class BaseEmailReportMessageListener
	extends BaseMessageListener {

	protected abstract void activate();

	protected abstract void deactivate();

	@Override
	protected void doReceive(Message message) throws Exception {
		for (FaroPreferences faroPreferences :
				faroPreferencesLocalService.getFaroPreferenceses(-1, -1)) {

			WorkspacePreferences workspacePreferences = JSONUtil.readValue(
				faroPreferences.getPreferences(), WorkspacePreferences.class);

			Map<String, EmailReportPreferences> emailReportPreferencesMap =
				workspacePreferences.getEmailReportPreferences(null);

			for (Map.Entry<String, EmailReportPreferences> entry :
					emailReportPreferencesMap.entrySet()) {

				EmailReportPreferences emailReportPreferences =
					entry.getValue();

				if (emailReportPreferences.getEnabled() &&
					Objects.equals(
						emailReportPreferences.getFrequency(),
						getFrequency())) {

					try {
						emailReportHelper.sendEmail(
							entry.getKey(), getFrequency(),
							faroPreferences.getGroupId(),
							faroPreferences.getUserId());
					}
					catch (Exception exception) {
						_log.error(
							String.format(
								"Unable to send %s email for channel ID %s " +
									"and user ID %s",
								getFrequency(), entry.getKey(),
								faroPreferences.getUserId()),
							exception);
					}
				}
			}
		}
	}

	protected abstract String getFrequency();

	@Reference
	protected EmailReportHelper emailReportHelper;

	@Reference
	protected FaroPreferencesLocalService faroPreferencesLocalService;

	@Reference
	protected SchedulerEngineHelper schedulerEngineHelper;

	@Reference
	protected TriggerFactory triggerFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseEmailReportMessageListener.class);

}