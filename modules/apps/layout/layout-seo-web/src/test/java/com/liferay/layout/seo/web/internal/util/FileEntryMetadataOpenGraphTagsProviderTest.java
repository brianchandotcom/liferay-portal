/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.web.internal.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalService;
import com.liferay.dynamic.data.mapping.model.DDMField;
import com.liferay.dynamic.data.mapping.model.DDMFieldAttribute;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFieldLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class FileEntryMetadataOpenGraphTagsProviderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_fileEntryMetadataOpenGraphTagsProvider =
			new FileEntryMetadataOpenGraphTagsProvider(
				_ddmFieldLocalService, _ddmStructureLocalService,
				_dlFileEntryMetadataLocalService, _portal);
	}

	@Test
	public void testGetKeyValuePairsFileEntryWithEmptyTiffMetadata()
		throws Exception {

		Mockito.when(
			_fileEntry.getModel()
		).thenReturn(
			Mockito.mock(DLFileEntry.class)
		);

		Mockito.when(
			_fileEntry.getFileVersion()
		).thenReturn(
			Mockito.mock(FileVersion.class)
		);

		Mockito.when(
			_ddmStructureLocalService.getClassStructures(
				Mockito.anyLong(), Mockito.anyLong(),
				Mockito.any(OrderByComparator.class))
		).thenReturn(
			Collections.singletonList(Mockito.mock(DDMStructure.class))
		);

		Mockito.when(
			_dlFileEntryMetadataLocalService.fetchFileEntryMetadata(
				Mockito.anyLong(), Mockito.anyLong())
		).thenReturn(
			_dlFileEntryMetadata
		);

		Assert.assertEquals(
			Collections.emptyList(),
			_fileEntryMetadataOpenGraphTagsProvider.
				getFileEntryMetadataOpenGraphTagKeyValuePairs(_fileEntry));
	}

	@Test
	public void testGetKeyValuePairsFileEntryWithNoDDMFormValues()
		throws Exception {

		Mockito.when(
			_fileEntry.getModel()
		).thenReturn(
			Mockito.mock(DLFileEntry.class)
		);

		Mockito.when(
			_fileEntry.getFileVersion()
		).thenReturn(
			Mockito.mock(FileVersion.class)
		);

		Mockito.when(
			_ddmStructureLocalService.getClassStructures(
				Mockito.anyLong(), Mockito.anyLong(),
				Mockito.any(OrderByComparator.class))
		).thenReturn(
			Collections.singletonList(Mockito.mock(DDMStructure.class))
		);

		Mockito.when(
			_dlFileEntryMetadataLocalService.fetchFileEntryMetadata(
				Mockito.anyLong(), Mockito.anyLong())
		).thenReturn(
			_dlFileEntryMetadata
		);

		Mockito.when(
			_ddmFieldLocalService.getDDMFields(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			Collections.emptyList()
		);

		Assert.assertEquals(
			Collections.emptyList(),
			_fileEntryMetadataOpenGraphTagsProvider.
				getFileEntryMetadataOpenGraphTagKeyValuePairs(_fileEntry));
	}

	@Test
	public void testGetKeyValuePairsFileEntryWithNoDLFileEntryMetadata()
		throws Exception {

		Mockito.when(
			_fileEntry.getModel()
		).thenReturn(
			Mockito.mock(DLFileEntry.class)
		);

		Mockito.when(
			_fileEntry.getFileVersion()
		).thenReturn(
			Mockito.mock(FileVersion.class)
		);

		Mockito.when(
			_ddmStructureLocalService.getClassStructures(
				Mockito.anyLong(), Mockito.anyLong(),
				Mockito.any(OrderByComparator.class))
		).thenReturn(
			Collections.singletonList(Mockito.mock(DDMStructure.class))
		);

		Mockito.when(
			_dlFileEntryMetadataLocalService.fetchFileEntryMetadata(
				Mockito.anyLong(), Mockito.anyLong())
		).thenReturn(
			null
		);

		Assert.assertEquals(
			Collections.emptyList(),
			_fileEntryMetadataOpenGraphTagsProvider.
				getFileEntryMetadataOpenGraphTagKeyValuePairs(_fileEntry));
	}

	@Test
	public void testGetKeyValuePairsFileEntryWithNoMetadata() throws Exception {
		Mockito.when(
			_fileEntry.getModel()
		).thenReturn(
			Mockito.mock(DLFileEntry.class)
		);

		Mockito.when(
			_fileEntry.getFileVersion()
		).thenReturn(
			Mockito.mock(FileVersion.class)
		);

		Mockito.when(
			_ddmStructureLocalService.getClassStructures(
				Mockito.anyLong(), Mockito.anyLong(),
				Mockito.any(OrderByComparator.class))
		).thenReturn(
			Collections.emptyList()
		);

		Assert.assertEquals(
			Collections.emptyList(),
			_fileEntryMetadataOpenGraphTagsProvider.
				getFileEntryMetadataOpenGraphTagKeyValuePairs(_fileEntry));
	}

	@Test
	public void testGetKeyValuePairsFileEntryWithNoTiffMetadata()
		throws Exception {

		Mockito.when(
			_fileEntry.getModel()
		).thenReturn(
			Mockito.mock(DLFileEntry.class)
		);

		Mockito.when(
			_fileEntry.getFileVersion()
		).thenReturn(
			Mockito.mock(FileVersion.class)
		);

		Mockito.when(
			_ddmStructureLocalService.getClassStructures(
				Mockito.anyLong(), Mockito.anyLong(),
				Mockito.any(OrderByComparator.class))
		).thenReturn(
			Collections.singletonList(Mockito.mock(DDMStructure.class))
		);

		Mockito.when(
			_dlFileEntryMetadataLocalService.fetchFileEntryMetadata(
				Mockito.anyLong(), Mockito.anyLong())
		).thenReturn(
			_dlFileEntryMetadata
		);

		Mockito.when(
			_ddmFieldLocalService.getDDMFields(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			Collections.emptyList()
		);

		Assert.assertEquals(
			Collections.emptyList(),
			_fileEntryMetadataOpenGraphTagsProvider.
				getFileEntryMetadataOpenGraphTagKeyValuePairs(_fileEntry));
	}

	@Test
	public void testGetKeyValuePairsFileEntryWithTiffMetadata()
		throws Exception {

		Mockito.when(
			_fileEntry.getModel()
		).thenReturn(
			Mockito.mock(DLFileEntry.class)
		);

		Mockito.when(
			_fileEntry.getFileVersion()
		).thenReturn(
			Mockito.mock(FileVersion.class)
		);

		Mockito.when(
			_ddmStructureLocalService.getClassStructures(
				Mockito.anyLong(), Mockito.anyLong(),
				Mockito.nullable(OrderByComparator.class))
		).thenReturn(
			Collections.singletonList(Mockito.mock(DDMStructure.class))
		);

		Mockito.when(
			_ddmFieldLocalService.fetchDDMFieldAttribute(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyString())
		).thenReturn(
			_ddmFieldAttribute
		);

		Mockito.when(
			_dlFileEntryMetadataLocalService.fetchFileEntryMetadata(
				Mockito.anyLong(), Mockito.anyLong())
		).thenReturn(
			_dlFileEntryMetadata
		);

		Mockito.when(
			_ddmFieldLocalService.getDDMFields(
				_dlFileEntryMetadata.getDDMStorageId(), "TIFF_IMAGE_LENGTH")
		).thenReturn(
			Collections.singletonList(Mockito.mock(DDMField.class))
		);

		String expectedValue = StringUtil.randomString();

		Mockito.when(
			_ddmFieldAttribute.getAttributeValue()
		).thenReturn(
			expectedValue
		);

		Assert.assertEquals(
			ListUtil.fromArray(
				new KeyValuePair("og:image:height", expectedValue)),
			_fileEntryMetadataOpenGraphTagsProvider.
				getFileEntryMetadataOpenGraphTagKeyValuePairs(_fileEntry));
	}

	@Test
	public void testGetKeyValuePairsNondlFileEntry() throws Exception {
		Assert.assertEquals(
			Collections.emptyList(),
			_fileEntryMetadataOpenGraphTagsProvider.
				getFileEntryMetadataOpenGraphTagKeyValuePairs(_fileEntry));
	}

	private final DDMFieldAttribute _ddmFieldAttribute = Mockito.mock(
		DDMFieldAttribute.class);
	private final DDMFieldLocalService _ddmFieldLocalService = Mockito.mock(
		DDMFieldLocalService.class);
	private final DDMStructureLocalService _ddmStructureLocalService =
		Mockito.mock(DDMStructureLocalService.class);
	private final DLFileEntryMetadata _dlFileEntryMetadata = Mockito.mock(
		DLFileEntryMetadata.class);
	private final DLFileEntryMetadataLocalService
		_dlFileEntryMetadataLocalService = Mockito.mock(
			DLFileEntryMetadataLocalService.class);
	private final FileEntry _fileEntry = Mockito.mock(FileEntry.class);
	private FileEntryMetadataOpenGraphTagsProvider
		_fileEntryMetadataOpenGraphTagsProvider;
	private final Portal _portal = Mockito.mock(Portal.class);

}