/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.lar;

import com.liferay.exportimport.internal.util.LARManifestPathUtil;
import com.liferay.exportimport.internal.util.SiteExportImportParameterUtil;
import com.liferay.exportimport.kernel.exception.LARFileException;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactory;
import com.liferay.exportimport.lar.SiteImporter;
import com.liferay.exportimport.report.constants.ExportImportReportEntryConstants;
import com.liferay.exportimport.report.service.ExportImportReportEntryLocalService;
import com.liferay.exportimport.site.ExportImportSiteProvider;
import com.liferay.exportimport.site.LARSite;
import com.liferay.exportimport.site.LARSiteReader;
import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.GroupParentException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(service = SiteImporter.class)
public class SiteImporterImpl implements SiteImporter {

	@Override
	public void importSites(
			PortletDataContext portletDataContext,
			UnsafeBiConsumer<PortletDataContext, Long, Exception>
				importSiteUnsafeBiConsumer,
			long userId)
		throws Exception {

		if (!SiteExportImportParameterUtil.isSiteExportImportEnabled(
				portletDataContext.getCompanyId()) ||
			ExportImportThreadLocal.isStagingInProcess() ||
			SiteExportImportParameterUtil.isSiteScoped(portletDataContext)) {

			return;
		}

		List<LARSite> larSites = _larSiteReader.getLARSites(portletDataContext);

		if (ListUtil.isEmpty(larSites)) {
			return;
		}

		String[] selectedSiteExternalReferenceCodes =
			SiteExportImportParameterUtil.getSelectedSiteExternalReferenceCodes(
				portletDataContext);

		if (ArrayUtil.isEmpty(selectedSiteExternalReferenceCodes)) {
			return;
		}

		Set<String> unsatisfiedSiteExternalReferenceCodes = SetUtil.fromArray(
			selectedSiteExternalReferenceCodes);

		for (LARSite larSite : larSites) {
			if (!unsatisfiedSiteExternalReferenceCodes.remove(
					larSite.getExternalReferenceCode())) {

				continue;
			}

			Group group = null;

			try {
				group = _fetchSite(portletDataContext, larSite);
			}
			catch (PrincipalException principalException) {
				_log.error(
					"Unable to import site " +
						larSite.getExternalReferenceCode(),
					principalException);
			}

			if (group == null) {
				continue;
			}

			_updateParentSite(portletDataContext, group, larSite);

			importSiteUnsafeBiConsumer.accept(
				_createPortletDataContext(portletDataContext, larSite, group),
				userId);
		}

		for (String unsatisfiedSiteExternalReferenceCode :
				unsatisfiedSiteExternalReferenceCodes) {

			_reportMissingSite(
				portletDataContext, unsatisfiedSiteExternalReferenceCode);
		}
	}

	private void _addReportEntry(
		PortletDataContext portletDataContext, String externalReferenceCode,
		String message, int type) {

		_exportImportReportEntryLocalService.getOrAddExportImportReportEntry(
			0, portletDataContext.getCompanyId(),
			GetterUtil.getString(externalReferenceCode),
			_classNameLocalService.getClassNameId(Group.class.getName()), 0,
			GetterUtil.getLong(
				ExportImportThreadLocal.getExportImportConfigurationId()),
			type, message, null, "sites");
	}

	private PortletDataContext _createPortletDataContext(
			PortletDataContext portletDataContext, LARSite larSite, Group group)
		throws Exception {

		PortletDataContext sitePortletDataContext =
			_portletDataContextFactory.createImportPortletDataContext(
				portletDataContext.getCompanyId(), group.getGroupId(),
				SiteExportImportParameterUtil.toSiteImportParameterMap(
					portletDataContext.getParameterMap(),
					larSite.getExternalReferenceCode()),
				portletDataContext.getUserIdStrategy(),
				portletDataContext.getZipReader());

		sitePortletDataContext.setExportImportProcessId(
			portletDataContext.getExportImportProcessId());

		Element rootElement = _getRootElement(
			sitePortletDataContext, larSite.getGroupId());

		sitePortletDataContext.setImportDataRootElement(rootElement);

		Element missingReferencesElement = rootElement.element(
			"missing-references");

		if (missingReferencesElement != null) {
			sitePortletDataContext.setMissingReferencesElement(
				missingReferencesElement);
		}

		sitePortletDataContext.setPrivateLayout(false);

		Element headerElement = rootElement.element("header");

		if (headerElement == null) {
			throw new LARFileException(LARFileException.TYPE_INVALID_MANIFEST);
		}

		sitePortletDataContext.setSourceCompanyId(
			GetterUtil.getLong(headerElement.attributeValue("company-id")));
		sitePortletDataContext.setSourceCompanyGroupId(
			GetterUtil.getLong(
				headerElement.attributeValue("company-group-id")));

		sitePortletDataContext.setSourceGroupId(larSite.getGroupId());
		sitePortletDataContext.setSourceUserPersonalSiteGroupId(
			GetterUtil.getLong(
				headerElement.attributeValue("user-personal-site-group-id")));

		return sitePortletDataContext;
	}

	private Group _fetchSite(
			PortletDataContext portletDataContext, LARSite larSite)
		throws Exception {

		Group group = _groupService.fetchGroupByExternalReferenceCode(
			larSite.getExternalReferenceCode(),
			portletDataContext.getCompanyId());

		if (group == null) {
			_reportMissingTargetSite(
				portletDataContext, larSite.getExternalReferenceCode());

			return null;
		}

		GroupPermissionUtil.check(
			PermissionThreadLocal.getPermissionChecker(), group.getGroupId(),
			ActionKeys.EXPORT_IMPORT_LAYOUTS);

		if (!_exportImportSiteProvider.isSupported(group)) {
			_reportUnsupportedSite(
				portletDataContext, group.getExternalReferenceCode());

			return null;
		}

		return group;
	}

	private Element _getRootElement(
			PortletDataContext portletDataContext, long sourceGroupId)
		throws Exception {

		String xml = portletDataContext.getZipEntryAsString(
			LARManifestPathUtil.getImportManifestXmlFilePath(sourceGroupId));

		if (Validator.isNull(xml)) {
			throw new LARFileException(LARFileException.TYPE_MISSING_MANIFEST);
		}

		try {
			Document document = SAXReaderUtil.read(xml);

			return document.getRootElement();
		}
		catch (Exception exception) {
			throw new LARFileException(
				LARFileException.TYPE_INVALID_MANIFEST, exception);
		}
	}

	private void _reportConflictingParentSite(
		PortletDataContext portletDataContext, LARSite larSite) {

		_addReportEntry(
			portletDataContext, larSite.getExternalReferenceCode(),
			StringBundler.concat(
				"The parent site ", larSite.getParentExternalReferenceCode(),
				" for ", larSite.getDescriptiveName(),
				" is below it in the target system. Leaving the site where it ",
				"is"),
			ExportImportReportEntryConstants.TYPE_WARNING);
	}

	private void _reportMissingParentSite(
		PortletDataContext portletDataContext, LARSite larSite) {

		_addReportEntry(
			portletDataContext, larSite.getExternalReferenceCode(),
			StringBundler.concat(
				"The parent site ", larSite.getParentExternalReferenceCode(),
				" for ", larSite.getDescriptiveName(),
				" does not exist in the target system or in the LAR file."),
			ExportImportReportEntryConstants.TYPE_WARNING);
	}

	private void _reportMissingSite(
		PortletDataContext portletDataContext, String externalReferenceCode) {

		_addReportEntry(
			portletDataContext, externalReferenceCode,
			"Site " + externalReferenceCode + " is missing in the LAR file",
			ExportImportReportEntryConstants.TYPE_ERROR);
	}

	private void _reportMissingTargetSite(
		PortletDataContext portletDataContext, String externalReferenceCode) {

		_addReportEntry(
			portletDataContext, externalReferenceCode,
			StringBundler.concat(
				"The site ", externalReferenceCode,
				" does not exist in the target system. Create the site before ",
				"importing it."),
			ExportImportReportEntryConstants.TYPE_ERROR);
	}

	private void _reportUnmovedSite(
		PortletDataContext portletDataContext, LARSite larSite) {

		_addReportEntry(
			portletDataContext, larSite.getExternalReferenceCode(),
			StringBundler.concat(
				"Unable to move the site ", larSite.getDescriptiveName(),
				" under its parent site ",
				larSite.getParentExternalReferenceCode(), "."),
			ExportImportReportEntryConstants.TYPE_WARNING);
	}

	private void _reportUnsupportedSite(
		PortletDataContext portletDataContext, String externalReferenceCode) {

		_addReportEntry(
			portletDataContext, externalReferenceCode,
			"Importing site " + externalReferenceCode + " is not supported",
			ExportImportReportEntryConstants.TYPE_ERROR);
	}

	private void _updateParentSite(
			PortletDataContext portletDataContext, Group group, LARSite larSite)
		throws Exception {

		String parentExternalReferenceCode =
			larSite.getParentExternalReferenceCode();

		if (Validator.isNull(parentExternalReferenceCode)) {
			return;
		}

		Group parentGroup =
			_groupLocalService.fetchGroupByExternalReferenceCode(
				parentExternalReferenceCode, portletDataContext.getCompanyId());

		if (parentGroup == null) {
			_reportMissingParentSite(portletDataContext, larSite);

			return;
		}

		if (group.getParentGroupId() == parentGroup.getGroupId()) {
			return;
		}

		try {
			_groupLocalService.updateGroup(
				group.getGroupId(), parentGroup.getGroupId(),
				group.getNameMap(), group.getDescriptionMap(), group.getType(),
				group.getTypeSettings(), group.isManualMembership(),
				group.getMembershipRestriction(), group.getFriendlyURL(),
				group.isInheritContent(), group.isActive(), null);
		}
		catch (GroupParentException groupParentException) {
			if (_log.isDebugEnabled()) {
				_log.debug(groupParentException);
			}

			_reportConflictingParentSite(portletDataContext, larSite);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}

			_reportUnmovedSite(portletDataContext, larSite);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SiteImporterImpl.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ExportImportReportEntryLocalService
		_exportImportReportEntryLocalService;

	@Reference
	private ExportImportSiteProvider _exportImportSiteProvider;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private GroupService _groupService;

	@Reference
	private LARSiteReader _larSiteReader;

	@Reference
	private PortletDataContextFactory _portletDataContextFactory;

}