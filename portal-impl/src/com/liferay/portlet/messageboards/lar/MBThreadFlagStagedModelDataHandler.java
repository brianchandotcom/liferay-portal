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

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelPathUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.MBThreadFlag;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadFlagLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.service.persistence.MBThreadUtil;

import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class MBThreadFlagStagedModelDataHandler
	extends BaseStagedModelDataHandler<MBThreadFlag> {

	@Override
	public String getClassName() {
		return MBThreadFlag.class.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Element[] elements,
			MBThreadFlag threadFlag)
		throws Exception {

		MBThread thread = MBThreadLocalServiceUtil.getThread(
			threadFlag.getThreadId());

		MBMessage rootMessage = MBMessageLocalServiceUtil.getMessage(
			thread.getRootMessageId());

		if ((rootMessage.getStatus() != WorkflowConstants.STATUS_APPROVED) ||
			(rootMessage.getCategoryId() ==
				MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			return;
		}

		Element categoriesElement = elements[0];
		Element messagesElement = elements[1];

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext,
			new Element[] {categoriesElement, messagesElement}, rootMessage);

		Element threadFlagsElement = elements[2];

		Element threadFlagElement = threadFlagsElement.addElement(
			"thread-flag");

		threadFlagElement.addAttribute(
			"root-message-id", String.valueOf(rootMessage.getMessageId()));

		portletDataContext.addClassedModel(
			threadFlagElement, StagedModelPathUtil.getPath(threadFlag),
			threadFlag, MBPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Element element,
			MBThreadFlag threadFlag)
		throws Exception {

		Map<Long, Long> threadIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MBThread.class);

		long threadId = MapUtil.getLong(
			threadIds, threadFlag.getThreadId(), threadFlag.getThreadId());

		if (threadId == threadFlag.getThreadId()) {
			long rootMessageId = GetterUtil.getLong(
				element.attributeValue("root-message-id"));

			String rootMessagePath = StagedModelPathUtil.getPath(
				portletDataContext, MBMessage.class.getName(), rootMessageId);

			MBMessage rootMessage = (MBMessage)portletDataContext.
				getZipEntryAsObject(rootMessagePath);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, element, rootMessage);

			threadId = MapUtil.getLong(
				threadIds, threadFlag.getThreadId(), threadFlag.getThreadId());
		}

		MBThread thread = MBThreadUtil.fetchByPrimaryKey(threadId);

		if (thread == null) {
			return;
		}

		long userId = portletDataContext.getUserId(threadFlag.getUserUuid());

		ServiceContext serviceContext =
			portletDataContext.createServiceContext(
				element, threadFlag, MBPortletDataHandler.NAMESPACE);

		MBThreadFlagLocalServiceUtil.addThreadFlag(
			userId, thread, serviceContext);
	}

}