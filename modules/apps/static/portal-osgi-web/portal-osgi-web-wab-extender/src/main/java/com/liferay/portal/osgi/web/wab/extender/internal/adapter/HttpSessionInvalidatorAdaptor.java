package com.liferay.portal.osgi.web.wab.extender.internal.adapter;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.eclipse.equinox.http.servlet.session.HttpSessionInvalidator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

import com.liferay.portal.kernel.servlet.PortletSessionListenerManager;

@Component(immediate = true)
public class HttpSessionInvalidatorAdaptor {

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setHttpSessionInvalidator(HttpSessionInvalidator httpSessionInvalidator) {
		_httpSessionListener = new SessionInvalidatingHttpSessionListener(
			httpSessionInvalidator);

		PortletSessionListenerManager.addHttpSessionListener(
			_httpSessionListener);
	}

	protected void unsetHttpSessionInvalidator(HttpSessionInvalidator httpSessionInvalidator) {
		PortletSessionListenerManager.removeHttpSessionListener(
			_httpSessionListener);
	}

	private volatile HttpSessionListener _httpSessionListener;

	private static class SessionInvalidatingHttpSessionListener
		implements HttpSessionListener {

		public SessionInvalidatingHttpSessionListener(
			HttpSessionInvalidator httpSessionInvalidator) {

			_httpSessionInvalidator = httpSessionInvalidator;
		}

		@Override
		public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		}

		@Override
		public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
			HttpSession httpSession = httpSessionEvent.getSession();

			_httpSessionInvalidator.invalidate(httpSession.getId(), false);
		}

		private HttpSessionInvalidator _httpSessionInvalidator;

	}

}