/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.view.timeline;

import com.liferay.frontend.data.set.constants.FDSConstants;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.FDSViewContextContributor;
import com.liferay.frontend.data.set.view.timeline.BaseTimelineFDSView;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	property = "frontend.data.set.view.name=" + FDSConstants.TIMELINE,
	service = FDSViewContextContributor.class
)
public class TimelineFDSViewContextContributor
	implements FDSViewContextContributor {

	@Override
	public Map<String, Object> getFDSViewContext(
		FDSView fdsView, Locale locale) {

		if (fdsView instanceof BaseTimelineFDSView) {
			return _serialize((BaseTimelineFDSView)fdsView);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		BaseTimelineFDSView baseTimelineFDSView) {

		return HashMapBuilder.<String, Object>put(
			"schema",
			JSONUtil.put(
				"date", baseTimelineFDSView.getDate()
			).put(
				"description", baseTimelineFDSView.getDescription()
			).put(
				"title", baseTimelineFDSView.getTitle()
			)
		).build();
	}

}