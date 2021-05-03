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

package com.liferay.portal.search.tuning.blueprints.bulkloader.internal.portlet.action;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.tuning.blueprints.bulkloader.internal.constants.BulkloaderPortletKeys;
import com.liferay.portal.search.tuning.blueprints.bulkloader.internal.constants.ImportTypeKeys;
import com.liferay.portal.search.tuning.blueprints.bulkloader.internal.constants.MVCActionCommandNames;
import com.liferay.portal.search.tuning.blueprints.bulkloader.internal.constants.PlacesConstants;

import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + BulkloaderPortletKeys.BULK_LOADER,
		"mvc.command.name=" + MVCActionCommandNames.IMPORT
	},
	service = MVCActionCommand.class
)
public class ImportMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String importType = ParamUtil.getString(actionRequest, "type");

		String userString = ParamUtil.getString(actionRequest, "userIds");

		List<Long> userIds = _toIdList(userString);

		String groupString = ParamUtil.getString(actionRequest, "groupIds");

		List<Long> groupIds = _toIdList(groupString);

		String languageId = ParamUtil.getString(
			actionRequest, "languageId", "en_US");

		// Disable link validation

		ExportImportThreadLocal.setPortletImportInProcess(true);

		if (importType.equals(ImportTypeKeys.WIKIPEDIA_ARTICLES)) {
			String wikiArticles = ParamUtil.getString(
				actionRequest, "wikiArticles");

			List<String> articleList = _toStringList(wikiArticles);

			if (articleList.isEmpty()) {
				if (_log.isInfoEnabled()) {
					_log.info("Root article not given");
				}

				return;
			}

			List<String> importedTitles = new ArrayList<>();

			int count = ParamUtil.getInteger(actionRequest, "count", 100);

			_importWikipediaArticles(
				actionRequest, articleList, userIds, groupIds, languageId, 0,
				count, importedTitles);
		}
		else {
			try {
				_createLocationExpandoField(actionRequest);
			}
			catch (PortalException portalException) {
				_log.error(portalException.getMessage(), portalException);

				return;
			}

			_importGooglePlaceArticles(
				actionRequest, userIds, groupIds, languageId, importType);
		}

		ExportImportThreadLocal.setPortletImportInProcess(false);

		if (_log.isInfoEnabled()) {
			_log.info("Finished data import");
		}
	}

	private JournalArticle _addJournalArticle(
			ActionRequest actionRequest, long userId, long groupId,
			String languageId, String title, String content,
			String[] assetTagNames)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info("Adding journal article " + title);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalArticle.class.getName(), actionRequest);

		serviceContext.setAssetTagNames(assetTagNames);
		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		Map<Locale, String> titleMap = HashMapBuilder.put(
			locale, title
		).build();

		// Respect ES 32KB field size limit (breaks the HTML).

		if (content.length() > 32000) {
			content = content.substring(0, 32000);
		}

		Map<Locale, String> descriptionMap = HashMapBuilder.put(
			locale, _getDescription(content)
		).build();

		String instanceId = _generateInstanceId();

		StringBundler sb = new StringBundler(13);

		sb.append("<root available-locales=\"en_US\" default-locale=\"");
		sb.append(languageId);
		sb.append("\">");
		sb.append("<dynamic-element name=\"content\" type=\"text_area\" ");
		sb.append("index-type=\"text\" instance-id=\"");
		sb.append(instanceId);
		sb.append("\">");
		sb.append("<dynamic-content language-id=\"");
		sb.append(languageId);
		sb.append("\"><![CDATA[");
		sb.append(content);
		sb.append("]]></dynamic-content></dynamic-element>");
		sb.append("</root>");

		return _journalArticleLocalService.addArticle(
			userId, groupId, 0, titleMap, descriptionMap, sb.toString(),
			"BASIC-WEB-CONTENT", "BASIC-WEB-CONTENT", serviceContext);
	}

	private void _addLocation(
		JournalArticle journalArticle, String lat, String lng) {

		JSONObject jsonObject = JSONUtil.put(
			"latitude", GetterUtil.getDouble(lat));

		jsonObject.put("longitude", GetterUtil.getDouble(lng));

		ExpandoBridge expandoBridge = journalArticle.getExpandoBridge();

		expandoBridge.setAttribute(_LOCATION_EXPANDO_FIELD, jsonObject, false);

		_journalArticleLocalService.updateJournalArticle(journalArticle);
	}

	private boolean _checkTagValue(String value) throws Exception {
		if (Validator.isBlank(value)) {
			return false;
		}

		char[] wordCharArray = value.toCharArray();

		for (char c : wordCharArray) {
			for (char invalidChar : _INVALID_CHARACTERS) {
				if (c == invalidChar) {
					return false;
				}
			}
		}

		return true;
	}

	private String _cleanTagValue(String s) {
		return StringUtil.replace(s, CharPool.UNDERLINE, CharPool.SPACE);
	}

	private void _createLocationExpandoField(ActionRequest actionRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			themeDisplay.getCompanyId(), JournalArticle.class.getName());

		if (!expandoBridge.hasAttribute(_LOCATION_EXPANDO_FIELD)) {
			boolean secure = false;

			JSONObject jsonObject = JSONUtil.put("latitude", 0D);

			jsonObject.put("longitude", 0D);

			expandoBridge.addAttribute(
				_LOCATION_EXPANDO_FIELD, ExpandoColumnConstants.GEOLOCATION,
				jsonObject, secure);

			UnicodeProperties unicodeProperties =
				expandoBridge.getAttributeProperties(_LOCATION_EXPANDO_FIELD);

			unicodeProperties.setProperty(
				ExpandoColumnConstants.INDEX_TYPE,
				String.valueOf(ExpandoColumnConstants.INDEX_TYPE_KEYWORD));

			unicodeProperties.setProperty(
				ExpandoColumnConstants.PROPERTY_LOCALIZE_FIELD_NAME, "false");

			expandoBridge.setAttributeProperties(
				_LOCATION_EXPANDO_FIELD, unicodeProperties);
		}
	}

	private String _generateInstanceId() {
		StringBuilder instanceId = new StringBuilder(8);

		String key = PwdGenerator.KEY1 + PwdGenerator.KEY2 + PwdGenerator.KEY3;

		for (int i = 0; i < 8; i++) {
			int pos = (int)Math.floor(Math.random() * key.length());

			instanceId.append(key.charAt(pos));
		}

		return instanceId.toString();
	}

	private String _getDescription(String s) {
		s = HtmlUtil.stripHtml(s);

		if (s.length() > 500) {
			return s.substring(0, 500);
		}

		return s;
	}

	private void _importGooglePlaceArticles(
			ActionRequest actionRequest, List<Long> userIds,
			List<Long> groupIds, String languageId, String importType)
		throws Exception {

		int groupIdx = 0;
		int userIdx = 0;

		JsonParser parser = new JsonParser();

		Set<String> fileNames = PlacesConstants.fileNameToCityMap.keySet();

		for (String fileName : fileNames) {
			if (importType.equals(ImportTypeKeys.RESTAURANTS) &&
				!fileName.endsWith("-restaurant.json")) {

				continue;
			}

			if (importType.equals(ImportTypeKeys.TOURIST_ATTRACTIONS) &&
				!fileName.endsWith("-tourist.json")) {

				continue;
			}

			if (_log.isInfoEnabled()) {
				_log.info("Importing " + fileName);
			}

			try (InputStream inputStream = getClass().getResourceAsStream(
					fileName)) {

				JsonElement rootJsonElement = parser.parse(
					new InputStreamReader(inputStream));

				JsonObject rootJsonObject = rootJsonElement.getAsJsonObject();

				JsonArray resultsJsonArray = rootJsonObject.getAsJsonArray(
					"results");

				for (int i = 0; i < resultsJsonArray.size(); i++) {
					JsonElement resultJsonElement = resultsJsonArray.get(i);

					JsonObject resultJsonObject =
						resultJsonElement.getAsJsonObject();

					// Content / City & geo

					JsonElement geometryJsonElement = resultJsonObject.get(
						"geometry");

					JsonObject geometryJsonObject =
						geometryJsonElement.getAsJsonObject();

					JsonElement locationJsonElement = geometryJsonObject.get(
						"location");

					JsonObject locationJsonObject =
						locationJsonElement.getAsJsonObject();

					JsonElement latJsonElement = locationJsonObject.get("lat");

					String lat = latJsonElement.getAsString();

					JsonElement lngJsonElement = locationJsonObject.get("lng");

					String lng = lngJsonElement.getAsString();

					String city = PlacesConstants.fileNameToCityMap.get(
						fileName);

					StringBundler sb = new StringBundler(5);

					sb.append(city);
					sb.append(":");
					sb.append(lat);
					sb.append(",");
					sb.append(lng);

					// Title / Name

					JsonElement titleJsonElement = resultJsonObject.get("name");

					String title = titleJsonElement.getAsString();

					// Categories => tags

					List<String> tags = new ArrayList<>();

					JsonArray types = resultJsonObject.getAsJsonArray("types");

					for (int j = 0; j < types.size(); j++) {
						JsonElement typeJsonElement = types.get(j);

						String type = typeJsonElement.getAsString();

						if (_checkTagValue(type)) {

							// Replace underscores

							type = _cleanTagValue(type);

							tags.add(type);
						}
					}

					Stream<String> tagsStream = tags.stream();

					String[] assetTagNames = tagsStream.toArray(String[]::new);

					if (userIdx == userIds.size()) {
						userIdx = 0;
					}

					long userId = userIds.get(userIdx++);

					if (groupIdx == groupIds.size()) {
						groupIdx = 0;
					}

					long groupId = groupIds.get(groupIdx++);

					JournalArticle journalArticle = _addJournalArticle(
						actionRequest, userId, groupId, languageId, title,
						sb.toString(), assetTagNames);

					_addLocation(journalArticle, lat, lng);
				}
			}
			catch (Exception exception) {
				_log.error(exception.getMessage(), exception);
			}
		}
	}

	private void _importWikipediaArticles(
			ActionRequest actionRequest, List<String> articleList,
			List<Long> userIds, List<Long> groupIds, String languageId,
			int counter, int count, List<String> importedTitles)
		throws Exception {

		List<String> articleLinks = new ArrayList<>();

		int groupIdx = 0;
		int userIdx = 0;

		for (String article : articleList) {
			if (counter >= count) {
				return;
			}

			String apiURL =
				_API_URL + URLCodec.encodeURL(article) + "&format=json";

			if (_log.isInfoEnabled()) {
				_log.info("Calling:" + apiURL);
			}

			URL url = new URL(apiURL);

			URLConnection request = url.openConnection();

			request.connect();

			JsonParser parser = new JsonParser();

			try {
				JsonElement rootJsonElement = parser.parse(
					new InputStreamReader((InputStream)request.getContent()));

				JsonObject rootJsonObject = rootJsonElement.getAsJsonObject();

				JsonObject parseJsonObject = rootJsonObject.getAsJsonObject(
					"parse");

				// Title

				JsonElement titleJsonElement = parseJsonObject.get("title");

				String title = titleJsonElement.getAsString();

				if (importedTitles.contains(title)) {
					continue;
				}

				// Text

				JsonObject textJsonObject = parseJsonObject.getAsJsonObject(
					"text");

				JsonElement contentJsonElement = textJsonObject.get("*");

				String content = contentJsonElement.getAsString();

				// Categories => tags

				List<String> tags = new ArrayList<>();

				JsonArray categoriesJsonArray = parseJsonObject.getAsJsonArray(
					"categories");

				for (int i = 0; i < categoriesJsonArray.size(); i++) {
					JsonElement categoryJsonElement = categoriesJsonArray.get(
						i);

					JsonObject categoryJsonObject =
						categoryJsonElement.getAsJsonObject();

					JsonElement hidden = categoryJsonObject.get("hidden");

					if (hidden != null) {
						continue;
					}

					JsonElement valueJsonElement = categoryJsonObject.get("*");

					String value = valueJsonElement.getAsString();

					if (_checkTagValue(value)) {
						value = _cleanTagValue(value);

						tags.add(value);
					}
				}

				Stream<String> tagsStream = tags.stream();

				String[] assetTagNames = tagsStream.toArray(String[]::new);

				// Links

				JsonArray linksJsonArray = parseJsonObject.getAsJsonArray(
					"links");

				for (int i = 0; i < linksJsonArray.size(); i++) {
					JsonElement linkJsonElement = linksJsonArray.get(i);

					JsonObject link = linkJsonElement.getAsJsonObject();

					JsonElement nsJsonElement = link.get("ns");

					int ns = nsJsonElement.getAsInt();

					if (ns != 0) {
						continue;
					}

					JsonElement linksJsonElement = link.get("*");

					articleLinks.add(linksJsonElement.getAsString());
				}

				if (userIdx == userIds.size()) {
					userIdx = 0;
				}

				long userId = userIds.get(userIdx++);

				if (groupIdx == groupIds.size()) {
					groupIdx = 0;
				}

				long groupId = groupIds.get(groupIdx++);

				_addJournalArticle(
					actionRequest, userId, groupId, languageId, title, content,
					assetTagNames);

				importedTitles.add(title);
			}
			catch (Exception exception) {
				_log.error(exception.getMessage(), exception);
			}

			counter++;
		}

		if (articleLinks.isEmpty()) {
			return;
		}

		_importWikipediaArticles(
			actionRequest, articleLinks, userIds, groupIds, languageId, counter,
			count, importedTitles);
	}

	private List<Long> _toIdList(String ids) throws Exception {
		String[] arr = ids.split(",");

		List<Long> values = new ArrayList<>();

		for (String s : arr) {
			s = StringUtil.trim(s);

			values.add(Long.valueOf(s));
		}

		return values;
	}

	private List<String> _toStringList(String ids) throws Exception {
		String[] arr = ids.split(",");
		List<String> values = new ArrayList<>();

		for (String s : arr) {
			s = StringUtil.trim(s);

			values.add(s);
		}

		return values;
	}

	private static final String _API_URL =
		"https://en.wikipedia.org/w/api.php?action=parse&page=";

	private static final char[] _INVALID_CHARACTERS = {
		CharPool.AMPERSAND, CharPool.APOSTROPHE, CharPool.AT,
		CharPool.BACK_SLASH, CharPool.CLOSE_BRACKET, CharPool.CLOSE_CURLY_BRACE,
		CharPool.COLON, CharPool.COMMA, CharPool.EQUAL, CharPool.GREATER_THAN,
		CharPool.FORWARD_SLASH, CharPool.LESS_THAN, CharPool.NEW_LINE,
		CharPool.OPEN_BRACKET, CharPool.OPEN_CURLY_BRACE, CharPool.PERCENT,
		CharPool.PIPE, CharPool.PLUS, CharPool.POUND, CharPool.PRIME,
		CharPool.QUESTION, CharPool.QUOTE, CharPool.RETURN, CharPool.SEMICOLON,
		CharPool.SLASH, CharPool.STAR, CharPool.TILDE
	};

	private static final String _LOCATION_EXPANDO_FIELD = "location";

	private static final Log _log = LogFactoryUtil.getLog(
		ImportMVCActionCommand.class);

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

}