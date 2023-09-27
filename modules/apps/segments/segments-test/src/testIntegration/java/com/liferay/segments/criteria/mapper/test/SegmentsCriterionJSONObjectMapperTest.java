/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.criteria.mapper.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.criteria.mapper.SegmentsCriteriaJSONObjectMapper;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo García
 */
@RunWith(Arquillian.class)
public class SegmentsCriterionJSONObjectMapperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testToJSONObject() throws Exception {
		Criteria criteria = new Criteria();

		_segmentsCriteriaContributor.contribute(
			criteria, "(firstName eq 'Margot')", Criteria.Conjunction.AND);

		JSONObject jsonObject = _segmentsCriteriaJSONObjectMapper.toJSONObject(
			criteria, _segmentsCriteriaContributor);

		Assert.assertEquals(
			String.valueOf(Criteria.Conjunction.AND),
			jsonObject.getString("conjunctionName"));

		Assert.assertEquals(
			JSONUtil.put(
				"conjunctionName", String.valueOf(Criteria.Conjunction.AND)
			).put(
				"groupId", "group_0"
			).put(
				"items",
				JSONUtil.putAll(
					JSONUtil.put(
						"operatorName", "eq"
					).put(
						"propertyName", "firstName"
					).put(
						"value", "Margot"
					))
			).toString(),
			String.valueOf(jsonObject.getString("query")));
	}

	@Inject(filter = "segments.criteria.contributor.key=user")
	private SegmentsCriteriaContributor _segmentsCriteriaContributor;

	@Inject(filter = "segments.criteria.mapper.key=odata")
	private SegmentsCriteriaJSONObjectMapper _segmentsCriteriaJSONObjectMapper;

}