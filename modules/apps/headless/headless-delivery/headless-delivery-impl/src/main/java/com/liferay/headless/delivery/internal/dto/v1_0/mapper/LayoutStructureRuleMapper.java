/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.mapper;

import com.liferay.headless.delivery.dto.v1_0.PageRule;
import com.liferay.layout.util.structure.LayoutStructureRule;

/**
 * @author Lourdes Fernández Besada
 */
public class LayoutStructureRuleMapper {

	public static PageRule getPageRule(
		LayoutStructureRule layoutStructureRule) {

		return new PageRule() {
			{
				id = layoutStructureRule.getId();
				name = layoutStructureRule.getName();
			}
		};
	}

}