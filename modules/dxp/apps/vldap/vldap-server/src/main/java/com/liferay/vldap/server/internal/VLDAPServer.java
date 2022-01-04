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

package com.liferay.vldap.server.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.vldap.server.internal.handler.util.LdapSslContextFactory;
import com.liferay.vldap.server.internal.util.PortletPropsValues;

import java.io.IOException;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import java.util.Map;

import org.apache.directory.api.ldap.codec.protocol.mina.LdapProtocolCodecFactory;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.ssl.SslFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class VLDAPServer {

	public void destroy() {
		_destroyIoAcceptor(_ldapSocketAcceptor);
		_destroyIoAcceptor(_ldapsSocketAcceptor);

		_ldapSocketAcceptor = null;
		_ldapsSocketAcceptor = null;
	}

	public void init() throws IOException {
		_ldapSocketAcceptor = new NioSocketAcceptor();
		_ldapsSocketAcceptor = new NioSocketAcceptor();

		if (PortletPropsValues.LDAP_BIND_PORT > 0) {
			_initIoAcceptor(
				_ldapSocketAcceptor, PortletPropsValues.LDAP_BIND_PORT, false);
		}

		if (PortletPropsValues.LDAPS_BIND_PORT > 0) {
			_initIoAcceptor(
				_ldapsSocketAcceptor, PortletPropsValues.LDAPS_BIND_PORT, true);
		}
	}

	private void _destroyIoAcceptor(NioSocketAcceptor nioSocketAcceptor) {
		if (nioSocketAcceptor == null) {
			return;
		}

		Map<Long, IoSession> managedSessions =
			nioSocketAcceptor.getManagedSessions();

		for (IoSession ioSession : managedSessions.values()) {
			ioSession.close(true);
		}

		nioSocketAcceptor.unbind();
		nioSocketAcceptor.dispose();
	}

	private void _initCodec(NioSocketAcceptor nioSocketAcceptor) {
		DefaultIoFilterChainBuilder defaultIoFilterChainBuilder =
			nioSocketAcceptor.getFilterChain();

		ProtocolCodecFactory protocolCodecFactory =
			new LdapProtocolCodecFactory();

		IoFilterAdapter ioFilterAdapter = new ProtocolCodecFilter(
			protocolCodecFactory);

		defaultIoFilterChainBuilder.addLast("codec", ioFilterAdapter);
	}

	private void _initIoAcceptor(
			NioSocketAcceptor nioSocketAcceptor, int bindPort, boolean useSSL)
		throws IOException {

		nioSocketAcceptor.setReuseAddress(true);

		if (useSSL) {
			_initSslFilter(nioSocketAcceptor);
		}

		_initIoHandler(nioSocketAcceptor);
		_initCodec(nioSocketAcceptor);
		_initLogging(nioSocketAcceptor);

		SocketAddress socketAddress = new InetSocketAddress(bindPort);

		nioSocketAcceptor.bind(socketAddress);
	}

	private void _initIoHandler(NioSocketAcceptor nioSocketAcceptor) {
		nioSocketAcceptor.setHandler(new DispatchIoHandler());
	}

	private void _initLogging(NioSocketAcceptor nioSocketAcceptor) {
		if (_log.isDebugEnabled()) {
			DefaultIoFilterChainBuilder defaultIoFilterChainBuilder =
				nioSocketAcceptor.getFilterChain();

			defaultIoFilterChainBuilder.addLast("logger", new LoggingFilter());
		}
	}

	private void _initSslFilter(NioSocketAcceptor nioSocketAcceptor) {
		DefaultIoFilterChainBuilder defaultIoFilterChainBuilder =
			nioSocketAcceptor.getFilterChain();

		SslFilter sslFilter = new SslFilter(
			LdapSslContextFactory.getSSLContext(true));

		defaultIoFilterChainBuilder.addFirst("sslFilter", sslFilter);
	}

	private static final Log _log = LogFactoryUtil.getLog(VLDAPServer.class);

	private NioSocketAcceptor _ldapSocketAcceptor;
	private NioSocketAcceptor _ldapsSocketAcceptor;

}