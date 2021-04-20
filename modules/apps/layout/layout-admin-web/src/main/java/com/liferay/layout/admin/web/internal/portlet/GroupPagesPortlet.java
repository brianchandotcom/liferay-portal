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
import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException;
import com.liferay.friendly.url.exception.DuplicateFriendlyURLEntryException;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.list.provider.InfoListProviderTracker;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.admin.web.internal.configuration.LayoutConverterConfiguration;
import com.liferay.layout.admin.web.internal.constants.LayoutAdminWebKeys;
import com.liferay.layout.admin.web.internal.display.context.LayoutsAdminDisplayContext;
import com.liferay.layout.admin.web.internal.display.context.MillerColumnsDisplayContext;
import com.liferay.layout.admin.web.internal.display.context.SelectLayoutCollectionDisplayContext;
import com.liferay.layout.page.template.exception.DuplicateLayoutPageTemplateCollectionException;
import com.liferay.layout.page.template.exception.LayoutPageTemplateCollectionNameException;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.layout.util.template.LayoutConverterRegistry;
import com.liferay.petra.string.StringPool;
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
import com.liferay.staging.StagingGroupHelper;

import java.io.IOException;

import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.MutableRenderParameters;
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
	configurationPid = "com.liferay.layout.admin.web.internal.configuration.LayoutConverterConfiguration",
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-layouts-admin",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/group_pages.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.preferences-unique-per-layout=false",
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
		"javax.portlet.supported-public-render-parameter=layoutSetBranchId",
		"javax.portlet.supported-public-render-parameter=selPlid"
	},
	service = Portlet.class
)
public class GroupPagesPortlet extends MVCPortlet {

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		super.processAction(actionRequest, actionResponse);

		if (!SessionErrors.isEmpty(actionRequest)) {
			MutableRenderParameters renderParameters =
				actionResponse.getRenderParameters();

			renderParameters.setValue("checkboxNames", StringPool.BLANK);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_layoutConverterConfiguration = ConfigurableUtil.createConfigurable(
			LayoutConverterConfiguration.class, properties);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		Group group = _groupProvider.getGroup(
			_portal.getHttpServletRequest(renderRequest));

		if (group.isCompany()) {
			throw new PortletException();
		}

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
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception, exception);
				}
			}

			LayoutsAdminDisplayContext layoutsAdminDisplayContext =
				new LayoutsAdminDisplayContext(
					_layoutConverterConfiguration, _layoutConverterRegistry,
					_layoutCopyHelper,
					_portal.getLiferayPortletRequest(renderRequest),
					_portal.getLiferayPortletResponse(renderResponse),
					_stagingGroupHelper);

			renderRequest.setAttribute(
				LayoutAdminWebKeys.LAYOUT_PAGE_LAYOUT_ADMIN_DISPLAY_CONTEXT,
				layoutsAdminDisplayContext);
			renderRequest.setAttribute(
				LayoutAdminWebKeys.MILLER_COLUMNS_DISPLAY_CONTEXT,
				new MillerColumnsDisplayContext(
					layoutsAdminDisplayContext,
					_portal.getLiferayPortletRequest(renderRequest),
					_portal.getLiferayPortletResponse(renderResponse)));

			renderRequest.setAttribute(
				LayoutAdminWebKeys.SELECT_LAYOUT_COLLECTION_DISPLAY_CONTEXT,
				new SelectLayoutCollectionDisplayContext(
					_infoItemServiceTracker, _infoListProviderTracker,
					_portal.getLiferayPortletRequest(renderRequest),
					_portal.getLiferayPortletResponse(renderResponse)));

			super.doDispatch(renderRequest, renderResponse);
		}
	}

	@Override
	protected boolean isAlwaysSendRedirect() {
		return true;
	}

	@Override
	protected boolean isSessionErrorException(Throwable throwable) {
		if (throwable instanceof AssetCategoryException ||
			throwable instanceof DDMFormValuesValidationException ||
			throwable instanceof DuplicateFriendlyURLEntryException ||
			throwable instanceof
				DuplicateLayoutPageTemplateCollectionException ||
			throwable instanceof GroupInheritContentException ||
			throwable instanceof ImageTypeException ||
			throwable instanceof LayoutFriendlyURLException ||
			throwable instanceof LayoutFriendlyURLsException ||
			throwable instanceof LayoutNameException ||
			throwable instanceof LayoutPageTemplateCollectionNameException ||
			throwable instanceof LayoutParentLayoutIdException ||
			throwable instanceof LayoutSetVirtualHostException ||
			throwable instanceof LayoutTypeException ||
			throwable instanceof NoSuchGroupException ||
			throwable instanceof PrincipalException ||
			throwable instanceof RequiredLayoutException ||
			throwable instanceof RequiredLayoutPrototypeException ||
			throwable instanceof SitemapChangeFrequencyException ||
			throwable instanceof SitemapIncludeException ||
			throwable instanceof SitemapPagePriorityException ||
			throwable instanceof UploadException) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GroupPagesPortlet.class);

	@Reference
	private GroupProvider _groupProvider;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private InfoListProviderTracker _infoListProviderTracker;

	private volatile LayoutConverterConfiguration _layoutConverterConfiguration;

	@Reference
	private LayoutConverterRegistry _layoutConverterRegistry;

	@Reference
	private LayoutCopyHelper _layoutCopyHelper;

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