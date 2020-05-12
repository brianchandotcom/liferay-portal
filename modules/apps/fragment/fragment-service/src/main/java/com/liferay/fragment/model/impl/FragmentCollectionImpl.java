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

package com.liferay.fragment.model.impl;

import com.liferay.fragment.constants.FragmentExportImportConstants;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCompositionLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.zip.ZipWriter;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class FragmentCollectionImpl extends FragmentCollectionBaseImpl {

	@Override
	public List<FileEntry> getResources() throws PortalException {
		long groupId = getGroupId();

		if (groupId == 0) {
			Company company = CompanyLocalServiceUtil.getCompany(
				getCompanyId());

			groupId = company.getGroupId();
		}

		return PortletFileRepositoryUtil.getPortletFileEntries(
			groupId, getResourcesFolderId());
	}

	@Override
	public long getResourcesFolderId() throws PortalException {
		return getResourcesFolderId(true);
	}

	@Override
	public long getResourcesFolderId(boolean createIfAbsent)
		throws PortalException {

		if (_resourcesFolderId != 0) {
			return _resourcesFolderId;
		}

		long groupId = getGroupId();

		if (groupId == 0) {
			User user = UserLocalServiceUtil.getUser(getUserId());

			groupId = user.getGroupId();
		}

		Repository repository =
			PortletFileRepositoryUtil.fetchPortletRepository(
				groupId, FragmentPortletKeys.FRAGMENT);

		if (repository == null) {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			repository = PortletFileRepositoryUtil.addPortletRepository(
				groupId, FragmentPortletKeys.FRAGMENT, serviceContext);
		}

		Folder folder = null;

		try {
			folder = PortletFileRepositoryUtil.getPortletFolder(
				repository.getRepositoryId(), repository.getDlFolderId(),
				String.valueOf(getFragmentCollectionId()));
		}
		catch (Exception exception) {
			if (createIfAbsent) {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setAddGroupPermissions(true);
				serviceContext.setAddGuestPermissions(true);

				folder = PortletFileRepositoryUtil.addPortletFolder(
					getUserId(), repository.getRepositoryId(),
					repository.getDlFolderId(),
					String.valueOf(getFragmentCollectionId()), serviceContext);
			}
			else {
				return 0;
			}
		}

		_resourcesFolderId = folder.getFolderId();

		return _resourcesFolderId;
	}

	@Override
	public boolean hasResources() throws PortalException {
		int fileEntriesCount =
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				getGroupId(), getResourcesFolderId());

		if (fileEntriesCount <= 0) {
			return false;
		}

		return true;
	}

	@Override
	public void populateZipWriter(ZipWriter zipWriter) throws Exception {
		populateZipWriter(zipWriter, StringPool.BLANK);
	}

	@Override
	public void populateZipWriter(ZipWriter zipWriter, String path)
		throws Exception {

		path = path + StringPool.SLASH + getFragmentCollectionKey();

		JSONObject jsonObject = JSONUtil.put(
			"description", getDescription()
		).put(
			"name", getName()
		);

		zipWriter.addEntry(
			path + StringPool.SLASH +
				FragmentExportImportConstants.FILE_NAME_COLLECTION,
			jsonObject.toString());

		List<FragmentComposition> fragmentCompositions =
			FragmentCompositionLocalServiceUtil.getFragmentCompositions(
				getFragmentCollectionId());

		for (FragmentComposition fragmentComposition : fragmentCompositions) {
			fragmentComposition.populateZipWriter(
				zipWriter, path + "/fragment-compositions");
		}

		List<FragmentEntry> fragmentEntries =
			FragmentEntryLocalServiceUtil.getFragmentEntries(
				getFragmentCollectionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (FragmentEntry fragmentEntry : fragmentEntries) {
			fragmentEntry.populateZipWriter(zipWriter, path + "/fragments");
		}

		if (!hasResources()) {
			return;
		}

		for (FileEntry fileEntry : getResources()) {
			StringBundler sb = new StringBundler(4);

			sb.append(path);
			sb.append(StringPool.SLASH);
			sb.append("resources/");
			sb.append(fileEntry.getFileName());

			zipWriter.addEntry(sb.toString(), fileEntry.getContentStream());
		}
	}

	private long _resourcesFolderId;

}