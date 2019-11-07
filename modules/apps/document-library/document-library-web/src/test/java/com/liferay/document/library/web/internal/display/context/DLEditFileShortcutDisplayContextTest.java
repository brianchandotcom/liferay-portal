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

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.bean.BeanPropertiesImpl;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 */
public class DLEditFileShortcutDisplayContextTest {

	@Before
	public void setUp() {
		BeanPropertiesUtil beanPropertiesUtil = new BeanPropertiesUtil();

		beanPropertiesUtil.setBeanProperties(new BeanPropertiesImpl());

		_dlAppService = Mockito.mock(DLAppService.class);

		_itemSelector = Mockito.mock(ItemSelector.class);

		PropsUtil.setProps(new PropsImpl());
	}

	@Test
	public void testGetFieldsWithoutParametersAndWithAttributes() {
		FileShortcut fileShortcut = _addRandomFileShortcut();

		DLEditFileShortcutDisplayContext dlEditFileShortcutDisplayContext =
			_getDLEditFileShortcutDisplayContext(
				new MockHttpServletRequestBuilder().withAttribute(
					WebKeys.DOCUMENT_LIBRARY_FILE_SHORTCUT, fileShortcut
				).build());

		Assert.assertEquals(
			fileShortcut.getFileShortcutId(),
			dlEditFileShortcutDisplayContext.getFileShortcutId());
		Assert.assertEquals(
			fileShortcut.getFolderId(),
			dlEditFileShortcutDisplayContext.getFolderId());
		Assert.assertEquals(
			fileShortcut.getRepositoryId(),
			dlEditFileShortcutDisplayContext.getRepositoryId());
		Assert.assertEquals(
			fileShortcut.getToFileEntryId(),
			dlEditFileShortcutDisplayContext.getToFileEntryId());
	}

	@Test
	public void testGetFieldsWithoutParametersAndWithoutAttributes() {
		DLEditFileShortcutDisplayContext dlEditFileShortcutDisplayContext =
			_getDLEditFileShortcutDisplayContext(new MockHttpServletRequest());

		Assert.assertEquals(
			0, dlEditFileShortcutDisplayContext.getFileShortcutId());
		Assert.assertEquals(0, dlEditFileShortcutDisplayContext.getFolderId());
		Assert.assertEquals(
			0, dlEditFileShortcutDisplayContext.getRepositoryId());
		Assert.assertEquals(
			0, dlEditFileShortcutDisplayContext.getToFileEntryId());
	}

	@Test
	public void testGetFieldsWithParametersAndWithAttributes() {
		FileShortcut fileShortcut = _addRandomFileShortcut();

		DLEditFileShortcutDisplayContext dlEditFileShortcutDisplayContext =
			_getDLEditFileShortcutDisplayContext(
				new MockHttpServletRequestBuilder().withAttribute(
					WebKeys.DOCUMENT_LIBRARY_FILE_SHORTCUT, fileShortcut
				).withParameter(
					"fileShortcutId", "12356"
				).withParameter(
					"folderId", "23567"
				).withParameter(
					"repositoryId", "35678"
				).withParameter(
					"toFileEntryId", "56789"
				).build());

		Assert.assertEquals(
			12356L, dlEditFileShortcutDisplayContext.getFileShortcutId());
		Assert.assertEquals(
			23567L, dlEditFileShortcutDisplayContext.getFolderId());
		Assert.assertEquals(
			35678L, dlEditFileShortcutDisplayContext.getRepositoryId());
		Assert.assertEquals(
			56789L, dlEditFileShortcutDisplayContext.getToFileEntryId());
	}

	@Test
	public void testGetFieldsWithParametersAndWithoutAttributes() {
		DLEditFileShortcutDisplayContext dlEditFileShortcutDisplayContext =
			_getDLEditFileShortcutDisplayContext(
				new MockHttpServletRequestBuilder().withParameter(
					"fileShortcutId", "12356"
				).withParameter(
					"folderId", "23567"
				).withParameter(
					"repositoryId", "35678"
				).withParameter(
					"toFileEntryId", "56789"
				).build());

		Assert.assertEquals(
			12356L, dlEditFileShortcutDisplayContext.getFileShortcutId());
		Assert.assertEquals(
			23567L, dlEditFileShortcutDisplayContext.getFolderId());
		Assert.assertEquals(
			35678L, dlEditFileShortcutDisplayContext.getRepositoryId());
		Assert.assertEquals(
			56789L, dlEditFileShortcutDisplayContext.getToFileEntryId());
	}

	public static class MockHttpServletRequestBuilder {

		public MockHttpServletRequestBuilder() {
		}

		public MockHttpServletRequest build() {
			MockHttpServletRequest mockHttpServletRequest =
				new MockHttpServletRequest();

			Set<Map.Entry<String, Object>> entries = _attributes.entrySet();

			entries.forEach(
				entry -> mockHttpServletRequest.setAttribute(
					entry.getKey(), entry.getValue()));

			mockHttpServletRequest.setParameters(_parameters);

			return mockHttpServletRequest;
		}

		public MockHttpServletRequestBuilder withAttribute(
			String key, Object value) {

			_attributes.put(key, value);

			return this;
		}

		public MockHttpServletRequestBuilder withParameter(
			String key, String value) {

			_parameters.put(key, value);

			return this;
		}

		private HashMap<String, Object> _attributes = new HashMap<>();
		private HashMap<String, String> _parameters = new HashMap<>();

	}

	private FileShortcut _addRandomFileShortcut() {
		FileShortcut fileShortcut = Mockito.mock(FileShortcut.class);

		long randomLong = RandomTestUtil.randomLong();

		Mockito.when(
			fileShortcut.getFileShortcutId()
		).thenReturn(
			randomLong
		);

		Mockito.when(
			fileShortcut.getFolderId()
		).thenReturn(
			randomLong + 1
		);

		Mockito.when(
			fileShortcut.getRepositoryId()
		).thenReturn(
			randomLong + 2
		);

		Mockito.when(
			fileShortcut.getToFileEntryId()
		).thenReturn(
			randomLong + 3
		);

		return fileShortcut;
	}

	private DLEditFileShortcutDisplayContext
		_getDLEditFileShortcutDisplayContext(
			HttpServletRequest httpServletRequest) {

		return new DLEditFileShortcutDisplayContext(
			_dlAppService, _itemSelector,
			new MockPortletRenderRequest(httpServletRequest),
			new MockLiferayPortletRenderResponse());
	}

	private DLAppService _dlAppService;
	private ItemSelector _itemSelector;

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