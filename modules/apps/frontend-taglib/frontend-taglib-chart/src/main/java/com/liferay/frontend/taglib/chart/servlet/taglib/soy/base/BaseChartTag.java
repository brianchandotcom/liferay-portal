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

package com.liferay.frontend.taglib.chart.servlet.taglib.soy.base;

import com.liferay.frontend.taglib.chart.model.ChartConfig;
import com.liferay.frontend.taglib.soy.servlet.taglib.TemplateRendererTag;
import com.liferay.petra.string.StringPool;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Chema Balsas
 */
public abstract class BaseChartTag extends TemplateRendererTag {

	public BaseChartTag(String moduleBaseName, String templateNamespace) {
		_moduleBaseName = moduleBaseName;
		_templateNamespace = templateNamespace;

		setDependencies(Collections.emptySet());
	}

	@Override
	public int doStartTag() {
		if (_templateNamespace != null) {
			setTemplateNamespace(_templateNamespace);
		}
		else {
			setTemplateNamespace("ClayChart.render");
		}

		return super.doStartTag();
	}

	@Override
	public String getModule() {
		return StringPool.OPEN_CURLY_BRACE + _moduleBaseName +
			"} from frontend-taglib-chart/exports/clay-charts.js";
	}

	public void setConfig(ChartConfig<?> chartConfig) {
		for (Map.Entry<String, Object> entry : chartConfig.entrySet()) {
			putValue(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void setDependencies(Set<String> dependencies) {
		dependencies = new HashSet<>(dependencies);

		dependencies.add(
			"from frontend-taglib-chart/exports/clay-charts$lib$css$" +
				"main.css.js");
		dependencies.add(
			"from frontend-taglib-chart/exports/clay-charts$lib$svg$" +
				"tiles.svg.js");

		super.setDependencies(dependencies);
	}

	public void setId(String id) {
		putValue("id", id);
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		setDependencies(Collections.emptySet());
	}

	private final String _moduleBaseName;
	private final String _templateNamespace;

}