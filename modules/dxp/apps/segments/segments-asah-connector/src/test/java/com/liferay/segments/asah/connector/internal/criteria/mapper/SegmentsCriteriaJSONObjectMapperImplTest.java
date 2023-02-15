/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.segments.asah.connector.internal.criteria.mapper;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.asah.connector.internal.expression.parser.test.util.IndividualSegmentsExpressionUtil;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.criteria.mapper.SegmentsCriteriaJSONObjectMapper;
import com.liferay.segments.field.Field;

import java.util.List;

import javax.portlet.PortletRequest;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class SegmentsCriteriaJSONObjectMapperImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testToJSONObject() throws Exception {
		SegmentsCriteriaJSONObjectMapper segmentsCriteriaJSONObjectMapper =
			new SegmentsCriteriaJSONObjectMapperImpl();

		SegmentsCriteriaContributor segmentsCriteriaContributor =
			new SegmentsCriteriaContributorImpl();

		Criteria criteria = new Criteria();

		criteria.addCriterion(
			segmentsCriteriaContributor.getKey(), Criteria.Type.ANALYTICS,
			IndividualSegmentsExpressionUtil.getFilterByCount(
				IndividualSegmentsExpressionUtil.getFilter(
					"Page#pageViewed#585976064510133365", "gt", "last24Hours"),
				"ge", 1),
			Criteria.Conjunction.AND);

		JSONObject jsonObject = segmentsCriteriaJSONObjectMapper.toJSONObject(
			criteria, segmentsCriteriaContributor);

		Assert.assertEquals(
			segmentsCriteriaContributor.getKey(),
			jsonObject.get("propertyKey"));
		Assert.assertEquals("and", jsonObject.get("conjunctionId"));
		Assert.assertEquals(
			JSONUtil.put(
				"conjunctionName", "and"
			).put(
				"groupId", "group_0"
			).put(
				"items",
				JSONUtil.putAll(
					JSONUtil.put(
						"assetId", "585976064510133365"
					).put(
						"day",
						JSONUtil.put(
							"operatorName", "gt"
						).put(
							"value", "last24Hours"
						)
					).put(
						"operatorName", "ge"
					).put(
						"propertyName", "pageViewed"
					).put(
						"value", "1"
					))
			).toString(),
			String.valueOf(jsonObject.get("query")));
	}

	private static class SegmentsCriteriaContributorImpl
		implements SegmentsCriteriaContributor {

		@Override
		public JSONObject getCriteriaJSONObject(Criteria criteria) {
			return null;
		}

		@Override
		public EntityModel getEntityModel() {
			return null;
		}

		@Override
		public String getEntityName() {
			return null;
		}

		@Override
		public List<Field> getFields(PortletRequest portletRequest) {
			return null;
		}

		@Override
		public String getKey() {
			return "KEY";
		}

		@Override
		public Criteria.Type getType() {
			return null;
		}

	}

}