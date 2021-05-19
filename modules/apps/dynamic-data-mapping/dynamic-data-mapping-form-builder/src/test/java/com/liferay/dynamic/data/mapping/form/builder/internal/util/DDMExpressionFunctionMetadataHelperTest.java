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

package com.liferay.dynamic.data.mapping.form.builder.internal.util;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionTracker;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Carolina Barbosa
 */
@RunWith(PowerMockRunner.class)
public class DDMExpressionFunctionMetadataHelperTest {

	@Test
	public void testPopulateCustomDDMExpressionFunctionsMetadata()
		throws Exception {

		mockDDMExpressionFunctionTracker();

		Map<String, List<DDMExpressionFunctionMetadata>>
			ddmExpressionFunctionMetadatasMap = new HashMap<>();

		_ddmExpressionFunctionMetadataHelper.
			populateCustomDDMExpressionFunctionsMetadata(
				ddmExpressionFunctionMetadatasMap);

		Assert.assertEquals(
			ddmExpressionFunctionMetadatasMap.toString(), 2,
			ddmExpressionFunctionMetadatasMap.size());

		List<DDMExpressionFunctionMetadata> ddmExpressionFunctionMetadatas =
			ddmExpressionFunctionMetadatasMap.get("number");

		Assert.assertEquals(
			ddmExpressionFunctionMetadatas.toString(), 1,
			ddmExpressionFunctionMetadatas.size());

		Assert.assertEquals(
			new DDMExpressionFunctionMetadata(
				"binaryFunction", "binaryFunction", "boolean",
				new String[] {"number", "number"}),
			ddmExpressionFunctionMetadatas.get(0));

		ddmExpressionFunctionMetadatas = ddmExpressionFunctionMetadatasMap.get(
			"text");

		Assert.assertEquals(
			ddmExpressionFunctionMetadatas.toString(), 1,
			ddmExpressionFunctionMetadatas.size());

		Assert.assertEquals(
			new DDMExpressionFunctionMetadata(
				"binaryFunction", "binaryFunction", "boolean",
				new String[] {"text", "text"}),
			ddmExpressionFunctionMetadatas.get(0));
	}

	protected void mockDDMExpressionFunctionTracker() throws Exception {
		DDMExpressionFunctionTracker ddmExpressionFunctionTracker =
			Mockito.mock(DDMExpressionFunctionTracker.class);

		Mockito.when(
			ddmExpressionFunctionTracker.getCustomDDMExpressionFunctions()
		).thenReturn(
			HashMapBuilder.<String, DDMExpressionFunction>put(
				"binaryFunction", new BinaryFunction()
			).put(
				"ternayFunction", new TernaryFunction()
			).put(
				"unaryFunction", new UnaryFunction()
			).build()
		);

		PowerMockito.field(
			DDMExpressionFunctionMetadataHelper.class,
			"_ddmExpressionFunctionTracker"
		).set(
			_ddmExpressionFunctionMetadataHelper, ddmExpressionFunctionTracker
		);
	}

	private final DDMExpressionFunctionMetadataHelper
		_ddmExpressionFunctionMetadataHelper =
			new DDMExpressionFunctionMetadataHelper();

	private static class BinaryFunction
		implements DDMExpressionFunction.Function2<Object, Object, Boolean> {

		@Override
		public Boolean apply(Object object1, Object object2) {
			return true;
		}

		@Override
		public String getName() {
			return "binaryFunction";
		}

	}

	private static class TernaryFunction
		implements DDMExpressionFunction.Function3
			<Object, Object, Object, Boolean> {

		@Override
		public Boolean apply(Object object1, Object object2, Object object3) {
			return true;
		}

		@Override
		public String getName() {
			return "ternayFunction";
		}

	}

	private static class UnaryFunction
		implements DDMExpressionFunction.Function1<Object, String> {

		@Override
		public String apply(Object object) {
			return StringPool.BLANK;
		}

		@Override
		public String getName() {
			return "unaryFunction";
		}

	}

}