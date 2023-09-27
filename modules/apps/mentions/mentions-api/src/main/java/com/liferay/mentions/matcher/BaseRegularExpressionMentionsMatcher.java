/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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