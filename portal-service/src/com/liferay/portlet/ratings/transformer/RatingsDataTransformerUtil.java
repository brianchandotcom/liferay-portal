/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.ratings.transformer;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletPreferences;

/**
 * @author Roberto Díaz
 */
public class RatingsDataTransformerUtil {

	public static void transformCompanyRatingsData(
			long companyId, PortletPreferences oldPortletPreferences,
			UnicodeProperties properties)
		throws Exception {

		if (_ratingsDataTransformer == null) {
			return;
		}

		for (String portletId :
				RatingsDataTransformerHelperUtil.getPortletIds()) {

			String[] classNames =
				RatingsDataTransformerHelperUtil.getClassNames(portletId);

			for (String className : classNames) {
				String propertyName =
					className + StringPool.UNDERLINE + "RatingsType";

				String fromRatingsType = oldPortletPreferences.getValue(
					propertyName, StringPool.BLANK);
				String toRatingsType = properties.getProperty(propertyName);

				if (Validator.isNotNull(fromRatingsType) &&
					Validator.isNotNull(toRatingsType) &&
					!fromRatingsType.equals(toRatingsType)) {

					classNames = ArrayUtil.append(classNames, className);
				}

				if (ArrayUtil.isNotEmpty(classNames)) {
					_ratingsDataTransformer.transformCompanyRatingsData(
						companyId, fromRatingsType, toRatingsType, classNames);
				}
			}
		}
	}

	public static void transformGroupRatingsData(
			long groupId, UnicodeProperties oldProperties,
			UnicodeProperties properties)
		throws Exception {

		if (_ratingsDataTransformer == null) {
			return;
		}

		for (String portletId :
				RatingsDataTransformerHelperUtil.getPortletIds()) {

			String[] classNames =
				RatingsDataTransformerHelperUtil.getClassNames(portletId);

			for (String className : classNames) {
				String propertyName =
					className + StringPool.UNDERLINE + "RatingsType";

				String fromRatingsType = oldProperties.getProperty(
					propertyName);
				String toRatingsType = properties.getProperty(propertyName);

				if (Validator.isNotNull(fromRatingsType) &&
					Validator.isNotNull(toRatingsType) &&
					!fromRatingsType.equals(toRatingsType)) {

					classNames = ArrayUtil.append(classNames, className);
				}

				if (ArrayUtil.isNotEmpty(classNames)) {
					_ratingsDataTransformer.transformGroupRatingsData(
						groupId, fromRatingsType, toRatingsType, classNames);
				}
			}
		}
	}

	private static RatingsDataTransformer _ratingsDataTransformer;

	static {
		try {
			Class<?> ratingsDataTransformerClassname = Class.forName(
				PropsUtil.get(PropsKeys.RATINGS_DATA_TRANSFORMER));

			_ratingsDataTransformer = (RatingsDataTransformer)
				ratingsDataTransformerClassname.newInstance();
		}
		catch (Exception e) {
		}
	}

}