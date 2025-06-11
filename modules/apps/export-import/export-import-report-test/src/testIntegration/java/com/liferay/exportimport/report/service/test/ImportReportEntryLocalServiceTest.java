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
import com.liferay.portal.kernel.util.Validator;
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
	public void testAddErrorImportReportEntry() throws Exception {
		int count = _importReportEntryPersistence.countAll();

		long classNameId = RandomTestUtil.randomLong();
		long classNamePK = RandomTestUtil.randomLong();
		long entityClassNameId = RandomTestUtil.randomLong();
		String entityExternalReferenceCode = RandomTestUtil.randomString();
		String error = RandomTestUtil.randomString();
		String errorStacktrace = RandomTestUtil.randomString();
		long groupId = RandomTestUtil.randomLong();

		ImportReportEntry importReportEntry =
			_importReportEntryLocalService.addErrorImportReportEntry(
				TestPropsValues.getCompanyId(), groupId, classNameId,
				classNamePK, entityClassNameId, entityExternalReferenceCode,
				error, errorStacktrace);

		Assert.assertEquals(classNameId, importReportEntry.getClassNameId());
		Assert.assertEquals(classNamePK, importReportEntry.getClassPK());
		Assert.assertEquals(
			entityClassNameId, importReportEntry.getEntityClassNameId());
		Assert.assertEquals(
			entityExternalReferenceCode,
			importReportEntry.getEntityExternalReferenceCode());
		Assert.assertEquals(error, importReportEntry.getError());
		Assert.assertEquals(
			errorStacktrace, importReportEntry.getErrorStacktrace());
		Assert.assertEquals(groupId, importReportEntry.getGroupId());
		Assert.assertEquals(
			ImportReportEntryConstants.TYPE_ERROR, importReportEntry.getType());

		Assert.assertEquals(
			count + 1, _importReportEntryPersistence.countAll());
	}

	@Test
	public void testAddIncompleteImportReportEntry() throws Exception {
		int count = _importReportEntryPersistence.countAll();

		long classNameId = RandomTestUtil.randomLong();
		long classNamePK = RandomTestUtil.randomLong();
		long entityClassNameId = RandomTestUtil.randomLong();
		String entityExternalReferenceCode = RandomTestUtil.randomString();
		long groupId = RandomTestUtil.randomLong();

		ImportReportEntry importReportEntry =
			_importReportEntryLocalService.addIncompleteImportReportEntry(
				TestPropsValues.getCompanyId(), groupId, classNameId,
				classNamePK, entityClassNameId, entityExternalReferenceCode);

		Assert.assertEquals(classNameId, importReportEntry.getClassNameId());
		Assert.assertEquals(classNamePK, importReportEntry.getClassPK());
		Assert.assertEquals(
			entityClassNameId, importReportEntry.getEntityClassNameId());
		Assert.assertEquals(
			entityExternalReferenceCode,
			importReportEntry.getEntityExternalReferenceCode());
		Assert.assertTrue(Validator.isNull(importReportEntry.getError()));
		Assert.assertTrue(
			Validator.isNull(importReportEntry.getErrorStacktrace()));
		Assert.assertEquals(groupId, importReportEntry.getGroupId());
		Assert.assertEquals(
			ImportReportEntryConstants.TYPE_INCOMPLETE,
			importReportEntry.getType());

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

		_importReportEntryLocalService.addErrorImportReportEntry(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			classNameId, classNamePK, RandomTestUtil.randomLong(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
		_importReportEntryLocalService.addErrorImportReportEntry(
			companyId, RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			classNamePK, RandomTestUtil.randomLong(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
		_importReportEntryLocalService.addErrorImportReportEntry(
			companyId, RandomTestUtil.randomLong(), classNameId,
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
		_importReportEntryLocalService.addErrorImportReportEntry(
			companyId, RandomTestUtil.randomLong(), classNameId, classNamePK,
			RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

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