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

/**
 * @author Brian Wing Shun Chan
 */
public class StringUtil_IW {
	public static StringUtil_IW getInstance() {
		return _instance;
	}

	public java.lang.String add(java.lang.String s, java.lang.String add) {
		return StringUtil.add(s, add);
	}

	public java.lang.String add(java.lang.String s, java.lang.String add,
		java.lang.String delimiter) {
		return StringUtil.add(s, add, delimiter);
	}

	public java.lang.String add(java.lang.String s, java.lang.String add,
		java.lang.String delimiter, boolean allowDuplicates) {
		return StringUtil.add(s, add, delimiter, allowDuplicates);
	}

	public java.lang.String appendParentheticalSuffix(java.lang.String s,
		int suffix) {
		return StringUtil.appendParentheticalSuffix(s, suffix);
	}

	public java.lang.String appendParentheticalSuffix(java.lang.String s,
		java.lang.String suffix) {
		return StringUtil.appendParentheticalSuffix(s, suffix);
	}

	public java.lang.String bytesToHexString(byte[] bytes) {
		return StringUtil.bytesToHexString(bytes);
	}

	public boolean contains(java.lang.String s, java.lang.String text) {
		return StringUtil.contains(s, text);
	}

	public boolean contains(java.lang.String s, java.lang.String text,
		java.lang.String delimiter) {
		return StringUtil.contains(s, text, delimiter);
	}

	public boolean containsIgnoreCase(java.lang.String s, java.lang.String text) {
		return StringUtil.containsIgnoreCase(s, text);
	}

	public boolean containsIgnoreCase(java.lang.String s,
		java.lang.String text, java.lang.String delimiter) {
		return StringUtil.containsIgnoreCase(s, text, delimiter);
	}

	public int count(java.lang.String s, char c) {
		return StringUtil.count(s, c);
	}

	public int count(java.lang.String s, int start, int end, char c) {
		return StringUtil.count(s, start, end, c);
	}

	public int count(java.lang.String s, int start, int end,
		java.lang.String text) {
		return StringUtil.count(s, start, end, text);
	}

	public int count(java.lang.String s, java.lang.String text) {
		return StringUtil.count(s, text);
	}

	public boolean endsWith(java.lang.String s, char end) {
		return StringUtil.endsWith(s, end);
	}

	public boolean endsWith(java.lang.String s, java.lang.String end) {
		return StringUtil.endsWith(s, end);
	}

	public boolean equals(java.lang.String s1, java.lang.String s2) {
		return StringUtil.equals(s1, s2);
	}

	public boolean equalsIgnoreBreakLine(java.lang.String s1,
		java.lang.String s2) {
		return StringUtil.equalsIgnoreBreakLine(s1, s2);
	}

	public boolean equalsIgnoreCase(char c1, char c2) {
		return StringUtil.equalsIgnoreCase(c1, c2);
	}

	public boolean equalsIgnoreCase(java.lang.String s1, java.lang.String s2) {
		return StringUtil.equalsIgnoreCase(s1, s2);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public java.lang.String extract(java.lang.String s, char[] chars) {
		return StringUtil.extract(s, chars);
	}

	public java.lang.String extractChars(java.lang.String s) {
		return StringUtil.extractChars(s);
	}

	public java.lang.String extractDigits(java.lang.String s) {
		return StringUtil.extractDigits(s);
	}

	public java.lang.String extractFirst(java.lang.String s, char delimiter) {
		return StringUtil.extractFirst(s, delimiter);
	}

	public java.lang.String extractFirst(java.lang.String s,
		java.lang.String delimiter) {
		return StringUtil.extractFirst(s, delimiter);
	}

	public java.lang.String extractLast(java.lang.String s, char delimiter) {
		return StringUtil.extractLast(s, delimiter);
	}

	public java.lang.String extractLast(java.lang.String s,
		java.lang.String delimiter) {
		return StringUtil.extractLast(s, delimiter);
	}

	public java.lang.String extractLeadingDigits(java.lang.String s) {
		return StringUtil.extractLeadingDigits(s);
	}

	public int indexOfAny(java.lang.String s, char[] chars) {
		return StringUtil.indexOfAny(s, chars);
	}

	public int indexOfAny(java.lang.String s, char[] chars, int fromIndex) {
		return StringUtil.indexOfAny(s, chars, fromIndex);
	}

	public int indexOfAny(java.lang.String s, char[] chars, int fromIndex,
		int toIndex) {
		return StringUtil.indexOfAny(s, chars, fromIndex, toIndex);
	}

	public int indexOfAny(java.lang.String s, java.lang.String[] texts) {
		return StringUtil.indexOfAny(s, texts);
	}

	public int indexOfAny(java.lang.String s, java.lang.String[] texts,
		int fromIndex) {
		return StringUtil.indexOfAny(s, texts, fromIndex);
	}

	public int indexOfAny(java.lang.String s, java.lang.String[] texts,
		int fromIndex, int toIndex) {
		return StringUtil.indexOfAny(s, texts, fromIndex, toIndex);
	}

	public java.lang.String insert(java.lang.String s, java.lang.String insert,
		int offset) {
		return StringUtil.insert(s, insert, offset);
	}

	public boolean isLowerCase(java.lang.String s) {
		return StringUtil.isLowerCase(s);
	}

	public boolean isUpperCase(java.lang.String s) {
		return StringUtil.isUpperCase(s);
	}

	public int lastIndexOfAny(java.lang.String s, char[] chars) {
		return StringUtil.lastIndexOfAny(s, chars);
	}

	public int lastIndexOfAny(java.lang.String s, char[] chars, int toIndex) {
		return StringUtil.lastIndexOfAny(s, chars, toIndex);
	}

	public int lastIndexOfAny(java.lang.String s, char[] chars, int fromIndex,
		int toIndex) {
		return StringUtil.lastIndexOfAny(s, chars, fromIndex, toIndex);
	}

	public int lastIndexOfAny(java.lang.String s, java.lang.String[] texts) {
		return StringUtil.lastIndexOfAny(s, texts);
	}

	public int lastIndexOfAny(java.lang.String s, java.lang.String[] texts,
		int toIndex) {
		return StringUtil.lastIndexOfAny(s, texts, toIndex);
	}

	public int lastIndexOfAny(java.lang.String s, java.lang.String[] texts,
		int fromIndex, int toIndex) {
		return StringUtil.lastIndexOfAny(s, texts, fromIndex, toIndex);
	}

	public java.lang.String lowerCase(java.lang.String s) {
		return StringUtil.lowerCase(s);
	}

	public void lowerCase(java.lang.String... array) {
		StringUtil.lowerCase(array);
	}

	public java.lang.String lowerCaseFirstLetter(java.lang.String s) {
		return StringUtil.lowerCaseFirstLetter(s);
	}

	public boolean matches(java.lang.String s, java.lang.String pattern) {
		return StringUtil.matches(s, pattern);
	}

	public boolean matchesIgnoreCase(java.lang.String s,
		java.lang.String pattern) {
		return StringUtil.matchesIgnoreCase(s, pattern);
	}

	public java.lang.String merge(boolean[] array) {
		return StringUtil.merge(array);
	}

	public java.lang.String merge(boolean[] array, java.lang.String delimiter) {
		return StringUtil.merge(array, delimiter);
	}

	public java.lang.String merge(char[] array) {
		return StringUtil.merge(array);
	}

	public java.lang.String merge(char[] array, java.lang.String delimiter) {
		return StringUtil.merge(array, delimiter);
	}

	public java.lang.String merge(java.util.Collection<?> col) {
		return StringUtil.merge(col);
	}

	public java.lang.String merge(java.util.Collection<?> col,
		java.lang.String delimiter) {
		return StringUtil.merge(col, delimiter);
	}

	public java.lang.String merge(double[] array) {
		return StringUtil.merge(array);
	}

	public java.lang.String merge(double[] array, java.lang.String delimiter) {
		return StringUtil.merge(array, delimiter);
	}

	public java.lang.String merge(float[] array) {
		return StringUtil.merge(array);
	}

	public java.lang.String merge(float[] array, java.lang.String delimiter) {
		return StringUtil.merge(array, delimiter);
	}

	public java.lang.String merge(int[] array) {
		return StringUtil.merge(array);
	}

	public java.lang.String merge(int[] array, java.lang.String delimiter) {
		return StringUtil.merge(array, delimiter);
	}

	public java.lang.String merge(long[] array) {
		return StringUtil.merge(array);
	}

	public java.lang.String merge(long[] array, java.lang.String delimiter) {
		return StringUtil.merge(array, delimiter);
	}

	public java.lang.String merge(java.lang.Object[] array) {
		return StringUtil.merge(array);
	}

	public java.lang.String merge(java.lang.Object[] array,
		java.lang.String delimiter) {
		return StringUtil.merge(array, delimiter);
	}

	public java.lang.String merge(short[] array) {
		return StringUtil.merge(array);
	}

	public java.lang.String merge(short[] array, java.lang.String delimiter) {
		return StringUtil.merge(array, delimiter);
	}

	public java.lang.String quote(java.lang.String s) {
		return StringUtil.quote(s);
	}

	public java.lang.String quote(java.lang.String s, char quote) {
		return StringUtil.quote(s, quote);
	}

	public java.lang.String quote(java.lang.String s, java.lang.String quote) {
		return StringUtil.quote(s, quote);
	}

	public java.lang.String randomId() {
		return StringUtil.randomId();
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public java.lang.String randomize(java.lang.String s) {
		return StringUtil.randomize(s);
	}

	public java.lang.String randomString() {
		return StringUtil.randomString();
	}

	public java.lang.String randomString(int length) {
		return StringUtil.randomString(length);
	}

	public java.lang.String read(java.lang.Class<?> clazz, java.lang.String name) {
		return StringUtil.read(clazz, name);
	}

	public java.lang.String read(java.lang.ClassLoader classLoader,
		java.lang.String name) throws java.io.IOException {
		return StringUtil.read(classLoader, name);
	}

	public java.lang.String read(java.lang.ClassLoader classLoader,
		java.lang.String name, boolean all) throws java.io.IOException {
		return StringUtil.read(classLoader, name, all);
	}

	public java.lang.String read(java.io.InputStream is)
		throws java.io.IOException {
		return StringUtil.read(is);
	}

	public void readLines(java.io.InputStream is,
		java.util.Collection<java.lang.String> lines)
		throws java.io.IOException {
		StringUtil.readLines(is, lines);
	}

	public java.lang.String removeChar(java.lang.String s, char oldSub) {
		return StringUtil.removeChar(s, oldSub);
	}

	public java.lang.String removeChars(java.lang.String s, char... oldSubs) {
		return StringUtil.removeChars(s, oldSubs);
	}

	public java.lang.String removeFromList(java.lang.String s,
		java.lang.String element) {
		return StringUtil.removeFromList(s, element);
	}

	public java.lang.String removeFromList(java.lang.String s,
		java.lang.String element, java.lang.String delimiter) {
		return StringUtil.removeFromList(s, element, delimiter);
	}

	public java.lang.String removeSubstring(java.lang.String s,
		java.lang.String oldSub) {
		return StringUtil.removeSubstring(s, oldSub);
	}

	public java.lang.String removeSubstrings(java.lang.String s,
		java.lang.String... oldSubs) {
		return StringUtil.removeSubstrings(s, oldSubs);
	}

	public java.lang.String replace(java.lang.String s, char oldSub, char newSub) {
		return StringUtil.replace(s, oldSub, newSub);
	}

	public java.lang.String replace(java.lang.String s, char oldSub,
		java.lang.String newSub) {
		return StringUtil.replace(s, oldSub, newSub);
	}

	public java.lang.String replace(java.lang.String s, char[] oldSubs,
		char[] newSubs) {
		return StringUtil.replace(s, oldSubs, newSubs);
	}

	public java.lang.String replace(java.lang.String s, char[] oldSubs,
		java.lang.String[] newSubs) {
		return StringUtil.replace(s, oldSubs, newSubs);
	}

	public java.lang.String replace(java.lang.String s,
		java.lang.String oldSub, java.lang.String newSub) {
		return StringUtil.replace(s, oldSub, newSub);
	}

	public java.lang.String replace(java.lang.String s,
		java.lang.String oldSub, java.lang.String newSub, int fromIndex) {
		return StringUtil.replace(s, oldSub, newSub, fromIndex);
	}

	public java.lang.String replace(java.lang.String s, java.lang.String begin,
		java.lang.String end,
		java.util.Map<java.lang.String, java.lang.String> values) {
		return StringUtil.replace(s, begin, end, values);
	}

	public java.lang.String replace(java.lang.String s,
		java.lang.String[] oldSubs, java.lang.String[] newSubs) {
		return StringUtil.replace(s, oldSubs, newSubs);
	}

	public java.lang.String replace(java.lang.String s,
		java.lang.String[] oldSubs, java.lang.String[] newSubs,
		boolean exactMatch) {
		return StringUtil.replace(s, oldSubs, newSubs, exactMatch);
	}

	public java.lang.String replaceFirst(java.lang.String s, char oldSub,
		char newSub) {
		return StringUtil.replaceFirst(s, oldSub, newSub);
	}

	public java.lang.String replaceFirst(java.lang.String s, char oldSub,
		java.lang.String newSub) {
		return StringUtil.replaceFirst(s, oldSub, newSub);
	}

	public java.lang.String replaceFirst(java.lang.String s,
		java.lang.String oldSub, java.lang.String newSub) {
		return StringUtil.replaceFirst(s, oldSub, newSub);
	}

	public java.lang.String replaceFirst(java.lang.String s,
		java.lang.String oldSub, java.lang.String newSub, int fromIndex) {
		return StringUtil.replaceFirst(s, oldSub, newSub, fromIndex);
	}

	public java.lang.String replaceFirst(java.lang.String s,
		java.lang.String[] oldSubs, java.lang.String[] newSubs) {
		return StringUtil.replaceFirst(s, oldSubs, newSubs);
	}

	public java.lang.String replaceLast(java.lang.String s, char oldSub,
		char newSub) {
		return StringUtil.replaceLast(s, oldSub, newSub);
	}

	public java.lang.String replaceLast(java.lang.String s, char oldSub,
		java.lang.String newSub) {
		return StringUtil.replaceLast(s, oldSub, newSub);
	}

	public java.lang.String replaceLast(java.lang.String s,
		java.lang.String oldSub, java.lang.String newSub) {
		return StringUtil.replaceLast(s, oldSub, newSub);
	}

	public java.lang.String replaceLast(java.lang.String s,
		java.lang.String[] oldSubs, java.lang.String[] newSubs) {
		return StringUtil.replaceLast(s, oldSubs, newSubs);
	}

	public com.liferay.portal.kernel.util.StringBundler replaceToStringBundler(
		java.lang.String s, java.lang.String begin, java.lang.String end,
		java.util.Map<java.lang.String, java.lang.String> values) {
		return StringUtil.replaceToStringBundler(s, begin, end, values);
	}

	public com.liferay.portal.kernel.util.StringBundler replaceWithStringBundler(
		java.lang.String s, java.lang.String begin, java.lang.String end,
		java.util.Map<java.lang.String, com.liferay.portal.kernel.util.StringBundler> values) {
		return StringUtil.replaceWithStringBundler(s, begin, end, values);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public java.lang.String reverse(java.lang.String s) {
		return StringUtil.reverse(s);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public java.lang.String safePath(java.lang.String path) {
		return StringUtil.safePath(path);
	}

	public java.lang.String shorten(java.lang.String s) {
		return StringUtil.shorten(s);
	}

	public java.lang.String shorten(java.lang.String s, int length) {
		return StringUtil.shorten(s, length);
	}

	public java.lang.String shorten(java.lang.String s, int length,
		java.lang.String suffix) {
		return StringUtil.shorten(s, length, suffix);
	}

	public java.lang.String shorten(java.lang.String s, java.lang.String suffix) {
		return StringUtil.shorten(s, suffix);
	}

	public java.lang.String[] split(java.lang.String s) {
		return StringUtil.split(s);
	}

	public boolean[] split(java.lang.String s, boolean x) {
		return StringUtil.split(s, x);
	}

	public java.lang.String[] split(java.lang.String s, char delimiter) {
		return StringUtil.split(s, delimiter);
	}

	public double[] split(java.lang.String s, double x) {
		return StringUtil.split(s, x);
	}

	public float[] split(java.lang.String s, float x) {
		return StringUtil.split(s, x);
	}

	public int[] split(java.lang.String s, int x) {
		return StringUtil.split(s, x);
	}

	public long[] split(java.lang.String s, long x) {
		return StringUtil.split(s, x);
	}

	public short[] split(java.lang.String s, short x) {
		return StringUtil.split(s, x);
	}

	public java.lang.String[] split(java.lang.String s,
		java.lang.String delimiter) {
		return StringUtil.split(s, delimiter);
	}

	public boolean[] split(java.lang.String s, java.lang.String delimiter,
		boolean x) {
		return StringUtil.split(s, delimiter, x);
	}

	public double[] split(java.lang.String s, java.lang.String delimiter,
		double x) {
		return StringUtil.split(s, delimiter, x);
	}

	public float[] split(java.lang.String s, java.lang.String delimiter, float x) {
		return StringUtil.split(s, delimiter, x);
	}

	public int[] split(java.lang.String s, java.lang.String delimiter, int x) {
		return StringUtil.split(s, delimiter, x);
	}

	public long[] split(java.lang.String s, java.lang.String delimiter, long x) {
		return StringUtil.split(s, delimiter, x);
	}

	public short[] split(java.lang.String s, java.lang.String delimiter, short x) {
		return StringUtil.split(s, delimiter, x);
	}

	public java.lang.String[] splitLines(java.lang.String s) {
		return StringUtil.splitLines(s);
	}

	public boolean startsWith(java.lang.String s, char begin) {
		return StringUtil.startsWith(s, begin);
	}

	public boolean startsWith(java.lang.String s, java.lang.String start) {
		return StringUtil.startsWith(s, start);
	}

	public int startsWithWeight(java.lang.String s1, java.lang.String s2) {
		return StringUtil.startsWithWeight(s1, s2);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public java.lang.String strip(java.lang.String s, char remove) {
		return StringUtil.strip(s, remove);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public java.lang.String strip(java.lang.String s, char[] remove) {
		return StringUtil.strip(s, remove);
	}

	public java.lang.String stripBetween(java.lang.String s,
		java.lang.String begin, java.lang.String end) {
		return StringUtil.stripBetween(s, begin, end);
	}

	public java.lang.String stripCDATA(java.lang.String s) {
		return StringUtil.stripCDATA(s);
	}

	public java.lang.String stripParentheticalSuffix(java.lang.String s) {
		return StringUtil.stripParentheticalSuffix(s);
	}

	public java.lang.String toCharCode(java.lang.String s) {
		return StringUtil.toCharCode(s);
	}

	public java.lang.String toHexString(int i) {
		return StringUtil.toHexString(i);
	}

	public java.lang.String toHexString(long l) {
		return StringUtil.toHexString(l);
	}

	public java.lang.String toHexString(java.lang.Object obj) {
		return StringUtil.toHexString(obj);
	}

	public java.lang.String toLowerCase(java.lang.String s) {
		return StringUtil.toLowerCase(s);
	}

	public java.lang.String toLowerCase(java.lang.String s,
		java.util.Locale locale) {
		return StringUtil.toLowerCase(s, locale);
	}

	public java.lang.String toUpperCase(java.lang.String s) {
		return StringUtil.toUpperCase(s);
	}

	public java.lang.String toUpperCase(java.lang.String s,
		java.util.Locale locale) {
		return StringUtil.toUpperCase(s, locale);
	}

	public java.lang.String trim(java.lang.String s) {
		return StringUtil.trim(s);
	}

	public java.lang.String trim(java.lang.String s, char c) {
		return StringUtil.trim(s, c);
	}

	public java.lang.String trim(java.lang.String s, char[] exceptions) {
		return StringUtil.trim(s, exceptions);
	}

	public java.lang.String trimLeading(java.lang.String s) {
		return StringUtil.trimLeading(s);
	}

	public java.lang.String trimLeading(java.lang.String s, char c) {
		return StringUtil.trimLeading(s, c);
	}

	public java.lang.String trimLeading(java.lang.String s, char[] exceptions) {
		return StringUtil.trimLeading(s, exceptions);
	}

	public java.lang.String trimTrailing(java.lang.String s) {
		return StringUtil.trimTrailing(s);
	}

	public java.lang.String trimTrailing(java.lang.String s, char c) {
		return StringUtil.trimTrailing(s, c);
	}

	public java.lang.String trimTrailing(java.lang.String s, char[] exceptions) {
		return StringUtil.trimTrailing(s, exceptions);
	}

	public java.lang.String unquote(java.lang.String s) {
		return StringUtil.unquote(s);
	}

	public java.lang.String upperCase(java.lang.String s) {
		return StringUtil.upperCase(s);
	}

	public java.lang.String upperCaseFirstLetter(java.lang.String s) {
		return StringUtil.upperCaseFirstLetter(s);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public java.lang.String valueOf(java.lang.Object obj) {
		return StringUtil.valueOf(obj);
	}

	public boolean wildcardMatches(java.lang.String s,
		java.lang.String wildcard, char singleWildcardCharacter,
		char multipleWildcardCharacter, char escapeWildcardCharacter,
		boolean caseSensitive) {
		return StringUtil.wildcardMatches(s, wildcard, singleWildcardCharacter,
			multipleWildcardCharacter, escapeWildcardCharacter, caseSensitive);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public java.lang.String wrap(java.lang.String text) {
		return StringUtil.wrap(text);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public java.lang.String wrap(java.lang.String text, int width,
		java.lang.String lineSeparator) {
		return StringUtil.wrap(text, width, lineSeparator);
	}

	private StringUtil_IW() {
	}

	private static StringUtil_IW _instance = new StringUtil_IW();
}