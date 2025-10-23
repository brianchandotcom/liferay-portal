/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.entries.display.context;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectWebKeys;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeRegistry;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectLayoutLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.Map;

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
	public void testAddFieldsetDDMFormField() {
		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.getLocale()
		).thenReturn(
			LocaleUtil.SPAIN
		);

		Mockito.when(
			httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		ObjectEntryDisplayContextImpl objectEntryDisplayContextImpl =
			_createObjectEntryDisplayContextImpl(httpServletRequest);

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();
		String fieldName = RandomTestUtil.randomString();
		String label = RandomTestUtil.randomString();

		objectEntryDisplayContextImpl.addFieldsetDDMFormField(
			RandomTestUtil.randomBoolean(), ddmForm, fieldName, label,
			Collections.singletonList(new DDMFormField()), null);

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		DDMFormField ddmFormField = ddmFormFieldsMap.get(fieldName);

		LocalizedValue localizedValue = ddmFormField.getLabel();

		Assert.assertEquals(
			LocaleUtil.SPAIN, localizedValue.getDefaultLocale());
		Assert.assertEquals(label, localizedValue.getString(LocaleUtil.BRAZIL));
		Assert.assertEquals(label, localizedValue.getString(LocaleUtil.SPAIN));
		Assert.assertEquals(label, localizedValue.getString(LocaleUtil.US));
	}

	@Test
	public void testGetObjectEntry() throws Exception {
		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);
		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		ObjectDefinition objectDefinition = Mockito.mock(
			ObjectDefinition.class);

		String externalReferenceCode = RandomTestUtil.randomString();

		Mockito.when(
			httpServletRequest.getParameter("externalReferenceCode")
		).thenReturn(
			externalReferenceCode
		);

		Mockito.when(
			httpServletRequest.getAttribute(ObjectWebKeys.OBJECT_DEFINITION)
		).thenReturn(
			objectDefinition
		);

		String groupId = String.valueOf(RandomTestUtil.randomLong());

		Mockito.when(
			httpServletRequest.getAttribute(ObjectWebKeys.OBJECT_ENTRY_GROUP_ID)
		).thenReturn(
			groupId
		);

		Mockito.when(
			httpServletRequest.getAttribute(
				ObjectWebKeys.OBJECT_ENTRY_READ_ONLY)
		).thenReturn(
			false
		);

		Mockito.when(
			httpServletRequest.getParameter("objectRelationshipId")
		).thenReturn(
			"0"
		);

		Mockito.when(
			objectDefinition.getStorageType()
		).thenReturn(
			ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT
		);

		Mockito.when(
			httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		long companyId = RandomTestUtil.randomLong();

		Mockito.when(
			themeDisplay.getCompanyId()
		).thenReturn(
			companyId
		);

		Mockito.when(
			themeDisplay.getSiteDefaultLocale()
		).thenReturn(
			LocaleUtil.US
		);

		Mockito.when(
			themeDisplay.getUser()
		).thenReturn(
			Mockito.mock(User.class)
		);

		ObjectEntryManagerRegistry objectEntryManagerRegistry = Mockito.mock(
			ObjectEntryManagerRegistry.class);
		ObjectEntryManager objectEntryManager = Mockito.mock(
			ObjectEntryManager.class);
		ObjectRelationshipLocalService objectRelationshipLocalService =
			Mockito.mock(ObjectRelationshipLocalService.class);

		Mockito.when(
			objectEntryManagerRegistry.getObjectEntryManager(
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT)
		).thenReturn(
			objectEntryManager
		);

		Mockito.when(
			objectRelationshipLocalService.fetchObjectRelationship(0L)
		).thenReturn(
			null
		);

		ObjectEntry objectEntry1 = Mockito.mock(ObjectEntry.class);

		Mockito.when(
			objectEntry1.getId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			objectEntryManager.getObjectEntry(
				Mockito.eq(companyId), Mockito.any(DTOConverterContext.class),
				Mockito.eq(externalReferenceCode), Mockito.eq(objectDefinition),
				Mockito.eq(groupId))
		).thenReturn(
			objectEntry1
		);

		ObjectEntryDisplayContextImpl objectEntryDisplayContextImpl =
			_createObjectEntryDisplayContextImpl(
				httpServletRequest, objectEntryManagerRegistry,
				objectRelationshipLocalService);

		ObjectEntry objectEntry2 = ReflectionTestUtil.invoke(
			objectEntryDisplayContextImpl, "_getObjectEntry", new Class<?>[0],
			new Object[0]);

		Assert.assertSame(objectEntry1, objectEntry2);

		ObjectEntry objectEntry3 = ReflectionTestUtil.invoke(
			objectEntryDisplayContextImpl, "_getObjectEntry", new Class<?>[0],
			new Object[0]);

		Assert.assertSame(objectEntry1, objectEntry3);

		Mockito.verify(
			objectRelationshipLocalService, Mockito.times(1)
		).fetchObjectRelationship(
			0L
		);

		Mockito.verify(
			objectEntryManager, Mockito.times(1)
		).getObjectEntry(
			Mockito.eq(companyId), Mockito.any(DTOConverterContext.class),
			Mockito.eq(externalReferenceCode), Mockito.eq(objectDefinition),
			Mockito.eq(groupId)
		);

		Mockito.verifyNoMoreInteractions(
			objectRelationshipLocalService, objectEntryManager);
	}

	@Test
	public void testGetObjectFieldBusinessType() {
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
			_createObjectEntryDisplayContextImpl(
				objectFieldBusinessTypeRegistry);

		Assert.assertSame(
			objectFieldBusinessType,
			ReflectionTestUtil.invoke(
				objectEntryDisplayContextImpl, "_getObjectFieldBusinessType",
				new Class<?>[] {ObjectField.class},
				new Object[] {objectField}));
	}

	private ObjectEntryDisplayContextImpl _createObjectEntryDisplayContextImpl(
		HttpServletRequest httpServletRequest) {

		return _createObjectEntryDisplayContextImpl(
			httpServletRequest, Mockito.mock(ObjectEntryManagerRegistry.class),
			Mockito.mock(ObjectFieldBusinessTypeRegistry.class),
			Mockito.mock(ObjectRelationshipLocalService.class));
	}

	private ObjectEntryDisplayContextImpl _createObjectEntryDisplayContextImpl(
		HttpServletRequest httpServletRequest,
		ObjectEntryManagerRegistry objectEntryManagerRegistry,
		ObjectFieldBusinessTypeRegistry objectFieldBusinessTypeRegistry,
		ObjectRelationshipLocalService objectRelationshipLocalService) {

		Mockito.when(
			httpServletRequest.getAttribute(
				ObjectWebKeys.OBJECT_ENTRY_READ_ONLY)
		).thenReturn(
			false
		);

		return new ObjectEntryDisplayContextImpl(
			Mockito.mock(DDMExpressionFactory.class),
			Mockito.mock(DDMFormRenderer.class), httpServletRequest,
			Mockito.mock(ItemSelector.class),
			Mockito.mock(ObjectDefinitionLocalService.class),
			objectEntryManagerRegistry,
			Mockito.mock(ObjectEntryLocalService.class),
			Mockito.mock(ObjectEntryService.class),
			objectFieldBusinessTypeRegistry,
			Mockito.mock(ObjectFieldLocalService.class),
			Mockito.mock(ObjectLayoutLocalService.class),
			objectRelationshipLocalService,
			Mockito.mock(ObjectScopeProviderRegistry.class));
	}

	private ObjectEntryDisplayContextImpl _createObjectEntryDisplayContextImpl(
		HttpServletRequest httpServletRequest,
		ObjectEntryManagerRegistry objectEntryManagerRegistry,
		ObjectRelationshipLocalService objectRelationshipLocalService) {

		return _createObjectEntryDisplayContextImpl(
			httpServletRequest, objectEntryManagerRegistry,
			Mockito.mock(ObjectFieldBusinessTypeRegistry.class),
			objectRelationshipLocalService);
	}

	private ObjectEntryDisplayContextImpl _createObjectEntryDisplayContextImpl(
		ObjectFieldBusinessTypeRegistry objectFieldBusinessTypeRegistry) {

		return _createObjectEntryDisplayContextImpl(
			Mockito.mock(HttpServletRequest.class),
			Mockito.mock(ObjectEntryManagerRegistry.class),
			objectFieldBusinessTypeRegistry,
			Mockito.mock(ObjectRelationshipLocalService.class));
	}

}