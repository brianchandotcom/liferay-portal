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

package com.liferay.document.library.asset.auto.tagger.tensorflow.internal.petra.process;

import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessException;
import com.liferay.petra.process.local.LocalProcessLauncher;

/**
 * @author Shuyang Zhou
 */
public class TensorFlowDaemonProcessCallable
	implements ProcessCallable<String> {

	@Override
	public String call() throws ProcessException {
		LocalProcessLauncher.ProcessContext.attach(
			"TensorFlow-Daemon", 10000,
			(shutdownCode, shutdownThrowable) -> {
				System.exit(shutdownCode);

				return true;
			});

		try {
			Thread.sleep(Long.MAX_VALUE);
		}
		catch (InterruptedException ie) {
			throw new ProcessException(ie);
		}

		return "DONE";
	}

	private static final long serialVersionUID = 1L;

}