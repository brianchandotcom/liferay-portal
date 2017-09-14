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

package com.liferay.chart.taglib.servlet.taglib.soy;

import com.liferay.chart.taglib.servlet.taglib.soy.base.BaseChartTag;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

/**
 * @author Chema Balsas
 */
public class LineChartTag extends BaseChartTag {

	@Override
	public int doStartTag() {
        Map<String, Object> context = getContext();
		
        setTemplateNamespace("LineChart.render");

		return super.doStartTag();
	}

	@Override
	public String getModule() {
		return "metal-charts@1.0.0-alpha.0/lib/LineChart";
	}
}