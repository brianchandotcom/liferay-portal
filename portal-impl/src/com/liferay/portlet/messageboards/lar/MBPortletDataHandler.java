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
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
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
import com.liferay.portlet.messageboards.service.persistence.MBBanActionableDynamicQuery;
import com.liferay.portlet.messageboards.service.persistence.MBCategoryActionableDynamicQuery;
import com.liferay.portlet.messageboards.service.persistence.MBMessageActionableDynamicQuery;
import com.liferay.portlet.messageboards.service.persistence.MBThreadFlagActionableDynamicQuery;

import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Daniel Kocsis
 */
public class MBPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "message_board";

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
			"com.liferay.portlet.messageboards",
			portletDataContext.getScopeGroupId());

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		ActionableDynamicQuery categoriesActionableDynamicQuery =
			new MBCategoryActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				portletDataContext.addDateRangeCriteria(
					dynamicQuery, "modifiedDate");
			}

			@Override
			protected void performAction(Object object) throws PortalException {
				MBCategory category = (MBCategory)object;

				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, category);
			}

		};

		categoriesActionableDynamicQuery.setGroupId(
			portletDataContext.getScopeGroupId());

		categoriesActionableDynamicQuery.performActions();

		ActionableDynamicQuery messagesActionableDynamicQuery =
			new MBMessageActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				portletDataContext.addDateRangeCriteria(
					dynamicQuery, "modifiedDate");
			}

			@Override
			protected void performAction(Object object) throws PortalException {
				MBMessage message = (MBMessage)object;

				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, message);
			}

		};

		messagesActionableDynamicQuery.setGroupId(
			portletDataContext.getScopeGroupId());

		messagesActionableDynamicQuery.performActions();

		if (portletDataContext.getBooleanParameter(
				MBPortletDataHandler.NAMESPACE, "thread-flags")) {

			ActionableDynamicQuery threadFlagsActionableDynamicQuery =
				new MBThreadFlagActionableDynamicQuery() {

				@Override
				protected void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(
						dynamicQuery, "modifiedDate");
				}

				@Override
				protected void performAction(Object object)
					throws PortalException {

					MBThreadFlag message = (MBThreadFlag)object;

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, message);
				}

			};

			threadFlagsActionableDynamicQuery.setGroupId(
				portletDataContext.getScopeGroupId());

			threadFlagsActionableDynamicQuery.performActions();
		}

		if (!portletDataContext.getBooleanParameter(NAMESPACE, "user-bans")) {
			return getExportDataRootElementString(rootElement);
		}

		ActionableDynamicQuery userBansActionableDynamicQuery =
			new MBBanActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				portletDataContext.addDateRangeCriteria(
					dynamicQuery, "modifiedDate");
			}

			@Override
			protected void performAction(Object object) throws PortalException {
				MBBan userBan = (MBBan)object;

				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, userBan);
			}

		};

		userBansActionableDynamicQuery.setGroupId(
			portletDataContext.getScopeGroupId());

		userBansActionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			"com.liferay.portlet.messageboards",
			portletDataContext.getSourceGroupId(),
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

}