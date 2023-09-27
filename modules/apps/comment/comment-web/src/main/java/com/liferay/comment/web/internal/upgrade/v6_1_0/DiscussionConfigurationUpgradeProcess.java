/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.comment.web.internal.upgrade.v6_1_0;

import com.liferay.comment.configuration.CommentGroupServiceConfiguration;
import com.liferay.comment.constants.LegacyDiscussionPropsKeys;
import com.liferay.portal.configuration.upgrade.PrefsPropsToConfigurationUpgradeHelper;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.KeyValuePair;

/**
 * @author Adolfo Pérez
 */
public class DiscussionConfigurationUpgradeProcess extends UpgradeProcess {

	public DiscussionConfigurationUpgradeProcess(
		PrefsPropsToConfigurationUpgradeHelper
			prefsPropsToConfigurationUpgradeHelper) {

		_prefsPropsToConfigurationUpgradeHelper =
			prefsPropsToConfigurationUpgradeHelper;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_prefsPropsToConfigurationUpgradeHelper.mapConfigurations(
			CommentGroupServiceConfiguration.class,
			new KeyValuePair(
				LegacyDiscussionPropsKeys.
					DISCUSSION_COMMENTS_ALWAYS_EDITABLE_BY_OWNER,
				"alwaysEditableByOwner"),
			new KeyValuePair(
				LegacyDiscussionPropsKeys.DISCUSSION_SUBSCRIBE, "subscribe"));
	}

	private final PrefsPropsToConfigurationUpgradeHelper
		_prefsPropsToConfigurationUpgradeHelper;

}