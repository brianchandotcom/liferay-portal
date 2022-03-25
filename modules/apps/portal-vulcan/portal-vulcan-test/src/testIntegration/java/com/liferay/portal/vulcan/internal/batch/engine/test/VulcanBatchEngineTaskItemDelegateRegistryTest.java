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
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.batch.engine.Field;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegateRegistry;
import com.liferay.portal.vulcan.resource.OpenAPIResource;
import com.liferay.portal.vulcan.util.OpenAPIUtil;
import com.liferay.portal.vulcan.yaml.YAMLUtil;
import com.liferay.portal.vulcan.yaml.openapi.OpenAPIYAML;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier de Arcos
 */
@RunWith(Arquillian.class)
public class VulcanBatchEngineTaskItemDelegateRegistryTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

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

	@Inject
	private OpenAPIResource _openAPIResource;

	@Inject
	private VulcanBatchEngineTaskItemDelegateRegistry
		_vulcanBatchEngineTaskItemDelegateRegistry;

}