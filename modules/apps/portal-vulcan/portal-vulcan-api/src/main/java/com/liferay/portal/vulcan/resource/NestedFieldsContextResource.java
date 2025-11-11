/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.resource;

import com.liferay.portal.vulcan.fields.NestedFieldsContext;

/**
 * @author Alejandro Tardín
 */
public interface NestedFieldsContextResource {

	public NestedFieldsContext customizeNestedFieldsContext(
		NestedFieldsContext nestedFieldsContext);

}