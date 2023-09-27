/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.rest.internal.odata.entity.v2_0;

import com.liferay.portal.odata.entity.ComplexEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;

import java.util.List;
import java.util.Map;

/**
 * @author Gabriel Albuquerque
 */
public class DataRecordEntityModel implements EntityModel {

	public DataRecordEntityModel(List<EntityField> entityFields) {
		_entityFieldsMap = EntityModel.toEntityFieldsMap(
			new ComplexEntityField("dataRecordValues", entityFields));
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	private final Map<String, EntityField> _entityFieldsMap;

}