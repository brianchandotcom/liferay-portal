/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Murilo Stodolni
 */
public class ObjectFolderImpl extends ObjectFolderBaseImpl {

	@Override
	public boolean isDefault() {
		if (StringUtil.equals(getName(), ObjectFolderConstants.NAME_DEFAULT)) {
			return true;
		}

		return false;
	}

}