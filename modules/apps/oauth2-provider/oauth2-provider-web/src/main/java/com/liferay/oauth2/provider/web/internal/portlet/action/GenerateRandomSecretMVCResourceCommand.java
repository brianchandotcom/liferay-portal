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

package com.liferay.oauth2.provider.web.internal.portlet.action;

import com.liferay.oauth2.provider.util.OAuth2SecureRandomGenerator;
import com.liferay.oauth2.provider.web.internal.constants.OAuth2ProviderPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tomas Polesovsky
 */
@Component(
	property = {
		"javax.portlet.name=" + OAuth2ProviderPortletKeys.OAUTH2_ADMIN,
		"mvc.command.name=/admin/generate_random_secret"
	},
	service = MVCResourceCommand.class
)
public class GenerateRandomSecretMVCResourceCommand
	implements MVCResourceCommand {

	@Override
	public boolean serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		resourceResponse.setContentType("text/plain");

		try {
			PrintWriter printWriter = resourceResponse.getWriter();

			String randomSecret =
				OAuth2SecureRandomGenerator.generateClientSecret();

			printWriter.write(randomSecret);
		}
		catch (IOException ioe) {
			throw new PortletException(ioe);
		}

		return false;
	}

}