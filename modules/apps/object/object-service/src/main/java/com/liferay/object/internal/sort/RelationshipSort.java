/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.sort;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;

/**
 * @author Carlos Correa
 */
public class RelationshipSort extends Sort {

	public RelationshipSort(
		ObjectDefinition objectDefinition,
		ObjectRelationship objectRelationship, String path,
		ObjectDefinition relatedObjectDefinition,
		com.liferay.portal.kernel.search.Sort sort) {

		super(objectDefinition, sort);

		_objectRelationship = objectRelationship;
		_path = path;
		_relatedObjectDefinition = relatedObjectDefinition;
	}

	@Override
	public String getFieldPath() {
		return _path;
	}

	public ObjectRelationship getObjectRelationship() {
		return _objectRelationship;
	}

	public ObjectDefinition getRelatedObjectDefinition() {
		return _relatedObjectDefinition;
	}

	private final ObjectRelationship _objectRelationship;
	private final String _path;
	private final ObjectDefinition _relatedObjectDefinition;

}