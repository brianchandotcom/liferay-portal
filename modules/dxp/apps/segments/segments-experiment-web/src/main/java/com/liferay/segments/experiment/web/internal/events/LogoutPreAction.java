/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.segments.experiment.web.internal.events;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.segments.experiment.web.internal.processor.SegmentsExperimentSegmentsExperienceRequestProcessor;
import com.liferay.segments.experiment.web.internal.product.navigation.control.menu.SegmentsExperimentProductNavigationControlMenuEntry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Yurena Cabrera
 */
@Component(property = "key=logout.events.pre", service = LifecycleAction.class)
public class LogoutPreAction extends Action {

	@Override
	public void run(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws ActionException {

		_segmentsExperimentProductNavigationControlMenuEntry.setPanelState(
			httpServletRequest, "closed");

		_segmentsExperimentSegmentsExperienceRequestProcessor.
			cleanCookieLogoutAction(httpServletRequest, httpServletResponse);
	}

	@Reference
	private SegmentsExperimentProductNavigationControlMenuEntry
		_segmentsExperimentProductNavigationControlMenuEntry;

	@Reference
	private SegmentsExperimentSegmentsExperienceRequestProcessor
		_segmentsExperimentSegmentsExperienceRequestProcessor;

}