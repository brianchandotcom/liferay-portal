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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.TimeRange;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
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

	@Ignore
	@Override
	@Test
	public void testGraphQLGetTimeRangesPage() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		List<GraphQLField> itemsGraphQLFields = getGraphQLFields();

		graphQLFields.add(
			new GraphQLField(
				"items", itemsGraphQLFields.toArray(new GraphQLField[0])));

		graphQLFields.add(new GraphQLField("page"));
		graphQLFields.add(new GraphQLField("totalCount"));

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"timeRanges", graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		JSONObject timeRangesJSONObject = dataJSONObject.getJSONObject(
			"timeRanges");

		Assert.assertEquals(7, timeRangesJSONObject.get("totalCount"));

		assertEqualsJSONArray(
			_getDefaultTimeRanges(),
			timeRangesJSONObject.getJSONArray("items"));
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