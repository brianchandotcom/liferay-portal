/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

/**
 * The base implementation of {@link FriendlyURLMapper}.
 *
 * <p>
 * Typically not subclassed directly. {@link DefaultFriendlyURLMapper} and a
 * <code>friendly-url-routes.xml</code> file will handle the needs of most
 * portlets.
 * </p>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @author Connor McKay
 * @see    DefaultFriendlyURLMapper
 */
public abstract class BaseFriendlyURLMapper implements FriendlyURLMapper {

	@Override
	public String getMapping() {
		return _mapping;
	}

	@Override
	public String getPortletId() {
		return _portletId;
	}

	@Override
	public Router getRouter() {
		return router;
	}

	@Override
	public boolean isCheckMappingWithPrefix() {
		return _CHECK_MAPPING_WITH_PREFIX;
	}

	@Override
	public boolean isPortletInstanceable() {
		return _portletInstanceable;
	}

	@Override
	public void setMapping(String mapping) {
		_mapping = mapping;
	}

	@Override
	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	@Override
	public void setPortletInstanceable(boolean portletInstanceable) {
		_portletInstanceable = portletInstanceable;
	}

	@Override
	public void setRouter(Router router) {
		this.router = router;
	}

	/**
	 * Adds a default namespaced parameter of any type to the parameter map.
	 *
	 * <p>
	 * <b>Do not use this method with an instanceable portlet, it will not
	 * properly namespace parameter names.</b>
	 * </p>
	 *
	 * @param parameterMap the parameter map
	 * @param name the name of the parameter
	 * @param value the value of the parameter
	 * @see   #addParameter(Map, String, String)
	 */
	protected void addParameter(
		Map<String, String[]> parameterMap, String name, Object value) {

		addParameter(getNamespace(), parameterMap, name, String.valueOf(value));
	}

	/**
	 * Adds a default namespaced string parameter to the parameter map.
	 *
	 * <p>
	 * <b>Do not use this method with an instanceable portlet, it will not
	 * properly namespace parameter names.</b>
	 * </p>
	 *
	 * @param parameterMap the parameter map
	 * @param name the name of the parameter
	 * @param value the value of the parameter
	 * @see   #getNamespace()
	 */
	protected void addParameter(
		Map<String, String[]> parameterMap, String name, String value) {

		addParameter(getNamespace(), parameterMap, name, new String[] {value});
	}

	/**
	 * Adds a default namespaced string parameter to the parameter map.
	 *
	 * <p>
	 * <b>Do not use this method with an instanceable portlet, it will not
	 * properly namespace parameter names.</b>
	 * </p>
	 *
	 * @param parameterMap the parameter map
	 * @param name the name of the parameter
	 * @param values the values of the parameter
	 * @see   #getNamespace()
	 */
	protected void addParameter(
		Map<String, String[]> parameterMap, String name, String[] values) {

		addParameter(getNamespace(), parameterMap, name, values);
	}

	/**
	 * Adds a namespaced parameter of any type to the parameter map.
	 *
	 * @param namespace the namespace for portlet parameters. For instanceable
	 *        portlets this must include the instance ID.
	 * @param parameterMap the parameter map
	 * @param name space the namespace for portlet parameters. For instanceable
	 *        portlets this must include the instance ID.
	 * @param value the value of the parameter
	 * @see   #addParameter(String, Map, String, String)
	 */
	protected void addParameter(
		String namespace, Map<String, String[]> parameterMap, String name,
		Object value) {

		addParameter(
			namespace, parameterMap, name,
			new String[] {String.valueOf(value)});
	}

	/**
	 * Adds a namespaced string parameter to the parameter map.
	 *
	 * @param namespace the namespace for portlet parameters. For instanceable
	 *        portlets this must include the instance ID.
	 * @param parameterMap the parameter map
	 * @param name space the namespace for portlet parameters. For instanceable
	 *        portlets this must include the instance ID.
	 * @param value the value of the parameter
	 * @see   PortalUtil#getPortletNamespace(String)
	 * @see   DefaultFriendlyURLMapper#getPortletId(Map)
	 */
	protected void addParameter(
		String namespace, Map<String, String[]> parameterMap, String name,
		String value) {

		addParameter(namespace, parameterMap, name, new String[] {value});
	}

	/**
	 * Adds a namespaced string parameter to the parameter map.
	 *
	 * @param namespace the namespace for portlet parameters. For instanceable
	 *        portlets this must include the instance ID.
	 * @param parameterMap the parameter map
	 * @param name space the namespace for portlet parameters. For instanceable
	 *        portlets this must include the instance ID.
	 * @param values the values of the parameter
	 * @see   PortalUtil#getPortletNamespace(String)
	 * @see   DefaultFriendlyURLMapper#getPortletId(Map)
	 */
	protected void addParameter(
		String namespace, Map<String, String[]> parameterMap, String name,
		String[] values) {

		try {
			if (!PortalUtil.isReservedParameter(name)) {
				Map<String, String> prpIdentifiers =
					FriendlyURLMapperThreadLocal.getPRPIdentifiers();

				String identiferValue = prpIdentifiers.get(name);

				if (identiferValue != null) {
					name = identiferValue;
				}
				else if (Validator.isNotNull(namespace)) {
					name = namespace.concat(name);
				}
			}

			parameterMap.put(name, values);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	/**
	 * Returns the default namespace.
	 *
	 * <p>
	 * <b>Do not use this method with an instanceable portlet, it will not
	 * include the instance ID.</b>
	 * </p>
	 *
	 * @return the default namespace, not including the instance ID
	 * @see    PortalUtil#getPortletNamespace(String)
	 */
	protected String getNamespace() {
		return PortalUtil.getPortletNamespace(getPortletId());
	}

	protected Router router;

	private static final boolean _CHECK_MAPPING_WITH_PREFIX = true;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseFriendlyURLMapper.class);

	private String _mapping;
	private String _portletId;
	private boolean _portletInstanceable;

}