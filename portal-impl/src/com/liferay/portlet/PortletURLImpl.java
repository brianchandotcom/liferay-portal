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

package com.liferay.portlet;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletModeFactory;
import com.liferay.portal.kernel.portlet.WindowStateFactory;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.model.impl.VirtualLayout;
import com.liferay.portal.security.lang.DoPrivilegedUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.social.util.FacebookUtil;
import com.liferay.util.Encryptor;
import com.liferay.util.EncryptorException;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import java.security.Key;
import java.security.PrivilegedAction;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Connor McKay
 */
public class PortletURLImpl
	implements LiferayPortletURL, PortletURL, ResourceURL, Serializable {

	public PortletURLImpl(
		HttpServletRequest request, String portletId, Layout layout,
		String lifecycle) {

		this(request, portletId, null, layout.getPlid(), lifecycle);

		_layout = layout;
	}

	public PortletURLImpl(
		HttpServletRequest request, String portletId, long plid,
		String lifecycle) {

		this(request, portletId, null, plid, lifecycle);
	}

	public PortletURLImpl(
		PortletRequest portletRequest, String portletId, Layout layout,
		String lifecycle) {

		this(
			PortalUtil.getHttpServletRequest(portletRequest), portletId,
			portletRequest, layout.getPlid(), lifecycle);

		_layout = layout;
	}

	public PortletURLImpl(
		PortletRequest portletRequest, String portletId, long plid,
		String lifecycle) {

		this(
			PortalUtil.getHttpServletRequest(portletRequest), portletId,
			portletRequest, plid, lifecycle);
	}

	@Override
	public void addParameterIncludedInPath(String name) {
		_parametersIncludedInPath.add(name);
	}

	@Override
	public void addProperty(String key, String value) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public String getCacheability() {
		return _cacheability;
	}

	public HttpServletRequest getHttpServletRequest() {
		return _request;
	}

	public Layout getLayout() {
		if (_layout == null) {
			try {
				if (_plid > 0) {
					_layout = LayoutLocalServiceUtil.getLayout(_plid);
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Layout cannot be found for " + _plid);
				}
			}
		}

		return _layout;
	}

	public String getLayoutFriendlyURL() {
		return _layoutFriendlyURL;
	}

	@Override
	public String getLifecycle() {
		return _lifecycle;
	}

	public String getNamespace() {
		if (_namespace == null) {
			_namespace = PortalUtil.getPortletNamespace(_portletId);
		}

		return _namespace;
	}

	@Override
	public String getParameter(String name) {
		String[] values = _params.get(name);

		if (ArrayUtil.isNotEmpty(values)) {
			return values[0];
		}
		else {
			return null;
		}
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return _params;
	}

	@Override
	public Set<String> getParametersIncludedInPath() {
		return _parametersIncludedInPath;
	}

	@Override
	public long getPlid() {
		return _plid;
	}

	public Portlet getPortlet() {
		if (_portlet == null) {
			try {
				_portlet = PortletLocalServiceUtil.getPortletById(
					PortalUtil.getCompanyId(_request), _portletId);
			}
			catch (SystemException se) {
				_log.error(se.getMessage());
			}
		}

		return _portlet;
	}

	public String getPortletFriendlyURLPath() {
		String portletFriendlyURLPath = null;

		Portlet portlet = getPortlet();

		if (portlet != null) {
			if (portlet.isUndeployedPortlet()) {
				return portletFriendlyURLPath;
			}

			FriendlyURLMapper friendlyURLMapper =
				portlet.getFriendlyURLMapperInstance();

			if (friendlyURLMapper != null) {
				portletFriendlyURLPath = friendlyURLMapper.buildPath(this);

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Portlet friendly URL path " + portletFriendlyURLPath);
				}
			}
		}

		return portletFriendlyURLPath;
	}

	@Override
	public String getPortletId() {
		return _portletId;
	}

	@Override
	public PortletMode getPortletMode() {
		if (_portletModeString == null) {
			return null;
		}

		return PortletModeFactory.getPortletMode(_portletModeString);
	}

	public PortletRequest getPortletRequest() {
		return _portletRequest;
	}

	@Override
	public Set<String> getRemovedParameterNames() {
		return _removedParameterNames;
	}

	@Override
	public Map<String, String> getReservedParameterMap() {
		if (_reservedParameters != null) {
			return _reservedParameters;
		}

		_reservedParameters = new LinkedHashMap<>();

		_reservedParameters.put("p_p_id", _portletId);

		if (_lifecycle.equals(PortletRequest.ACTION_PHASE)) {
			_reservedParameters.put("p_p_lifecycle", "1");
		}
		else if (_lifecycle.equals(PortletRequest.RENDER_PHASE)) {
			_reservedParameters.put("p_p_lifecycle", "0");
		}
		else if (_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
			_reservedParameters.put("p_p_lifecycle", "2");
		}

		if (_windowStateString != null) {
			_reservedParameters.put("p_p_state", _windowStateString);
		}

		if (_windowStateRestoreCurrentView) {
			_reservedParameters.put("p_p_state_rcv", "1");
		}

		if (_portletModeString != null) {
			_reservedParameters.put("p_p_mode", _portletModeString);
		}

		if (_resourceID != null) {
			_reservedParameters.put("p_p_resource_id", _resourceID);
		}

		if (_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
			_reservedParameters.put("p_p_cacheability", _cacheability);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		if (Validator.isNotNull(portletDisplay.getColumnId())) {
			_reservedParameters.put("p_p_col_id", portletDisplay.getColumnId());
		}

		if (portletDisplay.getColumnPos() > 0) {
			_reservedParameters.put(
				"p_p_col_pos", String.valueOf(portletDisplay.getColumnPos()));
		}

		if (portletDisplay.getColumnCount() > 0) {
			_reservedParameters.put(
				"p_p_col_count",
				String.valueOf(portletDisplay.getColumnCount()));
		}

		_reservedParameters = Collections.unmodifiableMap(_reservedParameters);

		return _reservedParameters;
	}

	@Override
	public String getResourceID() {
		return _resourceID;
	}

	@Override
	public WindowState getWindowState() {
		if (_windowStateString == null) {
			return null;
		}

		return WindowStateFactory.getWindowState(_windowStateString);
	}

	@Override
	public boolean isAnchor() {
		return _anchor;
	}

	@Override
	public boolean isCopyCurrentRenderParameters() {
		return _copyCurrentRenderParameters;
	}

	@Override
	public boolean isEncrypt() {
		return _encrypt;
	}

	@Override
	public boolean isEscapeXml() {
		return _escapeXml;
	}

	@Override
	public boolean isParameterIncludedInPath(String name) {
		if (_parametersIncludedInPath.contains(name)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isSecure() {
		return _secure;
	}

	@Override
	public void removePublicRenderParameter(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		Portlet portlet = getPortlet();

		if (portlet == null) {
			return;
		}

		PublicRenderParameter publicRenderParameter =
			portlet.getPublicRenderParameter(name);

		if (publicRenderParameter == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Public parameter " + name + "does not exist");
			}

			return;
		}

		QName qName = publicRenderParameter.getQName();

		_removePublicRenderParameters.put(
			PortletQNameUtil.getRemovePublicRenderParameterName(qName),
			new String[] {"1"});
	}

	@Override
	public void setAnchor(boolean anchor) {
		_anchor = anchor;

		clearCache();
	}

	@Override
	public void setCacheability(String cacheability) {
		if (cacheability == null) {
			throw new IllegalArgumentException("Cacheability is null");
		}

		if (!cacheability.equals(FULL) && !cacheability.equals(PORTLET) &&
			!cacheability.equals(PAGE)) {

			throw new IllegalArgumentException(
				"Cacheability " + cacheability + " is not " + FULL + ", " +
					PORTLET + ", or " + PAGE);
		}

		if (_portletRequest instanceof ResourceRequest) {
			ResourceRequest resourceRequest = (ResourceRequest)_portletRequest;

			String parentCacheability = resourceRequest.getCacheability();

			if (parentCacheability.equals(FULL)) {
				if (!cacheability.equals(FULL)) {
					throw new IllegalStateException(
						"Unable to set a weaker cacheability " + cacheability);
				}
			}
			else if (parentCacheability.equals(PORTLET)) {
				if (!cacheability.equals(FULL) &&
					!cacheability.equals(PORTLET)) {

					throw new IllegalStateException(
						"Unable to set a weaker cacheability " + cacheability);
				}
			}
		}

		_cacheability = cacheability;

		clearCache();
	}

	@Override
	public void setCopyCurrentRenderParameters(
		boolean copyCurrentRenderParameters) {

		_copyCurrentRenderParameters = copyCurrentRenderParameters;
	}

	@Override
	public void setDoAsGroupId(long doAsGroupId) {
		_doAsGroupId = doAsGroupId;

		clearCache();
	}

	@Override
	public void setDoAsUserId(long doAsUserId) {
		_doAsUserId = doAsUserId;

		clearCache();
	}

	@Override
	public void setDoAsUserLanguageId(String doAsUserLanguageId) {
		_doAsUserLanguageId = doAsUserLanguageId;

		clearCache();
	}

	@Override
	public void setEncrypt(boolean encrypt) {
		_encrypt = encrypt;

		clearCache();
	}

	@Override
	public void setEscapeXml(boolean escapeXml) {
		_escapeXml = escapeXml;

		clearCache();
	}

	@Override
	public void setLifecycle(String lifecycle) {
		_lifecycle = lifecycle;

		clearCache();
	}

	@Override
	public void setParameter(String name, String value) {
		setParameter(name, value, PropsValues.PORTLET_URL_APPEND_PARAMETERS);
	}

	@Override
	public void setParameter(String name, String value, boolean append) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (value == null) {
			removeParameter(name);

			return;
		}

		setParameter(name, new String[] {value}, append);
	}

	@Override
	public void setParameter(String name, String[] values) {
		setParameter(name, values, PropsValues.PORTLET_URL_APPEND_PARAMETERS);
	}

	@Override
	public void setParameter(String name, String[] values, boolean append) {
		if ((name == null) || (values == null)) {
			throw new IllegalArgumentException();
		}

		for (String value : values) {
			if (value == null) {
				throw new IllegalArgumentException();
			}
		}

		if (!append) {
			_params.put(name, values);
		}
		else {
			String[] oldValues = _params.get(name);

			if (oldValues == null) {
				_params.put(name, values);
			}
			else {
				String[] newValues = ArrayUtil.append(oldValues, values);

				_params.put(name, newValues);
			}
		}

		clearCache();
	}

	@Override
	public void setParameters(Map<String, String[]> params) {
		if (params == null) {
			throw new IllegalArgumentException();
		}
		else {
			Map<String, String[]> newParams = new LinkedHashMap<>();

			for (Map.Entry<String, String[]> entry : params.entrySet()) {
				try {
					String key = entry.getKey();
					String[] value = entry.getValue();

					if (key == null) {
						throw new IllegalArgumentException();
					}
					else if (value == null) {
						throw new IllegalArgumentException();
					}

					newParams.put(key, value);
				}
				catch (ClassCastException cce) {
					throw new IllegalArgumentException(cce);
				}
			}

			_params = newParams;
		}

		clearCache();
	}

	@Override
	public void setPlid(long plid) {
		_plid = plid;

		clearCache();
	}

	@Override
	public void setPortletId(String portletId) {
		_portletId = portletId;

		clearCache();
	}

	@Override
	public void setPortletMode(PortletMode portletMode)
		throws PortletModeException {

		if (_portletRequest != null) {
			Portlet portlet = getPortlet();

			if ((portlet != null) &&
				!portlet.hasPortletMode(
					_portletRequest.getResponseContentType(), portletMode)) {

				throw new PortletModeException(
					portletMode.toString(), portletMode);
			}
		}

		_portletModeString = portletMode.toString();

		clearCache();
	}

	public void setPortletMode(String portletMode) throws PortletModeException {
		setPortletMode(PortletModeFactory.getPortletMode(portletMode));
	}

	@Override
	public void setProperty(String key, String value) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void setRefererGroupId(long refererGroupId) {
		_refererGroupId = refererGroupId;

		clearCache();
	}

	@Override
	public void setRefererPlid(long refererPlid) {
		_refererPlid = refererPlid;

		clearCache();
	}

	@Override
	public void setRemovedParameterNames(Set<String> removedParameterNames) {
		_removedParameterNames = removedParameterNames;

		clearCache();
	}

	@Override
	public void setResourceID(String resourceID) {
		_resourceID = resourceID;
	}

	@Override
	public void setSecure(boolean secure) {
		_secure = secure;

		clearCache();
	}

	public void setWindowState(String windowState) throws WindowStateException {
		setWindowState(WindowStateFactory.getWindowState(windowState));
	}

	@Override
	public void setWindowState(WindowState windowState)
		throws WindowStateException {

		if (_portletRequest != null) {
			if (!_portletRequest.isWindowStateAllowed(windowState)) {
				throw new WindowStateException(
					windowState.toString(), windowState);
			}
		}

		if (LiferayWindowState.isWindowStatePreserved(
				getWindowState(), windowState)) {

			_windowStateString = windowState.toString();
		}

		clearCache();
	}

	public void setWindowStateRestoreCurrentView(
		boolean windowStateRestoreCurrentView) {

		_windowStateRestoreCurrentView = windowStateRestoreCurrentView;
	}

	@Override
	public String toString() {
		if (_toString != null) {
			return _toString;
		}

		_toString = DoPrivilegedUtil.wrap(new ToStringPrivilegedAction());

		return _toString;
	}

	@Override
	public void write(Writer writer) throws IOException {
		write(writer, _escapeXml);
	}

	@Override
	public void write(Writer writer, boolean escapeXml) throws IOException {
		String toString = toString();

		if (escapeXml && !_escapeXml) {
			toString = HtmlUtil.escape(toString);
		}

		writer.write(toString);
	}

	protected PortletURLImpl(
		HttpServletRequest request, String portletId,
		PortletRequest portletRequest, long plid, String lifecycle) {

		_request = request;
		_portletId = portletId;
		_portletRequest = portletRequest;
		_plid = plid;
		_lifecycle = lifecycle;
		_parametersIncludedInPath = new LinkedHashSet<>();
		_params = new LinkedHashMap<>();
		_removePublicRenderParameters = new LinkedHashMap<>();
		_secure = PortalUtil.isSecure(request);
		_wsrp = ParamUtil.getBoolean(request, "wsrp");

		Portlet portlet = getPortlet();

		if (portlet != null) {
			Set<String> autopropagatedParameters =
				portlet.getAutopropagatedParameters();

			for (String autopropagatedParameter : autopropagatedParameters) {
				if (PortalUtil.isReservedParameter(autopropagatedParameter)) {
					continue;
				}

				String value = request.getParameter(autopropagatedParameter);

				if (value != null) {
					setParameter(autopropagatedParameter, value);
				}
			}

			PortletApp portletApp = portlet.getPortletApp();

			_escapeXml = MapUtil.getBoolean(
				portletApp.getContainerRuntimeOptions(),
				LiferayPortletConfig.RUNTIME_OPTION_ESCAPE_XML,
				PropsValues.PORTLET_URL_ESCAPE_XML);
		}

		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		if ((layout != null) && (layout.getPlid() == _plid) &&
			(layout instanceof VirtualLayout)) {

			_layout = layout;
		}
	}

	protected void addPortalAuthToken(StringBundler sb, Key key) {
		AuthTokenUtil.addCSRFToken(_request, this);
	}

	protected void addPortletAuthToken(StringBundler sb, Key key) {
		AuthTokenUtil.addPortletInvocationToken(_request, this);
	}

	protected void clearCache() {
		_reservedParameters = null;
		_toString = null;
	}

	protected String generateToString() {
		StringBundler sb = new StringBundler(64);

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to generate string because theme display is null");
			}

			return StringPool.BLANK;
		}

		try {
			if (_layoutFriendlyURL == null) {
				Layout layout = getLayout();

				if (layout != null) {
					_layoutFriendlyURL = GetterUtil.getString(
						PortalUtil.getLayoutFriendlyURL(layout, themeDisplay));

					if (_secure) {
						_layoutFriendlyURL = HttpUtil.protocolize(
							_layoutFriendlyURL,
							PropsValues.WEB_SERVER_HTTPS_PORT, true);
					}
				}
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		Key key = null;

		try {
			if (_encrypt) {
				Company company = PortalUtil.getCompany(_request);

				key = company.getKeyObj();
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		if (Validator.isNull(_layoutFriendlyURL)) {
			sb.append(PortalUtil.getPortalURL(_request, _secure));
			sb.append(themeDisplay.getPathMain());
			sb.append("/portal/layout?");

			addPortalAuthToken(sb, key);

			sb.append("p_l_id");
			sb.append(StringPool.EQUAL);
			sb.append(processValue(key, _plid));
			sb.append(StringPool.AMPERSAND);
		}
		else {
			if (themeDisplay.isFacebook()) {
				sb.append(FacebookUtil.FACEBOOK_APPS_URL);
				sb.append(themeDisplay.getFacebookCanvasPageURL());
			}
			else {

				// A virtual host URL will contain the complete path. Do not
				// append the portal URL if the virtual host URL starts with
				// "http://" or "https://".

				if (!_layoutFriendlyURL.startsWith(Http.HTTP_WITH_SLASH) &&
					!_layoutFriendlyURL.startsWith(Http.HTTPS_WITH_SLASH)) {

					sb.append(PortalUtil.getPortalURL(_request, _secure));
				}

				sb.append(_layoutFriendlyURL);
			}

			String friendlyURLPath = getPortletFriendlyURLPath();

			if (Validator.isNotNull(friendlyURLPath)) {
				if (themeDisplay.isFacebook()) {
					int pos = friendlyURLPath.indexOf(CharPool.SLASH, 1);

					if (pos != -1) {
						sb.append(friendlyURLPath.substring(pos));
					}
					else {
						sb.append(friendlyURLPath);
					}
				}
				else {
					sb.append("/-");
					sb.append(friendlyURLPath);
				}
			}

			sb.append(StringPool.QUESTION);

			addPortalAuthToken(sb, key);
		}

		addPortletAuthToken(sb, key);

		for (Map.Entry<String, String> entry :
				getReservedParameterMap().entrySet()) {

			String name = entry.getKey();

			if (!isParameterIncludedInPath(name)) {
				sb.append(HttpUtil.encodeURL(name));
				sb.append(StringPool.EQUAL);
				sb.append(processValue(key, entry.getValue()));
				sb.append(StringPool.AMPERSAND);
			}
		}

		if (_doAsUserId > 0) {
			try {
				Company company = PortalUtil.getCompany(_request);

				sb.append("doAsUserId");
				sb.append(StringPool.EQUAL);
				sb.append(processValue(company.getKeyObj(), _doAsUserId));
				sb.append(StringPool.AMPERSAND);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
		else {
			String doAsUserId = themeDisplay.getDoAsUserId();

			if (Validator.isNotNull(doAsUserId)) {
				sb.append("doAsUserId");
				sb.append(StringPool.EQUAL);
				sb.append(processValue(key, doAsUserId));
				sb.append(StringPool.AMPERSAND);
			}
		}

		String doAsUserLanguageId = _doAsUserLanguageId;

		if (Validator.isNull(doAsUserLanguageId)) {
			doAsUserLanguageId = themeDisplay.getDoAsUserLanguageId();
		}

		if (Validator.isNotNull(doAsUserLanguageId)) {
			sb.append("doAsUserLanguageId");
			sb.append(StringPool.EQUAL);
			sb.append(processValue(key, doAsUserLanguageId));
			sb.append(StringPool.AMPERSAND);
		}

		long doAsGroupId = _doAsGroupId;

		if (doAsGroupId <= 0) {
			doAsGroupId = themeDisplay.getDoAsGroupId();
		}

		if (doAsGroupId > 0) {
			sb.append("doAsGroupId");
			sb.append(StringPool.EQUAL);
			sb.append(processValue(key, doAsGroupId));
			sb.append(StringPool.AMPERSAND);
		}

		long refererGroupId = _refererGroupId;

		if (refererGroupId <= 0) {
			refererGroupId = themeDisplay.getRefererGroupId();
		}

		if (refererGroupId > 0) {
			sb.append("refererGroupId");
			sb.append(StringPool.EQUAL);
			sb.append(processValue(key, refererGroupId));
			sb.append(StringPool.AMPERSAND);
		}

		long refererPlid = _refererPlid;

		if (refererPlid <= 0) {
			refererPlid = themeDisplay.getRefererPlid();
		}

		if (refererPlid > 0) {
			sb.append("refererPlid");
			sb.append(StringPool.EQUAL);
			sb.append(processValue(key, refererPlid));
			sb.append(StringPool.AMPERSAND);
		}

		for (Map.Entry<String, String[]> entry :
				_removePublicRenderParameters.entrySet()) {

			String lastString = sb.stringAt(sb.index() - 1);

			if (lastString.charAt(lastString.length() - 1) !=
					CharPool.AMPERSAND) {

				sb.append(StringPool.AMPERSAND);
			}

			sb.append(HttpUtil.encodeURL(entry.getKey()));
			sb.append(StringPool.EQUAL);
			sb.append(processValue(key, entry.getValue()[0]));
			sb.append(StringPool.AMPERSAND);
		}

		if (_copyCurrentRenderParameters) {
			mergeRenderParameters();
		}

		int previousSbIndex = sb.index();

		for (Map.Entry<String, String[]> entry : _params.entrySet()) {
			String name = entry.getKey();
			String[] values = entry.getValue();

			if (isParameterIncludedInPath(name)) {
				continue;
			}

			String publicRenderParameterName = getPublicRenderParameterName(
				name);

			if (Validator.isNotNull(publicRenderParameterName)) {
				name = publicRenderParameterName;
			}

			name = HttpUtil.encodeURL(prependNamespace(name));

			for (String value : values) {
				sb.append(name);
				sb.append(StringPool.EQUAL);
				sb.append(processValue(key, value));
				sb.append(StringPool.AMPERSAND);
			}
		}

		if (sb.index() > previousSbIndex) {
			sb.setIndex(sb.index() - 1);
		}

		if (_encrypt) {
			sb.append(StringPool.AMPERSAND);
			sb.append(WebKeys.ENCRYPT);
			sb.append("=1");
		}

		if (PropsValues.PORTLET_URL_ANCHOR_ENABLE) {
			if (_anchor && (_windowStateString != null) &&
				!_windowStateString.equals(WindowState.MAXIMIZED.toString()) &&
				!_windowStateString.equals(
					LiferayWindowState.EXCLUSIVE.toString()) &&
				!_windowStateString.equals(
					LiferayWindowState.POP_UP.toString())) {

				String lastString = sb.stringAt(sb.index() - 1);

				char lastChar = lastString.charAt(lastString.length() - 1);

				if ((lastChar != CharPool.AMPERSAND) &&
					(lastChar != CharPool.QUESTION)) {

					sb.append(StringPool.AMPERSAND);
				}

				sb.append("#p_");
				sb.append(HttpUtil.encodeURL(_portletId));
			}
		}

		String result = sb.toString();

		if (result.endsWith(StringPool.AMPERSAND) ||
			result.endsWith(StringPool.QUESTION)) {

			result = result.substring(0, result.length() - 1);
		}

		if (themeDisplay.isFacebook()) {

			// Facebook requires the path portion of the URL to end with a slash

			int pos = result.indexOf(CharPool.QUESTION);

			if (pos == -1) {
				if (!result.endsWith(StringPool.SLASH)) {
					result += StringPool.SLASH;
				}
			}
			else {
				String path = result.substring(0, pos);

				if (!result.endsWith(StringPool.SLASH)) {
					result = path + StringPool.SLASH + result.substring(pos);
				}
			}
		}
		else if (!CookieKeys.hasSessionId(_request)) {
			result = PortalUtil.getURLWithSessionId(
				result, _request.getSession().getId());
		}

		if (_escapeXml) {
			result = HtmlUtil.escape(result);
		}

		if (result.length() > Http.URL_MAXIMUM_LENGTH) {
			result = HttpUtil.shortenURL(result, 2);
		}

		return result;
	}

	protected String generateWSRPToString() {
		StringBundler sb = new StringBundler("wsrp_rewrite?");

		sb.append("wsrp-urlType");
		sb.append(StringPool.EQUAL);

		if (_lifecycle.equals(PortletRequest.ACTION_PHASE)) {
			sb.append(HttpUtil.encodeURL("blockingAction"));
		}
		else if (_lifecycle.equals(PortletRequest.RENDER_PHASE)) {
			sb.append(HttpUtil.encodeURL("render"));
		}
		else if (_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
			sb.append(HttpUtil.encodeURL("resource"));
		}

		sb.append(StringPool.AMPERSAND);

		if (_windowStateString != null) {
			sb.append("wsrp-windowState");
			sb.append(StringPool.EQUAL);
			sb.append(HttpUtil.encodeURL("wsrp:" + _windowStateString));
			sb.append(StringPool.AMPERSAND);
		}

		if (_portletModeString != null) {
			sb.append("wsrp-mode");
			sb.append(StringPool.EQUAL);
			sb.append(HttpUtil.encodeURL("wsrp:" + _portletModeString));
			sb.append(StringPool.AMPERSAND);
		}

		if (_resourceID != null) {
			sb.append("wsrp-resourceID");
			sb.append(StringPool.EQUAL);
			sb.append(HttpUtil.encodeURL(_resourceID));
			sb.append(StringPool.AMPERSAND);
		}

		if (_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
			sb.append("wsrp-resourceCacheability");
			sb.append(StringPool.EQUAL);
			sb.append(HttpUtil.encodeURL(_cacheability));
			sb.append(StringPool.AMPERSAND);
		}

		if (PropsValues.PORTLET_URL_ANCHOR_ENABLE) {
			if (_anchor && (_windowStateString != null) &&
				!_windowStateString.equals(WindowState.MAXIMIZED.toString()) &&
				!_windowStateString.equals(
					LiferayWindowState.EXCLUSIVE.toString()) &&
				!_windowStateString.equals(
					LiferayWindowState.POP_UP.toString())) {

				sb.append("wsrp-fragmentID");
				sb.append(StringPool.EQUAL);
				sb.append("#p_");
				sb.append(HttpUtil.encodeURL(_portletId));
				sb.append(StringPool.AMPERSAND);
			}
		}

		if (_copyCurrentRenderParameters) {
			mergeRenderParameters();
		}

		StringBundler parameterSb = new StringBundler();

		int previousSbIndex = sb.index();

		for (Map.Entry<String, String[]> entry : _params.entrySet()) {
			String name = entry.getKey();
			String[] values = entry.getValue();

			if (isParameterIncludedInPath(name)) {
				continue;
			}

			String publicRenderParameterName = getPublicRenderParameterName(
				name);

			if (Validator.isNotNull(publicRenderParameterName)) {
				name = publicRenderParameterName;
			}

			name = HttpUtil.encodeURL(prependNamespace(name));

			for (String value : values) {
				parameterSb.append(name);
				parameterSb.append(StringPool.EQUAL);
				parameterSb.append(HttpUtil.encodeURL(value));
				parameterSb.append(StringPool.AMPERSAND);
			}
		}

		if (sb.index() > previousSbIndex) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("wsrp-navigationalState");
		sb.append(StringPool.EQUAL);

		byte[] parameterBytes = null;

		try {
			String parameterString = parameterSb.toString();

			parameterBytes = parameterString.getBytes(StringPool.UTF8);
		}
		catch (UnsupportedEncodingException uee) {
			if (_log.isWarnEnabled()) {
				_log.warn(uee, uee);
			}
		}

		String navigationalState = Base64.toURLSafe(
			Base64.encode(parameterBytes));

		sb.append(navigationalState);

		sb.append("/wsrp_rewrite");

		return sb.toString();
	}

	protected String getPublicRenderParameterName(String name) {
		Portlet portlet = getPortlet();

		String publicRenderParameterName = null;

		if (portlet != null) {
			PublicRenderParameter publicRenderParameter =
				portlet.getPublicRenderParameter(name);

			if (publicRenderParameter != null) {
				QName qName = publicRenderParameter.getQName();

				publicRenderParameterName =
					PortletQNameUtil.getPublicRenderParameterName(qName);
			}
		}

		return publicRenderParameterName;
	}

	protected boolean isBlankValue(String[] value) {
		if ((value != null) && (value.length == 1) &&
			value[0].equals(StringPool.BLANK)) {

			return true;
		}
		else {
			return false;
		}
	}

	protected void mergeRenderParameters() {
		String namespace = getNamespace();

		Layout layout = getLayout();

		Map<String, String[]> renderParameters = RenderParametersPool.get(
			_request, layout.getPlid(), getPortlet().getPortletId());

		if (renderParameters == null) {
			return;
		}

		for (Map.Entry<String, String[]> entry : renderParameters.entrySet()) {
			String name = entry.getKey();

			if (name.contains(namespace)) {
				name = name.substring(namespace.length());
			}

			if (!_lifecycle.equals(PortletRequest.RESOURCE_PHASE) &&
				(_removedParameterNames != null) &&
				_removedParameterNames.contains(name)) {

				continue;
			}

			String[] oldValues = entry.getValue();
			String[] newValues = _params.get(name);

			if (newValues == null) {
				_params.put(name, oldValues);
			}
			else if (isBlankValue(newValues)) {
				_params.remove(name);
			}
			else {
				newValues = ArrayUtil.append(newValues, oldValues);

				_params.put(name, newValues);
			}
		}
	}

	protected String prependNamespace(String name) {
		String namespace = getNamespace();

		if (!name.startsWith(PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE) &&
			!name.startsWith(namespace) &&
			!PortalUtil.isReservedParameter(name)) {

			return namespace.concat(name);
		}

		return name;
	}

	protected String processValue(Key key, int value) {
		return processValue(key, String.valueOf(value));
	}

	protected String processValue(Key key, long value) {
		return processValue(key, String.valueOf(value));
	}

	protected String processValue(Key key, String value) {
		if (key == null) {
			return HttpUtil.encodeURL(value);
		}

		try {
			return HttpUtil.encodeURL(Encryptor.encrypt(key, value));
		}
		catch (EncryptorException ee) {
			return value;
		}
	}

	protected void removeParameter(String name) {
		if (_params.containsKey(name)) {
			_params.remove(name);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(PortletURLImpl.class);

	private boolean _anchor = true;
	private String _cacheability = ResourceURL.PAGE;
	private boolean _copyCurrentRenderParameters;
	private long _doAsGroupId;
	private long _doAsUserId;
	private String _doAsUserLanguageId;
	private boolean _encrypt;
	private boolean _escapeXml = PropsValues.PORTLET_URL_ESCAPE_XML;
	private Layout _layout;
	private String _layoutFriendlyURL;
	private String _lifecycle;
	private String _namespace;
	private final Set<String> _parametersIncludedInPath;
	private Map<String, String[]> _params;
	private long _plid;
	private Portlet _portlet;
	private String _portletId;
	private String _portletModeString;
	private final PortletRequest _portletRequest;
	private long _refererGroupId;
	private long _refererPlid;
	private Set<String> _removedParameterNames;
	private final Map<String, String[]> _removePublicRenderParameters;
	private final HttpServletRequest _request;
	private Map<String, String> _reservedParameters;
	private String _resourceID;
	private boolean _secure;
	private String _toString;
	private boolean _windowStateRestoreCurrentView;
	private String _windowStateString;
	private final boolean _wsrp;

	private class ToStringPrivilegedAction implements PrivilegedAction<String> {

		@Override
		public String run() {
			if (_wsrp) {
				return generateWSRPToString();
			}

			return generateToString();
		}

	}

}