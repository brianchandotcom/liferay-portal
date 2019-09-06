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

package com.liferay.document.library.web.internal.util;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;

/**
 * @author Adolfo Pérez
 */
public class DLPortletConfigurationIconUtil {

	public static <T> T runWithDefaultValueOnError(
		T defaultValue, UnsafeSupplier<T, PortalException> unsafeSupplier) {

		try {
			return unsafeSupplier.get();
		}
		catch (PrincipalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return defaultValue;
		}
		catch (PortalException pe) {
			_log.error(pe, pe);

			return defaultValue;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLPortletConfigurationIconUtil.class);

}