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

package com.liferay.portal.kernel.process;

import java.io.InputStream;

/**
 * An <class>OutputConsumer</class> instance reads output from a process'
 * stdout.
 *
 * @param <V> desired return value of the output from the process' stdout
 *
 * @author Ivica Cardic
 */
public interface NativeProcessOutputConsumer<V> {

	/**
	 * Reads the output of a process from the given <class>InputStream</class>
	 *
	 * @param inputStream  the input stream of the process
	 * @return the output from the input stream of the process
	 * @throws Exception if an unexpected error occurred
	 */
	public V consumeOutput(InputStream inputStream) throws Exception;

}