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

package com.liferay.search.experiences.web.internal.power.tools.portlet.action;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.search.experiences.constants.SXPPortletKeys;

import java.util.Map;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 * @author André de Oliveira
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SXPPortletKeys.SXP_POWER_TOOLS,
		"mvc.command.name=/sxp_power_tools/import_google_places"
	},
	service = MVCActionCommand.class
)
public class ImportGooglePlacesMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		_setUpExpandoBridge(actionRequest);

		for (Map.Entry<String, String> entry : _cityNames.entrySet()) {
			String cityCode = entry.getKey();
			String cityName = entry.getValue();

			_import(
				actionRequest, entry.getKey(), entry.getValue(), "restaurant");
			_import(actionRequest, entry.getKey(), entry.getValue(), "tourist");
		}
	}

	private String[] _cleanAssetTagNames(String[] assetTagNames) {
		return Stream.of(
			assetTagNames
		).map(
			assetTagName -> StringUtil.removeChars(
				assetTagName, _INVALID_CHARACTERS)
		).map(
			assetTagName -> StringUtil.replace(
				assetTagName, CharPool.UNDERLINE, CharPool.SPACE)
		).toArray(
			String[]::new
		);
	}

	private String _createArticleXML(String content, String languageId) {
		StringBundler sb = new StringBundler(13);

		sb.append("<root available-locales=\"en_US\" default-locale=\"");
		sb.append(languageId);
		sb.append("\">");
		sb.append("<dynamic-element name=\"content\" type=\"text_area\" ");
		sb.append("index-type=\"text\" instance-id=\"");
		sb.append(_generateInstanceId());
		sb.append("\">");
		sb.append("<dynamic-content language-id=\"");
		sb.append(languageId);
		sb.append("\"><![CDATA[");
		sb.append(
			StringUtil.shorten(
				content, _ELASTICSEARCH_FIELD_SIZE_LIMIT, "</html>"));
		sb.append("]]></dynamic-content></dynamic-element>");
		sb.append("</root>");

		return sb.toString();
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

	private void _import(
			ActionRequest actionRequest, String cityName, JSONObject jsonObject)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONObject geometryJSONObject = jsonObject.getJSONObject("geometry");

		JSONObject locationJSONObject = geometryJSONObject.getJSONObject(
			"location");

		double latitude = locationJSONObject.getDouble("lat");
		double longitude = locationJSONObject.getDouble("lng");

		String[] assetTagNames = _cleanAssetTagNames(
			JSONUtil.toStringArray(jsonObject.getJSONArray("types")));

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalArticle.class.getName(), actionRequest);

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		String content = StringBundler.concat(
			cityName, StringPool.COLON, latitude, StringPool.COMMA, longitude);

		JournalArticle journalArticle = _journalArticleLocalService.addArticle(
			themeDisplay.getUserId(), themeDisplay.getScopeGroupId(), 0,
			HashMapBuilder.put(
				themeDisplay.getLocale(), jsonObject.getString("name")
			).build(),
			HashMapBuilder.put(
				themeDisplay.getLocale(),
				StringUtil.shorten(HtmlUtil.stripHtml(content), 500)
			).build(),
			_createArticleXML(content, themeDisplay.getLanguageId()),
			"BASIC-WEB-CONTENT", "BASIC-WEB-CONTENT", serviceContext);

		ExpandoBridge expandoBridge = journalArticle.getExpandoBridge();

		expandoBridge.setAttribute(
			"location",
			JSONUtil.put(
				"latitude", latitude
			).put(
				"longitude", longitude
			),
			false);

		_journalArticleLocalService.updateJournalArticle(journalArticle);
	}

	private void _import(
			ActionRequest actionRequest, String cityCode, String cityName,
			String type)
		throws Exception {

		JSONObject jsonObject = _read(cityCode + "-" + type);

		JSONArray jsonArray = jsonObject.getJSONArray("results");

		for (int i = 0; i < jsonArray.length(); i++) {
			_import(actionRequest, cityName, jsonArray.getJSONObject(i));
		}
	}

	private JSONObject _read(String fileName) throws Exception {
		return _jsonFactory.createJSONObject(
			new String(
				FileUtil.getBytes(
					getClass(),
					"dependencies/google/places/" + fileName + ".json")));
	}

	private void _setUpExpandoBridge(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			themeDisplay.getCompanyId(), JournalArticle.class.getName());

		if (!expandoBridge.hasAttribute("location")) {
			expandoBridge.addAttribute(
				"location", ExpandoColumnConstants.GEOLOCATION,
				JSONUtil.put(
					"latitude", 0
				).put(
					"longitude", 0
				),
				false);
		}

		UnicodeProperties unicodeProperties =
			expandoBridge.getAttributeProperties("location");

		unicodeProperties.setProperty(
			ExpandoColumnConstants.INDEX_TYPE,
			String.valueOf(ExpandoColumnConstants.INDEX_TYPE_KEYWORD));
		unicodeProperties.setProperty(
			ExpandoColumnConstants.PROPERTY_LOCALIZE_FIELD_NAME, "false");

		expandoBridge.setAttributeProperties("location", unicodeProperties);
	}

	private static final int _ELASTICSEARCH_FIELD_SIZE_LIMIT = 32000;

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

	private static final Map<String, String> _cityNames = HashMapBuilder.put(
		"la", "Los Angeles"
	).put(
		"nashville", "Nashville"
	).put(
		"ny", "New York"
	).build();

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JSONFactory _jsonFactory;

}