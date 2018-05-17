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

package com.liferay.portal.osgi.web.wab.extender.internal.adapter;

import com.liferay.portal.kernel.servlet.PortletSessionListenerManager;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.eclipse.equinox.http.servlet.session.HttpSessionInvalidator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Raymond Augé
 */
@Component(immediate = true)
public class HttpSessionInvalidatorAdaptor {

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setHttpSessionInvalidator(
		HttpSessionInvalidator httpSessionInvalidator) {

		_httpSessionListener = new SessionInvalidatingHttpSessionListener(
			httpSessionInvalidator);

		PortletSessionListenerManager.addHttpSessionListener(
			_httpSessionListener);
	}

	protected void unsetHttpSessionInvalidator(
		HttpSessionInvalidator httpSessionInvalidator) {

		PortletSessionListenerManager.removeHttpSessionListener(
			_httpSessionListener);
	}

	private volatile HttpSessionListener _httpSessionListener;

	private static class SessionInvalidatingHttpSessionListener
		implements HttpSessionListener {

		@Override
		public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		}

		@Override
		public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
			HttpSession httpSession = httpSessionEvent.getSession();

			_httpSessionInvalidator.invalidate(httpSession.getId(), false);
		}

		private SessionInvalidatingHttpSessionListener(
			HttpSessionInvalidator httpSessionInvalidator) {

			_httpSessionInvalidator = httpSessionInvalidator;
		}

		private final HttpSessionInvalidator _httpSessionInvalidator;

	}

}