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

package com.liferay.document.library.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetLinkLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.document.library.uad.test.DLFileEntryUADTestUtil;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.message.boards.test.util.MBTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.test.util.BaseHasAssetEntryUADAnonymizerTestCase;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class DLFileEntryUADAnonymizerTest
	extends BaseHasAssetEntryUADAnonymizerTestCase<DLFileEntry> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAnonymizeDLFileEntryVersions() throws Exception {
		DLFileEntry dlFileEntry = addBaseModel(user.getUserId());

		long dlFileEntryId = dlFileEntry.getFileEntryId();

		_randomDLFileEntryUpdate(dlFileEntryId);
		_randomDLFileEntryUpdate(dlFileEntryId);
		_randomDLFileEntryUpdate(dlFileEntryId);

		dlFileEntry = _dlFileEntryLocalService.getDLFileEntry(dlFileEntryId);

		List<DLFileVersion> dlFileVersions = dlFileEntry.getFileVersions(
			WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			dlFileVersions.toString(), 4, dlFileVersions.size());

		uadAnonymizer.autoAnonymize(
			dlFileEntry, user.getUserId(), anonymousUser);

		dlFileEntry = _dlFileEntryLocalService.getDLFileEntry(dlFileEntryId);

		dlFileVersions = dlFileEntry.getFileVersions(
			WorkflowConstants.STATUS_ANY);

		for (DLFileVersion dlFileVersion : dlFileVersions) {
			_assertDLFileVersionAnonymized(dlFileVersion);
		}
	}

	@Test
	public void testRemoveAssetLinks() throws Exception {
		DLFileEntry dlFileEntry = addBaseModel(user.getUserId());

		AssetEntry dlFileEntryAssetEntry = _assetEntryLocalService.fetchEntry(
			DLFileEntry.class.getName(), dlFileEntry.getFileEntryId());

		Assert.assertNotNull(
			"There should be an associated AssetEntry for the DLFileEntry.",
			dlFileEntryAssetEntry);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(dlFileEntry.getGroupId());

		serviceContext.setAssetEntryVisible(true);
		serviceContext.setAssetLinkEntryIds(
			new long[] {dlFileEntryAssetEntry.getEntryId()});
		serviceContext.setAssetTagNames(new String[0]);

		MBMessage mbMessage = MBTestUtil.addMessageWithWorkflow(
			dlFileEntry.getGroupId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), true,
			serviceContext);

		try {
			AssetEntry mbMessageAssetEntry = _assetEntryLocalService.fetchEntry(
				MBMessage.class.getName(), mbMessage.getMessageId());

			List<AssetLink> assetLinks = _assetLinkLocalService.getDirectLinks(
				mbMessageAssetEntry.getEntryId());

			Assert.assertEquals(
				"There should be an asset link for the newly created " +
					"MBMessage.",
				1, assetLinks.size());

			AssetLink assetLink = assetLinks.get(0);

			Assert.assertEquals(
				"The AssetLink should be associated with the DLFileEntry",
				assetLink.getEntryId2(), dlFileEntryAssetEntry.getEntryId());

			_uadAnonymizer.delete(dlFileEntry);

			dlFileEntryAssetEntry = _assetEntryLocalService.fetchEntry(
				DLFileEntry.class.getName(), dlFileEntry.getFileEntryId());

			Assert.assertNull(
				"The anonymizer should also remove the AssetEntry.",
				dlFileEntryAssetEntry);

			assetLink = _assetLinkLocalService.fetchAssetLink(
				assetLink.getLinkId());

			Assert.assertNull(
				"The anonymizer should also remove the AssetLink.", assetLink);
		}
		finally {
			_mbThreadLocalService.deleteThread(mbMessage.getThread());
		}
	}

	@Override
	protected DLFileEntry addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected DLFileEntry addBaseModel(long userId, boolean deleteAfterTestRun)
		throws Exception {

		return DLFileEntryUADTestUtil.addDLFileEntry(
			_dlAppLocalService, _dlFileEntryLocalService, _dlFolderLocalService,
			userId, _group.getGroupId());
	}

	@Override
	protected void deleteBaseModels(List<DLFileEntry> baseModels)
		throws Exception {

		DLFileEntryUADTestUtil.cleanUpDependencies(
			_dlAppLocalService, _dlFileEntryLocalService, _dlFolderLocalService,
			baseModels);
	}

	@Override
	protected UADAnonymizer<DLFileEntry> getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.getDLFileEntry(
			baseModelPK);

		String userName = dlFileEntry.getUserName();

		if ((dlFileEntry.getUserId() != user.getUserId()) &&
			!userName.equals(user.getFullName()) &&
			isAssetEntryAutoAnonymized(
				DLFileEntry.class.getName(), dlFileEntry.getFileEntryId(),
				user)) {

			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		if (_dlFileEntryLocalService.fetchDLFileEntry(baseModelPK) == null) {
			return true;
		}

		return false;
	}

	private void _assertDLFileVersionAnonymized(DLFileVersion dlFileVersion) {
		Assert.assertEquals(
			dlFileVersion.toString(), anonymousUser.getUserId(),
			dlFileVersion.getUserId());
		Assert.assertEquals(
			dlFileVersion.toString(), anonymousUser.getUserId(),
			dlFileVersion.getStatusByUserId());
		Assert.assertEquals(
			dlFileVersion.toString(), anonymousUser.getFullName(),
			dlFileVersion.getUserName());
		Assert.assertEquals(
			dlFileVersion.toString(), anonymousUser.getFullName(),
			dlFileVersion.getStatusByUserName());
	}

	private void _randomDLFileEntryUpdate(long dlFileEntryId) throws Exception {
		DLFileEntry dlFileEntry = _dlFileEntryLocalService.getDLFileEntry(
			dlFileEntryId);

		_dlAppLocalService.updateFileEntry(
			dlFileEntry.getUserId(), dlFileEntryId,
			RandomTestUtil.randomString(), ContentTypes.TEXT,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			DLVersionNumberIncrease.fromMajorVersion(true),
			dlFileEntry.getContentStream(), dlFileEntry.getSize(), null, null,
			ServiceContextTestUtil.getServiceContext());
	}

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@Inject
	private AssetLinkLocalService _assetLinkLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject
	private DLFolderLocalService _dlFolderLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private MBThreadLocalService _mbThreadLocalService;

	@Inject(filter = "component.name=*.DLFileEntryUADAnonymizer")
	private UADAnonymizer<DLFileEntry> _uadAnonymizer;

}