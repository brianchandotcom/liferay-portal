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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.taglib.util.OutputData;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.net.URL;

import java.util.Map;

import javax.servlet.ServletRequest;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Chema Balsas
 */
public abstract class BaseChartTag extends TemplateRendererTag {

	public BaseChartTag(String moduleBaseName) {
		_moduleBaseName = moduleBaseName;
	}

	@Override
	public int doStartTag() {
		setTemplateNamespace("ClayChart.render");

		_outputStylesheetLink();
		_outputTilesSVG();

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

	public void setConfig(ChartConfig chartConfig) {
		for (Map.Entry<String, Object> entry : chartConfig.entrySet()) {
			putValue(entry.getKey(), entry.getValue());
		}
	}

	public void setId(String id) {
		putValue("id", id);
	}

	private OutputData _getOutputData() {
		ServletRequest servletRequest = getRequest();

		OutputData outputData = (OutputData)servletRequest.getAttribute(
			WebKeys.OUTPUT_DATA);

		if (outputData == null) {
			outputData = new OutputData();

			servletRequest.setAttribute(WebKeys.OUTPUT_DATA, outputData);
		}

		return outputData;
	}

	private void _outputStylesheetLink() {
		OutputData outputData = _getOutputData();

		NPMResolver npmResolver = NPMResolverProvider.getNPMResolver();

		if (npmResolver == null) {
			return;
		}

		String cssPath = npmResolver.resolveModuleName(
			"clay-charts/lib/css/main.css");

		StringBundler sb = new StringBundler(5);

		sb.append("<link href=\"");
		sb.append(PortalUtil.getPathModule());
		sb.append("/frontend-taglib-chart/node_modules/");
		sb.append(cssPath);
		sb.append("\" rel=\"stylesheet\">");

		outputData.setData(_OUTPUT_CSS_KEY, WebKeys.PAGE_TOP, sb);
	}

	private void _outputTilesSVG() {
		OutputData outputData = _getOutputData();

		NPMResolver npmResolver = NPMResolverProvider.getNPMResolver();

		if (npmResolver == null) {
			return;
		}

		String svgPath = npmResolver.resolveModuleName(
			"clay-charts/lib/svg/tiles.svg");

		Bundle bundle = FrameworkUtil.getBundle(BaseChartTag.class);

		URL url = bundle.getEntry("META-INF/resources/node_modules/" + svgPath);

		try {
			String svg = StringUtil.read(url.openStream());

			outputData.setData(
				_OUTPUT_SVG_KEY, WebKeys.PAGE_BODY_BOTTOM,
				new StringBundler(svg));
		}
		catch (IOException ioe) {
		}
	}

	private static final String _OUTPUT_CSS_KEY =
		BaseChartTag.class.getName() + "_CSS";

	private static final String _OUTPUT_SVG_KEY =
		BaseChartTag.class.getName() + "_SVG";

	private final String _moduleBaseName;

}