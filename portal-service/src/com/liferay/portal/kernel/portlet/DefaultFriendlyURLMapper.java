/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PortalUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

/**
 * The default friendly URL mapper to use with friendly URL routes.
 *
 * <p>
 * In most cases, to add friendly URL mapping to a portlet, simply set this
 * class as the friendly URL mapper in <code>liferay-portlet.xml</code>, and
 * write a <code>friendly-url-routes.xml</code> file.
 * </p>
 *
 * <p>
 * If you do need to extend this class, use {@link
 * com.liferay.util.bridges.alloy.AlloyFriendlyURLMapper} as a guide. The key
 * methods to override are {@link #buildPath(LiferayPortletURL)} and {@link
 * #populateParams(String, Map, Map)}.
 * </p>
 *
 * @author Connor McKay
 * @see    Router
 */
public class DefaultFriendlyURLMapper extends BaseFriendlyURLMapper {

	public DefaultFriendlyURLMapper() {
		defaultIgnoredParameters = new LinkedHashSet<String>();

		defaultIgnoredParameters.add("p_p_id");
		defaultIgnoredParameters.add("p_p_col_id");
		defaultIgnoredParameters.add("p_p_col_pos");
		defaultIgnoredParameters.add("p_p_col_count");

		defaultReservedParameters = new LinkedHashMap<String, String>();

		defaultReservedParameters.put("p_p_lifecycle", "0");
		defaultReservedParameters.put(
			"p_p_state", WindowState.NORMAL.toString());
		defaultReservedParameters.put("p_p_mode", PortletMode.VIEW.toString());
	}

	/**
	 * Adds a default ignored parameter.
	 *
	 * <p>
	 * A default ignored parameter will always be hidden in friendly URLs.
	 * </p>
	 *
	 * @param name the name of the parameter
	 */
	public void addDefaultIgnoredParameter(String name) {
		defaultIgnoredParameters.add(name);
	}

	/**
	 * Adds a default reserved parameter.
	 *
	 * <p>
	 * A default reserved parameter will be hidden in friendly URLs when it is
	 * set to its default value.
	 * </p>
	 *
	 * @param name the name of the parameter
	 * @param value the default value of the parameter
	 */
	public void addDefaultReservedParameter(String name, String value) {
		defaultReservedParameters.put(name, value);
	}

	@Override
	public String buildPath(LiferayPortletURL liferayPortletURL) {
		Map<String, String> routeParameters = new HashMap<String, String>();

		buildRouteParameters(liferayPortletURL, routeParameters);

		String friendlyURLPath = router.parametersToUrl(routeParameters);

		if (Validator.isNull(friendlyURLPath)) {
			return null;
		}

		addParametersIncludedInPath(liferayPortletURL, routeParameters);

		friendlyURLPath = StringPool.SLASH.concat(getMapping()).concat(
			friendlyURLPath);

		return friendlyURLPath;
	}

	/**
	 * Returns the default ignored parameters.
	 *
	 * @return the ignored parameter names
	 * @see    #addDefaultIgnoredParameter(String)
	 */
	public Set<String> getDefaultIgnoredParameters() {
		return defaultIgnoredParameters;
	}

	/**
	 * Returns the default reserved parameters.
	 *
	 * @return the default reserved parameter names and values
	 * @see    #addDefaultReservedParameter(String, String)
	 */
	public Map<String, String> getDefaultReservedParameters() {
		return defaultReservedParameters;
	}

	@Override
	public void populateParams(
		String friendlyURLPath, Map<String, String[]> parameterMap,
		Map<String, Object> requestContext) {

		friendlyURLPath = friendlyURLPath.substring(getMapping().length() + 1);

		if (friendlyURLPath.endsWith(StringPool.SLASH)) {
			friendlyURLPath = friendlyURLPath.substring(
				0, friendlyURLPath.length() - 1);
		}

		Map<String, String> routeParameters = new HashMap<String, String>();

		if (!router.urlToParameters(friendlyURLPath, routeParameters)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No route could be found to match URL " + friendlyURLPath);
			}

			return;
		}

		String portletId = getPortletId(routeParameters);

		if (Validator.isNull(portletId)) {
			return;
		}

		String namespace = PortalUtil.getPortletNamespace(portletId);

		addParameter(namespace, parameterMap, "p_p_id", portletId);

		populateParams(parameterMap, namespace, routeParameters);
	}

	/**
	 * Adds the parameters included in the path to the portlet URL.
	 *
	 * <p>
	 * Portlet URLs track which parameters are included in the friendly URL
	 * path. This method hides all the default ignored parameters, the
	 * parameters included in the path by the router, and the reserved
	 * parameters set to their defaults.
	 * </p>
	 *
	 * @param liferayPortletURL the portlet URL to which to add the parameters
	 *        included in the path
	 * @param routeParameters the parameter map populated by the router
	 * @see   com.liferay.portlet.PortletURLImpl#addParameterIncludedInPath(
	 *        String)
	 */
	protected void addParametersIncludedInPath(
		LiferayPortletURL liferayPortletURL,
		Map<String, String> routeParameters) {

		// Hide default ignored parameters

		for (String name : defaultIgnoredParameters) {
			liferayPortletURL.addParameterIncludedInPath(name);
		}

		// Hide application parameters removed by the router

		Map<String, String[]> portletURLParameters =
			liferayPortletURL.getParameterMap();

		for (String name : portletURLParameters.keySet()) {
			if (!routeParameters.containsKey(name)) {
				liferayPortletURL.addParameterIncludedInPath(name);
			}
		}

		// Hide reserved parameters removed by the router or set to the defaults

		Map<String, String> reservedParameters =
			liferayPortletURL.getReservedParameterMap();

		for (Map.Entry<String, String> entry : reservedParameters.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			if (!routeParameters.containsKey(key) ||
				value.equals(defaultReservedParameters.get(key))) {

				liferayPortletURL.addParameterIncludedInPath(key);
			}
		}
	}

	/**
	 * Builds the parameter map to be used by the router by copying parameters
	 * from the portlet URL.
	 *
	 * <p>
	 * This method also populates the special virtual parameters
	 * <code>p_p_id</code> and <code>instanceId</code> for instanceable
	 * portlets.
	 * </p>
	 *
	 * @param liferayPortletURL the portlet URL to copy parameters from
	 * @param routeParameters the parameter map to populate for use by the
	 *        router
	 */
	protected void buildRouteParameters(
		LiferayPortletURL liferayPortletURL,
		Map<String, String> routeParameters) {

		// Copy application parameters

		Map<String, String[]> portletURLParameters =
			liferayPortletURL.getParameterMap();

		for (Map.Entry<String, String[]> entry :
				portletURLParameters.entrySet()) {

			String[] values = entry.getValue();

			if (values.length > 0) {
				routeParameters.put(entry.getKey(), values[0]);
			}
		}

		// Populate virtual parameters for instanceable portlets

		if (isPortletInstanceable()) {
			String portletId = liferayPortletURL.getPortletId();

			routeParameters.put("p_p_id", portletId);

			if (Validator.isNotNull(portletId) &&
				PortletConstants.hasInstanceId(portletId)) {

				routeParameters.put(
					"instanceId", PortletConstants.getInstanceId(portletId));
			}
		}

		// Copy reserved parameters

		routeParameters.putAll(liferayPortletURL.getReservedParameterMap());
	}

	/**
	 * Returns the portlet ID, including the instance ID if applicable, from the
	 * parameter map.
	 *
	 * @param  routeParameters the parameter map. For an instanceable portlet,
	 *         this must contain either <code>p_p_id</code> or
	 *         <code>instanceId</code>.
	 * @return the portlet ID, including the instance ID if applicable, or
	 *         <code>null</code> if it cannot be determined
	 */
	protected String getPortletId(Map<String, String> routeParameters) {
		if (!isPortletInstanceable()) {
			return getPortletId();
		}

		String portletId = routeParameters.remove("p_p_id");

		if (Validator.isNotNull(portletId)) {
			return portletId;
		}

		String instanceId = routeParameters.remove("instanceId");

		if (Validator.isNull(instanceId)) {
			if (_log.isErrorEnabled()) {
				_log.error(
					"Either p_p_id or instanceId must be provided for an " +
						"instanceable portlet");
			}

			return null;
		}

		return PortletConstants.assemblePortletId(getPortletId(), instanceId);
	}

	/**
	 * Populates the parameter map using the parameters from the router and the
	 * default reserved parameters.
	 *
	 * @param parameterMap the parameter map to populate. This should be the map
	 *        passed to {@link #populateParams(String, Map, Map)} by {@link
	 *        com.liferay.portal.util.PortalImpl}.
	 * @param namespace the namespace to use for parameters added to
	 *        <code>parameterMap</code>
	 * @param routeParameters the parameter map populated by the router
	 */
	protected void populateParams(
		Map<String, String[]> parameterMap, String namespace,
		Map<String, String> routeParameters) {

		// Copy route parameters

		for (Map.Entry<String, String> entry : routeParameters.entrySet()) {
			addParameter(
				namespace, parameterMap, entry.getKey(), entry.getValue());
		}

		// Copy default reserved parameters if they are not already set

		for (Map.Entry<String, String> entry :
				defaultReservedParameters.entrySet()) {

			String key = entry.getKey();

			if (!parameterMap.containsKey(key)) {
				addParameter(namespace, parameterMap, key, entry.getValue());
			}
		}
	}

	protected Set<String> defaultIgnoredParameters;
	protected Map<String, String> defaultReservedParameters;

	private static Log _log = LogFactoryUtil.getLog(
		DefaultFriendlyURLMapper.class);

}