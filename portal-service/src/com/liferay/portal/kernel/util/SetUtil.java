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

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class SetUtil {

	public static Set<Boolean> fromArray(boolean[] array) {
		if (ArrayUtil.isEmpty(array)) {
			return new HashSet<Boolean>();
		}

		Set<Boolean> set = new HashSet<Boolean>(array.length);

		for (int i = 0; i < array.length; i++) {
			set.add(array[i]);
		}

		return set;
	}

	public static Set<Byte> fromArray(byte[] array) {
		if (ArrayUtil.isEmpty(array)) {
			return new HashSet<Byte>();
		}

		Set<Byte> set = new HashSet<Byte>(array.length);

		for (int i = 0; i < array.length; i++) {
			set.add(array[i]);
		}

		return set;
	}

	public static Set<Character> fromArray(char[] array) {
		if (ArrayUtil.isEmpty(array)) {
			return new HashSet<Character>();
		}

		Set<Character> set = new HashSet<Character>(array.length);

		for (int i = 0; i < array.length; i++) {
			set.add(array[i]);
		}

		return set;
	}

	public static Set<Double> fromArray(double[] array) {
		if (ArrayUtil.isEmpty(array)) {
			return new HashSet<Double>();
		}

		Set<Double> set = new HashSet<Double>(array.length);

		for (int i = 0; i < array.length; i++) {
			set.add(array[i]);
		}

		return set;
	}

	public static <E> Set<E> fromArray(E[] array) {
		if (ArrayUtil.isEmpty(array)) {
			return new HashSet<E>();
		}

		Set<E> set = new HashSet<E>(array.length);

		for (int i = 0; i < array.length; i++) {
			set.add(array[i]);
		}

		return set;
	}

	public static Set<Float> fromArray(float[] array) {
		if (ArrayUtil.isEmpty(array)) {
			return new HashSet<Float>();
		}

		Set<Float> set = new HashSet<Float>(array.length);

		for (int i = 0; i < array.length; i++) {
			set.add(array[i]);
		}

		return set;
	}

	public static Set<Integer> fromArray(int[] array) {
		if (ArrayUtil.isEmpty(array)) {
			return new HashSet<Integer>();
		}

		Set<Integer> set = new HashSet<Integer>(array.length);

		for (int i = 0; i < array.length; i++) {
			set.add(array[i]);
		}

		return set;
	}

	public static Set<Long> fromArray(long[] array) {
		if (ArrayUtil.isEmpty(array)) {
			return new HashSet<Long>();
		}

		Set<Long> set = new HashSet<Long>(array.length);

		for (int i = 0; i < array.length; i++) {
			set.add(array[i]);
		}

		return set;
	}

	public static Set<Short> fromArray(short[] array) {
		if (ArrayUtil.isEmpty(array)) {
			return new HashSet<Short>();
		}

		Set<Short> set = new HashSet<Short>(array.length);

		for (int i = 0; i < array.length; i++) {
			set.add(array[i]);
		}

		return set;
	}

	@SuppressWarnings("rawtypes")
	public static <E> Set<E> fromCollection(Collection<E> c) {
		if ((c != null) && Set.class.isAssignableFrom(c.getClass())) {
			return (Set)c;
		}

		if ((c == null) || (c.size() == 0)) {
			return new HashSet<E>();
		}

		return new HashSet<E>(c);
	}

	public static <E> Set<E> fromEnumeration(Enumeration<E> enu) {
		Set<E> set = new HashSet<E>();

		while (enu.hasMoreElements()) {
			set.add(enu.nextElement());
		}

		return set;
	}

	public static Set<String> fromFile(File file) throws IOException {
		Set<String> set = new HashSet<String>();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new FileReader(file));

		String s = StringPool.BLANK;

		while ((s = unsyncBufferedReader.readLine()) != null) {
			set.add(s);
		}

		unsyncBufferedReader.close();

		return set;
	}

	public static Set<String> fromFile(String fileName) throws IOException {
		return fromFile(new File(fileName));
	}

	public static <E> Set<E> fromIterator(Iterator<E> itr) {
		Set<E> set = new HashSet<E>();

		while (itr.hasNext()) {
			set.add(itr.next());
		}

		return set;
	}

	public static <E> Set<E> fromList(List<E> array) {
		if ((array == null) || (array.size() == 0)) {
			return new HashSet<E>();
		}

		return new HashSet<E>(array);
	}

	public static Set<String> fromString(String s) {
		return fromArray(StringUtil.splitLines(s));
	}

}