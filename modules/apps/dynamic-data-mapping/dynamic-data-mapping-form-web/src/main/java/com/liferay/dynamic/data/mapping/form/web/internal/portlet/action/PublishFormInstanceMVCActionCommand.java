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

package com.liferay.dynamic.data.mapping.form.web.internal.portlet.action;

import com.liferay.dynamic.data.mapping.constants.DDMActionKeys;
import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.form.values.query.DDMFormValuesQuery;
import com.liferay.dynamic.data.mapping.form.values.query.DDMFormValuesQueryFactory;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN,
		"mvc.command.name=publishFormInstance"
	},
	service = MVCActionCommand.class
)
public class PublishFormInstanceMVCActionCommand
	extends SaveFormInstanceMVCActionCommand {

	@Override
	protected void doService(
			ActionRequest actionRequest, ActionResponse actionResponse,
			LiferayPortletURL portletURL)
		throws Exception {

		DDMFormInstance ddmFormInstance =
			saveFormInstanceMVCCommandHelper.saveFormInstance(
				actionRequest, actionResponse, true);

		boolean published = !_isFormInstancePublished(ddmFormInstance);

		updateFormInstancePermission(
			actionRequest, ddmFormInstance.getFormInstanceId(), published);

		DDMFormValues settingsDDMFormValues =
			ddmFormInstance.getSettingsDDMFormValues();

		updatePublishedDDMFormFieldValue(settingsDDMFormValues, published);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMFormInstance.class.getName(), actionRequest);

		if (published) {
			serviceContext.setAttribute(
				"status", WorkflowConstants.STATUS_APPROVED);
		}
		else {
			DDMFormInstanceVersion latestFormInstanceVersion =
				ddmFormInstance.getFormInstanceVersion(
					ddmFormInstance.getVersion());

			serviceContext.setAttribute(
				"status", latestFormInstanceVersion.getStatus());
		}

		DDMStructure ddmStructure = ddmFormInstance.getStructure();

		DDMStructureVersion latestDDMStructureVersion =
			ddmStructure.getLatestStructureVersion();

		ddmFormInstance = _formInstanceService.updateFormInstance(
			ddmFormInstance.getFormInstanceId(), ddmFormInstance.getNameMap(),
			ddmFormInstance.getDescriptionMap(),
			latestDDMStructureVersion.getDDMForm(),
			latestDDMStructureVersion.getDDMFormLayout(), settingsDDMFormValues,
			serviceContext);

		portletURL.setParameter(
			"formInstanceId",
			String.valueOf(ddmFormInstance.getFormInstanceId()));

		portletURL.setParameter("showPublishAlert", Boolean.TRUE.toString());
	}

	@Reference(unbind = "-")
	protected void setDDMFormInstanceService(
		DDMFormInstanceService formInstanceService) {

		_formInstanceService = formInstanceService;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesQueryFactory(
		DDMFormValuesQueryFactory ddmFormValuesQueryFactory) {

		_ddmFormValuesQueryFactory = ddmFormValuesQueryFactory;
	}

	@Reference(unbind = "-")
	protected void setResourcePermissionLocalService(
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	@Reference(unbind = "-")
	protected void setRoleLocalService(RoleLocalService roleLocalService) {
		_roleLocalService = roleLocalService;
	}

	protected void updateFormInstancePermission(
			ActionRequest actionRequest, long formInstanceId, boolean published)
		throws PortalException {

		Role role = _roleLocalService.getRole(
			_portal.getCompanyId(actionRequest), RoleConstants.GUEST);

		ResourcePermission resourcePermission =
			_resourcePermissionLocalService.getResourcePermission(
				role.getCompanyId(), DDMFormInstance.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(formInstanceId), role.getRoleId());

		if (published) {
			resourcePermission.addResourceAction(
				DDMActionKeys.ADD_FORM_INSTANCE_RECORD);
		}
		else {
			resourcePermission.removeResourceAction(
				DDMActionKeys.ADD_FORM_INSTANCE_RECORD);
		}

		_resourcePermissionLocalService.updateResourcePermission(
			resourcePermission);
	}

	protected void updatePublishedDDMFormFieldValue(
			DDMFormValues ddmFormValues, boolean published)
		throws PortalException {

		DDMFormValuesQuery ddmFormValuesQuery =
			_ddmFormValuesQueryFactory.create(ddmFormValues, "/published");

		DDMFormFieldValue ddmFormFieldValue =
			ddmFormValuesQuery.selectSingleDDMFormFieldValue();

		Value value = ddmFormFieldValue.getValue();

		value.addString(
			ddmFormValues.getDefaultLocale(), Boolean.toString(published));
	}

	private boolean _isFormInstancePublished(DDMFormInstance formInstance)
		throws PortalException {

		DDMFormInstanceSettings ddmFormInstanceSettings =
			formInstance.getSettingsModel();

		return ddmFormInstanceSettings.published();
	}

	private DDMFormValuesQueryFactory _ddmFormValuesQueryFactory;
	private DDMFormInstanceService _formInstanceService;

	@Reference
	private Portal _portal;

	private ResourcePermissionLocalService _resourcePermissionLocalService;
	private RoleLocalService _roleLocalService;

}