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

import java.io.ByteArrayInputStream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Matija Petanjek
 */
public class JSONLBatchEngineTaskProgressTest
	extends BaseBatchEngineTaskProgressTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetTotalItemsCountFromEmptyJSONLFile() throws Exception {
		String productsJSONL = _getProductsJSONL(0);

		int totalItemsCount = _batchEngineTaskProgress.getTotalItemsCount(
			new ByteArrayInputStream(
				compressContent(
					productsJSONL.getBytes(),
					BatchEngineTaskContentType.JSONL.toString())));

		Assert.assertEquals(0, totalItemsCount);
	}

	@Test
	public void testGetTotalItemsCountFromJSONLFile() throws Exception {
		String productsJSONL = _getProductsJSONL(PRODUCTS_COUNT);

		int totalItemsCount = _batchEngineTaskProgress.getTotalItemsCount(
			new ByteArrayInputStream(
				compressContent(
					productsJSONL.getBytes(),
					BatchEngineTaskContentType.JSONL.toString())));

		Assert.assertEquals(PRODUCTS_COUNT, totalItemsCount);
	}

	private String _getProductsJSONL(int productsCount) {
		StringBundler sb = new StringBundler();

		for (int i = 0; i < productsCount; i++) {
			sb.append(PRODUCT_JSON);

			if (i < (PRODUCTS_COUNT - 1)) {
				sb.append(StringPool.NEW_LINE);
			}
		}

		return sb.toString();
	}

	private static final BatchEngineTaskProgress _batchEngineTaskProgress =
		new JSONLBatchEngineTaskProgressImpl();

}