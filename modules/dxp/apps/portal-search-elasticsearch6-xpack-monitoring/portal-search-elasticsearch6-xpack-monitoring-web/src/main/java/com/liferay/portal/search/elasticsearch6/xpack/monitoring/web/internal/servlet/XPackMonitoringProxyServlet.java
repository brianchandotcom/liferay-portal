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

package com.liferay.portal.search.elasticsearch6.xpack.monitoring.web.internal.servlet;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.search.elasticsearch6.xpack.monitoring.web.internal.configuration.XPackMonitoringConfiguration;
import com.liferay.portal.search.elasticsearch6.xpack.monitoring.web.internal.constants.XPackMonitoringPortletKeys;
import com.liferay.portal.search.elasticsearch6.xpack.monitoring.web.internal.constants.XPackMonitoringProxyServletWebKeys;
import com.liferay.portal.search.elasticsearch6.xpack.monitoring.web.internal.constants.XPackMonitoringWebConstants;
import com.liferay.portal.search.elasticsearch6.xpack.monitoring.web.internal.servlet.display.context.ErrorDisplayContext;

import java.io.IOException;

import java.net.ConnectException;

import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpRequest;

import org.mitre.dsmiley.httpproxy.ProxyServlet;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Miguel Angelo Caldas Gallindo
 * @author Artur Aquino
 * @author André de Oliveira
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch6.xpack.monitoring.web.internal.configuration.XPackMonitoringConfiguration",
	immediate = true,
	property = {
		"osgi.http.whiteboard.context.select=portal-search-elasticsearch-xpack-monitoring",
		"osgi.http.whiteboard.servlet.name=com.liferay.portal.search.elasticsearch6.xpack.monitoring.web.internal.servlet.XPackMonitoringProxyServlet",
		"osgi.http.whiteboard.servlet.pattern=/" + XPackMonitoringWebConstants.SERVLET_PATH + "/*"
	},
	service = Servlet.class
)
public class XPackMonitoringProxyServlet extends ProxyServlet {

	@Override
	public String getServletInfo() {
		return "Liferay Portal Search Elasticsearch X-Pack Monitoring Proxy " +
			"Servlet";
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		replaceConfiguration(properties);
	}

	protected ErrorDisplayContext buildErrorDisplayContext(Exception e) {
		ErrorDisplayContext errorDisplayContext = new ErrorDisplayContext();

		errorDisplayContext.setException(e);

		boolean error = true;

		if (e instanceof ConnectException) {
			errorDisplayContext.setConnectExceptionAddress(
				_xPackMonitoringConfiguration.kibanaURL());
		}
		else if (e instanceof PrincipalException.MustBeAuthenticated) {
			error = false;
		}
		else if (e instanceof PrincipalException.MustHavePermission) {
			error = false;
		}

		if (error) {
			_log.error(e, e);
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return errorDisplayContext;
	}

	protected void checkPermission(HttpServletRequest httpServletRequest)
		throws Exception {

		User user = portal.getUser(httpServletRequest);

		if (user == null) {
			throw new PrincipalException.MustBeAuthenticated(StringPool.BLANK);
		}

		PermissionChecker permissionChecker = permissionCheckerFactory.create(
			user);

		boolean hasPermission = permissionChecker.hasPermission(
			GroupThreadLocal.getGroupId(),
			XPackMonitoringPortletKeys.MONITORING,
			XPackMonitoringPortletKeys.MONITORING, ActionKeys.VIEW);

		if (!hasPermission) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, XPackMonitoringPortletKeys.MONITORING, 0,
				ActionKeys.VIEW);
		}
	}

	@Override
	protected void copyRequestHeaders(
		HttpServletRequest servletRequest, HttpRequest proxyRequest) {

		super.copyRequestHeaders(servletRequest, proxyRequest);

		proxyRequest.addHeader(
			HttpHeaders.AUTHORIZATION, getShieldAuthorization());

		proxyRequest.removeHeaders(HttpHeaders.ACCEPT_ENCODING);
	}

	@Override
	protected String getConfigParam(String key) {
		if (key.equals(ProxyServlet.P_TARGET_URI)) {
			return GetterUtil.getString(
				_xPackMonitoringConfiguration.kibanaURL());
		}

		if (key.equals(ProxyServlet.P_LOG)) {
			return String.valueOf(
				_xPackMonitoringConfiguration.proxyServletLogEnable());
		}

		return super.getConfigParam(key);
	}

	protected String getShieldAuthorization() {
		String userName = GetterUtil.getString(
			_xPackMonitoringConfiguration.kibanaUserName());
		String password = GetterUtil.getString(
			_xPackMonitoringConfiguration.kibanaPassword());

		String authorization = userName + ":" + password;

		return "Basic " + Base64.encode(authorization.getBytes());
	}

	@Modified
	protected void modified(Map<String, Object> properties) throws Exception {
		replaceConfiguration(properties);

		init();
	}

	protected void replaceConfiguration(Map<String, Object> properties) {
		_xPackMonitoringConfiguration = ConfigurableUtil.createConfigurable(
			XPackMonitoringConfiguration.class, properties);
	}

	protected void sendError(
			Exception e, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		httpServletRequest.setAttribute(
			XPackMonitoringProxyServletWebKeys.ERROR_DISPLAY_CONTEXT,
			buildErrorDisplayContext(e));

		RequestDispatcher requestDispatcher =
			httpServletRequest.getRequestDispatcher(
				httpServletRequest.getContextPath() + "/servlet/error.jsp");

		requestDispatcher.include(httpServletRequest, httpServletResponse);
	}

	@Override
	protected void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		try {
			checkPermission(httpServletRequest);

			super.service(httpServletRequest, httpServletResponse);
		}
		catch (Exception e) {
			sendError(e, httpServletRequest, httpServletResponse);
		}
	}

	@Reference
	protected PermissionCheckerFactory permissionCheckerFactory;

	@Reference
	protected Portal portal;

	private static final Log _log = LogFactoryUtil.getLog(
		XPackMonitoringProxyServlet.class);

	private XPackMonitoringConfiguration _xPackMonitoringConfiguration;

}