/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.page.template.admin.web.internal.portlet;

import com.liferay.asset.display.page.service.AssetDisplayPageEntryService;
import com.liferay.asset.kernel.service.AssetEntryService;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.search.InfoSearchClassMapperTracker;
import com.liferay.item.selector.ItemSelector;
import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.layout.page.template.admin.web.internal.configuration.LayoutPageTemplateAdminWebConfiguration;
import com.liferay.layout.page.template.admin.web.internal.constants.LayoutPageTemplateAdminWebKeys;
import com.liferay.layout.page.template.admin.web.internal.display.context.AssetDisplayPageUsagesDisplayContext;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.staging.StagingGroupHelper;

import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	configurationPid = "com.liferay.layout.page.template.admin.web.configuration.LayoutPageTemplateAdminWebConfiguration",
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-layout-templates-admin",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/group_pages.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Group Page Templates",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class LayoutPageTemplatesPortlet extends MVCPortlet {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_layoutPageTemplateAdminWebConfiguration =
			ConfigurableUtil.createConfigurable(
				LayoutPageTemplateAdminWebConfiguration.class, properties);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		String tabs = ParamUtil.getString(
			renderRequest, "tabs1", "master-layouts");

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group scopeGroup = themeDisplay.getScopeGroup();

		if (!Objects.equals(tabs, "master-layouts") &&
			(_stagingGroupHelper.isLocalLiveGroup(scopeGroup) ||
			 _stagingGroupHelper.isRemoteLiveGroup(scopeGroup))) {

			throw new PortletException();
		}

		try {
			List<LayoutPrototype> layoutPrototypes =
				_layoutPrototypeLocalService.getLayoutPrototypes(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (LayoutPrototype layoutPrototype : layoutPrototypes) {
				LayoutPageTemplateEntry layoutPageTemplateEntry =
					_layoutPageTemplateEntryLocalService.
						fetchFirstLayoutPageTemplateEntry(
							layoutPrototype.getLayoutPrototypeId());

				if (layoutPageTemplateEntry == null) {
					_layoutPageTemplateEntryLocalService.
						addGlobalLayoutPageTemplateEntry(layoutPrototype);
				}
			}

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				renderRequest);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		renderRequest.setAttribute(
			InfoDisplayWebKeys.INFO_ITEM_SERVICE_TRACKER,
			_infoItemServiceTracker);
		renderRequest.setAttribute(
			LayoutPageTemplateAdminWebKeys.
				ASSET_DISPLAY_PAGE_USAGES_DISPLAY_CONTEXT,
			new AssetDisplayPageUsagesDisplayContext(
				_assetDisplayPageEntryService, _assetEntryService,
				_portal.getHttpServletRequest(renderRequest),
				_infoSearchClassMapperTracker, _infoItemServiceTracker, _portal,
				renderRequest, renderResponse));
		renderRequest.setAttribute(
			LayoutPageTemplateAdminWebConfiguration.class.getName(),
			_layoutPageTemplateAdminWebConfiguration);
		renderRequest.setAttribute(
			LayoutPageTemplateAdminWebKeys.ITEM_SELECTOR, _itemSelector);

		if ((scopeGroup != null) && scopeGroup.isCompany()) {
			renderResponse.setTitle(
				_language.get(
					themeDisplay.getLocale(), "widget-page-templates"));
		}

		super.doDispatch(renderRequest, renderResponse);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplatesPortlet.class);

	@Reference
	private AssetDisplayPageEntryService _assetDisplayPageEntryService;

	@Reference
	private AssetEntryService _assetEntryService;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private InfoSearchClassMapperTracker _infoSearchClassMapperTracker;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private Language _language;

	private volatile LayoutPageTemplateAdminWebConfiguration
		_layoutPageTemplateAdminWebConfiguration;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPrototypeLocalService _layoutPrototypeLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

}