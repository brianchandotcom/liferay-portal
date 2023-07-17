/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.helper;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedFieldsContext;
import com.liferay.portal.vulcan.fields.NestedFieldsContextThreadLocal;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.BadRequestException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Miguel Barcos
 * @author Carlos Correa
 * @author Alejandro Tardín
 */
@Component(service = ObjectEntryHelper.class)
public class ObjectEntryHelper {

	public Page<Map<String, Object>>
			getAPIApplicationSchemaPropertyValueMapPage(
				long companyId, APIApplication.Endpoint endpoint,
				Pagination pagination)
		throws Exception {

		APIApplication.Schema responseSchema = endpoint.getResponseSchema();

		ObjectDefinition schemaMainObjectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					responseSchema.
						getMainObjectDefinitionExternalReferenceCode(),
					companyId);

		Map<APIApplication.Property, ObjectField> propertyObjectFieldHashMap =
			new HashMap<>();

		for (APIApplication.Property property :
				responseSchema.getProperties()) {

			String objectRelationshipNames =
				property.getObjectRelationshipNames();

			List<String> objectRelationshipNamesList;

			if (objectRelationshipNames == null) {
				objectRelationshipNamesList = Collections.emptyList();
			}
			else {
				objectRelationshipNamesList = new ArrayList<>(
					Arrays.asList(objectRelationshipNames.split(",")));
			}

			propertyObjectFieldHashMap.put(
				property,
				_objectFieldLocalService.getObjectField(
					property.getObjectFieldExternalReferenceCode(),
					_getPropertyObjectDefinition(
						schemaMainObjectDefinition, objectRelationshipNamesList
					).getObjectDefinitionId()));
		}

		Page<ObjectEntry> objectEntriesPage = getObjectEntriesPage(
			companyId, _getODataFilterString(endpoint), pagination,
			schemaMainObjectDefinition.getExternalReferenceCode());

		List<Map<String, Object>> entities = new ArrayList<>();

		for (ObjectEntry objectEntry : objectEntriesPage.getItems()) {
			Map<String, Object> objectEntryProperties =
				_getObjectEntryProperties(objectEntry);

			Map<String, Object> entity = new HashMap<>();

			for (Map.Entry<APIApplication.Property, ObjectField>
					propertyObjectFieldEntry :
						propertyObjectFieldHashMap.entrySet()) {

				APIApplication.Property property =
					propertyObjectFieldEntry.getKey();

				String objectRelationshipNames =
					property.getObjectRelationshipNames();

				if (objectRelationshipNames == null) {
					entity.put(
						property.getName(),
						objectEntryProperties.get(
							property.getSourceFieldName()));
				}
				else {
					List<String> objectRelationshipNamesList = Arrays.asList(
						objectRelationshipNames.split(","));

					ObjectField objectField =
						propertyObjectFieldEntry.getValue();

					List<ObjectEntry> objectEntries = getObjectEntries(
						companyId, _getODataFilterString(endpoint), objectRelationshipNamesList,
						schemaMainObjectDefinition.getExternalReferenceCode());

					for (ObjectEntry entry : objectEntries) {
						Map<String, Object> properties = entry.getProperties();

						Object object = properties.get(
							objectRelationshipNamesList.get(
								objectRelationshipNamesList.size() - 1));

						if (object instanceof ObjectEntry[]) {
							List<Object> values = new ArrayList<>();

							for (ObjectEntry objectEntry1 :
									(ObjectEntry[])object) {

								Map<String, Object> properties1 =
									objectEntry1.getProperties();

								values.add(
									properties1.get(objectField.getName()));
							}

							entity.put(property.getName(), values);
						}
						else {
							ObjectEntry o1 = (ObjectEntry)object;

							Map<String, Object> properties1 =
								o1.getProperties();

							entity.put(
								property.getName(),
								properties1.get(objectField.getName()));
						}
					}
				}
			}

			entities.add(entity);
		}

		return Page.of(entities, pagination, objectEntriesPage.getTotalCount());
	}

	public List<ObjectEntry> getObjectEntries(
			long companyId, String filterString, List<String> nestedFields,
			String objectDefinitionExternalReferenceCode)
		throws Exception {

		NestedFieldsContext nestedFieldsContext = new NestedFieldsContext(
			1, nestedFields);

		NestedFieldsContext oldNestedFieldsContext =
			NestedFieldsContextThreadLocal.getAndSetNestedFieldsContext(
				nestedFieldsContext);

		try {
			return getObjectEntries(
				companyId, filterString, objectDefinitionExternalReferenceCode);
		}
		finally {
			NestedFieldsContextThreadLocal.setNestedFieldsContext(
				oldNestedFieldsContext);
		}
	}

	public List<ObjectEntry> getObjectEntries(
			long companyId, String filterString,
			String objectDefinitionExternalReferenceCode)
		throws Exception {

		Page<ObjectEntry> objectEntriesPage = getObjectEntriesPage(
			companyId, filterString,
			Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS),
			objectDefinitionExternalReferenceCode);

		return new ArrayList<>(objectEntriesPage.getItems());
	}

	public Page<ObjectEntry> getObjectEntriesPage(
			long companyId, String filterString, Pagination pagination,
			String objectDefinitionExternalReferenceCode)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					objectDefinitionExternalReferenceCode, companyId);

		if (objectDefinition == null) {
			return Page.of(Collections.emptyList());
		}

		PermissionThreadLocal.setPermissionChecker(
			_permissionCheckerFactory.create(
				_userLocalService.getUser(objectDefinition.getUserId())));

		return _objectEntryManager.getObjectEntries(
			companyId, objectDefinition, null, null,
			_getDefaultDTOConverterContext(objectDefinition), filterString,
			pagination, null, null);
	}

	public ObjectEntry getObjectEntry(
			long companyId, String filterString,
			String objectDefinitionExternalReferenceCode)
		throws Exception {

		List<ObjectEntry> objectEntries = getObjectEntries(
			companyId, filterString, objectDefinitionExternalReferenceCode);

		if (ListUtil.isEmpty(objectEntries)) {
			return null;
		}

		return objectEntries.get(0);
	}

	public boolean isValidObjectEntry(
			long objectEntryId, String externalReferenceCode)
		throws Exception {

		if (objectEntryId == 0) {
			return false;
		}

		com.liferay.object.model.ObjectEntry objectEntry =
			_objectEntryLocalService.fetchObjectEntry(objectEntryId);

		if (objectEntry == null) {
			return false;
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectEntry.getObjectDefinitionId());

		if (!Objects.equals(
				objectDefinition.getExternalReferenceCode(),
				externalReferenceCode)) {

			return false;
		}

		return true;
	}

	private DTOConverterContext _getDefaultDTOConverterContext(
			ObjectDefinition objectDefinition)
		throws Exception {

		return new DefaultDTOConverterContext(
			false, null, null, null, null, LocaleUtil.getSiteDefault(), null,
			_userLocalService.getUser(objectDefinition.getUserId()));
	}

	private Map<String, Object> _getObjectEntryProperties(
		ObjectEntry objectEntry) {

		return HashMapBuilder.<String, Object>putAll(
			objectEntry.getProperties()
		).put(
			"createDate", objectEntry.getDateCreated()
		).put(
			"externalReferenceCode", objectEntry.getExternalReferenceCode()
		).put(
			"modifiedDate", objectEntry.getDateModified()
		).build();
	}

	private String _getODataFilterString(APIApplication.Endpoint endpoint) {
		APIApplication.Filter filter = endpoint.getFilter();

		if (filter == null) {
			return null;
		}

		return filter.getODataFilterString();
	}

	private ObjectDefinition _getPropertyObjectDefinition(
			ObjectDefinition objectDefinition,
			List<String> objectRelationshipNames)
		throws Exception {

		if (ListUtil.isEmpty(objectRelationshipNames)) {
			return objectDefinition;
		}

		return _getPropertyObjectDefinition(
			_getRelatedObjectDefinition(
				objectDefinition,
				_objectRelationshipLocalService.
					getObjectRelationshipByObjectDefinitionId(
						objectDefinition.getObjectDefinitionId(),
						StringUtil.trim(objectRelationshipNames.remove(0)))),
			objectRelationshipNames);
	}

	private ObjectDefinition _getRelatedObjectDefinition(
			ObjectDefinition objectDefinition,
			ObjectRelationship objectRelationship)
		throws Exception {

		long relatedObjectDefinitionId = 0;

		if (objectRelationship.getObjectDefinitionId1() ==
				objectDefinition.getObjectDefinitionId()) {

			relatedObjectDefinitionId =
				objectRelationship.getObjectDefinitionId2();
		}
		else {
			relatedObjectDefinitionId =
				objectRelationship.getObjectDefinitionId1();
		}

		ObjectDefinition relatedObjectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				relatedObjectDefinitionId);

		if (!relatedObjectDefinition.isActive()) {
			throw new BadRequestException(
				"Object definition " +
					relatedObjectDefinition.getObjectDefinitionId() +
						" is inactive");
		}

		return relatedObjectDefinition;
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference(target = "(object.entry.manager.storage.type=default)")
	private ObjectEntryManager _objectEntryManager;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private UserLocalService _userLocalService;

}