/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.exception;

import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutPageTemplateEntryLayoutPageTemplateEntryKeyException
	extends PortalException {

	public int getType() {
		return _type;
	}

	public static class MustNotBeDuplicate
		extends LayoutPageTemplateEntryLayoutPageTemplateEntryKeyException {

		public MustNotBeDuplicate(
			long groupId, String layoutPageTemplateEntryKey, int type) {

			super(
				StringBundler.concat(
					"Duplicate layout page template entry for group ", groupId,
					" with layout page template entry key ",
					layoutPageTemplateEntryKey),
				type);
		}

	}

	public static class MustNotContainInvalidCharacters
		extends LayoutPageTemplateEntryLayoutPageTemplateEntryKeyException {

		public MustNotContainInvalidCharacters(
			String layoutPageTemplateEntryKey, int type) {

			super(
				StringBundler.concat(
					"Layout page template entry key ",
					layoutPageTemplateEntryKey,
					" must contain only alphanumeric characters, dashes, and ",
					"underscores"),
				type);
		}

	}

	public static class MustNotExceedMaximumSize
		extends LayoutPageTemplateEntryLayoutPageTemplateEntryKeyException {

		public MustNotExceedMaximumSize(
			String layoutPageTemplateEntryKey,
			int layoutPageTemplateEntryKeyMaxSize, int type) {

			super(
				StringBundler.concat(
					"Layout page template entry key ",
					layoutPageTemplateEntryKey, " must have fewer than ",
					layoutPageTemplateEntryKeyMaxSize, " characters"),
				type);
		}

	}

	private LayoutPageTemplateEntryLayoutPageTemplateEntryKeyException() {
		_type = LayoutPageTemplateEntryTypeConstants.BASIC;
	}

	private LayoutPageTemplateEntryLayoutPageTemplateEntryKeyException(
		String msg) {

		super(msg);

		_type = LayoutPageTemplateEntryTypeConstants.BASIC;
	}

	private LayoutPageTemplateEntryLayoutPageTemplateEntryKeyException(
		String msg, int type) {

		super(msg);

		_type = type;
	}

	private LayoutPageTemplateEntryLayoutPageTemplateEntryKeyException(
		String msg, Throwable throwable) {

		super(msg, throwable);

		_type = LayoutPageTemplateEntryTypeConstants.BASIC;
	}

	private LayoutPageTemplateEntryLayoutPageTemplateEntryKeyException(
		Throwable throwable) {

		super(throwable);

		_type = LayoutPageTemplateEntryTypeConstants.BASIC;
	}

	private final int _type;

}