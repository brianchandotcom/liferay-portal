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

package com.liferay.osb.faro.web.internal.context;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationLocalService;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(service = GroupInfoContextProvider.class)
@Provider
public class GroupInfoContextProvider implements ContextProvider<GroupInfo> {

	@Override
	public GroupInfo createContext(Message message) {
		HttpServletRequest httpServletRequest =
			(HttpServletRequest)message.getContextualProperty("HTTP.REQUEST");

		String authorization = httpServletRequest.getHeader("Authorization");

		if (authorization == null) {
			throw new IllegalStateException(
				"Authorization Header is not available");
		}

		try {
			OAuth2Authorization oAuth2Authorization =
				_oAuth2AuthorizationLocalService.
					getOAuth2AuthorizationByAccessTokenContent(
						authorization.substring(7));

			ExpandoBridge expandoBridge =
				oAuth2Authorization.getExpandoBridge();

			return new GroupInfo(
				(long)expandoBridge.getAttribute("groupId", false));
		}
		catch (Exception exception) {
			throw new IllegalStateException(
				"Unable to fetch the OAuth2Authorization with access token " +
					authorization.substring(7),
				exception);
		}
	}

	@Reference
	private OAuth2AuthorizationLocalService _oAuth2AuthorizationLocalService;

}