/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.amazonrankings.util;

import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.amazonrankings.model.AmazonRankings;

import java.text.DateFormat;

import java.util.Calendar;

/**
 * @author Brian Wing Shun Chan
 */
public class AmazonRankingsUtil {

	public static String getAmazonAccessKeyId() {
		return PropsUtil.get(PropsKeys.AMAZON_ACCESS_KEY_ID);
	}

	public static String getAmazonAssociateTag() {
		return PropsUtil.get(PropsKeys.AMAZON_ASSOCIATE_TAG);
	}

	public static AmazonRankings getAmazonRankings(String isbn) {
		if (!Validator.isDigit(isbn)) {
			return null;
		}

		WebCacheItem wci = new AmazonRankingsWebCacheItem(isbn);

		return (AmazonRankings)WebCachePoolUtil.get(
			AmazonRankingsUtil.class.getName() + StringPool.PERIOD + isbn, wci);
	}

	public static String getAmazonSecretAccessKey() {
		return PropsUtil.get(PropsKeys.AMAZON_SECRET_ACCESS_KEY);
	}

	public static String getTimestamp() {
		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			_TIMESTAMP);

		dateFormat.setTimeZone(TimeZoneUtil.getDefault());

		Calendar calendar = Calendar.getInstance();

		return dateFormat.format(calendar.getTime());
	}

	private static final String _TIMESTAMP = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

}