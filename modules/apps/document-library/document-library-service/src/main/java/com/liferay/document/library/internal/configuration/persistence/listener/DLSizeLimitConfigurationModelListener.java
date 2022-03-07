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

package com.liferay.document.library.internal.configuration.persistence.listener;

import com.liferay.document.library.internal.configuration.DLSizeLimitConfiguration;
import com.liferay.document.library.internal.configuration.admin.service.DLSizeLimitManagedServiceFactory;
import com.liferay.document.library.internal.util.MimeTypeSizeLimitUtil;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Dictionary;

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

	protected void setDLSizeLimitManagedServiceFactory(
		DLSizeLimitManagedServiceFactory dlSizeLimitManagedServiceFactory) {

		_dlSizeLimitManagedServiceFactory = dlSizeLimitManagedServiceFactory;
	}

	@Reference
	private DLSizeLimitManagedServiceFactory _dlSizeLimitManagedServiceFactory;

}