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

package com.liferay.wiki.internal.change.tracking.spi.search;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.spi.search.CTSearchExcludeModelClassPKContributor;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageTable;
import com.liferay.wiki.service.WikiPageLocalService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

/**
 * @author David Truong
 */
@Component(service = CTSearchExcludeModelClassPKContributor.class)
public class WikiPageCTSearchExcludeModelClassPKContributor
	implements CTSearchExcludeModelClassPKContributor {

	@Override
	public void contribute(
		String className, long classPK,
		List<Long> excludeProductionModelClassPKs) {

		if (!className.equals(WikiPage.class.getName())) {
			return;
		}

		List<WikiPage> wikiPages =
			_wikiPageLocalService.dslQuery(
				DSLQueryFactoryUtil.select(
					WikiPageTable.INSTANCE
				).from(
					WikiPageTable.INSTANCE
				).where(
					WikiPageTable.INSTANCE.ctCollectionId.eq(
						CTConstants.CT_COLLECTION_ID_PRODUCTION
					).and(
						WikiPageTable.INSTANCE.resourcePrimKey.in(
							DSLQueryFactoryUtil.select(
								WikiPageTable.INSTANCE.resourcePrimKey
							).from(
								WikiPageTable.INSTANCE
							).where(
								WikiPageTable.INSTANCE.pageId.eq(classPK)
							))
					)
				));

		for (WikiPage wikiPage : wikiPages) {
			excludeProductionModelClassPKs.add(wikiPage.getPageId());
		}
	}

	@Reference
	private WikiPageLocalService _wikiPageLocalService;

}