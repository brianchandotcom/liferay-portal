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

package com.liferay.saml.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.runtime.metadata.LocalEntityManager;
import com.liferay.saml.web.internal.constants.SamlAdminPortletKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SamlAdminPortletKeys.SAML_ADMIN,
		"mvc.command.name=/admin/downloadCertificate"
	},
	service = MVCResourceCommand.class
)
public class DownloadCertificateMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		String encodedCertificate =
			_localEntityManager.getEncodedLocalEntityCertificate();

		if (Validator.isNull(encodedCertificate)) {
			return;
		}

		StringBundler sb = new StringBundler(3);

		sb.append("-----BEGIN CERTIFICATE-----\r\n");
		sb.append(encodedCertificate);
		sb.append("\r\n-----END CERTIFICATE-----");

		String content = sb.toString();

		PortletResponseUtil.sendFile(
			resourceRequest, resourceResponse,
			_localEntityManager.getLocalEntityId() + ".pem", content.getBytes(),
			ContentTypes.TEXT_PLAIN);
	}

	@Reference
	private LocalEntityManager _localEntityManager;

}