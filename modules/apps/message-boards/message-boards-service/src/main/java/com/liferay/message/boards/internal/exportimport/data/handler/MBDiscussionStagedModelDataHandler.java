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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBDiscussionLocalService;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
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
public class MBDiscussionStagedModelDataHandler
	extends BaseStagedModelDataHandler<MBDiscussion> {

	public static final String[] CLASS_NAMES = {MBDiscussion.class.getName()};

	@Override
	public void deleteStagedModel(MBDiscussion discussion) {
		_mbDiscussionLocalService.deleteMBDiscussion(discussion);
	}

	@Override
	public void deleteStagedModel(
		String uuid, long groupId, String className, String extraData) {

		MBDiscussion discussion = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (discussion != null) {
			deleteStagedModel(discussion);
		}
	}

	@Override
	public MBDiscussion fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _mbDiscussionLocalService.fetchMBDiscussionByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<MBDiscussion> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _mbDiscussionLocalService.getMBDiscussionsByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<MBDiscussion>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(MBDiscussion discussion) {
		try {
			AssetEntry assetEntry = _assetEntryLocalService.getEntry(
				discussion.getClassName(), discussion.getClassPK());

			return assetEntry.getTitleCurrentValue();
		}
		catch (Exception e) {
			return discussion.getUuid();
		}
	}

	@Override
	public void importStagedModel(
			PortletDataContext portletDataContext, MBDiscussion discussion)
		throws PortletDataException {

		Map<Long, Long> relatedClassPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				discussion.getClassName());

		if (!relatedClassPKs.containsKey(discussion.getClassPK())) {
			return;
		}

		super.importStagedModel(portletDataContext, discussion);
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, MBDiscussion discussion)
		throws Exception {

		Element discussionElement = portletDataContext.getExportDataElement(
			discussion);

		portletDataContext.addClassedModel(
			discussionElement, ExportImportPathUtil.getModelPath(discussion),
			discussion);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, MBDiscussion discussion)
		throws Exception {

		String className = discussion.getClassName();

		Map<Long, Long> relatedClassPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(className);

		long newClassPK = MapUtil.getLong(
			relatedClassPKs, discussion.getClassPK(), discussion.getClassPK());

		MBDiscussion existingDiscussion =
			_mbDiscussionLocalService.fetchDiscussion(
				discussion.getClassName(), newClassPK);

		if (existingDiscussion == null) {
			long userId = portletDataContext.getUserId(
				discussion.getUserUuid());

			MBMessage rootMessage = _mbMessageLocalService.addDiscussionMessage(
				userId, discussion.getUserName(),
				portletDataContext.getScopeGroupId(), className, newClassPK,
				WorkflowConstants.ACTION_PUBLISH);

			rootMessage.setCreateDate(discussion.getCreateDate());

			_mbMessageLocalService.updateMBMessage(rootMessage);

			existingDiscussion = _mbDiscussionLocalService.getThreadDiscussion(
				rootMessage.getThreadId());
		}

		Map<Long, Long> discussionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MBDiscussion.class);

		discussionIds.put(
			discussion.getDiscussionId(), existingDiscussion.getDiscussionId());

		Map<Long, Long> threadIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MBThread.class);

		threadIds.put(
			discussion.getThreadId(), existingDiscussion.getThreadId());
	}

	@Reference(unbind = "-")
	protected void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setMBDiscussionLocalService(
		MBDiscussionLocalService mbDiscussionLocalService) {

		_mbDiscussionLocalService = mbDiscussionLocalService;
	}

	@Reference(unbind = "-")
	protected void setMBMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {

		_mbMessageLocalService = mbMessageLocalService;
	}

	private AssetEntryLocalService _assetEntryLocalService;
	private MBDiscussionLocalService _mbDiscussionLocalService;
	private MBMessageLocalService _mbMessageLocalService;

}