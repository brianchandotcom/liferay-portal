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

package com.liferay.apio.architect.message.json;

import static com.liferay.apio.architect.unsafe.Unsafe.unsafeCast;

import static com.spotify.hamcrest.optional.OptionalMatchers.emptyOptional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.Optional;

import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Alejandro Hernández
 */
public class PageMessageMapperTest {

	@Test
	public void testMessageMapperGetSingleModelIsEmptyByDefault() {
		PageMessageMapper<Integer> pageMessageMapper = () -> "mediaType";

		Optional<SingleModelMessageMapper<Integer>> optional =
			pageMessageMapper.getSingleModelMessageMapperOptional();

		assertThat(optional, is(emptyOptional()));
	}

	@Test
	public void testMessageMapperRestItemMethodsOnSingleModelMapperByDefault() {
		SingleModelMessageMapper<Integer> singleModelMessageMapper = unsafeCast(
			Mockito.mock(SingleModelMessageMapper.class));

		PageMessageMapper<Integer> pageMessageMapper =
			new PageMessageMapper<Integer>() {

				@Override
				public String getMediaType() {
					return "mediaType";
				}

				@Override
				public Optional<SingleModelMessageMapper<Integer>>
					getSingleModelMessageMapperOptional() {

					return Optional.of(singleModelMessageMapper);
				}

			};

		_callAllItemMethods(pageMessageMapper);

		_callAllOperationMethods(pageMessageMapper);

		Mockito.verify(
			singleModelMessageMapper
		).mapBooleanField(
			null, null, null
		);

		Mockito.verify(
			singleModelMessageMapper
		).mapBooleanListField(
			null, null, null
		);

		Mockito.verify(
			singleModelMessageMapper
		).mapEmbeddedResourceBooleanField(
			null, null, null, null
		);

		Mockito.verify(
			singleModelMessageMapper
		).mapEmbeddedResourceBooleanListField(
			null, null, null, null
		);

		Mockito.verify(
			singleModelMessageMapper
		).mapEmbeddedResourceLink(
			null, null, null, null
		);

		Mockito.verify(
			singleModelMessageMapper
		).mapEmbeddedResourceNumberField(
			null, null, null, null
		);

		Mockito.verify(
			singleModelMessageMapper
		).mapEmbeddedResourceNumberListField(
			null, null, null, null
		);

		Mockito.verify(
			singleModelMessageMapper
		).mapEmbeddedResourceStringField(
			null, null, null, null
		);

		Mockito.verify(
			singleModelMessageMapper
		).mapEmbeddedResourceStringListField(
			null, null, null, null
		);

		Mockito.verify(
			singleModelMessageMapper
		).mapEmbeddedResourceTypes(
			null, null, null
		);

		Mockito.verify(
			singleModelMessageMapper
		).mapEmbeddedResourceURL(
			null, null, null
		);

		Mockito.verify(
			singleModelMessageMapper
		).mapLink(
			null, null, null
		);

		Mockito.verify(
			singleModelMessageMapper
		).mapLinkedResourceURL(
			null, null, null
		);

		Mockito.verify(
			singleModelMessageMapper
		).mapNumberField(
			null, null, null
		);

		Mockito.verify(
			singleModelMessageMapper
		).mapNumberListField(
			null, null, null
		);

		Mockito.verify(
			singleModelMessageMapper
		).mapOperationFormURL(
			null, null, null
		);

		Mockito.verify(
			singleModelMessageMapper
		).mapOperationMethod(
			null, null, null
		);

		Mockito.verify(
			singleModelMessageMapper
		).mapSelfURL(
			null, null
		);

		Mockito.verify(
			singleModelMessageMapper
		).mapStringField(
			null, null, null
		);

		Mockito.verify(
			singleModelMessageMapper
		).mapStringListField(
			null, null, null
		);

		Mockito.verify(
			singleModelMessageMapper
		).mapTypes(
			null, null
		);

		Mockito.verify(
			singleModelMessageMapper
		).onFinishOperation(
			null, null, null
		);

		Mockito.verify(
			singleModelMessageMapper
		).onStartOperation(
			null, null, null
		);
	}

	private static void _callAllItemMethods(
		PageMessageMapper<Integer> pageMessageMapper) {

		pageMessageMapper.mapItemBooleanField(null, null, null, null);
		pageMessageMapper.mapItemBooleanListField(null, null, null, null);
		pageMessageMapper.mapItemEmbeddedResourceBooleanField(
			null, null, null, null, null);
		pageMessageMapper.mapItemEmbeddedResourceBooleanListField(
			null, null, null, null, null);
		pageMessageMapper.mapItemEmbeddedResourceLink(
			null, null, null, null, null);
		pageMessageMapper.mapItemEmbeddedResourceNumberField(
			null, null, null, null, null);
		pageMessageMapper.mapItemEmbeddedResourceNumberListField(
			null, null, null, null, null);
		pageMessageMapper.mapItemEmbeddedResourceStringField(
			null, null, null, null, null);
		pageMessageMapper.mapItemEmbeddedResourceStringListField(
			null, null, null, null, null);
		pageMessageMapper.mapItemEmbeddedResourceTypes(null, null, null, null);
		pageMessageMapper.mapItemEmbeddedResourceURL(null, null, null, null);
		pageMessageMapper.mapItemLink(null, null, null, null);
		pageMessageMapper.mapItemLinkedResourceURL(null, null, null, null);
		pageMessageMapper.mapItemNumberField(null, null, null, null);
		pageMessageMapper.mapItemNumberListField(null, null, null, null);
		pageMessageMapper.mapItemSelfURL(null, null, null);
		pageMessageMapper.mapItemStringField(null, null, null, null);
		pageMessageMapper.mapItemStringListField(null, null, null, null);
		pageMessageMapper.mapItemTypes(null, null, null);
	}

	private static void _callAllOperationMethods(
		PageMessageMapper<Integer> pageMessageMapper) {

		pageMessageMapper.mapOperationFormURL(null, null, null);
		pageMessageMapper.mapOperationMethod(null, null, null);
		pageMessageMapper.onFinishOperation(null, null, null);
		pageMessageMapper.onStartOperation(null, null, null);
	}

}