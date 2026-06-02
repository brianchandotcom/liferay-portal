/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.related.models;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntryTable;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Jhosseph Gonzalez
 */
public class ObjectEntryMtoMObjectRelatedModelsPredicateProviderImplTest
	extends BaseObjectEntryObjectRelatedModelsPredicateProviderImplTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetPredicate() throws Exception {

		// Company scope

		long companyId = RandomTestUtil.randomLong();
		long objectDefinitionId1 = RandomTestUtil.randomLong();

		ObjectDefinition objectDefinition = mockObjectDefinition(
			companyId, objectDefinitionId1, RandomTestUtil.randomString(),
			ObjectDefinitionConstants.SCOPE_COMPANY);

		ObjectFieldLocalService objectFieldLocalService =
			_mockObjectFieldLocalService();

		long objectDefinitionId2 = RandomTestUtil.randomLong();

		ObjectRelationship objectRelationship = mockObjectRelationship(
			objectDefinitionId1, objectDefinitionId2,
			RandomTestUtil.randomString());

		String objectDefinitionName2 = RandomTestUtil.randomString();

		assertPredicateString(
			"= ?",
			_getPredicateString(
				new Long[] {RandomTestUtil.randomLong()}, objectDefinition,
				objectFieldLocalService, objectRelationship,
				mockObjectDefinition(
					companyId, objectDefinitionId2, objectDefinitionName2,
					ObjectDefinitionConstants.SCOPE_COMPANY)));

		// Depot scope with connected depot entries

		assertPredicateString(
			"in (?, ?, ?)",
			_getPredicateString(
				new Long[] {
					RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
					RandomTestUtil.randomLong()
				},
				objectDefinition, objectFieldLocalService, objectRelationship,
				mockObjectDefinition(
					companyId, objectDefinitionId2, objectDefinitionName2,
					ObjectDefinitionConstants.SCOPE_DEPOT)));

		// Site scope

		assertPredicateString(
			"in (?)",
			_getPredicateString(
				new Long[] {RandomTestUtil.randomLong()}, objectDefinition,
				objectFieldLocalService, objectRelationship,
				mockObjectDefinition(
					companyId, objectDefinitionId2, objectDefinitionName2,
					ObjectDefinitionConstants.SCOPE_SITE)));
	}

	private String _getPredicateString(
			Long[] groupIds, ObjectDefinition objectDefinition,
			ObjectFieldLocalService objectFieldLocalService,
			ObjectRelationship objectRelationship,
			ObjectDefinition relatedObjectDefinition)
		throws Exception {

		ObjectEntryMtoMObjectRelatedModelsPredicateProviderImpl
			objectEntryMtoMObjectRelatedModelsPredicateProviderImpl =
				new ObjectEntryMtoMObjectRelatedModelsPredicateProviderImpl(
					objectDefinition, objectFieldLocalService);

		return String.valueOf(
			objectEntryMtoMObjectRelatedModelsPredicateProviderImpl.
				getPredicate(
					groupIds, objectRelationship,
					ObjectEntryTable.INSTANCE.externalReferenceCode.eq(
						RandomTestUtil.randomString()),
					relatedObjectDefinition));
	}

	private ObjectFieldLocalService _mockObjectFieldLocalService() {
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

		return objectFieldLocalService;
	}

}