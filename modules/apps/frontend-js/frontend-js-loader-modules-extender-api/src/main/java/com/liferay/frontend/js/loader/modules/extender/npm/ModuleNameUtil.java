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

package com.liferay.frontend.js.loader.modules.extender.npm;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Provides utility methods to manipulate module names.
 *
 * <p>
 * There are several concepts that must be understood when using this class:
 * </p>
 *
 * <ul>
 * <li>
 * <b>module name</b>: the name of an NPM module deployed to the portal
 * <ul>
 * <li>
 * Syntax: <code>{module name}</code>
 * </li>
 * <li>
 * Example: <code>lib/index</code>
 * </li>
 * </ul>
 *
 * </li>
 * <li>
 * <b>module file name</b>: the file name implementing an NPM module deployed to
 * the portal
 *
 * <ul>
 * <li>
 * Syntax: <code>{module file name}</code>
 * </li>
 * <li>
 * Example: <code>lib/index.js</code>
 * </li>
 * </ul>
 * <li>
 * <b>package ID:</b> the unique ID of an NPM package deployed to the portal
 *
 * <ul>
 * <li>
 * Syntax: <code>{bundle ID}/{package name}@{package version}</code>
 * </li>
 * <li>
 * Example: <code>625/isarray@1.0.0</code>
 * </li>
 * </ul>
 * <li>
 * <b>module ID</b>: the unique ID of an NPM module deployed to the portal
 *
 * <ul>
 * <li>
 * Syntax: <code>{bundle ID}/{package name}@{package version}/{module
 * name}</code>
 * </li>
 * <li>
 * Example: <code>625/isarray@1.0.0/lib/index</code>
 * </li>
 * </ul>
 *
 * </li>
 * </ul>
 *
 * @author Iván Zaera
 */
public class ModuleNameUtil {

	/**
	 * Resolve dependency path based on current module's path.
	 *
	 * @param  moduleName the module's name
	 * @param  dependency the dependency's name
	 * @return the full path of the dependency if it is local, the given
	 *         dependency otherwise
	 * @review
	 */
	public static String getDependencyPath(
		String moduleName, String dependency) {

		if (!isLocalModuleName(dependency)) {
			return dependency;
		}

		List<String> moduleDirNameParts = _getDirNameParts(moduleName);

		if (dependency.equals(StringPool.PERIOD)) {
			return StringUtil.merge(moduleDirNameParts, StringPool.SLASH);
		}

		if (dependency.equals("..")) {
			return StringUtil.merge(
				moduleDirNameParts.subList(0, moduleDirNameParts.size() - 1),
				StringPool.SLASH);
		}

		List<String> dependencyDirNameParts = _getDirNameParts(dependency);

		for (String dependencyDirNamePart : dependencyDirNameParts) {
			if (dependencyDirNamePart.equals(StringPool.PERIOD)) {
				continue;
			}

			if (dependencyDirNamePart.equals(StringPool.DOUBLE_PERIOD)) {
				if (moduleDirNameParts.isEmpty()) {
					throw new IllegalArgumentException(
						"Invalid dependency " + dependency);
				}

				moduleDirNameParts.remove(moduleDirNameParts.size() - 1);
			}
			else {
				moduleDirNameParts.add(dependencyDirNamePart);
			}
		}

		moduleDirNameParts.add(_getFileName(dependency));

		return StringUtil.merge(moduleDirNameParts, StringPool.SLASH);
	}

	/**
	 * Returns the module ID with the NPM package and module name.
	 *
	 * @param  jsPackage the NPM package
	 * @param  moduleName the module's name
	 * @return the module ID
	 * @review
	 */
	public static String getModuleId(JSPackage jsPackage, String moduleName) {
		StringBundler sb = new StringBundler(3);

		sb.append(jsPackage.getId());
		sb.append(StringPool.SLASH);
		sb.append(moduleName);

		return sb.toString();
	}

	/**
	 * Returns the module resolved ID with the NPM package and module name.
	 *
	 * @param  jsPackage the NPM package
	 * @param  moduleName the module's name
	 * @return the module ID
	 * @review
	 */
	public static String getModuleResolvedId(
		JSPackage jsPackage, String moduleName) {

		StringBundler sb = new StringBundler(3);

		sb.append(jsPackage.getResolvedId());
		sb.append(StringPool.SLASH);
		sb.append(moduleName);

		return sb.toString();
	}

	/**
	 * Returns the package name portion of a full module name.
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * <code>
	 * getPackageName("isarray/lib/index") returns "isarray"
	 * </code>
	 * </pre></p>
	 *
	 * @param  moduleName the module's name
	 * @return the package name or <code>null</code> if the module name is a
	 *         reserved or local one
	 */
	public static String getPackageName(String moduleName) {
		if (isLocalModuleName(moduleName)) {
			return null;
		}

		if (isReservedModuleName(moduleName)) {
			return null;
		}

		int i = moduleName.indexOf(StringPool.SLASH);

		if ((moduleName.charAt(0) == CharPool.AT) && (i != -1)) {
			i = moduleName.indexOf(StringPool.SLASH, i + 1);
		}

		if (i == -1) {
			return moduleName;
		}

		return moduleName.substring(0, i);
	}

	/**
	 * Returns the path portion of a full module name.
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * <code>
	 * getPackagePath("isarray/lib/index") returns "lib/index"
	 * </code>
	 * </pre></p>
	 *
	 * @param  moduleName the module's name
	 * @return the path portion of a full module name or <code>null</code> in
	 *         any other case
	 */
	public static String getPackagePath(String moduleName) {
		if (isLocalModuleName(moduleName)) {
			return null;
		}

		int i = moduleName.indexOf(StringPool.SLASH);

		if ((moduleName.charAt(0) == CharPool.AT) && (i != -1)) {
			i = moduleName.indexOf(StringPool.SLASH, i + 1);
		}

		if (i == -1) {
			return null;
		}

		return moduleName.substring(i + 1);
	}

	public static boolean isLocalModuleName(String moduleName) {
		if (moduleName.equals(StringPool.PERIOD) || moduleName.equals("..") ||
			moduleName.startsWith("./") || moduleName.startsWith("../")) {

			return true;
		}

		return false;
	}

	public static boolean isReservedModuleName(String moduleName) {
		return _reservedModuleNames.contains(moduleName);
	}

	/**
	 * Returns the file name implementing the module.
	 *
	 * @param  moduleName the module's name
	 * @return the file name
	 */
	public static String toFileName(String moduleName) {
		return moduleName + ".js";
	}

	/**
	 * Returns the module's name given the file name implementing it.
	 *
	 * @param  fileName the file name implementing the module
	 * @return the module's name
	 */
	public static String toModuleName(String fileName) {
		String extension = FileUtil.getExtension(fileName);

		if (!extension.equals("js") || extension.isEmpty()) {
			return fileName;
		}

		return fileName.substring(
			0, fileName.length() - extension.length() - 1);
	}

	private static List<String> _getDirNameParts(String modulePath) {
		List<String> modulePathParts = new ArrayList<>(
			Arrays.asList(modulePath.split(StringPool.SLASH)));

		modulePathParts.remove(modulePathParts.size() - 1);

		return modulePathParts;
	}

	private static String _getFileName(String dependency) {
		int pos = dependency.lastIndexOf(StringPool.SLASH);

		return dependency.substring(pos + 1);
	}

	private static final Set<String> _reservedModuleNames = new HashSet<>(
		Arrays.asList("exports", "module", "require"));

}