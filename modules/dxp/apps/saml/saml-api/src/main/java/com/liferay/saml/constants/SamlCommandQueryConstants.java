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

package com.liferay.saml.constants;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;

/**
 * @author Arthur Chan
 */
public class SamlCommandQueryConstants {

	public static final String ACS = StringBundler.concat(
		CharPool.QUESTION, SamlCommandQueryConstants._P_P_ID,
		CharPool.AMPERSAND, SamlCommandQueryConstants._P_P_LIFECYCLE_ACTION,
		CharPool.AMPERSAND, SamlCommandQueryConstants._ACTION_COMMAND_NAME,
		"/saml/assertion_consumer_service");

	public static final String AUTH_REDIRECT = StringBundler.concat(
		CharPool.QUESTION, SamlCommandQueryConstants._P_P_ID,
		CharPool.AMPERSAND, SamlCommandQueryConstants._P_P_LIFECYCLE_ACTION,
		CharPool.AMPERSAND, SamlCommandQueryConstants._ACTION_COMMAND_NAME,
		"/saml/auth_redirect");

	public static final String FINISH_SLO = StringBundler.concat(
		CharPool.QUESTION, SamlCommandQueryConstants._P_P_ID,
		CharPool.AMPERSAND, SamlCommandQueryConstants._P_P_LIFECYCLE_RESOURCE,
		CharPool.AMPERSAND, SamlCommandQueryConstants._RESOURCE_COMMAND_NAME,
		"/saml/finish_slo");

	public static final String SELECT_IDP = StringBundler.concat(
		CharPool.QUESTION, SamlCommandQueryConstants._P_P_ID,
		CharPool.AMPERSAND, SamlCommandQueryConstants._P_P_LIFECYCLE_RENDER,
		CharPool.AMPERSAND, SamlCommandQueryConstants._P_P_STATE,
		CharPool.AMPERSAND, SamlCommandQueryConstants._RENDER_COMMAND_NAME,
		"/saml/select_idp");

	public static final String SEND_SAML_SLO_REQUEST_INFOS =
		StringBundler.concat(
			CharPool.QUESTION, SamlCommandQueryConstants._P_P_ID,
			CharPool.AMPERSAND,
			SamlCommandQueryConstants._P_P_LIFECYCLE_RESOURCE,
			CharPool.AMPERSAND,
			SamlCommandQueryConstants._RESOURCE_COMMAND_NAME,
			"/saml/send_saml_slo_request_infos");

	public static final String SLO = StringBundler.concat(
		CharPool.QUESTION, SamlCommandQueryConstants._P_P_ID,
		CharPool.AMPERSAND, SamlCommandQueryConstants._P_P_LIFECYCLE_RESOURCE,
		CharPool.AMPERSAND, SamlCommandQueryConstants._RESOURCE_COMMAND_NAME,
		"/saml/slo");

	public static final String SLO_LOGOUT = StringBundler.concat(
		CharPool.QUESTION, SamlCommandQueryConstants._P_P_ID,
		CharPool.AMPERSAND, SamlCommandQueryConstants._P_P_LIFECYCLE_RENDER,
		CharPool.AMPERSAND, SamlCommandQueryConstants._P_P_STATE,
		CharPool.AMPERSAND, SamlCommandQueryConstants._RENDER_COMMAND_NAME,
		"/saml/slo_logout");

	public static final String SLO_SOAP = StringBundler.concat(
		CharPool.QUESTION, SamlCommandQueryConstants._P_P_ID,
		CharPool.AMPERSAND, SamlCommandQueryConstants._P_P_LIFECYCLE_RESOURCE,
		CharPool.AMPERSAND, SamlCommandQueryConstants._RESOURCE_COMMAND_NAME,
		"/saml/slo_soap");

	public static final String WEB_SSO = StringBundler.concat(
		CharPool.QUESTION, SamlCommandQueryConstants._P_P_ID,
		CharPool.AMPERSAND, SamlCommandQueryConstants._P_P_LIFECYCLE_RESOURCE,
		CharPool.AMPERSAND, SamlCommandQueryConstants._RESOURCE_COMMAND_NAME,
		"/saml/web_sso");

	private static final String _ACTION_COMMAND_NAME =
		CharPool.UNDERLINE + SamlPortletKeys.SAML + "_javax.portlet.action=";

	private static final String _P_P_ID = "p_p_id=" + SamlPortletKeys.SAML;

	private static final String _P_P_LIFECYCLE_ACTION = "p_p_lifecycle=1";

	private static final String _P_P_LIFECYCLE_RENDER = "p_p_lifecycle=0";

	private static final String _P_P_LIFECYCLE_RESOURCE = "p_p_lifecycle=2";

	private static final String _P_P_STATE = "p_p_state=maximized";

	private static final String _RENDER_COMMAND_NAME =
		CharPool.UNDERLINE + SamlPortletKeys.SAML + "_mvcRenderCommandName=";

	private static final String _RESOURCE_COMMAND_NAME = "p_p_resource_id=";

}