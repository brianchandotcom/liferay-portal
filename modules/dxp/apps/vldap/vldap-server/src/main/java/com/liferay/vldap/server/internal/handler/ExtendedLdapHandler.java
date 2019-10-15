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

package com.liferay.vldap.server.internal.handler;

import com.liferay.vldap.server.internal.handler.util.LdapHandlerContext;
import com.liferay.vldap.server.internal.handler.util.LdapSslContextFactory;
import com.liferay.vldap.server.internal.util.OIDConstants;
import com.liferay.vldap.server.internal.util.VLDAPConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.directory.api.ldap.model.message.ExtendedRequest;
import org.apache.directory.api.ldap.model.message.ExtendedResponse;
import org.apache.directory.api.ldap.model.message.Request;
import org.apache.directory.api.ldap.model.message.Response;
import org.apache.mina.core.filterchain.IoFilterChain;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.ssl.SslFilter;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class ExtendedLdapHandler extends BaseLdapHandler {

	@Override
	public List<Response> messageReceived(
		Request request, IoSession ioSession,
		LdapHandlerContext ldapHandlerContext) {

		ExtendedRequest extendedRequest = (ExtendedRequest)request;

		String oid = extendedRequest.getRequestName();

		if (oid.equals(OIDConstants.START_TLS)) {
			return handleStartTLS(extendedRequest, ioSession);
		}

		return null;
	}

	protected List<Response> handleStartTLS(
		ExtendedRequest extendedRequest, IoSession ioSession) {

		SSLContext sslContext = LdapSslContextFactory.getSSLContext(true);

		SslFilter sslFilter = new SslFilter(sslContext);

		IoFilterChain ioFilterChain = ioSession.getFilterChain();

		ioFilterChain.addFirst("sslFilter", sslFilter);

		ExtendedResponse extendedResponse = (ExtendedResponse)getResultResponse(
			extendedRequest);

		extendedResponse.setResponseName(OIDConstants.START_TLS);

		Map<Object, Object> sessionAttributes = new HashMap<>();

		sessionAttributes.put(SslFilter.DISABLE_ENCRYPTION_ONCE, true);

		extendedResponse.put(
			VLDAPConstants.SESSION_ATTRIBUTES, sessionAttributes);

		return toList(extendedResponse);
	}

}