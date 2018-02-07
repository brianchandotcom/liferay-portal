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

package com.liferay.apio.architect.message.json.plain.internal;

import static com.liferay.apio.architect.message.json.plain.internal.PlainJSONTestUtil.aRootElementJsonObjectWithId;
import static com.liferay.apio.architect.message.json.plain.internal.PlainJSONTestUtil.isALinkTo;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonArrayThat;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonInt;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonObjectWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.liferay.apio.architect.message.json.PageMessageMapper;
import com.liferay.apio.architect.test.util.json.Conditions;
import com.liferay.apio.architect.test.util.json.Conditions.Builder;
import com.liferay.apio.architect.test.util.model.RootModel;
import com.liferay.apio.architect.test.util.writer.MockPageWriter;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;

import org.hamcrest.Matcher;

import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Javier Gamarra
 * @author Alejandro Hernández
 */
public class PlainJSONPageMessageMapperTest {

	@Test
	public void testMediaTypeIsCorrect() {
		String mediaType = _pageMessageMapper.getMediaType();

		assertThat(mediaType, is("application/json"));
	}

	@Test
	public void testPlainJSONPageMessageMapper() {
		HttpHeaders httpHeaders = Mockito.mock(HttpHeaders.class);

		JsonObject jsonObject = MockPageWriter.write(
			httpHeaders, _pageMessageMapper);

		Builder builder = new Builder();

		Conditions conditions = builder.where(
			"collection", isALinkTo("localhost/p/name/id/root")
		).where(
			"elements", is(aJsonArrayThat(_containsTheElements))
		).where(
			"numberOfItems", is(aJsonInt(equalTo(3)))
		).where(
			"pages", _isAJsonObjectWithThePages
		).where(
			"self", isALinkTo("localhost/p/name/id/root?page=2&per_page=3")
		).where(
			"totalNumberOfItems", is(aJsonInt(equalTo(9)))
		).build();

		assertThat(jsonObject, is(aJsonObjectWith(conditions)));
	}

	private static final Matcher<Iterable<? extends JsonElement>>
		_containsTheElements;
	private static final Matcher<? extends JsonElement>
		_isAJsonObjectWithThePages;

	static {
		Builder builder = new Builder();

		Conditions pagesConditions = builder.where(
			"first", isALinkTo("localhost/p/name/id/root?page=1&per_page=3")
		).where(
			"last", isALinkTo("localhost/p/name/id/root?page=3&per_page=3")
		).where(
			"next", isALinkTo("localhost/p/name/id/root?page=3&per_page=3")
		).where(
			"prev", isALinkTo("localhost/p/name/id/root?page=1&per_page=3")
		).build();

		_isAJsonObjectWithThePages = is(aJsonObjectWith(pagesConditions));

		List<Matcher<? super JsonElement>> theElements = Arrays.asList(
			aRootElementJsonObjectWithId("1"),
			aRootElementJsonObjectWithId("2"),
			aRootElementJsonObjectWithId("3"));

		_containsTheElements = contains(theElements);
	}

	private final PageMessageMapper<RootModel> _pageMessageMapper =
		new PlainJSONPageMessageMapper<>();

}