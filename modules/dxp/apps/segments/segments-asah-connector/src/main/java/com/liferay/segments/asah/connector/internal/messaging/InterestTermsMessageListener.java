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
	immediate = true,
	property = "destination.name=" + SegmentsAsahDestinationNames.INTEREST_TERMS,
	service = MessageListener.class
)
public class InterestTermsMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		String userId = (String)message.getPayload();

		if (Validator.isNull(userId)) {
			return;
		}

		_interestTermsChecker.checkInterestTerms(userId);
	}

	@Reference
	private InterestTermsChecker _interestTermsChecker;

}