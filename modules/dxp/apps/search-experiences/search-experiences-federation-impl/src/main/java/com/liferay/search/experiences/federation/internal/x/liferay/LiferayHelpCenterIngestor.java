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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.search.experiences.federation.configuration.IngestionConfiguration;
import com.liferay.search.experiences.federation.internal.index.FederatedContentIndexer;
import com.liferay.search.experiences.federation.internal.ingest.Ingestor;
import com.liferay.search.experiences.federation.internal.x.zendesk.ZendeskArticle;
import com.liferay.search.experiences.federation.internal.x.zendesk.ZendeskArticlesPage;
import com.liferay.search.experiences.federation.internal.x.zendesk.ZendeskUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gustavo Lima
 */
@Component(
	configurationPid = "com.liferay.search.experiences.federation.configuration.IngestionConfiguration",
	enabled = false, immediate = true, service = Ingestor.class
)
public class LiferayHelpCenterIngestor implements Ingestor {

	@Override
	public void ingest() {
		String address =
			"https://liferay-support.zendesk.com/api/v2/help_center/en-us" +
				"/articles.json";

		while (address != null) {
			ZendeskArticlesPage zendeskArticlesPage =
				ZendeskUtil.getZendeskArticlesPage(address);

			if (ListUtil.isNotEmpty(zendeskArticlesPage.articles)) {
				for (ZendeskArticle zendeskArticle :
						zendeskArticlesPage.articles) {

					_ingest(zendeskArticle);
				}
			}

			address = zendeskArticlesPage.next_page;
		}
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
	protected FederatedContentIndexer federatedContentIndexer;

	@Reference
	protected SearchEngineAdapter searchEngineAdapter;

	private void _ingest(ZendeskArticle zendeskArticle) {
		if (_log.isInfoEnabled()) {
			_log.info("Indexing: " + zendeskArticle.html_url);
		}

		DocumentBuilder documentBuilder = documentBuilderFactory.builder(
		).setString(
			"content", zendeskArticle.body
		).setString(
			"link", zendeskArticle.html_url
		).setString(
			"origin_site", "Liferay Help Center"
		).setString(
			"title", zendeskArticle.title
		);

		TagsIngestionUtil.ingestTags(
			documentBuilder, _ingestionConfiguration,
			zendeskArticle.label_names);

		federatedContentIndexer.index(documentBuilder.build());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayHelpCenterIngestor.class);

	private volatile IngestionConfiguration _ingestionConfiguration;

}