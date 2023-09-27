/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.wab.reference.support.internal;

import java.io.IOException;

import java.net.URL;
import java.net.URLConnection;

import org.eclipse.osgi.storage.url.reference.Handler;

/**
 * @author Gregory Amerson
 */
public class WabDirHandler extends Handler {

	public WabDirHandler(String installURL) {
		super(installURL);
	}

	@Override
	public URLConnection openConnection(URL url) throws IOException {
		return super.openConnection(url);
	}

}