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

package com.liferay.document.library.uad.display.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.document.library.uad.test.DLFileEntryUADTestUtil;
import com.liferay.document.library.uad.test.DLFolderUADTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.test.util.BaseUADDisplayTestCase;

import java.io.Serializable;

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
public class DLFileEntryUADDisplayTest
	extends BaseUADDisplayTestCase<DLFileEntry> {

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
	public void testGetParentContainerId() throws Exception {
		assertParentContainerId(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		DLFolder dlFolder = DLFolderUADTestUtil.addDLFolder(
			_dlAppLocalService, _dlFolderLocalService,
			TestPropsValues.getUserId(), _group.getGroupId());

		assertParentContainerId(dlFolder.getFolderId());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetTopLevelContainer() throws Exception {
		_uadDisplay.getTopLevelContainer(null, null, null);
	}

	@Override
	protected DLFileEntry addBaseModel(long userId) throws Exception {
		return DLFileEntryUADTestUtil.addDLFileEntry(
			_dlAppLocalService, _dlFileEntryLocalService, _dlFolderLocalService,
			userId, _group.getGroupId());
	}

	protected void assertParentContainerId(long dlFolderId) throws Exception {
		DLFileEntry dlFileEntry = DLFileEntryUADTestUtil.addDLFileEntry(
			_dlAppLocalService, _dlFileEntryLocalService, dlFolderId,
			TestPropsValues.getUserId(), _group.getGroupId());

		Serializable parentContainerId = _uadDisplay.getParentContainerId(
			dlFileEntry);

		Assert.assertEquals(dlFolderId, (long)parentContainerId);
	}

	@Override
	protected UADDisplay getUADDisplay() {
		return _uadDisplay;
	}

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject
	private DLFolderLocalService _dlFolderLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(filter = "component.name=*.DLFileEntryUADDisplay")
	private UADDisplay<DLFileEntry> _uadDisplay;

}