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

package com.liferay.tag.apio.internal.router;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagService;
import com.liferay.blog.apio.architect.identifier.BlogPostingIdentifier;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.tag.apio.identifier.TagIdentifier;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

/**
 * Provides the information necessary to expose the <a
 * href="http://schema.org/Tag">Tag</a> resources contained inside a <a
 * href="http://schema.org/BlogPosting">BlogPosting</a> through a web API. The
 * resources are mapped from the internal model {@link AssetTag} and {@code
 * BlogsEntry}.
 *
 * @author Ibai Ruiz
 * @review
 */
@Component(immediate = true)
public class BlogPostingTagNestedCollectionRouter implements
	NestedCollectionRouter<AssetTag, Long, TagIdentifier, Long, BlogPostingIdentifier>
{

	@Override
	public NestedCollectionRoutes<AssetTag, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<AssetTag, Long, Long> builder) {
		return builder.addGetter(
			this::_getPageItems
		).build();
	}

	private PageItems<AssetTag> _getPageItems(
		Pagination pagination, long blogEntryId)
		throws PortalException {

		List<AssetTag> tags = _assetTagService.getTags(
			BlogsEntry.class.getName(), blogEntryId,
			pagination.getStartPosition(), pagination.getEndPosition());

		int count = _assetTagService.getTagsCount(
			BlogsEntry.class.getName(), blogEntryId);

		return new PageItems<>(tags, count);
	}

	@Reference
	private AssetTagService _assetTagService;
}
