/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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