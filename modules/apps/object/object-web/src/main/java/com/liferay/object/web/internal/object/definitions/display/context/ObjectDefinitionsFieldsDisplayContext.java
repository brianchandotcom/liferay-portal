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

package com.liferay.object.web.internal.object.definitions.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.list.type.service.ListTypeDefinitionService;
import com.liferay.object.admin.rest.dto.v1_0.util.ObjectFieldUtil;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeTracker;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.web.internal.object.definitions.display.context.util.ObjectCodeEditorUtil;
import com.liferay.object.web.internal.util.ObjectFieldBusinessTypeUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeFormatter;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 * @author Gabriel Albuquerque
 */
public class ObjectDefinitionsFieldsDisplayContext
	extends BaseObjectDefinitionsDisplayContext {

	public ObjectDefinitionsFieldsDisplayContext(
		HttpServletRequest httpServletRequest,
		ListTypeDefinitionService listTypeDefinitionService,
		ModelResourcePermission<ObjectDefinition>
			objectDefinitionModelResourcePermission,
		ObjectFieldBusinessTypeTracker objectFieldBusinessTypeTracker,
		ObjectRelationshipLocalService objectRelationshipLocalService) {

		super(httpServletRequest, objectDefinitionModelResourcePermission);

		_listTypeDefinitionService = listTypeDefinitionService;
		_objectFieldBusinessTypeTracker = objectFieldBusinessTypeTracker;
		_objectRelationshipLocalService = objectRelationshipLocalService;
	}

	public CreationMenu getCreationMenu(ObjectDefinition objectDefinition)
		throws PortalException {

		CreationMenu creationMenu = new CreationMenu();

		if (!hasUpdateObjectDefinitionPermission()) {
			return creationMenu;
		}

		creationMenu.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref("addObjectField");
				dropdownItem.setLabel(
					LanguageUtil.get(
						objectRequestHelper.getRequest(), "add-object-field"));
				dropdownItem.setTarget("event");
			});

		return creationMenu;
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems()
		throws Exception {

		return Arrays.asList(
			new FDSActionDropdownItem(
				PortletURLBuilder.create(
					getPortletURL()
				).setMVCRenderCommandName(
					"/object_definitions/edit_object_field"
				).setParameter(
					"objectFieldId", "{id}"
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString(),
				"view", "view",
				LanguageUtil.get(objectRequestHelper.getRequest(), "view"),
				"get", null, "sidePanel"),
			new FDSActionDropdownItem(
				"/o/object-admin/v1.0/object-fields/{id}", "trash", "delete",
				LanguageUtil.get(objectRequestHelper.getRequest(), "delete"),
				"delete", "delete", "async"));
	}

	public String[] getForbiddenLastCharacters() {
		List<String> forbiddenLastCharacters = new ArrayList<>();

		for (String forbiddenLastCharacter :
				PropsValues.DL_CHAR_LAST_BLACKLIST) {

			if (forbiddenLastCharacter.startsWith(
					UnicodeFormatter.UNICODE_PREFIX)) {

				forbiddenLastCharacter = UnicodeFormatter.parseString(
					forbiddenLastCharacter);
			}

			forbiddenLastCharacters.add(forbiddenLastCharacter);
		}

		return forbiddenLastCharacters.toArray(new String[0]);
	}

	public List<Map<String, String>> getObjectFieldBusinessTypeMaps(
		boolean includeRelationshipObjectFieldBusinessType, Locale locale) {

		List<ObjectFieldBusinessType> objectFieldBusinessTypes =
			_objectFieldBusinessTypeTracker.getObjectFieldBusinessTypes();

		Stream<ObjectFieldBusinessType> stream =
			objectFieldBusinessTypes.stream();

		return ObjectFieldBusinessTypeUtil.getObjectFieldBusinessTypeMaps(
			locale,
			stream.filter(
				objectFieldBusinessType ->
					objectFieldBusinessType.isVisible() &&
					(!StringUtil.equals(
						objectFieldBusinessType.getName(),
						ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP) ||
					 includeRelationshipObjectFieldBusinessType)
			).collect(
				Collectors.toList()
			));
	}

	public List<Map<String, Object>> getObjectFieldCodeEditorElements() {
		return ObjectCodeEditorUtil.getCodeEditorElements(
			true, true, false, objectRequestHelper.getLocale(),
			getObjectDefinitionId());
	}

	public JSONObject getObjectFieldJSONObject(ObjectField objectField) {
		return ObjectFieldUtil.toJSONObject(
			_listTypeDefinitionService, objectField);
	}

	public Long getObjectRelationshipId(ObjectField objectField) {
		if (StringUtil.equals(
				objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP)) {

			ObjectRelationship objectRelationship =
				_objectRelationshipLocalService.
					fetchObjectRelationshipByObjectFieldId2(
						objectField.getObjectFieldId());

			return objectRelationship.getObjectRelationshipId();
		}

		return null;
	}

	@Override
	protected String getAPIURI() {
		return "/object-fields";
	}

	private final ListTypeDefinitionService _listTypeDefinitionService;
	private final ObjectFieldBusinessTypeTracker
		_objectFieldBusinessTypeTracker;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;

}