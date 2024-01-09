/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.admin.rest.internal.resource.v1_0;

import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.dto.v1_0.ObjectFolder;
import com.liferay.object.admin.rest.dto.v1_0.ObjectFolderItem;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectFolderResource;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectConstants;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.exception.ObjectFolderItemObjectDefinitionIdException;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFolderItemLocalService;
import com.liferay.object.service.ObjectFolderLocalService;
import com.liferay.object.service.ObjectFolderService;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

		com.liferay.object.model.ObjectFolder serviceBuilderObjectFolder =
			_objectFolderService.addObjectFolder(
				objectFolder.getExternalReferenceCode(),
				LocalizedMapUtil.getLocalizedMap(objectFolder.getLabel()),
				objectFolder.getName());

		_addObjectFolderResources(
			serviceBuilderObjectFolder.getExternalReferenceCode(),
			serviceBuilderObjectFolder.getObjectFolderId(),
			ListUtil.fromArray(objectFolder.getObjectFolderItems()),
			Collections.emptyList());

		return _toObjectFolder(serviceBuilderObjectFolder);
	}

	@Override
	public ObjectFolder putObjectFolder(
			Long objectFolderId, ObjectFolder objectFolder)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-148856")) {
			throw new UnsupportedOperationException();
		}

		com.liferay.object.model.ObjectFolder serviceBuilderObjectFolder =
			_objectFolderService.updateObjectFolder(
				objectFolder.getExternalReferenceCode(), objectFolderId,
				LocalizedMapUtil.getLocalizedMap(objectFolder.getLabel()));

		_addObjectFolderResources(
			objectFolder.getExternalReferenceCode(), objectFolderId,
			ListUtil.fromArray(objectFolder.getObjectFolderItems()),
			transform(
				new ArrayList<>(
					_objectFolderItemLocalService.
						getObjectFolderItemsByObjectFolderId(objectFolderId)),
				com.liferay.object.model.ObjectFolderItem::
					getObjectDefinitionId));

		return _toObjectFolder(serviceBuilderObjectFolder);
	}

	@Override
	public ObjectFolder putObjectFolderByExternalReferenceCode(
			String externalReferenceCode, ObjectFolder objectFolder)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-148856")) {
			throw new UnsupportedOperationException();
		}

		objectFolder.setExternalReferenceCode(externalReferenceCode);

		com.liferay.object.model.ObjectFolder serviceBuilderObjectFolder =
			_objectFolderLocalService.fetchObjectFolderByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (serviceBuilderObjectFolder != null) {
			return putObjectFolder(
				serviceBuilderObjectFolder.getObjectFolderId(), objectFolder);
		}

		return postObjectFolder(objectFolder);
	}

	@Override
	protected void preparePatch(
		ObjectFolder objectFolder, ObjectFolder existingObjectFolder) {

		if (objectFolder.getObjectFolderItems() != null) {
			existingObjectFolder.setObjectFolderItems(
				objectFolder.getObjectFolderItems());
		}
	}

	private void _addObjectFolderResources(
			String objectFolderExternalReferenceCode, long objectFolderId,
			List<ObjectFolderItem> objectFolderItems,
			List<Long> serviceBuilderObjectDefinitionIds)
		throws Exception {

		ObjectDefinitionResource.Builder builder =
			_objectDefinitionResourceFactory.create();

		ObjectDefinitionResource objectDefinitionResource = builder.user(
			contextUser
		).build();

		List<ObjectFolderItem> unlinkedObjectFolderItems = ListUtil.filter(
			objectFolderItems,
			objectFolderItem -> !objectFolderItem.getLinkedObjectDefinition());

		List<String> failedObjectDefinitionNames = new ArrayList<>();

		for (ObjectFolderItem unlinkedObjectFolderItem :
				unlinkedObjectFolderItems) {

			ObjectDefinition objectDefinition =
				unlinkedObjectFolderItem.getObjectDefinition();

			com.liferay.object.model.ObjectDefinition
				serviceBuilderObjectDefinition = null;

			if (objectDefinition != null) {
				objectDefinition.setObjectFolderExternalReferenceCode(
					objectFolderExternalReferenceCode);

				try {
					objectDefinition =
						objectDefinitionResource.
							putObjectDefinitionByExternalReferenceCode(
								objectDefinition.getExternalReferenceCode(),
								objectDefinition);

					_objectFolderItemLocalService.updateObjectFolderItem(
						objectDefinition.getId(), objectFolderId,
						unlinkedObjectFolderItem.getPositionX(),
						unlinkedObjectFolderItem.getPositionY());

					serviceBuilderObjectDefinitionIds.remove(
						objectDefinition.getId());
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(exception);
					}

					failedObjectDefinitionNames.add(objectDefinition.getName());

					serviceBuilderObjectDefinition =
						_objectDefinitionLocalService.
							fetchObjectDefinitionByExternalReferenceCode(
								objectDefinition.getExternalReferenceCode(),
								contextCompany.getCompanyId());

					if (serviceBuilderObjectDefinition != null) {
						serviceBuilderObjectDefinitionIds.remove(
							serviceBuilderObjectDefinition.
								getObjectDefinitionId());
					}
				}

				continue;
			}

			serviceBuilderObjectDefinition =
				_objectDefinitionLocalService.
					fetchObjectDefinitionByExternalReferenceCode(
						unlinkedObjectFolderItem.
							getObjectDefinitionExternalReferenceCode(),
						contextCompany.getCompanyId());

			if (serviceBuilderObjectDefinition == null) {
				continue;
			}

			_objectDefinitionLocalService.updateObjectFolderId(
				serviceBuilderObjectDefinition.getObjectDefinitionId(),
				objectFolderId);

			_objectFolderItemLocalService.updateObjectFolderItem(
				serviceBuilderObjectDefinition.getObjectDefinitionId(),
				objectFolderId, unlinkedObjectFolderItem.getPositionX(),
				unlinkedObjectFolderItem.getPositionY());

			serviceBuilderObjectDefinitionIds.remove(
				serviceBuilderObjectDefinition.getObjectDefinitionId());
		}

		com.liferay.object.model.ObjectFolder
			defaultServiceBuilderObjectFolder =
				_objectFolderService.getObjectFolderByExternalReferenceCode(
					ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_DEFAULT,
					contextCompany.getCompanyId());

		for (Long objectDefinitionId : serviceBuilderObjectDefinitionIds) {
			com.liferay.object.model.ObjectDefinition
				serviceBuilderObjectDefinition =
					_objectDefinitionLocalService.fetchObjectDefinition(
						objectDefinitionId);

			if (serviceBuilderObjectDefinition.isLinkedToObjectFolder(
					objectFolderId)) {

				continue;
			}

			_objectDefinitionLocalService.updateObjectFolderId(
				objectDefinitionId,
				defaultServiceBuilderObjectFolder.getObjectFolderId());
		}

		objectFolderItems.removeAll(unlinkedObjectFolderItems);

		for (ObjectFolderItem objectFolderItem : objectFolderItems) {
			com.liferay.object.model.ObjectDefinition
				serviceBuilderObjectDefinition =
					_objectDefinitionLocalService.
						fetchObjectDefinitionByExternalReferenceCode(
							objectFolderItem.
								getObjectDefinitionExternalReferenceCode(),
							contextCompany.getCompanyId());

			if (serviceBuilderObjectDefinition == null) {
				continue;
			}

			_objectFolderItemLocalService.updateObjectFolderItem(
				serviceBuilderObjectDefinition.getObjectDefinitionId(),
				objectFolderId, objectFolderItem.getPositionX(),
				objectFolderItem.getPositionY());
		}

		if (!failedObjectDefinitionNames.isEmpty()) {
			throw new ObjectFolderItemObjectDefinitionIdException(
				failedObjectDefinitionNames);
		}
	}

	private ObjectFolder _toObjectFolder(
		com.liferay.object.model.ObjectFolder objectFolder) {

		String permissionName =
			com.liferay.object.model.ObjectFolder.class.getName();

		return new ObjectFolder() {
			{
				setActions(
					() -> HashMapBuilder.put(
						"delete",
						() -> {
							if (objectFolder.isDefault()) {
								return null;
							}

							return addAction(
								ActionKeys.DELETE, "deleteObjectFolder",
								permissionName,
								objectFolder.getObjectFolderId());
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
							if (objectFolder.isDefault()) {
								return null;
							}

							return addAction(
								ActionKeys.UPDATE, "putObjectFolder",
								permissionName,
								objectFolder.getObjectFolderId());
						}
					).build());
				setDateCreated(objectFolder::getCreateDate);
				setDateModified(objectFolder::getModifiedDate);
				setExternalReferenceCode(
					objectFolder::getExternalReferenceCode);
				setId(objectFolder::getObjectFolderId);
				setLabel(
					() -> LocalizedMapUtil.getLanguageIdMap(
						objectFolder.getLabelMap()));
				setName(objectFolder::getName);
				setObjectFolderItems(
					() -> transformToArray(
						_objectFolderItemLocalService.
							getObjectFolderItemsByObjectFolderId(
								objectFolder.getObjectFolderId()),
						objectFolderItem -> _toObjectFolderItem(
							objectFolder.getExternalReferenceCode(),
							objectFolderItem),
						ObjectFolderItem.class));
			}
		};
	}

	private ObjectFolderItem _toObjectFolderItem(
			String objectFolderExternalReferenceCode,
			com.liferay.object.model.ObjectFolderItem objectFolderItem)
		throws Exception {

		if (objectFolderItem == null) {
			return null;
		}

		ObjectDefinitionResource.Builder builder =
			_objectDefinitionResourceFactory.create();

		ObjectDefinitionResource objectDefinitionResource = builder.user(
			contextUser
		).build();

		ObjectDefinition dtoObjectDefinition =
			objectDefinitionResource.getObjectDefinition(
				objectFolderItem.getObjectDefinitionId());

		return new ObjectFolderItem() {
			{
				setLinkedObjectDefinition(
					() -> !Objects.equals(
						dtoObjectDefinition.
							getObjectFolderExternalReferenceCode(),
						objectFolderExternalReferenceCode));
				setObjectDefinition(
					() -> {
						if (linkedObjectDefinition) {
							return null;
						}

						return dtoObjectDefinition;
					});
				setObjectDefinitionExternalReferenceCode(
					dtoObjectDefinition::getExternalReferenceCode);
				setPositionX(objectFolderItem::getPositionX);
				setPositionY(objectFolderItem::getPositionY);
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectFolderResourceImpl.class);

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectDefinitionResource.Factory _objectDefinitionResourceFactory;

	@Reference
	private ObjectFolderItemLocalService _objectFolderItemLocalService;

	@Reference
	private ObjectFolderLocalService _objectFolderLocalService;

	@Reference
	private ObjectFolderService _objectFolderService;

}