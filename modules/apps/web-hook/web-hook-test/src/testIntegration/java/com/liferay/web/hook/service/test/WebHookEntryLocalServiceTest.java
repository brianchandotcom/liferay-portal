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

package com.liferay.web.hook.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.web.hook.exception.DuplicateWebHookEntryException;
import com.liferay.web.hook.model.WebHookEntry;
import com.liferay.web.hook.service.WebHookEntryLocalService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo García
 */
@RunWith(Arquillian.class)
public class WebHookEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test(expected = DuplicateWebHookEntryException.class)
	public void testAddWebHookEntryDuplicated() throws Exception {
		Map<Locale, String> nameMap = _getLocalizedMap(
			RandomTestUtil.randomString());

		String destination = RandomTestUtil.randomString();
		String url = "http://abc.com/def";

		_webHookEntries.add(
			_webHookEntryLocalService.addWebHookEntry(
				TestPropsValues.getUserId(), nameMap, destination, url,
				ServiceContextTestUtil.getServiceContext()));

		_webHookEntries.add(
			_webHookEntryLocalService.addWebHookEntry(
				TestPropsValues.getUserId(), nameMap, destination, url,
				ServiceContextTestUtil.getServiceContext()));
	}

	private Map<Locale, String> _getLocalizedMap(String localizedValue) {
		return HashMapBuilder.put(
			LocaleUtil.US, localizedValue
		).build();
	}

	@DeleteAfterTestRun
	private final List<WebHookEntry> _webHookEntries = new ArrayList<>();

	@Inject
	private WebHookEntryLocalService _webHookEntryLocalService;

}