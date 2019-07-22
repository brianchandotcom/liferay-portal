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

package com.liferay.document.library.opener.one.drive.web.internal;

import com.liferay.document.library.opener.one.drive.web.internal.configuration.DLOneDriveCompanyConfiguration;
import com.liferay.document.library.opener.one.drive.web.internal.graph.IAuthenticationProviderImpl;
import com.liferay.document.library.opener.one.drive.web.internal.oauth.AccessToken;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.Validator;

import com.microsoft.graph.core.DefaultClientConfig;
import com.microsoft.graph.models.extensions.IGraphServiceClient;
import com.microsoft.graph.models.extensions.User;
import com.microsoft.graph.requests.extensions.GraphServiceClient;
import com.microsoft.graph.requests.extensions.IUserRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = DLOpenerOneDriveManager.class)
public class DLOpenerOneDriveManager {

	public User getUser(AccessToken accessToken) {
		IGraphServiceClient iGraphServiceClientBuilder =
			GraphServiceClient.fromConfig(
				DefaultClientConfig.createWithAuthenticationProvider(
					new IAuthenticationProviderImpl(accessToken)));

		IUserRequest iUserRequest = iGraphServiceClientBuilder.me(
		).buildRequest();

		return iUserRequest.get();
	}

	public boolean isConfigured(long companyId) throws ConfigurationException {
		DLOneDriveCompanyConfiguration dlOneDriveCompanyConfiguration =
			_configurationProvider.getCompanyConfiguration(
				DLOneDriveCompanyConfiguration.class, companyId);

		if (Validator.isNotNull(dlOneDriveCompanyConfiguration.clientId()) &&
			Validator.isNotNull(
				dlOneDriveCompanyConfiguration.clientSecret()) &&
			Validator.isNotNull(dlOneDriveCompanyConfiguration.tenant())) {

			return true;
		}

		return false;
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

}