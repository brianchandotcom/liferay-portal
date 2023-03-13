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

package com.liferay.headless.batch.engine.test;

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
import java.util.Objects;
import java.util.SortedMap;
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
 * Test or report on the compatibility of batch engine entities.
 * <p>
 * There are two environment variables that can be used with this test:
 * <p>
 * Enable report mode:
 * <p>
 * <code>HEADLESS_BATCH_COMPATIBILITY_REPORT=true</code>
 * <p>
 * Provide a <i>comma delimited</i> list of batch engine entity class names to verify.
 * When not set evaluates all entities not included in the list of <em>known
 * incompatible </em> entities are evaluated:
 * <p>
 * <code>HEADLESS_BATCH_ENGINE_ENTITIES_TO_VERIFY=...</code>
 * <p>
 * Example:
 * <pre><code>startTestableTomcat {
 *   environment = [
 *     "HEADLESS_BATCH_COMPATIBILITY_REPORT" : "true",
 *     "HEADLESS_BATCH_ENGINE_ENTITIES_TO_VERIFY" :
 *       "com.liferay.headless.admin.user.dto.v1_0.Role"
 *   ]
 * }</code></pre>
 *
 * @author Raymond Augé
 */
@RunWith(Arquillian.class)
public class BatchEngineEntityComatibilityTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		if (_batchEngineEntitiesToVerify.isEmpty()) {
			Bundle bundle = FrameworkUtil.getBundle(
				BatchEngineEntityComatibilityTest.class);

			_batchEngineEntityClassNameTracker =
				new ServiceTracker<Object, String>(
					bundle.getBundleContext(),
					FrameworkUtil.createFilter(
						"(batch.engine.entity.class.name=*)"),
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
							String batchEngineEntityClassName) {
						}

						@Override
						public void removedService(
							ServiceReference<Object> serviceReference,
							String batchEngineEntityClassName) {
						}

					});

			_batchEngineEntityClassNameTracker.open();

			SortedMap<ServiceReference<Object>, String> tracked =
				_batchEngineEntityClassNameTracker.getTracked();

			_batchEngineEntitiesToVerify = tracked.values();

			_batchEngineEntitiesToVerify.removeAll(_incompatibleEntities);
		}
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		if (_batchEngineEntityClassNameTracker != null) {
			_batchEngineEntityClassNameTracker.close();
		}
	}

	@Before
	public void setUp() {
		_logCaptures.add(
			LoggerTestUtil.configureLog4JLogger(
				"com.liferay.batch.engine.internal." +
					"BatchEngineImportTaskExecutorImpl",
				LoggerTestUtil.ERROR));
		_logCaptures.add(
			LoggerTestUtil.configureLog4JLogger(
				"com.liferay.batch.engine.internal." +
					"BatchEngineExportTaskExecutorImpl",
				LoggerTestUtil.ERROR));
	}

	@After
	public void tearDown() {
		_logCaptures.forEach(LogCapture::close);
	}

	@Test
	public void testBatchModelClientExtensionComatibility() throws Exception {
		Assert.assertFalse(_batchEngineEntitiesToVerify.isEmpty());

		StringBundler incompatibleSB = new StringBundler();
		boolean failures = false;

		for (String batchEngineEntityClassName : _batchEngineEntitiesToVerify) {
			try {
				_assertCompatibility(batchEngineEntityClassName);

				if (_GENERATE_REPORT) {
					System.out.print(batchEngineEntityClassName);
					System.out.println(", COMPATIBLE");
				}
			}
			catch (Throwable throwable) {
				failures = true;

				String failure = StringBundler.concat(
					batchEngineEntityClassName, ", INCOMPATIBLE",
					throwable.getMessage());

				incompatibleSB.append(failure);

				if (_GENERATE_REPORT) {
					System.out.println(failure);
				}
			}
		}

		if (failures) {
			throw new AssertionError(incompatibleSB.toString());
		}
	}

	private void _assertCompatibility(String batchEngineEntityClassName)
		throws Exception {

		ExportTaskResource.Builder builder = ExportTaskResource.builder();

		ExportTaskResource exportTaskResource = builder.authentication(
			"test@liferay.com", "test"
		).header(
			HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON
		).build();

		ExportTask exportTask = exportTaskResource.postExportTask(
			batchEngineEntityClassName, "jsont", null, null, null, "");

		String exportTaskErc = exportTask.getExternalReferenceCode();

		String exportTaskExecuteStatus = null;

		do {
			exportTask =
				exportTaskResource.getExportTaskByExternalReferenceCode(
					exportTaskErc);

			exportTaskExecuteStatus = exportTask.getExecuteStatusAsString();

			if (Objects.equals(exportTaskExecuteStatus, "FAILED")) {
				throw new AssertionError(
					", EXPORT-TASK, " + exportTask.getErrorMessage());
			}
		}
		while (!Objects.equals(exportTaskExecuteStatus, "COMPLETED"));

		exportTaskResource = builder.authentication(
			"test@liferay.com", "test"
		).header(
			HttpHeaders.ACCEPT, ContentTypes.APPLICATION_OCTET_STREAM
		).build();

		HttpInvoker.HttpResponse httpResponse =
			exportTaskResource.
				getExportTaskByExternalReferenceCodeContentHttpResponse(
					exportTaskErc);

		String exportTaskContent = null;

		try (InputStream inputStream = new UnsyncByteArrayInputStream(
				httpResponse.getBinaryContent())) {

			ZipInputStream zipInputStream = new ZipInputStream(inputStream);

			zipInputStream.getNextEntry();

			exportTaskContent = StringUtil.read(zipInputStream);
		}

		JSONObject exportTaskContentJSONObject = _jsonFactory.createJSONObject(
			exportTaskContent);

		JSONObject actionsJSONObject =
			exportTaskContentJSONObject.getJSONObject("actions");

		Assert.assertNotNull(
			"Must contain .actions property", actionsJSONObject);

		JSONObject createBatchJSONObject = actionsJSONObject.getJSONObject(
			"createBatch");

		Assert.assertNotNull(
			"Must contain .actions.createBatch property",
			createBatchJSONObject);

		Assert.assertNotNull(
			"Must contain .actions.createBatch.href property",
			createBatchJSONObject.getString("href"));

		JSONArray itemsJSONArray = exportTaskContentJSONObject.getJSONArray(
			"items");

		ImportTaskResource importTaskResource = ImportTaskResource.builder(
		).authentication(
			"test@liferay.com", "test"
		).header(
			HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON
		).header(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON
		).build();

		ImportTask importTask = importTaskResource.postImportTask(
			batchEngineEntityClassName, null, "UPSERT", null, null, null, null,
			itemsJSONArray);

		String importTaskErc = importTask.getExternalReferenceCode();

		String importTaskExecuteStatus = null;

		do {
			importTask =
				importTaskResource.getImportTaskByExternalReferenceCode(
					importTaskErc);

			importTaskExecuteStatus = importTask.getExecuteStatusAsString();

			if (Objects.equals(importTaskExecuteStatus, "FAILED")) {
				throw new AssertionError(
					", IMPORT-TASK, " + importTask.getErrorMessage());
			}
		}
		while (!Objects.equals(importTaskExecuteStatus, "COMPLETED"));
	}

	private static final boolean _GENERATE_REPORT = Boolean.valueOf(
		System.getenv("HEADLESS_BATCH_COMPATIBILITY_REPORT"));

	private static Collection<String> _batchEngineEntitiesToVerify =
		StringUtil.split(
			System.getenv("HEADLESS_BATCH_ENGINE_ENTITIES_TO_VERIFY"));
	private static ServiceTracker<Object, String>
		_batchEngineEntityClassNameTracker;
	private static final List<String> _incompatibleEntities = Arrays.asList(
		"com.liferay.data.engine.rest.dto.v2_0.DataDefinition",
		"com.liferay.data.engine.rest.dto.v2_0.DataDefinitionFieldLink",
		"com.liferay.data.engine.rest.dto.v2_0.DataLayout",
		"com.liferay.data.engine.rest.dto.v2_0.DataListView",
		"com.liferay.data.engine.rest.dto.v2_0.DataRecord",
		"com.liferay.data.engine.rest.dto.v2_0.DataRecordCollection",
		"com.liferay.digital.signature.rest.dto.v1_0.DSEnvelope",
		"com.liferay.dispatch.rest.dto.v1_0.DispatchTrigger",
		"com.liferay.headless.admin.address.dto.v1_0.Country",
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
		"com.liferay.headless.admin.workflow.dto.v1_0.WorkflowInstance",
		"com.liferay.headless.admin.workflow.dto.v1_0.WorkflowLog",
		"com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTask",
		"com.liferay.headless.commerce.admin.account.dto.v1_0.AccountAddress",
		"com.liferay.headless.commerce.admin.account.dto.v1_0." +
			"AccountChannelEntry",
		"com.liferay.headless.commerce.admin.account.dto.v1_0." +
			"AccountChannelShippingOption",
		"com.liferay.headless.commerce.admin.account.dto.v1_0.AccountMember",
		"com.liferay.headless.commerce.admin.account.dto.v1_0." +
			"AccountOrganization",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.Catalog",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.Category",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.GroupedProduct",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.LinkedProduct",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.LowStockAction",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.MappedProduct",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.OptionCategory",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.OptionValue",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.Pin",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0." +
			"ProductAccountGroup",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductChannel",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0." +
			"ProductGroupProduct",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductOption",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0." +
			"ProductOptionValue",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0." +
			"ProductSpecification",
		"com.liferay.headless.commerce.admin.catalog.dto.v1_0.RelatedProduct",
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
			"WarehouseChannel",
		"com.liferay.headless.commerce.admin.inventory.dto.v1_0.WarehouseItem",
		"com.liferay.headless.commerce.admin.inventory.dto.v1_0." +
			"WarehouseOrderType",
		"com.liferay.headless.commerce.admin.order.dto.v1_0.OrderNote",
		"com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleAccount",
		"com.liferay.headless.commerce.admin.order.dto.v1_0." +
			"OrderRuleAccountGroup",
		"com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleChannel",
		"com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleOrderType",
		"com.liferay.headless.commerce.admin.order.dto.v1_0.OrderTypeChannel",
		"com.liferay.headless.commerce.admin.order.dto.v1_0.TermOrderType",
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
		"com.liferay.headless.commerce.delivery.catalog.dto.v1_0.LinkedProduct",
		"com.liferay.headless.commerce.delivery.catalog.dto.v1_0.MappedProduct",
		"com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Pin",
		"com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Product",
		"com.liferay.headless.commerce.delivery.catalog.dto.v1_0.ProductOption",
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
		"com.liferay.object.admin.rest.dto.v1_0.ObjectLayout",
		"com.liferay.object.admin.rest.dto.v1_0.ObjectRelationship",
		"com.liferay.object.admin.rest.dto.v1_0.ObjectValidationRule",
		"com.liferay.object.admin.rest.dto.v1_0.ObjectView",
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
		"com.liferay.search.experiences.rest.dto.v1_0.SXPElement",
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