/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.headless.admin.site.dto.v1_0.ClassNameReference;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.object.constants.ObjectDefinitionSettingConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectDefinitionSetting;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectDefinitionSettingLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Jhosseph Gonzalez
 */
public class CollectionUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@AfterClass
	public static void tearDownClass() {
		_companyThreadLocalMockedStatic.close();
		_exportImportThreadLocalMockedStatic.close();
		_objectDefinitionLocalServiceUtilMockedStatic.close();
		_objectDefinitionSettingLocalServiceUtilMockedStatic.close();
	}

	@Before
	public void setUp() {
		_setUpCompanyThreadLocal();
		_setUpExportImportThreadLocal();
		_setUpInfoCollectionProvider();
		_setUpObjectDefinitionLocalServiceUtil();
		_setUpObjectDefinitionSettingLocalServiceUtil();
		_setUpRelatedInfoItemCollectionProvider();
	}

	@Test
	public void testGetCollectionJSONObject() throws Exception {

		// InfoCollectionProvider

		_assertCollectionJSONObject(
			CollectionUtil.getCollectionJSONObject(
				_getClassNameReference(), _COMPANY_ID, _infoItemServiceRegistry,
				RandomTestUtil.randomLong()));

		Mockito.verify(
			_infoItemServiceRegistry
		).getInfoItemService(
			InfoCollectionProvider.class, _NEW_CLASS_NAME
		);

		Mockito.verify(
			_infoItemServiceRegistry, Mockito.never()
		).getInfoItemService(
			RelatedInfoItemCollectionProvider.class, _NEW_CLASS_NAME
		);

		_objectDefinitionLocalServiceUtilMockedStatic.verify(
			() -> ObjectDefinitionLocalServiceUtil.fetchObjectDefinition(
				_OBJECT_DEFINITION_ID));

		_objectDefinitionSettingLocalServiceUtilMockedStatic.verify(
			() ->
				ObjectDefinitionSettingLocalServiceUtil.
					fetchObjectDefinitionSetting(
						_COMPANY_ID,
						ObjectDefinitionSettingConstants.NAME_OLD_CLASS_NAME,
						_OLD_CLASS_NAME));

		// RelatedInfoItemCollectionProvider

		Mockito.when(
			_infoItemServiceRegistry.getInfoItemService(
				InfoCollectionProvider.class, _NEW_CLASS_NAME)
		).thenReturn(
			null
		);

		_assertCollectionJSONObject(
			CollectionUtil.getCollectionJSONObject(
				_getClassNameReference(), _COMPANY_ID, _infoItemServiceRegistry,
				RandomTestUtil.randomLong()));

		Mockito.verify(
			_infoItemServiceRegistry, Mockito.times(2)
		).getInfoItemService(
			InfoCollectionProvider.class, _NEW_CLASS_NAME
		);

		Mockito.verify(
			_infoItemServiceRegistry
		).getInfoItemService(
			RelatedInfoItemCollectionProvider.class, _NEW_CLASS_NAME
		);
	}

	private void _assertCollectionJSONObject(JSONObject jsonObject) {
		Assert.assertEquals(_OLD_CLASS_NAME, jsonObject.getString("itemType"));
		Assert.assertEquals(_KEY, jsonObject.getString("key"));
		Assert.assertEquals(_LABEL, jsonObject.getString("title"));
		Assert.assertEquals(
			InfoListProviderItemSelectorReturnType.class.getName(),
			jsonObject.getString("type"));
	}

	private ClassNameReference _getClassNameReference() {
		ClassNameReference classNameReference = new ClassNameReference();

		classNameReference.setClassName(() -> _OLD_CLASS_NAME);

		return classNameReference;
	}

	private void _setUpCompanyThreadLocal() {
		_companyThreadLocalMockedStatic.when(
			CompanyThreadLocal::getCompanyId
		).thenReturn(
			_COMPANY_ID
		);
	}

	private void _setUpExportImportThreadLocal() {
		_exportImportThreadLocalMockedStatic.when(
			ExportImportThreadLocal::isImportInProcess
		).thenReturn(
			true
		);
	}

	private void _setUpInfoCollectionProvider() {
		Mockito.when(
			_infoCollectionProvider.getCollectionItemClassName()
		).thenReturn(
			_OLD_CLASS_NAME
		);

		Mockito.when(
			_infoCollectionProvider.getKey()
		).thenReturn(
			_KEY
		);

		Mockito.when(
			_infoCollectionProvider.getLabel(LocaleUtil.getDefault())
		).thenReturn(
			_LABEL
		);

		Mockito.when(
			_infoItemServiceRegistry.getInfoItemService(
				InfoCollectionProvider.class, _NEW_CLASS_NAME)
		).thenReturn(
			_infoCollectionProvider
		);
	}

	private void _setUpObjectDefinitionLocalServiceUtil() {
		ObjectDefinition objectDefinition = Mockito.mock(
			ObjectDefinition.class);

		Mockito.when(
			objectDefinition.getClassName()
		).thenReturn(
			_NEW_CLASS_NAME
		);

		_objectDefinitionLocalServiceUtilMockedStatic.when(
			() -> ObjectDefinitionLocalServiceUtil.fetchObjectDefinition(
				_OBJECT_DEFINITION_ID)
		).thenReturn(
			objectDefinition
		);
	}

	private void _setUpObjectDefinitionSettingLocalServiceUtil() {
		ObjectDefinitionSetting objectDefinitionSetting = Mockito.mock(
			ObjectDefinitionSetting.class);

		Mockito.when(
			objectDefinitionSetting.getObjectDefinitionId()
		).thenReturn(
			_OBJECT_DEFINITION_ID
		);

		_objectDefinitionSettingLocalServiceUtilMockedStatic.when(
			() ->
				ObjectDefinitionSettingLocalServiceUtil.
					fetchObjectDefinitionSetting(
						_COMPANY_ID,
						ObjectDefinitionSettingConstants.NAME_OLD_CLASS_NAME,
						_OLD_CLASS_NAME)
		).thenReturn(
			objectDefinitionSetting
		);
	}

	private void _setUpRelatedInfoItemCollectionProvider() {
		Mockito.when(
			_relatedInfoItemCollectionProvider.getCollectionItemClassName()
		).thenReturn(
			_OLD_CLASS_NAME
		);

		Mockito.when(
			_relatedInfoItemCollectionProvider.getKey()
		).thenReturn(
			_KEY
		);

		Mockito.when(
			_relatedInfoItemCollectionProvider.getLabel(LocaleUtil.getDefault())
		).thenReturn(
			_LABEL
		);

		Mockito.when(
			_infoItemServiceRegistry.getInfoItemService(
				RelatedInfoItemCollectionProvider.class, _NEW_CLASS_NAME)
		).thenReturn(
			_relatedInfoItemCollectionProvider
		);
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final String _KEY = RandomTestUtil.randomString();

	private static final String _LABEL = RandomTestUtil.randomString();

	private static final String _NEW_CLASS_NAME =
		"com.liferay.object.model.ObjectDefinition#C3D4";

	private static final long _OBJECT_DEFINITION_ID =
		RandomTestUtil.randomLong();

	private static final String _OLD_CLASS_NAME =
		"com.liferay.object.model.ObjectDefinition#A1B2";

	private static final MockedStatic<CompanyThreadLocal>
		_companyThreadLocalMockedStatic = Mockito.mockStatic(
			CompanyThreadLocal.class);
	private static final MockedStatic<ExportImportThreadLocal>
		_exportImportThreadLocalMockedStatic = Mockito.mockStatic(
			ExportImportThreadLocal.class);
	private static final MockedStatic<ObjectDefinitionLocalServiceUtil>
		_objectDefinitionLocalServiceUtilMockedStatic = Mockito.mockStatic(
			ObjectDefinitionLocalServiceUtil.class);
	private static final MockedStatic<ObjectDefinitionSettingLocalServiceUtil>
		_objectDefinitionSettingLocalServiceUtilMockedStatic =
			Mockito.mockStatic(ObjectDefinitionSettingLocalServiceUtil.class);

	private final InfoCollectionProvider _infoCollectionProvider = Mockito.mock(
		InfoCollectionProvider.class);
	private final InfoItemServiceRegistry _infoItemServiceRegistry =
		Mockito.mock(InfoItemServiceRegistry.class);
	private final RelatedInfoItemCollectionProvider
		_relatedInfoItemCollectionProvider = Mockito.mock(
			RelatedInfoItemCollectionProvider.class);

}