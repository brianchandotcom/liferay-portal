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

import com.liferay.petra.string.StringPool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.apache.commons.lang.StringUtils;

/**
 * @author André de Oliveira
 */
public class ScraperImpl implements Scraper {

	@Override
	public void scrape() {
		_crawlerBuilder.onAddress(
			this::_seed
		).build(
		).crawl();

		if (!_consumeImmediately) {
			consumeAll();
		}
	}

	public static class ScraperBuilderImpl implements Scraper.Builder {

		@Override
		public Scraper build() {
			return new ScraperImpl(_scraperImpl);
		}

		@Override
		public Scraper.Builder consumeImmediately(boolean consumeImmediately) {
			_scraperImpl._consumeImmediately = consumeImmediately;

			return this;
		}

		@Override
		public Scraper.Builder crawlerBuilder(Crawler.Builder crawlerBuilder) {
			_scraperImpl._crawlerBuilder = crawlerBuilder;

			return this;
		}

		@Override
		public Scraper.Builder onAddress(Consumer<String> consumer) {
			_scraperImpl._consumers.add(consumer);

			return this;
		}

		private final ScraperImpl _scraperImpl = new ScraperImpl();

	}

	protected void add(String address) {
		_frontier.add(address);
	}

	protected void consume(String address) {
		for (Consumer<String> consumers : _consumers) {
			consumers.accept(address);
		}

		_frontier.consume(address);
	}

	protected void consumeAll() {
		for (String address : new HashSet<>(_frontier._addedAddresses)) {
			consume(address);
		}
	}

	protected String sanitize(String address) {
		return StringUtils.substringBefore(address, StringPool.POUND);
	}

	private ScraperImpl() {
		_consumers = new ArrayList<>();
	}

	private ScraperImpl(ScraperImpl scraperImpl) {
		_consumeImmediately = scraperImpl._consumeImmediately;
		_consumers = new ArrayList<>(scraperImpl._consumers);
		_crawlerBuilder = scraperImpl._crawlerBuilder;
	}

	private void _seed(String address) {
		String sanitized = sanitize(address);

		if (_consumeImmediately) {
			consume(sanitized);
		}
		else {
			add(sanitized);
		}
	}

	private boolean _consumeImmediately;
	private final List<Consumer<String>> _consumers;
	private Crawler.Builder _crawlerBuilder;
	private final Frontier _frontier = new Frontier();

	private static class Frontier {

		public synchronized void add(String address) {
			if (_consumedAddresses.contains(address)) {
				return;
			}

			_addedAddresses.add(address);
		}

		public synchronized void consume(String address) {
			_addedAddresses.remove(address);
			_consumedAddresses.add(address);
		}

		private final Set<String> _addedAddresses = new HashSet<>();
		private final Set<String> _consumedAddresses = new HashSet<>();

	}

}