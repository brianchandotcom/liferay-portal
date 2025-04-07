/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.configuration.persistence.listener;

import com.liferay.document.library.internal.configuration.DLSizeLimitConfiguration;
import com.liferay.document.library.internal.util.MimeTypeSizeLimitUtil;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.upload.configuration.UploadServletRequestConfigurationProvider;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Dictionary;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.document.library.internal.configuration.DLSizeLimitConfiguration",
	service = ConfigurationModelListener.class
)
public class DLSizeLimitConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		Object requestFileSizeBufferObject = properties.get(
			"requestFileSizeBuffer");

		if (requestFileSizeBufferObject != null) {
			long requestFileSizeBuffer = (long)properties.get(
				"requestFileSizeBuffer");

			ResourceBundle resourceBundle = _getResourceBundle();

			if (requestFileSizeBuffer < _MINIMUM_MAX_SIZE) {
				throw new ConfigurationModelListenerException(
					LanguageUtil.format(
						resourceBundle,
						"the-request-file-size-buffer-cannot-be-less-than-x",
						LanguageUtil.formatStorageSize(
							GetterUtil.getDouble(_MINIMUM_MAX_SIZE),
							resourceBundle.getLocale())),
					DLSizeLimitConfiguration.class, getClass(), properties);
			}

			long fileMaxSize = (long)properties.get("fileMaxSize");

			long uploadServletRequestMaxSize =
				_uploadServletRequestConfigurationProvider.getMaxSize();

			long fileMaxSizeLimit =
				uploadServletRequestMaxSize - requestFileSizeBuffer;

			if (fileMaxSizeLimit < 0) {
				throw new ConfigurationModelListenerException(
					LanguageUtil.format(
						resourceBundle,
						"the-request-file-size-buffer-cannot-be-more-than-x",
						LanguageUtil.formatStorageSize(
							GetterUtil.getDouble(uploadServletRequestMaxSize),
							resourceBundle.getLocale())),
					DLSizeLimitConfiguration.class, getClass(), properties);
			}

			if (fileMaxSize > fileMaxSizeLimit) {
				throw new ConfigurationModelListenerException(
					LanguageUtil.format(
						resourceBundle,
						"the-maximum-file-upload-size-cannot-be-more-than-x",
						LanguageUtil.formatStorageSize(
							GetterUtil.getDouble(fileMaxSizeLimit),
							resourceBundle.getLocale())),
					DLSizeLimitConfiguration.class, getClass(), properties);
			}
		}

		String[] mimeTypeSizeLimit = (String[])properties.get(
			"mimeTypeSizeLimit");

		if (ArrayUtil.isEmpty(mimeTypeSizeLimit)) {
			return;
		}

		for (String mimeTypeSizeString : mimeTypeSizeLimit) {
			MimeTypeSizeLimitUtil.parseMimeTypeSizeLimit(
				mimeTypeSizeString,
				(mimeType, sizeLimit) -> {
					if ((mimeType == null) || (sizeLimit == null)) {
						throw new ConfigurationModelListenerException(
							LanguageUtil.get(
								LocaleThreadLocal.getThemeDisplayLocale(),
								"mime-type-size-limit-error"),
							DLSizeLimitConfiguration.class, getClass(),
							properties);
					}
				});
		}
	}

	private ResourceBundle _getResourceBundle() {
		return ResourceBundleUtil.getBundle(
			LocaleThreadLocal.getThemeDisplayLocale(), getClass());
	}

	private static final long _MINIMUM_MAX_SIZE = 1024 * 1024;

	@Reference
	private Language _language;

	@Reference
	private UploadServletRequestConfigurationProvider
		_uploadServletRequestConfigurationProvider;

}