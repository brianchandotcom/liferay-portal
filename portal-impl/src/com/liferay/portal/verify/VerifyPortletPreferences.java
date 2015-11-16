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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutRevisionLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;

import java.util.List;

/**
 * @author Andrew Betts
 */
public class VerifyPortletPreferences extends VerifyProcess {

	public static void cleanUpLayoutRevisionPortletPreferences()
		throws Exception {

		ActionableDynamicQuery actionableDynamicQuery =
			getPortletPreferencesActionableDynamicQuery();

		actionableDynamicQuery.setParallel(true);

		actionableDynamicQuery.performActions();
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

					LayoutTypePortlet layoutTypePortlet =
						(LayoutTypePortlet)layout.getLayoutType();

					List<Portlet> portlets = layoutTypePortlet.getAllPortlets();

					List<String> portletIds = ListUtil.toList(
						portlets, Portlet.PORTLET_ID_ACCESSOR);

					if (portletIds.contains(
							portletPreferences.getPortletId())) {

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

	@Override
	protected void doVerify() throws Exception {
		CacheRegistryUtil.setActive(true);

		cleanUpLayoutRevisionPortletPreferences();

		CacheRegistryUtil.setActive(false);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyPortletPreferences.class);

}