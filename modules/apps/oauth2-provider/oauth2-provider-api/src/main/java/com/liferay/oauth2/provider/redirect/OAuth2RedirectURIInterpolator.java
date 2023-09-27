/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.redirect;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Raymond Augé
 */
public class OAuth2RedirectURIInterpolator {

	public static final String TOKEN_PORT_WITH_COLON = "@port-with-colon@";

	public static final String TOKEN_PROTOCOL = "@protocol@";

	public static List<String> interpolateRedirectURIsList(
		HttpServletRequest httpServletRequest, List<String> redirectURIsList,
		Portal portal) {

		List<String> interpolattedRedirectURIsList = new ArrayList<>();

		String protocol = Http.HTTP;

		boolean secure = false;

		if (httpServletRequest != null) {
			secure = portal.isSecure(httpServletRequest);
		}
		else if (Http.HTTPS.equals(
					PropsUtil.get(PropsKeys.PORTAL_INSTANCE_PROTOCOL)) ||
				 Http.HTTPS.equals(
					 PropsUtil.get(PropsKeys.WEB_SERVER_PROTOCOL))) {

			secure = true;
		}

		if (secure) {
			protocol = Http.HTTPS;
		}

		String portWithColon = ":" + portal.getPortalLocalPort(secure);

		if (httpServletRequest != null) {
			portWithColon = ":" + portal.getForwardedPort(httpServletRequest);
		}

		if (Objects.equals(portWithColon, ":80")) {
			portWithColon = StringPool.BLANK;
		}

		if (secure && Objects.equals(portWithColon, ":443")) {
			portWithColon = StringPool.BLANK;
		}

		for (String redirectURI : redirectURIsList) {
			interpolattedRedirectURIsList.add(
				StringUtil.replace(
					redirectURI, _TOKENS,
					new String[] {portWithColon, protocol}));
		}

		return interpolattedRedirectURIsList;
	}

	private static final String[] _TOKENS = {
		TOKEN_PORT_WITH_COLON, TOKEN_PROTOCOL
	};

}