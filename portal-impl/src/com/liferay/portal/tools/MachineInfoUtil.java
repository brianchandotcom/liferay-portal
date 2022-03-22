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

package com.liferay.portal.tools;

import com.liferay.petra.string.StringPool;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

/**
 * @author Renato Rego
 */
public class MachineInfoUtil {

	public static void main(String[] args) {
		OperatingSystemMXBean operatingSystemMXBean =
			(OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();

		System.out.println("Architecture: " + operatingSystemMXBean.getArch());
		System.out.println("OS Name: " + operatingSystemMXBean.getName());
		System.out.println(
			"Processors: " + operatingSystemMXBean.getAvailableProcessors());

		double gigaBytesMemorySize = convertToGigaBytes(
			operatingSystemMXBean.getTotalPhysicalMemorySize());

		System.out.println("RAM: " + formatMemorySize(gigaBytesMemorySize));
	}

	protected static double convertToGigaBytes(long bytes) {
		return bytes / ONE_GIGA_BYTE_IN_BYTES;
	}

	protected static String formatMemorySize(double memorySize) {
		String memorySizeString = String.valueOf(memorySize);

		int indexOfPeriod = memorySizeString.indexOf(StringPool.PERIOD);

		int memorySizeStringEndIndex = indexOfPeriod + 2;

		if ((indexOfPeriod + 2) >= memorySizeString.length()) {
			memorySizeStringEndIndex = indexOfPeriod + 1;
		}

		return memorySizeString.substring(0, memorySizeStringEndIndex + 1) +
			" GB";
	}

	protected static final double ONE_GIGA_BYTE_IN_BYTES = 1073741824.0;

}