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

package com.liferay.message.boards.comment;

import com.liferay.portal.kernel.comment.DiscussionStagingHandler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.StagedModel;
import com.liferay.portlet.exportimport.lar.ExportImportClassedModelUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataException;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class MBDiscussionStagingHandler implements DiscussionStagingHandler {

	@Override
	public <T extends StagedModel> void exportReferenceDiscussions(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException {

		MBDiscussion mbDiscussion =
			MBDiscussionLocalServiceUtil.fetchDiscussion(
				ExportImportClassedModelUtil.getClassName(stagedModel),
				ExportImportClassedModelUtil.getClassPK(stagedModel));

		if (mbDiscussion == null) {
			return;
		}

		List<MBMessage> mbMessages =
			MBMessageLocalServiceUtil.getThreadMessages(
				mbDiscussion.getThreadId(), WorkflowConstants.STATUS_APPROVED);

		if (mbMessages.isEmpty()) {
			return;
		}

		MBMessage firstMBMessage = mbMessages.get(0);

		if ((mbMessages.size() == 1) && firstMBMessage.isRoot()) {
			return;
		}

		for (MBMessage mbMessage : mbMessages) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, stagedModel, mbMessage,
				PortletDataContext.REFERENCE_TYPE_WEAK);
		}
	}

	@Override
	public String getClassName() {
		Class<? extends StagedModel> stagedModelClass = getStagedModelClass();

		return stagedModelClass.getName();
	}

	@Override
	public Class<? extends StagedModel> getStagedModelClass() {
		return MBMessage.class;
	}

	@Override
	public <T extends StagedModel> void importReferenceDiscussions(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException {

		StagedModelDataHandlerUtil.importReferenceStagedModels(
			portletDataContext, stagedModel, MBMessage.class);
	}

}