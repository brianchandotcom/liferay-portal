/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.asset.model;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.document.library.helper.DLURLHelper;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.web.internal.object.entries.display.context.ObjectEntryDisplayContextFactoryImpl;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.ServletContext;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Carolina Barbosa
 */
public class ObjectEntryAssetRendererFactoryTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(new LanguageImpl());

		Mockito.when(
			_objectDefinitionLocalService.fetchObjectDefinition(
				Mockito.anyLong())
		).thenReturn(
			_objectDefinition
		);

		_resourceActionsUtilMockedStatic.when(
			ResourceActionsUtil::getModelResourceNamePrefix
		).thenReturn(
			RandomTestUtil.randomString()
		);
	}

	@After
	public void tearDown() throws Exception {
		_resourceActionsUtilMockedStatic.close();
	}

	@Test
	public void testGetTypeName() {
		ObjectEntryAssetRendererFactory objectEntryAssetRendererFactory =
			_getObjectEntryAssetRendererFactory();

		Mockito.when(
			_objectDefinition.isCMS()
		).thenReturn(
			false
		);

		String typeName1 = objectEntryAssetRendererFactory.getTypeName(
			LocaleUtil.getDefault());

		Assert.assertFalse(typeName1.contains("(CMS)"));

		Mockito.when(
			_objectDefinition.isCMS()
		).thenReturn(
			true
		);

		String typeName2 = objectEntryAssetRendererFactory.getTypeName(
			LocaleUtil.getDefault());

		Assert.assertEquals(typeName1 + " (CMS)", typeName2);
	}

	@Test
	public void testIsActive() {
		long companyId = RandomTestUtil.randomLong();

		Mockito.when(
			_objectDefinition.getCompanyId()
		).thenReturn(
			companyId
		);

		ObjectEntryAssetRendererFactory objectEntryAssetRendererFactory =
			_getObjectEntryAssetRendererFactory();

		Assert.assertFalse(
			objectEntryAssetRendererFactory.isActive(
				RandomTestUtil.randomLong()));
		Assert.assertTrue(objectEntryAssetRendererFactory.isActive(companyId));
	}

	@Test
	public void testIsSelectable() {
		Mockito.when(
			_objectDefinition.getScope()
		).thenReturn(
			ObjectDefinitionConstants.SCOPE_COMPANY
		);

		ObjectEntryAssetRendererFactory objectEntryAssetRendererFactory1 =
			_getObjectEntryAssetRendererFactory();

		Assert.assertFalse(objectEntryAssetRendererFactory1.isSelectable());

		Mockito.when(
			_objectDefinition.getScope()
		).thenReturn(
			ObjectDefinitionConstants.SCOPE_SITE
		);

		ObjectEntryAssetRendererFactory objectEntryAssetRendererFactory2 =
			_getObjectEntryAssetRendererFactory();

		Assert.assertTrue(objectEntryAssetRendererFactory2.isSelectable());
	}

	private ObjectEntryAssetRendererFactory
		_getObjectEntryAssetRendererFactory() {

		ObjectEntryAssetRendererFactory objectEntryAssetRendererFactory =
			new ObjectEntryAssetRendererFactory(
				_assetDisplayPageFriendlyURLProvider, _dlAppLocalService,
				_dlURLHelper, _objectDefinition, _objectDefinitionLocalService,
				_objectEntryDisplayContextFactoryImpl, _objectEntryLocalService,
				_objectEntryService, _objectFieldLocalService, _servletContext);

		objectEntryAssetRendererFactory.setClassName(
			RandomTestUtil.randomString());

		return objectEntryAssetRendererFactory;
	}

	private final AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider = Mockito.mock(
			AssetDisplayPageFriendlyURLProvider.class);
	private final DLAppLocalService _dlAppLocalService = Mockito.mock(
		DLAppLocalService.class);
	private final DLURLHelper _dlURLHelper = Mockito.mock(DLURLHelper.class);
	private final ObjectDefinition _objectDefinition = Mockito.mock(
		ObjectDefinition.class);
	private final ObjectDefinitionLocalService _objectDefinitionLocalService =
		Mockito.mock(ObjectDefinitionLocalService.class);
	private final ObjectEntryDisplayContextFactoryImpl
		_objectEntryDisplayContextFactoryImpl = Mockito.mock(
			ObjectEntryDisplayContextFactoryImpl.class);
	private final ObjectEntryLocalService _objectEntryLocalService =
		Mockito.mock(ObjectEntryLocalService.class);
	private final ObjectEntryService _objectEntryService = Mockito.mock(
		ObjectEntryService.class);
	private final ObjectFieldLocalService _objectFieldLocalService =
		Mockito.mock(ObjectFieldLocalService.class);
	private final MockedStatic<ResourceActionsUtil>
		_resourceActionsUtilMockedStatic = Mockito.mockStatic(
			ResourceActionsUtil.class);
	private final ServletContext _servletContext = Mockito.mock(
		ServletContext.class);

}