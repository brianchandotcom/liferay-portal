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

package com.liferay.portal.vulcan.internal.jaxrs.writer.interceptor;

import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.vulcan.internal.fields.nested.NestedFieldsContext;
import com.liferay.portal.vulcan.internal.fields.nested.NestedFieldsContextThreadLocal;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.PaginationContextProvider;
import com.liferay.portal.vulcan.internal.util.dto.Product;
import com.liferay.portal.vulcan.internal.util.dto.ProductOption;
import com.liferay.portal.vulcan.internal.util.dto.Sku;
import com.liferay.portal.vulcan.internal.util.request.MockHttpServletRequest;
import com.liferay.portal.vulcan.internal.util.resource.ProductResourceImpl;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.ext.WriterInterceptorContext;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.BundleContext;

/**
 * @author Ivica Cardic
 */
public class NestedFieldsWriterInterceptorTest {

	@Before
	public void setUp() throws Exception {
		PropsUtil.setProps(Mockito.mock(Props.class));

		_context = Mockito.mock(WriterInterceptorContext.class);

		_product = _getProductDTO();

		Mockito.when(
			_context.getEntity()
		).thenReturn(
			_product
		);

		BundleContext bundleContext = Mockito.mock(BundleContext.class);

		_nestedFieldsWriterInterceptor = Mockito.spy(
			new NestedFieldsWriterInterceptor(bundleContext));

		Mockito.doReturn(
			Arrays.asList(
				new PaginationContextProvider(),
				new MockThemeDisplayContextProvider())
		).when(
			_nestedFieldsWriterInterceptor
		).getContextProviders();

		_productResourceImpl = new ProductResourceImpl();

		Mockito.doReturn(
			Collections.singletonList(_productResourceImpl)
		).when(
			_nestedFieldsWriterInterceptor
		).getResources();
	}

	@Test
	public void testGetNestedFields() throws Exception {
		Mockito.doReturn(
			new MockHttpServletRequest()
		).when(
			_nestedFieldsWriterInterceptor
		).getHttpServletRequest(
			Mockito.any(Message.class)
		);

		NestedFieldsContextThreadLocal.setNestedFieldsContext(
			new NestedFieldsContext(
				Arrays.asList("skus", "productOptions"), new MessageImpl(),
				_getPathParameters(), new MultivaluedHashMap<>()));

		_nestedFieldsWriterInterceptor.aroundWriteTo(_context);

		Collection<Sku> skus = _product.getSkus();

		Assert.assertEquals(skus.toString(), 4, skus.size());

		Collection<ProductOption> productOptionsDTOs =
			_product.getProductOptions();

		Assert.assertEquals(
			productOptionsDTOs.toString(), 3, productOptionsDTOs.size());
	}

	@Test
	public void testGetNestedFieldsEmpty() throws IOException {
		NestedFieldsContextThreadLocal.setNestedFieldsContext(
			new NestedFieldsContext(
				Collections.emptyList(), new MessageImpl(),
				_getPathParameters(), new MultivaluedHashMap<>()));

		_nestedFieldsWriterInterceptor.aroundWriteTo(_context);

		Collection<Sku> skus = _product.getSkus();

		Assert.assertEquals(skus.toString(), 0, skus.size());

		NestedFieldsContextThreadLocal.setNestedFieldsContext(
			new NestedFieldsContext(
				Collections.singletonList("nonexistent"), new MessageImpl(),
				_getPathParameters(), new MultivaluedHashMap<>()));

		_nestedFieldsWriterInterceptor.aroundWriteTo(_context);

		skus = _product.getSkus();

		Assert.assertEquals(skus.toString(), 0, skus.size());
	}

	@Test
	public void testGetNestedFieldsWithPagination() throws Exception {
		Mockito.doReturn(
			new MockHttpServletRequest(
				"skus", "page", String.valueOf(1), "pageSize",
				String.valueOf(2))
		).when(
			_nestedFieldsWriterInterceptor
		).getHttpServletRequest(
			Mockito.any(Message.class)
		);

		NestedFieldsContextThreadLocal.setNestedFieldsContext(
			new NestedFieldsContext(
				Collections.singletonList("skus"), new MessageImpl(),
				_getPathParameters(), new MultivaluedHashMap<>()));

		_nestedFieldsWriterInterceptor.aroundWriteTo(_context);

		Collection<Sku> skus = _product.getSkus();

		Assert.assertEquals(skus.toString(), 2, skus.size());
	}

	@Test
	public void testGetNestedFieldsWithQueryParameter() throws IOException {
		Mockito.doReturn(
			new MockHttpServletRequest("productOptions")
		).when(
			_nestedFieldsWriterInterceptor
		).getHttpServletRequest(
			Mockito.any(Message.class)
		);

		MultivaluedHashMap<String, String> queryParameters =
			new MultivaluedHashMap<String, String>() {
				{
					putSingle(
						"productOptions.createDate",
						"2019-02-19T08:03:11.763Z");
					putSingle("productOptions.name", "test2");
				}
			};

		NestedFieldsContextThreadLocal.setNestedFieldsContext(
			new NestedFieldsContext(
				Collections.singletonList("productOptions"), new MessageImpl(),
				_getPathParameters(), queryParameters));

		_nestedFieldsWriterInterceptor.aroundWriteTo(_context);

		List<ProductOption> productOptions =
			(List<ProductOption>)_product.getProductOptions();

		Assert.assertEquals(
			productOptions.toString(), 1, productOptions.size());

		ProductOption productOption = productOptions.get(0);

		Assert.assertEquals("test2", productOption.getName());
	}

	@Test
	public void testInjectContextResourceFields() throws Exception {
		Mockito.doReturn(
			new MockHttpServletRequest("skus")
		).when(
			_nestedFieldsWriterInterceptor
		).getHttpServletRequest(
			Mockito.any(Message.class)
		);

		NestedFieldsContextThreadLocal.setNestedFieldsContext(
			new NestedFieldsContext(
				Arrays.asList("skus", "productOptions"), new MessageImpl(),
				_getPathParameters(), new MultivaluedHashMap<>()));

		Assert.assertNull(_productResourceImpl.themeDisplay);

		_nestedFieldsWriterInterceptor.aroundWriteTo(_context);

		Assert.assertNotNull(_productResourceImpl.themeDisplay);
	}

	private MultivaluedHashMap<String, String> _getPathParameters() {
		return new MultivaluedHashMap<String, String>() {
			{
				putSingle("id", "1");
			}
		};
	}

	private Product _getProductDTO() {
		Product product = new Product();

		product.setId(1L);

		return product;
	}

	private WriterInterceptorContext _context;
	private NestedFieldsWriterInterceptor _nestedFieldsWriterInterceptor;
	private Product _product;
	private ProductResourceImpl _productResourceImpl;

	private class MockThemeDisplayContextProvider
		implements ContextProvider<ThemeDisplay> {

		@Override
		public ThemeDisplay createContext(Message message) {
			return new ThemeDisplay();
		}

	}

}