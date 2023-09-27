/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bean.portlet.cdi.extension.internal.scope;

import com.liferay.bean.portlet.extension.ScopedBean;

import java.util.Enumeration;
import java.util.Objects;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;

import javax.mvc.RedirectScoped;

import javax.portlet.MutableRenderParameters;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.RenderParameters;
import javax.portlet.RenderResponse;
import javax.portlet.StateAwareResponse;
import javax.portlet.annotations.PortletRequestScoped;
import javax.portlet.annotations.PortletSerializable;
import javax.portlet.annotations.PortletSessionScoped;
import javax.portlet.annotations.RenderStateScoped;

/**
 * @author Neil Griffin
 */
public class ScopedBeanManager {

	public ScopedBeanManager(
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

			Enumeration<String> enumeration =
				_portletRequest.getAttributeNames();

			while (enumeration.hasMoreElements()) {
				String attributeName = enumeration.nextElement();

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

			Enumeration<String> enumeration =
				portletSession.getAttributeNames();

			while (enumeration.hasMoreElements()) {
				String name = enumeration.nextElement();

				Object value = portletSession.getAttribute(name);

				if (value instanceof CDIScopedBean) {
					CDIScopedBean<?> cdiScopedBean = (CDIScopedBean<?>)value;

					if (Objects.equals(
							cdiScopedBean.getScopeName(),
							RedirectScoped.class.getSimpleName())) {

						cdiScopedBean.destroy();

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

	public <T> T getPortletRequestScopedBean(
		Bean<T> bean, CreationalContext<T> creationalContext) {

		String name = _getAttributeName(bean);

		@SuppressWarnings("unchecked")
		ScopedBean<T> scopedBean = (ScopedBean<T>)_portletRequest.getAttribute(
			name);

		if (scopedBean == null) {
			if (creationalContext == null) {
				return null;
			}

			scopedBean = new CDIScopedBean<>(
				bean, creationalContext, name,
				PortletRequestScoped.class.getSimpleName());

			_portletRequest.setAttribute(name, scopedBean);
		}

		return scopedBean.getContainerCreatedInstance();
	}

	public PortletResponse getPortletResponse() {
		return _portletResponse;
	}

	public <T> T getPortletSessionScopedBean(
		int subscope, Bean<T> bean, CreationalContext<T> creationalContext) {

		PortletSession portletSession = _portletRequest.getPortletSession(true);

		String name = _getAttributeName(bean);

		@SuppressWarnings("unchecked")
		ScopedBean<T> scopedBean = (ScopedBean<T>)portletSession.getAttribute(
			name, subscope);

		if (scopedBean == null) {
			if (creationalContext == null) {
				return null;
			}

			scopedBean = new CDIScopedBean<>(
				bean, creationalContext, name,
				PortletSessionScoped.class.getSimpleName());

			portletSession.setAttribute(name, scopedBean, subscope);
		}

		return scopedBean.getContainerCreatedInstance();
	}

	public <T> T getRedirectScopedBean(
		Bean<T> bean, CreationalContext<T> creationalContext) {

		PortletSession portletSession = _portletRequest.getPortletSession(true);

		String name = _getAttributeName(bean);

		@SuppressWarnings("unchecked")
		ScopedBean<T> scopedBean = (ScopedBean<T>)portletSession.getAttribute(
			name);

		if (scopedBean == null) {
			if (creationalContext == null) {
				return null;
			}

			scopedBean = new CDIScopedBean<>(
				bean, creationalContext, name,
				RedirectScoped.class.getSimpleName());

			portletSession.setAttribute(name, scopedBean);
		}

		return scopedBean.getContainerCreatedInstance();
	}

	public <T> T getRenderStateScopedBean(
		Bean<T> bean, CreationalContext<T> creationalContext) {

		String name = _getAttributeName(bean);

		@SuppressWarnings("unchecked")
		ScopedBean<T> scopedBean = (ScopedBean<T>)_portletRequest.getAttribute(
			name);

		if (scopedBean == null) {
			if (creationalContext == null) {
				return null;
			}

			scopedBean = new CDIScopedBean<>(
				bean, creationalContext, name,
				RenderStateScoped.class.getSimpleName());

			PortletSerializable portletSerializable =
				(PortletSerializable)scopedBean.getContainerCreatedInstance();

			String parameterName = _getParameterName(portletSerializable);

			RenderParameters renderParameters =
				_portletRequest.getRenderParameters();

			String[] parameterValues = renderParameters.getValues(
				parameterName);

			if (parameterValues == null) {
				parameterValues = new String[0];
			}

			portletSerializable.deserialize(parameterValues);

			_portletRequest.setAttribute(name, scopedBean);
		}

		return scopedBean.getContainerCreatedInstance();
	}

	private String _getAttributeName(Bean<?> bean) {
		String attributeName = bean.getName();

		if ((attributeName == null) || attributeName.isEmpty()) {
			Class<?> beanClass = bean.getBeanClass();

			attributeName = beanClass.getName();
		}

		return _ATTRIBUTE_NAME_PREFIX.concat(attributeName);
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

	private static final String _ATTRIBUTE_NAME_PREFIX = "com.liferay.cdi.";

	private final PortletConfig _portletConfig;
	private final PortletRequest _portletRequest;
	private final PortletResponse _portletResponse;

}