/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.change.tracking.spi.listener;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.spi.listener.CTEventListener;
import com.liferay.fragment.internal.constants.FragmentEntryVersionConstants;
import com.liferay.fragment.model.FragmentEntryVersion;
import com.liferay.fragment.model.FragmentEntryVersionTable;
import com.liferay.fragment.service.persistence.FragmentEntryVersionPersistence;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(service = CTEventListener.class)
public class FragmentEntryVersionCTEventListener implements CTEventListener {

	@Override
	public void onAfterPublish(long ctCollectionId) {
		Map<Long, Integer> counts = _countsMap.remove(ctCollectionId);

		if (counts == null) {
			return;
		}

		for (Map.Entry<Long, Integer> entry : counts.entrySet()) {
			_deleteFragmentEntryVersions(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void onBeforePublish(long ctCollectionId) {
		Map<Long, Integer> counts = _getCounts(ctCollectionId);

		if (MapUtil.isEmpty(counts)) {
			_countsMap.remove(ctCollectionId);

			return;
		}

		_countsMap.put(ctCollectionId, counts);
	}

	private void _deleteFragmentEntryVersions(
		long fragmentEntryId, int fragmentEntryVersionCount) {

		try {
			List<FragmentEntryVersion> fragmentEntryVersions =
				_getDeletableFragmentEntryVersions(
					fragmentEntryId, fragmentEntryVersionCount);

			for (FragmentEntryVersion fragmentEntryVersion :
					fragmentEntryVersions) {

				_fragmentEntryVersionPersistence.remove(fragmentEntryVersion);
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to delete old fragment entry versions for " +
						"fragment entry ID " + fragmentEntryId,
					exception);
			}
		}
	}

	private Map<Long, Integer> _getCounts(long ctCollectionId) {
		Map<Long, Integer> counts = new HashMap<>();

		DSLQuery dslQuery = DSLQueryFactoryUtil.select(
			FragmentEntryVersionTable.INSTANCE.fragmentEntryId,
			DSLFunctionFactoryUtil.count(
				FragmentEntryVersionTable.INSTANCE.fragmentEntryVersionId
			).as(
				"fragmentEntryVersionCount"
			)
		).from(
			FragmentEntryVersionTable.INSTANCE
		).where(
			FragmentEntryVersionTable.INSTANCE.ctCollectionId.eq(ctCollectionId)
		).groupBy(
			FragmentEntryVersionTable.INSTANCE.fragmentEntryId
		);

		for (Object[] array :
				(List<Object[]>)_fragmentEntryVersionPersistence.dslQuery(
					dslQuery)) {

			long fragmentEntryId = GetterUtil.getLong(array[0]);
			int fragmentEntryVersionCount = GetterUtil.getInteger(array[1]);

			counts.put(fragmentEntryId, fragmentEntryVersionCount);
		}

		return counts;
	}

	private List<FragmentEntryVersion> _getDeletableFragmentEntryVersions(
		long fragmentEntryId, int fragmentEntryVersionCount) {

		return _fragmentEntryVersionPersistence.dslQuery(
			DSLQueryFactoryUtil.select(
				FragmentEntryVersionTable.INSTANCE
			).from(
				FragmentEntryVersionTable.INSTANCE
			).where(
				FragmentEntryVersionTable.INSTANCE.ctCollectionId.eq(
					CTConstants.CT_COLLECTION_ID_PRODUCTION
				).and(
					FragmentEntryVersionTable.INSTANCE.fragmentEntryId.eq(
						fragmentEntryId)
				)
			).orderBy(
				FragmentEntryVersionTable.INSTANCE.version.descending()
			).limit(
				Math.max(
					0,
					FragmentEntryVersionConstants.
						FRAGMENT_ENTRY_VERSIONS_COUNT_MAX -
							fragmentEntryVersionCount),
				Integer.MAX_VALUE
			));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryVersionCTEventListener.class);

	private final Map<Long, Map<Long, Integer>> _countsMap =
		new ConcurrentHashMap<>();

	@Reference
	private FragmentEntryVersionPersistence _fragmentEntryVersionPersistence;

}