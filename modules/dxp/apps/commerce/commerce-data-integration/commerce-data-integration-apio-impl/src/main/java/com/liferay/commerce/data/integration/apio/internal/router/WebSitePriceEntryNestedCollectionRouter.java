/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.data.integration.apio.internal.router;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.commerce.data.integration.apio.identifiers.PriceEntryIdentifier;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose a {@link CommercePriceEntry}
 * resources contained inside a <a href="http://schema.org/WebSite">WebSite</a>
 * through a web API. The resources are mapped from the internal model {@link
 * CommercePriceEntry}.
 *
 * @author Zoltán Takács
 * @review
 */
@Component(immediate = true)
public class WebSitePriceEntryNestedCollectionRouter implements
	NestedCollectionRouter<CommercePriceEntry, Long, PriceEntryIdentifier,
		Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<CommercePriceEntry, Long, Long>
		collectionRoutes(
			NestedCollectionRoutes.Builder<CommercePriceEntry, Long, Long>
				builder) {

		return builder.addGetter(
			this::_getPageItems
		).build();
	}

	private PageItems<CommercePriceEntry> _getPageItems(
		Pagination pagination, long groupId) {

		List<CommercePriceEntry> commercePriceEntries =
			_commercePriceEntryService.fetchCommercePriceEntries(
				groupId, pagination.getStartPosition(),
				pagination.getEndPosition());

		int count =
			_commercePriceEntryService.getCommercePriceEntriesCountByGroupId(
				groupId);

		return new PageItems<>(commercePriceEntries, count);
	}

	@Reference
	private CommercePriceEntryService _commercePriceEntryService;

}