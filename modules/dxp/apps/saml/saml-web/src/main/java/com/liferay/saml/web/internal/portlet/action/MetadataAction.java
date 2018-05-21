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

import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.util.SamlHttpRequestUtil;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	immediate = true, property = "path=/portal/saml/metadata",
	service = StrutsAction.class
)
public class MetadataAction extends BaseSamlStrutsAction {

	@Override
	@Reference(unbind = "-")
	public void setSamlProviderConfigurationHelper(
		SamlProviderConfigurationHelper samlProviderConfigurationHelper) {

		super.setSamlProviderConfigurationHelper(
			samlProviderConfigurationHelper);
	}

	@Override
	protected String doExecute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		response.setContentType(ContentTypes.TEXT_XML);

		PrintWriter printWriter = response.getWriter();

		String metadata = _samlHttpRequestUtil.getEntityDescriptorString(
			request);

		printWriter.print(metadata);

		return null;
	}

	@Reference
	private SamlHttpRequestUtil _samlHttpRequestUtil;

}