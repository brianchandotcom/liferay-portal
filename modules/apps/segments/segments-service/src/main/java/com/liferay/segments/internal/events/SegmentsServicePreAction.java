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

package com.liferay.segments.internal.events;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsWebKeys;
import com.liferay.segments.context.RequestContextMapper;
import com.liferay.segments.internal.configuration.SegmentsServiceConfiguration;
import com.liferay.segments.processor.SegmentsExperienceRequestProcessorRegistry;
import com.liferay.segments.provider.SegmentsEntryProviderRegistry;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.simulator.SegmentsEntrySimulator;

import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Eduardo García
 */
@Component(
	configurationPid = "com.liferay.segments.internal.configuration.SegmentsServiceConfiguration",
	service = {}
)
public class SegmentsServicePreAction extends Action {

	@Override
	public void run(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws ActionException {

		try {
			doRun(httpServletRequest, httpServletResponse);
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		SegmentsServiceConfiguration segmentsServiceConfiguration =
			ConfigurableUtil.createConfigurable(
				SegmentsServiceConfiguration.class, properties);

		if (segmentsServiceConfiguration.segmentationEnabled()) {
			_serviceRegistration = bundleContext.registerService(
				LifecycleAction.class, this,
				MapUtil.singletonDictionary(
					"key", "servlet.service.events.pre"));
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	protected void doRun(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isLifecycleRender()) {
			return;
		}

		Layout layout = themeDisplay.getLayout();

		if ((layout == null) || layout.isTypeControlPanel()) {
			return;
		}

		long[] segmentsEntryIds = _getSegmentsEntryIds(
			httpServletRequest, themeDisplay.getScopeGroupId(),
			themeDisplay.getUserId());

		httpServletRequest.setAttribute(
			SegmentsWebKeys.SEGMENTS_ENTRY_IDS, segmentsEntryIds);

		if (!Objects.equals(layout.getType(), LayoutConstants.TYPE_CONTENT)) {
			return;
		}

		httpServletRequest.setAttribute(
			SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS,
			_getSegmentsExperienceIds(
				httpServletRequest, httpServletResponse, layout.getGroupId(),
				segmentsEntryIds,
				_portal.getClassNameId(Layout.class.getName()),
				layout.getPlid()));
	}

	private long[] _getSegmentsEntryIds(
		HttpServletRequest httpServletRequest, long groupId, long userId) {

		long[] segmentsEntryIds = new long[0];

		if ((_segmentsEntrySimulator != null) &&
			_segmentsEntrySimulator.isSimulationActive(userId)) {

			segmentsEntryIds =
				_segmentsEntrySimulator.getSimulatedSegmentsEntryIds(userId);
		}
		else {
			try {
				segmentsEntryIds =
					_segmentsEntryProviderRegistry.getSegmentsEntryIds(
						groupId, User.class.getName(), userId,
						_requestContextMapper.map(httpServletRequest));
			}
			catch (PortalException pe) {
				if (_log.isWarnEnabled()) {
					_log.warn(pe.getMessage());
				}
			}
		}

		return ArrayUtil.append(
			segmentsEntryIds, SegmentsEntryConstants.ID_DEFAULT);
	}

	private long[] _getSegmentsExperienceIds(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, long groupId,
		long[] segmentsEntryIds, long classNameId, long classPK) {

		long[] segmentsExperienceIds = new long[0];

		try {
			segmentsExperienceIds =
				_segmentsExperienceRequestProcessorRegistry.
					getSegmentsExperienceIds(
						httpServletRequest, httpServletResponse, groupId,
						classNameId, classPK, segmentsEntryIds);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}
		}

		return ArrayUtil.append(
			segmentsExperienceIds, SegmentsExperienceConstants.ID_DEFAULT);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsServicePreAction.class);

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private RequestContextMapper _requestContextMapper;

	@Reference
	private SegmentsEntryProviderRegistry _segmentsEntryProviderRegistry;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.portal.kernel.model.User)"
	)
	private volatile SegmentsEntrySimulator _segmentsEntrySimulator;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Reference
	private SegmentsExperienceRequestProcessorRegistry
		_segmentsExperienceRequestProcessorRegistry;

	private ServiceRegistration<LifecycleAction> _serviceRegistration;

}