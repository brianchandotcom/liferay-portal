/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osgi.bundle.builder.commands;

import com.liferay.osgi.bundle.builder.OSGiBundleBuilderArgs;

/**
 * @author David Truong
 */
public interface Command {

	public void build(OSGiBundleBuilderArgs osgiBundleBuilderArgs)
		throws Exception;

}