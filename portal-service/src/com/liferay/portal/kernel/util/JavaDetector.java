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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class JavaDetector {

	public static String getJavaClassPath() {
		return _instance._javaClassPath;
	}

	public static double getJavaClassVersion() {
		return _instance._javaClassVersion;
	}

	public static String getJavaRuntimeName() {
		return _instance._javaRuntimeName;
	}

	public static String getJavaRuntimeVersion() {
		return _instance._javaRuntimeVersion;
	}

	public static double getJavaSpecificationVersion() {
		return _instance._javaSpecificationVersion;
	}

	public static String getJavaVendor() {
		return _instance._javaVendor;
	}

	public static String getJavaVersion() {
		return _instance._javaVersion;
	}

	public static String getJavaVmVersion() {
		return _instance._javaVmVersion;
	}

	public static boolean is64bit() {
		return _instance._64bit;
	}

	public static boolean isIBM() {
		return _instance._ibm;
	}

	public static boolean isJDK4() {
		String javaVersion = getJavaVersion();

		if (javaVersion.startsWith(_JAVA_VERSION_JDK_4)) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isJDK5() {
		String javaVersion = getJavaVersion();

		if (javaVersion.startsWith(_JAVA_VERSION_JDK_5)) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isJDK6() {
		String javaVersion = getJavaVersion();

		if (javaVersion.startsWith(_JAVA_VERSION_JDK_6)) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isJDK7() {
		String javaVersion = getJavaVersion();

		if (javaVersion.startsWith(_JAVA_VERSION_JDK_7)) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isOpenJDK() {
		return _instance._openJDK;
	}

	public static boolean isOracle() {
		return _instance._oracle;
	}

	protected JavaDetector() {
		_javaClassPath = System.getProperty("java.class.path");
		_javaClassVersion = GetterUtil.getDouble(
			System.getProperty("java.class.version"));
		_javaRuntimeName = System.getProperty("java.runtime.name");
		_javaRuntimeVersion = System.getProperty("java.runtime.version");
		_javaSpecificationVersion = GetterUtil.getDouble(
			System.getProperty("java.specification.version"));
		_javaVendor = System.getProperty("java.vendor");
		_javaVersion = System.getProperty("java.version");
		_javaVmVersion = System.getProperty("java.vm.version");

		_64bit = Validator.equals(
			"64", System.getProperty("sun.arch.data.model"));

		boolean oracle = false;

		if (_javaVendor != null) {
			_ibm = _javaVendor.startsWith("IBM");

			if (_javaVendor.startsWith("Oracle") ||
				_javaVendor.startsWith("Sun")) {

				oracle = true;
			}
		}
		else {
			_ibm = false;
		}

		_oracle = oracle;

		if (_javaRuntimeName != null) {
			_openJDK = _javaRuntimeName.contains("OpenJDK");
		}
		else {
			_openJDK = false;
		}

		if (_log.isDebugEnabled()) {
			LogUtil.debug(_log, new SortedProperties(System.getProperties()));
		}
	}

	private static final String _JAVA_VERSION_JDK_4 = "1.4.";

	private static final String _JAVA_VERSION_JDK_5 = "1.5.";

	private static final String _JAVA_VERSION_JDK_6 = "1.6.";

	private static final String _JAVA_VERSION_JDK_7 = "1.7.";

	private static final Log _log = LogFactoryUtil.getLog(JavaDetector.class);

	private static final JavaDetector _instance = new JavaDetector();

	private final boolean _64bit;
	private final boolean _ibm;
	private final String _javaClassPath;
	private final double _javaClassVersion;
	private final String _javaRuntimeName;
	private final String _javaRuntimeVersion;
	private final double _javaSpecificationVersion;
	private final String _javaVendor;
	private final String _javaVersion;
	private final String _javaVmVersion;
	private final boolean _openJDK;
	private final boolean _oracle;

}