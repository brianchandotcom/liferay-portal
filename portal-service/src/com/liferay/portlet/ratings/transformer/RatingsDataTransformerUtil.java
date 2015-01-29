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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.ratings.service.RatingsEntryLocalServiceUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import javax.portlet.PortletPreferences;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
public class RatingsDataTransformerUtil {

	public static String getPropertyKey(String className) {
		return className + StringPool.UNDERLINE + "RatingsType";
	}

	public static void transformCompanyRatingsData(
			final long companyId, PortletPreferences oldPortletPreferences,
			UnicodeProperties properties)
		throws PortalException {

		_instance._transformCompanyRatingsData(
			companyId, oldPortletPreferences, properties);
	}

	public static void transformGroupRatingsData(
			final long groupId, UnicodeProperties oldProperties,
			UnicodeProperties properties)
		throws PortalException {

		_instance._transformGroupRatingsData(
			groupId, oldProperties, properties);
	}

	private RatingsDataTransformerUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(RatingsDataTransformer.class);

		_serviceTracker.open();
	}

	private void _transformCompanyRatingsData(
			final long companyId, PortletPreferences oldPortletPreferences,
			UnicodeProperties properties)
		throws PortalException {

		RatingsDataTransformer ratingsDataTransformer =
			_serviceTracker.getService();

		if (ratingsDataTransformer == null) {
			return;
		}

		for (String portletId : PortletRatingsDefinitionUtil.getPortletIds()) {
			String[] classNames = PortletRatingsDefinitionUtil.getClassNames(
				portletId);

			for (final String className : classNames) {
				String propertyKey = getPropertyKey(className);

				_transformRatingsData(
					"companyId", companyId, className,
					oldPortletPreferences.getValue(
						propertyKey, StringPool.BLANK),
					properties.getProperty(propertyKey));
			}
		}
	}

	private void _transformGroupRatingsData(
			final long groupId, UnicodeProperties oldProperties,
			UnicodeProperties properties)
		throws PortalException {

		RatingsDataTransformer ratingsDataTransformer =
			_serviceTracker.getService();

		if (ratingsDataTransformer == null) {
			return;
		}

		for (String portletId : PortletRatingsDefinitionUtil.getPortletIds()) {
			String[] classNames = PortletRatingsDefinitionUtil.getClassNames(
				portletId);

			for (final String className : classNames) {
				String propertyKey = getPropertyKey(className);

				_transformRatingsData(
					"groupId", groupId, className,
					oldProperties.getProperty(propertyKey),
					properties.getProperty(propertyKey));
			}
		}
	}

	private void _transformRatingsData(
			final String classPKFieldName, final long classPKFieldValue,
			final String className, String fromRatingsType,
			String toRatingsType)
		throws PortalException {

		if (Validator.isNull(fromRatingsType) ||
			Validator.isNull(toRatingsType) ||
			fromRatingsType.equals(toRatingsType)) {

			return;
		}

		RatingsDataTransformer ratingsDataTransformer =
			_serviceTracker.getService();

		ActionableDynamicQuery.PerformActionMethod performActionMethod =
			ratingsDataTransformer.transformRatingsData(
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

	private static final RatingsDataTransformerUtil _instance =
		new RatingsDataTransformerUtil();

	private final ServiceTracker<RatingsDataTransformer, RatingsDataTransformer>
		_serviceTracker;

}