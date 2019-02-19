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
import com.liferay.headless.foundation.internal.odata.entity.v1_0.KeywordEntityModel;
import com.liferay.headless.foundation.resource.v1_0.KeywordResource;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchResultPermissionFilterFactory;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.ClassNameService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/keyword.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {EntityModelResource.class, KeywordResource.class}
)
public class KeywordResourceImpl
	extends BaseKeywordResourceImpl implements EntityModelResource {

	@Override
	public boolean deleteKeyword(Long keywordId) throws Exception {
		_assetTagService.deleteTag(keywordId);

		return true;
	}

	@Override
	public Page<Keyword> getContentSpaceKeywordsPage(
			Long contentSpaceId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		List<AssetTag> assetTags = new ArrayList<>();

		Hits hits = SearchUtil.getHits(
			filter, _indexerRegistry.nullSafeGetIndexer(AssetTag.class),
			pagination,
			booleanQuery -> {
			},
			queryConfig ->
				queryConfig.setSelectedFieldNames(Field.ASSET_TAG_IDS),
			searchContext -> {
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(new long[] {contentSpaceId});
			},
			_searchResultPermissionFilterFactory, sorts);

		for (Document document : hits.getDocs()) {
			AssetTag assetTag = _assetTagService.getTag(
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)));

			assetTags.add(assetTag);
		}

		return Page.of(
			transform(assetTags, this::_toKeyword), pagination,
			assetTags.size());
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
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
				contentSpace = assetTag.getGroupId();
				creator = CreatorUtil.toCreator(
					_portal,
					_userLocalService.getUserById(assetTag.getUserId()));
				dateCreated = assetTag.getCreateDate();
				dateModified = assetTag.getModifiedDate();
				id = assetTag.getTagId();
				keywordUsageCount = assetTag.getAssetCount();
				name = assetTag.getName();
			}
		};
	}

	private static final EntityModel _entityModel = new KeywordEntityModel();

	@Reference
	private AssetTagService _assetTagService;

	@Reference
	private ClassNameService _classNameService;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private SearchResultPermissionFilterFactory
		_searchResultPermissionFilterFactory;

	@Reference
	private UserLocalService _userLocalService;

}