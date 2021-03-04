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

import com.liferay.info.type.WebImage;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author David Arques
 */
public interface AnalyticsReportsInfoItem<T> {

	public default List<Action> getActions() {
		return Arrays.asList(
			Action.HISTORICAL_VIEWS, Action.TOTAL_VIEWS,
			Action.TRAFFIC_CHANNELS);
	}

	public String getAuthorName(T model);

	public default long getAuthorUserId(T model) {
		return 0L;
	}

	public default WebImage getAuthorWebImage(T model, Locale locale) {
		return null;
	}

	public default List<Locale> getAvailableLocales(T model) {
		return Collections.singletonList(LocaleUtil.getDefault());
	}

	public default String getCanonicalURL(T model, Locale locale) {
		return null;
	}

	public default Locale getDefaultLocale(T model) {
		return LocaleUtil.getDefault();
	}

	public Date getPublishDate(T model);

	public String getTitle(T model, Locale locale);

	public default boolean isShow(T model) {
		return false;
	}

	public enum Action {

		HISTORICAL_READS, HISTORICAL_VIEWS, TOTAL_READS, TOTAL_VIEWS,
		TRAFFIC_CHANNELS,

	}

}