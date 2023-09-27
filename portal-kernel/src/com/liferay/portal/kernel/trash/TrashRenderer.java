/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.trash;

import com.liferay.asset.kernel.model.Renderer;

/**
 * @author Zsolt Berentey
 */
public interface TrashRenderer extends Renderer {

	public String getNewName(String oldName, String token);

	public String getPortletId();

	public String getType();

}