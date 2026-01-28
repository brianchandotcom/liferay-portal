/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.internal.odata.entity.v1_0;

import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IntegerEntityField;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Davyson Melo
 */
@Component(
	property = "entity.model.name=" + TaskDefinitionEntityModel.NAME,
	service = EntityModel.class
)
public class TaskDefinitionEntityModel implements EntityModel {

	public static final String NAME = "TaskDefinition";

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldMap;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Activate
	protected void activate() {
		_entityFieldMap = EntityModel.toEntityFieldsMap(
			new IntegerEntityField("active", locale -> "active"));
	}

	private Map<String, EntityField> _entityFieldMap;

}