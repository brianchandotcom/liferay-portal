/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.upload.web.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.upload.configuration.UploadServletRequestConfigurationProvider;
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
					_uploadServletRequestConfigurationProvider.getMaxTries()) {

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
	private UploadServletRequestConfigurationProvider
		_uploadServletRequestConfigurationProvider;

}