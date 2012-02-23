/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.service.PortletLocalServiceUtil;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletInstanceFactoryImpl implements PortletInstanceFactory {

	public PortletInstanceFactoryImpl() {
		_pool = new ConcurrentHashMap<String, Map<String, InvokerPortlet>>();
	}

	public void clear(Portlet portlet) {
		clear(portlet, true);
	}

	public void clear(Portlet portlet, boolean resetRemotePortletBag) {
		Map<String, InvokerPortlet> portletInstances = _pool.get(
			portlet.getRootPortletId());

		if (portletInstances != null) {
			Iterator<Map.Entry<String, InvokerPortlet>> itr =
				portletInstances.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, InvokerPortlet> entry = itr.next();

				String portletId = entry.getKey();
				InvokerPortlet invokerPortletInstance = entry.getValue();

				if (PortletConstants.getInstanceId(portletId) == null) {
					invokerPortletInstance.destroy();

					break;
				}
			}
		}

		_pool.remove(portlet.getRootPortletId());

		PortletApp portletApp = portlet.getPortletApp();

		if (resetRemotePortletBag && portletApp.isWARFile()) {
			PortletBagPool.remove(portlet.getRootPortletId());
		}
	}

	public InvokerPortlet create(Portlet portlet, ServletContext servletContext)
		throws PortletException {

		boolean instanceable = false;

		if ((portlet.isInstanceable()) &&
			(PortletConstants.getInstanceId(portlet.getPortletId()) != null)) {

			instanceable = true;
		}

		Map<String, InvokerPortlet> portletInstances = _pool.get(
			portlet.getRootPortletId());

		if (portletInstances == null) {
			portletInstances = new ConcurrentHashMap<String, InvokerPortlet>();

			_pool.put(portlet.getRootPortletId(), portletInstances);
		}

		InvokerPortlet instanceInvokerPortletInstance = null;

		if (instanceable) {
			instanceInvokerPortletInstance = portletInstances.get(
				portlet.getPortletId());
		}

		InvokerPortlet rootInvokerPortletInstance = null;

		if (instanceInvokerPortletInstance == null) {
			rootInvokerPortletInstance = portletInstances.get(
				portlet.getRootPortletId());

			if (rootInvokerPortletInstance == null) {
				PortletBag portletBag = PortletBagPool.get(
					portlet.getRootPortletId());

				// Portlet bag should never be null unless the portlet has been
				// undeployed

				if (portletBag == null) {
					PortletBagFactory portletBagFactory =
						new PortletBagFactory();

					portletBagFactory.setClassLoader(
						PortalClassLoaderUtil.getClassLoader());
					portletBagFactory.setServletContext(servletContext);
					portletBagFactory.setWARFile(false);

					try {
						portletBag = portletBagFactory.create(portlet);
					}
					catch (Exception e) {
						throw new PortletException(e);
					}
				}

				PortletConfig portletConfig = PortletConfigFactoryUtil.create(
					portlet, servletContext);

				rootInvokerPortletInstance = init(
					portlet, portletConfig, portletBag.getPortletInstance());

				portletInstances.put(
					portlet.getRootPortletId(), rootInvokerPortletInstance);
			}
		}

		if (instanceable) {
			if (instanceInvokerPortletInstance == null) {
				javax.portlet.Portlet portletInstance =
					rootInvokerPortletInstance.getPortletInstance();

				PortletConfig portletConfig = PortletConfigFactoryUtil.create(
					portlet, servletContext);

				PortletContext portletContext =
					portletConfig.getPortletContext();
				boolean checkAuthToken =
					rootInvokerPortletInstance.isCheckAuthToken();
				boolean facesPortlet =
					rootInvokerPortletInstance.isFacesPortlet();
				boolean strutsPortlet =
					rootInvokerPortletInstance.isStrutsPortlet();
				boolean strutsBridgePortlet =
					rootInvokerPortletInstance.isStrutsBridgePortlet();

				instanceInvokerPortletInstance =
					_invokerPortletFactory.create(
						portlet, portletInstance, portletConfig, portletContext,
						checkAuthToken, facesPortlet, strutsPortlet,
						strutsBridgePortlet);

				portletInstances.put(
					portlet.getPortletId(), instanceInvokerPortletInstance);
			}
		}
		else {
			if (rootInvokerPortletInstance != null) {
				instanceInvokerPortletInstance = rootInvokerPortletInstance;
			}
		}

		return instanceInvokerPortletInstance;
	}

	public void destroy() {

		// LPS-10473

	}

	public void destroy(Portlet portlet) {
		clear(portlet);

		PortletConfigFactoryUtil.destroy(portlet);
		PortletContextFactory.destroy(portlet);

		PortletLocalServiceUtil.destroyPortlet(portlet);
	}

	public void setInvokerPortletFactory(
		InvokerPortletFactory invokerPortletFactory) {

		_invokerPortletFactory = invokerPortletFactory;
	}

	protected InvokerPortlet init(
			Portlet portlet, PortletConfig portletConfig,
			javax.portlet.Portlet portletInstance)
		throws PortletException {

		PortletContext portletContext = portletConfig.getPortletContext();

		InvokerPortlet invokerPortlet = _invokerPortletFactory.create(
			portlet, portletInstance, portletContext);

		invokerPortlet.init(portletConfig);

		return invokerPortlet;
	}

	private InvokerPortletFactory _invokerPortletFactory;
	private Map<String, Map<String, InvokerPortlet>> _pool;

}