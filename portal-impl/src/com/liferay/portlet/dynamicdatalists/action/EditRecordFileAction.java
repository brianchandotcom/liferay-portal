/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists.action;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.dynamicdatalists.util.DDLUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;

import javax.portlet.PortletConfig;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Bruno Basto
 */
public class EditRecordFileAction extends PortletAction {

	@Override
	public void serveResource(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			FileEntry fileEntry = updateRecordFile(resourceRequest);

			jsonObject = DDLUtil.getRecordFileEntryJSONObject(fileEntry);
		}
		catch (Exception e) {
			if (e instanceof FileSizeException) {
				jsonObject.put("exception", e.toString());
			}
			else {
				throw e;
			}
		}

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	protected FileEntry updateRecordFile(ResourceRequest resourceRequest)
		throws Exception {

		String fieldName = ParamUtil.getString(resourceRequest, "fieldName");

		long recordId = ParamUtil.getLong(resourceRequest, "recordId");

		long structureId = ParamUtil.getLong(resourceRequest, "structureId");

		DDMStructure structure = DDMStructureLocalServiceUtil.getDDMStructure(
			structureId);

		return DDLUtil.updateRecordFile(
			resourceRequest, recordId, fieldName, structure);
	}

}