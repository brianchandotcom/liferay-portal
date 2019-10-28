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

package com.liferay.portal.background.task.internal;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class BackgroundTaskThreadLocalManagerImplTest
	extends BaseBackgroundTaskTestCase {

	@Test
	public void testDeserializeThreadLocals() {
		HashMap<String, Serializable> threadLocalValues =
			initializeThreadLocalValues();

		Map<String, Serializable> taskContextMap =
			HashMapBuilder.<String, Serializable>put(
				BackgroundTaskThreadLocalManagerImpl.KEY_THREAD_LOCAL_VALUES,
				threadLocalValues
			).build();

		backgroundTaskThreadLocalManagerImpl.deserializeThreadLocals(
			taskContextMap);

		assertThreadLocalValues();
	}

	@Test
	public void testGetThreadLocalValues() {
		initalizeThreadLocals();

		Map<String, Serializable> threadLocalValues =
			backgroundTaskThreadLocalManagerImpl.getThreadLocalValues();

		assertThreadLocalValues(threadLocalValues);
	}

	@Test
	public void testSerializeThreadLocals() {
		initalizeThreadLocals();

		Map<String, Serializable> taskContextMap = new HashMap<>();

		backgroundTaskThreadLocalManagerImpl.serializeThreadLocals(
			taskContextMap);

		Map<String, Serializable> threadLocalValues =
			(Map<String, Serializable>)taskContextMap.get(
				BackgroundTaskThreadLocalManagerImpl.KEY_THREAD_LOCAL_VALUES);

		assertThreadLocalValues(threadLocalValues);
	}

	@Test
	public void testSetThreadLocalValues() {
		Map<String, Serializable> threadLocalValues =
			initializeThreadLocalValues();

		backgroundTaskThreadLocalManagerImpl.setThreadLocalValues(
			threadLocalValues);

		assertThreadLocalValues();
	}

}