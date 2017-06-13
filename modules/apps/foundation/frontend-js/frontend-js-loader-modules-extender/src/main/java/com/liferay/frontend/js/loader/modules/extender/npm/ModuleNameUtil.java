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
	 * Returns the module ID with the NPM package and module name.
	 *
	 * @param  jsPackage the NPM package
	 * @param  moduleName the module's name
	 * @return the module ID
	 */
	public static String getModuleId(JSPackage jsPackage, String moduleName) {
		StringBundler sb = new StringBundler(3);

		sb.append(jsPackage.getId());
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
	 * </pre>
	 * </p>
	 *
	 * @param  moduleName the module's name
	 * @return the package name
	 */
	public static String getPackageName(String moduleName) {
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
	 * </pre>
	 * </p>
	 *
	 * @param  moduleName the module's name
	 * @return the path portion of a full module name
	 */
	public static String getPackagePath(String moduleName) {
		int i = moduleName.indexOf(StringPool.SLASH);

		if ((moduleName.charAt(0) == CharPool.AT) && (i != -1)) {
			i = moduleName.indexOf(StringPool.SLASH, i + 1);
		}

		if (i == -1) {
			return null;
		}

		return moduleName.substring(i + 1);
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
		int i = fileName.lastIndexOf(CharPool.PERIOD);

		if (i == -1) {
			return fileName;
		}

		return fileName.substring(0, i);
	}

}