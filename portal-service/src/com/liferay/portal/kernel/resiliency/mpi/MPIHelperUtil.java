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

package com.liferay.portal.kernel.resiliency.mpi;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.nio.intraband.Intraband;
import com.liferay.portal.kernel.nio.intraband.IntrabandFactoryUtil;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.SPIRegistryUtil;
import com.liferay.portal.kernel.resiliency.spi.provider.SPIProvider;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Shuyang Zhou
 */
public class MPIHelperUtil {

	public static Intraband getIntraband() {
		return _intraband;
	}

	public static MPI getMPI() {
		return _mpi;
	}

	public static SPI getSPI(String spiProviderName, String spiId) {
		SPIKey spiKey = new SPIKey(spiProviderName, spiId);

		SPI spi = _spis.get(spiKey);

		if (spi != null) {
			spi = _checkSPILiveness(spi);
		}

		return spi;
	}

	public static SPIProvider getSPIProvider(String spiProviderName) {
		return _spiProviders.get(spiProviderName);
	}

	public static List<SPIProvider> getSPIProviders() {
		return new ArrayList<SPIProvider>(_spiProviders.values());
	}

	public static List<SPI> getSPIs() {
		List<SPI> spis = new ArrayList<SPI>();

		for (SPI spi : _spis.values()) {
			spi = _checkSPILiveness(spi);

			if (spi != null) {
				spis.add(spi);
			}
		}

		return spis;
	}

	public static List<SPI> getSPIs(String spiProviderName) {
		List<SPI> spis = new ArrayList<SPI>();

		for (Map.Entry<SPIKey, SPI> entry : _spis.entrySet()) {
			SPIKey spiKey = entry.getKey();

			if (!spiProviderName.equals(spiKey._spiProviderName)) {
				continue;
			}

			SPI spi = entry.getValue();

			spi = _checkSPILiveness(spi);

			if (spi != null) {
				spis.add(spi);
			}
		}

		return spis;
	}

	public static boolean registerSPI(SPI spi) {
		_lock.lock();

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

			SPIProvider spiProvider = _spiProviders.get(spiProviderName);

			if (spiProvider == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Not registering SPI " + spi +
							" with unknown SPI provider " + spiProviderName);
				}

				return false;
			}

			SPIConfiguration spiConfiguration = spi.getSPIConfiguration();

			SPIKey spiKey = new SPIKey(
				spiProviderName, spiConfiguration.getSPIId());

			SPI previousSPI = _spis.putIfAbsent(spiKey, spi);

			if (previousSPI != null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Not registering SPI " + spi +
							" because it duplicates " + previousSPI);
				}

				return false;
			}
			else {
				SPIRegistryUtil.registerSPI(spi);

				if (_log.isInfoEnabled()) {
					_log.info("Registered SPI " + spi);
				}

				return true;
			}
		}
		catch (RemoteException re) {
			throw new RuntimeException(re);
		}
		finally {
			_lock.unlock();
		}
	}

	public static boolean registerSPIProvider(SPIProvider spiProvider) {
		String spiProviderName = spiProvider.getName();

		SPIProvider previousSPIProvider = null;

		_lock.lock();

		try {
			previousSPIProvider = _spiProviders.putIfAbsent(
				spiProviderName, spiProvider);
		}
		finally {
			_lock.unlock();
		}

		if (previousSPIProvider != null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Not registering SPI provider " + spiProvider +
						" because it duplicates " + previousSPIProvider);
			}

			return false;
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info("Registered SPI provider " + spiProvider);
			}

			return true;
		}
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
	}

	public static boolean unregisterSPI(SPI spi) {
		_lock.lock();

		try {
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

			SPIProvider spiProvider = _spiProviders.get(spiProviderName);

			if (spiProvider == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Not unregistering SPI " + spi +
							" with unknown SPI provider " + spiProviderName);
				}

				return false;
			}

			SPIConfiguration spiConfiguration = spi.getSPIConfiguration();

			String spiId = spiConfiguration.getSPIId();

			SPIKey spiKey = new SPIKey(spiProviderName, spiId);

			if (_spis.remove(spiKey, spi)) {
				SPIRegistryUtil.unregisterSPI(spi);

				if (_log.isInfoEnabled()) {
					_log.info("Unregistered SPI " + spi);
				}

				return true;
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn("Not unregistering unregistered SPI " + spi);
				}

				return false;
			}
		}
		catch (RemoteException re) {
			throw new RuntimeException(re);
		}
		finally {
			_lock.unlock();
		}
	}

	public static boolean unregisterSPIProvider(SPIProvider spiProvider) {
		_lock.lock();

		try {
			String spiProviderName = spiProvider.getName();

			if (_spiProviders.remove(spiProviderName, spiProvider)) {
				for (Map.Entry<SPIKey, SPI> entry : _spis.entrySet()) {
					SPIKey spiKey = entry.getKey();

					if (!spiProviderName.equals(spiKey._spiProviderName)) {
						continue;
					}

					SPI spi = entry.getValue();

					try {
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
								" while unregistering SPI provider " +
									spiProvider,
							re);
					}
				}

				if (_log.isInfoEnabled()) {
					_log.info("Unregistered SPI provider " + spiProvider);
				}

				return true;
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Not unregistering unregistered SPI provider " +
							spiProvider);
				}

				return false;
			}
		}
		finally {
			_lock.unlock();
		}
	}

	private static SPI _checkSPILiveness(SPI spi) {
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

	private static Log _log = LogFactoryUtil.getLog(MPIHelperUtil.class);

	private static Intraband _intraband;
	private static Lock _lock = new ReentrantLock();
	private static MPI _mpi;
	private static MPI _mpiImpl;
	private static ConcurrentMap<String, SPIProvider> _spiProviders =
		new ConcurrentHashMap<String, SPIProvider>();
	private static ConcurrentMap<SPIKey, SPI> _spis =
		new ConcurrentHashMap<SPIKey, SPI>();

	private static class MPIImpl implements MPI {

		@Override
		public boolean isAlive() {
			return true;
		}

	}

	private static class SPIKey {

		public SPIKey(String spiProviderName, String spiId) {
			_spiProviderName = spiProviderName;
			_spiId = spiId;
		}

		@Override
		public boolean equals(Object obj) {
			SPIKey spiKey = (SPIKey)obj;

			if (Validator.equals(
					_spiProviderName, spiKey._spiProviderName) &&
				Validator.equals(_spiId, spiKey._spiId)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			return _spiProviderName.hashCode() * 11 + _spiId.hashCode();
		}

		@Override
		public String toString() {
			return _spiProviderName.concat(StringPool.POUND).concat(_spiId);
		}

		private String _spiId;
		private String _spiProviderName;

	}

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

			_mpi = (MPI)UnicastRemoteObject.exportObject(_mpiImpl, 0);
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

}