/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.admin.web.internal.info.item.provider;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.ERCInfoItemIdentifier;
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
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"info.item.identifier=com.liferay.info.item.ClassPKInfoItemIdentifier",
		"info.item.identifier=com.liferay.info.item.ERCInfoItemIdentifier",
		"service.ranking:Integer=100"
	},
	service = InfoItemObjectProvider.class
)
public class AssetCategoryInfoItemObjectProvider
	implements InfoItemObjectProvider<AssetCategory> {

	@Override
	public AssetCategory getInfoItem(InfoItemIdentifier infoItemIdentifier)
		throws NoSuchInfoItemException {

		return getInfoItem(_getGroupId(), infoItemIdentifier);
	}

	@Override
	public AssetCategory getInfoItem(
			long groupId, InfoItemIdentifier infoItemIdentifier)
		throws NoSuchInfoItemException {

		return _getInfoItem(
			_getGroupId(groupId, infoItemIdentifier), infoItemIdentifier);
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
			long groupId, InfoItemIdentifier infoItemIdentifier)
		throws NoSuchInfoItemException {

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

			Group group = _groupLocalService.getGroupByExternalReferenceCode(
				ercInfoItemIdentifier.getScopeExternalReferenceCode(),
				_getCompanyId());

			return group.getGroupId();
		}
		catch (PortalException portalException) {
			throw new NoSuchInfoItemException(portalException);
		}
	}

	private AssetCategory _getInfoItem(
			long groupId, InfoItemIdentifier infoItemIdentifier)
		throws NoSuchInfoItemException {

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier) &&
			!(infoItemIdentifier instanceof ERCInfoItemIdentifier)) {

			throw new NoSuchInfoItemException(
				"Unsupported info item identifier " + infoItemIdentifier);
		}

		if (infoItemIdentifier instanceof ClassPKInfoItemIdentifier) {
			ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
				(ClassPKInfoItemIdentifier)infoItemIdentifier;

			AssetCategory assetCategory =
				_assetCategoryLocalService.fetchAssetCategory(
					classPKInfoItemIdentifier.getClassPK());

			if (assetCategory == null) {
				throw new NoSuchInfoItemException(
					"Unable to get asset category with info item identifier " +
						infoItemIdentifier);
			}

			return assetCategory;
		}

		ERCInfoItemIdentifier ercInfoItemIdentifier =
			(ERCInfoItemIdentifier)infoItemIdentifier;

		AssetCategory assetCategory =
			_assetCategoryLocalService.
				fetchAssetCategoryByExternalReferenceCode(
					ercInfoItemIdentifier.getExternalReferenceCode(), groupId);

		if (assetCategory == null) {
			throw new NoSuchInfoItemException(
				"Unable to get asset category with info item identifier " +
					infoItemIdentifier);
		}

		return assetCategory;
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}