/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.internal.similarity;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.search.document.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Mikel Lorza
 */
public class SimilarityClusterUtil {

	public static final String FIELD_NAME_OBJECT_ENTRY_ID = "objectEntryId";

	public static List<List<Long>> getClusters(
		String bandField, List<Document> documents, Set<String> sharedBands) {

		Map<Long, Long> parents = new LinkedHashMap<>();
		Map<String, List<Long>> objectEntryIdsByBand = new HashMap<>();

		for (Document document : documents) {
			Long objectEntryId = document.getLong(FIELD_NAME_OBJECT_ENTRY_ID);

			if (objectEntryId == null) {
				continue;
			}

			parents.putIfAbsent(objectEntryId, objectEntryId);

			for (String band : document.getStrings(bandField)) {
				if (!sharedBands.contains(band)) {
					continue;
				}

				List<Long> bandObjectEntryIds =
					objectEntryIdsByBand.computeIfAbsent(
						band, key -> new ArrayList<>());

				bandObjectEntryIds.add(objectEntryId);
			}
		}

		_union(objectEntryIdsByBand, parents);

		Map<Long, List<Long>> clusters = new LinkedHashMap<>();

		for (Long objectEntryId : parents.keySet()) {
			List<Long> cluster = clusters.computeIfAbsent(
				_find(objectEntryId, parents), root -> new ArrayList<>());

			cluster.add(objectEntryId);
		}

		return TransformUtil.transform(
			clusters.values(),
			cluster -> {
				if (cluster.size() < 2) {
					return null;
				}

				return cluster;
			});
	}

	public static Long getMinObjectEntryId(List<Long> cluster) {
		Long minObjectEntryId = null;

		for (Long objectEntryId : cluster) {
			if ((minObjectEntryId == null) ||
				(objectEntryId < minObjectEntryId)) {

				minObjectEntryId = objectEntryId;
			}
		}

		return minObjectEntryId;
	}

	public static String getTokenPrefix(String languageId) {
		return languageId + StringPool.UNDERLINE;
	}

	private static Long _find(Long objectEntryId, Map<Long, Long> parents) {
		Long parent = parents.get(objectEntryId);

		while (!parent.equals(objectEntryId)) {
			objectEntryId = parent;

			parent = parents.get(objectEntryId);
		}

		return objectEntryId;
	}

	private static void _union(
		Long objectEntryId1, Long objectEntryId2, Map<Long, Long> parents) {

		Long root1 = _find(objectEntryId1, parents);
		Long root2 = _find(objectEntryId2, parents);

		if (!root1.equals(root2)) {
			parents.put(root1, root2);
		}
	}

	private static void _union(
		Map<String, List<Long>> objectEntryIdsByBand, Map<Long, Long> parents) {

		Map<Long, Map<Long, Integer>> sharedBandCounts = new HashMap<>();

		for (List<Long> objectEntryIds : objectEntryIdsByBand.values()) {
			for (int i = 0; i < objectEntryIds.size(); i++) {
				for (int j = i + 1; j < objectEntryIds.size(); j++) {
					Long objectEntryId1 = objectEntryIds.get(i);
					Long objectEntryId2 = objectEntryIds.get(j);

					Long root1 = _find(objectEntryId1, parents);
					Long root2 = _find(objectEntryId2, parents);

					if (root1.equals(root2)) {
						continue;
					}

					Map<Long, Integer> counts =
						sharedBandCounts.computeIfAbsent(
							Math.min(objectEntryId1, objectEntryId2),
							key -> new HashMap<>());

					int count = counts.merge(
						Math.max(objectEntryId1, objectEntryId2), 1,
						Integer::sum);

					if (count >= _MIN_SHARED_BANDS) {
						_union(objectEntryId1, objectEntryId2, parents);
					}
				}
			}
		}
	}

	private static final int _MIN_SHARED_BANDS = 3;

}