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

package com.liferay.dynamic.data.lists.form.web.internal.portlet.action;

import com.liferay.dynamic.data.lists.form.web.constants.DDLFormPortletKeys;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.RoleService;

import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
		"mvc.command.name=getRoles"
	},
	service = MVCResourceCommand.class
)
public class GetRolesMVCResourceCommand extends BaseMVCResourceCommand {

	protected JSONArray createJSONArray(List<Role> roles) {
		JSONArray rolesJSONArray = _jsonFactory.createJSONArray();

		for (Role role : roles) {
			rolesJSONArray.put(createJSONObject(role));
		}

		return rolesJSONArray;
	}

	protected JSONObject createJSONObject(Role role) {
		JSONObject roleJSONObject = _jsonFactory.createJSONObject();

		roleJSONObject.put("id", role.getRoleId());
		roleJSONObject.put("name", role.getName());

		return roleJSONObject;
	}

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		List<Role> roles = _roleService.getRoles(
			CompanyThreadLocal.getCompanyId(), null);

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, createJSONArray(roles));
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private RoleService _roleService;

}