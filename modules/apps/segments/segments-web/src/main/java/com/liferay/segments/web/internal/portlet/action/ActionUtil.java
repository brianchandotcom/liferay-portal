/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.segments.web.internal.portlet.action;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;

import java.util.List;

import javax.portlet.PortletRequest;

/**
 * @author Eduardo García
 */
public class ActionUtil {

	public static Criteria getCriteria(
		PortletRequest portletRequest,
		List<SegmentsCriteriaContributor> segmentsCriteriaContributors) {

		Criteria criteria = new Criteria();

		for (SegmentsCriteriaContributor segmentsCriteriaContributor :
				segmentsCriteriaContributors) {

			String filterString = ParamUtil.getString(
				portletRequest,
				"criterionFilter" + segmentsCriteriaContributor.getKey());

			if (Validator.isNull(filterString)) {
				continue;
			}

			String conjunctionString = ParamUtil.getString(
				portletRequest,
				"criterionConjunction" + segmentsCriteriaContributor.getKey(),
				Criteria.Conjunction.AND.getValue());

			segmentsCriteriaContributor.contribute(
				criteria, filterString,
				Criteria.Conjunction.parse(conjunctionString));
		}

		return criteria;
	}

}