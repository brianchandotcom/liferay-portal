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

package com.liferay.object.web.internal.object.entries.upload.util;

import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.object.configuration.ObjectConfiguration;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(
	configurationPid = "com.liferay.object.configuration.ObjectConfiguration",
	service = AttachmentValidator.class
)
public class AttachmentValidator {

	public String[] getAcceptedFileExtensions(long objectFieldId) {
		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				objectFieldId, "acceptedFileExtensions");

		String value = objectFieldSetting.getValue();

		return value.split("\\s*,\\s*");
	}

	public long getMaximumFileSize(long objectFieldId, boolean signedIn) {
		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				objectFieldId, "maximumFileSize");

		long maximumFileSize = GetterUtil.getLong(
			objectFieldSetting.getValue());

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-148112")) ||
			signedIn ||
			(maximumFileSize <
				_objectConfiguration.maximumFileSizeForGuestUsers())) {

			return maximumFileSize * _FILE_LENGTH_MB;
		}

		return _objectConfiguration.maximumFileSizeForGuestUsers() *
			_FILE_LENGTH_MB;
	}

	public void validateFileExtension(String fileName, long objectFieldId)
		throws FileExtensionException {

		if (!ArrayUtil.contains(
				getAcceptedFileExtensions(objectFieldId),
				FileUtil.getExtension(fileName), true)) {

			throw new FileExtensionException(
				"Invalid file extension for " + fileName);
		}
	}

	public void validateFileSize(
			String fileName, long fileSize, long objectFieldId,
			boolean signedIn)
		throws FileSizeException {

		long maximumFileSize = getMaximumFileSize(objectFieldId, signedIn);

		if ((maximumFileSize > 0) && (fileSize > maximumFileSize)) {
			throw new FileSizeException(
				StringBundler.concat(
					"File ", fileName,
					" exceeds the maximum permitted size of ",
					maximumFileSize / _FILE_LENGTH_MB, " MB"));
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_objectConfiguration = ConfigurableUtil.createConfigurable(
			ObjectConfiguration.class, properties);
	}

	private static final long _FILE_LENGTH_MB = 1024 * 1024;

	private volatile ObjectConfiguration _objectConfiguration;

	@Reference
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

}