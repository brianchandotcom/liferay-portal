/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.internal.resource.v1_0;

import com.liferay.exportimport.rest.dto.v1_0.PreviewSite;
import com.liferay.exportimport.site.LARSite;
import com.liferay.exportimport.site.LARSiteReader;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Petteri Karttunen
 */
public class ImportPreviewResourceImplTest {

	@ClassRule
	@Rule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_importPreviewResourceImpl = new ImportPreviewResourceImpl();

		_fileEntry = Mockito.mock(FileEntry.class);

		_larSiteReader = Mockito.mock(LARSiteReader.class);

		ReflectionTestUtil.setFieldValue(
			_importPreviewResourceImpl, "_larSiteReader", _larSiteReader);

		Company company = Mockito.mock(Company.class);

		Mockito.when(
			company.getCompanyId()
		).thenReturn(
			_COMPANY_ID
		);

		_importPreviewResourceImpl.setContextCompany(company);
	}

	@Test
	public void testGetPreviewSitesWhenSiteExportImportIsDisabled()
		throws Exception {

		Mockito.when(
			_larSiteReader.getLARSites(_fileEntry)
		).thenReturn(
			ListUtil.fromArray(Mockito.mock(LARSite.class))
		);

		Assert.assertEquals(0, _getPreviewSites(false).length);

		Mockito.verify(
			_larSiteReader, Mockito.never()
		).getLARSites(
			_fileEntry
		);
	}

	@Test
	public void testGetPreviewSitesWhenSiteExportImportIsEnabled()
		throws Exception {

		Mockito.when(
			_larSiteReader.getLARSites(_fileEntry)
		).thenReturn(
			ListUtil.fromArray(Mockito.mock(LARSite.class))
		);

		Assert.assertEquals(1, _getPreviewSites(true).length);

		Mockito.verify(
			_larSiteReader
		).getLARSites(
			_fileEntry
		);
	}

	private PreviewSite[] _getPreviewSites(boolean siteExportImportEnabled)
		throws Exception {

		try (MockedStatic<FeatureFlagManagerUtil> mockedStatic =
				Mockito.mockStatic(FeatureFlagManagerUtil.class)) {

			mockedStatic.when(
				() -> FeatureFlagManagerUtil.isEnabled(
					Mockito.anyLong(), Mockito.eq("LPD-85946"))
			).thenReturn(
				siteExportImportEnabled
			);

			return ReflectionTestUtil.invoke(
				_importPreviewResourceImpl, "_getPreviewSites",
				new Class<?>[] {FileEntry.class}, _fileEntry);
		}
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private FileEntry _fileEntry;
	private ImportPreviewResourceImpl _importPreviewResourceImpl;
	private LARSiteReader _larSiteReader;

}