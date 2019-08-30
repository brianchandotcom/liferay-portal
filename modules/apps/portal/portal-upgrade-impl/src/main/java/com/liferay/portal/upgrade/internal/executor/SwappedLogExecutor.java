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

package com.liferay.portal.upgrade.internal.executor;

import com.liferay.portal.output.stream.container.OutputStreamContainer;
import com.liferay.portal.output.stream.container.OutputStreamContainerFactory;
import com.liferay.portal.output.stream.container.OutputStreamContainerFactoryTracker;

import java.io.IOException;
import java.io.OutputStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mariano Álvaro Sáiz
 */
@Component(immediate = true, service = SwappedLogExecutor.class)
public class SwappedLogExecutor {

	public void execute(String bundleSymbolicName, Runnable runnable) {
		OutputStreamContainerFactory outputStreamContainerFactory =
			_outputStreamContainerFactoryTracker.
				getOutputStreamContainerFactory();

		OutputStreamContainer outputStreamContainer =
			outputStreamContainerFactory.create(
				"upgrade-" + bundleSymbolicName);

		try (OutputStream outputStream =
				outputStreamContainer.getOutputStream()) {

			_outputStreamThreadLocal.set(outputStream);

			_outputStreamContainerFactoryTracker.runWithSwappedLog(
				runnable, outputStreamContainer.getDescription(), outputStream);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		finally {
			_outputStreamThreadLocal.remove();
		}
	}

	public OutputStream getOutputStream() {
		return _outputStreamThreadLocal.get();
	}

	@Reference
	private OutputStreamContainerFactoryTracker
		_outputStreamContainerFactoryTracker;

	private final ThreadLocal<OutputStream> _outputStreamThreadLocal =
		new ThreadLocal<>();

}