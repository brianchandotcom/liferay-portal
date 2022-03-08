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

package com.liferay.document.library.web.internal.layout.display.page;

import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutDisplayPageProvider.class)
public class FileEntryLayoutDisplayPageProvider
	implements LayoutDisplayPageProvider<FileEntry> {

	@Override
	public String getClassName() {
		return FileEntry.class.getName();
	}

	@Override
	public LayoutDisplayPageObjectProvider<FileEntry>
		getLayoutDisplayPageObjectProvider(
			InfoItemReference infoItemReference) {

		try {
			LocalRepository localRepository =
				_repositoryProvider.fetchFileEntryLocalRepository(
					infoItemReference.getClassPK());

			if (localRepository == null) {
				return null;
			}

			FileEntry fileEntry = localRepository.getFileEntry(
				infoItemReference.getClassPK());

			if (fileEntry.isInTrash()) {
				return null;
			}

			return new FileEntryLayoutDisplayPageObjectProvider(fileEntry);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public LayoutDisplayPageObjectProvider<FileEntry>
		getLayoutDisplayPageObjectProvider(long groupId, String urlTitle) {

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				groupId, FileEntry.class, urlTitle);

		if (friendlyURLEntry != null) {
			return getLayoutDisplayPageObjectProvider(
				new InfoItemReference(
					FileEntry.class.getName(), friendlyURLEntry.getClassPK()));
		}

		if (Validator.isNumber(urlTitle)) {
			return getLayoutDisplayPageObjectProvider(
				new InfoItemReference(
					FileEntry.class.getName(), GetterUtil.getLong(urlTitle)));
		}

		return null;
	}

	@Override
	public String getURLSeparator() {
		return FriendlyURLResolverConstants.URL_SEPARATOR_FILE_ENTRY;
	}

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private RepositoryProvider _repositoryProvider;

}