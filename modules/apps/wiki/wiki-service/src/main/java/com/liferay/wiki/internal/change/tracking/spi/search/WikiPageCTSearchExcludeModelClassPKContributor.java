/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.internal.change.tracking.spi.search;

import com.liferay.change.tracking.spi.search.CTSearchExcludeModelClassPKContributor;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageTable;
import com.liferay.wiki.service.WikiPageLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

		List<WikiPage> wikiPages = _wikiPageLocalService.dslQuery(
			DSLQueryFactoryUtil.select(
				WikiPageTable.INSTANCE
			).from(
				WikiPageTable.INSTANCE
			).where(
				WikiPageTable.INSTANCE.ctCollectionId.neq(
					CTCollectionThreadLocal.getCTCollectionId()
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