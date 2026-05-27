/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.semantic.search.cli.command;

import com.liferay.semantic.search.cli.client.QdrantClientWrapper;

import java.time.Duration;
import java.time.OffsetDateTime;

import org.json.JSONObject;

/**
 * @author JR Houn
 */
public class StatusCommand implements Command {

	@Override
	public String description() {
		return "Print index status as JSON (always JSON; ignores --format)";
	}

	@Override
	public int run(String[] args) throws Exception {
		QdrantClientWrapper qdrantClient = new QdrantClientWrapper();

		JSONObject statusJSONObject = new JSONObject();

		statusJSONObject.put(
			"doc_count", 0
		).put(
			"index_exists", false
		).put(
			"last_ingest", JSONObject.NULL
		).put(
			"qdrant_reachable", false
		).put(
			"qdrant_url", qdrantClient.getQdrantUrl()
		).put(
			"stale_days", JSONObject.NULL
		);

		if (!qdrantClient.isReachable()) {
			System.out.println(statusJSONObject.toString(2));

			System.err.println(
				"search: cannot reach Qdrant at " +
					qdrantClient.getQdrantUrl() +
						". Start it: docker compose up -d qdrant");

			return 5;
		}

		statusJSONObject.put("qdrant_reachable", true);

		boolean indexExists = qdrantClient.collectionExists(
			QdrantClientWrapper.COLLECTION);

		statusJSONObject.put("index_exists", indexExists);

		boolean metaExists = qdrantClient.collectionExists(
			QdrantClientWrapper.META_COLLECTION);

		QdrantClientWrapper.MetaState metaState =
			metaExists ? qdrantClient.readMetaState() : null;

		if (metaState != null) {
			statusJSONObject.put(
				"doc_count", metaState.docCount()
			).put(
				"last_ingest", metaState.lastIngest()
			);

			String lastIngest = metaState.lastIngest();

			if (lastIngest != null) {
				OffsetDateTime when = OffsetDateTime.parse(lastIngest);

				Duration elapsed = Duration.between(when, OffsetDateTime.now());

				double elapsedSeconds = elapsed.toSeconds();

				double days = elapsedSeconds / 86400.0;

				statusJSONObject.put("stale_days", _round(days, 2));
			}
		}

		if (indexExists && (metaState == null)) {
			statusJSONObject.put("chunk_count", qdrantClient.getChunkCount());
		}

		System.out.println(statusJSONObject.toString(2));

		return 0;
	}

	private double _round(double value, int decimals) {
		double scale = Math.pow(10, decimals);

		return Math.round(value * scale) / scale;
	}

}