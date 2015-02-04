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

package com.liferay.portlet.documentlibrary.social;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.test.BaseSocialActivityInterpreterTestCase;

import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Zsolt Berentey
 */
@Sync
public class DLFileEntryActivityInterpreterTest
		extends BaseSocialActivityInterpreterTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	protected void addActivities() throws Exception {
		_fileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString());
	}

	@Override
	protected SocialActivityInterpreter getActivityInterpreter() {
		return new DLFileEntryActivityInterpreter();
	}

	@Override
	protected int[] getActivityTypes() {
		return new int[] {
			DLActivityKeys.ADD_FILE_ENTRY, DLActivityKeys.UPDATE_FILE_ENTRY,
			SocialActivityConstants.TYPE_MOVE_TO_TRASH,
			SocialActivityConstants.TYPE_RESTORE_FROM_TRASH
		};
	}

	@Override
	protected void moveModelsToTrash() throws Exception {
		DLAppServiceUtil.moveFileEntryToTrash(_fileEntry.getFileEntryId());
	}

	@Override
	protected void renameModels() throws Exception {
		DLAppTestUtil.updateFileEntry(
			group.getGroupId(), _fileEntry.getFileEntryId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());
	}

	@Override
	protected void restoreModelsFromTrash() throws Exception {
		DLAppServiceUtil.restoreFileEntryFromTrash(_fileEntry.getFileEntryId());
	}

	private FileEntry _fileEntry;

}