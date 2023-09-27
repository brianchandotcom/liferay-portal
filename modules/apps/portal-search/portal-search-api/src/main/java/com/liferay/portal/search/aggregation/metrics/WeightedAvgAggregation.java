/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.aggregation.metrics;

import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.ValueType;
import com.liferay.portal.search.script.Script;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface WeightedAvgAggregation extends Aggregation {

	public String getFormat();

	public String getValueField();

	public Object getValueMissing();

	public Script getValueScript();

	public ValueType getValueType();

	public String getWeightField();

	public Object getWeightMissing();

	public Script getWeightScript();

	public void setFormat(String format);

	public void setValueMissing(Object valueMissing);

	public void setValueScript(Script valueScript);

	public void setValueType(ValueType valueType);

	public void setWeightMissing(Object weightMissing);

	public void setWeightScript(Script weightScript);

}