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
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.bean.BeanPropertiesImpl;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsImpl;

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
	public void setUp() {
		BeanPropertiesUtil beanPropertiesUtil = new BeanPropertiesUtil();

		beanPropertiesUtil.setBeanProperties(new BeanPropertiesImpl());

		_ddm = Mockito.mock(DDM.class);

		_ddmStructureLocalService = Mockito.mock(
			DDMStructureLocalService.class);

		PropsUtil.setProps(new PropsImpl());
	}

	@Test
	public void testGetAvailableFields() {
		DLEditFileEntryTypeDisplayContext dlEditFileEntryTypeDisplayContext =
			new DLEditFileEntryTypeDisplayContext(
				_ddm, _ddmStructureLocalService, null, null);

		Assert.assertEquals(
			"Liferay.FormBuilder.AVAILABLE_FIELDS.DDM_STRUCTURE",
			dlEditFileEntryTypeDisplayContext.getAvailableFields());
	}

	@Test
	public void testGetFieldsJSONArrayString() {
		DLEditFileEntryTypeDisplayContext dlEditFileEntryTypeDisplayContext =
			new DLEditFileEntryTypeDisplayContext(
				_ddm, _ddmStructureLocalService,
				new MockPortletRenderRequest(
					new MockHttpServletRequestBuilder().withAttribute(
						WebKeys.DOCUMENT_LIBRARY_DYNAMIC_DATA_MAPPING_STRUCTURE,
						_getRandomDDMStructure()
					).build()),
				null);

		Assert.assertEquals(
			StringPool.BLANK,
			dlEditFileEntryTypeDisplayContext.getFieldsJSONArrayString());
	}

	@Test
	public void testGetStructure() {
		DLEditFileEntryTypeDisplayContext dlEditFileEntryTypeDisplayContext =
			new DLEditFileEntryTypeDisplayContext(
				_ddm, _ddmStructureLocalService,
				new MockPortletRenderRequest(
					new MockHttpServletRequestBuilder().withAttribute(
						WebKeys.DOCUMENT_LIBRARY_DYNAMIC_DATA_MAPPING_STRUCTURE,
						_getRandomDDMStructure()
					).build()),
				null);

		DDMStructure ddmStructure =
			dlEditFileEntryTypeDisplayContext.getDDMStructure();

		Assert.assertNotNull(ddmStructure);
		Assert.assertNotNull(ddmStructure.getStructureId());
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
	private DDMStructureLocalService _ddmStructureLocalService;

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