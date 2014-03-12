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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;

import java.io.File;
import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alexander Chow
 */
public class DLAppUtil {

	public static String getExtension(String title, String sourceFileName) {
		String extension = FileUtil.getExtension(sourceFileName);

		if (Validator.isNull(extension)) {
			extension = FileUtil.getExtension(title);
		}

		return extension;
	}

	public static String getMimeType(
		String sourceFileName, String mimeType, String title, File file) {

		if (Validator.isNull(mimeType) ||
			mimeType.equals(ContentTypes.APPLICATION_OCTET_STREAM)) {

			String extension = getExtension(title, sourceFileName);

			mimeType = MimeTypesUtil.getContentType(file, "A." + extension);
		}

		return mimeType;
	}

	public static boolean isMajorVersion(
		FileVersion previousFileVersion, FileVersion currentFileVersion) {

		long currentVersion = GetterUtil.getLong(
			currentFileVersion.getVersion());
		long previousVersion = GetterUtil.getLong(
			previousFileVersion.getVersion());

		return (currentVersion - previousVersion) >= 1;
	}

	public static void startWorkflowInstance(
			long userId, DLFileVersion dlFileVersion, String syncEventType,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Map<String, Serializable> workflowContext =
			new HashMap<String, Serializable>();

		workflowContext.put("event", syncEventType);
		workflowContext.put(
			WorkflowConstants.CONTEXT_URL,
			getEntryURL(
				dlFileVersion.getGroupId(), dlFileVersion.getFileEntryId(),
				serviceContext));

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			dlFileVersion.getCompanyId(), dlFileVersion.getGroupId(), userId,
			DLFileEntry.class.getName(), dlFileVersion.getFileVersionId(),
			dlFileVersion, serviceContext, workflowContext);
	}

	protected static String getEntryURL(
			long groupId, long fileEntryId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		HttpServletRequest request = serviceContext.getRequest();

		if (request == null) {
			return StringPool.BLANK;
		}

		long plid = serviceContext.getPlid();

		long controlPanelPlid = PortalUtil.getControlPanelPlid(
			serviceContext.getCompanyId());

		if (plid == controlPanelPlid) {
			plid = PortalUtil.getPlidFromPortletId(
				groupId, PortletKeys.DOCUMENT_LIBRARY);
		}

		if (plid == LayoutConstants.DEFAULT_PLID) {
			plid = controlPanelPlid;
		}

		PortletURL portletURL = PortletURLFactoryUtil.create(
			request, PortletKeys.DOCUMENT_LIBRARY, plid,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"struts_action", "/document_library/view_file_entry");
		portletURL.setParameter("fileEntryId", String.valueOf(fileEntryId));

		return portletURL.toString();
	}

}