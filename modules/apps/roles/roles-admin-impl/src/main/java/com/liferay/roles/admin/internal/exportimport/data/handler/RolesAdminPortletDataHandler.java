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

package com.liferay.roles.admin.internal.exportimport.data.handler;

import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.roles.admin.constants.RolesAdminPortletKeys;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	public static final String SCHEMA_VERSION = "1.0.0";

	@Override
	public String getSchemaVersion() {
		return SCHEMA_VERSION;
	}

	@Override
	public boolean isSupportsDataStrategyCopyAsNew() {
		return false;
	}

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
				Role.class.getName(), StagedModelType.REFERRER_CLASS_NAME_ALL));
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
			dynamicQuery -> {
				portletDataContext.addDateRangeCriteria(
					dynamicQuery, "modifiedDate");

				long classNameId = _portal.getClassNameId(Team.class);

				Property classNameIdProperty = PropertyFactoryUtil.forName(
					"classNameId");

				dynamicQuery.add(classNameIdProperty.ne(classNameId));

				if (!portletDataContext.getBooleanParameter(
						NAMESPACE, "system-roles")) {

					Conjunction conjunction =
						RestrictionsFactoryUtil.conjunction();

					Property nameProperty = PropertyFactoryUtil.forName("name");

					for (String roleName : _allSystemRoleNames) {
						conjunction.add(nameProperty.ne(roleName));
					}

					dynamicQuery.add(conjunction);
				}
			});

		@SuppressWarnings("unchecked")
		final ActionableDynamicQuery.PerformActionMethod<Role>
			performActionMethod =
				(ActionableDynamicQuery.PerformActionMethod<Role>)
					actionableDynamicQuery.getPerformActionMethod();

		ActionableDynamicQuery.PerformActionMethod<Role>
			performActionMethodWrapper =
				new RoleExportActionableDynamicQueryPerformActionMethod(
					performActionMethod, portletDataContext, export);

		actionableDynamicQuery.setPerformActionMethod(
			performActionMethodWrapper);

		return actionableDynamicQuery;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		Collections.addAll(
			_allSystemRoleNames, portal.getSystemOrganizationRoles());
		Collections.addAll(_allSystemRoleNames, portal.getSystemRoles());
		Collections.addAll(_allSystemRoleNames, portal.getSystemSiteRoles());
	}

	private final Set<String> _allSystemRoleNames = new HashSet<>();

	@Reference
	private Portal _portal;

	@Reference
	private RoleLocalService _roleLocalService;

	private class RoleExportActionableDynamicQueryPerformActionMethod
		implements ActionableDynamicQuery.PerformActionMethod<Role> {

		public RoleExportActionableDynamicQueryPerformActionMethod(
			ActionableDynamicQuery.PerformActionMethod<Role>
				performActionMethod,
			PortletDataContext portletDataContext, boolean export) {

			_performActionMethod = performActionMethod;
			_portletDataContext = portletDataContext;
			_export = export;
		}

		@Override
		public void performAction(Role role) throws PortalException {
			if (!_export) {
				return;
			}

			if (!_portletDataContext.getBooleanParameter(
					NAMESPACE, "system-roles") &&
				_allSystemRoleNames.contains(role.getName())) {

				return;
			}

			_performActionMethod.performAction(role);
		}

		private final boolean _export;
		private final ActionableDynamicQuery.PerformActionMethod<Role>
			_performActionMethod;
		private final PortletDataContext _portletDataContext;

	}

}