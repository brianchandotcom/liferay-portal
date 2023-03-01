/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.segments.asah.connector.internal.odata.entity;

import com.liferay.portal.odata.entity.ComplexEntityField;
import com.liferay.portal.odata.entity.DateEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IdEntityField;
import com.liferay.portal.odata.entity.IntegerEntityField;

import java.util.Arrays;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Cristina González
 */
@Component(
	property = "entity.model.name=" + EventEntityModel.NAME,
	service = EntityModel.class
)
public class EventEntityModel implements EntityModel {

	public static final String NAME = "Event";

	public EventEntityModel() {
		_entityFieldsMap = EntityModel.toEntityFieldsMap(
			new ComplexEntityField(
				"downloadDocumentAndMedia",
				Arrays.asList(
					new IdEntityField(
						"documentId", locale -> "documentId", String::valueOf),
					new DateEntityField(
						"day", String::valueOf, String::valueOf),
					new IntegerEntityField("count", String::valueOf))));
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	@Override
	public String getName() {
		return NAME;
	}

	private final Map<String, EntityField> _entityFieldsMap;

}