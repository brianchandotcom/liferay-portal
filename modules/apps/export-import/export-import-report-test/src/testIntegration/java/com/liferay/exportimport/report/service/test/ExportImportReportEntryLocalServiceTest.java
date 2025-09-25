/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.report.constants.ExportImportReportEntryConstants;
import com.liferay.exportimport.report.model.ExportImportReportEntry;
import com.liferay.exportimport.report.service.ExportImportReportEntryLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carlos Correa
 */
@RunWith(Arquillian.class)
public class ExportImportReportEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddEmptyExportImportReportEntry() throws Exception {
		int count =
			_exportImportReportEntryLocalService.
				getExportImportReportEntriesCount();

		long groupId = RandomTestUtil.randomLong();
		long companyId = RandomTestUtil.randomLong();
		String classExternalReferenceCode = RandomTestUtil.randomString();
		long classNameId = RandomTestUtil.randomLong();
		long exportImportConfigurationId = RandomTestUtil.randomLong();
		String modelName = RandomTestUtil.randomString();
		int origin = RandomTestUtil.randomInt();
		String scope = RandomTestUtil.randomString();
		String scopeKey = RandomTestUtil.randomString();

		ExportImportReportEntry exportImportReportEntry =
			_exportImportReportEntryLocalService.
				addEmptyExportImportReportEntry(
					groupId, companyId, classExternalReferenceCode, classNameId,
					exportImportConfigurationId, modelName, origin, scope,
					scopeKey);

		Assert.assertEquals(groupId, exportImportReportEntry.getGroupId());
		Assert.assertEquals(companyId, exportImportReportEntry.getCompanyId());
		Assert.assertEquals(
			classExternalReferenceCode,
			exportImportReportEntry.getClassExternalReferenceCode());
		Assert.assertEquals(
			classNameId, exportImportReportEntry.getClassNameId());
		Assert.assertEquals(
			exportImportConfigurationId,
			exportImportReportEntry.getExportImportConfigurationId());
		Assert.assertNull(exportImportReportEntry.getErrorMessage());
		Assert.assertNull(exportImportReportEntry.getErrorStacktrace());
		Assert.assertEquals(modelName, exportImportReportEntry.getModelName());
		Assert.assertEquals(origin, exportImportReportEntry.getOrigin());
		Assert.assertEquals(scope, exportImportReportEntry.getScope());
		Assert.assertEquals(scopeKey, exportImportReportEntry.getScopeKey());
		Assert.assertEquals(
			ExportImportReportEntryConstants.TYPE_EMPTY,
			exportImportReportEntry.getType());
		Assert.assertEquals(
			ExportImportReportEntryConstants.STATUS_UNRESOLVED,
			exportImportReportEntry.getStatus());

		Assert.assertEquals(
			count + 1,
			_exportImportReportEntryLocalService.
				getExportImportReportEntriesCount());
	}

	@Test
	public void testAddErrorExportImportReportEntry() throws Exception {
		int count =
			_exportImportReportEntryLocalService.
				getExportImportReportEntriesCount();

		long groupId = RandomTestUtil.randomLong();
		long companyId = RandomTestUtil.randomLong();
		String classExternalReferenceCode = RandomTestUtil.randomString();
		long classNameId = RandomTestUtil.randomLong();
		long classPK = RandomTestUtil.randomLong();
		long exportImportConfigurationId = RandomTestUtil.randomLong();
		String errorMessage = RandomTestUtil.randomString();
		String errorStacktrace = RandomTestUtil.randomString();
		String modelName = RandomTestUtil.randomString();
		int origin = RandomTestUtil.randomInt();
		String scope = RandomTestUtil.randomString();
		String scopeKey = RandomTestUtil.randomString();

		ExportImportReportEntry exportImportReportEntry =
			_exportImportReportEntryLocalService.
				addErrorExportImportReportEntry(
					groupId, companyId, classExternalReferenceCode, classNameId,
					classPK, exportImportConfigurationId, errorMessage,
					errorStacktrace, modelName, origin, scope, scopeKey);

		Assert.assertEquals(groupId, exportImportReportEntry.getGroupId());
		Assert.assertEquals(companyId, exportImportReportEntry.getCompanyId());
		Assert.assertEquals(
			classExternalReferenceCode,
			exportImportReportEntry.getClassExternalReferenceCode());
		Assert.assertEquals(
			classNameId, exportImportReportEntry.getClassNameId());
		Assert.assertEquals(classPK, exportImportReportEntry.getClassPK());
		Assert.assertEquals(
			exportImportConfigurationId,
			exportImportReportEntry.getExportImportConfigurationId());
		Assert.assertEquals(
			errorMessage, exportImportReportEntry.getErrorMessage());
		Assert.assertEquals(
			errorStacktrace, exportImportReportEntry.getErrorStacktrace());
		Assert.assertEquals(modelName, exportImportReportEntry.getModelName());
		Assert.assertEquals(origin, exportImportReportEntry.getOrigin());
		Assert.assertEquals(scope, exportImportReportEntry.getScope());
		Assert.assertEquals(scopeKey, exportImportReportEntry.getScopeKey());
		Assert.assertEquals(
			ExportImportReportEntryConstants.TYPE_ERROR,
			exportImportReportEntry.getType());
		Assert.assertEquals(
			ExportImportReportEntryConstants.STATUS_UNRESOLVED,
			exportImportReportEntry.getStatus());

		Assert.assertEquals(
			count + 1,
			_exportImportReportEntryLocalService.
				getExportImportReportEntriesCount());
	}

	@Test
	public void testGetImportReportEntries() throws Exception {
		long companyId = RandomTestUtil.randomLong();
		long exportImportConfigurationId = RandomTestUtil.randomLong();

		List<ExportImportReportEntry> exportImportReportEntries =
			_exportImportReportEntryLocalService.getExportImportReportEntries(
				companyId, exportImportConfigurationId);

		Assert.assertTrue(exportImportReportEntries.isEmpty());

		_exportImportReportEntryLocalService.addEmptyExportImportReportEntry(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			RandomTestUtil.randomString(), RandomTestUtil.randomLong(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
			RandomTestUtil.randomInt(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
		_exportImportReportEntryLocalService.addEmptyExportImportReportEntry(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			RandomTestUtil.randomString(), RandomTestUtil.randomLong(),
			exportImportConfigurationId, RandomTestUtil.randomString(),
			RandomTestUtil.randomInt(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
		_exportImportReportEntryLocalService.addEmptyExportImportReportEntry(
			RandomTestUtil.randomLong(), companyId,
			RandomTestUtil.randomString(), RandomTestUtil.randomLong(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
			RandomTestUtil.randomInt(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
		_exportImportReportEntryLocalService.addEmptyExportImportReportEntry(
			RandomTestUtil.randomLong(), companyId,
			RandomTestUtil.randomString(), RandomTestUtil.randomLong(),
			exportImportConfigurationId, RandomTestUtil.randomString(),
			RandomTestUtil.randomInt(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		exportImportReportEntries =
			_exportImportReportEntryLocalService.getExportImportReportEntries(
				companyId, exportImportConfigurationId);

		Assert.assertTrue(exportImportReportEntries.size() == 1);
	}

	@Inject
	private ExportImportReportEntryLocalService
		_exportImportReportEntryLocalService;

}