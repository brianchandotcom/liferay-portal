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

package com.liferay.portal.resiliency.spi;

import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.SPIRuntimeMapping;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.spring.aop.ServiceBeanAopCacheManagerUtil;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Shuyang Zhou
 */
public class SPIRuntimeMappingImpl implements SPIRuntimeMapping {

	@Override
	public void addSkipMappingPortletId(String portletId) {
		_skipMappingPortletIds.add(portletId);
	}

	@Override
	public SPI getMappingSPIForPortlet(String portletId) {
		if (_skipMappingPortletIds.contains(portletId)) {
			return null;
		}

		return _portletIdToSPIMap.get(portletId);
	}

	@Override
	public SPI getMappingSPIForServletContext(String servletContextName) {
		return _servletContextNameToSPIMap.get(servletContextName);
	}

	@Override
	public Set<String> getSkipMappingPortletIds() {
		return _skipMappingPortletIds;
	}

	@Override
	public void register(SPI spi) throws RemoteException {
		SPIConfiguration spiConfiguration = spi.getSPIConfiguration();

		String[] corePortletIds = spiConfiguration.getPortletIds();
		String[] servletContextNames =
			spiConfiguration.getServletContextNames();

		List<String> portletIds = new ArrayList<String>();

		for (String corePortletId : corePortletIds) {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				corePortletId);

			if (portlet == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("Skip unknown core portlet id " + corePortletId);
				}
			}
			else {
				portletIds.add(corePortletId);
			}
		}

		for (String servletContextName : servletContextNames) {
			PortletApp portletApp = PortletLocalServiceUtil.getPortletApp(
				servletContextName);

			if (portletApp == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Skip unknow plugin servlet context name " +
							servletContextName);
				}
			}
			else {
				List<Portlet> portlets = portletApp.getPortlets();

				for (Portlet portlet : portlets) {
					portletIds.add(portlet.getPortletId());
				}
			}
		}

		_modifyLock.lock();

		try {
			for (String portletId : portletIds) {
				_portletIdToSPIMap.put(portletId, spi);
			}

			_spiToPortletIdsMap.put(
				spi, portletIds.toArray(new String[portletIds.size()]));

			for (String servletContextName : servletContextNames) {
				_servletContextNameToSPIMap.put(servletContextName, spi);
			}

			_spiToServletContextNamesMap.put(spi, servletContextNames.clone());

			ServiceBeanAopCacheManagerUtil.reset();
		}
		finally {
			_modifyLock.unlock();
		}
	}

	@Override
	public void removeSkipMappingPortletId(String portletId) {
		_skipMappingPortletIds.remove(portletId);
	}

	@Override
	public void unregister(SPI spi) {
		_modifyLock.lock();

		try {
			String[] portletIds = _spiToPortletIdsMap.remove(spi);

			if (portletIds != null) {
				for (String portletId : portletIds) {
					_portletIdToSPIMap.remove(portletId);
				}
			}

			String[] servletContextNames = _spiToServletContextNamesMap.remove(
				spi);

			if (servletContextNames != null) {
				for (String servletContextName : servletContextNames) {
					_servletContextNameToSPIMap.remove(servletContextName);
				}
			}
		}
		finally {
			_modifyLock.unlock();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SPIRuntimeMappingImpl.class);

	private final Lock _modifyLock = new ReentrantLock();
	private final Map<String, SPI> _portletIdToSPIMap =
		new ConcurrentHashMap<String, SPI>();
	private final Map<String, SPI> _servletContextNameToSPIMap =
		new ConcurrentHashMap<String, SPI>();
	private final Set<String> _skipMappingPortletIds =
		new ConcurrentHashSet<String>();
	private final Map<SPI, String[]> _spiToPortletIdsMap =
		new ConcurrentHashMap<SPI, String[]>();
	private final Map<SPI, String[]> _spiToServletContextNamesMap =
		new ConcurrentHashMap<SPI, String[]>();

}