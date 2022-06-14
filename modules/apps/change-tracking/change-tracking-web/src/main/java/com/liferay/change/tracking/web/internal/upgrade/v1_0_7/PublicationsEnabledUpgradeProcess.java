/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.change.tracking.web.internal.upgrade.v1_0_7;

import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.model.CTPreferencesTable;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.change.tracking.web.internal.configuration.helper.CTSettingsConfigurationHelper;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

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
				ctPreferences.getCompanyId(), true, false);

			_ctPreferencesLocalService.deleteCTPreferences(ctPreferences);
		}
	}

	private final CTPreferencesLocalService _ctPreferencesLocalService;
	private final CTSettingsConfigurationHelper _ctSettingsConfigurationHelper;

}