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

package com.liferay.web.hook.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.web.hook.model.WebHookEntry;
import com.liferay.web.hook.service.WebHookEntryLocalService;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Eduardo García
 */
@RunWith(Arquillian.class)
public class WebHookMessageListenerRegistrarTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testRegisterMessageListener() throws Exception {
		_webHookEntry = _webHookEntryLocalService.addWebHookEntry(
			TestPropsValues.getUserId(),
			_getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext());

		Thread.sleep(500);

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		Collection<ServiceReference<MessageListener>> serviceReferences =
			bundleContext.getServiceReferences(
				MessageListener.class,
				"(destination.name=" + _webHookEntry.getDestination() + ")");

		Assert.assertEquals(
			serviceReferences.toString(), 1, serviceReferences.size());
	}

	private Map<Locale, String> _getLocalizedMap(String localizedValue) {
		return HashMapBuilder.put(
			LocaleUtil.US, localizedValue
		).build();
	}

	@DeleteAfterTestRun
	private WebHookEntry _webHookEntry;

	@Inject
	private WebHookEntryLocalService _webHookEntryLocalService;

}