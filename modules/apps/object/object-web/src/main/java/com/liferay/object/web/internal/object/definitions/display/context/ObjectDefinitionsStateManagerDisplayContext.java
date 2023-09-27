/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.definitions.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.list.type.service.ListTypeDefinitionService;
import com.liferay.object.admin.rest.dto.v1_0.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Montenegro
 */
public class ObjectDefinitionsStateManagerDisplayContext
	extends BaseObjectDefinitionsDisplayContext {

	public ObjectDefinitionsStateManagerDisplayContext(
		HttpServletRequest httpServletRequest,
		ListTypeDefinitionService listTypeDefinitionService,
		ModelResourcePermission<ObjectDefinition>
			objectDefinitionModelResourcePermission,
		ObjectFieldSettingLocalService objectFieldSettingLocalService) {

		super(httpServletRequest, objectDefinitionModelResourcePermission);

		_listTypeDefinitionService = listTypeDefinitionService;
		_objectFieldSettingLocalService = objectFieldSettingLocalService;
	}

	public CreationMenu getCreationMenu() throws PortalException {
		return new CreationMenu();
	}

	public String getEditObjectValidationURL() throws Exception {
		return PortletURLBuilder.create(
			getPortletURL()
		).setMVCRenderCommandName(
			"/object_definitions/edit_object_state"
		).setParameter(
			"objectFieldId", "{id}"
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems()
		throws Exception {

		boolean hasUpdatePermission = hasUpdateObjectDefinitionPermission();

		return Collections.singletonList(
			new FDSActionDropdownItem(
				getEditObjectValidationURL(),
				hasUpdatePermission ? "pencil" : "view",
				hasUpdatePermission ? "edit" : "view",
				LanguageUtil.get(
					objectRequestHelper.getRequest(),
					hasUpdatePermission ? "edit" : "view"),
				"get", null, "sidePanel"));
	}

	public JSONObject getObjectFieldJSONObject(ObjectField objectField) {
		return ObjectFieldUtil.toJSONObject(
			_listTypeDefinitionService, objectField,
			_objectFieldSettingLocalService);
	}

	@Override
	protected String getAPIURI() {
		return "/object-fields?filter=state%20eq%20true";
	}

	private final ListTypeDefinitionService _listTypeDefinitionService;
	private final ObjectFieldSettingLocalService
		_objectFieldSettingLocalService;

}