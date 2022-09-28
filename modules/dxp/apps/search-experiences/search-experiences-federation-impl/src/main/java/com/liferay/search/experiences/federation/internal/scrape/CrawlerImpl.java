/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.federation.internal.scrape;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * @author André de Oliveira
 */
public class CrawlerImpl implements Crawler {

	public static BuilderImpl builder() {
		return new BuilderImpl();
	}

	public CrawlerImpl() {
	}

	public CrawlerImpl(CrawlerImpl crawlerImpl) {
		_base = crawlerImpl._base;
		_beginListLinks = crawlerImpl._beginListLinks;
		_consumer = crawlerImpl._consumer;
		_delimiter = crawlerImpl._delimiter;
		_endListLinks = crawlerImpl._endListLinks;
		_html = crawlerImpl._html;
		_beginLink = crawlerImpl._beginLink;
		_endLink = crawlerImpl._endLink;
		_ignores = crawlerImpl._ignores;
	}

	@Override
	public void crawl() {
		String list = StringUtils.substringBetween(
			_html, _beginListLinks, _endListLinks);

		while (!list.equals("")) {
			String link = StringUtils.substringBetween(
				list, _getBeginLink(), _getEndLink());
			boolean hasIgnore = false;

			if (_ignores != null) {
				hasIgnore = _shouldIgnore(link);
			}

			if ((link != null) && !hasIgnore) {
				_consumer.accept(_base + link);
			}

			list = StringUtils.substringAfter(list, _getDelimiter());
		}
	}

	public static class BuilderImpl implements Crawler.Builder {

		public BuilderImpl base(String base) {
			_crawlerImpl._base = base;

			return this;
		}

		@Override
		public CrawlerImpl build() {
			return new CrawlerImpl(_crawlerImpl);
		}

		public BuilderImpl delimiter(String delimiter) {
			_crawlerImpl._delimiter = delimiter;

			return this;
		}

		public BuilderImpl html(String html) {
			_crawlerImpl._html = html;

			return this;
		}

		public BuilderImpl ignores(ArrayList<String> ignores) {
			_crawlerImpl._ignores = ignores;

			return this;
		}

		public BuilderImpl linkReference(String begin, String end) {
			_crawlerImpl._beginLink = begin;
			_crawlerImpl._endLink = end;

			return this;
		}

		public BuilderImpl listLinksDelimiter(String begin, String end) {
			_crawlerImpl._beginListLinks = begin;
			_crawlerImpl._endListLinks = end;

			return this;
		}

		@Override
		public BuilderImpl onAddress(Consumer<String> consumer) {
			_crawlerImpl._consumer = consumer;

			return this;
		}

		private final CrawlerImpl _crawlerImpl = new CrawlerImpl();

	}

	private String _getBeginLink() {
		if (_beginLink == null) {
			_beginLink = "href=\"";
		}

		return _beginLink;
	}

	private String _getDelimiter() {
		if (_delimiter == null) {
			_delimiter = "</li>";
		}

		return _delimiter;
	}

	private String _getEndLink() {
		if (_endLink == null) {
			_endLink = "\"";
		}

		return _endLink;
	}

	private boolean _shouldIgnore(String link) {
		for (String ignore : _ignores) {
			Pattern pattern = Pattern.compile(ignore, Pattern.CASE_INSENSITIVE);

			Matcher matcher = pattern.matcher(link);

			if (matcher.find()) {
				return true;
			}
		}

		return false;
	}

	private String _base;
	private String _beginLink;
	private String _beginListLinks;
	private Consumer<String> _consumer;
	private String _delimiter;
	private String _endLink;
	private String _endListLinks;
	private String _html;
	private ArrayList<String> _ignores;

}