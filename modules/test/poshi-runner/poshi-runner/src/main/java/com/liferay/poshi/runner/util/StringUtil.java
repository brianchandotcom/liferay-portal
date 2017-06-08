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

package com.liferay.poshi.runner.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

/**
 * The String utility class.
 *
 * @author Brian Wing Shun Chan
 * @author Sandeep Soni
 * @author Ganesh Ram
 * @author Shuyang Zhou
 * @author Hugo Huijser
 * @author Michael Hashimoto
 */
public class StringUtil {

	public static String add(String s, String add) {
		return add(s, add, StringPool.COMMA);
	}

	public static String add(String s, String add, String delimiter) {
		return add(s, add, delimiter, false);
	}

	public static String add(
		String s, String add, String delimiter, boolean allowDuplicates) {

		if ((add == null) || (delimiter == null)) {
			return null;
		}

		if (s == null) {
			s = StringPool.BLANK;
		}

		if (allowDuplicates || !contains(s, add, delimiter)) {
			StringBuilder sb = new StringBuilder(4);

			sb.append(s);

			if (Validator.isNull(s) || s.endsWith(delimiter)) {
				sb.append(add);
				sb.append(delimiter);
			}
			else {
				sb.append(delimiter);
				sb.append(add);
				sb.append(delimiter);
			}

			s = sb.toString();
		}

		return s;
	}

	public static boolean contains(String s, String text) {
		return contains(s, text, StringPool.COMMA);
	}

	public static boolean contains(String s, String text, String delimiter) {
		if ((s == null) || (text == null) || (delimiter == null)) {
			return false;
		}

		if (!s.endsWith(delimiter)) {
			s = s.concat(delimiter);
		}

		String dtd = delimiter.concat(text).concat(delimiter);

		int pos = s.indexOf(dtd);

		if (pos == -1) {
			String td = text.concat(delimiter);

			if (s.startsWith(td)) {
				return true;
			}

			return false;
		}

		return true;
	}

	public static int count(String s, String text) {
		if ((s == null) || (s.length() == 0) || (text == null) ||
			(text.length() == 0)) {

			return 0;
		}

		int count = 0;

		int pos = s.indexOf(text);

		while (pos != -1) {
			pos = s.indexOf(text, pos + text.length());

			count++;
		}

		return count;
	}

	public static boolean endsWith(String s, String end) {
		if ((s == null) || (end == null)) {
			return false;
		}

		if (end.length() > s.length()) {
			return false;
		}

		String temp = s.substring(s.length() - end.length());

		if (equalsIgnoreCase(temp, end)) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean equalsIgnoreCase(String s1, String s2) {
		if (s1 == s2) {
			return true;
		}

		if ((s1 == null) || (s2 == null)) {
			return false;
		}

		if (s1.length() != s2.length()) {
			return false;
		}

		for (int i = 0; i < s1.length(); i++) {
			char c1 = s1.charAt(i);

			char c2 = s2.charAt(i);

			if (c1 == c2) {
				continue;
			}

			if ((c1 > 127) || (c2 > 127)) {

				// Georgian alphabet needs to check both upper and lower case

				if ((Character.toLowerCase(c1) == Character.toLowerCase(c2)) ||
					(Character.toUpperCase(c1) == Character.toUpperCase(c2))) {

					continue;
				}

				return false;
			}

			int delta = c1 - c2;

			if ((delta != 32) && (delta != -32)) {
				return false;
			}
		}

		return true;
	}

	public static String extractChars(String s) {
		if (s == null) {
			return StringPool.BLANK;
		}

		StringBuilder sb = new StringBuilder();

		char[] chars = s.toCharArray();

		for (char c : chars) {
			if (Validator.isChar(c)) {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	public static String extractDigits(String s) {
		if (s == null) {
			return StringPool.BLANK;
		}

		StringBuilder sb = new StringBuilder();

		char[] chars = s.toCharArray();

		for (char c : chars) {
			if (Validator.isDigit(c)) {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	public static String extractFirst(String s, String delimiter) {
		if (s == null) {
			return null;
		}

		int index = s.indexOf(delimiter);

		if (index < 0) {
			return null;
		}
		else {
			return s.substring(0, index);
		}
	}

	public static String extractLast(String s, String delimiter) {
		if (s == null) {
			return null;
		}

		int index = s.lastIndexOf(delimiter);

		if (index < 0) {
			return null;
		}
		else {
			return s.substring(index + delimiter.length());
		}
	}

	public static String extractLeadingDigits(String s) {
		if (s == null) {
			return StringPool.BLANK;
		}

		StringBuilder sb = new StringBuilder();

		char[] chars = s.toCharArray();

		for (char c : chars) {
			if (Validator.isDigit(c)) {
				sb.append(c);
			}
			else {
				return sb.toString();
			}
		}

		return sb.toString();
	}

	public static boolean isLowerCase(String s) {
		if (s == null) {
			return false;
		}

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			// Fast path for ascii code, fallback to the slow unicode detection

			if (c <= 127) {
				if ((c >= CharPool.UPPER_CASE_A) &&
					(c <= CharPool.UPPER_CASE_Z)) {

					return false;
				}

				continue;
			}

			if (Character.isLetter(c) && Character.isUpperCase(c)) {
				return false;
			}
		}

		return true;
	}

	public static boolean isUpperCase(String s) {
		if (s == null) {
			return false;
		}

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			// Fast path for ascii code, fallback to the slow unicode detection

			if (c <= 127) {
				if ((c >= CharPool.LOWER_CASE_A) &&
					(c <= CharPool.LOWER_CASE_Z)) {

					return false;
				}

				continue;
			}

			if (Character.isLetter(c) && Character.isLowerCase(c)) {
				return false;
			}
		}

		return true;
	}

	public static String lowerCase(String s) {
		return toLowerCase(s);
	}

	public static void lowerCase(String... array) {
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				array[i] = toLowerCase(array[i]);
			}
		}
	}

	public static String lowerCaseFirstLetter(String s) {
		char[] chars = s.toCharArray();

		if ((chars[0] >= 65) && (chars[0] <= 90)) {
			chars[0] = (char)(chars[0] + 32);
		}

		return new String(chars);
	}

	public static boolean matches(String s, String pattern) {
		String[] array = pattern.split("\\*");

		for (String element : array) {
			int pos = s.indexOf(element);

			if (pos == -1) {
				return false;
			}

			s = s.substring(pos + element.length());
		}

		return true;
	}

	public static List<String> partition(String s, String[] delimiters) {
		List<Integer> delimiterIndexes = new ArrayList<>();

		for (String delimiter : delimiters) {
			int index = s.indexOf(delimiter);

			while (index >= 0) {
				delimiterIndexes.add(index);

				index = s.indexOf(delimiter, index + 1);
			}
		}

		if (!delimiterIndexes.contains(0)) {
			delimiterIndexes.add(0);
		}

		if (!delimiterIndexes.contains(s.length())) {
			delimiterIndexes.add(s.length());
		}

		Collections.sort(delimiterIndexes);

		List<String> substrings = new ArrayList<>();

		for (int i = 0; i < delimiterIndexes.size(); i++) {
			if ((i + 1) == delimiterIndexes.size()) {
				continue;
			}

			String substring = s.substring(
				(int)delimiterIndexes.get(i), (int)delimiterIndexes.get(i + 1));

			substrings.add(substring);
		}

		return substrings;
	}

	public static String quote(String s, String quote) {
		if (s == null) {
			return null;
		}

		return quote.concat(s).concat(quote);
	}

	public static String randomString(String length) {
		int lengthInt = Integer.parseInt(length);

		StringBuilder sb = new StringBuilder();

		while (sb.length() < lengthInt) {
			UUID randomUUID = UUID.randomUUID();

			String uuidString = randomUUID.toString();

			sb.append(uuidString.replace("-", ""));
		}

		return sb.substring(0, lengthInt);
	}

	public static String removeSpaces(String s) {
		return s.replaceAll(" ", "");
	}

	public static String replace(String s, String oldSub, String newSub) {
		if (s == null) {
			return null;
		}

		return s.replace(oldSub, newSub);
	}

	public static String replace(String s, String[] oldSubs, String[] newSubs) {
		if ((s == null) || (oldSubs == null) || (newSubs == null)) {
			return null;
		}

		if (oldSubs.length != newSubs.length) {
			return s;
		}

		for (int i = 0; i < oldSubs.length; i++) {
			s = replace(s, oldSubs[i], newSubs[i]);
		}

		return s;
	}

	public static String replaceFirst(String s, char oldSub, char newSub) {
		if (s == null) {
			return null;
		}

		return replaceFirst(s, String.valueOf(oldSub), String.valueOf(newSub));
	}

	public static String replaceFirst(String s, char oldSub, String newSub) {
		if ((s == null) || (newSub == null)) {
			return null;
		}

		return replaceFirst(s, String.valueOf(oldSub), newSub);
	}

	public static String replaceFirst(String s, String oldSub, String newSub) {
		return replaceFirst(s, oldSub, newSub, 0);
	}

	public static String replaceFirst(
		String s, String oldSub, String newSub, int fromIndex) {

		if ((s == null) || (oldSub == null) || (newSub == null)) {
			return null;
		}

		if (oldSub.equals(newSub)) {
			return s;
		}

		int y = s.indexOf(oldSub, fromIndex);

		if (y >= 0) {
			return s.substring(0, y).concat(newSub).concat(
				s.substring(y + oldSub.length()));
		}
		else {
			return s;
		}
	}

	public static String replaceFirst(
		String s, String[] oldSubs, String[] newSubs) {

		if ((s == null) || (oldSubs == null) || (newSubs == null)) {
			return null;
		}

		if (oldSubs.length != newSubs.length) {
			return s;
		}

		for (int i = 0; i < oldSubs.length; i++) {
			s = replaceFirst(s, oldSubs[i], newSubs[i]);
		}

		return s;
	}

	public static String reverse(String s) {
		if (s == null) {
			return null;
		}

		char[] chars = s.toCharArray();

		char[] reverse = new char[chars.length];

		for (int i = 0; i < chars.length; i++) {
			reverse[i] = chars[chars.length - i - 1];
		}

		return new String(reverse);
	}

	public static String[] split(String s) {
		return split(s, StringPool.COMMA);
	}

	public static String[] split(String s, String delimiter) {
		if (s == null) {
			return null;
		}

		return s.split(delimiter);
	}

	public static boolean startsWith(String s, String start) {
		if ((s == null) || (start == null)) {
			return false;
		}

		if (start.length() > s.length()) {
			return false;
		}

		String temp = s.substring(0, start.length());

		if (equalsIgnoreCase(temp, start)) {
			return true;
		}
		else {
			return false;
		}
	}

	public static String stripBetween(String s, String begin, String end) {
		if (Validator.isBlank(s) || Validator.isBlank(begin) ||
			Validator.isBlank(end)) {

			return s;
		}

		StringBuilder sb = new StringBuilder(s.length());

		int pos = 0;

		while (true) {
			int x = s.indexOf(begin, pos);

			int y = s.indexOf(end, x + begin.length());

			if ((x == -1) || (y == -1)) {
				sb.append(s.substring(pos));

				break;
			}
			else {
				sb.append(s.substring(pos, x));

				pos = y + end.length();
			}
		}

		return sb.toString();
	}

	public static String toLowerCase(String s) {
		if (s == null) {
			return null;
		}

		return StringUtils.lowerCase(s);
	}

	public static String toUpperCase(String s) {
		if (s == null) {
			return null;
		}

		return StringUtils.upperCase(s);
	}

	public static String trim(String s) {
		if (s == null) {
			return null;
		}

		if (s.length() == 0) {
			return s;
		}

		int len = s.length();

		int x = len;

		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);

			if (!Character.isWhitespace(c)) {
				x = i;

				break;
			}
		}

		if (x == len) {
			return StringPool.BLANK;
		}

		int y = x + 1;

		for (int i = len - 1; i > x; i--) {
			char c = s.charAt(i);

			if (!Character.isWhitespace(c)) {
				y = i + 1;

				break;
			}
		}

		if ((x == 0) && (y == len)) {
			return s;
		}

		return s.substring(x, y);
	}

	public static String trimLeading(String s) {
		if (s == null) {
			return null;
		}

		if (s.length() == 0) {
			return s;
		}

		int len = s.length();

		int x = len;

		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);

			if (!Character.isWhitespace(c)) {
				x = i;

				break;
			}
		}

		if (x == len) {
			return StringPool.BLANK;
		}
		else if (x == 0) {
			return s;
		}
		else {
			return s.substring(x);
		}
	}

	public static String trimTrailing(String s) {
		if (s == null) {
			return null;
		}

		if (s.length() == 0) {
			return s;
		}

		int len = s.length();
		int x = 0;

		for (int i = len - 1; i >= 0; i--) {
			char c = s.charAt(i);

			if (!Character.isWhitespace(c)) {
				x = i + 1;

				break;
			}
		}

		if (x == 0) {
			return StringPool.BLANK;
		}
		else if (x == len) {
			return s;
		}
		else {
			return s.substring(0, x);
		}
	}

	public static String unquote(String s) {
		if (Validator.isNull(s)) {
			return s;
		}

		if ((s.charAt(0) == CharPool.APOSTROPHE) &&
			(s.charAt(s.length() - 1) == CharPool.APOSTROPHE)) {

			return s.substring(1, s.length() - 1);
		}
		else if ((s.charAt(0) == CharPool.QUOTE) &&
				 (s.charAt(s.length() - 1) == CharPool.QUOTE)) {

			return s.substring(1, s.length() - 1);
		}

		return s;
	}

	public static String upperCase(String s) {
		return toUpperCase(s);
	}

	public static String upperCaseFirstLetter(String s) {
		char[] chars = s.toCharArray();

		if ((chars[0] >= 97) && (chars[0] <= 122)) {
			chars[0] = (char)(chars[0] - 32);
		}

		return new String(chars);
	}

	public static String valueOf(Object obj) {
		return String.valueOf(obj);
	}

}