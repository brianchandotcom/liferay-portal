/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil.BearerHTTPAuthorization;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil.HTTPAuthorization;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public abstract class SecretsUtil {

	public static String getSecret(String key) {
		if (!_isSecretsConfigured()) {
			return key;
		}

		Matcher matcher = _secretReferencePattern.matcher(key);

		if (matcher.matches()) {
			String secret = getSecret(
				matcher.group("vaultName"), matcher.group("itemTitle"),
				matcher.group("fieldLabel"));

			if (!JenkinsResultsParserUtil.isNullOrEmpty(secret)) {
				return secret;
			}
		}

		return key;
	}

	public static String getSecret(
		String vaultName, String itemTitle, String fieldLabel) {

		if (!_isSecretsConfigured()) {
			return null;
		}

		Vault vault = Vault.getInstance(vaultName);

		if (vault == null) {
			System.out.println("Vault Not Found: " + vaultName);

			return null;
		}

		Item item = vault.getItem(itemTitle);

		if (item == null) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Item Not Found: ", vaultName, "/", itemTitle));

			return null;
		}

		ItemField itemField = item.getItemField(fieldLabel);

		if (itemField != null) {
			return itemField.getValue();
		}

		ItemFile itemFile = item.getItemFile(fieldLabel);

		if (itemFile != null) {
			return itemFile.getValue();
		}

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Field Not Found: op://", vaultName, "/", itemTitle, "/",
				fieldLabel));

		return null;
	}

	public static boolean isSecretProperty(String value) {
		if (value == null) {
			return false;
		}

		Matcher matcher = _secretReferencePattern.matcher(value);

		return matcher.matches();
	}

	private static synchronized String _getAccessToken() {
		if (_accessToken != null) {
			return _accessToken;
		}

		String accessToken;

		try {
			String accessTokenKey = JenkinsResultsParserUtil.getBuildProperty(
				"one.password.access.token.key");

			if (!JenkinsResultsParserUtil.isNullOrEmpty(accessTokenKey)) {
				Process process = JenkinsResultsParserUtil.executeBashCommands(
					new File("."), true, false, 1000 * 60 * 60,
					JenkinsResultsParserUtil.combine(
						"aws ssm get-parameter --name \"", accessTokenKey,
						"\" --with-decryption | jq -r .Parameter.Value"));

				accessToken = JenkinsResultsParserUtil.readInputStream(
					process.getInputStream());

				accessToken = accessToken.replace(
					"Finished executing Bash commands.", "");

				accessToken = accessToken.trim();
			}
			else {
				accessToken = "";
			}
		}
		catch (IOException | TimeoutException exception) {
			accessToken = "";
		}

		if (!JenkinsResultsParserUtil.isNullOrEmpty(accessToken)) {
			JenkinsResultsParserUtil.addRedactToken(accessToken);
		}

		_accessToken = accessToken;

		return _accessToken;
	}

	private static synchronized String _getConnectURL() {
		if (_connectURL != null) {
			return _connectURL;
		}

		String connectURL;

		try {
			connectURL = JenkinsResultsParserUtil.getBuildProperty(
				"one.password.connect.url");

			if (!JenkinsResultsParserUtil.isURL(connectURL)) {
				connectURL = "";
			}
		}
		catch (IOException ioException) {
			connectURL = "";
		}

		_connectURL = connectURL;

		return _connectURL;
	}

	private static synchronized HTTPAuthorization _getHTTPAuthorization() {
		if (_httpAuthorization != null) {
			return _httpAuthorization;
		}

		String accessToken = _getAccessToken();

		if (JenkinsResultsParserUtil.isNullOrEmpty(accessToken)) {
			return null;
		}

		_httpAuthorization = new BearerHTTPAuthorization(accessToken);

		return _httpAuthorization;
	}

	private static boolean _isSecretsConfigured() {
		if (JenkinsResultsParserUtil.isNullOrEmpty(_getAccessToken()) ||
			JenkinsResultsParserUtil.isNullOrEmpty(_getConnectURL()) ||
			(_getHTTPAuthorization() == null)) {

			return false;
		}

		return true;
	}

	private static JSONArray _toJSONArray(String path) {
		if (!_isSecretsConfigured()) {
			return new JSONArray();
		}

		try {
			return JenkinsResultsParserUtil.toJSONArray(
				_getConnectURL() + path, null, _getHTTPAuthorization());
		}
		catch (IOException ioException) {
			System.out.println(ioException.getMessage());

			ioException.printStackTrace();

			return new JSONArray();
		}
	}

	private static JSONObject _toJSONObject(String path) {
		if (!_isSecretsConfigured()) {
			return new JSONObject();
		}

		try {
			return JenkinsResultsParserUtil.toJSONObject(
				_getConnectURL() + path, null, _getHTTPAuthorization());
		}
		catch (IOException ioException) {
			System.out.println(ioException.getMessage());

			ioException.printStackTrace();

			return null;
		}
	}

	private static String _toString(String path) {
		if (_httpAuthorization == null) {
			return "";
		}

		try {
			return JenkinsResultsParserUtil.toString(
				_getConnectURL() + path, null, _getHTTPAuthorization());
		}
		catch (IOException ioException) {
			System.out.println(ioException.getMessage());

			ioException.printStackTrace();

			return "";
		}
	}

	private static String _accessToken;
	private static String _connectURL;
	private static BearerHTTPAuthorization _httpAuthorization;
	private static final Pattern _secretReferencePattern = Pattern.compile(
		"op://(?<vaultName>[^/]*)/(?<itemTitle>[^/]*)/(?<fieldLabel>.*)");

	private static class Item {

		public String getId() {
			return _id;
		}

		public ItemField getItemField(String label) {
			if (_itemFields == null) {
				_init();
			}

			for (ItemField itemField : _itemFields) {
				if (Objects.equals(itemField.getId(), label) ||
					Objects.equals(itemField.getLabel(), label)) {

					return itemField;
				}
			}

			if (_linkedItem != null) {
				return _linkedItem.getItemField(label);
			}

			return null;
		}

		public ItemFile getItemFile(String fileName) {
			if (_itemFiles == null) {
				_init();
			}

			for (ItemFile itemFile : _itemFiles) {
				if (Objects.equals(itemFile.getName(), fileName)) {
					return itemFile;
				}
			}

			if (_linkedItem != null) {
				return _linkedItem.getItemFile(fileName);
			}

			return null;
		}

		public String getTitle() {
			return _title;
		}

		private Item(String id, String title, Vault vault) {
			_id = id;
			_title = title;
			_vault = vault;
		}

		private void _init() {
			JSONObject itemJSONObject = _toJSONObject(
				JenkinsResultsParserUtil.combine(
					"/v1/vaults/", _vault.getId(), "/items/", getId()));

			JSONArray fieldsJSONArray = itemJSONObject.getJSONArray("fields");

			_itemFields = new ArrayList<>(fieldsJSONArray.length());

			for (int i = 0; i < fieldsJSONArray.length(); i++) {
				JSONObject fieldJSONObject = fieldsJSONArray.getJSONObject(i);

				try {
					JSONObject sectionJSONObject =
						fieldJSONObject.optJSONObject("section");

					if (sectionJSONObject != null) {
						if (Objects.equals(
								sectionJSONObject.optString("label"),
								"Related Items")) {

							_linkedItem = _vault.getItem(
								fieldJSONObject.getString("label"));
						}

						if (_linkedItem != null) {
							continue;
						}
					}

					if (!fieldJSONObject.has("value")) {
						continue;
					}

					_itemFields.add(
						new ItemField(
							fieldJSONObject.getString("id"),
							fieldJSONObject.getString("label"),
							fieldJSONObject.getString("value")));
				}
				catch (JSONException jsonException) {
					System.err.println(jsonException.toString());
					System.out.println(fieldJSONObject.toString(2));
				}
			}

			JSONArray filesJSONArray = itemJSONObject.optJSONArray(
				"files", new JSONArray());

			_itemFiles = new ArrayList<>(filesJSONArray.length());

			for (int i = 0; i < filesJSONArray.length(); i++) {
				JSONObject fileJSONObject = filesJSONArray.getJSONObject(i);

				try {
					JSONObject sectionJSONObject = fileJSONObject.optJSONObject(
						"section");

					if (sectionJSONObject != null) {
						if (Objects.equals(
								sectionJSONObject.optString("label"),
								"Related Items")) {

							_linkedItem = _vault.getItem(
								fileJSONObject.getString("label"));
						}

						if (_linkedItem != null) {
							continue;
						}
					}

					if (!fileJSONObject.has("content_path")) {
						continue;
					}

					_itemFiles.add(
						new ItemFile(
							fileJSONObject.getString("content_path"),
							fileJSONObject.getString("name")));
				}
				catch (JSONException jsonException) {
					System.err.println(jsonException.toString());
					System.out.println(fileJSONObject.toString(2));
				}
			}
		}

		private final String _id;
		private List<ItemField> _itemFields;
		private List<ItemFile> _itemFiles;
		private Item _linkedItem;
		private final String _title;
		private final Vault _vault;

	}

	private static class ItemField {

		public String getId() {
			return _id;
		}

		public String getLabel() {
			return _label;
		}

		public String getValue() {
			return _value;
		}

		private ItemField(String id, String label, String value) {
			_id = id;
			_label = label;
			_value = value;

			if (!JenkinsResultsParserUtil.isNullOrEmpty(value)) {
				JenkinsResultsParserUtil.addRedactToken(value);
			}
		}

		private final String _id;
		private final String _label;
		private final String _value;

	}

	private static class ItemFile {

		public ItemFile(String contentPath, String name) {
			_contentPath = contentPath;
			_name = name;
		}

		public String getName() {
			return _name;
		}

		public String getValue() {
			if (_value != null) {
				return _value;
			}

			String value = _toString(_contentPath);

			value = value.trim();

			if (!JenkinsResultsParserUtil.isNullOrEmpty(value)) {
				JenkinsResultsParserUtil.addRedactToken(value);
			}

			_value = value;

			return _value;
		}

		private final String _contentPath;
		private final String _name;
		private String _value;

	}

	private static class Vault {

		public static Vault getInstance(String name) {
			return _vaultsMap.get(name);
		}

		public String getId() {
			return _id;
		}

		public Item getItem(String title) {
			if (_items == null) {
				_init();
			}

			for (Item item : _items) {
				if (Objects.equals(item.getId(), title) ||
					Objects.equals(item.getTitle(), title)) {

					return item;
				}
			}

			return null;
		}

		public String getName() {
			return _name;
		}

		private Vault(String id, String name) {
			_id = id;
			_name = name;
		}

		private void _init() {
			JSONArray itemsJSONArray = _toJSONArray(
				JenkinsResultsParserUtil.combine(
					"/v1/vaults/", getId(), "/items"));

			_items = new ArrayList<>(itemsJSONArray.length());

			for (int i = 0; i < itemsJSONArray.length(); i++) {
				JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

				_items.add(
					new Item(
						itemJSONObject.getString("id"),
						itemJSONObject.getString("title"), this));
			}
		}

		private static final Map<String, Vault> _vaultsMap = new HashMap<>();

		static {
			JSONArray vaultsJSONArray = _toJSONArray("/v1/vaults");

			for (int i = 0; i < vaultsJSONArray.length(); i++) {
				JSONObject vaultJSONObject = vaultsJSONArray.getJSONObject(i);

				Vault vault = new Vault(
					vaultJSONObject.getString("id"),
					vaultJSONObject.getString("name"));

				_vaultsMap.put(vault.getName(), vault);
			}
		}

		private final String _id;
		private List<Item> _items;
		private final String _name;

	}

}