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
	public void testGetRESTContextPathOfModifiableSystemObjectNoRootDescendantNode() {
		_testGetRESTContextPathOfModifiableSystemObject(
			"/headless-builder/endpoints", "APIEndpoint", null);
	}

	@FeatureFlags("LPS-187142")
	@Test
	public void testGetRESTContextPathOfModifiableSystemObjectRootDescendantNode() {
		_testGetRESTContextPathOfModifiableSystemObject(
			"/headless-builder/applications/endpoints", "APIEndpoint",
			"APIApplication");
		_testGetRESTContextPathOfModifiableSystemObject(
			"/commerce-returns/commerce-return-items", "CommerceReturnItem",
			"CommerceReturn");
	}

	private void _testGetRESTContextPathOfModifiableSystemObject(
		String expectedRESTContextPath, String objectDefinitionName,
		String rootObjectDefinitionName) {

		ObjectDefinition objectDefinition = Mockito.spy(
			new ObjectDefinitionImpl());

		objectDefinition.setModifiable(true);
		objectDefinition.setName(objectDefinitionName);
		objectDefinition.setSystem(true);

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
			ObjectDefinition rootObjectDefinition = new ObjectDefinitionImpl();

			rootObjectDefinition.setModifiable(true);
			rootObjectDefinition.setName(rootObjectDefinitionName);
			rootObjectDefinition.setSystem(true);

			long rootObjectDefinitionId = RandomTestUtil.randomLong();

			objectDefinition.setRootObjectDefinitionId(rootObjectDefinitionId);

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