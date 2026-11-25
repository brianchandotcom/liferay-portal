/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v1_1_5.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.test.util.DLAppTestUtil;
import com.liferay.journal.constants.JournalConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

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
public class ContentImagesUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_resourcePrimKey = RandomTestUtil.randomLong();

		_folder = _portletFileRepository.addPortletFolder(
			_group.getGroupId(), TestPropsValues.getUserId(),
			JournalConstants.SERVICE_NAME, 0, String.valueOf(_resourcePrimKey),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test
	@TestInfo({"LPS-98619", "LPS-147234"})
	public void testConvertTypeImageElements() throws Exception {
		_testConvertTypeImageElementsWithDocumentLibraryURL();
		_testConvertTypeImageElementsWithFileEntryIdInData();
		_testConvertTypeImageElementsWithId();
	}

	private FileEntry _addPortletFileEntry(String fileName) throws Exception {
		return _portletFileRepository.addPortletFileEntry(
			_group.getGroupId(), TestPropsValues.getUserId(),
			JournalArticle.class.getName(), _resourcePrimKey,
			JournalConstants.SERVICE_NAME, _folder.getFolderId(),
			RandomTestUtil.randomBytes(), fileName, ContentTypes.IMAGE_JPEG,
			false);
	}

	private void _assertConverted(
			Element dynamicContentElement, FileEntry fileEntry, String name,
			String title)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			dynamicContentElement.getText());

		Assert.assertEquals(
			fileEntry.getFileEntryId(), jsonObject.getLong("fileEntryId"));
		Assert.assertEquals(
			fileEntry.getGroupId(), jsonObject.getLong("groupId"));
		Assert.assertEquals(name, jsonObject.getString("name"));
		Assert.assertEquals(
			_resourcePrimKey, jsonObject.getLong("resourcePrimKey"));
		Assert.assertEquals(title, jsonObject.getString("title"));
		Assert.assertEquals("journal", jsonObject.getString("type"));
		Assert.assertEquals(fileEntry.getUuid(), jsonObject.getString("uuid"));
	}

	private Element _convertTypeImageElements(String dynamicContent)
		throws Exception {

		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.journal.internal.upgrade.v1_1_5." +
				"ContentImagesUpgradeProcess");

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
					"\" type=\"image\">", dynamicContent,
					"</dynamic-element></root>"),
				_resourcePrimKey));

		Element rootElement = document.getRootElement();

		Element dynamicElementElement = rootElement.element("dynamic-element");

		return dynamicElementElement.element("dynamic-content");
	}

	private void _testConvertTypeImageElementsWithDocumentLibraryURL()
		throws Exception {

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(_group.getGroupId());

		_assertConverted(
			_convertTypeImageElements(
				StringBundler.concat(
					"<dynamic-content alt=\"\" fileEntryId=\"\" ",
					"language-id=\"en_US\" name=\"", fileEntry.getFileName(),
					"\" title=\"", fileEntry.getTitle(),
					"\" type=\"document\"><![CDATA[/documents/",
					fileEntry.getGroupId(), "/", fileEntry.getFolderId(), "/",
					fileEntry.getTitle(), "/", fileEntry.getUuid(),
					"]]></dynamic-content>")),
			fileEntry, fileEntry.getFileName(), fileEntry.getTitle());
	}

	private void _testConvertTypeImageElementsWithFileEntryIdInData()
		throws Exception {

		FileEntry fileEntry = _addPortletFileEntry(
			RandomTestUtil.randomString() + ".png");

		_assertConverted(
			_convertTypeImageElements(
				StringBundler.concat(
					"<dynamic-content language-id=\"en_US\"><![CDATA[",
					JSONUtil.put(
						"alt", ""
					).put(
						"fileEntryId", fileEntry.getFileEntryId()
					).put(
						"groupId", fileEntry.getGroupId()
					).put(
						"name", fileEntry.getFileName()
					).put(
						"resourcePrimKey", _resourcePrimKey
					).put(
						"title", fileEntry.getTitle()
					).put(
						"type", "journal"
					).put(
						"uuid", fileEntry.getUuid()
					),
					"]]></dynamic-content>")),
			fileEntry, fileEntry.getFileName(), fileEntry.getTitle());
	}

	private void _testConvertTypeImageElementsWithId() throws Exception {
		FileEntry fileEntry = _addPortletFileEntry(
			String.valueOf(RandomTestUtil.randomLong()));

		String imageURL = StringBundler.concat(
			"/image/journal/article?img_id=", fileEntry.getFileName(), "&t=",
			RandomTestUtil.randomLong());

		_assertConverted(
			_convertTypeImageElements(
				StringBundler.concat(
					"<dynamic-content alt=\"\" fileEntryId=\"\" id=\"",
					fileEntry.getFileName(),
					"\" language-id=\"en_US\" name=\"Document_1.jpg\" ",
					"title=\"Document_1.jpg\" type=\"journal\"><![CDATA[",
					imageURL, "]]></dynamic-content>")),
			fileEntry, "Document_1.jpg", "Document_1.jpg");
	}

	private Folder _folder;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private PortletFileRepository _portletFileRepository;

	private long _resourcePrimKey;

	@Inject(
		filter = "(&(component.name=com.liferay.journal.internal.upgrade.registry.JournalServiceUpgradeStepRegistrator))"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}