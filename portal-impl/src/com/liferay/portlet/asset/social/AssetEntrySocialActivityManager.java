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

package com.liferay.portlet.asset.social;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.social.BaseSocialActivityManager;
import com.liferay.social.kernel.service.SocialActivityLocalService;

/**
 * @author     Adolfo Pérez
 * @deprecated As of Judson (7.1.x), moved to {@link
 *             com.liferay.asset.web.internal.social.AssetEntrySocialActivityManager}
 */
@Deprecated
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

	@BeanReference(type = SocialActivityLocalService.class)
	protected SocialActivityLocalService socialActivityLocalService;

}