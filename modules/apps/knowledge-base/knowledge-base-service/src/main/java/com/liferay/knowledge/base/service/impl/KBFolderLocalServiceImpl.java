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

package com.liferay.knowledge.base.service.impl;

import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.exception.DuplicateKBFolderNameException;
import com.liferay.knowledge.base.exception.InvalidKBFolderNameException;
import com.liferay.knowledge.base.exception.KBFolderParentException;
import com.liferay.knowledge.base.exception.NoSuchFolderException;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.knowledge.base.service.base.KBFolderLocalServiceBaseImpl;
import com.liferay.knowledge.base.util.KnowledgeBaseUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.knowledge.base.model.KBFolder",
	service = AopService.class
)
public class KBFolderLocalServiceImpl extends KBFolderLocalServiceBaseImpl {

	@Override
	public KBFolder addKBFolder(
			long userId, long groupId, long parentResourceClassNameId,
			long parentResourcePrimKey, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		// KB folder

		User user = userLocalService.getUser(userId);
		Date now = new Date();

		validateName(groupId, parentResourcePrimKey, name);
		validateParent(parentResourceClassNameId, parentResourcePrimKey);

		long kbFolderId = counterLocalService.increment();

		KBFolder kbFolder = kbFolderPersistence.create(kbFolderId);

		kbFolder.setUuid(serviceContext.getUuid());
		kbFolder.setGroupId(groupId);
		kbFolder.setCompanyId(user.getCompanyId());
		kbFolder.setUserId(userId);
		kbFolder.setUserName(user.getFullName());
		kbFolder.setCreateDate(now);
		kbFolder.setModifiedDate(now);
		kbFolder.setParentKBFolderId(parentResourcePrimKey);
		kbFolder.setName(name);
		kbFolder.setUrlTitle(
			getUniqueUrlTitle(
				groupId, parentResourcePrimKey, kbFolderId, name));
		kbFolder.setDescription(description);
		kbFolder.setExpandoBridgeAttributes(serviceContext);

		kbFolderPersistence.update(kbFolder);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addKBFolderResources(
				kbFolder, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addKBFolderResources(
				kbFolder, serviceContext.getModelPermissions());
		}

		return kbFolder;
	}

	@Override
	public KBFolder deleteKBFolder(long kbFolderId) throws PortalException {
		KBFolder kbFolder = kbFolderPersistence.findByPrimaryKey(kbFolderId);

		_kbArticleLocalService.deleteKBArticles(
			kbFolder.getGroupId(), kbFolder.getKbFolderId());

		List<KBFolder> childKBFolders = kbFolderPersistence.findByG_P(
			kbFolder.getGroupId(), kbFolder.getKbFolderId());

		for (KBFolder childKBFolder : childKBFolders) {
			deleteKBFolder(childKBFolder.getKbFolderId());
		}

		// Expando

		expandoRowLocalService.deleteRows(kbFolder.getKbFolderId());

		return kbFolderPersistence.remove(kbFolder);
	}

	@Override
	public void deleteKBFolders(long groupId) throws PortalException {
		List<KBFolder> kbFolders = getKBFolders(
			groupId, KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (KBFolder kbFolder : kbFolders) {
			deleteKBFolder(kbFolder.getKbFolderId());
		}
	}

	@Override
	public KBFolder fetchFirstChildKBFolder(long groupId, long kbFolderId)
		throws PortalException {

		return fetchFirstChildKBFolder(groupId, kbFolderId, null);
	}

	@Override
	public KBFolder fetchFirstChildKBFolder(
			long groupId, long kbFolderId, OrderByComparator<KBFolder> obc)
		throws PortalException {

		return kbFolderPersistence.fetchByG_P_First(groupId, kbFolderId, obc);
	}

	@Override
	public KBFolder fetchKBFolder(long kbFolderId) {
		return kbFolderPersistence.fetchByPrimaryKey(kbFolderId);
	}

	@Override
	public KBFolder fetchKBFolder(String uuid, long groupId) {
		return kbFolderPersistence.fetchByUUID_G(uuid, groupId);
	}

	@Override
	public KBFolder fetchKBFolderByUrlTitle(
			long groupId, long parentKbFolderId, String urlTitle)
		throws PortalException {

		return kbFolderPersistence.fetchByG_P_UT(
			groupId, parentKbFolderId, urlTitle);
	}

	@Override
	public KBFolder getKBFolderByUrlTitle(
			long groupId, long parentKbFolderId, String urlTitle)
		throws PortalException {

		return kbFolderPersistence.findByG_P_UT(
			groupId, parentKbFolderId, urlTitle);
	}

	@Override
	public List<KBFolder> getKBFolders(
			long groupId, long parentKBFolderId, int start, int end)
		throws PortalException {

		return kbFolderPersistence.findByG_P(
			groupId, parentKBFolderId, start, end);
	}

	@Override
	public List<Object> getKBFoldersAndKBArticles(
		long groupId, long parentResourcePrimKey, int status, int start,
		int end, OrderByComparator<?> orderByComparator) {

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(
			status, start, end, orderByComparator);

		return kbFolderFinder.findF_A_ByG_P(
			groupId, parentResourcePrimKey, queryDefinition);
	}

	@Override
	public int getKBFoldersAndKBArticlesCount(
		long groupId, long parentResourcePrimKey, int status) {

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(status);

		return kbFolderFinder.countF_A_ByG_P(
			groupId, parentResourcePrimKey, queryDefinition);
	}

	@Override
	public int getKBFoldersCount(long groupId, long parentKBFolderId)
		throws PortalException {

		return kbFolderPersistence.countByG_P(groupId, parentKBFolderId);
	}

	@Override
	public void moveKBFolder(long kbFolderId, long parentKBFolderId)
		throws PortalException {

		KBFolder kbFolder = kbFolderPersistence.findByPrimaryKey(kbFolderId);

		if (parentKBFolderId != KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			KBFolder parentKBFolder = kbFolderPersistence.findByPrimaryKey(
				parentKBFolderId);

			validateParent(kbFolder, parentKBFolder);

			parentKBFolderId = parentKBFolder.getKbFolderId();
		}

		kbFolder.setParentKBFolderId(parentKBFolderId);

		kbFolderPersistence.update(kbFolder);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #updateKBFolder(long, long, long, String, String,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public KBFolder updateKBFolder(
			long parentResourceClassNameId, long parentResourcePrimKey,
			long kbFolderId, String name, String description)
		throws PortalException {

		return updateKBFolder(
			parentResourceClassNameId, parentResourcePrimKey, kbFolderId, name,
			description, new ServiceContext());
	}

	@Override
	public KBFolder updateKBFolder(
			long parentResourceClassNameId, long parentResourcePrimKey,
			long kbFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		validateParent(parentResourceClassNameId, parentResourcePrimKey);

		KBFolder kbFolder = kbFolderPersistence.findByPrimaryKey(kbFolderId);

		if (!StringUtil.equals(name, kbFolder.getName())) {
			validateName(kbFolder.getGroupId(), parentResourcePrimKey, name);
		}

		kbFolder.setModifiedDate(new Date());
		kbFolder.setParentKBFolderId(parentResourcePrimKey);
		kbFolder.setName(name);
		kbFolder.setDescription(description);
		kbFolder.setExpandoBridgeAttributes(serviceContext);

		return kbFolderPersistence.update(kbFolder);
	}

	protected void addKBFolderResources(
			KBFolder kbFolder, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		resourceLocalService.addResources(
			kbFolder.getCompanyId(), kbFolder.getGroupId(),
			kbFolder.getUserId(), KBFolder.class.getName(),
			kbFolder.getKbFolderId(), false, addGroupPermissions,
			addGuestPermissions);
	}

	protected void addKBFolderResources(
			KBFolder kbFolder, ModelPermissions modelPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(
			kbFolder.getCompanyId(), kbFolder.getGroupId(),
			kbFolder.getUserId(), KBFolder.class.getName(),
			kbFolder.getKbFolderId(), modelPermissions);
	}

	protected String getUniqueUrlTitle(
		long groupId, long parentKbFolderId, long kbFolderId, String name) {

		String urlTitle = KnowledgeBaseUtil.getUrlTitle(kbFolderId, name);

		String uniqueUrlTitle = urlTitle;

		KBFolder kbFolder = kbFolderPersistence.fetchByG_P_UT(
			groupId, parentKbFolderId, uniqueUrlTitle);

		for (int i = 1; kbFolder != null; i++) {
			uniqueUrlTitle = urlTitle + StringPool.DASH + i;

			kbFolder = kbFolderPersistence.fetchByG_P_UT(
				groupId, parentKbFolderId, uniqueUrlTitle);
		}

		return uniqueUrlTitle;
	}

	protected void validateName(
			long groupId, long parentKBFolderId, String name)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new InvalidKBFolderNameException("KB folder name is null");
		}

		KBFolder kbFolder = kbFolderPersistence.fetchByG_P_N(
			groupId, parentKBFolderId, name);

		if (kbFolder != null) {
			throw new DuplicateKBFolderNameException(
				String.format("A KB folder with name %s already exists", name));
		}
	}

	protected void validateParent(KBFolder kbFolder, KBFolder parentKBFolder)
		throws PortalException {

		if (kbFolder.getGroupId() != parentKBFolder.getGroupId()) {
			throw new NoSuchFolderException(
				String.format(
					"No KB folder with KB folder ID %s found in group %s",
					parentKBFolder.getKbFolderId(), kbFolder.getGroupId()));
		}

		List<Long> ancestorKBFolderIds =
			parentKBFolder.getAncestorKBFolderIds();

		if (ancestorKBFolderIds.contains(kbFolder.getKbFolderId())) {
			throw new KBFolderParentException(
				String.format(
					"Cannot move KBFolder %s inside its descendant KBFolder %s",
					kbFolder.getKbFolderId(), parentKBFolder.getKbFolderId()));
		}
	}

	protected void validateParent(
			long parentResourceClassNameId, long parentResourcePrimKey)
		throws PortalException {

		long kbFolderClassNameId = classNameLocalService.getClassNameId(
			KBFolderConstants.getClassName());

		KBFolder parentKBFolder = null;

		if (parentResourceClassNameId == kbFolderClassNameId) {
			if (parentResourcePrimKey ==
					KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				return;
			}

			parentKBFolder = kbFolderPersistence.fetchByPrimaryKey(
				parentResourcePrimKey);
		}

		if (parentKBFolder == null) {
			throw new NoSuchFolderException(
				String.format(
					"No KB folder found with KB folder ID %",
					parentResourcePrimKey));
		}
	}

	@Reference
	private KBArticleLocalService _kbArticleLocalService;

}