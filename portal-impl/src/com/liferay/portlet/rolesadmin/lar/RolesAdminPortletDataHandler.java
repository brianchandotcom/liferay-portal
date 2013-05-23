/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.rolesadmin.lar;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.DataLevel;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.Team;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.RoleExportActionableDynamicQuery;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;

import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Michael C. Han
 * @author David Mendez Gonzalez
 */
public class RolesAdminPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "roles_admin";

	public RolesAdminPortletDataHandler() {
		super();

		setDataLevel(DataLevel.PORTAL);
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "system-roles", true, false));
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				RolesAdminPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		List<Role> roles = RoleLocalServiceUtil.getRoles(
			portletDataContext.getCompanyId());

		for (Role role : roles) {
			if (!PortalUtil.isSystemRole(role.getName())) {
				RoleLocalServiceUtil.deleteRole(role);
			}
		}

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			PortletKeys.PORTAL, portletDataContext.getCompanyId());

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		ActionableDynamicQuery actionableDynamicQuery =
			new RoleExportActionableDynamicQuery(portletDataContext) {

				@Override
				protected void addCriteria(DynamicQuery dynamicQuery) {
					super.addCriteria(dynamicQuery);

					long classNameId = PortalUtil.getClassNameId(Team.class);

					Property classNameIdProperty = PropertyFactoryUtil.forName(
						"classNameId");

					dynamicQuery.add(classNameIdProperty.ne(classNameId));
				}

				@Override
				protected void performAction(Object object)
					throws PortalException, SystemException {

					Role role = (Role)object;

					if (!portletDataContext.getBooleanParameter(
							NAMESPACE, "system-roles")) {

						return;
					}

					long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
						portletDataContext.getCompanyId());

					if (role.getUserId() == defaultUserId) {
						return;
					}

					super.performAction(object);
				}

			};

		actionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			PortletKeys.PORTAL, portletDataContext.getSourceCompanyId(),
			portletDataContext.getCompanyId());

		Element rolesElement = portletDataContext.getImportDataGroupElement(
			Role.class);

		List<Element> roleElements = rolesElement.elements();

		for (Element roleElement : roleElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, roleElement);
		}

		return null;
	}

}