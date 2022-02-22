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

package com.liferay.object.internal.field.business.type.attachment;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.object.field.render.ObjectFieldRenderingContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(immediate = true, service = AttachmentHelper.class)
public class AttachmentHelper {

	public Folder getFolder(
		ObjectFieldRenderingContext objectFieldRenderingContext) {

		Repository repository = _getRepository(
			objectFieldRenderingContext.getGroupId(),
			objectFieldRenderingContext.getPortletId(),
			objectFieldRenderingContext.getHttpServletRequest());

		if (repository == null) {
			return null;
		}

		return _getFolder(
			objectFieldRenderingContext.getUserId(),
			repository.getRepositoryId(),
			objectFieldRenderingContext.getHttpServletRequest());
	}

	private Folder _addFolder(
		long userId, long repositoryId, HttpServletRequest httpServletRequest) {

		try {
			return _portletFileRepository.addPortletFolder(
				userId, repositoryId,
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				String.valueOf(userId),
				ServiceContextFactory.getInstance(httpServletRequest));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return null;
		}
	}

	private Folder _getFolder(
		long userId, long repositoryId, HttpServletRequest httpServletRequest) {

		try {
			return _portletFileRepository.getPortletFolder(
				repositoryId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				String.valueOf(userId));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return _addFolder(userId, repositoryId, httpServletRequest);
		}
	}

	private Repository _getRepository(
		long groupId, String portletId, HttpServletRequest httpServletRequest) {

		Repository repository = _portletFileRepository.fetchPortletRepository(
			groupId, portletId);

		if (repository != null) {
			return repository;
		}

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				httpServletRequest);

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			return _portletFileRepository.addPortletRepository(
				groupId, portletId, serviceContext);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AttachmentHelper.class);

	@Reference
	private PortletFileRepository _portletFileRepository;

}