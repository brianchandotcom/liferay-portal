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

package com.liferay.announcements.uad.aggregator.test;

import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.uad.constants.AnnouncementsUADConstants;
import com.liferay.announcements.uad.test.BaseAnnouncementsEntryUADEntityTestCase;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.entity.UADEntity;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Noah Sherrill
 */
@RunWith(Arquillian.class)
public class AnnouncementsEntryUADEntityAggregatorTest
	extends BaseAnnouncementsEntryUADEntityTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addGroupUser(getGroup(), RoleConstants.USER);
	}

	@Test
	public void testCount() throws Exception {
		addEntry(_user.getUserId());

		Assert.assertEquals(1, _uadEntityAggregator.count(_user.getUserId()));
	}

	@Test
	public void testGetUADEntities() throws Exception {
		addEntry(TestPropsValues.getUserId());
		addEntry(_user.getUserId());

		List<UADEntity> uadEntities = _uadEntityAggregator.getUADEntities(
			_user.getUserId());

		Assert.assertEquals(uadEntities.toString(), 1, uadEntities.size());

		UADEntity uadEntity = uadEntities.get(0);

		Assert.assertEquals(_user.getUserId(), uadEntity.getUserId());
	}

	@Test
	public void testGetUADEntitiesNoAnnouncementsEntries() throws Exception {
		List<UADEntity> uadEntities = _uadEntityAggregator.getUADEntities(
			_user.getUserId());

		Assert.assertEquals(uadEntities.toString(), 0, uadEntities.size());
	}

	@Test
	public void testGetUADEntity() throws Exception {
		AnnouncementsEntry announcementsEntry = addEntry(_user.getUserId());

		long entryId = announcementsEntry.getEntryId();

		UADEntity uadEntity = _uadEntityAggregator.getUADEntity(
			Long.toString(entryId));

		Assert.assertEquals(Long.toString(entryId), uadEntity.getUADEntityId());
	}

	@Inject(
		filter = "model.class.name=" + AnnouncementsUADConstants.ANNOUNCEMENTS_ENTRY
	)
	private UADEntityAggregator _uadEntityAggregator;

	@DeleteAfterTestRun
	private User _user;

}