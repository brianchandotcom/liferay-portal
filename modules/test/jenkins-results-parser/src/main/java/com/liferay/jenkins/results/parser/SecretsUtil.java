/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil.BearerHTTPAuthorization;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
		Matcher matcher = _keyPattern.matcher(key);

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

		Vault vault = Vault.getInstance(vaultName);

		if (vault == null) {
			System.out.println(
				"Vault Not Found: " + vaultName + "/" + itemTitle + "/" +
					fieldLabel);

			return null;
		}

		Item item = vault.getItem(itemTitle);

		if (item == null) {
			System.out.println(
				"Item Not Found: " + vaultName + "/" + itemTitle + "/" +
					fieldLabel);

			return null;
		}

		Field field = item.getField(fieldLabel);

		if (field == null) {
			System.out.println(
				"Field Not Found: " + vaultName + "/" + itemTitle + "/" +
					fieldLabel);

			return null;
		}

		return field.value;
	}

	public static boolean isSecretProperty(String value) {
		if (value == null) {
			return false;
		}

		Matcher matcher = _secretPropertyPattern.matcher(value);

		return matcher.matches();
	}

	private static JSONArray _toJSONArray(String path) {
		if (_bearerHTTPAuthorization == null) {
			return new JSONArray();
		}

		try {
			return JenkinsResultsParserUtil.toJSONArray(
				_SERVER_URL + path, null, _bearerHTTPAuthorization);
		}
		catch (IOException ioException) {
			System.out.println(ioException.getMessage());

			ioException.printStackTrace();

			return new JSONArray();
		}
	}

	private static JSONObject _toJSONObject(String path) {
		if (_bearerHTTPAuthorization == null) {
			return new JSONObject();
		}

		try {
			return JenkinsResultsParserUtil.toJSONObject(
				_SERVER_URL + path, null, _bearerHTTPAuthorization);
		}
		catch (IOException ioException) {
			System.out.println(ioException.getMessage());

			ioException.printStackTrace();

			return null;
		}
	}

	private static final String _SERVER_URL = "https://1password.liferay.com";

	private static final BearerHTTPAuthorization _bearerHTTPAuthorization;
	private static final Pattern _keyPattern = Pattern.compile(
		"(secret\\:)?(?<vaultName>[^\\/]*)\\/" +
			"(?<itemTitle>[^\\/]*)\\/(?<fieldLabel>.*)");
	private static final Pattern _secretPropertyPattern = Pattern.compile(
		"secret\\:(?<key>.*)");

	static {
		String token = null;

		try {
			token = JenkinsResultsParserUtil.read(
				new File(
					System.getProperty("user.home") + "/.1password.connect"));

			token = token.trim();
		}
		catch (IOException ioException) {
			token = null;

			System.out.println(
				"Unable to load 1Password connect bearer token.");
		}

		if (token != null) {
			JenkinsResultsParserUtil.addRedactToken(token);

			_bearerHTTPAuthorization = new BearerHTTPAuthorization(token);
		}
		else {
			_bearerHTTPAuthorization = null;
		}
	}

	private static class Field {

		public Field(String label, String value) {
			this.label = label;
			this.value = value;

			if (!JenkinsResultsParserUtil.isNullOrEmpty(value)) {
				JenkinsResultsParserUtil.addRedactToken(value);
			}
		}

		public final String label;
		public final String value;

	}

	private static class Item {

		public Item(String id, String title, Vault vault) {
			this.id = id;
			this.title = title;

			_vault = vault;
		}

		public Field getField(String label) {
			if (_fields == null) {
				init();
			}

			for (Field field : _fields) {
				if (Objects.equals(field.label, label)) {
					return field;
				}
			}

			if (_linkedItem != null) {
				return _linkedItem.getField(label);
			}

			return null;
		}

		public void init() {
			JSONObject itemJSONObject = _toJSONObject(
				JenkinsResultsParserUtil.combine(
					"/v1/vaults/", _vault.id, "/items/", id));

			JSONArray fieldsJSONArray = itemJSONObject.getJSONArray("fields");

			_fields = new ArrayList<>(fieldsJSONArray.length());

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

					_fields.add(
						new Field(
							fieldJSONObject.getString("label"),
							fieldJSONObject.getString("value")));
				}
				catch (JSONException jsonException) {
					System.err.println(jsonException.toString());
					System.out.println(fieldJSONObject.toString(2));
				}
			}
		}

		public final String id;
		public final String title;

		private List<Field> _fields;
		private Item _linkedItem;
		private final Vault _vault;

	}

	private static class Vault {

		public static Vault getInstance(String name) {
			return _vaultsMap.get(name);
		}

		public Item getItem(String title) {
			if (_items == null) {
				init();
			}

			for (Item item : _items) {
				if (Objects.equals(item.title, title)) {
					return item;
				}
			}

			return null;
		}

		public void init() {
			JSONArray itemsJSONArray = _toJSONArray(
				JenkinsResultsParserUtil.combine("/v1/vaults/", id, "/items"));

			_items = new ArrayList<>(itemsJSONArray.length());

			for (int i = 0; i < itemsJSONArray.length(); i++) {
				JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

				_items.add(
					new Item(
						itemJSONObject.getString("id"),
						itemJSONObject.getString("title"), this));
			}
		}

		public final String id;
		public final String name;

		private Vault(String id, String name) {
			this.id = id;
			this.name = name;
		}

		private static final Map<String, Vault> _vaultsMap = new HashMap<>();

		static {
			JSONArray vaultsJSONArray = _toJSONArray("/v1/vaults");

			for (int i = 0; i < vaultsJSONArray.length(); i++) {
				JSONObject vaultJSONObject = vaultsJSONArray.getJSONObject(i);

				Vault vault = new Vault(
					vaultJSONObject.getString("id"),
					vaultJSONObject.getString("name"));

				_vaultsMap.put(vault.name, vault);
			}
		}

		private List<Item> _items;

	}

}