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

package com.liferay.mentions.matcher;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseRegularExpressionMentionsMatcher
	implements MentionsMatcher {

	@Override
	public Iterable<String> match(String s) {
		return new MentionsIterable(getMatcher(s));
	}

	protected Matcher getMatcher(String s) {
		if (_pattern == null) {
			_pattern = Pattern.compile(getRegularExpression());
		}

		return _pattern.matcher(s);
	}

	protected abstract String getRegularExpression();

	private volatile Pattern _pattern;

	private static class MentionsIterable implements Iterable<String> {

		public MentionsIterable(Matcher matcher) {
			_matcher = matcher;
		}

		@Override
		public Iterator<String> iterator() {
			_matcher.reset();

			return new MentionsIterator(_matcher);
		}

		private final Matcher _matcher;

	}

	private static class MentionsIterator implements Iterator<String> {

		public MentionsIterator(Matcher matcher) {
			_matcher = matcher;
		}

		@Override
		public boolean hasNext() {
			if (_hasNext == null) {
				_hasNext = _matcher.find();
			}

			return _hasNext;
		}

		@Override
		public String next() {
			if (_hasNext == null) {
				_hasNext = hasNext();
			}

			if (!_hasNext) {
				throw new NoSuchElementException();
			}

			_hasNext = null;

			return _matcher.group(1);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		private Boolean _hasNext;
		private final Matcher _matcher;

	}

}