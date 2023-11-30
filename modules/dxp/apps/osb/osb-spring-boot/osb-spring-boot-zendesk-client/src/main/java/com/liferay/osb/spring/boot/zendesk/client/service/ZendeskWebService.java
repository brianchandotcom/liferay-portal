/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.zendesk.client.service;

import com.liferay.osb.spring.boot.zendesk.client.model.ZendeskOrganization;
import com.liferay.osb.spring.boot.zendesk.client.model.ZendeskTicket;
import com.liferay.osb.spring.boot.zendesk.client.model.ZendeskUser;
import com.liferay.petra.string.StringPool;

import java.util.Base64;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Amos Fong
 */
@Component
public class ZendeskWebService {

	public void addAgentZendeskTicketComment(
			String htmlBody, long zendeskTicketId, long zendeskUserId)
		throws Exception {

		JSONObject commentJSONObject = new JSONObject();

		commentJSONObject.put(
			"author_id", zendeskUserId
		).put(
			"html_body", htmlBody
		).put(
			"public", true
		);

		JSONObject ticketJSONObject = new JSONObject();

		ticketJSONObject.put("comment", commentJSONObject);

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("ticket", ticketJSONObject);

		WebClient.create(
			_zendeskURL
		).put(
		).uri(
			"/api/v2/tickets/" + zendeskTicketId + ".json"
		).accept(
			MediaType.APPLICATION_JSON
		).contentType(
			MediaType.APPLICATION_JSON
		).header(
			HttpHeaders.AUTHORIZATION, _zendeskAuthorization
		).body(
			BodyInserters.fromValue(jsonObject.toString())
		).retrieve(
		).bodyToMono(
			String.class
		).block();
	}

	public void addEndUserZendeskTicketComment(
			String emailAddress, String htmlBody, long zendeskTicketId)
		throws Exception {

		JSONObject commentJSONObject = new JSONObject();

		commentJSONObject.put("html_body", htmlBody);

		JSONObject ticketJSONObject = new JSONObject();

		ticketJSONObject.put("comment", commentJSONObject);

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("request", ticketJSONObject);

		WebClient.create(
			_zendeskURL
		).put(
		).uri(
			"/api/v2/requests/" + zendeskTicketId + ".json"
		).accept(
			MediaType.APPLICATION_JSON
		).contentType(
			MediaType.APPLICATION_JSON
		).header(
			HttpHeaders.AUTHORIZATION, _getAuthorization(emailAddress)
		).body(
			BodyInserters.fromValue(jsonObject.toString())
		).retrieve(
		).bodyToMono(
			String.class
		).block();
	}

	public ZendeskUser fetchZendeskUser(String emailAddress) throws Exception {
		JSONObject jsonObject = new JSONObject(
			WebClient.create(
				_zendeskURL
			).get(
			).uri(
				"/api/v2/users/search.json?query=" + emailAddress
			).accept(
				MediaType.APPLICATION_JSON
			).header(
				HttpHeaders.AUTHORIZATION, _zendeskAuthorization
			).retrieve(
			).bodyToMono(
				String.class
			).block());

		JSONArray jsonArray = jsonObject.getJSONArray("users");

		if (jsonArray.length() <= 0) {
			return null;
		}

		return new ZendeskUser(jsonArray.getJSONObject(0));
	}

	public ZendeskOrganization getZendeskOrganization(
			long zendeskOrganizationId)
		throws Exception {

		JSONObject jsonObject = new JSONObject(
			WebClient.create(
				_zendeskURL
			).get(
			).uri(
				"/api/v2/organizations/" + zendeskOrganizationId + ".json"
			).accept(
				MediaType.APPLICATION_JSON
			).header(
				HttpHeaders.AUTHORIZATION, _zendeskAuthorization
			).retrieve(
			).bodyToMono(
				String.class
			).block());

		return new ZendeskOrganization(
			jsonObject.getJSONObject("organization"));
	}

	public ZendeskTicket getZendeskTicket(long zendeskTicketId)
		throws Exception {

		JSONObject jsonObject = new JSONObject(
			WebClient.create(
				_zendeskURL
			).get(
			).uri(
				"/api/v2/tickets/" + zendeskTicketId + ".json"
			).accept(
				MediaType.APPLICATION_JSON
			).header(
				HttpHeaders.AUTHORIZATION, _zendeskAuthorization
			).retrieve(
			).bodyToMono(
				String.class
			).block());

		return new ZendeskTicket(jsonObject.getJSONObject("ticket"));
	}

	@PostConstruct
	public void init() throws Exception {
		_zendeskAuthorization = _getAuthorization(_zendeskAPIEmailAddress);
	}

	private String _getAuthorization(String emailAddress) throws Exception {
		Base64.Encoder encoder = Base64.getEncoder();

		String zendeskCredentials = emailAddress + "/token:" + _zendeskAPIToken;

		String encodedZendeskCredentials = new String(
			encoder.encode(zendeskCredentials.getBytes(StringPool.UTF8)),
			StringPool.UTF8);

		return "Basic " + encodedZendeskCredentials;
	}

	@Value("${liferay.customer.zendesk.api.email.address}")
	private String _zendeskAPIEmailAddress;

	@Value("${liferay.customer.zendesk.api.token}")
	private String _zendeskAPIToken;

	private String _zendeskAuthorization;

	@Value("${liferay.customer.zendesk.url}")
	private String _zendeskURL;

}