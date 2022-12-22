/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.object.internal.related.models;

import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.internal.petra.sql.dsl.DynamicObjectDefinitionTable;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringBundler;

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
			ObjectRelationship objectRelationship, Predicate predicate)
		throws PortalException {

		ObjectDefinition objectDefinition1 = _getObjectDefinition1(
			objectRelationship);

		ObjectDefinition objectDefinition2 = _getObjectDefinition2(
			objectRelationship);

		DynamicObjectDefinitionTable objectDefinition2Table = getTable(
			objectDefinition2);

		Column<DynamicObjectDefinitionTable, ?>
			objectDefinition2RelationshipFieldTableColumn =
				objectDefinition2Table.getColumn(
					StringBundler.concat(
						"r_", objectRelationship.getName(), "_",
						objectDefinition1.getPKObjectFieldName()));

		DynamicObjectDefinitionTable objectDefinition2ExtensionTable =
			getExtensionTable(objectDefinition2);

		if (objectDefinition2RelationshipFieldTableColumn == null) {
			objectDefinition2RelationshipFieldTableColumn =
				objectDefinition2ExtensionTable.getColumn(
					StringBundler.concat(
						"r_", objectRelationship.getName(), "_",
						objectDefinition1.getPKObjectFieldName()));
		}

		DynamicObjectDefinitionTable objectDefinition1Table = getTable(
			objectDefinition1);

		Column<?, ?> objectDefinition1PKObjectFieldTableColumn =
			getPKObjectDefinitionTableColumn(
				objectDefinition1Table, objectDefinition1);

		if (objectDefinition.getObjectDefinitionId() ==
				objectRelationship.getObjectDefinitionId1()) {

			return objectDefinition1PKObjectFieldTableColumn.in(
				DSLQueryFactoryUtil.select(
					objectDefinition2RelationshipFieldTableColumn
				).from(
					objectDefinition2Table
				).innerJoinON(
					objectDefinition2ExtensionTable,
					objectDefinition2Table.getPrimaryKeyColumn(
					).eq(
						objectDefinition2ExtensionTable.getPrimaryKeyColumn()
					)
				).where(
					predicate
				));
		}

		Column<?, ?> objectDefinition2PKObjectFieldTableColumn =
			getPKObjectDefinitionTableColumn(
				objectDefinition2Table, objectDefinition2);

		DynamicObjectDefinitionTable objectDefinition1ExtensionTable =
			getExtensionTable(objectDefinition1);

		return objectDefinition2PKObjectFieldTableColumn.in(
			DSLQueryFactoryUtil.select(
				objectDefinition2PKObjectFieldTableColumn
			).from(
				objectDefinition2Table
			).innerJoinON(
				objectDefinition2ExtensionTable,
				objectDefinition2Table.getPrimaryKeyColumn(
				).eq(
					objectDefinition2ExtensionTable.getPrimaryKeyColumn()
				)
			).where(
				objectDefinition2RelationshipFieldTableColumn.in(
					DSLQueryFactoryUtil.select(
						objectDefinition1PKObjectFieldTableColumn
					).from(
						objectDefinition1Table
					).innerJoinON(
						objectDefinition1ExtensionTable,
						objectDefinition1Table.getPrimaryKeyColumn(
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