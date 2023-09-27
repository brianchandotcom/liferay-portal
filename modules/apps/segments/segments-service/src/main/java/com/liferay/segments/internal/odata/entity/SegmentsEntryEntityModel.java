/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.odata.entity;

import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IdEntityField;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * Provides the entity data model for the context that segments users.
 *
 * @author Eduardo García
 * @review
 */
@Component(
	property = "entity.model.name=" + SegmentsEntryEntityModel.NAME,
	service = EntityModel.class
)
public class SegmentsEntryEntityModel implements EntityModel {

	public static final String NAME = "Segment";

	public SegmentsEntryEntityModel() {
		_entityFieldsMap = EntityModel.toEntityFieldsMap(
			new IdEntityField(
				"segmentsEntryIds", locale -> "segmentsEntryIds",
				String::valueOf));
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