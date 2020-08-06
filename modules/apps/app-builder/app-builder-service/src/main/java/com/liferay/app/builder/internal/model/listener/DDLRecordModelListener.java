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

package com.liferay.app.builder.internal.model.listener;

import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.model.AppBuilderAppDataRecordLink;
import com.liferay.app.builder.service.AppBuilderAppDataRecordLinkLocalService;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = ModelListener.class)
public class DDLRecordModelListener extends BaseModelListener<DDLRecord> {

	@Override
	public void onBeforeRemove(DDLRecord ddlRecord)
		throws ModelListenerException {

		try {
			AppBuilderAppDataRecordLink appBuilderAppDataRecordLink =
				_appBuilderAppDataRecordLinkLocalService.
					fetchDDLRecordAppBuilderAppDataRecordLink(
						ddlRecord.getRecordId());

			if (Objects.isNull(appBuilderAppDataRecordLink)) {
				return;
			}

			_appBuilderAppDataRecordLinkLocalService.
				deleteAppBuilderAppDataRecordLink(
					appBuilderAppDataRecordLink.
						getAppBuilderAppDataRecordLinkId());

			_workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
				appBuilderAppDataRecordLink.getCompanyId(),
				appBuilderAppDataRecordLink.getGroupId(),
				ResourceActionsUtil.getCompositeModelName(
					AppBuilderApp.class.getName(), DDLRecord.class.getName()),
				ddlRecord.getRecordId());
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDLRecordModelListener.class);

	@Reference
	private AppBuilderAppDataRecordLinkLocalService
		_appBuilderAppDataRecordLinkLocalService;

	@Reference
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}