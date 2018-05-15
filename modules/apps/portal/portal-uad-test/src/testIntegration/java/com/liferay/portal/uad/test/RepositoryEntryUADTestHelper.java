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

package com.liferay.portal.uad.test;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.RepositoryEntry;
import com.liferay.portal.kernel.service.RepositoryEntryLocalService;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = RepositoryEntryUADTestHelper.class)
public class RepositoryEntryUADTestHelper {

	/**
	 * Implement addRepositoryEntry() to enable some UAD tests.
	 *
	 * <p>
	 * Several UAD tests depend on creating one or more valid RepositoryEntries with a specified user ID in order to execute correctly. Implement addRepositoryEntry() such that it creates a valid RepositoryEntry with the specified user ID value and returns it in order to enable the UAD tests that depend on it.
	 * </p>
	 */
	public RepositoryEntry addRepositoryEntry(long userId) throws Exception {
		long classNameId = _portal.getClassNameId(
			LiferayRepository.class.getName());

		Repository repository = _repositoryLocalService.addRepository(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			classNameId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new UnicodeProperties(), true,
			ServiceContextTestUtil.getServiceContext());

		return _repositoryEntryLocalService.addRepositoryEntry(
			userId, TestPropsValues.getGroupId(), repository.getRepositoryId(),
			StringPool.BLANK, ServiceContextTestUtil.getServiceContext());
	}

	/**
	 * Implement cleanUpDependencies(List<RepositoryEntry> repositoryEntries) if tests require additional tear down logic.
	 *
	 * <p>
	 * Several UAD tests depend on creating one or more valid RepositoryEntries with specified user ID and status by user ID in order to execute correctly. Implement cleanUpDependencies(List<RepositoryEntry> repositoryEntries) such that any additional objects created during the construction of repositoryEntries are safely removed.
	 * </p>
	 */
	public void cleanUpDependencies(List<RepositoryEntry> repositoryEntries)
		throws Exception {

		for (RepositoryEntry repositoryEntry : repositoryEntries) {
			_repositoryLocalService.deleteRepository(
				repositoryEntry.getRepositoryId());
		}
	}

	@Reference
	private Portal _portal;

	@Reference
	private RepositoryEntryLocalService _repositoryEntryLocalService;

	@Reference
	private RepositoryLocalService _repositoryLocalService;

}