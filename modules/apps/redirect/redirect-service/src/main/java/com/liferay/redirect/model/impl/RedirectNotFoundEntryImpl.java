/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.model.impl;

import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.view.count.ViewCountManagerUtil;
import com.liferay.redirect.model.RedirectNotFoundEntry;

/**
 * @author Brian Wing Shun Chan
 */
public class RedirectNotFoundEntryImpl extends RedirectNotFoundEntryBaseImpl {

	@Override
	public long getHits() {
		return getRequestCount();
	}

	@Override
	public long getRequestCount() {
		return ViewCountManagerUtil.getViewCount(
			getCompanyId(),
			ClassNameLocalServiceUtil.getClassNameId(
				RedirectNotFoundEntry.class),
			getPrimaryKey());
	}

}