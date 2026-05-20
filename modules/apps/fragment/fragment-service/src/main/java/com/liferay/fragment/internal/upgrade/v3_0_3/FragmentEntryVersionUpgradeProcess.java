/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.upgrade.v3_0_3;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Georgel Pop
 */
public class FragmentEntryVersionUpgradeProcess extends UpgradeProcess {

	public static final int MAX_VERSIONS = 10;

	@Override
	protected void doUpgrade() throws Exception {
		for (long fragmentEntryId : _getFragmentEntryIds()) {
			List<Long> fragmentEntryVersionIds = _getFragmentEntryVersionIds(
				fragmentEntryId);

			List<Long> fragmentEntryVersionIdsToDelete =
				fragmentEntryVersionIds.subList(
					MAX_VERSIONS, fragmentEntryVersionIds.size());

			for (int i = 0; i < fragmentEntryVersionIdsToDelete.size();
				 i += _BATCH_SIZE) {

				int end = Math.min(
					i + _BATCH_SIZE, fragmentEntryVersionIdsToDelete.size());

				runSQL(
					StringBundler.concat(
						"delete from FragmentEntryVersion where ",
						"fragmentEntryVersionId in (",
						StringUtil.merge(
							fragmentEntryVersionIdsToDelete.subList(i, end)),
						")"));
			}
		}
	}

	private List<Long> _getFragmentEntryIds() throws Exception {
		List<Long> fragmentEntryIds = new ArrayList<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select fragmentEntryId from FragmentEntryVersion group by " +
					"fragmentEntryId having count(*) > " + MAX_VERSIONS);

			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				fragmentEntryIds.add(resultSet.getLong("fragmentEntryId"));
			}
		}

		return fragmentEntryIds;
	}

	private List<Long> _getFragmentEntryVersionIds(long fragmentEntryId)
		throws Exception {

		List<Long> fragmentEntryVersionIds = new ArrayList<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select fragmentEntryVersionId from FragmentEntryVersion " +
					"where fragmentEntryId = ? order by version desc")) {

			preparedStatement.setLong(1, fragmentEntryId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					fragmentEntryVersionIds.add(
						resultSet.getLong("fragmentEntryVersionId"));
				}
			}
		}

		return fragmentEntryVersionIds;
	}

	private static final int _BATCH_SIZE = 1000;

}