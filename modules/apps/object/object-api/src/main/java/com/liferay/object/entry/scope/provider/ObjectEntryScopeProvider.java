/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.entry.scope.provider;

import com.liferay.portal.kernel.model.User;

/**
 * @author Carolina Barbosa
 */
public interface ObjectEntryScopeProvider {

	public String getGroupId(User user) throws Exception;

}