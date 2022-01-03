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
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.SegmentsEntryRetriever;
import com.liferay.segments.configuration.SegmentsConfiguration;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsWebKeys;
import com.liferay.segments.context.RequestContextMapper;
import com.liferay.segments.processor.SegmentsExperienceRequestProcessorRegistry;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	configurationPid = "com.liferay.segments.configuration.SegmentsConfiguration",
	service = {}
)
public class SegmentsServicePreAction extends Action {

	@Override
	public void run(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws ActionException {

		try {
			_doRun(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new ActionException(exception);
		}
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		SegmentsConfiguration segmentsConfiguration =
			ConfigurableUtil.createConfigurable(
				SegmentsConfiguration.class, properties);

		if (segmentsConfiguration.segmentationEnabled()) {
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

	private void _doRun(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isLifecycleRender()) {
			return;
		}

		Layout layout = themeDisplay.getLayout();

		if ((layout == null) || !layout.isTypeContent() ||
			layout.isTypeControlPanel()) {

			return;
		}

		httpServletRequest.setAttribute(
			SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS,
			_getSegmentsExperienceIds(
				httpServletRequest, httpServletResponse, layout.getGroupId(),
				themeDisplay.getUserId(),
				_portal.getClassNameId(Layout.class.getName()),
				layout.getPlid()));
	}

	private long[] _getSegmentsExperienceIds(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, long groupId, long userId,
		long classNameId, long classPK) {

		try {
			long[] segmentsExperienceIds =
				_segmentsExperienceRequestProcessorRegistry.
					getSegmentsExperienceIds(
						httpServletRequest, httpServletResponse, groupId,
						classNameId, classPK);

			if (segmentsExperienceIds.length > 0) {
				long[] segmentsEntryIds =
					_segmentsEntryRetriever.getSegmentsEntryIds(
						groupId, userId,
						_requestContextMapper.map(httpServletRequest));

				return ArrayUtil.append(
					_segmentsExperienceRequestProcessorRegistry.
						getSegmentsExperienceIds(
							httpServletRequest, httpServletResponse, groupId,
							classNameId, classPK, segmentsEntryIds),
					SegmentsExperienceConstants.ID_DEFAULT);
			}
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}
		}

		return new long[] {SegmentsExperienceConstants.ID_DEFAULT};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsServicePreAction.class);

	@Reference
	private Portal _portal;

	@Reference
	private RequestContextMapper _requestContextMapper;

	@Reference
	private volatile SegmentsEntryRetriever _segmentsEntryRetriever;

	@Reference
	private SegmentsExperienceRequestProcessorRegistry
		_segmentsExperienceRequestProcessorRegistry;

	private ServiceRegistration<LifecycleAction> _serviceRegistration;

}