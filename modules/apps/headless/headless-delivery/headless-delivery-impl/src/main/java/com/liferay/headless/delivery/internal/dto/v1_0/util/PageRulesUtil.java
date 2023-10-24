/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.headless.delivery.dto.v1_0.PageRule;
import com.liferay.headless.delivery.internal.dto.v1_0.mapper.LayoutStructureRuleMapper;
import com.liferay.layout.util.structure.LayoutStructureRule;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

/**
 * @author Lourdes Fernández Besada
 */
public class PageRulesUtil {

	public static PageRule[] toPageRules(
		List<LayoutStructureRule> layoutStructureRules) {

		if (ListUtil.isEmpty(layoutStructureRules)) {
			return null;
		}

		return TransformUtil.transformToArray(
			layoutStructureRules,
			layoutStructureRule -> LayoutStructureRuleMapper.getPageRule(
				layoutStructureRule),
			PageRule.class);
	}

}