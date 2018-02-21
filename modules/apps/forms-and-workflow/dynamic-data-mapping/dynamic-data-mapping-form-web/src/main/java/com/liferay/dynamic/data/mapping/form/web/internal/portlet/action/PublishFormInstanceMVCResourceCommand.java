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
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletResponse;

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
	service = MVCResourceCommand.class
)
public class PublishFormInstanceMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		try {
			long formInstanceId = ParamUtil.getLong(
				resourceRequest, "formInstanceId");

			if (formInstanceId == 0) {
				return;
			}

			boolean published = ParamUtil.getBoolean(
				resourceRequest, "published");

			updateFormInstancePermission(
				resourceRequest, formInstanceId, published);

			DDMFormInstance formInstance = _formInstanceService.getFormInstance(
				formInstanceId);

			DDMFormValues settingsDDMFormValues =
				formInstance.getSettingsDDMFormValues();

			updatePublishedDDMFormFieldValue(settingsDDMFormValues, published);

			_formInstanceService.updateFormInstance(
				formInstanceId, settingsDDMFormValues);
		}
		catch (Throwable t) {
			resourceResponse.setProperty(
				ResourceResponse.HTTP_STATUS_CODE,
				String.valueOf(HttpServletResponse.SC_BAD_REQUEST));
		}
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
			ResourceRequest resourceRequest, long formInstanceId,
			boolean published)
		throws PortalException {

		long companyId = _portal.getCompanyId(resourceRequest);

		Role role = _roleLocalService.getRole(companyId, RoleConstants.GUEST);

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

	private DDMFormValuesQueryFactory _ddmFormValuesQueryFactory;
	private DDMFormInstanceService _formInstanceService;

	@Reference
	private Portal _portal;

	private ResourcePermissionLocalService _resourcePermissionLocalService;
	private RoleLocalService _roleLocalService;

}