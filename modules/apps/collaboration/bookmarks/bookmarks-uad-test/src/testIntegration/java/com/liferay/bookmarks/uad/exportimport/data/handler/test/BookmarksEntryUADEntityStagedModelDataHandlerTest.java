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

package com.liferay.bookmarks.uad.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.uad.constants.BookmarksUADConstants;
import com.liferay.bookmarks.uad.entity.BookmarksEntryUADEntity;
import com.liferay.bookmarks.uad.test.BookmarksEntryUADEntityTestHelper;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.exporter.UADEntityExporter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class BookmarksEntryUADEntityStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser();
	}

	@Ignore
	@Test
	public void testCleanStagedModelDataHandler() throws Exception {
	}

	@Override
	@Test
	public void testStagedModelDataHandler() throws Exception {
		StagedModelDataHandler stagedModelDataHandler =
			_uadEntityExporter.getStagedModelDataHandler();

		String className = stagedModelDataHandler.getClassNames()[0];

		Assert.assertEquals(BookmarksEntryUADEntity.class.getName(), className);
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		long userId = _user.getUserId();

		BookmarksEntry bookmarksEntry =
			_bookmarksEntryUADEntityTestHelper.addBookmarksEntry(userId);

		_bookmarksEntries.add(bookmarksEntry);

		String uadEntityId = _getBookmarksEntryUADEntityId(
			userId, bookmarksEntry);

		return new BookmarksEntryUADEntity(userId, uadEntityId, bookmarksEntry);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return _uadEntityAggregator.getUADEntity(uuid);
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return BookmarksEntryUADEntity.class;
	}

	private String _getBookmarksEntryUADEntityId(
		long userId, BookmarksEntry bookmarksEntry) {

		return String.valueOf(bookmarksEntry.getEntryId()) + StringPool.POUND +
			String.valueOf(userId);
	}

	@DeleteAfterTestRun
	private final List<BookmarksEntry> _bookmarksEntries = new ArrayList<>();

	@Inject
	private BookmarksEntryUADEntityTestHelper
		_bookmarksEntryUADEntityTestHelper;

	@Inject(
		filter = "model.class.name=" + BookmarksUADConstants.CLASS_NAME_BOOKMARKS_ENTRY
	)
	private UADEntityAggregator _uadEntityAggregator;

	@Inject(
		filter = "model.class.name=" + BookmarksUADConstants.CLASS_NAME_BOOKMARKS_ENTRY
	)
	private UADEntityExporter _uadEntityExporter;

	@DeleteAfterTestRun
	private User _user;

}