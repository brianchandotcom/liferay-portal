/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Vilmos Papp
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class AdvancedFileSystemStoreTest {

	@Test
	public void testUpdateFileWithMoveFiles() throws Exception {
		Object[] data = setupStoreFolders();
		long companyId = (Long)data[0];
		long repositoryId = (Long)data[1];
		long newRepositoryId = (Long)data[2];
		String[] fileNames = new String[] {
			"1" + _FILE_NAME_EXTENSION, "10" + _FILE_NAME_EXTENSION,
			"101" + _FILE_NAME_EXTENSION, "110" + _FILE_NAME_EXTENSION,
			"150" + _FILE_NAME_EXTENSION
		};

		for (String fileName : fileNames) {
			_store.updateFile(
				companyId, repositoryId, newRepositoryId, fileName);

			Assert.assertTrue(
				_store.hasFile(companyId, newRepositoryId, fileName));
			Assert.assertFalse(
				_store.hasFile(companyId, repositoryId, fileName));
		}
	}

	protected Object[] setupStoreFolders() throws Exception {
		long companyId = ServiceTestUtil.nextLong();
		long repositoryId = ServiceTestUtil.nextLong();
		long newRepositoryId = ServiceTestUtil.nextLong();

		String fileName;
		for (int i = 0; i < _FILE_COUNT; i++) {
			fileName = String.valueOf(i) + _FILE_NAME_EXTENSION;

			_store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);
		}

		return new Object[] {
			companyId, repositoryId, newRepositoryId};
	}

	private static final int _DATA_SIZE = 1024 * 65;

	private static final byte[] _DATA_VERSION_1 = new byte[_DATA_SIZE];

	private static final int _FILE_COUNT = 200;

	private static final String _FILE_NAME_EXTENSION = ".txt";

	private static Store _store = new AdvancedFileSystemStore();

	static {
		for (int i = 0; i < _DATA_SIZE; i++) {
			_DATA_VERSION_1[i] = (byte)i;
		}
	}

}