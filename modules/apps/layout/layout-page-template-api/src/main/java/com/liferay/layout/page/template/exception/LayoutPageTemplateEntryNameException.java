/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.exception;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutPageTemplateEntryNameException extends PortalException {

	public LayoutPageTemplateEntryNameException() {
	}

	public LayoutPageTemplateEntryNameException(String msg) {
		super(msg);
	}

	public LayoutPageTemplateEntryNameException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public LayoutPageTemplateEntryNameException(Throwable throwable) {
		super(throwable);
	}

	public static class MustNotBeDuplicate
		extends LayoutPageTemplateEntryNameException {

		public MustNotBeDuplicate(long groupId, String name) {
			super(
				String.format(
					StringBundler.concat(
						"Duplicate layout page template for group ", groupId,
						" with name ", name)));
		}

	}

	public static class MustNotBeNull
		extends LayoutPageTemplateEntryNameException {

		public MustNotBeNull(long groupId) {
			super("Name must not be null for group " + groupId);
		}

	}

	public static class MustNotContainInvalidCharacters
		extends LayoutPageTemplateEntryNameException {

		public MustNotContainInvalidCharacters(char character) {
			super("Invalid character in name " + character);

			this.character = character;
		}

		public final char character;

	}

	public static class MustNotExceedMaximumSize
		extends LayoutPageTemplateEntryNameException {

		public MustNotExceedMaximumSize(int maxLength) {
			super("Maximum length of name exceeded " + maxLength);
		}

	}

}