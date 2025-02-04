/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.action;

import com.liferay.frontend.data.set.serializer.FDSSerializer;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;

/**
 * @author Daniel Sanz
 */
public interface CreationMenuFDSSerializer extends FDSSerializer<CreationMenu> {

	@Override
	public default String getKey() {
		return "creationMenu";
	}

}