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

package com.liferay.portlet.messageboards.lar;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThreadFlag;
import com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBStatsUserLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.service.persistence.MBBanExportActionableDynamicQuery;
import com.liferay.portlet.messageboards.service.persistence.MBCategoryExportActionableDynamicQuery;
import com.liferay.portlet.messageboards.service.persistence.MBMessageExportActionableDynamicQuery;
import com.liferay.portlet.messageboards.service.persistence.MBThreadFlagExportActionableDynamicQuery;

import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Daniel Kocsis
 */
public class MBPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "message_boards";

	public MBPortletDataHandler() {
		setAlwaysExportable(true);
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "categories-and-messages", true, true),
			new PortletDataHandlerBoolean(NAMESPACE, "thread-flags"),
			new PortletDataHandlerBoolean(NAMESPACE, "user-bans"));
		setExportMetadataControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "message-board-messages", true,
				new PortletDataHandlerControl[] {
					new PortletDataHandlerBoolean(NAMESPACE, "attachments"),
					new PortletDataHandlerBoolean(NAMESPACE, "ratings"),
					new PortletDataHandlerBoolean(NAMESPACE, "tags")
				}));
		setPublishToLiveByDefault(
			PropsValues.MESSAGE_BOARDS_PUBLISH_TO_LIVE_BY_DEFAULT);
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				MBPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		MBBanLocalServiceUtil.deleteBansByGroupId(
			portletDataContext.getScopeGroupId());

		MBCategoryLocalServiceUtil.deleteCategories(
			portletDataContext.getScopeGroupId());

		MBStatsUserLocalServiceUtil.deleteStatsUsersByGroupId(
			portletDataContext.getScopeGroupId());

		MBThreadLocalServiceUtil.deleteThreads(
			portletDataContext.getScopeGroupId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			RESOURCE_NAME, portletDataContext.getScopeGroupId());

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		ActionableDynamicQuery categoriesActionableDynamicQuery =
			new MBCategoryExportActionableDynamicQuery(portletDataContext);

		categoriesActionableDynamicQuery.performActions();

		ActionableDynamicQuery messagesActionableDynamicQuery =
			new MBMessageExportActionableDynamicQuery(portletDataContext);

		messagesActionableDynamicQuery.performActions();

		if (portletDataContext.getBooleanParameter(NAMESPACE, "thread-flags")) {
			ActionableDynamicQuery threadFlagsActionableDynamicQuery =
				new MBThreadFlagExportActionableDynamicQuery(
					portletDataContext);

			threadFlagsActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "user-bans")) {
			ActionableDynamicQuery userBansActionableDynamicQuery =
				new MBBanExportActionableDynamicQuery(portletDataContext);

			userBansActionableDynamicQuery.performActions();
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			RESOURCE_NAME, portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Element categoriesElement =
			portletDataContext.getImportDataGroupElement(MBCategory.class);

		List<Element> categoryElements = categoriesElement.elements();

		for (Element categoryElement : categoryElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, categoryElement);
		}

		Element messagesElement = portletDataContext.getImportDataGroupElement(
			MBMessage.class);

		List<Element> messageElements = messagesElement.elements();

		for (Element messageElement : messageElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, messageElement);
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "thread-flags")) {
			Element threadFlagsElement =
				portletDataContext.getImportDataGroupElement(
					MBThreadFlag.class);

			List<Element> threadFlagElements = threadFlagsElement.elements();

			for (Element threadFlagElement : threadFlagElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, threadFlagElement);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "user-bans")) {
			Element userBansElement =
				portletDataContext.getImportDataGroupElement(MBBan.class);

			List<Element> userBanElements = userBansElement.elements();

			for (Element userBanElement : userBanElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, userBanElement);
			}
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext)
		throws Exception {

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		ActionableDynamicQuery categoriesActionableDynamicQuery =
			new MBCategoryExportActionableDynamicQuery(portletDataContext);

		manifestSummary.addModelCount(
			MBCategory.class, categoriesActionableDynamicQuery.performCount());

		ActionableDynamicQuery messagesActionableDynamicQuery =
			new MBMessageExportActionableDynamicQuery(portletDataContext);

		manifestSummary.addModelCount(
			MBMessage.class, messagesActionableDynamicQuery.performCount());

		if (portletDataContext.getBooleanParameter(NAMESPACE, "thread-flags")) {
			ActionableDynamicQuery threadFlagsActionableDynamicQuery =
				new MBThreadFlagExportActionableDynamicQuery(
					portletDataContext);

			manifestSummary.addModelCount(
				MBThreadFlag.class,
				threadFlagsActionableDynamicQuery.performCount());
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "user-bans")) {
			ActionableDynamicQuery userBansActionableDynamicQuery =
				new MBBanExportActionableDynamicQuery(portletDataContext);

			manifestSummary.addModelCount(
				MBBan.class, userBansActionableDynamicQuery.performCount());
		}
	}

	protected static final String RESOURCE_NAME =
		"com.liferay.portlet.messageboards";

}