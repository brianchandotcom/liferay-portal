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

package com.liferay.data.engine.web.internal.servlet.data.fetcher;

import com.liferay.data.engine.service.DEDataDefinitionDeleteRequest;
import com.liferay.data.engine.service.DEDataDefinitionDeleteResponse;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.data.engine.web.internal.graphql.model.DataDefinitionType;
import com.liferay.data.engine.web.internal.graphql.model.DeleteDataDefinitionType;

import graphql.schema.DataFetchingEnvironment;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * @author Leonardo Barros
 */
public class DEDeleteDataDefinitionDataFetcherTest {

	@Test
	public void testGet() throws Exception {
		DataFetchingEnvironment dataFetchingEnvironment = Mockito.mock(
			DataFetchingEnvironment.class);

		Mockito.when(
			dataFetchingEnvironment.getArgument("dataDefinitionId")
		).thenReturn(
			"1"
		);

		DEDataDefinitionService deDataDefinitionService = Mockito.mock(
			DEDataDefinitionService.class);

		DEDataDefinitionDeleteResponse deDataDefinitionDeleteResponse =
			DEDataDefinitionDeleteResponse.Builder.newBuilder(
				1
			).build();

		Mockito.when(
			deDataDefinitionService.execute(
				Matchers.any(DEDataDefinitionDeleteRequest.class))
		).thenReturn(
			deDataDefinitionDeleteResponse
		);

		DEDeleteDataDefinitionDataFetcher deDeleteDataDefinitionDataFetcher =
			new DEDeleteDataDefinitionDataFetcher();

		deDeleteDataDefinitionDataFetcher.deDataDefinitionService =
			deDataDefinitionService;

		DeleteDataDefinitionType deleteDataDefinitionType =
			deDeleteDataDefinitionDataFetcher.get(dataFetchingEnvironment);

		DataDefinitionType deDataDefinitionType =
			deleteDataDefinitionType.getDataDefinition();

		Assert.assertEquals("1", deDataDefinitionType.getDataDefinitionId());
	}

}