/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.upgrade.v3_0_3;

import com.liferay.fragment.internal.constants.FragmentEntryVersionConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Georgel Pop
 */
public class FragmentEntryVersionUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		Map<Long, List<Long>> fragmentEntryIdsByCtCollectionId =
			_getFragmentEntryIdsByCtCollectionId();

		for (Map.Entry<Long, List<Long>> entry :
				fragmentEntryIdsByCtCollectionId.entrySet()) {

			long ctCollectionId = entry.getKey();

			for (Long fragmentEntryId : entry.getValue()) {
				List<Long> fragmentEntryVersionIds =
					_getFragmentEntryVersionIds(
						ctCollectionId, fragmentEntryId);

				List<Long> fragmentEntryVersionIdsToDelete =
					fragmentEntryVersionIds.subList(
						FragmentEntryVersionConstants.
							FRAGMENT_ENTRY_VERSIONS_COUNT_MAX,
						fragmentEntryVersionIds.size());

				int batchSize = 1000;
				int size = fragmentEntryVersionIdsToDelete.size();

				for (int i = 0; i < size; i += batchSize) {
					int end = Math.min(i + batchSize, size);

					runSQL(
						StringBundler.concat(
							"delete from FragmentEntryVersion where ",
							"fragmentEntryVersionId in (",
							StringUtil.merge(
								fragmentEntryVersionIdsToDelete.subList(
									i, end)),
							")"));
				}
			}
		}
	}

	private Map<Long, List<Long>> _getFragmentEntryIdsByCtCollectionId()
		throws Exception {

		Map<Long, List<Long>> fragmentEntryIdsByCtCollectionId =
			new HashMap<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select ctCollectionId, fragmentEntryId from ",
					"FragmentEntryVersion group by ctCollectionId, ",
					"fragmentEntryId having count(*) > ",
					FragmentEntryVersionConstants.
						FRAGMENT_ENTRY_VERSIONS_COUNT_MAX));

			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				List<Long> fragmentEntryIds =
					fragmentEntryIdsByCtCollectionId.computeIfAbsent(
						resultSet.getLong("ctCollectionId"),
						ctCollectionId -> new ArrayList<>());

				fragmentEntryIds.add(resultSet.getLong("fragmentEntryId"));
			}
		}

		return fragmentEntryIdsByCtCollectionId;
	}

	private List<Long> _getFragmentEntryVersionIds(
			long ctCollectionId, long fragmentEntryId)
		throws Exception {

		List<Long> fragmentEntryVersionIds = new ArrayList<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select fragmentEntryVersionId from FragmentEntryVersion " +
					"where ctCollectionId = ? and fragmentEntryId = ? order " +
						"by version desc")) {

			preparedStatement.setLong(1, ctCollectionId);
			preparedStatement.setLong(2, fragmentEntryId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					fragmentEntryVersionIds.add(
						resultSet.getLong("fragmentEntryVersionId"));
				}
			}
		}

		return fragmentEntryVersionIds;
	}

}