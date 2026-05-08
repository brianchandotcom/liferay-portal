/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.internal.missing.reference;

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.report.missing.reference.MissingReferenceManager;
import com.liferay.exportimport.report.service.ExportImportReportEntryLocalService;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Petteri Karttunen
 */
public class MissingReferenceManagerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_missingReferenceManager, "_classNameLocalService",
			_classNameLocalService);
		ReflectionTestUtil.setFieldValue(
			_missingReferenceManager, "_exportImportConfigurationLocalService",
			_exportImportConfigurationLocalService);
		ReflectionTestUtil.setFieldValue(
			_missingReferenceManager, "_exportImportReportEntryLocalService",
			_exportImportReportEntryLocalService);
	}

	@After
	public void tearDown() {
		Mockito.verifyNoMoreInteractions(
			_classNameLocalService, _exportImportConfiguration,
			_exportImportConfigurationLocalService,
			_exportImportReportEntryLocalService);
	}

	@Test
	public void testReportMissingReferenceOutsideImportProcess() {
		try (MockedStatic<ExportImportThreadLocal>
				exportImportThreadLocalMockedStatic = Mockito.mockStatic(
					ExportImportThreadLocal.class)) {

			exportImportThreadLocalMockedStatic.when(
				ExportImportThreadLocal::isImportInProcess
			).thenReturn(
				false
			);

			_missingReferenceManager.reportMissingReference(
				_CLASS_NAME, RandomTestUtil.randomString(),
				RandomTestUtil.randomLong());

			Mockito.verify(
				_classNameLocalService, Mockito.never()
			).fetchClassName(
				Mockito.anyString()
			);

			Mockito.verify(
				_exportImportConfigurationLocalService, Mockito.never()
			).fetchExportImportConfiguration(
				Mockito.anyLong()
			);

			Mockito.verify(
				_exportImportReportEntryLocalService, Mockito.never()
			).getOrAddMissingReferenceExportImportReportEntry(
				Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString()
			);
		}
	}

	@Test
	public void testReportMissingReferenceWithMissingConfiguration() {
		try (MockedStatic<ExportImportThreadLocal>
				exportImportThreadLocalMockedStatic = Mockito.mockStatic(
					ExportImportThreadLocal.class)) {

			exportImportThreadLocalMockedStatic.when(
				ExportImportThreadLocal::isImportInProcess
			).thenReturn(
				true
			);

			long exportImportConfigurationId = RandomTestUtil.randomLong();

			exportImportThreadLocalMockedStatic.when(
				ExportImportThreadLocal::getExportImportConfigurationId
			).thenReturn(
				exportImportConfigurationId
			);

			Mockito.when(
				_exportImportConfigurationLocalService.
					fetchExportImportConfiguration(exportImportConfigurationId)
			).thenReturn(
				null
			);

			_missingReferenceManager.reportMissingReference(
				_CLASS_NAME, RandomTestUtil.randomString(),
				RandomTestUtil.randomLong());

			Mockito.verify(
				_classNameLocalService, Mockito.never()
			).fetchClassName(
				Mockito.anyString()
			);

			Mockito.verify(
				_exportImportConfigurationLocalService
			).fetchExportImportConfiguration(
				exportImportConfigurationId
			);

			Mockito.verify(
				_exportImportReportEntryLocalService, Mockito.never()
			).getOrAddMissingReferenceExportImportReportEntry(
				Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString()
			);
		}
	}

	@Test
	public void testReportMissingReferenceWithNullExternalReferenceCode() {
		try (MockedStatic<ExportImportThreadLocal>
				exportImportThreadLocalMockedStatic = Mockito.mockStatic(
					ExportImportThreadLocal.class)) {

			ClassName className = Mockito.mock(ClassName.class);

			long classNameId = RandomTestUtil.randomLong();

			Mockito.when(
				className.getClassNameId()
			).thenReturn(
				classNameId
			);

			Mockito.when(
				_classNameLocalService.fetchClassName(_CLASS_NAME)
			).thenReturn(
				className
			);

			long companyId = RandomTestUtil.randomLong();

			Mockito.when(
				_exportImportConfiguration.getCompanyId()
			).thenReturn(
				companyId
			);

			long exportImportConfigurationId = RandomTestUtil.randomLong();

			Mockito.when(
				_exportImportConfigurationLocalService.
					fetchExportImportConfiguration(exportImportConfigurationId)
			).thenReturn(
				_exportImportConfiguration
			);

			exportImportThreadLocalMockedStatic.when(
				ExportImportThreadLocal::getExportImportConfigurationId
			).thenReturn(
				exportImportConfigurationId
			);

			exportImportThreadLocalMockedStatic.when(
				ExportImportThreadLocal::isImportInProcess
			).thenReturn(
				true
			);

			long groupId = RandomTestUtil.randomLong();

			_missingReferenceManager.reportMissingReference(
				_CLASS_NAME, null, groupId);

			Mockito.verify(
				_classNameLocalService
			).fetchClassName(
				_CLASS_NAME
			);

			Mockito.verify(
				_exportImportConfiguration
			).getCompanyId();

			Mockito.verify(
				_exportImportConfigurationLocalService
			).fetchExportImportConfiguration(
				exportImportConfigurationId
			);

			Mockito.verify(
				_exportImportReportEntryLocalService
			).getOrAddMissingReferenceExportImportReportEntry(
				groupId, companyId, null, classNameId,
				exportImportConfigurationId, _CLASS_NAME
			);
		}
	}

	@Test
	public void testReportMissingReferenceWithRegisteredClassName() {
		try (MockedStatic<ExportImportThreadLocal>
				exportImportThreadLocalMockedStatic = Mockito.mockStatic(
					ExportImportThreadLocal.class)) {

			ClassName className = Mockito.mock(ClassName.class);

			long classNameId = RandomTestUtil.randomLong();

			Mockito.when(
				className.getClassNameId()
			).thenReturn(
				classNameId
			);

			Mockito.when(
				_classNameLocalService.fetchClassName(_CLASS_NAME)
			).thenReturn(
				className
			);

			long companyId = RandomTestUtil.randomLong();

			Mockito.when(
				_exportImportConfiguration.getCompanyId()
			).thenReturn(
				companyId
			);

			long exportImportConfigurationId = RandomTestUtil.randomLong();

			Mockito.when(
				_exportImportConfigurationLocalService.
					fetchExportImportConfiguration(exportImportConfigurationId)
			).thenReturn(
				_exportImportConfiguration
			);

			exportImportThreadLocalMockedStatic.when(
				ExportImportThreadLocal::getExportImportConfigurationId
			).thenReturn(
				exportImportConfigurationId
			);

			exportImportThreadLocalMockedStatic.when(
				ExportImportThreadLocal::isImportInProcess
			).thenReturn(
				true
			);

			String externalReferenceCode = RandomTestUtil.randomString();
			long groupId = RandomTestUtil.randomLong();

			_missingReferenceManager.reportMissingReference(
				_CLASS_NAME, externalReferenceCode, groupId);

			Mockito.verify(
				_classNameLocalService
			).fetchClassName(
				_CLASS_NAME
			);

			Mockito.verify(
				_exportImportConfiguration
			).getCompanyId();

			Mockito.verify(
				_exportImportConfigurationLocalService
			).fetchExportImportConfiguration(
				exportImportConfigurationId
			);

			Mockito.verify(
				_exportImportReportEntryLocalService
			).getOrAddMissingReferenceExportImportReportEntry(
				groupId, companyId, externalReferenceCode, classNameId,
				exportImportConfigurationId, _CLASS_NAME
			);
		}
	}

	@Test
	public void testReportMissingReferenceWithUnregisteredClassName() {
		try (MockedStatic<ExportImportThreadLocal>
				exportImportThreadLocalMockedStatic = Mockito.mockStatic(
					ExportImportThreadLocal.class)) {

			Mockito.when(
				_classNameLocalService.fetchClassName(_CLASS_NAME)
			).thenReturn(
				null
			);

			long companyId = RandomTestUtil.randomLong();

			Mockito.when(
				_exportImportConfiguration.getCompanyId()
			).thenReturn(
				companyId
			);

			long exportImportConfigurationId = RandomTestUtil.randomLong();

			Mockito.when(
				_exportImportConfigurationLocalService.
					fetchExportImportConfiguration(exportImportConfigurationId)
			).thenReturn(
				_exportImportConfiguration
			);

			exportImportThreadLocalMockedStatic.when(
				ExportImportThreadLocal::getExportImportConfigurationId
			).thenReturn(
				exportImportConfigurationId
			);

			exportImportThreadLocalMockedStatic.when(
				ExportImportThreadLocal::isImportInProcess
			).thenReturn(
				true
			);

			String externalReferenceCode = RandomTestUtil.randomString();
			long groupId = RandomTestUtil.randomLong();

			_missingReferenceManager.reportMissingReference(
				_CLASS_NAME, externalReferenceCode, groupId);

			Mockito.verify(
				_classNameLocalService
			).fetchClassName(
				_CLASS_NAME
			);

			Mockito.verify(
				_classNameLocalService, Mockito.never()
			).addClassName(
				Mockito.anyString()
			);

			Mockito.verify(
				_exportImportConfiguration
			).getCompanyId();

			Mockito.verify(
				_exportImportConfigurationLocalService
			).fetchExportImportConfiguration(
				exportImportConfigurationId
			);

			Mockito.verify(
				_exportImportReportEntryLocalService
			).getOrAddMissingReferenceExportImportReportEntry(
				groupId, companyId, externalReferenceCode, 0,
				exportImportConfigurationId, _CLASS_NAME
			);
		}
	}

	private static final String _CLASS_NAME =
		MissingReferenceManagerImplTest.class.getName();

	private static final MissingReferenceManager _missingReferenceManager =
		new MissingReferenceManagerImpl();

	private final ClassNameLocalService _classNameLocalService = Mockito.mock(
		ClassNameLocalService.class);
	private final ExportImportConfiguration _exportImportConfiguration =
		Mockito.mock(ExportImportConfiguration.class);
	private final ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService = Mockito.mock(
			ExportImportConfigurationLocalService.class);
	private final ExportImportReportEntryLocalService
		_exportImportReportEntryLocalService = Mockito.mock(
			ExportImportReportEntryLocalService.class);

}