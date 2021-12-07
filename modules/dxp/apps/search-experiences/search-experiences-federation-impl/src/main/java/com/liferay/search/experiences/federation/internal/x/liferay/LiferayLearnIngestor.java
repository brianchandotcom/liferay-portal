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

package com.liferay.search.experiences.federation.internal.x.liferay;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.search.experiences.federation.configuration.IngestionConfiguration;
import com.liferay.search.experiences.federation.internal.download.Downloader;
import com.liferay.search.experiences.federation.internal.index.FederatedContentIndexer;
import com.liferay.search.experiences.federation.internal.ingest.Ingestor;
import com.liferay.search.experiences.federation.internal.scrape.Crawler;
import com.liferay.search.experiences.federation.internal.scrape.CrawlerImpl;
import com.liferay.search.experiences.federation.internal.scrape.Scraper;
import com.liferay.search.experiences.federation.internal.scrape.ScraperFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.commons.lang.StringUtils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gustavo Lima
 * @author André de Oliveira
 */
@Component(
	configurationPid = "com.liferay.search.experiences.federation.configuration.IngestionConfiguration",
	enabled = false, immediate = true, service = Ingestor.class
)
public class LiferayLearnIngestor implements Ingestor {

	@Override
	public void ingest() {
		Scraper scraper = scraperFactory.builder(
		).consumeImmediately(
			false
		).crawlerBuilder(
			_getCrawlerBuilder()
		).onAddress(
			this::_index
		).build();

		scraper.scrape();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ingestionConfiguration = ConfigurableUtil.createConfigurable(
			IngestionConfiguration.class, properties);
	}

	@Reference
	protected DocumentBuilderFactory documentBuilderFactory;

	@Reference
	protected Downloader downloader;

	@Reference
	protected FederatedContentIndexer federatedContentIndexer;

	@Reference
	protected ScraperFactory scraperFactory;

	private Crawler _buildCrawler(Consumer<String> consumer) {
		return CrawlerImpl.builder(
		).base(
			"https://learn.liferay.com"
		).listLinksDelimiter(
			"<section class=\"col-md-12 justify-content-center products\">",
			"</section>"
		).delimiter(
			"</a>"
		).ignores(
			new ArrayList<>(Arrays.asList("reference/latest/en/index.html"))
		).html(
			downloader.download("https://learn.liferay.com/index.html")
		).onAddress(
			address -> _crawl1(consumer, address)
		).build();
	}

	private void _crawl1(Consumer<String> consumer, String seed) {
		CrawlerImpl.builder(
		).base(
			StringUtils.substringBefore(seed, "index.html")
		).listLinksDelimiter(
			"<ul>", "</ul>"
		).html(
			downloader.download(seed)
		).onAddress(
			address -> _crawl2(consumer, address)
		).build(
		).crawl();
	}

	private void _crawl2(Consumer<String> consumer, String seed) {
		CrawlerImpl.builder(
		).base(
			StringUtils.substringBeforeLast(seed, "/") + "/"
		).listLinksDelimiter(
			"<ul>", "</ul>"
		).html(
			downloader.download(seed)
		).onAddress(
			consumer
		).build(
		).crawl();
	}

	private Crawler.Builder _getCrawlerBuilder() {
		return new Crawler.Builder() {

			@Override
			public Crawler build() {
				return _buildCrawler(_consumer);
			}

			@Override
			public Crawler.Builder onAddress(Consumer<String> consumer) {
				_consumer = consumer;

				return this;
			}

			private Consumer<String> _consumer;

		};
	}

	private String _getLiferayVersion(String content) {
		String aux = StringUtils.substringAfter(content, "Liferay DXP 7.");

		if (aux.equals("")) {
			aux = StringUtils.substringAfter(content, "Liferay Portal 6.");

			return "Liferay DXP 6." + aux.charAt(0);
		}

		return "Liferay DXP 7." + aux.charAt(0);
	}

	private String _getTitle(String content) {
		String title = StringUtils.substringBetween(
			content, "<title>", "</title>");

		return StringUtils.substringBeforeLast(title, "&");
	}

	private void _index(String address) {
		if (_log.isInfoEnabled()) {
			_log.info("Indexing: " + address);
		}

		String content = downloader.download(address);

		String version = _getLiferayVersion(content);

		DocumentBuilder documentBuilder = documentBuilderFactory.builder(
		).setString(
			"content", content
		).setString(
			"liferay_version", version
		).setString(
			"link", address
		).setString(
			"origin_site", "Liferay Learn"
		).setString(
			"title", _getTitle(content)
		);

		TagsIngestionUtil.ingestTags(
			documentBuilder, _ingestionConfiguration, Arrays.asList(version));

		federatedContentIndexer.index(documentBuilder.build());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayLearnIngestor.class);

	private volatile IngestionConfiguration _ingestionConfiguration;

}