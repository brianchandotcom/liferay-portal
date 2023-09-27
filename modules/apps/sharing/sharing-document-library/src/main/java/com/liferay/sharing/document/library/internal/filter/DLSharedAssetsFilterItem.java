/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.document.library.internal.filter;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.sharing.filter.SharedAssetsFilterItem;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(
	property = "navigation.item.order:Integer=1000",
	service = SharedAssetsFilterItem.class
)
public class DLSharedAssetsFilterItem implements SharedAssetsFilterItem {

	@Override
	public String getClassName() {
		return DLFileEntry.class.getName();
	}

}