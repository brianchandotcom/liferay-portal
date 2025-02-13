/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.headless.common.spi.service.context.ServiceContextBuilder;
import com.liferay.headless.delivery.dto.v1_0.ObjectEntryFolder;
import com.liferay.headless.delivery.resource.v1_0.ObjectEntryFolderResource;
import com.liferay.object.service.ObjectEntryFolderService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alicia García
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/object-entry-folder.properties",
	scope = ServiceScope.PROTOTYPE, service = ObjectEntryFolderResource.class
)
public class ObjectEntryFolderResourceImpl
	extends BaseObjectEntryFolderResourceImpl {

	@Override
	public Page<ObjectEntryFolder> getAssetLibraryObjectEntryFoldersPage(
			Long assetLibraryId, Boolean flatten, String search,
			Aggregation aggregation, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return _getAssetLibraryObjectEntryFoldersPage(
			HashMapBuilder.put(
				"createBatch",
				addAction(
					ActionKeys.UPDATE, "postAssetLibraryObjectEntryFolderBatch",
					_CLASS_NAME, assetLibraryId)
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, "getAssetLibraryObjectEntryFoldersPage",
					_CLASS_NAME, assetLibraryId)
			).build(),
			assetLibraryId, pagination);
	}

	@Override
	public ObjectEntryFolder postAssetLibraryObjectEntryFolder(
			Long assetLibraryId, ObjectEntryFolder objectEntryFolder)
		throws Exception {

		return _addObjectEntryFolder(assetLibraryId, null, objectEntryFolder);
	}

	private ObjectEntryFolder _addObjectEntryFolder(
			Long siteId, Long parentObjectEntryFolderId,
			ObjectEntryFolder objectEntryFolder)
		throws Exception {

		if (parentObjectEntryFolderId == null) {
			parentObjectEntryFolderId = 0L;
		}

		return _toObjectEntryFolder(
			_objectEntryFolderService.addObjectEntryFolder(
				objectEntryFolder.getExternalReferenceCode(), siteId,
				parentObjectEntryFolderId,
				LocalizedMapUtil.getLocalizedMap(
					contextAcceptLanguage.getPreferredLocale(),
					objectEntryFolder.getLabel(),
					objectEntryFolder.getLabel_i18n()),
				objectEntryFolder.getName(),
				ServiceContextBuilder.create(
					siteId, contextHttpServletRequest,
					objectEntryFolder.getViewableByAsString()
				).build()));
	}

	private Page<ObjectEntryFolder> _getAssetLibraryObjectEntryFoldersPage(
			Map<String, Map<String, String>> actions, long groupId,
			Pagination pagination)
		throws Exception {

		Group group = groupLocalService.getGroup(groupId);

		return Page.of(
			actions,
			transform(
				_objectEntryFolderService.getObjectEntryFolders(
					groupId, group.getCompanyId(), 0L,
					pagination.getStartPosition(), pagination.getEndPosition()),
				objectEntryFolder -> _toObjectEntryFolder(objectEntryFolder)),
			pagination,
			_objectEntryFolderService.getObjectEntryFoldersCount(
				groupId, group.getCompanyId(), 0L));
	}

	private ObjectEntryFolder _toObjectEntryFolder(
			com.liferay.object.model.ObjectEntryFolder objectEntryFolder)
		throws Exception {

		return _objectEntryFolderDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				HashMapBuilder.put(
					"create",
					addAction(
						ActionKeys.UPDATE, "postAssetLibraryObjectEntryFolder",
						_CLASS_NAME, objectEntryFolder.getGroupId())
				).put(
					"get",
					addAction(
						ActionKeys.VIEW,
						"getAssetLibraryObjectEntryFoldersPage", _CLASS_NAME,
						objectEntryFolder.getGroupId())
				).build(),
				_dtoConverterRegistry,
				objectEntryFolder.getObjectEntryFolderId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private static final String _CLASS_NAME =
		com.liferay.object.model.ObjectEntryFolder.class.getName();

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference(
		target = "(component.name=com.liferay.headless.delivery.internal.dto.v1_0.converter.ObjectEntryFolderDTOConverter)"
	)
	private DTOConverter
		<com.liferay.object.model.ObjectEntryFolder, ObjectEntryFolder>
			_objectEntryFolderDTOConverter;

	@Reference
	private ObjectEntryFolderService _objectEntryFolderService;

}