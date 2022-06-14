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

package com.liferay.frontend.icons.web.internal.repository;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.frontend.icons.web.internal.model.FrontendIconsResourcePack;
import com.liferay.frontend.icons.web.internal.util.SVGUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.documentlibrary.store.StoreFactory;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(service = FrontendIconsResourcePackRepository.class)
public class FrontendIconsResourcePackRepository {

	public void addFrontendIconsResourcePack(
			long companyId, FrontendIconsResourcePack frontendIconsResourcePack)
		throws PortalException {

		Company company = _companyLocalService.getCompany(companyId);

		Folder folder = _getFolder(company);

		FileEntry fileEntry = _portletFileRepository.fetchPortletFileEntry(
			company.getGroupId(), folder.getFolderId(),
			frontendIconsResourcePack.getName());

		if (fileEntry != null) {
			_portletFileRepository.deletePortletFileEntry(
				fileEntry.getFileEntryId());
		}

		String svgSpritemap = SVGUtil.getSVGSpritemap(
			frontendIconsResourcePack);

		_portletFileRepository.addPortletFileEntry(
			company.getGroupId(), _userLocalService.getDefaultUserId(companyId),
			null, 0, _REPOSITORY_NAME, folder.getFolderId(),
			svgSpritemap.getBytes(), frontendIconsResourcePack.getName(),
			ContentTypes.IMAGE_SVG_XML, false);

		if (frontendIconsResourcePack.isEditable()) {
			DLFileEntry dlFileEntry = _dlFileEntryLocalService.fetchFileEntry(
				company.getGroupId(), folder.getFolderId(),
				frontendIconsResourcePack.getName());

			if (dlFileEntry != null) {
				dlFileEntry.setExtraSettings("editable=true");

				_dlFileEntryLocalService.updateDLFileEntry(dlFileEntry);
			}
		}
	}

	public void deleteFrontendIconsResourcePack(long companyId, String name)
		throws PortalException {

		name = StringUtil.toUpperCase(name);

		Company company = _companyLocalService.getCompany(companyId);

		Folder folder = _getFolder(company);

		if (folder != null) {
			_portletFileRepository.deletePortletFileEntry(
				company.getGroupId(), folder.getFolderId(), name);
		}
	}

	public FrontendIconsResourcePack getFrontendIconsResourcePack(
		long companyId, String name) {

		name = StringUtil.toUpperCase(name);

		try {
			Company company = _companyLocalService.getCompany(companyId);

			Folder folder = _getFolder(company);

			FileEntry fileEntry = _portletFileRepository.fetchPortletFileEntry(
				company.getGroupId(), folder.getFolderId(), name);

			if (fileEntry == null) {
				return null;
			}

			FrontendIconsResourcePack frontendIconsResourcePack =
				new FrontendIconsResourcePack(name);

			frontendIconsResourcePack.addFrontendIconsResources(
				SVGUtil.getFrontendIconsResources(
					StringUtil.read(fileEntry.getContentStream())));

			return frontendIconsResourcePack;
		}
		catch (IOException | PortalException exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return null;
		}
	}

	public List<FrontendIconsResourcePack> getFrontendIconsResourcePacks(
			long companyId)
		throws IOException, PortalException {

		List<FrontendIconsResourcePack> frontendIconsResourcePacks =
			new ArrayList<>();

		Company company = _companyLocalService.getCompany(companyId);

		Folder folder = _getFolder(company);

		List<FileEntry> fileEntries =
			_portletFileRepository.getPortletFileEntries(
				company.getGroupId(), folder.getFolderId());

		for (FileEntry fileEntry : fileEntries) {
			String title = fileEntry.getTitle();

			DLFileEntry dlFileEntry = _dlFileEntryLocalService.fetchFileEntry(
				company.getGroupId(), folder.getFolderId(), title);

			String extraSettings = dlFileEntry.getExtraSettings();

			FrontendIconsResourcePack frontendIconsResourcePack =
				new FrontendIconsResourcePack(
					extraSettings.contains("editable=true"), title);

			frontendIconsResourcePack.addFrontendIconsResources(
				SVGUtil.getFrontendIconsResources(
					StringUtil.read(fileEntry.getContentStream())));

			frontendIconsResourcePacks.add(frontendIconsResourcePack);
		}

		return frontendIconsResourcePacks;
	}

	private Folder _getFolder(Company company) throws PortalException {
		Repository repository = _getRepository(company.getGroupId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);

		return _portletFileRepository.addPortletFolder(
			_userLocalService.getDefaultUserId(company.getCompanyId()),
			repository.getRepositoryId(), repository.getDlFolderId(),
			_ROOT_FOLDER_NAME, serviceContext);
	}

	private Repository _getRepository(long groupId) throws PortalException {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);

		return _portletFileRepository.addPortletRepository(
			groupId, _REPOSITORY_NAME, serviceContext);
	}

	private static final String _REPOSITORY_NAME =
		"com.liferay.frontend.icons.web";

	private static final String _ROOT_FOLDER_NAME =
		FrontendIconsResourcePack.class.getName();

	private static final Log _log = LogFactoryUtil.getLog(
		FrontendIconsResourcePackRepository.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private PortletFileRepository _portletFileRepository;

	@Reference(target = "(dl.store.impl.enabled=true)")
	private StoreFactory _storeFactory;

	@Reference
	private UserLocalService _userLocalService;

}