/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.internal.messaging;

import com.liferay.analytics.message.sender.constants.AnalyticsMessagesDestinationNames;
import com.liferay.analytics.message.sender.constants.AnalyticsMessagesProcessorCommand;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationRegistry;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.service.CompanyLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(
	property = "destination.name=" + AnalyticsMessagesDestinationNames.ANALYTICS_MESSAGES_PROCESSOR,
	service = MessageListener.class
)
public class SendAnalyticsMessagesMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		if (_skipProcess(message)) {
			return;
		}

		_companyLocalService.forEachCompanyId(
			companyId -> _analyticsMessagesHelper.send(companyId));
	}

	@Override
	protected void doReceive(Message message, long companyId) throws Exception {
		if (_skipProcess(message)) {
			return;
		}

		_analyticsMessagesHelper.send(companyId);
	}

	private boolean _skipProcess(Message message) {
		if (FeatureFlagManagerUtil.isEnabled("LRAC-10632") ||
			!_analyticsConfigurationRegistry.isActive()) {

			return true;
		}

		AnalyticsMessagesProcessorCommand analyticsMessagesProcessorCommand =
			(AnalyticsMessagesProcessorCommand)message.get("command");

		if ((analyticsMessagesProcessorCommand != null) &&
			(analyticsMessagesProcessorCommand !=
				AnalyticsMessagesProcessorCommand.SEND)) {

			return true;
		}

		return false;
	}

	@Reference
	private AnalyticsConfigurationRegistry _analyticsConfigurationRegistry;

	@Reference
	private AnalyticsMessagesHelper _analyticsMessagesHelper;

	@Reference
	private CompanyLocalService _companyLocalService;

}