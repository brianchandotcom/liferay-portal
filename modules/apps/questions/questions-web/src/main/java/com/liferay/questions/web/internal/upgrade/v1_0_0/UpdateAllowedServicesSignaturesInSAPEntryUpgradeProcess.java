/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.questions.web.internal.upgrade.v1_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.service.access.policy.service.SAPEntryService;

/**
 * @author Carlos Correa
 */
public class UpdateAllowedServicesSignaturesInSAPEntryUpgradeProcess
	extends UpgradeProcess {

	public UpdateAllowedServicesSignaturesInSAPEntryUpgradeProcess(
		SAPEntryService sapEntryService) {

		_sapEntryService = sapEntryService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		String headlessDeliveryPackage =
			"com.liferay.headless.delivery.internal.resource.v1_0.";

		String allowedServiceSignatures = StringBundler.concat(
			"com.liferay.headless.admin.taxonomy.internal.resource.v1_0.",
			"KeywordResourceImpl#getKeywordsRankedPage\n",
			"com.liferay.headless.admin.user.internal.resource.v1_0.",
			"SubscriptionResourceImpl#getMyUserAccountSubscriptionsPage\n",
			headlessDeliveryPackage,
			"MessageBoardMessageResourceImpl#getMessageBoardMessage\n",
			headlessDeliveryPackage, "MessageBoardMessageResourceImpl#",
			"getMessageBoardThreadMessageBoardMessagesPage\n",
			headlessDeliveryPackage, "MessageBoardMessageResourceImpl#",
			"getSiteMessageBoardMessageByFriendlyUrlPath\n",
			headlessDeliveryPackage, "MessageBoardMessageResourceImpl#",
			"getSiteMessageBoardMessagesPage\n", headlessDeliveryPackage,
			"MessageBoardSectionResourceImpl#getMessageBoardSection\n",
			headlessDeliveryPackage, "MessageBoardSectionResourceImpl#",
			"getSiteMessageBoardSectionsPage\n", headlessDeliveryPackage,
			"MessageBoardThreadResourceImpl#",
			"getMessageBoardSectionMessageBoardThreadsPage\n",
			headlessDeliveryPackage, "MessageBoardThreadResourceImpl#",
			"getMessageBoardThreadsRankedPage\n", headlessDeliveryPackage,
			"MessageBoardThreadResourceImpl#",
			"getSiteMessageBoardThreadByFriendlyUrlPath\n",
			headlessDeliveryPackage, "MessageBoardThreadResourceImpl#",
			"getSiteMessageBoardThreadsPage");

		runSQL(
			StringBundler.concat(
				"update SAPEntry set allowedServiceSignatures = ",
				StringUtil.quote(allowedServiceSignatures), " where name = ",
				StringUtil.quote("QUESTIONS_SERVICE_ACCESS_POLICY")));
	}

	private final SAPEntryService _sapEntryService;

}