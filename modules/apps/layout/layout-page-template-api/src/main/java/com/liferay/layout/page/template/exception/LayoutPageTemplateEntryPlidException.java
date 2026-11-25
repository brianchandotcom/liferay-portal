/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutPageTemplateEntryPlidException extends PortalException {

	public LayoutPageTemplateEntryPlidException() {
		plid = 0;
	}

	public LayoutPageTemplateEntryPlidException(long groupId, long plid) {
		super(
			String.format(
				"Layout %s does not belong to group %s", plid, groupId));

		this.plid = plid;
	}

	public LayoutPageTemplateEntryPlidException(String msg) {
		super(msg);

		plid = 0;
	}

	public LayoutPageTemplateEntryPlidException(
		String msg, Throwable throwable) {

		super(msg, throwable);

		plid = 0;
	}

	public LayoutPageTemplateEntryPlidException(Throwable throwable) {
		super(throwable);

		plid = 0;
	}

	public final long plid;

}