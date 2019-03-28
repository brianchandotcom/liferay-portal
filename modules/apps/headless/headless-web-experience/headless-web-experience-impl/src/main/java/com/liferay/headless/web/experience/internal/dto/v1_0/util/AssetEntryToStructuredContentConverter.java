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

package com.liferay.headless.web.experience.internal.dto.v1_0.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.headless.common.spi.osgi.AssetEntryToDTOConverter;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.util.JournalConverter;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 * @author Víctor Galán
 */
@Component(
	property = "asset.entry.to.dto.converter.class.name=com.liferay.journal.model.JournalArticle",
	service = AssetEntryToDTOConverter.class
)
public class AssetEntryToStructuredContentConverter
	implements AssetEntryToDTOConverter {

	@Override
	public Object toDTO(
		AssetEntry assetEntry, AcceptLanguage acceptLanguage, UriInfo uriInfo) {

		try {
			return StructuredContentUtil.toStructuredContent(
				_journalArticleService.getLatestArticle(
					assetEntry.getClassPK()),
				acceptLanguage, _assetCategoryLocalService,
				_assetTagLocalService, _commentManager, _dlAppService,
				_dlurlHelper, _fieldsToDDMFormValuesConverter,
				_journalArticleService, _journalConverter, _layoutLocalService,
				_portal, _ratingsStatsLocalService, uriInfo, _userLocalService);
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
	private CommentManager _commentManager;

	@Context
	private HttpServletRequest _contextHttpServletRequest;

	@Context
	private HttpServletResponse _contextHttpServletResponse;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlurlHelper;

	@Reference
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private JournalConverter _journalConverter;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private RatingsStatsLocalService _ratingsStatsLocalService;

	@Reference
	private UserLocalService _userLocalService;

}