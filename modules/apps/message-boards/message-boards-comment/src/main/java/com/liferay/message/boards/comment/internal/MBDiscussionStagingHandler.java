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

package com.liferay.message.boards.comment.internal;

import com.liferay.exportimport.kernel.lar.ExportImportClassedModelUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.message.boards.constants.MBConstants;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBDiscussionLocalServiceUtil;
import com.liferay.message.boards.service.MBMessageLocalServiceUtil;
import com.liferay.portal.kernel.comment.DiscussionStagingHandler;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

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

		Group group = GroupLocalServiceUtil.fetchGroup(
			mbDiscussion.getGroupId());

		if ((group != null) && group.isControlPanel()) {
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
	public ActionableDynamicQuery getCommentExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		final ExportActionableDynamicQuery actionableDynamicQuery =
			MBMessageLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Criterion modifiedDateCriterion =
					portletDataContext.getDateRangeCriteria("modifiedDate");
				Criterion statusDateCriterion =
					portletDataContext.getDateRangeCriteria("statusDate");

				if ((modifiedDateCriterion != null) &&
					(statusDateCriterion != null)) {

					Disjunction disjunction =
						RestrictionsFactoryUtil.disjunction();

					disjunction.add(modifiedDateCriterion);
					disjunction.add(statusDateCriterion);

					dynamicQuery.add(disjunction);
				}

				Property classNameIdProperty = PropertyFactoryUtil.forName(
					"classNameId");

				dynamicQuery.add(classNameIdProperty.gt(0L));

				Property parentMessageIdProperty = PropertyFactoryUtil.forName(
					"parentMessageId");

				dynamicQuery.add(
					parentMessageIdProperty.gt(
						MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID));

				Property statusProperty = PropertyFactoryUtil.forName("status");

				if (portletDataContext.isInitialPublication()) {
					dynamicQuery.add(
						statusProperty.ne(WorkflowConstants.STATUS_IN_TRASH));
				}
				else {
					StagedModelDataHandler<?> stagedModelDataHandler =
						StagedModelDataHandlerRegistryUtil.
							getStagedModelDataHandler(
								MBMessage.class.getName());

					dynamicQuery.add(
						statusProperty.in(
							stagedModelDataHandler.getExportableStatuses()));
				}
			});

		return actionableDynamicQuery;
	}

	@Override
	public String getResourceName() {
		return MBConstants.RESOURCE_NAME;
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