/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.object.web.internal.object.entries.frontend.data.set.filter.factory;

import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectViewFilterColumnConstants;
import com.liferay.object.field.filter.parser.ObjectFieldFilterContributor;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.model.ObjectViewFilterColumn;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.object.system.SystemObjectDefinitionMetadataTracker;
import com.liferay.object.web.internal.object.entries.frontend.data.set.filter.ListTypeEntryAutocompleteFDSFilter;
import com.liferay.object.web.internal.object.entries.frontend.data.set.filter.ObjectEntryStatusSelectionFDSFilter;
import com.liferay.object.web.internal.object.entries.frontend.data.set.filter.OneToManyAutocompleteFDSFilter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = {
		"object.field.business.type.key=" + ObjectFieldConstants.BUSINESS_TYPE_PICKLIST,
		"object.field.filter.type.key=" + ObjectViewFilterColumnConstants.FILTER_TYPE_EXCLUDES,
		"object.field.filter.type.key=" + ObjectViewFilterColumnConstants.FILTER_TYPE_INCLUDES
	},
	service = ObjectFieldFDSFilterFactory.class
)
public class ListTypeEntryObjectFieldFDSFilterFactory
	implements ObjectFieldFDSFilterFactory {

	public FDSFilter create(
			Locale locale, long objectDefinitionId,
			ObjectViewFilterColumn objectViewFilterColumn)
		throws PortalException {

		Map<String, Object> preloadedData = null;

		if (Objects.equals(
				objectViewFilterColumn.getObjectFieldName(), "status")) {

			if (Validator.isNotNull(objectViewFilterColumn.getFilterType())) {
				preloadedData = _objectFieldFilterParser.parse(
					0L, objectDefinitionId, locale, objectViewFilterColumn);
			}

			return new ObjectEntryStatusSelectionFDSFilter(preloadedData);
		}

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			objectDefinitionId, objectViewFilterColumn.getObjectFieldName());

		if (Validator.isNotNull(objectField.getListTypeDefinitionId())) {
			if (Validator.isNotNull(objectViewFilterColumn.getFilterType())) {
				preloadedData = _objectFieldFilterParser.parse(
					objectField.getListTypeDefinitionId(), objectDefinitionId,
					locale, objectViewFilterColumn);
			}

			ListTypeDefinition listTypeDefinition =
				_listTypeDefinitionLocalService.getListTypeDefinition(
					objectField.getListTypeDefinitionId());

			return new ListTypeEntryAutocompleteFDSFilter(
				objectField.getName(), listTypeDefinition.getName(locale),
				objectField.getListTypeDefinitionId(), preloadedData);
		}

		if (GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-152650"))) {
			ObjectRelationship objectRelationship =
				_objectRelationshipLocalService.
					fetchObjectRelationshipByObjectFieldId2(
						objectField.getObjectFieldId());

			ObjectDefinition objectDefinition1 =
				_objectDefinitionLocalService.getObjectDefinition(
					objectRelationship.getObjectDefinitionId1());

			ObjectField titleObjectField = null;

			if (Validator.isNull(objectDefinition1.getTitleObjectFieldId())) {
				titleObjectField = _objectFieldLocalService.getObjectField(
					objectDefinition1.getObjectDefinitionId(), "id");
			}
			else {
				titleObjectField = _objectFieldLocalService.getObjectField(
					objectDefinition1.getTitleObjectFieldId());
			}

			if (Validator.isNotNull(objectViewFilterColumn.getFilterType())) {
				preloadedData = _objectFieldFilterParser.parse(
					0L, objectDefinitionId, locale, objectViewFilterColumn);
			}

			String restContextPath = null;

			if (objectDefinition1.isSystem()) {
				SystemObjectDefinitionMetadata systemObjectDefinitionMetadata =
					_systemObjectDefinitionMetadataTracker.
						getSystemObjectDefinitionMetadata(
							objectDefinition1.getName());

				restContextPath =
					"/o/" + systemObjectDefinitionMetadata.getRESTContextPath();
			}
			else {
				restContextPath = "/o" + objectDefinition1.getRESTContextPath();
			}

			return new OneToManyAutocompleteFDSFilter(
				preloadedData, restContextPath,
				titleObjectField.getLabel(locale),
				objectField.getDBColumnName(), titleObjectField.getName());
		}

		throw new UnsupportedOperationException();
	}

	@Reference
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference(
		target = "(object.field.filter.type.key=" + ObjectViewFilterColumnConstants.FILTER_TYPE_EXCLUDES + ")"
	)
	private ObjectFieldFilterContributor _objectFieldFilterParser;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private SystemObjectDefinitionMetadataTracker
		_systemObjectDefinitionMetadataTracker;

}