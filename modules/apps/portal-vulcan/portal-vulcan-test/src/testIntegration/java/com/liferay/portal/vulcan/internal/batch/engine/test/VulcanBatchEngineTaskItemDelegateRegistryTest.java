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

package com.liferay.portal.vulcan.internal.batch.engine.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.batch.engine.Field;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegateRegistry;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.OpenAPIResource;
import com.liferay.portal.vulcan.util.OpenAPIUtil;
import com.liferay.portal.vulcan.yaml.YAMLUtil;
import com.liferay.portal.vulcan.yaml.openapi.OpenAPIYAML;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Javier de Arcos
 */
@RunWith(Arquillian.class)
public class VulcanBatchEngineTaskItemDelegateRegistryTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	@Test
	public void testGetEntityClassNames() {
		Set<String> entityClassNames =
			_vulcanBatchEngineTaskItemDelegateRegistry.getEntityClassNames();

		Assert.assertFalse(entityClassNames.isEmpty());

		Assert.assertTrue(
			entityClassNames.contains(
				"com.liferay.headless.delivery.dto.v1_0.StructuredContent"));
	}

	@Test
	public void testGetVulcanBatchEngineTaskItemDelegate() {
		VulcanBatchEngineTaskItemDelegate<?> vulcanBatchEngineTaskItemDelegate =
			_vulcanBatchEngineTaskItemDelegateRegistry.
				getVulcanBatchEngineTaskItemDelegate(
					"com.liferay.headless.delivery.dto.v1_0.StructuredContent");

		Assert.assertNotNull(vulcanBatchEngineTaskItemDelegate);

		Class<?> resourceClass =
			vulcanBatchEngineTaskItemDelegate.getResourceClass();

		Assert.assertEquals(
			"com.liferay.headless.delivery.internal.resource.v1_0." +
				"StructuredContentResourceImpl",
			resourceClass.getName());
	}

	@Test
	public void testIsBatchPlannerExportEnabled() {
		_registerVulcanBatchEngineTaskItemDelegate(true, false);

		Assert.assertTrue(
			_vulcanBatchEngineTaskItemDelegateRegistry.
				isBatchPlannerExportEnabled(
					TestVulcanBatchEngineTaskItemDelegate.class.getName()));
		Assert.assertFalse(
			_vulcanBatchEngineTaskItemDelegateRegistry.
				isBatchPlannerImportEnabled(
					TestVulcanBatchEngineTaskItemDelegate.class.getName()));
	}

	@Test
	public void testIsBatchPlannerExportImportDisabled() {
		_registerVulcanBatchEngineTaskItemDelegate(false, false);

		Assert.assertFalse(
			_vulcanBatchEngineTaskItemDelegateRegistry.
				isBatchPlannerExportEnabled(
					TestVulcanBatchEngineTaskItemDelegate.class.getName()));
		Assert.assertFalse(
			_vulcanBatchEngineTaskItemDelegateRegistry.
				isBatchPlannerImportEnabled(
					TestVulcanBatchEngineTaskItemDelegate.class.getName()));
	}

	@Test
	public void testIsBatchPlannerExportImportEnabled() {
		_registerVulcanBatchEngineTaskItemDelegate(true, true);

		Assert.assertTrue(
			_vulcanBatchEngineTaskItemDelegateRegistry.
				isBatchPlannerExportEnabled(
					TestVulcanBatchEngineTaskItemDelegate.class.getName()));
		Assert.assertTrue(
			_vulcanBatchEngineTaskItemDelegateRegistry.
				isBatchPlannerImportEnabled(
					TestVulcanBatchEngineTaskItemDelegate.class.getName()));
	}

	@Test
	public void testIsBatchPlannerImportEnabled() {
		_registerVulcanBatchEngineTaskItemDelegate(false, true);

		Assert.assertFalse(
			_vulcanBatchEngineTaskItemDelegateRegistry.
				isBatchPlannerExportEnabled(
					TestVulcanBatchEngineTaskItemDelegate.class.getName()));
		Assert.assertTrue(
			_vulcanBatchEngineTaskItemDelegateRegistry.
				isBatchPlannerImportEnabled(
					TestVulcanBatchEngineTaskItemDelegate.class.getName()));
	}

	@Test
	public void testRegistryAndOpenAPIUtilToGetEntityMetadata()
		throws Exception {

		Set<String> entityClassNames =
			_vulcanBatchEngineTaskItemDelegateRegistry.getEntityClassNames();

		Assert.assertTrue(
			entityClassNames.contains(
				"com.liferay.headless.delivery.dto.v1_0.StructuredContent"));

		VulcanBatchEngineTaskItemDelegate<?> vulcanBatchEngineTaskItemDelegate =
			_vulcanBatchEngineTaskItemDelegateRegistry.
				getVulcanBatchEngineTaskItemDelegate(
					"com.liferay.headless.delivery.dto.v1_0.StructuredContent");

		Assert.assertNotNull(vulcanBatchEngineTaskItemDelegate);

		Class<?> resourceClass =
			vulcanBatchEngineTaskItemDelegate.getResourceClass();

		Assert.assertEquals(
			"com.liferay.headless.delivery.internal.resource.v1_0." +
				"StructuredContentResourceImpl",
			resourceClass.getName());

		Assert.assertEquals(
			"v1.0", vulcanBatchEngineTaskItemDelegate.getVersion());

		Response response = _openAPIResource.getOpenAPI(
			Collections.singleton(resourceClass), "yaml");

		Assert.assertEquals(200, response.getStatus());

		OpenAPIYAML openAPIYAML = YAMLUtil.loadOpenAPIYAML(
			(String)response.getEntity());

		Assert.assertNotNull(openAPIYAML);

		List<String> createEntityScopes = OpenAPIUtil.getCreateEntityScopes(
			"StructuredContent", openAPIYAML);

		Assert.assertEquals(
			createEntityScopes.toString(), 3, createEntityScopes.size());
		AssertUtils.assertEquals(
			Arrays.asList("assetLibrary", "site", "structuredContentFolder"),
			createEntityScopes);

		List<String> readEntityScopes = OpenAPIUtil.getReadEntityScopes(
			"StructuredContent", openAPIYAML);

		Assert.assertEquals(
			readEntityScopes.toString(), 4, readEntityScopes.size());
		AssertUtils.assertEquals(
			Arrays.asList(
				"assetLibrary", "contentStructure", "site",
				"structuredContentFolder"),
			readEntityScopes);

		Map<String, Field> dtoEntityFields = OpenAPIUtil.getDTOEntityFields(
			"StructuredContent", openAPIYAML);

		_assertContainsAll(
			dtoEntityFields.keySet(), "contentFields", "description", "id",
			"title");
	}

	private void _assertContainsAll(
		Collection<String> collection, String... keys) {

		for (String key : keys) {
			Assert.assertTrue(collection.contains(key));
		}
	}

	private void _registerVulcanBatchEngineTaskItemDelegate(
		boolean exportEnabled, boolean importEnabled) {

		Bundle bundle = FrameworkUtil.getBundle(
			VulcanBatchEngineTaskItemDelegateRegistryTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			VulcanBatchEngineTaskItemDelegate.class,
			new TestVulcanBatchEngineTaskItemDelegate(),
			HashMapDictionaryBuilder.put(
				"batch.engine.task.item.delegate", "true"
			).put(
				"batch.planner.export.enabled", String.valueOf(exportEnabled)
			).put(
				"batch.planner.import.enabled", String.valueOf(importEnabled)
			).put(
				"entity.class.name",
				TestVulcanBatchEngineTaskItemDelegate.class.getName()
			).build());
	}

	@Inject
	private OpenAPIResource _openAPIResource;

	private ServiceRegistration<VulcanBatchEngineTaskItemDelegate>
		_serviceRegistration;

	@Inject
	private VulcanBatchEngineTaskItemDelegateRegistry
		_vulcanBatchEngineTaskItemDelegateRegistry;

	private static class TestVulcanBatchEngineTaskItemDelegate
		implements VulcanBatchEngineTaskItemDelegate<Object> {

		@Override
		public void create(
				Collection<Object> items, Map<String, Serializable> parameters)
			throws Exception {
		}

		@Override
		public void delete(
				Collection<Object> items, Map<String, Serializable> parameters)
			throws Exception {
		}

		@Override
		public EntityModel getEntityModel(
				Map<String, List<String>> multivaluedMap)
			throws Exception {

			return null;
		}

		@Override
		public Page<Object> read(
				Filter filter, Pagination pagination, Sort[] sorts,
				Map<String, Serializable> parameters, String search)
			throws Exception {

			return null;
		}

		@Override
		public void setContextBatchUnsafeConsumer(
			UnsafeBiConsumer
				<Collection<Object>, UnsafeConsumer<Object, Exception>,
				 Exception> contextBatchUnsafeConsumer) {
		}

		@Override
		public void setContextCompany(Company contextCompany) {
		}

		@Override
		public void setContextUser(User contextUser) {
		}

		@Override
		public void setLanguageId(String languageId) {
		}

		@Override
		public void update(
				Collection<Object> items, Map<String, Serializable> parameters)
			throws Exception {
		}

	}

}