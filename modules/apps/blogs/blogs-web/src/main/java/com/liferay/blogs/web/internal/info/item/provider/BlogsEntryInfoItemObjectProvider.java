/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.web.internal.info.item.provider;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.ERCInfoItemIdentifier;
import com.liferay.info.item.GroupUrlTitleInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(
	property = {
		"info.item.identifier=com.liferay.info.item.ClassPKInfoItemIdentifier",
		"info.item.identifier=com.liferay.info.item.ERCInfoItemIdentifier",
		"info.item.identifier=com.liferay.info.item.GroupUrlTitleInfoItemIdentifier",
		"service.ranking:Integer=100"
	},
	service = InfoItemObjectProvider.class
)
public class BlogsEntryInfoItemObjectProvider
	implements InfoItemObjectProvider<BlogsEntry> {

	@Override
	public BlogsEntry getInfoItem(InfoItemIdentifier infoItemIdentifier)
		throws NoSuchInfoItemException {

		return getInfoItem(_getGroupId(), infoItemIdentifier);
	}

	@Override
	public BlogsEntry getInfoItem(
			long groupId, InfoItemIdentifier infoItemIdentifier)
		throws NoSuchInfoItemException {

		return _getInfoItem(_getCompanyId(), groupId, infoItemIdentifier);
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
			long companyId, ERCInfoItemIdentifier ercInfoItemIdentifier,
			long groupId)
		throws NoSuchInfoItemException {

		try {
			if (Validator.isNull(
					ercInfoItemIdentifier.getScopeExternalReferenceCode())) {

				return groupId;
			}

			Group group = _groupLocalService.getGroupByExternalReferenceCode(
				ercInfoItemIdentifier.getScopeExternalReferenceCode(),
				companyId);

			return group.getGroupId();
		}
		catch (PortalException portalException) {
			throw new NoSuchInfoItemException(portalException);
		}
	}

	private BlogsEntry _getInfoItem(
			long companyId, long groupId, InfoItemIdentifier infoItemIdentifier)
		throws NoSuchInfoItemException {

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier) &&
			!(infoItemIdentifier instanceof ERCInfoItemIdentifier) &&
			!(infoItemIdentifier instanceof GroupUrlTitleInfoItemIdentifier)) {

			throw new NoSuchInfoItemException(
				"Unsupported info item identifier " + infoItemIdentifier);
		}

		BlogsEntry blogsEntry = null;

		if (infoItemIdentifier instanceof ClassPKInfoItemIdentifier) {
			ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
				(ClassPKInfoItemIdentifier)infoItemIdentifier;

			blogsEntry = _blogsEntryLocalService.fetchBlogsEntry(
				classPKInfoItemIdentifier.getClassPK());
		}
		else if (infoItemIdentifier instanceof ERCInfoItemIdentifier) {
			ERCInfoItemIdentifier ercInfoItemIdentifier =
				(ERCInfoItemIdentifier)infoItemIdentifier;

			blogsEntry =
				_blogsEntryLocalService.fetchBlogsEntryByExternalReferenceCode(
					ercInfoItemIdentifier.getExternalReferenceCode(),
					_getGroupId(companyId, ercInfoItemIdentifier, groupId));
		}
		else if (infoItemIdentifier instanceof
					GroupUrlTitleInfoItemIdentifier) {

			GroupUrlTitleInfoItemIdentifier groupURLTitleInfoItemIdentifier =
				(GroupUrlTitleInfoItemIdentifier)infoItemIdentifier;

			blogsEntry = _blogsEntryLocalService.fetchEntry(
				groupURLTitleInfoItemIdentifier.getGroupId(),
				groupURLTitleInfoItemIdentifier.getUrlTitle());
		}

		if ((blogsEntry == null) || blogsEntry.isDraft() ||
			blogsEntry.isInTrash()) {

			throw new NoSuchInfoItemException(
				"Unable to get blogs entry with info item identifier " +
					infoItemIdentifier);
		}

		return blogsEntry;
	}

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}