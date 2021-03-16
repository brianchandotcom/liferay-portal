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

package com.liferay.portal.search.tuning.blueprints.ipstack.internal.dataprovider;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.tuning.blueprints.engine.cache.JSONDataProviderCache;
import com.liferay.portal.search.tuning.blueprints.ipstack.internal.configuration.IPStackConfiguration;
import com.liferay.portal.search.tuning.blueprints.message.Message;
import com.liferay.portal.search.tuning.blueprints.message.Messages;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Petteri Karttunen
 */
public class IPStackDataProviderTest {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		setUpIPStackDataProvider();
		setUpCache();
		setUpConfiguration();
	}

	@Test
	public void testBlankIPAddress() {
		Messages messages = new Messages();

		assertIPAddress("", messages);
		assertMessages(1, messages);
	}

	@Test
	public void testNullIPAddress() {
		Messages messages = new Messages();

		assertIPAddress(null, messages);
		assertMessages(1, messages);
	}

	@Test
	public void testPrivateIPAddress() {
		Messages messages = new Messages();

		assertIPAddress("172.16.0.1", messages);
		assertMessages(1, messages);
	}

	protected void assertIPAddress(String ipAddress, Messages messages) {
		Assert.assertEquals(
			Optional.empty(),
			_ipStackDataProvider.getGeoLocationData(ipAddress, messages));
	}

	protected void assertMessages(int messageCount, Messages messages) {
		List<Message> messageList = messages.getAllMessages();

		Assert.assertEquals(
			messageList.toString(), messageCount, messageList.size());
	}

	protected void setUpCache() {
		Mockito.when(
			_jsonDataProviderCache.getJSONObject(Matchers.anyString())
		).thenReturn(
			null
		);

		ReflectionTestUtil.setFieldValue(
			_ipStackDataProvider, "_jsonDataProviderCache",
			_jsonDataProviderCache);
	}

	protected void setUpConfiguration() {
		Mockito.when(
			_ipStackConfiguration.testIpAddress()
		).thenReturn(
			""
		);

		Mockito.when(
			_ipStackConfiguration.isEnabled()
		).thenReturn(
			true
		);

		Mockito.when(
			_ipStackConfiguration.cacheTimeout()
		).thenReturn(
			0
		);

		Mockito.when(
			_ipStackConfiguration.apiURL()
		).thenReturn(
			"http://www.example.com"
		);

		Mockito.when(
			_ipStackConfiguration.apiKey()
		).thenReturn(
			"23456"
		);

		ReflectionTestUtil.setFieldValue(
			_ipStackDataProvider, "_ipStackConfiguration",
			_ipStackConfiguration);
	}

	protected void setUpIPStackDataProvider() {
		_ipStackDataProvider = new IPStackDataProvider();

		_ipStackDataProvider.activate(new HashMap<String, Object>());
	}

	@Mock
	private IPStackConfiguration _ipStackConfiguration;

	@Mock
	private IPStackDataProvider _ipStackDataProvider;

	@Mock
	private JSONDataProviderCache _jsonDataProviderCache;

}