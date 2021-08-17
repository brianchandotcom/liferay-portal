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
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.ReindexStatus;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;

import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class ReindexStatusResourceTest
	extends BaseReindexStatusResourceTestCase {

	@Override
	@Test
	public void testGetReindexStatusesPage() throws Exception {
		Page<ReindexStatus> reindexStatusPage =
			reindexStatusResource.getReindexStatusesPage();

		List<ReindexStatus> reindexStatuses =
			(List<ReindexStatus>)reindexStatusPage.getItems();

		Assert.assertEquals(
			reindexStatuses.toString(), 0, reindexStatuses.size());
	}

	@Override
	@Test
	public void testGraphQLGetReindexStatusesPage() throws Exception {
		JSONObject reindexStatusesJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(
				new GraphQLField(
					"reindexStatuses", new GraphQLField("totalCount"))),
			"JSONObject/data", "JSONObject/reindexStatuses");

		Assert.assertEquals(0, reindexStatusesJSONObject.get("totalCount"));
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

}