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

package com.liferay.bean.portlet.spring.extension.internal.scope;

import com.liferay.bean.portlet.extension.ScopedBean;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.mvc.RedirectScoped;

import javax.portlet.MutableRenderParameters;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.RenderParameters;
import javax.portlet.RenderResponse;
import javax.portlet.StateAwareResponse;
import javax.portlet.annotations.PortletSerializable;
import javax.portlet.annotations.RenderStateScoped;

/**
 * @author Neil Griffin
 */
public class SpringScopedBeanManager {

	public SpringScopedBeanManager(
		PortletConfig portletConfig, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		_portletConfig = portletConfig;
		_portletRequest = portletRequest;
		_portletResponse = portletResponse;
	}

	public void destroyScopedBeans() {
		if (_portletResponse instanceof StateAwareResponse) {
			StateAwareResponse stateAwareResponse =
				(StateAwareResponse)_portletResponse;

			Enumeration<String> attributeNames =
				_portletRequest.getAttributeNames();

			while (attributeNames.hasMoreElements()) {
				String attributeName = attributeNames.nextElement();

				if (!attributeName.startsWith(_ATTRIBUTE_NAME_PREFIX)) {
					continue;
				}

				Object attributeValue = _portletRequest.getAttribute(
					attributeName);

				if (!(attributeValue instanceof ScopedBean)) {
					continue;
				}

				ScopedBean<?> scopedBean = (ScopedBean<?>)attributeValue;

				Object beanInstance = scopedBean.getContainerCreatedInstance();

				if (!(beanInstance instanceof PortletSerializable)) {
					continue;
				}

				Class<?> beanInstanceClass = beanInstance.getClass();

				RenderStateScoped renderStateScoped =
					beanInstanceClass.getAnnotation(RenderStateScoped.class);

				if (renderStateScoped == null) {
					continue;
				}

				PortletSerializable portletSerializable =
					(PortletSerializable)beanInstance;

				MutableRenderParameters mutableRenderParameters =
					stateAwareResponse.getRenderParameters();

				mutableRenderParameters.setValues(
					_getParameterName(portletSerializable),
					portletSerializable.serialize());
			}
		}

		if (_portletResponse instanceof RenderResponse) {
			PortletSession portletSession = _portletRequest.getPortletSession(
				true);

			Enumeration<String> attributeNames =
				portletSession.getAttributeNames();

			while (attributeNames.hasMoreElements()) {
				String name = attributeNames.nextElement();

				Object value = portletSession.getAttribute(name);

				if (value instanceof ScopedBean) {
					SpringScopedBean springScopedBean = (SpringScopedBean)value;

					if (Objects.equals(
							springScopedBean.getScopeName(),
							RedirectScoped.class.getSimpleName())) {

						springScopedBean.destroy();

						portletSession.removeAttribute(name);
					}
				}
			}
		}

		Enumeration<String> enumeration = _portletRequest.getAttributeNames();

		while (enumeration.hasMoreElements()) {
			String name = enumeration.nextElement();

			if (name.startsWith(_ATTRIBUTE_NAME_PREFIX)) {
				Object value = _portletRequest.getAttribute(name);

				if ((value != null) && (value instanceof ScopedBean)) {
					ScopedBean<?> scopedBean = (ScopedBean)value;

					scopedBean.destroy();
				}

				_portletRequest.removeAttribute(name);
			}
		}
	}

	public PortletConfig getPortletConfig() {
		return _portletConfig;
	}

	public PortletRequest getPortletRequest() {
		return _portletRequest;
	}

	public SpringScopedBean getPortletRequestScopedBean(String name) {
		name = _ATTRIBUTE_NAME_PREFIX.concat(name);

		return (SpringScopedBean)_portletRequest.getAttribute(name);
	}

	public PortletResponse getPortletResponse() {
		return _portletResponse;
	}

	public SpringScopedBean getPortletSessionScopedBean(
		int subscope, String name) {

		PortletSession portletSession = _portletRequest.getPortletSession(true);

		return (SpringScopedBean)portletSession.getAttribute(
			_ATTRIBUTE_NAME_PREFIX.concat(name), subscope);
	}

	public SpringScopedBean getRedirectScopedBean(String name) {
		PortletSession portletSession = _portletRequest.getPortletSession(true);

		return (SpringScopedBean)portletSession.getAttribute(
			_ATTRIBUTE_NAME_PREFIX.concat(name));
	}

	public SpringScopedBean getRenderStateScopedBean(String name) {
		return getPortletRequestScopedBean(name);
	}

	public void setDestructionCallback(
		String name, Runnable destructionCallback) {

		_destructionCallbacks.put(name, destructionCallback);
	}

	public void setPortletRequestScopedBean(
		String name, SpringScopedBean springScopedBean) {

		name = _ATTRIBUTE_NAME_PREFIX.concat(name);

		_portletRequest.setAttribute(name, springScopedBean);
	}

	public void setPortletSessionScopedBean(
		int subscope, String name, SpringScopedBean springScopedBean) {

		PortletSession portletSession = _portletRequest.getPortletSession(true);

		portletSession.setAttribute(
			_ATTRIBUTE_NAME_PREFIX.concat(name), springScopedBean, subscope);
	}

	public void setRedirectScopedBean(
		String name, SpringScopedBean springScopedBean) {

		PortletSession portletSession = _portletRequest.getPortletSession(true);

		portletSession.setAttribute(
			_ATTRIBUTE_NAME_PREFIX.concat(name), springScopedBean);
	}

	public void setRenderStateScopedBean(
		String name, SpringScopedBean springScopedBean) {

		PortletSerializable portletSerializable =
			(PortletSerializable)springScopedBean.getContainerCreatedInstance();

		String parameterName = _getParameterName(portletSerializable);

		SpringScopedBeanManager springScopedBeanManager =
			SpringScopedBeanManagerThreadLocal.getCurrentScopedBeanManager();

		PortletRequest portletRequest =
			springScopedBeanManager.getPortletRequest();

		RenderParameters renderParameters =
			portletRequest.getRenderParameters();

		String[] parameterValues = renderParameters.getValues(parameterName);

		if (parameterValues == null) {
			parameterValues = new String[0];
		}

		portletSerializable.deserialize(parameterValues);

		setPortletRequestScopedBean(name, springScopedBean);
	}

	public Runnable unsetDestructionCallback(String name) {
		return _destructionCallbacks.remove(name);
	}

	private String _getParameterName(PortletSerializable portletSerializable) {
		String parameterName = null;

		Class<?> beanClass = portletSerializable.getClass();

		RenderStateScoped renderStateScoped = beanClass.getAnnotation(
			RenderStateScoped.class);

		if (renderStateScoped != null) {
			parameterName = renderStateScoped.paramName();
		}

		if ((parameterName == null) || parameterName.isEmpty()) {
			parameterName = beanClass.getSimpleName();
		}

		return parameterName;
	}

	private static final String _ATTRIBUTE_NAME_PREFIX = "com.liferay.spring.";

	private final Map<String, Runnable> _destructionCallbacks = new HashMap<>();
	private final PortletConfig _portletConfig;
	private final PortletRequest _portletRequest;
	private final PortletResponse _portletResponse;

}