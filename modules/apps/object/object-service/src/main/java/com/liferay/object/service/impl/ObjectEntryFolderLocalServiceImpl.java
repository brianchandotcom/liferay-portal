/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.impl;

import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.exception.DuplicateObjectEntryFolderExternalReferenceCodeException;
import com.liferay.object.exception.ObjectEntryFolderNameException;
import com.liferay.object.exception.ObjectEntryFolderScopeException;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.base.ObjectEntryFolderLocalServiceBaseImpl;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectEntryFolder",
	service = AopService.class
)
public class ObjectEntryFolderLocalServiceImpl
	extends ObjectEntryFolderLocalServiceBaseImpl {

	@Override
	public ObjectEntryFolder addObjectEntryFolder(
			String externalReferenceCode, long userId, long groupId,
			Map<Locale, String> labelMap, String name,
			long parentObjectEntryFolderId, ServiceContext serviceContext)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		_validateExternalReferenceCode(
			externalReferenceCode, groupId, user.getCompanyId());
		_validateName(
			groupId, user.getCompanyId(), name, 0, parentObjectEntryFolderId);

		_validateParentObjectEntryFolderId(groupId, parentObjectEntryFolderId);

		long objectEntryFolderId = counterLocalService.increment();

		ObjectEntryFolder objectEntryFolder =
			objectEntryFolderPersistence.create(objectEntryFolderId);

		objectEntryFolder.setUuid(serviceContext.getUuid());
		objectEntryFolder.setExternalReferenceCode(externalReferenceCode);
		objectEntryFolder.setGroupId(groupId);
		objectEntryFolder.setCompanyId(user.getCompanyId());
		objectEntryFolder.setUserId(user.getUserId());
		objectEntryFolder.setUserName(user.getFullName());
		objectEntryFolder.setLabelMap(_getLabelMap(labelMap, name));
		objectEntryFolder.setName(name);
		objectEntryFolder.setParentObjectEntryFolderId(
			parentObjectEntryFolderId);
		objectEntryFolder.setTreePath(objectEntryFolder.buildTreePath());

		objectEntryFolder = objectEntryFolderPersistence.update(
			objectEntryFolder);

		_addObjectEntryFolderResources(objectEntryFolder, serviceContext);

		return objectEntryFolder;
	}

	@Override
	public ObjectEntryFolder deleteObjectEntryFolder(long objectEntryFolderId)
		throws PortalException {

		ObjectEntryFolder objectEntryFolder =
			objectEntryFolderPersistence.findByPrimaryKey(objectEntryFolderId);

		return objectEntryFolderLocalService.deleteObjectEntryFolder(
			objectEntryFolder);
	}

	@Override
	public ObjectEntryFolder deleteObjectEntryFolder(
		ObjectEntryFolder objectEntryFolder) {

		try {
			_deleteObjectEntryFolderObjectEntries(objectEntryFolder);
			_deleteObjectEntryFolders(objectEntryFolder);

			return objectEntryFolder;
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	@Override
	public ObjectEntryFolder updateObjectEntryFolder(
			long userId, long objectEntryFolderId, Map<Locale, String> labelMap,
			String name, long parentObjectEntryFolderId)
		throws PortalException {

		ObjectEntryFolder objectEntryFolder =
			objectEntryFolderPersistence.findByPrimaryKey(objectEntryFolderId);

		User user = _userLocalService.getUser(userId);

		_validateName(
			objectEntryFolder.getGroupId(), user.getCompanyId(), name,
			objectEntryFolderId, parentObjectEntryFolderId);

		_validateParentObjectEntryFolderId(
			objectEntryFolder.getGroupId(), parentObjectEntryFolderId);

		objectEntryFolder.setLabelMap(_getLabelMap(labelMap, name));
		objectEntryFolder.setName(name);
		objectEntryFolder.setParentObjectEntryFolderId(
			parentObjectEntryFolderId);
		objectEntryFolder.setTreePath(objectEntryFolder.buildTreePath());

		return objectEntryFolderPersistence.update(objectEntryFolder);
	}

	private void _addObjectEntryFolderResources(
			ObjectEntryFolder objectEntryFolder, ServiceContext serviceContext)
		throws PortalException {

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			_resourceLocalService.addResources(
				objectEntryFolder.getCompanyId(),
				objectEntryFolder.getGroupId(), objectEntryFolder.getUserId(),
				ObjectEntryFolder.class.getName(),
				objectEntryFolder.getObjectEntryFolderId(), false,
				serviceContext);
		}
		else {
			_resourceLocalService.addModelResources(
				objectEntryFolder.getCompanyId(),
				objectEntryFolder.getGroupId(), objectEntryFolder.getUserId(),
				ObjectEntryFolder.class.getName(),
				objectEntryFolder.getObjectEntryFolderId(),
				serviceContext.getModelPermissions());
		}
	}

	private void _deleteObjectEntryFolderObjectEntries(
			ObjectEntryFolder objectEntryFolder)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_objectEntryLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				dynamicQuery.add(
					RestrictionsFactoryUtil.eq(
						"groupId", objectEntryFolder.getGroupId()));
				dynamicQuery.add(
					RestrictionsFactoryUtil.eq(
						"companyId", objectEntryFolder.getCompanyId()));
				dynamicQuery.add(
					RestrictionsFactoryUtil.like(
						"treePath", objectEntryFolder.getTreePath() + "%"));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(ObjectEntry objectEntry) ->
				_objectEntryLocalService.deleteObjectEntry(objectEntry));
		actionableDynamicQuery.performActions();
	}

	private void _deleteObjectEntryFolders(ObjectEntryFolder objectEntryFolder)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			objectEntryFolderLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				dynamicQuery.add(
					RestrictionsFactoryUtil.eq(
						"groupId", objectEntryFolder.getGroupId()));
				dynamicQuery.add(
					RestrictionsFactoryUtil.eq(
						"companyId", objectEntryFolder.getCompanyId()));
				dynamicQuery.add(
					RestrictionsFactoryUtil.like(
						"treePath", objectEntryFolder.getTreePath() + "%"));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(ObjectEntryFolder descendantObjectEntryFolder) ->
				_resourceLocalService.deleteResource(
					descendantObjectEntryFolder.getCompanyId(),
					ObjectEntryFolder.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					descendantObjectEntryFolder.getObjectEntryFolderId()));
		actionableDynamicQuery.performActions();

		objectEntryFolderPersistence.removeByG_C_LikeT(
			objectEntryFolder.getGroupId(), objectEntryFolder.getCompanyId(),
			objectEntryFolder.getTreePath() + "%");
	}

	private Map<Locale, String> _getLabelMap(
		Map<Locale, String> labelMap, String name) {

		if (MapUtil.isEmpty(labelMap) ||
			!labelMap.containsKey(LocaleUtil.getSiteDefault())) {

			return HashMapBuilder.putAll(
				labelMap
			).put(
				LocaleUtil.getSiteDefault(), name
			).build();
		}

		return labelMap;
	}

	private void _validateExternalReferenceCode(
		String externalReferenceCode, long groupId, long companyId) {

		ObjectEntryFolder objectEntryFolder =
			objectEntryFolderPersistence.fetchByERC_G_C(
				externalReferenceCode, groupId, companyId);

		if (objectEntryFolder != null) {
			throw new DuplicateObjectEntryFolderExternalReferenceCodeException(
				StringBundler.concat(
					"Duplicate object entry folder with external reference ",
					"code ", externalReferenceCode));
		}
	}

	private void _validateName(
			long groupId, long companyId, String name, long objectEntryFolderId,
			long parentObjectEntryFolderId)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new ObjectEntryFolderNameException.MustNotBeNull();
		}

		ObjectEntryFolder objectEntryFolder =
			objectEntryFolderPersistence.fetchByG_C_N_P(
				groupId, companyId, name, parentObjectEntryFolderId);

		if ((objectEntryFolder != null) &&
			(objectEntryFolder.getObjectEntryFolderId() !=
				objectEntryFolderId)) {

			throw new ObjectEntryFolderNameException.MustNotBeDuplicate(name);
		}
	}

	private void _validateParentObjectEntryFolderId(
			long groupId, long parentObjectEntryFolderId)
		throws PortalException {

		if (parentObjectEntryFolderId ==
				ObjectEntryFolderConstants.
					DEFAULT_PARENT_OBJECT_ENTRY_FOLDER_ID) {

			return;
		}

		ObjectEntryFolder objectEntryFolder =
			objectEntryFolderPersistence.findByPrimaryKey(
				parentObjectEntryFolderId);

		if (objectEntryFolder.getGroupId() != groupId) {
			throw new ObjectEntryFolderScopeException(
				StringBundler.concat(
					"Group ID ", groupId,
					" does not match parent folder group ID ",
					objectEntryFolder.getGroupId()));
		}
	}

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}