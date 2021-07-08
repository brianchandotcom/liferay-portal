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
public class JSONBatchEngineTaskProgressTest
	extends BaseBatchEngineTaskProgressTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetTotalItemsCountFromEmptyJSONFile() throws Exception {
		String productsJSON = _getProductsJSON(0, false);

		int totalItemsCount = _batchEngineTaskProgress.getTotalItemsCount(
			new ByteArrayInputStream(
				compressContent(
					productsJSON.getBytes(),
					BatchEngineTaskContentType.JSON.toString())));

		Assert.assertEquals(0, totalItemsCount);
	}

	@Test
	public void testGetTotalItemsCountFromInvalidJSONFile() throws Exception {
		String productsJSON = _getProductsJSON(PRODUCTS_COUNT, true);

		int totalItemsCount = _batchEngineTaskProgress.getTotalItemsCount(
			new ByteArrayInputStream(
				compressContent(
					productsJSON.getBytes(),
					BatchEngineTaskContentType.JSON.toString())));

		Assert.assertEquals(0, totalItemsCount);
	}

	@Test
	public void testGetTotalItemsCountFromJSONFile() throws Exception {
		String productsJSON = _getProductsJSON(PRODUCTS_COUNT, false);

		int totalItemsCount = _batchEngineTaskProgress.getTotalItemsCount(
			new ByteArrayInputStream(
				compressContent(
					productsJSON.getBytes(),
					BatchEngineTaskContentType.JSON.toString())));

		Assert.assertEquals(PRODUCTS_COUNT, totalItemsCount);
	}

	private String _getProductsJSON(int productsCount, boolean invalidJson) {
		StringBundler sb = new StringBundler();

		sb.append(StringPool.OPEN_BRACKET);

		for (int i = 0; i < productsCount; i++) {
			sb.append(PRODUCT_JSON);

			if (i < (PRODUCTS_COUNT - 1)) {
				sb.append(StringPool.COMMA);
			}
		}

		if (invalidJson) {
			sb.append("invalidJson");
		}

		sb.append(StringPool.CLOSE_BRACKET);

		return sb.toString();
	}

	private static final BatchEngineTaskProgress _batchEngineTaskProgress =
		new JSONBatchEngineTaskProgressImpl();

}