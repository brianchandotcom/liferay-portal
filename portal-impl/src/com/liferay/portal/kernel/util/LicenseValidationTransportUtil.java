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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.util.transport.MulticastTransport;

import java.lang.reflect.Field;

/**
 * @author Daniel Sanz
 */
public class LicenseValidationTransportUtil {

	public static void stopMulticastTransportThread() {
		try {
			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

			Class licenseManagerClass = classLoader.loadClass(
				"com.liferay.portal.license.LicenseManager");

			Field[] fields = licenseManagerClass.getDeclaredFields();

			for (Field field : fields) {
				Class<?> type = field.getType();

				String typeName = type.getName();

				if (!typeName.equals(
						"com.liferay.util.transport.MulticastTransport")) {

					continue;
				}

				field.setAccessible(true);

				try {
					Object value = field.get(null);

					MulticastTransport multicastTransport =
						(MulticastTransport)value;

					if (multicastTransport != null) {
						multicastTransport.disconnect();
					}
				}
				catch (IllegalAccessException iae) {
					iae.printStackTrace();
				}
				finally {
					field.setAccessible(false);
				}
			}
		}
		catch (ClassNotFoundException cnfe) {
			if (_log.isDebugEnabled()) {
				_log.debug(cnfe);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		LicenseValidationTransportUtil.class);

}