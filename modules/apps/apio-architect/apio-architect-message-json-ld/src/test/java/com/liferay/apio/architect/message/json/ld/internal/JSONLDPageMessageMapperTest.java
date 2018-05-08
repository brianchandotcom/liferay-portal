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

package com.liferay.apio.architect.message.json.ld.internal;

import static com.liferay.apio.architect.message.json.ld.internal.JSONLDTestUtil.IS_A_LINK_TO_HYDRA_PROFILE;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDTestUtil.aRootElementJsonObjectWithId;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDTestUtil.containsTheTypes;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDTestUtil.isALinkTo;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonArrayThat;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonInt;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonObjectWhere;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonObjectWith;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonString;

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
public class JSONLDPageMessageMapperTest {

	@Test
	public void testJSONLDPageMessageMapper() {
		HttpHeaders httpHeaders = Mockito.mock(HttpHeaders.class);

		JsonObject jsonObject = MockPageWriter.write(
			httpHeaders, _pageMessageMapper);

		Builder builder = new Builder();

		Conditions conditions = builder.where(
			"@context", is(aJsonArrayThat(contains(_theContext)))
		).where(
			"@id", isALinkTo("localhost/p/name/id/root")
		).where(
			"@type", containsTheTypes("Collection")
		).where(
			"member", is(aJsonArrayThat(contains(_theMembers)))
		).where(
			"numberOfItems", is(aJsonInt(equalTo(3)))
		).where(
			"operation", is(aJsonArrayThat(_containsTheOperations))
		).where(
			"totalItems", is(aJsonInt(equalTo(9)))
		).where(
			"view", _isAJsonObjectWithTheView
		).build();

		assertThat(jsonObject, is(aJsonObjectWith(conditions)));
	}

	@Test
	public void testMediaTypeIsCorrect() {
		String mediaType = _pageMessageMapper.getMediaType();

		assertThat(mediaType, is("application/ld+json"));
	}

	private static final Matcher<Iterable<? extends JsonElement>>
		_containsTheOperations;
	private static final Matcher<? extends JsonElement>
		_isAJsonObjectWithTheView;
	private static final List<Matcher<? super JsonElement>> _theContext;
	private static final List<Matcher<? super JsonElement>> _theMembers;

	static {
		Builder builder = new Builder();

		Conditions viewConditions = builder.where(
			"@type", containsTheTypes("PartialCollectionView")
		).where(
			"@id", isALinkTo("localhost/p/name/id/root?page=2&per_page=3")
		).where(
			"first", isALinkTo("localhost/p/name/id/root?page=1&per_page=3")
		).where(
			"last", isALinkTo("localhost/p/name/id/root?page=3&per_page=3")
		).where(
			"next", isALinkTo("localhost/p/name/id/root?page=3&per_page=3")
		).where(
			"previous", isALinkTo("localhost/p/name/id/root?page=1&per_page=3")
		).build();

		_isAJsonObjectWithTheView = is(aJsonObjectWith(viewConditions));

		_theContext = Arrays.asList(
			aJsonObjectWhere(
				"@vocab", is(aJsonString(equalTo("http://schema.org/")))),
			IS_A_LINK_TO_HYDRA_PROFILE);

		_theMembers = Arrays.asList(
			aRootElementJsonObjectWithId("1", false, true),
			aRootElementJsonObjectWithId("2", false, true),
			aRootElementJsonObjectWithId("3", false, true));

		Conditions operationConditions = builder.where(
			"@id", is(aJsonString(equalTo("_:create-operation")))
		).where(
			"@type", is(aJsonString(equalTo("Operation")))
		).where(
			"expects", is(aJsonString(equalTo("localhost/f/c/p")))
		).where(
			"method", is(aJsonString(equalTo("POST")))
		).build();

		_containsTheOperations = contains(aJsonObjectWith(operationConditions));
	}

	private final PageMessageMapper<RootModel> _pageMessageMapper =
		new JSONLDPageMessageMapper<>();

}