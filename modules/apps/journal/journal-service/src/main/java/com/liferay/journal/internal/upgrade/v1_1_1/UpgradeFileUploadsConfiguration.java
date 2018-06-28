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

package com.liferay.journal.internal.upgrade.v1_1_1;

import com.liferay.journal.configuration.JournalFileUploadsConfiguration;
import com.liferay.portal.configuration.upgrade.PrefsPropsToConfigurationUpgradeHelper;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.KeyValuePair;

/**
 * @author Drew Brokke
 */
public class UpgradeFileUploadsConfiguration extends UpgradeProcess {

	public UpgradeFileUploadsConfiguration(
		PrefsPropsToConfigurationUpgradeHelper
			prefsPropsToConfigurationUpgradeHelper) {

		_prefsPropsToConfigurationUpgradeHelper =
			prefsPropsToConfigurationUpgradeHelper;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradeFileUploadsConfiguration();
	}

	protected void upgradeFileUploadsConfiguration() throws Exception {
		_prefsPropsToConfigurationUpgradeHelper.mapConfigurations(
			JournalFileUploadsConfiguration.class,
			new KeyValuePair(
				_OLD_KEY_JOURNAL_IMAGE_EXTENSIONS, "imageExtensions"),
			new KeyValuePair(
				_OLD_KEY_JOURNAL_IMAGE_SMALL_MAX_SIZE, "smallImageMaxSize"));
	}

	private static final String _OLD_KEY_JOURNAL_IMAGE_EXTENSIONS =
		"journal.image.extensions";

	private static final String _OLD_KEY_JOURNAL_IMAGE_SMALL_MAX_SIZE =
		"journal.image.small.max.size";

	private final PrefsPropsToConfigurationUpgradeHelper
		_prefsPropsToConfigurationUpgradeHelper;

}