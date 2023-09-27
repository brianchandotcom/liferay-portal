/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.web.internal.layout.display.page;

import com.liferay.asset.util.AssetHelper;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.friendly.url.info.item.provider.InfoItemFriendlyURLProvider;
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
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutDisplayPageProvider.class)
public class BlogsLayoutDisplayPageProvider
	implements LayoutDisplayPageProvider<BlogsEntry> {

	@Override
	public String getClassName() {
		return BlogsEntry.class.getName();
	}

	@Override
	public LayoutDisplayPageObjectProvider<BlogsEntry>
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

			BlogsEntry blogsEntry = _blogsEntryLocalService.fetchBlogsEntry(
				classPKInfoItemIdentifier.getClassPK());

			if ((blogsEntry == null) || blogsEntry.isDraft() ||
				blogsEntry.isInTrash()) {

				return null;
			}

			return new BlogsLayoutDisplayPageObjectProvider(
				_assetHelper, blogsEntry, _infoItemFriendlyURLProvider,
				_language);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public LayoutDisplayPageObjectProvider<BlogsEntry>
		getLayoutDisplayPageObjectProvider(long groupId, String urlTitle) {

		try {
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

			BlogsEntry blogsEntry = _blogsEntryLocalService.getEntry(
				groupId, urlTitle);

			if (blogsEntry.isInTrash()) {
				return null;
			}

			return new BlogsLayoutDisplayPageObjectProvider(
				_assetHelper, blogsEntry, _infoItemFriendlyURLProvider,
				_language);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public String getURLSeparator() {
		return FriendlyURLResolverConstants.URL_SEPARATOR_BLOGS_ENTRY;
	}

	@Reference
	private AssetHelper _assetHelper;

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(target = "(item.class.name=com.liferay.blogs.model.BlogsEntry)")
	private InfoItemFriendlyURLProvider<BlogsEntry>
		_infoItemFriendlyURLProvider;

	@Reference
	private Language _language;

}