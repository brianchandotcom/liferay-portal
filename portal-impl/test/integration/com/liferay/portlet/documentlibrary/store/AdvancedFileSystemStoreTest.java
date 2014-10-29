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

package com.liferay.portlet.documentlibrary.store;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portlet.documentlibrary.NoSuchDirectoryException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Vilmos Papp
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class AdvancedFileSystemStoreTest {

	@Before
	public void setUp() {
		_store = new AdvancedFileSystemStore();
	}

	@Test
	public void testUpdateFileWithMoveFiles() throws Exception {
		Object[] data = initStoreData();

		long companyId = (Long)data[0];
		long repositoryId = (Long)data[1];
		long newRepositoryId = RandomTestUtil.nextLong();

		try {
			String[] fileNames = _store.getFileNames(companyId, repositoryId);

			for (String fileName : fileNames) {
				_store.updateFile(
					companyId, repositoryId, newRepositoryId, fileName);

				Assert.assertTrue(
					fileName + " was not found in " + newRepositoryId,
					_store.hasFile(companyId, newRepositoryId, fileName));
				Assert.assertFalse(
					fileName + " was found in " + repositoryId,
					_store.hasFile(companyId, repositoryId, fileName));
			}
		}
		finally {
			try {
				_store.deleteDirectory(
					companyId, repositoryId, StringPool.BLANK);
			}
			catch (NoSuchDirectoryException nsde) {
			}

			try {
				_store.deleteDirectory(
					companyId, newRepositoryId, StringPool.BLANK);
			}
			catch (NoSuchDirectoryException nsde) {
			}
		}
	}

	protected Object[] initStoreData() throws Exception {
		long companyId = RandomTestUtil.nextLong();
		long repositoryId = RandomTestUtil.nextLong();

		for (int i = 0; i < _FILE_COUNT; i++) {
			String fileName = String.valueOf(i) + _FILE_NAME_EXTENSION;

			_store.addFile(companyId, repositoryId, fileName, _FILE_DATA);
		}

		return new Object[] {companyId, repositoryId};
	}

	private static final int _DATA_SIZE = 100;

	private static final int _FILE_COUNT = 200;

	private static final byte[] _FILE_DATA = new byte[_DATA_SIZE];

	private static final String _FILE_NAME_EXTENSION = ".txt";

	static {
		for (int i = 0; i < _DATA_SIZE; i++) {
			_FILE_DATA[i] = (byte)i;
		}
	}

	private Store _store;

}