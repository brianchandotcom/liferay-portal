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

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.dto.v1_0.FileEntry;
import com.liferay.object.rest.dto.v1_0.Link;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.dto.v1_0.Status;
import com.liferay.object.rest.dto.v1_0.util.CreatorUtil;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.util.ObjectEntryFieldValueUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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

		Optional<UriInfo> uriInfoOptional =
			dtoConverterContext.getUriInfoOptional();

		UriInfo uriInfo = uriInfoOptional.orElse(null);

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

	private DTOConverterContext _getDTOConverterContext(
		DTOConverterContext dtoConverterContext, long objectEntryId) {

		Optional<UriInfo> uriInfoOptional =
			dtoConverterContext.getUriInfoOptional();

		UriInfo uriInfo = uriInfoOptional.orElse(null);

		return new DefaultDTOConverterContext(
			dtoConverterContext.isAcceptAllLanguages(), null,
			dtoConverterContext.getDTOConverterRegistry(),
			dtoConverterContext.getHttpServletRequest(), objectEntryId,
			dtoConverterContext.getLocale(), uriInfo,
			dtoConverterContext.getUser());
	}

	private ListEntry _getListEntry(
		long listTypeDefinitionId, DTOConverterContext dtoConverterContext,
		String key) {

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
					_portal, dtoConverterContext.getUriInfoOptional(),
					_userLocalService.fetchUser(objectEntry.getUserId()));
				dateCreated = objectEntry.getCreateDate();
				dateModified = objectEntry.getModifiedDate();
				externalReferenceCode = objectEntry.getExternalReferenceCode();
				id = objectEntry.getObjectEntryId();
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
								listTypeDefinitionId, dtoConverterContext,
								key));
					}

					map.put(objectFieldName, listEntries);

					continue;
				}

				ListEntry listEntry = _getListEntry(
					listTypeDefinitionId, dtoConverterContext,
					(String)serializable);

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

				DLFileEntry dlFileEntry = _dLFileEntryLocalService.getFileEntry(
					fileEntryId);

				Link fileEntryLink = new Link() {
					{
						href = StringBundler.concat(
							_portal.getPathContext(), _portal.getPathMain(),
							"/portal/login");
						label = dlFileEntry.getFileName();
					}
				};

				try {
					com.liferay.portal.kernel.repository.model.FileEntry
						fileEntry = _dlAppService.getFileEntry(fileEntryId);

					fileEntryLink.setHref(
						_dlURLHelper.getDownloadURL(
							fileEntry, fileEntry.getFileVersion(), null,
							StringPool.BLANK));
				}
				catch (PrincipalException principalException) {
					if (_log.isWarnEnabled()) {
						_log.warn(principalException);
					}
				}

				map.put(
					objectFieldName,
					new FileEntry() {
						{
							id = dlFileEntry.getFileEntryId();
							link = fileEntryLink;
							name = dlFileEntry.getFileName();
						}
					});
			}
			else if (Objects.equals(
						objectField.getBusinessType(),
						ObjectFieldConstants.BUSINESS_TYPE_RICH_TEXT)) {

				map.put(objectFieldName, serializable);
				map.put(
					objectFieldName + "RawText",
					ObjectEntryFieldValueUtil.getValueString(
						objectField, values));
			}
			else if ((nestedFieldsDepth > 0) &&
					 Objects.equals(
						 objectField.getRelationshipType(),
						 ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

				long objectEntryId = 0;

				if (serializable != null) {
					if (GetterUtil.getLong(serializable) > 0) {
						objectEntryId = (long)serializable;
					}

					Optional<UriInfo> uriInfoOptional =
						dtoConverterContext.getUriInfoOptional();

					int underlineLastIndex = objectFieldName.lastIndexOf(
						StringPool.UNDERLINE);

					if ((objectEntryId != 0) &&
						uriInfoOptional.map(
							UriInfo::getQueryParameters
						).map(
							queryParameters -> queryParameters.getFirst(
								"nestedFields")
						).map(
							nestedFields -> nestedFields.contains(
								StringUtil.replaceLast(
									objectFieldName.substring(
										underlineLastIndex + 1),
									"Id", ""))
						).orElse(
							false
						)) {

						ObjectRelationship objectRelationship =
							_objectRelationshipLocalService.
								fetchObjectRelationshipByObjectFieldId2(
									objectField.getObjectFieldId());

						ObjectDefinition relatedObjectDefinition =
							_objectDefinitionLocalService.getObjectDefinition(
								objectRelationship.getObjectDefinitionId1());

						if (relatedObjectDefinition.isSystem()) {
							map.put(
								StringUtil.replaceLast(
									objectFieldName, "Id", ""),
								_objectEntryLocalService.
									getSystemModelAttributes(
										relatedObjectDefinition,
										objectEntryId));
						}
						else {
							map.put(
								StringUtil.replaceLast(
									objectFieldName, "Id", ""),
								_toDTO(
									_getDTOConverterContext(
										dtoConverterContext, objectEntryId),
									nestedFieldsDepth - 1,
									_objectEntryLocalService.getObjectEntry(
										objectEntryId)));
						}
					}
				}

				map.put(objectFieldName, objectEntryId);
			}
			else {
				map.put(objectFieldName, serializable);
			}
		}

		if (nestedFieldsDepth > 0) {
			List<ObjectRelationship> objectRelationships =
				_objectRelationshipLocalService.getObjectRelationships(
					objectDefinition.getObjectDefinitionId());

			Optional<UriInfo> uriInfoOptional =
				dtoConverterContext.getUriInfoOptional();

			for (ObjectRelationship objectRelationship : objectRelationships) {
				if (!uriInfoOptional.map(
						UriInfo::getQueryParameters
					).map(
						queryParameters -> queryParameters.getFirst(
							"nestedFields")
					).map(
						nestedFields -> {
							List<String> strings = Arrays.asList(
								nestedFields.split(","));

							return strings.contains(
								objectRelationship.getName());
						}
					).orElse(
						false
					)) {

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

		values.remove(objectDefinition.getPKObjectFieldName());

		return map;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryDTOConverter.class);

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLFileEntryLocalService _dLFileEntryLocalService;

	@Reference
	private DLURLHelper _dlURLHelper;

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
	private UserLocalService _userLocalService;

}