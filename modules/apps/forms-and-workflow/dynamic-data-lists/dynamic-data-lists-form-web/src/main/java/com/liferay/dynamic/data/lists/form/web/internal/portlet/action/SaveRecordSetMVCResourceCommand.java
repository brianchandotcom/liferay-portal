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

package com.liferay.dynamic.data.lists.form.web.internal.portlet.action;

import com.liferay.dynamic.data.lists.form.web.constants.DDLFormPortletKeys;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

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
		"javax.portlet.name=" + DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
		"mvc.command.name=saveRecordSet"
	},
	service = MVCResourceCommand.class
)
public class SaveRecordSetMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException {

		Map<String, Object> response = new HashMap<>();

		try {
			DDLRecordSet recordSet = saveRecordSetInTransaction(
				resourceRequest, resourceResponse);

			response.put("ddmStructureId", recordSet.getDDMStructureId());
			response.put("modifiedDate", recordSet.getModifiedDate());
			response.put("recordSetId", recordSet.getRecordSetId());
		}
		catch (Throwable t) {
			resourceResponse.setProperty(
				ResourceResponse.HTTP_STATUS_CODE,
				String.valueOf(HttpServletResponse.SC_BAD_REQUEST));

			response.put("error", t.getMessage());
		}

		JSONSerializer jsonSerializer = jsonFactory.createJSONSerializer();

		PortletResponseUtil.write(
			resourceResponse, jsonSerializer.serializeDeep(response));
	}

	protected DDLRecordSet saveRecordSetInTransaction(
			final ResourceRequest resourceRequest,
			final ResourceResponse resourceResponse)
		throws Throwable {

		Callable<DDLRecordSet> callable = new Callable<DDLRecordSet>() {

			@Override
			public DDLRecordSet call() throws Exception {
				return saveRecordSetMVCCommandHelper.saveRecordSet(
					resourceRequest, resourceResponse);
			}

		};

		return TransactionInvokerUtil.invoke(_transactionConfig, callable);
	}

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected SaveRecordSetMVCCommandHelper saveRecordSetMVCCommandHelper;

	private static final TransactionConfig _transactionConfig;

	static {
		TransactionConfig.Builder builder = new TransactionConfig.Builder();

		builder.setPropagation(Propagation.REQUIRES_NEW);
		builder.setRollbackForClasses(Exception.class);

		_transactionConfig = builder.build();
	}

}