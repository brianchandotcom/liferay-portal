/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.action;

import com.liferay.frontend.data.set.action.FDSItemsActions;
import com.liferay.frontend.data.set.action.FDSItemsActionsRegistry;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.serializer.FDSSerializer;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Sanz
 */
@Component(
	property = "frontend.data.set.serializer.type=" + FDSSerializer.TYPE_SYSTEM,
	service = FDSSerializer.class
)
public class ItemsActionsSystemFDSSerializerImpl
	extends BaseItemsActionsFDSSerializer {

	@Override
	public List<FDSActionDropdownItem> serialize(
		String fdsName, HttpServletRequest httpServletRequest) {

		FDSItemsActions fdsItemsActions =
			_fdsItemsActionsRegistry.getFDSItemsActions(fdsName);

		if (fdsItemsActions == null) {
			return Collections.emptyList();
		}

		return fdsItemsActions.getFDSActionDropdownItems(httpServletRequest);
	}

	@Reference
	private FDSItemsActionsRegistry _fdsItemsActionsRegistry;

}