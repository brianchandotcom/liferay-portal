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
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Node;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.test.helper.WorkflowMetricsRESTTestHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
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
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_process = _workflowMetricsRESTTestHelper.addProcess(
			testGroup.getCompanyId());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		if (_process != null) {
			_workflowMetricsRESTTestHelper.deleteProcess(
				testGroup.getCompanyId(), _process);
		}

		_deleteNodes();
	}

	@Override
	@Test
	public void testGetProcessNodesPage() throws Exception {
		super.testGetProcessNodesPage();

		_deleteNodes();

		Node node1 = randomNode();

		node1.setId(1L);
		node1.setName("A");

		node1 = testGetProcessNodesPage_addNode(_process.getId(), node1);

		Node node2 = randomNode();

		node2.setId(2L);
		node2.setName("B");

		node2 = testGetProcessNodesPage_addNode(_process.getId(), node2);

		_workflowMetricsRESTTestHelper.updateProcess(
			testGroup.getCompanyId(), _process.getId(), "2.0");

		node1.setId(3L);

		node1 = _addNode(node1, _process.getId(), "2.0");

		node2.setId(4L);

		node2 = _addNode(node2, _process.getId(), "2.0");

		Page<Node> page = nodeResource.getProcessNodesPage(_process.getId());

		assertEqualsIgnoringOrder(
			Arrays.asList(node1, node2), (List<Node>)page.getItems());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"id", "name"};
	}

	@Override
	protected Node testGetProcessNodesPage_addNode(Long processId, Node node)
		throws Exception {

		return _addNode(node, processId, "1.0");
	}

	@Override
	protected Long testGetProcessNodesPage_getProcessId() throws Exception {
		return _process.getId();
	}

	private Node _addNode(Node node, Long processId, String version)
		throws Exception {

		node = _workflowMetricsRESTTestHelper.addNode(
			testGroup.getCompanyId(), node, processId, version);

		_nodes.add(node);

		return node;
	}

	private void _deleteNodes() throws Exception {
		for (Node node : _nodes) {
			_workflowMetricsRESTTestHelper.deleteNode(
				testGroup.getCompanyId(), node, _process.getId());
		}
	}

	@Inject
	private static Queries _queries;

	@Inject
	private static SearchEngineAdapter _searchEngineAdapter;

	private static WorkflowMetricsRESTTestHelper _workflowMetricsRESTTestHelper;

	private final List<Node> _nodes = new ArrayList<>();
	private Process _process;

}