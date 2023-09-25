/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.search.DDMStructureIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Fabiano Nazar
 * @author Lucas Marques de Paula
 */
@RunWith(Arquillian.class)
@Sync
public class DLFileEntryMetadataDDMStructureIndexerTest
	extends BaseDLIndexerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setGroup(dlFixture.addGroup());
		setIndexerClass(DLFileEntry.class);
		setUser(dlFixture.addUser());

		dlFixture.updateDisplaySettings(LocaleUtil.JAPAN);

		setUpDLFileEntryIndexerFixture();
		setUpFileEntryMetadataFixture();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		fileEntryMetadataFixture.tearDown();
	}

	@Test
	public void testReindexDLFileEntry() throws Exception, PortalException {
		Locale locale = LocaleUtil.JAPAN;
		String fileName_jp = "content_search.txt";
		String searchTerm = "新規";

		DDMStructure ddmStructure =
			fileEntryMetadataFixture.createStructureWithDLFileEntry(
				fileName_jp, locale);

		Document document = indexerFixture.searchOnlyOne(searchTerm, locale);

		indexerFixture.deleteDocument(document);

		Message message = new Message();

		message.put("ddmStructureIndexer", _ddmStructureIndexer);
		message.put("structureId", ddmStructure.getStructureId());

		_messageBus.sendMessage("liferay/ddm_structure_reindex", message);

		dlSearchFixture.searchOnlyOne(searchTerm, LocaleUtil.JAPAN);
	}

	protected void setIndexerEnable(boolean indexerEnabled) {
		Indexer<?> indexer = IndexerRegistryUtil.getIndexer(DLFileEntry.class);

		indexer.setIndexerEnabled(indexerEnabled);
	}

	protected void setUpDLFileEntryIndexerFixture() {
		indexerFixture = new IndexerFixture<>(DLFileEntry.class);
	}

	protected void setUpFileEntryMetadataFixture() {
		fileEntryMetadataFixture = new DLFileEntryMetadataDDMStructureFixture(
			dlFixture, dlAppLocalService, ddmStructureLocalService,
			dlFileEntryTypeLocalService);
	}

	@Inject
	protected DDMStructureLocalService ddmStructureLocalService;

	@Inject
	protected DLFileEntryTypeLocalService dlFileEntryTypeLocalService;

	protected DLFileEntryMetadataDDMStructureFixture fileEntryMetadataFixture;
	protected IndexerFixture<DLFileEntry> indexerFixture;

	@Inject
	private static MessageBus _messageBus;

	@Inject(
		filter = "ddm.structure.indexer.class.name=com.liferay.document.library.kernel.model.DLFileEntryMetadata"
	)
	private DDMStructureIndexer _ddmStructureIndexer;

}