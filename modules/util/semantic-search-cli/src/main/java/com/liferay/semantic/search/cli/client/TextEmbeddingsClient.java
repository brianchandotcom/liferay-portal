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
 *   TEI_URL    default "http://localhost:7997"
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

			// Not 8080: that is Liferay Tomcat's default HTTP port, so the
			// stack must avoid it to run alongside a portal bundle.

			url = "http://localhost:7997";
		}

		_baseURL = url;

		_httpClient = HttpClient.newBuilder(
		).connectTimeout(
			Duration.ofSeconds(5)
		).build();
	}

	public float[][] embedDocuments(List<String> texts) throws IOException {
		return _embedBatch(texts);
	}

	public float[] embedQuery(String text) throws IOException {

		// Asymmetric code models (e.g. CodeRankEmbed) require a static task
		// instruction on the query side only; omitting it drops recall
		// sharply. The prefix is a per-model concern, supplied by the
		// caller via SEARCH_QUERY_PREFIX so this client stays model-neutral.
		// Document embedding (embedDocuments) never gets the prefix.

		String prefix = System.getenv("SEARCH_QUERY_PREFIX");

		if ((prefix != null) && !prefix.isEmpty()) {
			text = prefix + text;
		}

		float[][] all = _embedBatch(List.of(text));

		return all[0];
	}

	public int getDimension() throws IOException {
		HttpRequest httpRequest = HttpRequest.newBuilder(
			URI.create(_baseURL + "/info")
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
				URI.create(_baseURL + "/health")
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
			URI.create(_baseURL + "/embed")
		).header(
			"Content-Type", "application/json"
		).POST(
			HttpRequest.BodyPublishers.ofString(bodyJSONObject.toString())
		).timeout(

			// Generous: a heavier code model (e.g. CodeRankEmbed) embedding a
			// batch of large methods in full takes far longer than the
			// general-purpose default, which truncates hard at 512 tokens.

			Duration.ofMinutes(10)
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

			int responseLength = responseJSONArray.length();

			float[][] vectors = new float[responseLength][];

			for (int i = 0; i < responseLength; i++) {
				JSONArray vectorJSONArray = responseJSONArray.getJSONArray(i);

				int vectorLength = vectorJSONArray.length();

				vectors[i] = new float[vectorLength];

				for (int j = 0; j < vectorLength; j++) {
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

	private final String _baseURL;
	private final HttpClient _httpClient;

}