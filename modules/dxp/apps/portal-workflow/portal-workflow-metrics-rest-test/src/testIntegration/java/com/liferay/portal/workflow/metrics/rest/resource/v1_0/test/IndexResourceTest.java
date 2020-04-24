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
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Index;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0.IndexSerDes;

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
public class IndexResourceTest extends BaseIndexResourceTestCase {

	@Override
	@Test
	public void testGetIndexesPage() throws Exception {
		Page<Index> indexesPage = indexResource.getIndexesPage();

		List<Index> indexes = (List<Index>)indexesPage.getItems();

		Assert.assertEquals(indexes.toString(), 7, indexes.size());

		assertEqualsIgnoringOrder(indexes, _getDefaultIndexes());
	}

	@Override
	@Test
	public void testGraphQLGetIndexesPage() throws Exception {
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
				"indexes", graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		JSONObject indexesJSONObject = dataJSONObject.getJSONObject("indexes");

		Assert.assertEquals(7, indexesJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			_getDefaultIndexes(),
			Arrays.asList(
				IndexSerDes.toDTOs(indexesJSONObject.getString("items"))));
	}

	@Override
	@Test
	public void testPatchIndexesReindex() throws Exception {
		assertHttpResponseStatusCode(
			400, indexResource.patchIndexesReindexHttpResponse(new Index()));

		assertHttpResponseStatusCode(
			400,
			indexResource.patchIndexesReindexHttpResponse(
				new Index() {
					{
						key = "Invalid";
					}
				}));

		for (Index index : _getDefaultIndexes()) {
			assertHttpResponseStatusCode(
				204, indexResource.patchIndexesReindexHttpResponse(index));
		}

		assertHttpResponseStatusCode(
			204,
			indexResource.patchIndexesReindexHttpResponse(
				new Index() {
					{
						key = Index.Group.METRIC.getValue();
					}
				}));
		assertHttpResponseStatusCode(
			204,
			indexResource.patchIndexesReindexHttpResponse(
				new Index() {
					{
						key = Index.Group.SLA.getValue();
					}
				}));
		assertHttpResponseStatusCode(
			204,
			indexResource.patchIndexesReindexHttpResponse(
				new Index() {
					{
						key = Index.Group.ALL.getValue();
					}
				}));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"key"};
	}

	private List<Index> _getDefaultIndexes() {
		return Arrays.asList(
			new Index() {
				{
					key = "instance";
				}
			},
			new Index() {
				{
					key = "node";
				}
			},
			new Index() {
				{
					key = "process";
				}
			},
			new Index() {
				{
					key = "sla-instance-result";
				}
			},
			new Index() {
				{
					key = "sla-task-result";
				}
			},
			new Index() {
				{
					key = "task";
				}
			},
			new Index() {
				{
					key = "transition";
				}
			});
	}

}