/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.repository.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.model.Counter;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.db.partition.test.util.BaseDBPartitionTestCase;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.IndexStatusManagerThreadLocal;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.test.rule.Inject;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marco Galluzzi
 */
@RunWith(Arquillian.class)
public class RepositoryFactoryDBPartitionTest extends BaseDBPartitionTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseDBPartitionTestCase.setUpClass();

		BaseDBPartitionTestCase.setUpDBPartitions();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		BaseDBPartitionTestCase.tearDownDBPartitions();
	}

	@Test
	public void testAddFolderInRepositoryWhenSameRepositoryIdExistsInDifferentPartition()
		throws Exception {

		IndexStatusManagerThreadLocal.setIndexReadOnly(true);

		long counterValue = _getCounterValue();

		long companyId = COMPANY_IDS[0];
		long groupId = counterValue + 100;
		long repositoryId = counterValue + 1000;

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setWithSafeCloseable(companyId)) {

			User user = _userLocalService.getGuestUser(companyId);

			Group group = _addGroup(user.getUserId(), groupId);

			Assert.assertEquals(groupId, group.getGroupId());

			com.liferay.portal.kernel.model.Repository repositoryModel =
				_addRepository(group, user.getUserId(), repositoryId);

			Assert.assertEquals(
				repositoryId, repositoryModel.getRepositoryId());

			_repositoryFactory.createRepository(
				repositoryModel.getRepositoryId());
		}

		companyId = COMPANY_IDS[1];
		groupId = counterValue + 200;

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setWithSafeCloseable(companyId)) {

			User user = _userLocalService.getGuestUser(companyId);

			Group group = _addGroup(user.getUserId(), groupId);

			Assert.assertEquals(groupId, group.getGroupId());

			com.liferay.portal.kernel.model.Repository repositoryModel =
				_addRepository(group, user.getUserId(), repositoryId);

			Assert.assertEquals(
				repositoryId, repositoryModel.getRepositoryId());

			Repository repository = _repositoryFactory.createRepository(
				repositoryModel.getRepositoryId());

			String originalUserId = PrincipalThreadLocal.getName();

			try {
				PrincipalThreadLocal.setName(user.getUserId());

				Folder folder = repository.addFolder(
					null, user.getUserId(), repositoryModel.getDlFolderId(),
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString(), new ServiceContext());

				Assert.assertEquals(
					folder.getCompanyId(), repositoryModel.getCompanyId());
			}
			finally {
				PrincipalThreadLocal.setName(originalUserId);
			}
		}
	}

	private Group _addGroup(long userId, long groupId) throws Exception {
		String name = RandomTestUtil.randomString();

		_counterLocalService.reset(Counter.class.getName(), groupId - 1);

		return _groupLocalService.addGroup(
			userId, GroupConstants.DEFAULT_PARENT_GROUP_ID, null, 0,
			GroupConstants.DEFAULT_LIVE_GROUP_ID,
			HashMapBuilder.put(
				LocaleUtil.getDefault(), name
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			GroupConstants.TYPE_SITE_OPEN, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name), true,
			true, new ServiceContext());
	}

	private com.liferay.portal.kernel.model.Repository _addRepository(
			Group group, long userId, long repositoryId)
		throws PortalException {

		DLFolder dlFolder = _dlFolderLocalService.addFolder(
			null, userId, group.getGroupId(), group.getGroupId(), false,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			new ServiceContext());

		_counterLocalService.reset(Counter.class.getName(), repositoryId - 1);

		return _repositoryLocalService.addRepository(
			userId, group.getGroupId(),
			_portal.getClassNameId(LiferayRepository.class.getName()),
			dlFolder.getFolderId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), "Test Portlet",
			new UnicodeProperties(), true, new ServiceContext());
	}

	private long _getCounterValue() {
		long counterValue1;
		long counterValue2;

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setWithSafeCloseable(COMPANY_IDS[0])) {

			counterValue1 = _counterLocalService.increment();
		}

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setWithSafeCloseable(COMPANY_IDS[1])) {

			counterValue2 = _counterLocalService.increment();
		}

		return Math.max(counterValue1, counterValue2);
	}

	@Inject
	private CounterLocalService _counterLocalService;

	@Inject
	private DLFolderLocalService _dlFolderLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private RepositoryFactory _repositoryFactory;

	@Inject
	private RepositoryLocalService _repositoryLocalService;

	@Inject
	private UserLocalService _userLocalService;

}