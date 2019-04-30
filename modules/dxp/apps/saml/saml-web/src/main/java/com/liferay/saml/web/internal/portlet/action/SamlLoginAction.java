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
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
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
	immediate = true, property = "path=/portal/saml/login",
	service = StrutsAction.class
)
public class SamlLoginAction extends BaseSamlStrutsAction {

	@Override
	public boolean isEnabled() {
		if (samlProviderConfigurationHelper.isRoleSp()) {
			return super.isEnabled();
		}

		return false;
	}

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

		String entityId = ParamUtil.getString(request, "idpEntityId");

		long companyId = _portal.getCompanyId(request);

		if (Validator.isNotNull(entityId)) {
			SamlSpIdpConnection samlSpIdpConnection =
				_samlSpIdpConnectionLocalService.getSamlSpIdpConnection(
					companyId, entityId);

			request.setAttribute(
				SamlWebKeys.SAML_SP_IDP_CONNECTION, samlSpIdpConnection);

			return null;
		}

		List<SamlSpIdpConnection> samlSpIdpConnections =
			_samlSpIdpConnectionLocalService.getSamlSpIdpConnections(companyId);

		Stream<SamlSpIdpConnection> stream = samlSpIdpConnections.stream();

		samlSpIdpConnections = stream.filter(
			samlSpIdpConnection -> isEnabled(samlSpIdpConnection, request)
		).collect(
			Collectors.toList()
		);

		if (samlSpIdpConnections.isEmpty()) {
			SamlProviderConfiguration samlProviderConfiguration =
				samlProviderConfigurationHelper.getSamlProviderConfiguration();

			if (samlProviderConfiguration.allowShowingTheLoginPortlet()) {
				return null;
			}
		}
		else if (samlSpIdpConnections.size() == 1) {
			request.setAttribute(
				SamlWebKeys.SAML_SP_IDP_CONNECTION,
				samlSpIdpConnections.get(0));

			return null;
		}

		request.setAttribute(
			SamlWebKeys.SAML_SSO_LOGIN_CONTEXT,
			toJSONObject(samlSpIdpConnections));

		JspUtil.dispatch(
			request, response, "/portal/saml/select_idp.jsp",
			"please-select-your-identity-provider", false);

		return null;
	}

	protected boolean isEnabled(
		SamlSpIdpConnection samlSpIdpConnection, HttpServletRequest request) {

		if (_samlSpIdpConnectionsProfile != null) {
			return _samlSpIdpConnectionsProfile.isEnabled(
				samlSpIdpConnection, request);
		}

		return samlSpIdpConnection.isEnabled();
	}

	protected JSONObject toJSONObject(
		List<SamlSpIdpConnection> samlSpIdpConnections) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (SamlSpIdpConnection samlSpIdpConnection : samlSpIdpConnections) {
			JSONObject samlIdpConnectionJSONObject = JSONUtil.put(
				"enabled", samlSpIdpConnection.getEnabled()
			).put(
				"entityId", samlSpIdpConnection.getSamlIdpEntityId()
			).put(
				"name", samlSpIdpConnection.getName()
			);

			jsonArray.put(samlIdpConnectionJSONObject);
		}

		return JSONUtil.put("relevantIdpConnections", jsonArray);
	}

	@Reference
	private Portal _portal;

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile SamlSpIdpConnectionsProfile _samlSpIdpConnectionsProfile;

}