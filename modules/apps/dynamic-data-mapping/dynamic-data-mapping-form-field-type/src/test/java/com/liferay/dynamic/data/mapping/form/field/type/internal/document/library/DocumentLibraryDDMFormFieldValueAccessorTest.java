/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Pedro Queiroz
 */
public class DocumentLibraryDDMFormFieldValueAccessorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
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

	private static void _setUpDocumentLibraryDDMFormFieldValueAccessor()
		throws Exception {

		DLAppService dlAppService = Mockito.mock(DLAppService.class);

		_documentLibraryDDMFormFieldValueAccessor =
			new DocumentLibraryDDMFormFieldValueAccessor();

		ReflectionTestUtil.setFieldValue(
			_documentLibraryDDMFormFieldValueAccessor, "jsonFactory",
			_jsonFactory);
		ReflectionTestUtil.setFieldValue(
			_documentLibraryDDMFormFieldValueAccessor, "_dlAppService",
			dlAppService);

		Mockito.when(
			dlAppService.getFileEntryByUuidAndGroupId(
				_FILE_ENTRY_UUID, _GROUP_ID)
		).thenReturn(
			_fileEntry
		);
	}

	private static void _setUpFileEntry() {
		_fileEntry.setUuid(_FILE_ENTRY_UUID);
		_fileEntry.setGroupId(_GROUP_ID);

		Mockito.when(
			_fileEntry.isInTrash()
		).thenReturn(
			false
		);
	}

	private static final String _FILE_ENTRY_UUID =
		"f85c8ae1-603b-04eb-1132-12645d73519e";

	private static final long _GROUP_ID = 32964;

	private static DocumentLibraryDDMFormFieldValueAccessor
		_documentLibraryDDMFormFieldValueAccessor;
	private static final FileEntry _fileEntry = Mockito.mock(FileEntry.class);
	private static final JSONFactory _jsonFactory = new JSONFactoryImpl();

}