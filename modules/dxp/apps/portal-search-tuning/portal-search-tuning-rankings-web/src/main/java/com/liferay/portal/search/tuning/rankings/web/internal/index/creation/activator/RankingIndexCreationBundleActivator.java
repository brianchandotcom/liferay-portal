/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index.creation.activator;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.uuid.PortalUUID;
import com.liferay.portal.search.tuning.rankings.web.internal.background.task.RankingIndexCreationBackgroundTaskExecutor;
import com.liferay.portal.search.tuning.rankings.web.internal.index.importer.SingleIndexToMultipleIndexImporter;

import java.util.HashMap;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 * @author Adam Brandizzi
 */
@Component(
	immediate = true, service = RankingIndexCreationBundleActivator.class
)
public class RankingIndexCreationBundleActivator {

	@Activate
	protected void activate() {
		if (_singleIndexToMultipleIndexImporter.needImport()) {
			_addBackgroundTask();
		}
	}

	private void _addBackgroundTask() {
		try {
			_backgroundTaskManager.addBackgroundTask(
				UserConstants.USER_ID_DEFAULT, CompanyConstants.SYSTEM,
				"createRankingIndex-" + _portalUUID.generate(),
				RankingIndexCreationBackgroundTaskExecutor.class.getName(),
				new HashMap<>(), new ServiceContext());
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to schedule the job for RankingIndexRename",
				portalException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RankingIndexCreationBundleActivator.class);

	@Reference
	private BackgroundTaskManager _backgroundTaskManager;

	@Reference
	private PortalUUID _portalUUID;

	@Reference
	private RankingIndexCreationBackgroundTaskExecutor
		_rankingIndexRenameBackgroundTaskExecutor;

	@Reference
	private SingleIndexToMultipleIndexImporter
		_singleIndexToMultipleIndexImporter;

}