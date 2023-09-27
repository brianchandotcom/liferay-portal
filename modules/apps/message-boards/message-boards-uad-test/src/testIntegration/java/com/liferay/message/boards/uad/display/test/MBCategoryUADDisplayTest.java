/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class MBCategoryUADDisplayTest
	extends BaseUADDisplayTestCase<MBCategory> {

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
		MBMessage mbMessage = _addMessage(
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		// A message that is an immediate child of the given parent should
		// return null

		Assert.assertNull(
			_getTopLevelContainer(
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, mbMessage));

		// A category that is an immediate child of the given parent should
		// return itself

		MBCategory mbCategoryA = _addCategory(
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		Assert.assertEquals(
			mbCategoryA,
			_getTopLevelContainer(
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, mbCategoryA));

		MBCategory mbCategoryAA = _addCategory(mbCategoryA.getCategoryId());

		Assert.assertEquals(
			mbCategoryAA,
			_getTopLevelContainer(mbCategoryA.getCategoryId(), mbCategoryAA));

		// A category that is the parent should return null

		Assert.assertNull(
			_getTopLevelContainer(mbCategoryA.getCategoryId(), mbCategoryA));

		// A message whose category is an immediate child of the given parent
		// should return its own category

		MBMessage mbMessageA = _addMessage(mbCategoryA.getCategoryId());

		Assert.assertEquals(
			mbCategoryA,
			_getTopLevelContainer(
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, mbMessageA));

		// A thread whose category is an immediate child of the given parent
		// should return its own category

		MBThread mbThread = _addThread(mbCategoryA.getCategoryId());

		Assert.assertEquals(
			mbCategoryA,
			_getTopLevelContainer(
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, mbThread));

		// A top level thread should return null

		MBThread topLevelMBThread = _addThread(
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		Assert.assertNull(
			_getTopLevelContainer(
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				topLevelMBThread));

		// A category that is not a descendant of the given parent should return
		// null

		MBCategory mbCategoryB = _addCategory(
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		MBCategory mbCategoryBA = _addCategory(mbCategoryB.getCategoryId());

		Assert.assertNull(
			_getTopLevelContainer(mbCategoryA.getCategoryId(), mbCategoryBA));

		// A message that is not a descendant of the given parent should
		// return null

		MBMessage mbMessageBA = _addMessage(mbCategoryBA.getCategoryId());

		Assert.assertNull(
			_getTopLevelContainer(mbCategoryA.getCategoryId(), mbMessageBA));

		// A category that is a nonimmediate descendant of the given parent
		// should return its highest ancestor below the given parent

		MBCategory mbCategoryBAA = _addCategory(mbCategoryBA.getCategoryId());

		MBCategory mbCategoryBAAA = _addCategory(mbCategoryBAA.getCategoryId());

		Assert.assertEquals(
			mbCategoryBA,
			_getTopLevelContainer(mbCategoryB.getCategoryId(), mbCategoryBAAA));

		// A message that is a nonimmediate descendant of the given parent
		// should return its highest ancestor below the given parent

		MBMessage mbMessageBAAA = _addMessage(mbCategoryBAAA.getCategoryId());

		Assert.assertEquals(
			mbCategoryB,
			_getTopLevelContainer(
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, mbMessageBAAA));

		Assert.assertEquals(
			mbCategoryBA,
			_getTopLevelContainer(mbCategoryB.getCategoryId(), mbMessageBAAA));
	}

	@Override
	protected MBCategory addBaseModel(long userId) throws Exception {
		MBCategory mbCategory = MBCategoryUADTestUtil.addMBCategory(
			_mbCategoryLocalService, userId);

		_mbCategories.add(mbCategory);

		return mbCategory;
	}

	protected void assertParentContainerId(long mbCategoryId) throws Exception {
		MBCategory mbCategory = MBCategoryUADTestUtil.addMBCategory(
			_mbCategoryLocalService, TestPropsValues.getUserId(), mbCategoryId);

		_mbCategories.add(mbCategory);

		Serializable parentContainerId = _uadDisplay.getParentContainerId(
			mbCategory);

		Assert.assertEquals(mbCategoryId, (long)parentContainerId);
	}

	@Override
	protected UADDisplay<MBCategory> getUADDisplay() {
		return _uadDisplay;
	}

	private MBCategory _addCategory(long parentMBCategoryId) throws Exception {
		MBCategory mbCategory = MBCategoryUADTestUtil.addMBCategory(
			_mbCategoryLocalService, TestPropsValues.getUserId(),
			parentMBCategoryId);

		_mbCategories.add(mbCategory);

		return mbCategory;
	}

	private MBMessage _addMessage(long mbCategoryId) throws Exception {
		MBMessage mbMessage = MBMessageUADTestUtil.addMBMessage(
			_mbMessageLocalService, TestPropsValues.getUserId(), mbCategoryId);

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

	private MBCategory _getTopLevelContainer(
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

	@Inject(
		filter = "component.name=com.liferay.message.boards.uad.display.MBCategoryUADDisplay"
	)
	private UADDisplay<MBCategory> _uadDisplay;

}