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

package com.liferay.portal.kernel.nio.intraband.welder;

import com.liferay.portal.kernel.nio.intraband.welder.fifo.FIFOUtil;
import com.liferay.portal.kernel.nio.intraband.welder.fifo.FIFOWelder;
import com.liferay.portal.kernel.nio.intraband.welder.socket.SocketWelder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OSDetector;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Shuyang Zhou
 */
public class WelderFactoryUtil {

	public static Welder createWelder() {
		Class<? extends Welder> welderClass = getWelderClass();

		try {
			return welderClass.newInstance();
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Unable to create Welder instance for class " + welderClass, e);
		}
	}

	public static Class<? extends Welder> getWelderClass() {
		if (Validator.isNotNull(_WELDER_IMPL_CLASS)) {
			try {
				return (Class<? extends Welder>)Class.forName(
					_WELDER_IMPL_CLASS);
			}
			catch (ClassNotFoundException cnfe) {
				throw new RuntimeException(
					"Unable to load Welder class with name " +
						_WELDER_IMPL_CLASS, cnfe);
			}
		}
		else {
			if (!OSDetector.isWindows() && FIFOUtil.isFIFOSupported()) {
				return FIFOWelder.class;
			}
			else {
				return SocketWelder.class;
			}
		}
	}

	private static final String _WELDER_IMPL_CLASS = GetterUtil.getString(
		System.getProperty(PropsKeys.INTRABAND_WELDER_IMPL_CLASS));

}