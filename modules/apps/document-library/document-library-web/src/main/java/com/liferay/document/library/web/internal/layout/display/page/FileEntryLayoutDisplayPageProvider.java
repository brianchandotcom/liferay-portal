/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.layout.display.page;

import com.liferay.friendly.url.info.item.provider.InfoItemFriendlyURLProvider;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
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
			InfoItemIdentifier infoItemIdentifier =
				infoItemReference.getInfoItemIdentifier();

			if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier)) {
				return null;
			}

			ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
				(ClassPKInfoItemIdentifier)
					infoItemReference.getInfoItemIdentifier();

			LocalRepository localRepository =
				_repositoryProvider.fetchFileEntryLocalRepository(
					classPKInfoItemIdentifier.getClassPK());

			if (localRepository == null) {
				return null;
			}

			FileEntry fileEntry = localRepository.getFileEntry(
				classPKInfoItemIdentifier.getClassPK());

			if (fileEntry.isInTrash()) {
				return null;
			}

			return new FileEntryLayoutDisplayPageObjectProvider(
				fileEntry, _infoItemFriendlyURLProvider, _language);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public LayoutDisplayPageObjectProvider<FileEntry>
		getLayoutDisplayPageObjectProvider(long groupId, String urlTitle) {

		if (urlTitle.contains(StringPool.SLASH)) {
			String[] urlNames = urlTitle.split(StringPool.SLASH);

			if (urlNames.length > 1) {
				Group group = _groupLocalService.fetchFriendlyURLGroup(
					CompanyThreadLocal.getCompanyId(),
					StringPool.SLASH + urlNames[0]);

				if (group != null) {
					return getLayoutDisplayPageObjectProvider(
						group.getGroupId(), urlNames[1]);
				}
			}
		}

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
	private GroupLocalService _groupLocalService;

	@Reference(
		target = "(item.class.name=com.liferay.portal.kernel.repository.model.FileEntry)"
	)
	private InfoItemFriendlyURLProvider<FileEntry> _infoItemFriendlyURLProvider;

	@Reference
	private Language _language;

	@Reference
	private RepositoryProvider _repositoryProvider;

}