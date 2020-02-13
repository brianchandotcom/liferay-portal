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

package com.liferay.dynamic.data.mapping.staging.impl;

import com.liferay.dynamic.data.mapping.service.http.DDMFormInstanceServiceHttp;
import com.liferay.dynamic.data.mapping.staging.DDMFormInstanceStagingHelper;
import com.liferay.exportimport.kernel.staging.StagingURLHelperUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcos Martins
 */
@Component(immediate = true, service = DDMFormInstanceStagingHelper.class)
public class DDMFormInstanceStagingHelperImpl
	implements DDMFormInstanceStagingHelper {

	@Override
	public boolean isFormInstancePublishedToRemoteLive(
		Group group, User user, String uuid) {

		try {
			String remoteURL = StagingURLHelperUtil.buildRemoteURL(
				group.getTypeSettingsProperties());

			HttpPrincipal httpPrincipal = new HttpPrincipal(
				remoteURL, user.getLogin(), user.getPassword(),
				user.isPasswordEncrypted());

			int countFormInstances =
				DDMFormInstanceServiceHttp.getFormInstancesCount(
					httpPrincipal, uuid);

			if (countFormInstances > 0) {
				return true;
			}

			return false;
		}
		catch (PortalException | SystemException exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceStagingHelperImpl.class);

}