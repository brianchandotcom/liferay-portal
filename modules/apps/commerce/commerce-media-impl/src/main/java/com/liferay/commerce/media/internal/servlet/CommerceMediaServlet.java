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

import com.liferay.account.constants.AccountConstants;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.commerce.media.CommerceMediaProvider;
import com.liferay.commerce.media.constants.CommerceMediaConstants;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.permission.CommerceProductViewPermission;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem;
import com.liferay.commerce.product.type.virtual.order.service.CommerceVirtualOrderItemLocalService;
import com.liferay.commerce.product.type.virtual.order.service.CommerceVirtualOrderItemService;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingLocalService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;

import java.io.IOException;
import java.io.PrintWriter;

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
				user = _userLocalService.getGuestUser(
					_portal.getCompanyId(httpServletRequest));
			}

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			PrincipalThreadLocal.setName(user.getUserId());
		}
		catch (Exception exception) {
			_log.error(exception);

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

		String path = HttpComponentsUtil.fixPath(
			httpServletRequest.getPathInfo());

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
				GetterUtil.getLongStrict(cpAttachmentFileEntryIdParam));

		return _getFileEntry(cpAttachmentFileEntry.getFileEntryId());
	}

	private FileEntry _getFileEntry(long fileEntryId) {
		try {
			return _dlAppLocalService.getFileEntry(fileEntryId);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
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
				_log.error(portalException);
			}
		}
		else if (className.equals(CPDefinition.class.getName())) {
			CPDefinition cpDefinition =
				_cpDefinitionLocalService.getCPDefinition(
					cpAttachmentFileEntry.getClassPK());

			if (commerceAccountId == AccountConstants.ACCOUNT_ENTRY_ID_ADMIN) {
				_commerceCatalogModelResourcePermission.check(
					PermissionThreadLocal.getPermissionChecker(),
					cpDefinition.getCommerceCatalog(), ActionKeys.VIEW);
			}
			else {
				_commerceProductViewPermission.check(
					PermissionThreadLocal.getPermissionChecker(),
					commerceAccountId, cpDefinition.getCPDefinitionId());
			}

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
			_log.error(exception);

			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private void _sendError(
		HttpServletResponse httpServletResponse, int status, String message) {

		try {
			PrintWriter printWriter = httpServletResponse.getWriter();

			JSONObject jsonObject = JSONUtil.put(
				CommerceMediaConstants.RESPONSE_ERROR,
				JSONUtil.put(
					CommerceMediaConstants.RESPONSE_CODE, status
				).put(
					CommerceMediaConstants.RESPONSE_MESSAGE, message
				));

			printWriter.write(jsonObject.toString());

			httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);
			httpServletResponse.setStatus(status);
		}
		catch (IOException ioException) {
			_log.error(ioException);

			httpServletResponse.setStatus(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	private void _sendMediaBytes(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String contentDisposition)
		throws IOException {

		String path = HttpComponentsUtil.fixPath(
			httpServletRequest.getPathInfo());

		String[] pathArray = StringUtil.split(path, CharPool.SLASH);

		String commerceVirtualOrderItemPath = pathArray[0];

		if (CommerceMediaConstants.URL_SEPARATOR_VIRTUAL_ORDER_ITEM.contains(
				commerceVirtualOrderItemPath)) {

			long commerceVirtualOrderItemId = GetterUtil.getLongStrict(
				pathArray[1]);
			long fileEntryId = GetterUtil.getLongStrict(pathArray[3]);

			try {
				CommerceVirtualOrderItem commerceVirtualOrderItem =
					_commerceVirtualOrderItemService.
						fetchCommerceVirtualOrderItem(
							commerceVirtualOrderItemId);

				if (commerceVirtualOrderItem == null) {
					_sendError(
						httpServletResponse, HttpServletResponse.SC_NOT_FOUND,
						"The commerce virtual order item " +
							commerceVirtualOrderItemId + " does not exist");

					return;
				}

				if (commerceVirtualOrderItem.getFileEntryId() != fileEntryId) {
					_sendError(
						httpServletResponse, HttpServletResponse.SC_NOT_FOUND,
						StringBundler.concat(
							"The commerce virtual item ",
							commerceVirtualOrderItemId,
							" does not have file entry ", fileEntryId));

					return;
				}

				FileEntry fileEntry = _getFileEntry(fileEntryId);

				if (fileEntry == null) {
					_sendError(
						httpServletResponse, HttpServletResponse.SC_NOT_FOUND,
						"The file entry " + fileEntryId + " does not exist");

					return;
				}

				ServletResponseUtil.sendFile(
					httpServletRequest, httpServletResponse,
					fileEntry.getFileName(),
					_file.getBytes(fileEntry.getContentStream()),
					fileEntry.getMimeType(),
					HttpHeaders.CONTENT_DISPOSITION_ATTACHMENT);

				int usages = commerceVirtualOrderItem.getUsages() + 1;

				_commerceVirtualOrderItemLocalService.
					updateCommerceVirtualOrderItem(
						commerceVirtualOrderItem.
							getCommerceVirtualOrderItemId(),
						commerceVirtualOrderItem.getFileEntryId(),
						commerceVirtualOrderItem.getUrl(),
						commerceVirtualOrderItem.getActivationStatus(),
						commerceVirtualOrderItem.getDuration(), usages,
						commerceVirtualOrderItem.getMaxUsages(),
						commerceVirtualOrderItem.isActive());

				return;
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException);
				}

				if (portalException instanceof PrincipalException) {
					_sendError(
						httpServletResponse,
						HttpServletResponse.SC_UNAUTHORIZED,
						"You do not have permission to access the requested " +
							"resource");

					return;
				}

				_sendError(
					httpServletResponse,
					HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"An unexpected error occurred");

				return;
			}
		}

		if (pathArray.length >= 6) {
			if (CommerceMediaConstants.URL_SEPARATOR_VIRTUAL_PRODUCT.contains(
					pathArray[2])) {

				_sendVirtualProductMediaBytes(
					httpServletRequest, httpServletResponse, false, pathArray);

				return;
			}
			else if (CommerceMediaConstants.
						URL_SEPARATOR_VIRTUAL_PRODUCT_SAMPLE.contains(
							pathArray[2])) {

				_sendVirtualProductMediaBytes(
					httpServletRequest, httpServletResponse, true, pathArray);

				return;
			}
		}

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
				GetterUtil.getLongStrict(pathArray[1]),
				GetterUtil.getLongStrict(cpAttachmentFileEntryIdParam));

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
			_log.error(portalException);

			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private void _sendVirtualProductMediaBytes(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, boolean sample,
			String[] pathArray)
		throws IOException {

		long commerceAccountId = GetterUtil.getLongStrict(pathArray[1]);
		long cpDefinitionId = GetterUtil.getLongStrict(pathArray[3]);
		long fileEntryId = GetterUtil.getLongStrict(pathArray[5]);

		try {
			CPDefinition cpDefinition =
				_cpDefinitionLocalService.fetchCPDefinition(cpDefinitionId);

			if (cpDefinition == null) {
				_sendError(
					httpServletResponse, HttpServletResponse.SC_NOT_FOUND,
					"The commerce product definition " + cpDefinitionId +
						" does not exist");

				return;
			}

			if (commerceAccountId == AccountConstants.ACCOUNT_ENTRY_ID_ADMIN) {
				_commerceCatalogModelResourcePermission.check(
					PermissionThreadLocal.getPermissionChecker(),
					cpDefinition.getCommerceCatalog(), ActionKeys.VIEW);
			}
			else {
				if (sample) {
					_commerceProductViewPermission.check(
						PermissionThreadLocal.getPermissionChecker(),
						commerceAccountId, cpDefinition.getCPDefinitionId());
				}
				else {
					_sendError(
						httpServletResponse,
						HttpServletResponse.SC_UNAUTHORIZED,
						"You do not have permission to access the requested " +
							"resource");

					return;
				}
			}

			CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
				_cpDefinitionVirtualSettingLocalService.
					fetchCPDefinitionVirtualSetting(
						CPDefinition.class.getName(), cpDefinitionId);

			if (cpDefinitionVirtualSetting == null) {
				_sendError(
					httpServletResponse, HttpServletResponse.SC_NOT_FOUND,
					"The commerce product definition " + cpDefinitionId +
						" is not virtual");

				return;
			}

			FileEntry fileEntry = null;

			if (sample) {
				fileEntry = cpDefinitionVirtualSetting.getSampleFileEntry();
			}
			else {
				fileEntry = cpDefinitionVirtualSetting.getFileEntry();
			}

			if ((fileEntry == null) ||
				(fileEntry.getFileEntryId() != fileEntryId)) {

				_sendError(
					httpServletResponse, HttpServletResponse.SC_NOT_FOUND,
					"The file entry " + fileEntryId + " does not exist");

				return;
			}

			ServletResponseUtil.sendFile(
				httpServletRequest, httpServletResponse,
				fileEntry.getFileName(),
				_file.getBytes(fileEntry.getContentStream()),
				fileEntry.getMimeType(),
				HttpHeaders.CONTENT_DISPOSITION_ATTACHMENT);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			if (portalException instanceof PrincipalException) {
				_sendError(
					httpServletResponse, HttpServletResponse.SC_UNAUTHORIZED,
					"You do not have permission to access the requested " +
						"resource");

				return;
			}

			_sendError(
				httpServletResponse,
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
				"An unexpected error occurred");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceMediaServlet.class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CommerceCatalog)"
	)
	private ModelResourcePermission<CommerceCatalog>
		_commerceCatalogModelResourcePermission;

	@Reference
	private CommerceMediaProvider _commerceMediaProvider;

	@Reference
	private CommerceProductViewPermission _commerceProductViewPermission;

	@Reference
	private CommerceVirtualOrderItemLocalService
		_commerceVirtualOrderItemLocalService;

	@Reference
	private CommerceVirtualOrderItemService _commerceVirtualOrderItemService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CPAttachmentFileEntryLocalService
		_cpAttachmentFileEntryLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPDefinitionVirtualSettingLocalService
		_cpDefinitionVirtualSettingLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private File _file;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}