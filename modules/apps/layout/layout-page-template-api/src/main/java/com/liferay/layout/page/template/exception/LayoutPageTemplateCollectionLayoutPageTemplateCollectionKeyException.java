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
				StringBundler.concat(
					"Duplicate layout page template for group ", groupId,
					" with layout page template collection key ",
					layoutPageTemplateCollectionKey));
		}

	}

	public static class MustNotContainInvalidCharacters
		extends LayoutPageTemplateCollectionLayoutPageTemplateCollectionKeyException {

		public MustNotContainInvalidCharacters(
			String layoutPageTemplateCollectionKey) {

			super(
				StringBundler.concat(
					"Layout page template collection key ",
					layoutPageTemplateCollectionKey,
					" must contain only alphanumeric characters, dashes, and ",
					"underscores"));
		}

	}

	public static class MustNotExceedMaximumSize
		extends LayoutPageTemplateCollectionLayoutPageTemplateCollectionKeyException {

		public MustNotExceedMaximumSize(
			String layoutPageTemplateCollectionKey,
			int layoutPageTemplateCollectionKeyMaxSize) {

			super(
				StringBundler.concat(
					"Layout page template collection key ",
					layoutPageTemplateCollectionKey, " must have fewer than ",
					layoutPageTemplateCollectionKeyMaxSize, " characters"));
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