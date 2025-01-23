/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.action;

import com.liferay.frontend.data.set.action.FDSBulkActionListSerializer;
import com.liferay.frontend.data.set.internal.serializer.BaseCustomFDSSerializer;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.serializer.FDSSerializer;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Daniel Sanz
 */
@Component(
	property = "frontend.data.set.serializer.type=" + FDSSerializer.TYPE_CUSTOM,
	service = FDSBulkActionListSerializer.class
)
public class CustomFDSBulkActionListSerializerImpl
	extends BaseCustomFDSSerializer implements FDSBulkActionListSerializer {

	@Override
	public List<FDSActionDropdownItem> serialize(
		String fdsName, HttpServletRequest httpServletRequest) {

		// TODO: add support for bulk actions in the DSM

		return Collections.emptyList();
	}

}