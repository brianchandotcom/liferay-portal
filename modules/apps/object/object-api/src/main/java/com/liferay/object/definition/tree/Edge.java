/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.definition.tree;

/**
 * @author Feliphe Marinho
 */
public class Edge {

	public Edge(long objectRelationshipId) {
		_objectRelationshipId = objectRelationshipId;
	}

	public long getObjectRelationshipId() {
		return _objectRelationshipId;
	}

	private final long _objectRelationshipId;

}