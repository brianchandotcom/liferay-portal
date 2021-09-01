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

package com.liferay.commerce.media.internal.servlet;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.commerce.media.CommerceMediaProvider;
import com.liferay.commerce.media.constants.CommerceMediaConstants;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.permission.CommerceProductViewPermission;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"osgi.http.whiteboard.context.path=/" + CommerceMediaConstants.SERVLET_PATH,
		"osgi.http.whiteboard.servlet.name=com.liferay.commerce.media.servlet.CommerceMediaServlet",
		"osgi.http.whiteboard.servlet.pattern=/" + CommerceMediaConstants.SERVLET_PATH + "/*"
	},
	service = Servlet.class
)
public class CommerceMediaServlet extends HttpServlet {

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		if (PortalSessionThreadLocal.getHttpSession() == null) {
			PortalSessionThreadLocal.setHttpSession(
				httpServletRequest.getSession());
		}

		try {
			User user = _portal.getUser(httpServletRequest);

			if (user == null) {
				user = _userLocalService.getDefaultUser(
					_portal.getCompanyId(httpServletRequest));
			}

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			PrincipalThreadLocal.setName(user.getUserId());
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		String contentDisposition = HttpHeaders.CONTENT_DISPOSITION_INLINE;

		boolean download = ParamUtil.getBoolean(httpServletRequest, "download");

		if (download) {
			contentDisposition = HttpHeaders.CONTENT_DISPOSITION_ATTACHMENT;
		}

		_sendMediaBytes(
			httpServletRequest, httpServletResponse, contentDisposition);
	}

	private FileEntry _getFileEntry(HttpServletRequest httpServletRequest)
		throws PortalException {

		String path = _http.fixPath(httpServletRequest.getPathInfo());

		String[] pathArray = StringUtil.split(path, CharPool.SLASH);

		if (pathArray.length < 2) {
			return null;
		}

		String cpAttachmentFileEntryIdParam = pathArray[3];

		if (cpAttachmentFileEntryIdParam.contains(StringPool.QUESTION)) {
			String[] cpAttachmentFileEntryIdParamArray = StringUtil.split(
				cpAttachmentFileEntryIdParam, StringPool.QUESTION);

			cpAttachmentFileEntryIdParam = cpAttachmentFileEntryIdParamArray[0];
		}

		CPAttachmentFileEntry cpAttachmentFileEntry =
			_cpAttachmentFileEntryLocalService.getCPAttachmentFileEntry(
				GetterUtil.getLong(cpAttachmentFileEntryIdParam));

		return _getFileEntry(cpAttachmentFileEntry.getFileEntryId());
	}

	private FileEntry _getFileEntry(long fileEntryId) {
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

	private long _getGroupId(
			long commerceAccountId, long cpAttachmentFileEntryId)
		throws PortalException {

		CPAttachmentFileEntry cpAttachmentFileEntry =
			_cpAttachmentFileEntryLocalService.getCPAttachmentFileEntry(
				cpAttachmentFileEntryId);

		String className = cpAttachmentFileEntry.getClassName();

		if (className.equals(AssetCategory.class.getName())) {
			AssetCategory assetCategory =
				_assetCategoryLocalService.fetchCategory(
					cpAttachmentFileEntry.getClassPK());

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
		else if (className.equals(CPDefinition.class.getName())) {
			CPDefinition cpDefinition =
				_cpDefinitionLocalService.getCPDefinition(
					cpAttachmentFileEntry.getClassPK());

			_commerceProductViewPermission.check(
				PermissionThreadLocal.getPermissionChecker(), commerceAccountId,
				cpDefinition.getCPDefinitionId());

			return cpDefinition.getGroupId();
		}

		return 0;
	}

	private void _sendDefaultMediaBytes(
			long groupId, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String contentDisposition)
		throws IOException {

		try {
			FileEntry fileEntry =
				_commerceMediaProvider.getDefaultImageFileEntry(
					_portal.getCompanyId(httpServletRequest), groupId);

			ServletResponseUtil.sendFile(
				httpServletRequest, httpServletResponse,
				fileEntry.getFileName(),
				_file.getBytes(fileEntry.getContentStream()),
				fileEntry.getMimeType(), contentDisposition);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private void _sendMediaBytes(
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

			_sendDefaultMediaBytes(
				groupId, httpServletRequest, httpServletResponse,
				contentDisposition);

			return;
		}

		try {
			String cpAttachmentFileEntryIdParam = pathArray[3];

			if (cpAttachmentFileEntryIdParam.contains(StringPool.QUESTION)) {
				String[] cpAttachmentFileEntryIdParamArray = StringUtil.split(
					cpAttachmentFileEntryIdParam, StringPool.QUESTION);

				cpAttachmentFileEntryIdParam =
					cpAttachmentFileEntryIdParamArray[0];
			}

			long groupId = _getGroupId(
				GetterUtil.getLong(pathArray[1]),
				GetterUtil.getLong(cpAttachmentFileEntryIdParam));

			if (groupId == 0) {
				httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);

				return;
			}

			FileEntry fileEntry = _getFileEntry(httpServletRequest);

			if (fileEntry == null) {
				_sendDefaultMediaBytes(
					groupId, httpServletRequest, httpServletResponse,
					contentDisposition);

				return;
			}

			ServletResponseUtil.sendFile(
				httpServletRequest, httpServletResponse,
				fileEntry.getFileName(),
				_file.getBytes(fileEntry.getContentStream()),
				fileEntry.getMimeType(), contentDisposition);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceMediaServlet.class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private CommerceMediaProvider _commerceMediaProvider;

	@Reference
	private CommerceProductViewPermission _commerceProductViewPermission;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CPAttachmentFileEntryLocalService
		_cpAttachmentFileEntryLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private File _file;

	@Reference
	private Http _http;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}