/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.entries.frontend.data.set.filter.factory;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.filter.parser.ObjectFieldFilterContributorRegistry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectViewFilterColumn;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Pedro Leite
 */
public class ObjectFieldFDSFilterFactoryRegistryTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetObjectFieldFDSFilterFactory() throws Exception {
		ObjectFieldLocalService objectFieldLocalService = Mockito.mock(
			ObjectFieldLocalService.class);

		ObjectFieldFDSFilterFactoryRegistry
			objectFieldFDSFilterFactoryRegistry =
				new ObjectFieldFDSFilterFactoryRegistry(
					Mockito.mock(Language.class),
					Mockito.mock(ObjectFieldFilterContributorRegistry.class),
					objectFieldLocalService);

		ObjectViewFilterColumn objectViewFilterColumn = Mockito.mock(
			ObjectViewFilterColumn.class);

		Mockito.when(
			objectViewFilterColumn.getFilterType()
		).thenReturn(
			null
		);

		String objectFieldName = RandomTestUtil.randomString();

		Mockito.when(
			objectViewFilterColumn.getObjectFieldName()
		).thenReturn(
			objectFieldName
		);

		ObjectField objectField = Mockito.mock(ObjectField.class);

		Mockito.when(
			objectField.getBusinessType()
		).thenReturn(
			ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST
		);

		long objectDefinitionId = RandomTestUtil.randomLong();

		Mockito.when(
			objectFieldLocalService.getObjectField(
				objectDefinitionId, objectFieldName)
		).thenReturn(
			objectField
		);

		Assert.assertNotNull(
			objectFieldFDSFilterFactoryRegistry.getObjectFieldFDSFilterFactory(
				objectDefinitionId, objectViewFilterColumn));
	}

}