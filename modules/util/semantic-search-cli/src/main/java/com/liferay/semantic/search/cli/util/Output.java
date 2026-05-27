/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.semantic.search.cli.util;

import java.util.List;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Formats result lists as the two output styles `query` and `similar`
 * accept. The JSON shape is the stable contract — field names and
 * types must not change without bumping the contract version.
 *
 * @author JR Houn
 */
public class Output {

	public static String formatHits(List<Hit> hits, String format) {
		if (Objects.equals(format, "json")) {
			return _formatHitsJSON(hits);
		}

		return _formatHitsText(hits);
	}

	public static String snippet(String text) {
		String collapsed = text.replaceAll("\\s+", " ");

		collapsed = collapsed.trim();

		if (collapsed.length() <= _SNIPPET_MAX_CHARS) {
			return collapsed;
		}

		String cut = collapsed.substring(0, _SNIPPET_MAX_CHARS);

		int lastSpace = cut.lastIndexOf(' ');

		if (lastSpace > (_SNIPPET_MAX_CHARS * 0.6)) {
			cut = cut.substring(0, lastSpace);
		}

		return cut + "...";
	}

	private static String _formatHitsJSON(List<Hit> hits) {
		JSONArray hitsJSONArray = new JSONArray();

		for (Hit hit : hits) {
			JSONObject hitJSONObject = new JSONObject();

			hitJSONObject.put(
				"chunk_id", hit.chunkId()
			).put(
				"heading_path", new JSONArray(hit.headingPath())
			).put(
				"path", hit.path()
			).put(
				"score", _round(hit.score(), 4)
			).put(
				"snippet", hit.snippet()
			);

			hitsJSONArray.put(hitJSONObject);
		}

		return hitsJSONArray.toString(2);
	}

	private static String _formatHitsText(List<Hit> hits) {
		if (hits.isEmpty()) {
			return "(no results)";
		}

		StringBuilder stringBuilder = new StringBuilder();

		int i = 1;

		for (Hit hit : hits) {
			String head = String.join(" > ", hit.headingPath());

			if (head.isEmpty()) {
				head = "(no heading)";
			}

			stringBuilder.append(i++);
			stringBuilder.append(". ");
			stringBuilder.append(hit.path());
			stringBuilder.append("  [");
			stringBuilder.append(String.format("%.3f", hit.score()));
			stringBuilder.append("]\n   ");
			stringBuilder.append(head);
			stringBuilder.append("\n   ");
			stringBuilder.append(hit.snippet());
			stringBuilder.append('\n');
		}

		stringBuilder.setLength(stringBuilder.length() - 1);

		return stringBuilder.toString();
	}

	private static double _round(double value, int decimals) {
		double scale = Math.pow(10, decimals);

		return Math.round(value * scale) / scale;
	}

	private static final int _SNIPPET_MAX_CHARS = 300;

}