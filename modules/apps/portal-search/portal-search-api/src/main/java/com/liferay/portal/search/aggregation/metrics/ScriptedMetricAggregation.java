/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.aggregation.metrics;

import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.script.Script;

import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface ScriptedMetricAggregation extends Aggregation {

	public Script getCombineScript();

	public Script getInitScript();

	public Script getMapScript();

	public Map<String, Object> getParameters();

	public Script getReduceScript();

	public void putParameter(String paramName, Object paramValue);

	public void setCombineScript(Script combineScript);

	public void setInitScript(Script initScript);

	public void setMapScript(Script mapScript);

	public void setParameters(Map<String, Object> parameters);

	public void setReduceScript(Script reduceScript);

}