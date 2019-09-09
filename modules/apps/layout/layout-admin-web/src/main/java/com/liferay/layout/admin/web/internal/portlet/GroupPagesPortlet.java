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

package com.liferay.layout.admin.web.internal.portlet;

import com.liferay.application.list.GroupProvider;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.item.selector.ItemSelector;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.admin.web.internal.configuration.LayoutAdminWebConfiguration;
import com.liferay.layout.admin.web.internal.configuration.LayoutConverterConfiguration;
import com.liferay.layout.admin.web.internal.constants.LayoutAdminWebKeys;
import com.liferay.layout.page.template.exception.DuplicateLayoutPageTemplateCollectionException;
import com.liferay.layout.page.template.exception.LayoutPageTemplateCollectionNameException;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.layout.util.template.LayoutConverterRegistry;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.GroupInheritContentException;
import com.liferay.portal.kernel.exception.ImageTypeException;
import com.liferay.portal.kernel.exception.LayoutFriendlyURLException;
import com.liferay.portal.kernel.exception.LayoutFriendlyURLsException;
import com.liferay.portal.kernel.exception.LayoutNameException;
import com.liferay.portal.kernel.exception.LayoutParentLayoutIdException;
import com.liferay.portal.kernel.exception.LayoutSetVirtualHostException;
import com.liferay.portal.kernel.exception.LayoutTypeException;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.RequiredLayoutException;
import com.liferay.portal.kernel.exception.RequiredLayoutPrototypeException;
import com.liferay.portal.kernel.exception.SitemapChangeFrequencyException;
import com.liferay.portal.kernel.exception.SitemapIncludeException;
import com.liferay.portal.kernel.exception.SitemapPagePriorityException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.List;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(
	configurationPid = "com.liferay.layout.admin.web.configuration.LayoutAdminWebConfiguration, com.liferay.layout.admin.web.internal.configuration.LayoutConverterConfiguration",
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-layouts-admin",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/group_pages.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Group Pages",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.supported-public-render-parameter=layoutSetBranchId"
	},
	service = Portlet.class
)
public class GroupPagesPortlet extends MVCPortlet {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_layoutAdminWebConfiguration = ConfigurableUtil.createConfigurable(
			LayoutAdminWebConfiguration.class, properties);
		_layoutConverterConfiguration = ConfigurableUtil.createConfigurable(
			LayoutConverterConfiguration.class, properties);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		Group group = _groupProvider.getGroup(
			_portal.getHttpServletRequest(renderRequest));

		renderRequest.setAttribute(WebKeys.GROUP, group);

		if (SessionErrors.contains(
				renderRequest, NoSuchGroupException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, PrincipalException.getNestedClasses())) {

			include("/error.jsp", renderRequest, renderResponse);
		}
		else {
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

				ServiceContext serviceContext =
					ServiceContextFactory.getInstance(renderRequest);

				ServiceContextThreadLocal.pushServiceContext(serviceContext);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}

			renderRequest.setAttribute(
				LayoutAdminWebConfiguration.class.getName(),
				_layoutAdminWebConfiguration);
			renderRequest.setAttribute(
				LayoutConverterConfiguration.class.getName(),
				_layoutConverterConfiguration);
			renderRequest.setAttribute(
				ApplicationListWebKeys.GROUP_PROVIDER, _groupProvider);
			renderRequest.setAttribute(
				InfoDisplayWebKeys.INFO_DISPLAY_CONTRIBUTOR_TRACKER,
				_infoDisplayContributorTracker);
			renderRequest.setAttribute(
				LayoutAdminWebKeys.ITEM_SELECTOR, _itemSelector);
			renderRequest.setAttribute(
				LayoutAdminWebKeys.LAYOUT_COPY_HELPER, _layoutCopyHelper);
			renderRequest.setAttribute(
				LayoutAdminWebKeys.LAYOUT_TEMPLATE_CONVERTER_REGISTRY,
				_layoutConverterRegistry);

			super.doDispatch(renderRequest, renderResponse);
		}
	}

	@Override
	protected boolean isAlwaysSendRedirect() {
		return true;
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof AssetCategoryException ||
			cause instanceof DuplicateLayoutPageTemplateCollectionException ||
			cause instanceof GroupInheritContentException ||
			cause instanceof ImageTypeException ||
			cause instanceof LayoutFriendlyURLException ||
			cause instanceof LayoutFriendlyURLsException ||
			cause instanceof LayoutNameException ||
			cause instanceof LayoutPageTemplateCollectionNameException ||
			cause instanceof LayoutParentLayoutIdException ||
			cause instanceof LayoutSetVirtualHostException ||
			cause instanceof LayoutTypeException ||
			cause instanceof NoSuchGroupException ||
			cause instanceof PrincipalException ||
			cause instanceof RequiredLayoutException ||
			cause instanceof RequiredLayoutPrototypeException ||
			cause instanceof SitemapChangeFrequencyException ||
			cause instanceof SitemapIncludeException ||
			cause instanceof SitemapPagePriorityException ||
			cause instanceof UploadException) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GroupPagesPortlet.class);

	@Reference
	private GroupProvider _groupProvider;

	@Reference
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Reference
	private ItemSelector _itemSelector;

	private volatile LayoutAdminWebConfiguration _layoutAdminWebConfiguration;
	private volatile LayoutConverterConfiguration _layoutConverterConfiguration;

	@Reference
	private LayoutConverterRegistry _layoutConverterRegistry;

	@Reference
	private LayoutCopyHelper _layoutCopyHelper;

	@Reference
	private LayoutPageTemplateCollectionService
		_layoutPageTemplateCollectionService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPrototypeLocalService _layoutPrototypeLocalService;

	@Reference
	private Portal _portal;

}