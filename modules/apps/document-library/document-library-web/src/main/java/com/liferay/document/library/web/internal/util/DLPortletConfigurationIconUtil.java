/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		catch (PrincipalException principalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(principalException);
			}

			return defaultValue;
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return defaultValue;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLPortletConfigurationIconUtil.class);

}