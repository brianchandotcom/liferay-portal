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
 * @author Iván Zaera
 */
public class ModuleNameUtil {

	public static String getModuleId(JSPackage jsPackage, String moduleName) {
		StringBundler sb = new StringBundler(3);

		sb.append(jsPackage.getId());
		sb.append(StringPool.SLASH);
		sb.append(moduleName);

		return sb.toString();
	}

	public static String getPackageName(String moduleName) {
		int i = moduleName.indexOf(StringPool.SLASH);

		if (i == -1) {
			return moduleName;
		}

		return moduleName.substring(0, i);
	}

	public static String getPackagePath(String moduleName) {
		int i = moduleName.indexOf(StringPool.SLASH);

		if (i == -1) {
			return null;
		}

		return moduleName.substring(i + 1);
	}

	public static String toFileName(String moduleName) {
		return moduleName + ".js";
	}

	public static String toModuleName(String fileName) {
		int i = fileName.lastIndexOf(CharPool.PERIOD);

		if (i == -1) {
			return fileName;
		}

		return fileName.substring(0, i);
	}

}