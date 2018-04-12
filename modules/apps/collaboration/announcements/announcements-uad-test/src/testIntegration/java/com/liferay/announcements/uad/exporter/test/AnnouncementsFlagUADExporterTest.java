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

import com.liferay.announcements.kernel.model.AnnouncementsFlag;
import com.liferay.announcements.uad.constants.AnnouncementsUADConstants;
import com.liferay.announcements.uad.test.BaseAnnouncementsFlagUADEntityTestCase;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
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
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Noah Sherrill
 */
@RunWith(Arquillian.class)
public class AnnouncementsFlagUADExporterTest
	extends BaseAnnouncementsFlagUADEntityTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();
	}

	@Test
	public void testExport() throws Exception {
		AnnouncementsFlag announcementsFlag = addAnnouncementsFlag(
			_user.getUserId());

		List<AnnouncementsFlag> announcementsFlags = _uadAggregator.getRange(
			_user.getUserId(), 0, 1);

		AnnouncementsFlag announcementsFlag1 = announcementsFlags.get(0);

		byte[] bytes = _uadExporter.export(announcementsFlag1);

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			bytes);

		Document document = SAXReaderUtil.read(byteArrayInputStream);

		Node entryIdNode = document.selectSingleNode(
			"/model/column[column-name='flagId']/column-value");
		Node userIdNode = document.selectSingleNode(
			"/model/column[column-name='userId']/column-value");

		Assert.assertEquals(
			String.valueOf(announcementsFlag.getFlagId()),
			entryIdNode.getText());
		Assert.assertEquals(
			String.valueOf(_user.getUserId()), userIdNode.getText());
	}

	@Test
	public void testExportAll() throws Exception {
		addAnnouncementsFlag(_user.getUserId());

		File file = _uadExporter.exportAll(_user.getUserId());

		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(file);

		List<String> entries = zipReader.getEntries();

		Assert.assertEquals(entries.toString(), 1, entries.size());
	}

	@Inject(
		filter = "model.class.name=" + AnnouncementsUADConstants.CLASS_NAME_ANNOUNCEMENTS_FLAG
	)
	private UADAggregator _uadAggregator;

	@Inject(
		filter = "model.class.name=" + AnnouncementsUADConstants.CLASS_NAME_ANNOUNCEMENTS_FLAG
	)
	private UADExporter _uadExporter;

	@DeleteAfterTestRun
	private User _user;

}