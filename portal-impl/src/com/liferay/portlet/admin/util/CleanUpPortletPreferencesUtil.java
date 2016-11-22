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

package com.liferay.portlet.admin.util;

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutStagingHandler;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutRevisionLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.util.List;

/**
 * @author Andrew Betts
 */
public class CleanUpPortletPreferencesUtil {

	public static void cleanUpLayoutRevisionPortletPreferences()
		throws Exception {

		CacheRegistryUtil.setActive(true);

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			ActionableDynamicQuery actionableDynamicQuery =
				getPortletPreferencesActionableDynamicQuery();

			actionableDynamicQuery.setParallel(true);

			actionableDynamicQuery.performActions();
		}
		finally {
			CacheRegistryUtil.setActive(false);
		}
	}

	protected static boolean containsPortlet(Layout layout, String portletId) {
		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		List<Portlet> portlets = layoutTypePortlet.getAllPortlets();

		List<String> portletIds = ListUtil.toList(
			portlets, Portlet.PORTLET_ID_ACCESSOR);

		return portletIds.contains(portletId);
	}

	protected static ActionableDynamicQuery
		getPortletPreferencesActionableDynamicQuery() {

		ActionableDynamicQuery portletPreferencesActionableDynamicQuery =
			PortletPreferencesLocalServiceUtil.getActionableDynamicQuery();

		portletPreferencesActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property plidProperty = PropertyFactoryUtil.forName("plid");

					DynamicQuery layoutRevisionDynamicQuery =
						LayoutRevisionLocalServiceUtil.dynamicQuery();

					layoutRevisionDynamicQuery.setProjection(
						ProjectionFactoryUtil.property("layoutRevisionId"));

					dynamicQuery.add(
						plidProperty.in(layoutRevisionDynamicQuery));
				}

			});
		portletPreferencesActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.
				PerformActionMethod<PortletPreferences>() {

				@Override
				public void performAction(PortletPreferences portletPreferences)
					throws PortalException {

					long layoutRevisionId = portletPreferences.getPlid();

					LayoutRevision layoutRevision =
						LayoutRevisionLocalServiceUtil.getLayoutRevision(
							layoutRevisionId);

					Layout layout = LayoutLocalServiceUtil.getLayout(
						layoutRevision.getPlid());

					if (!layout.isTypePortlet()) {
						return;
					}

					if (containsPortlet(
							layout, portletPreferences.getPortletId())) {

						return;
					}

					LayoutStagingHandler layoutStagingHandler =
						new LayoutStagingHandler(layout);

					layoutStagingHandler.setLayoutRevision(layoutRevision);

					Layout proxiedLayout = (Layout)ProxyUtil.newProxyInstance(
						PortalClassLoaderUtil.getClassLoader(),
						new Class<?>[] {Layout.class}, layoutStagingHandler);

					if (containsPortlet(
							proxiedLayout, portletPreferences.getPortletId())) {

						return;
					}

					if (_log.isWarnEnabled()) {
						_log.warn(
							"Removing portlet preferences " +
								portletPreferences.getPortletPreferencesId());
					}

					PortletPreferencesLocalServiceUtil.deletePortletPreferences(
						portletPreferences);
				}

			});

		return portletPreferencesActionableDynamicQuery;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CleanUpPortletPreferencesUtil.class);

}