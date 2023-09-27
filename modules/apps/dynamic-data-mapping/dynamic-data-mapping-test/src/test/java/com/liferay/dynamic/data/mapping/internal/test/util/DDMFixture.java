/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.test.util;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portal.bean.BeanPropertiesImpl;
import com.liferay.portal.kernel.bean.BeanProperties;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.util.ResourceBundle;

import org.mockito.Mockito;

/**
 * @author André de Oliveira
 */
public class DDMFixture {

	public void setUp() throws Exception {
		_setUpBeanPropertiesUtil();
		_setUpDDMStructureLocalServiceUtil();
		_setUpLanguageUtil();

		ClassLoader classLoader = Mockito.mock(ClassLoader.class);

		_setUpPortalClassLoaderUtil(classLoader);

		_setUpResourceBundleUtil();
	}

	public void tearDown() {
		_tearDownBeanPropertiesUtil();
		_tearDownLanguage();
		_tearDownPortalClassLoaderUtil();
	}

	public void whenDDMStructureLocalServiceFetchStructure(
		DDMStructure ddmStructure) {

		Mockito.doReturn(
			ddmStructure
		).when(
			_ddmStructureLocalService
		).fetchStructure(
			ddmStructure.getStructureId()
		);
	}

	private void _setUpBeanPropertiesUtil() {
		_beanProperties = BeanPropertiesUtil.getBeanProperties();

		BeanPropertiesUtil beanPropertiesUtil = new BeanPropertiesUtil();

		beanPropertiesUtil.setBeanProperties(new BeanPropertiesImpl());
	}

	private void _setUpDDMStructureLocalServiceUtil() {
		ReflectionTestUtil.setFieldValue(
			DDMStructureLocalServiceUtil.class, "_service",
			_ddmStructureLocalService);
	}

	private void _setUpLanguageUtil() {
		_language = LanguageUtil.getLanguage();

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(Mockito.mock(Language.class));
	}

	private void _setUpPortalClassLoaderUtil(ClassLoader classLoader) {
		_classLoader = PortalClassLoaderUtil.getClassLoader();

		PortalClassLoaderUtil.setClassLoader(classLoader);
	}

	private void _setUpResourceBundleUtil() {
		ResourceBundle resourceBundle = Mockito.mock(ResourceBundle.class);

		ResourceBundleLoader resourceBundleLoader = Mockito.mock(
			ResourceBundleLoader.class);

		ResourceBundleLoaderUtil.setPortalResourceBundleLoader(
			resourceBundleLoader);

		Mockito.when(
			resourceBundleLoader.loadResourceBundle(LocaleUtil.BRAZIL)
		).thenReturn(
			resourceBundle
		);

		Mockito.when(
			resourceBundleLoader.loadResourceBundle(LocaleUtil.US)
		).thenReturn(
			resourceBundle
		);
	}

	private void _tearDownBeanPropertiesUtil() {
		BeanPropertiesUtil beanPropertiesUtil = new BeanPropertiesUtil();

		beanPropertiesUtil.setBeanProperties(_beanProperties);
	}

	private void _tearDownLanguage() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);
	}

	private void _tearDownPortalClassLoaderUtil() {
		PortalClassLoaderUtil.setClassLoader(_classLoader);
	}

	private BeanProperties _beanProperties;
	private ClassLoader _classLoader;
	private final DDMStructureLocalService _ddmStructureLocalService =
		Mockito.mock(DDMStructureLocalService.class);
	private Language _language;

}