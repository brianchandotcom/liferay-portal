/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.configuration.persistence.listener;

import com.liferay.document.library.internal.configuration.DLSizeLimitConfiguration;
import com.liferay.document.library.internal.util.MimeTypeSizeLimitUtil;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Dictionary;

import org.osgi.service.component.annotations.Component;

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

		String[] mimeTypeSizeLimit = (String[])properties.get(
			"mimeTypeSizeLimit");

		if (ArrayUtil.isEmpty(mimeTypeSizeLimit)) {
			return;
		}

		for (String mimeTypeSizeString : mimeTypeSizeLimit) {
			MimeTypeSizeLimitUtil.parseMimeTypeSizeLimit(
				mimeTypeSizeString,
				(mimeType, sizeLimit) -> {
					if (mimeType == null) {
						throw new ConfigurationModelListenerException(
							mimeTypeSizeString +
								" does not contain a valid mime type name",
							DLSizeLimitConfiguration.class,
							DLSizeLimitConfigurationModelListener.class,
							properties);
					}

					if (sizeLimit == null) {
						throw new ConfigurationModelListenerException(
							mimeTypeSizeString +
								" does not contain a valid size limit value",
							DLSizeLimitConfiguration.class,
							DLSizeLimitConfigurationModelListener.class,
							properties);
					}
				});
		}
	}

}