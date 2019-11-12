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

package com.liferay.portal.output.stream.container;

import java.io.OutputStream;

import java.util.Set;

/**
 * @author Matthew Tambara
 */
public interface OutputStreamContainerFactoryTracker {

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #getOutputStreamContainerFactory(String)}
	 */
	@Deprecated
	public OutputStreamContainerFactory getOutputStreamContainerFactory();

	public OutputStreamContainerFactory getOutputStreamContainerFactory(
		String outputStreamContainerFactoryName);

	public Set<String> getOutputStreamContainerFactoryNames();

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #runWithSwappedLog(Runnable, String, OutputStream)}
	 */
	@Deprecated
	public void runWithSwappedLog(Runnable runnable, String outputStreamHint);

	public void runWithSwappedLog(
		Runnable runnable, String outputStreamName, OutputStream outputStream);

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #runWithSwappedLog(Runnable, String, OutputStream)}
	 */
	@Deprecated
	public void runWithSwappedLog(
		Runnable runnable, String outputStreamHint,
		String outputStreamContainerName);

}