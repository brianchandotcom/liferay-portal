/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.suggestions.spi.asah.user.activity;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.internal.util.AssetURLUtil;
import com.liferay.portal.search.rest.dto.v1_0.SuggestionsContributorConfiguration;
import com.liferay.portal.search.spi.suggestions.SuggestionsContributor;
import com.liferay.portal.search.suggestions.SuggestionsContributorResults;
import com.liferay.portal.search.suggestions.spi.constants.AsahSuggestionsConstants;

import java.util.HashMap;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gustavo Lima
 */
@Component(
	configurationPid = "com.liferay.portal.search.internal.configuration.UserActivityAsahConfiguration",
	property = "search.suggestions.contributor.name=recentAssetsUserActivity",
	service = SuggestionsContributor.class
)
public class RecentAssetsUserActivityAsahSuggestionsContributor
	extends BaseUserActivityAsahSuggestionsContributor
	implements SuggestionsContributor {

	@Override
	public SuggestionsContributorResults getSuggestionsContributorResults(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContext searchContext,
		SuggestionsContributorConfiguration
			suggestionsContributorConfiguration) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		return getSuggestionsContributorResults(
			AsahSuggestionsConstants.INDIVIDUALS,
			AsahSuggestionsConstants.RECENT_ASSETS, searchContext,
			"visits,lastVisitDate,firstVisitDate,url,assetTitle,assetId",
			suggestionsContributorConfiguration);
	}

	protected String getAssetURL(
		String destinationBaseURL, JSONObject itemJSONObject) {

		String url = itemJSONObject.getString("url");

		if (url.endsWith("/search")) {
			try {
				String className = _contentTypeToClassNameMap.get(
					itemJSONObject.getString("contentType"));

				AssetRendererFactory<?> assetRendererFactory =
					AssetRendererFactoryRegistryUtil.
						getAssetRendererFactoryByClassName(className);

				if (assetRendererFactory == null) {
					return null;
				}

				long classPK = itemJSONObject.getLong("assetId");

				return AssetURLUtil.getAssetURLView(
					assetRendererFactory.getAssetRenderer(classPK),
					assetRendererFactory, className, classPK,
					_liferayPortletRequest, _liferayPortletResponse);
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}

		return url;
	}

	protected String getText(JSONObject itemJSONObject) {
		return itemJSONObject.getString("assetTitle");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RecentAssetsUserActivityAsahSuggestionsContributor.class);

	private static final HashMap<String, String> _contentTypeToClassNameMap =
		HashMapBuilder.put(
			"blog", "com.liferay.blogs.model.BlogsEntry"
		).put(
			"document", "com.liferay.document.library.kernel.model.DLFileEntry"
		).put(
			"form",
			"com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord"
		).put(
			"web-content", "com.liferay.journal.model.JournalArticle"
		).build();

	private LiferayPortletRequest _liferayPortletRequest;
	private LiferayPortletResponse _liferayPortletResponse;

}