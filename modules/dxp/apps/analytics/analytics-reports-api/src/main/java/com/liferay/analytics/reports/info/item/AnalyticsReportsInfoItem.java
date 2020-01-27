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

package com.liferay.analytics.reports.info.item;

import java.util.Date;
import java.util.Locale;

/**
 * @author David Arques
 */
public interface AnalyticsReportsInfoItem<T> {

	public String getAuthorName(T model);

	public default String getKey() {
		Class<?> clazz = getClass();

		return clazz.getName();
	}

	public Date getPublishDate(T model);

	public String getTitle(T model, Locale locale);

}