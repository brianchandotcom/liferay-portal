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

package com.liferay.document.library.web.internal.display.context;

import com.liferay.document.library.web.internal.display.context.util.MockHttpServletRequestBuilder;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.bean.BeanPropertiesImpl;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.util.PropsImpl;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Cristina González
 */
public class DLEditFileEntryTypeDisplayContextTest {

	@Before
	public void setUp() throws PortalException {
		BeanPropertiesUtil beanPropertiesUtil = new BeanPropertiesUtil();

		beanPropertiesUtil.setBeanProperties(new BeanPropertiesImpl());

		_ddm = Mockito.mock(DDM.class);

		DDMForm ddmForm = _getRandomDDMForm();

		Mockito.when(
			_ddm.getDDMForm(Mockito.anyString())
		).thenReturn(
			ddmForm
		);

		_ddmStorageLinkLocalService = Mockito.mock(
			DDMStorageLinkLocalService.class);

		Mockito.when(
			_ddmStorageLinkLocalService.getStructureStorageLinksCount(
				Mockito.anyInt())
		).thenReturn(
			3
		);

		_ddmStructureLocalService = Mockito.mock(
			DDMStructureLocalService.class);

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		_language = new LanguageImpl();

		LanguageResources languageResources = new LanguageResources();

		languageResources.setConfig(StringPool.BLANK);

		PropsUtil.setProps(new PropsImpl());
	}

	@Test
	public void testGetAvailableFields() {
		DLEditFileEntryTypeDisplayContext dlEditFileEntryTypeDisplayContext =
			new DLEditFileEntryTypeDisplayContext(
				_ddm, _ddmStorageLinkLocalService, _ddmStructureLocalService,
				null, _language, null);

		Assert.assertEquals(
			"Liferay.FormBuilder.AVAILABLE_FIELDS.DDM_STRUCTURE",
			dlEditFileEntryTypeDisplayContext.getAvailableFields());
	}

	@Test
	public void testGetAvailableLocalesString() throws Exception {
		DLEditFileEntryTypeDisplayContext dlEditFileEntryTypeDisplayContext =
			new DLEditFileEntryTypeDisplayContext(
				_ddm, _ddmStorageLinkLocalService, _ddmStructureLocalService,
				null, _language,
				new MockPortletRenderRequest(
					new MockHttpServletRequestBuilder().withAttribute(
						WebKeys.DOCUMENT_LIBRARY_DYNAMIC_DATA_MAPPING_STRUCTURE,
						_getRandomDDMStructure()
					).withParameter(
						"definition", RandomTestUtil.randomString()
					).build()));

		Assert.assertEquals(
			"[\"pt_BR\"]",
			dlEditFileEntryTypeDisplayContext.getAvailableLocalesString());
	}

	@Test
	public void testGetDefaultLanguageId() throws Exception {
		DLEditFileEntryTypeDisplayContext dlEditFileEntryTypeDisplayContext =
			new DLEditFileEntryTypeDisplayContext(
				_ddm, _ddmStorageLinkLocalService, _ddmStructureLocalService,
				null, _language,
				new MockPortletRenderRequest(
					new MockHttpServletRequestBuilder().withAttribute(
						WebKeys.DOCUMENT_LIBRARY_DYNAMIC_DATA_MAPPING_STRUCTURE,
						_getRandomDDMStructure()
					).withParameter(
						"definition", RandomTestUtil.randomString()
					).build()));

		Assert.assertEquals(
			"pt_BR", dlEditFileEntryTypeDisplayContext.getDefaultLanguageId());
	}

	@Test
	public void testGetFieldNameEditionDisabled() {
		DLEditFileEntryTypeDisplayContext dlEditFileEntryTypeDisplayContext =
			new DLEditFileEntryTypeDisplayContext(
				_ddm, _ddmStorageLinkLocalService, _ddmStructureLocalService,
				null, _language,
				new MockPortletRenderRequest(
					new MockHttpServletRequestBuilder().withAttribute(
						WebKeys.DOCUMENT_LIBRARY_DYNAMIC_DATA_MAPPING_STRUCTURE,
						_getRandomDDMStructure()
					).build()));

		Assert.assertTrue(
			dlEditFileEntryTypeDisplayContext.isFieldNameEditionDisabled());
	}

	@Test
	public void testGetFieldsJSONArrayString() {
		DLEditFileEntryTypeDisplayContext dlEditFileEntryTypeDisplayContext =
			new DLEditFileEntryTypeDisplayContext(
				_ddm, _ddmStorageLinkLocalService, _ddmStructureLocalService,
				null, _language,
				new MockPortletRenderRequest(
					new MockHttpServletRequestBuilder().withAttribute(
						WebKeys.DOCUMENT_LIBRARY_DYNAMIC_DATA_MAPPING_STRUCTURE,
						_getRandomDDMStructure()
					).build()));

		Assert.assertEquals(
			StringPool.BLANK,
			dlEditFileEntryTypeDisplayContext.getFieldsJSONArrayString());
	}

	@Test
	public void testIsChangeableDefaultLanguage() throws Exception {
		DLEditFileEntryTypeDisplayContext dlEditFileEntryTypeDisplayContext =
			new DLEditFileEntryTypeDisplayContext(
				_ddm, _ddmStorageLinkLocalService, _ddmStructureLocalService,
				null, _language,
				new MockPortletRenderRequest(
					new MockHttpServletRequestBuilder().withAttribute(
						WebKeys.DOCUMENT_LIBRARY_DYNAMIC_DATA_MAPPING_STRUCTURE,
						_getRandomDDMStructure()
					).withParameter(
						"definition", RandomTestUtil.randomString()
					).build()));

		Assert.assertEquals(
			true,
			dlEditFileEntryTypeDisplayContext.isChangeableDefaultLanguage());
	}

	private DDMForm _getRandomDDMForm() {
		DDMForm ddmForm = Mockito.mock(DDMForm.class);

		Mockito.when(
			ddmForm.getAvailableLocales()
		).thenReturn(
			Collections.singleton(LocaleUtil.BRAZIL)
		);

		Mockito.when(
			ddmForm.getDefaultLocale()
		).thenReturn(
			LocaleUtil.BRAZIL
		);

		return ddmForm;
	}

	private DDMStructure _getRandomDDMStructure() {
		DDMStructure ddmStructure = Mockito.mock(DDMStructure.class);

		long randomLong = RandomTestUtil.randomLong();

		Mockito.when(
			ddmStructure.getStructureId()
		).thenReturn(
			randomLong
		);

		Mockito.when(
			ddmStructure.getDescription()
		).thenReturn(
			"Description" + (randomLong + 1)
		);

		return ddmStructure;
	}

	private DDM _ddm;
	private DDMStorageLinkLocalService _ddmStorageLinkLocalService;
	private DDMStructureLocalService _ddmStructureLocalService;
	private Language _language;

	private class MockPortletRenderRequest
		extends MockLiferayPortletRenderRequest {

		public MockPortletRenderRequest(HttpServletRequest httpServletRequest) {
			_httpServletRequest = httpServletRequest;
		}

		@Override
		public Object getAttribute(String name) {
			return _httpServletRequest.getAttribute(name);
		}

		@Override
		public HttpServletRequest getHttpServletRequest() {
			return _httpServletRequest;
		}

		@Override
		public String getParameter(String name) {
			return _httpServletRequest.getParameter(name);
		}

		private final HttpServletRequest _httpServletRequest;

	}

}