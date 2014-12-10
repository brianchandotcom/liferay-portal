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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ListUtil {

	public static <E> List<E> copy(List<? extends E> master) {
		if (master == null) {
			return null;
		}

		return new ArrayList<E>(master);
	}

	public static <E> void copy(
		List<? extends E> master, List<? super E> copy) {

		if ((master == null) || (copy == null)) {
			return;
		}

		copy.clear();

		copy.addAll(master);
	}

	public static <E> int count(
		List<? extends E> list, PredicateFilter<E> predicateFilter) {

		if (isEmpty(list)) {
			return 0;
		}

		int count = 0;

		for (E element : list) {
			if (predicateFilter.filter(element)) {
				count++;
			}
		}

		return count;
	}

	public static <E> void distinct(
		List<? extends E> list, Comparator<E> comparator) {

		if (isEmpty(list)) {
			return;
		}

		Set<E> set = new HashSet<E>();

		Iterator<? extends E> itr = list.iterator();

		while (itr.hasNext()) {
			E obj = itr.next();

			if (!set.add(obj)) {
				itr.remove();
			}
		}

		if (comparator != null) {
			Collections.sort(list, comparator);
		}
	}

	public static void distinct(List<?> list) {
		distinct(list, null);
	}

	public static <E> boolean exists(
		List<? extends E> list, PredicateFilter<E> predicateFilter) {

		if (isEmpty(list)) {
			return false;
		}

		for (E element : list) {
			if (predicateFilter.filter(element)) {
				return true;
			}
		}

		return false;
	}

	public static <T> List<T> filter(
		List<? extends T> inputList, List<T> outputList,
		PredicateFilter<T> predicateFilter) {

		for (T item : inputList) {
			if (predicateFilter.filter(item)) {
				outputList.add(item);
			}
		}

		return outputList;
	}

	public static <T> List<T> filter(
		List<? extends T> inputList, PredicateFilter<T> predicateFilter) {

		return filter(
			inputList, new ArrayList<T>(inputList.size()), predicateFilter);
	}

	public static <E> List<E> fromArray(E[] array) {
		if (ArrayUtil.isEmpty(array)) {
			return new ArrayList<E>();
		}

		return new ArrayList<E>(Arrays.asList(array));
	}

	@SuppressWarnings("rawtypes")
	public static <E> List<E> fromCollection(Collection<? extends E> c) {
		if ((c != null) && (c instanceof List)) {
			return (List)c;
		}

		if ((c == null) || c.isEmpty()) {
			return new ArrayList<E>();
		}

		List<E> list = new ArrayList<E>(c.size());

		list.addAll(c);

		return list;
	}

	public static <E> List<E> fromEnumeration(Enumeration<? extends E> enu) {
		List<E> list = new ArrayList<E>();

		while (enu.hasMoreElements()) {
			E obj = enu.nextElement();

			list.add(obj);
		}

		return list;
	}

	public static List<String> fromFile(File file) throws IOException {
		if (!file.exists()) {
			return new ArrayList<String>();
		}

		List<String> list = new ArrayList<String>();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new FileReader(file))) {

			String s = StringPool.BLANK;

			while ((s = unsyncBufferedReader.readLine()) != null) {
				list.add(s);
			}
		}

		return list;
	}

	public static List<String> fromFile(String fileName) throws IOException {
		return fromFile(new File(fileName));
	}

	public static <E> List<E> fromMapKeys(Map<? extends E, ?> map) {
		if (MapUtil.isEmpty(map)) {
			return new ArrayList<E>();
		}

		List<E> list = new ArrayList<E>(map.size());

		for (Map.Entry<? extends E, ?> entry : map.entrySet()) {
			list.add(entry.getKey());
		}

		return list;
	}

	public static <E> List<E> fromMapValues(Map<?, ? extends E> map) {
		if (MapUtil.isEmpty(map)) {
			return new ArrayList<E>();
		}

		List<E> list = new ArrayList<E>(map.size());

		for (Map.Entry<?, ? extends E> entry : map.entrySet()) {
			list.add(entry.getValue());
		}

		return list;
	}

	public static List<String> fromString(String s) {
		return fromArray(StringUtil.splitLines(s));
	}

	public static List<String> fromString(String s, String delimiter) {
		return fromArray(StringUtil.split(s, delimiter));
	}

	public static boolean isEmpty(List<?> list) {
		if ((list == null) || list.isEmpty()) {
			return true;
		}

		return false;
	}

	public static boolean isNotEmpty(List<?> list) {
		return !isEmpty(list);
	}

	public static boolean isUnmodifiableList(List<?> list) {
		return _unmodifiableListClass.isInstance(list);
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public static <E> boolean remove(List<? extends E> list, E element) {
		Iterator<? extends E> itr = list.iterator();

		while (itr.hasNext()) {
			E curElement = itr.next();

			if ((curElement == element) || curElement.equals(element)) {
				itr.remove();

				return true;
			}
		}

		return false;
	}

	public static <E> List<E> remove(List<E> list, List<? extends E> remove) {
		if (isEmpty(list) || isEmpty(remove)) {
			return list;
		}

		list = copy(list);

		for (E element : remove) {
			list.remove(element);
		}

		return list;
	}

	public static <E> Iterator<E> reverseIterator(List<E> list) {
		final ListIterator<E> listIterator = list.listIterator(list.size());

		return new Iterator<E>() {

			@Override
			public boolean hasNext() {
				return listIterator.hasPrevious();
			}

			@Override
			public E next() {
				return listIterator.previous();
			}

			@Override
			public void remove() {
				listIterator.remove();
			}

		};
	}

	public static <E> List<E> sort(List<E> list) {
		return sort(list, null);
	}

	public static <E> List<E> sort(
		List<E> list, Comparator<? super E> comparator) {

		if (isUnmodifiableList(list)) {
			list = copy(list);
		}

		Collections.sort(list, comparator);

		return list;
	}

	public static <E> List<E> subList(List<E> list, int start, int end) {
		if (start < 0) {
			start = 0;
		}

		if ((end < 0) || (end > list.size())) {
			end = list.size();
		}

		if (start < end) {
			return list.subList(start, end);
		}

		return Collections.emptyList();
	}

	public static <T, A> A[] toArray(
		List<? extends T> list, Accessor<T, A> accessor) {

		if (isEmpty(list)) {
			return (A[])Array.newInstance(accessor.getAttributeClass(), 0);
		}

		A[] array = (A[])Array.newInstance(
			accessor.getAttributeClass(), list.size());

		for (int i = 0; i < list.size(); i++) {
			T bean = list.get(i);

			A attribute = accessor.get(bean);

			array[i] = attribute;
		}

		return array;
	}

	public static List<Boolean> toList(boolean[] array) {
		if (ArrayUtil.isEmpty(array)) {
			return new ArrayList<Boolean>();
		}

		List<Boolean> list = new ArrayList<Boolean>(array.length);

		for (boolean value : array) {
			list.add(value);
		}

		return list;
	}

	public static List<Character> toList(char[] array) {
		if (ArrayUtil.isEmpty(array)) {
			return new ArrayList<Character>();
		}

		List<Character> list = new ArrayList<Character>(array.length);

		for (char value : array) {
			list.add(value);
		}

		return list;
	}

	public static List<Double> toList(double[] array) {
		if (ArrayUtil.isEmpty(array)) {
			return new ArrayList<Double>();
		}

		List<Double> list = new ArrayList<Double>(array.length);

		for (double value : array) {
			list.add(value);
		}

		return list;
	}

	public static <E> List<E> toList(E[] array) {
		if (ArrayUtil.isEmpty(array)) {
			return new ArrayList<E>();
		}

		return new ArrayList<E>(Arrays.asList(array));
	}

	public static List<Float> toList(float[] array) {
		if (ArrayUtil.isEmpty(array)) {
			return new ArrayList<Float>();
		}

		List<Float> list = new ArrayList<Float>(array.length);

		for (float value : array) {
			list.add(value);
		}

		return list;
	}

	public static List<Integer> toList(int[] array) {
		if (ArrayUtil.isEmpty(array)) {
			return new ArrayList<Integer>();
		}

		List<Integer> list = new ArrayList<Integer>(array.length);

		for (int value : array) {
			list.add(value);
		}

		return list;
	}

	public static <T, A> List<A> toList(List<T> list, Accessor<T, A> accessor) {
		List<A> aList = new ArrayList<A>(list.size());

		for (T t : list) {
			aList.add(accessor.get(t));
		}

		return aList;
	}

	public static <T, V extends T> List<T> toList(List<V> vlist) {
		return new ArrayList<T>(vlist);
	}

	public static List<Long> toList(long[] array) {
		if (ArrayUtil.isEmpty(array)) {
			return new ArrayList<Long>();
		}

		List<Long> list = new ArrayList<Long>(array.length);

		for (long value : array) {
			list.add(value);
		}

		return list;
	}

	public static List<Short> toList(short[] array) {
		if (ArrayUtil.isEmpty(array)) {
			return new ArrayList<Short>();
		}

		List<Short> list = new ArrayList<Short>(array.length);

		for (short value : array) {
			list.add(value);
		}

		return list;
	}

	public static <T> long[] toLongArray(
		List<? extends T> list, Accessor<T, Long> accessor) {

		if (isEmpty(list)) {
			return (long[])Array.newInstance(long.class, 0);
		}

		long[] array = (long[])Array.newInstance(long.class, list.size());

		for (int i = 0; i < list.size(); i++) {
			T bean = list.get(i);

			Long attribute = accessor.get(bean);

			array[i] = attribute;
		}

		return array;
	}

	/**
	 * @see ArrayUtil#toString(Object[], Accessor)
	 */
	public static <T, A> String toString(
		List<? extends T> list, Accessor<T, A> accessor) {

		return toString(list, accessor, StringPool.COMMA);
	}

	/**
	 * @see ArrayUtil#toString(Object[], Accessor, String)
	 */
	public static <T, A> String toString(
		List<? extends T> list, Accessor<T, A> accessor, String delimiter) {

		if (isEmpty(list)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(2 * list.size() - 1);

		for (int i = 0; i < list.size(); i++) {
			T bean = list.get(i);

			A attribute = accessor.get(bean);

			if (attribute != null) {
				sb.append(attribute);
			}

			if ((i + 1) != list.size()) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

	/**
	 * @see ArrayUtil#toString(Object[], String)
	 */
	public static String toString(List<?> list, String param) {
		return toString(list, param, StringPool.COMMA);
	}

	/**
	 * @see ArrayUtil#toString(Object[], String, String)
	 */
	public static String toString(
		List<?> list, String param, String delimiter) {

		if (isEmpty(list)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(2 * list.size() - 1);

		for (int i = 0; i < list.size(); i++) {
			Object bean = list.get(i);

			Object value = null;

			if (Validator.isNull(param)) {
				value = String.valueOf(bean);
			}
			else {
				value = BeanPropertiesUtil.getObject(bean, param);
			}

			if (value != null) {
				sb.append(value);
			}

			if ((i + 1) != list.size()) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

	public static <T> List<T> unique(List<T> list) {
		Set<T> set = new LinkedHashSet<T>();

		set.addAll(list);

		if (list.size() == set.size()) {
			return list;
		}

		return new ArrayList<T>(set);
	}

	private static Class<? extends List<?>> _unmodifiableListClass;

	static {
		List<Object> unmodifiableList = Collections.<Object>unmodifiableList(
			new LinkedList<Object>());

		_unmodifiableListClass =
			(Class<? extends List<?>>)unmodifiableList.getClass();
	}

}