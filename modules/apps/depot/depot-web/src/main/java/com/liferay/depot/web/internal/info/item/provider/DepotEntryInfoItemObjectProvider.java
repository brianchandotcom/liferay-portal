/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.info.item.provider;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.ERCInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "item.class.name=com.liferay.depot.model.DepotEntry",
	service = InfoItemObjectProvider.class
)
public class DepotEntryInfoItemObjectProvider
	implements InfoItemObjectProvider<DepotEntry> {

	@Override
	public DepotEntry getInfoItem(InfoItemIdentifier infoItemIdentifier)
		throws NoSuchInfoItemException {

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier) &&
			!(infoItemIdentifier instanceof ERCInfoItemIdentifier)) {

			throw new NoSuchInfoItemException(
				"Unsupported info item identifier " + infoItemIdentifier);
		}

		if (infoItemIdentifier instanceof ClassPKInfoItemIdentifier) {
			ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
				(ClassPKInfoItemIdentifier)infoItemIdentifier;

			DepotEntry depotEntry = _fetchDepotEntry(
				classPKInfoItemIdentifier.getClassPK());

			if (depotEntry == null) {
				throw new NoSuchInfoItemException(
					"Unable to get depot entry folder " +
						classPKInfoItemIdentifier.getClassPK());
			}

			return depotEntry;
		}

		ERCInfoItemIdentifier ercInfoItemIdentifier =
			(ERCInfoItemIdentifier)infoItemIdentifier;

		Long companyId = _getCompanyId();

		if (companyId == null) {
			throw new NoSuchInfoItemException(
				"Unable to get depot entry " +
					ercInfoItemIdentifier.getExternalReferenceCode());
		}

		Group group = _groupLocalService.fetchGroupByExternalReferenceCode(
			ercInfoItemIdentifier.getExternalReferenceCode(), companyId);

		if (group == null) {
			throw new NoSuchInfoItemException(
				"Unable to get depot entry " +
					ercInfoItemIdentifier.getExternalReferenceCode());
		}

		DepotEntry depotEntry = _depotEntryLocalService.fetchGroupDepotEntry(
			group.getGroupId());

		if (depotEntry == null) {
			throw new NoSuchInfoItemException(
				"Unable to get depot entry " +
					ercInfoItemIdentifier.getExternalReferenceCode());
		}

		return depotEntry;
	}

	private DepotEntry _fetchDepotEntry(long classPK) {
		DepotEntry depotEntry = _depotEntryLocalService.fetchDepotEntry(
			classPK);

		if (depotEntry != null) {
			return depotEntry;
		}

		return _depotEntryLocalService.fetchGroupDepotEntry(classPK);
	}

	private Long _getCompanyId() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			return serviceContext.getCompanyId();
		}

		return CompanyThreadLocal.getCompanyId();
	}

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}