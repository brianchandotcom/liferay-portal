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

package com.liferay.announcements.uad.exporter.test;

import com.liferay.announcements.constants.AnnouncementsPortletKeys;
import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.uad.constants.AnnouncementsUADConstants;
import com.liferay.announcements.uad.test.BaseAnnouncementsEntryUADEntityTestCase;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.exporter.UADEntityExporter;

import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Noah Sherrill
 */
@RunWith(Arquillian.class)
public class AnnouncementsEntryUADEntityExporterTest
	extends BaseAnnouncementsEntryUADEntityTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser();
	}

	@Test
	public void testExport() throws Exception {
		AnnouncementsEntry announcementsEntry = addAnnouncementsEntry(
			_user.getUserId());

		UADEntity uadEntity = _uadEntityAggregator.getUADEntity(
			String.valueOf(announcementsEntry.getEntryId()));

		_uadEntityExporter.export(uadEntity);

		FileEntry fileEntry = _getFileEntry(
			announcementsEntry.getCompanyId(), uadEntity.getUADEntityId());

		_verifyFileEntry(fileEntry, announcementsEntry);

		PortletFileRepositoryUtil.deletePortletFileEntry(
			fileEntry.getFileEntryId());
	}

	private FileEntry _getFileEntry(long companyId, String uadEntityId)
		throws Exception {

		Group guestGroup = _groupLocalService.getGroup(
			companyId, GroupConstants.GUEST);

		Repository repository = PortletFileRepositoryUtil.getPortletRepository(
			guestGroup.getGroupId(), AnnouncementsPortletKeys.ANNOUNCEMENTS);

		Folder folder = PortletFileRepositoryUtil.getPortletFolder(
			repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "UADExport");

		return PortletFileRepositoryUtil.getPortletFileEntry(
			guestGroup.getGroupId(), folder.getFolderId(),
			uadEntityId + ".json");
	}

	private void _verifyFileEntry(
			FileEntry fileEntry, AnnouncementsEntry announcementsEntry)
		throws Exception {

		InputStream is = _dlFileEntryLocalService.getFileAsStream(
			fileEntry.getFileEntryId(), fileEntry.getVersion());
		StringWriter stringWriter = new StringWriter();

		IOUtils.copy(is, stringWriter, StringPool.UTF8);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			stringWriter.toString());

		Assert.assertEquals(
			announcementsEntry.getEntryId(), jsonObject.getInt("entryId"));
	}

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject(
		filter = "model.class.name=" + AnnouncementsUADConstants.CLASS_NAME_ANNOUNCEMENTS_ENTRY
	)
	private UADEntityAggregator _uadEntityAggregator;

	@Inject(
		filter = "model.class.name=" + AnnouncementsUADConstants.CLASS_NAME_ANNOUNCEMENTS_ENTRY
	)
	private UADEntityExporter _uadEntityExporter;

	@DeleteAfterTestRun
	private User _user;

}