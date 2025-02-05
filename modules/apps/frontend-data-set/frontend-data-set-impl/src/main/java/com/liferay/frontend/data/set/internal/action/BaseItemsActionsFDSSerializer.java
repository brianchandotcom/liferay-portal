/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.action;

import com.liferay.frontend.data.set.internal.serializer.BaseFDSSerializer;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.serializer.FDSSerializer;

import java.util.List;

/**
 * @author Daniel Sanz
 */
public abstract class BaseItemsActionsFDSSerializer
	extends BaseFDSSerializer
	implements FDSSerializer<List<FDSActionDropdownItem>> {

	@Override
	public String getKey() {
		return "itemsActions";
	}

}