/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.layout.page.template.web.internal.servlet.taglib.constants;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class AnalyticsRenderFragmentLayoutConstants {

	public static final List<String> analyticsAssetTypes = List.of(
		BlogsEntry.class.getName(), JournalArticle.class.getName(),
		FileEntry.class.getName());

}