/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.messaging;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.asah.connector.internal.constants.SegmentsAsahDestinationNames;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sarai Díaz
 */
@Component(
	property = "destination.name=" + SegmentsAsahDestinationNames.INTEREST_TERMS,
	service = MessageListener.class
)
public class InterestTermsMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		String userId = message.getString("userId");

		if (Validator.isNull(userId)) {
			return;
		}

		_interestTermsChecker.checkInterestTerms(
			message.getLong("companyId"), userId);
	}

	@Reference
	private InterestTermsChecker _interestTermsChecker;

}