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

package com.liferay.portal.pop;

import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.pop.bundle.popserverutil.TestMessageListener;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portal.util.test.AtomicState;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Philip Jones
 */
public class POPServerUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.popserverutil"));

	@BeforeClass
	public static void setUpClass() {
		_atomicState = new AtomicState();
	}

	@AfterClass
	public static void tearDownClass() {
		_atomicState.close();
	}

	@Test
	public void testGetListeners() throws Exception {

		POPServerUtil.start();

		Assert.assertTrue(_atomicState.isSet());

		List<MessageListener> messageListeners = POPServerUtil.getListeners();

		if (messageListeners != null) {
			for (MessageListener messageListenerWrapper : messageListeners) {
				
				MessageListener messageListener =
					((MessageListenerWrapper)messageListenerWrapper).
						getMessageListener();

				Class<?> clazz = messageListener.getClass();

				if (TestMessageListener.class.getName().equals(
					clazz.getName())) {
					
					return;
				}
			}
		}

		Assert.fail();
	}

	private static AtomicState _atomicState;

}