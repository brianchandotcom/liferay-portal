/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.service.impl;

import com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingException;
import com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingFileEntryIdException;
import com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingURLException;
import com.liferay.commerce.product.type.virtual.model.CPDVirtualSettingFileEntry;
import com.liferay.commerce.product.type.virtual.service.base.CPDVirtualSettingFileEntryLocalServiceBaseImpl;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Validator;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "model.class.name=com.liferay.commerce.product.type.virtual.model.CPDVirtualSettingFileEntry",
	service = AopService.class
)
public class CPDVirtualSettingFileEntryLocalServiceImpl
	extends CPDVirtualSettingFileEntryLocalServiceBaseImpl {

	@Override
	public CPDVirtualSettingFileEntry addCPDVirtualSettingFileEntry(
			long userId, long groupId, long cpDefinitionVirtualSettingId,
			long fileEntryId, String url, String version)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		if (Validator.isNotNull(url)) {
			fileEntryId = 0;
		}
		else {
			url = null;
		}

		_validate(fileEntryId, url);

		long cpdVirtualSettingFileEntryId = counterLocalService.increment();

		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry =
			cpdVirtualSettingFileEntryPersistence.create(
				cpdVirtualSettingFileEntryId);

		cpdVirtualSettingFileEntry.setGroupId(groupId);
		cpdVirtualSettingFileEntry.setCompanyId(user.getCompanyId());
		cpdVirtualSettingFileEntry.setUserId(user.getUserId());
		cpdVirtualSettingFileEntry.setUserName(user.getFullName());
		cpdVirtualSettingFileEntry.setCPDefinitionVirtualSettingId(
			cpDefinitionVirtualSettingId);
		cpdVirtualSettingFileEntry.setFileEntryId(fileEntryId);
		cpdVirtualSettingFileEntry.setUrl(url);
		cpdVirtualSettingFileEntry.setVersion(version);

		return cpdVirtualSettingFileEntryPersistence.update(
			cpdVirtualSettingFileEntry);
	}

	@Override
	public List<CPDVirtualSettingFileEntry> getCPDVirtualSettingFileEntries(
		long cpDefinitionVirtualSettingId) {

		return cpdVirtualSettingFileEntryPersistence.
			findByCPDefinitionVirtualSettingId(cpDefinitionVirtualSettingId);
	}

	@Override
	public List<CPDVirtualSettingFileEntry> getCPDVirtualSettingFileEntries(
		long cpDefinitionVirtualSettingId, int start, int end) {

		return cpdVirtualSettingFileEntryPersistence.
			findByCPDefinitionVirtualSettingId(
				cpDefinitionVirtualSettingId, start, end, null);
	}

	@Override
	public int getCPDVirtualSettingFileEntriesCount(
		long cpDefinitionVirtualSettingId) {

		return cpdVirtualSettingFileEntryPersistence.
			countByCPDefinitionVirtualSettingId(cpDefinitionVirtualSettingId);
	}

	@Override
	public CPDVirtualSettingFileEntry updateCPDVirtualSettingFileEntry(
			long cpdVirtualSettingFileEntryId, long fileEntryId, String url,
			String version)
		throws PortalException {

		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry =
			cpdVirtualSettingFileEntryPersistence.findByPrimaryKey(
				cpdVirtualSettingFileEntryId);

		if (Validator.isNotNull(url)) {
			fileEntryId = 0;
		}
		else {
			url = null;
		}

		_validate(fileEntryId, url);

		cpdVirtualSettingFileEntry.setFileEntryId(fileEntryId);
		cpdVirtualSettingFileEntry.setUrl(url);
		cpdVirtualSettingFileEntry.setVersion(version);

		return cpdVirtualSettingFileEntryPersistence.update(
			cpdVirtualSettingFileEntry);
	}

	private void _validate(long fileEntryId, String url)
		throws PortalException {

		if (fileEntryId > 0) {
			try {
				_dlAppLocalService.getFileEntry(fileEntryId);
			}
			catch (NoSuchFileEntryException noSuchFileEntryException) {
				throw new CPDefinitionVirtualSettingFileEntryIdException(
					noSuchFileEntryException);
			}
		}
		else if (Validator.isNull(url)) {
			throw new CPDefinitionVirtualSettingException();
		}
		else {
			try {
				new URL(url);
			}
			catch (MalformedURLException malformedURLException) {
				throw new CPDefinitionVirtualSettingURLException(
					malformedURLException);
			}
		}
	}

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private UserLocalService _userLocalService;

}