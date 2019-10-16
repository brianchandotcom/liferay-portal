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

package com.liferay.segments.experiment.web.internal.template;

import com.liferay.petra.string.StringPool;
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
	immediate = true,
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
		catch (PortalException pe) {
			_log.error(pe, pe);

			return;
		}

		String cssClass = GetterUtil.getString(
			contextObjects.get("bodyCssClass"));

		if (_segmentsExperimentProductNavigationControlMenuEntry.
				isPanelStateOpen(httpServletRequest)) {

			contextObjects.put(
				"bodyCssClass",
				cssClass + StringPool.SPACE +
					"lfr-has-segments-experiment-panel open-admin-panel");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsExperimentTemplateContextContributor.class);

	@Reference
	private SegmentsExperimentProductNavigationControlMenuEntry
		_segmentsExperimentProductNavigationControlMenuEntry;

}