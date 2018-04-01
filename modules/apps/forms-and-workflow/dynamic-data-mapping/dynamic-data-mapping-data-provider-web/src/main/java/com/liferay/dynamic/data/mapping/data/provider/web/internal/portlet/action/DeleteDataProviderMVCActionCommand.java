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

package com.liferay.dynamic.data.mapping.data.provider.web.internal.portlet.action;

import com.liferay.dynamic.data.mapping.data.provider.web.internal.constants.DDMDataProviderPortletKeys;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
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
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDMDataProviderPortletKeys.DYNAMIC_DATA_MAPPING_DATA_PROVIDER,
		"mvc.command.name=deleteDataProvider"
	},
	service = MVCActionCommand.class
)
public class DeleteDataProviderMVCActionCommand
	extends BaseTransactionalMVCActionCommand {

	protected void doDeleteDataProviderInstance(long dataProviderInstanceId)
		throws PortalException {

		_ddmDataProviderInstanceService.deleteDataProviderInstance(
			dataProviderInstanceId);
	}

	@Override
	protected void doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] deleteDataProviderInstanceIds = null;

		long dataProviderInstanceId = ParamUtil.getLong(
			actionRequest, "dataProviderInstanceId");

		if (dataProviderInstanceId > 0) {
			deleteDataProviderInstanceIds = new long[] {dataProviderInstanceId};
		}
		else {
			deleteDataProviderInstanceIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteDataProviderInstanceIds"),
				0L);
		}

		for (long deleteDataProviderInstanceId :
				deleteDataProviderInstanceIds) {

			doDeleteDataProviderInstance(deleteDataProviderInstanceId);
		}
	}

	@Reference
	private DDMDataProviderInstanceService _ddmDataProviderInstanceService;

}