/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.validation.rule;

import com.liferay.object.internal.configuration.FunctionObjectValidationRuleEngineImplConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.catapult.PortalCatapult;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Pedro Tavares
 */
public class FunctionObjectValidationRuleEngineImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testExecute() throws Exception {
		FunctionObjectValidationRuleEngineImpl
			functionObjectValidationRuleEngineImpl =
				new FunctionObjectValidationRuleEngineImpl();

		JSONFactory jsonFactory = new JSONFactoryImpl();

		ReflectionTestUtil.setFieldValue(
			functionObjectValidationRuleEngineImpl, "_jsonFactory",
			jsonFactory);

		FunctionObjectValidationRuleEngineImplConfiguration
			functionObjectValidationRuleEngineImplConfiguration = Mockito.mock(
				FunctionObjectValidationRuleEngineImplConfiguration.class);

		Mockito.when(
			functionObjectValidationRuleEngineImplConfiguration.
				oAuth2ApplicationExternalReferenceCode()
		).thenReturn(
			StringPool.BLANK
		);
		Mockito.when(
			functionObjectValidationRuleEngineImplConfiguration.resourcePath()
		).thenReturn(
			StringPool.BLANK
		);

		ReflectionTestUtil.setFieldValue(
			functionObjectValidationRuleEngineImpl,
			"_functionObjectValidationRuleEngineImplConfiguration",
			functionObjectValidationRuleEngineImplConfiguration);

		Future<byte[]> future = Mockito.mock(Future.class);

		String json = String.valueOf(
			jsonFactory.createJSONObject(
				Collections.singletonMap("validationCriteriaMet", true)));

		Mockito.when(
			future.get()
		).thenReturn(
			json.getBytes()
		);

		PortalCatapult portalCatapult = Mockito.mock(PortalCatapult.class);

		Mockito.when(
			portalCatapult.launch(
				Mockito.anyLong(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.anyLong())
		).thenReturn(
			future
		);

		ReflectionTestUtil.setFieldValue(
			functionObjectValidationRuleEngineImpl, "_portalCatapult",
			portalCatapult);

		Map<String, Object> results =
			functionObjectValidationRuleEngineImpl.execute(
				HashMapBuilder.<String, Object>put(
					"entryDTO",
					HashMapBuilder.<String, Object>put(
						"creator",
						Collections.singletonMap(
							"id", RandomTestUtil.randomInt())
					).put(
						"properties", Collections.emptyMap()
					).build()
				).build(),
				null);

		Assert.assertTrue((Boolean)results.get("validationCriteriaMet"));

		results = functionObjectValidationRuleEngineImpl.execute(
			HashMapBuilder.<String, Object>put(
				"baseModel",
				Collections.singletonMap("creator", RandomTestUtil.randomInt())
			).build(),
			null);

		Assert.assertTrue((Boolean)results.get("validationCriteriaMet"));
	}

}