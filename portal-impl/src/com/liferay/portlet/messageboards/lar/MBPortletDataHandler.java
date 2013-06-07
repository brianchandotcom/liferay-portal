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
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.MBThreadFlag;
import com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBStatsUserLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.service.permission.MBPermission;
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
		setDeletionEventClassNames(
			MBBan.class.getName(), MBCategory.class.getName(),
			MBMessage.class.getName(), MBThread.class.getName(),
			MBThreadFlag.class.getName());
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "messages", true, false, null,
				MBMessage.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "thread-flags", true, false, null,
				MBThreadFlag.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "user-bans", true, false, null,
				MBBan.class.getName()));
		setImportControls(getExportControls());
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
			MBPermission.RESOURCE_NAME, portletDataContext.getScopeGroupId());

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		if (portletDataContext.getBooleanParameter(NAMESPACE, "messages")) {
			ActionableDynamicQuery categoryActionableDynamicQuery =
				new MBCategoryExportActionableDynamicQuery(portletDataContext);

			categoryActionableDynamicQuery.performActions();

			ActionableDynamicQuery messageActionableDynamicQuery =
				new MBMessageExportActionableDynamicQuery(portletDataContext);

			messageActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "thread-flags")) {
			ActionableDynamicQuery threadFlagActionableDynamicQuery =
				new MBThreadFlagExportActionableDynamicQuery(
					portletDataContext);

			threadFlagActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "user-bans")) {
			ActionableDynamicQuery banActionableDynamicQuery =
				new MBBanExportActionableDynamicQuery(portletDataContext);

			banActionableDynamicQuery.performActions();
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			MBPermission.RESOURCE_NAME, portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		if (portletDataContext.getBooleanParameter(NAMESPACE, "messages")) {
			Element categoriesElement =
				portletDataContext.getImportDataGroupElement(MBCategory.class);

			List<Element> categoryElements = categoriesElement.elements();

			for (Element categoryElement : categoryElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, categoryElement);
			}

			Element messagesElement =
				portletDataContext.getImportDataGroupElement(MBMessage.class);

			List<Element> messageElements = messagesElement.elements();

			for (Element messageElement : messageElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, messageElement);
			}
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

		ActionableDynamicQuery banActionableDynamicQuery =
			new MBBanExportActionableDynamicQuery(portletDataContext);

		banActionableDynamicQuery.performCount();

		ActionableDynamicQuery categoryActionableDynamicQuery =
			new MBCategoryExportActionableDynamicQuery(portletDataContext);

		categoryActionableDynamicQuery.performCount();

		ActionableDynamicQuery messageActionableDynamicQuery =
			new MBMessageExportActionableDynamicQuery(portletDataContext);

		messageActionableDynamicQuery.performCount();

		ActionableDynamicQuery threadFlagActionableDynamicQuery =
			new MBThreadFlagExportActionableDynamicQuery(portletDataContext);

		threadFlagActionableDynamicQuery.performCount();
	}

}