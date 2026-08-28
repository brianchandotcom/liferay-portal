/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.controller.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.link.model.adapter.StagedAssetLink;
import com.liferay.exportimport.configuration.ExportImportServiceConfiguration;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.test.util.lar.BaseExportImportTestCase;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactory;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jaime León
 */
@RunWith(Arquillian.class)
public class PortletExportControllerTest extends BaseExportImportTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		super.setUp();
	}

	@Test
	@TestInfo("LPS-83011")
	public void testExportAssetLinksIncludeAllAssetLinks() throws Exception {
		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		addAssetLink(journalArticle1, journalArticle2, 0);

		Map<String, String[]> exportParameterMap = getExportParameterMap();

		exportParameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.FALSE.toString()});

		exportLayouts(new long[] {layout.getLayoutId()}, exportParameterMap);

		List<String> stagedAssetLinkEntries = _getStagedAssetLinkEntries();

		Assert.assertTrue(
			stagedAssetLinkEntries.toString(),
			stagedAssetLinkEntries.isEmpty());

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						ExportImportServiceConfiguration.class.getName(),
						HashMapDictionaryBuilder.<String, Object>put(
							"includeAllAssetLinks", true
						).build())) {

			exportLayouts(
				new long[] {layout.getLayoutId()}, exportParameterMap);
		}

		stagedAssetLinkEntries = _getStagedAssetLinkEntries();

		Assert.assertFalse(
			stagedAssetLinkEntries.toString(),
			stagedAssetLinkEntries.isEmpty());
	}

	private List<String> _getStagedAssetLinkEntries() throws Exception {
		try (ZipReader zipReader = _zipReaderFactory.getZipReader(larFile)) {
			return ListUtil.filter(
				zipReader.getEntries(),
				entry -> entry.contains(StagedAssetLink.class.getName()));
		}
	}

	@Inject
	private ZipReaderFactory _zipReaderFactory;

}