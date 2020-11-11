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

package com.liferay.segments.web.internal.portlet;

import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.roles.admin.role.type.contributor.RoleTypeContributor;
import com.liferay.roles.admin.role.type.contributor.provider.RoleTypeContributorProvider;
import com.liferay.segments.configuration.SegmentsConfiguration;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.service.SegmentsEntryService;
import com.liferay.segments.web.internal.constants.SegmentsWebKeys;
import com.liferay.segments.web.internal.display.context.SegmentsDisplayContext;

import java.io.IOException;

import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	configurationPid = "com.liferay.segments.configuration.SegmentsConfiguration",
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-segments",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"javax.portlet.display-name=Segments",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + SegmentsPortletKeys.SEGMENTS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = {Portlet.class, SegmentsPortlet.class}
)
public class SegmentsPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(
			SegmentsWebKeys.EXCLUDED_ROLE_NAMES, _getExcludedRoleNames());
		renderRequest.setAttribute(
			SegmentsWebKeys.ITEM_SELECTOR, _itemSelector);

		SegmentsDisplayContext segmentsDisplayContext =
			new SegmentsDisplayContext(
				_portal.getHttpServletRequest(renderRequest), renderRequest,
				renderResponse, _roleSegmentationEnabled,
				_segmentsEntryService);

		renderRequest.setAttribute(
			SegmentsWebKeys.SEGMENTS_DISPLAY_CONTEXT, segmentsDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		SegmentsConfiguration segmentsConfiguration =
			ConfigurableUtil.createConfigurable(
				SegmentsConfiguration.class, properties);

		_roleSegmentationEnabled =
			segmentsConfiguration.roleSegmentationEnabled();
	}

	@Deactivate
	protected void deactivate() {
		_roleSegmentationEnabled = false;
	}

	private String[] _getExcludedRoleNames() {
		RoleTypeContributor roleTypeContributor =
			_roleTypeContributorProvider.getRoleTypeContributor(
				RoleConstants.TYPE_SITE);

		if (roleTypeContributor != null) {
			return roleTypeContributor.getExcludedRoleNames();
		}

		return new String[0];
	}

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private Portal _portal;

	private boolean _roleSegmentationEnabled;

	@Reference
	private RoleTypeContributorProvider _roleTypeContributorProvider;

	@Reference
	private SegmentsEntryService _segmentsEntryService;

}