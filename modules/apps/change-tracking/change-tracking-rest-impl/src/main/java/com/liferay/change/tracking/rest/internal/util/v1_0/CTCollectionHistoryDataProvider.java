/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.rest.internal.util.v1_0;

import com.liferay.change.tracking.constants.CTDestinationNames;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Noor Najjar
 */
public class CTCollectionHistoryDataProvider {

	public CTCollectionHistoryDataProvider(
		CTCollection ctCollection, HttpServletRequest httpServletRequest) {

		_ctCollection = ctCollection;
		_httpServletRequest = httpServletRequest;
	}

	public String getStatusMessage() {
		if (_ctCollection == null) {
			return StringPool.BLANK;
		}

		if (_ctCollection.getStatus() == WorkflowConstants.STATUS_APPROVED) {
			Date statusDate = _ctCollection.getStatusDate();

			return LanguageUtil.format(
				_httpServletRequest, "published-x-ago-by-x",
				new String[] {
					LanguageUtil.getTimeDescription(
						_httpServletRequest,
						System.currentTimeMillis() - statusDate.getTime(),
						true),
					HtmlUtil.escape(_ctCollection.getUserName())
				});
		}
		else if (_ctCollection.getStatus() == WorkflowConstants.STATUS_DRAFT) {
			Date modifiedDate = _ctCollection.getModifiedDate();

			return LanguageUtil.format(
				_httpServletRequest, "modified-x-ago-by-x",
				new String[] {
					LanguageUtil.getTimeDescription(
						_httpServletRequest,
						System.currentTimeMillis() - modifiedDate.getTime(),
						true),
					HtmlUtil.escape(_ctCollection.getUserName())
				});
		}
		else if (_ctCollection.getStatus() ==
					WorkflowConstants.STATUS_SCHEDULED) {

			try {
				SchedulerResponse schedulerResponse =
					SchedulerEngineHelperUtil.getScheduledJob(
						StringBundler.concat(
							_ctCollection.getCtCollectionId(), StringPool.AT,
							_ctCollection.getCompanyId()),
						CTDestinationNames.CT_COLLECTION_SCHEDULED_PUBLISH,
						StorageType.PERSISTED);

				if (schedulerResponse == null) {
					return null;
				}

				Date scheduledDate = SchedulerEngineHelperUtil.getStartTime(
					schedulerResponse);

				return LanguageUtil.format(
					_httpServletRequest, "schedule-to-publish-in-x-by-x",
					new String[] {
						LanguageUtil.getTimeDescription(
							_httpServletRequest,
							scheduledDate.getTime() -
								System.currentTimeMillis(),
							true),
						HtmlUtil.escape(_ctCollection.getUserName())
					});
			}
			catch (SchedulerException schedulerException) {
				_log.error(schedulerException);
			}
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTCollectionHistoryDataProvider.class);

	private final CTCollection _ctCollection;
	private final HttpServletRequest _httpServletRequest;

}