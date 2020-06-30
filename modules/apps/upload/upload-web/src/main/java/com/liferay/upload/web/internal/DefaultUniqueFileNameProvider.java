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

package com.liferay.upload.web.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.upload.UploadServletRequestConfigurationHelper;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.upload.UniqueFileNameProvider;

import java.util.function.Predicate;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = UniqueFileNameProvider.class)
public class DefaultUniqueFileNameProvider implements UniqueFileNameProvider {

	@Override
	public String provide(String fileName, Predicate<String> predicate)
		throws PortalException {

		String baseFileName = _file.stripParentheticalSuffix(fileName);

		String uniqueFileName = baseFileName;

		int tries = 0;

		while (predicate.test(uniqueFileName)) {
			if (tries >=
					_uploadServletRequestConfigurationHelper.getMaxTries()) {

				throw new PortalException(
					"Unable to get a unique file name for " + baseFileName);
			}

			tries++;

			uniqueFileName = FileUtil.appendParentheticalSuffix(
				baseFileName, String.valueOf(tries));
		}

		return uniqueFileName;
	}

	@Reference
	private File _file;

	@Reference
	private UploadServletRequestConfigurationHelper
		_uploadServletRequestConfigurationHelper;

}