/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.web.internal.util;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = BlogsEntryUtil.class)
public class BlogsEntryUtil {

	public static String getDisplayTitle(
		ResourceBundle resourceBundle, BlogsEntry entry) {

		if (Validator.isNull(entry.getTitle())) {
			return LanguageUtil.get(resourceBundle, "untitled-entry");
		}

		return entry.getTitle();
	}

}