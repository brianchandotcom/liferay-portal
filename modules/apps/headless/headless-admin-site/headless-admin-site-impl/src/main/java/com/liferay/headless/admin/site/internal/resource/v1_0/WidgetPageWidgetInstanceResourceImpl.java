/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.headless.admin.site.dto.v1_0.WidgetPageWidgetInstance;
import com.liferay.headless.admin.site.internal.resource.util.GroupUtil;
import com.liferay.headless.admin.site.resource.v1_0.WidgetPageWidgetInstanceResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchPortletException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutType;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rubén Pulido
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/widget-page-widget-instance.properties",
	scope = ServiceScope.PROTOTYPE,
	service = WidgetPageWidgetInstanceResource.class
)
public class WidgetPageWidgetInstanceResourceImpl
	extends BaseWidgetPageWidgetInstanceResourceImpl {

	@Override
	public void
			deleteSiteSiteByExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode(
				String siteExternalReferenceCode,
				String sitePageExternalReferenceCode,
				String widgetInstanceExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Layout layout = _layoutLocalService.fetchLayoutByExternalReferenceCode(
			sitePageExternalReferenceCode,
			GroupUtil.getGroupId(
				false, contextCompany.getCompanyId(),
				siteExternalReferenceCode));

		if (layout == null) {
			throw new UnsupportedOperationException();
		}

		LayoutType layoutType = layout.getLayoutType();

		if (!(layoutType instanceof LayoutTypePortlet)) {
			throw new UnsupportedOperationException();
		}

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		if (!layoutTypePortlet.hasPortletId(
				widgetInstanceExternalReferenceCode)) {

			throw new NoSuchPortletException();
		}

		layoutTypePortlet.removePortletId(
			contextUser.getUserId(), widgetInstanceExternalReferenceCode);

		_layoutLocalService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());
	}

	@Override
	public Page<WidgetPageWidgetInstance>
			getSiteSiteByExternalReferenceCodeSitePageWidgetInstancesPage(
				String siteExternalReferenceCode,
				String sitePageExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Layout layout = _layoutLocalService.fetchLayoutByExternalReferenceCode(
			sitePageExternalReferenceCode,
			GroupUtil.getGroupId(
				false, contextCompany.getCompanyId(),
				siteExternalReferenceCode));

		if (layout == null) {
			throw new UnsupportedOperationException();
		}

		LayoutType layoutType = layout.getLayoutType();

		if (!(layoutType instanceof LayoutTypePortlet)) {
			throw new UnsupportedOperationException();
		}

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		return Page.of(
			transform(
				layoutTypePortlet.getPortletIds(),
				portletId -> _toWidgetPageWidgetInstance(layout, portletId)));
	}

	@Override
	public WidgetPageWidgetInstance
			getSiteSiteByExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode(
				String siteExternalReferenceCode,
				String sitePageExternalReferenceCode,
				String widgetInstanceExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Layout layout = _layoutLocalService.fetchLayoutByExternalReferenceCode(
			sitePageExternalReferenceCode,
			GroupUtil.getGroupId(
				false, contextCompany.getCompanyId(),
				siteExternalReferenceCode));

		if (layout == null) {
			throw new UnsupportedOperationException();
		}

		LayoutType layoutType = layout.getLayoutType();

		if (!(layoutType instanceof LayoutTypePortlet)) {
			throw new UnsupportedOperationException();
		}

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		if (!layoutTypePortlet.hasPortletId(
				widgetInstanceExternalReferenceCode)) {

			throw new NoSuchPortletException();
		}

		return _toWidgetPageWidgetInstance(
			layout, widgetInstanceExternalReferenceCode);
	}

	@Override
	public WidgetPageWidgetInstance
			postSiteSiteByExternalReferenceCodeSitePageWidgetInstance(
				String siteExternalReferenceCode,
				String sitePageExternalReferenceCode,
				WidgetPageWidgetInstance widgetPageWidgetInstance)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Layout layout = _layoutLocalService.fetchLayoutByExternalReferenceCode(
			sitePageExternalReferenceCode,
			GroupUtil.getGroupId(
				false, contextCompany.getCompanyId(),
				siteExternalReferenceCode));

		if (layout == null) {
			throw new UnsupportedOperationException();
		}

		LayoutType layoutType = layout.getLayoutType();

		if (!(layoutType instanceof LayoutTypePortlet)) {
			throw new UnsupportedOperationException();
		}

		String portletId = PortletIdCodec.encode(
			widgetPageWidgetInstance.getWidgetName(),
			widgetPageWidgetInstance.getWidgetInstanceId());

		return _addPortletId(
			widgetPageWidgetInstance.getParentSectionId(), layout, portletId,
			widgetPageWidgetInstance.getPosition());
	}

	@Override
	public WidgetPageWidgetInstance
			putSiteSiteByExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode(
				String siteExternalReferenceCode,
				String sitePageExternalReferenceCode,
				String widgetInstanceExternalReferenceCode,
				WidgetPageWidgetInstance widgetPageWidgetInstance)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Layout layout = _layoutLocalService.fetchLayoutByExternalReferenceCode(
			sitePageExternalReferenceCode,
			GroupUtil.getGroupId(
				false, contextCompany.getCompanyId(),
				siteExternalReferenceCode));

		if (layout == null) {
			throw new UnsupportedOperationException();
		}

		LayoutType layoutType = layout.getLayoutType();

		if (!(layoutType instanceof LayoutTypePortlet)) {
			throw new UnsupportedOperationException();
		}

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		String portletId = PortletIdCodec.encode(
			widgetPageWidgetInstance.getWidgetName(),
			widgetPageWidgetInstance.getWidgetInstanceId());

		if (!layoutTypePortlet.hasPortletId(portletId)) {
			return _addPortletId(
				widgetPageWidgetInstance.getParentSectionId(), layout,
				portletId, widgetPageWidgetInstance.getPosition());
		}

		if (!Objects.equals(
				widgetPageWidgetInstance.getParentSectionId(),
				_getParentSectionId(layout, portletId)) ||
			!Objects.equals(
				widgetPageWidgetInstance.getPosition(),
				_getPosition(layout, portletId))) {

			layoutTypePortlet.movePortletId(
				contextUser.getUserId(), portletId,
				widgetPageWidgetInstance.getParentSectionId(),
				widgetPageWidgetInstance.getPosition());
		}

		layout = _layoutLocalService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());

		return _toWidgetPageWidgetInstance(layout, portletId);
	}

	private WidgetPageWidgetInstance _addPortletId(
			String columnId, Layout layout, String portletId, int position)
		throws Exception {

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		String addedPortletId = layoutTypePortlet.addPortletId(
			contextUser.getUserId(), portletId, columnId, position);

		if (addedPortletId == null) {
			throw new PortalException(
				StringBundler.concat(
					"Portlet ", portletId, " cannot be added to layout ",
					layout.getPlid(), " by user ", contextUser.getUserId()));
		}

		layout = _layoutLocalService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());

		return _toWidgetPageWidgetInstance(layout, addedPortletId);
	}

	private String _getParentSectionId(Layout layout, String portletId) {
		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		return layoutTypePortlet.getColumn(portletId);
	}

	private Integer _getPosition(Layout layout, String portletId) {
		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		List<String> columns = layoutTypePortlet.getColumns();

		UnicodeProperties typeSettingsUnicodeProperties =
			layout.getTypeSettingsProperties();

		for (String columnId : columns) {
			String columnValue = typeSettingsUnicodeProperties.getProperty(
				columnId, StringPool.BLANK);

			List<String> portletIds = ListUtil.fromString(
				columnValue, StringPool.COMMA);

			int position = portletIds.indexOf(portletId);

			if (position >= 0) {
				return position;
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Position for portlet cannot be obtained since portlet ",
					portletId, " cannot be found in layout ",
					layout.getPlid()));
		}

		return null;
	}

	private WidgetPageWidgetInstance _toWidgetPageWidgetInstance(
		Layout layout, String portletId) {

		return new WidgetPageWidgetInstance() {
			{
				setExternalReferenceCode(() -> portletId);
				setParentSectionId(
					() -> _getParentSectionId(layout, portletId));
				setPosition(() -> _getPosition(layout, portletId));
				setWidgetInstanceId(
					() -> PortletIdCodec.decodeInstanceId(portletId));
				setWidgetName(
					() -> PortletIdCodec.decodePortletName(portletId));
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WidgetPageWidgetInstanceResourceImpl.class);

	@Reference
	private LayoutLocalService _layoutLocalService;

}