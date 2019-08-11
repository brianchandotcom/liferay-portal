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
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.internal.fields.NestedFieldsContext;
import com.liferay.portal.vulcan.internal.fields.NestedFieldsContextThreadLocal;
import com.liferay.portal.vulcan.internal.fields.servlet.NestedFieldsHttpServletRequestWrapperTest;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.PaginationContextProvider;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
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
		_nestedFieldsWriterInterceptor = Mockito.spy(
			new NestedFieldsWriterInterceptor(
				Mockito.mock(BundleContext.class)));

		Mockito.doReturn(
			Arrays.asList(
				new PaginationContextProvider(),
				new ThemeDisplayContextProvider())
		).when(
			_nestedFieldsWriterInterceptor
		).getContextProviders();

		_productResourceImpl = new ProductResourceImpl();

		Mockito.doReturn(
			Collections.singletonList(_productResourceImpl)
		).when(
			_nestedFieldsWriterInterceptor
		).getResources();

		_writerInterceptorContext = Mockito.mock(
			WriterInterceptorContext.class);

		_product = new Product();

		_product.setId(1L);

		Mockito.when(
			_writerInterceptorContext.getEntity()
		).thenReturn(
			_product
		);
	}

	@Test
	public void testGetNestedFields() throws Exception {
		Mockito.doReturn(
			new NestedFieldsHttpServletRequestWrapperTest.
				MockHttpServletRequest()
		).when(
			_nestedFieldsWriterInterceptor
		).getHttpServletRequest(
			Mockito.any(Message.class)
		);

		NestedFieldsContextThreadLocal.setNestedFieldsContext(
			new NestedFieldsContext(
				Arrays.asList("productOptions", "skus"), new MessageImpl(),
				_getPathParameters(), new MultivaluedHashMap<>()));

		_nestedFieldsWriterInterceptor.aroundWriteTo(_writerInterceptorContext);

		Collection<Sku> skus = _product.getSkus();

		Assert.assertEquals(skus.toString(), 4, skus.size());

		Collection<ProductOption> productOptionsDTOs =
			_product.getProductOptions();

		Assert.assertEquals(
			productOptionsDTOs.toString(), 3, productOptionsDTOs.size());
	}

	@Test
	public void testGetNestedFieldsWithNonexistendFieldName() throws Exception {
		NestedFieldsContextThreadLocal.setNestedFieldsContext(
			new NestedFieldsContext(
				Collections.emptyList(), new MessageImpl(),
				_getPathParameters(), new MultivaluedHashMap<>()));

		_nestedFieldsWriterInterceptor.aroundWriteTo(_writerInterceptorContext);

		Collection<Sku> skus = _product.getSkus();

		Assert.assertEquals(skus.toString(), 0, skus.size());

		NestedFieldsContextThreadLocal.setNestedFieldsContext(
			new NestedFieldsContext(
				Collections.singletonList("nonexistent"), new MessageImpl(),
				_getPathParameters(), new MultivaluedHashMap<>()));

		_nestedFieldsWriterInterceptor.aroundWriteTo(_writerInterceptorContext);

		skus = _product.getSkus();

		Assert.assertEquals(skus.toString(), 0, skus.size());
	}

	@Test
	public void testGetNestedFieldsWithPagination() throws Exception {
		Mockito.doReturn(
			new NestedFieldsHttpServletRequestWrapperTest.
				MockHttpServletRequest(
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

		_nestedFieldsWriterInterceptor.aroundWriteTo(_writerInterceptorContext);

		Collection<Sku> skus = _product.getSkus();

		Assert.assertEquals(skus.toString(), 2, skus.size());
	}

	@Test
	public void testGetNestedFieldsWithQueryParameter() throws IOException {
		Mockito.doReturn(
			new NestedFieldsHttpServletRequestWrapperTest.
				MockHttpServletRequest("productOptions")
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

		_nestedFieldsWriterInterceptor.aroundWriteTo(_writerInterceptorContext);

		List<ProductOption> productOptions =
			(List<ProductOption>)_product.getProductOptions();

		Assert.assertEquals(
			productOptions.toString(), 1, productOptions.size());

		ProductOption productOption = productOptions.get(0);

		Assert.assertEquals("test2", productOption.getName());
	}

	@Test
	public void testInjectResourceContexts() throws Exception {
		Mockito.doReturn(
			new NestedFieldsHttpServletRequestWrapperTest.
				MockHttpServletRequest("skus")
		).when(
			_nestedFieldsWriterInterceptor
		).getHttpServletRequest(
			Mockito.any(Message.class)
		);

		NestedFieldsContextThreadLocal.setNestedFieldsContext(
			new NestedFieldsContext(
				Arrays.asList("productOptions", "skus"), new MessageImpl(),
				_getPathParameters(), new MultivaluedHashMap<>()));

		Assert.assertNull(_productResourceImpl.themeDisplay);

		_nestedFieldsWriterInterceptor.aroundWriteTo(_writerInterceptorContext);

		Assert.assertNotNull(_productResourceImpl.themeDisplay);
	}

	private MultivaluedHashMap<String, String> _getPathParameters() {
		return new MultivaluedHashMap<String, String>() {
			{
				putSingle("id", "1");
			}
		};
	}

	private NestedFieldsWriterInterceptor _nestedFieldsWriterInterceptor;
	private Product _product;
	private ProductResourceImpl _productResourceImpl;
	private WriterInterceptorContext _writerInterceptorContext;

	private class BaseProductResourceImpl implements ProductResource {

		@GET
		@Path("/{id}/productOption")
		@Produces("application/*")
		public List<ProductOption> getProductOptions(
			@NotNull @PathParam("id") Long id,
			@QueryParam("name") String name) {

			return Collections.emptyList();
		}

		@GET
		@Path("/{id}/sku")
		@Produces("application/*")
		public Page<Sku> getSkus(
			@NotNull @PathParam("id") String id,
			@Context @NotNull Pagination pagination) {

			return Page.of(Collections.emptyList());
		}

	}

	private class Product {

		public Long getId() {
			return _id;
		}

		public Collection<ProductOption> getProductOptions() {
			return _productOptions;
		}

		public Collection<Sku> getSkus() {
			return _skus;
		}

		public void setId(Long id) {
			_id = id;
		}

		public void setProductOptions(
			Collection<ProductOption> productOptions) {

			_productOptions = productOptions;
		}

		public void setSkus(Collection<Sku> skus) {
			_skus = skus;
		}

		private Long _id;
		private Collection<ProductOption> _productOptions =
			Collections.emptyList();
		private Collection<Sku> _skus = Collections.emptyList();

	}

	private class ProductOption {

		public Long getId() {
			return _id;
		}

		public String getName() {
			return _name;
		}

		public void setId(Long id) {
			_id = id;
		}

		public void setName(String name) {
			_name = name;
		}

		private Long _id;
		private String _name;

	}

	private interface ProductResource {

		public List<ProductOption> getProductOptions(Long id, String name);

		public Page<Sku> getSkus(String id, Pagination pagination);

	}

	private class ProductResourceImpl extends BaseProductResourceImpl {

		@NestedField("productOptions")
		@Override
		public List<ProductOption> getProductOptions(Long id, String name) {
			if (id != 1) {
				return Collections.emptyList();
			}

			List<ProductOption> productOptions = Arrays.asList(
				_toProductOption(1L, "test1"), _toProductOption(2L, "test2"),
				_toProductOption(3L, "test3"));

			if (name != null) {
				Stream<ProductOption> productOptionDTOStream =
					productOptions.stream();

				productOptions = productOptionDTOStream.filter(
					productOptionDTO -> Objects.equals(
						productOptionDTO.getName(), name)
				).collect(
					Collectors.toList()
				);
			}

			return productOptions;
		}

		@NestedField("skus")
		@Override
		public Page<Sku> getSkus(String id, Pagination pagination) {
			if (!Objects.equals(id, "1")) {
				return Page.of(Collections.emptyList());
			}

			List<Sku> skus = Arrays.asList(
				_toSku(1L), _toSku(2L), _toSku(3L), _toSku(4L));

			skus = skus.subList(
				pagination.getStartPosition(),
				Math.min(pagination.getEndPosition(), skus.size()));

			return Page.of(skus);
		}

		@Context
		public ThemeDisplay themeDisplay;

		private ProductOption _toProductOption(long id, String name) {
			ProductOption productOption = new ProductOption();

			productOption.setId(id);
			productOption.setName(name);

			return productOption;
		}

		private Sku _toSku(long id) {
			Sku sku = new Sku();

			sku.setId(id);

			return sku;
		}

	}

	private class Sku {

		public Long getId() {
			return _id;
		}

		public void setId(Long id) {
			_id = id;
		}

		private Long _id;

	}

	private class ThemeDisplayContextProvider
		implements ContextProvider<ThemeDisplay> {

		@Override
		public ThemeDisplay createContext(Message message) {
			return new ThemeDisplay();
		}

	}

}