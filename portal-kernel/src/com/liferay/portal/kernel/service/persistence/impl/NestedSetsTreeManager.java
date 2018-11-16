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

package com.liferay.portal.kernel.service.persistence.impl;

import com.liferay.portal.kernel.model.NestedSetsTreeNodeModel;

import java.util.List;
import java.util.Objects;

/**
 * @author Shuyang Zhou
 */
public abstract class NestedSetsTreeManager<T extends NestedSetsTreeNodeModel> {

	public long countAncestors(T t) {
		return doCountAncestors(
			t.getNestedSetsTreeNodeScopeId(), t.getNestedSetsTreeNodeLeft(),
			t.getNestedSetsTreeNodeRight());
	}

	public long countDescendants(T t) {
		return doCountDescendants(
			t.getNestedSetsTreeNodeScopeId(), t.getNestedSetsTreeNodeLeft(),
			t.getNestedSetsTreeNodeRight());
	}

	public void delete(T t) {
		doUpdate(
			t.getNestedSetsTreeNodeScopeId(), -1, t.getNestedSetsTreeNodeLeft(),
			false, t.getNestedSetsTreeNodeRight(), false, null);
		doUpdate(
			t.getNestedSetsTreeNodeScopeId(), true, -2,
			t.getNestedSetsTreeNodeRight(), false);
		doUpdate(
			t.getNestedSetsTreeNodeScopeId(), false, -2,
			t.getNestedSetsTreeNodeRight(), false);
	}

	public List<T> getAncestors(T t) {
		return doGetAncestors(
			t.getNestedSetsTreeNodeScopeId(), t.getNestedSetsTreeNodeLeft(),
			t.getNestedSetsTreeNodeRight());
	}

	public List<T> getDescendants(T t) {
		return doGetDescendants(
			t.getNestedSetsTreeNodeScopeId(), t.getNestedSetsTreeNodeLeft(),
			t.getNestedSetsTreeNodeRight());
	}

	public void insert(T t, T parentT) {
		if (parentT == null) {
			long maxNestedSetsTreeNodeRight = getMaxNestedSetsTreeNodeRight(
				t.getNestedSetsTreeNodeScopeId());

			t.setNestedSetsTreeNodeLeft(maxNestedSetsTreeNodeRight);
			t.setNestedSetsTreeNodeRight(maxNestedSetsTreeNodeRight + 1);
		}
		else {
			doUpdate(
				t.getNestedSetsTreeNodeScopeId(), true, 2,
				parentT.getNestedSetsTreeNodeRight(), true);
			doUpdate(
				t.getNestedSetsTreeNodeScopeId(), false, 2,
				parentT.getNestedSetsTreeNodeRight(), true);

			t.setNestedSetsTreeNodeLeft(parentT.getNestedSetsTreeNodeRight());
			t.setNestedSetsTreeNodeRight(
				parentT.getNestedSetsTreeNodeRight() + 1);
		}
	}

	public void move(T t, T oldParentT, T newParentT) {
		if (Objects.equals(oldParentT, newParentT)) {
			return;
		}

		List<T> descendants = doGetDescendants(
			t.getNestedSetsTreeNodeScopeId(), t.getNestedSetsTreeNodeLeft(),
			t.getNestedSetsTreeNodeRight());

		long newParentNestedSetsTreeNodeRight = 0;

		if (newParentT == null) {
			newParentNestedSetsTreeNodeRight = getMaxNestedSetsTreeNodeRight(
				t.getNestedSetsTreeNodeScopeId());
		}
		else {
			newParentNestedSetsTreeNodeRight =
				newParentT.getNestedSetsTreeNodeRight();
		}

		long delta = 0;

		if (t.getNestedSetsTreeNodeRight() < newParentNestedSetsTreeNodeRight) {
			doUpdate(
				t.getNestedSetsTreeNodeScopeId(),
				-(t.getNestedSetsTreeNodeRight() -
					t.getNestedSetsTreeNodeLeft() + 1),
				t.getNestedSetsTreeNodeRight(), false,
				newParentNestedSetsTreeNodeRight, false, null);

			delta =
				newParentNestedSetsTreeNodeRight -
					t.getNestedSetsTreeNodeRight() - 1;

			doUpdate(
				t.getNestedSetsTreeNodeScopeId(), delta,
				t.getNestedSetsTreeNodeLeft(), true,
				t.getNestedSetsTreeNodeRight(), true, descendants);
		}
		else {
			doUpdate(
				t.getNestedSetsTreeNodeScopeId(),
				t.getNestedSetsTreeNodeRight() - t.getNestedSetsTreeNodeLeft() +
					1,
				newParentNestedSetsTreeNodeRight, true,
				t.getNestedSetsTreeNodeLeft(), false, null);

			delta =
				newParentNestedSetsTreeNodeRight -
					t.getNestedSetsTreeNodeLeft();

			doUpdate(
				t.getNestedSetsTreeNodeScopeId(), delta,
				t.getNestedSetsTreeNodeLeft(), true,
				t.getNestedSetsTreeNodeRight(), true, descendants);
		}

		t.setNestedSetsTreeNodeLeft(t.getNestedSetsTreeNodeLeft() + delta);
		t.setNestedSetsTreeNodeRight(t.getNestedSetsTreeNodeRight() + delta);
	}

	protected abstract long doCountAncestors(
		long nestedSetsTreeNodeScopeId, long nestedSetsTreeNodeLeft,
		long nestedSetsTreeNodeRight);

	protected abstract long doCountDescendants(
		long nestedSetsTreeNodeScopeId, long nestedSetsTreeNodeLeft,
		long nestedSetsTreeNodeRight);

	protected abstract List<T> doGetAncestors(
		long nestedSetsTreeNodeScopeId, long nestedSetsTreeNodeLeft,
		long nestedSetsTreeNodeRight);

	protected abstract List<T> doGetDescendants(
		long nestedSetsTreeNodeScopeId, long nestedSetsTreeNodeLeft,
		long nestedSetsTreeNodeRight);

	protected abstract void doUpdate(
		long nestedSetsTreeNodeScopeId, boolean leftOrRight, long delta,
		long limit, boolean inclusive);

	protected abstract void doUpdate(
		long nestedSetsTreeNodeScopeId, long delta, long start,
		boolean startInclusive, long end, boolean endInclusive,
		List<T> includeList);

	protected abstract long getMaxNestedSetsTreeNodeRight(
		long nestedSetsTreeNodeScopeId);

}