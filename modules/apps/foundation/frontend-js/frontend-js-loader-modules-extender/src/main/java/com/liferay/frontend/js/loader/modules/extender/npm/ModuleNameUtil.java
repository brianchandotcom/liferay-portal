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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

/**
 * <p>
 * An utility class to manipulate module names.
 * </p>
 * <p>
 * There are several concepts that must be understood when using this class:
 * <ul>
 *     <li>
 *         <b>module name</b>:
 *		   the name of an NPM module deployed to the portal.
 *         Syntax: {module name}
 *         Example: lib/index
 *     </li>
 *     <li>
 *         <b>module file name</b>:
 *		   the name file implementing an NPM module deployed to the portal.
 *         Syntax: {module file name}
 *         Example: lib/index.js
 *     </li>
 *     <li>
 *         <b>package id:</b>
 *         the unique id of an NPM package deployed to the portal.
 *         Syntax: {bundle id}/{package name}@{package version}.
 *         Example: 625/isarray@1.0.0
 *     </li>
 *     <li>
 *         <b>module id</b>:
 *		   the unique id of an NPM module deployed to the portal.
 *         Syntax: {bundle id}/{package name}@{package version}/{module name}.
 *         Example: 625/isarray@1.0.0/lib/index
 *     </li>
 * </ul>
 * </p>
 * @author Iván Zaera
 */
public class ModuleNameUtil {

	/**
	 * Get a module id given an NPM package and the name of a module inside it.
	 * @param jsPackage an NPM package
	 * @param moduleName a module name
	 */
	public static String getModuleId(JSPackage jsPackage, String moduleName) {
		StringBundler sb = new StringBundler(3);

		sb.append(jsPackage.getId());
		sb.append(StringPool.SLASH);
		sb.append(moduleName);

		return sb.toString();
	}

	/**
	 * Get the package name portion of a full module name. For instance,
	 * given `isarray/lib/index` return `isarray`.
	 */
	public static String getPackageName(String moduleName) {
		int i = moduleName.indexOf(StringPool.SLASH);

		if (i == -1) {
			return moduleName;
		}

		return moduleName.substring(0, i);
	}

	/**
	 * Get the path portion of a full module name. For instance,
	 * given `isarray/lib/index` return `lib/index`.
	 */
	public static String getPackagePath(String moduleName) {
		int i = moduleName.indexOf(StringPool.SLASH);

		if (i == -1) {
			return null;
		}

		return moduleName.substring(i + 1);
	}

	/**
	 * Get the file name implementing a module.
	 * @param moduleName the module name
	 */
	public static String toFileName(String moduleName) {
		return moduleName + ".js";
	}

	/**
	 * Get the name of a module given the file name implementing it.
	 */
	public static String toModuleName(String fileName) {
		int i = fileName.lastIndexOf(CharPool.PERIOD);

		if (i == -1) {
			return fileName;
		}

		return fileName.substring(0, i);
	}

}