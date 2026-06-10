/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.internal.upgrade.v2_4_0;

import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

/**
 * @author Alicia García
 */
public class TrashEntriesMaxAgeUpgradeProcess extends UpgradeProcess {

	public TrashEntriesMaxAgeUpgradeProcess(
		CompanyLocalService companyLocalService,
		DepotEntryLocalService depotEntryLocalService,
		GroupLocalService groupLocalService) {

		_companyLocalService = companyLocalService;
		_depotEntryLocalService = depotEntryLocalService;
		_groupLocalService = groupLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompanyId(
			companyId -> {
				int trashEntriesMaxAgeCompany = PrefsPropsUtil.getInteger(
					companyId, PropsKeys.TRASH_ENTRIES_MAX_AGE,
					PropsValues.TRASH_ENTRIES_MAX_AGE);

				if (trashEntriesMaxAgeCompany <= 0) {
					return;
				}

				List<DepotEntry> depotEntries =
					_depotEntryLocalService.getDepotEntries(
						companyId, DepotConstants.TYPE_SPACE);

				for (DepotEntry depotEntry : depotEntries) {
					Group group = _groupLocalService.fetchGroup(
						depotEntry.getGroupId());

					if (group == null) {
						continue;
					}

					_updateTrashEntriesMaxAge(group, trashEntriesMaxAgeCompany);
				}
			});
	}

	private void _updateTrashEntriesMaxAge(
			Group group, int trashEntriesMaxAgeCompany)
		throws Exception {

		UnicodeProperties typeSettingsUnicodeProperties =
			group.getTypeSettingsProperties();

		String trashEntriesMaxAgeGroup =
			typeSettingsUnicodeProperties.getProperty("trashEntriesMaxAge");

		if (Validator.isNotNull(trashEntriesMaxAgeGroup) &&
			(GetterUtil.getInteger(trashEntriesMaxAgeGroup) > 0)) {

			return;
		}

		typeSettingsUnicodeProperties.setProperty(
			"trashEntriesMaxAge", String.valueOf(trashEntriesMaxAgeCompany));

		_groupLocalService.updateGroup(
			group.getGroupId(), typeSettingsUnicodeProperties.toString());

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Set trash entries max age for group ID " + group.getGroupId());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TrashEntriesMaxAgeUpgradeProcess.class);

	private final CompanyLocalService _companyLocalService;
	private final DepotEntryLocalService _depotEntryLocalService;
	private final GroupLocalService _groupLocalService;

}