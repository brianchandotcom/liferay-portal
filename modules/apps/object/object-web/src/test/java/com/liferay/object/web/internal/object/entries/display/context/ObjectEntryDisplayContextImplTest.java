/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.entries.display.context;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.item.selector.ItemSelector;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectWebKeys;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeRegistry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectLayoutLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Aquiles Duarte
 */
public class ObjectEntryDisplayContextImplTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetObjectFieldBusinessType() {
		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.when(
			httpServletRequest.getAttribute(
				ObjectWebKeys.OBJECT_ENTRY_READ_ONLY)
		).thenReturn(
			false
		);

		ObjectField objectField = Mockito.mock(ObjectField.class);

		Mockito.when(
			objectField.getBusinessType()
		).thenReturn(
			ObjectFieldConstants.BUSINESS_TYPE_DATE
		);

		Mockito.when(
			objectField.isMetadata()
		).thenReturn(
			true
		);

		ObjectFieldBusinessType objectFieldBusinessType = Mockito.mock(
			ObjectFieldBusinessType.class);

		ObjectFieldBusinessTypeRegistry objectFieldBusinessTypeRegistry =
			Mockito.mock(ObjectFieldBusinessTypeRegistry.class);

		Mockito.when(
			objectFieldBusinessTypeRegistry.getObjectFieldBusinessType(
				ObjectFieldConstants.BUSINESS_TYPE_DATE_TIME)
		).thenReturn(
			objectFieldBusinessType
		);

		ObjectEntryDisplayContextImpl objectEntryDisplayContextImpl =
			new ObjectEntryDisplayContextImpl(
				Mockito.mock(DDMExpressionFactory.class),
				Mockito.mock(DDMFormRenderer.class), httpServletRequest,
				Mockito.mock(ItemSelector.class),
				Mockito.mock(ObjectDefinitionLocalService.class),
				Mockito.mock(ObjectEntryManagerRegistry.class),
				Mockito.mock(ObjectEntryLocalService.class),
				Mockito.mock(ObjectEntryService.class),
				objectFieldBusinessTypeRegistry,
				Mockito.mock(ObjectFieldLocalService.class),
				Mockito.mock(ObjectLayoutLocalService.class),
				Mockito.mock(ObjectRelationshipLocalService.class),
				Mockito.mock(ObjectScopeProviderRegistry.class));

		Assert.assertSame(
			objectFieldBusinessType,
			ReflectionTestUtil.invoke(
				objectEntryDisplayContextImpl, "_getObjectFieldBusinessType",
				new Class<?>[] {ObjectField.class},
				new Object[] {objectField}));
	}

}