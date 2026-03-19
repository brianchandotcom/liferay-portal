/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.web.internal.model;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Gabriel Lima
 */
public class ConfigurationModelTest extends Mockito {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testSplit() {
		ConfigurationModel configurationModel = mock(ConfigurationModel.class);

		Assert.assertArrayEquals(
			new String[0],
			ReflectionTestUtil.<String[]>invoke(
				configurationModel, "_split",
				new Class<?>[] {String.class, String.class}, null, null));
		Assert.assertArrayEquals(
			new String[] {"argument"},
			ReflectionTestUtil.<String[]>invoke(
				configurationModel, "_split",
				new Class<?>[] {String.class, String.class}, "argument", null));
		Assert.assertArrayEquals(
			new String[] {"argument1", "argument2"},
			ReflectionTestUtil.<String[]>invoke(
				configurationModel, "_split",
				new Class<?>[] {String.class, String.class},
				"argument1,argument2", ","));
	}

}