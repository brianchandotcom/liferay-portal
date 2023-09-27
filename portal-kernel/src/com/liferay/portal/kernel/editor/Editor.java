/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.editor;

/**
 * @author     Roberto Díaz
 * @deprecated As of Judson (7.1.x), replaced by
 *             com.liferay.frontend.editor.api.EditorProvider
 */
@Deprecated
public interface Editor {

	public String[] getJavaScriptModules();

	public String getJspPath();

	public String getName();

	public String getResourceType();

}