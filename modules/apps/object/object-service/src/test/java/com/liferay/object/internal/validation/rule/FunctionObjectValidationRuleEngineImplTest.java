/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.validation.rule;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Map;

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
	public void testExecute() {
		FunctionObjectValidationRuleEngineImpl
			functionObjectValidationRuleEngineImpl = Mockito.spy(
				new FunctionObjectValidationRuleEngineImpl());

		Mockito.doReturn(
			Collections.emptyMap()
		).when(
			functionObjectValidationRuleEngineImpl
		).getResults(
			Mockito.any(Map.class), Mockito.anyLong()
		);

		long creatorId = RandomTestUtil.randomLong();

		Map<String, Object> baseModel = HashMapBuilder.<String, Object>put(
			"creator", creatorId
		).build();

		functionObjectValidationRuleEngineImpl.execute(
			HashMapBuilder.<String, Object>put(
				"baseModel", baseModel
			).build(),
			null);

		Mockito.verify(
			functionObjectValidationRuleEngineImpl, Mockito.times(1)
		).getResults(
			baseModel, creatorId
		);

		creatorId = RandomTestUtil.randomLong();

		Map<String, Object> entryDTO = HashMapBuilder.<String, Object>put(
			"creator", Collections.singletonMap("id", creatorId)
		).put(
			"dateCreated", RandomTestUtil.nextDate()
		).put(
			"dateModified", RandomTestUtil.nextDate()
		).put(
			"externalReferenceCode", RandomTestUtil.randomString()
		).put(
			"status", RandomTestUtil.randomBoolean()
		).build();

		functionObjectValidationRuleEngineImpl.execute(
			HashMapBuilder.<String, Object>put(
				"baseModel", baseModel
			).put(
				"entryDTO",
				HashMapBuilder.putAll(
					entryDTO
				).put(
					"properties", Collections.singletonMap("key", "value")
				).build()
			).build(),
			null);

		Mockito.verify(
			functionObjectValidationRuleEngineImpl, Mockito.times(1)
		).getResults(
			HashMapBuilder.putAll(
				entryDTO
			).put(
				"key", "value"
			).build(),
			creatorId
		);
	}

}