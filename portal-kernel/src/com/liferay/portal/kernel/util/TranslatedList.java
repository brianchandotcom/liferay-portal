/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class TranslatedList<E, F> extends ListWrapper<E> {

	public TranslatedList(List<E> newList, List<F> oldList) {
		super(newList);

		_oldList = oldList;
	}

	@Override
	public boolean add(E o) {
		_oldList.add(toOldObject(o));

		return super.add(o);
	}

	@Override
	public void add(int index, E element) {
		_oldList.add(index, toOldObject(element));

		super.add(index, element);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		for (E o : c) {
			_oldList.add(toOldObject(o));
		}

		return super.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		for (E o : c) {
			_oldList.add(index++, toOldObject(o));
		}

		return super.addAll(c);
	}

	@Override
	public E remove(int index) {
		_oldList.remove(index);

		return super.remove(index);
	}

	@Override
	public boolean remove(Object object) {
		_oldList.remove(toOldObject((E)object));

		return super.remove(object);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		List<F> tempList = new ArrayList<>();

		for (Object object : c) {
			tempList.add(toOldObject((E)object));
		}

		_oldList.removeAll(tempList);

		return super.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		List<F> tempList = new ArrayList<>();

		for (Object object : c) {
			tempList.add(toOldObject((E)object));
		}

		_oldList.retainAll(tempList);

		return super.retainAll(c);
	}

	@Override
	public E set(int index, E element) {
		_oldList.set(index, toOldObject(element));

		return super.set(index, element);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		List<E> newList = super.subList(fromIndex, toIndex);
		List<F> oldList = _oldList.subList(fromIndex, toIndex);

		return newInstance(newList, oldList);
	}

	protected abstract TranslatedList<E, F> newInstance(
		List<E> newList, List<F> oldList);

	protected abstract F toOldObject(E o);

	private final List<F> _oldList;

}