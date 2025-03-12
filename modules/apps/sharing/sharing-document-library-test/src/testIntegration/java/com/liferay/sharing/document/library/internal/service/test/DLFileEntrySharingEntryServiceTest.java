/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.document.library.internal.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.SharingEntryService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import jodd.net.MimeTypes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class DLFileEntrySharingEntryServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousMailTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		FileEntry fileEntry = _dlAppService.addFileEntry(
			null, _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			MimeTypes.MIME_APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), null, RandomTestUtil.randomString(),
			StringPool.BLANK, (byte[])null, null, null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_dlFileEntry = (DLFileEntry)fileEntry.getModel();

		_fromUser = UserTestUtil.addOmniadminUser();

		UserTestUtil.setUser(_fromUser);

		_toUser = UserTestUtil.addUser();
	}

	@Test
	public void testAddSharingEntryViewActionAddsDownloadAction()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			DLFileEntry.class.getName());
		long classPK = _dlFileEntry.getFileEntryId();

		Instant instant = Instant.now();

		Date expirationDate = Date.from(instant.plus(2, ChronoUnit.DAYS));

		SharingEntry sharingEntry = _addSharingEntry(
			classNameId, classPK, expirationDate);

		Assert.assertEquals(_group.getGroupId(), sharingEntry.getGroupId());
		Assert.assertEquals(_group.getCompanyId(), sharingEntry.getCompanyId());
		Assert.assertEquals(_fromUser.getUserId(), sharingEntry.getUserId());
		Assert.assertEquals(_toUser.getUserId(), sharingEntry.getToUserId());
		Assert.assertEquals(classNameId, sharingEntry.getClassNameId());
		Assert.assertEquals(classPK, sharingEntry.getClassPK());
		Assert.assertTrue(sharingEntry.isShareable());
		Assert.assertEquals(
			SharingEntryAction.DOWNLOAD.getBitwiseValue() |
			SharingEntryAction.VIEW.getBitwiseValue(),
			sharingEntry.getActionIds());
		Assert.assertEquals(expirationDate, sharingEntry.getExpirationDate());
	}

	@Test
	public void testUpdateSharingEntry() throws Exception {
		Instant instant = Instant.now();

		SharingEntry sharingEntry = _addSharingEntry(
			_classNameLocalService.getClassNameId(DLFileEntry.class.getName()),
			_dlFileEntry.getFileEntryId(),
			Date.from(instant.plus(2, ChronoUnit.DAYS)));

		Assert.assertEquals(
			SharingEntryAction.DOWNLOAD.getBitwiseValue() |
			SharingEntryAction.VIEW.getBitwiseValue(),
			sharingEntry.getActionIds());

		sharingEntry = _sharingEntryService.updateSharingEntry(
			sharingEntry.getSharingEntryId(),
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			true, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertEquals(
			SharingEntryAction.DOWNLOAD.getBitwiseValue() |
			SharingEntryAction.UPDATE.getBitwiseValue() |
			SharingEntryAction.VIEW.getBitwiseValue(),
			sharingEntry.getActionIds());
	}

	private SharingEntry _addSharingEntry(
			long classNameId, long classPK, Date expirationDate)
		throws Exception {

		return _sharingEntryService.addSharingEntry(
			null, 0, _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true,
			Collections.singletonList(SharingEntryAction.VIEW), expirationDate,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private DLAppService _dlAppService;

	private DLFileEntry _dlFileEntry;

	@DeleteAfterTestRun
	private User _fromUser;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private SharingEntryService _sharingEntryService;

	@DeleteAfterTestRun
	private User _toUser;

}