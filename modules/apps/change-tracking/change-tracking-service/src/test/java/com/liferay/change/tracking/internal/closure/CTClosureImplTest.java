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

import com.liferay.change.tracking.closure.CTClosure;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

		Set<Node> nodes = new HashSet<>(
			Arrays.asList(node1, node2, node3, node4, node5));

		Map<Node, Collection<Node>> nodeMap = GraphUtil.getNodeMap(
			nodes,
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

}