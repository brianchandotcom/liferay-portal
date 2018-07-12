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

package com.liferay.apio.architect.impl.documentation;

import static com.spotify.hamcrest.optional.OptionalMatchers.emptyOptional;
import static com.spotify.hamcrest.optional.OptionalMatchers.optionalWithValue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.collection.IsMapWithSize.aMapWithSize;
import static org.hamcrest.collection.IsMapWithSize.anEmptyMap;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.hamcrest.Matcher;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class DocumentationTest {

	@Test
	public void testDocumentationWithEmptyValuesReturnEmpty() {
		Documentation documentation = new Documentation(
			Optional::empty, Optional::empty, Collections::emptyMap,
			Collections::emptyMap, Collections::emptyMap,
			Collections::emptyMap);

		Optional<String> optionalTitle = documentation.getAPITitleOptional();

		Optional<String> optionalDescription =
			documentation.getAPIDescriptionOptional();

		assertThat(optionalTitle, is(emptyOptional()));
		assertThat(optionalDescription, is(emptyOptional()));

		assertThat(documentation.getRepresentors(), is(anEmptyMap()));
		assertThat(documentation.getCollectionRoutes(), is(anEmptyMap()));
		assertThat(documentation.getItemRoutes(), is(anEmptyMap()));
		assertThat(documentation.getNestedCollectionRoutes(), is(anEmptyMap()));
	}

	@Test
	public void testDocumentationWithNonemptyValuesReturnThem() {
		Documentation documentation = new Documentation(
			() -> Optional.of(() -> "A"), () -> Optional.of(() -> "B"),
			() -> Collections.singletonMap("r", null),
			() -> Collections.singletonMap("c", null),
			() -> Collections.singletonMap("i", null),
			() -> Collections.singletonMap("n", null));

		Optional<String> optionalTitle = documentation.getAPITitleOptional();

		Optional<String> optionalDescription =
			documentation.getAPIDescriptionOptional();

		assertThat(optionalTitle, is(optionalWithValue(equalTo("A"))));
		assertThat(optionalDescription, is(optionalWithValue(equalTo("B"))));
		assertThat(documentation.getRepresentors(), _HAS_SIZE_ONE);
		assertThat(documentation.getRepresentors(), hasKey("r"));
		assertThat(documentation.getCollectionRoutes(), _HAS_SIZE_ONE);
		assertThat(documentation.getCollectionRoutes(), hasKey("c"));
		assertThat(documentation.getItemRoutes(), _HAS_SIZE_ONE);
		assertThat(documentation.getItemRoutes(), hasKey("i"));
		assertThat(documentation.getNestedCollectionRoutes(), _HAS_SIZE_ONE);
		assertThat(documentation.getNestedCollectionRoutes(), hasKey("n"));
	}

	private static final Matcher<Map<?, ?>> _HAS_SIZE_ONE = is(aMapWithSize(1));

}