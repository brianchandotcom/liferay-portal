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

package com.liferay.document.library.web.internal.upgrade.v1_0_0;

import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.portal.kernel.upgrade.BaseUpgradeAdminPortlets;

/**
 * @author Sergio González
 */
public class UpgradeAdminPortlets extends BaseUpgradeAdminPortlets {

	@Override
	protected void doUpgrade() throws Exception {
		updateAccessInControlPanelPermission(
			DLPortletKeys.DOCUMENT_LIBRARY,
			DLPortletKeys.DOCUMENT_LIBRARY_ADMIN);
	}

}