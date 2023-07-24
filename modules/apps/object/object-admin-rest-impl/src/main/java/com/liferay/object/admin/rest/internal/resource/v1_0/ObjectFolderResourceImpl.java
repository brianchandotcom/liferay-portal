/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.admin.rest.internal.resource.v1_0;

import com.liferay.object.admin.rest.dto.v1_0.ObjectFolder;
import com.liferay.object.admin.rest.resource.v1_0.ObjectFolderResource;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectConstants;
import com.liferay.object.service.ObjectFolderLocalService;
import com.liferay.object.service.ObjectFolderService;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.SearchUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Murilo Stodolni
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/object-folder.properties",
	scope = ServiceScope.PROTOTYPE, service = ObjectFolderResource.class
)
public class ObjectFolderResourceImpl extends BaseObjectFolderResourceImpl {

	@Override
	public void deleteObjectFolder(Long objectFolderId) throws Exception {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-148856")) {
			throw new UnsupportedOperationException();
		}

		_objectFolderService.deleteObjectFolder(objectFolderId);
	}

	@Override
	public ObjectFolder getObjectFolder(Long objectFolderId) throws Exception {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-148856")) {
			throw new UnsupportedOperationException();
		}

		return _toObjectFolder(
			_objectFolderService.getObjectFolder(objectFolderId));
	}

	@Override
	public ObjectFolder getObjectFolderByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-148856")) {
			throw new UnsupportedOperationException();
		}

		return _toObjectFolder(
			_objectFolderService.getObjectFolderByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId()));
	}

	@Override
	public Page<ObjectFolder> getObjectFoldersPage(
			String search, Pagination pagination)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-148856")) {
			throw new UnsupportedOperationException();
		}

		return SearchUtil.search(
			HashMapBuilder.put(
				"create",
				addAction(
					ObjectActionKeys.ADD_OBJECT_FOLDER, "postObjectFolder",
					ObjectConstants.RESOURCE_NAME,
					contextCompany.getCompanyId())
			).put(
				"createBatch",
				addAction(
					ObjectActionKeys.ADD_OBJECT_FOLDER, "postObjectFolderBatch",
					ObjectConstants.RESOURCE_NAME,
					contextCompany.getCompanyId())
			).put(
				"deleteBatch",
				addAction(
					ActionKeys.DELETE, "deleteObjectFolderBatch",
					com.liferay.object.model.ObjectFolder.class.getName(), null)
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, "getObjectFoldersPage",
					com.liferay.object.model.ObjectFolder.class.getName(), null)
			).put(
				"updateBatch",
				addAction(
					ActionKeys.UPDATE, "putObjectFolderBatch",
					com.liferay.object.model.ObjectFolder.class.getName(), null)
			).build(),
			booleanQuery -> {
			},
			null, com.liferay.object.model.ObjectFolder.class.getName(), search,
			pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(Field.NAME, search);
				searchContext.setCompanyId(contextCompany.getCompanyId());
			},
			null,
			document -> _toObjectFolder(
				_objectFolderService.getObjectFolder(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public ObjectFolder postObjectFolder(ObjectFolder objectFolder)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-148856")) {
			throw new UnsupportedOperationException();
		}

		return _toObjectFolder(
			_objectFolderService.addObjectFolder(
				objectFolder.getExternalReferenceCode(),
				LocalizedMapUtil.getLocalizedMap(objectFolder.getLabel()),
				objectFolder.getName()));
	}

	@Override
	public ObjectFolder putObjectFolder(
			Long objectFolderId, ObjectFolder objectFolder)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-148856")) {
			throw new UnsupportedOperationException();
		}

		return _toObjectFolder(
			_objectFolderService.updateObjectFolder(
				objectFolder.getExternalReferenceCode(), objectFolderId,
				LocalizedMapUtil.getLocalizedMap(objectFolder.getLabel())));
	}

	@Override
	public ObjectFolder putObjectFolderByExternalReferenceCode(
			String externalReferenceCode, ObjectFolder objectFolder)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-148856")) {
			throw new UnsupportedOperationException();
		}

		com.liferay.object.model.ObjectFolder serviceBuilderObjectFolder =
			_objectFolderLocalService.getObjectFolderByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		objectFolder.setExternalReferenceCode(externalReferenceCode);

		return putObjectFolder(
			serviceBuilderObjectFolder.getObjectFolderId(), objectFolder);
	}

	private ObjectFolder _toObjectFolder(
		com.liferay.object.model.ObjectFolder objectFolder) {

		String permissionName =
			com.liferay.object.model.ObjectFolder.class.getName();

		return new ObjectFolder() {
			{
				actions = HashMapBuilder.put(
					"delete",
					() -> {
						if (objectFolder.isUncategorized()) {
							return null;
						}

						return addAction(
							ActionKeys.DELETE, "deleteObjectFolder",
							permissionName, objectFolder.getObjectFolderId());
					}
				).put(
					"get",
					addAction(
						ActionKeys.VIEW, "getObjectFolder", permissionName,
						objectFolder.getObjectFolderId())
				).put(
					"permissions",
					addAction(
						ActionKeys.PERMISSIONS, "patchObjectFolder",
						permissionName, objectFolder.getObjectFolderId())
				).put(
					"update",
					() -> {
						if (objectFolder.isUncategorized()) {
							return null;
						}

						return addAction(
							ActionKeys.UPDATE, "putObjectFolder",
							permissionName, objectFolder.getObjectFolderId());
					}
				).build();
				dateCreated = objectFolder.getCreateDate();
				dateModified = objectFolder.getModifiedDate();
				externalReferenceCode = objectFolder.getExternalReferenceCode();
				id = objectFolder.getObjectFolderId();
				label = LocalizedMapUtil.getLanguageIdMap(
					objectFolder.getLabelMap());
				name = objectFolder.getName();
			}
		};
	}

	@Reference
	private ObjectFolderLocalService _objectFolderLocalService;

	@Reference
	private ObjectFolderService _objectFolderService;

}