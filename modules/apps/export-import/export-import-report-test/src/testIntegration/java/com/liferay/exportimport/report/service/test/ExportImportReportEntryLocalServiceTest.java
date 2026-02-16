/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.report.constants.ExportImportReportEntryConstants;
import com.liferay.exportimport.report.model.ExportImportReportEntry;
import com.liferay.exportimport.report.service.ExportImportReportEntryLocalService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
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
		long companyId = TestPropsValues.getCompanyId();
		String classExternalReferenceCode = RandomTestUtil.randomString();
		long classNameId = RandomTestUtil.randomLong();
		long exportImportConfigurationId = RandomTestUtil.randomLong();
		String modelNameLanguageKey = RandomTestUtil.randomString();

		ExportImportReportEntry exportImportReportEntry =
			_exportImportReportEntryLocalService.
				addEmptyExportImportReportEntry(
					groupId, companyId, classExternalReferenceCode, classNameId,
					exportImportConfigurationId, modelNameLanguageKey);

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
		Assert.assertEquals(
			modelNameLanguageKey,
			exportImportReportEntry.getModelNameLanguageKey());
		Assert.assertEquals(
			ExportImportReportEntryConstants.ORIGIN_STAGING,
			exportImportReportEntry.getOrigin());
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
		long companyId = TestPropsValues.getCompanyId();
		String classExternalReferenceCode = RandomTestUtil.randomString();
		long classNameId = RandomTestUtil.randomLong();
		long classPK = RandomTestUtil.randomLong();
		long exportImportConfigurationId = RandomTestUtil.randomLong();
		String errorMessage = RandomTestUtil.randomString();
		String errorStacktrace = RandomTestUtil.randomString();
		String modelNameLanguageKey = RandomTestUtil.randomString();

		ExportImportReportEntry exportImportReportEntry =
			_exportImportReportEntryLocalService.
				addErrorExportImportReportEntry(
					groupId, companyId, classExternalReferenceCode, classNameId,
					classPK, exportImportConfigurationId, errorMessage,
					errorStacktrace, modelNameLanguageKey);

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
		Assert.assertEquals(
			modelNameLanguageKey,
			exportImportReportEntry.getModelNameLanguageKey());
		Assert.assertEquals(
			ExportImportReportEntryConstants.ORIGIN_STAGING,
			exportImportReportEntry.getOrigin());
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
	public void testGetExportImportReportEntries() throws Exception {
		long exportImportConfigurationId = RandomTestUtil.randomLong();

		List<ExportImportReportEntry> exportImportReportEntries =
			_exportImportReportEntryLocalService.getExportImportReportEntries(
				TestPropsValues.getCompanyId(), exportImportConfigurationId);

		Assert.assertTrue(exportImportReportEntries.isEmpty());

		_exportImportReportEntryLocalService.addEmptyExportImportReportEntry(
			RandomTestUtil.randomLong(), TestPropsValues.getCompanyId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomLong(),
			exportImportConfigurationId, RandomTestUtil.randomString());
		_exportImportReportEntryLocalService.addEmptyExportImportReportEntry(
			RandomTestUtil.randomLong(), TestPropsValues.getCompanyId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomLong(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomString());

		Company company = CompanyTestUtil.addCompany();

		_exportImportReportEntryLocalService.addEmptyExportImportReportEntry(
			RandomTestUtil.randomLong(), company.getCompanyId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomLong(),
			exportImportConfigurationId, RandomTestUtil.randomString());
		_exportImportReportEntryLocalService.addEmptyExportImportReportEntry(
			RandomTestUtil.randomLong(), company.getCompanyId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomLong(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomString());

		exportImportReportEntries =
			_exportImportReportEntryLocalService.getExportImportReportEntries(
				TestPropsValues.getCompanyId(), exportImportConfigurationId);

		Assert.assertEquals(
			exportImportReportEntries.toString(), 1,
			exportImportReportEntries.size());

		_companyLocalService.deleteCompany(company);
	}

	@Test
	public void testGetOrAddEmptyExportImportReportEntry() throws Exception {
		int count =
			_exportImportReportEntryLocalService.
				getExportImportReportEntriesCount();

		long groupId = RandomTestUtil.randomLong();
		long companyId = TestPropsValues.getCompanyId();
		String classExternalReferenceCode = RandomTestUtil.randomString();
		long classNameId = RandomTestUtil.randomLong();
		long exportImportConfigurationId = RandomTestUtil.randomLong();
		String modelNameLanguageKey = RandomTestUtil.randomString();

		ExportImportReportEntry exportImportReportEntry =
			_exportImportReportEntryLocalService.
				getOrAddEmptyExportImportReportEntry(
					groupId, companyId, classExternalReferenceCode, classNameId,
					exportImportConfigurationId, modelNameLanguageKey);

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
		Assert.assertEquals(
			modelNameLanguageKey,
			exportImportReportEntry.getModelNameLanguageKey());
		Assert.assertEquals(
			ExportImportReportEntryConstants.ORIGIN_STAGING,
			exportImportReportEntry.getOrigin());
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

		ExportImportReportEntry getExportImportReportEntry =
			_exportImportReportEntryLocalService.
				getOrAddEmptyExportImportReportEntry(
					groupId, companyId, classExternalReferenceCode, classNameId,
					exportImportConfigurationId, modelNameLanguageKey);

		Assert.assertEquals(
			exportImportReportEntry.getExportImportReportEntryId(),
			getExportImportReportEntry.getExportImportReportEntryId());

		Assert.assertEquals(
			count + 1,
			_exportImportReportEntryLocalService.
				getExportImportReportEntriesCount());
	}

	@Test
	public void testResolveEmptyExportImportReportEntries() throws Exception {
		String classExternalReferenceCode = RandomTestUtil.randomString();
		long classNameId = RandomTestUtil.randomLong();
		long companyId = TestPropsValues.getCompanyId();
		long exportImportConfigurationId1 = RandomTestUtil.randomLong();
		long exportImportConfigurationId2 = RandomTestUtil.randomLong();
		long exportImportConfigurationId3 = RandomTestUtil.randomLong();
		long groupId = TestPropsValues.getGroupId();

		ExportImportReportEntry exportImportReportEntry1 =
			_exportImportReportEntryLocalService.
				getOrAddEmptyExportImportReportEntry(
					groupId, companyId, classExternalReferenceCode, classNameId,
					exportImportConfigurationId1,
					RandomTestUtil.randomString());
		ExportImportReportEntry exportImportReportEntry2 =
			_exportImportReportEntryLocalService.
				getOrAddEmptyExportImportReportEntry(
					groupId, companyId, classExternalReferenceCode, classNameId,
					exportImportConfigurationId2,
					RandomTestUtil.randomString());
		ExportImportReportEntry exportImportReportEntry3 = _markAsResolved(
			_exportImportReportEntryLocalService.
				getOrAddEmptyExportImportReportEntry(
					groupId, companyId, classExternalReferenceCode, classNameId,
					exportImportConfigurationId3,
					RandomTestUtil.randomString()));

		ExportImportReportEntry randomExportImportReportEntry1 =
			_exportImportReportEntryLocalService.
				getOrAddEmptyExportImportReportEntry(
					groupId, companyId, RandomTestUtil.randomString(),
					classNameId, RandomTestUtil.randomLong(),
					RandomTestUtil.randomString());
		ExportImportReportEntry randomExportImportReportEntry2 =
			_exportImportReportEntryLocalService.
				getOrAddEmptyExportImportReportEntry(
					groupId, companyId, classExternalReferenceCode,
					RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
					RandomTestUtil.randomString());
		ExportImportReportEntry randomExportImportReportEntry3 =
			_markAsResolved(
				_exportImportReportEntryLocalService.
					getOrAddEmptyExportImportReportEntry(
						groupId, companyId, RandomTestUtil.randomString(),
						classNameId, RandomTestUtil.randomLong(),
						RandomTestUtil.randomString()));
		ExportImportReportEntry randomExportImportReportEntry4 =
			_markAsResolved(
				_exportImportReportEntryLocalService.
					getOrAddEmptyExportImportReportEntry(
						groupId, companyId, classExternalReferenceCode,
						RandomTestUtil.randomLong(),
						RandomTestUtil.randomLong(),
						RandomTestUtil.randomString()));

		Long originalExportImportConfigurationId =
			ExportImportThreadLocal.getExportImportConfigurationId();

		ExportImportThreadLocal.setExportImportConfigurationId(
			exportImportConfigurationId1);

		try {
			_exportImportReportEntryLocalService.
				resolveEmptyExportImportReportEntries(
					groupId, companyId, classExternalReferenceCode,
					classNameId);

			Assert.assertNull(
				_exportImportReportEntryLocalService.
					fetchExportImportReportEntry(
						exportImportReportEntry1.
							getExportImportReportEntryId()));

			_assertStatus(
				exportImportReportEntry2,
				ExportImportReportEntryConstants.STATUS_RESOLVED, true);
			_assertStatus(
				exportImportReportEntry3,
				ExportImportReportEntryConstants.STATUS_RESOLVED, false);
			_assertStatus(
				randomExportImportReportEntry1,
				ExportImportReportEntryConstants.STATUS_UNRESOLVED, false);
			_assertStatus(
				randomExportImportReportEntry2,
				ExportImportReportEntryConstants.STATUS_UNRESOLVED, false);
			_assertStatus(
				randomExportImportReportEntry3,
				ExportImportReportEntryConstants.STATUS_RESOLVED, false);
			_assertStatus(
				randomExportImportReportEntry4,
				ExportImportReportEntryConstants.STATUS_RESOLVED, false);
		}
		finally {
			ExportImportThreadLocal.setExportImportConfigurationId(
				originalExportImportConfigurationId);
		}
	}

	private void _assertStatus(
			ExportImportReportEntry exportImportReportEntry, int status,
			boolean updated)
		throws Exception {

		ExportImportReportEntry actualExportImportReportEntry =
			_exportImportReportEntryLocalService.getExportImportReportEntry(
				exportImportReportEntry.getExportImportReportEntryId());

		long mvccVersion = exportImportReportEntry.getMvccVersion();

		Assert.assertEquals(
			updated ? mvccVersion + 1 : mvccVersion,
			actualExportImportReportEntry.getMvccVersion());

		Assert.assertEquals(status, actualExportImportReportEntry.getStatus());
	}

	private ExportImportReportEntry _markAsResolved(
		ExportImportReportEntry exportImportReportEntry) {

		exportImportReportEntry.setStatus(
			ExportImportReportEntryConstants.STATUS_RESOLVED);

		return _exportImportReportEntryLocalService.
			updateExportImportReportEntry(exportImportReportEntry);
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private ExportImportReportEntryLocalService
		_exportImportReportEntryLocalService;

}