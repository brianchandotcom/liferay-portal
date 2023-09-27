/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.batch.engine.task;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Carlos Correa
 */
public class TaskItemUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetDelegateName() {
		Assert.assertEquals(
			"DEFAULT",
			TaskItemUtil.getTaskItemDelegateName(
				RandomTestUtil.randomString()));
	}

	@Test
	public void testGetDelegateNameWithPound() {
		String taskItemDelegateName = RandomTestUtil.randomString();

		String internalClassNameKey =
			RandomTestUtil.randomString() + StringPool.POUND +
				taskItemDelegateName;

		Assert.assertEquals(
			taskItemDelegateName,
			TaskItemUtil.getTaskItemDelegateName(internalClassNameKey));
	}

	@Test
	public void testGetInternalClassName() {
		String internalClassNameKey = RandomTestUtil.randomString();

		Assert.assertEquals(
			internalClassNameKey,
			TaskItemUtil.getInternalClassName(internalClassNameKey));
	}

	@Test
	public void testGetInternalClassNameKey() {
		String internalClassName = RandomTestUtil.randomString();

		Assert.assertEquals(
			internalClassName,
			TaskItemUtil.getInternalClassNameKey(internalClassName, null));
		Assert.assertEquals(
			internalClassName,
			TaskItemUtil.getInternalClassNameKey(internalClassName, ""));
		Assert.assertEquals(
			internalClassName,
			TaskItemUtil.getInternalClassNameKey(internalClassName, "DEFAULT"));
	}

	@Test
	public void testGetInternalClassNameKeyWithDelegateName() {
		String taskItemDelegateName = RandomTestUtil.randomString();
		String internalClassName = RandomTestUtil.randomString();

		Assert.assertEquals(
			internalClassName + StringPool.POUND + taskItemDelegateName,
			TaskItemUtil.getInternalClassNameKey(
				internalClassName, taskItemDelegateName));
	}

	@Test
	public void testGetInternalClassNameWithPound() {
		String internalClassName = RandomTestUtil.randomString();

		String internalClassNameKey =
			internalClassName + StringPool.POUND +
				RandomTestUtil.randomString();

		Assert.assertEquals(
			internalClassName,
			TaskItemUtil.getInternalClassName(internalClassNameKey));
	}

	@Test
	public void testGetSimpleClassName() {
		String simpleClassName = RandomTestUtil.randomString();

		Assert.assertEquals(
			simpleClassName,
			TaskItemUtil.getSimpleClassName(
				RandomTestUtil.randomString() + StringPool.PERIOD +
					simpleClassName));
	}

	@Test
	public void testGetSimpleClassNameWithPound() {
		String taskItemDelegateName = RandomTestUtil.randomString();

		Assert.assertEquals(
			taskItemDelegateName,
			TaskItemUtil.getSimpleClassName(
				StringBundler.concat(
					RandomTestUtil.randomString(), StringPool.PERIOD,
					RandomTestUtil.randomString(), StringPool.POUND,
					taskItemDelegateName)));
	}

}