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
import com.liferay.portal.kernel.resiliency.spi.SPIRuntimeMappingUtil;
import com.liferay.portal.kernel.resiliency.spi.provider.SPIProvider;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;

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
public class MPIUtil {

	public static Intraband getIntraband() {
		return _intraband;
	}

	public static MPI getMPIStub() {
		return _mpiStub;
	}

	public static SPI getSPI(String spiProviderName, String spiId) {
		SPIKey spiKey = new SPIKey(spiProviderName, spiId);

		SPI spi = _spiMap.get(spiKey);

		if (spi != null) {
			spi = _checkSPILiveness(spi);
		}

		return spi;
	}

	public static SPIProvider getSPIProvider(String spiProviderName) {
		return _spiProviderMap.get(spiProviderName);
	}

	public static List<SPIProvider> getSPIProviders() {
		return new ArrayList<SPIProvider>(_spiProviderMap.values());
	}

	public static List<SPI> getSPIs() {
		List<SPI> spiList = new ArrayList<SPI>();

		for (SPI spi : _spiMap.values()) {
			spi = _checkSPILiveness(spi);

			if (spi != null) {
				spiList.add(spi);
			}
		}

		return spiList;
	}

	public static List<SPI> getSPIs(String spiProviderName) {
		List<SPI> spiList = new ArrayList<SPI>();

		for (Map.Entry<SPIKey, SPI> entry : _spiMap.entrySet()) {
			SPIKey spiKey = entry.getKey();

			if (spiKey._spiProviderName.equals(spiProviderName)) {
				SPI spi = entry.getValue();

				spi = _checkSPILiveness(spi);

				if (spi != null) {
					spiList.add(spi);
				}
			}
		}

		return spiList;
	}

	public static boolean registerSPI(SPI spi) {
		_registerLock.lock();

		try {
			MPI mpi = spi.getMPI();

			if (mpi != _mpiStub) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to register SPI instance " + spi +
							". It was created by a foreign MPI " + mpi +
								", not by " + _mpiStub);
				}

				return false;
			}

			String spiProviderName = spi.getSPIProviderName();

			SPIProvider registeredSPIProvider = _spiProviderMap.get(
				spiProviderName);

			if (registeredSPIProvider == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to register SPI instance " + spi +
							". No such SPIProvider registered with name " +
								spiProviderName);
				}

				return false;
			}

			SPIConfiguration spiConfiguration = spi.getSPIConfiguration();

			String spiId = spiConfiguration.getId();

			SPIKey spiKey = new SPIKey(spiProviderName, spiId);

			SPI previousSPI = _spiMap.putIfAbsent(spiKey, spi);

			if (previousSPI != null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to register SPI instance " + spi +
							" with key " + spiKey + ". There is already a " +
								"spi with the same key registered " +
									previousSPI);
				}

				return false;
			}
			else {
				SPIRuntimeMappingUtil.register(spi);

				if (_log.isInfoEnabled()) {
					_log.info(
						"Registered new SPI " + spi + " with key " + spiKey);
				}

				return true;
			}
		}
		catch (RemoteException re) {
			throw new RuntimeException(re);
		}
		finally {
			_registerLock.unlock();
		}
	}

	public static boolean registerSPIProvider(SPIProvider spiProvider) {
		String spiProviderName = spiProvider.getName();

		SPIProvider previousSPIProvider = null;

		_registerLock.lock();

		try {
			previousSPIProvider = _spiProviderMap.putIfAbsent(
				spiProviderName, spiProvider);
		}
		finally {
			_registerLock.unlock();
		}

		if (previousSPIProvider != null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Can not register SPIProvider " + spiProvider +
						" with name " + spiProviderName +
							". There is already a SPIProvider with the " +
								"same name registered " + previousSPIProvider);
			}

			return false;
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Registered new SPIProvider " + spiProvider +
						" with name " + spiProviderName);
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
				_log.warn("Unable to unexport MPIImpl", nsoe);
			}
		}
	}

	public static boolean unregisterSPI(SPI spi) {
		_registerLock.lock();

		try {
			MPI mpi = spi.getMPI();

			if (mpi != _mpiStub) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to unregister SPI instance " + spi +
							". It was created by a foreign MPI " + mpi +
								", not by " + _mpiStub);
				}

				return false;
			}

			String spiProviderName = spi.getSPIProviderName();

			SPIProvider registeredSPIProvider = _spiProviderMap.get(
				spiProviderName);

			if (registeredSPIProvider == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to unregister SPI instance " + spi +
							". No such SPIProvider registered with name " +
								spiProviderName);
				}

				return false;
			}

			SPIConfiguration spiConfiguration = spi.getSPIConfiguration();

			String spiId = spiConfiguration.getId();

			SPIKey spiKey = new SPIKey(spiProviderName, spiId);

			boolean result = _spiMap.remove(spiKey, spi);

			if (result) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Unregistered SPI " + spi + " with key " + spiKey);
				}
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to unregister SPI " + spi + " with key " + spiKey +
						". It is not registered");
			}

			SPIRuntimeMappingUtil.unregister(spi);

			return result;
		}
		catch (RemoteException re) {
			throw new RuntimeException(re);
		}
		finally {
			_registerLock.unlock();
		}
	}

	public static boolean unregisterSPIProvider(SPIProvider spiProvider) {
		String spiProviderName = spiProvider.getName();

		boolean result = false;

		_registerLock.lock();

		try {
			result = _spiProviderMap.remove(spiProviderName, spiProvider);

			if (result) {
				for (Map.Entry<SPIKey, SPI> entry : _spiMap.entrySet()) {
					SPIKey spiKey = entry.getKey();

					if (spiKey._spiProviderName.equals(spiProviderName)) {
						SPI spi = entry.getValue();

						try {
							spi.destroy();

							if (_log.isInfoEnabled()) {
								_log.info(
									"Cascaded unregistered SPI " + spi +
										" with key " + spiKey);
							}
						}
						catch (RemoteException re) {
							_log.error(
								"Failed cascaded unregister SPI " + spi +
									" with key " + spiKey, re);
						}
					}
				}

				if (_log.isInfoEnabled()) {
					_log.info(
						"Unregistered SPIProvider " + spiProvider +
							" with name " + spiProviderName);
				}
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to unregister SPIProvider " + spiProvider +
						" with name " + spiProviderName +
							". It is not registered");
			}
		}
		finally {
			_registerLock.unlock();
		}

		return result;
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

	private static final Intraband _intraband;

	private static final MPI _mpiImpl;

	private static final MPI _mpiStub;

	private static final Lock _registerLock = new ReentrantLock();

	private static final ConcurrentMap<SPIKey, SPI> _spiMap =
		new ConcurrentHashMap<SPIKey, SPI>();

	private static final ConcurrentMap<String, SPIProvider> _spiProviderMap =
		new ConcurrentHashMap<String, SPIProvider>();

	private static Log _log = LogFactoryUtil.getLog(MPIUtil.class);

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

			if (_spiProviderName.equals(spiKey._spiProviderName) &&
				_spiId.equals(spiKey._spiId)) {

				return true;
			}
			else {
				return false;
			}
		}

		@Override
		public int hashCode() {
			return _spiProviderName.hashCode() * 11 + _spiId.hashCode();
		}

		@Override
		public String toString() {
			return _spiProviderName.concat(StringPool.POUND).concat(_spiId);
		}

		private final String _spiId;
		private final String _spiProviderName;

	}

	static {

		// Keep strong reference to stop gc

		_mpiImpl = new MPIImpl();

		try {
			if (PropsUtil.getProps() != null) {

				// This is MPI, propagate portal.properties to System properties

				System.setProperty(
					PropsKeys.INTRABAND_TIMEOUT_DEFAULT,
					PropsUtil.get(PropsKeys.INTRABAND_TIMEOUT_DEFAULT));
				System.setProperty(
					PropsKeys.INTRABAND_IMPL,
					PropsUtil.get(PropsKeys.INTRABAND_IMPL));
				System.setProperty(
					PropsKeys.INTRABAND_WELDER_IMPL,
					PropsUtil.get(PropsKeys.INTRABAND_WELDER_IMPL));
			}

			_intraband = IntrabandFactoryUtil.createIntraband();

			_mpiStub = (MPI)UnicastRemoteObject.exportObject(_mpiImpl, 0);
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

}