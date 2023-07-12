/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
import java.util.Properties;
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
			return null;
		}

		Item item = vault.getItem(itemTitle);

		if (item == null) {
			return null;
		}

		Field field = item.getField(fieldLabel);

		if (field == null) {
			return null;
		}

		return field.value;
	}

	public static Properties updateSecretProperties(Properties properties) {
		Properties updatedProperties = new Properties();

		for (Object propertyKey : properties.keySet()) {
			String propertyValue = (String)properties.get(propertyKey);

			Matcher matcher = _secretPropertyPattern.matcher(propertyValue);

			if (matcher.matches()) {
				String secretValue = getSecret(matcher.group("key"));

				if (!Objects.equals(secretValue, propertyValue)) {
					updatedProperties.put(propertyKey, secretValue);
				}
			}
		}

		properties.putAll(updatedProperties);

		return properties;
	}

	private static JSONArray _toJSONArray(String path) {
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

	private static final String _SERVER_URL =
		"http://1password.liferay.com:8080";

	private static final BearerHTTPAuthorization _bearerHTTPAuthorization;
	private static final Pattern _keyPattern = Pattern.compile(
		"(?<vaultName>[^\\/]*)\\/(?<itemTitle>[^\\/]*)\\/(?<fieldLabel>.*)");
	private static final Pattern _secretPropertyPattern = Pattern.compile(
		"secret\\:(?<key>.*)");

	static {
		try {
			String token = JenkinsResultsParserUtil.read(
				new File(
					System.getProperty("user.home") + "/.1password.connect"));

			token = token.trim();

			JenkinsResultsParserUtil.addRedactToken(token);

			_bearerHTTPAuthorization = new BearerHTTPAuthorization(token);
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to load 1Password bearer token", ioException);
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
					if (fieldJSONObject.has("section")) {
						_linkedItem = _vault.getItem(
							fieldJSONObject.getString("label"));

						continue;
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