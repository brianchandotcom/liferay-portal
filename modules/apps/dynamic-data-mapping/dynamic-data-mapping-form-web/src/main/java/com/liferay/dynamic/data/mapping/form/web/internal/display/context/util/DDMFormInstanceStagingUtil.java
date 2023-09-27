/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.display.context.util;

import com.liferay.dynamic.data.mapping.service.http.DDMFormInstanceServiceHttp;
import com.liferay.exportimport.kernel.staging.StagingURLHelperUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;

/**
 * @author Marcos Martins
 */
public class DDMFormInstanceStagingUtil {

	public static boolean isFormInstancePublishedToRemoteLive(
		Group group, User user, String uuid) {

		try {
			String remoteURL = StagingURLHelperUtil.buildRemoteURL(
				group.getTypeSettingsProperties());

			HttpPrincipal httpPrincipal = new HttpPrincipal(
				remoteURL, user.getLogin(), user.getPassword(),
				user.isPasswordEncrypted());

			int formInstancesCount =
				DDMFormInstanceServiceHttp.getFormInstancesCount(
					httpPrincipal, uuid);

			if (formInstancesCount > 0) {
				return true;
			}

			return false;
		}
		catch (PortalException | SystemException exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to verify if form instance was published to live",
					exception);
			}

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceStagingUtil.class);

}