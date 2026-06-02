/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.related.models;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntryTable;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Jhosseph Gonzalez
 */
public class ObjectEntry1toMObjectRelatedModelsPredicateProviderImplTest
	extends BaseObjectRelatedModelsPredicateProviderImplTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetPredicate() throws Exception {

		// Company scope

		long companyId = RandomTestUtil.randomLong();
		long objectDefinitionId = RandomTestUtil.randomLong();

		String objectDefinitionName = RandomTestUtil.randomString();
		String objectRelationshipName = RandomTestUtil.randomString();

		ObjectFieldLocalService objectFieldLocalService =
			_mockObjectFieldLocalService(
				StringBundler.concat(
					"r_", objectRelationshipName, "_c_", objectDefinitionName,
					"Id"),
				objectDefinitionId);

		ObjectRelationship objectRelationship = mockObjectRelationship(
			objectDefinitionId, objectDefinitionId, objectRelationshipName);

		assertPredicateString(
			"= ?",
			_getPredicateString(
				new Long[] {RandomTestUtil.randomLong()},
				mockObjectDefinition(
					companyId, objectDefinitionId, objectDefinitionName,
					ObjectDefinitionConstants.SCOPE_COMPANY),
				objectFieldLocalService, objectRelationship));

		// Depot scope with connected depot entries

		assertPredicateString(
			"in (?, ?, ?)",
			_getPredicateString(
				new Long[] {
					RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
					RandomTestUtil.randomLong()
				},
				mockObjectDefinition(
					companyId, objectDefinitionId, objectDefinitionName,
					ObjectDefinitionConstants.SCOPE_DEPOT),
				objectFieldLocalService, objectRelationship));

		// Site scope

		assertPredicateString(
			"in (?)",
			_getPredicateString(
				new Long[] {RandomTestUtil.randomLong()},
				mockObjectDefinition(
					companyId, objectDefinitionId, objectDefinitionName,
					ObjectDefinitionConstants.SCOPE_SITE),
				objectFieldLocalService, objectRelationship));
	}

	private String _getPredicateString(
			Long[] groupIds, ObjectDefinition objectDefinition,
			ObjectFieldLocalService objectFieldLocalService,
			ObjectRelationship objectRelationship)
		throws Exception {

		ObjectEntry1toMObjectRelatedModelsPredicateProviderImpl
			objectEntry1toMObjectRelatedModelsPredicateProviderImpl =
				new ObjectEntry1toMObjectRelatedModelsPredicateProviderImpl(
					objectDefinition, objectFieldLocalService);

		return String.valueOf(
			objectEntry1toMObjectRelatedModelsPredicateProviderImpl.
				getPredicate(
					groupIds, objectRelationship,
					ObjectEntryTable.INSTANCE.externalReferenceCode.eq(
						RandomTestUtil.randomString()),
					objectDefinition));
	}

	private ObjectFieldLocalService _mockObjectFieldLocalService(
		String dbColumnName, long objectDefinitionId) {

		ObjectFieldLocalService objectFieldLocalService = Mockito.mock(
			ObjectFieldLocalService.class);

		Mockito.when(
			objectFieldLocalService.getLocalizedObjectFields(Mockito.anyLong())
		).thenReturn(
			Collections.emptyList()
		);

		Mockito.when(
			objectFieldLocalService.getObjectFields(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			Collections.emptyList()
		);

		ObjectField objectField = Mockito.mock(ObjectField.class);

		Mockito.when(
			objectField.compareBusinessType(
				ObjectFieldConstants.BUSINESS_TYPE_AUTO_INCREMENT)
		).thenReturn(
			false
		);

		Mockito.when(
			objectField.getDBColumnNames()
		).thenReturn(
			new String[] {dbColumnName}
		);

		Mockito.when(
			objectField.getDBType()
		).thenReturn(
			ObjectFieldConstants.DB_TYPE_LONG
		);

		Mockito.when(
			objectField.hasInsertValues()
		).thenReturn(
			true
		);

		Mockito.when(
			objectField.isLocalized()
		).thenReturn(
			false
		);

		Mockito.when(
			objectFieldLocalService.getObjectFields(
				Mockito.eq(objectDefinitionId), Mockito.anyString())
		).thenReturn(
			List.of(objectField)
		);

		return objectFieldLocalService;
	}

}