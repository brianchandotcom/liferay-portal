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

package com.liferay.layout.page.template.service.impl;

import com.liferay.layout.page.template.exception.DuplicateLayoutPageTemplateFolderException;
import com.liferay.layout.page.template.exception.LayoutPageTemplateFolderNameException;
import com.liferay.layout.page.template.model.LayoutPageTemplate;
import com.liferay.layout.page.template.model.LayoutPageTemplateFolder;
import com.liferay.layout.page.template.service.base.LayoutPageTemplateFolderLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Jürgen Kappler
 */
public class LayoutPageTemplateFolderLocalServiceImpl
	extends LayoutPageTemplateFolderLocalServiceBaseImpl {

	@Override
	public LayoutPageTemplateFolder addLayoutPageTemplateFolder(
			long groupId, long userId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		// Layout Page Template Folder

		User user = userLocalService.getUser(userId);

		validate(groupId, name);

		long layoutPageTemplateId = counterLocalService.increment();

		LayoutPageTemplateFolder layoutPageTemplateFolder =
			layoutPageTemplateFolderPersistence.create(layoutPageTemplateId);

		layoutPageTemplateFolder.setGroupId(groupId);
		layoutPageTemplateFolder.setCompanyId(user.getCompanyId());
		layoutPageTemplateFolder.setUserId(user.getUserId());
		layoutPageTemplateFolder.setUserName(user.getFullName());
		layoutPageTemplateFolder.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		layoutPageTemplateFolder.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		layoutPageTemplateFolder.setName(name);
		layoutPageTemplateFolder.setDescription(description);

		layoutPageTemplateFolderPersistence.update(layoutPageTemplateFolder);

		// Resources

		resourceLocalService.addModelResources(
			layoutPageTemplateFolder, serviceContext);

		return layoutPageTemplateFolder;
	}

	@Override
	public LayoutPageTemplateFolder deleteLayoutPageTemplateFolder(
			LayoutPageTemplateFolder layoutPageTemplateFolder)
		throws PortalException {

		/// Layout Page Template Folder

		layoutPageTemplateFolderPersistence.remove(layoutPageTemplateFolder);

		// Resources

		resourceLocalService.deleteResource(
			layoutPageTemplateFolder.getCompanyId(),
			LayoutPageTemplateFolder.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			layoutPageTemplateFolder.getLayoutPageTemplateFolderId());

		// Page Templates

		List<LayoutPageTemplate> layoutPageTemplates =
			layoutPageTemplatePersistence.findByLayoutPageTemplateFolderId(
				layoutPageTemplateFolder.getLayoutPageTemplateFolderId());

		for (LayoutPageTemplate layoutPageTemplate : layoutPageTemplates) {
			layoutPageTemplateLocalService.deleteLayoutPageTemplate(
				layoutPageTemplate);
		}

		return layoutPageTemplateFolder;
	}

	@Override
	public LayoutPageTemplateFolder deleteLayoutPageTemplateFolder(
			long layoutPageTemplateFolderId)
		throws PortalException {

		LayoutPageTemplateFolder layoutPageTemplateFolder =
			getLayoutPageTemplateFolder(layoutPageTemplateFolderId);

		return deleteLayoutPageTemplateFolder(layoutPageTemplateFolder);
	}

	@Override
	public LayoutPageTemplateFolder fetchLayoutPageTemplateFolder(
		long layoutPageTemplateFolderId) {

		return layoutPageTemplateFolderPersistence.fetchByPrimaryKey(
			layoutPageTemplateFolderId);
	}

	@Override
	public List<LayoutPageTemplateFolder> getLayoutPageTemplateFolders(
			long groupId, int start, int end)
		throws PortalException {

		return getLayoutPageTemplateFolders(groupId, start, end, null);
	}

	@Override
	public List<LayoutPageTemplateFolder> getLayoutPageTemplateFolders(
			long groupId, int start, int end,
			OrderByComparator<LayoutPageTemplateFolder> orderByComparator)
		throws PortalException {

		return layoutPageTemplateFolderPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<LayoutPageTemplateFolder> getLayoutPageTemplateFolders(
		long groupId, String name, int start, int end,
		OrderByComparator<LayoutPageTemplateFolder> orderByComparator) {

		if (Validator.isNull(name)) {
			return layoutPageTemplateFolderPersistence.findByGroupId(
				groupId, start, end, orderByComparator);
		}

		return layoutPageTemplateFolderPersistence.findByG_LikeN(
			groupId, name, start, end, orderByComparator);
	}

	@Override
	public LayoutPageTemplateFolder updateLayoutPageTemplateFolder(
			long layoutPageTemplateFolderId, String name, String description)
		throws PortalException {

		LayoutPageTemplateFolder layoutPageTemplateFolder =
			layoutPageTemplateFolderPersistence.findByPrimaryKey(
				layoutPageTemplateFolderId);

		if (!Objects.equals(layoutPageTemplateFolder.getName(), name)) {
			validate(layoutPageTemplateFolder.getGroupId(), name);
		}

		layoutPageTemplateFolder.setModifiedDate(new Date());
		layoutPageTemplateFolder.setName(name);
		layoutPageTemplateFolder.setDescription(description);

		layoutPageTemplateFolderPersistence.update(layoutPageTemplateFolder);

		return layoutPageTemplateFolder;
	}

	protected void validate(long groupId, String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new LayoutPageTemplateFolderNameException(
				"Name must not be null for group " + groupId);
		}

		LayoutPageTemplateFolder layoutPageTemplateFolder =
			layoutPageTemplateFolderPersistence.fetchByG_N(groupId, name);

		if (layoutPageTemplateFolder != null) {
			throw new DuplicateLayoutPageTemplateFolderException(name);
		}
	}

}