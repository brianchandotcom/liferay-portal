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

package com.liferay.bookmarks.uad.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.uad.constants.BookmarksUADConstants;
import com.liferay.bookmarks.uad.test.BaseBookmarksEntryUADEntityTestCase;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.aggregator.UADAggregator;
import com.liferay.user.associated.data.exporter.UADExporter;

import java.io.ByteArrayInputStream;
import java.io.File;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Noah Sherrill
 */
@RunWith(Arquillian.class)
public class BookmarksEntryUADExporterTest
	extends BaseBookmarksEntryUADEntityTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser();
	}

	@Test
	public void testExport() throws Exception {
		BookmarksEntry bookmarksEntry = addBookmarksEntry(_user.getUserId());

		List<BookmarksEntry> bookmarksEntries = _uadAggregator.getRange(
			_user.getUserId(), 0, 1);

		BookmarksEntry bookmarksEntry1 = bookmarksEntries.get(0);

		byte[] bytes = _uadExporter.export(bookmarksEntry1);

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			bytes);

		Document document = SAXReaderUtil.read(byteArrayInputStream);

		Node entryIdNode = document.selectSingleNode(
			"/model/column[column-name='entryId']/column-value");
		Node userIdNode = document.selectSingleNode(
			"/model/column[column-name='userId']/column-value");

		Assert.assertEquals(
			String.valueOf(bookmarksEntry.getEntryId()), entryIdNode.getText());
		Assert.assertEquals(
			String.valueOf(_user.getUserId()), userIdNode.getText());
	}

	@Test
	public void testExportAll() throws Exception {
		addBookmarksEntry(_user.getUserId());

		File file = _uadExporter.exportAll(_user.getUserId());

		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(file);

		List<String> entries = zipReader.getEntries();

		Assert.assertEquals(entries.toString(), 1, entries.size());
	}

	@Inject(
		filter = "model.class.name=" + BookmarksUADConstants.CLASS_NAME_BOOKMARKS_ENTRY
	)
	private UADAggregator _uadAggregator;

	@Inject(
		filter = "model.class.name=" + BookmarksUADConstants.CLASS_NAME_BOOKMARKS_ENTRY
	)
	private UADExporter _uadExporter;

	@DeleteAfterTestRun
	private User _user;

}