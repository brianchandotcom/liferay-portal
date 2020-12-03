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

package com.liferay.change.tracking.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.closure.CTClosureFactory;
import com.liferay.change.tracking.constants.CTActionKeys;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.exception.CTEnclosureException;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTProcessService;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.test.util.JournalFolderFixture;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class CTCollectionServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_resourcePermissionLocalService.addResourcePermission(
			_role.getCompanyId(), CTConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(_role.getCompanyId()), _role.getRoleId(),
			CTActionKeys.ADD_PUBLICATION);

		_user = UserTestUtil.addGroupUser(_group, RoleConstants.SITE_MEMBER);

		_roleLocalService.addUserRole(_user.getUserId(), _role);
	}

	@Test
	public void testDiscardCTEntryWithRemovedParent() throws Exception {
		UserTestUtil.setUser(_user);

		JournalFolder folder = _journalFolderFixture.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(), folder.getFolderId());

		_ctCollection = _ctCollectionService.addCTCollection(
			_user.getCompanyId(), _user.getUserId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			_journalArticleLocalService.moveArticle(
				_group.getGroupId(), article.getArticleId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, null);

			_journalFolderLocalService.deleteFolder(folder);
		}

		long articleClassNameId = _classNameLocalService.getClassNameId(
			JournalArticle.class);

		long folderClassNameId = _classNameLocalService.getClassNameId(
			JournalFolder.class);

		Assert.assertFalse(
			_ctCollectionLocalService.isCTEntryEnclosed(
				_ctCollection.getCtCollectionId(), articleClassNameId,
				article.getPrimaryKey()));

		try {
			_ctCollectionService.discardCTEntry(
				_ctCollection.getCtCollectionId(), articleClassNameId,
				article.getPrimaryKey());

			Assert.fail();
		}
		catch (CTEnclosureException ctEnclosureException) {
			Assert.assertEquals(
				StringBundler.concat(
					"{classNameId=", folderClassNameId, ", classPK=",
					folder.getPrimaryKey(), ", ctCollectionId=",
					_ctCollection.getCtCollectionId(), "}"),
				ctEnclosureException.getMessage());
		}

		Assert.assertNotNull(
			_ctEntryLocalService.fetchCTEntry(
				_ctCollection.getCtCollectionId(), articleClassNameId,
				article.getPrimaryKey()));

		Assert.assertTrue(
			_ctCollectionLocalService.isCTEntryEnclosed(
				_ctCollection.getCtCollectionId(), folderClassNameId,
				article.getPrimaryKey()));

		_ctCollectionService.discardCTEntry(
			_ctCollection.getCtCollectionId(), folderClassNameId,
			folder.getPrimaryKey());

		Assert.assertEquals(
			0,
			_ctEntryLocalService.getCTCollectionCTEntriesCount(
				_ctCollection.getCtCollectionId()));

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select count(*) from JournalArticle where id_ = ",
					article.getPrimaryKey(), " and ctCollectionId = ",
					_ctCollection.getCtCollectionId()));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertTrue(resultSet.next());

			Assert.assertEquals(0, resultSet.getLong(1));
		}
	}

	@Test
	public void testPublishCTCollection() throws Exception {
		UserTestUtil.setUser(_user);

		Assert.assertEquals(
			0,
			_ctCollectionService.getCTCollectionsCount(
				_user.getCompanyId(), null, ""));

		_ctCollection = _ctCollectionService.addCTCollection(
			_user.getCompanyId(), _user.getUserId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		Assert.assertEquals(
			1,
			_ctCollectionService.getCTCollectionsCount(
				_user.getCompanyId(), null, ""));

		JournalFolder journalFolder = null;

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			journalFolder = _journalFolderFixture.addFolder(
				_group.getGroupId(), RandomTestUtil.randomString());

			_ctCollectionService.publishCTCollection(
				_user.getUserId(), _ctCollection.getCtCollectionId());
		}

		Assert.assertEquals(
			journalFolder,
			_journalFolderLocalService.fetchJournalFolder(
				journalFolder.getFolderId()));

		List<CTProcess> ctProcesses = _ctProcessService.getCTProcesses(
			_user.getCompanyId(), _user.getUserId(), _ctCollection.getName(),
			WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);

		Assert.assertEquals(ctProcesses.toString(), 1, ctProcesses.size());

		CTProcess ctProcess = ctProcesses.get(0);

		Assert.assertEquals(
			_ctCollection.getCtCollectionId(), ctProcess.getCtCollectionId());
	}

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	@Inject
	private static CTClosureFactory _ctClosureFactory;

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static CTCollectionService _ctCollectionService;

	@Inject
	private static CTEntryLocalService _ctEntryLocalService;

	@Inject
	private static CTProcessService _ctProcessService;

	@Inject
	private static JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private static JournalFolderLocalService _journalFolderLocalService;

	@Inject
	private static ResourcePermissionLocalService
		_resourcePermissionLocalService;

	@Inject
	private static RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	@DeleteAfterTestRun
	private Group _group;

	private final JournalFolderFixture _journalFolderFixture =
		new JournalFolderFixture(_journalFolderLocalService);

	@DeleteAfterTestRun
	private Role _role;

	@DeleteAfterTestRun
	private User _user;

}