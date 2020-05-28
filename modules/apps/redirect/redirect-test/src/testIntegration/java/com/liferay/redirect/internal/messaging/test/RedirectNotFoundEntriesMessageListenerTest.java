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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
@Sync
public class RedirectNotFoundEntriesMessageListenerTest {

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

		_redirectNotFoundEntriesMessageListener.receive(new Message());

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

	private RedirectNotFoundEntry _addOrUpdateRedirectNotFoundEntry(
		String url, Date date) {

		RedirectNotFoundEntry redirectNotFoundEntry =
			_redirectNotFoundEntryLocalService.addOrUpdateRedirectNotFoundEntry(
				_group, url);

		redirectNotFoundEntry.setModifiedDate(date);

		return _redirectNotFoundEntryLocalService.updateRedirectNotFoundEntry(
			redirectNotFoundEntry);
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private final Set<RedirectNotFoundEntry> _redirectNotFoundEntries =
		new HashSet<>();

	@Inject(filter = "component.name=*.RedirectNotFoundEntriesMessageListener")
	private MessageListener _redirectNotFoundEntriesMessageListener;

	@Inject
	private RedirectNotFoundEntryLocalService
		_redirectNotFoundEntryLocalService;

}