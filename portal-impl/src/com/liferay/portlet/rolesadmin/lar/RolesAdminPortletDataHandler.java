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

package com.liferay.portlet.rolesadmin.lar;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.exportimport.lar.BasePortletDataHandler;
import com.liferay.portlet.exportimport.lar.DataLevel;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerBoolean;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerControl;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelType;
import com.liferay.portlet.exportimport.xstream.XStreamAliasRegistryUtil;

import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Michael C. Han
 * @author David Mendez Gonzalez
 */
public class RolesAdminPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "roles_admin";

	public RolesAdminPortletDataHandler() {
		setDataLevel(DataLevel.PORTAL);
		setDeletionSystemEventStagedModelTypes(new StagedModelType(Role.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "roles", true, true,
				new PortletDataHandlerControl[] {
					new PortletDataHandlerBoolean(
						NAMESPACE, "system-roles", true, false)
				},
				Role.class.getName(), StagedModelType.REFERRER_CLASS_NAME_ALL
			));
		setSupportsDataStrategyCopyAsNew(false);

		XStreamAliasRegistryUtil.register(RoleImpl.class, "Role");
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
			if (!role.isSystem() && !role.isTeam()) {
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

		portletDataContext.addPortalPermissions();

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		ActionableDynamicQuery actionableDynamicQuery =
			getRoleActionableDynamicQuery(portletDataContext, true);

		actionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPortalPermissions();

		Element rolesElement = portletDataContext.getImportDataGroupElement(
			Role.class);

		List<Element> roleElements = rolesElement.elements();

		for (Element roleElement : roleElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, roleElement);
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		ActionableDynamicQuery actionableDynamicQuery =
			getRoleActionableDynamicQuery(portletDataContext, false);

		actionableDynamicQuery.performCount();
	}

	protected ActionableDynamicQuery getRoleActionableDynamicQuery(
		final PortletDataContext portletDataContext, final boolean export) {

		ActionableDynamicQuery actionableDynamicQuery =
			RoleLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(
						dynamicQuery, "modifiedDate");

					long classNameId = PortalUtil.getClassNameId(Team.class);

					Property classNameIdProperty = PropertyFactoryUtil.forName(
						"classNameId");

					dynamicQuery.add(classNameIdProperty.ne(classNameId));
				}

			});

		final ActionableDynamicQuery.PerformActionMethod performActionMethod =
			actionableDynamicQuery.getPerformActionMethod();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					if (!export) {
						return;
					}

					Role role = (Role)object;

					long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
						portletDataContext.getCompanyId());

					if (!portletDataContext.getBooleanParameter(
							NAMESPACE, "system-roles") &&
						(role.getUserId() == defaultUserId)) {

						return;
					}

					performActionMethod.performAction(object);
				}

			});

		return actionableDynamicQuery;
	}

}