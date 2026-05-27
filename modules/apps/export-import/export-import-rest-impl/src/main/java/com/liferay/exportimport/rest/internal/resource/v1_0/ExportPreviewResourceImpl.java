/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.internal.resource.v1_0;

import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactory;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.portlet.data.handler.provider.PortletDataHandlerProvider;
import com.liferay.exportimport.rest.dto.v1_0.ExportPreview;
import com.liferay.exportimport.rest.dto.v1_0.PreviewPortletDataHandler;
import com.liferay.exportimport.rest.internal.util.DateRangeUtil;
import com.liferay.exportimport.rest.internal.util.PermissionUtil;
import com.liferay.exportimport.rest.internal.util.PreviewPortletDataHandlerUtil;
import com.liferay.exportimport.rest.resource.v1_0.ExportPreviewResource;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.staging.StagingGroupHelper;

import jakarta.ws.rs.NotFoundException;

import java.util.Date;
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
	properties = "OSGI-INF/liferay/rest/v1_0/export-preview.properties",
	scope = ServiceScope.PROTOTYPE, service = ExportPreviewResource.class
)
public class ExportPreviewResourceImpl extends BaseExportPreviewResourceImpl {

	@Override
	public ExportPreview getAssetLibraryExportPreview(
			String assetLibraryExternalReferenceCode, Date endDate,
			Integer last, String range, Date startDate)
		throws Exception {

		Group group = groupLocalService.fetchGroupByExternalReferenceCode(
			assetLibraryExternalReferenceCode, contextCompany.getCompanyId());

		if ((group == null) || !group.isDepot()) {
			throw new NotFoundException();
		}

		return _getExportPreview(
			endDate, group.getGroupId(), last, range, startDate);
	}

	@Override
	public ExportPreview getExportPreview(
			Date endDate, Integer last, String range, Date startDate)
		throws Exception {

		Group group = _stagingGroupHelper.fetchCompanyGroup(
			contextCompany.getCompanyId());

		if (group == null) {
			throw new NotFoundException();
		}

		return _getExportPreview(
			endDate, group.getGroupId(), last, range, startDate);
	}

	@Override
	public ExportPreview getSiteExportPreview(
			String siteExternalReferenceCode, Date endDate, Integer last,
			String range, Date startDate)
		throws Exception {

		Group group = groupLocalService.fetchGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		if ((group == null) || !group.isSite()) {
			throw new NotFoundException();
		}

		return _getExportPreview(
			endDate, group.getGroupId(), last, range, startDate);
	}

	private ExportPreview _getExportPreview(
			Date endDate, long groupId, Integer last, String range,
			Date startDate)
		throws Exception {

		PermissionUtil.checkExportPermission(
			contextCompany.getCompanyId(), groupId);

		DateRange dateRange = DateRangeUtil.toDateRange(
			endDate, last, range, startDate);

		Locale locale = contextAcceptLanguage.getPreferredLocale();

		Map<String, List<PreviewPortletDataHandler>>
			previewPortletDataHandlers = new LinkedHashMap<>();

		for (Portlet portlet :
				_exportImportHelper.getExportablePortlets(
					contextCompany.getCompanyId(), false, groupId)) {

			PortletDataHandler portletDataHandler =
				_portletDataHandlerProvider.provide(portlet);

			if (portletDataHandler == null) {
				continue;
			}

			PortletDataContext portletDataContext =
				_portletDataContextFactory.createPreparePortletDataContext(
					contextCompany.getCompanyId(), groupId, range,
					dateRange.getStartDate(), dateRange.getEndDate());

			portletDataHandler.prepareManifestSummary(portletDataContext);

			PreviewPortletDataHandlerUtil.addPreviewPortletDataHandler(
				contextCompany.getCompanyId(), locale,
				portletDataContext.getManifestSummary(), portlet,
				portletDataHandler,
				PortletDataHandler::getExportPortletDataHandlerControls,
				previewPortletDataHandlers);
		}

		return new ExportPreview() {
			{
				setAdditionCount(
					() -> PreviewPortletDataHandlerUtil.getAdditionCount(
						previewPortletDataHandlers));
				setDeletionCount(
					() -> PreviewPortletDataHandlerUtil.getDeletionCount(
						previewPortletDataHandlers));
				setPreviewPortletDataHandlerSections(
					() ->
						PreviewPortletDataHandlerUtil.
							toPreviewPortletDataHandlerSections(
								locale, previewPortletDataHandlers));
			}
		};
	}

	@Reference
	private ExportImportHelper _exportImportHelper;

	@Reference
	private PortletDataContextFactory _portletDataContextFactory;

	@Reference
	private PortletDataHandlerProvider _portletDataHandlerProvider;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

}