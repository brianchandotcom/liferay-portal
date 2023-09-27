/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.bundle.blacklist;

import java.io.IOException;

import java.util.Collection;

/**
 * @author Preston Crary
 */
public interface BundleBlacklistManager {

	public void addToBlacklistAndUninstall(String... bundleSymbolicNames)
		throws IOException;

	public Collection<String> getBlacklistBundleSymbolicNames();

	public void removeFromBlacklistAndInstall(String... bundleSymbolicNames)
		throws IOException;

}