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

package com.liferay.portal.tools.sass;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.concurrent.Callable;

/**
 * @author Minhchau Dang
 */
public abstract class TimedSassFragment extends BaseSassFragment
	implements Callable<TimedSassFragment> {

	public TimedSassFragment(String fileName) {
		_fileName = fileName;
	}

	@Override
	public TimedSassFragment call() throws Exception {
		long start = System.currentTimeMillis();

		doCall();

		long end = System.currentTimeMillis();

		_elapsedTime = (end - start);

		return this;
	}

	protected abstract void doCall() throws Exception;

	protected long getElapsedTime() {
		return _elapsedTime;
	}

	protected String getFileName() {
		return _fileName;
	}

	private static Log _log = LogFactoryUtil.getLog(TimedSassFragment.class);

	private long _elapsedTime;
	private String _fileName;

}