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

import com.liferay.osb.portal.instance.provisioning.internal.provider.OSBPortalInstanceProvisioningProvider;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Eduardo García
 */
@RunWith(MockitoJUnitRunner.class)
public class CommerceOrderStatusMessageListenerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_commerceOrderStatusMessageListener,
			"_osbPortalInstanceProvisioningProvider",
			_osbPortalInstanceProvisioningProvider);
	}

	@Test
	public void testProvisionsPortalInstanceOnMessageToCommerceOrderStatus()
		throws Exception {

		Message message = new Message();

		message.put("commerceOrderId", 1);

		_commerceOrderStatusMessageListener.doReceive(message);

		Mockito.verify(
			_osbPortalInstanceProvisioningProvider, Mockito.times(1)
		).provisionPortalInstance(
			1
		);
	}

	private final CommerceOrderStatusMessageListener
		_commerceOrderStatusMessageListener =
			new CommerceOrderStatusMessageListener();

	@Mock
	private OSBPortalInstanceProvisioningProvider
		_osbPortalInstanceProvisioningProvider;

}