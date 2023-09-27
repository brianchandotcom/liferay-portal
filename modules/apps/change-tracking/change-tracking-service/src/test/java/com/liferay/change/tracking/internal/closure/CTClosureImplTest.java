/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.closure;

import com.liferay.change.tracking.closure.CTClosure;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class CTClosureImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@Test
	public void testCTClosureImpl() {
		Node node1 = new Node(1, 1);
		Node node2 = new Node(2, 2);
		Node node3 = new Node(3, 3);
		Node node4 = new Node(4, 4);
		Node node5 = new Node(5, 5);

		List<Node> nodes = new ArrayList<>(
			Arrays.asList(node1, node2, node3, node4, node5));

		Map<Node, Collection<Node>> nodeMap = ReflectionTestUtil.invoke(
			new CTClosureFactoryImpl(), "_getNodeMap",
			new Class<?>[] {List.class, Map.class}, nodes,
			HashMapBuilder.<Node, Collection<Edge>>put(
				node1,
				Arrays.asList(
					new Edge(node1, node2), new Edge(node1, node3),
					new Edge(node1, node4))
			).put(
				node2,
				Arrays.asList(new Edge(node2, node3), new Edge(node2, node4))
			).put(
				node3, Collections.singleton(new Edge(node3, node4))
			).build());

		long ctCollectionId = 1;

		CTClosure ctClosure = new CTClosureImpl(ctCollectionId, nodeMap);

		Assert.assertEquals(ctCollectionId, ctClosure.getCTCollectionId());

		Assert.assertEquals(
			HashMapBuilder.<Long, List<Long>>put(
				node1.getClassNameId(),
				Collections.singletonList(node1.getPrimaryKey())
			).put(
				node5.getClassNameId(),
				Collections.singletonList(node5.getPrimaryKey())
			).build(),
			ctClosure.getRootPKsMap());

		Assert.assertEquals(
			Collections.singletonMap(
				node2.getClassNameId(),
				Collections.singletonList(node2.getPrimaryKey())),
			ctClosure.getChildPKsMap(
				node1.getClassNameId(), node1.getPrimaryKey()));

		Assert.assertEquals(
			Collections.singletonMap(
				node3.getClassNameId(),
				Collections.singletonList(node3.getPrimaryKey())),
			ctClosure.getChildPKsMap(
				node2.getClassNameId(), node2.getPrimaryKey()));

		Assert.assertEquals(
			Collections.singletonMap(
				node4.getClassNameId(),
				Collections.singletonList(node4.getPrimaryKey())),
			ctClosure.getChildPKsMap(
				node3.getClassNameId(), node3.getPrimaryKey()));

		Assert.assertSame(
			Collections.emptyMap(),
			ctClosure.getChildPKsMap(
				node4.getClassNameId(), node4.getPrimaryKey()));

		Assert.assertSame(
			Collections.emptyMap(),
			ctClosure.getChildPKsMap(
				node5.getClassNameId(), node5.getPrimaryKey()));

		Assert.assertEquals(
			StringBundler.concat(
				"{\n\t(classNameId=1, classPK=1)\n\t\t(classNameId=2, ",
				"classPK=2)\n\t\t\t(classNameId=3, classPK=3)\n\t\t\t\t(",
				"classNameId=4, classPK=4)\n\t(classNameId=5, classPK=5)\n}"),
			ctClosure.toString());
	}

	@Test
	public void testIntersectingCycles() {
		Node node1 = new Node(1, 1);
		Node node2 = new Node(1, 2);
		Node node3 = new Node(1, 3);
		Node node4 = new Node(1, 4);

		List<Node> nodes = new ArrayList<>(
			Arrays.asList(node1, node2, node3, node4));

		Map<Node, Collection<Node>> nodeMap = ReflectionTestUtil.invoke(
			new CTClosureFactoryImpl(), "_getNodeMap",
			new Class<?>[] {List.class, Map.class}, nodes,
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
			Collections.singletonList(node3), nodeMap.remove(new Node(0, 0)));

		Assert.assertEquals(
			Arrays.asList(node1, node2, node4), nodeMap.remove(node3));

		Assert.assertEquals(
			Collections.singletonList(node2), nodeMap.remove(node4));

		Assert.assertTrue(nodeMap.toString(), nodeMap.isEmpty());
	}

	@Test
	public void testRoot() {
		Node node1 = new Node(1, 1);
		Node node2 = new Node(1, 2);

		List<Node> nodes = new ArrayList<>(Arrays.asList(node1, node2));

		Map<Node, Collection<Node>> nodeMap = ReflectionTestUtil.invoke(
			new CTClosureFactoryImpl(), "_getNodeMap",
			new Class<?>[] {List.class, Map.class}, nodes,
			Collections.emptyMap());

		Assert.assertEquals(nodeMap.toString(), 1, nodeMap.size());

		Assert.assertEquals(nodes, nodeMap.get(new Node(0, 0)));
	}

	@Test
	public void testSelfCycle() {
		Node node1 = new Node(1, 1);

		Map<Node, Collection<Node>> nodeMap = ReflectionTestUtil.invoke(
			new CTClosureFactoryImpl(), "_getNodeMap",
			new Class<?>[] {List.class, Map.class},
			Collections.singletonList(node1),
			Collections.singletonMap(
				node1, Collections.singletonList(new Edge(node1, node1))));

		Assert.assertEquals(
			Collections.singletonList(node1), nodeMap.remove(new Node(0, 0)));

		Assert.assertTrue(nodeMap.toString(), nodeMap.isEmpty());
	}

	@Test
	public void testSimpleCycle() {
		Node node1 = new Node(1, 1);
		Node node2 = new Node(1, 2);

		List<Node> nodes = new ArrayList<>(Arrays.asList(node1, node2));

		Map<Node, Collection<Node>> nodeMap = ReflectionTestUtil.invoke(
			new CTClosureFactoryImpl(), "_getNodeMap",
			new Class<?>[] {List.class, Map.class}, nodes,
			HashMapBuilder.<Node, Collection<Edge>>put(
				node1, Collections.singletonList(new Edge(node1, node2))
			).put(
				node2, Collections.singletonList(new Edge(node2, node1))
			).build());

		Assert.assertEquals(
			Collections.singletonList(node2), nodeMap.remove(new Node(0, 0)));

		Assert.assertEquals(
			Collections.singletonList(node1), nodeMap.remove(node2));

		Assert.assertTrue(nodeMap.toString(), nodeMap.isEmpty());
	}

	@Test
	public void testTreeConverges() {
		Node node1 = new Node(1, 1);
		Node node2 = new Node(1, 2);
		Node node3 = new Node(1, 3);
		Node node4 = new Node(1, 4);
		Node node5 = new Node(1, 5);

		List<Node> nodes = new ArrayList<>(
			Arrays.asList(node1, node2, node3, node4, node5));

		Map<Node, Collection<Node>> nodeMap = ReflectionTestUtil.invoke(
			new CTClosureFactoryImpl(), "_getNodeMap",
			new Class<?>[] {List.class, Map.class}, nodes,
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
			Collections.singletonList(node1), nodeMap.remove(new Node(0, 0)));
		Assert.assertEquals(
			Collections.singletonList(node2), nodeMap.remove(node1));
		Assert.assertEquals(Arrays.asList(node3, node4), nodeMap.remove(node2));
		Assert.assertEquals(
			Collections.singletonList(node4), nodeMap.remove(node3));
		Assert.assertEquals(
			Collections.singletonList(node5), nodeMap.remove(node4));
		Assert.assertTrue(nodeMap.toString(), nodeMap.isEmpty());
	}

}