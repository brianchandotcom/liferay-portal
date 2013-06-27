/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ListUtil {

	public static <E> List<E> copy(List<E> master) {
		if (master == null) {
			return null;
		}

		return new ArrayList<E>(master);
	}

	public static <E> void copy(List<E> master, List<? super E> copy) {
		if ((master == null) || (copy == null)) {
			return;
		}

		copy.clear();

		copy.addAll(master);
	}

	public static void distinct(List<?> list) {
		distinct(list, null);
	}

	public static <E> void distinct(List<E> list, Comparator<E> comparator) {
		if ((list == null) || list.isEmpty()) {
			return;
		}

		Set<E> set = new HashSet<E>();

		Iterator<E> itr = list.iterator();

		while (itr.hasNext()) {
			E obj = itr.next();

			if (set.contains(obj)) {
				itr.remove();
			}
			else {
				set.add(obj);
			}
		}

		if (comparator != null) {
			Collections.sort(list, comparator);
		}
	}

	public static <E> List<E> fromArray(E[] array) {
		if ((array == null) || (array.length == 0)) {
			return new ArrayList<E>();
		}

		return new ArrayList<E>(Arrays.asList(array));
	}

	@SuppressWarnings("rawtypes")
	public static <E> List<E> fromCollection(Collection<E> c) {
		if ((c != null) && List.class.isAssignableFrom(c.getClass())) {
			return (List)c;
		}

		if ((c == null) || c.isEmpty()) {
			return new ArrayList<E>();
		}

		List<E> list = new ArrayList<E>(c.size());

		list.addAll(c);

		return list;
	}

	public static <E> List<E> fromEnumeration(Enumeration<E> enu) {
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

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new FileReader(file));

		String s = StringPool.BLANK;

		while ((s = unsyncBufferedReader.readLine()) != null) {
			list.add(s);
		}

		unsyncBufferedReader.close();

		return list;
	}

	public static List<String> fromFile(String fileName) throws IOException {
		return fromFile(new File(fileName));
	}

	public static <E> List<E> fromMapKeys(Map<E, ?> map) {
		if ((map == null) || map.isEmpty()) {
			return new ArrayList<E>();
		}

		List<E> list = new ArrayList<E>(map.size());

		for (Map.Entry<E, ?> entry : map.entrySet()) {
			list.add(entry.getKey());
		}

		return list;
	}

	public static <E> List<E> fromMapValues(Map<?, E> map) {
		if ((map == null) || map.isEmpty()) {
			return new ArrayList<E>();
		}

		List<E> list = new ArrayList<E>(map.size());

		for (Map.Entry<?, E> entry : map.entrySet()) {
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

	/**
	 * @deprecated As of 6.2.0
	 */
	public static <E> boolean remove(List<E> list, E element) {
		Iterator<E> itr = list.iterator();

		while (itr.hasNext()) {
			E curElement = itr.next();

			if ((curElement == element) || curElement.equals(element)) {
				itr.remove();

				return true;
			}
		}

		return false;
	}

	public static <E> List<E> remove(List<E> list, List<E> remove) {
		if ((list == null) || list.isEmpty() ||
			(remove == null)|| remove.isEmpty()) {

			return list;
		}

		list = copy(list);

		for (E element : remove) {
			list.remove(element);
		}

		return list;
	}

	public static <E> List<E> sort(List<E> list) {
		return sort(list, null);
	}

	public static <E> List<E> sort(
		List<E> list, Comparator<? super E> comparator) {

		if (UnmodifiableList.class.isAssignableFrom(list.getClass())) {
			list = copy(list);
		}

		Collections.sort(list, comparator);

		return list;
	}

	public static <E> List<E> subList(List<E> list, int start, int end) {
		List<E> newList = new ArrayList<E>();

		int normalizedSize = list.size() - 1;

		if ((start < 0) || (start > normalizedSize) || (end < 0) ||
			(start > end)) {

			return newList;
		}

		for (int i = start; (i < end) && (i <= normalizedSize); i++) {
			newList.add(list.get(i));
		}

		return newList;
	}

	public static List<Boolean> toList(boolean[] array) {
		if ((array == null) || (array.length == 0)) {
			return new ArrayList<Boolean>();
		}

		List<Boolean> list = new ArrayList<Boolean>(array.length);

		for (boolean value : array) {
			list.add(value);
		}

		return list;
	}

	public static List<Character> toList(char[] array) {
		if ((array == null) || (array.length == 0)) {
			return new ArrayList<Character>();
		}

		List<Character> list = new ArrayList<Character>(array.length);

		for (char value : array) {
			list.add(value);
		}

		return list;
	}

	public static List<Double> toList(double[] array) {
		if ((array == null) || (array.length == 0)) {
			return new ArrayList<Double>();
		}

		List<Double> list = new ArrayList<Double>(array.length);

		for (double value : array) {
			list.add(value);
		}

		return list;
	}

	public static <E> List<E> toList(E[] array) {
		if ((array == null) || (array.length == 0)) {
			return new ArrayList<E>();
		}

		return new ArrayList<E>(Arrays.asList(array));
	}

	public static List<Float> toList(float[] array) {
		if ((array == null) || (array.length == 0)) {
			return new ArrayList<Float>();
		}

		List<Float> list = new ArrayList<Float>(array.length);

		for (float value : array) {
			list.add(value);
		}

		return list;
	}

	public static List<Integer> toList(int[] array) {
		if ((array == null) || (array.length == 0)) {
			return new ArrayList<Integer>();
		}

		List<Integer> list = new ArrayList<Integer>(array.length);

		for (int value : array) {
			list.add(value);
		}

		return list;
	}

	public static List<Long> toList(long[] array) {
		if ((array == null) || (array.length == 0)) {
			return new ArrayList<Long>();
		}

		List<Long> list = new ArrayList<Long>(array.length);

		for (long value : array) {
			list.add(value);
		}

		return list;
	}

	public static List<Short> toList(short[] array) {
		if ((array == null) || (array.length == 0)) {
			return new ArrayList<Short>();
		}

		List<Short> list = new ArrayList<Short>(array.length);

		for (short value : array) {
			list.add(value);
		}

		return list;
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

		if ((list == null) || list.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(2 * list.size() - 1);

		for (int i = 0; i < list.size(); i++) {
			Object bean = list.get(i);

			Object value = BeanPropertiesUtil.getObject(bean, param);

			if (value != null) {
				sb.append(value);
			}

			if ((i + 1) != list.size()) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

	/**
	 * @see ArrayUtil#toString(Object[], Accessor)
	 */
	public static <T, V> String toString(
		List<T> list, Accessor<T, V> accessor) {

		return toString(list, accessor, StringPool.COMMA);
	}

	/**
	 * @see ArrayUtil#toString(Object[], Accessor, String)
	 */
	public static <T, V> String toString(
		List<T> list, Accessor<T, V> accessor, String delimiter) {

		if ((list == null) || list.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(2 * list.size() - 1);

		for (int i = 0; i < list.size(); i++) {
			T bean = list.get(i);

			V value = accessor.get(bean);

			if (value != null) {
				sb.append(value);
			}

			if ((i + 1) != list.size()) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

}