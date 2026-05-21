/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.internal.resource.v1_0;

import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.exportimport.rest.dto.v1_0.ImportPreview;
import com.liferay.exportimport.rest.dto.v1_0.PreviewPortletDataHandler;
import com.liferay.exportimport.rest.internal.util.PermissionUtil;
import com.liferay.exportimport.rest.internal.util.PortletDataHandlerSectionUtil;
import com.liferay.exportimport.rest.resource.v1_0.ImportPreviewResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.staging.StagingGroupHelper;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.io.Serializable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Daniel Raposo
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/import-preview.properties",
	scope = ServiceScope.PROTOTYPE, service = ImportPreviewResource.class
)
public class ImportPreviewResourceImpl extends BaseImportPreviewResourceImpl {

	@Override
	public ImportPreview postAssetLibraryImportPreview(
			String assetLibraryExternalReferenceCode,
			MultipartBody multipartBody)
		throws Exception {

		Group group = groupLocalService.getGroupByExternalReferenceCode(
			assetLibraryExternalReferenceCode, contextCompany.getCompanyId());

		if (!group.isDepot()) {
			throw new NotFoundException();
		}

		return _getImportPreview(group.getGroupId(), multipartBody);
	}

	@Override
	public ImportPreview postImportPreview(MultipartBody multipartBody)
		throws Exception {

		Group group = _stagingGroupHelper.fetchCompanyGroup(
			contextCompany.getCompanyId());

		return _getImportPreview(group.getGroupId(), multipartBody);
	}

	@Override
	public ImportPreview postSiteImportPreview(
			String siteExternalReferenceCode, MultipartBody multipartBody)
		throws Exception {

		Group group = groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		if (!group.isSite()) {
			throw new NotFoundException();
		}

		return _getImportPreview(group.getGroupId(), multipartBody);
	}

	private FileEntry _addTempFileEntry(
			long groupId, MultipartBody multipartBody)
		throws Exception {

		BinaryFile binaryFile = multipartBody.getBinaryFile("file");

		String folderName = ImportPreviewResource.class.getName();

		String[] tempFileNames = _layoutService.getTempFileNames(
			groupId, folderName);

		for (String tempFileEntryName : tempFileNames) {
			_layoutService.deleteTempFileEntry(
				groupId, folderName, tempFileEntryName);
		}

		return _layoutService.addTempFileEntry(
			groupId, folderName, binaryFile.getFileName(),
			binaryFile.getInputStream(), binaryFile.getContentType());
	}

	private ImportPreview _getImportPreview(
			long groupId, MultipartBody multipartBody)
		throws Exception {

		PermissionUtil.checkImportPermission(
			contextCompany.getCompanyId(), groupId);

		FileEntry fileEntry = _addTempFileEntry(groupId, multipartBody);

		_validateImportLayoutsFile(groupId, fileEntry);

		ManifestSummary manifestSummary =
			_exportImportHelper.getManifestSummary(
				contextUser.getUserId(), groupId, new HashMap<>(), fileEntry);

		Locale locale = contextAcceptLanguage.getPreferredLocale();

		Map<String, List<PreviewPortletDataHandler>>
			previewPortletDataHandlers = new LinkedHashMap<>();

		for (Portlet portlet : manifestSummary.getDataPortlets()) {
			PortletDataHandlerSectionUtil.addPortletDataHandlerSection(
				contextCompany.getCompanyId(), locale, manifestSummary, portlet,
				portlet.getPortletDataHandlerInstance(),
				PortletDataHandler::getImportPortletDataHandlerControls,
				previewPortletDataHandlers);
		}

		return new ImportPreview() {
			{
				setAdditionCount(
					() -> PortletDataHandlerSectionUtil.getAdditionCount(
						previewPortletDataHandlers));
				setAuthor(fileEntry::getUserName);
				setDeletionCount(
					() -> PortletDataHandlerSectionUtil.getDeletionCount(
						previewPortletDataHandlers));
				setExportDate(manifestSummary::getExportDate);
				setFileName(fileEntry::getFileName);
				setFileSize(fileEntry::getSize);
				setPreviewPortletDataHandlerSections(
					() ->
						PortletDataHandlerSectionUtil.
							toPortletDataHandlerSections(
								locale, previewPortletDataHandlers));
			}
		};
	}

	private void _validateImportLayoutsFile(long groupId, FileEntry fileEntry)
		throws Exception {

		Map<String, Serializable> parameterMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildImportLayoutSettingsMap(
					contextUser.getUserId(), groupId, false, null,
					new HashMap<>(), contextAcceptLanguage.getPreferredLocale(),
					contextUser.getTimeZone());

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.addExportImportConfiguration(
				contextUser.getUserId(), groupId, null, null,
				ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
				parameterMap, new ServiceContext());

		try {
			_exportImportLocalService.validateImportLayoutsFile(
				exportImportConfiguration, fileEntry.getContentStream());
		}
		catch (PortalException portalException) {
			_layoutService.deleteTempFileEntry(
				groupId, ImportPreviewResource.class.getName(),
				fileEntry.getFileName());

			JSONObject jsonObject = _staging.getExceptionMessagesJSONObject(
				contextAcceptLanguage.getPreferredLocale(), portalException,
				exportImportConfiguration);

			throw new BadRequestException(jsonObject.getString("message"));
		}
		finally {
			_exportImportConfigurationLocalService.
				deleteExportImportConfiguration(exportImportConfiguration);
		}
	}

	@Reference
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Reference
	private ExportImportHelper _exportImportHelper;

	@Reference
	private ExportImportLocalService _exportImportLocalService;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private Staging _staging;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

}