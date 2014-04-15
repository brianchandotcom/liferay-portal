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

package com.liferay.portal.service.persistence.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.NestedSetsTreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class NestedSetsTreeManagerTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testCountAncestors() throws SystemException {
		testInsert();

		_assertCountAncestors(1, _nestedSetsNodes[0]);
		_assertCountAncestors(1, _nestedSetsNodes[1]);
		_assertCountAncestors(1, _nestedSetsNodes[2]);
		_assertCountAncestors(2, _nestedSetsNodes[3]);
		_assertCountAncestors(2, _nestedSetsNodes[4]);
		_assertCountAncestors(3, _nestedSetsNodes[5]);
		_assertCountAncestors(2, _nestedSetsNodes[6]);
		_assertCountAncestors(3, _nestedSetsNodes[7]);
		_assertCountAncestors(3, _nestedSetsNodes[8]);
	}

	@Test
	public void testCountDescendants() throws SystemException {
		testInsert();

		_assertCountDescendants(5, _nestedSetsNodes[0]);
		_assertCountDescendants(3, _nestedSetsNodes[1]);
		_assertCountDescendants(1, _nestedSetsNodes[2]);
		_assertCountDescendants(2, _nestedSetsNodes[3]);
		_assertCountDescendants(2, _nestedSetsNodes[4]);
		_assertCountDescendants(1, _nestedSetsNodes[5]);
		_assertCountDescendants(2, _nestedSetsNodes[6]);
		_assertCountDescendants(1, _nestedSetsNodes[7]);
		_assertCountDescendants(1, _nestedSetsNodes[8]);
	}

	@Test
	public void testDelete() throws SystemException {
		testInsert();

		_nestedSetsTreeManager.delete(_nestedSetsNodes[7]);

		Assert.assertEquals(
			"(1#0(2#3(3#5, 4), 5)(6#4(7#8, 8), 9), 10)(11#1(12#6, 13), 14)" +
				"(15#2, 16)",
			_nestedSetsTreeManager.toString());

		_nestedSetsTreeManager.delete(_nestedSetsNodes[4]);

		Assert.assertEquals(
			"(1#0(2#3(3#5, 4), 5)(6#8, 7), 8)(9#1(10#6, 11), 12)(13#2, 14)",
			_nestedSetsTreeManager.toString());

		_nestedSetsTreeManager.delete(_nestedSetsNodes[0]);

		Assert.assertEquals(
			"(1#3(2#5, 3), 4)(5#8, 6)(7#1(8#6, 9), 10)(11#2, 12)",
			_nestedSetsTreeManager.toString());

		_nestedSetsTreeManager.delete(_nestedSetsNodes[8]);

		Assert.assertEquals(
			"(1#3(2#5, 3), 4)(5#1(6#6, 7), 8)(9#2, 10)",
			_nestedSetsTreeManager.toString());

		_nestedSetsTreeManager.delete(_nestedSetsNodes[2]);

		Assert.assertEquals(
			"(1#3(2#5, 3), 4)(5#1(6#6, 7), 8)",
			_nestedSetsTreeManager.toString());

		_nestedSetsTreeManager.delete(_nestedSetsNodes[5]);

		Assert.assertEquals(
			"(1#3, 2)(3#1(4#6, 5), 6)", _nestedSetsTreeManager.toString());

		_nestedSetsTreeManager.delete(_nestedSetsNodes[1]);

		Assert.assertEquals(
			"(1#3, 2)(3#6, 4)", _nestedSetsTreeManager.toString());

		_nestedSetsTreeManager.delete(_nestedSetsNodes[6]);

		Assert.assertEquals("(1#3, 2)", _nestedSetsTreeManager.toString());

		_nestedSetsTreeManager.delete(_nestedSetsNodes[3]);

		Assert.assertEquals("", _nestedSetsTreeManager.toString());
	}

	@Test
	public void testGetAncestors() throws SystemException {
		testInsert();

		_assertGetAncestors(_nestedSetsNodes[0]);
		_assertGetAncestors(_nestedSetsNodes[1]);
		_assertGetAncestors(_nestedSetsNodes[2]);
		_assertGetAncestors(_nestedSetsNodes[3], _nestedSetsNodes[0]);
		_assertGetAncestors(_nestedSetsNodes[4], _nestedSetsNodes[0]);
		_assertGetAncestors(
			_nestedSetsNodes[5], _nestedSetsNodes[3], _nestedSetsNodes[0]);
		_assertGetAncestors(_nestedSetsNodes[6], _nestedSetsNodes[1]);
		_assertGetAncestors(
			_nestedSetsNodes[7], _nestedSetsNodes[6], _nestedSetsNodes[1]);
		_assertGetAncestors(
			_nestedSetsNodes[8], _nestedSetsNodes[4], _nestedSetsNodes[0]);
	}

	@Test
	public void testGetDescendants() throws SystemException {
		testInsert();

		_assertGetDescendants(
			_nestedSetsNodes[0], _nestedSetsNodes[3], _nestedSetsNodes[4],
			_nestedSetsNodes[5], _nestedSetsNodes[8]);
		_assertGetDescendants(
			_nestedSetsNodes[1], _nestedSetsNodes[6], _nestedSetsNodes[7]);
		_assertGetDescendants(_nestedSetsNodes[2]);
		_assertGetDescendants(_nestedSetsNodes[3], _nestedSetsNodes[5]);
		_assertGetDescendants(_nestedSetsNodes[4], _nestedSetsNodes[8]);
		_assertGetDescendants(_nestedSetsNodes[5]);
		_assertGetDescendants(_nestedSetsNodes[6], _nestedSetsNodes[7]);
		_assertGetDescendants(_nestedSetsNodes[7]);
		_assertGetDescendants(_nestedSetsNodes[8]);
	}

	@Test
	public void testInsert() throws SystemException {

		// (0)

		_nestedSetsTreeManager.insert(_nestedSetsNodes[0], null);

		Assert.assertEquals("(1#0, 2)", _nestedSetsTreeManager.toString());

		// (0, 1)

		_nestedSetsTreeManager.insert(_nestedSetsNodes[1], null);

		Assert.assertEquals(
			"(1#0, 2)(3#1, 4)", _nestedSetsTreeManager.toString());

		// (0, 1, 2)

		_nestedSetsTreeManager.insert(_nestedSetsNodes[2], null);

		Assert.assertEquals(
			"(1#0, 2)(3#1, 4)(5#2, 6)", _nestedSetsTreeManager.toString());

		// (0(3), 1, 2)

		_nestedSetsTreeManager.insert(_nestedSetsNodes[3], _nestedSetsNodes[0]);

		Assert.assertEquals(
			"(1#0(2#3, 3), 4)(5#1, 6)(7#2, 8)",
			_nestedSetsTreeManager.toString());

		// (0(3, 4), 1, 2)

		_nestedSetsTreeManager.insert(_nestedSetsNodes[4], _nestedSetsNodes[0]);

		Assert.assertEquals(
			"(1#0(2#3, 3)(4#4, 5), 6)(7#1, 8)(9#2, 10)",
			_nestedSetsTreeManager.toString());

		// (0(3(5), 4), 1, 2)

		_nestedSetsTreeManager.insert(_nestedSetsNodes[5], _nestedSetsNodes[3]);

		Assert.assertEquals(
			"(1#0(2#3(3#5, 4), 5)(6#4, 7), 8)(9#1, 10)(11#2, 12)",
			_nestedSetsTreeManager.toString());

		// (0(3(5), 4), 1(6), 2)

		_nestedSetsTreeManager.insert(_nestedSetsNodes[6], _nestedSetsNodes[1]);

		Assert.assertEquals(
			"(1#0(2#3(3#5, 4), 5)(6#4, 7), 8)(9#1(10#6, 11), 12)(13#2, 14)",
			_nestedSetsTreeManager.toString());

		// (0(3(5), 4), 1(6(7)), 2)

		_nestedSetsTreeManager.insert(_nestedSetsNodes[7], _nestedSetsNodes[6]);

		Assert.assertEquals(
			"(1#0(2#3(3#5, 4), 5)(6#4, 7), 8)(9#1(10#6(11#7, 12), 13), 14)" +
				"(15#2, 16)",
			_nestedSetsTreeManager.toString());

		// (0(3(5), 4(8)), 1(6(7)), 2)

		_nestedSetsTreeManager.insert(_nestedSetsNodes[8], _nestedSetsNodes[4]);

		Assert.assertEquals(
			"(1#0(2#3(3#5, 4), 5)(6#4(7#8, 8), 9), 10)" +
				"(11#1(12#6(13#7, 14), 15), 16)(17#2, 18)",
			_nestedSetsTreeManager.toString());
	}

	@Test
	public void testMove() throws SystemException {
		testInsert();

		_nestedSetsTreeManager.move(_nestedSetsNodes[4], null, null);

		Assert.assertEquals(
			"(1#0(2#3(3#5, 4), 5)(6#4(7#8, 8), 9), 10)" +
				"(11#1(12#6(13#7, 14), 15), 16)(17#2, 18)",
			_nestedSetsTreeManager.toString());

		_nestedSetsTreeManager.move(
			_nestedSetsNodes[4], _nestedSetsNodes[0], _nestedSetsNodes[0]);

		Assert.assertEquals(
			"(1#0(2#3(3#5, 4), 5)(6#4(7#8, 8), 9), 10)" +
				"(11#1(12#6(13#7, 14), 15), 16)(17#2, 18)",
			_nestedSetsTreeManager.toString());

		_nestedSetsTreeManager.move(
			_nestedSetsNodes[4], _nestedSetsNodes[0], _nestedSetsNodes[2]);

		Assert.assertEquals(
			"(1#0(2#3(3#5, 4), 5), 6)(7#1(8#6(9#7, 10), 11), 12)" +
				"(13#2(14#4(15#8, 16), 17), 18)",
			_nestedSetsTreeManager.toString());

		_nestedSetsTreeManager.move(
			_nestedSetsNodes[2], null, _nestedSetsNodes[0]);

		Assert.assertEquals(
			"(1#0(2#3(3#5, 4), 5)(6#2(7#4(8#8, 9), 10), 11), 12)" +
				"(13#1(14#6(15#7, 16), 17), 18)",
			_nestedSetsTreeManager.toString());

		_nestedSetsTreeManager.move(
			_nestedSetsNodes[3], _nestedSetsNodes[0], null);

		Assert.assertEquals(
			"(1#0(2#2(3#4(4#8, 5), 6), 7), 8)(9#1(10#6(11#7, 12), 13), 14)" +
				"(15#3(16#5, 17), 18)",
			_nestedSetsTreeManager.toString());

		_nestedSetsTreeManager.move(
			_nestedSetsNodes[1], null, _nestedSetsNodes[0]);

		Assert.assertEquals(
			"(1#0(2#2(3#4(4#8, 5), 6), 7)(8#1(9#6(10#7, 11), 12), 13), 14)" +
				"(15#3(16#5, 17), 18)",
			_nestedSetsTreeManager.toString());

		_nestedSetsTreeManager.move(
			_nestedSetsNodes[3], null, _nestedSetsNodes[1]);

		Assert.assertEquals(
			"(1#0(2#2(3#4(4#8, 5), 6), 7)(8#1(9#6(10#7, 11), 12)" +
				"(13#3(14#5, 15), 16), 17), 18)",
			_nestedSetsTreeManager.toString());

		_nestedSetsTreeManager.move(
			_nestedSetsNodes[2], _nestedSetsNodes[0], _nestedSetsNodes[3]);

		Assert.assertEquals(
			"(1#0(2#1(3#6(4#7, 5), 6)(7#3(8#5, 9)(10#2(11#4(12#8, 13), 14), " +
				"15), 16), 17), 18)",
			_nestedSetsTreeManager.toString());
	}

	private void _assertCountAncestors(
			long ancestorsCount,
			SimpleNestedSetsTreeNode simpleNestedSetsTreeNode)
		throws SystemException {

		Assert.assertEquals(
			ancestorsCount,
			_nestedSetsTreeManager.countAncestors(simpleNestedSetsTreeNode));
	}

	private void _assertCountDescendants(
			long childrenCount,
			SimpleNestedSetsTreeNode simpleNestedSetsTreeNode)
		throws SystemException {

		Assert.assertEquals(
			childrenCount,
			_nestedSetsTreeManager.countDescendants(simpleNestedSetsTreeNode));
	}

	private void _assertGetAncestors(
			SimpleNestedSetsTreeNode simpleNestedSetsTreeNode,
			SimpleNestedSetsTreeNode... ancestorSimpleNestedSetsTreeNodes)
		throws SystemException {

		List<SimpleNestedSetsTreeNode> simpleNestedSetsTreeNodes =
			new ArrayList<SimpleNestedSetsTreeNode>(
				Arrays.asList(ancestorSimpleNestedSetsTreeNodes));

		simpleNestedSetsTreeNodes.add(simpleNestedSetsTreeNode);

		Collections.sort(simpleNestedSetsTreeNodes);

		Assert.assertEquals(
			simpleNestedSetsTreeNodes,
			_nestedSetsTreeManager.getAncestors(simpleNestedSetsTreeNode));
	}

	private void _assertGetDescendants(
			SimpleNestedSetsTreeNode simpleNestedSetsTreeNode,
			SimpleNestedSetsTreeNode... childSimpleNestedSetsTreeNodes)
		throws SystemException {

		List<SimpleNestedSetsTreeNode> simpleNestedSetsTreeNodes =
			new ArrayList<SimpleNestedSetsTreeNode>(
				Arrays.asList(childSimpleNestedSetsTreeNodes));

		simpleNestedSetsTreeNodes.add(simpleNestedSetsTreeNode);

		Collections.sort(simpleNestedSetsTreeNodes);

		Assert.assertEquals(
			simpleNestedSetsTreeNodes,
			_nestedSetsTreeManager.getDescendants(simpleNestedSetsTreeNode));
	}

	private SimpleNestedSetsTreeNode[] _nestedSetsNodes =
		new SimpleNestedSetsTreeNode[] {
			new SimpleNestedSetsTreeNode(0), new SimpleNestedSetsTreeNode(1),
			new SimpleNestedSetsTreeNode(2), new SimpleNestedSetsTreeNode(3),
			new SimpleNestedSetsTreeNode(4), new SimpleNestedSetsTreeNode(5),
			new SimpleNestedSetsTreeNode(6), new SimpleNestedSetsTreeNode(7),
			new SimpleNestedSetsTreeNode(8)
		};
	private NestedSetsTreeManager<SimpleNestedSetsTreeNode>
		_nestedSetsTreeManager = new ListNestedSetsTreeManager();

	private static class ListNestedSetsTreeManager
		extends NestedSetsTreeManager<SimpleNestedSetsTreeNode> {

		@Override
		protected long doCountAncestors(
			long scopeId, long nsLeft, long nsRight) {

			long count = 0;

			for (SimpleNestedSetsTreeNode simpleNestedSetsTreeNode :
					_simpleNestedSetsTreeNodes) {

				if ((nsLeft >= simpleNestedSetsTreeNode._nsLeft) &&
					(nsRight <= simpleNestedSetsTreeNode._nsRight)) {

					count++;
				}
			}

			return count;
		}

		@Override
		protected long doCountDescendants(
			long scopeId, long nsLeft, long nsRight) {

			long count = 0;

			for (SimpleNestedSetsTreeNode simpleNestedSetsTreeNode :
					_simpleNestedSetsTreeNodes) {

				if ((nsLeft <= simpleNestedSetsTreeNode._nsLeft) &&
					(nsRight >= simpleNestedSetsTreeNode._nsRight)) {

					count++;
				}
			}

			return count;
		}

		@Override
		public void delete(SimpleNestedSetsTreeNode t) throws SystemException {
			super.delete(t);

			_simpleNestedSetsTreeNodes.remove(t);
		}

		@Override
		protected List<SimpleNestedSetsTreeNode> doGetAncestors(
			long scopeId, long nsLeft, long nsRight) {

			List<SimpleNestedSetsTreeNode> simpleNestedSetsTreeNodes =
				new ArrayList<SimpleNestedSetsTreeNode>();

			for (SimpleNestedSetsTreeNode simpleNestedSetsTreeNode :
					_simpleNestedSetsTreeNodes) {

				if ((nsLeft >= simpleNestedSetsTreeNode._nsLeft) &&
					(nsRight <= simpleNestedSetsTreeNode._nsRight)) {

					simpleNestedSetsTreeNodes.add(simpleNestedSetsTreeNode);
				}
			}

			Collections.sort(simpleNestedSetsTreeNodes);

			return simpleNestedSetsTreeNodes;
		}

		@Override
		protected List<SimpleNestedSetsTreeNode> doGetDescendants(
			long scopeId, long nsLeft, long nsRight) {

			List<SimpleNestedSetsTreeNode> simpleNestedSetsTreeNodes =
				new ArrayList<SimpleNestedSetsTreeNode>();

			for (SimpleNestedSetsTreeNode simpleNestedSetsTreeNode :
					_simpleNestedSetsTreeNodes) {

				if ((nsLeft <= simpleNestedSetsTreeNode._nsLeft) &&
					(nsRight >= simpleNestedSetsTreeNode._nsRight)) {

					simpleNestedSetsTreeNodes.add(simpleNestedSetsTreeNode);
				}
			}

			Collections.sort(simpleNestedSetsTreeNodes);

			return simpleNestedSetsTreeNodes;
		}

		@Override
		public void insert(
				SimpleNestedSetsTreeNode simpleNestedSetsTreeNode,
				SimpleNestedSetsTreeNode parentSimpleNestedSetsTreeNode)
			throws SystemException {

			super.insert(
				simpleNestedSetsTreeNode, parentSimpleNestedSetsTreeNode);

			_simpleNestedSetsTreeNodes.add(simpleNestedSetsTreeNode);
		}

		@Override
		protected void doUpdate(
			long scopeId, boolean leftOrRight, long delta, long limit,
			boolean inclusive) {

			for (SimpleNestedSetsTreeNode simpleNestedSetsTreeNode :
					_simpleNestedSetsTreeNodes) {

				if (leftOrRight) {
					long nsLeft = simpleNestedSetsTreeNode.getNSLeft();

					if (inclusive) {
						if (nsLeft >= limit) {
							simpleNestedSetsTreeNode.setNSLeft(nsLeft + delta);
						}
					}
					else {
						if (nsLeft > limit) {
							simpleNestedSetsTreeNode.setNSLeft(nsLeft + delta);
						}
					}
				}
				else {
					long nsRight = simpleNestedSetsTreeNode.getNSRight();

					if (inclusive) {
						if (nsRight >= limit) {
							simpleNestedSetsTreeNode.setNSRight(
								nsRight + delta);
						}
					}
					else {
						if (nsRight > limit) {
							simpleNestedSetsTreeNode.setNSRight(
								nsRight + delta);
						}
					}
				}
			}
		}

		private boolean _isInRange(
			long value, long start, boolean startIncluside, long end,
			boolean endInclusive) {

			if (startIncluside) {
				if (value < start) {
					return false;
				}
			}
			else {
				if (value <= start) {
					return false;
				}
			}

			if (endInclusive) {
				if (value > end) {
					return false;
				}
			}
			else {
				if (value >= end) {
					return false;
				}
			}

			return true;
		}

		@Override
		protected void doUpdate(
			long scopeId, long delta, long start, boolean startIncluside,
			long end, boolean endInclusive,
			List<SimpleNestedSetsTreeNode> inList) {

			for (SimpleNestedSetsTreeNode simpleNestedSetsTreeNode :
					_simpleNestedSetsTreeNodes) {

				if ((inList != null) &&
					!inList.contains(simpleNestedSetsTreeNode)) {

					continue;
				}

				long nsLeft = simpleNestedSetsTreeNode._nsLeft;

				if (_isInRange(
						nsLeft, start, startIncluside, end, endInclusive)) {

					simpleNestedSetsTreeNode.setNSLeft(nsLeft + delta);
				}

				long nsRight = simpleNestedSetsTreeNode._nsRight;

				if (_isInRange(
						nsRight, start, startIncluside, end, endInclusive)) {

					simpleNestedSetsTreeNode.setNSRight(nsRight + delta);
				}
			}
		}

		@Override
		protected long getMaxNSRight(long scopeId) {
			long maxNSRight = 0;

			for (SimpleNestedSetsTreeNode simpleNestedSetsTreeNode :
					_simpleNestedSetsTreeNodes) {

				long nsRight = simpleNestedSetsTreeNode.getNSRight();

				if (nsRight > maxNSRight) {
					maxNSRight = nsRight;
				}
			}

			return maxNSRight + 1;
		}

		@Override
		public String toString() {
			StringBundler sb = new StringBundler(
				_simpleNestedSetsTreeNodes.size() * 7);

			Collections.sort(_simpleNestedSetsTreeNodes);

			Deque<SimpleNestedSetsTreeNode> deque =
				new LinkedList<SimpleNestedSetsTreeNode>();

			for (SimpleNestedSetsTreeNode simpleNestedSetsTreeNode :
					_simpleNestedSetsTreeNodes) {

				long nsLeft = simpleNestedSetsTreeNode.getNSLeft();
				long nsRight = simpleNestedSetsTreeNode.getNSRight();

				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(nsLeft);
				sb.append(StringPool.POUND);
				sb.append(simpleNestedSetsTreeNode.getId());

				if ((nsLeft + 1) != nsRight) {
					deque.push(simpleNestedSetsTreeNode);

					continue;
				}

				sb.append(StringPool.COMMA_AND_SPACE);
				sb.append(nsRight);
				sb.append(StringPool.CLOSE_PARENTHESIS);

				SimpleNestedSetsTreeNode previousNode = null;

				while (((previousNode = deque.peek()) != null) &&
					   ((nsRight + 1) == previousNode.getNSRight())) {

					sb.append(StringPool.COMMA_AND_SPACE);
					sb.append(previousNode.getNSRight());
					sb.append(StringPool.CLOSE_PARENTHESIS);

					nsRight = previousNode.getNSRight();

					deque.pop();
				}
			}

			return sb.toString();
		}

		private List<SimpleNestedSetsTreeNode> _simpleNestedSetsTreeNodes =
			new ArrayList<NestedSetsTreeManagerTest.SimpleNestedSetsTreeNode>();
	}

	private static class SimpleNestedSetsTreeNode
		implements Comparable<SimpleNestedSetsTreeNode>, NestedSetsTreeNode {

		public SimpleNestedSetsTreeNode(long id) {
			_id = id;
		}

		@Override
		public int compareTo(
			SimpleNestedSetsTreeNode simpleNestedSetsTreeNode) {

			if (_nsLeft > simpleNestedSetsTreeNode._nsLeft) {
				return 1;
			}
			else if (_nsLeft == simpleNestedSetsTreeNode._nsLeft) {
				return 0;
			}
			else {
				return -1;
			}
		}

		@Override
		public boolean equals(Object obj) {
			SimpleNestedSetsTreeNode simpleNestedSetsTreeNode =
				(SimpleNestedSetsTreeNode)obj;

			return _id == simpleNestedSetsTreeNode._id;
		}

		@Override
		public long getId() {
			return _id;
		}

		@Override
		public long getNSLeft() {
			return _nsLeft;
		}

		@Override
		public long getNSRight() {
			return _nsRight;
		}

		@Override
		public long getScopeId() {
			return 0;
		}

		@Override
		public void setNSLeft(long nsLeft) {
			_nsLeft = nsLeft;
		}

		@Override
		public void setNSRight(long nsRight) {
			_nsRight = nsRight;
		}

		@Override
		public String toString() {
			StringBundler sb = new StringBundler(7);

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(_nsLeft);
			sb.append(StringPool.POUND);
			sb.append(_id);
			sb.append(StringPool.COMMA_AND_SPACE);
			sb.append(_nsRight);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			return sb.toString();
		}

		private final long _id;
		private long _nsLeft;
		private long _nsRight;

	}

}