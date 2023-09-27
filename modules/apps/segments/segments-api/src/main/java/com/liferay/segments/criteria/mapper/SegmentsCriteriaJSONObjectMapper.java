/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.criteria.mapper;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;

/**
 * @author Cristina González
 */
public interface SegmentsCriteriaJSONObjectMapper {

	public JSONObject toJSONObject(
			Criteria criteria,
			SegmentsCriteriaContributor segmentsCriteriaContributor)
		throws Exception;

}