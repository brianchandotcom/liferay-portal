/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.jaxrs.writer.interceptor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.internal.jaxrs.container.request.filter.test.TransactionContainerRequestFilterTest;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Context;

import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Alejandro Tardín
 * @author Ivica Cardic
 */
@RunWith(Arquillian.class)
public class NestedFieldsWriterInterceptorTest {

	@ClassRule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			NestedFieldsWriterInterceptorTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceRegistrations = Arrays.asList(
			bundleContext.registerService(
				Application.class,
				new TransactionContainerRequestFilterTest.TestApplication(),
				HashMapDictionaryBuilder.<String, Object>put(
					"liferay.auth.verifier", true
				).put(
					"liferay.oauth2", false
				).put(
					"osgi.jaxrs.application.base", "/test-vulcan"
				).put(
					"osgi.jaxrs.extension.select",
					"(osgi.jaxrs.name=Liferay.Vulcan)"
				).put(
					"osgi.jaxrs.name", "Test.Vulcan"
				).build()),
			bundleContext.registerService(
				ProductResource_v1_0.class, new ProductResource_v1_0_Impl(),
				HashMapDictionaryBuilder.<String, Object>put(
					"api.version", "v1.0"
				).put(
					"nested.field.support", "true"
				).put(
					"osgi.jaxrs.application.select",
					"(osgi.jaxrs.name=Test.Vulcan)"
				).put(
					"osgi.jaxrs.resource", "true"
				).build()),
			bundleContext.registerService(
				ProductResource_v2_0.class, new ProductResource_v2_0_Impl(),
				HashMapDictionaryBuilder.<String, Object>put(
					"api.version", "v2.0"
				).put(
					"nested.field.support", "true"
				).put(
					"osgi.jaxrs.application.select",
					"(osgi.jaxrs.name=Test.Vulcan)"
				).put(
					"osgi.jaxrs.resource", "true"
				).build()));
	}

	@After
	public void tearDown() {
		_serviceRegistrations.forEach(ServiceRegistration::unregister);
	}

	@Test
	public void testGetNestedFields() throws Exception {
		_test(
			JSONUtil.putAll(
				JSONUtil.put(
					"id", 1
				).put(
					"productOptions",
					JSONUtil.putAll(
						JSONUtil.put(
							"id", 10
						).put(
							"name", "test1"
						),
						JSONUtil.put(
							"id", 20
						).put(
							"name", "test2"
						),
						JSONUtil.put(
							"id", 30
						).put(
							"name", "test3"
						))
				).put(
					"skus",
					JSONUtil.putAll(
						JSONUtil.put("id", 1), JSONUtil.put("id", 2),
						JSONUtil.put("id", 3), JSONUtil.put("id", 4))
				),
				JSONUtil.put(
					"id", 2
				).put(
					"productOptions", JSONUtil.putAll()
				).put(
					"skus", JSONUtil.putAll()
				)),
			HashMapBuilder.put(
				"nestedFields", "productOptions,skus"
			).build(),
			"v1.0");
	}

	@Test
	public void testGetNestedFieldsWithDeeplyNestedFields() throws Exception {
		_test(
			JSONUtil.putAll(
				JSONUtil.put(
					"id", 1
				).put(
					"productOptions",
					JSONUtil.putAll(
						JSONUtil.put(
							"id", 10
						).put(
							"name", "test1"
						).put(
							"productOptionValues",
							JSONUtil.putAll(
								JSONUtil.put("id", 100),
								JSONUtil.put("id", 200),
								JSONUtil.put("id", 300))
						),
						JSONUtil.put(
							"id", 20
						).put(
							"name", "test2"
						).put(
							"productOptionValues",
							JSONUtil.putAll(
								JSONUtil.put("id", 400),
								JSONUtil.put("id", 500))
						),
						JSONUtil.put(
							"id", 30
						).put(
							"name", "test3"
						).put(
							"productOptionValues", JSONUtil.putAll()
						))
				),
				JSONUtil.put(
					"id", 2
				).put(
					"productOptions", JSONUtil.putAll()
				)),
			HashMapBuilder.put(
				"nestedFields",
				"productOptions,productOptions.productOptionValues"
			).build(),
			"v1.0");
	}

	@Test
	public void testGetNestedFieldsWithNestedFieldId() throws Exception {
		_test(
			JSONUtil.putAll(
				JSONUtil.put(
					"categories",
					JSONUtil.putAll(
						JSONUtil.put("id", 1), JSONUtil.put("id", 2),
						JSONUtil.put("id", 3))
				).put(
					"id", 1
				),
				JSONUtil.put(
					"categories", JSONUtil.putAll()
				).put(
					"id", 2
				)),
			HashMapBuilder.put(
				"nestedFields", "categories"
			).build(),
			"v1.0");
		_test(
			JSONUtil.putAll(
				JSONUtil.put(
					"categories",
					JSONUtil.putAll(
						JSONUtil.put("id", 1), JSONUtil.put("id", 2))
				).put(
					"id", 1
				),
				JSONUtil.put(
					"categories", JSONUtil.putAll()
				).put(
					"id", 2
				)),
			HashMapBuilder.put(
				"nestedFields", "categories"
			).build(),
			"v2.0");
	}

	@Test
	public void testGetNestedFieldsWithNonexistentFieldName() throws Exception {
		_test(
			JSONUtil.putAll(JSONUtil.put("id", 1), JSONUtil.put("id", 2)),
			Collections.emptyMap(), "v1.0");
		_test(
			JSONUtil.putAll(JSONUtil.put("id", 1), JSONUtil.put("id", 2)),
			HashMapBuilder.put(
				"nestedFields", "nonexistentField"
			).build(),
			"v1.0");
	}

	@Test
	public void testGetNestedFieldsWithoutOverridingMethod() throws Exception {
		_test(
			JSONUtil.putAll(
				JSONUtil.put(
					"externalCode", "codigoExterno"
				).put(
					"id", 1
				),
				JSONUtil.put("id", 2)),
			HashMapBuilder.put(
				"externalCode.AcceptLanguage", "es_ES"
			).put(
				"nestedFields", "externalCode"
			).build(),
			"v1.0");
	}

	@Test
	public void testGetNestedFieldsWithPagination() throws Exception {
		_test(
			JSONUtil.putAll(
				JSONUtil.put(
					"id", 1
				).put(
					"skus",
					JSONUtil.putAll(
						JSONUtil.put("id", 1), JSONUtil.put("id", 2))
				),
				JSONUtil.put(
					"id", 2
				).put(
					"skus", JSONUtil.putAll()
				)),
			HashMapBuilder.put(
				"nestedFields", "skus"
			).put(
				"skus.page", "1"
			).put(
				"skus.pageSize", "2"
			).build(),
			"v1.0");
	}

	@Test
	public void testGetNestedFieldsWithQueryParameter() throws Exception {
		_test(
			JSONUtil.putAll(
				JSONUtil.put(
					"id", 1
				).put(
					"productOptions",
					JSONUtil.putAll(
						JSONUtil.put(
							"id", 20
						).put(
							"name", "test2"
						))
				),
				JSONUtil.put(
					"id", 2
				).put(
					"productOptions", JSONUtil.putAll()
				)),
			HashMapBuilder.put(
				"nestedFields", "productOptions"
			).put(
				"productOptions.name", "test2"
			).build(),
			"v1.0");
	}

	@Test
	public void testGetNestedFieldsWithResourceVersioning() throws Exception {
		_test(
			JSONUtil.putAll(
				JSONUtil.put(
					"id", 1
				).put(
					"skus",
					JSONUtil.putAll(
						JSONUtil.put("id", 1), JSONUtil.put("id", 2),
						JSONUtil.put("id", 3), JSONUtil.put("id", 4),
						JSONUtil.put("id", 5), JSONUtil.put("id", 6))
				),
				JSONUtil.put("id", 2)),
			HashMapBuilder.put(
				"nestedFields", "skus"
			).build(),
			"v2.0");
	}

	@Path("/v1.0")
	public static class BaseProductResource_v1_0_Impl
		implements ProductResource_v1_0 {

		@GET
		@Path("/products/{id}/productOptions")
		@Produces("application/*")
		public List<ProductOption> getProductOptions(
			@PathParam("id") Long id, @QueryParam("name") String name) {

			return Collections.emptyList();
		}

		@GET
		@Path("/productOptions/{id}/productOptionValues")
		@Produces("application/*")
		public List<ProductOptionValue> getProductOptionValues(
			@PathParam("id") Long id) {

			return Collections.emptyList();
		}

		@GET
		@Path("/products")
		@Produces("application/json")
		public List<Product> getProducts(
			@QueryParam("inlineInitialization") boolean inlineInitialization) {

			return Collections.emptyList();
		}

		@GET
		@Path("/products/{id}/skus")
		@Produces("application/*")
		public Page<Sku> getSkus(
			@PathParam("id") Long id, @Context Pagination pagination) {

			return Page.of(Collections.emptyList());
		}

	}

	@Path("/v2.0")
	public static class BaseProductResource_v2_0_Impl
		implements ProductResource_v2_0 {

		@GET
		@Path("/products/{productExternalCode}/categories")
		@Produces("application/json")
		public List<Category> getCategories(
			@PathParam("productExternalCode") String productExternalCode) {

			return Collections.emptyList();
		}

		@GET
		@Path("/products/{id}/productOptions")
		@Produces("application/*")
		public List<ProductOption> getProductOptions(
			@PathParam("id") Long id, @QueryParam("name") String name) {

			return Collections.emptyList();
		}

		@GET
		@Path("/products")
		@Produces("application/json")
		public List<Product> getProducts(
			@QueryParam("inlineInitialization") boolean inlineInitialization) {

			return Collections.emptyList();
		}

		@GET
		@Path("/products/{id}/skus")
		@Produces("application/*")
		public Page<Sku> getSkus(
			@PathParam("id") Long id, @Context Pagination pagination) {

			return Page.of(Collections.emptyList());
		}

	}

	public static class Category {

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

	public static class Product {

		public Category[] getCategories() {
			return _categories;
		}

		public String getExternalCode() {
			return _externalCode;
		}

		public Long getId() {
			return _id;
		}

		public ProductOption[] getProductOptions() {
			return _productOptions;
		}

		public Sku[] getSkus() {
			return _skus;
		}

		public void setExternalCode(String externalCode) {
			_externalCode = externalCode;
		}

		public void setId(Long id) {
			_id = id;
		}

		private Category[] _categories;
		private String _externalCode;
		private Long _id;
		private ProductOption[] _productOptions;
		private Sku[] _skus;

	}

	public static class ProductOption {

		public Long getId() {
			return _id;
		}

		public String getName() {
			return _name;
		}

		public ProductOptionValue[] getProductOptionValues() {
			return _productOptionValues;
		}

		public void setId(Long id) {
			_id = id;
		}

		public void setName(String name) {
			_name = name;
		}

		private Long _id;
		private String _name;
		private ProductOptionValue[] _productOptionValues;

	}

	public static class ProductOptionValue {

		public Long getId() {
			return _id;
		}

		public void setId(Long id) {
			_id = id;
		}

		private Long _id;

	}

	public static class ProductResource_v1_0_Impl
		extends BaseProductResource_v1_0_Impl {

		@NestedField("categories")
		public List<Category> getCategories(
			@NestedFieldId("externalCode") String externalCode) {

			Assert.assertNotNull(_company);
			Assert.assertNotNull(contextCompany);

			if (!Objects.equals(externalCode, "externalCode")) {
				return Collections.emptyList();
			}

			return Arrays.asList(
				_toCategory(1L), _toCategory(2L), _toCategory(3L));
		}

		@NestedField("externalCode")
		public String getExternalCodeByQueryParam(
			@QueryParam("AcceptLanguage") String acceptLanguage) {

			Assert.assertNotNull(_company);
			Assert.assertNotNull(contextCompany);

			if (!Objects.equals(acceptLanguage, "es_ES")) {
				return "";
			}

			return "codigoExterno";
		}

		@NestedField("productOptions")
		@Override
		public List<ProductOption> getProductOptions(Long id, String name) {
			Assert.assertNotNull(_company);
			Assert.assertNotNull(contextCompany);

			if (id != 1) {
				return Collections.emptyList();
			}

			List<ProductOption> productOptions = Arrays.asList(
				_toProductOption(10L, "test1"), _toProductOption(20L, "test2"),
				_toProductOption(30L, "test3"));

			if (name != null) {
				productOptions = ListUtil.filter(
					productOptions,
					productOption -> Objects.equals(
						productOption.getName(), name));
			}

			return productOptions;
		}

		@NestedField("productOptionValues")
		@Override
		public List<ProductOptionValue> getProductOptionValues(Long id) {
			Assert.assertNotNull(_company);
			Assert.assertNotNull(contextCompany);

			if (id == 10) {
				return Arrays.asList(
					_toProductOptionValue(100L), _toProductOptionValue(200L),
					_toProductOptionValue(300L));
			}
			else if (id == 20) {
				return Arrays.asList(
					_toProductOptionValue(400L), _toProductOptionValue(500L));
			}

			return Collections.emptyList();
		}

		@Override
		public List<Product> getProducts(boolean inlineInitialization) {
			return Arrays.asList(
				_toProduct("externalCode", 1L, inlineInitialization),
				_toProduct(null, 2L, inlineInitialization));
		}

		@NestedField("skus")
		@Override
		public Page<Sku> getSkus(Long id, Pagination pagination) {
			Assert.assertNotNull(_company);
			Assert.assertNotNull(contextCompany);

			if (!Objects.equals(id, 1L)) {
				return Page.of(Collections.emptyList());
			}

			List<Sku> skus = Arrays.asList(
				_toSku(1L), _toSku(2L), _toSku(3L), _toSku(4L));

			skus = skus.subList(
				pagination.getStartPosition(),
				Math.min(pagination.getEndPosition(), skus.size()));

			return Page.of(skus);
		}

		protected Company contextCompany;

		@Context
		private Company _company;

	}

	public static class ProductResource_v2_0_Impl
		extends BaseProductResource_v2_0_Impl {

		@NestedField("categories")
		public List<Category> getCategories(
			@NestedFieldId("externalCode") String productExternalCode) {

			Assert.assertNotNull(_company);
			Assert.assertNotNull(contextCompany);

			if (!Objects.equals(productExternalCode, "externalCode")) {
				return Collections.emptyList();
			}

			return Arrays.asList(_toCategory(1L), _toCategory(2L));
		}

		@NestedField("productOptions")
		@Override
		public List<ProductOption> getProductOptions(Long id, String name) {
			Assert.assertNotNull(_company);
			Assert.assertNotNull(contextCompany);

			if (id != 1) {
				return Collections.emptyList();
			}

			List<ProductOption> productOptions = Arrays.asList(
				_toProductOption(1L, "test1"), _toProductOption(2L, "test2"),
				_toProductOption(3L, "test3"));

			if (name != null) {
				productOptions = ListUtil.filter(
					productOptions,
					productOption -> Objects.equals(
						productOption.getName(), name));
			}

			return productOptions;
		}

		@Override
		public List<Product> getProducts(boolean inlineInitialization) {
			return Arrays.asList(
				_toSubproduct("externalCode", 1L, inlineInitialization),
				_toProduct(null, 2L, inlineInitialization));
		}

		@NestedField(parentClass = Product.class, value = "skus")
		@Override
		public Page<Sku> getSkus(Long id, Pagination pagination) {
			Assert.assertNotNull(_company);
			Assert.assertNotNull(contextCompany);

			if (!Objects.equals(id, 1L)) {
				return Page.of(Collections.emptyList());
			}

			List<Sku> skus = Arrays.asList(
				_toSku(1L), _toSku(2L), _toSku(3L), _toSku(4L), _toSku(5L),
				_toSku(6L));

			skus = skus.subList(
				pagination.getStartPosition(),
				Math.min(pagination.getEndPosition(), skus.size()));

			return Page.of(skus);
		}

		protected Company contextCompany;

		@Context
		private Company _company;

	}

	public static class Sku {

		public Long getId() {
			return _id;
		}

		public void setId(Long id) {
			_id = id;
		}

		private Long _id;

	}

	public static class Subproduct extends Product {
	}

	public interface ProductResource_v1_0 {

		public List<ProductOption> getProductOptions(Long id, String name);

		public List<ProductOptionValue> getProductOptionValues(Long id);

		public List<Product> getProducts(boolean inlineInitialization);

		public Page<Sku> getSkus(Long id, Pagination pagination);

	}

	public interface ProductResource_v2_0 {

		public List<Category> getCategories(String externalCode);

		public List<ProductOption> getProductOptions(Long id, String name);

		public List<Product> getProducts(boolean inlineInitialization);

		public Page<Sku> getSkus(Long id, Pagination pagination);

	}

	private static Category _toCategory(long id) {
		Category category = new Category();

		category.setId(id);

		return category;
	}

	private static Product _toProduct(
		String externalCode, long id, boolean inlineInitialization) {

		if (inlineInitialization) {
			return new Product() {
				{
					setExternalCode(externalCode);
					setId(id);
				}
			};
		}

		Product product = new Product();

		product.setExternalCode(externalCode);
		product.setId(id);

		return product;
	}

	private static ProductOption _toProductOption(long id, String name) {
		ProductOption productOption = new ProductOption();

		productOption.setId(id);
		productOption.setName(name);

		return productOption;
	}

	private static ProductOptionValue _toProductOptionValue(long id) {
		ProductOptionValue productOptionValue = new ProductOptionValue();

		productOptionValue.setId(id);

		return productOptionValue;
	}

	private static Sku _toSku(long id) {
		Sku sku = new Sku();

		sku.setId(id);

		return sku;
	}

	private static Subproduct _toSubproduct(
		String externalCode, long id, boolean inlineInitialization) {

		if (inlineInitialization) {
			return new Subproduct() {
				{
					setExternalCode(externalCode);
					setId(id);
				}
			};
		}

		Subproduct subproduct = new Subproduct();

		subproduct.setExternalCode(externalCode);
		subproduct.setId(id);

		return subproduct;
	}

	private String _getQueryString(Map<String, String> parameters) {
		String queryString = StringUtil.merge(
			TransformUtil.transform(
				HashMapBuilder.create(
					parameters
				).build(
				).entrySet(),
				entry -> StringBundler.concat(
					URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8),
					"=",
					URLEncoder.encode(
						entry.getValue(), StandardCharsets.UTF_8))),
			"&");

		if (Validator.isNotNull(queryString)) {
			return "?" + queryString;
		}

		return StringPool.BLANK;
	}

	private void _test(
			JSONArray expectedJSONArray, Map<String, String> queryParameters,
			String version)
		throws Exception {

		for (boolean inlineInitialization : new boolean[] {false, true}) {
			JSONAssert.assertEquals(
				expectedJSONArray.toString(),
				HTTPTestUtil.invokeToString(
					null,
					StringBundler.concat(
						"test-vulcan/", version, "/products",
						_getQueryString(
							HashMapBuilder.putAll(
								queryParameters
							).put(
								"inlineInitialization",
								String.valueOf(inlineInitialization)
							).build())),
					Http.Method.GET),
				JSONCompareMode.STRICT_ORDER);
		}
	}

	private List<ServiceRegistration<?>> _serviceRegistrations;

}