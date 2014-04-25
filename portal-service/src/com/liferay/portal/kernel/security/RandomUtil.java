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

package com.liferay.portal.kernel.security;

import java.util.Random;

/**
 * @author Shuyang Zhou
 */
public class RandomUtil {

	public static int nextInt(int n) {
		return random.nextInt(n);
	}

	public static int[] nextInts(int n, int size) {
		if (size > n) {
			size = n;
		}

		int[] numbers = new int[n];

		for (int i = 0; i < n; i++) {
			numbers[i] = i;
		}

		shuffle(random, numbers);

		if (size == n) {
			return numbers;
		}

		int[] results = new int[size];

		System.arraycopy(numbers, 0, results, 0, size);

		return results;
	}

	public static void shuffle(Random random, int[] numbers) {
		for (int i = numbers.length; i > 1; i--) {
			int position = random.nextInt(i);

			if (position != (i - 1)) {
				int number = numbers[position];

				numbers[position] = numbers[i - 1];
				numbers[i - 1] = number;
			}
		}
	}

	public static String shuffle(Random random, String s) {
		StringBuilder sb = new StringBuilder(s);

		shuffle(random, sb);

		return sb.toString();
	}

	public static void shuffle(Random random, StringBuilder sb) {
		for (int i = sb.length(); i > 1; i--) {
			int position = random.nextInt(i);

			if (position != (i - 1)) {
				char c = sb.charAt(position);

				sb.setCharAt(position, sb.charAt(i - 1));
				sb.setCharAt(i - 1, c);
			}
		}
	}

	public static String shuffle(String s) {
		return shuffle(random, s);
	}

	protected static Random random = new Random();

}