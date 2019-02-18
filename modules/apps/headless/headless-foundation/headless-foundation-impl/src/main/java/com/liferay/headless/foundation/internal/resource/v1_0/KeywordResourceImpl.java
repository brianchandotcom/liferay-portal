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

package com.liferay.headless.foundation.internal.resource.v1_0;

import com.liferay.asset.kernel.exception.AssetTagNameException;
import com.liferay.asset.kernel.exception.DuplicateTagException;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagService;
import com.liferay.headless.foundation.dto.v1_0.Keyword;
import com.liferay.headless.foundation.internal.dto.v1_0.KeywordImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.foundation.resource.v1_0.KeywordResource;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portlet.asset.util.comparator.AssetTagNameComparator;

import javax.ws.rs.ClientErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/keyword.properties",
	scope = ServiceScope.PROTOTYPE, service = KeywordResource.class
)
public class KeywordResourceImpl extends BaseKeywordResourceImpl {

	@Override
	public boolean deleteKeyword(Long keywordId) throws Exception {
		_assetTagService.deleteTag(keywordId);

		return true;
	}

	@Override
	public Page<Keyword> getContentSpaceKeywordsPage(
		Long contentSpaceId, Pagination pagination) {

		return Page.of(
			transform(
				_assetTagService.getGroupTags(
					contentSpaceId, pagination.getStartPosition(),
					pagination.getEndPosition(), new AssetTagNameComparator()),
				this::_toKeyword),
			pagination, _assetTagService.getGroupTagsCount(contentSpaceId));
	}

	@Override
	public Keyword getKeyword(Long keywordId) throws Exception {
		return _toKeyword(_assetTagService.getTag(keywordId));
	}

	@Override
	public Keyword postContentSpaceKeyword(Long contentSpaceId, Keyword keyword)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(contentSpaceId);

		try {
			return _toKeyword(
				_assetTagService.addTag(
					contentSpaceId, keyword.getName(), serviceContext));
		}
		catch (AssetTagNameException atne) {
			throw new ClientErrorException(
				"Name contains invalid characters", 422, atne);
		}
		catch (DuplicateTagException dte) {
			throw new ClientErrorException(
				"A tag with the name " + keyword.getName() + " already exists",
				422, dte);
		}
	}

	@Override
	public Keyword putKeyword(Long keywordId, Keyword keyword)
		throws Exception {

		try {
			return _toKeyword(
				_assetTagService.updateTag(keywordId, keyword.getName(), null));
		}
		catch (AssetTagNameException atne) {
			throw new ClientErrorException(
				"Name contains invalid characters", 422, atne);
		}
		catch (DuplicateTagException dte) {
			throw new ClientErrorException(
				"A tag with the name " + keyword.getName() + " already exists",
				422, dte);
		}
	}

	private Keyword _toKeyword(AssetTag assetTag) throws Exception {
		return new KeywordImpl() {
			{
				setContentSpace(assetTag.getGroupId());
				setCreator(
					CreatorUtil.toCreator(
						_portal,
						_userLocalService.getUserById(assetTag.getUserId())));
				setDateCreated(assetTag.getCreateDate());
				setDateModified(assetTag.getModifiedDate());
				setId(assetTag.getTagId());
				setKeywordUsageCount(assetTag.getAssetCount());
				setName(assetTag.getName());
			}
		};
	}

	@Reference
	private AssetTagService _assetTagService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}