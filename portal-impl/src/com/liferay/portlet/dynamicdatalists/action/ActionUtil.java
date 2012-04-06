/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetLocalServiceUtil;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Basto
 * @author Eduardo Lundgren
 */
public class ActionUtil {

	public static void getRecord(HttpServletRequest request) throws Exception {
		long recordId = ParamUtil.getLong(request, "recordId");

		DDLRecord record = null;

		if (recordId > 0) {
			record = DDLRecordLocalServiceUtil.getRecord(recordId);
		}

		request.setAttribute(WebKeys.DYNAMIC_DATA_LISTS_RECORD, record);
	}

	public static void getRecord(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getRecord(request);
	}

	public static void getRecordSet(HttpServletRequest request)
		throws Exception {

		long recordSetId = ParamUtil.getLong(request, "recordSetId");

		DDLRecordSet recordSet = null;

		if (Validator.isNotNull(recordSetId)) {
			recordSet = DDLRecordSetLocalServiceUtil.getRecordSet(recordSetId);
		}

		request.setAttribute(WebKeys.DYNAMIC_DATA_LISTS_RECORD_SET, recordSet);
	}

	public static void getRecordSet(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getRecordSet(request);
	}

}