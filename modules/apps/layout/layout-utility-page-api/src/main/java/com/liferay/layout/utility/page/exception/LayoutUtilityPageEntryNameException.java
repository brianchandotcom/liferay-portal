/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.exception;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutUtilityPageEntryNameException extends PortalException {

	public static class MustNotBeDuplicate
		extends LayoutUtilityPageEntryNameException {

		public MustNotBeDuplicate(long groupId, String name) {
			super(
				String.format(
					StringBundler.concat(
						"Duplicate utility page for group ", groupId,
						" with name ", name)));
		}

	}

	public static class MustNotBeNull
		extends LayoutUtilityPageEntryNameException {

		public MustNotBeNull(long groupId) {
			super("Name must not be null for group " + groupId);
		}

	}

	public static class MustNotContainInvalidCharacters
		extends LayoutUtilityPageEntryNameException {

		public MustNotContainInvalidCharacters(char character) {
			super("Invalid character in name " + character);

			this.character = character;
		}

		public final char character;

	}

	public static class MustNotExceedMaximumSize
		extends LayoutUtilityPageEntryNameException {

		public MustNotExceedMaximumSize(int maxLength) {
			super("Maximum length of name exceeded " + maxLength);
		}

	}

	private LayoutUtilityPageEntryNameException(String msg) {
		super(msg);
	}

}