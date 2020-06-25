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

package com.liferay.frontend.taglib.clay.internal.data.set.view.table.selectable;

import com.liferay.frontend.taglib.clay.data.set.ClayDataSetContentRendererContextContributor;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.constants.ClayDataSetConstants;
import com.liferay.frontend.taglib.clay.data.set.view.table.selectable.BaseSelectableTableClayDataSetDisplayView;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "clay.data.set.content.renderer.name=" + ClayDataSetConstants.CONTENT_RENDERER_SELECTABLE_TABLE,
	service = ClayDataSetContentRendererContextContributor.class
)
public class SelectableTableClayDataSetContentRendererContextContributor
	implements ClayDataSetContentRendererContextContributor {

	public Map<String, Object> getContentRendererContext(
		ClayDataSetDisplayView clayDataSetDisplayView, Locale locale) {

		if (clayDataSetDisplayView instanceof
				BaseSelectableTableClayDataSetDisplayView) {

			return _serialize(
				(BaseSelectableTableClayDataSetDisplayView)
					clayDataSetDisplayView,
				locale);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		BaseSelectableTableClayDataSetDisplayView
			baseSelectableTableClayDataSetDisplayView,
		Locale locale) {

		return HashMapBuilder.<String, Object>put(
			"schema",
			JSONUtil.put(
				"firstColumnLabel",
				baseSelectableTableClayDataSetDisplayView.getFirstColumnLabel(
					locale)
			).put(
				"firstColumnName",
				baseSelectableTableClayDataSetDisplayView.getFirstColumnName()
			)
		).build();
	}

}