/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.kernel.util;

import com.liferay.expando.kernel.model.ExpandoBridge;

/**
 * @author Brian Wing Shun Chan
 */
public interface ExpandoBridgeFactory {

	public ExpandoBridge getExpandoBridge(long companyId, String className);

	public ExpandoBridge getExpandoBridge(
		long companyId, String className, long classPK);

}