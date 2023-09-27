/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.chart.servlet.taglib.soy;

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
		return StringPool.OPEN_CURLY_BRACE + _moduleBaseName +
			"} from frontend-taglib-chart/exports/clay-charts.js";
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