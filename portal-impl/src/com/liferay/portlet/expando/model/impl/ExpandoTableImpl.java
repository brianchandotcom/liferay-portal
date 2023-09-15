/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.expando.model.impl;

import com.liferay.expando.kernel.model.ExpandoTableConstants;

import java.util.Objects;

/**
 * @author Brian Wing Shun Chan
 */
public class ExpandoTableImpl extends ExpandoTableBaseImpl {

	@Override
	public boolean isDefaultTable() {
		if (Objects.equals(
				getName(), ExpandoTableConstants.DEFAULT_TABLE_NAME)) {

			return true;
		}

		return false;
	}

}