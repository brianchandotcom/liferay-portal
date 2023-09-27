/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.lpkg.deployer.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;

import org.osgi.framework.Bundle;

/**
 * @author Matthew Tambara
 */
public class LPKGLocationUtil {

	public static String generateInnerBundleLocation(
		Bundle lpkgBundle, String path) {

		String location = path.concat("?lpkgPath=");

		return location.concat(lpkgBundle.getLocation());
	}

	public static String getLPKGLocation(File lpkgFile) {
		String uriString = String.valueOf(lpkgFile.toURI());

		return StringUtil.replace(
			uriString, CharPool.BACK_SLASH, CharPool.FORWARD_SLASH);
	}

}