/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.payment.internal.odata.entity.v1_0;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.odata.entity.CollectionEntityField;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IntegerEntityField;
import com.liferay.portal.odata.entity.StringEntityField;

import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
public class PaymentEntityModel implements EntityModel {

	public PaymentEntityModel() {
		_entityFieldsMap = EntityModel.toEntityFieldsMap(
			new CollectionEntityField(
				new IntegerEntityField(
					"paymentStatus", locale -> "paymentStatus")),
			new CollectionEntityField(
				new IntegerEntityField("type", locale -> "type")),
			new CollectionEntityField(
				new StringEntityField("reasonKey", locale -> "reasonKey")),
			new DateTimeEntityField(
				"createDate",
				locale -> Field.getSortableFieldName(Field.CREATE_DATE),
				locale -> Field.CREATE_DATE),
			new IntegerEntityField("id", locale -> Field.ENTRY_CLASS_PK),
			new IntegerEntityField("relatedItemId", locale -> "classPK"));
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	private final Map<String, EntityField> _entityFieldsMap;

}