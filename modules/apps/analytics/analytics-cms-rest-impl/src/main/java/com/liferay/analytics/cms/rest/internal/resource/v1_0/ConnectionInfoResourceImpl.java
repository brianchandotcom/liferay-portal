/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.internal.resource.v1_0;

import com.liferay.analytics.cms.rest.dto.v1_0.ConnectionInfo;
import com.liferay.analytics.cms.rest.resource.v1_0.ConnectionInfoResource;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.model.DepotEntryGroupRelModel;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.depot.service.DepotEntryService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rachael Koestartyo
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/connection-info.properties",
	scope = ServiceScope.PROTOTYPE, service = ConnectionInfoResource.class
)
public class ConnectionInfoResourceImpl extends BaseConnectionInfoResourceImpl {

	@Override
	public ConnectionInfo getConnectionInfo(Long spaceId) throws Exception {
		String analyticsSettingsPortletURL = PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				contextHttpServletRequest,
				ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/configuration_admin/view_configuration_screen"
		).setParameter(
			"configurationScreenKey", "analytics-cloud-connection"
		).buildString();

		DepotEntry depotEntry = _depotEntryService.getDepotEntry(spaceId);

		String siteEditDepotEntryDepotAdminPortletURL =
			PortletURLBuilder.create(
				_portletURLFactory.create(
					contextHttpServletRequest, _DEPOT_ADMIN_PORTLET_ID,
					PortletRequest.RENDER_PHASE)
			).setMVCRenderCommandName(
				"/depot/edit_depot_entry"
			).setParameter(
				"depotEntryId", depotEntry.getDepotEntryId()
			).setParameter(
				"screenNavigationEntryKey", "sites"
			).buildString();

		AnalyticsConfiguration analyticsConfiguration =
			_analyticsSettingsManager.getAnalyticsConfiguration(
				contextUser.getCompanyId());

		List<Long> groupIds = _getDepotEntryGroupRelToGroupId(depotEntry);

		return _toConnectionInfo(
			roleLocalService.hasUserRole(
				contextUser.getUserId(), contextUser.getCompanyId(),
				RoleConstants.ADMINISTRATOR, true),
			analyticsSettingsPortletURL,
			!Validator.isBlank(analyticsConfiguration.token()),
			!groupIds.isEmpty(), siteEditDepotEntryDepotAdminPortletURL,
			_hasSitesSyncedToAnalyticsCloud(
				analyticsConfiguration.syncedGroupIds(), groupIds));
	}

	private List<Long> _getDepotEntryGroupRelToGroupId(DepotEntry depotEntry) {
		return TransformUtil.transform(
			_depotEntryGroupRelLocalService.getDepotEntryGroupRels(depotEntry),
			DepotEntryGroupRelModel::getToGroupId);
	}

	private boolean _hasSitesSyncedToAnalyticsCloud(
		String[] analyticsCloudSyncedGroupIds, List<Long> groupIds) {

		for (long groupId : groupIds) {
			if (ArrayUtil.contains(
					analyticsCloudSyncedGroupIds, String.valueOf(groupId))) {

				return true;
			}
		}

		return false;
	}

	private ConnectionInfo _toConnectionInfo(
		boolean admin, String analyticsSettingsPortletURL,
		boolean connectedToAnalyticsCloud, boolean connectedToSpace,
		String siteEditDepotEntryDepotAdminPortletURL,
		boolean siteSyncedToAnalyticsCloud) {

		ConnectionInfo connectionInfo = new ConnectionInfo();

		connectionInfo.setIsAdmin(() -> admin);
		connectionInfo.setAnalyticsSettingsPortletURL(
			() -> analyticsSettingsPortletURL);
		connectionInfo.setConnectedToAnalyticsCloud(
			() -> connectedToAnalyticsCloud);
		connectionInfo.setConnectedToSpace(() -> connectedToSpace);
		connectionInfo.setSiteEditDepotEntryDepotAdminPortletURL(
			() -> siteEditDepotEntryDepotAdminPortletURL);
		connectionInfo.setSiteSyncedToAnalyticsCloud(
			() -> siteSyncedToAnalyticsCloud);

		return connectionInfo;
	}

	private static final String _DEPOT_ADMIN_PORTLET_ID =
		"com_liferay_depot_web_portlet_DepotAdminPortlet";

	private static final Log _log = LogFactoryUtil.getLog(
		ConnectionInfoResourceImpl.class);

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private DepotEntryService _depotEntryService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

}