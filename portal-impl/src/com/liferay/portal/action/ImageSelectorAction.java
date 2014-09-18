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

package com.liferay.portal.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.upload.LiferayFileItemException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileUtil;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.ResourcePermissionChecker;
import com.liferay.portal.struts.JSONAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Sergio González
 */
public class ImageSelectorAction extends JSONAction {

	@Override
	public String getJSON(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		UploadServletRequest uploadPortletRequest =
			PortalUtil.getUploadServletRequest(request);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String resourceName = ParamUtil.getString(
			uploadPortletRequest, "resourceName");

		ResourcePermissionChecker resourcePermissionChecker =
			_serviceTrackerMap.getService(resourceName);

		if (resourcePermissionChecker == null) {
			throw new PrincipalException(
				"Access denied. There is no resource permission checker " +
					"registered for the resource " + resourceName);
		}

		resourcePermissionChecker.check(
			themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroupId(),
			ActionKeys.ADD_ENTRY);

		UploadException uploadException = (UploadException)request.getAttribute(
			WebKeys.UPLOAD_EXCEPTION);

		if (uploadException != null) {
			if (uploadException.isExceededLiferayFileItemSizeLimit()) {
				throw new LiferayFileItemException();
			}
			else if (uploadException.isExceededSizeLimit()) {
				throw new FileSizeException(uploadException.getCause());
			}

			throw new PortalException(uploadException.getCause());
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			JSONObject imageJSONObject = JSONFactoryUtil.createJSONObject();

			String fileName = uploadPortletRequest.getFileName(
				"imageSelectorFileName");

			Class<?> clazz = getClass();

			String tempFolderName = clazz.getName();

			InputStream inputStream = uploadPortletRequest.getFileAsStream(
				"imageSelectorFileName");

			FileEntry fileEntry = TempFileUtil.addTempFile(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				StringUtil.randomString() + fileName, tempFolderName,
				inputStream, MimeTypesUtil.getContentType(fileName));

			imageJSONObject.put("fileEntryId", fileEntry.getFileEntryId());

			imageJSONObject.put(
				"url",
				PortletFileRepositoryUtil.getPortletFileEntryURL(
					themeDisplay, fileEntry, StringPool.BLANK));

			jsonObject.put("image", imageJSONObject);

			jsonObject.put("success", Boolean.TRUE);
		}
		catch (Exception e) {
			jsonObject.put("success", Boolean.FALSE);
		}

		return jsonObject.toString();
	}

	private ServiceTrackerMap<String, ResourcePermissionChecker>
		_serviceTrackerMap = ServiceTrackerCollections.singleValueMap(
			ResourcePermissionChecker.class, "resource.name");

}