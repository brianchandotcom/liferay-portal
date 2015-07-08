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

package com.liferay.user.groups.admin.web.lar;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.impl.UserGroupImpl;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portlet.exportimport.lar.BasePortletDataHandler;
import com.liferay.portlet.exportimport.lar.DataLevel;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataHandler;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerBoolean;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.xstream.XStreamAliasRegistryUtil;
import com.liferay.user.groups.admin.web.constants.UserGroupsAdminPortletKeys;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author David Mendez Gonzalez
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UserGroupsAdminPortletKeys.USER_GROUPS_ADMIN
	},
	service = PortletDataHandler.class
)
public class UserGroupsAdminPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "user_groups_admin";

	public UserGroupsAdminPortletDataHandler() {
		setDataLevel(DataLevel.PORTAL);
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "user-groups", true, true, null,
				UserGroup.class.getName()));
		setSupportsDataStrategyCopyAsNew(false);

		XStreamAliasRegistryUtil.register(UserGroupImpl.class, "UserGroup");
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				UserGroupsAdminPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		UserGroupLocalServiceUtil.deleteUserGroups(
			portletDataContext.getCompanyId());

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
			UserGroupLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		actionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPortalPermissions();

		Element userGroupsElement =
			portletDataContext.getImportDataGroupElement(UserGroup.class);

		List<Element> userGroupElements = userGroupsElement.elements();

		for (Element userGroupElement : userGroupElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, userGroupElement);
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		ActionableDynamicQuery actionableDynamicQuery =
			UserGroupLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		actionableDynamicQuery.performCount();
	}

	@Override
	protected String getDisplayStyle(
		PortletDataContext portletDataContext, String portletId,
		PortletPreferences portletPreferences) {

		try {
			TemplateHandler templateHandler =
				TemplateHandlerRegistryUtil.getTemplateHandler(
					UserGroup.class.getName());

			if (Validator.isNotNull(templateHandler)) {
				return portletPreferences.getValue("displayStyle", null);
			}
		}
		catch (Exception e) {
		}

		return null;
	}

	@Override
	protected long getDisplayStyleGroupId(
		PortletDataContext portletDataContext, String portletId,
		PortletPreferences portletPreferences) {

		try {
			TemplateHandler templateHandler =
				TemplateHandlerRegistryUtil.getTemplateHandler(
					UserGroup.class.getName());

			if (Validator.isNotNull(templateHandler)) {
				return GetterUtil.getLong(
					portletPreferences.getValue("displayStyleGroupId", null));
			}
		}
		catch (Exception e) {
		}

		return 0;
	}

}