/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.questions.web.internal.upgrade.v1_0_1;

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
			"getMessageBoardMessageMessageBoardMessagesPage\n",
			headlessDeliveryPackage,
			"MessageBoardMessageResourceImpl#getMessageBoardMessageMyRating\n",
			headlessDeliveryPackage, "MessageBoardMessageResourceImpl#",
			"getMessageBoardThreadMessageBoardMessagesPage\n",
			headlessDeliveryPackage, "MessageBoardMessageResourceImpl#",
			"getSiteMessageBoardMessageByFriendlyUrlPath\n",
			headlessDeliveryPackage, "MessageBoardMessageResourceImpl#",
			"getSiteMessageBoardMessagesPage\n", headlessDeliveryPackage,
			"MessageBoardSectionResourceImpl#getMessageBoardSection\n",
			headlessDeliveryPackage, "MessageBoardSectionResourceImpl#",
			"getSiteMessageBoardSectionsPage\n", headlessDeliveryPackage,
			"MessageBoardSectionResourceImpl#",
			"getMessageBoardSectionMessageBoardSectionsPage\n",
			headlessDeliveryPackage, "MessageBoardThreadResourceImpl#",
			"getMessageBoardSectionMessageBoardThreadsPage\n",
			headlessDeliveryPackage, "MessageBoardThreadResourceImpl#",
			"getMessageBoardThreadsRankedPage\n", headlessDeliveryPackage,
			"MessageBoardThreadResourceImpl#",
			"getMessageBoardThreadMyRating\n", headlessDeliveryPackage,
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