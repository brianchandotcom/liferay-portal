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

package com.liferay.portal.kernel.resiliency.spi.provider;

import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.welder.Welder;
import com.liferay.portal.kernel.process.ProcessExecutor;
import com.liferay.portal.kernel.resiliency.PortalResiliencyException;
import com.liferay.portal.kernel.resiliency.mpi.MPIHelperUtil;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.remote.RemoteSPI;
import com.liferay.portal.kernel.resiliency.spi.remote.RemoteSPIProxy;
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

	@Override
	public SPI createSPI(SPIConfiguration spiConfiguration)
		throws PortalResiliencyException {

		String classPath = getClassPath();

		RemoteSPI remoteSPI = createRemoteSPI(spiConfiguration);

		String spiUUID = remoteSPI.getUUID();

		SynchronousQueue<SPI> synchronousQueue =
			SPISynchronousQueueUtil.createSynchronousQueue(spiUUID);

		FutureTask<RegistrationReference> weldServerFutureTask =
			new FutureTask<RegistrationReference>(
				new WeldServerCallable(remoteSPI.getWelder()));

		Thread weldServerThread = new Thread(
			weldServerFutureTask,
			"Weld Server Thread for " + spiConfiguration.getSPIId());

		weldServerThread.setDaemon(true);

		weldServerThread.start();

		try {
			Future<SPI> cancelHandlerFuture = ProcessExecutor.execute(
				spiConfiguration.getJavaExecutable(), classPath,
				spiConfiguration.getJVMArguments(), remoteSPI);

			SPI spi = synchronousQueue.poll(
				spiConfiguration.getRegisterTimeout(), TimeUnit.MILLISECONDS);

			if (spi != null) {
				RegistrationReference registrationReference =
					weldServerFutureTask.get(
						spiConfiguration.getRegisterTimeout(),
						TimeUnit.MILLISECONDS);

				RemoteSPIProxy remoteSPIHolder =
					new RemoteSPIProxy(
						spi, spiConfiguration, getName(), cancelHandlerFuture,
						registrationReference);

				if (!MPIHelperUtil.registerSPI(remoteSPIHolder)) {
					cancelHandlerFuture.cancel(true);

					throw new PortalResiliencyException(
						"Unable to register SPI " + remoteSPIHolder +
							". Forcibly cancelled spi process launching.");
				}

				return remoteSPIHolder;
			}
			else {
				cancelHandlerFuture.cancel(true);

				throw new PortalResiliencyException(
					"SPI synchronous queue waiting timeout. Forcibly " +
						"cancelled spi process launching.");
			}
		}
		catch (InterruptedException ie) {
			throw new PortalResiliencyException(
				"Interrupted on waiting SPI process registering back RMI Stub",
				ie);
		}
		catch (PortalResiliencyException pre) {
			throw pre;
		}
		catch (Exception e) {
			throw new PortalResiliencyException(
				"Unable to launch SPI process", e);
		}
		finally {
			weldServerFutureTask.cancel(true);

			SPISynchronousQueueUtil.destroySynchronousQueue(spiUUID);
		}
	}

	public abstract String getClassPath();

	@Override
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

		@Override
		public RegistrationReference call() throws Exception {
			return _welder.weld(MPIHelperUtil.getIntraband());
		}

		private final Welder _welder;

	}

}