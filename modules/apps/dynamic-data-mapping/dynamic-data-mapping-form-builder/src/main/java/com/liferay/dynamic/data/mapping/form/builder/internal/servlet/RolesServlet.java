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

package com.liferay.dynamic.data.mapping.form.builder.internal.servlet;

import com.liferay.dynamic.data.mapping.form.builder.internal.servlet.base.BaseDDMFormBuilderServlet;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.RoleService;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;

import java.io.IOException;

import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = {
		"dynamic.data.mapping.form.builder.servlet=true",
		"osgi.http.whiteboard.context.path=/dynamic-data-mapping-form-builder-roles",
		"osgi.http.whiteboard.servlet.name=com.liferay.dynamic.data.mapping.form.builder.internal.servlet.RolesServlet",
		"osgi.http.whiteboard.servlet.pattern=/dynamic-data-mapping-form-builder-roles/*"
	},
	service = Servlet.class
)
public class RolesServlet extends BaseDDMFormBuilderServlet {

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		JSONArray jsonArray = getRolesJSONArray();

		response.setContentType(ContentTypes.APPLICATION_JSON);
		response.setStatus(HttpServletResponse.SC_OK);

		ServletResponseUtil.write(response, jsonArray.toJSONString());
	}

	protected JSONArray getRolesJSONArray() {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		try {
			List<Role> roles = _roleService.getRoles(
				CompanyThreadLocal.getCompanyId(), null);

			for (Role role : roles) {
				jsonArray.put(toJSONObject(role));
			}

			return jsonArray;
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return jsonArray;
	}

	protected JSONObject toJSONObject(Role role) {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put("id", role.getRoleId());
		jsonObject.put("name", role.getName());

		return jsonObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(RolesServlet.class);

	private static final long serialVersionUID = 1L;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private RoleService _roleService;

}