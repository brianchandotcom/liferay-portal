/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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