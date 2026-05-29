/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.background.task.BackgroundTaskExecutorNames;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.report.constants.ExportImportReportEntryConstants;
import com.liferay.exportimport.report.model.ExportImportReportEntry;
import com.liferay.exportimport.report.service.ExportImportReportEntryLocalService;
import com.liferay.exportimport.rest.client.dto.v1_0.ReportEntry;
import com.liferay.exportimport.rest.client.dto.v1_0.Type;
import com.liferay.exportimport.rest.client.pagination.Page;
import com.liferay.exportimport.rest.client.pagination.Pagination;
import com.liferay.exportimport.rest.client.resource.v1_0.ReportEntryResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.test.rule.Inject;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Petteri Karttunen
 */
@RunWith(Arquillian.class)
public class ReportEntryResourceTest extends BaseReportEntryResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					testGroup.getCreatorUserId(), RandomTestUtil.randomString(),
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					Collections.emptyMap());

		_backgroundTask = _backgroundTaskLocalService.addBackgroundTask(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			RandomTestUtil.randomString(),
			BackgroundTaskExecutorNames.LAYOUT_IMPORT_BACKGROUND_TASK_EXECUTOR,
			HashMapBuilder.<String, Serializable>put(
				"exportImportConfigurationId",
				_exportImportConfiguration.getExportImportConfigurationId()
			).build(),
			null);
	}

	@Override
	@Test
	public void testGetImportProcessReportEntriesPage() throws Exception {
		super.testGetImportProcessReportEntriesPage();

		_testGetImportProcessReportEntriesPageWithEmptyExportImportReportEntry();
		_testGetImportProcessReportEntriesPageWithFilter();
		_testGetImportProcessReportEntriesPageWithLocalizedSearchTerm();
		_testGetImportProcessReportEntriesPageWithSort();
	}

	@Override
	protected ReportEntry testGetImportProcessReportEntriesPage_addReportEntry(
			Long importProcessId, ReportEntry reportEntry)
		throws Exception {

		return _addReportEntry(reportEntry);
	}

	@Override
	protected Long testGetImportProcessReportEntriesPage_getImportProcessId()
		throws Exception {

		return _backgroundTask.getBackgroundTaskId();
	}

	@Override
	protected ReportEntry testGetReportEntry_addReportEntry() throws Exception {
		return _addReportEntry(randomReportEntry());
	}

	private ReportEntry _addReportEntry(ReportEntry reportEntry)
		throws Exception {

		ExportImportReportEntry exportImportReportEntry;

		Type type = reportEntry.getType();

		if ((type != null) &&
			(type.getCode() == ExportImportReportEntryConstants.TYPE_EMPTY)) {

			exportImportReportEntry =
				_exportImportReportEntryLocalService.
					addEmptyExportImportReportEntry(
						testGroup.getGroupId(), testCompany.getCompanyId(),
						reportEntry.getClassExternalReferenceCode(),
						reportEntry.getClassNameId(),
						_exportImportConfiguration.
							getExportImportConfigurationId(),
						reportEntry.getModelName());
		}
		else {
			exportImportReportEntry =
				_exportImportReportEntryLocalService.
					addErrorExportImportReportEntry(
						testGroup.getGroupId(), testCompany.getCompanyId(),
						reportEntry.getClassExternalReferenceCode(),
						reportEntry.getClassNameId(), reportEntry.getClassPK(),
						_exportImportConfiguration.
							getExportImportConfigurationId(),
						reportEntry.getErrorMessage(),
						reportEntry.getErrorStacktrace(),
						reportEntry.getModelName());
		}

		_exportImportReportEntries.add(exportImportReportEntry);

		return new ReportEntry() {
			{
				setClassExternalReferenceCode(
					exportImportReportEntry.getClassExternalReferenceCode());
				setClassNameId(exportImportReportEntry.getClassNameId());
				setClassPK(exportImportReportEntry.getClassPK());
				setConfigurationId(
					_exportImportConfiguration.
						getExportImportConfigurationId());
				setDateCreated(exportImportReportEntry.getCreateDate());
				setDateModified(exportImportReportEntry.getModifiedDate());
				setErrorMessage(exportImportReportEntry.getErrorMessage());
				setErrorStacktrace(
					exportImportReportEntry.getErrorStacktrace());
				setId(exportImportReportEntry.getExportImportReportEntryId());
				setModelName(exportImportReportEntry.getModelNameLanguageKey());
			}
		};
	}

	private void _assertReportEntries(
			String filterString, String sortString,
			Long... expectedReportEntryIds)
		throws Exception {

		List<ReportEntry> items =
			(List<ReportEntry>)
				reportEntryResource.getImportProcessReportEntriesPage(
					_backgroundTask.getBackgroundTaskId(), null, filterString,
					Pagination.of(1, 10), sortString
				).getItems();

		Assert.assertEquals(
			items.toString(), expectedReportEntryIds.length, items.size());

		for (int i = 0; i < expectedReportEntryIds.length; i++) {
			Assert.assertEquals(
				expectedReportEntryIds[i],
				items.get(
					i
				).getId());
		}
	}

	private ReportEntry _randomReportEntry(int typeCode) throws Exception {
		ReportEntry reportEntry = randomReportEntry();

		reportEntry.setType(
			() -> {
				Type type = new Type();

				type.setCode(typeCode);

				return type;
			});

		return reportEntry;
	}

	private void _testGetImportProcessReportEntriesPageWithEmptyExportImportReportEntry()
		throws Exception {

		Page<ReportEntry> page =
			reportEntryResource.getImportProcessReportEntriesPage(
				testGetImportProcessReportEntriesPage_getImportProcessId(),
				null, null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		_addReportEntry(
			_randomReportEntry(ExportImportReportEntryConstants.TYPE_EMPTY));

		page = reportEntryResource.getImportProcessReportEntriesPage(
			testGetImportProcessReportEntriesPage_getImportProcessId(), null,
			null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 1, page.getTotalCount());
	}

	private void _testGetImportProcessReportEntriesPageWithFilter()
		throws Exception {

		ReportEntry reportEntry1 = _addReportEntry(
			_randomReportEntry(ExportImportReportEntryConstants.TYPE_ERROR));

		_assertReportEntries(
			"contains(classExternalReferenceCode, '" +
				reportEntry1.getClassExternalReferenceCode() + "')",
			null, reportEntry1.getId());

		_assertReportEntries(
			"contains(classExternalReferenceCode, '" +
				RandomTestUtil.randomString() + "')",
			null);

		_assertReportEntries(
			"startswith(modelName, '" + reportEntry1.getModelName() + "')",
			null, reportEntry1.getId());
		_assertReportEntries(
			"startswith(modelName, '" + RandomTestUtil.randomString() + "')",
			null);

		ReportEntry reportEntry2 = _addReportEntry(
			_randomReportEntry(ExportImportReportEntryConstants.TYPE_EMPTY));

		_assertReportEntries(
			StringBundler.concat(
				"id eq ", reportEntry2.getId(), " and type/code eq ",
				ExportImportReportEntryConstants.TYPE_EMPTY),
			null, reportEntry2.getId());
		_assertReportEntries(
			StringBundler.concat(
				"id eq ", reportEntry2.getId(), " and type/code eq ",
				ExportImportReportEntryConstants.TYPE_ERROR),
			null);

		_assertReportEntries(
			StringBundler.concat(
				"id eq ", reportEntry2.getId(), " and type/label eq '",
				LanguageUtil.get(
					LocaleUtil.getDefault(),
					ExportImportReportEntryConstants.getTypeLabel(
						ExportImportReportEntryConstants.TYPE_EMPTY)),
				"'"),
			null, reportEntry2.getId());
		_assertReportEntries(
			StringBundler.concat(
				"id eq ", reportEntry2.getId(), " and type/label eq '",
				LanguageUtil.get(
					LocaleUtil.getDefault(),
					ExportImportReportEntryConstants.getTypeLabel(
						ExportImportReportEntryConstants.TYPE_ERROR)),
				"'"),
			null);
	}

	private void _testGetImportProcessReportEntriesPageWithLocalizedSearchTerm()
		throws Exception {

		User user = UserTestUtil.getAdminUser(testCompany.getCompanyId());

		ReportEntryResource reportEntryResource = ReportEntryResource.builder(
		).authentication(
			user.getEmailAddress(), PropsValues.DEFAULT_ADMIN_PASSWORD
		).endpoint(
			testCompany.getVirtualHostname(),
			PortalUtil.getPortalServerPort(false), "http"
		).locale(
			LocaleUtil.SPAIN
		).build();

		ReportEntry reportEntry = randomReportEntry();

		_exportImportReportEntries.add(
			_exportImportReportEntryLocalService.
				addErrorExportImportReportEntry(
					testGroup.getGroupId(), testCompany.getCompanyId(),
					reportEntry.getClassExternalReferenceCode(),
					reportEntry.getClassNameId(), reportEntry.getClassPK(),
					_exportImportConfiguration.getExportImportConfigurationId(),
					reportEntry.getErrorMessage(),
					reportEntry.getErrorStacktrace(), "example-text"));

		Page<ReportEntry> page =
			reportEntryResource.getImportProcessReportEntriesPage(
				testGetImportProcessReportEntriesPage_getImportProcessId(),
				"Texto de ejemplo", null, Pagination.of(1, 10), null);

		Assert.assertEquals(1, page.getTotalCount());

		assertContains(reportEntry, (List<ReportEntry>)page.getItems());
	}

	private void _testGetImportProcessReportEntriesPageWithSort()
		throws Exception {

		ReportEntry reportEntry1 = _randomReportEntry(
			ExportImportReportEntryConstants.TYPE_EMPTY);

		reportEntry1.setClassExternalReferenceCode(
			"a" + RandomTestUtil.randomString());
		reportEntry1.setModelName("z" + RandomTestUtil.randomString());

		reportEntry1 = _addReportEntry(reportEntry1);

		ReportEntry reportEntry2 = _randomReportEntry(
			ExportImportReportEntryConstants.TYPE_ERROR);

		reportEntry2.setClassExternalReferenceCode(
			"z" + RandomTestUtil.randomString());
		reportEntry2.setModelName("a" + RandomTestUtil.randomString());

		reportEntry2 = _addReportEntry(reportEntry2);

		String filterString = StringBundler.concat(
			"id eq ", reportEntry1.getId(), " or id eq ", reportEntry2.getId());

		_assertReportEntries(
			filterString, "classExternalReferenceCode:asc",
			reportEntry1.getId(), reportEntry2.getId());
		_assertReportEntries(
			filterString, "classExternalReferenceCode:desc",
			reportEntry2.getId(), reportEntry1.getId());
		_assertReportEntries(
			filterString, "modelName:asc", reportEntry2.getId(),
			reportEntry1.getId());
		_assertReportEntries(
			filterString, "modelName:desc", reportEntry1.getId(),
			reportEntry2.getId());
		_assertReportEntries(
			filterString, "type/code:asc", reportEntry2.getId(),
			reportEntry1.getId());
		_assertReportEntries(
			filterString, "type/code:desc", reportEntry1.getId(),
			reportEntry2.getId());
		_assertReportEntries(
			filterString, "type/label:asc", reportEntry1.getId(),
			reportEntry2.getId());
		_assertReportEntries(
			filterString, "type/label:desc", reportEntry2.getId(),
			reportEntry1.getId());
	}

	@DeleteAfterTestRun
	private BackgroundTask _backgroundTask;

	@Inject
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@DeleteAfterTestRun
	private ExportImportConfiguration _exportImportConfiguration;

	@Inject
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@DeleteAfterTestRun
	private final List<ExportImportReportEntry> _exportImportReportEntries =
		new ArrayList<>();

	@Inject
	private ExportImportReportEntryLocalService
		_exportImportReportEntryLocalService;

}