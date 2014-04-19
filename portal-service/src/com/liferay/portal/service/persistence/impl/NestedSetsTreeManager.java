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
import com.liferay.portal.model.NestedSetsTreeNode;

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public abstract class NestedSetsTreeManager<T extends NestedSetsTreeNode> {

	public long countAncestors(T t) throws SystemException {
		return doCountAncestors(t.getScopeId(), t.getNSLeft(), t.getNSRight());
	}

	public long countDescendants(T t) throws SystemException {
		return doCountDescendants(
			t.getScopeId(), t.getNSLeft(), t.getNSRight());
	}

	public void delete(T t) throws SystemException {
		long nsLeft = t.getNSLeft();
		long nsRight = t.getNSRight();

		doUpdate(t.getScopeId(), -1, nsLeft, false, nsRight, false, null);
		doUpdate(t.getScopeId(), true, -2, nsRight, false);
		doUpdate(t.getScopeId(), false, -2, nsRight, false);
	}

	public List<T> getAncestors(T t) throws SystemException {
		return doGetAncestors(t.getScopeId(), t.getNSLeft(), t.getNSRight());
	}

	public List<T> getDescendants(T t) throws SystemException {
		return doGetDescendants(t.getScopeId(), t.getNSLeft(), t.getNSRight());
	}

	public void insert(T t, T parent) throws SystemException {
		if (parent == null) {
			long maxNSRight = getMaxNSRight(t.getScopeId());

			t.setNSLeft(maxNSRight);
			t.setNSRight(maxNSRight + 1);
		}
		else {
			long nsRight = parent.getNSRight();

			doUpdate(t.getScopeId(), true, 2, nsRight, true);

			doUpdate(t.getScopeId(), false, 2, nsRight, true);

			t.setNSLeft(nsRight);
			t.setNSRight(nsRight + 1);
		}
	}

	public void move(T t, T oldParent, T newParent) throws SystemException {
		if (Validator.equals(oldParent, newParent)) {
			return;
		}

		long nsLeft = t.getNSLeft();
		long nsRight = t.getNSRight();

		List<T> childrenList = doGetDescendants(
			t.getScopeId(), nsLeft, nsRight);

		long newParentNSRight = 0;

		if (newParent == null) {
			newParentNSRight = getMaxNSRight(t.getScopeId());
		}
		else {
			newParentNSRight = newParent.getNSRight();
		}

		long delta = 0;

		if (nsRight < newParentNSRight) {
			doUpdate(
				t.getScopeId(), -(nsRight - nsLeft + 1), nsRight, false,
				newParentNSRight, false, null);

			delta = newParentNSRight - nsRight - 1;

			doUpdate(
				t.getScopeId(), delta, nsLeft, true, nsRight, true,
				childrenList);
		}
		else {
			doUpdate(
				t.getScopeId(), nsRight - nsLeft + 1, newParentNSRight, true,
				nsLeft, false, null);

			delta = newParentNSRight - nsLeft;

			doUpdate(
				t.getScopeId(), delta, nsLeft, true, nsRight, true,
				childrenList);
		}

		t.setNSLeft(nsLeft + delta);
		t.setNSRight(nsRight + delta);
	}

	protected abstract long doCountAncestors(
			long scopeId, long nsLeft, long nsRight)
		throws SystemException;

	protected abstract long doCountDescendants(
			long scopeId, long nsLeft, long nsRight)
		throws SystemException;

	protected abstract List<T> doGetAncestors(
			long scopeId, long nsLeft, long nsRight)
		throws SystemException;

	protected abstract List<T> doGetDescendants(
			long scopeId, long nsLeft, long nsRight)
		throws SystemException;

	protected abstract void doUpdate(
			long scopeId, boolean leftOrRight, long delta, long limit,
			boolean inclusive)
		throws SystemException;

	protected abstract void doUpdate(
			long scopeId, long delta, long start, boolean startIncluside,
			long end, boolean endInclusive, List<T> inList)
		throws SystemException;

	protected abstract long getMaxNSRight(long scopeId) throws SystemException;

}