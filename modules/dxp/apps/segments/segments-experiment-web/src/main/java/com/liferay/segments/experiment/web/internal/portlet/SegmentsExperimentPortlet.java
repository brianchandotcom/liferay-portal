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

package com.liferay.segments.experiment.web.internal.portlet;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.experiment.web.internal.configuration.SegmentsExperimentConfiguration;
import com.liferay.segments.experiment.web.internal.constants.SegmentsExperimentWebKeys;
import com.liferay.segments.experiment.web.internal.display.context.SegmentsExperimentDisplayContext;
import com.liferay.segments.experiment.web.internal.product.navigation.control.menu.SegmentsExperimentProductNavigationControlMenuEntry;
import com.liferay.segments.service.SegmentsExperienceService;
import com.liferay.segments.service.SegmentsExperimentRelService;
import com.liferay.segments.service.SegmentsExperimentService;

import java.io.IOException;

import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(
	configurationPid = "com.liferay.segments.experiment.web.internal.configuration.SegmentsExperimentConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=false",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=false",
		"javax.portlet.display-name=Segments Experiment",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + SegmentsPortletKeys.SEGMENTS_EXPERIMENT,
		"javax.portlet.resource-bundle=content.Language"
	},
	service = {Portlet.class, SegmentsExperimentPortlet.class}
)
public class SegmentsExperimentPortlet extends MVCPortlet {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_segmentsExperimentConfiguration = ConfigurableUtil.createConfigurable(
			SegmentsExperimentConfiguration.class, properties);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		String layoutMode = ParamUtil.getString(
			originalHttpServletRequest, "p_l_mode", Constants.VIEW);

		if (layoutMode.equals(Constants.PREVIEW)) {
			return;
		}

		SegmentsExperimentDisplayContext segmentsExperimentDisplayContext =
			new SegmentsExperimentDisplayContext(
				httpServletRequest, _layoutLocalService, _portal,
				renderResponse, _segmentsExperienceService,
				_segmentsExperimentConfiguration, _segmentsExperimentRelService,
				_segmentsExperimentService);

		renderRequest.setAttribute(
			SegmentsExperimentWebKeys.SEGMENTS_EXPERIMENT_DISPLAY_CONTEXT,
			segmentsExperimentDisplayContext);

		renderRequest.setAttribute(
			SegmentsExperimentWebKeys.SEGMENTS_EXPERIMENT_PANEL_STATE_OPEN,
			_segmentsExperimentProductNavigationControlMenuEntry.
				isPanelStateOpen(httpServletRequest));

		super.doDispatch(renderRequest, renderResponse);
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsExperienceService _segmentsExperienceService;

	private volatile SegmentsExperimentConfiguration
		_segmentsExperimentConfiguration;

	@Reference
	private SegmentsExperimentProductNavigationControlMenuEntry
		_segmentsExperimentProductNavigationControlMenuEntry;

	@Reference
	private SegmentsExperimentRelService _segmentsExperimentRelService;

	@Reference
	private SegmentsExperimentService _segmentsExperimentService;

}