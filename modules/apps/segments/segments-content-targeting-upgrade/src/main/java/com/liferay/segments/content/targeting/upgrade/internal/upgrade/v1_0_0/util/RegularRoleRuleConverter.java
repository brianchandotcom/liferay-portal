/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.content.targeting.upgrade.internal.upgrade.v1_0_0.util;

import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = "rule.converter.key=RegularRoleRule",
	service = RuleConverter.class
)
public class RegularRoleRuleConverter implements RuleConverter {

	@Override
	public void convert(
		long companyId, Criteria criteria, String typeSettings) {

		_userSegmentsCriteriaContributor.contribute(
			criteria, "(roleIds eq '" + typeSettings + "')",
			Criteria.Conjunction.AND);
	}

	@Reference(target = "(segments.criteria.contributor.key=user)")
	private SegmentsCriteriaContributor _userSegmentsCriteriaContributor;

}