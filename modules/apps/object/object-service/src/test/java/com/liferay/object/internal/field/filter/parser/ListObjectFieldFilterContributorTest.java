/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.field.filter.parser;

import com.liferay.object.constants.ObjectViewFilterColumnConstants;
import com.liferay.object.exception.ObjectViewFilterColumnException;
import com.liferay.object.field.filter.parser.OneToManyObjectFieldFilterStrategy;
import com.liferay.object.field.filter.parser.StatusSystemObjectFieldFilterStrategy;
import com.liferay.object.field.frontend.data.set.filter.OneToManySelectionFDSFilter;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.model.ObjectViewFilterColumn;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.system.SystemObjectDefinitionManagerRegistry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Feliphe Marinho
 */
public class ListObjectFieldFilterContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetFDSFilter() throws Exception {
		long groupId = RandomTestUtil.randomLong();

		ObjectDefinition objectDefinition = Mockito.mock(
			ObjectDefinition.class);

		String restContextPath = RandomTestUtil.randomString();

		Mockito.when(
			objectDefinition.getRESTContextPath()
		).thenReturn(
			restContextPath
		);

		Mockito.when(
			objectDefinition.getTitleObjectFieldId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			objectDefinition.isUnmodifiableSystemObject()
		).thenReturn(
			false
		);

		ObjectDefinitionLocalService objectDefinitionLocalService =
			Mockito.mock(ObjectDefinitionLocalService.class);

		Mockito.when(
			objectDefinitionLocalService.getObjectDefinition(Mockito.anyLong())
		).thenReturn(
			objectDefinition
		);

		ObjectField objectField1 = Mockito.mock(ObjectField.class);

		Mockito.when(
			objectField1.getObjectFieldId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		ObjectFieldLocalService objectFieldLocalService = Mockito.mock(
			ObjectFieldLocalService.class);

		ObjectField objectField2 = Mockito.mock(ObjectField.class);

		Mockito.when(
			objectFieldLocalService.getObjectField(Mockito.anyLong())
		).thenReturn(
			objectField2
		);

		ObjectRelationship objectRelationship = Mockito.mock(
			ObjectRelationship.class);

		Mockito.when(
			objectRelationship.getObjectDefinitionId1()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		ObjectRelationshipLocalService objectRelationshipLocalService =
			Mockito.mock(ObjectRelationshipLocalService.class);

		Mockito.when(
			objectRelationshipLocalService.
				fetchObjectRelationshipByObjectFieldId2(Mockito.anyLong())
		).thenReturn(
			objectRelationship
		);

		ReflectionTestUtil.setFieldValue(
			_listObjectFieldFilterContributor, "_objectFieldFilterStrategy",
			new OneToManyObjectFieldFilterStrategy(
				groupId, LocaleUtil.getDefault(),
				Mockito.mock(ObjectDefinition.class),
				objectDefinitionLocalService,
				Mockito.mock(ObjectEntryLocalService.class), objectField1,
				objectFieldLocalService, objectRelationshipLocalService,
				Mockito.mock(ObjectViewFilterColumn.class),
				Mockito.mock(SystemObjectDefinitionManagerRegistry.class)));

		OneToManySelectionFDSFilter oneToManySelectionFDSFilter =
			(OneToManySelectionFDSFilter)
				_listObjectFieldFilterContributor.getFDSFilter();

		Assert.assertEquals(
			StringBundler.concat("/o", restContextPath, "/scopes/", groupId),
			oneToManySelectionFDSFilter.getAPIURL());
	}

	@Test
	public void testParse() throws Exception {
		ObjectViewFilterColumn objectViewFilterColumn = Mockito.mock(
			ObjectViewFilterColumn.class);

		Mockito.when(
			objectViewFilterColumn.getFilterType()
		).thenReturn(
			null
		);

		Mockito.when(
			objectViewFilterColumn.getJSON()
		).thenReturn(
			null
		);

		ReflectionTestUtil.setFieldValue(
			_listObjectFieldFilterContributor, "_objectFieldFilterStrategy",
			new StatusSystemObjectFieldFilterStrategy(
				Mockito.mock(Language.class), LocaleUtil.getDefault(),
				objectViewFilterColumn));

		Assert.assertEquals(
			HashMapBuilder.<String, Object>put(
				"exclude", false
			).put(
				"selectedItems", new ArrayList<>()
			).build(),
			_listObjectFieldFilterContributor.parse());
	}

	@Test
	public void testValidate() throws PortalException {
		Language language = Mockito.mock(Language.class);

		Mockito.when(
			language.get(LocaleUtil.getDefault(), "approved")
		).thenReturn(
			"approved"
		);

		ObjectViewFilterColumn objectViewFilterColumn = Mockito.mock(
			ObjectViewFilterColumn.class);

		Mockito.when(
			objectViewFilterColumn.getFilterType()
		).thenReturn(
			ObjectViewFilterColumnConstants.FILTER_TYPE_EXCLUDES
		);

		Mockito.when(
			objectViewFilterColumn.getJSON()
		).thenReturn(
			"{\"includes\": [0, 1]}"
		);

		ReflectionTestUtil.setFieldValue(
			_listObjectFieldFilterContributor, "_objectFieldFilterStrategy",
			new StatusSystemObjectFieldFilterStrategy(
				language, LocaleUtil.getDefault(), objectViewFilterColumn));

		try {
			_listObjectFieldFilterContributor.validate();

			Assert.fail();
		}
		catch (ObjectViewFilterColumnException
					objectViewFilterColumnException) {

			Assert.assertEquals(
				"JSON array is null for filter type excludes",
				objectViewFilterColumnException.getMessage());
		}

		Mockito.when(
			objectViewFilterColumn.getJSON()
		).thenReturn(
			"{\"excludes\": [\"brazil\"]}"
		);

		Mockito.when(
			objectViewFilterColumn.getObjectFieldName()
		).thenReturn(
			"status"
		);

		try {
			_listObjectFieldFilterContributor.validate();

			Assert.fail();
		}
		catch (ObjectViewFilterColumnException
					objectViewFilterColumnException) {

			Assert.assertEquals(
				"JSON array is invalid for filter type excludes",
				objectViewFilterColumnException.getMessage());
		}

		Mockito.when(
			objectViewFilterColumn.getJSON()
		).thenReturn(
			"{\"excludes\": [0, 1]}"
		);

		_listObjectFieldFilterContributor.validate();
	}

	private final ListObjectFieldFilterContributor
		_listObjectFieldFilterContributor =
			new ListObjectFieldFilterContributor();

}