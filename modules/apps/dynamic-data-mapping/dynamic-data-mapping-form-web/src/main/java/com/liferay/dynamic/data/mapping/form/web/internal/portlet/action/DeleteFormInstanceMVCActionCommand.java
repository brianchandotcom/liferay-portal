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

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

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
		"mvc.command.name=deleteFormInstance"
	},
	service = MVCActionCommand.class
)
public class DeleteFormInstanceMVCActionCommand
	extends BaseTransactionalMVCActionCommand {

	protected void doDeleteFormInstance(long formInstanceId)
		throws PortalException {

		_formInstanceService.deleteFormInstance(formInstanceId);
	}

	@Override
	protected void doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] deleteFormInstanceIds = null;

		long formInstanceId = ParamUtil.getLong(
			actionRequest, "formInstanceId");

		if (formInstanceId > 0) {
			deleteFormInstanceIds = new long[] {formInstanceId};
		}
		else {
			deleteFormInstanceIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteFormInstanceIds"),
				0L);
		}

		for (long deleteFormInstanceId : deleteFormInstanceIds) {
			doDeleteFormInstance(deleteFormInstanceId);
		}
	}

	@Reference(unbind = "-")
	protected void setDDMFormInstanceService(
		DDMFormInstanceService formInstanceService) {

		_formInstanceService = formInstanceService;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureService(
		DDMStructureService ddmStructureService) {

		_ddmStructureService = ddmStructureService;
	}

	private DDMStructureService _ddmStructureService;
	private DDMFormInstanceService _formInstanceService;

}