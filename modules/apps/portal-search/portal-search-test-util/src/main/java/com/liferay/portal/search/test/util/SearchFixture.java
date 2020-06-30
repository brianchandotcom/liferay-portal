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

package com.liferay.portal.search.test.util;

import com.liferay.portal.search.index.IndexStatusManager;

import java.lang.reflect.Method;

import java.util.concurrent.TimeUnit;

/**
 * @author André de Oliveira
 */
public class SearchFixture {

	public static void setUp(IndexStatusManager indexStatusManager) {
		SearchFixture searchFixture = new SearchFixture(indexStatusManager);

		searchFixture.setUp();
	}

	public static void tearDown(IndexStatusManager indexStatusManager) {
		SearchFixture searchFixture = new SearchFixture(indexStatusManager);

		searchFixture.tearDown();
	}

	public SearchFixture(IndexStatusManager indexStatusManager) {
		_indexStatusManager = indexStatusManager;
	}

	public void setUp() {
		retry(
			() -> {
				Method method = _getRequireIndexReadWriteMethod();

				if (method != null) {
					try {
						method.invoke(_indexStatusManager, true);
					}
					catch (Exception exception) {
						throw new RuntimeException(exception);
					}
				}
			});
	}

	public void tearDown() {
		Method method = _getRequireIndexReadWriteMethod();

		if (method != null) {
			try {
				method.invoke(_indexStatusManager, false);
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}
	}

	protected void retry(Runnable runnable) {
		SearchRetryFixture.builder(
		).timeout(
			10, TimeUnit.SECONDS
		).build(
		).assertSearch(
			runnable
		);
	}

	private Method _getRequireIndexReadWriteMethod() {
		try {
			Class<? extends IndexStatusManager> clazz =
				_indexStatusManager.getClass();

			return clazz.getMethod("requireIndexReadWrite", boolean.class);
		}
		catch (NoSuchMethodException e) {
			return null;
		}
	}

	private final IndexStatusManager _indexStatusManager;

}