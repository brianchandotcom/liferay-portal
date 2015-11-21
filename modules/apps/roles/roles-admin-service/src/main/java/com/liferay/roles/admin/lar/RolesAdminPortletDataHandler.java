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

package com.liferay.roles.admin.lar;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.Team;
import com.liferay.portal.service.RoleLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.exportimport.lar.BasePortletDataHandler;
import com.liferay.portlet.exportimport.lar.DataLevel;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataHandler;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerBoolean;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerControl;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelType;
import com.liferay.roles.admin.constants.RolesAdminPortletKeys;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author David Mendez Gonzalez
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + RolesAdminPortletKeys.ROLES_ADMIN,
	service = PortletDataHandler.class
)
public class RolesAdminPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "roles_admin";

	@Activate
	protected void activate() {
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

		List<Role> roles = _roleLocalService.getRoles(
			portletDataContext.getCompanyId());

		for (Role role : roles) {
			if (!role.isSystem() && !role.isTeam()) {
				_roleLocalService.deleteRole(role);
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
			_roleLocalService.getExportActionableDynamicQuery(
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

		@SuppressWarnings("unchecked")
		final ActionableDynamicQuery.PerformActionMethod<Role>
			performActionMethod =
				(ActionableDynamicQuery.PerformActionMethod<Role>)
					actionableDynamicQuery.getPerformActionMethod();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Role>() {

				@Override
				public void performAction(Role role) throws PortalException {
					if (!export) {
						return;
					}

					long defaultUserId = _userLocalService.getDefaultUserId(
						portletDataContext.getCompanyId());

					if (!portletDataContext.getBooleanParameter(
							NAMESPACE, "system-roles") &&
						(role.getUserId() == defaultUserId)) {

						return;
					}

					performActionMethod.performAction(role);
				}

			});

		return actionableDynamicQuery;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setRoleLocalService(RoleLocalService roleLocalService) {
		_roleLocalService = roleLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private volatile RoleLocalService _roleLocalService;
	private volatile UserLocalService _userLocalService;

}