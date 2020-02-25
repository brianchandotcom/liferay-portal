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

package com.liferay.document.library.web.internal.upload;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.util.DLValidator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionHelper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UniqueFileNameProvider;
import com.liferay.upload.UploadFileEntryHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 * @author Sergio González
 * @author Alejandro Tardín
 */
@Component(service = DLUploadFileEntryHandler.class)
public class DLUploadFileEntryHandler implements UploadFileEntryHandler {

	@Override
	public FileEntry upload(UploadPortletRequest uploadPortletRequest)
		throws IOException, PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)uploadPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(uploadPortletRequest, "folderId");

		ModelResourcePermissionHelper.check(
			_folderModelResourcePermission, themeDisplay.getPermissionChecker(),
			themeDisplay.getScopeGroupId(), folderId, ActionKeys.ADD_DOCUMENT);

		String fileName = uploadPortletRequest.getFileName(_PARAMETER_NAME);
		long size = uploadPortletRequest.getSize(_PARAMETER_NAME);

		_dlValidator.validateFileSize(fileName, size);

		String contentType = uploadPortletRequest.getContentType(
			_PARAMETER_NAME);
		String description = uploadPortletRequest.getParameter("description");

		try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
				_PARAMETER_NAME)) {

			String uniqueFileName = _uniqueFileNameProvider.provide(
				fileName,
				curFileName -> _exists(
					themeDisplay.getScopeGroupId(), folderId, curFileName));

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				DLFileEntry.class.getName(), uploadPortletRequest);

			Map<String, Serializable> customFieldsMap = _getCustomFieldsMap(
				uploadPortletRequest);

			if (!MapUtil.isEmpty(customFieldsMap)) {
				serviceContext.setExpandoBridgeAttributes(customFieldsMap);
			}

			return _dlAppService.addFileEntry(
				themeDisplay.getScopeGroupId(), folderId, uniqueFileName,
				contentType, uniqueFileName, description, StringPool.BLANK,
				inputStream, size, serviceContext);
		}
	}

	private boolean _exists(long groupId, long folderId, String fileName) {
		try {
			if (_dlAppService.getFileEntry(groupId, folderId, fileName) !=
					null) {

				return true;
			}

			return false;
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}

			return false;
		}
	}

	private Map<String, Serializable> _getCustomFieldsMap(
		UploadPortletRequest uploadPortletRequest) {

		String customFieldsKeys = uploadPortletRequest.getParameter(
			"customFieldsKeys");
		String customFieldsTypes = uploadPortletRequest.getParameter(
			"customFieldsTypes");
		String customFieldsValues = uploadPortletRequest.getParameter(
			"customFieldsValues");

		String[] customFieldsKeysArray = StringUtil.split(customFieldsKeys);
		List<String> customFieldsValuesList =
			(List)JSONFactoryUtil.looseDeserialize(customFieldsValues);

		Stream<String> customFieldsTypesStream = Arrays.stream(
			StringUtil.split(customFieldsTypes));

		List<Class<?>> customFieldsTypesList = customFieldsTypesStream.map(
			x -> {
				try {
					return Class.forName(x);
				}
				catch (Exception exception) {
					_log.error(exception, exception);
				}

				return null;
			}
		).collect(
			Collectors.toList()
		);

		return _mapCustomFieldsKeysToValues(
			customFieldsValuesList.size(), customFieldsKeysArray,
			customFieldsValuesList, customFieldsTypesList);
	}

	private Map<String, Serializable> _mapCustomFieldsKeysToValues(
		int length, String[] keys, List<String> values, List<Class<?>> types) {

		if ((keys.length != length) || (values.size() != length) ||
			(types.size() != length)) {

			return null;
		}

		Map<String, Serializable> customFieldsMap = new HashMap<>();

		for (int i = 0; i < length; i++) {
			Serializable value = (Serializable)JSONFactoryUtil.looseDeserialize(
				values.get(i), types.get(i));

			customFieldsMap.put(keys[i], value);
		}

		return customFieldsMap;
	}

	private static final String _PARAMETER_NAME = "imageSelectorFileName";

	private static final Log _log = LogFactoryUtil.getLog(
		DLUploadFileEntryHandler.class);

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLValidator _dlValidator;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.Folder)"
	)
	private ModelResourcePermission<Folder> _folderModelResourcePermission;

	@Reference
	private UniqueFileNameProvider _uniqueFileNameProvider;

}