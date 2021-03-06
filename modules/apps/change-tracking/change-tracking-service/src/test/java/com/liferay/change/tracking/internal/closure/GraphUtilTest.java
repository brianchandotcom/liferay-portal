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

package com.liferay.change.tracking.internal.closure;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class GraphUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@Test
	public void testConstructor() {
		new GraphUtil();
	}

	@Test
	public void testIntersectingCycles() {
		Node node1 = new Node(1, 1);
		Node node2 = new Node(1, 2);
		Node node3 = new Node(1, 3);
		Node node4 = new Node(1, 4);

		Set<Node> nodes = new HashSet<>(
			Arrays.asList(node1, node2, node3, node4));

		Map<Node, Collection<Node>> nodeMap = GraphUtil.getNodeMap(
			nodes,
			HashMapBuilder.<Node, Collection<Edge>>put(
				node1, Collections.singletonList(new Edge(node1, node2))
			).put(
				node2, Collections.singletonList(new Edge(node2, node3))
			).put(
				node3,
				Arrays.asList(
					new Edge(node3, node1), new Edge(node3, node2),
					new Edge(node3, node4))
			).put(
				node4, Collections.singletonList(new Edge(node4, node2))
			).build());

		Assert.assertEquals(
			Collections.singleton(node3), nodeMap.remove(new Node(0, 0)));

		Assert.assertEquals(
			new HashSet<>(Arrays.asList(node1, node2, node4)),
			nodeMap.remove(node3));

		Assert.assertEquals(
			Collections.singleton(node2), nodeMap.remove(node4));

		Assert.assertTrue(nodeMap.toString(), nodeMap.isEmpty());
	}

	@Test
	public void testRoot() {
		Node node1 = new Node(1, 1);
		Node node2 = new Node(1, 2);

		Set<Node> nodes = new HashSet<>(Arrays.asList(node1, node2));

		Map<Node, Collection<Node>> nodeMap = GraphUtil.getNodeMap(
			nodes, Collections.emptyMap());

		Assert.assertEquals(nodeMap.toString(), 1, nodeMap.size());

		Assert.assertEquals(nodes, nodeMap.get(new Node(0, 0)));
	}

	@Test
	public void testSelfCycle() {
		Node node1 = new Node(1, 1);

		Map<Node, Collection<Node>> nodeMap = GraphUtil.getNodeMap(
			Collections.singleton(node1),
			Collections.singletonMap(
				node1, Collections.singletonList(new Edge(node1, node1))));

		Assert.assertEquals(
			Collections.singleton(node1), nodeMap.remove(new Node(0, 0)));

		Assert.assertTrue(nodeMap.toString(), nodeMap.isEmpty());
	}

	@Test
	public void testSimpleCycle() {
		Node node1 = new Node(1, 1);
		Node node2 = new Node(1, 2);

		Set<Node> nodes = new HashSet<>(Arrays.asList(node1, node2));

		Map<Node, Collection<Node>> nodeMap = GraphUtil.getNodeMap(
			nodes,
			HashMapBuilder.<Node, Collection<Edge>>put(
				node1, Collections.singletonList(new Edge(node1, node2))
			).put(
				node2, Collections.singletonList(new Edge(node2, node1))
			).build());

		Assert.assertEquals(
			Collections.singleton(node2), nodeMap.remove(new Node(0, 0)));

		Assert.assertEquals(
			Collections.singleton(node1), nodeMap.remove(node2));

		Assert.assertTrue(nodeMap.toString(), nodeMap.isEmpty());
	}

	@Test
	public void testTreeConverges() {
		Node node1 = new Node(1, 1);
		Node node2 = new Node(1, 2);
		Node node3 = new Node(1, 3);
		Node node4 = new Node(1, 4);
		Node node5 = new Node(1, 5);

		Set<Node> nodes = new HashSet<>(
			Arrays.asList(node1, node2, node3, node4, node5));

		Map<Node, Collection<Node>> nodeMap = GraphUtil.getNodeMap(
			nodes,
			HashMapBuilder.<Node, Collection<Edge>>put(
				node1, Collections.singletonList(new Edge(node1, node2))
			).put(
				node2,
				Arrays.asList(new Edge(node2, node3), new Edge(node2, node4))
			).put(
				node3, Collections.singletonList(new Edge(node3, node4))
			).put(
				node4, Collections.singletonList(new Edge(node4, node5))
			).build());

		Assert.assertEquals(
			Collections.singleton(node1), nodeMap.remove(new Node(0, 0)));

		Assert.assertEquals(
			Collections.singleton(node2), nodeMap.remove(node1));

		Assert.assertEquals(
			new HashSet<>(Arrays.asList(node3, node4)), nodeMap.remove(node2));

		Assert.assertEquals(
			Collections.singleton(node4), nodeMap.remove(node3));

		Assert.assertEquals(
			Collections.singleton(node5), nodeMap.remove(node4));

		Assert.assertTrue(nodeMap.toString(), nodeMap.isEmpty());
	}

}