/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.exception;

import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutPageTemplateEntryDefaultTemplateException
	extends PortalException {

	public LayoutPageTemplateEntryDefaultTemplateException() {
		_type = LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE;
	}

	public LayoutPageTemplateEntryDefaultTemplateException(int type) {
		super(
			"The default Layout Page Template Entry must be published first.");

		_type = type;
	}

	public LayoutPageTemplateEntryDefaultTemplateException(String msg) {
		super(msg);

		_type = LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE;
	}

	public LayoutPageTemplateEntryDefaultTemplateException(
		String msg, int type) {

		super(msg);

		_type = type;
	}

	public LayoutPageTemplateEntryDefaultTemplateException(
		String msg, Throwable throwable) {

		super(msg, throwable);

		_type = LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE;
	}

	public LayoutPageTemplateEntryDefaultTemplateException(
		Throwable throwable) {

		super(throwable);

		_type = LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE;
	}

	public int getType() {
		return _type;
	}

	private final int _type;

}