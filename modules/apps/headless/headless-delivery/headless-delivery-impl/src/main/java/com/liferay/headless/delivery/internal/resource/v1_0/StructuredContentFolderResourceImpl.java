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

package com.liferay.headless.web.experience.internal.resource.v1_0;

import com.liferay.headless.common.spi.service.context.ServiceContextUtil;
import com.liferay.headless.web.experience.dto.v1_0.StructuredContentFolder;
import com.liferay.headless.web.experience.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.web.experience.internal.odata.entity.v1_0.StructuredContentFolderEntityModel;
import com.liferay.headless.web.experience.resource.v1_0.StructuredContentFolderResource;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.service.JournalFolderService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/structured-content-folder.properties",
	scope = ServiceScope.PROTOTYPE,
	service = StructuredContentFolderResource.class
)
public class StructuredContentFolderResourceImpl
	extends BaseStructuredContentFolderResourceImpl
	implements EntityModelResource {

	@Override
	public void deleteStructuredContentFolder(Long structuredContentFolderId)
		throws Exception {

		_journalFolderService.deleteFolder(structuredContentFolderId);
	}

	@Override
	public Page<StructuredContentFolder>
			getContentSpaceStructuredContentFoldersPage(
				Long contentSpaceId, Boolean flatten, String search,
				Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		Long parentStructuredContentFolderId = null;

		if (!GetterUtil.getBoolean(flatten)) {
			parentStructuredContentFolderId =
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}

		return _getFoldersPage(
			contentSpaceId, parentStructuredContentFolderId, search, filter,
			pagination, sorts);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public StructuredContentFolder getStructuredContentFolder(
			Long structuredContentFolderId)
		throws Exception {

		return _toStructuredContentFolder(
			_journalFolderService.getFolder(structuredContentFolderId));
	}

	@Override
	public Page<StructuredContentFolder>
			getStructuredContentFolderStructuredContentFoldersPage(
				Long structuredContentFolderId, String search, Filter filter,
				Pagination pagination, Sort[] sorts)
		throws Exception {

		JournalFolder journalFolder = _journalFolderService.getFolder(
			structuredContentFolderId);

		return _getFoldersPage(
			journalFolder.getGroupId(), structuredContentFolderId, search,
			filter, pagination, sorts);
	}

	@Override
	public StructuredContentFolder postContentSpaceStructuredContentFolder(
			Long contentSpaceId,
			StructuredContentFolder structuredContentFolder)
		throws Exception {

		return _addStructuredContentFolder(
			contentSpaceId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			structuredContentFolder);
	}

	@Override
	public StructuredContentFolder
			postStructuredContentFolderStructuredContentFolder(
				Long structuredContentFolderId,
				StructuredContentFolder structuredContentFolder)
		throws Exception {

		JournalFolder journalFolder = _journalFolderService.getFolder(
			structuredContentFolderId);

		return _addStructuredContentFolder(
			journalFolder.getGroupId(), structuredContentFolderId,
			structuredContentFolder);
	}

	@Override
	public StructuredContentFolder putStructuredContentFolder(
			Long structuredContentFolderId,
			StructuredContentFolder structuredContentFolder)
		throws Exception {

		JournalFolder journalFolder = _journalFolderService.getFolder(
			structuredContentFolderId);

		return _toStructuredContentFolder(
			_journalFolderService.updateFolder(
				journalFolder.getGroupId(), structuredContentFolderId,
				journalFolder.getParentFolderId(),
				structuredContentFolder.getName(),
				structuredContentFolder.getDescription(), false,
				ServiceContextUtil.createServiceContext(
					journalFolder.getGroupId(),
					structuredContentFolder.getViewableByAsString())));
	}

	private StructuredContentFolder _addStructuredContentFolder(
			Long contentSpaceId, Long parentFolderId,
			StructuredContentFolder structuredContentFolder)
		throws PortalException {

		return _toStructuredContentFolder(
			_journalFolderService.addFolder(
				contentSpaceId, parentFolderId,
				structuredContentFolder.getName(),
				structuredContentFolder.getDescription(),
				ServiceContextUtil.createServiceContext(
					contentSpaceId,
					structuredContentFolder.getViewableByAsString())));
	}

	private Page<StructuredContentFolder> _getFoldersPage(
			Long contentSpaceId, Long parentStructuredContentFolderId,
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			booleanQuery -> {
				if (parentStructuredContentFolderId != null) {
					BooleanFilter booleanFilter =
						booleanQuery.getPreBooleanFilter();

					booleanFilter.add(
						new TermFilter(
							Field.FOLDER_ID,
							String.valueOf(parentStructuredContentFolderId)),
						BooleanClauseOccur.MUST);
				}
			},
			filter, JournalFolder.class, search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(new long[] {contentSpaceId});
			},
			document -> _toStructuredContentFolder(
				_journalFolderService.getFolder(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			sorts);
	}

	private StructuredContentFolder _toStructuredContentFolder(
			JournalFolder journalFolder)
		throws PortalException {

		return new StructuredContentFolder() {
			{
				contentSpaceId = journalFolder.getGroupId();
				creator = CreatorUtil.toCreator(
					_portal,
					_userLocalService.getUser(journalFolder.getUserId()));
				dateCreated = journalFolder.getCreateDate();
				dateModified = journalFolder.getModifiedDate();
				description = journalFolder.getDescription();
				id = journalFolder.getFolderId();
				name = journalFolder.getName();
				numberOfStructuredContentFolders =
					_journalFolderService.getFoldersCount(
						journalFolder.getGroupId(),
						journalFolder.getFolderId());
				numberOfStructuredContents =
					_journalArticleService.getArticlesCount(
						journalFolder.getGroupId(), journalFolder.getFolderId(),
						WorkflowConstants.STATUS_APPROVED);
			}
		};
	}

	private static final EntityModel _entityModel =
		new StructuredContentFolderEntityModel();

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private JournalFolderService _journalFolderService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}