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

package com.liferay.apio.architect.message.hal.internal;

import static com.liferay.apio.architect.message.hal.internal.HALTestUtil.aRootElementJsonObjectWithId;
import static com.liferay.apio.architect.message.hal.internal.HALTestUtil.isALinkTo;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonArrayThat;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonInt;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonObjectWhere;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonObjectWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.test.util.json.Conditions;
import com.liferay.apio.architect.test.util.model.RootModel;
import com.liferay.apio.architect.test.util.writer.MockPageWriter;
import com.liferay.apio.architect.test.util.writer.MockWriterUtil;
import com.liferay.apio.architect.unsafe.Unsafe;
import com.liferay.apio.architect.wiring.osgi.manager.representable.RepresentableManager;

import java.util.Optional;

import javax.ws.rs.core.HttpHeaders;

import org.hamcrest.Matcher;

import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Alejandro Hernández
 * @author Javier Gamarra
 */
public class HALPageMessageMapperTest implements RepresentableManager {

	public HALPageMessageMapperTest() {
		_pageMessageMapper = new HALPageMessageMapper<>();

		_pageMessageMapper.representableManager = this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T, U> Optional<Representor<T, U>> getRepresentorOptional(
		String name) {

		Optional<Representor<?, ?>> optional =
			MockWriterUtil.getRepresentorOptional(name);

		return optional.map(Unsafe::unsafeCast);
	}

	@Test
	public void testHALPageMessageMapper() {
		HttpHeaders httpHeaders = Mockito.mock(HttpHeaders.class);

		JsonObject jsonObject = MockPageWriter.write(
			httpHeaders, _pageMessageMapper);

		Conditions.Builder builder = new Conditions.Builder();

		Conditions conditions = builder.where(
			"_embedded", _isAJsonObjectWithTheEmbedded
		).where(
			"_links", _isAJsonObjectWithTheLinks
		).where(
			"count", is(aJsonInt(equalTo(3)))
		).where(
			"total", is(aJsonInt(equalTo(9)))
		).build();

		assertThat(jsonObject, is(aJsonObjectWith(conditions)));
	}

	@Test
	public void testMediaTypeIsCorrect() {
		String mediaType = _pageMessageMapper.getMediaType();

		assertThat(mediaType, is("application/hal+json"));
	}

	private static final Matcher<JsonElement> _isAJsonObjectWithTheEmbedded;
	private static final Matcher<JsonElement> _isAJsonObjectWithTheLinks;

	static {
		Conditions.Builder builder = new Conditions.Builder();

		@SuppressWarnings("unchecked")
		Matcher<Iterable<? extends JsonElement>> containsTheEmbedded = contains(
			aRootElementJsonObjectWithId("1"),
			aRootElementJsonObjectWithId("2"),
			aRootElementJsonObjectWithId("3"));

		_isAJsonObjectWithTheEmbedded = is(
			aJsonObjectWhere(
				"Type 1", is(aJsonArrayThat(containsTheEmbedded))));

		Conditions linkConditions = builder.where(
			"collection", isALinkTo("localhost/p/name/id/root")
		).where(
			"first", isALinkTo("localhost/p/name/id/root?page=1&per_page=3")
		).where(
			"last", isALinkTo("localhost/p/name/id/root?page=3&per_page=3")
		).where(
			"next", isALinkTo("localhost/p/name/id/root?page=3&per_page=3")
		).where(
			"prev", isALinkTo("localhost/p/name/id/root?page=1&per_page=3")
		).where(
			"self", isALinkTo("localhost/p/name/id/root?page=2&per_page=3")
		).build();

		_isAJsonObjectWithTheLinks = is(aJsonObjectWith(linkConditions));
	}

	private final HALPageMessageMapper<RootModel> _pageMessageMapper;

}