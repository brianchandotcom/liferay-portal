/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.ratings.util;

import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.service.RatingsEntryLocalServiceUtil;

/**
 * @author Daniel Kocsis
 */
public class RatingsTestUtil {

	public static RatingsEntry addEntry(String className, long classPK)
		throws Exception {

		return addEntry(className, classPK, 1.0d);
	}

	public static RatingsEntry addEntry(
			String className, long classPK, double score)
		throws Exception {

		return RatingsEntryLocalServiceUtil.updateEntry(
			TestPropsValues.getUserId(), className, classPK, score,
			ServiceTestUtil.getServiceContext());
	}

}