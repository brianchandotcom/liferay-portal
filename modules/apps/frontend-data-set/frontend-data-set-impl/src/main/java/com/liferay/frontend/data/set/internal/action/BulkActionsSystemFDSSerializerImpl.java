/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.action;

import com.liferay.frontend.data.set.action.FDSBulkActions;
import com.liferay.frontend.data.set.action.FDSBulkActionsRegistry;
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
public class BulkActionsSystemFDSSerializerImpl
	extends BaseBulkActionsFDSSerializer {

	@Override
	public List<FDSActionDropdownItem> serialize(
		String fdsName, HttpServletRequest httpServletRequest) {

		FDSBulkActions fdsBulkActions =
			_fdsBulkActionsRegistry.getFDSBulkActions(fdsName);

		if (fdsBulkActions == null) {
			return Collections.emptyList();
		}

		return fdsBulkActions.getFDSActionDropdownItems(httpServletRequest);
	}

	@Reference
	private FDSBulkActionsRegistry _fdsBulkActionsRegistry;

}