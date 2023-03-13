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

package com.liferay.object.rest.internal.dto.v1_0.converter;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.entry.util.ObjectEntryValuesUtil;
import com.liferay.object.field.setting.util.ObjectFieldSettingUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.dto.v1_0.FileEntry;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.dto.v1_0.Status;
import com.liferay.object.rest.dto.v1_0.TaxonomyCategoryBrief;
import com.liferay.object.rest.dto.v1_0.util.CreatorUtil;
import com.liferay.object.rest.dto.v1_0.util.LinkUtil;
import com.liferay.object.rest.internal.dto.v1_0.util.TaxonomyCategoryBriefUtil;
import com.liferay.object.rest.internal.util.DTOConverterUtil;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.object.system.SystemObjectDefinitionMetadataRegistry;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier de Arcos
 */
@Component(
	property = "dto.class.name=com.liferay.object.model.ObjectEntry",
	service = {DTOConverter.class, ObjectEntryDTOConverter.class}
)
public class ObjectEntryDTOConverter
	implements DTOConverter<com.liferay.object.model.ObjectEntry, ObjectEntry> {

	@Override
	public String getContentType() {
		return ObjectEntry.class.getSimpleName();
	}

	@Override
	public ObjectEntry toDTO(
			DTOConverterContext dtoConverterContext,
			com.liferay.object.model.ObjectEntry objectEntry)
		throws Exception {

		UriInfo uriInfo = dtoConverterContext.getUriInfo();

		if (uriInfo == null) {
			return _toDTO(
				dtoConverterContext,
				Math.min(1, PropsValues.OBJECT_NESTED_FIELDS_MAX_QUERY_DEPTH),
				objectEntry);
		}

		MultivaluedMap<String, String> queryParameters =
			uriInfo.getQueryParameters();

		return _toDTO(
			dtoConverterContext,
			Math.min(
				GetterUtil.getInteger(
					queryParameters.getFirst("nestedFieldsDepth"), 1),
				PropsValues.OBJECT_NESTED_FIELDS_MAX_QUERY_DEPTH),
			objectEntry);
	}

	private void _addNestedFields(
			DTOConverterContext dtoConverterContext, Map<String, Object> map,
			int nestedFieldsDepth, String objectFieldName,
			ObjectRelationship objectRelationship, long primaryKey)
		throws Exception {

		UriInfo uriInfo = dtoConverterContext.getUriInfo();

		if (uriInfo == null) {
			return;
		}

		MultivaluedMap<String, String> queryParameters =
			uriInfo.getQueryParameters();

		String nestedFields = queryParameters.getFirst("nestedFields");

		if (nestedFields == null) {
			return;
		}

		Object value = null;

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId1());

		if (objectDefinition.isSystem()) {
			if (FeatureFlagManagerUtil.isEnabled("LPS-172094")) {
				SystemObjectDefinitionMetadata systemObjectDefinitionMetadata =
					_systemObjectDefinitionMetadataRegistry.
						getSystemObjectDefinitionMetadata(
							objectDefinition.getName());

				value = DTOConverterUtil.toDTO(
					systemObjectDefinitionMetadata.
						getBaseModelByExternalReferenceCode(
							systemObjectDefinitionMetadata.
								getExternalReferenceCode(primaryKey),
							objectDefinition.getCompanyId()),
					_dtoConverterRegistry, systemObjectDefinitionMetadata,
					dtoConverterContext.getUser());
			}
			else {
				value = _objectEntryLocalService.getSystemModelAttributes(
					objectDefinition, primaryKey);
			}
		}
		else {
			value = _toDTO(
				_getDTOConverterContext(dtoConverterContext, primaryKey),
				nestedFieldsDepth - 1,
				_objectEntryLocalService.getObjectEntry(primaryKey));
		}

		String objectFieldNameNestedField = StringUtil.replaceLast(
			objectFieldName.substring(
				objectFieldName.lastIndexOf(StringPool.UNDERLINE) + 1),
			"Id", "");

		for (String nestedField : nestedFields.split(",")) {
			if (nestedField.contains(objectFieldNameNestedField)) {
				map.put(
					StringUtil.replaceLast(objectFieldName, "Id", ""), value);
			}

			if (nestedField.equals(objectRelationship.getName())) {
				map.put(nestedField, value);
			}
		}
	}

	private void _addObjectRelationshipNames(
		Map<String, Object> map, ObjectField objectField,
		String objectFieldName, ObjectRelationship objectRelationship,
		long primaryKey, Map<String, Serializable> values) {

		String objectRelationshipERCObjectFieldName =
			ObjectFieldSettingUtil.getValue(
				ObjectFieldSettingConstants.
					NAME_OBJECT_RELATIONSHIP_ERC_OBJECT_FIELD_NAME,
				objectField);

		String relatedObjectEntryERC = GetterUtil.getString(
			values.get(objectRelationshipERCObjectFieldName));

		if (map.get(objectRelationship.getName()) == null) {
			map.put(
				objectRelationship.getName() + "ERC", relatedObjectEntryERC);
		}

		map.put(objectFieldName, primaryKey);

		map.put(objectRelationshipERCObjectFieldName, relatedObjectEntryERC);
	}

	private DTOConverterContext _getDTOConverterContext(
		DTOConverterContext dtoConverterContext, long objectEntryId) {

		UriInfo uriInfo = dtoConverterContext.getUriInfo();

		return new DefaultDTOConverterContext(
			dtoConverterContext.isAcceptAllLanguages(), null,
			dtoConverterContext.getDTOConverterRegistry(),
			dtoConverterContext.getHttpServletRequest(), objectEntryId,
			dtoConverterContext.getLocale(), uriInfo,
			dtoConverterContext.getUser());
	}

	private ListEntry _getListEntry(
		DTOConverterContext dtoConverterContext, String key,
		long listTypeDefinitionId) {

		ListTypeEntry listTypeEntry =
			_listTypeEntryLocalService.fetchListTypeEntry(
				listTypeDefinitionId, key);

		if (listTypeEntry == null) {
			return null;
		}

		return new ListEntry() {
			{
				key = listTypeEntry.getKey();
				name = listTypeEntry.getName(dtoConverterContext.getLocale());
				name_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					listTypeEntry.getNameMap());
			}
		};
	}

	private ObjectEntry[] _getManyToManyRelationshipObjectEntries(
		DTOConverterContext dtoConverterContext, int nestedFieldsDepth,
		com.liferay.object.model.ObjectEntry objectEntry,
		ObjectRelationship objectRelationship) {

		try {
			return _toObjectEntries(
				dtoConverterContext, nestedFieldsDepth,
				_objectEntryLocalService.getManyToManyObjectEntries(
					objectEntry.getGroupId(),
					objectRelationship.getObjectRelationshipId(),
					objectEntry.getObjectEntryId(), true,
					objectRelationship.isReverse(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS));
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}

			return null;
		}
	}

	private ObjectDefinition _getObjectDefinition(
			DTOConverterContext dtoConverterContext,
			com.liferay.object.model.ObjectEntry objectEntry)
		throws Exception {

		ObjectDefinition objectDefinition =
			(ObjectDefinition)dtoConverterContext.getAttribute(
				"objectDefinition");

		if (objectDefinition == null) {
			objectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					objectEntry.getObjectDefinitionId());
		}

		return objectDefinition;
	}

	private ObjectEntry[] _getOneToManyRelationshipObjectEntries(
		DTOConverterContext dtoConverterContext, int nestedFieldsDepth,
		com.liferay.object.model.ObjectEntry objectEntry,
		ObjectRelationship objectRelationship) {

		try {
			return _toObjectEntries(
				dtoConverterContext, nestedFieldsDepth,
				_objectEntryLocalService.getOneToManyObjectEntries(
					objectEntry.getGroupId(),
					objectRelationship.getObjectRelationshipId(),
					objectEntry.getObjectEntryId(), true, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS));
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}

			return null;
		}
	}

	private String _getScopeKey(
		ObjectDefinition objectDefinition,
		com.liferay.object.model.ObjectEntry objectEntry) {

		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				objectDefinition.getScope());

		if (objectScopeProvider.isGroupAware()) {
			Group group = _groupLocalService.fetchGroup(
				objectEntry.getGroupId());

			if (group == null) {
				return null;
			}

			return group.getGroupKey();
		}

		return null;
	}

	private ObjectEntry _toDTO(
			DTOConverterContext dtoConverterContext, int nestedFieldsDepth,
			com.liferay.object.model.ObjectEntry objectEntry)
		throws Exception {

		ObjectDefinition objectDefinition = _getObjectDefinition(
			dtoConverterContext, objectEntry);

		return new ObjectEntry() {
			{
				actions = dtoConverterContext.getActions();
				creator = CreatorUtil.toCreator(
					_portal, dtoConverterContext.getUriInfo(),
					_userLocalService.fetchUser(objectEntry.getUserId()));
				dateCreated = objectEntry.getCreateDate();
				dateModified = objectEntry.getModifiedDate();
				externalReferenceCode = objectEntry.getExternalReferenceCode();
				id = objectEntry.getObjectEntryId();

				if (FeatureFlagManagerUtil.isEnabled("LPS-176651")) {
					keywords = ListUtil.toArray(
						_assetTagLocalService.getTags(
							objectDefinition.getClassName(),
							objectEntry.getObjectEntryId()),
						AssetTag.NAME_ACCESSOR);
				}

				properties = _toProperties(
					dtoConverterContext, nestedFieldsDepth, objectDefinition,
					objectEntry);
				scopeKey = _getScopeKey(objectDefinition, objectEntry);
				status = new Status() {
					{
						code = objectEntry.getStatus();
						label = WorkflowConstants.getStatusLabel(
							objectEntry.getStatus());
						label_i18n = _language.get(
							LanguageResources.getResourceBundle(
								dtoConverterContext.getLocale()),
							WorkflowConstants.getStatusLabel(
								objectEntry.getStatus()));
					}
				};

				if (FeatureFlagManagerUtil.isEnabled("LPS-176651")) {
					taxonomyCategoryBriefs = TransformUtil.transformToArray(
						_assetCategoryLocalService.getCategories(
							objectDefinition.getClassName(),
							objectEntry.getObjectEntryId()),
						assetCategory ->
							TaxonomyCategoryBriefUtil.toTaxonomyCategoryBrief(
								assetCategory, dtoConverterContext),
						TaxonomyCategoryBrief.class);
				}
			}
		};
	}

	private ObjectEntry[] _toObjectEntries(
		DTOConverterContext dtoConverterContext, int nestedFieldsDepth,
		List<com.liferay.object.model.ObjectEntry> objectEntries) {

		return TransformUtil.transformToArray(
			objectEntries,
			objectEntry -> {
				try {
					return _toDTO(
						_getDTOConverterContext(
							dtoConverterContext,
							objectEntry.getObjectEntryId()),
						nestedFieldsDepth - 1, objectEntry);
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(exception);
					}

					return null;
				}
			},
			ObjectEntry.class);
	}

	private Map<String, Object> _toProperties(
			DTOConverterContext dtoConverterContext, int nestedFieldsDepth,
			ObjectDefinition objectDefinition,
			com.liferay.object.model.ObjectEntry objectEntry)
		throws Exception {

		Map<String, Object> map = new HashMap<>();

		Map<String, Serializable> values = objectEntry.getValues();

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				objectDefinition.getObjectDefinitionId(), false);

		for (ObjectField objectField : objectFields) {
			long listTypeDefinitionId = objectField.getListTypeDefinitionId();

			String objectFieldName = objectField.getName();

			Serializable serializable = values.get(objectFieldName);

			if (listTypeDefinitionId != 0) {
				if (StringUtil.equals(
						objectField.getBusinessType(),
						ObjectFieldConstants.
							BUSINESS_TYPE_MULTISELECT_PICKLIST)) {

					List<ListEntry> listEntries = new ArrayList<>();

					for (String key :
							StringUtil.split(
								(String)serializable,
								StringPool.COMMA_AND_SPACE)) {

						listEntries.add(
							_getListEntry(
								dtoConverterContext, key,
								listTypeDefinitionId));
					}

					map.put(objectFieldName, listEntries);

					continue;
				}

				ListEntry listEntry = _getListEntry(
					dtoConverterContext, (String)serializable,
					listTypeDefinitionId);

				if (listEntry == null) {
					continue;
				}

				map.put(objectFieldName, listEntry);
			}
			else if (Objects.equals(
						objectField.getBusinessType(),
						ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT)) {

				long fileEntryId = GetterUtil.getLong(
					values.get(objectField.getName()));

				if (fileEntryId == 0) {
					continue;
				}

				DLFileEntry dlFileEntry =
					_dLFileEntryLocalService.fetchDLFileEntry(fileEntryId);

				if (dlFileEntry != null) {
					map.put(
						objectFieldName,
						new FileEntry() {
							{
								id = dlFileEntry.getFileEntryId();
								link = LinkUtil.toLink(
									_dlAppService, dlFileEntry, _dlURLHelper,
									objectDefinition.getExternalReferenceCode(),
									objectEntry.getExternalReferenceCode(),
									_portal);
								name = dlFileEntry.getFileName();
							}
						});
				}
				else {
					map.put(objectFieldName, new FileEntry());
				}
			}
			else if (Objects.equals(
						objectField.getBusinessType(),
						ObjectFieldConstants.BUSINESS_TYPE_RICH_TEXT)) {

				map.put(objectFieldName, serializable);
				map.put(
					objectFieldName + "RawText",
					ObjectEntryValuesUtil.getValueString(objectField, values));
			}
			else if ((nestedFieldsDepth > 0) &&
					 Objects.equals(
						 objectField.getRelationshipType(),
						 ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

				long primaryKey = GetterUtil.getLong(serializable);

				ObjectRelationship objectRelationship =
					_objectRelationshipLocalService.
						fetchObjectRelationshipByObjectFieldId2(
							objectField.getObjectFieldId());

				if (primaryKey > 0) {
					_addNestedFields(
						dtoConverterContext, map, nestedFieldsDepth,
						objectFieldName, objectRelationship, primaryKey);
				}

				_addObjectRelationshipNames(
					map, objectField, objectFieldName, objectRelationship,
					primaryKey, values);
			}
			else if ((nestedFieldsDepth == 0) &&
					 Objects.equals(
						 objectField.getRelationshipType(),
						 ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

				ObjectRelationship objectRelationship =
					_objectRelationshipLocalService.
						fetchObjectRelationshipByObjectFieldId2(
							objectField.getObjectFieldId());

				_addObjectRelationshipNames(
					map, objectField, objectFieldName, objectRelationship,
					(long)serializable, values);
			}
			else {
				map.put(objectFieldName, serializable);
			}
		}

		if (nestedFieldsDepth > 0) {
			UriInfo uriInfo = dtoConverterContext.getUriInfo();

			if (uriInfo != null) {
				MultivaluedMap<String, String> queryParameters =
					uriInfo.getQueryParameters();

				String nestedFields = queryParameters.getFirst("nestedFields");

				if (nestedFields == null) {
					values.remove(objectDefinition.getPKObjectFieldName());

					return map;
				}

				List<ObjectRelationship> objectRelationships =
					_objectRelationshipLocalService.getObjectRelationships(
						objectDefinition.getObjectDefinitionId());

				for (ObjectRelationship objectRelationship :
						objectRelationships) {

					List<String> strings = Arrays.asList(
						nestedFields.split(","));

					if (!strings.contains(objectRelationship.getName())) {
						continue;
					}

					ObjectEntry[] objectEntries = new ObjectEntry[0];

					if (Objects.equals(
							objectRelationship.getType(),
							ObjectRelationshipConstants.TYPE_MANY_TO_MANY)) {

						objectEntries = _getManyToManyRelationshipObjectEntries(
							dtoConverterContext, nestedFieldsDepth, objectEntry,
							objectRelationship);
					}
					else if (Objects.equals(
								objectRelationship.getType(),
								ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

						objectEntries = _getOneToManyRelationshipObjectEntries(
							dtoConverterContext, nestedFieldsDepth, objectEntry,
							objectRelationship);
					}

					map.put(objectRelationship.getName(), objectEntries);
				}
			}
		}

		values.remove(objectDefinition.getPKObjectFieldName());

		return map;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryDTOConverter.class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLFileEntryLocalService _dLFileEntryLocalService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private ObjectScopeProviderRegistry _objectScopeProviderRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private SystemObjectDefinitionMetadataRegistry
		_systemObjectDefinitionMetadataRegistry;

	@Reference
	private UserLocalService _userLocalService;

}