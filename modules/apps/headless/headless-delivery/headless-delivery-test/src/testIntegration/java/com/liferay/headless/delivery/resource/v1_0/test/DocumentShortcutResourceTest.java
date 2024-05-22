/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.headless.delivery.client.dto.v1_0.DocumentShortcut;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.util.GroupUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class DocumentShortcutResourceTest
	extends BaseDocumentShortcutResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testGroup.getGroupId(), TestPropsValues.getUserId());
	}

	@Override
	protected DocumentShortcut randomDocumentShortcut() throws Exception {
		DocumentShortcut documentShortcut = super.randomDocumentShortcut();

		FileEntry fileEntry = _addFileEntry(testGroup.getGroupId());

		documentShortcut.setFolderId(0L);
		documentShortcut.setTargetDocumentId(fileEntry.getFileEntryId());

		return documentShortcut;
	}

	@Override
	protected DocumentShortcut
			testGetAssetLibraryDocumentShortcutsPage_addDocumentShortcut(
				Long assetLibraryId, DocumentShortcut documentShortcut)
		throws Exception {

		if (assetLibraryId.equals(
				testGetAssetLibraryDocumentShortcutsPage_getIrrelevantAssetLibraryId())) {

			return randomIrrelevantDocumentShortcut();
		}

		return _addDocumentShortcut(testDepotEntry.getGroup());
	}

	@Override
	protected DocumentShortcut testGetDocumentShortcut_addDocumentShortcut()
		throws Exception {

		return _addDocumentShortcut();
	}

	@Override
	protected DocumentShortcut
			testGetSiteDocumentShortcutsPage_addDocumentShortcut(
				Long siteId, DocumentShortcut documentShortcut)
		throws Exception {

		if (siteId.equals(
				testGetSiteDocumentShortcutsPage_getIrrelevantSiteId())) {

			return _addDocumentShortcut(irrelevantGroup);
		}

		return _addDocumentShortcut();
	}

	@Override
	protected DocumentShortcut testGraphQLDocumentShortcut_addDocumentShortcut()
		throws Exception {

		return _addDocumentShortcut();
	}

	private DocumentShortcut _addDocumentShortcut() throws Exception {
		return _addDocumentShortcut(testGroup);
	}

	private DocumentShortcut _addDocumentShortcut(Group group)
		throws Exception {

		FileEntry fileEntry = _addFileEntry(group.getGroupId());

		FileShortcut fileShortcut = _dlAppService.addFileShortcut(
			fileEntry.getRepositoryId(), fileEntry.getFolderId(),
			fileEntry.getFileEntryId(), _serviceContext);

		return new DocumentShortcut() {
			{
				assetLibraryKey = GroupUtil.getAssetLibraryKey(group);
				dateCreated = fileShortcut.getCreateDate();
				dateModified = fileShortcut.getModifiedDate();
				folderId = fileShortcut.getFolderId();
				id = fileShortcut.getFileShortcutId();
				siteId = fileShortcut.getGroupId();
				targetDocumentId = fileShortcut.getToFileEntryId();
				title = fileShortcut.getToTitle();
			}
		};
	}

	private FileEntry _addFileEntry(long groupId) throws Exception {
		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		InputStream inputStream = new ByteArrayInputStream(bytes);

		return _dlAppService.addFileEntry(
			null, groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, inputStream, bytes.length, null,
			null, null, _serviceContext);
	}

	@Inject
	private static DLAppService _dlAppService;

	private ServiceContext _serviceContext;

}