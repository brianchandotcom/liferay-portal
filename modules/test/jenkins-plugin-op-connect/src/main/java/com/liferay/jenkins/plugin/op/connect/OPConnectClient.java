/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.plugin.op.connect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class OPConnectClient {

	public OPConnectClient(String accessToken, String connectURL) {
		_accessToken = accessToken;

		if ((connectURL != null) && connectURL.endsWith("/")) {
			connectURL = connectURL.substring(0, connectURL.length() - 1);
		}

		_connectURL = connectURL;
	}

	public String get(String path) throws IOException {
		URL url = new URL(_connectURL + path);

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)url.openConnection();

		try {
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("Accept", "application/json");
			httpURLConnection.setRequestProperty(
				"Authorization", "Bearer " + _accessToken);

			int responseCode = httpURLConnection.getResponseCode();

			if (responseCode != HttpURLConnection.HTTP_OK) {
				StringBuilder sb = new StringBuilder();

				sb.append("1Password Connect request to ");
				sb.append(path);
				sb.append(" failed with response code ");
				sb.append(responseCode);

				throw new IOException(sb.toString());
			}

			try (InputStream inputStream = httpURLConnection.getInputStream();

				BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(
						inputStream, StandardCharsets.UTF_8))) {

				StringBuilder sb = new StringBuilder();

				String line = null;

				while ((line = bufferedReader.readLine()) != null) {
					sb.append(line);
				}

				return sb.toString();
			}
		}
		finally {
			httpURLConnection.disconnect();
		}
	}

	public Vault getVault(String vaultName) throws IOException {
		if ((vaultName == null) || vaultName.isEmpty()) {
			return null;
		}

		String filter = URLEncoder.encode(
			"name eq \"" + vaultName + "\"", StandardCharsets.UTF_8.name());

		filter = filter.replace("+", "%20");

		JSONArray jsonArray = new JSONArray(get("/v1/vaults?filter=" + filter));

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String name = jsonObject.optString("name");

			if (!vaultName.equals(name)) {
				continue;
			}

			return new Vault(jsonObject.getString("id"), name, this);
		}

		return null;
	}

	public List<Vault> getVaults() throws IOException {
		List<Vault> vaults = new ArrayList<>();

		JSONArray jsonArray = new JSONArray(get("/v1/vaults"));

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			vaults.add(
				new Vault(
					jsonObject.getString("id"), jsonObject.optString("name"),
					this));
		}

		return vaults;
	}

	public static class Field {

		public String getLabel() {
			return _label;
		}

		public String getValue() {
			return _value;
		}

		protected Field(String label, String value) {
			_label = label;
			_value = value;
		}

		private final String _label;
		private final String _value;

	}

	public static class Item {

		public Item(String id, OPConnectClient opConnectClient, Vault vault) {
			_id = id;
			_opConnectClient = opConnectClient;
			_vault = vault;
		}

		public List<Field> getFields() throws IOException {
			List<Field> fields = new ArrayList<>();

			Vault vault = getVault();

			String vaultId = vault.getId();

			if ((vaultId == null) || vaultId.isEmpty()) {
				return fields;
			}

			StringBuilder sb = new StringBuilder();

			sb.append("/v1/vaults/");
			sb.append(vaultId);
			sb.append("/items/");
			sb.append(getId());

			JSONObject itemJSONObject = new JSONObject(
				_opConnectClient.get(sb.toString()));

			JSONArray fieldsJSONArray = itemJSONObject.optJSONArray("fields");

			if (fieldsJSONArray == null) {
				return fields;
			}

			for (int i = 0; i < fieldsJSONArray.length(); i++) {
				JSONObject fieldJSONObject = fieldsJSONArray.getJSONObject(i);

				String label = fieldJSONObject.optString("label");
				String value = fieldJSONObject.optString("value");

				if ((label == null) || label.isEmpty() || (value == null) ||
					value.isEmpty()) {

					continue;
				}

				fields.add(new Field(label, value));
			}

			return fields;
		}

		public String getId() {
			return _id;
		}

		public Vault getVault() {
			return _vault;
		}

		private final String _id;
		private final OPConnectClient _opConnectClient;
		private final Vault _vault;

	}

	public static class Vault {

		public Vault(String id, String name, OPConnectClient opConnectClient) {
			_id = id;
			_name = name;
			_opConnectClient = opConnectClient;
		}

		public String getId() {
			return _id;
		}

		public List<Item> getItems() throws IOException {
			List<Item> items = new ArrayList<>();

			String id = getId();

			if ((id == null) || id.isEmpty()) {
				return items;
			}

			JSONArray itemsJSONArray = new JSONArray(
				_opConnectClient.get("/v1/vaults/" + id + "/items"));

			for (int i = 0; i < itemsJSONArray.length(); i++) {
				JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

				String itemId = itemJSONObject.optString("id");

				if (itemId == null) {
					continue;
				}

				items.add(new Item(itemId, _opConnectClient, this));
			}

			return items;
		}

		public String getName() {
			return _name;
		}

		private final String _id;
		private final String _name;
		private final OPConnectClient _opConnectClient;

	}

	private final String _accessToken;
	private final String _connectURL;

}