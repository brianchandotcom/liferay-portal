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

package com.liferay.portal.search.internal.reindexer;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class ReindexTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_reindex = createReindex();
	}

	@Test
	public void testMustRepeatModelChangedOutsideAfterIndexedInside() {
		_reindex.setNonbulkIndexing(false);
		_reindex.setSynchronousExecution(false);

		_testMustRepeatModelChangedOutsideAfterIndexedInside();
	}

	@Test
	public void testMustRepeatModelChangedOutsideAfterIndexedInsideNonbulk() {
		_reindex.setNonbulkIndexing(true);
		_reindex.setSynchronousExecution(false);

		_testMustRepeatModelChangedOutsideAfterIndexedInside();
	}

	@Test
	public void testSynchronousIndexingForIntegrationTests() {
		_reindex.setNonbulkIndexing(false);
		_reindex.setSynchronousExecution(true);

		_testSynchronousIndexingForIntegrationTests();
	}

	@Test
	public void testSynchronousIndexingForIntegrationTestsNonbulk() {
		_reindex.setNonbulkIndexing(true);
		_reindex.setSynchronousExecution(true);

		_testSynchronousIndexingForIntegrationTests();
	}

	protected void addReindexEndListener(
		Reindex.ReindexEndListener reindexEndListener) {

		_reindex.addReindexEndListener(reindexEndListener);
	}

	protected Reindex createReindex() {
		Reindex reindex = new Reindex(
			null, null, Executors.newSingleThreadExecutor(),
			new ReindexRequestsHolder());

		reindex.setCustomReindex(this::_reindex);
		reindex.setCustomReindexBulk(this::_reindexBulk);

		return reindex;
	}

	private void _acquire(Semaphore semaphore, int permits) {
		try {
			if (!semaphore.tryAcquire(permits, 2L, TimeUnit.SECONDS)) {
				Assert.assertEquals(
					"Permits", permits, semaphore.availablePermits());
			}
		}
		catch (InterruptedException interruptedException) {
			throw new RuntimeException(interruptedException);
		}
	}

	private void _assertIndexedValues(List<Integer> list) {
		Assert.assertEquals(list, _indexedValues);
	}

	private void _reindex(long classPK) {
		_transferAccumulatedToIndexed();
	}

	private void _reindexAddingValue(int group) {
		_addedValues.add(group);

		_reindex.reindex(_CLASS_NAME, _CLASS_PK);
	}

	private void _reindexBulk(Collection<Long> classPKs) {
		_transferAccumulatedToIndexed();
	}

	private void _testMustRepeatModelChangedOutsideAfterIndexedInside() {
		Semaphore semaphore1 = new Semaphore(0);

		Semaphore semaphore2 = new Semaphore(0);

		AtomicBoolean atomicBoolean = new AtomicBoolean();

		addReindexEndListener(
			() -> {
				semaphore1.release(1);

				if (!atomicBoolean.getAndSet(true)) {
					_acquire(semaphore2, 1);
				}
			});

		_reindexAddingValue(101);

		_acquire(semaphore1, 1);

		_assertIndexedValues(Arrays.asList(101));

		_reindexAddingValue(202);
		_reindexAddingValue(303);
		_reindexAddingValue(404);
		_reindexAddingValue(505);

		_assertIndexedValues(Arrays.asList(101));

		semaphore2.release(1);

		int multipleRequestsForSameClassPKCollapseIntoOne = 1;

		_acquire(semaphore1, multipleRequestsForSameClassPKCollapseIntoOne);

		_assertIndexedValues(Arrays.asList(101, 202, 303, 404, 505));
	}

	private void _testSynchronousIndexingForIntegrationTests() {
		_reindexAddingValue(101);

		_assertIndexedValues(Arrays.asList(101));

		_reindexAddingValue(202);
		_reindexAddingValue(303);
		_reindexAddingValue(404);
		_reindexAddingValue(505);

		_assertIndexedValues(Arrays.asList(101, 202, 303, 404, 505));
	}

	private void _transferAccumulatedToIndexed() {
		_indexedValues.clear();

		_indexedValues.addAll(_addedValues);
	}

	private static final String _CLASS_NAME = RandomTestUtil.randomString();

	private static final long _CLASS_PK = RandomTestUtil.randomLong();

	private final List<Integer> _addedValues = new ArrayList<>();
	private final List<Integer> _indexedValues = new ArrayList<>();
	private Reindex _reindex;

}