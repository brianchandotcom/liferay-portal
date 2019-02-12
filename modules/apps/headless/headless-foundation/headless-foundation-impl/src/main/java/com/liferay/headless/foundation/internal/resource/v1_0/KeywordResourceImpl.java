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
import com.liferay.headless.foundation.resource.v1_0.KeywordResource;
import com.liferay.portal.kernel.service.ServiceContext;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

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
	public Response deleteKeyword(Long keywordsId) throws Exception {
		_assetTagService.deleteTag(keywordsId);

		return buildNoContentResponse();
	}

	@Override
	public Keyword getKeyword(Long keywordId) throws Exception {
		AssetTag assetTag = _assetTagService.getTag(keywordId);

		return _toKeyword(assetTag);
	}

	@Override
	public Keyword postContentSpaceKeyword(Long contentSpaceId, Keyword keyword)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(contentSpaceId);

		try {
			AssetTag assetTag = _assetTagService.addTag(
				contentSpaceId, keyword.getName(), serviceContext);

			return _toKeyword(assetTag);
		}
		catch (DuplicateTagException dte) {
			throw new ClientErrorException(
				"A tag with the name " + keyword.getName() + " already exists",
				422, dte);
		}
		catch (AssetTagNameException atne) {
			throw new ClientErrorException(
				"Name contains invalid characters", 422, atne);
		}
	}

	@Override
	public Keyword putKeyword(Long keywordsId, Keyword keyword)
		throws Exception {

		try {
			AssetTag assetTag = _assetTagService.updateTag(
				keywordsId, keyword.getName(), null);

			return _toKeyword(assetTag);
		}
		catch (DuplicateTagException dte) {
			throw new ClientErrorException(
				"A tag with the name " + keyword.getName() + " already exists",
				422, dte);
		}
		catch (AssetTagNameException atne) {
			throw new ClientErrorException(
				"Name contains invalid characters", 422, atne);
		}
	}

	private static Keyword _toKeyword(AssetTag assetTag) {
		return new Keyword() {
			{
				setContentSpace(assetTag.getGroupId());
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

}