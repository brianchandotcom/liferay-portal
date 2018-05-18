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

package com.liferay.commerce.cloud.client.internal.messaging;

import com.liferay.commerce.cloud.client.configuration.CommerceCloudClientConfiguration;
import com.liferay.commerce.cloud.client.constants.CommerceCloudClientConstants;
import com.liferay.commerce.cloud.client.service.CommerceCloudForecastOrderLocalService;
import com.liferay.commerce.constants.CommerceDestinationNames;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	configurationPid = CommerceCloudClientConstants.CONFIGURATION_PID,
	immediate = true,
	property = "destination.name=" + CommerceDestinationNames.ORDER_STATUS,
	service = MessageListener.class
)
public class CommerceOrderStatusMessageListener extends BaseMessageListener {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_commerceCloudClientConfiguration = ConfigurableUtil.createConfigurable(
			CommerceCloudClientConfiguration.class, properties);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		int orderStatus = message.getInteger("orderStatus");

		if (orderStatus ==
				_commerceCloudClientConfiguration.forecastingOrderStatus()) {

			long commerceOrderId = message.getLong("commerceOrderId");

			_commerceCloudForecastOrderLocalService.
				addCommerceCloudForecastOrder(commerceOrderId);
		}
	}

	private volatile CommerceCloudClientConfiguration
		_commerceCloudClientConfiguration;

	@Reference
	private CommerceCloudForecastOrderLocalService
		_commerceCloudForecastOrderLocalService;

}