/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.constants.ObjectPortletKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.service.ObjectFolderLocalServiceUtil;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Magdalena Jedraszak
 */
public class ObjectDefinitionImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@AfterClass
	public static void tearDownClass() {
		_objectFolderLocalServiceUtilMockedStatic.close();
	}

	@Test
	public void testGetPortletId() {
		ObjectDefinition objectDefinition = new ObjectDefinitionImpl();

		String classNameSuffix = RandomTestUtil.randomString();

		objectDefinition.setClassName(
			ObjectDefinitionConstants.
				CLASS_NAME_PREFIX_CUSTOM_OBJECT_DEFINITION + classNameSuffix);

		Assert.assertEquals(
			ObjectPortletKeys.OBJECT_DEFINITIONS + StringPool.UNDERLINE +
				classNameSuffix,
			objectDefinition.getPortletId());
	}

	@Test
	public void testGetRESTContextPath() {

		// Modifiable custom object definition

		_testGetRESTContextPath("/c/customobjects", "CustomObject", false);

		// Modifiable system object definition

		_testGetRESTContextPath(
			"/headless-builder/endpoints", "APIEndpoint", true);

		// Unmodifiable system object definition

		try {
			ObjectDefinition objectDefinition = _createObjectDefinition(
				false, "AccountEntry", true);

			objectDefinition.getRESTContextPath();

			Assert.fail();
		}
		catch (UnsupportedOperationException unsupportedOperationException) {
			Assert.assertNotNull(unsupportedOperationException);
		}
	}

	@Test
	public void testIsCMS() throws Exception {
		_testIsCMS(
			ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENT_STRUCTURES,
			"false",
			objectDefinition -> Assert.assertFalse(objectDefinition.isCMS()));
		_testIsCMS(
			ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENT_STRUCTURES,
			"true",
			objectDefinition -> Assert.assertTrue(objectDefinition.isCMS()));
		_testIsCMS(
			ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_FILE_TYPES, "false",
			objectDefinition -> Assert.assertFalse(objectDefinition.isCMS()));
		_testIsCMS(
			ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_FILE_TYPES, "true",
			objectDefinition -> Assert.assertTrue(objectDefinition.isCMS()));
		_testIsCMS(
			RandomTestUtil.randomString(), "true",
			objectDefinition -> Assert.assertFalse(objectDefinition.isCMS()));
	}

	private ObjectDefinition _createObjectDefinition(
		boolean modifiable, String name, boolean system) {

		ObjectDefinition objectDefinition = new ObjectDefinitionImpl();

		objectDefinition.setModifiable(modifiable);
		objectDefinition.setName(name);
		objectDefinition.setPluralLabel(
			TextFormatter.formatPlural(StringUtil.lowerCaseFirstLetter(name)));
		objectDefinition.setSystem(system);

		return objectDefinition;
	}

	private void _testGetRESTContextPath(
		String expectedRESTContextPath, String name, boolean system) {

		ObjectDefinition objectDefinition = Mockito.spy(
			_createObjectDefinition(true, name, system));

		Assert.assertEquals(
			expectedRESTContextPath, objectDefinition.getRESTContextPath());
	}

	private void _testIsCMS(
			String externalReferenceCode, String featureFlagEnabled,
			UnsafeConsumer<ObjectDefinition, Exception> unsafeConsumer)
		throws Exception {

		long objectFolderId = RandomTestUtil.randomLong();

		ObjectFolder objectFolder = Mockito.mock(ObjectFolder.class);

		Mockito.when(
			objectFolder.getExternalReferenceCode()
		).thenReturn(
			externalReferenceCode
		);

		_objectFolderLocalServiceUtilMockedStatic.when(
			() -> ObjectFolderLocalServiceUtil.fetchObjectFolder(objectFolderId)
		).thenReturn(
			objectFolder
		);

		ObjectDefinition objectDefinition = new ObjectDefinitionImpl();

		objectDefinition.setObjectFolderId(objectFolderId);

		String featureFlagKey = "feature.flag.LPD-17564";

		String originalValue = PropsUtil.get(featureFlagKey);

		try {
			PropsUtil.set(featureFlagKey, featureFlagEnabled);

			unsafeConsumer.accept(objectDefinition);
		}
		finally {
			PropsUtil.set(featureFlagKey, originalValue);
		}
	}

	private static final MockedStatic<ObjectFolderLocalServiceUtil>
		_objectFolderLocalServiceUtilMockedStatic = Mockito.mockStatic(
			ObjectFolderLocalServiceUtil.class);

}