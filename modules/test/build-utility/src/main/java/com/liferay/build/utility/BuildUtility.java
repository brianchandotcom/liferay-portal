package com.liferay.build.utility;

public class BuildUtility {
	
	public static String expandSlaveRange(String value) {
		StringBuilder sb = new StringBuilder();

		for (String hostName : value.split(",")) {
			int x = hostName.indexOf("..");

			if (x == -1) {
				sb.append(hostName);

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
				sb.append(",");
			}
		}

		return sb.toString();		
	}

}
