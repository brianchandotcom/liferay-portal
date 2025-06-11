/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.report.constants.ImportReportEntryConstants;
import com.liferay.exportimport.report.model.ImportReportEntry;
import com.liferay.exportimport.report.service.ImportReportEntryLocalService;
import com.liferay.exportimport.report.service.persistence.ImportReportEntryPersistence;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

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
public class ImportReportEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.exportimport.report.service"));

	@Test
	public void testAddImportReportEntry() throws Exception {
		int count = _importReportEntryPersistence.countAll();

		long classNameId = RandomTestUtil.randomLong();
		long classNamePK = RandomTestUtil.randomLong();
		long entityClassNameId = RandomTestUtil.randomLong();
		String entityExternalReferenceCode = RandomTestUtil.randomString();
		String error = RandomTestUtil.randomString();
		int type = RandomTestUtil.randomInt(
			ImportReportEntryConstants.TYPE_ERROR,
			ImportReportEntryConstants.TYPE_INCOMPLETE);

		ImportReportEntry importReportEntry =
			_importReportEntryLocalService.addImportReportEntry(
				TestPropsValues.getCompanyId(), classNameId, classNamePK,
				entityClassNameId, entityExternalReferenceCode, error, type);

		Assert.assertEquals(classNameId, importReportEntry.getClassNameId());
		Assert.assertEquals(classNamePK, importReportEntry.getClassPK());
		Assert.assertEquals(
			entityClassNameId, importReportEntry.getEntityClassNameId());
		Assert.assertEquals(
			entityExternalReferenceCode,
			importReportEntry.getEntityExternalReferenceCode());
		Assert.assertEquals(error, importReportEntry.getError());
		Assert.assertEquals(type, importReportEntry.getType());

		Assert.assertEquals(
			count + 1, _importReportEntryPersistence.countAll());
	}

	@Test
	public void testGetImportReportEntries() throws Exception {
		long classNameId = RandomTestUtil.randomLong();
		long classNamePK = RandomTestUtil.randomLong();
		long companyId = RandomTestUtil.randomLong();

		List<ImportReportEntry> importReportEntries =
			_importReportEntryLocalService.getImportReportEntries(
				companyId, classNameId, classNamePK);

		Assert.assertTrue(importReportEntries.isEmpty());

		_importReportEntryLocalService.addImportReportEntry(
			RandomTestUtil.randomLong(), classNameId, classNamePK,
			RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			ImportReportEntryConstants.TYPE_ERROR);
		_importReportEntryLocalService.addImportReportEntry(
			companyId, RandomTestUtil.randomLong(), classNamePK,
			RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			ImportReportEntryConstants.TYPE_ERROR);
		_importReportEntryLocalService.addImportReportEntry(
			companyId, classNameId, RandomTestUtil.randomLong(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			ImportReportEntryConstants.TYPE_ERROR);
		_importReportEntryLocalService.addImportReportEntry(
			companyId, classNameId, classNamePK, RandomTestUtil.randomLong(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ImportReportEntryConstants.TYPE_ERROR);

		importReportEntries =
			_importReportEntryLocalService.getImportReportEntries(
				companyId, classNameId, classNamePK);

		Assert.assertTrue(importReportEntries.size() == 1);
	}

	@Inject
	private ImportReportEntryLocalService _importReportEntryLocalService;

	@Inject
	private ImportReportEntryPersistence _importReportEntryPersistence;

}