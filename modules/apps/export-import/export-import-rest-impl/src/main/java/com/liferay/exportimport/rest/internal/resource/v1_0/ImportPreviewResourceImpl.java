/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.internal.resource.v1_0;

import com.liferay.exportimport.rest.dto.v1_0.ImportPreview;
import com.liferay.exportimport.rest.internal.util.GroupUtil;
import com.liferay.exportimport.rest.internal.util.ImportPreviewUtil;
import com.liferay.exportimport.rest.internal.util.ImportTempFileEntryUtil;
import com.liferay.exportimport.rest.internal.util.LayoutUtil;
import com.liferay.exportimport.rest.internal.util.PermissionUtil;
import com.liferay.exportimport.rest.resource.v1_0.ImportPreviewResource;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;

import jakarta.ws.rs.BadRequestException;

import java.io.File;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Daniel Raposo
 * @author Jaime León
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/import-preview.properties",
	scope = ServiceScope.PROTOTYPE, service = ImportPreviewResource.class
)
public class ImportPreviewResourceImpl extends BaseImportPreviewResourceImpl {

	@Override
	public ImportPreview postAssetLibraryImportPreview(
			String assetLibraryExternalReferenceCode, String portletId,
			MultipartBody multipartBody)
		throws Exception {

		return _getImportPreview(
			GroupUtil.getAssetLibraryGroup(
				contextCompany.getCompanyId(),
				assetLibraryExternalReferenceCode),
			multipartBody, portletId, null);
	}

	@Override
	public ImportPreview postImportPreview(
			String portletId, MultipartBody multipartBody)
		throws Exception {

		return _getImportPreview(
			GroupUtil.getCompanyGroup(contextCompany.getCompanyId()),
			multipartBody, portletId, null);
	}

	@Override
	public ImportPreview postSiteImportPreview(
			String siteExternalReferenceCode, String portletId,
			String sitePageExternalReferenceCode, MultipartBody multipartBody)
		throws Exception {

		return _getImportPreview(
			GroupUtil.getSiteGroup(
				contextCompany.getCompanyId(), siteExternalReferenceCode,
				portletId),
			multipartBody, portletId, sitePageExternalReferenceCode);
	}

	private ImportPreview _getImportPreview(
			BinaryFile binaryFile, File file, Layout layout, String portletId,
			Group scopeGroup)
		throws Exception {

		long groupId = scopeGroup.getGroupId();

		ImportTempFileEntryUtil.deleteUnreservedTempFileEntries(
			groupId, contextUser.getUserId());

		ImportPreviewUtil.validateImportFile(
			file, groupId, contextAcceptLanguage.getPreferredLocale(),
			(layout == null) ? 0 : layout.getPlid(), portletId,
			contextUser.getTimeZone(), contextUser.getUserId());

		FileEntry fileEntry = ImportTempFileEntryUtil.addTempFileEntry(
			file, binaryFile.getFileName(), groupId,
			binaryFile.getContentType());

		return ImportPreviewUtil.getImportPreview(
			fileEntry.getUserName(), file,
			ImportTempFileEntryUtil.getFileName(fileEntry), fileEntry.getSize(),
			contextAcceptLanguage.getPreferredLocale(), portletId, scopeGroup,
			contextUser.getUserId());
	}

	private ImportPreview _getImportPreview(
			Group group, MultipartBody multipartBody, String portletId,
			String sitePageExternalReferenceCode)
		throws Exception {

		Layout layout = LayoutUtil.getPortletLayout(
			group, portletId, sitePageExternalReferenceCode);

		Group scopeGroup = GroupUtil.getScopeGroup(group, layout, portletId);

		PermissionUtil.checkImportPermission(
			contextCompany.getCompanyId(), scopeGroup.getGroupId(),
			PermissionUtil.getGroupActionId(portletId));

		UploadServletRequest uploadServletRequest =
			_portal.getUploadServletRequest(contextHttpServletRequest);

		File file = uploadServletRequest.getFile("file", true);

		if (file == null) {
			throw new BadRequestException("The request has no \"file\" part");
		}

		return _getImportPreview(
			multipartBody.getBinaryFile("file"), file, layout, portletId,
			scopeGroup);
	}

	@Reference
	private Portal _portal;

}