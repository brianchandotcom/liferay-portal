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

package com.liferay.batch.engine.internal.task.progress;

import com.liferay.batch.engine.BatchEngineTaskContentType;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Matija Petanjek
 */
public class JSONBatchEngineTaskProgressImplTest
	extends BaseBatchEngineTaskProgressImplTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetTotalItems() throws Exception {
		_testGetTotalItemsCount(0, true);
		_testGetTotalItemsCount(PRODUCTS_COUNT, false);
		_testGetTotalItemsCount(0, true);
	}

	private void _testGetTotalItemsCount(
			int expectedTotalItemsCount, boolean invalidJSONSyntax)
		throws Exception {

		StringBundler sb = new StringBundler();

		sb.append(StringPool.OPEN_BRACKET);

		for (int i = 0; i < expectedTotalItemsCount; i++) {
			sb.append(productJSONObject.toString());

			if (i < (PRODUCTS_COUNT - 1)) {
				sb.append(StringPool.COMMA);
			}
		}

		if (!invalidJSONSyntax) {
			sb.append(StringPool.CLOSE_BRACKET);
		}

		String content = sb.toString();

		Assert.assertEquals(
			expectedTotalItemsCount,
			_batchEngineTaskProgress.getTotalItemsCount(
				compress(
					content.getBytes(),
					BatchEngineTaskContentType.JSON.toString())));
	}

	private static final BatchEngineTaskProgress _batchEngineTaskProgress =
		new JSONBatchEngineTaskProgressImpl();

}