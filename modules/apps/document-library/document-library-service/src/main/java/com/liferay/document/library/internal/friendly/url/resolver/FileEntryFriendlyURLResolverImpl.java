/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.friendly.url.resolver;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.friendly.url.resolver.FileEntryFriendlyURLResolver;
import com.liferay.portal.kernel.repository.model.FileEntry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = FileEntryFriendlyURLResolver.class)
public class FileEntryFriendlyURLResolverImpl
	implements FileEntryFriendlyURLResolver {

	@Override
	public FileEntry resolveFriendlyURL(long groupId, String friendlyURL)
		throws PortalException {

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				groupId, FileEntry.class, friendlyURL);

		if (friendlyURLEntry == null) {
			return null;
		}

		return _dlAppLocalService.getFileEntry(friendlyURLEntry.getClassPK());
	}

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

}