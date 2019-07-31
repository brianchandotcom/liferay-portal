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

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.cache.thread.local.Lifecycle;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.vldap.server.internal.handler.AbandonLdapHandler;
import com.liferay.vldap.server.internal.handler.BindLdapHandler;
import com.liferay.vldap.server.internal.handler.CompareLdapHandler;
import com.liferay.vldap.server.internal.handler.ExtendedLdapHandler;
import com.liferay.vldap.server.internal.handler.LdapHandler;
import com.liferay.vldap.server.internal.handler.SearchLdapHandler;
import com.liferay.vldap.server.internal.handler.UnbindLdapHandler;
import com.liferay.vldap.server.internal.handler.UnwillingToPerformLdapHandler;
import com.liferay.vldap.server.internal.handler.util.LdapHandlerContext;
import com.liferay.vldap.server.internal.handler.util.LdapHandlerThreadLocal;
import com.liferay.vldap.server.internal.handler.util.LiferayLdapMessageContainer;
import com.liferay.vldap.server.internal.util.PortletPropsValues;
import com.liferay.vldap.server.internal.util.VLDAPConstants;

import java.util.List;
import java.util.Map;

import org.apache.directory.api.ldap.codec.api.LdapDecoder;
import org.apache.directory.api.ldap.model.message.MessageTypeEnum;
import org.apache.directory.api.ldap.model.message.Request;
import org.apache.directory.api.ldap.model.message.Response;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.FilterEvent;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class DispatchIoHandler implements IoHandler {

	@Override
	public void event(IoSession ioSession, FilterEvent event) {
	}

	@Override
	public void exceptionCaught(IoSession ioSession, Throwable cause) {
		if (_log.isDebugEnabled()) {
			_log.debug(cause, cause);
		}
	}

	@Override
	public void inputClosed(IoSession ioSession) throws Exception {
	}

	@Override
	public void messageReceived(IoSession ioSession, Object message) {
		Request request = (Request)message;

		LdapHandler ldapHandler = getLdapHandler(request);

		if (ldapHandler == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(request.getType() + " is not supported");
			}

			ldapHandler = _unwillingToPerformLdapHandler;
		}

		try {
			List<Response> responses = ldapHandler.messageReceived(
				request, ioSession, getLdapHandlerContext(ioSession));

			writeResponses(responses, ioSession);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}
		finally {
			ThreadLocalCacheManager.clearAll(Lifecycle.REQUEST);

			CentralizedThreadLocal.clearShortLivedThreadLocals();
		}
	}

	@Override
	public void messageSent(IoSession ioSession, Object message) {
	}

	@Override
	public void sessionClosed(IoSession ioSession) {
		LdapHandlerThreadLocal.clearSocketAddress();
	}

	@Override
	public void sessionCreated(IoSession ioSession) {
	}

	@Override
	public void sessionIdle(IoSession ioSession, IdleStatus idleStatus) {
	}

	@Override
	public void sessionOpened(IoSession ioSession) {
		try {
			LdapHandlerThreadLocal.setSocketAddress(
				ioSession.getRemoteAddress());

			if (!LdapHandlerThreadLocal.isHostAllowed(
					PortletPropsValues.HOSTS_ALLOWED)) {

				if (_log.isWarnEnabled()) {
					_log.warn(
						"Access denied for " + ioSession.getRemoteAddress());
				}

				ioSession.close(true);

				return;
			}

			LiferayLdapMessageContainer liferayLdapMessageContainer =
				new LiferayLdapMessageContainer();

			ioSession.setAttribute(
				LdapDecoder.MESSAGE_CONTAINER_ATTR,
				liferayLdapMessageContainer);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected LdapHandler getLdapHandler(Request request) {
		MessageTypeEnum messageTypeEnum = request.getType();

		if (messageTypeEnum == MessageTypeEnum.ABANDON_REQUEST) {
			return _abandonLdapHandler;
		}
		else if (messageTypeEnum == MessageTypeEnum.BIND_REQUEST) {
			return _bindLdapHandler;
		}
		else if (messageTypeEnum == MessageTypeEnum.COMPARE_REQUEST) {
			return _compareLdapHandler;
		}
		else if (messageTypeEnum == MessageTypeEnum.EXTENDED_REQUEST) {
			return _extendedLdapHandler;
		}
		else if (messageTypeEnum == MessageTypeEnum.SEARCH_REQUEST) {
			return _searchLdapHandler;
		}
		else if (messageTypeEnum == MessageTypeEnum.UNBIND_REQUEST) {
			return _unbindLdapHandler;
		}

		return null;
	}

	protected LdapHandlerContext getLdapHandlerContext(IoSession ioSession) {
		LdapHandlerContext ldapHandlerContext =
			(LdapHandlerContext)ioSession.getAttribute(
				LdapHandlerContext.class.getName());

		if (ldapHandlerContext == null) {
			synchronized (ioSession) {
				if (ldapHandlerContext == null) {
					ldapHandlerContext = new LdapHandlerContext();

					ioSession.setAttribute(
						LdapHandlerContext.class.getName(), ldapHandlerContext);
				}
			}
		}

		return ldapHandlerContext;
	}

	protected void setSessionAttributes(
		Response response, IoSession ioSession) {

		Map<Object, Object> sessionAttributes =
			(Map<Object, Object>)response.get(
				VLDAPConstants.SESSION_ATTRIBUTES);

		if (sessionAttributes == null) {
			return;
		}

		for (Map.Entry<Object, Object> entry : sessionAttributes.entrySet()) {
			ioSession.setAttribute(entry.getKey(), entry.getValue());
		}
	}

	protected void writeResponses(
		List<Response> responses, IoSession ioSession) {

		if (responses == null) {
			return;
		}

		for (Response response : responses) {
			setSessionAttributes(response, ioSession);

			ioSession.write(response);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DispatchIoHandler.class);

	private final LdapHandler _abandonLdapHandler = new AbandonLdapHandler();
	private final LdapHandler _bindLdapHandler = new BindLdapHandler();
	private final LdapHandler _compareLdapHandler = new CompareLdapHandler();
	private final LdapHandler _extendedLdapHandler = new ExtendedLdapHandler();
	private final LdapHandler _searchLdapHandler = new SearchLdapHandler();
	private final LdapHandler _unbindLdapHandler = new UnbindLdapHandler();
	private final LdapHandler _unwillingToPerformLdapHandler =
		new UnwillingToPerformLdapHandler();

}