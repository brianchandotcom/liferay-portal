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

package com.liferay.headless.collaboration.internal.resource.v1_0;

import com.liferay.headless.collaboration.dto.v1_0.DiscussionSection;
import com.liferay.headless.collaboration.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.collaboration.internal.odata.entity.v1_0.DiscussionSectionEntityModel;
import com.liferay.headless.collaboration.resource.v1_0.DiscussionSectionResource;
import com.liferay.headless.common.spi.service.context.ServiceContextUtil;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.service.MBCategoryService;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/discussion-section.properties",
	scope = ServiceScope.PROTOTYPE, service = DiscussionSectionResource.class
)
public class DiscussionSectionResourceImpl
	extends BaseDiscussionSectionResourceImpl implements EntityModelResource {

	@Override
	public void deleteDiscussionSection(Long discussionSectionId)
		throws Exception {

		_mbCategoryService.deleteCategory(discussionSectionId, true);
	}

	@Override
	public Page<DiscussionSection> getContentSpaceDiscussionSectionsPage(
			Long contentSpaceId, Boolean tree, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return _getContentSpaceDiscussionSectionsPage(
			booleanQuery -> {
				if (Boolean.TRUE.equals(tree)) {
					BooleanFilter booleanFilter =
						booleanQuery.getPreBooleanFilter();

					booleanFilter.add(
						new TermFilter(Field.ASSET_PARENT_CATEGORY_ID, "0"),
						BooleanClauseOccur.MUST);
				}
			},
			filter, pagination, sorts);
	}

	@Override
	public DiscussionSection getDiscussionSection(Long discussionSectionId)
		throws Exception {

		return _toDiscussionSection(
			_mbCategoryService.getCategory(discussionSectionId));
	}

	@Override
	public Page<DiscussionSection> getDiscussionSectionDiscussionSectionsPage(
			Long discussionSectionId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		MBCategory mbCategory = _mbCategoryService.getCategory(
			discussionSectionId);

		return _getContentSpaceDiscussionSectionsPage(
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						Field.ASSET_PARENT_CATEGORY_ID,
						String.valueOf(mbCategory.getCategoryId())),
					BooleanClauseOccur.MUST);
			},
			filter, pagination, sorts);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public DiscussionSection postContentSpaceDiscussionSection(
			Long contentSpaceId, DiscussionSection discussionSection)
		throws Exception {

		return _addDiscussionSection(contentSpaceId, 0L, discussionSection);
	}

	@Override
	public DiscussionSection postDiscussionSectionDiscussionSection(
			Long discussionSectionId, DiscussionSection discussionSection)
		throws Exception {

		MBCategory mbCategory = _mbCategoryService.getCategory(
			discussionSectionId);

		return _addDiscussionSection(
			mbCategory.getGroupId(), discussionSectionId, discussionSection);
	}

	@Override
	public DiscussionSection putDiscussionSection(
			Long discussionSectionId, DiscussionSection discussionSection)
		throws Exception {

		MBCategory mbCategory = _mbCategoryService.getCategory(
			discussionSectionId);

		return _toDiscussionSection(
			_mbCategoryService.updateCategory(
				discussionSectionId, mbCategory.getParentCategoryId(),
				discussionSection.getTitle(),
				discussionSection.getDescription(),
				mbCategory.getDisplayStyle(), "", "", "", 0, false, "", "", 0,
				"", false, "", 0, false, "", "", false, false, false,
				new ServiceContext()));
	}

	private DiscussionSection _addDiscussionSection(
			long contentSpaceId, Long parentDiscussionSectionId,
			DiscussionSection discussionSection)
		throws PortalException {

		return _toDiscussionSection(
			_mbCategoryService.addCategory(
				_user.getUserId(), parentDiscussionSectionId,
				discussionSection.getTitle(),
				discussionSection.getDescription(),
				ServiceContextUtil.createServiceContext(
					null, null, contentSpaceId,
					discussionSection.getViewableByAsString())));
	}

	private Page<DiscussionSection> _getContentSpaceDiscussionSectionsPage(
			UnsafeConsumer<BooleanQuery, Exception> booleanQueryUnsafeConsumer,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			booleanQueryUnsafeConsumer, filter, MBCategory.class, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setCompanyId(contextCompany.getCompanyId());
			},
			document -> _toDiscussionSection(
				_mbCategoryService.getCategory(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			sorts);
	}

	private DiscussionSection _toDiscussionSection(MBCategory mbCategory)
		throws PortalException {

		return new DiscussionSection() {
			{
				contentSpaceId = mbCategory.getGroupId();
				creator = CreatorUtil.toCreator(
					_portal,
					_userLocalService.getUserById(mbCategory.getUserId()));
				dateCreated = mbCategory.getCreateDate();
				dateModified = mbCategory.getModifiedDate();
				description = mbCategory.getDescription();
				id = mbCategory.getCategoryId();
				numberOfDiscussionSections =
					_mbCategoryService.getCategoriesCount(
						mbCategory.getGroupId(), mbCategory.getCategoryId());
				numberOfDiscussionThreads = mbCategory.getThreadCount();
				title = mbCategory.getName();
			}
		};
	}

	private static final EntityModel _entityModel =
		new DiscussionSectionEntityModel();

	@Reference
	private MBCategoryService _mbCategoryService;

	@Reference
	private Portal _portal;

	@Context
	private User _user;

	@Reference
	private UserLocalService _userLocalService;

}