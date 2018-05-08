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

package com.liferay.apio.architect.endpoint;

import static com.liferay.apio.architect.test.util.result.TryMatchers.aFailTry;

import static java.nio.charset.StandardCharsets.UTF_8;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import com.liferay.apio.architect.file.BinaryFile;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.representor.Representor.Builder;
import com.liferay.apio.architect.single.model.SingleModel;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class BinaryEndpointTest {

	@Test
	public void testBinaryEndpointWithEmptyRepresentorReturnsFailure() {
		BinaryEndpoint binaryEndpoint = _getBinaryEndpoint(null);

		Try<BinaryFile> binaryFileTry =
			binaryEndpoint.getCollectionItemBinaryFileTry("", "", "");

		assertThat(binaryFileTry, is(aFailTry()));
	}

	@Test
	public void testBinaryEndpointWithFailSingleModelReturnsFailure() {
		BinaryEndpoint binaryEndpoint = new BinaryEndpoint(
			__ -> Optional.of(_representor()),
			(name, id) -> Try.fail(new IllegalArgumentException()));

		Try<BinaryFile> binaryFileTry =
			binaryEndpoint.getCollectionItemBinaryFileTry("", "", "");

		assertThat(binaryFileTry, is(aFailTry()));
	}

	@Test
	public void testBinaryEndpointWithNoPresentIdReturnFailure() {
		BinaryEndpoint binaryEndpoint = _getBinaryEndpoint(_representor());

		Try<BinaryFile> binaryFileTry =
			binaryEndpoint.getCollectionItemBinaryFileTry("", "", "");

		assertThat(binaryFileTry, is(aFailTry()));
	}

	@Test
	public void testBinaryEndpointWithValidFunctionsReturnInputStream() {
		BinaryEndpoint binaryEndpoint = _getBinaryEndpoint(_representor());

		Try<BinaryFile> binaryFileTry =
			binaryEndpoint.getCollectionItemBinaryFileTry("", "", "binary");

		BinaryFile binaryFile = binaryFileTry.getUnchecked();

		assertThat(binaryFile.getSize(), is(0L));

		InputStream inputStream = binaryFile.getInputStream();

		String result = Try.fromFallibleWithResources(
			() -> new BufferedReader(new InputStreamReader(inputStream)),
			BufferedReader::readLine).getUnchecked();

		assertThat(result, is("Apio"));
	}

	@Test
	public void testFunctionsReceiveResourceNamesAndBinaryId() {
		List<String> names = new ArrayList<>();

		BinaryEndpoint binaryEndpoint = new BinaryEndpoint(
			name -> {
				names.add(name);

				return Optional.of(_representor());
			},
			(name, id) -> {
				names.add(name);
				names.add(id);

				return Try.success(
					new SingleModel<>("apio", name, Collections.emptyList()));
			});

		binaryEndpoint.getCollectionItemBinaryFileTry("a", "b", "binaryId");

		assertThat(names, contains("a", "b", "a"));
	}

	private static BinaryEndpoint _getBinaryEndpoint(
		Representor<Object, Object> representor) {

		return new BinaryEndpoint(
			__ -> Optional.ofNullable(representor),
			(name, id) -> Try.success(
				new SingleModel<>("apio", name, Collections.emptyList())));
	}

	private static Representor<Object, Object> _representor() {
		Builder<Object, Object> builder = new Builder<>();

		return builder.types(
			""
		).identifier(
			Function.identity()
		).addBinary(
			"binary",
			__ -> new BinaryFile(
				new ByteArrayInputStream("Apio".getBytes(UTF_8)), 0L,
				"image/png")
		).build();
	}

}