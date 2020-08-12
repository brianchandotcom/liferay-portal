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

package com.liferay.headless.admin.taxonomy.internal.resource.v1_0;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.kernel.service.AssetTagService;
import com.liferay.headless.admin.taxonomy.dto.v1_0.Keyword;
import com.liferay.headless.admin.taxonomy.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.admin.taxonomy.internal.odata.entity.v1_0.KeywordEntityModel;
import com.liferay.headless.admin.taxonomy.resource.v1_0.KeywordResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionList;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.GroupUtil;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.portlet.asset.model.impl.AssetTagImpl;

import java.sql.Timestamp;

import java.util.Date;

import javax.ws.rs.core.MultivaluedMap;

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
public class KeywordResourceImpl
	extends BaseKeywordResourceImpl implements EntityModelResource {

	@Override
	public void deleteKeyword(Long keywordId) throws Exception {
		_assetTagService.deleteTag(keywordId);
	}

	@Override
	public Page<Keyword> getAssetLibraryKeywordsPage(
			Long assetLibraryId, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return getSiteKeywordsPage(
			assetLibraryId, search, filter, pagination, sorts);
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
	public Page<Keyword> getKeywordsRankedPage(
		Long siteId, String search, Pagination pagination) {

		DynamicQuery dynamicQuery = _assetTagLocalService.dynamicQuery();

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"companyId", contextCompany.getCompanyId()));

		if (siteId != null) {
			dynamicQuery.add(RestrictionsFactoryUtil.eq("groupId", siteId));
		}

		if (!Validator.isBlank(search)) {
			dynamicQuery.add(
				RestrictionsFactoryUtil.ilike(
					"name", StringUtil.quote(search, StringPool.PERCENT)));
		}

		dynamicQuery.addOrder(OrderFactoryUtil.desc("assetCount"));
		dynamicQuery.setProjection(_getProjectionList(), true);

		return Page.of(
			transform(
				transform(
					_assetTagLocalService.dynamicQuery(
						dynamicQuery, pagination.getStartPosition(),
						pagination.getEndPosition()),
					this::_toAssetTag),
				this::_toKeyword),
			pagination, _getTotalCount(siteId));
	}

	@Override
	public Page<Keyword> getSiteKeywordsPage(
			Long siteId, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			HashMapBuilder.put(
				"create",
				addAction(
					"MANAGE_TAG", "postSiteKeyword", "com.liferay.asset.tags",
					siteId)
			).put(
				"get",
				addAction(
					"MANAGE_TAG", "getSiteKeywordsPage",
					"com.liferay.asset.tags", siteId)
			).build(),
			booleanQuery -> {
			},
			filter, AssetTag.class, search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(new long[] {siteId});
			},
			sorts,
			document -> _toKeyword(
				_assetTagService.getTag(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public Keyword postAssetLibraryKeyword(Long assetLibraryId, Keyword keyword)
		throws Exception {

		return postSiteKeyword(assetLibraryId, keyword);
	}

	@Override
	public Keyword postSiteKeyword(Long siteId, Keyword keyword)
		throws Exception {

		return _toKeyword(
			_assetTagService.addTag(
				siteId, keyword.getName(), new ServiceContext()));
	}

	@Override
	public Keyword putKeyword(Long keywordId, Keyword keyword)
		throws Exception {

		return _toKeyword(
			_assetTagService.updateTag(keywordId, keyword.getName(), null));
	}

	private ProjectionList _getProjectionList() {
		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(
			ProjectionFactoryUtil.alias(
				ProjectionFactoryUtil.sqlProjection(
					"COALESCE((select count(entryId) assetCount from " +
						"AssetEntries_AssetTags where tagId = this_.tagId " +
							"group by tagId), 0) AS assetCount",
					new String[] {"assetCount"}, new Type[] {Type.INTEGER}),
				"assetCount"));
		projectionList.add(ProjectionFactoryUtil.property("companyId"));
		projectionList.add(ProjectionFactoryUtil.property("createDate"));
		projectionList.add(ProjectionFactoryUtil.property("groupId"));
		projectionList.add(ProjectionFactoryUtil.property("modifiedDate"));
		projectionList.add(ProjectionFactoryUtil.property("name"));
		projectionList.add(ProjectionFactoryUtil.property("tagId"));
		projectionList.add(ProjectionFactoryUtil.property("userId"));

		return projectionList;
	}

	private long _getTotalCount(Long siteId) {
		DynamicQuery dynamicQuery = _assetTagLocalService.dynamicQuery();

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"companyId", contextCompany.getCompanyId()));

		if (siteId != null) {
			dynamicQuery.add(RestrictionsFactoryUtil.eq("groupId", siteId));
		}

		dynamicQuery.add(
			RestrictionsFactoryUtil.sqlRestriction(
				"exists (select 1 from AssetEntries_AssetTags where tagId = " +
					"this_.tagId)"));

		return _assetTagLocalService.dynamicQueryCount(dynamicQuery);
	}

	private AssetTag _toAssetTag(Object[] assetTags) {
		return new AssetTagImpl() {
			{
				if (assetTags[0] != null) {
					setAssetCount((int)assetTags[0]);
				}

				setCompanyId((long)assetTags[1]);
				setCreateDate(_toDate((Timestamp)assetTags[2]));
				setGroupId((long)assetTags[3]);
				setModifiedDate(_toDate((Timestamp)assetTags[4]));
				setName((String)assetTags[5]);
				setTagId((long)assetTags[6]);
				setUserId((long)assetTags[7]);
			}
		};
	}

	private Date _toDate(Timestamp timestamp) {
		return new Date(timestamp.getTime());
	}

	private Keyword _toKeyword(AssetTag assetTag) {
		Group group = groupLocalService.fetchGroup(assetTag.getGroupId());

		return new Keyword() {
			{
				actions = HashMapBuilder.put(
					"delete",
					addAction(
						"MANAGE_TAG", assetTag.getTagId(), "deleteKeyword",
						assetTag.getUserId(), "com.liferay.asset.tags",
						assetTag.getGroupId())
				).put(
					"get",
					addAction(
						"MANAGE_TAG", assetTag.getTagId(), "getKeyword",
						assetTag.getUserId(), "com.liferay.asset.tags",
						assetTag.getGroupId())
				).put(
					"replace",
					addAction(
						"MANAGE_TAG", assetTag.getTagId(), "putKeyword",
						assetTag.getUserId(), "com.liferay.asset.tags",
						assetTag.getGroupId())
				).build();
				assetLibraryKey = GroupUtil.getAssetLibraryKey(group);
				dateCreated = assetTag.getCreateDate();
				dateModified = assetTag.getModifiedDate();
				id = assetTag.getTagId();
				name = assetTag.getName();
				siteId = GroupUtil.getSiteId(group);

				setCreator(
					() -> {
						if (assetTag.getUserId() != 0) {
							return CreatorUtil.toCreator(
								_portal,
								_userLocalService.fetchUser(
									assetTag.getUserId()));
						}

						return null;
					});
				setKeywordUsageCount(
					() -> {
						Hits hits = _assetEntryLocalService.search(
							assetTag.getCompanyId(),
							new long[] {assetTag.getGroupId()},
							assetTag.getUserId(), null, 0, null, null, null,
							null, assetTag.getName(), true,
							new int[] {
								WorkflowConstants.STATUS_APPROVED,
								WorkflowConstants.STATUS_PENDING,
								WorkflowConstants.STATUS_SCHEDULED
							},
							false, 0, 1);

						return hits.getLength();
					});
			}
		};
	}

	private static final EntityModel _entityModel = new KeywordEntityModel();

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private AssetTagService _assetTagService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}