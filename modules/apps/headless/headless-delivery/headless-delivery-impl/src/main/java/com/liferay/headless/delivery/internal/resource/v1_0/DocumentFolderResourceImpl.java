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

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.headless.common.spi.service.context.ServiceContextRequestUtil;
import com.liferay.headless.delivery.dto.v1_0.CustomField;
import com.liferay.headless.delivery.dto.v1_0.DocumentFolder;
import com.liferay.headless.delivery.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.converter.DocumentFolderDTOConverter;
import com.liferay.headless.delivery.internal.dto.v1_0.util.EntityFieldsUtil;
import com.liferay.headless.delivery.internal.odata.entity.v1_0.DocumentFolderEntityModel;
import com.liferay.headless.delivery.resource.v1_0.DocumentFolderResource;
import com.liferay.headless.delivery.search.aggregation.AggregationUtil;
import com.liferay.headless.delivery.search.filter.FilterUtil;
import com.liferay.headless.delivery.search.sort.SortUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.portlet.documentlibrary.constants.DLConstants;

import java.util.Map;
import java.util.Optional;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/document-folder.properties",
	scope = ServiceScope.PROTOTYPE, service = DocumentFolderResource.class
)
public class DocumentFolderResourceImpl
	extends BaseDocumentFolderResourceImpl implements EntityModelResource {

	@Override
	public void deleteDocumentFolder(Long documentFolderId) throws Exception {
		_dlAppService.deleteFolder(documentFolderId);
	}

	@Override
	public Page<DocumentFolder> getAssetLibraryDocumentFoldersPage(
			Long assetLibraryId, Boolean flatten, String search,
			Aggregation aggregation, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return getSiteDocumentFoldersPage(
			assetLibraryId, flatten, search, aggregation, filter, pagination,
			sorts);
	}

	@Override
	public DocumentFolder getDocumentFolder(Long documentFolderId)
		throws Exception {

		return _toDocumentFolder(_dlAppService.getFolder(documentFolderId));
	}

	@Override
	public Page<DocumentFolder> getDocumentFolderDocumentFoldersPage(
			Long parentDocumentFolderId, Boolean flatten, String search,
			Aggregation aggregation, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		Folder folder = _dlAppService.getFolder(parentDocumentFolderId);

		return _getDocumentFoldersPage(
			HashMapBuilder.put(
				"create",
				addAction(
					ActionKeys.ADD_SUBFOLDER, folder.getFolderId(),
					"postDocumentFolderDocumentFolder", folder.getUserId(),
					DLConstants.RESOURCE_NAME, folder.getGroupId())
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, folder.getFolderId(),
					"getDocumentFolderDocumentFoldersPage", folder.getUserId(),
					DLConstants.RESOURCE_NAME, folder.getGroupId())
			).build(),
			folder.getFolderId(), folder.getGroupId(), flatten, search,
			aggregation, filter, pagination, sorts);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return new DocumentFolderEntityModel(
			EntityFieldsUtil.getEntityFields(
				_portal.getClassNameId(DLFolder.class.getName()),
				contextCompany.getCompanyId(), _expandoColumnLocalService,
				_expandoTableLocalService));
	}

	@Override
	public Page<DocumentFolder> getSiteDocumentFoldersPage(
			Long siteId, Boolean flatten, String search,
			Aggregation aggregation, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		Long documentFolderId = null;

		if (!GetterUtil.getBoolean(flatten)) {
			documentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}

		return _getDocumentFoldersPage(
			HashMapBuilder.put(
				"create",
				addAction(
					ActionKeys.ADD_FOLDER, "postSiteDocumentFolder",
					DLConstants.RESOURCE_NAME, siteId)
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, "getSiteDocumentFoldersPage",
					DLConstants.RESOURCE_NAME, siteId)
			).build(),
			documentFolderId, siteId, flatten, search, aggregation, filter,
			pagination, sorts);
	}

	@Override
	public DocumentFolder patchDocumentFolder(
			Long documentFolderId, DocumentFolder documentFolder)
		throws Exception {

		Folder existingFolder = _dlAppService.getFolder(documentFolderId);

		return _updateDocumentFolder(
			documentFolderId, documentFolder.getCustomFields(),
			Optional.ofNullable(
				documentFolder.getDescription()
			).orElse(
				existingFolder.getDescription()
			),
			Optional.ofNullable(
				documentFolder.getName()
			).orElse(
				existingFolder.getName()
			));
	}

	@Override
	public DocumentFolder postAssetLibraryDocumentFolder(
			Long assetLibraryId, DocumentFolder documentFolder)
		throws Exception {

		return postSiteDocumentFolder(assetLibraryId, documentFolder);
	}

	@Override
	public DocumentFolder postDocumentFolderDocumentFolder(
			Long parentDocumentFolderId, DocumentFolder documentFolder)
		throws Exception {

		Folder folder = _dlAppService.getFolder(parentDocumentFolderId);

		return _addFolder(
			folder.getGroupId(), folder.getFolderId(), documentFolder);
	}

	@Override
	public DocumentFolder postSiteDocumentFolder(
			Long siteId, DocumentFolder documentFolder)
		throws Exception {

		return _addFolder(siteId, 0L, documentFolder);
	}

	@Override
	public DocumentFolder putDocumentFolder(
			Long documentFolderId, DocumentFolder documentFolder)
		throws Exception {

		return _updateDocumentFolder(
			documentFolderId, documentFolder.getCustomFields(),
			documentFolder.getDescription(), documentFolder.getName());
	}

	@Override
	public void putDocumentFolderSubscribe(Long documentFolderId)
		throws Exception {

		Folder folder = _dlAppService.getFolder(documentFolderId);

		_dlAppService.subscribeFolder(
			folder.getGroupId(), folder.getFolderId());
	}

	@Override
	public void putDocumentFolderUnsubscribe(Long documentFolderId)
		throws Exception {

		Folder folder = _dlAppService.getFolder(documentFolderId);

		_dlAppService.unsubscribeFolder(
			folder.getGroupId(), folder.getFolderId());
	}

	@Override
	protected Long getPermissionCheckerGroupId(Object id) throws Exception {
		Folder folder = _dlAppService.getFolder((Long)id);

		return folder.getGroupId();
	}

	@Override
	protected String getPermissionCheckerPortletName(Object id) {
		return DLConstants.RESOURCE_NAME;
	}

	@Override
	protected String getPermissionCheckerResourceName(Object id) {
		return DLFolder.class.getName();
	}

	private DocumentFolder _addFolder(
			Long siteId, Long parentDocumentFolderId,
			DocumentFolder documentFolder)
		throws Exception {

		return _toDocumentFolder(
			_dlAppService.addFolder(
				siteId, parentDocumentFolderId, documentFolder.getName(),
				documentFolder.getDescription(),
				ServiceContextRequestUtil.createServiceContext(
					CustomFieldsUtil.toMap(
						DLFolder.class.getName(), contextCompany.getCompanyId(),
						documentFolder.getCustomFields(),
						contextAcceptLanguage.getPreferredLocale()),
					siteId, contextHttpServletRequest,
					documentFolder.getViewableByAsString())));
	}

	private Page<DocumentFolder> _getDocumentFoldersPage(
			Map<String, Map<String, String>> actions,
			Long parentDocumentFolderId, Long groupId, Boolean flatten,
			String keywords, Aggregation aggregation, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			actions,
			booleanQuery -> {
				if (parentDocumentFolderId != null) {
					BooleanFilter booleanFilter =
						booleanQuery.getPreBooleanFilter();

					String field = Field.FOLDER_ID;

					if (GetterUtil.getBoolean(flatten)) {
						booleanFilter.add(
							new TermFilter(
								field, String.valueOf(parentDocumentFolderId)),
							BooleanClauseOccur.MUST_NOT);
						field = Field.TREE_PATH;
					}

					booleanFilter.add(
						new TermFilter(
							field, String.valueOf(parentDocumentFolderId)),
						BooleanClauseOccur.MUST);
				}
			},
			FilterUtil.processFilter(_ddmIndexer, filter), DLFolder.class,
			keywords, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.addVulcanAggregation(aggregation);
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(new long[] {groupId});

				SearchRequestBuilder searchRequestBuilder =
					_searchRequestBuilderFactory.builder(searchContext);

				AggregationUtil.processVulcanAggregation(
					_aggregations, _ddmIndexer, _queries, searchRequestBuilder,
					aggregation);

				SortUtil.processSorts(
					_ddmIndexer, searchRequestBuilder, searchContext.getSorts(),
					_queries, _sorts);
			},
			sorts,
			document -> _toDocumentFolder(
				_dlAppService.getFolder(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	private DocumentFolder _toDocumentFolder(Folder folder) throws Exception {
		return _documentFolderDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				HashMapBuilder.put(
					"delete",
					addAction(
						ActionKeys.DELETE, folder.getFolderId(),
						"deleteDocumentFolder", folder.getUserId(),
						DLFolder.class.getName(), folder.getGroupId())
				).put(
					"get",
					addAction(
						ActionKeys.ACCESS, folder.getFolderId(),
						"getDocumentFolder", folder.getUserId(),
						DLFolder.class.getName(), folder.getGroupId())
				).put(
					"replace",
					addAction(
						ActionKeys.UPDATE, folder.getFolderId(),
						"putDocumentFolder", folder.getUserId(),
						DLFolder.class.getName(), folder.getGroupId())
				).put(
					"subscribe",
					addAction(
						ActionKeys.SUBSCRIBE, folder.getFolderId(),
						"putDocumentFolderSubscribe", folder.getUserId(),
						DLFolder.class.getName(), folder.getGroupId())
				).put(
					"unsubscribe",
					addAction(
						ActionKeys.SUBSCRIBE, folder.getFolderId(),
						"putDocumentFolderUnsubscribe", folder.getUserId(),
						DLFolder.class.getName(), folder.getGroupId())
				).put(
					"update",
					addAction(
						ActionKeys.UPDATE, folder.getFolderId(),
						"patchDocumentFolder", folder.getUserId(),
						DLFolder.class.getName(), folder.getGroupId())
				).build(),
				_dtoConverterRegistry, folder.getFolderId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private DocumentFolder _updateDocumentFolder(
			Long documentFolderId, CustomField[] customFields,
			String description, String name)
		throws Exception {

		return _toDocumentFolder(
			_dlAppService.updateFolder(
				documentFolderId, name, description,
				ServiceContextRequestUtil.createServiceContext(
					CustomFieldsUtil.toMap(
						DLFolder.class.getName(), contextCompany.getCompanyId(),
						customFields,
						contextAcceptLanguage.getPreferredLocale()),
					0, contextHttpServletRequest, null)));
	}

	@Reference
	private Aggregations _aggregations;

	@Reference
	private DDMIndexer _ddmIndexer;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DocumentFolderDTOConverter _documentFolderDTOConverter;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private Queries _queries;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private Sorts _sorts;

}