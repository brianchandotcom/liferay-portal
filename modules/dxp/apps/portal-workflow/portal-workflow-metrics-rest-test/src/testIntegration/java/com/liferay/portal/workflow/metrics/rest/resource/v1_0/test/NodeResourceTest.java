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
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Node;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.client.resource.v1_0.NodeResource;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.test.helper.WorkflowMetricsRESTTestHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class NodeResourceTest extends BaseNodeResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseTaskResourceTestCase.setUpClass();

		_workflowMetricsRESTTestHelper = new WorkflowMetricsRESTTestHelper(
			_queries, _searchEngineAdapter);
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_process = _workflowMetricsRESTTestHelper.addProcess(
			testGroup.getCompanyId());

		_singleApproverDocument =
			_workflowMetricsRESTTestHelper.getSingleApproverDocument(
				testGroup.getCompanyId());

		_workflowMetricsRESTTestHelper.deleteProcess(_singleApproverDocument);
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		_workflowMetricsRESTTestHelper.restoreProcess(_singleApproverDocument);

		if (_process == null) {
			return;
		}

		_workflowMetricsRESTTestHelper.deleteProcess(
			testGroup.getCompanyId(), _process.getId());

		for (Node node : _nodes) {
			_workflowMetricsRESTTestHelper.deleteNode(
				testGroup.getCompanyId(), _process.getId(), node.getName());
		}

		_nodes = new ArrayList<>();
	}

	@Test
	public void testGetProcessNodesPageLatestVersion() throws Exception {
		_nodes.add(
			_workflowMetricsRESTTestHelper.addNode(
				testGroup.getCompanyId(), _process.getId(), "1.0",
				new Node() {
					{
						id = 1L;
						name = "A";
					}
				}));
		_nodes.add(
			_workflowMetricsRESTTestHelper.addNode(
				testGroup.getCompanyId(), _process.getId(), "1.0",
				new Node() {
					{
						id = 2L;
						name = "B";
					}
				}));

		_workflowMetricsRESTTestHelper.updateProcess(
			testGroup.getCompanyId(), _process.getId(), "2.0");

		Node node1 = new Node() {
			{
				id = 3L;
				name = "A";
			}
		};

		_nodes.add(
			_workflowMetricsRESTTestHelper.addNode(
				testGroup.getCompanyId(), _process.getId(), "2.0", node1));

		Node node2 = new Node() {
			{
				id = 4L;
				name = "B";
			}
		};

		_nodes.add(
			_workflowMetricsRESTTestHelper.addNode(
				testGroup.getCompanyId(), _process.getId(), "2.0", node2));

		Page<Node> page = NodeResource.getProcessNodesPage(_process.getId());

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(node1, node2), (List<Node>)page.getItems());
		assertValid(page);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"id", "name"};
	}

	@Override
	protected Node testGetProcessNodesPage_addNode(Long processId, Node node)
		throws Exception {

		node = _workflowMetricsRESTTestHelper.addNode(
			testGroup.getCompanyId(), processId, "1.0", node);

		_nodes.add(node);

		return node;
	}

	@Override
	protected Long testGetProcessNodesPage_getProcessId() throws Exception {
		return _process.getId();
	}

	@Inject
	private static Queries _queries;

	@Inject
	private static SearchEngineAdapter _searchEngineAdapter;

	private static Document _singleApproverDocument;
	private static WorkflowMetricsRESTTestHelper _workflowMetricsRESTTestHelper;

	private List<Node> _nodes = new ArrayList<>();
	private Process _process;

}