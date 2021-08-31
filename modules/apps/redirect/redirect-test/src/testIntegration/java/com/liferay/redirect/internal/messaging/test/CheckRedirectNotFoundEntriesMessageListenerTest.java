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

package com.liferay.redirect.internal.messaging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.redirect.model.RedirectNotFoundEntry;
import com.liferay.redirect.service.RedirectNotFoundEntryLocalService;

import java.time.Duration;
import java.time.Instant;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
@Sync
public class CheckRedirectNotFoundEntriesMessageListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testDeletesEntriesOlderThan30Days() throws Exception {
		Instant instant = Instant.now();

		_addOrUpdateRedirectNotFoundEntry(
			"url1", Date.from(instant.minus(Duration.ofDays(31))));

		RedirectNotFoundEntry redirectNotFoundEntry =
			_addOrUpdateRedirectNotFoundEntry(
				"url2", Date.from(instant.minus(Duration.ofDays(29))));

		List<RedirectNotFoundEntry> redirectNotFoundEntries =
			_redirectNotFoundEntryLocalService.getRedirectNotFoundEntries(
				_group.getGroupId(), null, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

		Assert.assertEquals(
			redirectNotFoundEntries.toString(), 2,
			redirectNotFoundEntries.size());

		_checkRedirectNotFoundEntriesMessageListener.receive(new Message());

		redirectNotFoundEntries =
			_redirectNotFoundEntryLocalService.getRedirectNotFoundEntries(
				_group.getGroupId(), null, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

		Assert.assertEquals(
			redirectNotFoundEntries.toString(), 1,
			redirectNotFoundEntries.size());
		Assert.assertEquals(
			redirectNotFoundEntry, redirectNotFoundEntries.get(0));
	}

	@Test
	public void testDeletesEntriesOverflowing1000Elements() throws Exception {
		for (int i = 0; i < 1001; i++) {
			_redirectNotFoundEntryLocalService.addOrUpdateRedirectNotFoundEntry(
				_group, "url" + i);
		}

		Assert.assertEquals(
			1001,
			_redirectNotFoundEntryLocalService.getRedirectNotFoundEntriesCount(
				_group.getGroupId()));

		_checkRedirectNotFoundEntriesMessageListener.receive(new Message());

		Assert.assertEquals(
			1000,
			_redirectNotFoundEntryLocalService.getRedirectNotFoundEntriesCount(
				_group.getGroupId()));
	}

	private RedirectNotFoundEntry _addOrUpdateRedirectNotFoundEntry(
		String url, Date date) {

		RedirectNotFoundEntry redirectNotFoundEntry =
			_redirectNotFoundEntryLocalService.addOrUpdateRedirectNotFoundEntry(
				_group, url);

		redirectNotFoundEntry.setModifiedDate(date);

		return _redirectNotFoundEntryLocalService.updateRedirectNotFoundEntry(
			redirectNotFoundEntry);
	}

	@Inject(
		filter = "component.name=*.CheckRedirectNotFoundEntriesMessageListener"
	)
	private MessageListener _checkRedirectNotFoundEntriesMessageListener;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private RedirectNotFoundEntryLocalService
		_redirectNotFoundEntryLocalService;

}