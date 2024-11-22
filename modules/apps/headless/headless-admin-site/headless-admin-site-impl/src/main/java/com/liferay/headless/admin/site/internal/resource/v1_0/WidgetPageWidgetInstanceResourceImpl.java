/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.headless.admin.site.dto.v1_0.WidgetPageWidgetInstance;
import com.liferay.headless.admin.site.internal.resource.util.GroupUtil;
import com.liferay.headless.admin.site.resource.v1_0.WidgetPageWidgetInstanceResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchPortletException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutType;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.List;

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

		return new WidgetPageWidgetInstance() {
			{
				setParentSectionId(
					() -> _getParentSectionId(
						layout, widgetInstanceExternalReferenceCode));
				setPosition(
					() -> _getPosition(
						layout, widgetInstanceExternalReferenceCode));
				setWidgetInstanceId(
					() -> PortletIdCodec.decodeInstanceId(
						widgetInstanceExternalReferenceCode));
				setWidgetName(
					() -> PortletIdCodec.decodePortletName(
						widgetInstanceExternalReferenceCode));
			}
		};
	}

	private String _getParentSectionId(Layout layout, String portletId) {
		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		return layoutTypePortlet.getColumn(portletId);
	}

	private int _getPosition(Layout layout, String portletId) {
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

		return 0;
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

}