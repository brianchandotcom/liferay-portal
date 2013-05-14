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

package com.liferay.portal.kernel.process;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Shuyang Zhou
 */
public class ConsumerOutputProcessor implements OutputProcessor<Void, Void> {

	@Override
	public Void processStdErr(InputStream stdErrInputStream)
		throws ProcessException {

		_consume(stdErrInputStream);

		return null;
	}

	@Override
	public Void processStdOut(InputStream stdOutInputStream)
		throws ProcessException {

		_consume(stdOutInputStream);

		return null;
	}

	private void _consume(InputStream inputStream) throws ProcessException {
		byte[] buffer = new byte[1024];

		try {
			while (inputStream.read(buffer) != -1);
		}
		catch (IOException ioe) {
			throw new ProcessException(ioe);
		}
		finally {
			try {
				inputStream.close();
			}
			catch (IOException ioe) {
				throw new ProcessException(ioe);
			}
		}
	}

}