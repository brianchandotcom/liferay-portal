/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model;

import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.servlet.filters.cache.CacheUtil;

/**
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class LayoutSetModelListener extends BaseModelListener<LayoutSet> {

	@Override
	public void onAfterRemove(LayoutSet layoutSet) {
		if (layoutSet == null) {
			return;
		}

		clearCache(layoutSet);
	}

	@Override
	public void onAfterUpdate(
		LayoutSet originalLayoutSet, LayoutSet layoutSet) {

		if (layoutSet == null) {
			return;
		}

		clearCache(layoutSet);
	}

	protected void clearCache(LayoutSet layoutSet) {
		if (!layoutSet.isPrivateLayout()) {
			CacheUtil.clearCache(layoutSet.getCompanyId());
		}
	}

}