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

import com.liferay.app.builder.model.AppBuilderAppDataRecordLink;
import com.liferay.app.builder.service.AppBuilderAppDataRecordLinkLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.WorkflowInstanceLink;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ModelListener.class)
public class WorkflowInstanceLinkModelListener
	extends BaseModelListener<WorkflowInstanceLink> {

	@Override
	public void onBeforeRemove(WorkflowInstanceLink workflowInstanceLink)
		throws ModelListenerException {

		try {
			AppBuilderAppDataRecordLink appBuilderAppDataRecordLink =
				_appBuilderAppDataRecordLinkLocalService.
					fetchDDLRecordAppBuilderAppDataRecordLink(
						workflowInstanceLink.getClassPK());

			if (Objects.isNull(appBuilderAppDataRecordLink)) {
				return;
			}

			_appBuilderAppDataRecordLinkLocalService.
				deleteAppBuilderAppDataRecordLink(
					appBuilderAppDataRecordLink.
						getAppBuilderAppDataRecordLinkId());
		}
		catch (Exception exception) {
			_log.error(
				"Unable to delete app builder app data record link", exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WorkflowInstanceLinkModelListener.class);

	@Reference
	private AppBuilderAppDataRecordLinkLocalService
		_appBuilderAppDataRecordLinkLocalService;

	@Reference
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}