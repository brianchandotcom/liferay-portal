/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.search.spi.model.index.contributor;

import com.liferay.message.boards.model.MBCategory;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Javier Gamarra
 */
@Component(
	property = "indexer.class.name=com.liferay.message.boards.model.MBCategory",
	service = ModelDocumentContributor.class
)
public class MBCategoryModelDocumentContributor
	implements ModelDocumentContributor<MBCategory> {

	@Override
	public void contribute(Document document, MBCategory mbCategory) {
		document.addKeyword(
			Field.ASSET_PARENT_CATEGORY_ID, mbCategory.getParentCategoryId());
		document.addText(Field.DESCRIPTION, mbCategory.getDescription());
		document.addKeyword(Field.NAME, mbCategory.getName());
	}

}