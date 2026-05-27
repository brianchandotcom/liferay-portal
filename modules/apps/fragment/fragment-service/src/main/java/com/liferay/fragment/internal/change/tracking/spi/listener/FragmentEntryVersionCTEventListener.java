/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.change.tracking.spi.listener;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.spi.listener.CTEventListener;
import com.liferay.fragment.internal.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentEntryVersion;
import com.liferay.fragment.model.FragmentEntryVersionTable;
import com.liferay.fragment.service.persistence.FragmentEntryVersionPersistence;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

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
		Map<Long, Integer> fragmentEntryVersionCountByFragmentEntryIdMap =
			_fragmentEntryVersionCountByCtCollectionIdMap.remove(
				ctCollectionId);

		if (fragmentEntryVersionCountByFragmentEntryIdMap == null) {
			return;
		}

		for (Map.Entry<Long, Integer> entry :
				fragmentEntryVersionCountByFragmentEntryIdMap.entrySet()) {

			_removeFragmentEntryVersions(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void onBeforePublish(long ctCollectionId) {
		Map<Long, Integer> fragmentEntryVersionCountByFragmentEntryIdMap =
			_getFragmentEntryVersionCountByFragmentEntryIdMap(ctCollectionId);

		if (fragmentEntryVersionCountByFragmentEntryIdMap.isEmpty()) {
			_fragmentEntryVersionCountByCtCollectionIdMap.remove(
				ctCollectionId);

			return;
		}

		_fragmentEntryVersionCountByCtCollectionIdMap.put(
			ctCollectionId, fragmentEntryVersionCountByFragmentEntryIdMap);
	}

	private Map<Long, Integer>
		_getFragmentEntryVersionCountByFragmentEntryIdMap(long ctCollectionId) {

		Map<Long, Integer> fragmentEntryVersionCountByFragmentEntryIdMap =
			new HashMap<>();

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

		for (Object[] values :
				(List<Object[]>)_fragmentEntryVersionPersistence.dslQuery(
					dslQuery)) {

			long fragmentEntryId = GetterUtil.getLong(values[0]);
			int fragmentEntryVersionCount = GetterUtil.getInteger(values[1]);

			fragmentEntryVersionCountByFragmentEntryIdMap.put(
				fragmentEntryId, fragmentEntryVersionCount);
		}

		return fragmentEntryVersionCountByFragmentEntryIdMap;
	}

	private List<FragmentEntryVersion> _getRemovableFragmentEntryVersions(
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
					FragmentConstants.MAX_FRAGMENT_ENTRY_VERSION_COUNT -
						fragmentEntryVersionCount),
				Integer.MAX_VALUE
			));
	}

	private void _removeFragmentEntryVersions(
		long fragmentEntryId, int fragmentEntryVersionCount) {

		try {
			List<FragmentEntryVersion> fragmentEntryVersions =
				_getRemovableFragmentEntryVersions(
					fragmentEntryId, fragmentEntryVersionCount);

			for (FragmentEntryVersion fragmentEntryVersion :
					fragmentEntryVersions) {

				_fragmentEntryVersionPersistence.remove(fragmentEntryVersion);
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to remove old versions for fragment entry " +
						fragmentEntryId,
					exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryVersionCTEventListener.class);

	private final Map<Long, Map<Long, Integer>>
		_fragmentEntryVersionCountByCtCollectionIdMap =
			new ConcurrentHashMap<>();

	@Reference
	private FragmentEntryVersionPersistence _fragmentEntryVersionPersistence;

}