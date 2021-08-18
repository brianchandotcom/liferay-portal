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

package com.liferay.osb.portal.instance.provisioning.internal.messaging;

import com.liferay.commerce.constants.CommerceDestinationNames;
import com.liferay.osb.portal.instance.provisioning.internal.provider.OSBPortalInstanceProvisioningProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = "destination.name=" + CommerceDestinationNames.ORDER_STATUS,
	service = MessageListener.class
)
public class CommerceOrderStatusMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		long commerceOrderId = message.getLong("commerceOrderId");

		try {
			_osbPortalInstanceProvisioningProvider.provisionPortalInstance(
				commerceOrderId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderStatusMessageListener.class);

	@Reference
	private OSBPortalInstanceProvisioningProvider
		_osbPortalInstanceProvisioningProvider;

}