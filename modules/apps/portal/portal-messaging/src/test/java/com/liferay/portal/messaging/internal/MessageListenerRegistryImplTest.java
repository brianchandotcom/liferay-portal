/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.messaging.internal;

import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Dante Wang
 */
public class MessageListenerRegistryImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testRegisterUnregisterMessageListener() {
		_messageListenerRegistryImpl.activate(_bundleContext);

		ServiceRegistration<MessageListener> serviceRegistration1 =
			_bundleContext.registerService(
				MessageListener.class,
				message -> {
				},
				HashMapDictionaryBuilder.put(
					"destination.name", "test"
				).build());
		ServiceRegistration<MessageListener> serviceRegistration2 =
			_bundleContext.registerService(
				MessageListener.class,
				message -> {
				},
				HashMapDictionaryBuilder.put(
					"destination.name", "test"
				).build());

		try {
			List<MessageListener> messageListeners =
				_messageListenerRegistryImpl.getMessageListeners("test");

			Assert.assertEquals(
				messageListeners.toString(), 2, messageListeners.size());

			serviceRegistration2.unregister();

			messageListeners = _messageListenerRegistryImpl.getMessageListeners(
				"test");

			Assert.assertEquals(
				messageListeners.toString(), 1, messageListeners.size());
		}
		finally {
			serviceRegistration1.unregister();

			_messageListenerRegistryImpl.deactivate();
		}
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

	private final MessageListenerRegistryImpl _messageListenerRegistryImpl =
		new MessageListenerRegistryImpl();

}