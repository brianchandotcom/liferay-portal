/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.model.impl;

import com.liferay.portal.kernel.util.ScopeUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutSEOEntryImpl extends LayoutSEOEntryBaseImpl {

	@Override
	public long getOpenGraphImageFileEntryGroupId() {
		Long groupId = ScopeUtil.getItemGroupId(
			getCompanyId(), getOpenGraphImageFileEntryScopeERC(), getGroupId());

		if (groupId == null) {
			return 0;
		}

		return groupId;
	}

}