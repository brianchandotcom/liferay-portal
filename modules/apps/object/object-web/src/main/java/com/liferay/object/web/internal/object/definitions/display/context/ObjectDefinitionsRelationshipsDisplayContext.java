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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.object.system.SystemObjectDefinitionMetadataTracker;
import com.liferay.object.web.internal.configuration.activator.FFOneToOneRelationshipConfigurationActivator;
import com.liferay.object.web.internal.display.context.helper.ObjectRequestHelper;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class ObjectDefinitionsRelationshipsDisplayContext
	extends BaseObjectDefinitionsDisplayContext {

	public ObjectDefinitionsRelationshipsDisplayContext(
		FFOneToOneRelationshipConfigurationActivator
			ffOneToOneRelationshipConfigurationActivator,
		HttpServletRequest httpServletRequest,
		ModelResourcePermission<ObjectDefinition>
			objectDefinitionModelResourcePermission,
		ObjectDefinitionService objectDefinitionService,
		SystemObjectDefinitionMetadataTracker
			systemObjectDefinitionMetadataTracker) {

		super(httpServletRequest, objectDefinitionModelResourcePermission);

		_ffOneToOneRelationshipConfigurationActivator =
			ffOneToOneRelationshipConfigurationActivator;
		_objectDefinitionModelResourcePermission =
			objectDefinitionModelResourcePermission;
		_objectDefinitionService = objectDefinitionService;
		_systemObjectDefinitionMetadataTracker =
			systemObjectDefinitionMetadataTracker;

		_objectRequestHelper = new ObjectRequestHelper(httpServletRequest);
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems()
		throws Exception {

		return Arrays.asList(
			new FDSActionDropdownItem(
				PortletURLBuilder.create(
					getPortletURL()
				).setMVCRenderCommandName(
					"/object_definitions/edit_object_relationship"
				).setParameter(
					"objectRelationshipId", "{id}"
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString(),
				"view", "view",
				LanguageUtil.get(objectRequestHelper.getRequest(), "view"),
				"get", null, "sidePanel"),
			new FDSActionDropdownItem(
				null, "trash", "deleteObjectRelationship",
				LanguageUtil.get(objectRequestHelper.getRequest(), "delete"),
				"delete", "delete", null));
	}

	public JSONArray getObjectRelationshipDeletionTypesJSONArray() {
		return JSONUtil.putAll(
			JSONUtil.put(
				"label",
				LanguageUtil.get(
					_objectRequestHelper.getRequest(),
					ObjectRelationshipConstants.DELETION_TYPE_CASCADE)
			).put(
				"value", ObjectRelationshipConstants.DELETION_TYPE_CASCADE
			)
		).put(
			JSONUtil.put(
				"label",
				LanguageUtil.get(
					_objectRequestHelper.getRequest(),
					ObjectRelationshipConstants.DELETION_TYPE_DISASSOCIATE)
			).put(
				"value", ObjectRelationshipConstants.DELETION_TYPE_DISASSOCIATE
			)
		).put(
			JSONUtil.put(
				"label",
				LanguageUtil.get(
					_objectRequestHelper.getRequest(),
					ObjectRelationshipConstants.DELETION_TYPE_PREVENT)
			).put(
				"value", ObjectRelationshipConstants.DELETION_TYPE_PREVENT
			)
		);
	}

	public JSONObject getObjectRelationshipJSONObject(
			ObjectRelationship objectRelationship)
		throws PortalException {

		ObjectDefinition objectDefinition2 =
			_objectDefinitionService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId2());

		return JSONUtil.put(
			"deletionType", objectRelationship.getDeletionType()
		).put(
			"label", objectRelationship.getLabelMap()
		).put(
			"name", objectRelationship.getName()
		).put(
			"objectDefinitionId1",
			Long.valueOf(objectRelationship.getObjectDefinitionId1())
		).put(
			"objectDefinitionId2",
			Long.valueOf(objectRelationship.getObjectDefinitionId2())
		).put(
			"objectDefinitionName2", objectDefinition2.getShortName()
		).put(
			"objectRelationshipId",
			Long.valueOf(objectRelationship.getObjectRelationshipId())
		).put(
			"parameterObjectFieldId",
			objectRelationship.getParameterObjectFieldId()
		).put(
			"reverse", objectRelationship.isReverse()
		).put(
			"type", objectRelationship.getType()
		);
	}

	public String getRESTContextPath(ObjectDefinition objectDefinition) {
		if (!objectDefinition.isSystem()) {
			return objectDefinition.getRESTContextPath();
		}

		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata =
			_systemObjectDefinitionMetadataTracker.
				getSystemObjectDefinitionMetadata(objectDefinition.getName());

		if (systemObjectDefinitionMetadata == null) {
			return StringPool.BLANK;
		}

		return systemObjectDefinitionMetadata.getRESTContextPath();
	}

	public boolean isFFOneToOneRelationshipConfigurationEnabled() {
		return _ffOneToOneRelationshipConfigurationActivator.enabled();
	}

	public boolean isParameterRequired(ObjectDefinition objectDefinition) {
		String restContextPath = getRESTContextPath(objectDefinition);

		if (restContextPath.matches(".*/\\{\\w+}/.*")) {
			return true;
		}

		return false;
	}

	@Override
	protected String getAPIURI() {
		return "/object-relationships";
	}

	@Override
	protected UnsafeConsumer<DropdownItem, Exception>
		getCreationMenuDropdownItemUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref("addObjectRelationship");
			dropdownItem.setLabel(
				LanguageUtil.get(
					objectRequestHelper.getRequest(),
					"add-object-relationship"));
			dropdownItem.setTarget("event");
		};
	}

	private final FFOneToOneRelationshipConfigurationActivator
		_ffOneToOneRelationshipConfigurationActivator;
	private final ModelResourcePermission<ObjectDefinition>
		_objectDefinitionModelResourcePermission;
	private final ObjectDefinitionService _objectDefinitionService;
	private final ObjectRequestHelper _objectRequestHelper;
	private final SystemObjectDefinitionMetadataTracker
		_systemObjectDefinitionMetadataTracker;

}