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