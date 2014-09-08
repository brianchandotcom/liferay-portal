/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.documentlibrary.context.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeService;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngine;
import org.junit.Test;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Zaera
 */
public class FileVersionMetadataHelperTest extends PowerMockito {

	public FileVersionMetadataHelperTest() {
		_fileVersion = mock(FileVersion.class);

		_dlFileEntryMetadataLocalService = mock(
			DLFileEntryMetadataLocalService.class);

		_dlFileEntryTypeService = mock(DLFileEntryTypeService.class);

		_storageEngine = mock(StorageEngine.class);

		_fileVersionMetadataHelper = new FileVersionMetadataHelper(
			_fileVersion, _dlFileEntryMetadataLocalService,
			_dlFileEntryTypeService, _storageEngine);
	}

	@Test
	public void testGetDDMStructures() throws Exception {
		DLFileVersion model = mock(DLFileVersion.class);

		_mockDDMStructures(model);

		List<DDMStructure> ddmStructures =
			_fileVersionMetadataHelper.getDDMStructures();

		Assert.assertEquals(2, ddmStructures.size());
	}

	@Test
	public void testGetDDMStructuresReturnsEmptyListForNonDLFileVersions()
		throws Exception {

		Object model = new Object();

		_mockDDMStructures(model);

		List<DDMStructure> ddmStructures =
			_fileVersionMetadataHelper.getDDMStructures();

		Assert.assertEquals(0, ddmStructures.size());
	}

	@Test
	public void testGetFields() throws Exception {
		DLFileVersion model = mock(DLFileVersion.class);

		_mockDDMStructures(model);

		List<DDMStructure> ddmStructures =
			_fileVersionMetadataHelper.getDDMStructures();

		DDMStructure ddmStructure = ddmStructures.get(0);

		_mockFields();

		Fields fields = _fileVersionMetadataHelper.getFields(ddmStructure);

		Assert.assertNotNull(fields);
	}

	private void _mockFields() throws PortalException {
		DLFileEntryMetadata fileEntryMetadata = mock(DLFileEntryMetadata.class);

		when(
			_dlFileEntryMetadataLocalService.getFileEntryMetadata(
				Matchers.anyLong(), Matchers.anyLong())
		).thenReturn(
			fileEntryMetadata
		);

		Fields fields = mock(Fields.class);

		when(
			_storageEngine.getFields(fileEntryMetadata.getDDMStorageId())
		).thenReturn(
			fields
		);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetFieldsFailsForUnknownStructure() throws Exception {
		DLFileVersion model = mock(DLFileVersion.class);

		_mockDDMStructures(model);

		DDMStructure ddmStructure = mock(DDMStructure.class);

		_fileVersionMetadataHelper.getFields(ddmStructure);
	}

	private void _mockDDMStructures(Object model) throws PortalException {
		when(
			_fileVersion.getModel()
		).thenReturn(
			model
		);

		DLFileEntryType dlFileEntryType = mock(DLFileEntryType.class);

		when(
			_dlFileEntryTypeService.getFileEntryType(Matchers.anyLong())
		).thenReturn(
			dlFileEntryType
		);

		List<DDMStructure> ddmStructures = new ArrayList<>();

		ddmStructures.add(mock(DDMStructure.class));
		ddmStructures.add(mock(DDMStructure.class));

		when(
			dlFileEntryType.getDDMStructures()
		).thenReturn(
			ddmStructures
		);
	}

	private DLFileEntryMetadataLocalService _dlFileEntryMetadataLocalService;
	private DLFileEntryTypeService _dlFileEntryTypeService;
	private FileVersion _fileVersion;
	private FileVersionMetadataHelper _fileVersionMetadataHelper;
	private StorageEngine _storageEngine;

}