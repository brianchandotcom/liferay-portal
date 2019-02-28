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

package com.liferay.document.library.change.tracking.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.CTEngineManager;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.ByteArrayInputStream;

import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luiz Marins
 */
@RunWith(Arquillian.class)
public class DLFileEntryFilterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();

		_user1 = TestPropsValues.getUser();

		_user2 = UserTestUtil.addUser();

		_ctEngineManager.enableChangeTracking(
			TestPropsValues.getCompanyId(), _user1.getUserId());

		_ctCollectionUser1 = _createCtCollection(_user1);

		_createCtCollection(_user2);
	}

	@After
	public void tearDown() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_deleteCTCollections();

		_ctEngineManager.disableChangeTracking(TestPropsValues.getCompanyId());
	}

	@Test
	public void testUsersDoNotSeeChangesOfEachOtherChangeList()
		throws PortalException {

		FileEntry fileUser1 = _addFileEntry(_user1);

		FileEntry fileUser2 = _addFileEntry(_user2);

		_assertUserFetchedFiles(_user1, fileUser1);

		_assertUserFetchedFiles(_user2, fileUser2);
	}

	@Test
	public void testUserSeeAnotherUsersPublishedFiles() throws PortalException {
		_addFileEntry(_user1);

		_addFileEntry(_user2);

		PrincipalThreadLocal.setName(_user1.getUserId());

		_ctEngineManager.publishCTCollection(
			_user1.getUserId(), _ctCollectionUser1.getCtCollectionId());

		PrincipalThreadLocal.setName(_user2.getUserId());

		List<Object> filesUser =
			DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(
				_group.getGroupId(), 0, -1, true, 0, Integer.MAX_VALUE);

		Assert.assertEquals(
			"Amount of files fetches is incorrect", 2, filesUser.size());
	}

	private FileEntry _addFileEntry(User user) throws PortalException {
		PrincipalThreadLocal.setName(user.getUserId());

		String content = StringUtil.randomString();

		String fileName = user.getScreenName() + "file.txt";

		return _dlAppService.addFileEntry(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			fileName, ContentTypes.TEXT_PLAIN, fileName, StringPool.BLANK,
			StringPool.BLANK, new ByteArrayInputStream(content.getBytes()), 0,
			_getServiceContext(user));
	}

	private void _assertUserFetchedFiles(User user, FileEntry fileEntry)
		throws PortalException {

		PrincipalThreadLocal.setName(user.getUserId());

		List<Object> filesUser =
			DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(
				_group.getGroupId(), 0, -1, true, 0, Integer.MAX_VALUE);

		Assert.assertEquals(
			"Amount of files fetches is incorrect", 1, filesUser.size());

		Assert.assertEquals(
			"Incorrect file fetched", fileEntry.getFileName(),
			((FileEntry)filesUser.get(0)).getFileName());
	}

	private CTCollection _createCtCollection(User user) {
		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.createCTCollection(
				user.getUserId(), user.getScreenName() + _CT_COLLECTION_NAME,
				StringPool.BLANK);

		CTCollection ctCollection = ctCollectionOptional.get();

		_ctEngineManager.checkoutCTCollection(
			user.getUserId(), ctCollection.getCtCollectionId());

		return ctCollection;
	}

	private void _deleteCTCollections() throws PortalException {
		List<CTCollection> ctCollections = _ctEngineManager.getCTCollections(
			TestPropsValues.getCompanyId());

		for (CTCollection c : ctCollections) {
			_ctEngineManager.deleteCTCollection(c.getCtCollectionId());
		}
	}

	private ServiceContext _getServiceContext(User user)
		throws PortalException {

		return ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), user.getUserId());
	}

	private static final String _CT_COLLECTION_NAME = "CTCollection";

	private CTCollection _ctCollectionUser1;

	@Inject
	private CTEngineManager _ctEngineManager;

	@Inject
	private DLAppService _dlAppService;

	@DeleteAfterTestRun
	private Group _group;

	private User _user1;

	@DeleteAfterTestRun
	private User _user2;

}