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

package com.liferay.portal.workflow.metrics.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.TimeRange;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0.TimeRangeSerDes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class TimeRangeResourceTest extends BaseTimeRangeResourceTestCase {

	@Override
	@Test
	public void testGetTimeRangesPage() throws Exception {
		Page<TimeRange> timeRangesPage = timeRangeResource.getTimeRangesPage();

		List<TimeRange> timeRanges = (List<TimeRange>)timeRangesPage.getItems();

		Assert.assertEquals(timeRanges.toString(), 7, timeRanges.size());

		assertEquals(_getDefaultTimeRanges(), timeRanges);
	}

	@Override
	@Test
	public void testGraphQLGetTimeRangesPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"timeRanges", new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject timeRangesJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/timeRanges");

		Assert.assertEquals(7, timeRangesJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			_getDefaultTimeRanges(),
			Arrays.asList(
				TimeRangeSerDes.toDTOs(
					timeRangesJSONObject.getString("items"))));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"defaultTimeRange", "id"};
	}

	private List<TimeRange> _getDefaultTimeRanges() {
		return new ArrayList<TimeRange>() {
			{
				add(
					new TimeRange() {
						{
							defaultTimeRange = false;
							id = 0;
						}
					});
				add(
					new TimeRange() {
						{
							defaultTimeRange = false;
							id = 1;
						}
					});
				add(
					new TimeRange() {
						{
							defaultTimeRange = false;
							id = 7;
						}
					});
				add(
					new TimeRange() {
						{
							defaultTimeRange = true;
							id = 30;
						}
					});
				add(
					new TimeRange() {
						{
							defaultTimeRange = false;
							id = 90;
						}
					});
				add(
					new TimeRange() {
						{
							defaultTimeRange = false;
							id = 180;
						}
					});
				add(
					new TimeRange() {
						{
							defaultTimeRange = false;
							id = 365;
						}
					});
			}
		};
	}

}