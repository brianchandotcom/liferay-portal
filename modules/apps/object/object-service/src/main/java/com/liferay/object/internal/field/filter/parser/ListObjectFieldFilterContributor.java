/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.field.filter.parser;

import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectViewFilterColumnConstants;
import com.liferay.object.field.filter.parser.ObjectFieldFilterContext;
import com.liferay.object.field.filter.parser.ObjectFieldFilterContributor;
import com.liferay.object.field.filter.parser.ObjectFieldFilterStrategy;
import com.liferay.object.field.filter.parser.OneToManyObjectFieldFilterStrategy;
import com.liferay.object.field.filter.parser.PicklistObjectFieldFilterStrategy;
import com.liferay.object.field.filter.parser.StatusSystemObjectFieldFilterStrategy;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.model.ObjectViewFilterColumn;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.system.SystemObjectDefinitionManagerRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;

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
		"object.field.filter.type.key=" + ObjectViewFilterColumnConstants.FILTER_TYPE_EXCLUDES,
		"object.field.filter.type.key=" + ObjectViewFilterColumnConstants.FILTER_TYPE_INCLUDES
	},
	service = ObjectFieldFilterContributor.class
)
public class ListObjectFieldFilterContributor
	implements ObjectFieldFilterContributor {

	@Override
	public FDSFilter getFDSFilter() throws PortalException {
		return _objectFieldFilterStrategy.getFDSFilter();
	}

	@Override
	public Map<String, Object> parse() throws PortalException {
		return _objectFieldFilterStrategy.parse();
	}

	@Override
	public void setObjectFieldFilterStrategy(
			ObjectFieldFilterContext objectFieldFilterContext)
		throws PortalException {

		_objectFieldFilterStrategy = _toObjectFieldFilterStrategy(
			objectFieldFilterContext.getLocale(),
			objectFieldFilterContext.getObjectDefinitionId(),
			objectFieldFilterContext.getObjectViewFilterColumn());
	}

	@Override
	public String toValueSummary() throws PortalException {
		return _objectFieldFilterStrategy.toValueSummary();
	}

	@Override
	public void validate() throws PortalException {
		_objectFieldFilterStrategy.validate();
	}

	private ObjectFieldFilterStrategy _toObjectFieldFilterStrategy(
			Locale locale, long objectDefinitionId,
			ObjectViewFilterColumn objectViewFilterColumn)
		throws PortalException {

		if (Objects.equals(
				objectViewFilterColumn.getObjectFieldName(), Field.STATUS)) {

			return new StatusSystemObjectFieldFilterStrategy(
				_language, locale, objectViewFilterColumn);
		}

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			objectDefinitionId, objectViewFilterColumn.getObjectFieldName());

		if (Objects.equals(
				ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST,
				objectField.getBusinessType()) ||
			Objects.equals(
				ObjectFieldConstants.BUSINESS_TYPE_PICKLIST,
				objectField.getBusinessType())) {

			return new PicklistObjectFieldFilterStrategy(
				locale, objectField.getListTypeDefinitionId(),
				_listTypeDefinitionLocalService, _listTypeEntryLocalService,
				objectField, objectViewFilterColumn);
		}

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.
				fetchObjectRelationshipByObjectFieldId2(
					objectField.getObjectFieldId());

		return new OneToManyObjectFieldFilterStrategy(
			locale,
			_objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId1()),
			_objectDefinitionLocalService, _objectEntryLocalService,
			objectField, _objectFieldLocalService,
			_objectRelationshipLocalService, objectViewFilterColumn,
			_persistedModelLocalServiceRegistry,
			_systemObjectDefinitionManagerRegistry);
	}

	@Reference
	private Language _language;

	@Reference
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	private ObjectFieldFilterStrategy _objectFieldFilterStrategy;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private PersistedModelLocalServiceRegistry
		_persistedModelLocalServiceRegistry;

	@Reference
	private SystemObjectDefinitionManagerRegistry
		_systemObjectDefinitionManagerRegistry;

}