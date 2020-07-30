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

package com.liferay.portal.remote.cors.internal.path.pattern.matcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Arthur Chan
 * @author Carlos Sierra Andrés
 */
public class StaticURLPathPatternMatcher<T>
	implements URLPathPatternMatcher<T> {

	public StaticURLPathPatternMatcher(int longesturlPathPatternSize) {
		if (longesturlPathPatternSize < 1) {
			longesturlPathPatternSize = 64;
		}

		_extensionStaticTrieArrayMatcher =
			new ExtensionStaticTrieArrayMatcher<>(longesturlPathPatternSize);
		_wildcardStaticTrieArrayMatcher = new WildcardStaticTrieArrayMatcher<>(
			longesturlPathPatternSize);
	}

	public T getValue(String urlPath) {
		T value = _wildcardStaticTrieArrayMatcher.getValue(urlPath);

		if (value != null) {
			return value;
		}

		return _extensionStaticTrieArrayMatcher.getValue(urlPath);
	}

	public void putValue(String urlPathPattern, T value)
		throws IllegalArgumentException {

		if (URLPathPatternMatcher.isValidExtensionPattern(urlPathPattern)) {
			_extensionStaticTrieArrayMatcher.putValue(
				urlPathPattern, value, false);
		}
		else {
			_wildcardStaticTrieArrayMatcher.putValue(
				urlPathPattern, value, true);
		}
	}

	protected static int getFirstSetBitIndex(long bitMask) {
		int firstSetBitIndex = -1;

		if (bitMask == 0) {
			return firstSetBitIndex;
		}

		firstSetBitIndex = 63;

		long currentBitMask = bitMask << 32;

		if (currentBitMask != 0) {
			firstSetBitIndex -= 32;
			bitMask = currentBitMask;
		}

		currentBitMask = bitMask << 16;

		if (currentBitMask != 0) {
			firstSetBitIndex -= 16;
			bitMask = currentBitMask;
		}

		currentBitMask = bitMask << 8;

		if (currentBitMask != 0) {
			firstSetBitIndex -= 8;
			bitMask = currentBitMask;
		}

		currentBitMask = bitMask << 4;

		if (currentBitMask != 0) {
			firstSetBitIndex -= 4;
			bitMask = currentBitMask;
		}

		currentBitMask = bitMask << 2;

		if (currentBitMask != 0) {
			firstSetBitIndex -= 2;
			bitMask = currentBitMask;
		}

		currentBitMask = bitMask << 1;

		if (currentBitMask != 0) {
			firstSetBitIndex -= 1;
		}

		return firstSetBitIndex;
	}

	private static final long _ALL_BITS_SET = ~0;

	private static final int _SLASH_INDEX = '/' - ASCII_PRINTABLE_OFFSET;

	private static final int _STAR_INDEX = '*' - ASCII_PRINTABLE_OFFSET;

	private final ExtensionStaticTrieArrayMatcher<T>
		_extensionStaticTrieArrayMatcher;
	private final WildcardStaticTrieArrayMatcher<T>
		_wildcardStaticTrieArrayMatcher;

	private abstract static class BaseStaticTrieArrayMatcher<T> {

		public BaseStaticTrieArrayMatcher(int maxUrlPathPatternLength) {
			this.maxUrlPathPatternLength = maxUrlPathPatternLength;

			trieArray =
				new long[2][maxUrlPathPatternLength][ASCII_CHARACTER_RANGE];
		}

		protected int getExactIndex(String urlPath) {
			int row = 0;
			long bitMask = _ALL_BITS_SET;
			int column = 0;

			for (; row < urlPath.length(); ++row) {
				if (row > (maxUrlPathPatternLength - 1)) {
					bitMask = 0;

					break;
				}

				char character = urlPath.charAt(row);

				column = character - ASCII_PRINTABLE_OFFSET;

				bitMask &= trieArray[0][row][column];

				if (bitMask == 0) {
					break;
				}
			}

			if (bitMask != 0) {
				bitMask &= trieArray[1][row - 1][column];

				if (bitMask != 0) {
					return getFirstSetBitIndex(bitMask);
				}
			}

			return -1;
		}

		protected void putValue(
			String urlPathPattern, T value, boolean forward) {

			if (_count > 63) {
				throw new IllegalArgumentException(
					"Exceeding maximum number of allowed URL patterns");
			}

			int index = getExactIndex(urlPathPattern);

			if (index > -1) {
				values.add(index, value);

				return;
			}

			index = _count++;

			int row = 0;
			int column = 0;
			long bitMask = 1 << index;

			for (; row < urlPathPattern.length(); ++row) {
				char character = urlPathPattern.charAt(row);

				if (!forward) {
					character = urlPathPattern.charAt(
						urlPathPattern.length() - 1 - row);
				}

				column = character - ASCII_PRINTABLE_OFFSET;

				trieArray[0][row][column] |= bitMask;
			}

			trieArray[1][row - 1][column] |= bitMask;

			values.add(index, value);
		}

		protected int maxUrlPathPatternLength;
		protected final long[][][] trieArray;
		protected List<T> values = new ArrayList<>(Long.SIZE);

		private int _count;

	}

	private static class ExtensionStaticTrieArrayMatcher<T>
		extends BaseStaticTrieArrayMatcher<T> {

		public ExtensionStaticTrieArrayMatcher(int maxUrlPathPatternLength) {
			super(maxUrlPathPatternLength);
		}

		public T getValue(String urlPath) {
			int urlPathLength = urlPath.length();
			long currentBitMask = _ALL_BITS_SET;

			for (int row = 0; row < urlPathLength; ++row) {
				if (row > (maxUrlPathPatternLength - 1)) {
					break;
				}

				char character = urlPath.charAt(urlPathLength - 1 - row);

				if (character == '/') {
					break;
				}

				int column = character - ASCII_PRINTABLE_OFFSET;

				currentBitMask &= trieArray[0][row][column];

				if (currentBitMask == 0) {
					break;
				}

				if ((character == '.') &&
					((row + 1) < maxUrlPathPatternLength)) {

					long bitMask =
						currentBitMask & trieArray[1][row + 1][_STAR_INDEX];

					if (bitMask != 0) {
						return values.get(getFirstSetBitIndex(bitMask));
					}

					break;
				}
			}

			return null;
		}

	}

	private static class WildcardStaticTrieArrayMatcher<T>
		extends BaseStaticTrieArrayMatcher<T> {

		public WildcardStaticTrieArrayMatcher(int maxUrlPathPatternLength) {
			super(maxUrlPathPatternLength);
		}

		public T getValue(String urlPath) {
			boolean onlyExact = false;
			boolean onlyWildcard = false;

			if (urlPath.charAt(0) != '/') {
				onlyExact = true;
			}
			else if ((urlPath.length() > 1) &&
					 (urlPath.charAt(urlPath.length() - 2) == '/') &&
					 (urlPath.charAt(urlPath.length() - 1) == '*')) {

				onlyWildcard = true;
			}

			int row = 0;
			int col = 0;
			long currentBitMask = _ALL_BITS_SET;
			long bestMatchBitMask = 0;

			for (; row < urlPath.length(); ++row) {
				if (row > (maxUrlPathPatternLength - 1)) {
					currentBitMask = 0;

					break;
				}

				char character = urlPath.charAt(row);

				col = character - ASCII_PRINTABLE_OFFSET;

				currentBitMask &= trieArray[0][row][col];

				if (currentBitMask == 0) {
					break;
				}

				if (!onlyExact && (character == '/') &&
					((row + 1) < maxUrlPathPatternLength)) {

					long bitMask =
						currentBitMask & trieArray[1][row + 1][_STAR_INDEX];

					if (bitMask != 0) {
						bestMatchBitMask = bitMask;
					}
				}
			}

			if (currentBitMask == 0) {
				if (bestMatchBitMask == 0) {
					return null;
				}

				return values.get(getFirstSetBitIndex(bestMatchBitMask));
			}

			if (onlyExact) {
				long bitMask = currentBitMask & trieArray[1][row - 1][col];

				if (bitMask != 0) {
					return values.get(getFirstSetBitIndex(bitMask));
				}

				return null;
			}

			if (!onlyWildcard) {
				long bitMask = currentBitMask & trieArray[1][row - 1][col];

				if (bitMask != 0) {
					return values.get(getFirstSetBitIndex(bitMask));
				}
			}

			long extraBitMask =
				currentBitMask & trieArray[0][row][_SLASH_INDEX];

			extraBitMask &= trieArray[0][row + 1][_STAR_INDEX];
			extraBitMask &= trieArray[1][row + 1][_STAR_INDEX];

			if (extraBitMask != 0) {
				return values.get(getFirstSetBitIndex(extraBitMask));
			}

			return values.get(getFirstSetBitIndex(bestMatchBitMask));
		}

	}

}