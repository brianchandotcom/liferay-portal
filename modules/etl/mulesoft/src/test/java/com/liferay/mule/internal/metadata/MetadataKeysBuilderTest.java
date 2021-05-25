/**
 * Copyright (c) 2000-2021 Liferay, Inc. All rights reserved.
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

package com.liferay.mule.internal.metadata;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.mule.internal.oas.constants.OASConstants;

import java.io.InputStream;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.metadata.MetadataKey;
import org.mule.runtime.api.metadata.MetadataKeyBuilder;
import org.mule.runtime.api.metadata.MetadataResolvingException;

/**
 * @author Matija Petanjek
 */
public class MetadataKeysBuilderTest {

	@Before
	public void setUp() throws Exception {
		metadataKeysBuilder = Mockito.spy(MetadataKeysBuilder.class);

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"com/liferay/mule/internal/metadata/openapi.json");

		ObjectMapper objectMapper = new ObjectMapper();

		openAPISpecJsonNode = objectMapper.readTree(inputStream);

		Mockito.doReturn(
			openAPISpecJsonNode
		).when(
			metadataKeysBuilder
		).getOASJsonNode(
			Matchers.anyObject()
		);
	}

	@Test
	public void testBuildClassNameMetadataKeys()
		throws ConnectionException, MetadataResolvingException {

		Set<MetadataKey> metadataKeys =
			metadataKeysBuilder.buildClassNameMetadataKeys(null);

		Assert.assertEquals(metadataKeys.toString(), 2, metadataKeys.size());

		MetadataKey metadataKey = MetadataKeyBuilder.newKey(
			"com.liferay.headless.v1_0.Entity"
		).build();

		Assert.assertTrue(metadataKeys.contains(metadataKey));

		metadataKey = MetadataKeyBuilder.newKey(
			"com.liferay.headless.v1_0.NestedEntity"
		).build();

		Assert.assertTrue(metadataKeys.contains(metadataKey));
	}

	@Test
	public void testBuildDELETEEndpointMetadataKeys()
		throws ConnectionException, MetadataResolvingException {

		Set<String> metadataKeyIds = toMetadataKeyIdSet(
			metadataKeysBuilder.buildEndpointMetadataKeys(
				null, OASConstants.OPERATION_DELETE));

		Assert.assertTrue(
			metadataKeyIds.contains("/endpoint/with/delete/and/get/operation"));
	}

	@Test
	public void testBuildEmptyMetadataKeys()
		throws ConnectionException, MetadataResolvingException {

		Set<MetadataKey> metadataKeys =
			metadataKeysBuilder.buildEndpointMetadataKeys(null, OPERATION_HEAD);

		Assert.assertTrue(metadataKeys.isEmpty());
	}

	@Test
	public void testBuildGETEndpointMetadataKeys()
		throws ConnectionException, MetadataResolvingException {

		Set<String> metadataKeyIds = toMetadataKeyIdSet(
			metadataKeysBuilder.buildEndpointMetadataKeys(
				null, OASConstants.OPERATION_GET));

		Assert.assertTrue(
			metadataKeyIds.contains("/endpoint/with/delete/and/get/operation"));
		Assert.assertTrue(
			metadataKeyIds.contains("/endpoint/with/get/and/patch/operation"));
		Assert.assertTrue(
			metadataKeyIds.contains("/endpoint/with/get/and/post/operation"));
	}

	@Test
	public void testBuildPATCHEndpointMetadataKeys()
		throws ConnectionException, MetadataResolvingException {

		Set<String> metadataKeyIds = toMetadataKeyIdSet(
			metadataKeysBuilder.buildEndpointMetadataKeys(
				null, OASConstants.OPERATION_PATCH));

		Assert.assertTrue(
			metadataKeyIds.contains("/endpoint/with/get/and/patch/operation"));
	}

	@Test
	public void testBuildPOSTEndpointMetadataKeys()
		throws ConnectionException, MetadataResolvingException {

		Set<String> metadataKeyIds = toMetadataKeyIdSet(
			metadataKeysBuilder.buildEndpointMetadataKeys(
				null, OASConstants.OPERATION_POST));

		Assert.assertTrue(
			metadataKeyIds.contains("/endpoint/with/get/and/post/operation"));
	}

	private Set<String> toMetadataKeyIdSet(Set<MetadataKey> metadataKeys) {
		Stream<MetadataKey> stream = metadataKeys.stream();

		return stream.map(
			MetadataKey::getId
		).collect(
			Collectors.toSet()
		);
	}

	private static final String OPERATION_HEAD = "head";

	private MetadataKeysBuilder metadataKeysBuilder;
	private JsonNode openAPISpecJsonNode;

}