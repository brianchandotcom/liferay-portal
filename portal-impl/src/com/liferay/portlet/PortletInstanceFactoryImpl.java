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

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.portlet.InvokerFilterContainer;
import com.liferay.portal.kernel.portlet.InvokerPortlet;
import com.liferay.portal.kernel.portlet.InvokerPortletFactory;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletContextFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletInstanceFactory;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.util.ClassLoaderUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerFieldUpdaterCustomizer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
@DoPrivileged
public class PortletInstanceFactoryImpl implements PortletInstanceFactory {

	public void afterPropertiesSet() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			InvokerPortletFactory.class,
			new ServiceTrackerFieldUpdaterCustomizer
				<InvokerPortletFactory, InvokerPortletFactory>(
					ReflectionUtil.getDeclaredField(
						PortletInstanceFactoryImpl.class,
						"_invokerPortletFactory"),
					this, _defaultInvokerPortletFactory) {

				@Override
				protected void afterServiceUpdate(
					InvokerPortletFactory oldInvokerPortletFactory,
					InvokerPortletFactory newInvokerPortletFactory) {

					_pool.clear();
				}

			});

		_serviceTracker.open();
	}

	@Override
	public void clear(Portlet portlet) {
		clear(portlet, true);
	}

	@Override
	public void clear(Portlet portlet, boolean resetRemotePortletBag) {
		String rootPortletId = portlet.getRootPortletId();

		Map<String, InvokerPortlet> portletInstances = _pool.remove(
			rootPortletId);

		if (portletInstances != null) {
			InvokerPortlet rootInvokerPortletInstance = portletInstances.remove(
				rootPortletId);

			if (rootInvokerPortletInstance != null) {
				rootInvokerPortletInstance.destroy();
			}

			portletInstances.clear();
		}

		PortletApp portletApp = portlet.getPortletApp();

		if (resetRemotePortletBag && portletApp.isWARFile()) {
			PortletBag portletBag = PortletBagPool.remove(rootPortletId);

			if (portletBag != null) {
				portletBag.destroy();
			}
		}
	}

	@Override
	public InvokerPortlet create(Portlet portlet, ServletContext servletContext)
		throws PortletException {

		return create(portlet, servletContext, false);
	}

	@Override
	public InvokerPortlet create(
			Portlet portlet, ServletContext servletContext,
			boolean destroyPrevious)
		throws PortletException {

		if (destroyPrevious) {
			destroyRelated(portlet);
		}

		boolean instanceable = false;

		boolean deployed = !portlet.isUndeployedPortlet();

		if (portlet.isInstanceable() && deployed &&
			PortletIdCodec.hasInstanceId(portlet.getPortletId())) {

			instanceable = true;
		}

		String rootPortletId = portlet.getRootPortletId();

		InvokerPortlet rootInvokerPortletInstance = null;

		Map<String, InvokerPortlet> portletInstances = null;

		if (deployed) {
			portletInstances = _pool.get(rootPortletId);

			if (portletInstances == null) {
				portletInstances = new ConcurrentHashMap<>();

				_pool.put(rootPortletId, portletInstances);
			}
			else {
				if (instanceable) {
					InvokerPortlet instanceInvokerPortletInstance =
						portletInstances.get(portlet.getPortletId());

					if (instanceInvokerPortletInstance != null) {
						return instanceInvokerPortletInstance;
					}
				}

				rootInvokerPortletInstance = portletInstances.get(
					rootPortletId);
			}
		}

		if (rootInvokerPortletInstance == null) {
			PortletBag portletBag = PortletBagPool.get(rootPortletId);

			// Portlet bag should never be null unless the portlet has been
			// undeployed

			if (portletBag == null) {
				PortletBagFactory portletBagFactory = new PortletBagFactory();

				portletBagFactory.setClassLoader(
					ClassLoaderUtil.getPortalClassLoader());
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

			if (deployed) {
				portletInstances.put(rootPortletId, rootInvokerPortletInstance);
			}
		}

		if (!instanceable) {
			return rootInvokerPortletInstance;
		}

		javax.portlet.Portlet portletInstance =
			rootInvokerPortletInstance.getPortletInstance();

		PortletConfig portletConfig = PortletConfigFactoryUtil.create(
			portlet, servletContext);

		PortletContext portletContext = portletConfig.getPortletContext();

		boolean checkAuthToken = rootInvokerPortletInstance.isCheckAuthToken();
		boolean facesPortlet = rootInvokerPortletInstance.isFacesPortlet();
		boolean strutsPortlet = rootInvokerPortletInstance.isStrutsPortlet();
		boolean strutsBridgePortlet =
			rootInvokerPortletInstance.isStrutsBridgePortlet();

		InvokerPortlet instanceInvokerPortletInstance =
			_invokerPortletFactory.create(
				portlet, portletInstance, portletConfig, portletContext,
				(InvokerFilterContainer)rootInvokerPortletInstance,
				checkAuthToken, facesPortlet, strutsPortlet,
				strutsBridgePortlet);

		if (deployed) {
			portletInstances.put(
				portlet.getPortletId(), instanceInvokerPortletInstance);
		}

		return instanceInvokerPortletInstance;
	}

	@Override
	public void delete(Portlet portlet) {
		if (PortletIdCodec.hasInstanceId(portlet.getPortletId())) {
			Map<String, InvokerPortlet> portletInstances = _pool.get(
				portlet.getRootPortletId());

			if (portletInstances != null) {
				portletInstances.remove(portlet.getPortletId());
			}
		}
	}

	public void destroy() {

		// LPS-10473

		_serviceTracker.close();
	}

	@Override
	public void destroy(Portlet portlet) {
		clear(portlet);

		destroyRelated(portlet);

		PortletLocalServiceUtil.destroyPortlet(portlet);
	}

	public void setDefaultInvokerPortletFactory(
		InvokerPortletFactory defaultInvokerPortletFactory) {

		_defaultInvokerPortletFactory = defaultInvokerPortletFactory;

		_invokerPortletFactory = defaultInvokerPortletFactory;
	}

	protected void destroyRelated(Portlet portlet) {
		PortletConfigFactoryUtil.destroy(portlet);
		PortletContextFactoryUtil.destroy(portlet);
	}

	protected InvokerPortlet init(
			Portlet portlet, PortletConfig portletConfig,
			javax.portlet.Portlet portletInstance)
		throws PortletException {

		PortletContext portletContext = portletConfig.getPortletContext();

		InvokerFilterContainer invokerFilterContainer =
			InvokerFilterContainerImpl.EMPTY_INVOKER_FILTER_CONTAINER;

		if (!portlet.isUndeployedPortlet()) {
			invokerFilterContainer = new InvokerFilterContainerImpl(
				portlet, portletContext);
		}

		InvokerPortlet invokerPortlet = _invokerPortletFactory.create(
			portlet, portletInstance, portletContext, invokerFilterContainer);

		invokerPortlet.init(portletConfig);

		return invokerPortlet;
	}

	private InvokerPortletFactory _defaultInvokerPortletFactory;
	private volatile InvokerPortletFactory _invokerPortletFactory;
	private final Map<String, Map<String, InvokerPortlet>> _pool =
		new ConcurrentHashMap<>();
	private ServiceTracker<InvokerPortletFactory, InvokerPortletFactory>
		_serviceTracker;

}