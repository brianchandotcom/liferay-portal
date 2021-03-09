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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.saml.constants.SamlPortletKeys;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.servlet.profile.SamlSpIdpConnectionsProfile;
import com.liferay.saml.util.JspUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Stian Sigvartsen
 */
@Component(
	property = {
		"javax.portlet.name=" + SamlPortletKeys.SAML,
		"mvc.command.name=/saml/select_idp"
	},
	service = MVCRenderCommand.class
)
public class SelectIdpMVCRenderCommand extends BaseSamlMVCRenderCommand {

	@Override
	public boolean isEnabled() {
		if (super.isEnabled()) {
			return samlProviderConfigurationHelper.isRoleSp();
		}

		return false;
	}

	@Override
	@Reference(unbind = "-")
	public void setPortal(Portal portal) {
		super.setPortal(portal);
	}

	@Override
	@Reference(unbind = "-")
	public void setSamlProviderConfigurationHelper(
		SamlProviderConfigurationHelper samlProviderConfigurationHelper) {

		super.setSamlProviderConfigurationHelper(
			samlProviderConfigurationHelper);
	}

	@Override
	protected String doRender(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		List<SamlSpIdpConnection> samlSpIdpConnections =
			_samlSpIdpConnectionLocalService.getSamlSpIdpConnections(
				portal.getCompanyId(httpServletRequest));

		Stream<SamlSpIdpConnection> stream = samlSpIdpConnections.stream();

		samlSpIdpConnections = stream.filter(
			samlSpIdpConnection -> isEnabled(
				samlSpIdpConnection, httpServletRequest)
		).collect(
			Collectors.toList()
		);

		httpServletRequest.setAttribute(
			SamlWebKeys.SAML_SSO_LOGIN_CONTEXT,
			toJSONObject(samlSpIdpConnections));

		return JspUtil.PATH_PORTAL_SAML_SELECT_IDP;
	}

	protected boolean isEnabled(
		SamlSpIdpConnection samlSpIdpConnection,
		HttpServletRequest httpServletRequest) {

		if (_samlSpIdpConnectionsProfile != null) {
			return _samlSpIdpConnectionsProfile.isEnabled(
				samlSpIdpConnection, httpServletRequest);
		}

		return samlSpIdpConnection.isEnabled();
	}

	protected JSONObject toJSONObject(
		List<SamlSpIdpConnection> samlSpIdpConnections) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (SamlSpIdpConnection samlSpIdpConnection : samlSpIdpConnections) {
			jsonArray.put(
				JSONUtil.put(
					"enabled", samlSpIdpConnection.isEnabled()
				).put(
					"entityId", samlSpIdpConnection.getSamlIdpEntityId()
				).put(
					"name", samlSpIdpConnection.getName()
				));
		}

		return JSONUtil.put("relevantIdpConnections", jsonArray);
	}

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile SamlSpIdpConnectionsProfile _samlSpIdpConnectionsProfile;

}