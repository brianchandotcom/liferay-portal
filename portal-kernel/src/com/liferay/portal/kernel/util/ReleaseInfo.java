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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.text.DateFormat;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class ReleaseInfo {

	public static final int RELEASE_6_2_0_BUILD_NUMBER = 6200;

	public static final int RELEASE_7_0_0_BUILD_NUMBER = 7000;

	public static final int RELEASE_7_0_1_BUILD_NUMBER = 7001;

	public static final int RELEASE_7_0_2_BUILD_NUMBER = 7002;

	public static final int RELEASE_7_0_3_BUILD_NUMBER = 7003;

	public static final int RELEASE_7_0_4_BUILD_NUMBER = 7004;

	public static final int RELEASE_7_0_5_BUILD_NUMBER = 7005;

	public static final int RELEASE_7_0_6_BUILD_NUMBER = 7006;

	public static final int RELEASE_7_0_10_BUILD_NUMBER = 7010;

	public static final int RELEASE_7_1_0_BUILD_NUMBER = 7100;

	public static final int RELEASE_7_1_1_BUILD_NUMBER = 7101;

	public static final int RELEASE_7_1_2_BUILD_NUMBER = 7102;

	public static final int RELEASE_7_1_10_BUILD_NUMBER = 7110;

	public static final int RELEASE_7_2_0_BUILD_NUMBER = 7200;

	public static final int RELEASE_7_2_1_BUILD_NUMBER = 7201;

	public static final int RELEASE_7_2_10_BUILD_NUMBER = 7210;

	public static final int RELEASE_7_3_0_BUILD_NUMBER = 7300;

	public static final int RELEASE_7_3_1_BUILD_NUMBER = 7301;

	public static final int RELEASE_7_3_2_BUILD_NUMBER = 7302;

	public static final int RELEASE_7_3_3_BUILD_NUMBER = 7303;

	public static final int RELEASE_7_3_4_BUILD_NUMBER = 7304;

	public static final int RELEASE_7_3_5_BUILD_NUMBER = 7305;

	public static final int RELEASE_7_3_10_BUILD_NUMBER = 7310;

	public static final Date getBuildDate() {
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);

		return GetterUtil.getDate(_DATE, df);
	}

	public static final int getBuildNumber() {
		return _BUILD_NUMBER;
	}

	public static final String getCodeName() {
		return _CODE_NAME;
	}

	public static final String getName() {
		return _NAME;
	}

	public static final int getParentBuildNumber() {
		return _PARENT_BUILD_NUMBER;
	}

	public static final String getReleaseInfo() {
		if (_releaseInfo == null) {
			_releaseInfo = StringBundler.concat(
				_RELEASE_INFO_PREFIX, _NAME, " ", _VERSION_DISPLAY_NAME, " (",
				_CODE_NAME, " / Build ", _BUILD, " / ", _DATE, ")",
				_RELEASE_INFO_SUFFIX);
		}

		return _releaseInfo;
	}

	public static final String getServerInfo() {
		if (_serverInfo == null) {
			_serverInfo = _NAME + " / " + _VERSION;
		}

		return _serverInfo;
	}

	public static String getVendor() {
		return _VENDOR;
	}

	public static final String getVersion() {
		return _VERSION;
	}

	private static final String _BUILD = "@release.info.build@";

	private static final int _BUILD_NUMBER = GetterUtil.getInteger(_BUILD);

	private static final String _CODE_NAME = "Athanasius";

	private static final String _DATE = "@release.info.date@";

	private static final String _NAME = "@release.info.name@";

	private static final int _PARENT_BUILD_NUMBER = _BUILD_NUMBER;

	private static final String _RELEASE_INFO_PREFIX = System.getProperty(
		"liferay.release.info.prefix", StringPool.BLANK);

	private static final String _RELEASE_INFO_SUFFIX = System.getProperty(
		"liferay.release.info.suffix", StringPool.BLANK);

	private static final String _VENDOR = "Liferay, Inc.";

	private static final String _VERSION = "@release.info.version@";

	private static final String _VERSION_DISPLAY_NAME =
		"@release.info.version.display.name@";

	private static String _releaseInfo;
	private static String _serverInfo;

}