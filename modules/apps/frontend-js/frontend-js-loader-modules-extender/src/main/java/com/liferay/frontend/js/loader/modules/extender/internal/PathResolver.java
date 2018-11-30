package com.liferay.frontend.js.loader.modules.extender.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Rodolfo Roza Miranda
 */
public class PathResolver {

	public static String resolvePath(String root, String dependency) {

		if (
			dependency.equals("require") ||
				dependency.equals("exports") ||
				dependency.equals("module") ||
				!(dependency.startsWith(".") || dependency.startsWith(".."))
		) {
			return dependency;
		}

		// Split module directories
		List<String> moduleParts = new ArrayList<>(Arrays.asList(root.split("/")));

		// Remove module name
		moduleParts.remove(moduleParts.size() - 1);

		// Split dependency directories
		List<String> dependencyParts = new ArrayList<>(Arrays.asList(dependency.split("/")));

		// Extract dependency name
		String dependencyName = dependencyParts.remove(dependencyParts.size() - 1);

		for (int i = 0; i < dependencyParts.size(); i++) {
			String dependencyPart = dependencyParts.get(i);

			if (dependencyPart.equals(".")) {
				continue;
			}

			if (dependencyPart.equals("..")) {
				if (moduleParts.size() > 0) {
					moduleParts.remove(moduleParts.size() - 1);
				}
				else {
					moduleParts.addAll(dependencyParts.subList(i, dependencyParts.size()));
					break;
				}
			}
			else {
				moduleParts.add(dependencyPart);
			}
		}

		moduleParts.add(dependencyName);

		return String.join("/", moduleParts);
	}
}
