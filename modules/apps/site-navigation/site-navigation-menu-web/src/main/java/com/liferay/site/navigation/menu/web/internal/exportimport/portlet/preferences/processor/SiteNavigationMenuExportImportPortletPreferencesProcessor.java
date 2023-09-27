/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.menu.web.internal.exportimport.portlet.preferences.processor;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.PortletPreferenceValueLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.site.navigation.constants.SiteNavigationConstants;
import com.liferay.site.navigation.constants.SiteNavigationMenuPortletKeys;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = "javax.portlet.name=" + SiteNavigationMenuPortletKeys.SITE_NAVIGATION_MENU,
	service = ExportImportPortletPreferencesProcessor.class
)
public class SiteNavigationMenuExportImportPortletPreferencesProcessor
	implements ExportImportPortletPreferencesProcessor {

	@Override
	public List<Capability> getExportCapabilities() {
		return ListUtil.fromArray(_exportCapability);
	}

	@Override
	public List<Capability> getImportCapabilities() {
		return ListUtil.fromArray(_importCapability);
	}

	@Override
	public PortletPreferences processExportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		if (!MapUtil.getBoolean(
				portletDataContext.getParameterMap(),
				PortletDataHandlerKeys.PORTLET_DATA) &&
			MergeLayoutPrototypesThreadLocal.isInProgress()) {

			return portletPreferences;
		}

		try {
			portletDataContext.addPortletPermissions(
				SiteNavigationConstants.RESOURCE_NAME);
		}
		catch (PortalException portalException) {
			throw new PortletDataException(
				"Unable to export portlet permissions", portalException);
		}

		long siteNavigationMenuId = GetterUtil.getLong(
			portletPreferences.getValue("siteNavigationMenuId", null));

		if (siteNavigationMenuId > 0) {
			SiteNavigationMenu siteNavigationMenu =
				_siteNavigationMenuLocalService.fetchSiteNavigationMenu(
					siteNavigationMenuId);

			if (siteNavigationMenu != null) {
				String siteNavigationMenuUuid = siteNavigationMenu.getUuid();

				SiteNavigationMenu siteNavigationMenuToExport =
					_siteNavigationMenuLocalService.
						fetchSiteNavigationMenuByUuidAndGroupId(
							siteNavigationMenuUuid,
							portletDataContext.getScopeGroupId());

				if (siteNavigationMenuToExport != null) {
					StagedModelDataHandlerUtil.exportReferenceStagedModel(
						portletDataContext, portletDataContext.getPortletId(),
						siteNavigationMenuToExport);
				}
			}
		}

		return portletPreferences;
	}

	@Override
	public PortletPreferences processImportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		try {
			portletDataContext.importPortletPermissions(
				SiteNavigationConstants.RESOURCE_NAME);
		}
		catch (PortalException portalException) {
			throw new PortletDataException(
				"Unable to import portlet permissions", portalException);
		}

		if (!MapUtil.getBoolean(
				portletDataContext.getParameterMap(),
				PortletDataHandlerKeys.PORTLET_DATA) &&
			MergeLayoutPrototypesThreadLocal.isInProgress()) {

			long siteNavigationMenuId = 0;

			long originalPlid = MapUtil.getLong(
				portletDataContext.getParameterMap(), "portletPreferencePlid");

			List<com.liferay.portal.kernel.model.PortletPreferences>
				serviceBuilderPortletPreferencesList = null;

			if (originalPlid == PortletKeys.PREFS_PLID_SHARED) {
				serviceBuilderPortletPreferencesList =
					_portletPreferencesLocalService.getPortletPreferences(
						PortletKeys.PREFS_PLID_SHARED,
						portletDataContext.getPortletId());
			}
			else {
				serviceBuilderPortletPreferencesList =
					_portletPreferencesLocalService.getPortletPreferences(
						portletDataContext.getPlid(),
						portletDataContext.getPortletId());
			}

			if (!serviceBuilderPortletPreferencesList.isEmpty()) {
				for (com.liferay.portal.kernel.model.PortletPreferences
						serviceBuilderPortletPreferences :
							serviceBuilderPortletPreferencesList) {

					if (serviceBuilderPortletPreferences.getCompanyId() !=
							portletDataContext.getCompanyId()) {

						continue;
					}

					PortletPreferences originalPortletPreferences =
						_portletPreferenceValueLocalService.getPreferences(
							serviceBuilderPortletPreferences);

					siteNavigationMenuId = GetterUtil.getLong(
						originalPortletPreferences.getValue(
							"siteNavigationMenuId", "0"));
				}
			}

			try {
				portletPreferences.setValue(
					"siteNavigationMenuId",
					String.valueOf(siteNavigationMenuId));
			}
			catch (ReadOnlyException readOnlyException) {
				PortletDataException portletDataException =
					new PortletDataException(readOnlyException);

				throw portletDataException;
			}

			return portletPreferences;
		}

		long importedSiteNavigationMenuId = GetterUtil.getLong(
			portletPreferences.getValue("siteNavigationMenuId", null));

		try {
			if (importedSiteNavigationMenuId > 0) {
				StagedModelDataHandlerUtil.importReferenceStagedModel(
					portletDataContext, SiteNavigationMenu.class,
					importedSiteNavigationMenuId);

				Map<Long, Long> siteNavigationMenuIds =
					(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
						SiteNavigationMenu.class);

				long siteNavigationMenuId = MapUtil.getLong(
					siteNavigationMenuIds, importedSiteNavigationMenuId,
					importedSiteNavigationMenuId);

				if (siteNavigationMenuId > 0) {
					portletPreferences.setValue(
						"siteNavigationMenuId",
						String.valueOf(siteNavigationMenuId));
				}
			}
		}
		catch (ReadOnlyException readOnlyException) {
			PortletDataException portletDataException =
				new PortletDataException(readOnlyException);

			throw portletDataException;
		}

		return portletPreferences;
	}

	@Reference(target = "(name=PortletDisplayTemplateExporter)")
	private Capability _exportCapability;

	@Reference(target = "(name=PortletDisplayTemplateImporter)")
	private Capability _importCapability;

	@Reference(unbind = "-")
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference(unbind = "-")
	private PortletPreferenceValueLocalService
		_portletPreferenceValueLocalService;

	@Reference(unbind = "-")
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

}