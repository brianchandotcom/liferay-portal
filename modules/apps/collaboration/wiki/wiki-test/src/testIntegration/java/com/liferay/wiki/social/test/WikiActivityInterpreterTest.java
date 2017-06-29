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

package com.liferay.wiki.social.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.social.test.BaseSocialActivityInterpreterTestCase;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;
import com.liferay.social.kernel.model.SocialActivityConstants;
import com.liferay.social.kernel.model.SocialActivityInterpreter;
import com.liferay.trash.TrashHelper;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.social.WikiActivityKeys;
import com.liferay.wiki.util.test.WikiTestUtil;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@RunWith(Arquillian.class)
@Sync
public class WikiActivityInterpreterTest
	extends BaseSocialActivityInterpreterTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(TrashHelper.class.getName());

		_serviceTracker.open();
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceTracker.close();
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_trashHelper = _serviceTracker.getService();
	}

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
		return getActivityInterpreter(
			WikiPortletKeys.WIKI, WikiPage.class.getName());
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
		WikiPageLocalServiceUtil.movePageAttachmentToTrash(
			TestPropsValues.getUserId(), _page.getNodeId(), _page.getTitle(),
			_attachmentFileName);

		WikiPageLocalServiceUtil.movePageToTrash(
			TestPropsValues.getUserId(), _page);
	}

	@Override
	protected void renameModels() throws Exception {
	}

	@Override
	protected void restoreModelsFromTrash() throws Exception {
		TrashEntry trashEntry = TrashEntryLocalServiceUtil.getEntry(
			WikiPage.class.getName(), _page.getResourcePrimKey());

		String trashTitle = _trashHelper.getTrashTitle(trashEntry.getEntryId());

		_page.setTitle(trashTitle);

		WikiPageLocalServiceUtil.restorePageAttachmentFromTrash(
			TestPropsValues.getUserId(), _page.getNodeId(), _page.getTitle(),
			_attachmentFileName);

		WikiPageLocalServiceUtil.restorePageFromTrash(
			TestPropsValues.getUserId(), _page);
	}

	private static ServiceTracker<TrashHelper, TrashHelper> _serviceTracker;

	private String _attachmentFileName;
	private WikiPage _page;
	private TrashHelper _trashHelper;

}