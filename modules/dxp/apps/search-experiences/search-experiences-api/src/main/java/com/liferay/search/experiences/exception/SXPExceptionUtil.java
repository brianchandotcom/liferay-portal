/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.exception;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.experiences.blueprint.exception.InvalidElementInstanceException;
import com.liferay.search.experiences.blueprint.exception.InvalidWebCacheItemException;
import com.liferay.search.experiences.blueprint.exception.PrivateIPAddressException;

/**
 * @author André de Oliveira
 * @author Wade Cao
 */
public class SXPExceptionUtil {

	public static boolean hasErrors(Throwable throwable) {
		if (throwable instanceof InvalidElementInstanceException ||
			throwable instanceof InvalidWebCacheItemException ||
			throwable instanceof PrivateIPAddressException) {

			return false;
		}

		if ((throwable.getClass() == RuntimeException.class) &&
			Validator.isBlank(throwable.getMessage())) {

			for (Throwable curThrowable : throwable.getSuppressed()) {
				if (hasErrors(curThrowable)) {
					return true;
				}
			}

			return false;
		}

		return true;
	}

}