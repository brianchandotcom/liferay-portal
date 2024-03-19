/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.content.targeting.upgrade.internal.upgrade.v1_0_0.util;

import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;

/**
 * @author Eduardo García
 */
public class BrowseRuleConverter implements RuleConverter {

	public static final String RULE_CONVERTER_KEY = "BrowserRule";

	@Override
	public void convert(
		long companyId, Criteria criteria, String typeSettings) {

		SegmentsCriteriaContributor.contribute(
			criteria, "(browser eq '" + typeSettings + "')",
			Criteria.Conjunction.AND, "context", Criteria.Type.CONTEXT);
	}

}