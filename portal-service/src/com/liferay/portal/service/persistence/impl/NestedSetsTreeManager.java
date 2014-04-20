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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.NestedSetsTreeNodeModel;

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public abstract class NestedSetsTreeManager<T extends NestedSetsTreeNodeModel> {

	public long countAncestors(T t) throws SystemException {
		return doCountAncestors(
			t.getNestedSetsScopeId(), t.getNestedSetsLeft(),
			t.getNestedSetsRight());
	}

	public long countDescendants(T t) throws SystemException {
		return doCountDescendants(
			t.getNestedSetsScopeId(), t.getNestedSetsLeft(),
			t.getNestedSetsRight());
	}

	public void delete(T t) throws SystemException {
		long nestedSetsLeft = t.getNestedSetsLeft();
		long nestedSetsRight = t.getNestedSetsRight();

		doUpdate(
			t.getNestedSetsScopeId(), -1, nestedSetsLeft, false,
			nestedSetsRight, false, null);
		doUpdate(t.getNestedSetsScopeId(), true, -2, nestedSetsRight, false);
		doUpdate(t.getNestedSetsScopeId(), false, -2, nestedSetsRight, false);
	}

	public List<T> getAncestors(T t) throws SystemException {
		return doGetAncestors(
			t.getNestedSetsScopeId(), t.getNestedSetsLeft(),
			t.getNestedSetsRight());
	}

	public List<T> getDescendants(T t) throws SystemException {
		return doGetDescendants(
			t.getNestedSetsScopeId(), t.getNestedSetsLeft(),
			t.getNestedSetsRight());
	}

	public void insert(T t, T parent) throws SystemException {
		if (parent == null) {
			long maxNestedSetsRight = getMaxNestedSetsRight(
				t.getNestedSetsScopeId());

			t.setNestedSetsLeft(maxNestedSetsRight);
			t.setNestedSetsRight(maxNestedSetsRight + 1);
		}
		else {
			long nestedSetsRight = parent.getNestedSetsRight();

			doUpdate(t.getNestedSetsScopeId(), true, 2, nestedSetsRight, true);

			doUpdate(t.getNestedSetsScopeId(), false, 2, nestedSetsRight, true);

			t.setNestedSetsLeft(nestedSetsRight);
			t.setNestedSetsRight(nestedSetsRight + 1);
		}
	}

	public void move(T t, T oldParent, T newParent) throws SystemException {
		if (Validator.equals(oldParent, newParent)) {
			return;
		}

		long nestedSetsLeft = t.getNestedSetsLeft();
		long nestedSetsRight = t.getNestedSetsRight();

		List<T> childrenList = doGetDescendants(
			t.getNestedSetsScopeId(), nestedSetsLeft, nestedSetsRight);

		long newParentNestedSetsRight = 0;

		if (newParent == null) {
			newParentNestedSetsRight = getMaxNestedSetsRight(
				t.getNestedSetsScopeId());
		}
		else {
			newParentNestedSetsRight = newParent.getNestedSetsRight();
		}

		long delta = 0;

		if (nestedSetsRight < newParentNestedSetsRight) {
			doUpdate(
				t.getNestedSetsScopeId(),
				-(nestedSetsRight - nestedSetsLeft + 1), nestedSetsRight, false,
				newParentNestedSetsRight, false, null);

			delta = newParentNestedSetsRight - nestedSetsRight - 1;

			doUpdate(
				t.getNestedSetsScopeId(), delta, nestedSetsLeft, true,
				nestedSetsRight, true, childrenList);
		}
		else {
			doUpdate(
				t.getNestedSetsScopeId(), nestedSetsRight - nestedSetsLeft + 1,
				newParentNestedSetsRight, true, nestedSetsLeft, false, null);

			delta = newParentNestedSetsRight - nestedSetsLeft;

			doUpdate(
				t.getNestedSetsScopeId(), delta, nestedSetsLeft, true,
				nestedSetsRight, true, childrenList);
		}

		t.setNestedSetsLeft(nestedSetsLeft + delta);
		t.setNestedSetsRight(nestedSetsRight + delta);
	}

	protected abstract long doCountAncestors(
			long scopeId, long nestedSetsLeft, long nestedSetsRight)
		throws SystemException;

	protected abstract long doCountDescendants(
			long scopeId, long nestedSetsLeft, long nestedSetsRight)
		throws SystemException;

	protected abstract List<T> doGetAncestors(
			long scopeId, long nestedSetsLeft, long nestedSetsRight)
		throws SystemException;

	protected abstract List<T> doGetDescendants(
			long scopeId, long nestedSetsLeft, long nestedSetsRight)
		throws SystemException;

	protected abstract void doUpdate(
			long scopeId, boolean leftOrRight, long delta, long limit,
			boolean inclusive)
		throws SystemException;

	protected abstract void doUpdate(
			long scopeId, long delta, long start, boolean startIncluside,
			long end, boolean endInclusive, List<T> inList)
		throws SystemException;

	protected abstract long getMaxNestedSetsRight(long scopeId)
		throws SystemException;

}