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

package com.liferay.apio.architect.test.util.representor;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.test.util.identifier.FirstEmbeddedId;
import com.liferay.apio.architect.test.util.identifier.RootModelId;
import com.liferay.apio.architect.test.util.identifier.SecondEmbeddedId;
import com.liferay.apio.architect.test.util.identifier.ThirdEmbeddedId;
import com.liferay.apio.architect.test.util.model.FirstEmbeddedModel;
import com.liferay.apio.architect.test.util.model.RootModel;
import com.liferay.apio.architect.test.util.model.SecondEmbeddedModel;
import com.liferay.apio.architect.test.util.model.ThirdEmbeddedModel;

import java.util.Date;
import java.util.function.Function;

/**
 * Provides methods that create {@link Representor} objects for {@link
 * RootModel}, {@link FirstEmbeddedModel}, {@link SecondEmbeddedModel} or {@link
 * ThirdEmbeddedModel}.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 */
public class MockRepresentorCreator {

	/**
	 * Creates a mock {@code Representor} for {@code FirstEmbeddedModel}.
	 *
	 * @return the mock {@code Representor} for {@code FirstEmbeddedModel}
	 */
	public static Representor<FirstEmbeddedModel, String>
		createFirstEmbeddedModelRepresentor() {

		Representor.Builder<FirstEmbeddedModel, String> builder =
			new Representor.Builder<>(FirstEmbeddedId.class);

		return builder.types(
			"Type"
		).identifier(
			FirstEmbeddedModel::getId
		).addBinary(
			"binary", __ -> null
		).addBoolean(
			"boolean", __ -> true
		).addBooleanList(
			"booleanList", __ -> asList(true, false)
		).addLink(
			"link", "www.liferay.com"
		).addLinkedModel(
			"embedded", SecondEmbeddedId.class, __ -> "first"
		).addLinkedModel(
			"linked", SecondEmbeddedId.class, __ -> "second"
		).addLocalizedString(
			"localizedString", (firstEmbeddedModel, language) -> "Translated"
		).addNumber(
			"number", __ -> 42
		).addNumberList(
			"numberList", __ -> asList(1, 2)
		).addRelatedCollection(
			"relatedCollection", SecondEmbeddedId.class
		).addString(
			"string", __ -> "A string"
		).addStringList(
			"stringList", __ -> asList("a", "b")
		).build();
	}

	/**
	 * Creates a mock {@code Representor} for {@code RootModel}.
	 *
	 * @param  activateNulls whether to add {@code null} empty values
	 * @return the mock {@code Representor} for {@code RootModel}
	 */
	public static Representor<RootModel, String>
		createRootModelRepresentor(boolean activateNulls) {

		Representor.Builder<RootModel, String> builder =
			new Representor.Builder<>(RootModelId.class);

		Representor.Builder<RootModel, String>.FirstStep firstStepBuilder =
			builder.types(
				"Type 1", "Type 2"
			).identifier(
				RootModel::getId
			).addBinary(
				"binary1", __ -> null
			).addBinary(
				"binary2", __ -> null
			).addBoolean(
				"boolean1", __ -> true
			).addBoolean(
				"boolean2", __ -> false
			).addBooleanList(
				"booleanList1", __ -> asList(true, true, false, false)
			).addBooleanList(
				"booleanList2", __ -> asList(true, false, true, false)
			).addDate(
				"date1", __ -> new Date(1465981200000L)
			).addDate(
				"date2", __ -> new Date(1491244560000L)
			).addLinkedModel(
				"embedded1", FirstEmbeddedId.class, __ -> "first"
			).addLinkedModel(
				"embedded2", FirstEmbeddedId.class, __ -> "second"
			).addLinkedModel(
				"linked1", FirstEmbeddedId.class, __ -> "third"
			).addLinkedModel(
				"linked2", FirstEmbeddedId.class, __ -> "fourth"
			).addLink(
				"link1", "www.liferay.com"
			).addLink(
				"link2", "community.liferay.com"
			).addLocalizedString(
				"localizedString1", (model, language) -> "Translated 1"
			).addLocalizedString(
				"localizedString2", (model, language) -> "Translated 2"
			).addNumber(
				"number1", __ -> 2017
			).addNumber(
				"number2", __ -> 42
			).addNumberList(
				"numberList1", __ -> asList(1, 2, 3, 4, 5)
			).addNumberList(
				"numberList2", __ -> asList(6, 7, 8, 9, 10)
			).addRelatedCollection(
				"relatedCollection1", FirstEmbeddedId.class
			).addRelatedCollection(
				"relatedCollection2", FirstEmbeddedId.class
			).addString(
				"string1", __ -> "Live long and prosper"
			).addString(
				"string2", __ -> "Hypermedia"
			).addStringList(
				"stringList1", __ -> asList("a", "b", "c", "d", "e")
			).addStringList(
				"stringList2", __ -> asList("f", "g", "h", "i", "j")
			).addNested(
				"nested1", __ -> (FirstEmbeddedModel)() -> "id 1",
				nestedBuilder -> nestedBuilder.nestedTypes(
					"Type 3"
				).addNumber(
					"number1", __ -> 2017
				).addString(
					"string1", FirstEmbeddedModel::getId
				).addString(
					"string2", __ -> "string2"
				).build()
			).addNested(
				"nested2", rootModel -> (SecondEmbeddedModel)rootModel::getId,
				nestedBuilder -> nestedBuilder.nestedTypes(
					"Type 4"
				).addBidirectionalModel(
					"bidirectionalModel3", "bidirectionalKey",
					FirstEmbeddedId.class,
					(Function<SecondEmbeddedModel, String>)
						SecondEmbeddedModel::getId
				).addString(
					"string1", SecondEmbeddedModel::getId
				).addNumber(
					"number1", __ -> 42
				).addLinkedModel(
					"linked3", ThirdEmbeddedId.class, __ -> "fifth"
				).addNested(
					"nested3", __ -> () -> "id 3",
					(Representor.Builder<ThirdEmbeddedModel, ?>
						thirdEmbeddedModelBuilder) ->
						thirdEmbeddedModelBuilder.nestedTypes(
							"Type 5"
						).addString(
							"string1", ThirdEmbeddedModel::getId
						).build()
				).addNumber(
					"number1", __ -> 42
				).addRelatedCollection(
					"relatedCollection3", ThirdEmbeddedId.class
				).addString(
					"string1", SecondEmbeddedModel::getId
				).build()
			);

		if (activateNulls) {
			return firstStepBuilder.addBoolean(
				"boolean3", __ -> null
			).addLink(
				"link3", null
			).addLink(
				"link4", ""
			).addLocalizedString(
				"localizedString3", (model, language) -> null
			).addLocalizedString(
				"localizedString4", (model, language) -> ""
			).addNumber(
				"number3", __ -> null
			).addString(
				"string3", __ -> null
			).addString(
				"string4", __ -> ""
			).build();
		}

		return firstStepBuilder.build();
	}

	/**
	 * Creates a mock {@code Representor} for {@code SecondEmbeddedModel}.
	 *
	 * @return the mock {@code Representor} for {@code SecondEmbeddedModel}
	 */
	public static Representor<SecondEmbeddedModel, String>
		createSecondEmbeddedModelRepresentor() {

		Representor.Builder<SecondEmbeddedModel, String> builder =
			new Representor.Builder<>(SecondEmbeddedId.class);

		return builder.types(
			"Type"
		).identifier(
			SecondEmbeddedModel::getId
		).addBinary(
			"binary", __ -> null
		).addBoolean(
			"boolean", __ -> false
		).addBooleanList(
			"booleanList", __ -> singletonList(true)
		).addLink(
			"link", "community.liferay.com"
		).addLinkedModel(
			"embedded", ThirdEmbeddedId.class, __ -> "first"
		).addLinkedModel(
			"linked", ThirdEmbeddedId.class, __ -> "second"
		).addNumber(
			"number", __ -> 2017
		).addNumberList(
			"numberList", __ -> singletonList(1)
		).addRelatedCollection(
			"relatedCollection", ThirdEmbeddedId.class
		).addString(
			"string", __ -> "A string"
		).addStringList(
			"stringList", __ -> singletonList("a")
		).build();
	}

	/**
	 * Creates a mock {@code Representor} for {@code ThirdEmbeddedModel}.
	 *
	 * @return the mock {@code Representor} for {@code ThirdEmbeddedModel}
	 */
	public static Representor<ThirdEmbeddedModel, String>
		createThirdEmbeddedModelRepresentor() {

		Representor.Builder<ThirdEmbeddedModel, String> builder =
			new Representor.Builder<>(ThirdEmbeddedId.class);

		return builder.types(
			"Type"
		).identifier(
			ThirdEmbeddedModel::getId
		).build();
	}

	private MockRepresentorCreator() {
		throw new UnsupportedOperationException();
	}

}