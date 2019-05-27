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

package com.liferay.headless.common.spi.service.context;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

/**
 * @author Víctor Galán
 */
public class ServiceContextUtil {

	public static ServiceContext createServiceContext(
		Class<?> clazz, long companyId,
		Map<String, Object> expandoBridgeAttributes, long groupId,
		Locale locale, String viewableBy) {

		return createServiceContext(
			new String[0], new Long[0], clazz, companyId,
			expandoBridgeAttributes, groupId, locale, viewableBy);
	}

	public static ServiceContext createServiceContext(
		long groupId, String viewableBy) {

		return createServiceContext(
			new String[0], new Long[0], groupId, viewableBy);
	}

	public static ServiceContext createServiceContext(
		String[] assetTagNames, Long[] assetCategoryIds, Class<?> clazz,
		long companyId, Map<String, Object> expandoBridgeAttributes,
		Long groupId, Locale locale, String viewableBy) {

		return new ServiceContext() {
			{
				if (StringUtil.equalsIgnoreCase(viewableBy, "anyone")) {
					setAddGuestPermissions(true);
					setAddGroupPermissions(true);
				}
				else if (StringUtil.equalsIgnoreCase(viewableBy, "members")) {
					setAddGuestPermissions(false);
					setAddGroupPermissions(true);
				}
				else {
					setAddGuestPermissions(false);
					setAddGroupPermissions(false);
				}

				if (assetCategoryIds != null) {
					setAssetCategoryIds(ArrayUtil.toArray(assetCategoryIds));
				}

				if (assetTagNames != null) {
					setAssetTagNames(assetTagNames);
				}

				if (expandoBridgeAttributes != null) {
					setExpandoBridgeAttributes(
						_toExpandoBridgeAttributes(
							clazz, companyId, (Map)expandoBridgeAttributes,
							locale));
				}

				if (groupId != null) {
					setScopeGroupId(groupId);
				}
			}
		};
	}

	public static ServiceContext createServiceContext(
		String[] assetTagNames, Long[] assetCategoryIds, Long groupId,
		String viewableBy) {

		return createServiceContext(
			assetTagNames, assetCategoryIds, null, 0, null, groupId, null,
			viewableBy);
	}

	private static Map<String, Object> _toExpandoBridgeAttributes(
		Class<?> clazz, long companyId, Map<String, Object> customFields,
		Locale locale) {

		if (customFields == null) {
			return null;
		}

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			companyId, clazz.getName());

		for (Map.Entry<String, Object> entry : customFields.entrySet()) {
			int attributeType = expandoBridge.getAttributeType(entry.getKey());

			if (ExpandoColumnConstants.STRING_LOCALIZED == attributeType) {
				Map map = Collections.singletonMap(locale, entry.getValue());

				customFields.put(entry.getKey(), map);
			}
			else if (ExpandoColumnConstants.DATE == attributeType) {
				DateFormat dateFormat =
					DateFormatFactoryUtil.getSimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss'Z'");

				try {
					customFields.put(
						entry.getKey(),
						dateFormat.parse((String)entry.getValue()));
				}
				catch (ParseException pe) {
					throw new IllegalArgumentException(
						"Unable to parse date from " + entry.getValue(), pe);
				}
			}
		}

		return customFields;
	}

}