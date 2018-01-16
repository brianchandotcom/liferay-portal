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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.model.PublicRenderParameter;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletQNameUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.Event;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.StateAwareResponse;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletResponse;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class StateAwareResponseImpl
	extends PortletResponseImpl implements StateAwareResponse {

	public String getDefaultNamespace() {
		Portlet portlet = getPortlet();

		if (portlet != null) {
			PortletApp portletApp = portlet.getPortletApp();

			return portletApp.getDefaultNamespace();
		}
		else {
			return XMLConstants.NULL_NS_URI;
		}
	}

	public List<Event> getEvents() {
		return _events;
	}

	public Layout getLayout() {
		return _layout;
	}

	@Override
	public PortletMode getPortletMode() {
		return _portletMode;
	}

	public String getRedirectLocation() {
		return _redirectLocation;
	}

	@Override
	public Map<String, String[]> getRenderParameterMap() {
		return _params;
	}

	public User getUser() {
		return _user;
	}

	@Override
	public WindowState getWindowState() {
		return _windowState;
	}

	public boolean isCalledSetRenderParameter() {
		return _calledSetRenderParameter;
	}

	@Override
	public void removePublicRenderParameter(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		PublicRenderParameter publicRenderParameter =
			getPortlet().getPublicRenderParameter(name);

		if (publicRenderParameter == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Public parameter " + name + "does not exist");
			}

			return;
		}

		com.liferay.portal.kernel.xml.QName qName =
			publicRenderParameter.getQName();

		String key = PortletQNameUtil.getPublicRenderParameterName(qName);

		_publicRenderParameters.remove(key);
	}

	@Override
	public void setEvent(QName name, Serializable value) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		_events.add(new EventImpl(name.getLocalPart(), name, value));
	}

	@Override
	public void setEvent(String name, Serializable value) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		setEvent(new QName(getDefaultNamespace(), name), value);
	}

	@Override
	public void setPortletMode(PortletMode portletMode)
		throws PortletModeException {

		if (_redirectLocation != null) {
			throw new IllegalStateException();
		}

		if (portletMode == null) {
			throw new IllegalArgumentException();
		}

		if (!portletRequestImpl.isPortletModeAllowed(portletMode)) {
			throw new PortletModeException(portletMode.toString(), portletMode);
		}

		try {
			_portletMode = PortalUtil.updatePortletMode(
				portletName, _user, _layout, portletMode,
				portletRequestImpl.getHttpServletRequest());

			portletRequestImpl.setPortletMode(_portletMode);
		}
		catch (Exception e) {
			throw new PortletModeException(e, portletMode);
		}

		_calledSetRenderParameter = true;
	}

	public void setRedirectLocation(String redirectLocation) {
		_redirectLocation = redirectLocation;
	}

	@Override
	public void setRenderParameter(String name, String value) {
		if (_redirectLocation != null) {
			throw new IllegalStateException();
		}

		if ((name == null) || (value == null)) {
			throw new IllegalArgumentException();
		}

		setRenderParameter(name, new String[] {value});
	}

	@Override
	public void setRenderParameter(String name, String[] values) {
		if (_redirectLocation != null) {
			throw new IllegalStateException();
		}

		if ((name == null) || (values == null)) {
			throw new IllegalArgumentException();
		}

		for (int i = 0; i < values.length; i++) {
			if (values[i] == null) {
				throw new IllegalArgumentException();
			}
		}

		if (!setPublicRenderParameter(name, values)) {
			_params.put(name, values);
		}

		_calledSetRenderParameter = true;
	}

	@Override
	public void setRenderParameters(Map<String, String[]> params) {
		if (_redirectLocation != null) {
			throw new IllegalStateException();
		}

		if (params == null) {
			throw new IllegalArgumentException();
		}
		else {
			Map<String, String[]> newParams = new LinkedHashMap<>();

			for (Map.Entry<String, String[]> entry : params.entrySet()) {
				String key = entry.getKey();
				String[] value = entry.getValue();

				if (key == null) {
					throw new IllegalArgumentException();
				}
				else if (value == null) {
					throw new IllegalArgumentException();
				}

				if (setPublicRenderParameter(key, value)) {
					continue;
				}

				newParams.put(key, value);
			}

			_params = newParams;
		}

		_calledSetRenderParameter = true;
	}

	@Override
	public void setWindowState(WindowState windowState)
		throws WindowStateException {

		if (_redirectLocation != null) {
			throw new IllegalStateException();
		}

		if (windowState == null) {
			throw new IllegalArgumentException();
		}

		if (!portletRequestImpl.isWindowStateAllowed(windowState)) {
			throw new WindowStateException(windowState.toString(), windowState);
		}

		try {
			_windowState = PortalUtil.updateWindowState(
				portletName, _user, _layout, windowState,
				portletRequestImpl.getHttpServletRequest());

			portletRequestImpl.setWindowState(_windowState);
		}
		catch (Exception e) {
			throw new WindowStateException(e, windowState);
		}

		_calledSetRenderParameter = true;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #init(PortletRequestImpl,
	 *             HttpServletResponse, User, Layout)}
	 */
	@Deprecated
	protected void init(
			PortletRequestImpl portletRequestImpl, HttpServletResponse response,
			String portletName, User user, Layout layout,
			WindowState windowState, PortletMode portletMode)
		throws PortletModeException, WindowStateException {

		init(portletRequestImpl, response, user, layout, true);
	}

	protected void init(
			PortletRequestImpl portletRequestImpl, HttpServletResponse response,
			User user, Layout layout, boolean setWindowStateAndPortletMode)
		throws PortletModeException, WindowStateException {

		super.init(portletRequestImpl, response);

		_user = user;
		_layout = layout;

		_publicRenderParameters = PublicRenderParametersPool.get(
			getHttpServletRequest(), layout.getPlid());

		if (setWindowStateAndPortletMode) {
			setWindowState(portletRequestImpl.getWindowState());
			setPortletMode(portletRequestImpl.getPortletMode());
		}

		// Set _calledSetRenderParameter to false because setWindowState and
		// setPortletMode sets it to true

		_calledSetRenderParameter = false;
	}

	protected void reset() {
		_events.clear();
		_params.clear();

		try {
			setPortletMode(PortletMode.VIEW);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to reset portlet mode to VIEW", e);
			}
		}

		_portletMode = null;

		_redirectLocation = null;

		try {
			setWindowState(WindowState.NORMAL);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to reset window state to NORMAL", e);
			}
		}

		_windowState = null;

		_calledSetRenderParameter = false;
	}

	protected boolean setPublicRenderParameter(String name, String[] values) {
		Portlet portlet = getPortlet();

		PublicRenderParameter publicRenderParameter =
			portlet.getPublicRenderParameter(name);

		if (publicRenderParameter == null) {
			return false;
		}

		com.liferay.portal.kernel.xml.QName qName =
			publicRenderParameter.getQName();

		String[] oldValues = _publicRenderParameters.get(name);

		if (oldValues != null) {
			values = ArrayUtil.append(oldValues, values);
		}

		_publicRenderParameters.put(
			PortletQNameUtil.getPublicRenderParameterName(qName), values);

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StateAwareResponseImpl.class);

	private boolean _calledSetRenderParameter;
	private final List<Event> _events = new ArrayList<>();
	private Layout _layout;
	private Map<String, String[]> _params = new LinkedHashMap<>();
	private PortletMode _portletMode;
	private Map<String, String[]> _publicRenderParameters;
	private String _redirectLocation;
	private User _user;
	private WindowState _windowState;

}