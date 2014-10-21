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

package com.liferay.portal.kernel.resiliency.mpi;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.config.MessagingConfigurator;
import com.liferay.portal.kernel.messaging.config.MessagingConfiguratorRegistry;
import com.liferay.portal.kernel.nio.intraband.Intraband;
import com.liferay.portal.kernel.nio.intraband.IntrabandFactoryUtil;
import com.liferay.portal.kernel.nio.intraband.SystemDataType;
import com.liferay.portal.kernel.nio.intraband.rpc.BootstrapRPCDatagramReceiveHandler;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.SPIRegistryUtil;
import com.liferay.portal.kernel.resiliency.spi.provider.SPIProvider;
import com.liferay.portal.kernel.util.CentralizedThreadLocal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Shuyang Zhou
 */
public class MPIHelperUtil {

	public static SPI checkSPILiveness(SPI spi) {
		boolean alive = false;

		try {
			alive = spi.isAlive();
		}
		catch (RemoteException re) {
			_log.error(re);
		}

		if (alive) {
			return spi;
		}

		unregisterSPI(spi);

		return null;
	}

	public static Intraband getIntraband() {
		return _intraband;
	}

	public static MPI getMPI() {
		return _mpi;
	}

	public static SPI getSPI(String spiProviderName, String spiId) {
		SPIProviderContainer spiProviderContainer = _spiProviderContainers.get(
			spiProviderName);

		if (spiProviderContainer == null) {
			return null;
		}

		SPI spi = spiProviderContainer.getSPI(spiId);

		if (spi != null) {
			spi = checkSPILiveness(spi);
		}

		return spi;
	}

	public static SPIProvider getSPIProvider(String spiProviderName) {
		SPIProviderContainer spiProviderContainer = _spiProviderContainers.get(
			spiProviderName);

		if (spiProviderContainer == null) {
			return null;
		}

		return spiProviderContainer.getSPIProvider();
	}

	public static List<SPIProvider> getSPIProviders() {
		List<SPIProvider> spiProviders = new ArrayList<SPIProvider>();

		for (SPIProviderContainer spiProviderContainer :
				_spiProviderContainers.values()) {

			spiProviders.add(spiProviderContainer.getSPIProvider());
		}

		return spiProviders;
	}

	public static List<SPI> getSPIs() {
		List<SPI> spis = new ArrayList<SPI>();

		for (SPIProviderContainer spiProviderContainer :
				_spiProviderContainers.values()) {

			for (SPI spi : spiProviderContainer.getSPIs()) {
				spi = checkSPILiveness(spi);

				if (spi != null) {
					spis.add(spi);
				}
			}
		}

		return spis;
	}

	public static List<SPI> getSPIs(String spiProviderName) {
		List<SPI> spis = new ArrayList<SPI>();

		SPIProviderContainer spiProviderContainer = _spiProviderContainers.get(
			spiProviderName);

		if (spiProviderContainer != null) {
			for (SPI spi : spiProviderContainer.getSPIs()) {
				spi = checkSPILiveness(spi);

				if (spi != null) {
					spis.add(spi);
				}
			}
		}

		return spis;
	}

	public static boolean registerSPI(SPI spi) {
		try {
			MPI mpi = spi.getMPI();

			if (mpi != _mpi) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Not registering SPI " + spi + " with foreign MPI " +
							mpi + " versus " + _mpi);
				}

				return false;
			}

			String spiProviderName = spi.getSPIProviderName();

			SPIProviderContainer spiProviderContainer =
				_spiProviderContainers.get(spiProviderName);

			if (spiProviderContainer == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Not registering SPI " + spi +
							" with unknown SPI provider " + spiProviderName);
				}

				return false;
			}

			SPIConfiguration spiConfiguration = spi.getSPIConfiguration();

			SPI previousSPI = spiProviderContainer.putSPIIfAbsent(
				spiConfiguration.getSPIId(), spi);

			if (previousSPI != null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Not registering SPI " + spi +
							" because it duplicates " + previousSPI);
				}

				return false;
			}

			SPIRegistryUtil.registerSPI(spi);

			for (String servletContextName :
					spiConfiguration.getServletContextNames()) {

				List<MessagingConfigurator> messagingConfigurators =
					MessagingConfiguratorRegistry.getMessagingConfigurators(
						servletContextName);

				if (messagingConfigurators != null) {
					for (MessagingConfigurator messagingConfigurator :
							messagingConfigurators) {

						messagingConfigurator.disconnect();
					}
				}
			}

			if (_log.isInfoEnabled()) {
				_log.info("Registered SPI " + spi);
			}

			return true;
		}
		catch (RemoteException re) {
			throw new RuntimeException(re);
		}
	}

	public static boolean registerSPIProvider(SPIProvider spiProvider) {
		String spiProviderName = spiProvider.getName();

		SPIProviderContainer previousSPIProviderContainer =
			_spiProviderContainers.putIfAbsent(
				spiProviderName, new SPIProviderContainer(spiProvider));

		if (previousSPIProviderContainer != null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Not registering SPI provider " + spiProvider +
						" because it duplicates " +
							previousSPIProviderContainer.getSPIProvider());
			}

			return false;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Registered SPI provider " + spiProvider);
		}

		return true;
	}

	public static void shutdown() {
		try {
			UnicastRemoteObject.unexportObject(_mpiImpl, true);
		}
		catch (NoSuchObjectException nsoe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to unexport " + _mpiImpl, nsoe);
			}
		}

		try {
			_intraband.close();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to close intraband", e);
			}
		}
	}

	public static boolean unregisterSPI(SPI spi) {
		try {
			if (spi == _unregisteringSPIThreadLocal.get()) {
				_doUnregisterSPI(spi);

				return true;
			}

			MPI mpi = spi.getMPI();

			if (mpi != _mpi) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Not unregistering SPI " + spi + " with foreign MPI " +
							mpi + " versus " + _mpi);
				}

				return false;
			}

			String spiProviderName = spi.getSPIProviderName();

			SPIProviderContainer spiProviderContainer =
				_spiProviderContainers.get(spiProviderName);

			if (spiProviderContainer == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Not unregistering SPI " + spi +
							" with unknown SPI provider " + spiProviderName);
				}

				return false;
			}

			SPIConfiguration spiConfiguration = spi.getSPIConfiguration();

			if (spiProviderContainer.removeSPI(
					spiConfiguration.getSPIId(), spi)) {

				_doUnregisterSPI(spi);

				return true;
			}

			if (_log.isWarnEnabled()) {
				_log.warn("Not unregistering unregistered SPI " + spi);
			}

			return false;
		}
		catch (RemoteException re) {
			throw new RuntimeException(re);
		}
	}

	public static boolean unregisterSPIProvider(SPIProvider spiProvider) {
		String spiProviderName = spiProvider.getName();

		SPIProviderContainer spiProviderContainer = _spiProviderContainers.get(
			spiProviderName);

		if ((spiProviderContainer != null) &&
			(spiProviderContainer.getSPIProvider() == spiProvider) &&
			_spiProviderContainers.remove(
				spiProviderName, spiProviderContainer)) {

			Collection<SPI> spis = spiProviderContainer.getSPIs();

			Iterator<SPI> iterator = spis.iterator();

			while (iterator.hasNext()) {
				SPI spi = iterator.next();

				iterator.remove();

				_unregisteringSPIThreadLocal.set(spi);

				try {
					spi.stop();

					spi.destroy();

					if (_log.isInfoEnabled()) {
						_log.info(
							"Unregistered SPI " + spi +
								" while unregistering SPI provider " +
									spiProvider);
					}
				}
				catch (RemoteException re) {
					_log.error(
						"Unable to unregister SPI " + spi +
							" while unregistering SPI provider " + spiProvider,
						re);
				}
				finally {
					_unregisteringSPIThreadLocal.remove();
				}
			}

			if (_log.isInfoEnabled()) {
				_log.info("Unregistered SPI provider " + spiProvider);
			}

			return true;
		}

		if (_log.isWarnEnabled()) {
			_log.warn(
				"Not unregistering unregistered SPI provider " + spiProvider);
		}

		return false;
	}

	private static void _doUnregisterSPI(SPI spi) throws RemoteException {
		SPIRegistryUtil.unregisterSPI(spi);

		SPIConfiguration spiConfiguration = spi.getSPIConfiguration();

		for (String servletContextName :
				spiConfiguration.getServletContextNames()) {

			List<MessagingConfigurator> messagingConfigurators =
				MessagingConfiguratorRegistry.getMessagingConfigurators(
					servletContextName);

			if (messagingConfigurators == null) {
				continue;
			}

			for (MessagingConfigurator messagingConfigurator :
					messagingConfigurators) {

				messagingConfigurator.connect();
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("Unregistered SPI " + spi);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(MPIHelperUtil.class);

	private static final Intraband _intraband;
	private static final MPI _mpi;
	private static final MPI _mpiImpl;
	private static final ConcurrentMap<String, SPIProviderContainer>
		_spiProviderContainers =
			new ConcurrentHashMap<String, SPIProviderContainer>();
	private static final ThreadLocal<SPI> _unregisteringSPIThreadLocal =
		new CentralizedThreadLocal<SPI>(true);

	static {

		// Keep strong reference to prevent garbage collection

		_mpiImpl = new MPIImpl();

		try {
			if (PropsUtil.getProps() != null) {
				System.setProperty(
					PropsKeys.INTRABAND_IMPL,
					PropsUtil.get(PropsKeys.INTRABAND_IMPL));
				System.setProperty(
					PropsKeys.INTRABAND_TIMEOUT_DEFAULT,
					PropsUtil.get(PropsKeys.INTRABAND_TIMEOUT_DEFAULT));
				System.setProperty(
					PropsKeys.INTRABAND_WELDER_IMPL,
					PropsUtil.get(PropsKeys.INTRABAND_WELDER_IMPL));
			}

			_intraband = IntrabandFactoryUtil.createIntraband();

			_intraband.registerDatagramReceiveHandler(
				SystemDataType.RPC.getValue(),
				new BootstrapRPCDatagramReceiveHandler());

			_mpi = (MPI)UnicastRemoteObject.exportObject(_mpiImpl, 0);
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	private static class MPIImpl implements MPI {

		@Override
		public boolean isAlive() {
			return true;
		}

	}

	private static class SPIProviderContainer {

		public SPIProviderContainer(SPIProvider spiProvider) {
			_spiProvider = spiProvider;
		}

		public SPI getSPI(String spiId) {
			return _spis.get(spiId);
		}

		public SPIProvider getSPIProvider() {
			return _spiProvider;
		}

		public Collection<SPI> getSPIs() {
			return _spis.values();
		}

		public SPI putSPIIfAbsent(String spiId, SPI spi) {
			return _spis.putIfAbsent(spiId, spi);
		}

		public boolean removeSPI(String spiId, SPI spi) {
			return _spis.remove(spiId, spi);
		}

		private final SPIProvider _spiProvider;
		private final ConcurrentMap<String, SPI> _spis =
			new ConcurrentHashMap<String, SPI>();

	}

}