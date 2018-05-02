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

package com.liferay.message.boards.internal.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.model.MBThreadFlag;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBThreadFlagLocalService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class MBThreadFlagStagedModelDataHandler
	extends BaseStagedModelDataHandler<MBThreadFlag> {

	public static final String[] CLASS_NAMES = {MBThreadFlag.class.getName()};

	@Override
	public void deleteStagedModel(MBThreadFlag threadFlag) {
		_mbThreadFlagLocalService.deleteThreadFlag(threadFlag);
	}

	@Override
	public void deleteStagedModel(
		String uuid, long groupId, String className, String extraData) {

		MBThreadFlag threadFlag = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (threadFlag != null) {
			deleteStagedModel(threadFlag);
		}
	}

	@Override
	public MBThreadFlag fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _mbThreadFlagLocalService.fetchMBThreadFlagByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<MBThreadFlag> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _mbThreadFlagLocalService.getMBThreadFlagsByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<MBThreadFlag>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, MBThreadFlag threadFlag)
		throws Exception {

		MBThread thread = _mbThreadLocalService.getThread(
			threadFlag.getThreadId());

		MBMessage rootMessage = _mbMessageLocalService.getMessage(
			thread.getRootMessageId());

		if ((rootMessage.getStatus() != WorkflowConstants.STATUS_APPROVED) ||
			(rootMessage.getCategoryId() ==
				MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			return;
		}

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, rootMessage);

		Element threadFlagElement = portletDataContext.getExportDataElement(
			threadFlag);

		threadFlagElement.addAttribute(
			"root-message-id", String.valueOf(rootMessage.getMessageId()));

		portletDataContext.addClassedModel(
			threadFlagElement, ExportImportPathUtil.getModelPath(threadFlag),
			threadFlag);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, MBThreadFlag threadFlag)
		throws Exception {

		User user = _userLocalService.fetchUserByUuidAndCompanyId(
			threadFlag.getUserUuid(), portletDataContext.getCompanyId());

		if (user == null) {
			return;
		}

		Element element = portletDataContext.getImportDataStagedModelElement(
			threadFlag);

		long rootMessageId = GetterUtil.getLong(
			element.attributeValue("root-message-id"));

		String rootMessagePath = ExportImportPathUtil.getModelPath(
			portletDataContext, MBMessage.class.getName(), rootMessageId);

		MBMessage rootMessage =
			(MBMessage)portletDataContext.getZipEntryAsObject(
				element, rootMessagePath);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, rootMessage);

		Map<Long, Long> threadIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MBThread.class);

		long threadId = MapUtil.getLong(
			threadIds, threadFlag.getThreadId(), threadFlag.getThreadId());

		MBThread thread = _mbThreadLocalService.fetchThread(threadId);

		if (thread == null) {
			return;
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			threadFlag);

		serviceContext.setUuid(threadFlag.getUuid());

		_mbThreadFlagLocalService.addThreadFlag(
			user.getUserId(), thread, serviceContext);
	}

	@Reference(unbind = "-")
	protected void setMBMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {

		_mbMessageLocalService = mbMessageLocalService;
	}

	@Reference(unbind = "-")
	protected void setMBThreadFlagLocalService(
		MBThreadFlagLocalService mbThreadFlagLocalService) {

		_mbThreadFlagLocalService = mbThreadFlagLocalService;
	}

	@Reference(unbind = "-")
	protected void setMBThreadLocalService(
		MBThreadLocalService mbThreadLocalService) {

		_mbThreadLocalService = mbThreadLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private MBMessageLocalService _mbMessageLocalService;
	private MBThreadFlagLocalService _mbThreadFlagLocalService;
	private MBThreadLocalService _mbThreadLocalService;
	private UserLocalService _userLocalService;

}