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
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.search.experiences.constants.SXPPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

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

	private static final Map<String, String> _cityNames = HashMapBuilder.put(
		"la", "Los Angeles"
	).put(
		"nashville", "Nashville"
	).put(
		"ny", "New York"
	).build();

	/*public static Builder builder(
		JournalArticleLocalService journalArticleLocalService) {

		return new Builder(
			new GooglePlacesImporter(journalArticleLocalService));
	}

	public int getCounter() {
		return _counter;
	}

	public void importGooglePlaces() {
		_createLocationExpandoField(
			(ThemeDisplay)_portletRequest.getAttribute(WebKeys.THEME_DISPLAY));

		_runtimeException = new RuntimeException();

		_citiesMap.forEach(
			(prefix, city) -> {
				if (_shouldImportType(ImportTypeKeys.RESTAURANTS)) {
					_import(city, _getFileName(prefix, "restaurant"));
				}

				if (_shouldImportType(ImportTypeKeys.TOURIST_ATTRACTIONS)) {
					_import(city, _getFileName(prefix, "tourist"));
				}
			});

		if (ArrayUtil.isNotEmpty(_runtimeException.getSuppressed())) {
			throw _runtimeException;
		}
	}

	public static class Builder {

		public GooglePlacesImporter build() {
			return new GooglePlacesImporter(_googlePlacesImporter);
		}

		public Builder groupIds(long... groupIds) {
			_googlePlacesImporter._groupIds = groupIds;

			return this;
		}

		public Builder languageId(String languageId) {
			_googlePlacesImporter._languageId = languageId;

			return this;
		}

		public Builder portletRequest(PortletRequest portletRequest) {
			_googlePlacesImporter._portletRequest = portletRequest;

			return this;
		}

		public Builder type(String type) {
			_googlePlacesImporter._type = type;

			return this;
		}

		public Builder userIds(long... userIds) {
			_googlePlacesImporter._userIds = userIds;

			return this;
		}

		private Builder(GooglePlacesImporter googlePlacesImporter) {
			_googlePlacesImporter = googlePlacesImporter;
		}

		private final GooglePlacesImporter _googlePlacesImporter;

	}

	private GooglePlacesImporter(GooglePlacesImporter googlePlacesImporter) {
		_groupIds = ArrayUtil.clone(googlePlacesImporter._groupIds);
		_type = googlePlacesImporter._type;
		_journalArticleHelper = googlePlacesImporter._journalArticleHelper;
		_languageId = googlePlacesImporter._languageId;
		_portletRequest = googlePlacesImporter._portletRequest;
		_userIds = ArrayUtil.clone(googlePlacesImporter._userIds);
	}

	private GooglePlacesImporter(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleHelper = new JournalArticleHelper(
			journalArticleLocalService);
	}

	private void _addLocationAttribute(
		JournalArticle journalArticle, String latitude, String longitude) {

		ExpandoBridge expandoBridge = journalArticle.getExpandoBridge();

		expandoBridge.setAttribute(
			_LOCATION_EXPANDO_FIELD,
			JSONUtil.put(
				"latitude", GetterUtil.getDouble(latitude)
			).put(
				"longitude", GetterUtil.getDouble(longitude)
			),
			false);

		_journalArticleHelper.updateJournalArticle(journalArticle);
	}

	private String[] _getAssetTagNames(JsonObject jsonObject) {
		List<String> tags = new ArrayList<>();

		JsonArray jsonArray = jsonObject.getAsJsonArray("types");

		for (JsonElement jsonElement : jsonArray) {
			tags.add(jsonElement.getAsString());
		}

		return tags.toArray(new String[0]);
	}

	private String _getFileName(String prefix, String suffix) {
		return StringBundler.concat(prefix, StringPool.DASH, suffix, ".json");
	}

	private String _getLat(JsonObject jsonObject) {
		JsonElement jsonElement = jsonObject.get("lat");

		return jsonElement.getAsString();
	}

	private String _getLng(JsonObject jsonObject) {
		JsonElement jsonElement = jsonObject.get("lng");

		return jsonElement.getAsString();
	}

	private JsonObject _getLocationJsonObject(JsonObject jsonObject) {
		JsonElement geometryJsonElement = jsonObject.get("geometry");

		JsonObject geometryJsonObject = geometryJsonElement.getAsJsonObject();

		JsonElement locationJsonElement = geometryJsonObject.get("location");

		return locationJsonElement.getAsJsonObject();
	}

	private String _getTitle(JsonObject jsonObject) {
		JsonElement jsonElement = jsonObject.get("name");

		return jsonElement.getAsString();
	}

	private void _import(String city, InputStream inputStream) {
		JsonParser jsonParser = new JsonParser();

		JsonElement jsonElement = jsonParser.parse(
			new InputStreamReader(inputStream));

		JsonObject jsonObject = jsonElement.getAsJsonObject();

		for (JsonElement resultJsonElement :
				jsonObject.getAsJsonArray("results")) {

			try {
				_import(city, resultJsonElement.getAsJsonObject());
			}
			catch (Exception exception) {
				_runtimeException.addSuppressed(exception);
			}
		}
	}

	private void _import(String city, JsonObject jsonObject) {
		JsonObject locationJsonObject = _getLocationJsonObject(jsonObject);

		String latitude = _getLat(locationJsonObject);

		String longitude = _getLng(locationJsonObject);

		JournalArticle journalArticle = _journalArticleHelper.addJournalArticle(
			_portletRequest, _userIds[_counter % _userIds.length],
			_groupIds[_counter % _groupIds.length], _languageId,
			_getTitle(jsonObject),
			StringBundler.concat(
				city, StringPool.COLON, latitude, StringPool.COMMA, longitude),
			_getAssetTagNames(jsonObject));

		_addLocationAttribute(journalArticle, latitude, longitude);

		_counter++;
	}

	private void _import(String city, String fileName) {
		if (_log.isInfoEnabled()) {
			_log.info("Importing " + fileName);
		}

		try (InputStream inputStream = getClass().getResourceAsStream(
				"dependencies/" + fileName)) {

			_import(city, inputStream);
		}
		catch (Exception exception) {
			_runtimeException.addSuppressed(exception);
		}
	}

	private boolean _shouldImportType(String type) {
		if (Validator.isNull(_type) || _type.equals(ImportTypeKeys.ALL) ||
			_type.equals(type)) {

			return true;
		}

		return false;
	}

	private static final String _LOCATION_EXPANDO_FIELD = "location";

	private static final Log _log = LogFactoryUtil.getLog(
		ImportGooglePlacesMVCActionCommand.class);

	private static final Map<String, String> _citiesMap = HashMapBuilder.put(
		"la", "Los Angeles"
	).put(
		"nashville", "Nashville"
	).put(
		"ny", "New York"
	).build();

	private int _counter;
	private long[] _groupIds;
	private final JournalArticleHelper _journalArticleHelper;
	private String _languageId;
	private PortletRequest _portletRequest;
	private RuntimeException _runtimeException;
	private String _type;
	private long[] _userIds;

}
*/
}