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

package com.liferay.portal.kernel.resiliency.spi.provider;

import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.welder.Welder;
import com.liferay.portal.kernel.process.ProcessExecutor;
import com.liferay.portal.kernel.resiliency.PortalResiliencyException;
import com.liferay.portal.kernel.resiliency.mpi.MPIUtil;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.remote.RemoteSPI;
import com.liferay.portal.kernel.resiliency.spi.remote.RemoteSPIStubHolder;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Shuyang Zhou
 */
public abstract class BaseSPIProvider implements SPIProvider {

	public abstract RemoteSPI createRemoteSPI(SPIConfiguration spiConfiguration)
		throws PortalResiliencyException;

	public SPI createSPI(SPIConfiguration spiConfiguration)
		throws PortalResiliencyException {

		String classPath = getClassPath();

		RemoteSPI remoteSPI = createRemoteSPI(spiConfiguration);

		String spiUUID = remoteSPI.getUUID();

		SynchronousQueue<SPI> spiSynchronizer =
			SPIRegisterSynchronizer.createSynchronizer(spiUUID);

		FutureTask<RegistrationReference> weldServerFutureTask =
			new FutureTask<RegistrationReference>(
				new WeldServerCallable(remoteSPI.getWelder()));

		Thread weldServerThread = new Thread(
			weldServerFutureTask,
			"Weld Server Thread for " + spiConfiguration.getId());

		weldServerThread.setDaemon(true);

		weldServerThread.start();

		try {
			Future<SPI> cancelHandlerFuture = ProcessExecutor.execute(
				spiConfiguration.getJavaExecutable(), classPath,
				spiConfiguration.getJVMArguments(), remoteSPI);

			SPI spiStub = spiSynchronizer.poll(
				spiConfiguration.getRegisterTimeout(), TimeUnit.MILLISECONDS);

			if (spiStub != null) {
				RegistrationReference registrationReference =
					weldServerFutureTask.get(
						spiConfiguration.getRegisterTimeout(),
						TimeUnit.MILLISECONDS);

				RemoteSPIStubHolder remoteSPIStubHolder =
					new RemoteSPIStubHolder(
						spiStub, spiConfiguration, getName(),
						cancelHandlerFuture, registrationReference);

				if (!MPIUtil.registerSPI(remoteSPIStubHolder)) {
					cancelHandlerFuture.cancel(true);

					throw new PortalResiliencyException(
						"Failed to register SPI instance " +
							remoteSPIStubHolder + ". Forcibly cancelled spi " +
								"process launching.");
				}

				return remoteSPIStubHolder;
			}
			else {
				cancelHandlerFuture.cancel(true);

				throw new PortalResiliencyException(
					"SPI synchronizer waiting timeout. Forcibly cancelled " +
						"spi process launching.");
			}
		}
		catch (InterruptedException ie) {
			throw new PortalResiliencyException(
				"Interrupted while waiting on SPI process to register back " +
					"the RMI Stub", ie);
		}
		catch (PortalResiliencyException pre) {
			throw pre;
		}
		catch (Exception e) {
			throw new PortalResiliencyException(
				"Failed to launch SPI process", e);
		}
		finally {
			weldServerFutureTask.cancel(true);

			SPIRegisterSynchronizer.destroySynchronizer(spiUUID);
		}
	}

	public abstract String getClassPath();

	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{name=");
		sb.append(getName());
		sb.append(", classPath=");
		sb.append(getClassPath());
		sb.append("}");

		return sb.toString();
	}

	protected static class WeldServerCallable
		implements Callable<RegistrationReference> {

		public WeldServerCallable(Welder welder) {
			_welder = welder;
		}

		public RegistrationReference call() throws Exception {
			return _welder.weldServer(MPIUtil.getIntraBand());
		}

		private final Welder _welder;

	}

}