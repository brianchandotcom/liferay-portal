/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Magdalena Jedraszak
 */
public class ObjectDefinitionImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetRESTContextPath() {

		// Modifiable custom object definition

		_testGetRESTContextPath(
			"/c/customobjects", true, "CustomObject", null, false);

		// Modifiable system object definition

		_testGetRESTContextPath(
			"/headless-builder/endpoints", true, "APIEndpoint", null, true);

		// Unmodifiable system object definition

		_testGetRESTContextPath("", false, "AccountEntry", null, true);
	}

	@FeatureFlags("LPS-187142")
	@Test
	public void testGetRESTContextPathRootDescendantNode() {

		// Modifiable custom object definition

		_testGetRESTContextPath(
			"/c/rootobjects/customobjects", true, "CustomObject", "RootObject",
			false);

		// Modifiable system object definition

		_testGetRESTContextPath(
			"/headless-builder/applications/endpoints", true, "APIEndpoint",
			"APIApplication", true);
		_testGetRESTContextPath(
			"/commerce-returns/commerce-return-items", true,
			"CommerceReturnItem", "CommerceReturn", true);
	}

	private void _testGetRESTContextPath(
		String expectedRESTContextPath, boolean modifiable,
		String objectDefinitionName, String rootObjectDefinitionName,
		boolean system) {

		ObjectDefinition objectDefinition = Mockito.spy(
			new ObjectDefinitionImpl());

		objectDefinition.setModifiable(modifiable);
		objectDefinition.setName(objectDefinitionName);
		objectDefinition.setSystem(system);

		if (!modifiable && system) {
			try {
				objectDefinition.getRESTContextPath();
				Assert.fail();
			}
			catch (UnsupportedOperationException
						unsupportedOperationException) {

				Assert.assertNotNull(unsupportedOperationException);
			}
		}
		else {
			if (!system) {
				objectDefinition.setPluralLabel(
					TextFormatter.formatPlural(
						StringUtil.lowerCaseFirstLetter(objectDefinitionName)));
			}

			ObjectDefinitionLocalService objectDefinitionLocalService =
				Mockito.mock(ObjectDefinitionLocalService.class);

			ReflectionTestUtil.setFieldValue(
				ObjectDefinitionLocalServiceUtil.class, "_serviceSnapshot",
				new Snapshot<ObjectDefinitionLocalService>(
					ObjectDefinitionLocalServiceUtil.class,
					ObjectDefinitionLocalService.class) {

					@Override
					public ObjectDefinitionLocalService get() {
						return objectDefinitionLocalService;
					}

				});

			if (rootObjectDefinitionName != null) {
				ObjectDefinition rootObjectDefinition =
					new ObjectDefinitionImpl();

				rootObjectDefinition.setName(rootObjectDefinitionName);

				long rootObjectDefinitionId = RandomTestUtil.randomLong();

				objectDefinition.setRootObjectDefinitionId(
					rootObjectDefinitionId);

				if (!system) {
					objectDefinition.setPluralLabel(
						TextFormatter.formatPlural(
							StringUtil.lowerCaseFirstLetter(
								rootObjectDefinitionName)));
				}

				Mockito.when(
					objectDefinitionLocalService.fetchObjectDefinition(
						rootObjectDefinitionId)
				).thenReturn(
					rootObjectDefinition
				);

				Mockito.doReturn(
					true
				).when(
					objectDefinition
				).isRootDescendantNode();
			}

			Assert.assertEquals(
				expectedRESTContextPath, objectDefinition.getRESTContextPath());
		}
	}

}