/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.bundle.support.internal;

import com.beust.jcommander.Parameter;

/**
 * @author Andrea Di Giorgi
 */
public class BundleSupportArgs {

	public boolean isHelp() {
		return _help;
	}

	@Parameter(
		description = "Print this message.", help = true,
		names = {"-h", "--help"}
	)
	private boolean _help;

}