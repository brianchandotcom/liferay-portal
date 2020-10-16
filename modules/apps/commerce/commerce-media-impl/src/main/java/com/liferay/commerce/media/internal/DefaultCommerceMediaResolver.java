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
import com.liferay.commerce.media.internal.configuration.CommerceMediaDefaultImageConfiguration;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;

import java.io.IOException;

import java.util.Locale;
import java.util.Map;

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

	@Override
	public String getDownloadUrl(long cpAttachmentFileEntryId)
		throws PortalException {

		return getUrl(cpAttachmentFileEntryId, true, false);
	}

	@Override
	public byte[] getMediaBytes(HttpServletRequest httpServletRequest)
		throws IOException, PortalException {

		return getBytes(getFileEntry(httpServletRequest));
	}

	@Override
	public String getThumbnailUrl(long cpAttachmentFileEntryId)
		throws PortalException {

		return getUrl(cpAttachmentFileEntryId, false, true);
	}

	@Override
	public String getUrl(long cpAttachmentFileEntryId) throws PortalException {
		return getUrl(cpAttachmentFileEntryId, false, false);
	}

	@Override
	public String getUrl(
			long cpAttachmentFileEntryId, boolean download, boolean thumbnail)
		throws PortalException {

		return getUrl(cpAttachmentFileEntryId, download, thumbnail, true);
	}

	@Override
	public String getUrl(
			long cpAttachmentFileEntryId, boolean download, boolean thumbnail,
			boolean secure)
		throws PortalException {

		StringBundler sb = new StringBundler(10);

		sb.append(_portal.getPathModule());
		sb.append(StringPool.SLASH);
		sb.append(CommerceMediaConstants.SERVLET_PATH);

		CPAttachmentFileEntry cpAttachmentFileEntry = null;

		if (secure) {
			cpAttachmentFileEntry =
				_cpAttachmentFileEntryLocalService.fetchCPAttachmentFileEntry(
					cpAttachmentFileEntryId);
		}
		else {
			cpAttachmentFileEntry =
				_cpAttachmentFileEntryLocalService.fetchCPAttachmentFileEntry(
					cpAttachmentFileEntryId);
		}

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

		Locale siteDefaultLocale = _portal.getSiteDefaultLocale(
			cpAttachmentFileEntry.getGroupId());

		sb.append(
			setUrl(
				cpAttachmentFileEntry.getClassName(),
				cpAttachmentFileEntry.getClassPK(), siteDefaultLocale));

		sb.append(StringPool.SLASH);
		sb.append(cpAttachmentFileEntry.getFileEntryId());
		sb.append(StringPool.SLASH);
		sb.append(
			URLCodec.encodeURL(
				StringUtil.replace(
					cpAttachmentFileEntry.getTitle(siteDefaultLocale),
					CharPool.SLASH, StringPool.BLANK),
				true));
		sb.append("?download=");
		sb.append(download);

		return sb.toString();
	}

	@Override
	public void sendMediaBytes(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		sendMediaBytes(httpServletRequest, httpServletResponse, null);
	}

	@Override
	public void sendMediaBytes(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String contentDisposition)
		throws IOException {

		String path = _http.fixPath(httpServletRequest.getPathInfo());

		String[] pathArray = StringUtil.split(path, CharPool.SLASH);

		if (pathArray.length < 2) {
			long groupId = ParamUtil.getLong(httpServletRequest, "groupId");

			if (groupId == 0) {
				httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);

				return;
			}

			sendDefaultMediaBytes(
				groupId, httpServletRequest, httpServletResponse,
				contentDisposition);

			return;
		}

		long groupId = getGroupId(
			pathArray[0], GetterUtil.getLong(pathArray[1]));

		if (groupId == 0) {
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		try {
			FileEntry fileEntry = getFileEntry(httpServletRequest);

			if (fileEntry == null) {
				sendDefaultMediaBytes(
					groupId, httpServletRequest, httpServletResponse,
					contentDisposition);

				return;
			}

			ServletResponseUtil.sendFile(
				httpServletRequest, httpServletResponse,
				fileEntry.getFileName(), getBytes(fileEntry),
				fileEntry.getMimeType(), contentDisposition);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	protected byte[] getBytes(FileEntry fileEntry)
		throws IOException, PortalException {

		return _file.getBytes(fileEntry.getContentStream());
	}

	protected FileEntry getFileEntry(HttpServletRequest httpServletRequest)
		throws PortalException {

		String path = _http.fixPath(httpServletRequest.getPathInfo());

		String[] pathArray = StringUtil.split(path, CharPool.SLASH);

		if (pathArray.length < 2) {
			return null;
		}

		long fileEntryId = GetterUtil.getLong(pathArray[pathArray.length - 2]);

		return getFileEntry(fileEntryId);
	}

	protected FileEntry getFileEntry(long fileEntryId) {
		try {
			return _dlAppLocalService.getFileEntry(fileEntryId);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}

			return null;
		}
	}

	protected long getGroupId(String mediaType, long primaryKey) {
		if (mediaType.equals("asset-categories")) {
			AssetCategory assetCategory =
				_assetCategoryLocalService.fetchCategory(primaryKey);

			try {
				if (AssetCategoryPermission.contains(
						PermissionThreadLocal.getPermissionChecker(),
						assetCategory, ActionKeys.VIEW)) {

					Company company = _companyLocalService.getCompany(
						assetCategory.getCompanyId());

					return company.getGroupId();
				}
			}
			catch (PortalException portalException) {
				_log.error(portalException, portalException);
			}
		}
		else if (mediaType.equals("products")) {
			CProduct cProduct = _cProductLocalService.fetchCProduct(primaryKey);

			CommerceCatalog commerceCatalog =
				_commerceCatalogLocalService.fetchCommerceCatalogByGroupId(
					cProduct.getGroupId());

			return commerceCatalog.getGroupId();
		}

		return 0;
	}

	protected void sendDefaultMediaBytes(
			long groupId, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String contentDisposition)
		throws IOException {

		try {
			CommerceMediaDefaultImageConfiguration
				commerceMediaDefaultImageConfiguration =
					ConfigurationProviderUtil.getConfiguration(
						CommerceMediaDefaultImageConfiguration.class,
						new GroupServiceSettingsLocator(
							groupId, CommerceMediaConstants.SERVICE_NAME));

			FileEntry fileEntry = getFileEntry(
				commerceMediaDefaultImageConfiguration.defaultFileEntryId());

			if (fileEntry == null) {
				Company company = _portal.getCompany(httpServletRequest);

				Image image = ImageToolUtil.getDefaultCompanyLogo();

				ServletResponseUtil.sendFile(
					httpServletRequest, httpServletResponse, company.getName(),
					image.getTextObj(), image.getType(), contentDisposition);

				return;
			}

			ServletResponseUtil.sendFile(
				httpServletRequest, httpServletResponse,
				fileEntry.getFileName(), getBytes(fileEntry),
				fileEntry.getMimeType(), contentDisposition);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	protected String setUrl(String className, long classPK, Locale locale)
		throws PortalException {

		StringBundler sb = new StringBundler(4);

		if (className.equals(CPDefinition.class.getName())) {
			sb.append("/products/");

			CPDefinition cpDefinition =
				_cpDefinitionLocalService.getCPDefinition(classPK);

			sb.append(cpDefinition.getCProductId());

			sb.append(StringPool.SLASH);

			Map<Locale, String> titleMap = cpDefinition.getUrlTitleMap();

			sb.append(titleMap.get(locale));
		}
		else if (className.equals(AssetCategory.class.getName())) {
			sb.append("/asset-categories/");

			AssetCategory assetCategory =
				_assetCategoryLocalService.getAssetCategory(classPK);

			sb.append(assetCategory.getCategoryId());

			sb.append(StringPool.SLASH);

			Map<Locale, String> titleMap = assetCategory.getTitleMap();

			sb.append(titleMap.get(locale));
		}
		else {
			throw new UnsupportedOperationException();
		}

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultCommerceMediaResolver.class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CommerceCatalog)"
	)
	private ModelResourcePermission<CommerceCatalog>
		_commerceCatalogModelResourcePermission;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CPAttachmentFileEntryLocalService
		_cpAttachmentFileEntryLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CProductLocalService _cProductLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private File _file;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Http _http;

	@Reference
	private Portal _portal;

}