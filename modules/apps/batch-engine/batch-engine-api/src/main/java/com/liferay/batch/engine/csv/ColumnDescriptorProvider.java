/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.csv;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ObjectValuePair;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.Map;

/**
 * @author Matija Petanjek
 */
public interface ColumnDescriptorProvider {

	public ColumnDescriptor[] getColumnDescriptors(
			long companyId, String fieldName, int index,
			Map<String, ObjectValuePair<Field, Method>> objectValuePairs,
			String taskItemDelegateName)
		throws PortalException;

}