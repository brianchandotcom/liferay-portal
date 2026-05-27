/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.semantic.search.cli.client;

import com.liferay.petra.string.StringBundler;

import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.Duration;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * HTTP client for Hugging Face's text-embeddings-inference (TEI)
 * server. TEI exposes /embed (batch) and /info (model metadata).
 *
 * Configuration via env vars:
 *   TEI_URL    default "http://localhost:8080"
 *
 * No retry, no auth — TEI runs on localhost behind the docker-compose
 * network. If we ever move it to a remote host, add a connect-timeout
 * retry loop here.
 *
 * @author JR Houn
 */
public class TextEmbeddingsClient {

	public TextEmbeddingsClient() {
		String url = System.getenv("TEI_URL");

		if ((url == null) || url.isEmpty()) {
			url = "http://localhost:8080";
		}

		_baseUrl = url;

		_httpClient = HttpClient.newBuilder(
		).connectTimeout(
			Duration.ofSeconds(5)
		).build();
	}

	public float[][] embedDocuments(List<String> texts) throws IOException {
		return _embedBatch(texts);
	}

	public float[] embedQuery(String text) throws IOException {
		float[][] all = _embedBatch(List.of(text));

		return all[0];
	}

	public int getDimension() throws IOException {
		HttpRequest httpRequest = HttpRequest.newBuilder(
			URI.create(_baseUrl + "/info")
		).GET(
		).timeout(
			Duration.ofSeconds(5)
		).build();

		try {
			HttpResponse<String> httpResponse = _httpClient.send(
				httpRequest, HttpResponse.BodyHandlers.ofString());

			if (httpResponse.statusCode() != 200) {
				throw new IOException(
					"TEI /info returned " + httpResponse.statusCode());
			}

			JSONObject responseJSONObject = new JSONObject(httpResponse.body());

			return responseJSONObject.getInt("max_input_length");
		}
		catch (InterruptedException interruptedException) {
			Thread currentThread = Thread.currentThread();

			currentThread.interrupt();

			throw new IOException("interrupted", interruptedException);
		}
	}

	public boolean isReachable() {
		try {
			HttpRequest httpRequest = HttpRequest.newBuilder(
				URI.create(_baseUrl + "/health")
			).GET(
			).timeout(
				Duration.ofSeconds(2)
			).build();

			HttpResponse<Void> httpResponse = _httpClient.send(
				httpRequest, HttpResponse.BodyHandlers.discarding());

			if (httpResponse.statusCode() == 200) {
				return true;
			}

			return false;
		}
		catch (Exception exception) {
			return false;
		}
	}

	private float[][] _embedBatch(List<String> texts) throws IOException {
		JSONObject bodyJSONObject = new JSONObject();

		bodyJSONObject.put(
			"inputs", new JSONArray(texts)
		).put(
			"normalize", true
		);

		HttpRequest httpRequest = HttpRequest.newBuilder(
			URI.create(_baseUrl + "/embed")
		).header(
			"Content-Type", "application/json"
		).POST(
			HttpRequest.BodyPublishers.ofString(bodyJSONObject.toString())
		).timeout(
			Duration.ofMinutes(2)
		).build();

		try {
			HttpResponse<String> httpResponse = _httpClient.send(
				httpRequest, HttpResponse.BodyHandlers.ofString());

			if (httpResponse.statusCode() != 200) {
				throw new IOException(
					StringBundler.concat(
						"TEI /embed returned ", httpResponse.statusCode(), ": ",
						httpResponse.body()));
			}

			JSONArray responseJSONArray = new JSONArray(httpResponse.body());

			float[][] vectors = new float[responseJSONArray.length()][];

			for (int i = 0; i < responseJSONArray.length(); i++) {
				JSONArray vectorJSONArray = responseJSONArray.getJSONArray(i);

				vectors[i] = new float[vectorJSONArray.length()];

				for (int j = 0; j < vectorJSONArray.length(); j++) {
					vectors[i][j] = (float)vectorJSONArray.getDouble(j);
				}
			}

			return vectors;
		}
		catch (InterruptedException interruptedException) {
			Thread currentThread = Thread.currentThread();

			currentThread.interrupt();

			throw new IOException("interrupted", interruptedException);
		}
	}

	private final String _baseUrl;
	private final HttpClient _httpClient;

}