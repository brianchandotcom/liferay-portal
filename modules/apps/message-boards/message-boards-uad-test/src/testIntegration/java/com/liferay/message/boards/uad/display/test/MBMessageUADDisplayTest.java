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

package com.liferay.message.boards.uad.display.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.message.boards.uad.test.MBMessageUADTestUtil;
import com.liferay.message.boards.uad.test.MBThreadUADTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.test.util.BaseUADDisplayTestCase;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class MBMessageUADDisplayTest extends BaseUADDisplayTestCase<MBMessage> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		MBMessageUADTestUtil.cleanUpDependencies(
			_mbCategoryLocalService, _mbMessages);
	}

	@Test
	public void testGetParentContainerId() throws Exception {
		_mbThread = MBThreadUADTestUtil.addMBThread(
			_mbMessageLocalService, _mbThreadLocalService,
			TestPropsValues.getUserId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		MBMessage mbMessage = MBMessageUADTestUtil.addMBMessage(
			_mbMessageLocalService, TestPropsValues.getUserId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			_mbThread.getThreadId());

		_mbMessages.add(mbMessage);

		Serializable parentContainerId = _uadDisplay.getParentContainerId(
			mbMessage);

		Assert.assertEquals(_mbThread.getThreadId(), (long)parentContainerId);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetTopLevelContainer() throws Exception {
		_uadDisplay.getTopLevelContainer(null, null, null);
	}

	@Override
	protected MBMessage addBaseModel(long userId) throws Exception {
		MBMessage mbMessage = MBMessageUADTestUtil.addMBMessage(
			_mbCategoryLocalService, _mbMessageLocalService, userId);

		_mbMessages.add(mbMessage);

		return mbMessage;
	}

	@Override
	protected UADDisplay getUADDisplay() {
		return _uadDisplay;
	}

	@Inject
	private MBCategoryLocalService _mbCategoryLocalService;

	@Inject
	private MBMessageLocalService _mbMessageLocalService;

	@DeleteAfterTestRun
	private final List<MBMessage> _mbMessages = new ArrayList<>();

	@DeleteAfterTestRun
	private MBThread _mbThread;

	@Inject
	private MBThreadLocalService _mbThreadLocalService;

	@Inject(filter = "component.name=*.MBMessageUADDisplay")
	private UADDisplay<MBMessage> _uadDisplay;

}