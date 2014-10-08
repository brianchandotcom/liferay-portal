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

package com.liferay.portlet.wiki.lar;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.DataLevel;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.service.permission.WikiPermission;
import com.liferay.portlet.wiki.service.persistence.WikiNodeUtil;

import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Marcellus Tavares
 * @author Zsolt Berentey
 */
public class WikiDisplayPortletDataHandler extends WikiPortletDataHandler {

	public WikiDisplayPortletDataHandler() {
		setDataLevel(DataLevel.PORTLET_INSTANCE);
		setDataPortletPreferences("title", "nodeId");
		setExportControls(new PortletDataHandlerControl[0]);
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletPreferences == null) {
			return portletPreferences;
		}

		portletPreferences.setValue("title", StringPool.BLANK);
		portletPreferences.setValue("nodeId", StringPool.BLANK);

		return portletPreferences;
	}

	@Override
	protected PortletPreferences doProcessExportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		long nodeId = GetterUtil.getLong(
			portletPreferences.getValue("nodeId", StringPool.BLANK));

		if (nodeId <= 0) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No node id found in preferences of portlet " + portletId);
			}

			return portletPreferences;
		}

		String title = portletPreferences.getValue("title", null);

		if (title == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No title found in preferences of portlet " + portletId);
			}

			return portletPreferences;
		}

		WikiNode node = WikiNodeUtil.fetchByPrimaryKey(nodeId);

		if (node == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to find wiki node");
			}

			return portletPreferences;
		}

		portletDataContext.addPortletPermissions(WikiPermission.RESOURCE_NAME);

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, portletId, node);

		ActionableDynamicQuery actionableDynamicQuery =
			getPageActionableDynamicQuery(
				portletDataContext, node.getNodeId(), portletId);

		actionableDynamicQuery.performActions();

		return portletPreferences;
	}

	@Override
	protected PortletPreferences doProcessImportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.importPortletPermissions(
			WikiPermission.RESOURCE_NAME);

		StagedModelDataHandlerUtil.importReferenceStagedModels(
			portletDataContext, WikiNode.class);

		StagedModelDataHandlerUtil.importReferenceStagedModels(
			portletDataContext, WikiPage.class);

		long nodeId = GetterUtil.getLong(
			portletPreferences.getValue("nodeId", StringPool.BLANK));

		if (nodeId > 0) {
			Map<Long, Long> nodeIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					WikiNode.class);

			nodeId = MapUtil.getLong(nodeIds, nodeId, nodeId);

			portletPreferences.setValue("nodeId", String.valueOf(nodeId));
		}

		return portletPreferences;
	}

	protected ActionableDynamicQuery getPageActionableDynamicQuery(
		final PortletDataContext portletDataContext, final long nodeId,
		final String portletId) {

		ActionableDynamicQuery actionableDynamicQuery =
			WikiPageLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		final ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			actionableDynamicQuery.getAddCriteriaMethod();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					addCriteriaMethod.addCriteria(dynamicQuery);

					Property property = PropertyFactoryUtil.forName("nodeId");

					dynamicQuery.add(property.eq(nodeId));
				}

			});

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					WikiPage page = (WikiPage)object;

					StagedModelDataHandlerUtil.exportReferenceStagedModel(
						portletDataContext, portletId, page);
				}

			});

		return actionableDynamicQuery;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WikiDisplayPortletDataHandler.class);

}