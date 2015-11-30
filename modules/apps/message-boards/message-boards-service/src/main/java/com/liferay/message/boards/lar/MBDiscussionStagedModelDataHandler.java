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
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Layout;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelModifiedDateComparator;
import com.liferay.portlet.messageboards.NoSuchDiscussionException;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalService;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;

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

		long userId = portletDataContext.getUserId(discussion.getUserUuid());

		String className = discussion.getClassName();

		Map<Long, Long> relatedClassPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(className);

		long newClassPK = MapUtil.getLong(
			relatedClassPKs, discussion.getClassPK(), discussion.getClassPK());

		MBDiscussion existingDiscussion =
			_mbDiscussionLocalService.fetchDiscussion(
				discussion.getClassName(), newClassPK);

		if (existingDiscussion == null) {
			if (className.equals(Layout.class.getName()) &&
				PropsValues.LAYOUT_COMMENTS_ENABLED) {

				MBMessage rootMessage =
					_mbMessageLocalService.addDiscussionMessage(
						userId, discussion.getUserName(),
						portletDataContext.getScopeGroupId(), className,
						newClassPK, WorkflowConstants.ACTION_PUBLISH);

				existingDiscussion =
					_mbDiscussionLocalService.getThreadDiscussion(
						rootMessage.getThreadId());
			}
			else {
				throw new NoSuchDiscussionException();
			}
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

	private volatile AssetEntryLocalService _assetEntryLocalService;
	private volatile MBDiscussionLocalService _mbDiscussionLocalService;
	private volatile MBMessageLocalService _mbMessageLocalService;

}