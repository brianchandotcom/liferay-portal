/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.exception;

import com.liferay.layout.page.template.constants.LayoutPageTemplateCollectionTypeConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class
	LayoutPageTemplateCollectionLayoutPageTemplateCollectionKeyException
		extends PortalException {

	public int getLayoutPageTemplateCollectionType() {
		return _layoutPageTemplateCollectionType;
	}

	public static class MustNotBeDuplicate
		extends LayoutPageTemplateCollectionLayoutPageTemplateCollectionKeyException {

		public MustNotBeDuplicate(
			long groupId, String layoutPageTemplateCollectionKey,
			int layoutPageTemplateCollectionType) {

			super(
				StringBundler.concat(
					"Duplicate layout page template collection for group ",
					groupId, " with layout page template collection key ",
					layoutPageTemplateCollectionKey),
				layoutPageTemplateCollectionType);
		}

	}

	public static class MustNotContainInvalidCharacters
		extends LayoutPageTemplateCollectionLayoutPageTemplateCollectionKeyException {

		public MustNotContainInvalidCharacters(
			String layoutPageTemplateCollectionKey,
			int layoutPageTemplateCollectionType) {

			super(
				StringBundler.concat(
					"Layout page template collection key ",
					layoutPageTemplateCollectionKey,
					" must contain only alphanumeric characters, dashes, and ",
					"underscores"),
				layoutPageTemplateCollectionType);
		}

	}

	public static class MustNotExceedMaximumSize
		extends LayoutPageTemplateCollectionLayoutPageTemplateCollectionKeyException {

		public MustNotExceedMaximumSize(
			String layoutPageTemplateCollectionKey,
			int layoutPageTemplateCollectionKeyMaxSize,
			int layoutPageTemplateCollectionType) {

			super(
				StringBundler.concat(
					"Layout page template collection key ",
					layoutPageTemplateCollectionKey, " must have fewer than ",
					layoutPageTemplateCollectionKeyMaxSize, " characters"),
				layoutPageTemplateCollectionType);
		}

	}

	private LayoutPageTemplateCollectionLayoutPageTemplateCollectionKeyException() {
		_layoutPageTemplateCollectionType =
			LayoutPageTemplateCollectionTypeConstants.BASIC;
	}

	private LayoutPageTemplateCollectionLayoutPageTemplateCollectionKeyException(
		String msg) {

		super(msg);

		_layoutPageTemplateCollectionType =
			LayoutPageTemplateCollectionTypeConstants.BASIC;
	}

	private LayoutPageTemplateCollectionLayoutPageTemplateCollectionKeyException(
		String msg, int layoutPageTemplateCollectionType) {

		super(msg);

		_layoutPageTemplateCollectionType = layoutPageTemplateCollectionType;
	}

	private LayoutPageTemplateCollectionLayoutPageTemplateCollectionKeyException(
		String msg, Throwable throwable) {

		super(msg, throwable);

		_layoutPageTemplateCollectionType =
			LayoutPageTemplateCollectionTypeConstants.BASIC;
	}

	private LayoutPageTemplateCollectionLayoutPageTemplateCollectionKeyException(
		Throwable throwable) {

		super(throwable);

		_layoutPageTemplateCollectionType =
			LayoutPageTemplateCollectionTypeConstants.BASIC;
	}

	private final int _layoutPageTemplateCollectionType;

}