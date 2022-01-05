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

package com.liferay.portal.search.internal.batch;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class BatchIndexingHelperImplBulkSizesTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testConfiguration() {
		String entryClassName1 = RandomTestUtil.randomString();
		String entryClassName2 = RandomTestUtil.randomString();

		activate(entryClassName1 + "=200", entryClassName2 + "=500");

		_assertBulkSize(200, entryClassName1);
		_assertBulkSize(500, entryClassName2);
	}

	@Test
	public void testDefault() {
		_activateWithoutConfiguration();

		_assertBulkSize(10000, "com.liferay.journal.model.JournalArticle");

		_assertBulkSize(10000, RandomTestUtil.randomString());
	}

	@Test
	public void testDefaultWithConfiguration() {
		activate("com.liferay.journal.model.JournalArticle=200");

		_assertBulkSize(200, "com.liferay.journal.model.JournalArticle");

		_assertBulkSize(10000, RandomTestUtil.randomString());
	}

	@Test
	public void testMalformed() {
		String entryClassName1 = RandomTestUtil.randomString();
		String entryClassName2 = RandomTestUtil.randomString();

		activate(
			entryClassName1 + "= ", StringPool.SPACE, entryClassName2 + "?200");

		_assertBulkSize(10000, entryClassName1);
		_assertBulkSize(10000, entryClassName2);
	}

	protected void activate(String... indexingBatchSizes) {
		_batchIndexingHelperImpl.activate(
			Collections.singletonMap(
				"indexingBatchSizes", Arrays.asList(indexingBatchSizes)));
	}

	private void _activateWithoutConfiguration() {
		_batchIndexingHelperImpl.activate(Collections.emptyMap());
	}

	private void _assertBulkSize(int bulkSize, String entryClassName) {
		Assert.assertEquals(
			bulkSize, _batchIndexingHelperImpl.getBulkSize(entryClassName));
	}

	private final BatchIndexingHelperImpl _batchIndexingHelperImpl =
		new BatchIndexingHelperImpl();

}