/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.related.models;

import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntryTable;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.petra.sql.dsl.DynamicObjectDefinitionTable;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Luis Miguel Barcos
 */
public class ObjectEntry1toMObjectRelatedModelsPredicateProviderImpl
	extends BaseObjectEntryObjectRelatedModelsPredicateProviderImpl {

	public ObjectEntry1toMObjectRelatedModelsPredicateProviderImpl(
		ObjectDefinition objectDefinition,
		ObjectFieldLocalService objectFieldLocalService) {

		super(objectDefinition, objectFieldLocalService);
	}

	@Override
	public String getObjectRelationshipType() {
		return ObjectRelationshipConstants.TYPE_ONE_TO_MANY;
	}

	@Override
	public Predicate getPredicate(
			ObjectRelationship objectRelationship, Predicate predicate,
			ObjectDefinition relatedObjectDefinition)
		throws PortalException {

		ObjectDefinition objectDefinition1 = _getObjectDefinition1(
			objectRelationship);

		DynamicObjectDefinitionTable
			objectDefinition1DynamicObjectDefinitionTable =
				getDynamicObjectDefinitionTable(objectDefinition1);

		Column<?, ?> objectDefinition1PKObjectFieldColumn =
			getPKObjectFieldColumn(
				objectDefinition1DynamicObjectDefinitionTable,
				objectDefinition1.getPKObjectFieldDBColumnName());

		ObjectDefinition objectDefinition2 = _getObjectDefinition2(
			objectRelationship);

		DynamicObjectDefinitionTable
			objectDefinition2DynamicObjectDefinitionTable =
				getDynamicObjectDefinitionTable(objectDefinition2);
		DynamicObjectDefinitionTable
			objectDefinition2ExtensionDynamicObjectDefinitionTable =
				getExtensionDynamicObjectDefinitionTable(objectDefinition2);

		Column<DynamicObjectDefinitionTable, ?> objectRelationshipColumn =
			objectDefinition2DynamicObjectDefinitionTable.getColumn(
				StringBundler.concat(
					"r_", objectRelationship.getName(), "_",
					objectDefinition1.getPKObjectFieldName()));

		if (objectRelationshipColumn == null) {
			objectRelationshipColumn =
				objectDefinition2ExtensionDynamicObjectDefinitionTable.
					getColumn(
						StringBundler.concat(
							"r_", objectRelationship.getName(), "_",
							objectDefinition1.getPKObjectFieldName()));
		}

		if (objectDefinition.getObjectDefinitionId() ==
				objectRelationship.getObjectDefinitionId1()) {

			return objectDefinition1PKObjectFieldColumn.in(
				DSLQueryFactoryUtil.select(
					objectRelationshipColumn
				).from(
					objectDefinition2DynamicObjectDefinitionTable
				).innerJoinON(
					ObjectEntryTable.INSTANCE,
					ObjectEntryTable.INSTANCE.objectEntryId.eq(
						objectDefinition2DynamicObjectDefinitionTable.
							getPrimaryKeyColumn())
				).innerJoinON(
					objectDefinition2ExtensionDynamicObjectDefinitionTable,
					objectDefinition2DynamicObjectDefinitionTable.
						getPrimaryKeyColumn(
						).eq(
							objectDefinition2ExtensionDynamicObjectDefinitionTable.
								getPrimaryKeyColumn()
						)
				).where(
					predicate
				));
		}

		Column<?, ?> objectDefinition2PKObjectFieldColumn =
			getPKObjectFieldColumn(
				objectDefinition2DynamicObjectDefinitionTable,
				objectDefinition2.getPKObjectFieldDBColumnName());
		DynamicObjectDefinitionTable objectDefinition1ExtensionTable =
			getExtensionDynamicObjectDefinitionTable(objectDefinition1);

		return objectDefinition2PKObjectFieldColumn.in(
			DSLQueryFactoryUtil.select(
				objectDefinition2PKObjectFieldColumn
			).from(
				objectDefinition2DynamicObjectDefinitionTable
			).innerJoinON(
				objectDefinition2ExtensionDynamicObjectDefinitionTable,
				objectDefinition2DynamicObjectDefinitionTable.
					getPrimaryKeyColumn(
					).eq(
						objectDefinition2ExtensionDynamicObjectDefinitionTable.
							getPrimaryKeyColumn()
					)
			).where(
				objectRelationshipColumn.in(
					DSLQueryFactoryUtil.select(
						objectDefinition1PKObjectFieldColumn
					).from(
						objectDefinition1DynamicObjectDefinitionTable
					).innerJoinON(
						ObjectEntryTable.INSTANCE,
						ObjectEntryTable.INSTANCE.objectEntryId.eq(
							objectDefinition1DynamicObjectDefinitionTable.
								getPrimaryKeyColumn())
					).innerJoinON(
						objectDefinition1ExtensionTable,
						objectDefinition1DynamicObjectDefinitionTable.
							getPrimaryKeyColumn(
							).eq(
								objectDefinition1ExtensionTable.
									getPrimaryKeyColumn()
							)
					).where(
						predicate
					))
			));
	}

	private ObjectDefinition _getObjectDefinition1(
			ObjectRelationship objectRelationship)
		throws PortalException {

		if (objectRelationship.getObjectDefinitionId1() ==
				objectDefinition.getObjectDefinitionId()) {

			return objectDefinition;
		}

		return ObjectDefinitionLocalServiceUtil.getObjectDefinition(
			objectRelationship.getObjectDefinitionId1());
	}

	private ObjectDefinition _getObjectDefinition2(
			ObjectRelationship objectRelationship)
		throws PortalException {

		if (objectRelationship.getObjectDefinitionId2() ==
				objectDefinition.getObjectDefinitionId()) {

			return objectDefinition;
		}

		return ObjectDefinitionLocalServiceUtil.getObjectDefinition(
			objectRelationship.getObjectDefinitionId2());
	}

}