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
import com.liferay.info.item.ERCInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.BaseLayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutDisplayPageProvider.class)
public class BlogsLayoutDisplayPageProvider
	extends BaseLayoutDisplayPageProvider<BlogsEntry> {

	@Override
	public String getClassName() {
		return BlogsEntry.class.getName();
	}

	@Override
	public String getDefaultURLSeparator() {
		return FriendlyURLResolverConstants.URL_SEPARATOR_BLOGS_ENTRY;
	}

	@Override
	public LayoutDisplayPageObjectProvider<BlogsEntry>
		getLayoutDisplayPageObjectProvider(BlogsEntry blogsEntry) {

		if ((blogsEntry == null) || blogsEntry.isDraft() ||
			blogsEntry.isInTrash()) {

			return null;
		}

		return new BlogsLayoutDisplayPageObjectProvider(
			_assetHelper, blogsEntry, _infoItemFriendlyURLProvider, _language);
	}

	@Override
	public LayoutDisplayPageObjectProvider<BlogsEntry>
		getLayoutDisplayPageObjectProvider(
			InfoItemReference infoItemReference) {

		return getLayoutDisplayPageObjectProvider(
			_getGroupId(), infoItemReference);
	}

	@Override
	public LayoutDisplayPageObjectProvider<BlogsEntry>
		getLayoutDisplayPageObjectProvider(
			long groupId, InfoItemReference infoItemReference) {

		return _getLayoutDisplayPageObjectProvider(
			_getGroupId(groupId, infoItemReference.getInfoItemIdentifier()),
			infoItemReference);
	}

	@Override
	public LayoutDisplayPageObjectProvider<BlogsEntry>
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

		BlogsEntry blogsEntry = _blogsEntryLocalService.fetchEntry(
			groupId, urlTitle);

		if (blogsEntry == null) {
			blogsEntry = _blogsEntryLocalService.fetchEntry(
				groupId,
				urlTitle.substring(urlTitle.lastIndexOf(StringPool.SLASH) + 1));
		}

		if ((blogsEntry == null) || blogsEntry.isInTrash()) {
			return null;
		}

		return new BlogsLayoutDisplayPageObjectProvider(
			_assetHelper, blogsEntry, _infoItemFriendlyURLProvider, _language);
	}

	private long _getCompanyId() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			return serviceContext.getCompanyId();
		}

		Long companyId = CompanyThreadLocal.getCompanyId();

		if (companyId != null) {
			return companyId;
		}

		throw new IllegalStateException(
			"Neither service context thread local nor company thread local " +
				"are initialized");
	}

	private long _getGroupId() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			return serviceContext.getScopeGroupId();
		}

		Long groupId = GroupThreadLocal.getGroupId();

		if (groupId != null) {
			return groupId;
		}

		throw new IllegalStateException(
			"Neither service context thread local nor group thread local are " +
				"initialized");
	}

	private long _getGroupId(
		long groupId, InfoItemIdentifier infoItemIdentifier) {

		try {
			if (!(infoItemIdentifier instanceof ERCInfoItemIdentifier)) {
				return groupId;
			}

			ERCInfoItemIdentifier ercInfoItemIdentifier =
				(ERCInfoItemIdentifier)infoItemIdentifier;

			if (Validator.isNull(
					ercInfoItemIdentifier.getScopeExternalReferenceCode())) {

				return groupId;
			}

			Group group = GroupLocalServiceUtil.getGroupByExternalReferenceCode(
				ercInfoItemIdentifier.getScopeExternalReferenceCode(),
				_getCompanyId());

			return group.getGroupId();
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	private BlogsLayoutDisplayPageObjectProvider
		_getLayoutDisplayPageObjectProvider(
			long groupId, InfoItemReference infoItemReference) {

		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier) &&
			!(infoItemIdentifier instanceof ERCInfoItemIdentifier)) {

			return null;
		}

		BlogsEntry blogsEntry = null;

		if (infoItemIdentifier instanceof ClassPKInfoItemIdentifier) {
			ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
				(ClassPKInfoItemIdentifier)
					infoItemReference.getInfoItemIdentifier();

			blogsEntry = _blogsEntryLocalService.fetchBlogsEntry(
				classPKInfoItemIdentifier.getClassPK());
		}
		else {
			ERCInfoItemIdentifier ercInfoItemIdentifier =
				(ERCInfoItemIdentifier)infoItemIdentifier;

			blogsEntry =
				_blogsEntryLocalService.fetchBlogsEntryByExternalReferenceCode(
					ercInfoItemIdentifier.getExternalReferenceCode(), groupId);
		}

		if ((blogsEntry == null) || blogsEntry.isDraft() ||
			blogsEntry.isInTrash()) {

			return null;
		}

		return new BlogsLayoutDisplayPageObjectProvider(
			_assetHelper, blogsEntry, _infoItemFriendlyURLProvider, _language);
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