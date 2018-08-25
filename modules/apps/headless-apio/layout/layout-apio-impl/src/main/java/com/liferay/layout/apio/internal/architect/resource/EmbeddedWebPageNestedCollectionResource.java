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

package com.liferay.layout.apio.internal.architect.resource;

import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.layout.apio.architect.identifier.EmbeddedWebPageIdentifier;
import com.liferay.layout.apio.internal.util.LayoutResourceCollectionUtil;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true)
public class EmbeddedWebPageNestedCollectionResource
	implements NestedCollectionResource
		<Layout, Long, EmbeddedWebPageIdentifier, Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<Layout, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<Layout, Long, Long> builder) {

		return builder.addGetter(
			this::_getLayouts
		).build();
	}

	@Override
	public String getName() {
		return "embedded-web-page";
	}

	@Override
	public ItemRoutes<Layout, Long> itemRoutes(
		ItemRoutes.Builder<Layout, Long> builder) {

		return builder.addGetter(
			_layoutLocalService::getLayout
		).addRemover(
			idempotent(_layoutLocalService::deleteLayout),
			_hasPermission::forDeleting
		).build();
	}

	@Override
	public Representor<Layout> representor(
		Representor.Builder<Layout, Long> builder) {

		return builder.types(
			"EmbeddedWebPage"
		).identifier(
			Layout::getPlid
		).addBidirectionalModel(
			"webSite", "embeddedWebPages", WebSiteIdentifier.class,
			Layout::getGroupId
		).addDate(
			"dateCreated", Layout::getCreateDate
		).addDate(
			"dateModified", Layout::getModifiedDate
		).addLocalizedStringByLocale(
			"breadcrumb", LayoutResourceCollectionUtil::getBreadcrumb
		).addLocalizedStringByLocale(
			"description", Layout::getDescription
		).addLocalizedStringByLocale(
			"keywords", Layout::getKeywords
		).addLocalizedStringByLocale(
			"name", Layout::getName
		).addLocalizedStringByLocale(
			"title", Layout::getTitle
		).addLocalizedStringByLocale(
			"url", Layout::getFriendlyURL
		).addString(
			"embeddedUrl",
			layout -> layout.getTypeSettingsProperty("embeddedLayoutURL")
		).addString(
			"image", LayoutResourceCollectionUtil::getImageURL
		).addStringList(
			"availableLanguages",
			layout -> Arrays.asList(layout.getAvailableLanguageIds())
		).build();
	}

	private PageItems<Layout> _getLayouts(Pagination pagination, long groupId) {
		List<Layout> layouts = _layoutService.getLayouts(
			groupId, LayoutConstants.TYPE_EMBEDDED,
			pagination.getStartPosition(), pagination.getEndPosition());
		int count = _layoutService.getLayoutsCount(
			groupId, LayoutConstants.TYPE_EMBEDDED);

		return new PageItems<>(layouts, count);
	}

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.model.Layout)"
	)
	private HasPermission<Long> _hasPermission;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutService _layoutService;

}