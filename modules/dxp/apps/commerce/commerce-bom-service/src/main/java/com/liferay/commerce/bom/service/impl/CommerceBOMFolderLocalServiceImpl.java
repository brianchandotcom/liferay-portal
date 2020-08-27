/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.bom.service.impl;

import com.liferay.commerce.bom.model.CommerceBOMFolder;
import com.liferay.commerce.bom.service.base.CommerceBOMFolderLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.users.admin.kernel.file.uploads.UserFileUploadsSettings;

import java.util.List;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class CommerceBOMFolderLocalServiceImpl
	extends CommerceBOMFolderLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceBOMFolder addCommerceBOMFolder(
			long userId, long parentCommerceBOMFolderId, String name,
			boolean logo, byte[] logoBytes)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long commerceBOMFolderId = counterLocalService.increment();

		CommerceBOMFolder commerceBOMFolder =
			commerceBOMFolderPersistence.create(commerceBOMFolderId);

		commerceBOMFolder.setCompanyId(user.getCompanyId());
		commerceBOMFolder.setUserId(user.getUserId());
		commerceBOMFolder.setUserName(user.getFullName());
		commerceBOMFolder.setParentCommerceBOMFolderId(
			parentCommerceBOMFolderId);
		commerceBOMFolder.setName(name);

		_portal.updateImageId(
			commerceBOMFolder, logo, logoBytes, "logoId",
			_userFileUploadsSettings.getImageMaxSize(),
			_userFileUploadsSettings.getImageMaxHeight(),
			_userFileUploadsSettings.getImageMaxWidth());

		commerceBOMFolder.setTreePath(commerceBOMFolder.buildTreePath());

		commerceBOMFolder = commerceBOMFolderPersistence.update(
			commerceBOMFolder);

		// Resources

		resourceLocalService.addResources(
			user.getCompanyId(), GroupConstants.DEFAULT_LIVE_GROUP_ID,
			user.getUserId(), CommerceBOMFolder.class.getName(),
			commerceBOMFolder.getCommerceBOMFolderId(), false, false, false);

		return commerceBOMFolder;
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceBOMFolder deleteCommerceBOMFolder(
			CommerceBOMFolder commerceBOMFolder)
		throws PortalException {

		// Commerce BOM folder application rels

		commerceBOMFolderApplicationRelLocalService.
			deleteCommerceBOMFolderApplicationRelsByCommerceBOMFolderId(
				commerceBOMFolder.getCommerceBOMFolderId());

		// Commerce BOM definitions

		commerceBOMDefinitionLocalService.deleteCommerceBOMDefinitions(
			commerceBOMFolder.getCommerceBOMFolderId());

		// Resources

		resourceLocalService.deleteResource(
			commerceBOMFolder, ResourceConstants.SCOPE_INDIVIDUAL);

		// Commerce BOM folder

		return commerceBOMFolderPersistence.remove(commerceBOMFolder);
	}

	@Override
	public CommerceBOMFolder deleteCommerceBOMFolder(long commerceBOMFolderId)
		throws PortalException {

		CommerceBOMFolder commerceBOMFolder =
			commerceBOMFolderPersistence.findByPrimaryKey(commerceBOMFolderId);

		return commerceBOMFolderLocalService.deleteCommerceBOMFolder(
			commerceBOMFolder);
	}

	@Override
	public void deleteCommerceBOMFolders(long companyId)
		throws PortalException {

		List<CommerceBOMFolder> commerceBOMFolders =
			commerceBOMFolderPersistence.findByCompanyId(companyId);

		for (CommerceBOMFolder commerceBOMFolder : commerceBOMFolders) {
			commerceBOMFolderLocalService.deleteCommerceBOMFolder(
				commerceBOMFolder);
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceBOMFolder updateCommerceBOMFolder(
			long commerceBOMFolderId, String name, boolean logo,
			byte[] logoBytes)
		throws PortalException {

		CommerceBOMFolder commerceBOMFolder =
			commerceBOMFolderLocalService.getCommerceBOMFolder(
				commerceBOMFolderId);

		commerceBOMFolder.setName(name);

		_portal.updateImageId(
			commerceBOMFolder, logo, logoBytes, "logoId",
			_userFileUploadsSettings.getImageMaxSize(),
			_userFileUploadsSettings.getImageMaxHeight(),
			_userFileUploadsSettings.getImageMaxWidth());

		return commerceBOMFolderPersistence.update(commerceBOMFolder);
	}

	@ServiceReference(type = Portal.class)
	private Portal _portal;

	@ServiceReference(type = UserFileUploadsSettings.class)
	private UserFileUploadsSettings _userFileUploadsSettings;

}