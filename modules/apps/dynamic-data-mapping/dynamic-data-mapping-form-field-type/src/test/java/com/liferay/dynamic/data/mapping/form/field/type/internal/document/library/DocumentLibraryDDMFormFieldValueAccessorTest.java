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

package com.liferay.dynamic.data.mapping.form.field.type.internal.document.library;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.LocaleUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Pedro Queiroz
 */
@RunWith(PowerMockRunner.class)
public class DocumentLibraryDDMFormFieldValueAccessorTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		_setUpDocumentLibraryDDMFormFieldValueAccessor();
		_setUpFileEntry();
	}

	@Test
	public void testEmpty() {
		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"documentLibrary", new UnlocalizedValue("{}"));

		Assert.assertTrue(
			_documentLibraryDDMFormFieldValueAccessor.isEmpty(
				ddmFormFieldValue, LocaleUtil.US));
	}

	@Test
	public void testNotEmpty() {
		JSONObject valueJSONObject = _jsonFactory.createJSONObject();

		valueJSONObject.put(
			"groupId", _GROUP_ID
		).put(
			"title", "Welcome to Liferay Forms!"
		).put(
			"type", "document"
		).put(
			"uuid", _FILE_ENTRY_UUID
		);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"documentLibrary",
				new UnlocalizedValue(valueJSONObject.toString()));

		Assert.assertFalse(
			_documentLibraryDDMFormFieldValueAccessor.isEmpty(
				ddmFormFieldValue, LocaleUtil.US));
	}

	private void _setUpDocumentLibraryDDMFormFieldValueAccessor()
		throws Exception {

		_documentLibraryDDMFormFieldValueAccessor =
			new DocumentLibraryDDMFormFieldValueAccessor();

		field(
			DocumentLibraryDDMFormFieldValueAccessor.class, "jsonFactory"
		).set(
			_documentLibraryDDMFormFieldValueAccessor, _jsonFactory
		);

		MemberMatcher.field(
			DocumentLibraryDDMFormFieldValueAccessor.class, "_dlAppService"
		).set(
			_documentLibraryDDMFormFieldValueAccessor, _dlAppService
		);

		PowerMockito.when(
			_dlAppService.getFileEntryByUuidAndGroupId(
				_FILE_ENTRY_UUID, _GROUP_ID)
		).thenReturn(
			_fileEntry
		);
	}

	private void _setUpFileEntry() {
		_fileEntry.setUuid(_FILE_ENTRY_UUID);
		_fileEntry.setGroupId(_GROUP_ID);

		PowerMockito.when(
			_fileEntry.isInTrash()
		).thenReturn(
			false
		);
	}

	private static final String _FILE_ENTRY_UUID =
		"f85c8ae1-603b-04eb-1132-12645d73519e";

	private static final long _GROUP_ID = 32964;

	@Mock
	private DLAppService _dlAppService;

	private DocumentLibraryDDMFormFieldValueAccessor
		_documentLibraryDDMFormFieldValueAccessor;

	@Mock
	private FileEntry _fileEntry;

	private final JSONFactory _jsonFactory = new JSONFactoryImpl();

}