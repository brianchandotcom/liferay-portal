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

package com.liferay.message.boards.lar;

import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelModifiedDateComparator;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.MBThreadFlag;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.portlet.messageboards.service.MBThreadFlagLocalService;
import com.liferay.portlet.messageboards.service.MBThreadLocalService;

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

	private volatile MBMessageLocalService _mbMessageLocalService;
	private volatile MBThreadFlagLocalService _mbThreadFlagLocalService;
	private volatile MBThreadLocalService _mbThreadLocalService;
	private volatile UserLocalService _userLocalService;

}