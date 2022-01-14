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
import com.liferay.frontend.icons.web.internal.model.FrontendIconsResource;
import com.liferay.frontend.icons.web.internal.model.FrontendIconsResourcePack;
import com.liferay.frontend.icons.web.internal.util.ClayFrontendIconsResourcePackUtil;
import com.liferay.frontend.icons.web.internal.util.SVGUtil;
import com.liferay.portal.kernel.exception.PortalException;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

		Folder companyIconsFolder = _getFolder(company);

		String svgSpritemap = SVGUtil.getSVGSpritemap(
			frontendIconsResourcePack);

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.fetchFileEntry(
			company.getGroupId(), companyIconsFolder.getFolderId(),
			frontendIconsResourcePack.getName());

		FileEntry fileEntry = _portletFileRepository.fetchPortletFileEntry(
			company.getGroupId(), companyIconsFolder.getFolderId(),
			frontendIconsResourcePack.getName());

		if (dlFileEntry != null) {
			_portletFileRepository.deletePortletFileEntry(
				fileEntry.getFileEntryId());
		}

		_portletFileRepository.addPortletFileEntry(
			company.getGroupId(), _userLocalService.getDefaultUserId(companyId),
			null, 0, _REPOSITORY_NAME, companyIconsFolder.getFolderId(),
			svgSpritemap.getBytes(), frontendIconsResourcePack.getName(),
			ContentTypes.IMAGE_SVG_XML, false);
	}

	public void deleteIconResourcePack(long companyId, String iconPackName)
		throws PortalException {

		Company company = _companyLocalService.getCompany(companyId);

		Folder companyIconsFolder = _getFolder(company);

		_portletFileRepository.deletePortletFileEntry(
			company.getGroupId(), companyIconsFolder.getFolderId(),
			iconPackName);
	}

	public Optional<FrontendIconsResourcePack> getFrontendIconsResourcePack(
		long companyId, String name) {

		if (Objects.equals(
				name,
				ClayFrontendIconsResourcePackUtil.
					CLAY_FRONTEND_ICONS_PACK_NAME)) {

			return Optional.of(
				ClayFrontendIconsResourcePackUtil.
					getFrontendIconResourcePack());
		}

		try {
			Company company = _companyLocalService.getCompany(companyId);

			Folder companyIconsFolder = _getFolder(company);

			FileEntry fileEntry = _portletFileRepository.fetchPortletFileEntry(
				company.getGroupId(), companyIconsFolder.getFolderId(), name);

			if (fileEntry == null) {
				return Optional.empty();
			}

			FrontendIconsResourcePack frontendIconsResourcePack =
				new FrontendIconsResourcePack(name);

			frontendIconsResourcePack.addFrontendIconsResources(
				SVGUtil.getFrontendIconsResources(
					StringUtil.read(
						_dlFileEntryLocalService.getFileAsStream(
							fileEntry.getFileEntryId(),
							fileEntry.getVersion()))));

			return Optional.of(frontendIconsResourcePack);
		}
		catch (Exception exception) {
			return Optional.empty();
		}
	}

	public List<FrontendIconsResourcePack> getFrontendIconsResourcePacks(
			long companyId)
		throws Exception {

		List<FrontendIconsResourcePack> frontendIconsResourcePacks =
			new ArrayList<>();

		frontendIconsResourcePacks.add(
			ClayFrontendIconsResourcePackUtil.getFrontendIconResourcePack());

		Company company = _companyLocalService.getCompany(companyId);

		Folder companyIconsFolder = _getFolder(company);

		List<FileEntry> fileEntries =
			_portletFileRepository.getPortletFileEntries(
				company.getGroupId(), companyIconsFolder.getFolderId());

		for (FileEntry fileEntry : fileEntries) {
			FrontendIconsResourcePack frontendIconsResourcePack =
				new FrontendIconsResourcePack(fileEntry.getTitle());

			List<FrontendIconsResource> iconResources =
				SVGUtil.getFrontendIconsResources(
					StringUtil.read(
						_dlFileEntryLocalService.getFileAsStream(
							fileEntry.getFileEntryId(),
							fileEntry.getVersion())));

			frontendIconsResourcePack.addFrontendIconsResources(iconResources);

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

	private static final String _REPOSITORY_NAME = "icon.admin.web";

	private static final String _ROOT_FOLDER_NAME = "icon.admin.web.icon.packs";

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private PortletFileRepository _portletFileRepository;

	@Reference
	private UserLocalService _userLocalService;

}