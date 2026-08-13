/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.internal.similarity;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.document.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Mikel Lorza
 */
public class SimilarityClusterUtil {

	public static Map<Long, List<Long>> getClusters(
		List<Document> documents, Set<String> sharedSimilarityKeys) {

		Map<Long, Long> parentObjectEntryIds = new LinkedHashMap<>();
		Map<String, List<Long>> objectEntryIdsBySimilarityKey = new HashMap<>();

		for (Document document : documents) {
			Long objectEntryId = document.getLong("objectEntryId");

			if (objectEntryId == null) {
				continue;
			}

			parentObjectEntryIds.putIfAbsent(objectEntryId, objectEntryId);

			for (String similarityKey :
					document.getStrings("textSimilarityKeys")) {

				if (!sharedSimilarityKeys.contains(similarityKey)) {
					continue;
				}

				List<Long> similarityKeyObjectEntryIds =
					objectEntryIdsBySimilarityKey.computeIfAbsent(
						similarityKey, key -> new ArrayList<>());

				similarityKeyObjectEntryIds.add(objectEntryId);
			}
		}

		_mergeNearDuplicateClusters(
			objectEntryIdsBySimilarityKey, parentObjectEntryIds);

		return _getObjectEntryIdsByClusterId(parentObjectEntryIds);
	}

	private static Map<Long, List<Long>> _getObjectEntryIdsByClusterId(
		Map<Long, Long> parentObjectEntryIds) {

		Map<Long, List<Long>> objectEntryIdsByRootObjectEntryId =
			new LinkedHashMap<>();

		for (Long objectEntryId : parentObjectEntryIds.keySet()) {
			List<Long> rootObjectEntryIds =
				objectEntryIdsByRootObjectEntryId.computeIfAbsent(
					_getRootObjectEntryId(objectEntryId, parentObjectEntryIds),
					rootObjectEntryId -> new ArrayList<>());

			rootObjectEntryIds.add(objectEntryId);
		}

		Map<Long, List<Long>> objectEntryIdsByClusterId = new HashMap<>();

		for (List<Long> objectEntryIds :
				objectEntryIdsByRootObjectEntryId.values()) {

			if (objectEntryIds.size() < 2) {
				continue;
			}

			Collections.sort(objectEntryIds);

			objectEntryIdsByClusterId.put(
				objectEntryIds.get(0), objectEntryIds);
		}

		return _getSortedObjectEntryIdsByClusterId(objectEntryIdsByClusterId);
	}

	private static Long _getRootObjectEntryId(
		Long objectEntryId, Map<Long, Long> parentObjectEntryIds) {

		Long parentObjectEntryId = parentObjectEntryIds.get(objectEntryId);

		while (!parentObjectEntryId.equals(objectEntryId)) {
			objectEntryId = parentObjectEntryId;

			parentObjectEntryId = parentObjectEntryIds.get(objectEntryId);
		}

		return objectEntryId;
	}

	private static Map<Long, List<Long>> _getSortedObjectEntryIdsByClusterId(
		Map<Long, List<Long>> objectEntryIdsByClusterId) {

		List<Long> clusterIds = ListUtil.fromMapKeys(objectEntryIdsByClusterId);

		clusterIds.sort(
			Comparator.comparingInt(
				(Long clusterId) -> {
					List<Long> objectEntryIds = objectEntryIdsByClusterId.get(
						clusterId);

					return objectEntryIds.size();
				}
			).reversed(
			).thenComparing(
				Comparator.naturalOrder()
			));

		Map<Long, List<Long>> sortedObjectEntryIdsByClusterId =
			new LinkedHashMap<>();

		for (Long clusterId : clusterIds) {
			sortedObjectEntryIdsByClusterId.put(
				clusterId, objectEntryIdsByClusterId.get(clusterId));
		}

		return sortedObjectEntryIdsByClusterId;
	}

	private static void _mergeClusters(
		Long objectEntryId1, Long objectEntryId2,
		Map<Long, Long> parentObjectEntryIds) {

		Long rootObjectEntryId1 = _getRootObjectEntryId(
			objectEntryId1, parentObjectEntryIds);
		Long rootObjectEntryId2 = _getRootObjectEntryId(
			objectEntryId2, parentObjectEntryIds);

		if (!rootObjectEntryId1.equals(rootObjectEntryId2)) {
			parentObjectEntryIds.put(rootObjectEntryId1, rootObjectEntryId2);
		}
	}

	private static void _mergeNearDuplicateClusters(
		Map<String, List<Long>> objectEntryIdsBySimilarityKey,
		Map<Long, Long> parentObjectEntryIds) {

		Map<Long, Map<Long, Integer>> sharedSimilarityKeyCounts =
			new HashMap<>();

		for (List<Long> objectEntryIds :
				objectEntryIdsBySimilarityKey.values()) {

			for (int i = 0; i < objectEntryIds.size(); i++) {
				for (int j = i + 1; j < objectEntryIds.size(); j++) {
					Long objectEntryId1 = objectEntryIds.get(i);
					Long objectEntryId2 = objectEntryIds.get(j);

					Long rootObjectEntryId1 = _getRootObjectEntryId(
						objectEntryId1, parentObjectEntryIds);
					Long rootObjectEntryId2 = _getRootObjectEntryId(
						objectEntryId2, parentObjectEntryIds);

					if (rootObjectEntryId1.equals(rootObjectEntryId2)) {
						continue;
					}

					Map<Long, Integer> counts =
						sharedSimilarityKeyCounts.computeIfAbsent(
							Math.min(objectEntryId1, objectEntryId2),
							key -> new HashMap<>());

					int count = counts.merge(
						Math.max(objectEntryId1, objectEntryId2), 1,
						Integer::sum);

					if (count >= _MIN_SHARED_SIMILARITY_KEYS) {
						_mergeClusters(
							objectEntryId1, objectEntryId2,
							parentObjectEntryIds);
					}
				}
			}
		}
	}

	private static final int _MIN_SHARED_SIMILARITY_KEYS = 3;

}