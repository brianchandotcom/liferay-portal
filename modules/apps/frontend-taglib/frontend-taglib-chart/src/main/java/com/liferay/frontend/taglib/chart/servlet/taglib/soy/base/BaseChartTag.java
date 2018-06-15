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

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.taglib.chart.internal.js.loader.modules.extender.npm.NPMResolverProvider;
import com.liferay.frontend.taglib.chart.model.ChartConfig;
import com.liferay.frontend.taglib.soy.servlet.taglib.TemplateRendererTag;
import com.liferay.frontend.taglib.util.TagAccessor;
import com.liferay.frontend.taglib.util.TagResourceHandler;
import com.liferay.petra.string.StringPool;

import javax.servlet.jsp.PageContext;
import java.util.Map;

/**
 * @author Chema Balsas
 */
public abstract class BaseChartTag
	extends TemplateRendererTag implements TagAccessor {

	public BaseChartTag(String moduleBaseName, String templateNamespace) {
		_moduleBaseName = moduleBaseName;
		_templateNamespace = templateNamespace;
	}

	@Override
	public int doStartTag() {
		if (_templateNamespace != null) {
			setTemplateNamespace(_templateNamespace);
		}
		else {
			setTemplateNamespace("ClayChart.render");
		}

		_tagResourceHandler.outputNPMStyleSheet("clay-charts/lib/css/main.css");
		_tagResourceHandler.outputNPMResource("clay-charts/lib/svg/tiles.svg");

		return super.doStartTag();
	}

	@Override
	public String getModule() {
		NPMResolver npmResolver = NPMResolverProvider.getNPMResolver();

		if (npmResolver == null) {
			return StringPool.BLANK;
		}

		return npmResolver.resolveModuleName(
			"clay-charts/lib/" + _moduleBaseName);
	}

	@Override
	public PageContext getPageContext() {
		return pageContext;
	}

	public void setConfig(ChartConfig chartConfig) {
		for (Map.Entry<String, Object> entry : chartConfig.entrySet()) {
			putValue(entry.getKey(), entry.getValue());
		}
	}

	public void setId(String id) {
		putValue("id", id);
	}

	private final String _moduleBaseName;
	private final TagResourceHandler _tagResourceHandler =
		new TagResourceHandler(this);
	private final String _templateNamespace;

}