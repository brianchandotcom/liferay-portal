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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.saml.constants.SamlPortletKeys;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.runtime.servlet.profile.WebSsoProfile;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(
	property = {
		"javax.portlet.name=" + SamlPortletKeys.SAML,
		"mvc.command.name=/saml/send_authn_request"
	},
	service = MVCResourceCommand.class
)
public class SendAuthnRequestMVCResourceCommand implements MVCResourceCommand {

	@Override
	public boolean serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);

		String entityId = ParamUtil.getString(
			httpServletRequest, "idpEntityId");

		if (Validator.isNull(entityId)) {
			return false;
		}

		long companyId = _portal.getCompanyId(httpServletRequest);

		try {
			SamlSpIdpConnection samlSpIdpConnection =
				_samlSpIdpConnectionLocalService.getSamlSpIdpConnection(
					companyId, entityId);

			httpServletRequest.setAttribute(
				SamlWebKeys.SAML_SP_IDP_CONNECTION, samlSpIdpConnection);

			_login(
				httpServletRequest,
				_portal.getHttpServletResponse(resourceResponse));
		}
		catch (Exception exception) {
			if (_log.isInfoEnabled()) {
				_log.info(exception, exception);
			}

			return false;
		}

		return true;
	}

	private void _login(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		String relayState = ParamUtil.getString(httpServletRequest, "redirect");

		if (Validator.isNotNull(relayState)) {
			relayState = _portal.escapeRedirect(relayState);
		}

		HttpSession session = httpServletRequest.getSession();

		LastPath lastPath = (LastPath)session.getAttribute(WebKeys.LAST_PATH);

		if (GetterUtil.getBoolean(
				_props.get(PropsKeys.AUTH_FORWARD_BY_LAST_PATH)) &&
			(lastPath != null) && Validator.isNull(relayState)) {

			StringBundler sb = new StringBundler(4);

			sb.append(_portal.getPortalURL(httpServletRequest));
			sb.append(lastPath.getContextPath());
			sb.append(lastPath.getPath());
			sb.append(lastPath.getParameters());

			relayState = sb.toString();
		}
		else if (Validator.isNull(relayState)) {
			relayState = _portal.getHomeURL(httpServletRequest);
		}

		_webSsoProfile.sendAuthnRequest(
			httpServletRequest, httpServletResponse, relayState);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SendAuthnRequestMVCResourceCommand.class);

	@Reference
	private Portal _portal;

	@Reference
	private Props _props;

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

	@Reference
	private WebSsoProfile _webSsoProfile;

}