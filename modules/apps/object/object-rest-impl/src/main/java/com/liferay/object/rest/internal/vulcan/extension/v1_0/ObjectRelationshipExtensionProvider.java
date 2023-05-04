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

package com.liferay.object.rest.internal.vulcan.extension.v1_0;

import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryRelatedObjectsManager;
import com.liferay.object.rest.manager.v1_0.ObjectRelationshipElementsParser;
import com.liferay.object.rest.manager.v1_0.ObjectRelationshipElementsParserRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.ObjectRelationshipService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.extension.ExtensionProvider;
import com.liferay.portal.vulcan.extension.PropertyDefinition;
import com.liferay.portal.vulcan.extension.validation.DefaultPropertyValidator;
import com.liferay.portal.vulcan.extension.validation.PropertyValidator;
import com.liferay.portal.vulcan.fields.NestedFieldsSupplier;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(service = ExtensionProvider.class)
public class ObjectRelationshipExtensionProvider
	extends BaseObjectExtensionProvider {

	@Override
	public Map<String, Serializable> getExtendedProperties(
			long companyId, String className, Object entity)
		throws Exception {

		ObjectDefinition objectDefinition = fetchObjectDefinition(
			companyId, className);

		return NestedFieldsSupplier.supply(
			nestedFieldName -> {
				ObjectRelationship objectRelationship =
					_objectRelationshipLocalService.
						fetchObjectRelationshipByObjectDefinitionId1(
							objectDefinition.getObjectDefinitionId(),
							nestedFieldName);

				if ((objectRelationship == null) ||
					(!Objects.equals(
						objectRelationship.getType(),
						ObjectRelationshipConstants.TYPE_MANY_TO_MANY) &&
					 !Objects.equals(
						 objectRelationship.getType(),
						 ObjectRelationshipConstants.TYPE_ONE_TO_MANY))) {

					return null;
				}

				ObjectDefinition relatedObjectDefinition =
					_getRelatedObjectDefinition(
						objectDefinition, objectRelationship);

				if (!relatedObjectDefinition.isActive() ||
					relatedObjectDefinition.isUnmodifiableSystemObject()) {

					return null;
				}

				ObjectEntryManager objectEntryManager =
					_objectEntryManagerRegistry.getObjectEntryManager(
						objectDefinition.getStorageType());

				if (!(objectEntryManager instanceof
						ObjectEntryRelatedObjectsManager)) {

					throw new UnsupportedOperationException();
				}

				ObjectEntryRelatedObjectsManager
					objectEntryRelatedObjectsManager =
						(ObjectEntryRelatedObjectsManager)objectEntryManager;

				long primaryKey = getPrimaryKey(entity);

				Page<ObjectEntry> relatedObjectEntriesPage =
					objectEntryRelatedObjectsManager.
						getObjectEntryRelatedObjectEntries(
							_getDefaultDTOConverterContext(
								objectDefinition, primaryKey, null),
							objectDefinition, primaryKey,
							objectRelationship.getName(),
							Pagination.of(
								QueryUtil.ALL_POS, QueryUtil.ALL_POS));

				return (Serializable)relatedObjectEntriesPage.getItems();
			});
	}

	@Override
	public Map<String, PropertyDefinition> getExtendedPropertyDefinitions(
			long companyId, String className)
		throws Exception {

		Map<String, PropertyDefinition> extendedPropertyDefinitions =
			new HashMap<>();

		ObjectDefinition objectDefinition = fetchObjectDefinition(
			companyId, className);

		for (ObjectRelationship objectRelationship :
				_objectRelationshipLocalService.getAllObjectRelationships(
					objectDefinition.getObjectDefinitionId())) {

			if (!Objects.equals(
					objectRelationship.getType(),
					ObjectRelationshipConstants.TYPE_MANY_TO_MANY) &&
				!Objects.equals(
					objectRelationship.getType(),
					ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

				continue;
			}

			ObjectDefinition relatedObjectDefinition =
				_getRelatedObjectDefinition(
					objectDefinition, objectRelationship);

			if (!relatedObjectDefinition.isActive() ||
				relatedObjectDefinition.isUnmodifiableSystemObject()) {

				continue;
			}

			PropertyValidator propertyValidator = null;

			if (FeatureFlagManagerUtil.isEnabled("LPS-153117")) {
				propertyValidator = new DefaultPropertyValidator();
			}
			else {
				propertyValidator = new UnsupportedOperationPropertyValidator();
			}

			extendedPropertyDefinitions.put(
				objectRelationship.getName(),
				new PropertyDefinition(
					Collections.singleton(ObjectEntry.class), null,
					StringUtil.removeFirst(
						relatedObjectDefinition.getName(), "C_"),
					StringBundler.concat(
						"Information about the object relationship ",
						objectRelationship.getName(),
						" can be embedded with \"nestedFields\"."),
					objectRelationship.getName(),
					_getPropertyType(objectDefinition, objectRelationship),
					propertyValidator, false));
		}

		return extendedPropertyDefinitions;
	}

	@Override
	public void setExtendedProperties(
			long companyId, long userId, String className, Object entity,
			Map<String, Serializable> extendedProperties)
		throws Exception {

		ObjectDefinition objectDefinition = fetchObjectDefinition(
			companyId, className);

		if (objectDefinition == null) {
			throw new IllegalStateException(
				"No object definition exists with class name " + className);
		}

		for (Map.Entry<String, Serializable> entry :
				extendedProperties.entrySet()) {

			ObjectRelationship objectRelationship =
				_objectRelationshipLocalService.
					getObjectRelationshipByObjectDefinitionId(
						objectDefinition.getObjectDefinitionId(),
						entry.getKey());

			ObjectDefinition relatedObjectDefinition =
				_getRelatedObjectDefinition(
					objectDefinition, objectRelationship);

			ObjectEntryManager objectEntryManager =
				_objectEntryManagerRegistry.getObjectEntryManager(
					relatedObjectDefinition.getStorageType());

			ObjectRelationshipElementsParser objectRelationshipElementsParser =
				_objectRelationshipElementsParserRegistry.
					getObjectRelationshipElementsParser(
						relatedObjectDefinition.getClassName(),
						relatedObjectDefinition.getCompanyId(),
						objectRelationship.getType());

			List<ObjectEntry> nestedObjectEntries =
				objectRelationshipElementsParser.parse(
					objectRelationship, entry.getValue());

			for (ObjectEntry nestedObjectEntry : nestedObjectEntries) {
				nestedObjectEntry = objectEntryManager.updateObjectEntry(
					objectDefinition.getCompanyId(),
					_getDefaultDTOConverterContext(
						objectDefinition, getPrimaryKey(entity), null),
					nestedObjectEntry.getExternalReferenceCode(),
					relatedObjectDefinition, nestedObjectEntry,
					relatedObjectDefinition.getScope());

				_relateNestedObjectEntry(
					objectDefinition, objectRelationship, getPrimaryKey(entity),
					nestedObjectEntry.getId());
			}

			NestedFieldsSupplier.addFieldName(entry.getKey());
		}
	}

	private DefaultDTOConverterContext _getDefaultDTOConverterContext(
		ObjectDefinition objectDefinition, Long objectEntryId,
		UriInfo uriInfo) {

		DefaultDTOConverterContext defaultDTOConverterContext =
			new DefaultDTOConverterContext(
				false, null, _dtoConverterRegistry, objectEntryId,
				LocaleUtil.fromLanguageId(
					objectDefinition.getDefaultLanguageId(), true, false),
				uriInfo, null);

		defaultDTOConverterContext.setAttribute("addActions", Boolean.FALSE);

		return defaultDTOConverterContext;
	}

	private PropertyDefinition.PropertyType _getPropertyType(
		ObjectDefinition objectDefinition,
		ObjectRelationship objectRelationship) {

		if (Objects.equals(
				objectRelationship.getType(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY) &&
			(objectDefinition.getObjectDefinitionId() ==
				objectRelationship.getObjectDefinitionId2())) {

			return PropertyDefinition.PropertyType.SINGLE_ELEMENT;
		}

		return PropertyDefinition.PropertyType.MULTIPLE_ELEMENT;
	}

	private ObjectDefinition _getRelatedObjectDefinition(
			ObjectDefinition objectDefinition,
			ObjectRelationship objectRelationship)
		throws Exception {

		long relatedObjectDefinitionId =
			objectRelationship.getObjectDefinitionId1();

		if (objectDefinition.getObjectDefinitionId() ==
				objectRelationship.getObjectDefinitionId1()) {

			relatedObjectDefinitionId =
				objectRelationship.getObjectDefinitionId2();
		}

		return _objectDefinitionLocalService.getObjectDefinition(
			relatedObjectDefinitionId);
	}

	private void _relateNestedObjectEntry(
			ObjectDefinition objectDefinition,
			ObjectRelationship objectRelationship, long primaryKey,
			long relatedPrimaryKey)
		throws Exception {

		long primaryKey1 = relatedPrimaryKey;
		long primaryKey2 = primaryKey;

		if (objectDefinition.getObjectDefinitionId() ==
				objectRelationship.getObjectDefinitionId1()) {

			primaryKey1 = primaryKey;
			primaryKey2 = relatedPrimaryKey;
		}

		_objectRelationshipService.addObjectRelationshipMappingTableValues(
			objectRelationship.getObjectRelationshipId(), primaryKey1,
			primaryKey2, new ServiceContext());
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

	@Reference
	private ObjectRelationshipElementsParserRegistry
		_objectRelationshipElementsParserRegistry;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private ObjectRelationshipService _objectRelationshipService;

	private class UnsupportedOperationPropertyValidator
		implements PropertyValidator {

		@Override
		public void validate(
			PropertyDefinition propertyDefinition, Object propertyValue) {

			throw new UnsupportedOperationException(
				"The property " + propertyDefinition.getPropertyName() +
					" cannot be set");
		}

	}

}