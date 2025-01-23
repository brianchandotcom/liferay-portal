/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.action;

import com.liferay.frontend.data.set.action.FDSBulkActionList;
import com.liferay.frontend.data.set.action.FDSBulkActionListRegistry;
import com.liferay.frontend.data.set.action.FDSBulkActionListSerializer;
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
	service = FDSBulkActionListSerializer.class
)
public class SystemFDSBulkActionListSerializerImpl
	implements FDSBulkActionListSerializer {

	@Override
	public List<FDSActionDropdownItem> serialize(
		String fdsName, HttpServletRequest httpServletRequest) {

		FDSBulkActionList fdsBulkActionList =
			_fdsBulkActionListRegistry.getFDSBulkActionList(fdsName);

		if (fdsBulkActionList == null) {
			return Collections.emptyList();
		}

		return fdsBulkActionList.getFDSActionDropdownItems(httpServletRequest);
	}

	@Reference
	private FDSBulkActionListRegistry _fdsBulkActionListRegistry;

}