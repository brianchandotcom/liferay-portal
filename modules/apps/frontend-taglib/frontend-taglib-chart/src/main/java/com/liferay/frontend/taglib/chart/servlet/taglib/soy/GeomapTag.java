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

package com.liferay.frontend.taglib.chart.servlet.taglib.soy;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.taglib.chart.internal.js.loader.modules.extender.npm.NPMResolverProvider;
import com.liferay.frontend.taglib.chart.model.geomap.GeomapConfig;
import com.liferay.frontend.taglib.soy.servlet.taglib.TemplateRendererTag;
import com.liferay.petra.string.StringPool;

import java.util.Map;

/**
 * @author Julien Castelain
 */
public class GeomapTag extends TemplateRendererTag {

	public GeomapTag() {
		_moduleBaseName = "Geomap";
	}

	@Override
	public int doStartTag() {
		setTemplateNamespace("ClayGeomap.render");

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

	public void setConfig(GeomapConfig geomapConfig) {
		for (Map.Entry<String, Object> entry : geomapConfig.entrySet()) {
			putValue(entry.getKey(), entry.getValue());
		}
	}

	public void setId(String id) {
		putValue("id", id);
	}

	private final String _moduleBaseName;

}