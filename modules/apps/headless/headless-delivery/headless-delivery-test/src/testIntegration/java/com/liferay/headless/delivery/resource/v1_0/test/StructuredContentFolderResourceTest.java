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

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collections;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class StructuredContentFolderResourceTest
	extends BaseStructuredContentFolderResourceTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_depotEntry = DepotEntryLocalServiceUtil.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			null,
			new ServiceContext() {
				{
					setCompanyId(testGroup.getCompanyId());
					setUserId(TestPropsValues.getUserId());
				}
			});

		testGroup = _depotEntry.getGroup();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"description", "name"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"creatorId"};
	}

	@Override
	protected Long
		testGetAssetLibraryStructuredContentFoldersPage_getAssetLibraryId() {

		return _depotEntry.getDepotEntryId();
	}

	@Override
	protected Long
			testGetStructuredContentFolderStructuredContentFoldersPage_getIrrelevantParentStructuredContentFolderId()
		throws Exception {

		JournalFolder journalFolder = JournalTestUtil.addFolder(
			irrelevantGroup.getGroupId(), RandomTestUtil.randomString());

		return journalFolder.getFolderId();
	}

	@Override
	protected Long
			testGetStructuredContentFolderStructuredContentFoldersPage_getParentStructuredContentFolderId()
		throws Exception {

		JournalFolder journalFolder = JournalTestUtil.addFolder(
			testGroup.getGroupId(), RandomTestUtil.randomString());

		return journalFolder.getFolderId();
	}

	private DepotEntry _depotEntry;

}