/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = RepositoryEntryUADTestHelper.class)
public class RepositoryEntryUADTestHelper {

	public RepositoryEntry addRepositoryEntry(long userId) throws Exception {
		Repository repository = _repositoryLocalService.addRepository(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			_portal.getClassNameId(LiferayRepository.class.getName()),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new UnicodeProperties(), true,
			ServiceContextTestUtil.getServiceContext());

		return _repositoryEntryLocalService.addRepositoryEntry(
			userId, TestPropsValues.getGroupId(), repository.getRepositoryId(),
			StringPool.BLANK, ServiceContextTestUtil.getServiceContext());
	}

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