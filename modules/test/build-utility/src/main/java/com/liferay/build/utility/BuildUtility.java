package com.liferay.build.utility;

public class BuildUtility {
	
	public static String expandSlaveRange(String value) {
		StringBuilder sb = new StringBuilder();

		for (String slaveHostname : value.split(",")) {
			int x = slaveHostname.indexOf("..");

			if (x == -1) {
				sb.append(slaveHostname);

				continue;
			}

			int y = slaveHostname.lastIndexOf("-") + 1;

			String slaveHostnamePrefix = slaveHostname.substring(0, y);

			int firstSlaveHostnameIndex = Integer.parseInt(slaveHostname.substring(y, x));
			int lastSlaveHostnameIndex = Integer.parseInt(slaveHostname.substring(x + 2));

			for (int currentSlaveHostnameIndex = firstSlaveHostnameIndex; currentSlaveHostnameIndex <= lastSlaveHostnameIndex; currentSlaveHostnameIndex++) {
				sb.append(slaveHostnamePrefix);
				sb.append(currentSlaveHostnameIndex);
				sb.append(",");
			}
		}

		value = sb.toString();		
	}

}
