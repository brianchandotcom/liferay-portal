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
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.upload.UniqueFileNameProvider;

import java.util.function.Predicate;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component
public class UniqueFileNameProviderImpl implements UniqueFileNameProvider {

	@Override
	public String provide(String fileName, Predicate<String> exists)
		throws PortalException {

		String curFileName = fileName;
		int suffix = 1;

		while (exists.test(curFileName)) {
			if (suffix++ >= _UNIQUE_FILE_NAME_TRIES) {
				throw new PortalException(
					"Unable to get a unique file name for " + fileName);
			}

			curFileName = FileUtil.appendParentheticalSuffix(
				fileName, String.valueOf(suffix));
		}

		return curFileName;
	}

	private static final int _UNIQUE_FILE_NAME_TRIES = 50;

}