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

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

/**
 * @author Hugo Huijser
 */
public class BNDMultipleAppBNDsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!absolutePath.endsWith("/app.bnd")) {
			return content;
		}

		_checkMuldipleAppBND(fileName, absolutePath, "dxp");
		_checkMuldipleAppBND(fileName, absolutePath, "private");

		return content;
	}

	private void _checkMuldipleAppBND(
			String fileName, String absolutePath, String dirName)
		throws IOException {

		int x = absolutePath.indexOf("/modules/" + dirName + "/apps/");

		if (x == -1) {
			return;
		}

		String portalAppBNDFileName = StringUtil.replaceFirst(
			absolutePath.substring(x + 1), "/" + dirName + "/", "/");

		if (getPortalContent(portalAppBNDFileName, absolutePath, true) !=
				null) {

			addMessage(
				fileName,
				"Redundant app.bnd. There is one already: '" +
					portalAppBNDFileName + "'.");
		}
	}

}