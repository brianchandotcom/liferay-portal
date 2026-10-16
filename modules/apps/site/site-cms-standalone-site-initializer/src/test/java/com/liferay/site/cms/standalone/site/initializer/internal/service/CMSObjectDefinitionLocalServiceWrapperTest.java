/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.standalone.site.initializer.internal.service;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.exception.ObjectDefinitionObjectFolderIdException;
import com.liferay.object.exception.ObjectDefinitionScopeException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFolderLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Jürgen Kappler
 */
public class CMSObjectDefinitionLocalServiceWrapperTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_cmsObjectDefinitionLocalServiceWrapper.setWrappedService(
			_objectDefinitionLocalService);

		ReflectionTestUtil.setFieldValue(
			_cmsObjectDefinitionLocalServiceWrapper,
			"_objectFolderLocalService", _objectFolderLocalService);
	}

	@Test
	@TestInfo("LPD-110251")
	public void testAddCustomObjectDefinitionInCMSFolderWithDepotScope()
		throws Exception {

		for (String externalReferenceCode : _CMS_EXTERNAL_REFERENCE_CODES) {
			ObjectDefinition objectDefinition = Mockito.mock(
				ObjectDefinition.class);

			Mockito.when(
				_objectDefinitionLocalService.addCustomObjectDefinition(
					Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong(),
					Mockito.any(), Mockito.any(), Mockito.anyBoolean(),
					Mockito.anyBoolean(), Mockito.anyBoolean(),
					Mockito.anyBoolean(), Mockito.anyBoolean(),
					Mockito.anyBoolean(), Mockito.anyBoolean(),
					Mockito.anyBoolean(), Mockito.anyBoolean(), Mockito.any(),
					Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
					Mockito.any(), Mockito.anyBoolean(), Mockito.anyString(),
					Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
					Mockito.any())
			).thenReturn(
				objectDefinition
			);

			Assert.assertSame(
				objectDefinition,
				_addCustomObjectDefinition(
					_mockObjectFolder(externalReferenceCode),
					ObjectDefinitionConstants.SCOPE_DEPOT));
		}
	}

	@Test
	@TestInfo("LPD-110251")
	public void testAddCustomObjectDefinitionInDefaultFolder()
		throws Exception {

		Assert.assertThrows(
			ObjectDefinitionObjectFolderIdException.class,
			() -> _addCustomObjectDefinition(
				0, ObjectDefinitionConstants.SCOPE_DEPOT));
	}

	@Test
	@TestInfo("LPD-110251")
	public void testAddCustomObjectDefinitionWhenFolderIsNotCMS()
		throws Exception {

		Assert.assertThrows(
			ObjectDefinitionObjectFolderIdException.class,
			() -> _addCustomObjectDefinition(
				_mockObjectFolder(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_DEPOT));
	}

	@Test
	@TestInfo("LPD-110251")
	public void testAddCustomObjectDefinitionWhenScopeIsNotDepot()
		throws Exception {

		Assert.assertThrows(
			ObjectDefinitionScopeException.class,
			() -> _addCustomObjectDefinition(
				_mockObjectFolder(
					ObjectFolderConstants.
						EXTERNAL_REFERENCE_CODE_CONTENT_STRUCTURES),
				ObjectDefinitionConstants.SCOPE_SITE));
	}

	@Test
	@TestInfo("LPD-110251")
	public void testAddObjectDefinitionInCMSFolderWithDepotScope()
		throws Exception {

		for (String externalReferenceCode : _CMS_EXTERNAL_REFERENCE_CODES) {
			ObjectDefinition objectDefinition = Mockito.mock(
				ObjectDefinition.class);

			String objectDefinitionExternalReferenceCode =
				RandomTestUtil.randomString();
			long userId = RandomTestUtil.randomLong();
			long objectFolderId = _mockObjectFolder(externalReferenceCode);

			Mockito.when(
				_objectDefinitionLocalService.addObjectDefinition(
					objectDefinitionExternalReferenceCode, userId,
					objectFolderId, true, ObjectDefinitionConstants.SCOPE_DEPOT,
					false)
			).thenReturn(
				objectDefinition
			);

			Assert.assertSame(
				objectDefinition,
				_cmsObjectDefinitionLocalServiceWrapper.addObjectDefinition(
					objectDefinitionExternalReferenceCode, userId,
					objectFolderId, true, ObjectDefinitionConstants.SCOPE_DEPOT,
					false));
		}
	}

	@Test
	@TestInfo("LPD-110251")
	public void testAddObjectDefinitionInDefaultFolder() throws Exception {
		Assert.assertThrows(
			ObjectDefinitionObjectFolderIdException.class,
			() -> _cmsObjectDefinitionLocalServiceWrapper.addObjectDefinition(
				RandomTestUtil.randomString(), RandomTestUtil.randomLong(), 0,
				true, ObjectDefinitionConstants.SCOPE_DEPOT, false));
	}

	@Test
	@TestInfo("LPD-110251")
	public void testAddObjectDefinitionOfSystemObjectDefinition()
		throws Exception {

		ObjectDefinition objectDefinition = Mockito.mock(
			ObjectDefinition.class);

		String externalReferenceCode = "L_" + RandomTestUtil.randomString();
		long userId = RandomTestUtil.randomLong();

		Mockito.when(
			_objectDefinitionLocalService.addObjectDefinition(
				externalReferenceCode, userId, 0, true,
				ObjectDefinitionConstants.SCOPE_SITE, false)
		).thenReturn(
			objectDefinition
		);

		Assert.assertSame(
			objectDefinition,
			_cmsObjectDefinitionLocalServiceWrapper.addObjectDefinition(
				externalReferenceCode, userId, 0, true,
				ObjectDefinitionConstants.SCOPE_SITE, false));
	}

	@Test
	@TestInfo("LPD-110251")
	public void testAddObjectDefinitionWhenFolderIsNotCMS() throws Exception {
		Assert.assertThrows(
			ObjectDefinitionObjectFolderIdException.class,
			() -> _cmsObjectDefinitionLocalServiceWrapper.addObjectDefinition(
				RandomTestUtil.randomString(), RandomTestUtil.randomLong(),
				_mockObjectFolder(RandomTestUtil.randomString()), true,
				ObjectDefinitionConstants.SCOPE_DEPOT, false));
	}

	@Test
	@TestInfo("LPD-110251")
	public void testAddObjectDefinitionWhenScopeIsNotDepot() throws Exception {
		Assert.assertThrows(
			ObjectDefinitionScopeException.class,
			() -> _cmsObjectDefinitionLocalServiceWrapper.addObjectDefinition(
				RandomTestUtil.randomString(), RandomTestUtil.randomLong(),
				_mockObjectFolder(
					ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_FILE_TYPES),
				true, ObjectDefinitionConstants.SCOPE_SITE, false));
	}

	private ObjectDefinition _addCustomObjectDefinition(
			long objectFolderId, String scope)
		throws Exception {

		return _cmsObjectDefinitionLocalServiceWrapper.
			addCustomObjectDefinition(
				RandomTestUtil.randomString(), RandomTestUtil.randomLong(),
				objectFolderId, null, null, false, false, false, false, false,
				false, false, false, false, null, null,
				RandomTestUtil.randomString(), null, null, null, false, scope,
				null, null, null, null, null);
	}

	private long _mockObjectFolder(String externalReferenceCode)
		throws Exception {

		long objectFolderId = RandomTestUtil.randomLong();

		ObjectFolder objectFolder = Mockito.mock(ObjectFolder.class);

		Mockito.when(
			objectFolder.getExternalReferenceCode()
		).thenReturn(
			externalReferenceCode
		);

		Mockito.when(
			_objectFolderLocalService.getObjectFolder(objectFolderId)
		).thenReturn(
			objectFolder
		);

		return objectFolderId;
	}

	private static final String[] _CMS_EXTERNAL_REFERENCE_CODES = {
		ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENT_STRUCTURES,
		ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_FILE_TYPES,
		ObjectFolderConstants.
			EXTERNAL_REFERENCE_CODE_STRUCTURE_REPEATABLE_GROUPS
	};

	private final CMSObjectDefinitionLocalServiceWrapper
		_cmsObjectDefinitionLocalServiceWrapper =
			new CMSObjectDefinitionLocalServiceWrapper();
	private final ObjectDefinitionLocalService _objectDefinitionLocalService =
		Mockito.mock(ObjectDefinitionLocalService.class);
	private final ObjectFolderLocalService _objectFolderLocalService =
		Mockito.mock(ObjectFolderLocalService.class);

}