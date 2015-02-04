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

package com.liferay.wiki.social;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.test.BaseSocialActivityInterpreterTestCase;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.portlet.trash.util.TrashUtil;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.util.WikiTestUtil;

import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Zsolt Berentey
 */
@Sync
public class WikiActivityInterpreterTest
	extends BaseSocialActivityInterpreterTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	protected void addActivities() throws Exception {
		WikiNode wikiNode = WikiTestUtil.addNode(group.getGroupId());

		_page = WikiTestUtil.addPage(
			group.getGroupId(), wikiNode.getNodeId(), true);

		_attachmentFileName = RandomTestUtil.randomString() + ".docx";

		WikiTestUtil.addWikiAttachment(
			_page.getUserId(), _page.getNodeId(), _page.getTitle(),
			_attachmentFileName, getClass());
	}

	@Override
	protected SocialActivityInterpreter getActivityInterpreter() {
		return new WikiActivityInterpreter();
	}

	@Override
	protected int[] getActivityTypes() {
		return new int[] {
			SocialActivityConstants.TYPE_ADD_ATTACHMENT,
			SocialActivityConstants.TYPE_MOVE_ATTACHMENT_TO_TRASH,
			SocialActivityConstants.TYPE_MOVE_TO_TRASH,
			SocialActivityConstants.TYPE_RESTORE_ATTACHMENT_FROM_TRASH,
			SocialActivityConstants.TYPE_RESTORE_FROM_TRASH,
			WikiActivityKeys.ADD_PAGE, WikiActivityKeys.UPDATE_PAGE
		};
	}

	@Override
	protected boolean isSupportsRename(String className) {
		return false;
	}

	@Override
	protected void moveModelsToTrash() throws Exception {
		FileEntry fileEntry =
			WikiPageLocalServiceUtil.movePageAttachmentToTrash(
				TestPropsValues.getUserId(), _page.getNodeId(),
				_page.getTitle(), _attachmentFileName);

		TrashEntry trashEntry = TrashEntryLocalServiceUtil.getEntry(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());

		_attachmentFileName = TrashUtil.getTrashTitle(trashEntry.getEntryId());

		WikiPageLocalServiceUtil.movePageToTrash(
			TestPropsValues.getUserId(), _page);
	}

	@Override
	protected void renameModels() throws Exception {
	}

	@Override
	protected void restoreModelsFromTrash() throws Exception {
		WikiPageLocalServiceUtil.restorePageFromTrash(
			TestPropsValues.getUserId(), _page);

		WikiPageLocalServiceUtil.restorePageAttachmentFromTrash(
			TestPropsValues.getUserId(), _page.getNodeId(), _page.getTitle(),
			_attachmentFileName);
	}

	private String _attachmentFileName;
	private WikiPage _page;

}