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

package com.liferay.portal.servlet.filters.threaddump;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ThreadUtil;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class ThreadDumper implements Runnable {

	public boolean isExecuted() {
		return _executed;
	}

	@Override
	public void run() {
		if (_log.isInfoEnabled()) {
			_log.info(ThreadUtil.threadDump());
		}

		_executed = true;
	}

	private static Log _log = LogFactoryUtil.getLog(ThreadDumper.class);

	private boolean _executed;

}