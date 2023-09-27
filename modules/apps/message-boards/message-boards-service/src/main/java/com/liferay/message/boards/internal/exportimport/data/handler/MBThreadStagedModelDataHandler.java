/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(service = StagedModelDataHandler.class)
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

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

}