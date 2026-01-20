/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.search.spi.model.index.contributor;

import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.ReindexCacheThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.segments.model.SegmentsEntryRel;
import com.liferay.segments.model.SegmentsEntryRelTable;
import com.liferay.segments.service.SegmentsEntryRelLocalService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = "indexer.class.name=com.liferay.portal.kernel.model.User",
	service = ModelDocumentContributor.class
)
public class UserModelDocumentContributor
	implements ModelDocumentContributor<User> {

	@Override
	public void contribute(Document document, User user) {
		document.addKeyword("segmentsEntryIds", _getSegmentsEntryIds(user));
	}

	private long[] _getSegmentsEntryIds(User user) {
		Map<Long, long[]> segmentsEntryIdsMap =
			ReindexCacheThreadLocal.getGlobalReindexCache(
				() -> -1, UserModelDocumentContributor.class.getName(),
				count -> {
					Map<Long, List<Long>> segmentsEntryIdListMap =
						new HashMap<>();

					for (Object[] values :
							_segmentsEntryRelLocalService.
								<List<Object[]>>dslQuery(
									DSLQueryFactoryUtil.select(
										SegmentsEntryRelTable.INSTANCE.classPK,
										SegmentsEntryRelTable.INSTANCE.
											segmentsEntryId
									).from(
										SegmentsEntryRelTable.INSTANCE
									).where(
										SegmentsEntryRelTable.INSTANCE.
											classNameId.eq(
												_portal.getClassNameId(
													User.class))
									),
									false)) {

						List<Long> segmentsEntryIds =
							segmentsEntryIdListMap.computeIfAbsent(
								(Long)values[0], key -> new ArrayList<>());

						segmentsEntryIds.add((Long)values[1]);
					}

					Map<Long, long[]> localSegmentsEntryIdsMap =
						new HashMap<>();

					for (Map.Entry<Long, List<Long>> entry :
							segmentsEntryIdListMap.entrySet()) {

						localSegmentsEntryIdsMap.put(
							entry.getKey(),
							ArrayUtil.toLongArray(entry.getValue()));
					}

					return localSegmentsEntryIdsMap;
				});

		if (segmentsEntryIdsMap == null) {
			return ListUtil.toLongArray(
				_segmentsEntryRelLocalService.getSegmentsEntryRels(
					_portal.getClassNameId(User.class), user.getUserId()),
				SegmentsEntryRel::getSegmentsEntryId);
		}

		return segmentsEntryIdsMap.get(user.getUserId());
	}

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsEntryRelLocalService _segmentsEntryRelLocalService;

}