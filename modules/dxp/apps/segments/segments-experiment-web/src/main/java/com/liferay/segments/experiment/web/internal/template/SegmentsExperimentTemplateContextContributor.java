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

package com.liferay.segments.experiment.web.internal.template;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateContextContributor;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.segments.experiment.web.internal.product.navigation.control.menu.SegmentsExperimentProductNavigationControlMenuEntry;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = "type=" + TemplateContextContributor.TYPE_THEME,
	service = TemplateContextContributor.class
)
public class SegmentsExperimentTemplateContextContributor
	implements TemplateContextContributor {

	@Override
	public void prepare(
		Map<String, Object> contextObjects,
		HttpServletRequest httpServletRequest) {

		try {
			if (!_segmentsExperimentProductNavigationControlMenuEntry.isShow(
					httpServletRequest)) {

				return;
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return;
		}

		if (_segmentsExperimentProductNavigationControlMenuEntry.
				isPanelStateOpen(httpServletRequest)) {

			String cssClass = GetterUtil.getString(
				contextObjects.get("bodyCssClass"));

			contextObjects.put(
				"bodyCssClass",
				cssClass +
					" lfr-has-segments-experiment-panel open-admin-panel");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsExperimentTemplateContextContributor.class);

	@Reference
	private SegmentsExperimentProductNavigationControlMenuEntry
		_segmentsExperimentProductNavigationControlMenuEntry;

}