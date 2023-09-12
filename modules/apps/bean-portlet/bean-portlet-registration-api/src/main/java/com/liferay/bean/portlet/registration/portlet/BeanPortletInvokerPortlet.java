/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bean.portlet.registration.portlet;

import com.liferay.bean.portlet.extension.BeanPortletMethod;
import com.liferay.bean.portlet.extension.BeanPortletMethodInvoker;
import com.liferay.bean.portlet.extension.BeanPortletMethodType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.InvokerPortlet;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Event;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.HeaderRequest;
import javax.portlet.HeaderResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Neil Griffin
 */
public class BeanPortletInvokerPortlet implements InvokerPortlet {

	public BeanPortletInvokerPortlet(
		Map<BeanPortletMethodType, List<BeanPortletMethod>> beanMethods,
		BeanPortletMethodInvoker beanPortletMethodInvoker,
		ClassLoader portletClassLoader) {

		_beanMethods = beanMethods;
		_beanPortletMethodInvoker = beanPortletMethodInvoker;
		_portletClassLoader = portletClassLoader;

		boolean facesPortlet = false;

		beanMethods:
		for (Map.Entry<BeanPortletMethodType, List<BeanPortletMethod>> entry :
				beanMethods.entrySet()) {

			for (BeanPortletMethod beanPortletMethod : entry.getValue()) {
				Method method = beanPortletMethod.getMethod();

				if (ClassUtil.isSubclass(
						method.getDeclaringClass(),
						"javax.portlet.faces.GenericFacesPortlet")) {

					facesPortlet = true;

					break beanMethods;
				}
			}
		}

		_facesPortlet = facesPortlet;
	}

	@Override
	public void destroy() {
		try {
			_invokeBeanMethods(_beanMethods.get(BeanPortletMethodType.DESTROY));
		}
		catch (PortletException portletException) {
			_log.error(portletException);
		}
	}

	@Override
	public Integer getExpCache() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Portlet getPortlet() {
		return this;
	}

	@Override
	public ClassLoader getPortletClassLoader() {
		return _portletClassLoader;
	}

	@Override
	public PortletConfig getPortletConfig() {
		return _portletConfig;
	}

	@Override
	public PortletContext getPortletContext() {
		return _portletConfig.getPortletContext();
	}

	@Override
	public Portlet getPortletInstance() {
		return this;
	}

	@Override
	public void init(PortletConfig portletConfig) throws PortletException {
		_invokeBeanMethods(
			_beanMethods.get(BeanPortletMethodType.INIT), portletConfig);

		_portletConfig = portletConfig;
	}

	@Override
	public boolean isCheckAuthToken() {
		return GetterUtil.getBoolean(
			_portletConfig.getInitParameter("check-auth-token"));
	}

	@Override
	public boolean isFacesPortlet() {
		return _facesPortlet;
	}

	@Override
	public boolean isHeaderPortlet() {
		return true;
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		_invokeMethodWithActiveScopes(
			_beanMethods.get(BeanPortletMethodType.ACTION), actionRequest,
			actionResponse);
	}

	@Override
	public void processEvent(
			EventRequest eventRequest, EventResponse eventResponse)
		throws PortletException {

		List<BeanPortletMethod> beanPortletMethods = _beanMethods.get(
			BeanPortletMethodType.EVENT);

		if (ListUtil.isEmpty(beanPortletMethods)) {
			return;
		}

		Event event = eventRequest.getEvent();

		List<BeanPortletMethod> eventMethods = new ArrayList<>();

		for (BeanPortletMethod beanPortletMethod : beanPortletMethods) {
			if (beanPortletMethod.isEventProcessor(event.getQName())) {
				eventMethods.add(beanPortletMethod);
			}
		}

		_invokeMethodWithActiveScopes(
			eventMethods, eventRequest, eventResponse);
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		_invokeMethodWithActiveScopes(
			_beanMethods.get(BeanPortletMethodType.RENDER), renderRequest,
			renderResponse);
	}

	@Override
	public void renderHeaders(
			HeaderRequest headerRequest, HeaderResponse headerResponse)
		throws PortletException {

		_invokeMethodWithActiveScopes(
			_beanMethods.get(BeanPortletMethodType.HEADER), headerRequest,
			headerResponse);
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		_invokeMethodWithActiveScopes(
			_beanMethods.get(BeanPortletMethodType.SERVE_RESOURCE),
			resourceRequest, resourceResponse);
	}

	@Override
	public void setPortletFilters() {
		throw new UnsupportedOperationException();
	}

	private void _invokeBeanMethods(
			List<BeanPortletMethod> beanPortletMethods, Object... arguments)
		throws PortletException {

		if (ListUtil.isEmpty(beanPortletMethods)) {
			return;
		}

		for (BeanPortletMethod beanPortletMethod : beanPortletMethods) {
			try {
				beanPortletMethod.invoke(arguments);
			}
			catch (ReflectiveOperationException reflectiveOperationException) {
				Throwable throwable = reflectiveOperationException.getCause();

				if (throwable instanceof PortletException) {
					throw (PortletException)throwable;
				}

				throw new PortletException(throwable);
			}
		}
	}

	private void _invokeMethodWithActiveScopes(
			List<BeanPortletMethod> beanPortletMethods,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortletException {

		if (ListUtil.isEmpty(beanPortletMethods)) {
			return;
		}

		_beanPortletMethodInvoker.invokeWithActiveScopes(
			beanPortletMethods, _portletConfig, portletRequest,
			portletResponse);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BeanPortletInvokerPortlet.class);

	private final Map<BeanPortletMethodType, List<BeanPortletMethod>>
		_beanMethods;
	private final BeanPortletMethodInvoker _beanPortletMethodInvoker;
	private final boolean _facesPortlet;
	private final ClassLoader _portletClassLoader;
	private PortletConfig _portletConfig;

}