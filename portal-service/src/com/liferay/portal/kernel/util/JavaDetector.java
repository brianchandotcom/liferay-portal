/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

	public static final double JAVA_CLASS_VERSION_JDK_4 = 48.0;

	public static final double JAVA_CLASS_VERSION_JDK_5 = 49.0;

	public static final double JAVA_CLASS_VERSION_JDK_6 = 50.0;

	public static final double JAVA_CLASS_VERSION_JDK_7 = 51.0;

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
		if (getJavaClassVersion() >= JAVA_CLASS_VERSION_JDK_4) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isJDK5() {
		if (getJavaClassVersion() >= JavaDetector.JAVA_CLASS_VERSION_JDK_5) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isJDK6() {
		if (getJavaClassVersion() >= JavaDetector.JAVA_CLASS_VERSION_JDK_6) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isJDK7() {
		if (getJavaClassVersion() >= JavaDetector.JAVA_CLASS_VERSION_JDK_7) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isOpenJDK() {
		return _instance._openJDK;
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

		if (_javaVendor != null) {
			_ibm = _javaVendor.startsWith("IBM");
		}

		if (_javaRuntimeName != null) {
			_openJDK = _javaRuntimeName.contains("OpenJDK");
		}

		if (_log.isDebugEnabled()) {
			LogUtil.debug(_log, new SortedProperties(System.getProperties()));
		}
	}

	private static Log _log = LogFactoryUtil.getLog(JavaDetector.class);

	private static JavaDetector _instance = new JavaDetector();

	private boolean _64bit;
	private boolean _ibm;
	private String _javaClassPath;
	private double _javaClassVersion;
	private String _javaRuntimeName;
	private String _javaRuntimeVersion;
	private double _javaSpecificationVersion;
	private String _javaVendor;
	private String _javaVersion;
	private String _javaVmVersion;
	private boolean _openJDK;

}