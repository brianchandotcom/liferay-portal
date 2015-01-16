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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.ratings.service.RatingsEntryLocalServiceUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
public class RatingsDataTransformerUtil {

	public static void transformCompanyRatingsData(
			final long companyId, PortletPreferences oldPortletPreferences,
			UnicodeProperties properties)
		throws Exception {

		if (_ratingsDataTransformer == null) {
			return;
		}

		for (String portletId : PortletRatingsDefinitionUtil.getPortletIds()) {

			String[] classNames = PortletRatingsDefinitionUtil.getClassNames(
				portletId);

			for (final String className : classNames) {
				String propertyName = _getPropertyName(className);

				transformRatingsData(
					"companyId", companyId, className,
					oldPortletPreferences.getValue(
						propertyName, StringPool.BLANK),
					properties.getProperty(propertyName));
			}
		}
	}

	public static void transformGroupRatingsData(
			final long groupId, UnicodeProperties oldProperties,
			UnicodeProperties properties)
		throws Exception {

		if (_ratingsDataTransformer == null) {
			return;
		}

		for (String portletId : PortletRatingsDefinitionUtil.getPortletIds()) {

			String[] classNames = PortletRatingsDefinitionUtil.getClassNames(
				portletId);

			for (final String className : classNames) {
				String propertyName = _getPropertyName(className);

				transformRatingsData(
					"groupId", groupId, className,
					oldProperties.getProperty(propertyName),
					properties.getProperty(propertyName));
			}
		}
	}

	protected static void transformRatingsData(
			final String classPKFieldName, final long classPKFieldValue,
			final String className, String fromRatingsType,
			String toRatingsType)
		throws PortalException {

		if (Validator.isNull(fromRatingsType) ||
			Validator.isNull(toRatingsType) ||
			fromRatingsType.equals(toRatingsType)) {

			return;
		}

		ActionableDynamicQuery.PerformActionMethod performActionMethod =
			_ratingsDataTransformer.transformRatingsData(
				fromRatingsType, toRatingsType);

		if (performActionMethod == null) {
			return;
		}

		ActionableDynamicQuery ratingsEntryActionableDynamicQuery =
			RatingsEntryLocalServiceUtil.getActionableDynamicQuery();

		ratingsEntryActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property property = PropertyFactoryUtil.forName(
						classPKFieldName);

					dynamicQuery.add(property.eq(classPKFieldValue));

					property = PropertyFactoryUtil.forName("className");

					dynamicQuery.add(property.eq(className));
				}

			});

		ratingsEntryActionableDynamicQuery.setPerformActionMethod(
			performActionMethod);

		ratingsEntryActionableDynamicQuery.performActions();
	}

	private static String _getPropertyName(String className) {
		return className + StringPool.UNDERLINE + "RatingsType";
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