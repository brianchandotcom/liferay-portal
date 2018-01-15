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

import static com.spotify.hamcrest.optional.OptionalMatchers.emptyOptional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.pagination.Page;

import java.util.Optional;

import javax.ws.rs.core.HttpHeaders;

import org.junit.Test;

import org.mockito.Mock;
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
	public void testMessageMapperIsEmptyByDefaultAndSupportsMapping() {
		PageMessageMapper<Integer> pageMessageMapper = () -> "mediaType";

		HttpHeaders httpHeaders = Mockito.mock(HttpHeaders.class);

		assertThat(pageMessageMapper.supports(page, httpHeaders), is(true));
	}

	@Test
	public void testMessageMapperRestItemMethodsOnSingleModelMapperByDefault() {
		@SuppressWarnings("unchecked")
		SingleModelMessageMapper<Integer> singleModelMessageMapper =
			Mockito.mock(SingleModelMessageMapper.class);

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

		pageMessageMapper.mapItemBooleanField(null, null, null, null);
		pageMessageMapper.mapItemEmbeddedResourceBooleanField(
			null, null, null, null, null);
		pageMessageMapper.mapItemEmbeddedResourceLink(
			null, null, null, null, null);
		pageMessageMapper.mapItemEmbeddedResourceNumberField(
			null, null, null, null, null);
		pageMessageMapper.mapItemEmbeddedResourceStringField(
			null, null, null, null, null);
		pageMessageMapper.mapItemEmbeddedResourceTypes(null, null, null, null);
		pageMessageMapper.mapItemEmbeddedResourceURL(null, null, null, null);
		pageMessageMapper.mapItemLink(null, null, null, null);
		pageMessageMapper.mapItemLinkedResourceURL(null, null, null, null);
		pageMessageMapper.mapItemNumberField(null, null, null, null);
		pageMessageMapper.mapItemSelfURL(null, null, null);
		pageMessageMapper.mapItemStringField(null, null, null, null);
		pageMessageMapper.mapItemTypes(null, null, null);

		Mockito.verify(
			singleModelMessageMapper
		).mapBooleanField(
			null, null, null
		);

		Mockito.verify(
			singleModelMessageMapper
		).mapEmbeddedResourceBooleanField(
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
		).mapEmbeddedResourceStringField(
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
		).mapTypes(
			null, null
		);
	}

	@Mock
	public Page<Integer> page;

}