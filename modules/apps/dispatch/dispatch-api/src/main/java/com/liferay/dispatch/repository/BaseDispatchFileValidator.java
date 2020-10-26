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

package com.liferay.dispatch.repository;

import com.liferay.petra.string.StringPool;

import java.util.Objects;

/**
 * @author Igor Beslic
 */
public abstract class BaseDispatchFileValidator
	implements DispatchFileValidator {

	protected boolean isValidExtension(
		String extension, String[] validExtensions) {

		for (String fileExtension : validExtensions) {
			if (Objects.equals(StringPool.STAR, fileExtension) ||
				Objects.equals(fileExtension, extension)) {

				return true;
			}
		}

		return false;
	}

	protected boolean isValidSize(long size, long validSize) {
		if ((validSize == 0) || (size < validSize)) {
			return true;
		}

		return false;
	}

}