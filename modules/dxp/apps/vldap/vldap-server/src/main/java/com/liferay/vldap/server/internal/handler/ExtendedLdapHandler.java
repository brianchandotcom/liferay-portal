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