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

package com.liferay.content.dashboard.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.content.dashboard.item.ContentDashboardItem;
import com.liferay.content.dashboard.item.ContentDashboardItemFactory;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Yurena Cabrera
 */
@RunWith(Arquillian.class)
public class GetContentDashboardItemInfoMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());
	}

	@Test
	public void testGetFileSpecificFields() throws Exception {
		FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
			"Site", TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "fileName.pdf",
			"application/pdf", new byte[0], new Date(150000), new Date(150000),
			_serviceContext);

		ContentDashboardItem contentDashboardItem =
			_contentDashboardFileItemFactory.create(fileEntry.getPrimaryKey());

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "_getSpecificFieldsJSONObject",
			new Class<?>[] {ContentDashboardItem.class, Locale.class},
			contentDashboardItem, LocaleUtil.SPAIN);

		Assert.assertNotNull(jsonObject);
		Assert.assertNotNull(jsonObject.getString("extension"));
		Assert.assertNotNull(jsonObject.getString("size"));
		Assert.assertNotNull(jsonObject.getString("file-name"));
	}

	@Test
	public void testGetJournalArticleSpecificFields() throws Exception {
		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASS_NAME_ID_DEFAULT, StringPool.BLANK,
			true,
			HashMapBuilder.put(
				LocaleUtil.SPAIN, RandomTestUtil.randomString()
			).put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			new HashMap<>(),
			HashMapBuilder.put(
				LocaleUtil.SPAIN, RandomTestUtil.randomString()
			).put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			null, LocaleUtil.getSiteDefault(), null, false, false,
			_serviceContext);

		ContentDashboardItem contentDashboardItem =
			_contentDashboardJournalItemFactory.create(
				journalArticle.getResourcePrimKey());

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "_getSpecificFieldsJSONObject",
			new Class<?>[] {ContentDashboardItem.class, Locale.class},
			contentDashboardItem, LocaleUtil.SPAIN);

		Assert.assertNotNull(jsonObject);
		Assert.assertNotNull(jsonObject.getString("review-date"));
		Assert.assertNotNull(jsonObject.getString("display-date"));
		Assert.assertNotNull(jsonObject.getString("expiration-date"));
	}

	@Inject(
		filter = "component.name=com.liferay.content.dashboard.document.library.internal.item.FileEntryContentDashboardItemFactory"
	)
	private ContentDashboardItemFactory _contentDashboardFileItemFactory;

	@Inject(
		filter = "component.name=com.liferay.content.dashboard.journal.internal.item.JournalArticleContentDashboardItemFactory"
	)
	private ContentDashboardItemFactory _contentDashboardJournalItemFactory;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "mvc.command.name=/content_dashboard/get_content_dashboard_item_info"
	)
	private MVCResourceCommand _mvcResourceCommand;

	private ServiceContext _serviceContext;

}