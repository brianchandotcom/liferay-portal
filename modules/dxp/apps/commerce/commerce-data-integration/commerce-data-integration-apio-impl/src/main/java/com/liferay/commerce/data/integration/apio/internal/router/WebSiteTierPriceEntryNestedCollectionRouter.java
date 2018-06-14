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

package com.liferay.commerce.data.integration.apio.internal.router;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.commerce.data.integration.apio.identifiers.TierPriceEntryIdentifier;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryService;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose a {@link CommerceTierPriceEntry}
 * resources contained inside a <a href="http://schema.org/WebSite">WebSite</a>
 * through a web API. The resources are mapped from the internal model {@link
 * CommerceTierPriceEntry}.
 *
 * @author Zoltán Takács
 * @review
 */
@Component(immediate = true)
public class WebSiteTierPriceEntryNestedCollectionRouter implements
	NestedCollectionRouter<CommerceTierPriceEntry, Long,
		TierPriceEntryIdentifier, Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<CommerceTierPriceEntry, Long, Long>
		collectionRoutes(
			NestedCollectionRoutes.Builder<CommerceTierPriceEntry, Long, Long>
				builder) {

		return builder.addGetter(
			this::_getPageItems
		).build();
	}

	private PageItems<CommerceTierPriceEntry> _getPageItems(
		Pagination pagination, long groupId) {

		List<CommerceTierPriceEntry> commercePriceEntries =
			_commerceTierPriceEntryService.fetchCommerceTierPriceEntries(
				groupId, pagination.getStartPosition(),
				pagination.getEndPosition());

		int count =
			_commerceTierPriceEntryService.
				getCommerceTierPriceEntriesCountByGroupId(groupId);

		return new PageItems<>(commercePriceEntries, count);
	}

	@Reference
	private CommerceTierPriceEntryService _commerceTierPriceEntryService;

}