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

package com.liferay.portal.kernel.trash;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.trash.bundle.trashhandlerregistryutil.TestTrashHandler;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class TrashHandlerRegistryUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleRule("bundle.trashhandlerregistryutil"));

	@Test
	public void testGetTrashHandler() {
		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			TestTrashHandler.class.getName());

		Class<?> clazz = trashHandler.getClass();

		Assert.assertEquals(TestTrashHandler.class.getName(), clazz.getName());
	}

	@Test
	public void testGetTrashHandlers() {
		List<TrashHandler> trashHandlers =
			TrashHandlerRegistryUtil.getTrashHandlers();

		for (TrashHandler trashHandler : trashHandlers) {
			Class<?> clazz = trashHandler.getClass();

			String className = clazz.getName();

			if (className.equals(TestTrashHandler.class.getName())) {
				return;
			}
		}

		Assert.fail();
	}

}