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

package com.liferay.apio.architect.representor;

import static com.liferay.apio.architect.representor.RepresentorTestUtil.testFields;
import static com.liferay.apio.architect.representor.RepresentorTestUtil.testRelatedModel;

import static java.util.Arrays.asList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.related.RelatedCollection;
import com.liferay.apio.architect.related.RelatedModel;
import com.liferay.apio.architect.representor.Representor.Builder;
import com.liferay.apio.architect.representor.dummy.Dummy;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class RepresentorTest {

	@Before
	public void setUp() {
		_keys = new ArrayList<>();
		_classes = new ArrayList<>();
		_relatedCollectionsClasses = new ArrayList<>();

		Builder<Dummy, Integer> builder = new Builder<>(
			IntegerIdentifier.class,
			(clazz, relatedCollection) -> {
				_classes.add(clazz);
				_keys.add(relatedCollection.getKey());
				_relatedCollectionsClasses.add(
					relatedCollection.getIdentifierClass());
			},
			() -> Collections.singletonList(
				new RelatedCollection<>("extra", IntegerIdentifier.class)));

		_representor = builder.types(
			"Type 1", "Type 2", "Type 3"
		).identifier(
			dummy -> dummy.id
		).addBinary(
			"binary1", dummy -> dummy.binaryFile1
		).addBinary(
			"binary2", dummy -> dummy.binaryFile2
		).addBidirectionalModel(
			"bidirectional1", "dummy1", IntegerIdentifier.class,
			dummy -> dummy.relatedModelId1
		).addBidirectionalModel(
			"bidirectional2", "dummy2", IntegerIdentifier.class,
			dummy -> dummy.relatedModelId2
		).addBoolean(
			"boolean1", dummy -> dummy.boolean1
		).addBoolean(
			"boolean2", dummy -> dummy.boolean2
		).addBooleanList(
			"booleanList1", dummy -> dummy.booleanList1
		).addBooleanList(
			"booleanList2", dummy -> dummy.booleanList2
		).addDate(
			"date1", dummy -> dummy.date1
		).addDate(
			"date2", dummy -> dummy.date2
		).addDate(
			"nullDate", __ -> null
		).addLink(
			"link1", "Link 1"
		).addLink(
			"link2", "Link 2"
		).addLinkedModel(
			"linked1", IntegerIdentifier.class, dummy -> dummy.relatedModelId3
		).addLinkedModel(
			"linked2", IntegerIdentifier.class, dummy -> dummy.relatedModelId4
		).addLocalizedStringByLanguage(
			"localized1", Dummy::getLocalizedString1
		).addLocalizedStringByLocale(
			"localized2", Dummy::getLocalizedString2
		).addNumber(
			"number1", dummy -> dummy.number1
		).addNumber(
			"number2", dummy -> dummy.number2
		).addNumberList(
			"numberList1", dummy -> dummy.numberList1
		).addNumberList(
			"numberList2", dummy -> dummy.numberList2
		).addRelatedCollection(
			"relatedCollection", IntegerIdentifier.class
		).addRelatedCollection(
			"relatedCollection", IntegerIdentifier.class
		).addString(
			"string1", Dummy::getString1
		).addString(
			"string2", Dummy::getString2
		).addStringList(
			"stringList1", dummy -> dummy.stringList1
		).addStringList(
			"stringList2", dummy -> dummy.stringList2
		).build();
	}

	@Test
	public void testBidirectionalFields() {
		assertThat(_keys, contains("dummy1", "dummy2"));
		assertThat(
			_classes,
			contains(IntegerIdentifier.class, IntegerIdentifier.class));

		assertThat(
			_relatedCollectionsClasses,
			contains(IntegerIdentifier.class, IntegerIdentifier.class));
	}

	@Test
	public void testBinaryFunctions() {
		testFields(
			_dummy, _representor.getBinaryFunctions(),
			binaryFile -> Try.fromFallibleWithResources(
				() -> new BufferedReader(new InputStreamReader(
					binaryFile.getInputStream())),
				BufferedReader::readLine).getUnchecked(),
			asList("binary1", "binary2"),
			asList("Input Stream 1", "Input Stream 2"));
	}

	@Test
	public void testIdentifier() {
		assertThat(_representor.getIdentifier(_dummy), is(23));
	}

	@Test
	public void testLinks() {
		testFields(
			_dummy, _representor.getLinkFunctions(), asList("link1", "link2"),
			asList("Link 1", "Link 2"));
	}

	@Test
	public void testPrimitiveFunctions() {
		testFields(
			_dummy, _representor.getBooleanFunctions(),
			asList("boolean1", "boolean2"), asList(true, false));

		testFields(
			_dummy, _representor.getLocalizedStringFunctions(),
			function -> function.apply(() -> Locale.US),
			asList("localized1", "localized2"), asList("en1", "en2"));

		testFields(
			_dummy, _representor.getNumberFunctions(),
			asList("number1", "number2"), asList(1L, 2L));

		testFields(
			_dummy, _representor.getStringFunctions(),
			asList("date1", "date2", "nullDate", "string1", "string2"),
			asList(
				"2016-06-15T09:00Z", "2017-04-03T18:36Z", null, "String 1",
				"String 2"));
	}

	@Test
	public void testPrimitiveListFunctions() {
		testFields(
			_dummy, _representor.getBooleanListFunctions(),
			asList("booleanList1", "booleanList2"),
			asList(
				asList(true, false, false, true),
				asList(false, false, true, false)));

		testFields(
			_dummy, _representor.getNumberListFunctions(),
			asList("numberList1", "numberList2"),
			asList(asList(1, 2, 3, 4, 5), asList(6, 7, 8, 9, 10)));

		testFields(
			_dummy, _representor.getStringListFunctions(),
			asList("stringList1", "stringList2"),
			asList(asList("a", "b", "c", "d"), asList("e", "f", "g", "h")));
	}

	@Test
	public void testRelatedCollections() {
		Stream<RelatedCollection<?>> relatedCollections =
			_representor.getRelatedCollections();

		List<?> list0 = relatedCollections.filter(
			relatedCollection ->
				relatedCollection.getIdentifierClass() ==
					IntegerIdentifier.class
		).filter(
			relatedCollection ->
				relatedCollection.getKey().equals("relatedCollection")
		).collect(
			Collectors.toList()
		);

		assertThat(list0, hasSize(2));
	}

	@Test
	public void testRelatedModels() {
		List<RelatedModel<Dummy, ?>> relatedModels =
			_representor.getRelatedModels();

		assertThat(relatedModels, hasSize(4));

		testRelatedModel(
			relatedModels.get(0), _dummy, "bidirectional1",
			IntegerIdentifier.class, 1);

		testRelatedModel(
			relatedModels.get(1), _dummy, "bidirectional2",
			IntegerIdentifier.class, 2);

		testRelatedModel(
			relatedModels.get(2), _dummy, "linked1", IntegerIdentifier.class,
			3);

		testRelatedModel(
			relatedModels.get(3), _dummy, "linked2", IntegerIdentifier.class,
			4);
	}

	@Test
	public void testTypes() {
		List<String> types = _representor.getTypes();

		assertThat(types, contains("Type 1", "Type 2", "Type 3"));
	}

	private List<Class> _classes;
	private final Dummy _dummy = new Dummy(23);
	private List<String> _keys;
	private List<Class<?>> _relatedCollectionsClasses;
	private Representor<Dummy> _representor;

	private interface IntegerIdentifier extends Identifier<Integer> {
	}

}