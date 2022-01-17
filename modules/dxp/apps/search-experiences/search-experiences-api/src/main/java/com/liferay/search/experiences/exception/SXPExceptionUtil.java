/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.exception;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.experiences.blueprint.exception.InvalidElementInstanceException;

/**
 * @author André de Oliveira
 * @author Wade Cao
 */
public class SXPExceptionUtil {

	public static boolean hasErrors(Throwable throwable) {
		if (throwable instanceof InvalidElementInstanceException) {
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