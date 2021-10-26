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

package com.liferay.search.experiences.development.web.internal.importer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.URLCodec;

import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLConnection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.portlet.PortletRequest;

/**
 * @author Petteri Karttunen
 * @author André de Oliveira
 */
public class WikipediaArticlesImporter {

	public static Builder builder(
		JournalArticleLocalService journalArticleLocalService) {

		return new Builder(
			new WikipediaArticlesImporter(journalArticleLocalService));
	}

	public int getCounter() {
		return _counter;
	}

	public void importWikipediaArticles() {
		if (ArrayUtil.isEmpty(_seeds)) {
			throw new RuntimeException("At least one seed article is required");
		}

		_runtimeException = new RuntimeException();

		_import(Arrays.asList(_seeds));

		if (ArrayUtil.isNotEmpty(_runtimeException.getSuppressed())) {
			throw _runtimeException;
		}
	}

	public static class Builder {

		public WikipediaArticlesImporter build() {
			return new WikipediaArticlesImporter(_wikipediaArticlesImporter);
		}

		public Builder groupIds(long... groupIds) {
			_wikipediaArticlesImporter._groupIds = groupIds;

			return this;
		}

		public Builder languageId(String languageId) {
			_wikipediaArticlesImporter._languageId = languageId;

			return this;
		}

		public Builder limit(Integer limit) {
			_wikipediaArticlesImporter._limit = limit;

			return this;
		}

		public Builder portletRequest(PortletRequest portletRequest) {
			_wikipediaArticlesImporter._portletRequest = portletRequest;

			return this;
		}

		public Builder seeds(String[] seeds) {
			_wikipediaArticlesImporter._seeds = seeds;

			return this;
		}

		public Builder userIds(long... userIds) {
			_wikipediaArticlesImporter._userIds = userIds;

			return this;
		}

		public Builder wikiLanguage(String wikiLanguage) {
			_wikipediaArticlesImporter._wikiLanguage = wikiLanguage;

			return this;
		}

		private Builder(WikipediaArticlesImporter googlePlacesImporter) {
			_wikipediaArticlesImporter = googlePlacesImporter;
		}

		private final WikipediaArticlesImporter _wikipediaArticlesImporter;

	}

	private WikipediaArticlesImporter(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleHelper = new JournalArticleHelper(
			journalArticleLocalService);
	}

	private WikipediaArticlesImporter(
		WikipediaArticlesImporter wikipediaArticlesImporter) {

		_groupIds = ArrayUtil.clone(wikipediaArticlesImporter._groupIds);
		_journalArticleHelper = wikipediaArticlesImporter._journalArticleHelper;
		_languageId = wikipediaArticlesImporter._languageId;
		_limit = wikipediaArticlesImporter._limit;
		_portletRequest = wikipediaArticlesImporter._portletRequest;
		_seeds = ArrayUtil.clone(wikipediaArticlesImporter._seeds);
		_userIds = ArrayUtil.clone(wikipediaArticlesImporter._userIds);
		_wikiLanguage = wikipediaArticlesImporter._wikiLanguage;
	}

	private void _addLinkedArticles(
		List<String> linkedArticles, JsonObject jsonObject) {

		for (JsonElement jsonElement : jsonObject.getAsJsonArray("links")) {
			JsonObject linkJsonObject = jsonElement.getAsJsonObject();

			JsonElement nsJsonElement = linkJsonObject.get("ns");

			if (nsJsonElement.getAsInt() == 0) {
				JsonElement linksJsonElement = linkJsonObject.get("*");

				linkedArticles.add(linksJsonElement.getAsString());
			}
		}
	}

	private String[] _getAssetTagNames(JsonObject jsonObject) {
		List<String> tags = new ArrayList<>();

		for (JsonElement jsonElement :
				jsonObject.getAsJsonArray("categories")) {

			JsonObject categoryJsonObject = jsonElement.getAsJsonObject();

			JsonElement hiddenJsonElement = categoryJsonObject.get("hidden");

			if (hiddenJsonElement == null) {
				JsonElement valueJsonElement = categoryJsonObject.get("*");

				tags.add(valueJsonElement.getAsString());
			}
		}

		return tags.toArray(new String[0]);
	}

	private String _getContent(JsonObject jsonObject) {
		JsonObject textJsonObject = jsonObject.getAsJsonObject("text");

		JsonElement jsonElement = textJsonObject.get("*");

		return jsonElement.getAsString();
	}

	private int _getLimit() {
		if (_limit != null) {
			return _limit;
		}

		return 100;
	}

	private String _getTitle(JsonObject jsonObject) {
		JsonElement jsonElement = jsonObject.get("title");

		return jsonElement.getAsString();
	}

	private URLConnection _getURLConnection(
		String article, String wikiLanguage) {

		try {
			URL url = new URL(
				StringBundler.concat(
					"https://", wikiLanguage,
					".wikipedia.org/w/api.php?action=parse&page=",
					URLCodec.encodeURL(article), "&format=json"));

			URLConnection request = url.openConnection();

			request.connect();

			return request;
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	private void _import(List<String> articles) {
		List<String> linkedArticles = new ArrayList<>();

		for (String article : articles) {
			if ((_counter + 1) > _getLimit()) {
				return;
			}

			URLConnection urlConnection = _getURLConnection(
				article, _wikiLanguage);

			JsonParser jsonParser = new JsonParser();

			try (InputStreamReader inputStreamReader = new InputStreamReader(
					(InputStream)urlConnection.getContent())) {

				JsonElement jsonElement = jsonParser.parse(inputStreamReader);

				JsonObject jsonObject = jsonElement.getAsJsonObject();

				JsonObject parseJsonObject = jsonObject.getAsJsonObject(
					"parse");

				String title = _getTitle(parseJsonObject);

				if (!_imported.contains(title)) {
					_addLinkedArticles(linkedArticles, parseJsonObject);

					_journalArticleHelper.addJournalArticle(
						_portletRequest, _userIds[_counter % _userIds.length],
						_groupIds[_counter % _groupIds.length], _languageId,
						title, _getContent(parseJsonObject),
						_getAssetTagNames(parseJsonObject));

					_counter++;

					_imported.add(title);
				}
			}
			catch (Exception exception) {
				_runtimeException.addSuppressed(exception);
			}
		}

		if (ListUtil.isNotEmpty(linkedArticles)) {
			_import(linkedArticles);
		}
	}

	private int _counter;
	private long[] _groupIds;
	private final Set<String> _imported = new HashSet<>();
	private final JournalArticleHelper _journalArticleHelper;
	private String _languageId;
	private Integer _limit;
	private PortletRequest _portletRequest;
	private RuntimeException _runtimeException;
	private String[] _seeds;
	private long[] _userIds;
	private String _wikiLanguage;

}