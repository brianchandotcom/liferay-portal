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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutRevisionLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.persistence.PortletPreferencesActionableDynamicQuery;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.assetpublisher.util.AssetPublisher;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.portlet.ReadOnlyException;

/**
 * @author Andrew Betts
 * @author Christopher Kian
 */
public class VerifyPortletPreferences extends VerifyProcess {

	public static void cleanUpLayoutRevisionPortletPreferences()
		throws Exception {

		ActionableDynamicQuery actionableDynamicQuery =
			new PortletPreferencesActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				Property plidProperty = PropertyFactoryUtil.forName("plid");

				DynamicQuery layoutRevisionDynamicQuery =
					LayoutRevisionLocalServiceUtil.dynamicQuery();

				layoutRevisionDynamicQuery.setProjection(
					ProjectionFactoryUtil.property("layoutRevisionId"));

				dynamicQuery.add(plidProperty.in(layoutRevisionDynamicQuery));
			}

			@Override
			protected void performAction(Object object)
				throws PortalException, SystemException {

				PortletPreferences portletPreferences =
					(PortletPreferences)object;

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

				List<String> portletIds = new ArrayList<String>(
					portlets.size());

				for (Portlet portlet : portlets) {
					portletIds.add(Portlet.PORTLET_ID_ACCESSOR.get(portlet));
				}

				if (portletIds.contains(portletPreferences.getPortletId())) {
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

		};

		actionableDynamicQuery.performActions();
	}

	public static void cleanUpScopeIdPortletPreferences() throws Exception {
		final long classNameId = ClassNameLocalServiceUtil.getClassNameId(
			JournalArticle.class.getName());

		ActionableDynamicQuery actionableDynamicQuery =
			new PortletPreferencesActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				Property plidProperty = PropertyFactoryUtil.forName("plid");

				DynamicQuery layoutDynamicQuery =
					LayoutLocalServiceUtil.dynamicQuery();

				layoutDynamicQuery.setProjection(
					ProjectionFactoryUtil.property("plid"));

				dynamicQuery.add(plidProperty.in(layoutDynamicQuery));
			}

			@Override
			protected void performAction(Object object)
				throws PortalException, SystemException {

				PortletPreferences portletPreferences =
					(PortletPreferences)object;

				long plid = portletPreferences.getPlid();

				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				if (!layout.isTypePortlet()) {
					return;
				}

				javax.portlet.PortletPreferences jxPortletPreferences =
					PortletPreferencesFactoryUtil.strictFromXML(
						layout.getCompanyId(), portletPreferences.getOwnerId(),
						portletPreferences.getOwnerType(), plid,
						portletPreferences.getPortletId(),
						portletPreferences.getPreferences());

				String[] scopeIds = jxPortletPreferences.getValues(
					"scopeIds", null);

				String preferenceName =
					"classTypeIdsJournalArticleAssetRendererFactory";

				String[] classTypeIds = jxPortletPreferences.getValues(
					preferenceName, null);

				try {
					if (ArrayUtil.isNotEmpty(classTypeIds) ||
						ArrayUtil.isNotEmpty(scopeIds)) {

						if (ArrayUtil.isEmpty(scopeIds)) {
							scopeIds = new String[] {
								AssetPublisher.SCOPE_ID_GROUP_PREFIX +
									GroupConstants.DEFAULT
							};

							jxPortletPreferences.setValue(
								"scopeIds", scopeIds[0]);
						}

						long[] groupIds = getGroupIds(
							scopeIds, layout.getGroupId());

						List<DDMStructure> structures =
							DDMStructureLocalServiceUtil.getStructures(
								groupIds, classNameId);

						long[] structureIds = new long[structures.size()];

						for (DDMStructure structure : structures) {
							structureIds = ArrayUtil.append(
								structureIds, structure.getStructureId());
						}

						if (ArrayUtil.isNotEmpty(structureIds)) {
							String structureIdsString = StringUtil.strip(
								Arrays.toString(structureIds),
								new char[] {
									CharPool.CLOSE_BRACKET,
									CharPool.OPEN_BRACKET, CharPool.SPACE
								});

							jxPortletPreferences.setValue(
								preferenceName, structureIdsString);
						}
						else {
							jxPortletPreferences.reset(preferenceName);
						}
					}
					else {
						jxPortletPreferences.reset(preferenceName);
					}
				}
				catch (ReadOnlyException roe) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to update portlet preferences " +
								portletPreferences.getPortletPreferencesId());
					}

					return;
				}

				if (_log.isInfoEnabled()) {
					_log.info(
						"Updating portlet preferences " +
							portletPreferences.getPortletPreferencesId());
				}

				PortletPreferencesLocalServiceUtil.updatePreferences(
					portletPreferences.getOwnerId(),
					portletPreferences.getOwnerType(), plid,
					portletPreferences.getPortletId(), jxPortletPreferences);
			}

		};

		actionableDynamicQuery.performActions();
	}

	private static long[] getGroupIds(String[] scopeIds, long defaultGroupId) {
		long[] groupIds = new long[0];

		for (String scopeId : scopeIds) {
			if (!scopeId.startsWith(AssetPublisher.SCOPE_ID_GROUP_PREFIX)) {
				continue;
			}

			long siteGroupId = 0;

			String scopeIdSuffix = scopeId.substring(
				AssetPublisher.SCOPE_ID_GROUP_PREFIX.length());

			if (scopeIdSuffix.equals(GroupConstants.DEFAULT)) {
				siteGroupId = defaultGroupId;
			}
			else {
				siteGroupId = Long.valueOf(scopeIdSuffix);
			}

			groupIds = ArrayUtil.append(groupIds, siteGroupId);
		}

		return groupIds;
	}

	@Override
	protected void doVerify() throws Exception {
		cleanUpLayoutRevisionPortletPreferences();
		cleanUpScopeIdPortletPreferences();
	}

	private static Log _log = LogFactoryUtil.getLog(
		VerifyPortletPreferences.class);

}