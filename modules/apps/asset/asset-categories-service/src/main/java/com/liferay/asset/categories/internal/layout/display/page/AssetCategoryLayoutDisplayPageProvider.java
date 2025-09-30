/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.internal.layout.display.page;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.ERCInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.BaseLayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutDisplayPageProvider.class)
public class AssetCategoryLayoutDisplayPageProvider
	extends BaseLayoutDisplayPageProvider<AssetCategory> {

	@Override
	public String getClassName() {
		return AssetCategory.class.getName();
	}

	@Override
	public String getDefaultURLSeparator() {
		return FriendlyURLResolverConstants.URL_SEPARATOR_ASSET_CATEGORY;
	}

	@Override
	public LayoutDisplayPageObjectProvider<AssetCategory>
		getLayoutDisplayPageObjectProvider(AssetCategory assetCategory) {

		return new AssetCategoryLayoutDisplayPageObjectProvider(
			assetCategory, _portal);
	}

	@Override
	public LayoutDisplayPageObjectProvider<AssetCategory>
		getLayoutDisplayPageObjectProvider(
			InfoItemReference infoItemReference) {

		return getLayoutDisplayPageObjectProvider(
			_getGroupId(), infoItemReference);
	}

	@Override
	public LayoutDisplayPageObjectProvider<AssetCategory>
		getLayoutDisplayPageObjectProvider(
			long groupId, InfoItemReference infoItemReference) {

		return _getLayoutDisplayPageObjectProvider(
			_getGroupId(groupId, infoItemReference.getInfoItemIdentifier()),
			infoItemReference);
	}

	@Override
	public LayoutDisplayPageObjectProvider<AssetCategory>
		getLayoutDisplayPageObjectProvider(long groupId, String urlTitle) {

		AssetCategory assetCategory =
			_assetCategoryLocalService.fetchAssetCategory(
				GetterUtil.getLong(urlTitle));

		if (assetCategory == null) {
			return null;
		}

		return new AssetCategoryLayoutDisplayPageObjectProvider(
			assetCategory, _portal);
	}

	@Override
	public LayoutDisplayPageObjectProvider<AssetCategory>
		getParentLayoutDisplayPageObjectProvider(
			InfoItemReference infoItemReference) {

		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier)) {
			return null;
		}

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)infoItemIdentifier;

		AssetCategory assetCategory =
			_assetCategoryLocalService.fetchAssetCategory(
				classPKInfoItemIdentifier.getClassPK());

		if (assetCategory == null) {
			return null;
		}

		AssetCategory parentCategory = assetCategory.getParentCategory();

		if (parentCategory == null) {
			return null;
		}

		return new AssetCategoryLayoutDisplayPageObjectProvider(
			parentCategory, _portal);
	}

	@Override
	public boolean inheritable() {
		return true;
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

	private AssetCategoryLayoutDisplayPageObjectProvider
		_getLayoutDisplayPageObjectProvider(
			long groupId, InfoItemReference infoItemReference) {

		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier) &&
			!(infoItemIdentifier instanceof ERCInfoItemIdentifier)) {

			return null;
		}

		AssetCategory assetCategory = null;

		if (infoItemIdentifier instanceof ClassPKInfoItemIdentifier) {
			ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
				(ClassPKInfoItemIdentifier)infoItemIdentifier;

			assetCategory = _assetCategoryLocalService.fetchAssetCategory(
				classPKInfoItemIdentifier.getClassPK());
		}
		else {
			ERCInfoItemIdentifier ercInfoItemIdentifier =
				(ERCInfoItemIdentifier)infoItemIdentifier;

			assetCategory =
				_assetCategoryLocalService.
					fetchAssetCategoryByExternalReferenceCode(
						ercInfoItemIdentifier.getExternalReferenceCode(),
						groupId);
		}

		if (assetCategory == null) {
			return null;
		}

		return new AssetCategoryLayoutDisplayPageObjectProvider(
			assetCategory, _portal);
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private Portal _portal;

}