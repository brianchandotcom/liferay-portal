/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.module.util;

import com.liferay.portal.kernel.module.util.SystemBundleProvider;
import com.liferay.portal.module.framework.ModuleFrameworkUtil;

import org.osgi.framework.Bundle;

/**
 * @author Shuyang Zhou
 */
public class SystemBundleProviderImpl implements SystemBundleProvider {

	@Override
	public Bundle getSystemBundle() {
		return ModuleFrameworkUtil.getFramework();
	}

}