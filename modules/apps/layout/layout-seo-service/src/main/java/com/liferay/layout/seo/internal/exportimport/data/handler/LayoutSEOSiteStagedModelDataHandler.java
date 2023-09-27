/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.internal.exportimport.data.handler;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.exportimport.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.layout.seo.model.LayoutSEOSite;
import com.liferay.layout.seo.service.LayoutSEOSiteLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = StagedModelDataHandler.class)
public class LayoutSEOSiteStagedModelDataHandler
	extends BaseStagedModelDataHandler<LayoutSEOSite> {

	public static final String[] CLASS_NAMES = {LayoutSEOSite.class.getName()};

	@Override
	public void deleteStagedModel(LayoutSEOSite layoutSEOSite) {
		_layoutSEOSiteLocalService.deleteLayoutSEOSite(layoutSEOSite);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		_layoutSEOSiteLocalService.deleteLayoutSEOSite(uuid, groupId);
	}

	@Override
	public List<LayoutSEOSite> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _layoutSEOSiteLocalService.getLayoutSEOSitesByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, LayoutSEOSite layoutSEOSite)
		throws Exception {

		FileEntry openGraphImageFileEntry = _fetchFileEntry(
			layoutSEOSite.getOpenGraphImageFileEntryId());

		if (openGraphImageFileEntry != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, layoutSEOSite, openGraphImageFileEntry,
				PortletDataContext.REFERENCE_TYPE_WEAK);
		}

		portletDataContext.addClassedModel(
			portletDataContext.getExportDataElement(layoutSEOSite),
			ExportImportPathUtil.getModelPath(layoutSEOSite), layoutSEOSite);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, LayoutSEOSite layoutSEOSite)
		throws Exception {

		Map<Long, Long> fileEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				FileEntry.class);

		long openGraphImageFileEntryId = MapUtil.getLong(
			fileEntryIds, layoutSEOSite.getOpenGraphImageFileEntryId(), 0);

		LayoutSEOSite existingLayoutSEOSite = fetchStagedModelByUuidAndGroupId(
			layoutSEOSite.getUuid(), layoutSEOSite.getGroupId());

		if (existingLayoutSEOSite == null) {
			_layoutSEOSiteLocalService.updateLayoutSEOSite(
				portletDataContext.getUserId(layoutSEOSite.getUserUuid()),
				portletDataContext.getScopeGroupId(),
				layoutSEOSite.isOpenGraphEnabled(),
				layoutSEOSite.getOpenGraphImageAltMap(),
				openGraphImageFileEntryId,
				_createServiceContext(layoutSEOSite, null, portletDataContext));
		}
		else {
			_layoutSEOSiteLocalService.updateLayoutSEOSite(
				existingLayoutSEOSite.getUserId(),
				existingLayoutSEOSite.getGroupId(),
				layoutSEOSite.isOpenGraphEnabled(),
				layoutSEOSite.getOpenGraphImageAltMap(),
				openGraphImageFileEntryId,
				_createServiceContext(
					layoutSEOSite, existingLayoutSEOSite, portletDataContext));
		}
	}

	private ServiceContext _createServiceContext(
		LayoutSEOSite layoutSEOSite, LayoutSEOSite existingLayoutSEOSite,
		PortletDataContext portletDataContext) {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			layoutSEOSite);

		if (portletDataContext.isDataStrategyMirror() &&
			(existingLayoutSEOSite == null)) {

			serviceContext.setUuid(layoutSEOSite.getUuid());
		}

		return serviceContext;
	}

	private FileEntry _fetchFileEntry(long fileEntryId) {
		if (fileEntryId <= 0) {
			return null;
		}

		try {
			return _dlAppLocalService.getFileEntry(fileEntryId);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get file entry " + fileEntryId, portalException);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSEOSiteStagedModelDataHandler.class);

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private LayoutSEOSiteLocalService _layoutSEOSiteLocalService;

}