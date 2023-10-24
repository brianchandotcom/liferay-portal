/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.importer.structure.util;

import com.liferay.headless.delivery.dto.v1_0.PageRule;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureRule;

/**
 * @author Lourdes Fernández Besada
 */
public class LayoutStructureRuleImporter {

	public static LayoutStructureRule addLayoutStructureRule(
		LayoutStructure layoutStructure, PageRule pageRule) {

		return layoutStructure.addLayoutStructureRule(
			pageRule.getId(), pageRule.getName());
	}

}