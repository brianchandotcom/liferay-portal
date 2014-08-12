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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.SessionAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.text.NumberFormat;

import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 */
public class GarbageCollectorAction extends SessionAction {

	@Override
	public void run(HttpSession session) {
		Runtime runtime = Runtime.getRuntime();

		NumberFormat nf = NumberFormat.getInstance();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Before:\t\t" +
					nf.format(runtime.freeMemory()) + "\t" +
						nf.format(runtime.totalMemory()) + "\t" +
							nf.format(runtime.maxMemory()));
		}

		System.gc();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"After:\t\t" +
					nf.format(runtime.freeMemory()) + "\t" +
						nf.format(runtime.totalMemory()) + "\t" +
							nf.format(runtime.maxMemory()));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GarbageCollectorAction.class);

}