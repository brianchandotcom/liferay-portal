/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.provider;

import com.liferay.layout.util.structure.LayoutStructure;

/**
 * @author Víctor Galán
 */
public interface LayoutStructureProvider {

	public LayoutStructure getLayoutStructure(
		long plid, long segmentsExperienceId);

}