/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.sort;

import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.relationship.util.ObjectRelationshipUtil;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.odata.sort.InvalidSortException;

import java.util.List;
import java.util.Objects;

/**
 * @author Carlos Correa
 */
public class SortDSLQueryVisitor extends BaseSortDSLQueryVisitor {

	public SortDSLQueryVisitor(
		ObjectFieldLocalService objectFieldLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService) {

		super(objectFieldLocalService, objectRelationshipLocalService);
	}

	@Override
	public DSLQuery visit(DSLQuery dslQuery, Sort sort) throws PortalException {
		ObjectDefinition currentObjectDefinition = sort.getObjectDefinition();

		List<String> fieldPathParts = StringUtil.split(
			sort.getFieldPath(), CharPool.FORWARD_SLASH);

		List<String> relationshipNames = ListUtil.subList(
			fieldPathParts, 0, fieldPathParts.size() - 1);

		for (int i = 0; i < relationshipNames.size(); i++) {
			String relationshipName = relationshipNames.get(i);

			ObjectRelationship objectRelationship =
				objectRelationshipLocalService.
					getObjectRelationshipByObjectDefinitionId(
						currentObjectDefinition.getObjectDefinitionId(),
						relationshipName);

			ObjectDefinition relatedObjectDefinition =
				ObjectRelationshipUtil.getRelatedObjectDefinition(
					currentObjectDefinition, objectRelationship);

			dslQuery = _visitWithRelationship(
				dslQuery, currentObjectDefinition, objectRelationship,
				StringUtil.merge(
					relationshipNames.subList(0, i + 1),
					StringPool.FORWARD_SLASH),
				relatedObjectDefinition, sort);

			currentObjectDefinition = relatedObjectDefinition;
		}

		ObjectEntryFieldSortDSLQueryVisitor
			objectEntryFieldSortDSLQueryVisitor =
				new ObjectEntryFieldSortDSLQueryVisitor(
					objectFieldLocalService);

		return objectEntryFieldSortDSLQueryVisitor.visit(
			dslQuery, new Sort(currentObjectDefinition, sort));
	}

	private DSLQuery _visitWithRelationship(
			DSLQuery dslQuery, ObjectDefinition objectDefinition,
			ObjectRelationship objectRelationship, String path,
			ObjectDefinition relatedObjectDefinition, Sort sort)
		throws PortalException {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-18730")) {
			throw new InvalidSortException("Unable to sort by a related field");
		}

		if (!Objects.equals(
				objectRelationship.getType(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

			String relationshipType = CamelCaseUtil.fromCamelCase(
				objectRelationship.getType(), CharPool.SPACE);

			throw new InvalidSortException(
				"Unable to sort by a " + relationshipType + " related field");
		}

		if (objectDefinition.getObjectDefinitionId() !=
				objectRelationship.getObjectDefinitionId1()) {

			throw new InvalidSortException(
				"Unable to sort by a many to one related field");
		}

		ObjectEntry1ToMRelationshipSortDSLQueryVisitor
			objectEntry1ToMRelationshipSortDSLQueryVisitor =
				new ObjectEntry1ToMRelationshipSortDSLQueryVisitor(
					objectFieldLocalService, objectRelationshipLocalService);

		return objectEntry1ToMRelationshipSortDSLQueryVisitor.visit(
			dslQuery,
			new RelationshipSort(
				objectDefinition, objectRelationship, path,
				relatedObjectDefinition, sort));
	}

}