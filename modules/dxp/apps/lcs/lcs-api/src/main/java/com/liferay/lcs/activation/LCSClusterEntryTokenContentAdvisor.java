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

package com.liferay.lcs.activation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor Beslic
 */
public class LCSClusterEntryTokenContentAdvisor {

	public LCSClusterEntryTokenContentAdvisor(String content)
		throws IOException {

		if (isNotValid(content)) {
			throw new UnsupportedOperationException(
				"Automatic activation token content must not be empty");
		}

		String[] array = content.split("--");

		if (array.length == 1) {
			processContent(content);
		}
		else if (array.length == 2) {
			_accessSecret = array[1];
			_accessToken = array[0];
		}
		else if (array.length == 3) {
			_accessSecret = array[1];
			_accessToken = array[0];

			processContent(array[2]);
		}
		else {
			throw new UnsupportedEncodingException(
				"Automatic activation token content structure is not " +
					"supported");
		}
	}

	public LCSClusterEntryTokenContentAdvisor(
		String accessSecret, String accessToken,
		Map<String, String> lcsServicesConfiguration,
		String portalPropertiesBlacklist) {

		if (isNotValid(accessSecret) || isNotValid(accessToken)) {
			throw new UnsupportedOperationException(
				"OAuth access secret and token must not be empty");
		}

		_accessSecret = accessSecret;
		_accessToken = accessToken;
		_portalPropertiesBlacklist = portalPropertiesBlacklist;

		_lcsServicesConfiguration.putAll(lcsServicesConfiguration);
	}

	public LCSClusterEntryTokenContentAdvisor(
		String accessSecret, String accessToken, String consumerKey,
		String consumerSecret, String dataCenterHostName,
		String dataCenterHostPort, String dataCenterProtocol,
		Map<String, String> lcsServicesConfiguration,
		String portalPropertiesBlacklist) {

		this(
			accessSecret, accessToken, lcsServicesConfiguration,
			portalPropertiesBlacklist);

		if (isNotValid(consumerKey) || isNotValid(consumerSecret)) {
			throw new UnsupportedOperationException(
				"OAuth consumer key and secret must not be empty");
		}

		_consumerKey = consumerKey;
		_consumerSecret = consumerSecret;
		_dataCenterHostName = dataCenterHostName;
		_dataCenterHostPort = dataCenterHostPort;
		_dataCenterProtocol = dataCenterProtocol;
	}

	public String getAccessSecret() {
		return _accessSecret;
	}

	public String getAccessToken() {
		return _accessToken;
	}

	public String getConsumerKey() {
		return _consumerKey;
	}

	public String getConsumerSecret() {
		return _consumerSecret;
	}

	public String getContent() {
		StringBuilder sb = new StringBuilder();

		sb.append(_accessToken);
		sb.append("--");
		sb.append(_accessSecret);
		sb.append("--");
		sb.append(getContentJSONString());

		return sb.toString();
	}

	public String getDataCenterHostName() {
		return _dataCenterHostName;
	}

	public String getDataCenterHostPort() {
		return _dataCenterHostPort;
	}

	public String getDataCenterProtocol() {
		return _dataCenterProtocol;
	}

	public Map<String, String> getLCSServicesConfiguration() {
		return _lcsServicesConfiguration;
	}

	public String getPortalPropertiesBlacklist() {
		return _portalPropertiesBlacklist;
	}

	protected String getContentJSONString() {
		ObjectMapper objectMapper = new ObjectMapper();

		ObjectNode rootNode = objectMapper.createObjectNode();

		rootNode.put("accessSecret", _accessSecret);
		rootNode.put("accessToken", _accessToken);

		if (_consumerKey != null) {
			rootNode.put("consumerKey", _consumerKey);
		}

		if (_consumerSecret != null) {
			rootNode.put("consumerSecret", _consumerSecret);
		}

		if (_dataCenterHostName != null) {
			rootNode.put("dataCenterHostName", _dataCenterHostName);
		}

		if (_dataCenterHostPort != null) {
			rootNode.put("dataCenterHostPort", _dataCenterHostPort);
		}

		if (_dataCenterProtocol != null) {
			rootNode.put("dataCenterProtocol", _dataCenterProtocol);
		}

		ArrayNode arrayNode = rootNode.putArray("lcsServicesConfiguration");

		for (Map.Entry<String, String> entry :
				_lcsServicesConfiguration.entrySet()) {

			if (Boolean.parseBoolean(entry.getValue())) {
				arrayNode.add(entry.getKey());
			}
		}

		if (_portalPropertiesBlacklist != null) {
			rootNode.put(
				"portalPropertiesBlacklist", _portalPropertiesBlacklist);
		}

		return rootNode.toString();
	}

	protected boolean isNotValid(String value) {
		if (value == null) {
			return true;
		}

		value = value.trim();

		if (value.length() == 0) {
			return true;
		}

		return false;
	}

	protected void processContent(String contentJSONString) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();

		JsonNode jsonTreeNode = objectMapper.readTree(contentJSONString);

		JsonNode jsonNode = jsonTreeNode.get("accessSecret");

		_accessSecret = jsonNode.asText();

		jsonNode = jsonTreeNode.get("accessToken");

		_accessToken = jsonNode.asText();

		jsonNode = jsonTreeNode.get("consumerKey");

		if (jsonNode != null) {
			_consumerKey = jsonNode.asText();
		}

		jsonNode = jsonTreeNode.get("consumerSecret");

		if (jsonNode != null) {
			_consumerSecret = jsonNode.asText();
		}

		jsonNode = jsonTreeNode.get("dataCenterHostName");

		if (jsonNode != null) {
			_dataCenterHostName = jsonNode.asText();
		}

		jsonNode = jsonTreeNode.get("dataCenterHostPort");

		if (jsonNode != null) {
			_dataCenterHostPort = jsonNode.asText();
		}

		jsonNode = jsonTreeNode.get("dataCenterProtocol");

		if (jsonNode != null) {
			_dataCenterProtocol = jsonNode.asText();
		}

		jsonNode = jsonTreeNode.get("lcsServicesConfiguration");

		if (jsonNode.isArray()) {
			for (JsonNode jsonArrayElement : jsonNode) {
				_lcsServicesConfiguration.put(
					jsonArrayElement.asText(), "true");
			}
		}
		else {
			_lcsServicesConfiguration.put(jsonNode.asText(), "true");
		}

		jsonNode = jsonTreeNode.get("portalPropertiesBlacklist");

		if (jsonNode != null) {
			_portalPropertiesBlacklist = jsonNode.asText();
		}
	}

	private String _accessSecret;
	private String _accessToken;
	private String _consumerKey;
	private String _consumerSecret;
	private String _dataCenterHostName;
	private String _dataCenterHostPort;
	private String _dataCenterProtocol;
	private final Map<String, String> _lcsServicesConfiguration =
		new HashMap<String, String>();
	private String _portalPropertiesBlacklist;

}