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

package com.liferay.layout.apio.internal.resource;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.layout.apio.identifier.EmbeddedWebPageId;
import com.liferay.layout.apio.internal.util.LayoutResourceCollectionUtil;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.site.apio.identifier.WebSiteId;

import java.util.List;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true)
public class EmbeddedWebPageCollectionResource
	implements NestedCollectionResource
		<Layout, Long, EmbeddedWebPageId, Long, WebSiteId> {

	@Override
	public NestedCollectionRoutes<Layout, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<Layout, Long> builder) {

		return builder.addGetter(
			this::_getLayouts
		).build();
	}

	@Override
	public String getName() {
		return "embedded-web-pages";
	}

	@Override
	public ItemRoutes<Layout> itemRoutes(
		ItemRoutes.Builder<Layout, Long> builder) {

		return builder.addGetter(
			this::_getLayout
		).addRemover(
			this::_removeLayout, (credentials, plid) -> true
		).build();
	}

	@Override
	public Representor<Layout, Long> representor(
		Representor.Builder<Layout, Long> builder) {

		return builder.types(
			"EmbeddedWebPage"
		).identifier(
			Layout::getPlid
		).addBidirectionalModel(
			"webSite", "embeddedWebPages", WebSiteId.class, Layout::getGroupId
		).addLocalizedString(
			"breadcrumb", _layoutResourceCollectionUtil::getBreadcrumb
		).addDate(
			"dateCreated", Layout::getCreateDate
		).addDate(
			"dateModified", Layout::getModifiedDate
		).addDate(
			"datePublished", Layout::getLastPublishDate
		).addLocalizedString(
			"description",
			(layout, language) -> layout.getDescription(
				language.getPreferredLocale())
		).addString(
			"image", _layoutResourceCollectionUtil::getImageURL
		).addLocalizedString(
			"keywords",
			(layout, language) -> layout.getKeywords(
				language.getPreferredLocale())
		).addLocalizedString(
			"name",
			(layout, language) -> layout.getName(language.getPreferredLocale())
		).addLocalizedString(
			"title",
			(layout, language) -> layout.getTitle(language.getPreferredLocale())
		).addLocalizedString(
			"url",
			(layout, language) -> layout.getFriendlyURL(
				language.getPreferredLocale())
		).addString(
			"embeddedUrl",
			layout -> layout.getTypeSettingsProperty("embeddedLayoutURL")
		).build();
	}

	private Layout _getLayout(Long plid) {
		try {
			return _layoutLocalService.getLayout(plid);
		}
		catch (AuthException | PrincipalException e) {
			throw new NotAuthorizedException(e);
		}
		catch (NoSuchLayoutException nsle) {
			throw new NotFoundException("Unable to get layout " + plid, nsle);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private PageItems<Layout> _getLayouts(Pagination pagination, Long groupId) {
		List<Layout> layouts = _layoutService.getLayouts(
			groupId, LayoutConstants.TYPE_EMBEDDED,
			pagination.getStartPosition(), pagination.getEndPosition());

		int layoutsCount = _layoutService.getLayoutsCount(
			groupId, LayoutConstants.TYPE_EMBEDDED);

		return new PageItems<>(layouts, layoutsCount);
	}

	private Layout _removeLayout(Long plid) {
		try {
			return _layoutLocalService.deleteLayout(plid);
		}
		catch (AuthException | PrincipalException e) {
			throw new NotAuthorizedException(e);
		}
		catch (NoSuchLayoutException nsle) {
			throw new NotFoundException("Unable to get layout " + plid, nsle);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutResourceCollectionUtil _layoutResourceCollectionUtil;

	@Reference
	private LayoutService _layoutService;

}