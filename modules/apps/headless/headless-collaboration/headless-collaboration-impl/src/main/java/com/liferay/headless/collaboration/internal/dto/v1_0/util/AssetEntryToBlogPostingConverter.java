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

package com.liferay.headless.collaboration.internal.dto.v1_0.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.headless.common.spi.osgi.AssetEntryToDTOConverter;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 * @author Víctor Galán
 */
@Component(
	property = "asset.entry.to.dto.converter.class.name=com.liferay.blogs.model.BlogsEntry",
	service = AssetEntryToDTOConverter.class
)
public class AssetEntryToBlogPostingConverter
	implements AssetEntryToDTOConverter {

	@Override
	public Object toDTO(
		AssetEntry assetEntry, AcceptLanguage acceptLanguage, UriInfo uriInfo) {

		try {
			return BlogPostingUtil.toBlogPosting(
				_assetCategoryLocalService, _assetTagLocalService,
				_blogsEntryService.getEntry(assetEntry.getClassPK()),
				_commentManager, _dlAppService, _dlURLHelper, _portal,
				_ratingsStatsLocalService, _userLocalService);
		}
		catch (Exception e) {
			throw new NotFoundException(e);
		}
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private BlogsEntryService _blogsEntryService;

	@Reference
	private CommentManager _commentManager;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private Portal _portal;

	@Reference
	private RatingsStatsLocalService _ratingsStatsLocalService;

	@Reference
	private UserLocalService _userLocalService;

}