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
	property = {
		"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN,
		"mvc.command.name=/dynamic_data_mapping_form/publish_form_instance"
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

		_updateFormInstancePermission(
			actionRequest, ddmFormInstance.getFormInstanceId(), published);

		DDMFormValues settingsDDMFormValues =
			ddmFormInstance.getSettingsDDMFormValues();

		_updatePublishedDDMFormFieldValue(settingsDDMFormValues, published);

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

		ddmFormInstance = _ddmFormInstanceService.updateFormInstance(
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

	private boolean _isFormInstancePublished(DDMFormInstance formInstance)
		throws Exception {

		DDMFormInstanceSettings ddmFormInstanceSettings =
			formInstance.getSettingsModel();

		return ddmFormInstanceSettings.published();
	}

	private void _updateFormInstancePermission(
			ActionRequest actionRequest, long formInstanceId, boolean published)
		throws Exception {

		Role role = _roleLocalService.getRole(
			_portal.getCompanyId(actionRequest), RoleConstants.GUEST);

		ResourcePermission resourcePermission =
			_resourcePermissionLocalService.fetchResourcePermission(
				role.getCompanyId(), DDMFormInstance.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(formInstanceId), role.getRoleId());

		if (resourcePermission == null) {
			_resourcePermissionLocalService.setResourcePermissions(
				role.getCompanyId(), DDMFormInstance.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(formInstanceId), role.getRoleId(),
				new String[] {DDMActionKeys.ADD_FORM_INSTANCE_RECORD});

			resourcePermission =
				_resourcePermissionLocalService.fetchResourcePermission(
					role.getCompanyId(), DDMFormInstance.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(formInstanceId), role.getRoleId());
		}

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

	private void _updatePublishedDDMFormFieldValue(
			DDMFormValues ddmFormValues, boolean published)
		throws Exception {

		DDMFormValuesQuery ddmFormValuesQuery =
			_ddmFormValuesQueryFactory.create(ddmFormValues, "/published");

		DDMFormFieldValue ddmFormFieldValue =
			ddmFormValuesQuery.selectSingleDDMFormFieldValue();

		Value value = ddmFormFieldValue.getValue();

		value.addString(
			ddmFormValues.getDefaultLocale(), Boolean.toString(published));
	}

	@Reference
	private DDMFormInstanceService _ddmFormInstanceService;

	@Reference
	private DDMFormValuesQueryFactory _ddmFormValuesQueryFactory;

	@Reference
	private Portal _portal;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}