/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.search.spi.model.index.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.store.DLStore;
import com.liferay.document.library.kernel.store.DLStoreRequest;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;

import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class DLFileEntryModelDocumentContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testCachedTextExtractionIsNotReused() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				_getConfigurationTemporarySwapper(false)) {

			DLFileEntry dlFileEntry = _addDLFileEntry();

			DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

			_dlStore.addFile(
				DLStoreRequest.builder(
					dlFileEntry.getCompanyId(),
					dlFileEntry.getDataRepositoryId(), dlFileEntry.getName()
				).versionLabel(
					dlFileVersion.getStoreFileName() + ".index"
				).build(),
				"overriden".getBytes(StandardCharsets.UTF_8));

			Document document = new DocumentImpl();

			_dlFileEntryModelDocumentContributor.contribute(
				document, dlFileEntry);

			Assert.assertNotEquals(
				"overriden",
				document.get(
					PortalUtil.getSiteDefaultLocale(dlFileEntry.getGroupId()),
					Field.CONTENT));
		}
	}

	@Test
	public void testCachedTextExtractionIsReused() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				_getConfigurationTemporarySwapper(true)) {

			DLFileEntry dlFileEntry = _addDLFileEntry();

			DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

			_dlStore.addFile(
				DLStoreRequest.builder(
					dlFileEntry.getCompanyId(),
					dlFileEntry.getDataRepositoryId(), dlFileEntry.getName()
				).versionLabel(
					dlFileVersion.getStoreFileName() + ".index"
				).build(),
				"overriden".getBytes(StandardCharsets.UTF_8));

			Document document = new DocumentImpl();

			_dlFileEntryModelDocumentContributor.contribute(
				document, dlFileEntry);

			Assert.assertEquals(
				"overriden",
				document.get(
					PortalUtil.getSiteDefaultLocale(dlFileEntry.getGroupId()),
					Field.CONTENT));
		}
	}

	@Test
	public void testTextExtractionIsCachedInDLStore() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				_getConfigurationTemporarySwapper(true)) {

			DLFileEntry dlFileEntry = _addDLFileEntry();

			Assert.assertFalse(_hasFile(dlFileEntry));

			_dlFileEntryModelDocumentContributor.contribute(
				new DocumentImpl(), dlFileEntry);

			Assert.assertEquals(
				StreamUtil.toString(dlFileEntry.getContentStream()),
				_getFileAsString(dlFileEntry));
			Assert.assertTrue(_hasFile(dlFileEntry));
		}
	}

	@Test
	public void testTextExtractionIsNotCachedInDLStore() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				_getConfigurationTemporarySwapper(false)) {

			DLFileEntry dlFileEntry = _addDLFileEntry();

			Assert.assertFalse(_hasFile(dlFileEntry));

			_dlFileEntryModelDocumentContributor.contribute(
				new DocumentImpl(), dlFileEntry);

			Assert.assertFalse(_hasFile(dlFileEntry));
		}
	}

	private DLFileEntry _addDLFileEntry() throws Exception {
		String content = StringUtil.randomString();

		byte[] bytes = content.getBytes(StandardCharsets.UTF_8);

		return _dlFileEntryLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), null, StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, new UnsyncByteArrayInputStream(bytes), bytes.length, null,
			null, null, ServiceContextTestUtil.getServiceContext());
	}

	private ConfigurationTemporarySwapper _getConfigurationTemporarySwapper(
			boolean cacheTextExtraction)
		throws Exception {

		return new ConfigurationTemporarySwapper(
			"com.liferay.document.library.internal.configuration." +
				"DLIndexerConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"cacheTextExtraction", cacheTextExtraction
			).build());
	}

	private String _getFileAsString(DLFileEntry dlFileEntry)
		throws IOException, PortalException {

		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		return StringUtil.trim(
			StreamUtil.toString(
				_dlStore.getFileAsStream(
					dlFileEntry.getCompanyId(),
					dlFileEntry.getDataRepositoryId(), dlFileEntry.getName(),
					dlFileVersion.getStoreFileName() + ".index")));
	}

	private boolean _hasFile(DLFileEntry dlFileEntry) throws PortalException {
		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		return _dlStore.hasFile(
			dlFileEntry.getCompanyId(), dlFileEntry.getDataRepositoryId(),
			dlFileEntry.getName(), dlFileVersion.getStoreFileName() + ".index");
	}

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject(
		filter = "component.name=com.liferay.document.library.internal.search.spi.model.index.contributor.DLFileEntryModelDocumentContributor"
	)
	private ModelDocumentContributor<DLFileEntry>
		_dlFileEntryModelDocumentContributor;

	@Inject
	private DLStore _dlStore;

}