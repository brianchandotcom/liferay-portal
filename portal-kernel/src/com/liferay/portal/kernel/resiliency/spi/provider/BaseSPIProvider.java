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

package com.liferay.portal.kernel.resiliency.spi.provider;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.welder.Welder;
import com.liferay.portal.kernel.process.ProcessChannel;
import com.liferay.portal.kernel.process.ProcessConfig;
import com.liferay.portal.kernel.process.ProcessExecutor;
import com.liferay.portal.kernel.resiliency.PortalResiliencyException;
import com.liferay.portal.kernel.resiliency.mpi.MPIHelperUtil;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.remote.RemoteSPI;
import com.liferay.portal.kernel.resiliency.spi.remote.RemoteSPIProxy;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Shuyang Zhou
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
@Deprecated
public abstract class BaseSPIProvider implements SPIProvider {

	public abstract RemoteSPI createRemoteSPI(SPIConfiguration spiConfiguration)
		throws PortalResiliencyException;

	@Override
	public SPI createSPI(SPIConfiguration spiConfiguration)
		throws PortalResiliencyException {

		ProcessConfig.Builder builder = new ProcessConfig.Builder();

		builder.setArguments(spiConfiguration.getJVMArguments());
		builder.setBootstrapClassPath(getClassPath());
		builder.setJavaExecutable(spiConfiguration.getJavaExecutable());
		builder.setReactClassLoader(PortalClassLoaderUtil.getClassLoader());
		builder.setRuntimeClassPath(getClassPath());

		RemoteSPI remoteSPI = createRemoteSPI(spiConfiguration);

		String spiUUID = remoteSPI.getUUID();

		SynchronousQueue<SPI> synchronousQueue =
			SPISynchronousQueueUtil.createSynchronousQueue(spiUUID);

		FutureTask<RegistrationReference> weldServerFutureTask =
			new FutureTask<>(new WeldServerCallable(remoteSPI.getWelder()));

		Thread weldServerThread = new Thread(
			weldServerFutureTask,
			"Weld Server Thread for " + spiConfiguration.getSPIId());

		weldServerThread.setDaemon(true);

		weldServerThread.start();

		try {
			ProcessChannel<SPI> processChannel = _processExecutor.execute(
				builder.build(), remoteSPI);

			Future<SPI> cancelHandlerFuture =
				processChannel.getProcessNoticeableFuture();

			SPI spi = synchronousQueue.poll(
				spiConfiguration.getRegisterTimeout(), TimeUnit.MILLISECONDS);

			if (spi != null) {
				RegistrationReference registrationReference =
					weldServerFutureTask.get(
						spiConfiguration.getRegisterTimeout(),
						TimeUnit.MILLISECONDS);

				RemoteSPIProxy remoteSPIProxy = new RemoteSPIProxy(
					spi, spiConfiguration, getName(), cancelHandlerFuture,
					registrationReference);

				if (!MPIHelperUtil.registerSPI(remoteSPIProxy)) {
					cancelHandlerFuture.cancel(true);

					throw new PortalResiliencyException(
						"Unable to register SPI " + remoteSPIProxy +
							". Forcibly cancelled SPI process launch.");
				}

				return remoteSPIProxy;
			}

			cancelHandlerFuture.cancel(true);

			throw new PortalResiliencyException(
				"SPI synchronous queue waiting timeout. Forcibly cancelled " +
					"SPI process launch.");
		}
		catch (InterruptedException interruptedException) {
			throw new PortalResiliencyException(
				"Interrupted on waiting SPI process, registering back RMI stub",
				interruptedException);
		}
		catch (PortalResiliencyException portalResiliencyException) {
			throw portalResiliencyException;
		}
		catch (Exception exception) {
			throw new PortalResiliencyException(
				"Unable to launch SPI process", exception);
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

	private static volatile ProcessExecutor _processExecutor =
		ServiceProxyFactory.newServiceTrackedInstance(
			ProcessExecutor.class, BaseSPIProvider.class, "_processExecutor",
			true);

}