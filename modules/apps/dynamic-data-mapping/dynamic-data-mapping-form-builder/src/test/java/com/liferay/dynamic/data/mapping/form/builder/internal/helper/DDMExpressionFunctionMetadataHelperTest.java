/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.builder.internal.helper;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionRegistry;
import com.liferay.dynamic.data.mapping.form.builder.internal.util.DDMExpressionFunctionMetadata;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * @author Carolina Barbosa
 */
public class DDMExpressionFunctionMetadataHelperTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		Portal portal = Mockito.mock(Portal.class);

		Mockito.when(
			portal.getResourceBundle(Mockito.any(Locale.class))
		).thenReturn(
			_resourceBundle
		);
		ReflectionTestUtil.setFieldValue(
			_ddmExpressionFunctionMetadataHelper, "_portal", portal);

		ResourceBundleLoader resourceBundleLoader = Mockito.mock(
			ResourceBundleLoader.class);

		ResourceBundleLoaderUtil.setPortalResourceBundleLoader(
			resourceBundleLoader);

		Mockito.when(
			resourceBundleLoader.loadResourceBundle(Mockito.any(Locale.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);

		ReflectionTestUtil.setFieldValue(
			_ddmExpressionFunctionMetadataHelper, "_language",
			new LanguageImpl());
	}

	@Test
	public void testPopulateCustomDDMExpressionFunctionsMetadata() {
		_mockDDMExpressionFunctionRegistry();

		Map<String, List<DDMExpressionFunctionMetadata>>
			ddmExpressionFunctionMetadatasMap = new HashMap<>();

		_ddmExpressionFunctionMetadataHelper.
			populateCustomDDMExpressionFunctionsMetadata(
				ddmExpressionFunctionMetadatasMap, LocaleUtil.US);

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
				"binaryFunction", "Binary Function", "boolean",
				new String[] {"number", "number"}),
			ddmExpressionFunctionMetadatas.get(0));

		ddmExpressionFunctionMetadatas = ddmExpressionFunctionMetadatasMap.get(
			"text");

		Assert.assertEquals(
			ddmExpressionFunctionMetadatas.toString(), 1,
			ddmExpressionFunctionMetadatas.size());
		Assert.assertEquals(
			new DDMExpressionFunctionMetadata(
				"binaryFunction", "Binary Function", "boolean",
				new String[] {"text", "text"}),
			ddmExpressionFunctionMetadatas.get(0));
	}

	@Test
	public void testPopulateDDMExpressionFunctionsMetadata() {
		Map<String, List<DDMExpressionFunctionMetadata>>
			ddmExpressionFunctionMetadatasMap = new HashMap<>();

		_ddmExpressionFunctionMetadataHelper.
			populateDDMExpressionFunctionsMetadata(
				ddmExpressionFunctionMetadatasMap, _resourceBundle);

		Assert.assertEquals(
			ddmExpressionFunctionMetadatasMap.toString(), 4,
			ddmExpressionFunctionMetadatasMap.size());

		List<DDMExpressionFunctionMetadata> ddmExpressionFunctionMetadatas =
			ddmExpressionFunctionMetadatasMap.get("boolean");

		Assert.assertEquals(
			ddmExpressionFunctionMetadatas.toString(), 1,
			ddmExpressionFunctionMetadatas.size());

		ddmExpressionFunctionMetadatas = ddmExpressionFunctionMetadatasMap.get(
			"number");

		Assert.assertEquals(
			ddmExpressionFunctionMetadatas.toString(), 8,
			ddmExpressionFunctionMetadatas.size());

		ddmExpressionFunctionMetadatas = ddmExpressionFunctionMetadatasMap.get(
			"text");

		Assert.assertEquals(
			ddmExpressionFunctionMetadatas.toString(), 6,
			ddmExpressionFunctionMetadatas.size());

		ddmExpressionFunctionMetadatas = ddmExpressionFunctionMetadatasMap.get(
			"user");

		Assert.assertEquals(
			ddmExpressionFunctionMetadatas.toString(), 1,
			ddmExpressionFunctionMetadatas.size());
	}

	private void _mockDDMExpressionFunctionRegistry() {
		DDMExpressionFunctionRegistry ddmExpressionFunctionRegistry =
			Mockito.mock(DDMExpressionFunctionRegistry.class);

		Mockito.when(
			ddmExpressionFunctionRegistry.getCustomDDMExpressionFunctions()
		).thenReturn(
			HashMapBuilder.<String, DDMExpressionFunction>put(
				"binaryFunction", new BinaryFunction()
			).put(
				"ternayFunction", new TernaryFunction()
			).put(
				"unaryFunction", new UnaryFunction()
			).build()
		);

		ReflectionTestUtil.setFieldValue(
			_ddmExpressionFunctionMetadataHelper,
			"_ddmExpressionFunctionRegistry", ddmExpressionFunctionRegistry);
	}

	private static final DDMExpressionFunctionMetadataHelper
		_ddmExpressionFunctionMetadataHelper =
			new DDMExpressionFunctionMetadataHelper();

	@Mock
	private static ResourceBundle _resourceBundle;

	private static class BinaryFunction
		implements DDMExpressionFunction.Function2<Object, Object, Boolean> {

		@Override
		public Boolean apply(Object object1, Object object2) {
			return true;
		}

		@Override
		public String getLabel(Locale locale) {
			return "Binary Function";
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