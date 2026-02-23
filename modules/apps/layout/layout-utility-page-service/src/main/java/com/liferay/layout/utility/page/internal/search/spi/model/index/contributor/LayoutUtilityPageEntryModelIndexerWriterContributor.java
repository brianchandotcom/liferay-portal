/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.internal.search.spi.model.index.contributor;

import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author Juan Pablo Montero
 */
public class LayoutUtilityPageEntryModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<LayoutUtilityPageEntry> {

	public LayoutUtilityPageEntryModelIndexerWriterContributor(
		LayoutUtilityPageEntryLocalService layoutUtilityPageEntryLocalService) {

		_layoutUtilityPageEntryLocalService =
			layoutUtilityPageEntryLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _layoutUtilityPageEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	private final LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

}