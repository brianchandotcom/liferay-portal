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

package com.liferay.message.boards.uad.test;

import com.liferay.message.boards.model.MBThread;

import org.junit.Assume;

import org.osgi.service.component.annotations.Component;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = MBThreadUADEntityTestHelper.class)
public class MBThreadUADEntityTestHelper {
	/**
	 * Implement addMBThread() to enable some UAD tests.
	 *
	 * <p>
	 * Several UAD tests depend on creating one or more valid MBThreads with a specified user ID in order to execute correctly. Implement addMBThread() such that it creates a valid MBThread with the specified user ID value and returns it in order to enable the UAD tests that depend on it.
	 * </p>
	 *
	 */
	public MBThread addMBThread(long userId) throws Exception {
		Assume.assumeTrue(false);

		return null;
	}

	/**
	 * Implement addMBThreadWithStatusByUserId() to enable some UAD tests.
	 *
	 * <p>
	 * Several UAD tests depend on creating one or more valid MBThreads with specified user ID and status by user ID in order to execute correctly. Implement addMBThreadWithStatusByUserId() such that it creates a valid MBThread with the specified user ID and status by user ID values and returns it in order to enable the UAD tests that depend on it.
	 * </p>
	 *
	 */
	public MBThread addMBThreadWithStatusByUserId(long userId,
		long statusByUserId) throws Exception {
		Assume.assumeTrue(false);

		return null;
	}

	/**
	 * Implement cleanUpDependencies(List<MBThread> mbThreads) if tests require additional tear down logic.
	 *
	 * <p>
	 * Several UAD tests depend on creating one or more valid MBThreads with specified user ID and status by user ID in order to execute correctly. Implement cleanUpDependencies(List<MBThread> mbThreads) such that any additional objects created during the construction of mbThreads are safely removed.
	 * </p>
	 *
	 */
	public void cleanUpDependencies(List<MBThread> mbThreads)
		throws Exception {
	}
}