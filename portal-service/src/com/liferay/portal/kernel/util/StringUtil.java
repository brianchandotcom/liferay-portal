/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 * @author Sandeep Soni
 * @author Ganesh Ram
 * @author Shuyang Zhou
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
			StringBundler sb = new StringBundler();

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

	public static String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);

		for (byte b : bytes) {
			String hex = Integer.toHexString(
				0x0100 + (b & 0x00FF)).substring(1);

			if (hex.length() < 2) {
				sb.append("0");
			}

			sb.append(hex);
		}

		return sb.toString();
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
		if ((s == null) || (text == null)) {
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

	public static boolean endsWith(String s, char end) {
		return endsWith(s, (new Character(end)).toString());
	}

	public static boolean endsWith(String s, String end) {
		if ((s == null) || (end == null)) {
			return false;
		}

		if (end.length() > s.length()) {
			return false;
		}

		String temp = s.substring(s.length() - end.length(), s.length());

		if (temp.equalsIgnoreCase(end)) {
			return true;
		}
		else {
			return false;
		}
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

	public static String extractFirst(String s, char delimiter) {
		if (s == null) {
			return null;
		}
		else {
			int index = s.indexOf(delimiter);

			if (index < 0) {
				return null;
			}
			else {
				return s.substring(0, index);
			}
		}
	}

	public static String extractFirst(String s, String delimiter) {
		if (s == null) {
			return null;
		}
		else {
			int index = s.indexOf(delimiter);

			if (index < 0) {
				return null;
			}
			else {
				return s.substring(0, index);
			}
		}
	}

	public static String extractLast(String s, char delimiter) {
		if (s == null) {
			return null;
		}
		else {
			int index = s.lastIndexOf(delimiter);

			if (index < 0) {
				return null;
			}
			else {
				return s.substring(index + 1);
			}
		}
	}

	public static String extractLast(String s, String delimiter) {
		if (s == null) {
			return null;
		}
		else {
			int index = s.lastIndexOf(delimiter);

			if (index < 0) {
				return null;
			}
			else {
				return s.substring(index + delimiter.length());
			}
		}
	}

	/**
	 * @deprecated
	 */
	public static String highlight(String s, String keywords) {
		return highlight(s, keywords, "<span class=\"highlight\">", "</span>");
	}

	/**
	 * @deprecated
	 */
	public static String highlight(
		String s, String keywords, String highlight1, String highlight2) {

		if (Validator.isNull(s) || Validator.isNull(keywords)) {
			return s;
		}

		Pattern pattern = Pattern.compile(
			Pattern.quote(keywords), Pattern.CASE_INSENSITIVE);

		return _highlight(s, pattern, highlight1, highlight2);
	}

	public static String highlight(String s, String[] queryTerms) {
		return highlight(
			s, queryTerms, "<span class=\"highlight\">", "</span>");
	}

	public static String highlight(
		String s, String[] queryTerms, String highlight1, String highlight2) {

		if (Validator.isNull(s) || Validator.isNull(queryTerms)) {
			return s;
		}

		if (queryTerms.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(2 * queryTerms.length - 1);

		for (int i = 0; i < queryTerms.length; i++) {
			sb.append(Pattern.quote(queryTerms[i].trim()));

			if ((i + 1) < queryTerms.length) {
				sb.append(StringPool.PIPE);
			}
		}

		int flags =
			Pattern.CANON_EQ | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;

		Pattern pattern = Pattern.compile(sb.toString(), flags);

		return _highlight(s, pattern, highlight1, highlight2);
	}

	public static String insert(String s, String insert, int offset) {
		if (s == null) {
			return null;
		}

		if (insert == null) {
			return s;
		}

		if (offset > s.length()) {
			return s.concat(insert);
		}
		else {
			String prefix = s.substring(0, offset);
			String postfix = s.substring(offset);

			return prefix.concat(insert).concat(postfix);
		}
	}

	public static String lowerCase(String s) {
		if (s == null) {
			return null;
		}
		else {
			return s.toLowerCase();
		}
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

	public static boolean matchesIgnoreCase(String s, String pattern) {
		return matches(lowerCase(s), lowerCase(pattern));
	}

	public static String merge(boolean[] array) {
		return merge(array, StringPool.COMMA);
	}

	public static String merge(boolean[] array, String delimiter) {
		if (array == null) {
			return null;
		}

		if (array.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(2 * array.length - 1);

		for (int i = 0; i < array.length; i++) {
			sb.append(String.valueOf(array[i]).trim());

			if ((i + 1) != array.length) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

	public static String merge(Collection<?> col) {
		return merge(col, StringPool.COMMA);
	}

	public static String merge(Collection<?> col, String delimiter) {
		if (col == null) {
			return null;
		}

		return merge(col.toArray(new Object[col.size()]), delimiter);
	}

	public static String merge(char[] array) {
		return merge(array, StringPool.COMMA);
	}

	public static String merge(char[] array, String delimiter) {
		if (array == null) {
			return null;
		}

		if (array.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(2 * array.length - 1);

		for (int i = 0; i < array.length; i++) {
			sb.append(String.valueOf(array[i]).trim());

			if ((i + 1) != array.length) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

	public static String merge(double[] array) {
		return merge(array, StringPool.COMMA);
	}

	public static String merge(double[] array, String delimiter) {
		if (array == null) {
			return null;
		}

		if (array.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(2 * array.length - 1);

		for (int i = 0; i < array.length; i++) {
			sb.append(String.valueOf(array[i]).trim());

			if ((i + 1) != array.length) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

	public static String merge(float[] array) {
		return merge(array, StringPool.COMMA);
	}

	public static String merge(float[] array, String delimiter) {
		if (array == null) {
			return null;
		}

		if (array.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(2 * array.length - 1);

		for (int i = 0; i < array.length; i++) {
			sb.append(String.valueOf(array[i]).trim());

			if ((i + 1) != array.length) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

	public static String merge(int[] array) {
		return merge(array, StringPool.COMMA);
	}

	public static String merge(int[] array, String delimiter) {
		if (array == null) {
			return null;
		}

		if (array.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(2 * array.length - 1);

		for (int i = 0; i < array.length; i++) {
			sb.append(String.valueOf(array[i]).trim());

			if ((i + 1) != array.length) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

	public static String merge(long[] array) {
		return merge(array, StringPool.COMMA);
	}

	public static String merge(long[] array, String delimiter) {
		if (array == null) {
			return null;
		}

		if (array.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(2 * array.length - 1);

		for (int i = 0; i < array.length; i++) {
			sb.append(String.valueOf(array[i]).trim());

			if ((i + 1) != array.length) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

	public static String merge(Object[] array) {
		return merge(array, StringPool.COMMA);
	}

	public static String merge(Object[] array, String delimiter) {
		if (array == null) {
			return null;
		}

		if (array.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(2 * array.length - 1);

		for (int i = 0; i < array.length; i++) {
			sb.append(String.valueOf(array[i]).trim());

			if ((i + 1) != array.length) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

	public static String merge(short[] array) {
		return merge(array, StringPool.COMMA);
	}

	public static String merge(short[] array, String delimiter) {
		if (array == null) {
			return null;
		}

		if (array.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(2 * array.length - 1);

		for (int i = 0; i < array.length; i++) {
			sb.append(String.valueOf(array[i]).trim());

			if ((i + 1) != array.length) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

	public static String quote(String s) {
		return quote(s, CharPool.APOSTROPHE);
	}

	public static String quote(String s, char quote) {
		if (s == null) {
			return null;
		}

		return quote(s, String.valueOf(quote));
	}

	public static String quote(String s, String quote) {
		if (s == null) {
			return null;
		}

		return quote.concat(s).concat(quote);
	}

	public static String randomize(String s) {
		return Randomizer.getInstance().randomize(s);
	}

	public static String read(ClassLoader classLoader, String name)
		throws IOException {

		return read(classLoader, name, false);
	}

	public static String read(ClassLoader classLoader, String name, boolean all)
		throws IOException {

		if (all) {
			StringBundler sb = new StringBundler();

			Enumeration<URL> enu = classLoader.getResources(name);

			while (enu.hasMoreElements()) {
				URL url = enu.nextElement();

				InputStream is = url.openStream();

				if (is == null) {
					throw new IOException(
						"Unable to open resource at " + url.toString());
				}

				String s = read(is);

				if (s != null) {
					sb.append(s);
					sb.append(StringPool.NEW_LINE);
				}

				is.close();
			}

			return sb.toString().trim();
		}
		else {
			InputStream is = classLoader.getResourceAsStream(name);

			if (is == null) {
				throw new IOException(
					"Unable to open resource in class loader " + name);
			}

			String s = read(is);

			is.close();

			return s;
		}
	}

	public static String read(InputStream is) throws IOException {
		StringBundler sb = new StringBundler();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new InputStreamReader(is));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			sb.append(line);
			sb.append(CharPool.NEW_LINE);
		}

		unsyncBufferedReader.close();

		return sb.toString().trim();
	}

	public static void readLines(InputStream is, Collection<String> lines)
		throws IOException {

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new InputStreamReader(is));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			lines.add(line);
		}

		unsyncBufferedReader.close();
	}

	public static String remove(String s, String remove) {
		return remove(s, remove, StringPool.COMMA);
	}

	public static String remove(String s, String remove, String delimiter) {
		if ((s == null) || (remove == null) || (delimiter == null)) {
			return null;
		}

		if (Validator.isNotNull(s) && !s.endsWith(delimiter)) {
			s += delimiter;
		}

		String drd = delimiter.concat(remove).concat(delimiter);

		String rd = remove.concat(delimiter);

		while (contains(s, remove, delimiter)) {
			int pos = s.indexOf(drd);

			if (pos == -1) {
				if (s.startsWith(rd)) {
					int x = remove.length() + delimiter.length();
					int y = s.length();

					s = s.substring(x, y);
				}
			}
			else {
				int x = pos + remove.length() + delimiter.length();
				int y = s.length();

				String temp = s.substring(0, pos);

				s = temp.concat(s.substring(x, y));
			}
		}

		return s;
	}

	public static String replace(String s, char oldSub, char newSub) {
		if (s == null) {
			return null;
		}

		return s.replace(oldSub, newSub);
	}

	public static String replace(String s, char oldSub, String newSub) {
		if ((s == null) || (newSub == null)) {
			return null;
		}

		// The number 5 is arbitrary and is used as extra padding to reduce
		// buffer expansion

		StringBuilder sb = new StringBuilder(s.length() + 5 * newSub.length());

		char[] chars = s.toCharArray();

		for (char c : chars) {
			if (c == oldSub) {
				sb.append(newSub);
			}
			else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	public static String replace(String s, String oldSub, String newSub) {
		return replace(s, oldSub, newSub, 0);
	}

	public static String replace(
		String s, String oldSub, String newSub, int fromIndex) {

		if (s == null) {
			return null;
		}

		if ((oldSub == null) || oldSub.equals(StringPool.BLANK)) {
			return s;
		}

		if (newSub == null) {
			newSub = StringPool.BLANK;
		}

		int y = s.indexOf(oldSub, fromIndex);

		if (y >= 0) {
			StringBundler sb = new StringBundler();

			int length = oldSub.length();
			int x = 0;

			while (x <= y) {
				sb.append(s.substring(x, y));
				sb.append(newSub);

				x = y + length;
				y = s.indexOf(oldSub, x);
			}

			sb.append(s.substring(x));

			return sb.toString();
		}
		else {
			return s;
		}
	}

	public static String replace(
		String s, String begin, String end, Map<String, String> values) {

		StringBundler sb = replaceToStringBundler(s, begin, end, values);

		return sb.toString();
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

	public static String replace(
		String s, String[] oldSubs, String[] newSubs, boolean exactMatch) {

		if ((s == null) || (oldSubs == null) || (newSubs == null)) {
			return null;
		}

		if (oldSubs.length != newSubs.length) {
			return s;
		}

		if (!exactMatch) {
			replace(s, oldSubs, newSubs);
		}
		else {
			for (int i = 0; i < oldSubs.length; i++) {
				s = s.replaceAll("\\b" + oldSubs[i] + "\\b" , newSubs[i]);
			}
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
		if ((s == null) || (oldSub == null) || (newSub == null)) {
			return null;
		}

		if (oldSub.equals(newSub)) {
			return s;
		}

		int y = s.indexOf(oldSub);

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

	public static String replaceLast(String s, char oldSub, char newSub) {
		if (s == null) {
			return null;
		}

		return replaceLast(s, String.valueOf(oldSub), String.valueOf(newSub));
	}

	public static String replaceLast(String s, char oldSub, String newSub) {
		if ((s == null) || (newSub == null)) {
			return null;
		}

		return replaceLast(s, String.valueOf(oldSub), newSub);
	}

	public static String replaceLast(String s, String oldSub, String newSub) {
		if ((s == null) || (oldSub == null) || (newSub == null)) {
			return null;
		}

		if (oldSub.equals(newSub)) {
			return s;
		}

		int y = s.lastIndexOf(oldSub);

		if (y >= 0) {
			return s.substring(0, y).concat(newSub).concat(
				s.substring(y + oldSub.length()));
		}
		else {
			return s;
		}
	}

	public static String replaceLast(
		String s, String[] oldSubs, String[] newSubs) {

		if ((s == null) || (oldSubs == null) || (newSubs == null)) {
			return null;
		}

		if (oldSubs.length != newSubs.length) {
			return s;
		}

		for (int i = 0; i < oldSubs.length; i++) {
			s = replaceLast(s, oldSubs[i], newSubs[i]);
		}

		return s;
	}

	public static StringBundler replaceToStringBundler(
		String s, String begin, String end, Map<String, String> values) {

		if ((s == null) || (begin == null) || (end == null) ||
			(values == null) || (values.size() == 0)) {

			return new StringBundler(s);
		}

		StringBundler sb = new StringBundler(values.size() * 2 + 1);

		int pos = 0;

		while (true) {
			int x = s.indexOf(begin, pos);
			int y = s.indexOf(end, x + begin.length());

			if ((x == -1) || (y == -1)) {
				sb.append(s.substring(pos, s.length()));

				break;
			}
			else {
				sb.append(s.substring(pos, x));

				String oldValue = s.substring(x + begin.length(), y);

				String newValue = values.get(oldValue);

				if (newValue == null) {
					newValue = oldValue;
				}

				sb.append(newValue);

				pos = y + end.length();
			}
		}

		return sb;
	}

	public static StringBundler replaceWithStringBundler(
		String s, String begin, String end, Map<String, StringBundler> values) {

		if ((s == null) || (begin == null) || (end == null) ||
			(values == null) || (values.size() == 0)) {

			return new StringBundler(s);
		}

		int size = values.size() + 1;

		for (StringBundler valueSB : values.values()) {
			size += valueSB.index();
		}

		StringBundler sb = new StringBundler(size);

		int pos = 0;

		while (true) {
			int x = s.indexOf(begin, pos);
			int y = s.indexOf(end, x + begin.length());

			if ((x == -1) || (y == -1)) {
				sb.append(s.substring(pos, s.length()));

				break;
			}
			else {
				sb.append(s.substring(pos, x));

				String oldValue = s.substring(x + begin.length(), y);

				StringBundler newValue = values.get(oldValue);

				if (newValue == null) {
					sb.append(oldValue);
				}
				else {
					sb.append(newValue);
				}

				pos = y + end.length();
			}
		}

		return sb;
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

	public static String safePath(String path) {
		return replace(path, StringPool.DOUBLE_SLASH, StringPool.SLASH);
	}

	public static String shorten(String s) {
		return shorten(s, 20);
	}

	public static String shorten(String s, int length) {
		return shorten(s, length, "...");
	}

	public static String shorten(String s, int length, String suffix) {
		if ((s == null) || (suffix == null)) {
			return null;
		}

		if (s.length() > length) {
			for (int j = length; j >= 0; j--) {
				if (Character.isWhitespace(s.charAt(j))) {
					length = j;

					break;
				}
			}

			String temp = s.substring(0, length);

			s = temp.concat(suffix);
		}

		return s;
	}

	public static String shorten(String s, String suffix) {
		return shorten(s, 20, suffix);
	}

	public static String[] split(String s) {
		return split(s, CharPool.COMMA);
	}

	public static boolean[] split(String s, boolean x) {
		return split(s, StringPool.COMMA, x);
	}

	public static double[] split(String s, double x) {
		return split(s, StringPool.COMMA, x);
	}

	public static float[] split(String s, float x) {
		return split(s, StringPool.COMMA, x);
	}

	public static int[] split(String s, int x) {
		return split(s, StringPool.COMMA, x);
	}

	public static long[] split(String s, long x) {
		return split(s, StringPool.COMMA, x);
	}

	public static short[] split(String s, short x) {
		return split(s, StringPool.COMMA, x);
	}

	public static String[] split(String s, char delimiter) {
		if (Validator.isNull(s)) {
			return _emptyStringArray;
		}

		s = s.trim();

		if (s.length() == 0) {
			return _emptyStringArray;
		}

		if ((delimiter == CharPool.RETURN) ||
			(delimiter == CharPool.NEW_LINE)) {

			return splitLines(s);
		}

		List<String> nodeValues = new ArrayList<String>();

		int offset = 0;
		int pos = s.indexOf(delimiter, offset);

		while (pos != -1) {
			nodeValues.add(s.substring(offset, pos));

			offset = pos + 1;
			pos = s.indexOf(delimiter, offset);
		}

		if (offset < s.length()) {
			nodeValues.add(s.substring(offset));
		}

		return nodeValues.toArray(new String[nodeValues.size()]);
	}

	public static String[] split(String s, String delimiter) {
		if ((Validator.isNull(s)) || (delimiter == null) ||
			(delimiter.equals(StringPool.BLANK))) {

			return _emptyStringArray;
		}

		s = s.trim();

		if (s.equals(delimiter)) {
			return _emptyStringArray;
		}

		if (delimiter.length() == 1) {
			return split(s, delimiter.charAt(0));
		}

		List<String> nodeValues = new ArrayList<String>();

		int offset = 0;
		int pos = s.indexOf(delimiter, offset);

		while (pos != -1) {
			nodeValues.add(s.substring(offset, pos));

			offset = pos + delimiter.length();
			pos = s.indexOf(delimiter, offset);
		}

		if (offset < s.length()) {
			nodeValues.add(s.substring(offset));
		}

		return nodeValues.toArray(new String[nodeValues.size()]);
	}

	public static boolean[] split(String s, String delimiter, boolean x) {
		String[] array = split(s, delimiter);
		boolean[] newArray = new boolean[array.length];

		for (int i = 0; i < array.length; i++) {
			boolean value = x;

			try {
				value = Boolean.valueOf(array[i]).booleanValue();
			}
			catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static double[] split(String s, String delimiter, double x) {
		String[] array = split(s, delimiter);
		double[] newArray = new double[array.length];

		for (int i = 0; i < array.length; i++) {
			double value = x;

			try {
				value = Double.parseDouble(array[i]);
			}
			catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static float[] split(String s, String delimiter, float x) {
		String[] array = split(s, delimiter);
		float[] newArray = new float[array.length];

		for (int i = 0; i < array.length; i++) {
			float value = x;

			try {
				value = Float.parseFloat(array[i]);
			}
			catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static int[] split(String s, String delimiter, int x) {
		String[] array = split(s, delimiter);
		int[] newArray = new int[array.length];

		for (int i = 0; i < array.length; i++) {
			int value = x;

			try {
				value = Integer.parseInt(array[i]);
			}
			catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static long[] split(String s, String delimiter, long x) {
		String[] array = split(s, delimiter);
		long[] newArray = new long[array.length];

		for (int i = 0; i < array.length; i++) {
			long value = x;

			try {
				value = Long.parseLong(array[i]);
			}
			catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static short[] split(String s, String delimiter, short x) {
		String[] array = split(s, delimiter);
		short[] newArray = new short[array.length];

		for (int i = 0; i < array.length; i++) {
			short value = x;

			try {
				value = Short.parseShort(array[i]);
			}
			catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static String[] splitLines(String s) {
		if (Validator.isNull(s)) {
			return _emptyStringArray;
		}

		s = s.trim();

		List<String> lines = new ArrayList<String>();

		int lastIndex = 0;

		while (true) {
			int returnIndex = s.indexOf(CharPool.RETURN, lastIndex);
			int newLineIndex = s.indexOf(CharPool.NEW_LINE, lastIndex);

			if ((returnIndex == -1) && (newLineIndex == -1)) {
				break;
			}

			if (returnIndex == -1) {
				lines.add(s.substring(lastIndex, newLineIndex));

				lastIndex = newLineIndex + 1;
			}
			else if (newLineIndex == -1) {
				lines.add(s.substring(lastIndex, returnIndex));

				lastIndex = returnIndex + 1;
			}
			else  if (newLineIndex < returnIndex) {
				lines.add(s.substring(lastIndex, newLineIndex));

				lastIndex = newLineIndex + 1;
			}
			else {
				lines.add(s.substring(lastIndex, returnIndex));

				lastIndex = returnIndex + 1;

				if (lastIndex == newLineIndex) {
					lastIndex++;
				}
			}
		}

		if (lastIndex < s.length()) {
			lines.add(s.substring(lastIndex));
		}

		return lines.toArray(new String[lines.size()]);
	}

	public static boolean startsWith(String s, char begin) {
		return startsWith(s, (new Character(begin)).toString());
	}

	public static boolean startsWith(String s, String start) {
		if ((s == null) || (start == null)) {
			return false;
		}

		if (start.length() > s.length()) {
			return false;
		}

		String temp = s.substring(0, start.length());

		if (temp.equalsIgnoreCase(start)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Return the number of starting letters that s1 and s2 have in common
	 * before they deviate.
	 *
	 * @return the number of starting letters that s1 and s2 have in common
	 *         before they deviate
	 */
	public static int startsWithWeight(String s1, String s2) {
		if ((s1 == null) || (s2 == null)) {
			return 0;
		}

		char[] chars1 = s1.toCharArray();
		char[] chars2 = s2.toCharArray();

		int i = 0;

		for (; (i < chars1.length) && (i < chars2.length); i++) {
			if (chars1[i] != chars2[i]) {
				break;
			}
		}

		return i;
	}

	public static String strip(String s, char remove) {
		if (s == null) {
			return null;
		}

		int x = s.indexOf(remove);

		if (x < 0) {
			return s;
		}

		int y = 0;

		StringBuilder sb = new StringBuilder(s.length());

		while (x >= 0) {
			sb.append(s.subSequence(y, x));

			y = x + 1;

			x = s.indexOf(remove, y);
		}

		sb.append(s.substring(y));

		return sb.toString();
	}

	public static String stripBetween(String s, String begin, String end) {
		if ((s == null) || (begin == null) || (end == null)) {
			return s;
		}

		StringBuilder sb = new StringBuilder(s.length());

		int pos = 0;

		while (true) {
			int x = s.indexOf(begin, pos);
			int y = s.indexOf(end, x + begin.length());

			if ((x == -1) || (y == -1)) {
				sb.append(s.substring(pos, s.length()));

				break;
			}
			else {
				sb.append(s.substring(pos, x));

				pos = y + end.length();
			}
		}

		return sb.toString();
	}

	public static String toCharCode(String s) {
		StringBundler sb = new StringBundler(s.length());

		for (int i = 0; i < s.length(); i++) {
			sb.append(s.codePointAt(i));
		}

		return sb.toString();
	}

	public static String toHexString(int i) {
		char[] buffer = new char[8];

		int index = 8;

		do {
			buffer[--index] = _HEX_DIGITS[i & 15];

			i >>>= 4;
		}
		while (i != 0);

		return new String(buffer, index, 8 - index);
	}

	public static String toHexString(long l) {
		char[] buffer = new char[16];

		int index = 16;

		do {
			buffer[--index] = _HEX_DIGITS[(int) (l & 15)];

			l >>>= 4;
		}
		while (l != 0);

		return new String(buffer, index, 16 - index);
	}

	public static String toHexString(Object obj) {
		if (obj instanceof Integer) {
			return toHexString(((Integer)obj).intValue());
		}
		else if (obj instanceof Long) {
			return toHexString(((Long)obj).longValue());
		}
		else {
			return String.valueOf(obj);
		}
	}

	public static String trim(String s) {
		return trim(s, null);
	}

	public static String trim(String s, char c) {
		return trim(s, new char[] {c});
	}

	public static String trim(String s, char[] exceptions) {
		if (s == null) {
			return null;
		}

		char[] chars = s.toCharArray();

		int len = chars.length;

		int x = 0;
		int y = chars.length;

		for (int i = 0; i < len; i++) {
			char c = chars[i];

			if (_isTrimable(c, exceptions)) {
				x = i + 1;
			}
			else {
				break;
			}
		}

		for (int i = len - 1; i >= 0; i--) {
			char c = chars[i];

			if (_isTrimable(c, exceptions)) {
				y = i;
			}
			else {
				break;
			}
		}

		if ((x != 0) || (y != len)) {
			return s.substring(x, y);
		}
		else {
			return s;
		}
	}

	public static String trimLeading(String s) {
		return trimLeading(s, null);
	}

	public static String trimLeading(String s, char c) {
		return trimLeading(s, new char[] {c});
	}

	public static String trimLeading(String s, char[] exceptions) {
		if (s == null) {
			return null;
		}

		char[] chars = s.toCharArray();

		int len = chars.length;

		int x = 0;
		int y = chars.length;

		for (int i = 0; i < len; i++) {
			char c = chars[i];

			if (_isTrimable(c, exceptions)) {
				x = i + 1;
			}
			else {
				break;
			}
		}

		if ((x != 0) || (y != len)) {
			return s.substring(x, y);
		}
		else {
			return s;
		}
	}

	public static String trimTrailing(String s) {
		return trimTrailing(s, null);
	}

	public static String trimTrailing(String s, char c) {
		return trimTrailing(s, new char[] {c});
	}

	public static String trimTrailing(String s, char[] exceptions) {
		if (s == null) {
			return null;
		}

		char[] chars = s.toCharArray();

		int len = chars.length;

		int x = 0;
		int y = chars.length;

		for (int i = len - 1; i >= 0; i--) {
			char c = chars[i];

			if (_isTrimable(c, exceptions)) {
				y = i;
			}
			else {
				break;
			}
		}

		if ((x != 0) || (y != len)) {
			return s.substring(x, y);
		}
		else {
			return s;
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
		if (s == null) {
			return null;
		}
		else {
			return s.toUpperCase();
		}
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

	public static String wrap(String text) {
		return wrap(text, 80, StringPool.NEW_LINE);
	}

	public static String wrap(String text, int width, String lineSeparator) {
		try {
			return _wrap(text, width, lineSeparator);
		}
		catch (IOException ioe) {
			_log.error(ioe.getMessage());

			return text;
		}
	}

	private static String _highlight(
		String s, Pattern pattern, String highlight1, String highlight2) {

		StringTokenizer st = new StringTokenizer(s);

		if (st.countTokens() == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(2 * st.countTokens() - 1);

		while (st.hasMoreTokens()) {
			String token = st.nextToken();

			Matcher matcher = pattern.matcher(token);

			if (matcher.find()) {
				StringBuffer hightlighted = new StringBuffer();

				do {
					matcher.appendReplacement(
						hightlighted, highlight1 + matcher.group() +
						highlight2);
				}
				while (matcher.find());

				matcher.appendTail(hightlighted);

				sb.append(hightlighted);
			}
			else {
				sb.append(token);
			}

			if (st.hasMoreTokens()) {
				sb.append(StringPool.SPACE);
			}
		}

		return sb.toString();
	}

	private static boolean _isTrimable(char c, char[] exceptions) {
		if ((exceptions != null) && (exceptions.length > 0)) {
			for (char exception : exceptions) {
				if (c == exception) {
					return false;
				}
			}
		}

		return Character.isWhitespace(c);
	}

	private static String _wrap(String text, int width, String lineSeparator)
		throws IOException {

		if (text == null) {
			return null;
		}

		StringBundler sb = new StringBundler();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(text));

		String s = StringPool.BLANK;

		while ((s = unsyncBufferedReader.readLine()) != null) {
			if (s.length() == 0) {
				sb.append(lineSeparator);

				continue;
			}

			int lineLength = 0;

			String[] tokens = s.split(StringPool.SPACE);

			for (String token : tokens) {
				if ((lineLength + token.length() + 1) > width) {
					if (lineLength > 0) {
						sb.append(lineSeparator);
					}

					if (token.length() > width) {
						int pos = token.indexOf(CharPool.OPEN_PARENTHESIS);

						if (pos != -1) {
							sb.append(token.substring(0, pos + 1));
							sb.append(lineSeparator);

							token = token.substring(pos + 1);

							sb.append(token);

							lineLength = token.length();
						}
						else {
							sb.append(token);

							lineLength = token.length();
						}
					}
					else {
						sb.append(token);

						lineLength = token.length();
					}
				}
				else {
					if (lineLength > 0) {
						sb.append(StringPool.SPACE);

						lineLength++;
					}

					sb.append(token);

					lineLength += token.length();
				}
			}

			sb.append(lineSeparator);
		}

		return sb.toString();
	}

	private static final char[] _HEX_DIGITS = {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
		'e', 'f'
	};

	private static String[] _emptyStringArray = new String[0];

	private static Log _log = LogFactoryUtil.getLog(StringUtil.class);

}