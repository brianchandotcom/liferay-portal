/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.internal.messaging;

import com.liferay.analytics.message.sender.client.AnalyticsMessageSenderClient;
import com.liferay.analytics.message.storage.model.AnalyticsMessage;
import com.liferay.analytics.message.storage.service.AnalyticsMessageLocalService;
import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.nio.charset.StandardCharsets;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(service = AnalyticsMessagesHelper.class)
public class AnalyticsMessagesHelper {

	public void send(long companyId) throws Exception {
		while (true) {
			List<AnalyticsMessage> analyticsMessages =
				_analyticsMessageLocalService.getAnalyticsMessages(
					companyId, 0, _BATCH_SIZE);

			if (analyticsMessages.isEmpty()) {
				if (_log.isInfoEnabled()) {
					_log.info("Finished processing analytics messages");
				}

				return;
			}

			JSONArray jsonArray = _jsonFactory.createJSONArray();

			for (AnalyticsMessage analyticsMessage : analyticsMessages) {
				String json = new String(
					StreamUtil.toByteArray(
						_analyticsMessageLocalService.openBodyInputStream(
							analyticsMessage.getAnalyticsMessageId())),
					StandardCharsets.UTF_8);

				jsonArray.put(_jsonFactory.createJSONObject(json));
			}

			try {
				_analyticsMessageSenderClient.send(
					jsonArray.toString(), companyId);

				if (_log.isInfoEnabled()) {
					_log.info(
						"Sent " + jsonArray.length() + " analytics messages");
				}
			}
			catch (Exception exception) {
				_log.error(
					"Unable to send analytics messages for company " +
						companyId,
					exception);
			}

			_analyticsMessageLocalService.deleteAnalyticsMessages(
				analyticsMessages);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Deleted " + analyticsMessages.size() +
						" analytics messages");
			}
		}
	}

	private static final int _BATCH_SIZE = 100;

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsMessagesHelper.class);

	@Reference
	private AnalyticsMessageLocalService _analyticsMessageLocalService;

	@Reference
	private AnalyticsMessageSenderClient _analyticsMessageSenderClient;

	@Reference
	private JSONFactory _jsonFactory;

}