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

package com.liferay.frontend.data.set.internal.view.table.selectable;

import com.liferay.frontend.data.set.constants.FrontendDataSetConstants;
import com.liferay.frontend.data.set.view.FrontendDataSetView;
import com.liferay.frontend.data.set.view.FrontendDataSetViewContextContributor;
import com.liferay.frontend.data.set.view.table.selectable.BaseSelectableTableFrontendDataSetView;
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
	property = "frontend.data.set.view.name=" + FrontendDataSetConstants.SELECTABLE_TABLE,
	service = FrontendDataSetViewContextContributor.class
)
public class SelectableTableFrontendDataSetViewContextContributor
	implements FrontendDataSetViewContextContributor {

	@Override
	public Map<String, Object> getFrontendDataSetViewContext(
		FrontendDataSetView frontendDataSetView, Locale locale) {

		if (frontendDataSetView instanceof
				BaseSelectableTableFrontendDataSetView) {

			return _serialize(
				(BaseSelectableTableFrontendDataSetView)frontendDataSetView,
				locale);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		BaseSelectableTableFrontendDataSetView
			baseSelectableTableFrontendDataSetView,
		Locale locale) {

		return HashMapBuilder.<String, Object>put(
			"schema",
			JSONUtil.put(
				"firstColumnLabel",
				baseSelectableTableFrontendDataSetView.getFirstColumnLabel(
					locale)
			).put(
				"firstColumnName",
				baseSelectableTableFrontendDataSetView.getFirstColumnName()
			)
		).build();
	}

}