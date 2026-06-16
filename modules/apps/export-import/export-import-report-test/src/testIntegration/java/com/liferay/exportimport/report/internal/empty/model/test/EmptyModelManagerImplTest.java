/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.internal.empty.model.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.empty.model.EmptyModelManagerUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.report.constants.ExportImportReportEntryConstants;
import com.liferay.exportimport.report.model.ExportImportReportEntry;
import com.liferay.exportimport.report.service.ExportImportReportEntryLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Petteri Karttunen
 */
@RunWith(Arquillian.class)
public class EmptyModelManagerImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testReportMissingReferenceDuringImport() throws Throwable {
		long exportImportConfigurationId = RandomTestUtil.randomLong();

		ExportImportThreadLocal.setExportImportConfigurationId(
			exportImportConfigurationId);

		ExportImportThreadLocal.setPortletImportInProcess(true);

		String externalReferenceCode = RandomTestUtil.randomString();

		// Reporting a missing reference must join the surrounding import
		// transaction. When the report entry was written in a separate
		// REQUIRES_NEW transaction it deadlocked against the lock the import
		// transaction already held on the report entry table under HSQLDB's
		// table level locking, hanging the import.

		try {
			TransactionInvokerUtil.invoke(
				TransactionConfig.Factory.create(
					Propagation.REQUIRED, new Class<?>[] {Exception.class}),
				() -> {
					EmptyModelManagerUtil.reportMissingReference(
						Group.class.getName(), externalReferenceCode,
						_group.getGroupId());

					return null;
				});

			List<ExportImportReportEntry> exportImportReportEntries =
				_exportImportReportEntryLocalService.
					getExportImportReportEntries(
						TestPropsValues.getCompanyId(),
						exportImportConfigurationId);

			Assert.assertEquals(
				exportImportReportEntries.toString(), 1,
				exportImportReportEntries.size());

			ExportImportReportEntry exportImportReportEntry =
				exportImportReportEntries.get(0);

			Assert.assertEquals(
				externalReferenceCode,
				exportImportReportEntry.getClassExternalReferenceCode());
			Assert.assertEquals(
				_classNameLocalService.getClassNameId(Group.class),
				exportImportReportEntry.getClassNameId());
			Assert.assertEquals(
				_group.getGroupId(), exportImportReportEntry.getGroupId());
			Assert.assertEquals(
				ExportImportReportEntryConstants.TYPE_MISSING_REFERENCE,
				exportImportReportEntry.getType());
		}
		finally {
			ExportImportThreadLocal.setExportImportConfigurationId(0);
			ExportImportThreadLocal.setPortletImportInProcess(false);
		}
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private ExportImportReportEntryLocalService
		_exportImportReportEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

}