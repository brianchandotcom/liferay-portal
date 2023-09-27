/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.microblogs.model.impl;

import com.liferay.microblogs.model.MicroblogsEntry;
import com.liferay.microblogs.service.MicroblogsEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class MicroblogsEntryImpl extends MicroblogsEntryBaseImpl {

	@Override
	public long fetchParentMicroblogsEntryUserId() {
		if (getMicroblogsEntryId() == getParentMicroblogsEntryId()) {
			return getUserId();
		}

		MicroblogsEntry microblogsEntry =
			MicroblogsEntryLocalServiceUtil.fetchMicroblogsEntry(
				getParentMicroblogsEntryId());

		if (microblogsEntry == null) {
			return 0;
		}

		return microblogsEntry.getUserId();
	}

	@Override
	public long getParentMicroblogsEntryUserId() throws PortalException {
		if (getMicroblogsEntryId() == getParentMicroblogsEntryId()) {
			return getUserId();
		}

		MicroblogsEntry microblogsEntry =
			MicroblogsEntryLocalServiceUtil.getMicroblogsEntry(
				getParentMicroblogsEntryId());

		return microblogsEntry.getUserId();
	}

}