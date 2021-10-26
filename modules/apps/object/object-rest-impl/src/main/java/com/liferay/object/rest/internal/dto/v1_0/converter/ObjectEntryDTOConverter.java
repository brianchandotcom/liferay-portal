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

import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.dto.v1_0.Status;
import com.liferay.object.rest.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

		ObjectDefinition objectDefinition = _getObjectDefinition(
			dtoConverterContext, objectEntry);

		Locale locale = dtoConverterContext.getLocale();

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
					dtoConverterContext.isAcceptAllLanguages(), locale,
					objectDefinition, objectEntry);
				scopeKey = _getScopeKey(objectDefinition, objectEntry);
				status = new Status() {
					{
						code = objectEntry.getStatus();
						label = WorkflowConstants.getStatusLabel(
							objectEntry.getStatus());
						label_i18n = LanguageUtil.get(
							LanguageResources.getResourceBundle(locale),
							WorkflowConstants.getStatusLabel(
								objectEntry.getStatus()));
					}
				};
			}
		};
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

	private Map<String, Object> _toProperties(
		boolean acceptAllLanguages, Locale locale,
		ObjectDefinition objectDefinition,
		com.liferay.object.model.ObjectEntry objectEntry) {

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				objectDefinition.getObjectDefinitionId());

		Map<String, Object> map = new HashMap<>();

		Map<String, Serializable> values = objectEntry.getValues();

		for (ObjectField objectField : objectFields) {
			long listTypeDefinitionId = objectField.getListTypeDefinitionId();

			Serializable serializable = values.get(objectField.getName());

			if (listTypeDefinitionId != 0) {
				ListTypeEntry listTypeEntry =
					_listTypeEntryLocalService.fetchListTypeEntry(
						listTypeDefinitionId, (String)serializable);

				if (listTypeEntry == null) {
					continue;
				}

				map.put(
					objectField.getName(),
					HashMapBuilder.<String, Object>put(
						"key", listTypeEntry.getKey()
					).put(
						"name", listTypeEntry.getName(locale)
					).put(
						"name_i18n",
						LocalizedMapUtil.getI18nMap(
							acceptAllLanguages, listTypeEntry.getNameMap())
					).build());
			}
			else {
				map.put(objectField.getName(), serializable);
			}
		}

		values.remove(objectDefinition.getPKObjectFieldName());

		return map;
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectScopeProviderRegistry _objectScopeProviderRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}