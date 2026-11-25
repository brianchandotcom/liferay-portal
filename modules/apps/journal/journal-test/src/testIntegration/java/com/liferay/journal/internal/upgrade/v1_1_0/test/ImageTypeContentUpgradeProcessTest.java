/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v1_1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.constants.JournalConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wulbern
 */
@RunWith(Arquillian.class)
public class ImageTypeContentUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		DB db = DBManagerUtil.getDB();

		db.runSQL(
			StringBundler.concat(
				"create table JournalArticleImage (articleImageId LONG not ",
				"null primary key, companyId LONG, groupId LONG, articleId ",
				"VARCHAR(75) null, version DOUBLE)"));

		_group = GroupTestUtil.addGroup();

		_journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		_folder = _portletFileRepository.addPortletFolder(
			_group.getGroupId(), TestPropsValues.getUserId(),
			JournalConstants.SERVICE_NAME, 0,
			String.valueOf(_journalArticle.getResourcePrimKey()),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@After
	public void tearDown() throws Exception {
		DB db = DBManagerUtil.getDB();

		db.runSQL("drop table JournalArticleImage");
	}

	@Test
	@TestInfo("LPS-98619")
	public void testUpgradeProcess() throws Exception {
		long articleImageId = RandomTestUtil.nextLong();
		byte[] bytes = RandomTestUtil.randomBytes();

		_imageLocalService.updateImage(
			_group.getCompanyId(), articleImageId, bytes,
			RandomTestUtil.randomString(), RandomTestUtil.randomInt(),
			RandomTestUtil.randomInt(), bytes.length);

		DB db = DBManagerUtil.getDB();

		db.runSQL(
			StringBundler.concat(
				"insert into JournalArticleImage (articleImageId, companyId, ",
				"groupId, articleId, version) values (", articleImageId, ", ",
				_group.getCompanyId(), ", ", _group.getGroupId(), ", '",
				_journalArticle.getArticleId(), "', ",
				_journalArticle.getVersion(), ")"));

		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.journal.internal.upgrade.v1_1_0." +
				"ImageTypeContentUpgradeProcess");

		upgradeProcess.upgrade();

		Assert.assertNull(_imageLocalService.fetchImage(articleImageId));

		List<FileEntry> fileEntries =
			_portletFileRepository.getPortletFileEntries(
				_group.getGroupId(), _folder.getFolderId());

		Assert.assertEquals(fileEntries.toString(), 1, fileEntries.size());

		FileEntry fileEntry = fileEntries.get(0);

		Assert.assertEquals(
			String.valueOf(articleImageId), fileEntry.getFileName());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_convertTypeImageElements(articleImageId));

		Assert.assertEquals(
			fileEntry.getFileEntryId(), jsonObject.getLong("fileEntryId"));
		Assert.assertEquals(fileEntry.getUuid(), jsonObject.getString("uuid"));
	}

	private String _convertTypeImageElements(long articleImageId)
		throws Exception {

		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.journal.internal.upgrade.v1_1_5." +
				"ContentImagesUpgradeProcess");

		String imageURL = "/image/journal/article?img_id=" + articleImageId;

		Document document = SAXReaderUtil.read(
			ReflectionTestUtil.<String>invoke(
				upgradeProcess, "_convertTypeImageElements",
				new Class<?>[] {
					long.class, long.class, long.class, String.class, long.class
				},
				TestPropsValues.getUserId(), _group.getGroupId(),
				_group.getCompanyId(),
				StringBundler.concat(
					"<?xml version=\"1.0\"?><root><dynamic-element ",
					"index-type=\"\" name=\"", RandomTestUtil.randomString(),
					"\" type=\"image\"><dynamic-content alt=\"\" ",
					"fileEntryId=\"\" id=\"", articleImageId,
					"\" language-id=\"en_US\" type=\"journal\"><![CDATA[",
					imageURL, "]]></dynamic-content></dynamic-element></root>"),
				_journalArticle.getResourcePrimKey()));

		Element rootElement = document.getRootElement();

		Element dynamicElementElement = rootElement.element("dynamic-element");

		Element dynamicContentElement = dynamicElementElement.element(
			"dynamic-content");

		return dynamicContentElement.getText();
	}

	private Folder _folder;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private ImageLocalService _imageLocalService;

	private JournalArticle _journalArticle;

	@Inject
	private PortletFileRepository _portletFileRepository;

	@Inject(
		filter = "(&(component.name=com.liferay.journal.internal.upgrade.registry.JournalServiceUpgradeStepRegistrator))"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}