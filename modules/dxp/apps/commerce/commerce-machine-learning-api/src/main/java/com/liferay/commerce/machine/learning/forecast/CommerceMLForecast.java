/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.forecast;

import aQute.bnd.annotation.ProviderType;

import java.util.Date;

/**
 * @author Riccardo Ferrari
 */
@ProviderType
public interface CommerceMLForecast {

	public float getActual();

	public long getCompanyId();

	public float getForecast();

	public long getForecastId();

	public float getForecastLowerBound();

	public float getForecastUpperBound();

	public String getJobId();

	public String getPeriod();

	public String getScope();

	public String getTarget();

	public Date getTimestamp();

	public void setActual(float actual);

	public void setCompanyId(long companyId);

	public void setForecast(float forecast);

	public void setForecastId(long forecastId);

	public void setForecastLowerBound(float forecastLowerBound);

	public void setForecastUpperBound(float forecastUpperBound);

	public void setJobId(String jobId);

	public void setPeriod(String period);

	public void setScope(String scope);

	public void setTarget(String target);

	public void setTimestamp(Date timestamp);

}