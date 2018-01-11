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

package com.liferay.announcements.uad.exporter;

import com.liferay.announcements.constants.AnnouncementsPortletKeys;
import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.uad.constants.AnnouncementsUADConstants;
import com.liferay.announcements.uad.entity.AnnouncementsEntryUADEntity;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.exception.UADEntityException;
import com.liferay.user.associated.data.exception.UADEntityExporterException;
import com.liferay.user.associated.data.exporter.BaseUADEntityExporter;
import com.liferay.user.associated.data.exporter.UADEntityExporter;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Noah Sherrill
 */
@Component(
	immediate = true,
	property = "model.class.name=" + AnnouncementsUADConstants.ANNOUNCEMENTS_ENTRY,
	service = UADEntityExporter.class
)
public class AnnouncementsEntryUADEntityExporter extends BaseUADEntityExporter {

	@Override
	public void export(UADEntity uadEntity) throws PortalException {
		AnnouncementsEntry announcementsEntry = _getAnnouncementsEntry(
			uadEntity);

		String json = getJSON(announcementsEntry);

		Folder folder = _getFolder(announcementsEntry.getCompanyId());

		try {
			InputStream is = new UnsyncByteArrayInputStream(
				json.getBytes(StringPool.UTF8));

			PortletFileRepositoryUtil.addPortletFileEntry(
				folder.getGroupId(), announcementsEntry.getUserId(),
				Group.class.getName(), folder.getGroupId(),
				AnnouncementsPortletKeys.ANNOUNCEMENTS, folder.getFolderId(),
				is, uadEntity.getUADEntityId() + ".json",
				ContentTypes.APPLICATION_JSON, false);
		}
		catch (UnsupportedEncodingException uee) {
			throw new UADEntityExporterException(uee);
		}
	}

	@Override
	protected List<UADEntity> getUADEntities(long userId) {
		return _uadEntityAggregator.getUADEntities(userId);
	}

	private AnnouncementsEntry _getAnnouncementsEntry(UADEntity uadEntity)
		throws PortalException {

		_validate(uadEntity);

		AnnouncementsEntryUADEntity announcementsEntryUADEntity =
			(AnnouncementsEntryUADEntity)uadEntity;

		return announcementsEntryUADEntity.getAnnouncementsEntry();
	}

	private Folder _getFolder(long companyId) throws PortalException {
		Group guestGroup = _groupLocalService.getGroup(
			companyId, GroupConstants.GUEST);

		Repository repository =
			PortletFileRepositoryUtil.fetchPortletRepository(
				guestGroup.getGroupId(),
				AnnouncementsPortletKeys.ANNOUNCEMENTS);

		ServiceContext serviceContext = new ServiceContext();

		if (repository == null) {
			repository = PortletFileRepositoryUtil.addPortletRepository(
				guestGroup.getGroupId(), AnnouncementsPortletKeys.ANNOUNCEMENTS,
				serviceContext);
		}

		Folder folder = null;

		try {
			folder = PortletFileRepositoryUtil.getPortletFolder(
				repository.getRepositoryId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, _FOLDER_NAME);
		}
		catch (NoSuchFolderException nsfe) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsfe, nsfe);
			}

			folder = PortletFileRepositoryUtil.addPortletFolder(
				_userLocalService.getDefaultUserId(companyId),
				repository.getRepositoryId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, _FOLDER_NAME,
				serviceContext);
		}

		return folder;
	}

	private void _validate(UADEntity uadEntity) throws PortalException {
		if (!(uadEntity instanceof AnnouncementsEntryUADEntity)) {
			throw new UADEntityException();
		}
	}

	private static final String _FOLDER_NAME = "UADExport";

	private static final Log _log = LogFactoryUtil.getLog(
		AnnouncementsEntryUADEntityExporter.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(
		target = "(model.class.name=" + AnnouncementsUADConstants.ANNOUNCEMENTS_ENTRY + ")"
	)
	private UADEntityAggregator _uadEntityAggregator;

	@Reference
	private UserLocalService _userLocalService;

}