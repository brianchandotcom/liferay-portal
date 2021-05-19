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

package com.liferay.commerce.media.internal;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.commerce.media.CommerceMediaResolver;
import com.liferay.commerce.media.constants.CommerceMediaConstants;
import com.liferay.commerce.product.constants.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.permission.CommerceProductViewPermission;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 * @author Alessio Antonio Rendina
 */
@Component(enabled = false, service = CommerceMediaResolver.class)
public class DefaultCommerceMediaResolver implements CommerceMediaResolver {

	@Override
	public String getDefaultUrl(long groupId) {
		StringBundler sb = new StringBundler(5);

		sb.append(_portal.getPathModule());
		sb.append(StringPool.SLASH);
		sb.append(CommerceMediaConstants.SERVLET_PATH);
		sb.append("/default/?groupId=");
		sb.append(groupId);

		return sb.toString();
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #getDownloadUrl(long, long)}}
	 */
	@Deprecated
	@Override
	public String getDownloadUrl(long cpAttachmentFileEntryId)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public String getDownloadUrl(
			long commerceAccountId, long cpAttachmentFileEntryId)
		throws PortalException {

		return getUrl(commerceAccountId, cpAttachmentFileEntryId, true, false);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public byte[] getMediaBytes(HttpServletRequest httpServletRequest)
		throws IOException, PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #getThumbnailUrl(long, long)}}
	 */
	@Deprecated
	@Override
	public String getThumbnailUrl(long cpAttachmentFileEntryId)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public String getThumbnailUrl(
			long commerceAccountId, long cpAttachmentFileEntryId)
		throws PortalException {

		return getUrl(commerceAccountId, cpAttachmentFileEntryId, false, true);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #getUrl(long, long)}}
	 */
	@Deprecated
	@Override
	public String getUrl(long cpAttachmentFileEntryId) throws PortalException {
		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #getUrl(long, long, boolean, boolean)}}
	 */
	@Deprecated
	@Override
	public String getUrl(
			long cpAttachmentFileEntryId, boolean download, boolean thumbnail)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #getUrl(long, long, boolean, boolean, boolean)}}
	 */
	@Deprecated
	@Override
	public String getUrl(
			long cpAttachmentFileEntryId, boolean download, boolean thumbnail,
			boolean secure)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public String getUrl(long commerceAccountId, long cpAttachmentFileEntryId)
		throws PortalException {

		return getUrl(commerceAccountId, cpAttachmentFileEntryId, false, false);
	}

	@Override
	public String getUrl(
			long commerceAccountId, long cpAttachmentFileEntryId,
			boolean download, boolean thumbnail)
		throws PortalException {

		return getUrl(
			commerceAccountId, cpAttachmentFileEntryId, download, thumbnail,
			true);
	}

	@Override
	public String getUrl(
			long commerceAccountId, long cpAttachmentFileEntryId,
			boolean download, boolean thumbnail, boolean secure)
		throws PortalException {

		StringBundler sb = new StringBundler(9);

		sb.append(_portal.getPathModule());
		sb.append(StringPool.SLASH);
		sb.append(CommerceMediaConstants.SERVLET_PATH);

		CPAttachmentFileEntry cpAttachmentFileEntry =
			_cpAttachmentFileEntryLocalService.fetchCPAttachmentFileEntry(
				cpAttachmentFileEntryId);

		if (cpAttachmentFileEntry == null) {
			HttpSession httpSession = PortalSessionThreadLocal.getHttpSession();

			if (httpSession == null) {
				return sb.toString();
			}

			long companyId = GetterUtil.getLong(
				httpSession.getAttribute(WebKeys.COMPANY_ID));

			Company company = _companyLocalService.getCompany(companyId);

			return getDefaultUrl(company.getGroupId());
		}

		if (secure) {
			String className = cpAttachmentFileEntry.getClassName();

			if (className.equals(AssetCategory.class.getName())) {
				AssetCategory assetCategory =
					_assetCategoryLocalService.fetchCategory(
						cpAttachmentFileEntry.getClassPK());

				AssetCategoryPermission.check(
					PermissionThreadLocal.getPermissionChecker(), assetCategory,
					ActionKeys.VIEW);
			}
			else if (className.equals(CPDefinition.class.getName())) {
				_commerceProductViewPermission.check(
					PermissionThreadLocal.getPermissionChecker(),
					commerceAccountId, cpAttachmentFileEntry.getClassPK());
			}
		}

		if (cpAttachmentFileEntry.isCdn()) {
			return cpAttachmentFileEntry.getCdnUrl();
		}

		sb.append("/accounts/");
		sb.append(commerceAccountId);

		if (cpAttachmentFileEntry.getType() ==
				CPAttachmentFileEntryConstants.TYPE_IMAGE) {

			sb.append("/images/");
		}
		else if (cpAttachmentFileEntry.getType() ==
					CPAttachmentFileEntryConstants.TYPE_OTHER) {

			sb.append("/attachments/");
		}

		sb.append(cpAttachmentFileEntry.getCPAttachmentFileEntryId());
		sb.append("?download=");
		sb.append(download);

		return sb.toString();
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public void sendMediaBytes(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public void sendMediaBytes(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String download)
		throws IOException {

		throw new UnsupportedOperationException();
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private CommerceProductViewPermission _commerceProductViewPermission;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CPAttachmentFileEntryLocalService
		_cpAttachmentFileEntryLocalService;

	@Reference
	private Portal _portal;

}