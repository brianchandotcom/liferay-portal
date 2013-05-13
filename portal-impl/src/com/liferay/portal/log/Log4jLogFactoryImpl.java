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

package com.liferay.portal.log;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactory;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.util.log4j.Log4JUtil;

import org.apache.log4j.LogManager;

/**
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
public class Log4jLogFactoryImpl implements LogFactory {

	@Override
	public Log getLog(Class<?> c) {
		return getLog(c.getName());
	}

	@Override
	public Log getLog(String name) {
		return new Log4jLogImpl(LogManager.getLogger(name));
	}

	@Override
	public void setLevel(String name, String priority, boolean custom) {
		Log4JUtil.setLevel(name, priority, custom);
	}

}