/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		if (signedIn ||
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