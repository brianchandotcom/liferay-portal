/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.web.internal.social;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.portal.kernel.social.BaseSocialActivityManager;
import com.liferay.portal.kernel.social.SocialActivityManager;
import com.liferay.social.kernel.service.SocialActivityLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.asset.kernel.model.AssetEntry",
	service = SocialActivityManager.class
)
public class AssetEntrySocialActivityManager
	extends BaseSocialActivityManager<AssetEntry> {

	@Override
	protected String getClassName(AssetEntry assetEntry) {
		return assetEntry.getClassName();
	}

	@Override
	protected long getPrimaryKey(AssetEntry assetEntry) {
		return assetEntry.getClassPK();
	}

	@Override
	protected SocialActivityLocalService getSocialActivityLocalService() {
		return socialActivityLocalService;
	}

	@Reference
	protected SocialActivityLocalService socialActivityLocalService;

}