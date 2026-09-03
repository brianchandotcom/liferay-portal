/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.lar;

import com.liferay.exportimport.internal.util.SiteExportImportParameterUtil;
import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactory;
import com.liferay.exportimport.lar.SiteExporter;
import com.liferay.exportimport.site.ExportImportSiteProvider;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(service = SiteExporter.class)
public class SiteExporterImpl implements SiteExporter {

	@Override
	public void addSitesElement(
			PortletDataContext portletDataContext, Element element)
		throws PortalException {

		if (!SiteExportImportParameterUtil.isSiteExportImportEnabled(
				portletDataContext.getCompanyId()) ||
			ExportImportThreadLocal.isStagingInProcess() ||
			SiteExportImportParameterUtil.isSiteScoped(portletDataContext)) {

			return;
		}

		String[] selectedSiteExternalReferenceCodes =
			SiteExportImportParameterUtil.getSelectedSiteExternalReferenceCodes(
				portletDataContext);

		if (ArrayUtil.isEmpty(selectedSiteExternalReferenceCodes)) {
			return;
		}

		Element sitesElement = element.addElement("sites");

		for (String selectedSiteExternalReferenceCode :
				selectedSiteExternalReferenceCodes) {

			Group group = null;

			try {
				group = _fetchGroup(
					portletDataContext, selectedSiteExternalReferenceCode);
			}
			catch (PrincipalException principalException) {
				_log.error(
					"Unable to export site " +
						selectedSiteExternalReferenceCode,
					principalException);
			}

			if (group == null) {
				continue;
			}

			Element siteElement = sitesElement.addElement("site");

			Locale locale = LocaleUtil.fromLanguageId(
				group.getDefaultLanguageId());

			siteElement.addAttribute(
				"child-site-count",
				String.valueOf(
					_exportImportSiteProvider.getChildGroupCount(group)));
			siteElement.addAttribute(
				"descriptive-name",
				_exportImportSiteProvider.getDescriptiveName(group, locale));
			siteElement.addAttribute(
				"external-reference-code", group.getExternalReferenceCode());
			siteElement.addAttribute(
				"group-id", String.valueOf(group.getGroupId()));
			siteElement.addAttribute(
				"path", _exportImportSiteProvider.getPath(group, locale));

			Group parentGroup = group.getParentGroup();

			if (parentGroup != null) {
				siteElement.addAttribute(
					"parent-external-reference-code",
					parentGroup.getExternalReferenceCode());
			}
		}
	}

	@Override
	public void exportSites(
			PortletDataContext portletDataContext,
			UnsafeConsumer<PortletDataContext, Exception>
				exportSiteUnsafeConsumer)
		throws Exception {

		if (!SiteExportImportParameterUtil.isSiteExportImportEnabled(
				portletDataContext.getCompanyId()) ||
			ExportImportThreadLocal.isStagingInProcess() ||
			SiteExportImportParameterUtil.isSiteScoped(portletDataContext)) {

			return;
		}

		String[] selectedSiteExternalReferenceCodes =
			SiteExportImportParameterUtil.getSelectedSiteExternalReferenceCodes(
				portletDataContext);

		for (String selectedSiteExternalReferenceCode :
				selectedSiteExternalReferenceCodes) {

			Group group = null;

			try {
				group = _fetchGroup(
					portletDataContext, selectedSiteExternalReferenceCode);
			}
			catch (PrincipalException principalException) {
				_log.error(
					"Unable to export site " +
						selectedSiteExternalReferenceCode,
					principalException);
			}

			if (group == null) {
				continue;
			}

			exportSiteUnsafeConsumer.accept(
				_createPortletDataContext(portletDataContext, group));
		}
	}

	private PortletDataContext _createPortletDataContext(
			PortletDataContext portletDataContext, Group group)
		throws Exception {

		PortletDataContext sitePortletDataContext =
			_portletDataContextFactory.createExportPortletDataContext(
				portletDataContext.getCompanyId(), group.getGroupId(),
				SiteExportImportParameterUtil.toSiteExportParameterMap(
					portletDataContext.getParameterMap(),
					group.getExternalReferenceCode()),
				portletDataContext.getStartDate(),
				portletDataContext.getEndDate(),
				portletDataContext.getZipWriter());

		sitePortletDataContext.setExportImportProcessId(
			portletDataContext.getExportImportProcessId());
		sitePortletDataContext.setLayoutIds(
			_exportImportHelper.getAllLayoutIds(group.getGroupId(), false));
		sitePortletDataContext.setPrivateLayout(false);

		return sitePortletDataContext;
	}

	private Group _fetchGroup(
			PortletDataContext portletDataContext, String externalReferenceCode)
		throws PortalException {

		Group group = _groupService.fetchGroupByExternalReferenceCode(
			externalReferenceCode, portletDataContext.getCompanyId());

		if (group == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Site " + externalReferenceCode +
						" is missing in the instance");
			}

			return null;
		}

		GroupPermissionUtil.check(
			PermissionThreadLocal.getPermissionChecker(), group.getGroupId(),
			ActionKeys.EXPORT_IMPORT_LAYOUTS);

		if (!_exportImportSiteProvider.isSupported(group)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Exporting site " + externalReferenceCode +
						" is not supported");
			}

			return null;
		}

		return group;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SiteExporterImpl.class);

	@Reference
	private ExportImportHelper _exportImportHelper;

	@Reference
	private ExportImportSiteProvider _exportImportSiteProvider;

	@Reference
	private GroupService _groupService;

	@Reference
	private PortletDataContextFactory _portletDataContextFactory;

}