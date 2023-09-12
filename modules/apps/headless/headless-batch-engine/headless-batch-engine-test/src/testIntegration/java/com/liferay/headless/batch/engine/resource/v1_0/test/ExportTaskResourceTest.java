/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.batch.engine.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.batch.engine.client.dto.v1_0.ExportTask;
import com.liferay.headless.batch.engine.client.dto.v1_0.ImportTask;
import com.liferay.headless.batch.engine.client.http.HttpInvoker;
import com.liferay.headless.batch.engine.client.resource.v1_0.ExportTaskResource;
import com.liferay.headless.batch.engine.client.resource.v1_0.ImportTaskResource;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipInputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * Modify the value of _testableClassNames to test specific class names.
 *
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class ExportTaskResourceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		if (!_testableClassNames.isEmpty()) {
			return;
		}

		Bundle bundle = FrameworkUtil.getBundle(ExportTaskResourceTest.class);

		_serviceTracker = new ServiceTracker<Object, String>(
			bundle.getBundleContext(),
			FrameworkUtil.createFilter("(batch.engine.entity.class.name=*)"),
			new ServiceTrackerCustomizer<Object, String>() {

				@Override
				public String addingService(
					ServiceReference<Object> serviceReference) {

					return (String)serviceReference.getProperty(
						"batch.engine.entity.class.name");
				}

				@Override
				public void modifiedService(
					ServiceReference<Object> serviceReference,
					String className) {
				}

				@Override
				public void removedService(
					ServiceReference<Object> serviceReference,
					String className) {
				}

			});

		_serviceTracker.open();

		Map<ServiceReference<Object>, String> map =
			_serviceTracker.getTracked();

		_testableClassNames = map.values();

		_testableClassNames.removeAll(_untestableDTOClassNames);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		if (_serviceTracker != null) {
			_serviceTracker.close();
		}
	}

	@Before
	public void setUp() {
		_logCaptures.add(
			LoggerTestUtil.configureLog4JLogger(
				"com.liferay.batch.engine.internal." +
					"BatchEngineExportTaskExecutorImpl",
				LoggerTestUtil.ERROR));
		_logCaptures.add(
			LoggerTestUtil.configureLog4JLogger(
				"com.liferay.batch.engine.internal." +
					"BatchEngineImportTaskExecutorImpl",
				LoggerTestUtil.ERROR));
	}

	@After
	public void tearDown() {
		_logCaptures.forEach(LogCapture::close);
	}

	@Test
	public void testPostExportTask() throws Exception {
		Assert.assertFalse(_testableClassNames.isEmpty());

		StringBundler sb = new StringBundler();

		for (String className : _testableClassNames) {
			try {
				if (_log.isInfoEnabled()) {
					_log.info("Testing " + className);
				}

				_testPostExportTask(className);
			}
			catch (Throwable throwable) {
				sb.append(className);
				sb.append(": ");
				sb.append(throwable.getMessage());
				sb.append("\n");
			}
		}

		if (sb.length() > 0) {
			throw new AssertionError(sb.toString());
		}
	}

	private void _testPostExportTask(String className) throws Exception {
		ExportTaskResource.Builder builder = ExportTaskResource.builder();

		ExportTaskResource exportTaskResource = builder.authentication(
			"test@liferay.com", "test"
		).header(
			HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON
		).build();

		ExportTask exportTask = exportTaskResource.postExportTask(
			className, "jsont", null, null, null, "");

		String externalReferenceCode = exportTask.getExternalReferenceCode();

		while (true) {
			exportTask =
				exportTaskResource.getExportTaskByExternalReferenceCode(
					externalReferenceCode);

			if (Objects.equals(
					exportTask.getExecuteStatusAsString(), "COMPLETED")) {

				break;
			}
			else if (Objects.equals(
						exportTask.getExecuteStatusAsString(), "FAILED")) {

				throw new AssertionError(exportTask.getErrorMessage());
			}
		}

		String json = null;

		exportTaskResource = builder.authentication(
			"test@liferay.com", "test"
		).header(
			HttpHeaders.ACCEPT, ContentTypes.APPLICATION_OCTET_STREAM
		).build();

		HttpInvoker.HttpResponse httpResponse =
			exportTaskResource.
				getExportTaskByExternalReferenceCodeContentHttpResponse(
					externalReferenceCode);

		try (InputStream inputStream = new UnsyncByteArrayInputStream(
				httpResponse.getBinaryContent())) {

			ZipInputStream zipInputStream = new ZipInputStream(inputStream);

			zipInputStream.getNextEntry();

			json = StringUtil.read(zipInputStream);
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject(json);

		JSONObject actionsJSONObject = jsonObject.getJSONObject("actions");

		Assert.assertNotNull(actionsJSONObject);

		JSONObject createBatchJSONObject = actionsJSONObject.getJSONObject(
			"createBatch");

		Assert.assertNotNull(createBatchJSONObject);

		Assert.assertNotNull(createBatchJSONObject.getString("href"));

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		ImportTaskResource importTaskResource = ImportTaskResource.builder(
		).authentication(
			"test@liferay.com", "test"
		).header(
			HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON
		).header(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON
		).build();

		ImportTask importTask = importTaskResource.postImportTask(
			className, null, "UPSERT", null, null, null, null, itemsJSONArray);

		externalReferenceCode = importTask.getExternalReferenceCode();

		while (true) {
			importTask =
				importTaskResource.getImportTaskByExternalReferenceCode(
					externalReferenceCode);

			if (Objects.equals(
					importTask.getExecuteStatusAsString(), "COMPLETED")) {

				break;
			}
			else if (Objects.equals(
						importTask.getExecuteStatusAsString(), "FAILED")) {

				throw new AssertionError(importTask.getErrorMessage());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportTaskResourceTest.class);

	private static ServiceTracker<Object, String> _serviceTracker;

	/**
	 * Modify the value of _testableClassNames to test specific class names.
	 */
	private static Collection<String> _testableClassNames = Arrays.asList(
		//"com.liferay.data.engine.rest.dto.v2_0.DataDefinition",
		//"com.liferay.data.engine.rest.dto.v2_0.DataDefinitionFieldLink"
	);

	private static final List<String> _untestableDTOClassNames = Arrays.asList(
		"com.liferay.change.tracking.rest.dto.v1_0.CTCollection",
		"com.liferay.change.tracking.rest.dto.v1_0.CTEntry",
		"com.liferay.change.tracking.rest.dto.v1_0.CTProcess",
		"com.liferay.change.tracking.rest.dto.v1_0.CTRemote",
		"com.liferay.data.engine.rest.dto.v2_0.DataDefinition",
		"com.liferay.data.engine.rest.dto.v2_0.DataDefinitionFieldLink",
		"com.liferay.data.engine.rest.dto.v2_0.DataLayout",
		"com.liferay.data.engine.rest.dto.v2_0.DataListView",
		"com.liferay.data.engine.rest.dto.v2_0.DataRecord",
		"com.liferay.data.engine.rest.dto.v2_0.DataRecordCollection",
		"com.liferay.digital.signature.rest.dto.v1_0.DSEnvelope",
		"com.liferay.dispatch.rest.dto.v1_0.DispatchTrigger",
		"com.liferay.headless.admin.address.dto.v1_0.Country",
		"com.liferay.headless.admin.address.dto.v1_0.Region",
		"com.liferay.headless.admin.list.type.dto.v1_0.ListTypeEntry",
		"com.liferay.headless.admin.taxonomy.dto.v1_0.Keyword",
		"com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyCategory",
		"com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyVocabulary",
		"com.liferay.headless.admin.user.dto.v1_0.AccountRole",
		"com.liferay.headless.admin.user.dto.v1_0.EmailAddress",
		"com.liferay.headless.admin.user.dto.v1_0.Phone",
		"com.liferay.headless.admin.user.dto.v1_0.PostalAddress",
		"com.liferay.headless.admin.user.dto.v1_0.Role",
		"com.liferay.headless.admin.user.dto.v1_0.Segment",
		"com.liferay.headless.admin.user.dto.v1_0.SegmentUser",
		"com.liferay.headless.admin.user.dto.v1_0.Site",
		"com.liferay.headless.admin.user.dto.v1_0.Subscription",
		"com.liferay.headless.admin.user.dto.v1_0.WebUrl",
		"com.liferay.headless.admin.workflow.dto.v1_0.Assignee",
		"com.liferay.headless.admin.workflow.dto.v1_0.Transition",
		"com.liferay.headless.admin.workflow.dto.v1_0.WorkflowDefinition",
		"com.liferay.headless.admin.workflow.dto.v1_0.WorkflowInstance",
		"com.liferay.headless.admin.workflow.dto.v1_0.WorkflowLog",
		"com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTask",
		"com.liferay.headless.commerce.admin.account.dto.v1_0.Account",
		"com.liferay.headless.commerce.admin.account.dto.v1_0.AccountAddress",
		"com.liferay.headless.commerce.admin.account.dto.v1_0." +
			"AccountChannelEntry",
		"com.liferay.headless.commerce.admin.account.dto.v1_0." +
			"AccountChannelShippingOption",
		"com.liferay.headless.commerce.admin.account.dto.v1_0.AccountMember",
		"com.liferay.headless.commerce.admin.account.dto.v1_0." +
			"AccountOrganization",
		"com.liferay.headless.commerce.admin.account.dto.v1_0." +
			"AdminAccountGroup",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.Attachment",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.Catalog",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.Category",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.Diagram",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.GroupedProduct",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.LinkedProduct",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.LowStockAction",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.MappedProduct",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.Option",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.OptionCategory",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.OptionValue",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.Pin",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0." +
			"ProductAccountGroup",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductChannel",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductGroup",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0." +
			"ProductGroupProduct",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductOption",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0." +
			"ProductOptionValue",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0." +
			"ProductSpecification",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.RelatedProduct",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.Sku",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.SkuUnitOfMeasure",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.Specification",
		"com.liferay.headless.commerce.admin.channel.dto.v1_0." +
			"PaymentMethodGroupRelOrderType",
		"com.liferay.headless.commerce.admin.channel.dto.v1_0." +
			"PaymentMethodGroupRelTerm",
		"com.liferay.headless.commerce.admin.channel.dto.v1_0." +
			"ShippingFixedOptionOrderType",
		"com.liferay.headless.commerce.admin.channel.dto.v1_0." +
			"ShippingFixedOptionTerm",
		"com.liferay.headless.commerce.admin.channel.dto.v1_0.ShippingMethod",
		"com.liferay.headless.commerce.admin.channel.dto.v1_0.TaxCategory",
		"com.liferay.headless.commerce.admin.inventory.dto.v1_0." +
			"ReplenishmentItem",
		"com.liferay.headless.commerce.admin.inventory.dto.v1_0.Warehouse",
		"com.liferay.headless.commerce.admin.inventory.dto.v1_0." +
			"WarehouseChannel",
		"com.liferay.headless.commerce.admin.inventory.dto.v1_0.WarehouseItem",
		"com.liferay.headless.commerce.admin.inventory.dto.v1_0." +
			"WarehouseOrderType",
		"com.liferay.headless.commerce.admin.order.dto.v1_0.Order",
		"com.liferay.headless.commerce.admin.order.dto.v1_0.OrderNote",
		"com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRule",
		"com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleAccount",
		"com.liferay.headless.commerce.admin.order.dto.v1_0." +
			"OrderRuleAccountGroup",
		"com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleChannel",
		"com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleOrderType",
		"com.liferay.headless.commerce.admin.order.dto.v1_0.OrderType",
		"com.liferay.headless.commerce.admin.order.dto.v1_0.OrderTypeChannel",
		"com.liferay.headless.commerce.admin.order.dto.v1_0.Term",
		"com.liferay.headless.commerce.admin.order.dto.v1_0.TermOrderType",
		"com.liferay.headless.commerce.admin.pricing.dto.v1_0.Discount",
		"com.liferay.headless.commerce.admin.pricing.dto.v1_0." +
			"DiscountAccountGroup",
		"com.liferay.headless.commerce.admin.pricing.dto.v1_0.DiscountCategory",
		"com.liferay.headless.commerce.admin.pricing.dto.v1_0.DiscountProduct",
		"com.liferay.headless.commerce.admin.pricing.dto.v1_0.DiscountRule",
		"com.liferay.headless.commerce.admin.pricing.dto.v1_0.PriceEntry",
		"com.liferay.headless.commerce.admin.pricing.dto.v1_0.PriceList",
		"com.liferay.headless.commerce.admin.pricing.dto.v1_0." +
			"PriceListAccountGroup",
		"com.liferay.headless.commerce.admin.pricing.dto.v1_0.TierPrice",
		"com.liferay.headless.commerce.admin.pricing.dto.v2_0.Discount",
		"com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountAccount",
		"com.liferay.headless.commerce.admin.pricing.dto.v2_0." +
			"DiscountAccountGroup",
		"com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountCategory",
		"com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountChannel",
		"com.liferay.headless.commerce.admin.pricing.dto.v2_0." +
			"DiscountOrderType",
		"com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountProduct",
		"com.liferay.headless.commerce.admin.pricing.dto.v2_0." +
			"DiscountProductGroup",
		"com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountRule",
		"com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountSku",
		"com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceEntry",
		"com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceList",
		"com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListAccount",
		"com.liferay.headless.commerce.admin.pricing.dto.v2_0." +
			"PriceListAccountGroup",
		"com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListChannel",
		"com.liferay.headless.commerce.admin.pricing.dto.v2_0." +
			"PriceListDiscount",
		"com.liferay.headless.commerce.admin.pricing.dto.v2_0." +
			"PriceListOrderType",
		"com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceModifier",
		"com.liferay.headless.commerce.admin.pricing.dto.v2_0." +
			"PriceModifierCategory",
		"com.liferay.headless.commerce.admin.pricing.dto.v2_0." +
			"PriceModifierProduct",
		"com.liferay.headless.commerce.admin.pricing.dto.v2_0." +
			"PriceModifierProductGroup",
		"com.liferay.headless.commerce.admin.pricing.dto.v2_0.TierPrice",
		"com.liferay.headless.commerce.admin.shipment.dto.v1_0.ShipmentItem",
		"com.liferay.headless.commerce.admin.site.setting.dto.v1_0." +
			"AvailabilityEstimate",
		"com.liferay.headless.commerce.admin.site.setting.dto.v1_0." +
			"MeasurementUnit",
		"com.liferay.headless.commerce.admin.site.setting.dto.v1_0.TaxCategory",
		"com.liferay.headless.commerce.admin.site.setting.dto.v1_0.Warehouse",
		"com.liferay.headless.commerce.delivery.cart.dto.v1_0.Cart",
		"com.liferay.headless.commerce.delivery.cart.dto.v1_0.CartComment",
		"com.liferay.headless.commerce.delivery.cart.dto.v1_0.CartItem",
		"com.liferay.headless.commerce.delivery.cart.dto.v1_0.PaymentMethod",
		"com.liferay.headless.commerce.delivery.cart.dto.v1_0.ShippingMethod",
		"com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Attachment",
		"com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Category",
		"com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Channel",
		"com.liferay.headless.commerce.delivery.catalog.dto.v1_0.LinkedProduct",
		"com.liferay.headless.commerce.delivery.catalog.dto.v1_0.MappedProduct",
		"com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Pin",
		"com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Product",
		"com.liferay.headless.commerce.delivery.catalog.dto.v1_0.ProductOption",
		"com.liferay.headless.commerce.delivery.catalog.dto.v1_0." +
			"ProductOptionValue",
		"com.liferay.headless.commerce.delivery.catalog.dto.v1_0." +
			"ProductSpecification",
		"com.liferay.headless.commerce.delivery.catalog.dto.v1_0." +
			"RelatedProduct",
		"com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Sku",
		"com.liferay.headless.commerce.delivery.catalog.dto.v1_0.WishList",
		"com.liferay.headless.commerce.delivery.catalog.dto.v1_0.WishListItem",
		"com.liferay.headless.commerce.delivery.order.dto.v1_0.PlacedOrder",
		"com.liferay.headless.commerce.delivery.order.dto.v1_0." +
			"PlacedOrderComment",
		"com.liferay.headless.commerce.delivery.order.dto.v1_0.PlacedOrderItem",
		"com.liferay.headless.commerce.delivery.order.dto.v1_0." +
			"PlacedOrderItemShipment",
		"com.liferay.headless.commerce.machine.learning.dto.v1_0." +
			"AccountCategoryForecast",
		"com.liferay.headless.commerce.machine.learning.dto.v1_0." +
			"AccountForecast",
		"com.liferay.headless.commerce.machine.learning.dto.v1_0.SkuForecast",
		"com.liferay.headless.delivery.dto.v1_0.BlogPosting",
		"com.liferay.headless.delivery.dto.v1_0.BlogPostingImage",
		"com.liferay.headless.delivery.dto.v1_0.Comment",
		"com.liferay.headless.delivery.dto.v1_0.ContentElement",
		"com.liferay.headless.delivery.dto.v1_0.ContentSetElement",
		"com.liferay.headless.delivery.dto.v1_0.ContentStructure",
		"com.liferay.headless.delivery.dto.v1_0.ContentTemplate",
		"com.liferay.headless.delivery.dto.v1_0.Document",
		"com.liferay.headless.delivery.dto.v1_0.DocumentFolder",
		"com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseArticle",
		"com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseAttachment",
		"com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseFolder",
		"com.liferay.headless.delivery.dto.v1_0.Language",
		"com.liferay.headless.delivery.dto.v1_0.MessageBoardAttachment",
		"com.liferay.headless.delivery.dto.v1_0.MessageBoardMessage",
		"com.liferay.headless.delivery.dto.v1_0.MessageBoardSection",
		"com.liferay.headless.delivery.dto.v1_0.MessageBoardThread",
		"com.liferay.headless.delivery.dto.v1_0.NavigationMenu",
		"com.liferay.headless.delivery.dto.v1_0.SitePage",
		"com.liferay.headless.delivery.dto.v1_0.StructuredContent",
		"com.liferay.headless.delivery.dto.v1_0.StructuredContentFolder",
		"com.liferay.headless.delivery.dto.v1_0.WikiNode",
		"com.liferay.headless.delivery.dto.v1_0.WikiPage",
		"com.liferay.headless.delivery.dto.v1_0.WikiPageAttachment",
		"com.liferay.headless.form.dto.v1_0.Form",
		"com.liferay.headless.form.dto.v1_0.FormDocument",
		"com.liferay.headless.form.dto.v1_0.FormRecord",
		"com.liferay.headless.form.dto.v1_0.FormStructure",
		"com.liferay.headless.user.notification.dto.v1_0.UserNotification",
		"com.liferay.notification.rest.dto.v1_0.NotificationQueueEntry",
		"com.liferay.object.admin.rest.dto.v1_0.ObjectAction",
		"com.liferay.object.admin.rest.dto.v1_0.ObjectField",
		"com.liferay.object.admin.rest.dto.v1_0.ObjectFolder",
		"com.liferay.object.admin.rest.dto.v1_0.ObjectLayout",
		"com.liferay.object.admin.rest.dto.v1_0.ObjectRelationship",
		"com.liferay.object.admin.rest.dto.v1_0.ObjectValidationRule",
		"com.liferay.object.admin.rest.dto.v1_0.ObjectView",
		"com.liferay.portal.search.rest.dto.v1_0.SearchResult",
		"com.liferay.portal.workflow.metrics.rest.dto.v1_0.Assignee",
		"com.liferay.portal.workflow.metrics.rest.dto.v1_0.AssigneeMetric",
		"com.liferay.portal.workflow.metrics.rest.dto.v1_0.Calendar",
		"com.liferay.portal.workflow.metrics.rest.dto.v1_0.Index",
		"com.liferay.portal.workflow.metrics.rest.dto.v1_0.Instance",
		"com.liferay.portal.workflow.metrics.rest.dto.v1_0.Node",
		"com.liferay.portal.workflow.metrics.rest.dto.v1_0.NodeMetric",
		"com.liferay.portal.workflow.metrics.rest.dto.v1_0.Process",
		"com.liferay.portal.workflow.metrics.rest.dto.v1_0.ProcessMetric",
		"com.liferay.portal.workflow.metrics.rest.dto.v1_0.ProcessVersion",
		"com.liferay.portal.workflow.metrics.rest.dto.v1_0.ReindexStatus",
		"com.liferay.portal.workflow.metrics.rest.dto.v1_0.Role",
		"com.liferay.portal.workflow.metrics.rest.dto.v1_0.SLA",
		"com.liferay.portal.workflow.metrics.rest.dto.v1_0.Task",
		"com.liferay.portal.workflow.metrics.rest.dto.v1_0.TimeRange",
		"com.liferay.saml.admin.rest.dto.v1_0.SamlProvider",
		"com.liferay.search.experiences.rest.dto.v1_0.FieldMappingInfo",
		"com.liferay.search.experiences.rest.dto.v1_0.KeywordQueryContributor",
		"com.liferay.search.experiences.rest.dto.v1_0.MLModel",
		"com.liferay.search.experiences.rest.dto.v1_0." +
			"ModelPrefilterContributor",
		"com.liferay.search.experiences.rest.dto.v1_0." +
			"QueryPrefilterContributor",
		"com.liferay.search.experiences.rest.dto.v1_0.SearchableAssetName",
		"com.liferay.search.experiences.rest.dto.v1_0." +
			"SearchableAssetNameDisplay",
		"com.liferay.search.experiences.rest.dto.v1_0.SearchIndex",
		"com.liferay.search.experiences.rest.dto.v1_0." +
			"SXPParameterContributorDefinition",
		"com.liferay.segments.asah.rest.dto.v1_0.Experiment",
		"com.liferay.segments.asah.rest.dto.v1_0.Status");

	@Inject
	private Http _http;

	@Inject
	private JSONFactory _jsonFactory;

	private final List<LogCapture> _logCaptures = new ArrayList<>();

}