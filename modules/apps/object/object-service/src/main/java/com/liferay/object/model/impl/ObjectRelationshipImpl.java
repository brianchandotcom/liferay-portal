/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.relationship.util.ObjectRelationshipUtil;

import java.util.Objects;
import java.util.Set;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectRelationshipImpl extends ObjectRelationshipBaseImpl {

	@Override
	public boolean isAllowedObjectRelationshipType(String type) {
		Set<String> defaultObjectRelationshipTypes =
			ObjectRelationshipUtil.getDefaultObjectRelationshipTypes();

		if (defaultObjectRelationshipTypes.contains(type)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isSelf() {
		return Objects.equals(
			getObjectDefinitionId1(), getObjectDefinitionId2());
	}

}