/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.exception;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class
	LayoutPageTemplateCollectionLayoutPageTemplateCollectionKeyException
		extends PortalException {

	public static class MustNotBeDuplicate
		extends LayoutPageTemplateCollectionLayoutPageTemplateCollectionKeyException {

		public MustNotBeDuplicate(
			long groupId, String layoutPageTemplateCollectionKey) {

			super(
				String.format(
					StringBundler.concat(
						"Duplicate layout page template for group ", groupId,
						" with layoutPageTemplateCollectionKey ",
						layoutPageTemplateCollectionKey)));
		}

	}

	public static class MustNotContainInvalidCharacters
		extends LayoutPageTemplateCollectionLayoutPageTemplateCollectionKeyException {

		public MustNotContainInvalidCharacters(Character character) {
			super(
				"Invalid character in layoutPageTemplateCollectionKey " +
					character);

			this.character = character;
		}

		public final Character character;

	}

	public static class MustNotExceedMaximumSize
		extends LayoutPageTemplateCollectionLayoutPageTemplateCollectionKeyException {

		public MustNotExceedMaximumSize(int maxLength) {
			super(
				"Maximum length of layoutPageTemplateCollectionKey exceeded " +
					maxLength);
		}

	}

	private LayoutPageTemplateCollectionLayoutPageTemplateCollectionKeyException() {
	}

	private LayoutPageTemplateCollectionLayoutPageTemplateCollectionKeyException(
		String msg) {

		super(msg);
	}

	private LayoutPageTemplateCollectionLayoutPageTemplateCollectionKeyException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	private LayoutPageTemplateCollectionLayoutPageTemplateCollectionKeyException(
		Throwable throwable) {

		super(throwable);
	}

}