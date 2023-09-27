/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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