/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.trash.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.trash.service.test.trashhandlerresgistryutil.TestTrashHandler;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Peter Fellwock
 */
@RunWith(Arquillian.class)
public class TrashHandlerRegistryUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

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

		boolean exists = false;

		for (TrashHandler trashHandler : trashHandlers) {
			Class<?> clazz = trashHandler.getClass();

			String className = clazz.getName();

			if (className.equals(TestTrashHandler.class.getName())) {
				exists = true;

				break;
			}
		}

		Assert.assertTrue(
			TestTrashHandler.class.getName() + " is not registered", exists);
	}

}