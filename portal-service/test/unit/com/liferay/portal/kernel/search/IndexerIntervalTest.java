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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.exception.PortalException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jonathan McCann
 */
public class IndexerIntervalTest {

	@Test
	public void testIndexerIntervalEndCalculation() throws Exception {
		IndexerInterval indexerIntervalInstance = new IndexerInterval();

		indexerIntervalInstance.setPerformActionMethod(
			new IndexerInterval.PerformIntervalActionMethod() {

				@Override
				public void performIntervalAction(int start, int end)
					throws PortalException {

					for (int i = 0; i < end; i++) {
						incrementCounter();
					}
				}

			});

		indexerIntervalInstance.setCount(125);
		indexerIntervalInstance.performInterval();

		Assert.assertEquals(200, _count);
	}

	@Test
	public void testIndexerIntervalPageCalculation() throws Exception {
		IndexerInterval indexerIntervalInstance = new IndexerInterval();

		indexerIntervalInstance.setPerformActionMethod(
			new IndexerInterval.PerformIntervalActionMethod() {

				@Override
				public void performIntervalAction(int start, int end)
					throws PortalException {

					incrementCounter();
				}

			});

		indexerIntervalInstance.setCount(125);
		indexerIntervalInstance.performInterval();

		Assert.assertEquals(2, _count);
	}

	protected void incrementCounter() {
		_count++;
	}

	private int _count;

}