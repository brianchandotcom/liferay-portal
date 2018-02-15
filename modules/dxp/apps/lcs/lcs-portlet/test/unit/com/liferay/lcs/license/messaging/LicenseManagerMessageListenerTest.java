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

package com.liferay.lcs.license.messaging;

import com.liferay.lcs.advisor.LCSPortletStateAdvisor;
import com.liferay.lcs.util.LCSUtil;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.license.messaging.LCSPortletState;
import com.liferay.portal.kernel.license.messaging.LicenseManagerMessageType;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.RegistryUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Igor Beslic
 */
@PrepareForTest(
	{
		JSONFactoryUtil.class, LCSUtil.class, MessageBusUtil.class,
		RegistryUtil.class
	}
)
@RunWith(PowerMockRunner.class)
public class LicenseManagerMessageListenerTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		RegistryUtil.setRegistry(new BasicRegistryImpl());

		mockStatic(
			JSONFactoryUtil.class, MessageBusUtil.class, RegistryUtil.class);

		when(RegistryUtil.getRegistry()).thenReturn(null);

		when(
			JSONFactoryUtil.createJSONObject()
		).thenReturn(
			new JSONObjectImpl()
		);

		for (LicenseManagerMessageType licenseManagerMessageType :
				LicenseManagerMessageType.values()) {

			Message message = licenseManagerMessageType.createMessage(
				LCSPortletState.GOOD);

			String json = (String)message.getPayload();

			when(
				JSONFactoryUtil.createJSONObject(json)
			).thenReturn(
				new JSONObjectImpl(json)
			);
		}
	}

	@Test
	public void testCreateResponseMessage() throws Exception {
		LCSPortletStateAdvisor lcsPortletStateAdvisor = spy(
			new LCSPortletStateAdvisor());

		doReturn(
			LCSPortletState.GOOD
		).when(
			lcsPortletStateAdvisor
		).getLCSPortletState(
			Boolean.TRUE
		);

		List<LicenseManagerBaseMessageListener>
			licenseManagerBaseMessageListeners = new ArrayList<>();

		LicenseManagerValidateLCSMessageListener
			licenseManagerValidateLCSMessageListener = spy(
				new LicenseManagerValidateLCSMessageListener());

		licenseManagerValidateLCSMessageListener.setLCSPortletStateAdvisor(
			lcsPortletStateAdvisor);

		licenseManagerBaseMessageListeners.add(
			licenseManagerValidateLCSMessageListener);

		LicenseManagerValidateSubscriptionMessageListener
			licenseManagerValidateSubscriptionMessageListener = spy(
				new LicenseManagerValidateSubscriptionMessageListener());

		licenseManagerValidateSubscriptionMessageListener.
			setLcsPortletStateAdvisor(lcsPortletStateAdvisor);

		licenseManagerBaseMessageListeners.add(
			licenseManagerValidateSubscriptionMessageListener);

		for (LicenseManagerMessageType licenseManagerMessageType :
				LicenseManagerMessageType.values()) {

			Message message = licenseManagerMessageType.createMessage(
				LCSPortletState.GOOD);

			for (MessageListener messageListener :
					licenseManagerBaseMessageListeners) {

				messageListener.receive(message);
			}
		}

		for (LicenseManagerBaseMessageListener
				curLicenseManagerBaseMessageListener :
					licenseManagerBaseMessageListeners) {

			Mockito.verify(
				curLicenseManagerBaseMessageListener, Mockito.times(1)
			).createResponseMessage(
				Matchers.any(JSONObject.class)
			);
		}

		Mockito.verify(
			lcsPortletStateAdvisor, Mockito.times(2)
		).getLCSPortletState(
			Boolean.TRUE
		);
	}

}