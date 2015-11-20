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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBThreadLocalService;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class MBThreadStagedModelDataHandler
	extends BaseStagedModelDataHandler<MBThread> {

	public static final String[] CLASS_NAMES = {MBThread.class.getName()};

	@Override
	public void deleteStagedModel(MBThread thread) throws PortalException {
		_mbThreadLocalService.deleteThread(thread);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		MBThread thread = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (thread != null) {
			deleteStagedModel(thread);
		}
	}

	@Override
	public MBThread fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _mbThreadLocalService.fetchMBThreadByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<MBThread> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _mbThreadLocalService.getMBThreadsByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, MBThread thread)
		throws Exception {
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long threadId)
		throws Exception {

		MBThread existingThread = fetchMissingReference(uuid, groupId);

		if (existingThread == null) {
			return;
		}

		Map<Long, Long> threadIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MBThread.class);

		threadIds.put(threadId, existingThread.getThreadId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, MBThread thread)
		throws Exception {
	}

	@Reference(unbind = "-")
	protected void setMBThreadLocalService(
		MBThreadLocalService mbThreadLocalService) {

		_mbThreadLocalService = mbThreadLocalService;
	}

	private volatile MBThreadLocalService _mbThreadLocalService;

}