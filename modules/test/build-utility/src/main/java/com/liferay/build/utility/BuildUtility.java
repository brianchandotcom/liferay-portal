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

package com.liferay.build.utility;

/**
 * @author Peter Yoo
 */
public class BuildUtility {
	
	public static String expandSlaveRange(String value) {
		StringBuilder sb = new StringBuilder();

		for (String hostName : value.split(",")) {
			hostName = hostName.trim();
			
			int x = hostName.indexOf("..");

			if (x == -1) {
				sb.append(hostName);				
				sb.append(",");

				continue;
			}

			int y = hostName.lastIndexOf("-") + 1;

			String prefix = hostName.substring(0, y);

			int first =
				Integer.parseInt(hostName.substring(y, x));
			int last =
				Integer.parseInt(hostName.substring(x + 2));

			for (int current = first; current <= last; current++) {
				sb.append(prefix);
				sb.append(current);
				if (current < last) {
					sb.append(",");
				}
			}
			
			sb.append(",");
		}
		String newValue = sb.toString();

		return newValue.substring(0, newValue.length() - 1);
	}

}
