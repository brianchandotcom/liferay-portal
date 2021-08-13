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
import com.liferay.dynamic.data.mapping.form.web.internal.portlet.action.util.BaseDDMFormMVCResourceCommand;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;

import java.io.IOException;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN,
		"mvc.command.name=/dynamic_data_mapping_form/save_form_instance"
	},
	service = MVCResourceCommand.class
)
public class SaveFormInstanceMVCResourceCommand
	extends BaseDDMFormMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException {

		try {
			DDMFormInstance formInstance = saveFormInstanceInTransaction(
				resourceRequest, resourceResponse);

			writeResponse(resourceRequest, resourceResponse, formInstance);
		}
		catch (Throwable throwable) {
			resourceResponse.setProperty(
				ResourceResponse.HTTP_STATUS_CODE,
				String.valueOf(HttpServletResponse.SC_BAD_REQUEST));
		}
	}

	protected DDMFormInstance saveFormInstanceInTransaction(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Throwable {

		return TransactionInvokerUtil.invoke(
			_transactionConfig,
			() -> saveFormInstanceMVCCommandHelper.saveFormInstance(
				resourceRequest, resourceResponse));
	}

	@Reference
	protected SaveFormInstanceMVCCommandHelper saveFormInstanceMVCCommandHelper;

	private static final TransactionConfig _transactionConfig;

	static {
		TransactionConfig.Builder builder = new TransactionConfig.Builder();

		builder.setPropagation(Propagation.REQUIRES_NEW);
		builder.setRollbackForClasses(Exception.class);

		_transactionConfig = builder.build();
	}

}