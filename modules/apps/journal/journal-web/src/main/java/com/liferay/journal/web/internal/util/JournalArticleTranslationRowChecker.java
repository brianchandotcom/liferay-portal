/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.journal.web.internal.util;

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;

import javax.portlet.PortletResponse;

/**
 * @author Pavel Savinov
 */
public class JournalArticleTranslationRowChecker
	extends EmptyOnClickRowChecker {

	public JournalArticleTranslationRowChecker(
		PortletResponse portletResponse) {

		super(portletResponse);
	}

	@Override
	public boolean isChecked(Object object) {
		if (object instanceof JournalArticleTranslation) {
			JournalArticleTranslation articleTranslation =
				(JournalArticleTranslation)object;

			if (articleTranslation.isDefault()) {
				return false;
			}
		}

		return super.isDisabled(object);
	}

	@Override
	public boolean isDisabled(Object object) {
		if (object instanceof JournalArticleTranslation) {
			JournalArticleTranslation articleTranslation =
				(JournalArticleTranslation)object;

			return articleTranslation.isDefault();
		}

		return super.isDisabled(object);
	}

}