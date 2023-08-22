/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.upgrade.v1_0_7;

import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.model.CTPreferencesTable;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.change.tracking.web.internal.configuration.helper.CTSettingsConfigurationHelper;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.List;

/**
 * @author David Truong
 */
public class PublicationsEnabledUpgradeProcess extends UpgradeProcess {

	public PublicationsEnabledUpgradeProcess(
		CTPreferencesLocalService ctPreferencesLocalService,
		CTSettingsConfigurationHelper ctSettingsConfigurationHelper) {

		_ctPreferencesLocalService = ctPreferencesLocalService;
		_ctSettingsConfigurationHelper = ctSettingsConfigurationHelper;
	}

	@Override
	protected void doUpgrade() throws Exception {
		for (CTPreferences ctPreferences :
				_ctPreferencesLocalService.<List<CTPreferences>>dslQuery(
					DSLQueryFactoryUtil.select(
						CTPreferencesTable.INSTANCE
					).from(
						CTPreferencesTable.INSTANCE
					).where(
						CTPreferencesTable.INSTANCE.userId.eq(0L)
					))) {

			_ctSettingsConfigurationHelper.save(
				ctPreferences.getCompanyId(),
				HashMapBuilder.<String, Object>put(
					"enabled", true
				).build());

			_ctPreferencesLocalService.deleteCTPreferences(ctPreferences);
		}
	}

	private final CTPreferencesLocalService _ctPreferencesLocalService;
	private final CTSettingsConfigurationHelper _ctSettingsConfigurationHelper;

}