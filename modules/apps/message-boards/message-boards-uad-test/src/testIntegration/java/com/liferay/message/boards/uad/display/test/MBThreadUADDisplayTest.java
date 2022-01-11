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
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.message.boards.uad.test.MBCategoryUADTestUtil;
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

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Samuel Trong Tran
 */
@RunWith(Arquillian.class)
public class MBThreadUADDisplayTest extends BaseUADDisplayTestCase<MBThread> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetParentContainerId() throws Exception {
		assertParentContainerId(MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		MBCategory mbCategory = _addCategory(
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		assertParentContainerId(mbCategory.getCategoryId());
	}

	@Test
	public void testGetTopLevelContainer() throws Exception {

		// A thread that is an immediate child of the given parent should
		// return itself

		MBThread mbThread = _addThread(
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		Assert.assertEquals(
			mbThread,
			_getTopLevelContainer(
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, mbThread));

		MBCategory mbCategoryA = _addCategory(
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		MBThread mbThreadA = _addThread(mbCategoryA.getCategoryId());

		Assert.assertEquals(
			mbThreadA,
			_getTopLevelContainer(mbCategoryA.getCategoryId(), mbThreadA));

		// A message whose thread is an immediate child of the given parent
		// should return its own thread

		MBMessage mbMessage = _addMessage(
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			mbThread.getThreadId());

		Assert.assertEquals(
			mbThread,
			_getTopLevelContainer(
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, mbMessage));

		MBMessage mbMessageA = _addMessage(
			mbCategoryA.getCategoryId(), mbThreadA.getThreadId());

		Assert.assertEquals(
			mbThreadA,
			_getTopLevelContainer(mbCategoryA.getCategoryId(), mbMessageA));

		// A thread that is the parent should return null

		Assert.assertNull(
			_uadDisplay.getTopLevelContainer(
				MBThread.class, mbThread.getThreadId(), mbThread));

		// A thread that is not a descendant of the given parent should return
		// null

		MBCategory mbCategoryB = _addCategory(
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		MBThread mbThreadB = _addThread(mbCategoryB.getCategoryId());

		Assert.assertNull(
			_getTopLevelContainer(mbCategoryA.getCategoryId(), mbThreadB));

		// A message that is not a descendant of the given parent should
		// return null

		MBMessage mbMessageB = _addMessage(
			mbCategoryB.getCategoryId(), mbThreadB.getThreadId());

		Assert.assertNull(
			_getTopLevelContainer(mbCategoryA.getCategoryId(), mbMessageB));

		// A thread that is a nonimmediate descendant of the given parent
		// should return null

		MBCategory mbCategoryBA = _addCategory(mbCategoryB.getCategoryId());

		MBThread mbThreadBA = _addThread(mbCategoryBA.getCategoryId());

		Assert.assertNull(
			_getTopLevelContainer(mbCategoryB.getCategoryId(), mbThreadBA));

		// A message whose thread is a nonimmediate descendant of the given
		// parent should return null

		MBMessage mbMessageBA = _addMessage(
			mbCategoryBA.getCategoryId(), mbThreadBA.getThreadId());

		Assert.assertNull(
			_getTopLevelContainer(mbCategoryB.getCategoryId(), mbMessageBA));
	}

	@Override
	protected MBThread addBaseModel(long userId) throws Exception {
		MBThread mbThread = MBThreadUADTestUtil.addMBThread(
			_mbCategoryLocalService, _mbMessageLocalService,
			_mbThreadLocalService, userId);

		_mbThreads.add(mbThread);

		return mbThread;
	}

	protected void assertParentContainerId(long mbCategoryId) throws Exception {
		MBThread mbThread = MBThreadUADTestUtil.addMBThread(
			_mbMessageLocalService, _mbThreadLocalService,
			TestPropsValues.getUserId(), mbCategoryId);

		_mbThreads.add(mbThread);

		Serializable parentContainerId = _uadDisplay.getParentContainerId(
			mbThread);

		Assert.assertEquals(mbCategoryId, (long)parentContainerId);
	}

	@Override
	protected UADDisplay<MBThread> getUADDisplay() {
		return _uadDisplay;
	}

	private MBCategory _addCategory(long parentMBCategoryId) throws Exception {
		MBCategory mbCategory = MBCategoryUADTestUtil.addMBCategory(
			_mbCategoryLocalService, TestPropsValues.getUserId(),
			parentMBCategoryId);

		_mbCategories.add(mbCategory);

		return mbCategory;
	}

	private MBMessage _addMessage(long mbCategoryId, long mbThreadId)
		throws Exception {

		MBMessage mbMessage = MBMessageUADTestUtil.addMBMessage(
			_mbMessageLocalService, TestPropsValues.getUserId(), mbCategoryId,
			mbThreadId);

		_mbMessages.add(mbMessage);

		return mbMessage;
	}

	private MBThread _addThread(long mbCategoryId) throws Exception {
		MBThread mbThread = MBThreadUADTestUtil.addMBThread(
			_mbMessageLocalService, _mbThreadLocalService,
			TestPropsValues.getUserId(), mbCategoryId);

		_mbThreads.add(mbThread);

		return mbThread;
	}

	private MBThread _getTopLevelContainer(
		Serializable mbCategoryId, Object childObject) {

		return _uadDisplay.getTopLevelContainer(
			MBCategory.class, mbCategoryId, childObject);
	}

	@DeleteAfterTestRun
	private final List<MBCategory> _mbCategories = new ArrayList<>();

	@Inject
	private MBCategoryLocalService _mbCategoryLocalService;

	@Inject
	private MBMessageLocalService _mbMessageLocalService;

	@DeleteAfterTestRun
	private final List<MBMessage> _mbMessages = new ArrayList<>();

	@Inject
	private MBThreadLocalService _mbThreadLocalService;

	@DeleteAfterTestRun
	private final List<MBThread> _mbThreads = new ArrayList<>();

	@Inject(filter = "component.name=*.MBThreadUADDisplay")
	private UADDisplay<MBThread> _uadDisplay;

}