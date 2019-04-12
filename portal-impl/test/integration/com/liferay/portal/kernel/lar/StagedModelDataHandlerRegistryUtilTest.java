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

package com.liferay.portal.kernel.lar;

import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.portal.kernel.lar.bundle.stagedmodeldatahandlerregistryutil.TestStagedModelDataHandler;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleClassTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class StagedModelDataHandlerRegistryUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleClassTestRule(
				"bundle.stagedmodeldatahandlerregistryutil"));

	@Test
	public void testGetStagedModelDataHandler() {
		StagedModelDataHandler<?> stagedModelDataHandler =
			StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
				TestStagedModelDataHandler.CLASS_NAMES[0]);

		Class<?> clazz = stagedModelDataHandler.getClass();

		Assert.assertEquals(
			TestStagedModelDataHandler.class.getName(), clazz.getName());
	}

	@Test
	public void testGetStagedModelDataHandlers() {
		List<StagedModelDataHandler<?>> stagedModelDataHandlers =
			StagedModelDataHandlerRegistryUtil.getStagedModelDataHandlers();

		String testClassName = TestStagedModelDataHandler.class.getName();

		Assert.assertTrue(
			testClassName + " not found in " + stagedModelDataHandlers,
			stagedModelDataHandlers.removeIf(
				stagedModelDataHandler -> {
					Class<?> clazz = stagedModelDataHandler.getClass();

					return testClassName.equals(clazz.getName());
				}));
	}

}