/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.inventory.internal.odata.entity.v1_0;

import com.liferay.portal.odata.entity.BooleanEntityField;
import com.liferay.portal.odata.entity.DoubleEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.StringEntityField;

import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
public class WarehouseEntityModel implements EntityModel {

	public WarehouseEntityModel() {
		_entityFieldsMap = EntityModel.toEntityFieldsMap(
			new BooleanEntityField("active", locale -> "active"),
			new StringEntityField("city", locale -> "city"),
			new StringEntityField(
				"countryISOCode", locale -> "countryTwoLettersISOCode"),
			new DoubleEntityField("latitude", locale -> "latitude"),
			new DoubleEntityField("longitude", locale -> "longitude"),
			new StringEntityField("name", locale -> "name"),
			new StringEntityField("regionISOCode", locale -> "regionCode"),
			new StringEntityField("street1", locale -> "street1"));
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	private final Map<String, EntityField> _entityFieldsMap;

}